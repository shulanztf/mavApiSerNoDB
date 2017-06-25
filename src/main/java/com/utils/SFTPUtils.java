package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * 
 * @Title: SFTPUtils0
 * @Description:SFTP 工具类
 * @Author: zhaotf
 * @Since:2017年6月22日 上午8:26:30
 * @Version:1.0
 */
public class SFTPUtils {
	public static Logger logger = LoggerFactory.getLogger(SFTPUtils.class);

	/**
	 * SFTP 密码登录，上传文件集
	 * 
	 * @param ip
	 *            服务器地址
	 * @param port
	 *            端口
	 * @param user
	 *            账户
	 * @param psw
	 *            密码
	 * @param directory
	 *            服务器端目录
	 * @param files
	 *            文件列表
	 * @param encoding
	 *            字符集
	 * @throws Exception
	 *             void
	 */
	public static void upload(String ip, int port, String user, String psw, String directory, List<File> files,
			String encoding) throws Exception {
		Session session = null;
		Channel channel = null;

		JSch jsch = new JSch();
		if (port <= 0) {
			// 连接服务器，采用默认端口
			session = jsch.getSession(user, ip);
		} else {
			// 采用指定的端口连接服务器
			session = jsch.getSession(user, ip, port);
		}

		// 如果服务器连接不上，则抛出异常
		if (session == null) {
			throw new Exception("SFTP session 为null");
		}

		// 设置登陆主机的密码
		session.setPassword(psw);// 设置密码
		session.setConfig("StrictHostKeyChecking", "no"); // 设置第一次登陆的时候提示，可选值：(ask|yes|no)
		session.connect(30000); // 设置登陆超时时间
		OutputStream outStream = null;
		InputStream inStream = null;
		try {
			channel = (Channel) session.openChannel("sftp"); // 创建sftp通信通道
			channel.connect(1000);
			ChannelSftp sftp = (ChannelSftp) channel;
			sftp.cd(directory); // 进入服务器指定的文件夹

			Class<ChannelSftp> cl = ChannelSftp.class;
			Field filed = cl.getDeclaredField("server_version");
			filed.setAccessible(true);
			filed.set(sftp, 2);
			sftp.setFilenameEncoding(encoding);// 设置字符集

			byte[] ba = new byte[1024];
			int line;
			for (File file : files) {
				// 以下代码实现从本地上传一个文件到服务器，如果要实现下载，对换以下流就可以了
				outStream = sftp.put(file.getName());
				inStream = new FileInputStream(file);

				while ((line = inStream.read(ba)) != -1) {
					outStream.write(ba, 0, line);
				}

				outStream.flush();
				outStream.close();
				inStream.close();
			}
		} catch (Exception e) {
			logger.error("SFTP操作异常：", e);
		} finally {
			if (outStream != null) {
				outStream.close();
			}
			if (inStream != null) {
				inStream.close();
			}
			if (session != null) {
				session.disconnect();
			}
			if (channel != null) {
				channel.disconnect();
			}
		}
	}

	/**
	 * SFTP 密码登录，下载文件
	 * 
	 * @param ip
	 * @param port
	 * @param user
	 * @param psw
	 * @param directory
	 *            远程服务器目录
	 * @param localDir
	 *            本地存放目录
	 * @param files
	 *            文件名称列表
	 * @throws Exception
	 *             void
	 */
	public static void download(String ip, int port, String user, String psw, String directory, String localDir,
			List<String> files) throws Exception {
		Session session = null;
		Channel channel = null;

		JSch jsch = new JSch();
		if (port <= 0) {
			// 连接服务器，采用默认端口
			session = jsch.getSession(user, ip);
		} else {
			// 采用指定的端口连接服务器
			session = jsch.getSession(user, ip, port);
		}
		// 如果服务器连接不上，则抛出异常
		if (session == null) {
			throw new Exception("SFTP session 为null");
		}

		// 设置登陆主机的密码
		session.setPassword(psw);// 设置密码
		session.setConfig("StrictHostKeyChecking", "no"); // 设置第一次登陆的时候提示，可选值：(ask|yes|no)
		session.connect(30000); // 设置登陆超时时间
		OutputStream outStream = null;
		InputStream inStream = null;
		try {
			channel = (Channel) session.openChannel("sftp"); // 创建sftp通信通道
			channel.connect(1000);
			ChannelSftp sftp = (ChannelSftp) channel;
			sftp.cd(directory); // 进入服务器指定的文件夹

			byte[] ba = new byte[1024];
			int line;
			for (String file : files) {
				inStream = sftp.get(file);
				outStream = new FileOutputStream(localDir + file);

				while ((line = inStream.read(ba)) != -1) {
					outStream.write(ba, 0, line);
				}

				outStream.flush();
				outStream.close();
				inStream.close();
			}
		} catch (Exception e) {
			logger.error("SFTP操作异常：", e);
		} finally {
			if (outStream != null) {
				outStream.close();
			}
			if (inStream != null) {
				inStream.close();
			}
			if (session != null) {
				session.disconnect();
			}
			if (channel != null) {
				channel.disconnect();
			}
		}
	}

	/**
	 * 利用JSch包实现SFTP下载、上传文件,示例代码，不能调用
	 * 
	 * @param ip
	 *            主机IP
	 * @param user
	 *            主机登陆用户名
	 * @param psw
	 *            主机登陆密码
	 * @param port
	 *            主机ssh2登陆端口，如果取默认值，传-1
	 */
	@Deprecated
	public static void sshSftp(String ip, String user, String psw, int port) throws Exception {
		Session session = null;
		Channel channel = null;

		JSch jsch = new JSch();

		if (port <= 0) {
			// 连接服务器，采用默认端口
			session = jsch.getSession(user, ip);
		} else {
			// 采用指定的端口连接服务器
			session = jsch.getSession(user, ip, port);
		}

		// 如果服务器连接不上，则抛出异常
		if (session == null) {
			throw new Exception("session is null");
		}

		// 设置登陆主机的密码
		session.setPassword(psw);// 设置密码
		// 设置第一次登陆的时候提示，可选值：(ask | yes | no)
		session.setConfig("StrictHostKeyChecking", "no");
		// 设置登陆超时时间
		session.connect(30000);

		try {
			// 创建sftp通信通道
			channel = (Channel) session.openChannel("sftp");
			channel.connect(1000);
			ChannelSftp sftp = (ChannelSftp) channel;

			// 进入服务器指定的文件夹
			sftp.cd("/check");

			// 列出服务器指定的文件列表
			Vector v = sftp.ls("*.txt");
			for (int i = 0; i < v.size(); i++) {
				System.out.println(v.get(i));
			}

			// 以下代码实现从本地上传一个文件到服务器，如果要实现下载，对换以下流就可以了
			OutputStream outstream = sftp.put("P2P_PW10_20170625_0002.txt");
			InputStream instream = new FileInputStream(new File("D://check/P2P_PW10_20170620_0002.txt"));

			byte b[] = new byte[1024];
			int n;
			while ((n = instream.read(b)) != -1) {
				outstream.write(b, 0, n);
			}

			outstream.flush();
			outstream.close();
			instream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.disconnect();
			channel.disconnect();
		}
	}

	/**
	 * 利用JSch包实现SFTP下载、上传文件,示例代码，不能调用
	 * 
	 * @param ip
	 *            主机IP
	 * @param user
	 *            主机登陆用户名
	 * @param psw
	 *            主机登陆密码
	 * @param port
	 *            主机ssh2登陆端口，如果取默认值(默认值22)，传-1
	 * @param privateKey
	 *            密钥文件路径
	 * @param passphrase
	 *            密钥的密码
	 * 
	 */
	@Deprecated
	public static void sshSftp(String ip, String user, String psw, int port, String privateKey, String passphrase)
			throws Exception {
		Session session = null;
		Channel channel = null;

		JSch jsch = new JSch();

		// 设置密钥和密码
		if (privateKey != null && !"".equals(privateKey)) {
			if (passphrase != null && "".equals(passphrase)) {
				// 设置带口令的密钥
				jsch.addIdentity(privateKey, passphrase);
			} else {
				// 设置不带口令的密钥
				jsch.addIdentity(privateKey);
			}
		}

		if (port <= 0) {
			// 连接服务器，采用默认端口
			session = jsch.getSession(user, ip);
		} else {
			// 采用指定的端口连接服务器
			session = jsch.getSession(user, ip, port);
		}

		// 如果服务器连接不上，则抛出异常
		if (session == null) {
			throw new Exception("session is null");
		}

		// 设置登陆主机的密码
		session.setPassword(psw);// 设置密码
		// 设置第一次登陆的时候提示，可选值：(ask | yes | no)
		session.setConfig("StrictHostKeyChecking", "no");
		// 设置登陆超时时间
		session.connect(30000);

		try {
			// 创建sftp通信通道
			channel = (Channel) session.openChannel("sftp");
			channel.connect(1000);
			ChannelSftp sftp = (ChannelSftp) channel;

			// 进入服务器指定的文件夹
			sftp.cd("domains");

			// 列出服务器指定的文件列表
			Vector v = sftp.ls("*.txt");
			for (int i = 0; i < v.size(); i++) {
				System.out.println(v.get(i));
			}

			// 以下代码实现从本地上传一个文件到服务器，如果要实现下载，对换以下流就可以了
			OutputStream outstream = sftp.put("1.txt");
			InputStream instream = new FileInputStream(new File("c:/print.txt"));

			byte b[] = new byte[1024];
			int n;
			while ((n = instream.read(b)) != -1) {
				outstream.write(b, 0, n);
			}

			outstream.flush();
			outstream.close();
			instream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.disconnect();
			channel.disconnect();
		}
	}

}
