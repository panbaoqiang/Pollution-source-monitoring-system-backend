package edu.hfut.mobus.entity.func;

import io.netty.buffer.ByteBuf;

import static edu.hfut.mobus.entity.ModbusFunction.WRITE_SINGLE_COIL;

/**
 *写开关量，所以是以字节为单位�? 功能�? 05
 * @author ares
 */
//功能�? 05，写当个线圈 �?FF00 ,�?0000
public class WriteSingleCoil extends AbstractFunction {
    /**
               *  相当于一个实体，他包含的信息�?05功能码（写死的），address（不确定），value（根据他的state来决定）
     * 05功能码就是写�?个线圈，可就是单个开�?
                * 他的�?略版格式�? 从机地址+05+寄存器首地址+值（FF00/0000二�?�一�?
     * modbus tcp格式就相当于�?
               *   报文�?+05+首地�?+�?
               *  这是�?个body 
     * address 
     */
    //outputAddress;
    //outputValue;
    private boolean state;

    public WriteSingleCoil() {
        super(WRITE_SINGLE_COIL);
    }

    public WriteSingleCoil(int outputAddress, boolean state) {
        super(WRITE_SINGLE_COIL, outputAddress, state ? 0xFF00 : 0x0000);
        this.state = state;
    }

    public int getOutputAddress() {
        return address;
    }

    @Override
    public void decode(ByteBuf data) {
        super.decode(data);
        state = value == 0xFF00;
    }

    public boolean isState() {
        return state;
    }

    @Override
    public String toString() {
        return "WriteSingleCoil{" + "outputAddress=" + address + ", state=" + state + '}';
    }
}
