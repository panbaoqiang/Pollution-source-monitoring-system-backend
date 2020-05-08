package edu.hfut.mobus.entity.func;

import static edu.hfut.mobus.entity.ModbusFunction.WRITE_SINGLE_REGISTER;

/**
    * 写单个保持寄存器的，功能�? 06
 * @author ares
 */
public class WriteSingleRegister extends AbstractFunction {

	/**
	 * 他的格式就是06 +寄存器首地址 +�?
	 * 完整的报文格式：报文�?+06+寄存器首地址+�?
	 * 这样就实现了想某�?个地�?中写�?
	 */
    //registerAddress;
    //registerValue;
    public WriteSingleRegister() {
        super(WRITE_SINGLE_REGISTER);
    }

    public WriteSingleRegister(int outputAddress, int value) {
        super(WRITE_SINGLE_REGISTER, outputAddress, value);
    }

    public int getRegisterAddress() {
        return address;
    }

    public int getRegisterValue() {
        return value;
    }

    @Override
    public String toString() {
        return "WriteSingleInputRegister{" + "registerAddress=" + address + ", registerValue=" + value + '}';
    }
}
