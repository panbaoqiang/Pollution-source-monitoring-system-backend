package edu.hfut.mobus.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.util.BitSet;


public class ByteUtil  {

	/**
	 * Hex字符串转byte
	 * 
	 * @param inHex 待转换的Hex字符�?
	 * @return 转换后的byte
	 */
	public static byte hexToByte(String inHex) {
		return (byte) Integer.parseInt(inHex, 16);
	}

	/**
	 * hex字符串转byte数组
	 * 
	 * @param inHex 待转换的Hex字符�?
	 * @return 转换后的byte数组结果
	 */
	public static byte[] hexToByteArray(String inHex) {
		int hexlen = inHex.length();
		byte[] result;
		if (hexlen % 2 == 1) {
			// 奇数
			hexlen++;
			result = new byte[(hexlen / 2)];
			inHex = "0" + inHex;
		} else {
			// 偶数
			result = new byte[(hexlen / 2)];
		}
		int j = 0;
		for (int i = 0; i < hexlen; i += 2) {
			result[j] = hexToByte(inHex.substring(i, i + 2));
			j++;
		}
		return result;
	}
	
	/**
	 * 数组转换成十六进制字符串
	 * 
	 * @param bArray
	 * @return HexString
	 */
	public static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}
	

	
	/**
	 * 某一个16位数据逻辑右移num位数
	 * @param num
	 * @param num
	 * @return
	 */
	public static int ShortLogicalRightShift(int num,int count) {
		return   ((num&0xffff)>>>count);
	}
	/**
	 * 查看short最右边是不是1
	 * @param num
	 * @return
	 */
	public static boolean shortLowestPosition(int num) {
		return (num&1) != 0;
	}
	/**
	 * 计算byte数组区间的某区间段crc值，int返回
	 * @param byteArr
	 * @param beg
	 * @param end
	 * @return
	 */
	public static int CRC(byte []byteArr,int beg,int end) {
		//自定义一个暂存器
		int regTmp = 0x0000ffff;
        //每个字节�?8�?
		for(int i = beg ; i<end;i++) {//对于每一个字�?
			byte b = byteArr[i];
			regTmp ^= (b & 0x000000ff);//异或每一个字�?
			for(int j=0;j<8;j++) {
				boolean flag = shortLowestPosition(regTmp);//查看移出位，�?右边的是不是1
				regTmp = ShortLogicalRightShift(regTmp,1);//右移
				if(flag) {//如果�?1
					regTmp ^= 0XA001;//�?0xA001异或
				}
			}
		}
		System.out.println(regTmp);
		return ((regTmp&0xff00)>>>8)^((regTmp&0xff)<<8);
	}



	
	/**
	 * 辅助类：根据十六进制字符串获取byte数组，字符串无空字符
	 * @param str
	 * @return
	 * @throws DecoderException
	 */
	public static byte[] getBytes(String str) throws DecoderException {
        return Hex.decodeHex(str);
	}
	public static void main(String [] args) throws DecoderException {
		//08 10 00 00 00 02 04 00 03 00 00 2D 33
		String order = "32 04 00 02 00 01 ".replace(" ", "");
		System.out.println(CRC(getBytes(order),0,order.length()/2));
	}
	public static String getBinaryString(short byteCount, BitSet coilStatus) {

		StringBuilder bitString = new StringBuilder();
		int bitCount = 0;
		//从最后一个比特开�?
		for (int i = byteCount * 8 - 1; i >= 0; i--) {
			boolean state = coilStatus.get(i);
			bitString.append(state ? '1' : '0');

			bitCount++;
			if (bitCount == 8 && i > 0) {
				bitCount = 0;
				bitString.append("#");
			}
		}
		return bitString.toString();
	}
	public static String getIntArrStr(int[] arr) {
		StringBuffer sb = new StringBuffer();
		int size = arr.length-1;
		for(int i=0;i<size;i++) {
			sb.append(arr[i]+",");
		}
		sb.append(arr[size]);
		return sb.toString();
	}

	
}
