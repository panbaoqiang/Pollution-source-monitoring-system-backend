package edu.hfut.mobus.entity;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 *此类定义了modbus tcp的头部，还需要pdu
 * @author ares
 */
public class ModbusTcpHeader {

	/**
	 * 数据扩大到原来的两�?�，方便截取
	 */
    private final int transactionIdentifier;//事务标识符，在modbus tcp是两个字节（int 4个）
    private final int protocolIdentifier;//协议标识符（modbus 默认为2个字节，固定为?00 00)
    private final int length;//长度，两个字节，包含unitIdentifier的后面几个字
    private final short unitIdentifier;//单位标识符，类似于从机地�?，我认为把他写成从机地址也没关系,是固定值

    public ModbusTcpHeader(int transactionIdentifier, int protocolIdentifier, int pduLength, short unitIdentifier) {
        this.transactionIdentifier = transactionIdentifier;
        this.protocolIdentifier = protocolIdentifier;
        this.length = pduLength + 1; //+ unit identifier
        this.unitIdentifier = unitIdentifier;
    }

    public int getLength() {
        return length;
    }

    public int getProtocolIdentifier() {
        return protocolIdentifier;
    }

    public int getTransactionIdentifier() {
        return transactionIdentifier;
    }

    public short getUnitIdentifier() {
        return unitIdentifier;
    }

    /**
     * 解码，对于一个代表modbus header的ByteBuf，将他解码成MObusHeader
     * @param buffer
     * @return
     */
    public static ModbusTcpHeader decode(ByteBuf buffer) {
    	//默认该buffer 的readIndex指向Modbus Tcp的头�?
        return new ModbusTcpHeader(buffer.readUnsignedShort(),
                buffer.readUnsignedShort(),
                buffer.readUnsignedShort(),
                buffer.readUnsignedByte());
    }
    //将一个对象编码成modbus tcp格式 的ByteBuf
    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeShort(transactionIdentifier);
        buf.writeShort(protocolIdentifier);
        buf.writeShort(length);
        buf.writeByte(unitIdentifier);
        return buf;
    }

    @Override
    public String toString() {
        return "ModbusHeader{" + "transactionIdentifier=" + transactionIdentifier + ", protocolIdentifier=" + protocolIdentifier + ", length=" + length + ", unitIdentifier=" + unitIdentifier + '}';
    }
}
