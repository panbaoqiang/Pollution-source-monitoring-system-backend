package edu.hfut.mobus.handler;


import edu.hfut.mobus.entity.ModbusTcpFrame;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * 这会被我们当成出站的�?个out handler,记住我们约定
 * �?有的这个项目架构�?有的对象都会先用channel.writeAndFlush(Object)
 * 然后这个Object会第�?个传递到这里
 * 然后我们在根据他的格式去编码成他的Tcp 对应的byte 格式
 * @author ares
 */
public class ModbusEncoder extends ChannelOutboundHandlerAdapter {

	//ChannelPromise这个参数，我们可以调用它的addListener注册监听，当回调方法在对应的操作完成后，会触发这个监听
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof ModbusTcpFrame) {
            ModbusTcpFrame frame = (ModbusTcpFrame) msg;
            //frame的header编码一定是写死的
            //但是他的function编码会根据我么实际的功能去初始化他的二进制格式
            //比如这个function是05
            //那么function编码就是
            //05 xxxx(寄存器首地址) xxxx（开或者关闭）
            //那她encode一定是这个格式
            //如果是0F:写多个线圈
            //那么他的格式就是
            //0F(功能码)+xxxx(线圈首地址)+xxxx(线圈数量)+xx(几个字节：’线圈数量+7’余8)+（这几个字节）
            //所以虽然我不知道这个function是哪一个，但是每一个function实体类都编码好他自定义的二进制格式
            ctx.writeAndFlush(frame.encode());
        } else {
        	//如果他本身是二进制流，我们默认他是modbus tcp的byte数组
//        	rtu我直接写
            ctx.write(msg);
        }
    }
}
