package com.holyshatots.setOne;

import org.apache.commons.codec.binary.Base64;

public class hexToBase64
{
	public static void main(String[] args)
	{
//		String hexString = new String("49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d");
//		System.out.println(convertToString(hexString));

		Xor.test();
	}
	
	public hexToBase64()
	{

	}
	
	public static byte[] convert(String s)
	{
		int len = s.length();
		byte[] data = new byte[len / 2];
		for(int i=0; i<len; i+=2)
		{
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
								 + Character.digit(s.charAt(i+1), 16));
		}
		return Base64.encodeBase64(data);
	}
	
	public static String convertToString(String s)
	{
		int len = s.length();
		byte[] data = new byte[len / 2];
		for(int i=0; i<len; i+=2)
		{
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
								 + Character.digit(s.charAt(i+1), 16));
		}
		return Base64.encodeBase64String(data);
	}
}
