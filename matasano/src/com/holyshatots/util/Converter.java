package com.holyshatots.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Converter
{
	public byte[] hexStringToByte(String s)
	{
		int len = s.length();
		byte[] data = new byte[len / 2];
		for(int i=0; i<len; i+=2)
		{
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
								 + Character.digit(s.charAt(i+1), 16));
		}
		return data;
	}
	
	public static int byteArrayToInt(byte[] b) 
	{
		final ByteBuffer bb = ByteBuffer.wrap(b);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		return bb.getInt();
	}
}
