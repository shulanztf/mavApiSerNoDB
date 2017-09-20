package com.proxy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import sun.misc.ProxyGenerator;

/**
 * 
 * @Title: StudentsProxy
 * @Description:JAVA代理
 * @Author: zhaotf
 * @Since:2017年5月31日 下午2:22:22
 * @Version:1.0
 */
public class StudentsProxy implements Person {
	// 被代理的学生
	Student stu;

	public StudentsProxy() {
	}

	public StudentsProxy(Person per) {
		if (per instanceof Student) {
			this.stu = (Student) per;
		}
	}

	@Override
	public void giveMoney() {
		// TODO Auto-generated method stub
		stu.giveMoney();
	}

	public static void main(String[] args) {
		// TODO 动态代理
		// 创建一个实例对象，这个对象是被代理的对象
		Person zhangsan = new Student("张三");
		// 创建一个与代理对象相关联的InvocationHandler
		InvocationHandler stuHandler = new StuInvocationHandler<Person>(zhangsan);
		// 创建一个代理对象stuProxy来代理zhangsan，代理对象的每个执行方法都会替换执行Invocation中的invoke方法
		Person stuProxy = (Person) Proxy.newProxyInstance(Person.class.getClassLoader(),
				new Class<?>[] { Person.class }, stuHandler);
		// 代理执行上交班费的方法
		stuProxy.giveMoney();

		String path = "D://StuProxy.class";
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
			fos.write(ProxyGenerator.generateProxyClass("$Proxy0", Student.class.getInterfaces()));
			fos.flush();
			System.out.println("代理类class文件写入成功");
		} catch (Exception e) {
			System.out.println("写文件错误");
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// TODO 静态代理
		// // 被代理的学生张三，他的班费上交有代理对象monitor（班长）完成
		// Person zhangsan = new Student("张三");
		// // 生成代理对象，并将张三传给代理对象
		// Person monitor = new StudentsProxy(zhangsan);
		// // 班长代理上交班费
		// monitor.giveMoney();
	}

}
