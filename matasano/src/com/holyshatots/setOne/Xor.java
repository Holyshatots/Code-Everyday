package com.holyshatots.setOne;

public class Xor
{
	public Xor()
	{
		
	}
	
	public String xor(String firstNumber, String secondNumber)
	{
		byte[] firstByte = hexStringToByte(firstNumber);
		byte[] secondByte = hexStringToByte(secondNumber);
		byte[] resultByte = new byte[firstByte.length - 1];
		
		int i = 0;
		for (byte b : firstByte)
			resultByte[i] = (byte) (b ^ secondByte[i++]);
		
		
		return null;
	}
	
	private byte[] hexStringToByte(String s)
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
}
