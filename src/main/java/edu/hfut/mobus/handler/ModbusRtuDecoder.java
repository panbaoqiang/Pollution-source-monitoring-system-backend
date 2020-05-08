package edu.hfut.mobus.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.hfut.mobus.entity.ModbusFunction;
import edu.hfut.mobus.entity.ModbusRtuFrame;
import edu.hfut.mobus.entity.func.ModbusError;
import edu.hfut.mobus.entity.func.WriteSingleCoil;
import edu.hfut.mobus.entity.func.WriteSingleRegister;
import edu.hfut.mobus.entity.func.response.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 这个类的话原先是为了模拟，所以分成服务器和客户端 现在我有硬件做支持，�?以不�?要有服务器的概念
 * 硬件服务器进站然�?
 * TcpClient会添加的handler
 * @author ag
 */
public class ModbusRtuDecoder extends ByteToMessageDecoder {

	public ModbusRtuDecoder() {}

	// �?旦数据应该从给定的ByteBuf解码，则调用 �?
	// 解码从一个ByteBuf到另�?�?
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) {
//		if (buffer.capacity() < MBAP_LENGTH + 1 /* Function Code */) {// 如果小于正常的帧�?
//			return;
//		}
		Map<String,String> map = new HashMap<>();
		
 
//        byte [] bytes = new byte[buffer.readableBytes()];
//        buffer.readBytes(bytes);
//        for(byte b:bytes) {
//        	System.out.print(b&0xff);
//        	System.out.print("**");
//        }
		
		
		// 先解码头部，不用担心，buffer只会更改他的readIndex而已
		short header = buffer.readUnsignedByte();//获取从机地址
		System.out.println("从机地址："+header);
		// 读取功能�?
		short functionCode = buffer.readUnsignedByte();//获取功能码
		System.out.println("功能码：："+functionCode);
		// 初始�?
		ModbusFunction function = null;
		switch (functionCode) {
		case ModbusFunction.READ_COILS:
			function = new ReadCoilsResponse();
			break;
		case ModbusFunction.READ_DISCRETE_INPUTS:
			function = new ReadDiscreteInputsResponse();
			break;
		case ModbusFunction.READ_INPUT_REGISTERS:
			function = new ReadInputRegistersResponse();
			break;
		case ModbusFunction.READ_HOLDING_REGISTERS:
			function = new ReadHoldingRegistersResponse();
			break;
		case ModbusFunction.WRITE_SINGLE_COIL:
			function = new WriteSingleCoil();
			break;
		case ModbusFunction.WRITE_SINGLE_REGISTER:
			function = new WriteSingleRegister();
			break;
		case ModbusFunction.WRITE_MULTIPLE_COILS:
			function = new WriteMultipleCoilsResponse();
			break;
		case ModbusFunction.WRITE_MULTIPLE_REGISTERS:
			function = new WriteMultipleRegistersResponse();
			break;
		}

		if (ModbusFunction.isError(functionCode)) {//如果解析出来的reponse functionCode是错�?
			function = new ModbusError(functionCode);
			System.out.println("有错误");
		} else if (function == null) { //功能码不在项目规定的
			//1代表的是非法 的功能，也就是项目没有规定的，就算modbus设备有，但是项目没有规定的都算非�?
			function = new ModbusError(functionCode, (short) 1);
			System.out.println("没有这个功能码");
		}

		//这一步解码后，整个就是完�? 的数据了，就算是功能码错误，但是也能把之�? 的字节都读取进去
		
		function.decode(buffer);
		int crcTmp = buffer.readShort();
        ModbusRtuFrame tmpFrame = new ModbusRtuFrame(header,function);
		if(!tmpFrame.checkCrc(crcTmp)) {
			function = new ModbusError(functionCode, (short) 03);
			System.out.println("Crc不对");
			System.out.println("我算的CRC："+tmpFrame.getCRC()+" 他的CRC:"+crcTmp);
		}
		 ModbusRtuFrame frame = new ModbusRtuFrame(header,function);
        //处理完这�?帧之�?
		out.add(frame);
	}
}
