package edu.hfut.mobus.client;

import edu.hfut.mobus.entity.ModbusFunction;
import edu.hfut.mobus.entity.ModbusRtuFrame;
import edu.hfut.mobus.entity.exception.ConnectionException;
import edu.hfut.mobus.entity.exception.ErrorResponseException;
import edu.hfut.mobus.entity.exception.NoResponseException;
import edu.hfut.mobus.entity.func.WriteSingleCoil;
import edu.hfut.mobus.entity.func.WriteSingleRegister;
import edu.hfut.mobus.entity.func.request.*;
import edu.hfut.mobus.entity.func.response.*;
import edu.hfut.mobus.handler.ModbusRtuChannelInitializer;
import edu.hfut.mobus.handler.ModbusRtuResponseHandler;
import edu.hfut.mobus.rxtx.SerialChannel;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFactory;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.rxtx.RxtxChannelConfig;
import io.netty.channel.rxtx.RxtxDeviceAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.BitSet;


//确认一次只能发一条，确定接受成功或接受失败后才可以发第二条
public class ModbusRtuProcessor {
	private Logger logger = LoggerFactory.getLogger(ModbusRtuProcessor.class);
	private String portName;//COM7
	private int baudrate;//波特率
	private SerialChannel channel;//串口连接的通道
    public boolean sendStatus;//true 可以发送，false说明不能发送等待响应，确定消息帧失败还是成功都要更新这个字段
	public ModbusRtuProcessor(String portName, int baudrate) {
		this.portName = portName;
		this.baudrate = baudrate;
	}


	public void setup() throws InterruptedException {//这个只是连接那个rs485usb转接口
		OioEventLoopGroup group = new OioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channelFactory(new ChannelFactory<SerialChannel>() {
				public SerialChannel newChannel() {
					return channel;
				}
			}).handler(new ModbusRtuChannelInitializer());
			channel = new SerialChannel();
			channel.config().setBaudrate(baudrate);
			channel.config().setDatabits(RxtxChannelConfig.Databits.DATABITS_8);
			channel.config().setParitybit(RxtxChannelConfig.Paritybit.NONE);
			channel.config().setStopbits(RxtxChannelConfig.Stopbits.STOPBITS_1);
			channel.config().setConnectTimeoutMillis(3000);
			channel.config().setWriteBufferHighWaterMark(10 * 1024 * 64);
			channel.config().setWaitTimeMillis(0);
			channel.config().setAutoRead(true);

			ChannelFuture future = b.connect(new RxtxDeviceAddress(portName)).sync();
			future.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if (future.isSuccess()) {
						logger.info("串口硬件连接成功");
						sendStatus = true;//可以发送了
					} else {
						logger.info("串口硬件连接失败");
					}
				}
			});//future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			if (channel != null && channel.isOpen()) {
				channel.close();
				channel = null;
			}
			group.shutdownGracefully();
			throw e;
		}
	}

	private void closePart() throws ConnectionException {
		if (channel != null) {
			channel.close();
			channel = null;
		} else {
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
		return channel == null || !channel.isOpen();
	}


	public void callModbusFunction(ModbusFunction function, short unitIdentifier) throws ConnectionException {
		if (channel == null) {
			throw new ConnectionException("Not connected!");
		}
		ModbusRtuFrame frame = new ModbusRtuFrame(unitIdentifier, function);
		logger.info("*************************************");
		logger.info(frame.getHeader()+"");//地址
		logger.info(frame.getFunction().toString());//功能码
		logger.info("*************************************");
		try {
			while(!sendStatus) {} //sendStatus为true说明不能发送，一直循环
			//到这里说明sendStatus变成true
			channel.writeOrderToMachine(frame.encode());
			sendStatus = false;//发送后立马更改成不能发送
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public <V extends ModbusFunction> V callModbusFunctionSync(ModbusFunction function,short unitIdentifier)
			throws NoResponseException, ErrorResponseException, ConnectionException {

		callModbusFunction(function,unitIdentifier);//发送
		ModbusRtuResponseHandler handler = (ModbusRtuResponseHandler) channel.pipeline().get("rtuResponseHandler");
		if (handler == null) {
			throw new ConnectionException("Not connected!");
		}
		ModbusFunction responseFunction = null;
		try {
			responseFunction = handler.getResponse().getFunction();
		} catch (Exception e) {
			throw e;//扔出去
		}finally {
			this.sendStatus = true;//可以发送了
		}
		return (V) responseFunction;
	}

	// sync function execution
	public WriteSingleCoil writeSingleCoil(int address, boolean state, short unitIdentifier)
			throws NoResponseException, ErrorResponseException, ConnectionException {
		return this.<WriteSingleCoil>callModbusFunctionSync(new WriteSingleCoil(address, state),unitIdentifier);
	}

	public WriteSingleRegister writeSingleRegister(int address, int value, short unitIdentifier)
			throws NoResponseException, ErrorResponseException, ConnectionException {
		return this.<WriteSingleRegister>callModbusFunctionSync(new WriteSingleRegister(address, value),unitIdentifier);
	}

	public ReadCoilsResponse readCoils(int startAddress, int quantityOfCoils,short unitIdentifier)
			throws NoResponseException, ErrorResponseException, ConnectionException {
		return this.<ReadCoilsResponse>callModbusFunctionSync(new ReadCoilsRequest(startAddress, quantityOfCoils),unitIdentifier);
	}

	public ReadDiscreteInputsResponse readDiscreteInputs(int startAddress, int quantityOfCoils,short unitIdentifier)
			throws NoResponseException, ErrorResponseException, ConnectionException {
		return this.<ReadDiscreteInputsResponse>callModbusFunctionSync(
				new ReadDiscreteInputsRequest(startAddress, quantityOfCoils),unitIdentifier);
	}

	public ReadInputRegistersResponse readInputRegisters(int startAddress, int quantityOfInputRegisters, short unitIdentifier)
			throws NoResponseException, ErrorResponseException, ConnectionException {
		return this.<ReadInputRegistersResponse>callModbusFunctionSync(
				new ReadInputRegistersRequest(startAddress, quantityOfInputRegisters),unitIdentifier);
	}

	public ReadHoldingRegistersResponse readHoldingRegisters(int startAddress, int quantityOfInputRegisters, short unitIdentifier)
			throws NoResponseException, ErrorResponseException, ConnectionException {
		return this.<ReadHoldingRegistersResponse>callModbusFunctionSync(
				new ReadHoldingRegistersRequest(startAddress, quantityOfInputRegisters),unitIdentifier);
	}

	public WriteMultipleCoilsResponse writeMultipleCoils(int address, int quantityOfOutputs, BitSet outputsValue, short unitIdentifier)
			throws NoResponseException, ErrorResponseException, ConnectionException {
		return this.<WriteMultipleCoilsResponse>callModbusFunctionSync(
				new WriteMultipleCoilsRequest(address, quantityOfOutputs, outputsValue),unitIdentifier);
	}

	public WriteMultipleRegistersResponse writeMultipleRegisters(int address, int quantityOfRegisters, int[] registers, short unitIdentifier)
			throws NoResponseException, ErrorResponseException, ConnectionException {
		return this.<WriteMultipleRegistersResponse>callModbusFunctionSync(
				new WriteMultipleRegistersRequest(address, quantityOfRegisters, registers),unitIdentifier);
	}
	
	public static void main(String []args) throws NoResponseException, ErrorResponseException, ConnectionException, InterruptedException {

	}

}
