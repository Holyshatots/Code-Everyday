package com.holyshatots.setOne;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Xor
{
	public static void test()
	{
		String testOne = new String("1c0111001f010100061a024b53535009181c");
		String testTwo = new String("686974207468652062756c6c277320657965");
		System.out.println(xor(testOne, testTwo));
	}
	
	public static String xor(String firstNumber, String secondNumber)
	{
		byte[] firstByte = hexStringToByte(firstNumber);
		byte[] secondByte = hexStringToByte(secondNumber);
		byte[] resultByte = new byte[firstByte.length];
		
		int i = 0;
		for (byte b : firstByte)
			resultByte[i] = (byte) (b ^ secondByte[i++]);
		
		return Integer.toHexString(byteArrayToInt(resultByte));
	}
	
	private static byte[] hexStringToByte(String s)
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
	
	private static int byteArrayToInt(byte[] b) 
	{
		final ByteBuffer bb = ByteBuffer.wrap(b);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		return bb.getInt();
	}
}
