package edu.hfut.mobus.client;

import edu.hfut.mobus.entity.ModbusFunction;
import edu.hfut.mobus.entity.ModbusTcpFrame;
import edu.hfut.mobus.entity.ModbusTcpHeader;
import edu.hfut.mobus.entity.exception.ConnectionException;
import edu.hfut.mobus.entity.exception.ErrorResponseException;
import edu.hfut.mobus.entity.exception.NoResponseException;
import edu.hfut.mobus.entity.func.WriteSingleCoil;
import edu.hfut.mobus.entity.func.WriteSingleRegister;
import edu.hfut.mobus.entity.func.request.*;
import edu.hfut.mobus.entity.func.response.*;
import edu.hfut.mobus.handler.ModbusTcpChannelInitializer;
import edu.hfut.mobus.handler.ModbusTcpResponseHandler;
import edu.hfut.mobus.utils.ModbusConstants;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.LoggerFactory;

import java.util.BitSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import static edu.hfut.mobus.utils.ModbusConstants.DEFAULT_PROTOCOL_IDENTIFIER;

public class ModbusTcpProcessor {//相当于一个Channel,连接硬件而已，设备有没有不清楚
    private org.slf4j.Logger logger = LoggerFactory.getLogger(ModbusTcpProcessor.class);
    private final String host;//默认�?502
    private final int port;//硬件服务器，项目�?192.168.2.109
    private int lastTransactionIdentifier = 0;//每发�?个指�?,�?1
    private Channel channel;//连接硬件的channel
    private Bootstrap bootstrap;//引导�?
    private short protocolIdentifier;//协议
   
    public ModbusTcpProcessor(String host, int port) {
        this(host, port, DEFAULT_PROTOCOL_IDENTIFIER);
    }


    public ModbusTcpProcessor(String host, int port, short protocolIdentifier) {
        this.host = host;
        this.port = port;
        this.protocolIdentifier = protocolIdentifier;
    }



    public void setup() throws ConnectionException {
        try {
            final EventLoopGroup workerGroup = new NioEventLoopGroup();

            bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ModbusTcpChannelInitializer());

           

            ChannelFuture f = bootstrap.connect(host, port).sync();
            f.addListener(new GenericFutureListener<ChannelFuture>() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if (future.isSuccess()) {
                        logger.info("Tcp硬件连接成功");
					} else {
                        logger.info("Tcp硬件连接失败");
					}
				}
            });

            channel = f.channel();

            channel.closeFuture().addListener(new GenericFutureListener<ChannelFuture>() {

                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    workerGroup.shutdownGracefully();
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(ModbusTcpProcessor.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
            throw new ConnectionException(ex.getLocalizedMessage());
        }

    }

    public Channel getChannel() {
        return channel;
    }

    private void closePart() throws ConnectionException {
        if (channel != null) {
            channel.close().awaitUninterruptibly();
            channel = null;
        }else {
        	throw new ConnectionException("关闭异常");
        }
    }
    
    public void close() throws ConnectionException {
    	try {
			closePart();
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			throw e;
		}
    }
    public boolean isClose() {
    	//连接状态是未连接
    	try {
    		this.close();
			this.setup();
		} catch (ConnectionException e) {
			return true;
		}
    	return false;
//    	return channel == null || !channel.isOpen();
    	//为什么不用return channel == null || !channel.isOpen();呢，因为channel.isOpen()不管是网线拨出来还是插进去都是返回true，无解，干脆重新连接
    }

    private synchronized int calculateTransactionIdentifier() {
        if (lastTransactionIdentifier < ModbusConstants.TRANSACTION_IDENTIFIER_MAX) {
            lastTransactionIdentifier++;
        } else {
            lastTransactionIdentifier = 1;
        }
        return lastTransactionIdentifier;
    }







    //单纯的写进去，返回一个事务的值，可能不需要看返回值
    //由于function有很多种
    //所以callModbusFunction的具体实现也分为很多种
    //就是以下的
    //writeSingleCoilAsync， 相当于callModbusFunction传进去的参数是new WriteSingleCoil(address, state)
    //writeSingleRegisterAsync，相当于callModbusFunction传进去的参数是new WriteSingleRegister(address, value)
    //readCoilsAsync，相当于callModbusFunction传进去的参数是new ReadCoilsRequest(startAddress, quantityOfCoils)
    //readDiscreteInputsAsync，相当于callModbusFunction传进去的参数是new ReadDiscreteInputsRequest(startAddress, quantityOfCoils)
    //readInputRegistersAsync，相当于callModbusFunction传进去的参数是new ReadInputRegistersRequest(startAddress, quantityOfInputRegisters)
    //readHoldingRegistersAsync，相当于callModbusFunction传进去的参数是new ReadHoldingRegistersRequest(startAddress, quantityOfInputRegisters)
    //writeMultipleCoilsAsync，相当于callModbusFunction传进去的参数是new WriteMultipleCoilsRequest(address, quantityOfOutputs, outputsValue)
    //writeMultipleRegistersAsync相当于callModbusFunction传进去的参数是new WriteMultipleRegistersRequest(address, quantityOfRegisters, registers)
    //这些函数相当于我发送出去后就不管了，只要知道事务的id就可以
    public int callModbusFunction(ModbusFunction function, short unitIdentifier)
            throws ConnectionException {

        if (channel == null) {
            throw new ConnectionException("Not connected!");
        }

        int transactionId = calculateTransactionIdentifier();
        int pduLength = function.calculateLength();

        ModbusTcpHeader header = new ModbusTcpHeader(transactionId, protocolIdentifier, pduLength, unitIdentifier);
        ModbusTcpFrame frame = new ModbusTcpFrame(header, function);
        
        //这里把他的结构查看一下
        logger.info("*************************************");
        logger.info(frame.getHeader().toString());//头部
        logger.info(frame.getFunction().toString());//功能码
        logger.info("*************************************");
        channel.writeAndFlush(frame);
        return transactionId;
    }

    public <V extends ModbusFunction> V callModbusFunctionSync(ModbusFunction function,short unitIdentifier)
            throws NoResponseException, ErrorResponseException, ConnectionException {

        int transactionId = callModbusFunction(function,unitIdentifier);

        ModbusTcpResponseHandler handler = (ModbusTcpResponseHandler) channel.pipeline().get("responseHandler");
        if (handler == null) {
            throw new ConnectionException("Not connected!");
        }

        //TODO handle cast exception!?
        return (V) handler.getResponse(transactionId).getFunction();
//        return null;
    }

    //async 异步写当个线圈 05 ，单纯的写进去
    public int writeSingleCoilAsync(int address, boolean state,short unitIdentifier) throws ConnectionException {
        return callModbusFunction(new WriteSingleCoil(address, state),unitIdentifier);
    }

    //写单个寄存器06，单纯的写进去
    public int writeSingleRegisterAsync(int address, int value,short unitIdentifier) throws ConnectionException {
        return callModbusFunction(new WriteSingleRegister(address, value), unitIdentifier);
    }

    //读线圈 01，单纯的写进去
    public int readCoilsAsync(int startAddress, int quantityOfCoils,short unitIdentifier) throws ConnectionException {
        return callModbusFunction(new ReadCoilsRequest(startAddress, quantityOfCoils), unitIdentifier);
    }

    //读输入线圈02，单纯的写进去
    public int readDiscreteInputsAsync(int startAddress, int quantityOfCoils,short unitIdentifier) throws ConnectionException {
        return callModbusFunction(new ReadDiscreteInputsRequest(startAddress, quantityOfCoils), unitIdentifier);
    }

    //读输入寄存器 04，单纯的写进去
    public int readInputRegistersAsync(int startAddress, int quantityOfInputRegisters,short unitIdentifier) throws ConnectionException {
        return callModbusFunction(new ReadInputRegistersRequest(startAddress, quantityOfInputRegisters), unitIdentifier);
    }

    //读保持寄存器 03，单纯的写进去
    public int readHoldingRegistersAsync(int startAddress, int quantityOfInputRegisters,short unitIdentifier) throws ConnectionException {
        return callModbusFunction(new ReadHoldingRegistersRequest(startAddress, quantityOfInputRegisters), unitIdentifier);
    }

    //写多个线圈 0F，单纯的写进去
    public int writeMultipleCoilsAsync(int address, int quantityOfOutputs, BitSet outputsValue,short unitIdentifier) throws ConnectionException {
        return callModbusFunction(new WriteMultipleCoilsRequest(address, quantityOfOutputs, outputsValue), unitIdentifier);
    }

    //写多个保持寄存器16，单纯的写进去
    public int writeMultipleRegistersAsync(int address, int quantityOfRegisters, int[] registers,short unitIdentifier) throws ConnectionException {
        return callModbusFunction(new WriteMultipleRegistersRequest(address, quantityOfRegisters, registers), unitIdentifier);
    }

    //sync function execution
    public WriteSingleCoil writeSingleCoil(int address, boolean state,short unitIdentifier)
            throws NoResponseException, ErrorResponseException, ConnectionException {
        return this.<WriteSingleCoil>callModbusFunctionSync(new WriteSingleCoil(address, state), unitIdentifier);
    }

    public WriteSingleRegister writeSingleRegister(int address, int value,short unitIdentifier)
            throws NoResponseException, ErrorResponseException, ConnectionException {
        return this.<WriteSingleRegister>callModbusFunctionSync(new WriteSingleRegister(address, value), unitIdentifier);
    }

    public ReadCoilsResponse readCoils(int startAddress, int quantityOfCoils, short unitIdentifier)
            throws NoResponseException, ErrorResponseException, ConnectionException {
        return this.<ReadCoilsResponse>callModbusFunctionSync(new ReadCoilsRequest(startAddress, quantityOfCoils), unitIdentifier);
    }

    public ReadDiscreteInputsResponse readDiscreteInputs(int startAddress, int quantityOfCoils, short unitIdentifier)
            throws NoResponseException, ErrorResponseException, ConnectionException {
        return this.<ReadDiscreteInputsResponse>callModbusFunctionSync(new ReadDiscreteInputsRequest(startAddress, quantityOfCoils), unitIdentifier);
    }

    public ReadInputRegistersResponse readInputRegisters(int startAddress, int quantityOfInputRegisters, short unitIdentifier)
            throws NoResponseException, ErrorResponseException, ConnectionException {
        return this.<ReadInputRegistersResponse>callModbusFunctionSync(new ReadInputRegistersRequest(startAddress, quantityOfInputRegisters), unitIdentifier);
    }

    public ReadHoldingRegistersResponse readHoldingRegisters(int startAddress, int quantityOfInputRegisters, short unitIdentifier)
            throws NoResponseException, ErrorResponseException, ConnectionException {
        return this.<ReadHoldingRegistersResponse>callModbusFunctionSync(new ReadHoldingRegistersRequest(startAddress, quantityOfInputRegisters), unitIdentifier);
    }

    public WriteMultipleCoilsResponse writeMultipleCoils(int address, int quantityOfOutputs, BitSet outputsValue, short unitIdentifier)
            throws NoResponseException, ErrorResponseException, ConnectionException {
        return this.<WriteMultipleCoilsResponse>callModbusFunctionSync(new WriteMultipleCoilsRequest(address, quantityOfOutputs, outputsValue), unitIdentifier);
    }

    public WriteMultipleRegistersResponse writeMultipleRegisters(int address, int quantityOfRegisters, int[] registers,short unitIdentifier)
            throws NoResponseException, ErrorResponseException, ConnectionException {
        return this.<WriteMultipleRegistersResponse>callModbusFunctionSync(new WriteMultipleRegistersRequest(address, quantityOfRegisters, registers), unitIdentifier);
    }
}
