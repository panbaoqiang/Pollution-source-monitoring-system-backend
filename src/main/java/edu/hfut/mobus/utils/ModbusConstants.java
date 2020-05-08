package edu.hfut.mobus.utils;

/**
 * �?些常�?
 * @author ag
 */
public class ModbusConstants {
    public static final int ERROR_OFFSET = 0x80;//这个�?旦功能码出错，他响应码会在原来的基础上看是不是大�?80，大于就是出�?
    public static final int SYNC_TCP_RESPONSE_TIMEOUT = 200; //2000milliseconds
    public static final int SYNC_RTU_RESPONSE_TIMEOUT = 200; //200milliseconds
    public static final int TRANSACTION_IDENTIFIER_MAX = 100; //affects memory usage of library
    public static final int ADU_MAX_LENGTH = 260;//modbus帧最大不超过260
    public static final int MBAP_LENGTH = 7;//header 有多少个字节事务 标识�?2+协议标识2+字符个数2+单元标识�?1
    public static final int DEFAULT_MODBUS_PORT = 502;//默认端口
    public static final short DEFAULT_PROTOCOL_IDENTIFIER = 0;//0000是modbus tcp协议
    
    public static final short TCP_CARBON_DIOXIDE_IDENTIFIER = 1;//01主机，可以在frame中自己设�?
    public static final short RTU_CIRCLE_BOARD_IDENTIFIER = 8;//放大电路板
    public static final short RTU_THERMOMETER_IDENTIFIER = 0x32;//温度计
    public static final short RTU_DAM3232_IDENTIFIER = 0x64;//温度计
    
    
}
