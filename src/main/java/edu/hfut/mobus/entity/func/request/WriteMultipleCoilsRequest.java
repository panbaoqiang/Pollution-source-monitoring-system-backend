/*
 * Copyright 2012 modjn Project
 *
 * The modjn Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package edu.hfut.mobus.entity.func.request;

import java.util.BitSet;

import edu.hfut.mobus.entity.func.AbstractFunction;
import io.netty.buffer.ByteBuf;

/**
 *0F :写多个线�?
 *super.encode()�? 功能�?+地址+value，但是写多个线圈的格式要在这个基�?上加很多东西，所以要重新写encode()
 * 格式�?0F(功能�?)+xxxx(线圈首地�?)+xxxx(几个线圈)+xx(这些线圈�?要多少字节表示，�?般就除以8)+(�?些列要写的字�?)
 * @author Andreas Gabriel <ag.gandev@googlemail.com>
 * 
 */
public class WriteMultipleCoilsRequest extends AbstractFunction {

    //startingAddress = 0x0000 to 0xFFFF
    //quantityOfOutputs = 1 - 1968 (0x07B0)
    private short byteCount;//个数寄存�?
    private BitSet outputsValue;//�?堆二进制�?
    
    
    //---------------------------------------------------------//
    //                     字节排列                            //
    //                        |  |
    //                       \    /
    //                        \  /
    //                         \/
    //|1  2  3  4  5  6  7  8 | 1  2  3  4  5  6  7  8| 1  2  3  4  5  6  7  8|...
    // 8  7  6  5  4  3  2  1   16 15 14 13 12 11 10 9  24 23 22 21 20 19 18 17
    //                         /\ 
    //                        /  \
    //                       /    \
    //                        |  |
    //                      线圈对应                           //

    public WriteMultipleCoilsRequest() {
        super(WRITE_MULTIPLE_COILS);
    }

    public WriteMultipleCoilsRequest(int startingAddress, int quantityOfOutputs, BitSet outputsValue) {
        super(WRITE_MULTIPLE_COILS, startingAddress, quantityOfOutputs);

        byte[] coils = outputsValue.toByteArray();

        // maximum of 1968 bits
        if (coils.length > 246) {
            throw new IllegalArgumentException();
        }

        this.byteCount = (short) coils.length;
        this.outputsValue = outputsValue;
    }

    public short getByteCount() {
        return byteCount;
    }

    public BitSet getOutputsValue() {
        return outputsValue;
    }

    public int getQuantityOfOutputs() {
        return value;
    }

    public int getStartingAddress() {
        return address;
    }

    @Override
    public int calculateLength() {
        return super.calculateLength() + 1 + byteCount;
    }

    @Override
    public ByteBuf encode() {
    	//父类的编码已经不符合要求了，必须添加字段
        ByteBuf buf = super.encode();
       //在原来的基础上加几个字节
        buf.writeByte(byteCount);
        //这几个字节的值，每一个比特位都是线圈的开和关
        buf.writeBytes(outputsValue.toByteArray());

        return buf;
    }

    @Override
    public void decode(ByteBuf data) {
        super.decode(data);

        byteCount = data.readUnsignedByte();

        byte[] coils = new byte[byteCount];
        data.readBytes(coils);

        outputsValue = BitSet.valueOf(coils);
    }

    @Override
    public String toString() {
        return "WriteMultipleCoilsRequest{" + "startingAddress=" + address + ", quantityOfOutputs=" + value + ", byteCount=" + byteCount + ", outputsValue=" + outputsValue + '}';
    }
}
