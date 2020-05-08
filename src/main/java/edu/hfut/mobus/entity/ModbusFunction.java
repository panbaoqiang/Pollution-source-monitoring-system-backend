package edu.hfut.mobus.entity;


import edu.hfut.mobus.utils.ModbusConstants;
import io.netty.buffer.ByteBuf;

/**
 *  modbus协议固定总共就那些方法，写死也没关系
 * @author ares
 */
public abstract class ModbusFunction {

    public static final short READ_COILS = 0x01;//读取线圈状态
    public static final short READ_DISCRETE_INPUTS = 0x02;//读取输入状态
    public static final short READ_HOLDING_REGISTERS = 0x03;//读取保持寄存器
    public static final short READ_INPUT_REGISTERS = 0x04;//读取输入寄存器
    public static final short WRITE_SINGLE_COIL = 0x05;//写入单线圈
    public static final short WRITE_SINGLE_REGISTER = 0x06;//写入单个保持寄存器
    public static final short WRITE_MULTIPLE_COILS = 0x0F;//写入多线圈
    public static final short WRITE_MULTIPLE_REGISTERS = 0x10;//写入多保持寄存器
    public static final short READ_FILE_RECORD = 0x14;//读取通用参数
    public static final short WRITE_FILE_RECORD = 0x15;//写入通用参数
    public static final short MASK_WRITE_REGISTER = 0x16;//屏蔽写寄存器
    public static final short READ_WRITE_MULTIPLE_REGISTERS = 0x17;//读写多个寄存器
    public static final short READ_FIFO_QUEUE = 0x18;//读FOFO队列
    public static final short ENCAPSULATED_INTERFACE_TRANSPORT = 0x2B;//读取设备的识别码

    private final short functionCode;

    public ModbusFunction(short functionCode) {
        this.functionCode = functionCode;
    }

    public short getFunctionCode() {
        return functionCode;
    }

    /**
     * 默认功能码小于0x80，由于通讯故障，返回的功能码可能会在原来的基础上加上0x80,所以当通讯故障的时候，功能码一定大于80，所以减去80就大于0
     * @param functionCode
     * @return
     */
    public static boolean isError(short functionCode) {
        return functionCode - ModbusConstants.ERROR_OFFSET >= 0;
    }

    public abstract int calculateLength();

    public abstract ByteBuf encode();

    public abstract void decode(ByteBuf data);
}
