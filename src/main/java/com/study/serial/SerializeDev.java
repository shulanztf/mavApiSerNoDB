package com.study.serial;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import com.alibaba.fastjson.JSON;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.hhcf.model.TSUser;

/**
 * 
 * @Title: SerializeDev
 * @Description:
 * @Author: zhaotf
 * @Since:2017年12月11日 下午3:21:14
 * @see {@link http://www.jianshu.com/p/f017d4518b8f}
 */
public class SerializeDev {
	private static Objenesis objenesis = new ObjenesisStd(true);

	public static void main(String[] args) {
		TSUser user = new TSUser();
		user.setId(Long.valueOf(100));
		user.setAddres("2017年12月11日 下午3:21:14");
		user.setRegtime(new Date());
		user.setAge(33);
		user.setUsername("7年12月11日 ");
		user.setUsercode("abc");
		byte[] ba = serialize(user);
		System.out.println("aa:" + ba);

		TSUser user1 = deserialize(ba, TSUser.class);
		System.out.println("bb:" + JSON.toJSONString(user1));
	}

	private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<Class<?>, Schema<?>>();

	private static <T> Schema<T> getSchema(Class<T> cls) {
		Schema<T> schema = (Schema<T>) cachedSchema.get(cls);
		if (schema == null) {
			schema = RuntimeSchema.createFrom(cls);
			if (schema != null) {
				cachedSchema.put(cls, schema);
			}
		}
		return schema;
	}

	/**
	 * 序列化
	 */
	public static <T> byte[] serialize(T obj) {
		Class<T> cls = (Class<T>) obj.getClass();// 第3行：获得对象的类
		LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);// 第4行：使用LinkedBuffer分配一块默认大小的buffer空间；
		try {
			Schema<T> schema = getSchema(cls);// 第6行：通过对象的类构建对应的schema；
			return ProtostuffIOUtil.toByteArray(obj, schema, buffer);// 第7行：使用给定的schema将对象序列化为一个byte数组，并返回。
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		} finally {
			buffer.clear();
		}
	}

	/**
	 * 反序列化
	 */
	public static <T> T deserialize(byte[] data, Class<T> cls) {
		try {
			T message = objenesis.newInstance(cls);
			Schema<T> schema = getSchema(cls);
			ProtostuffIOUtil.mergeFrom(data, message, schema);
			return message;
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

}
