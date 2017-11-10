package com.hhcf.backend.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.enmus.ExcelType;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hhcf.backend.model.HmAppProductMgEntity;
import com.hhcf.backend.model.TestModel;
import com.hhcf.backend.model.UserEntity;
import com.hhcf.backend.model.ZxbMoneyInRecModel;
import com.hhcf.backend.model.ZxbMoneyOutRecModel;
import com.hhcf.backend.service.BaseSolr;
import com.hhcf.backend.service.GeneralService;
import com.hhcf.backend.service.NettyBaseService;

import net.sf.json.JSONObject;

/**
 * @Title: GeneralController
 * @Description:
 * @author zhaotf
 * @date 2016��8��22�� ����11:14:20
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/general")
public class GeneralController {
	private static final Logger logger = Logger.getLogger(GeneralController.class);
	@Resource
	private GeneralService generalService;
	@Resource
	private NettyBaseService nettyBaseService;
	@Value("${items.createtime.isNull}")
	private String msg;
	@Autowired
	private BaseSolr baseSolr;

	/**
	 * @see {@link http://localhost:8080/mavApiSerNoDB/general/getSolrUer.do}
	 * @param id
	 */
	@RequestMapping("/getSolrUer")
	@ResponseBody
	public Object getSolrUer(String id) throws Exception {
		UserEntity user = baseSolr.getUser(id);
		logger.info("solr查询:" + id + "," + JSON.toJSONString(user));
		return user;
	}

	/**
	 * @see {@link http://localhost:8080/mavApiSerNoDB/general/getNettyMsg.do}
	 */
	@ResponseBody
	@RequestMapping("/getNettyMsg")
	public Object getNettyMsg(HttpServletRequest request) {
		System.out.println("aa:" + msg);
		Object obj = nettyBaseService.getMsg(request.getParameter("uname"));
		return obj;
	}

	/**
	 * 数据绑定测试 POST
	 * 
	 * @return Object
	 */
	@ResponseBody
	@RequestMapping(value = "/postDataTest", method = RequestMethod.POST)
	public Object postDataTest(HttpServletRequest request, String msg, Integer pageNo, boolean filg) {
		logger.info("数据绑定 ，POST请求:");
		logger.info("数据绑定 ，request:" + request.getParameter("msg") + ",绑定数据:" + msg);
		logger.info("数据绑定 ，request:" + request.getParameter("pageNo") + ",绑定数据:" + pageNo);
		logger.info("数据绑定 ，request:" + request.getParameter("filg") + ",绑定数据:" + filg);
		return "ovdata";
	}

	/**
	 * 数据绑定测试 POST 对象
	 * 
	 * @param request
	 * @param obj
	 * @return Object
	 */
	@ResponseBody
	@RequestMapping(value = "/postObjTest", method = RequestMethod.POST)
	public Object postObjTest(HttpServletRequest request, TestModel obj) {
		logger.info("数据绑定对象 ，POST请求:");
		logger.info("数据绑定 ，request:" + request.getParameter("name") + ",绑定数据:" + obj.getName());
		logger.info("数据绑定 ，request:" + request.getParameter("age") + ",绑定数据:" + obj.getAge());
		logger.info("数据绑定 ，request:" + request.getParameter("good") + ",绑定数据:" + obj.isGood());
		return "ovdata";
	}

	/**
	 * 数据绑定测试 GET
	 * 
	 * @return Object
	 */
	@RequestMapping(value = "/getDataTest", method = RequestMethod.GET)
	public Object getDataTest(HttpServletRequest request, String msg, Integer pageNo, boolean filg) {
		logger.info("数据绑定 ，GET请求:");
		logger.info("数据绑定 ，request:" + request.getParameter("msg") + ",绑定数据:" + msg);
		logger.info("数据绑定 ，request:" + request.getParameter("pageNo") + ",绑定数据:" + pageNo);
		logger.info("数据绑定 ，request:" + request.getParameter("filg") + ",绑定数据:" + filg);
		return "ovdata";
	}

	/**
	 * springMVC 返回xml 方式之一；也可以用 @XmlRootElement 实现；
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *             void
	 */
	@ResponseBody
	@RequestMapping(params = "transact")
	public void transact(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/xml;charset=UTF-8");
		// String xml = (String) depositTransactService.transact(request);
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ap><plain><resp_code>0000</resp_code><resp_desc>成功</resp_desc><mchnt_cd>0003310F0352406</mchnt_cd><mchnt_txn_ssn>HF1500885462332</mchnt_txn_ssn></plain><signature>bw75avvsmJChwl57lhHt48rlCBewLVvbPiBIfuA9rWidct4mTMMcr9B4XvFMfaPFlSz2FchExCVRH0hTfK2Y3PKOrmee3KFgxqlKEu+zI5bZyPCJDrEEgGUHQurh04VfVSwCc4nWztJHp3ZOf9JOHglendOdnM1NHcnURUSGS2s=</signature></ap>";
		ServletOutputStream os = response.getOutputStream();
		os.write(xml.getBytes());
		os.flush();
		os.close();
	}

	/**
	 * Exctl 导出
	 * 
	 * @see http://localhost:8080/mavApiSerNoDB/general/exportInXls.do
	 * @param request
	 * @param modelMap
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportInXls")
	public String exportInXls(HttpServletRequest request, ModelMap modelMap) throws Exception {
		List<ZxbMoneyInRecModel> incomes = this.generalService.findZxbMoneyInRecList(request);
		modelMap.put(NormalExcelConstants.FILE_NAME, "Exctl导出信息");
		modelMap.put(NormalExcelConstants.CLASS, ZxbMoneyInRecModel.class);
		ExportParams ep = new ExportParams("Exctl导出信息列表", "导出人:ZTF", "导出信息");
		ep.setType(ExcelType.XSSF);
		modelMap.put(NormalExcelConstants.PARAMS, ep);
		modelMap.put(NormalExcelConstants.DATA_LIST, incomes);
		return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}

	/**
	 * @Title: exportOutXls @Description: 自定义Excel导出 @see
	 *         http://localhost:8080/mavApiSerNoDB/general/exportOutXls.
	 *         do @param @param request @param @param
	 *         modelMap @param @return @param @throws Exception @return
	 *         String @throws
	 */
	@RequestMapping(value = "/exportOutXls")
	public String exportOutXls(HttpServletRequest request, ModelMap modelMap) throws Exception {
		List<ZxbMoneyOutRecModel> incomes = this.generalService.exportOutXls(request);
		modelMap.put(NormalExcelConstants.FILE_NAME, "自定义Excel导出信息");
		modelMap.put(NormalExcelConstants.CLASS, ZxbMoneyOutRecModel.class);
		ExportParams ep = new ExportParams("自定义Excel信息列表", "导出人:ZTF", "导出信息");
		ep.setType(ExcelType.XSSF);
		modelMap.put(NormalExcelConstants.PARAMS, ep);
		modelMap.put(NormalExcelConstants.DATA_LIST, incomes);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

	/**
	 * http://localhost:8080/mavApiSerNoDB/general/home.do
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @Description:
	 */
	@RequestMapping(value = "/home")
	public String home(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			model.addAttribute("liming", "好了");
			// HmAppProductMgEntity entity =
			// this.generalService.getHmAppProductMg(2L);
			// System.out.println(entity.getId());
			logger.info("好了");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return "index";
	}

	/**
	 * http://localhost:8080/mavApiSerNoDB/general/bootStarpTest.do
	 * 
	 * @Description:
	 */
	@RequestMapping(value = "bootStarpTest")
	public String bootStarpTest(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("bsTest", "bs学习");
		return "bootstarp/head";
	}

	/**
	 * http://localhost:8080/mavApiSerNoDB/general/list.do
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @Description:
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			model.addAttribute("liming", "好了");
			// HmAppProductMgEntity entity =
			// this.generalService.getHmAppProductMg(2L);
			// System.out.println(entity.getId());
			System.out.println("好了");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return "com11/datagrid_bufferview";
	}

	/**
	 * http://localhost:8080/mavApiSerNoDB/general/findList.do
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @Description:
	 */
	@ResponseBody
	@RequestMapping(value = "findList", method = RequestMethod.POST)
	public Object findList(HttpServletRequest request, HttpServletResponse response, Model model, Integer page,
			Integer rows) {
		JSONObject result = new JSONObject();
		try {
			List<HmAppProductMgEntity> list = new ArrayList<HmAppProductMgEntity>();
			list.add(generalService.getHmAppProductMg(3l));
			model.addAttribute("liming", "拿到数据");

			JSONObject params = new JSONObject();
			params.put("pageSize", rows);
			params.put("pageIndex", (page - 1) * rows);
			result.put("rows", list);
			result.put("total", 11);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return JSON.toJSONString(result);
	}

	/**
	 * http://localhost:8080/mavApiSerNoDB/general/getAllParam.do
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @param rows
	 * @param paramName
	 * @param createTime
	 * @return
	 * @throws IOException
	 * @Description:
	 */
	@ResponseBody
	@RequestMapping(value = "getAllParam")
	public Object getAllParam(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, defaultValue = "1") Integer page, // 第几页
			@RequestParam(required = false, defaultValue = "10") Integer rows, // 页数大小
			@RequestParam(required = false, defaultValue = "") String paramName,
			@RequestParam(required = false, defaultValue = "") String createTime) throws IOException {
		JSONObject params = new JSONObject();
		params.put("pageSize", rows);
		params.put("pageIndex", (page - 1) * rows);
		List<HmAppProductMgEntity> list = new ArrayList<HmAppProductMgEntity>();
		for (int i = 0; i < 20; i++) {
			HmAppProductMgEntity entity = new HmAppProductMgEntity();
			entity.setId(i + 1l);
			entity.setName("标的" + i);
			entity.setTotalmoney(new BigDecimal(i));
			entity.setInserttime(new Date());
			list.add(entity);
		}

		JSONObject result = new JSONObject();
		result.put("rows", list);
		result.put("total", 11);
		return JSON.toJSONString(result);// org.springframework.http.converter.StringHttpMessageConverter
		// return result; //
		// com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter
	}
}
