package com.tuil;

//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Map;
//import java.util.UUID;
//import java.util.concurrent.TimeUnit;
//
//import org.apache.commons.io.IOUtils;
//import org.hibernate.sql.Template;
//import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
//import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

/**
 * 
 * @Title: PDFTool
 * @Description: 生成PDF，通过freemarker模板
 * @Author:Administrator
 * @Since:2016年9月7日 上午9:59:56
 * @Version:1.1.0
 */
public class PDFTool {
	// @Resource(name="webfreemarkerconfiguration")
//	private FreeMarkerConfigurer freemarkerconfiguration;
//
//	private final String dzorderftl = "pdftemplete/dzorder.ftl";

//	private static String tmpdir = PDFTool.class.getResource("/").getPath() + "tmpdir";
//	{
//		tmpdir = tmpdir.replaceAll("^/", "");
//	}

//	public InputStream generationPdfDzOrder(Map<String, Object> params) throws Exception {
//		freemarkerconfiguration = new FreeMarkerConfigurer();
//		freemarkerconfiguration.getConfiguration();
//		final Template template = freemarkerconfiguration.getConfiguration().getTemplate(dzorderftl);
//		String htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, params);
//		String tmpFileName = UUID.randomUUID().toString(); // 生成随机文件名
//		File dir = new File(tmpdir);
//		if (!dir.exists())
//			dir.mkdirs();
//		String htmlFileName = tmpdir + "/" + tmpFileName + ".html";
//		String pdfFileName = tmpdir + "/" + tmpFileName + ".pdf";
//		File htmlFile = new File(htmlFileName); // html文件
//		File pdfFile = new File(pdfFileName); // pdf文件
//		IOUtils.write(htmlText, new FileOutputStream(htmlFile)); // 将内容写入html文件
//		String command = getCommand(htmlFileName, pdfFileName);
//		Runtime.getRuntime().exec(command);
//		TimeUnit.SECONDS.sleep(3);
//		return new FileInputStream(pdfFile);
//	}

//	public String getCommand(String htmlName, String pdfName) {
//		String system = System.getProperty("os.name");
//		if ("Windows XP".equalsIgnoreCase(system)) // xp系统
//			return "D:/Program Files/wkhtmltopdf/wkhtmltopdf.exe " + htmlName + " " + pdfName;
//		else if ("Linux".equalsIgnoreCase(system)) // linux 系统
//			return "wkhtmltopdf-amd64 " + htmlName + " " + pdfName;
//		return "";
//	}

	public String getCommand(String htmlName, String pdfName) {
		return "D:\\Program Files\\wkhtmltopdf\\wkhtmltopdf.exe " + htmlName + " " + pdfName;// 前半段是我的安装路径，根据自己的安装路径换上即可
	}

	public static void main(String[] args) {
		PDFTool pdfTool = new PDFTool();
//		String htmlUrl = "http://www.baidu.com";
		String htmlUrl = "http://www.hcharts.cn/demo/highcharts";
//		String htmlUrl = "D:/baidu.html";
		String command = pdfTool.getCommand(htmlUrl, "D:/test2.pdf");
		System.out.println(command);
		try {
			Runtime.getRuntime().exec(command);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
