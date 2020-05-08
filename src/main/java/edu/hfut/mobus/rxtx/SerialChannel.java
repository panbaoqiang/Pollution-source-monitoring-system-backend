package edu.hfut.mobus.rxtx;

import io.netty.buffer.ByteBuf;
import io.netty.channel.rxtx.RxtxChannel;


          /*一个串口的通道，为了调用他的doWriteBytes，因为RxtxChannel的该方法是protect的*/
public class SerialChannel extends RxtxChannel {

	public void writeOrderToMachine(ByteBuf buf) throws Exception {
		this.doWriteBytes(buf);
	}
}
