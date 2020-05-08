package edu.hfut.mobus.handler;

import edu.hfut.mobus.entity.ModbusTcpFrame;
import edu.hfut.mobus.utils.ModbusConstants;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 *这是channel初始化的构�?�函数每�?次channel创建的时候都会用到这个类的initChannel函数（回调）
 * @author 
 */
public class ModbusTcpChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        /*
         * Modbus TCP Frame Description
         *  - max. 260 Byte (ADU = 7 Byte MBAP + 253 Byte PDU)
         *  - Length field includes Unit Identifier + PDU
         *
         * <----------------------------------------------- ADU -------------------------------------------------------->
         * <---------------------------- MBAP -----------------------------------------><------------- PDU ------------->
         * +------------------------+---------------------+----------+-----------------++---------------+---------------+
         * | Transaction Identifier | Protocol Identifier | Length   | Unit Identifier || Function Code | Data          |
         * | (2 Byte)               | (2 Byte)            | (2 Byte) | (1 Byte)        || (1 Byte)      | (1 - 252 Byte |
         * +------------------------+---------------------+----------+-----------------++---------------+---------------+
         */
        //入站的时候进行分割,这个是遵循一定的规则的Decoder
        //他的参数遵循以下规则:第一个参数是帧的最大长度，超出就丢弃
        //第二个参数是长度域，比如说在modbus协议中第5,6个字节就是长度（对应下标4,5），他检测到长度域后会根据长度域的值获取后面的字节
        //比如长度域值是6，他知道后面还有6个字节，读取长度域后的6个字节自动转化成一帧（标识着一帧的结束），也就是说
        //modbus tcp刚好有长度域，所以才能用这个特殊的解码器，否则类似rtu就不适用了
        //第三个参数当然是长度域所占的字节数啦
        //所以第一个解码器将长度固定
        pipeline.addLast("framer", new LengthFieldBasedFrameDecoder(ModbusConstants.ADU_MAX_LENGTH, 4, 2));

        //Modbus encoder, decoder
        //编码器，看起来他是encoder,其实他就是ChannelOutboundHandlerAdapter,就是你发送帧的时候就会触发
        pipeline.addLast("encoder", new ModbusEncoder());
        //第二个解码器将二进制转化成实体类
        pipeline.addLast("decoder", new ModbusTcpDecoder());
       

        pipeline.addLast("responseHandler", new ModbusTcpResponseHandler() {
                @Override
                public void newResponse(ModbusTcpFrame frame) {
                    //discard in sync mode
                	int transactionIdentifier = frame.getHeader().getTransactionIdentifier();
                	System.out.println("目前的的事务标识："+transactionIdentifier);
                }
            });
    }
}
