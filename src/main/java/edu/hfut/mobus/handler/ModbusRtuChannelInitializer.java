package edu.hfut.mobus.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.rxtx.RxtxChannel;
import io.netty.channel.socket.SocketChannel;

/**
 *这是channel初始化的构�?�函数每�?次channel创建的时候都会用到这个类的initChannel函数（回调）
 * @author 
 */
public class ModbusRtuChannelInitializer extends ChannelInitializer<RxtxChannel> {

    @Override
    protected void initChannel(RxtxChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        /*
         * Modbus TCP Frame Description
         *  - max. 260 Byte (ADU = 7 Byte MBAP + 253 Byte PDU)
         *  - Length field includes Unit Identifier + PDU
         *
         * <-------------------------ADU----------------------- -------->
         * <---------------------------- Body-------- --------><--CRC -->
         * +-----------------++---------------+---------------+---------+
         * | Unit Identifier || Function Code | Data          |   Crc   |
         * | (1 Byte)        || (1 Byte)      | (1 - 252 Byte | (2 Byte)|
         * +-----------------++---------------+---------------+---------+
         */
        //没有长度域默认不会粘包
        //Modbus encoder, decoder
        //编码器，看起来他是encoder,其实他就是ChannelOutboundHandlerAdapter,就是你发送帧的时候就会触�?
        pipeline.addLast("encoder", new ModbusEncoder());//tcp跟rtu共用一个，rtu直接写出去
        //第二个解码器将二进制转化成实体类
        pipeline.addLast("decoder", new ModbusRtuDecoder());
       

        pipeline.addLast("rtuResponseHandler", new ModbusRtuResponseHandler());
    }
}
