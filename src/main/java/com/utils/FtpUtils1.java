package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;

/**
 * 
 * @ClassName: FtpUtils1
 * @Description:FTP工具类
 * @author: zhaotf
 * @date: 2017年6月24日 下午3:53:15
 */
public class FtpUtils1 {

	public static void main(String[] args) {
		testUpload();
		testDownload();
	}

	/**
	 * FTP上传单个文件测试
	 */
	public static void testUpload() {
		FTPClient ftpClient = new FTPClient();
		FileInputStream fis = null;

		try {
			ftpClient.connect("192.168.14.117");
			ftpClient.login("admin", "123");

			File srcFile = new File("C:\\new.gif");
			fis = new FileInputStream(srcFile);
			// 设置上传目录
			ftpClient.changeWorkingDirectory("/admin/pic");
			ftpClient.setBufferSize(1024);
			ftpClient.setControlEncoding("GBK");
			// 设置文件类型（二进制）
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.storeFile("3.gif", fis);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("FTP客户端出错！", e);
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("关闭FTP连接发生异常！", e);
			}
		}
	}

	/**
	 * FTP下载单个文件测试
	 */
	public static void testDownload() {
		FTPClient ftpClient = new FTPClient();
		FileOutputStream fos = null;

		try {
			ftpClient.connect("192.168.14.117");
			ftpClient.login("admin", "123");

			String remoteFileName = "/admin/pic/3.gif";
			fos = new FileOutputStream("c:/down.gif");

			ftpClient.setBufferSize(1024);
			// 设置文件类型（二进制）
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.retrieveFile(remoteFileName, fos);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("FTP客户端出错！", e);
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("关闭FTP连接发生异常！", e);
			}
		}
	}
}
