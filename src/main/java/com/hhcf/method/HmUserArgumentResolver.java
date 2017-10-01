package com.hhcf.method;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.PropertyValues;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.format.number.CurrencyFormatter;
import org.springframework.format.number.NumberFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.bind.support.WebRequestDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.testng.Assert;

import com.alibaba.fastjson.JSONArray;
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
		if (binderFactory==null) {
			return null;
		}
		Class<?> targetType=parameter.getParameterType();
//		MyForm myForm=parameter.getParameterAnnotation(MyForm.class);
		String prefix=parameter.getParameterAnnotation(HmForm.class).value();
		Object arg=null;
		Field[] fields=targetType.getDeclaredFields();
		Object target=targetType.newInstance();
//		WebDataBinder binder = binderFactory.createBinder(webRequest, null,prefix);
		
//		start
		String name = ModelFactory.getNameForParameter(parameter);
		Object attribute = null;
		if ((mavContainer.containsAttribute(name))) {
			attribute = mavContainer.getModel().get(name);
		} else {
			attribute = createAttribute(name, parameter, binderFactory,
					webRequest);
		}
		// 重点在这里在这里在这里在这里在这里在这里在这里
		WebDataBinder binder = binderFactory.createBinder(webRequest,
				attribute, name);
		
//		binder.bind((PropertyValues) webRequest);// TODO 类型转换，并，发现错误，生成异常
		((WebRequestDataBinder) binder).bind(webRequest);// TODO 类型转换，并，发现错误，生成异常
		
//		if (binder.getTarget() != null) {
////			bindRequestParameters(binder, webRequest);
//			validateIfApplicable(binder, parameter);
//			if (binder.getBindingResult().hasErrors()) {
//				if (isBindExceptionRequired(binder, parameter)) {
//					throw new BindException(binder.getBindingResult());
//				}
//			}
//		}
//		end
		
		try {
			for(Field field:fields){
				
				field.setAccessible(true);
				String fieldName=field.getName();
				Class<?> fieldType=field.getType();
				arg = binder.convertIfNecessary(webRequest.getParameter(prefix+"."+fieldName),fieldType, parameter);
				
				Annotation[] annotations = field.getAnnotations();
				for(Annotation annot: annotations) {
					Map<String, Object> checks = AnnotationUtils.getAnnotationAttributes(annot);
					System.out.println("aa:"+checks.toString());
					System.out.println("tt:"+AnnotationUtils.getDefaultValue(annot));
//					System.out.println("bb:"+AnnotationUtils.getValue(annot)==null?this.toString():null);
					for(String key : checks.keySet()) {
						Object obj1 = AnnotationUtils.getValue(annot, key);
						Object obj2 = AnnotationUtils.getDefaultValue(annot, key);
						System.out.println("gggggg:"+obj1);
						System.out.println("hhhhhh:"+obj2);
						if(obj1 instanceof Object[] ) {
							binder.validate((Object[]) obj1);
						}else {
							binder.validate(new Object[] { obj1 });
						}
						System.out.println("ssss:"+JSONArray.toJSONString(binder.getBindingResult().getAllErrors()));
					}
					
//					Object hints = AnnotationUtils.getValue(annot);
//						binder.validate(hints instanceof Object[] ? (Object[]) hints
//								: new Object[] { hints });
				}
				
				field.set(target,arg);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return target;
		


//
//		// Add resolved attribute and BindingResult at the end of the model
//
//		Map<String, Object> bindingResultModel = binder.getBindingResult()
//				.getModel();
//		// mavContainer..removeAttributes(bindingResultModel);
//		mavContainer.addAllAttributes(bindingResultModel);
//
//		return binder.getTarget();

		// String objName =
		// parameter.getParameterAnnotation(HmForm.class).value()
		// + ".";// 获取注解里的逻辑名
		// Object obj = parameter.getParameterType().newInstance();// 实例化对象
		//
		// StringBuffer tmp = new StringBuffer();
		// String[] val;
		// Field fie = null;
		// Field[] frr = parameter.getParameterType().getDeclaredFields();
		// for (Iterator<String> itr = webRequest.getParameterNames(); itr
		// .hasNext();) {
		// tmp.delete(0, tmp.length());
		// tmp.append(itr.next());
		// if (tmp.indexOf(objName) < 0) {
		// continue;
		// }
		// for (int i = 0; i < frr.length; i++) {
		// frr[i].setAccessible(true);
		// String filed = objName + frr[i].getName();
		// if (tmp.toString().equals(filed)) {
		// // TODO 缺少类型转换 java.lang.IllegalArgumentException: Can not
		// // set java.lang.Integer field
		// // com.hhcf.backend.model.HmUserEntity.age to
		// // java.lang.String
		// val = webRequest.getParameterValues(tmp.toString());
		// fie = frr[i];
		// fie.set(obj, val[0]);
		// }
		// }
		// }
		// return obj;
	}

	protected boolean isBindExceptionRequired(WebDataBinder binder,
			MethodParameter parameter) {
		int i = parameter.getParameterIndex();
		Class<?>[] paramTypes = parameter.getMethod().getParameterTypes();
		boolean hasBindingResult = (paramTypes.length > (i + 1) && Errors.class
				.isAssignableFrom(paramTypes[i + 1]));

		return !hasBindingResult;
	}

	protected void validateIfApplicable(WebDataBinder binder,
			MethodParameter parameter) {
		Annotation[] annotations = parameter.getParameterAnnotations();
		for (Annotation annot : annotations) {
			if (annot.annotationType().getSimpleName().startsWith("Valid")) {
				Object hints = AnnotationUtils.getValue(annot);
//				binder.validate(hints instanceof Object[] ? (Object[]) hints
//						: new Object[] { hints });
				if(hints instanceof Object[] ) {
					binder.validate((Object[]) hints);
				}else {
					binder.validate(new Object[] { hints });
				}
			}
		}
	}

	protected void bindRequestParameters(WebDataBinder binder,
			NativeWebRequest request) {
		((WebRequestDataBinder) binder).bind(request);
	}

	protected Object createAttribute(String attributeName,
			MethodParameter parameter, WebDataBinderFactory binderFactory,
			NativeWebRequest request) throws Exception {

		return BeanUtils.instantiateClass(parameter.getParameterType());
	}

	public static void main(String[] args) {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		// 默认不自动注册任何Formatter
		CurrencyFormatter currencyFormatter = new CurrencyFormatter();
		currencyFormatter.setFractionDigits(2);// 保留小数点后几位
		currencyFormatter.setRoundingMode(RoundingMode.CEILING);// 舍入模式（ceilling表示四舍五入）
		// 注册Formatter SPI实现
		// conversionService.addFormatter(currencyFormatter);

		NumberFormatter nf = new NumberFormatter();
		conversionService.addFormatter(nf);

		// System.out.println("ccc:"
		// + conversionService.convert(new BigDecimal("1234.128"),
		// String.class));
		// System.out.println("tttt:"
		// + conversionService.convert("￥1,234.13", BigDecimal.class));
		System.out.println("aaaaa:"
				+ conversionService.convert("444444", Integer.class));
		// System.out.println("eee:"
		// + conversionService.convert("￥333.3", Integer.class));

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
