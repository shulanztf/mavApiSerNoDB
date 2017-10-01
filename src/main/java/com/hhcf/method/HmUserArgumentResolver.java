package com.hhcf.method;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.format.number.CurrencyFormatter;
import org.springframework.format.number.NumberFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.testng.Assert;

import com.hhcf.annotation.HmForm;

/**
 * 
 * @Title: HmUserArgumentResolver
 * @Description:springMVC自定义参数解析器
 * @see http://blog.csdn.net/hongxingxiaonan/article/details/50084179
 * @Author: zhaotf
 * @Since:2017年9月30日 下午2:45:33
 * @Version:1.0
 */
public class HmUserArgumentResolver implements HandlerMethodArgumentResolver {
	private static Logger logger = Logger
			.getLogger(HmUserArgumentResolver.class);

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (parameter.hasParameterAnnotation(HmForm.class)) {
			// logger.info("eeeee:" +
			// parameter.getMethodAnnotation(HmForm.class).toString());
			logger.info("eeeee:"
					+ parameter.getParameterAnnotation(HmForm.class).toString());
			logger.info("eeeee:"
					+ parameter.getParameterAnnotation(HmForm.class).value());
			logger.info("eeeee:"
					+ parameter.getParameterAnnotation(HmForm.class).name());
			return true;
		}
		return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		String objName = parameter.getParameterAnnotation(HmForm.class).value()
				+ ".";// 获取注解里的逻辑名
		Object obj = parameter.getParameterType().newInstance();// 实例化对象

		StringBuffer tmp = new StringBuffer();
		String[] val;
		Field fie = null;
		Field[] frr = parameter.getParameterType().getDeclaredFields();
		for (Iterator<String> itr = webRequest.getParameterNames(); itr
				.hasNext();) {
			tmp.delete(0, tmp.length());
			tmp.append(itr.next());
			if (tmp.indexOf(objName) < 0) {
				continue;
			}
			for (int i = 0; i < frr.length; i++) {
				frr[i].setAccessible(true);
				String filed = objName + frr[i].getName();
				if (tmp.toString().equals(filed)) {
					// TODO 缺少类型转换 java.lang.IllegalArgumentException: Can not
					// set java.lang.Integer field
					// com.hhcf.backend.model.HmUserEntity.age to
					// java.lang.String
					val = webRequest.getParameterValues(tmp.toString());
					fie = frr[i];
					fie.set(obj, val[0]);
				}
			}
		}
		return obj;
	}

	public static void main(String[] args) {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		// 默认不自动注册任何Formatter
		CurrencyFormatter currencyFormatter = new CurrencyFormatter();
		currencyFormatter.setFractionDigits(2);// 保留小数点后几位
		currencyFormatter.setRoundingMode(RoundingMode.CEILING);// 舍入模式（ceilling表示四舍五入）
		// 注册Formatter SPI实现
//		conversionService.addFormatter(currencyFormatter);
		
		NumberFormatter nf = new NumberFormatter();
		conversionService.addFormatter(nf);

//		System.out.println("ccc:"
//				+ conversionService.convert(new BigDecimal("1234.128"),
//						String.class));
//		System.out.println("tttt:"
//				+ conversionService.convert("￥1,234.13", BigDecimal.class));
		System.out.println("aaaaa:"
				+ conversionService.convert("444444", Integer.class));
//		System.out.println("eee:"
//				+ conversionService.convert("￥333.3", Integer.class));

		// LocaleContextHolder.setLocale(Locale.US);
		// Assert.assertEquals("$1,234.13", conversionService.convert(
		// new BigDecimal("1234.128"), String.class));
		// LocaleContextHolder.setLocale(null);
	}

	public void testWithDefaultFormattingConversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		// 默认不自动注册任何Formatter
		CurrencyFormatter currencyFormatter = new CurrencyFormatter();
		currencyFormatter.setFractionDigits(2);// 保留小数点后几位
		currencyFormatter.setRoundingMode(RoundingMode.CEILING);// 舍入模式（ceilling表示四舍五入）
		// 注册Formatter SPI实现
		conversionService.addFormatter(currencyFormatter);

		// 绑定Locale信息到ThreadLocal
		// FormattingConversionService内部自动获取作为Locale信息，如果不设值默认是
		// Locale.getDefault()
		LocaleContextHolder.setLocale(Locale.US);
		Assert.assertEquals("$1,234.13", conversionService.convert(
				new BigDecimal("1234.128"), String.class));
		LocaleContextHolder.setLocale(null);

		LocaleContextHolder.setLocale(Locale.CHINA);
		Assert.assertEquals("￥1,234.13", conversionService.convert(
				new BigDecimal("1234.128"), String.class));
		Assert.assertEquals(new BigDecimal("1234.13"),
				conversionService.convert("￥1,234.13", BigDecimal.class));
		LocaleContextHolder.setLocale(null);
	}

}
