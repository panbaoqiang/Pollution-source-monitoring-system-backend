package edu.hfut.mobus.entity.func;


import edu.hfut.mobus.entity.ModbusFunction;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 起初ModbusFunction这个类只有functionCode这个字段，�?�几乎所有的指令，功能码后面紧贴�?首地�?(address)和数量（value�?
 * �?以这个类在原来的基础上加了两个必须的字段
 * @author Administrator
 *
 */
public abstract class AbstractFunction extends ModbusFunction {

    protected int address;//不管�?么指令，都有地址（寄存器首地�?�?
    protected int value;//多少个寄存器之类的吧，或者说是指

    public AbstractFunction(short functionCode) {
        super(functionCode);
    }

    public AbstractFunction(short functionCode, int address, int quantity) {
        super(functionCode);

        this.address = address;
        this.value = quantity;
    }

    @Override
    public int calculateLength() {
        //function code + address + quantity
        return 1 + 2 + 2;
    }

    @Override
    public ByteBuf encode() {
    	
    	 // 1.创建�?个非池化的ByteBuf，大小为calculateLength()
    	//使用指定�? capacity创建新的大端Java堆缓冲区，该缓冲区根据需要无限扩展其容量�? 
        ByteBuf buf = Unpooled.buffer(calculateLength());
        // 2.写入�?段内�?
        buf.writeByte(getFunctionCode());
        buf.writeShort(address);
        buf.writeShort(value);
        return buf;
    }

    @Override
    public void decode(ByteBuf data) {
        address = data.readUnsignedShort();
        value = data.readUnsignedShort();
    }
}
