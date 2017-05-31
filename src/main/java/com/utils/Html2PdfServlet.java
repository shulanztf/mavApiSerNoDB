package com.utils;

public class Html2PdfServlet {
//	private static final long serialVersionUID = 1L;

//	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// pageContext.getServletContext().getRealPath("/")
//		ServletContext sc = request.getSession().getServletContext();
//		String path = sc.getRealPath(""); // 值为D:\apache-tomcat-6.0.26\webapps\createpdf
//		System.out.println("原path: " + path);
//		// 把路径中的反斜杠转成正斜杠
//		path = path.replaceAll("\\\\", "/"); // 值为D:/apache-tomcat-6.0.26/webapps/createpdf
//		System.out.println(path);
//
//		String path2 = sc.getRealPath("/");
//		System.out.println("path2: " + path2);
//
//		System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
//
//		System.out.println("request.getRequestURI: " + request.getRequestURI());
//		// 获取使用的端口号
//		System.out.println(request.getLocalPort());
//
//		String path3 = request.getContextPath();
//		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path3
//				+ "/";
//
//		System.out.println("basepath: " + basePath);
//
//		response.setContentType("application/pdf");
//		// response.setHeader("Content-Disposition", "attachment;
//		// filename=WebReport.pdf");
//		response.setHeader("Content-Disposition", "inline; filename=WebReport.pdf");
//
//		StringBuffer html = new StringBuffer();
//		// 组装成符合W3C标准的html文件，否则不能正确解析
//		html.append(
//				"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
//		html.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">").append("<head>")
//				.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />")
//				.append("<style type=\"text/css\" mce_bogus=\"1\">body {font-family: SimSun;}</style>")
//				.append("<style type=\"text/css\">img {width: 700px;}</style>").append("</head>").append("<body>");
//
//		html.append("<center><h1>统计报表</h1></center>");
//		html.append("<center>");
//		html.append("<img src=\"images/chart.jpg\"/>");
//		html.append("</center>");
//
//		html.append("</body></html>");
//
//		// parse our markup into an xml Document
//		try {
//			ITextRenderer renderer = new ITextRenderer();
//			/**
//			 * 引入了新的jar包，不用再导入字体了 ITextFontResolver fontResolver =
//			 * renderer.getFontResolver();
//			 * fontResolver.addFont("C:/Windows/fonts/simsun.ttc",
//			 * BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//			 */
//			renderer.setDocumentFromString(html.toString());
//			// 解决图片的相对路径问题
//			// renderer.getSharedContext().setBaseURL("file:/C:/Documents and
//			// Settings/dashan.yin/workspace/createpdf/WebRoot/images");
//			// renderer.getSharedContext().setBaseURL("file:/D:/apache-tomcat-6.0.26/webapps/createpdf/images");
//			renderer.getSharedContext().setBaseURL("file:/" + path + "/images");
//			renderer.layout();
//			OutputStream os = response.getOutputStream();
//			renderer.createPDF(os);
//			os.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doGet(request, response);
//	}
//
//	public void viewExportPDF(HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		ServletContext sc = request.getSession().getServletContext();
//		String rootpath = sc.getRealPath(""); // 值为D:\apache-tomcat-6.0.26\webapps\webmonitor
//		// 把路径中的反斜杠转成正斜杠
//		rootpath = rootpath.replaceAll("\\\\", "/"); // 值为D:/apache-tomcat-6.0.26/webapps/webmonitor
//		// 临时存储目录
//		String pdfPathName = rootpath + "/WebReport.pdf";
//		ArrayList<String> list = new ArrayList<String>();
////		for (int i = 0; i < outstreamlist.size(); i++) {
////			list.add(outstreamlist.get(i));
////		}
//		// 调用方法
//		boolean flag = createPdf(list, pdfPathName, request, response);
//		if (flag == true) {
//			// 要实现另存为下载,必须满足两个条件：导入commons-upload.jar包，表单提交
//			try {
//				OutputStream out = response.getOutputStream();
//				byte by[] = new byte[1024];
//				File fileLoad = new File(pdfPathName);
//				response.reset();
//				response.setContentType("application/pdf");
//				response.setHeader("Content-Disposition", "attachment; filename=WebReport.pdf");
//				long fileLength = fileLoad.length();
//				String length1 = String.valueOf(fileLength);
//				response.setHeader("Content_Length", length1);
//				FileInputStream in = new FileInputStream(fileLoad);
//				int n;
//				while ((n = in.read(by)) != -1) {
//					out.write(by, 0, n);
//				}
//				in.close();
//				out.flush();
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} else {
////			renderText(response, ERR_MESSAGE);
//		}
//		return;
//
//	}
//
//	// 生成pdf
//	public boolean createPdf(ArrayList<String> list, String pdfPathName, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//
//		/**
//		 * 用rootpath指定目录也可以生成pdf文件，只不过不能在myeclipse的左边导航窗口中看不到而已
//		 * 左边导航窗口对应C盘目录下的workspace目录下程序 用rootpath指定的目录是D盘Tomcat目录
//		 */
//		ServletContext sc = request.getSession().getServletContext();
//		String rootpath = sc.getRealPath(""); // 值为D:\apache-tomcat-6.0.26\webapps\webmonitor
//		// 把路径中的反斜杠转成正斜杠
//		rootpath = rootpath.replaceAll("\\\\", "/"); // 值为D:/apache-tomcat-6.0.26/webapps/webmonitor
//
//		boolean flag = false;
//		String outputFile = pdfPathName;
//		// 指定目录导出文件
//		OutputStream os = new FileOutputStream(outputFile);
//		ITextRenderer renderer = new ITextRenderer();
//		StringBuffer html = new StringBuffer();
//		// 组装成符合W3C标准的html文件，否则不能正确解析
//		html.append(
//				"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
//		html.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">").append("<head>")
//				.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />")
//				.append("<style type=\"text/css\" mce_bogus=\"1\">body {font-family: SimSun;}</style>")
//				.append("<style type=\"text/css\">img {width: 500px;}</style>")
//				.append("<style type=\"text/css\">table {font-size:13px;}</style>").append("</head>").append("<body>");
//		html.append("<center>");
//		html.append("<h1>统计报表</h1>");
//		for (int i = 0; i < list.size(); i++) {
//			html.append("<div>" + list.get(i) + "</div>");
//		}
//		html.append("</center>");
//		html.append("</body></html>");
//		try {
//			renderer.setDocumentFromString(html.toString());
//			// 解决图片的相对路径问题,图片路径必须以file开头
//			renderer.getSharedContext().setBaseURL("file:/" + rootpath);
//			renderer.layout();
//			renderer.createPDF(os);
//			os.close();
//			flag = true;
//		} catch (Exception e) {
//			flag = false;
//			e.printStackTrace();
//		}
//		return flag;
//	}
}
