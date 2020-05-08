package edu.hfut.mobus.entity;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 *相当于一个完整的modbus tcp报文
 * @author ag
 */
public class ModbusTcpFrame {

	//这个是一个实体类，没有子类，固定写法
    private final ModbusTcpHeader header;
    //这是�?个抽象类，他会根据功能的不同拥有不同的结构，抽象类里面只有function字段，�?�继承他的类会多出相应的字段
    private final ModbusFunction function;

    public ModbusTcpFrame(ModbusTcpHeader header, ModbusFunction function) {
        this.header = header;
        this.function = function;
    }

    public ModbusTcpHeader getHeader() {
        return header;
    }

    public ModbusFunction getFunction() {
        return function;
    }

    /**
     * 相当于把整个报文编码，分成两部，先编码头部，后编码pdu
     * @return
     */
    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(header.encode());//写死
        buf.writeBytes(function.encode());//他会根据实际的function进行编码
        return buf;
    }

    @Override
    public String toString() {
        return "ModbusFrame{" + "header=" + header + ", function=" + function + '}';
    }
}
