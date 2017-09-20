package com.proxy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import sun.misc.ProxyGenerator;

/**
 * 
 * @Title: DynamicProxyDemonstration
 * @Description:JAVA代理
 * @Author: zhaotf
 * @Since:2017年5月31日 下午3:44:26
 * @Version:1.0
 */
@SuppressWarnings("restriction")
public class DynamicProxyDemonstration {

	public static void main(String[] args) {
		// 代理的真实对象
		// Subject realSubject = new RealSubject();
		Person person = new Student("张三");

		/**
		 * InvocationHandlerImpl 实现了 InvocationHandler 接口，并能实现方法调用从代理类到委托类的分派转发
		 * 其内部通常包含指向委托类实例的引用，用于真正执行分派转发过来的方法调用.
		 * 即：要代理哪个真实对象，就将该对象传进去，最后是通过该真实对象来调用其方法
		 */
		InvocationHandler handler = new StuInvocationHandler(person);

		ClassLoader loader = handler.getClass().getClassLoader();
		Class[] interfaces = person.getClass().getInterfaces();
		/**
		 * 该方法用于为指定类装载器、一组接口及调用处理器生成动态代理类实例
		 */
		Person subject = (Person) Proxy.newProxyInstance(loader, interfaces, handler);

		System.out.println("动态代理对象的类型：" + subject.getClass().getName());

		person.giveMoney();
		// String hello = subject...SayHello("jiankunking");
		// System.out.println(hello);
		// 将生成的字节码保存到本地，
		createProxyClassFile();
	}

	/**
	 * 打印动态生成的对象文件(*.class)
	 * 
	 * void
	 */
	private static void createProxyClassFile() {
		String name = "Student";
		// byte[] data = ProxyGenerator.generateProxyClass(name, new Class[] {
		// Person.class });
		byte[] data = ProxyGenerator.generateProxyClass(name, Student.class.getInterfaces());
		FileOutputStream out = null;
		try {
			out = new FileOutputStream("D://" + name + ".class");
			System.out.println((new File("hello")).getAbsolutePath());
			out.write(data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != out)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
