package com.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * 
 * @Title: SFTPUtil
 * @Description:SFTP工具类
 * @see http://blog.csdn.net/liujianfei_lovefeng/article/details/52330288
 * @Author: zhaotf
 * @Since:2017年6月22日 上午7:41:12
 * @Version:1.0
 */
public class SFTPUtil {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private ChannelSftp sftp;

	private Session session;
	/** FTP 登录用户名 */
	private String username;
	/** FTP 登录密码 */
	private String password;
	/** 私钥文件的路径 */
	private String keyFilePath;
	/** FTP 服务器地址IP地址 */
	private String host;
	/** FTP 端口 */
	private int port;

	
	
	/**
	 *
	 * @param args
	 * @throws Exception
	 *             void
	 */
	public static void main(String[] args) throws Exception {
		SFTPUtil sftpU = new SFTPUtil("jzcfycgsftp", "4RFV87aSFG93bNPQ", "ftp-1.fuiou.com", 9022);
		sftpU.login();

//		Collections.synchronizedMap(m;)
		
		FileInputStream input = null;
		try {
			// File file = null;
			// file = new File("D:\\check/P2P_PW10_20170620_0002.txt");
			// input = new FileInputStream(file);
			// String filename = file.getName();
			// //// 上传
			// sftpU.upload("/check", filename, new
			// String(filename.getBytes("UTF-8"), "GBK").getBytes());

			// // 下载 TODO
			// sftpU.download("/overcheck", "P2P_PW*.txt",
			// "D:\\Servers/TEEE.txt");

			// 列出服务器指定的文件列表
			sftpU.sftp.cd("/overcheck");
			Vector v = sftpU.sftp.ls("*.txt");
			for (int i = 0; i < v.size(); i++) {
				System.out.println(v.get(i));
			}

			// byte[] buff = sftp.download("./download", "abc.txt");
			// System.out.println(Arrays.toString(buff));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (input != null) {
				input.close();
			}
			if (sftpU != null) {
				sftpU.logout();
			}
		}
	}

	/**
	 * 构造基于密码认证的sftp对象
	 * 
	 * @param userName
	 *            账户
	 * @param password
	 *            密码
	 * @param host
	 *            地址
	 * @param port
	 *            端口号
	 */
	public SFTPUtil(String username, String password, String host, int port) {
		this.username = username;
		this.password = password;
		this.host = host;
		this.port = port;
	}

	/**
	 * 构造基于秘钥认证的sftp对象
	 * 
	 * @param userName
	 * @param host
	 * @param port
	 * @param keyFilePath
	 *            密钥路径
	 */
	public SFTPUtil(String username, String host, int port, String keyFilePath) {
		this.username = username;
		this.host = host;
		this.port = port;
		this.keyFilePath = keyFilePath;
	}

	public SFTPUtil() {
	}

	/**
	 * 连接sftp服务器
	 * 
	 * @throws Exception
	 */
	public void login() throws Exception {
		try {
			JSch jsch = new JSch();
			if (keyFilePath != null) {
				jsch.addIdentity(keyFilePath);// 设置私钥
				logger.info("sftp connect,path of private key file：{}", keyFilePath);
			}
			logger.info("sftp connect by host:{} username:{}", host, username);

			session = jsch.getSession(username, host, port);
			logger.info("Session is build");
			if (password != null) {
				session.setPassword(password);
			}
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");

			session.setConfig(config);
			session.connect();
			logger.info("Session is connected");

			Channel channel = session.openChannel("sftp");
			channel.connect();
			logger.info("channel is connected");

			sftp = (ChannelSftp) channel;
			logger.info(String.format("sftp server host:[%s] port:[%s] is connect successfull", host, port));
		} catch (JSchException e) {
			logger.error("Cannot connect to specified sftp server : {}:{} \n Exception message is: {}",
					new Object[] { host, port, e.getMessage() });
			throw new Exception(e.getMessage(), e);
		}
	}

	/**
	 * 关闭连接 server
	 */
	public void logout() {
		if (sftp != null) {
			if (sftp.isConnected()) {
				sftp.disconnect();
				logger.info("sftp is closed already");
			}
		}
		if (session != null) {
			if (session.isConnected()) {
				session.disconnect();
				logger.info("sshSession is closed already");
			}
		}
	}

	/**
	 * 将输入流的数据上传到sftp作为文件
	 * 
	 * @param directory
	 *            上传到该目录
	 * @param sftpFileName
	 *            sftp端文件名
	 * @param in
	 *            输入流
	 * @throws SftpException
	 * @throws Exception
	 */
	public void upload(String directory, String sftpFileName, InputStream input) throws SftpException {
		try {
			sftp.cd(directory);
		} catch (SftpException e) {
			logger.warn("directory is not exist");
			sftp.mkdir(directory);
			sftp.cd(directory);
		}
		sftp.put(input, sftpFileName);
		// logger.info("file:{} is upload successful", sftpFileName);
		logger.info("file:{} 上传成功", sftpFileName);
	}

	/**
	 * 上传单个文件
	 * 
	 * @param directory
	 *            上传到sftp目录
	 * @param uploadFile
	 *            要上传的文件,包括路径
	 * @throws FileNotFoundException
	 * @throws SftpException
	 * @throws Exception
	 */
	public void upload(String directory, String uploadFile) throws FileNotFoundException, SftpException {
		File file = new File(uploadFile);
		upload(directory, file.getName(), new FileInputStream(file));
	}

	/**
	 * 将byte[]上传到sftp，作为文件。注意:从String生成byte[]是，要指定字符集。
	 * 
	 * @param directory
	 *            上传到sftp目录
	 * @param sftpFileName
	 *            文件在sftp端的命名
	 * @param byteArr
	 *            要上传的字节数组
	 * @throws SftpException
	 * @throws Exception
	 */
	public void upload(String directory, String sftpFileName, byte[] byteArr) throws SftpException {
		upload(directory, sftpFileName, new ByteArrayInputStream(byteArr));
	}

	/**
	 * 将字符串按照指定的字符编码上传到sftp
	 * 
	 * @param directory
	 *            上传到sftp目录
	 * @param sftpFileName
	 *            文件在sftp端的命名
	 * @param dataStr
	 *            待上传的数据
	 * @param charsetName
	 *            sftp上的文件，按该字符编码保存
	 * @throws UnsupportedEncodingException
	 * @throws SftpException
	 * @throws Exception
	 */
	public void upload(String directory, String sftpFileName, String dataStr, String charsetName)
			throws UnsupportedEncodingException, SftpException {
		upload(directory, sftpFileName, new ByteArrayInputStream(dataStr.getBytes(charsetName)));

	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
	 * @throws SftpException
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public void download(String directory, String downloadFile, String saveFile)
			throws SftpException, FileNotFoundException {
		if (directory != null && !"".equals(directory)) {
			sftp.cd(directory);
		}
		File file = new File(saveFile);
		sftp.get(downloadFile, new FileOutputStream(file));
		logger.info("file:{} is download successful", downloadFile);
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件名
	 * @return 字节数组
	 * @throws SftpException
	 * @throws IOException
	 * @throws Exception
	 */
	public byte[] download(String directory, String downloadFile) throws SftpException, IOException {
		if (directory != null && !"".equals(directory)) {
			sftp.cd(directory);
		}
		InputStream is = sftp.get(downloadFile);

		byte[] fileData = IOUtils.toByteArray(is);

		logger.info("file:{} is download successful", downloadFile);
		return fileData;
	}

	/**
	 * 删除文件
	 * 
	 * @param directory
	 *            要删除文件所在目录
	 * @param deleteFile
	 *            要删除的文件
	 * @throws SftpException
	 * @throws Exception
	 */
	public void delete(String directory, String deleteFile) throws SftpException {
		sftp.cd(directory);
		sftp.rm(deleteFile);
	}

	/**
	 * 列出目录下的文件
	 * 
	 * @param directory
	 *            要列出的目录
	 * @param sftp
	 * @return
	 * @throws SftpException
	 */
	public Vector<?> listFiles(String directory) throws SftpException {
		return sftp.ls(directory);
	}

	// public static void main(String[] args) throws Exception {
	// SFTPUtil sftp = new SFTPUtil("somnus", "passw0rd", "192.168.1.100", 22);
	// sftp.login();
	// byte[] buff = sftp.download("./download", "abc.txt");
	// System.out.println(Arrays.toString(buff));
	// sftp.logout();
	// }
}
