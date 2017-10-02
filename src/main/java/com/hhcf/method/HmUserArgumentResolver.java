package com.hhcf.method;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.number.CurrencyFormatter;
import org.springframework.format.number.NumberFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.util.Assert;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.bind.support.WebRequestDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

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
		// if (binderFactory==null) {
		// return null;
		// }
		// Class<?> targetType=parameter.getParameterType();
		// // MyForm myForm=parameter.getParameterAnnotation(MyForm.class);
		// String prefix=parameter.getParameterAnnotation(HmForm.class).value();
		// Object arg=null;
		// Field[] fields=targetType.getDeclaredFields();
		// Object target=targetType.newInstance();
		// // WebDataBinder binder = binderFactory.createBinder(webRequest,
		// null,prefix);
		//
		// // start
		// String name = ModelFactory.getNameForParameter(parameter);
		// Object attribute = null;
		// if ((mavContainer.containsAttribute(name))) {
		// attribute = mavContainer.getModel().get(name);
		// } else {
		// attribute = createAttribute(name, parameter, binderFactory,
		// webRequest);
		// }
		// // 重点在这里在这里在这里在这里在这里在这里在这里
		// WebDataBinder binder = binderFactory.createBinder(webRequest,
		// attribute, name);
		//
		// // binder.bind((PropertyValues) webRequest);// TODO 类型转换，并，发现错误，生成异常
		// ((WebRequestDataBinder) binder).bind(webRequest);// TODO
		// 类型转换，并，发现错误，生成异常
		//
		// // if (binder.getTarget() != null) {
		// //// bindRequestParameters(binder, webRequest);
		// // validateIfApplicable(binder, parameter);
		// // if (binder.getBindingResult().hasErrors()) {
		// // if (isBindExceptionRequired(binder, parameter)) {
		// // throw new BindException(binder.getBindingResult());
		// // }
		// // }
		// // }
		// // end
		//
		// try {
		// for(Field field:fields){
		//
		// field.setAccessible(true);
		// String fieldName=field.getName();
		// Class<?> fieldType=field.getType();
		// arg =
		// binder.convertIfNecessary(webRequest.getParameter(prefix+"."+fieldName),fieldType,
		// parameter);
		//
		// Annotation[] annotations = field.getAnnotations();
		// for(Annotation annot: annotations) {
		// Map<String, Object> checks =
		// AnnotationUtils.getAnnotationAttributes(annot);
		// System.out.println("aa:"+checks.toString());
		// System.out.println("tt:"+AnnotationUtils.getDefaultValue(annot));
		// //
		// System.out.println("bb:"+AnnotationUtils.getValue(annot)==null?this.toString():null);
		// for(String key : checks.keySet()) {
		// Object obj1 = AnnotationUtils.getValue(annot, key);
		// Object obj2 = AnnotationUtils.getDefaultValue(annot, key);
		// System.out.println("gggggg:"+obj1);
		// System.out.println("hhhhhh:"+obj2);
		// if(obj1 instanceof Object[] ) {
		// binder.validate((Object[]) obj1);
		// }else {
		// binder.validate(new Object[] { obj1 });
		// }
		// System.out.println("ssss:"+JSONArray.toJSONString(binder.getBindingResult().getAllErrors()));
		// }
		//
		// // Object hints = AnnotationUtils.getValue(annot);
		// // binder.validate(hints instanceof Object[] ? (Object[]) hints
		// // : new Object[] { hints });
		// }
		//
		// field.set(target,arg);
		// }
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// return target;

		//
		// // Add resolved attribute and BindingResult at the end of the model
		//
		// Map<String, Object> bindingResultModel = binder.getBindingResult()
		// .getModel();
		// // mavContainer..removeAttributes(bindingResultModel);
		// mavContainer.addAllAttributes(bindingResultModel);
		//
		// return binder.getTarget();

		String objName = parameter.getParameterAnnotation(HmForm.class).value()
				+ ".";// 获取注解里的逻辑名
		Object obj = parameter.getParameterType().newInstance();// 实例化对象

		// 数据绑定
		StringBuffer tmp = new StringBuffer();
		String[] val;
		Field fie = null;
		Field[] frr = parameter.getParameterType().getDeclaredFields();
		for (Iterator<String> itr = webRequest.getParameterNames(); itr
				.hasNext();) {
			tmp.delete(0, tmp.length());
			tmp.append(itr.next());
			if (tmp.indexOf(objName) < 0) {
				logger.info("属性未找到，暂不转换，AA:" + tmp.toString() + ":" + objName);
				continue;
			}
			for (int i = 0; i < frr.length; i++) {
				frr[i].setAccessible(true);
				String filed = objName + frr[i].getName();

				if (!tmp.toString().equals(filed)) {
					// logger.info("属性未找到，暂不转换:" + tmp.toString() + ":" +
					// filed);
					continue;
				}
				val = webRequest.getParameterValues(tmp.toString());
				fie = frr[i];
				// 数字类型
				// 参数绑定+校验，类型无法转换的，抛出异常
				if (Number.class.isAssignableFrom(frr[i].getType())) {
					fie.set(obj, NumberUtils.parseNumber(val[0],
							(Class<Number>) frr[i].getType()));
				} else if (String.class.isAssignableFrom(frr[i].getType())) {
					fie.set(obj, val[0]);
				} else if (Date.class.isAssignableFrom(frr[i].getType())) {
					fie.set(obj, conversionFormat(val[0],frr[i].getType()));
				} else {
					logger.info("未找到属性，不转换:" + frr[i].getName() + "," + val[0]);
				}
			}
		}
		return obj;
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
				// binder.validate(hints instanceof Object[] ? (Object[]) hints
				// : new Object[] { hints });
				if (hints instanceof Object[]) {
					binder.validate((Object[]) hints);
				} else {
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
		// DefaultFormattingConversionService conversionService = new
		// DefaultFormattingConversionService();
		// // 默认不自动注册任何Formatter
		// CurrencyFormatter currencyFormatter = new CurrencyFormatter();// 金额
		// currencyFormatter.setFractionDigits(2);// 保留小数点后几位
		// currencyFormatter.setRoundingMode(RoundingMode.CEILING);//
		// 舍入模式（ceilling表示四舍五入）
		// // 注册Formatter SPI实现
		// conversionService.addFormatter(currencyFormatter);
		//
		// NumberFormatter nf = new NumberFormatter();// 数字
		// conversionService.addFormatter(nf);
		//
		// DateFormatter df = new DateFormatter("yyyy-MM-dd");// 日期
		// conversionService.addFormatter(df);
		//
		// System.out.println("字符转数字:"
		// + conversionService.convert("444444", Integer.class));
		// System.out.println("字符转日期:"
		// + conversionService.convert("2016-07-03", Date.class));
		try {
			System.out.println("字符转数字:"
					+ conversionFormat("444444", Integer.class));
			System.out.println("字符转日期:"
					+ conversionFormat("2016-07-03", Date.class));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Object conversionFormat(String val, Class<?> targetClass)
			throws Exception {
		if (Number.class.isAssignableFrom(targetClass)) {
		} else if (String.class.isAssignableFrom(targetClass)) {
		} else if (Date.class.isAssignableFrom(targetClass)) {
		} else {
			throw new Exception("不支持的转换类型:" + targetClass);
		}

		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		// 默认不自动注册任何Formatter
		CurrencyFormatter currencyFormatter = new CurrencyFormatter();// 金额
		currencyFormatter.setFractionDigits(2);// 保留小数点后几位
		currencyFormatter.setRoundingMode(RoundingMode.CEILING);// 舍入模式（ceilling表示四舍五入）
		// 注册Formatter SPI实现
		conversionService.addFormatter(currencyFormatter);

		NumberFormatter nf = new NumberFormatter();// 数字
		conversionService.addFormatter(nf);

		DateFormatter df = new DateFormatter("yyyy-MM-dd");// 日期
		conversionService.addFormatter(df);

		// System.out.println("字符转数字:"
		// + conversionService.convert("444444", Integer.class));
		// System.out.println("字符转日期:"
		// + conversionService.convert("2016-07-03", Date.class));
		return conversionService.convert(val, targetClass);
	}

}
