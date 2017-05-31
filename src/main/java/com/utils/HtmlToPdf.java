package com.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.pdf.BaseFont;

/**
 * 
 * @Title: HtmlToPdf
 * @Description: 页面 转 pdf
 * @Author:Administrator
 * @Since:2016年9月7日 上午9:34:35
 * @Version:1.1.0
 */
public class HtmlToPdf {
	public static void main(String[] args) throws Exception {
		String inputFile = "D://baidu.html";
		String url = new File(inputFile).toURI().toURL().toString();
		String outputFile = "D://test.pdf";
		System.out.println(url);
		OutputStream os = new FileOutputStream(outputFile);
		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocument(url);
		// 解决中文支持问题
		ITextFontResolver fontResolver = renderer.getFontResolver();
		fontResolver.addFont("C:/Windows/Fonts/SIMSUN.TTC", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		// 解决图片的相对路径问题
		// renderer.getSharedContext().setBaseURL("file:/D:/z/temp/");
		renderer.layout();
		renderer.createPDF(os);
		os.close();
	}
}
