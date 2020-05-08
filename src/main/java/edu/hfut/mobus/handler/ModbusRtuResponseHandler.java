package edu.hfut.mobus.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


import edu.hfut.mobus.entity.ModbusRtuFrame;
import edu.hfut.mobus.entity.exception.ErrorResponseException;
import edu.hfut.mobus.entity.exception.NoResponseException;
import edu.hfut.mobus.entity.func.ModbusError;
import edu.hfut.mobus.utils.ModbusConstants;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
   *这是是对于客户端的，硬件服务器返回一个response,然后我们软件接受即可
 * @author ares
 */
public  class ModbusRtuResponseHandler extends SimpleChannelInboundHandler<ModbusRtuFrame> {

    private static final Logger logger = Logger.getLogger(ModbusRtuResponseHandler.class.getSimpleName());
    private final Map<Integer, ModbusRtuFrame> responses = new HashMap<>(ModbusConstants.TRANSACTION_IDENTIFIER_MAX);
    public  volatile ModbusRtuFrame frame;
    //异步获取结果
   
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.log(Level.SEVERE, cause.getLocalizedMessage());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ModbusRtuFrame response) throws Exception {
    	this.frame = response;
    }
    
    //异步获取结果
    public  synchronized ModbusRtuFrame getResponse()
            throws NoResponseException, ErrorResponseException {//这个方法会导致异常
        long timeoutTime = System.currentTimeMillis() + ModbusConstants.SYNC_RTU_RESPONSE_TIMEOUT;//超时
        while (frame == null && (timeoutTime - System.currentTimeMillis()) > 0) {};
      //就是要么frame答案有值了，要么超时没答案才会跳过，永远不会死循环，因为第二个不等式
//        System.out.println("哑谜"+(frame == null));
        ModbusRtuFrame frameTmp = null;
        if (frame != null) {//有答案
        	frameTmp = this.frame;//暂存
        	this.frame = null;//清空等待下一个可以发送的指令，这里不设置更新sendStatus字段
        }else if (this.frame == null) {
            throw new NoResponseException();
        } else if (this.frame.getFunction() instanceof ModbusError) {
            throw new ErrorResponseException((ModbusError) frame.getFunction());
        }
        //不管怎么样，抛出异常的时候frame都是空，也就是初始化状态
        return frameTmp;
    }

}
