package edu.hfut.mobus.entity;


import edu.hfut.mobus.utils.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**,
 *相当于一个完整的modbus tcp报文
 * @author ag
 */
public class ModbusRtuFrame {

	//这个是一个实体类，没有子类，固定写法
    private final short header;//从机地址
    //这是�?个抽象类，他会根据功能的不同拥有不同的结构，抽象类里面只有function字段，�?�继承他的类会多出相应的字段
    private final ModbusFunction function;
    
    private  final int crc;//两个字节

    public ModbusRtuFrame(short header, ModbusFunction function) {
        this.header = header;
        this.function = function;
        ByteBuf buf = function.encode();
        byte[] ans = new byte[buf.readableBytes()+1];
        ans[0] = (byte) header;//首个字节是从站地址
        for(int i = 1;i<ans.length;i++) {
        	ans[i]= buf.readByte();
        }
        this.crc = ByteUtil.CRC(ans, 0, ans.length);
    }

    public short getHeader() {
        return header;
    }

    public ModbusFunction getFunction() {
        return function;
    }
    
    public int getCRC() {
    	return this.crc;
    }

    /**,
     * 相当于把整个报文编码，分成两部，先编码头部，后编码pdu
     * @return
     */
    public ByteBuf encode() {
    	//功能码编码
    	ByteBuf buf = Unpooled.buffer();
        buf.writeByte(header);
        buf.writeBytes(function.encode());
        buf.writeShort(crc);
        return buf;
    }

    public boolean checkCrc(int otherCRC) {
    	return (otherCRC&0xffff) == (this.crc&0xffff);
    }
    @Override
    public String toString() {
        return "ModbusFrame{" + "header=" + header + ", function=" + function + '}';
    }
    
    public static void main(String[]args) {
//    	ModbusRtuFrame frame = new ModbusRtuFrame((short)8,new ReadHoldingRegistersRequest(0x37,2));
//    	ByteBuf buf = frame.encode();
////    	System.out.println(frame.crc[0]&0xff);
//    	//,(byte) 167,126
//    	byte[] b = {8,3,4,(byte) 255,(byte) 254,(byte) 207,(byte) 221};
//    	
//    	System.out.println(ByteUtil.CrcCreate(b, 0,7)[0]&0xff);
    }
}
