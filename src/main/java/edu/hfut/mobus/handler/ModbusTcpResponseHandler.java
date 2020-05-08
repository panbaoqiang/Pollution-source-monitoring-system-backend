package edu.hfut.mobus.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.hfut.mobus.entity.ModbusTcpFrame;
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
public abstract class ModbusTcpResponseHandler extends SimpleChannelInboundHandler<ModbusTcpFrame> {

    private static final Logger logger = Logger.getLogger(ModbusTcpResponseHandler.class.getSimpleName());
    private final Map<Integer, ModbusTcpFrame> responses = new HashMap<>(ModbusConstants.TRANSACTION_IDENTIFIER_MAX);

    //异步获取结果
    public ModbusTcpFrame getResponse(int transactionIdentifier)
            throws NoResponseException, ErrorResponseException {

        long timeoutTime = System.currentTimeMillis() + ModbusConstants.SYNC_TCP_RESPONSE_TIMEOUT;
        ModbusTcpFrame frame;
        do {
            frame = responses.get(transactionIdentifier);
        } while (frame == null && (timeoutTime - System.currentTimeMillis()) > 0);

        if (frame != null) {
            responses.remove(transactionIdentifier);
        }

        if (frame == null) {
            throw new NoResponseException();
        } else if (frame.getFunction() instanceof ModbusError) {
            throw new ErrorResponseException((ModbusError) frame.getFunction());
        }
        return frame;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.log(Level.SEVERE, cause.getLocalizedMessage());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ModbusTcpFrame response) throws Exception {
        //不管同步还是异步，先存
    	responses.put(response.getHeader().getTransactionIdentifier(), response);
    	//如果是同步的，直接通知，newResponse(response)指的是同步
        newResponse(response);
    }
    //同步获取，首先，有些东西可以异步获取，比如说我发一个指令，那么我可能在很久之后才会去拿他们肯定用的是上面的getResponse函数
    //但是还有一种可能是我必须要一发指令拿到后才会发第二条指令，那么
    //就用到这个函数了,也就是说除了把硬件相应的那些modbus指令，可能还需要及时报警的比如说如果是frame
    //是modbus Error ,通知一个组件立马去执行某一个报警操作，比如停止发送指令呀
    //或者是重新连接呀之类的
    public abstract void newResponse(ModbusTcpFrame frame);
}
