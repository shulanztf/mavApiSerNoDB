package com.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.SequenceInputStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

/**
 * 
 * @Title: OrderBy
 * @Description:
 * @author zhaotf
 * @date 2016年8月26日 下午3:08:24
 * @version V1.0
 *
 */
public class IOUtils {

	public static void main(String[] args) {
	}

	/**
	 * 字符流 处理
	 */
	public static void doPrint() {
		char[] buffer = new char[512]; // 一次取出的字节数大小,缓冲区大小
		int numberRead = 0;
		FileReader reader = null; // 读取字符文件的流
		PrintWriter writer = null; // 写字符到控制台的流

		try {
			reader = new FileReader("D://var/copy1.txt");
			writer = new PrintWriter(System.out); // PrintWriter可以输出字符到文件，也可以输出到控制台
			while ((numberRead = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, numberRead);
			}
		} catch (IOException e) {
			// TODO自动生成的 catch 块
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO自动生成的 catch 块
				e.printStackTrace();
			}
			writer.close(); // 这个不用抛异常
		}
	}

	/**
	 * SequenceInputStream合并流，将与之相连接的流集组合成一个输入流并从第一个输入流开始读取，
	 * 直到到达文件末尾，接着从第二个输入流读取，依次类推，直到到达包含的最后一个输入流的文件末尾为止。
	 * 合并流的作用是将多个源合并合一个源。可接收枚举类所封闭的多个字节流对象。
	 */
	public static void doSequence() {
		// 创建一个合并流的对象
		SequenceInputStream sis = null;
		// 创建输出流。
		BufferedOutputStream bos = null;
		try {
			// 构建流集合。
			Vector<InputStream> vector = new Vector<InputStream>();
			vector.addElement(new FileInputStream("D:\\var\text1.txt"));
			vector.addElement(new FileInputStream("D:\\var\text2.txt"));
			vector.addElement(new FileInputStream("D:\\var\text3.txt"));
			Enumeration<InputStream> en = vector.elements();

			sis = new SequenceInputStream(en);

			bos = new BufferedOutputStream(new FileOutputStream(
					"D:\\var\text4.txt"));
			// 读写数据
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = sis.read(buf)) != -1) {
				bos.write(buf, 0, len);
				bos.flush();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (sis != null)
					sis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (bos != null)
					bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读写JAVA 对象
	 */
	public static void readObject() {
		ObjectOutputStream objectwriter = null;
		ObjectInputStream objectreader = null;

		try {
			objectwriter = new ObjectOutputStream(new FileOutputStream(
					"D://var/student.txt"));
			objectwriter.writeObject(new Student("gg", 22));
			objectwriter.writeObject(new Student("tt", 18));
			objectwriter.writeObject(new Student("rr", 17));
			objectreader = new ObjectInputStream(new FileInputStream(
					"D://var/student.txt"));
			for (int i = 0; i < 3; i++) {
				System.out.println(objectreader.readObject());
			}
		} catch (Exception e) {
			// TODO自动生成的 catch 块
			e.printStackTrace();
		} finally {
			try {
				objectreader.close();
				objectwriter.close();
			} catch (IOException e) {
				// TODO自动生成的 catch 块
				e.printStackTrace();
			}

		}
	}

	/**
	 * 有缓冲区
	 */
	public static void readFile2() {
		// TODO自动生成的方法存根
		byte[] buffer = new byte[512]; // 一次取出的字节数大小,缓冲区大小
		int numberRead = 0;
		FileInputStream input = null;
		FileOutputStream out = null;
		try {
			input = new FileInputStream("D://var/tiger.txt");
			out = new FileOutputStream("D://var/tiger2.txt"); // 如果文件不存在会自动创建

			// numberRead的目的在于防止最后一次读取的字节小于buffer长度，
			while ((numberRead = input.read(buffer)) != -1) {
				out.write(buffer, 0, numberRead); // 否则会自动被填充0
			}
		} catch (final IOException e) {
			// TODO自动生成的 catch 块
			e.printStackTrace();
		} finally {
			try {
				input.close();
				out.close();
			} catch (IOException e) {
				// TODO自动生成的 catch 块
				e.printStackTrace();
			}

		}
	}

	public static void readFile1() {
		// TODO 自动生成的方法存根
		int count = 0; // 统计文件字节长度
		InputStream streamReader = null; // 文件输入流
		try {
			streamReader = new FileInputStream(new File("D://var/tiger.txt"));
			/*
			 * 1.new File()里面的文件地址也可以写成D:\\David\\Java\\java
			 * 高级进阶\\files\\tiger.jpg,前一个\是用来对后一个
			 * 进行转换的，FileInputStream是有缓冲区的，所以用完之后必须关闭，否则可能导致内存占满，数据丢失。
			 */
			while (streamReader.read() != -1) { // 读取文件字节，并递增指针到下一个字节
				count++;
			}
			System.out.println("---长度是： " + count + " 字节");
		} catch (final IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			try {
				if (streamReader != null) {
					streamReader.close();
				}
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}

	/**
	 * 文件处理示例
	 */
	public static void createFile() {
		File file = new File("D://var/create.txt");
		try {
			file.createNewFile(); // 当且仅当不存在具有此抽象路径名指定名称的文件时，不可分地创建一个新的空文件。
			System.out.println("该分区大小" + file.getTotalSpace()
					/ (1024 * 1024 * 1024) + "G"); // 返回由此抽象路径名表示的文件或目录的名称。
			file.mkdirs(); // 创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
			// f.delete(); // 删除此抽象路径名表示的文件或目录
			System.out.println("文件名  " + file.getName()); // 返回由此抽象路径名表示的文件或目录的名称。
			System.out.println("文件父目录字符串 " + file.getParent());// 返回此抽象路径名父目录的路径名字符串；如果此路径名没有指定父目录，则返回null。

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

@SuppressWarnings("serial")
class Student implements Serializable {
	private String name;
	private int age;

	public Student(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", age=" + age + "]";
	}

}
