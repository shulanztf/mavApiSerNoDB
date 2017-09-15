package com.study.aio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * 
 * @Title: XiaoNoAioCharsetHelper
 * @Description:工具类
 * @see http://blog.csdn.net/zmx729618/article/details/53170867
 * @Author: zhaotf
 * @Since:2017年9月15日 上午7:45:23
 * @Version:1.0
 */
public class XiaoNoAioCharsetHelper {
	private static final String UTF_8 = "UTF-8";
	private static CharsetEncoder encoder = Charset.forName(UTF_8).newEncoder();
	private static CharsetDecoder decoder = Charset.forName(UTF_8).newDecoder();

	public static ByteBuffer encode(CharBuffer in) throws CharacterCodingException {
		return encoder.encode(in);
	}

	public static CharBuffer decode(ByteBuffer in) throws CharacterCodingException {
		return decoder.decode(in);
	}
}
