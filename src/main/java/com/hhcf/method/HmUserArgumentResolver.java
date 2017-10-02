package com.hhcf.method;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import net.sf.json.util.JSONUtils;

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
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.bind.support.WebRequestDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.ModelFactory;
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
	private static ValidatorFactory factory = Validation
			.buildDefaultValidatorFactory();

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
					fie.set(obj, conversionFormat(val[0], frr[i].getType()));
				} else {
					logger.info("未找到属性，不转换:" + frr[i].getName() + "," + val[0]);
				}
			}
		}

		if (null != parameter.getParameterAnnotation(Valid.class)) {
			logger.info("aaa:" + parameter.getParameterType());
			// JSR303验证
			// 重点在这里在这里在这里在这里在这里在这里在这里
			String name = ModelFactory.getNameForParameter(parameter);
			Object attribute = null;
			if ((mavContainer.containsAttribute(name))) {
				attribute = mavContainer.getModel().get(name);
			} else {
				attribute = createAttribute(name, parameter, binderFactory,
						webRequest);
			}
			WebDataBinder binder = binderFactory.createBinder(webRequest,
					attribute, name);
			// System.out.println("ccc:" + binder.getObjectName());
			Errors mrb = this.validate(obj);
			// System.out.println("aaa:" + mrb.getObjectName());

			binder.getBindingResult().addAllErrors(mrb);// JSR303验证
		}
		return obj;
	}

	public static void main(String[] args) {
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

	protected Object createAttribute(String attributeName,
			MethodParameter parameter, WebDataBinderFactory binderFactory,
			NativeWebRequest request) throws Exception {
		return BeanUtils.instantiateClass(parameter.getParameterType());
	}

	/**
	 * JSR303验证
	 * 
	 */

	public <T> MapBindingResult validate(T t) {
		System.out.println("ttt:" + t.toString());
		System.out.println("ttt:" + t.getClass().getSimpleName());

		Validator validator = factory.getValidator();
		Set<ConstraintViolation<T>> cvs = validator.validate(t);
		logger.info("错误原始信息:" + cvs.toString());
		Map<String, Object> map = new HashMap<String, Object>();
		for (ConstraintViolation<T> cv : cvs) {
			logger.info("错误原始信息,分组:" + JSONUtils.valueToString(cv));
			map.put(t.getClass().getName() + "."
					+ cv.getPropertyPath().toString(), cv);
		}
		logger.info("aaa:" + t.getClass().getName());
		MapBindingResult errors = new MapBindingResult(map, lowerCaseFirst(t
				.getClass().getSimpleName()));
		errors.addError(new ObjectError(lowerCaseFirst(t.getClass()
				.getSimpleName()), "ccccccccccccccccccccccccccccc"));
		return errors;
	}

	/**
	 * 类型转换
	 */
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

	/**
	 * 首字母转小写
	 */
	public static String lowerCaseFirst(String text) {
		if (Character.isLowerCase(text.charAt(0))) {
			return text;

		}
		return (new StringBuilder())
				.append(Character.toLowerCase(text.charAt(0)))
				.append(text.substring(1)).toString();
	}

	/**
	 * 首字母转大写
	 */
	public static String upperCaseFirst(String text) {
		if (Character.isUpperCase(text.charAt(0))) {
			return text;
		}
		return (new StringBuilder())
				.append(Character.toUpperCase(text.charAt(0)))
				.append(text.substring(1)).toString();
	}
}
