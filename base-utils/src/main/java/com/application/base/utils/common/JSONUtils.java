package com.application.base.utils.common;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class JSONUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(JSONUtils.class);
	
	public static final String EMPTY = "";
	/** 空的 {@code JSON} 数据 - <code>"{}"</code>。 */
	public static final String EMPTY_JSON = "{}";
	/** 空的 {@code JSON} 数组(集合)数据 - {@code "[]"}。 */
	public static final String EMPTY_JSON_ARRAY = "[]";
	/** 默认的 {@code JSON} 完整日期/时间字段的格式化模式。 */
	public static final String DEFAULT_ALL_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss SSS";
	/** 默认的 {@code JSON} 日期/时间字段的格式化模式。 */
	public static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	/** 默认的 {@code JSON} 日期字段的格式化模式。 */
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	/** 默认的 {@code JSON} 日期/时间/小时/分钟字段的格式化模式。 */
	public static final String DEFAULT_DATE_NO_SECOND = "yyyy-MM-dd HH:mm";
	/** {@code Google Gson} 的 {@literal @Since} 注解常用的版本号常量 - {@code 1.0}。 */
	public static final Double SINCE_VERSION_10 = 1.0d;
	/** {@code Google Gson} 的 {@literal @Since} 注解常用的版本号常量 - {@code 1.1}。 */
	public static final Double SINCE_VERSION_11 = 1.1d;
	/** {@code Google Gson} 的 {@literal @Since} 注解常用的版本号常量 - {@code 1.2}。 */
	public static final Double SINCE_VERSION_12 = 1.2d;
	//public static final GsonBuilder builder = new GsonBuilder();
	private static final StringNullEmptyAdapterFactory<String> stringNullEmptyConvertor = new StringNullEmptyAdapterFactory<String>();

	/**
	 * 将给定的目标对象根据指定的条件参数转换成 {@code JSON} 格式的字符串。
	 * <p />
	 * <strong>该方法转换发生错误时，不会抛出任何异常。若发生错误时，曾通对象返回 <code>"{}"</code>； 集合或数组对象返回
	 * <code>"[]"</code></strong>
	 * 
	 * @param target
	 *            目标对象。
	 * @param targetType
	 *            目标对象的类型。
	 * @param isSerializeNulls
	 *            是否序列化 {@code null} 值字段。
	 * @param version
	 *            字段的版本号注解。
	 * @param datePattern
	 *            日期字段的格式化模式。
	 * @param excludesFieldsWithoutExpose
	 *            是否排除未标注 {@literal @Expose} 注解的字段。
	 * @param isStringNull2Empty
	 *            是否将null值转换为空字符串。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, Type targetType,
			boolean isSerializeNulls, Double version, String datePattern,
			boolean excludesFieldsWithoutExpose, boolean isStringNull2Empty) {
		GsonBuilder builder = new GsonBuilder();
		if (target == null) {
			return EMPTY_JSON;
		}
		builder.disableHtmlEscaping(); // 防止将数据库中的特殊字符如&转码的问题
		if (isSerializeNulls) {
			builder.serializeNulls();
		}
		if (version != null) {
			builder.setVersion(version.doubleValue());
		}
		if (isEmpty(datePattern)) {
			datePattern = DEFAULT_ALL_DATE_PATTERN;
		}
		builder.setDateFormat(datePattern);
		if (excludesFieldsWithoutExpose) {
			builder.excludeFieldsWithoutExposeAnnotation();
		}
		String result = EMPTY;
		if (isStringNull2Empty) {
			builder.registerTypeAdapterFactory(stringNullEmptyConvertor);
		} else {
		}
		Gson gson = builder.registerTypeAdapter(Double.class, new DoubleImpl()).create();
		try {
			if (targetType != null) {
				result = gson.toJson(target, targetType);
			} else {
				result = gson.toJson(target);
			}
		} catch (Exception ex) {
			logger.warn("目标对象{}  转换 JSON 字符串时，发生异常！", target.getClass().getName());
			if (target instanceof Collection || target instanceof Iterator
					|| target instanceof Enumeration
					|| target.getClass().isArray()) {
				result = EMPTY_JSON_ARRAY;
			} else {
				result = EMPTY_JSON;
			}
		}
		return result;
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法会将String的null值输出为空字符串("")。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法将会String字段的 {@code null} 值字段转换为空字符串；其它对象字段的 {@code null} 值不变</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJsonWithStringNull2Empty(Object target) {
		return toJson(target, null, true, null, DEFAULT_DATE_TIME_PATTERN, false,true);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target) {
		return toJson(target, null, true, null, DEFAULT_DATE_TIME_PATTERN, false,false);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param datePattern
	 *            日期字段的格式化模式。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, String datePattern) {
		return toJson(target, null, false, null, datePattern, false, false);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param version
	 *            字段的版本号注解({@literal @Since})。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, Double version) {
		return toJson(target, null, false, version, DEFAULT_DATE_TIME_PATTERN,false, false);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param nullAble
	 *            是否排除为NULL的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, boolean nullAble) {
		return toJson(target, null, nullAble, null, DEFAULT_DATE_TIME_PATTERN,false, false);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param version
	 *            字段的版本号注解({@literal @Since})。
	 * @param excludesFieldsWithoutExpose
	 *            是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, Double version,boolean excludesFieldsWithoutExpose) {
		return toJson(target, null, false, version, DEFAULT_DATE_TIME_PATTERN,excludesFieldsWithoutExpose, false);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSSS}；</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param targetType
	 *            目标对象的类型。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, Type targetType) {
		return toJson(target, targetType, false, null, DEFAULT_DATE_TIME_PATTERN,false, false);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSSS}；</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param targetType
	 *            目标对象的类型。
	 * @param version
	 *            字段的版本号注解({@literal @Since})。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, Type targetType, Double version) {
		return toJson(target, targetType, false, version, DEFAULT_DATE_TIME_PATTERN,false, false);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param targetType
	 *            目标对象的类型。
	 * @param excludesFieldsWithoutExpose
	 *            是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, Type targetType,
			boolean excludesFieldsWithoutExpose) {
		return toJson(target, targetType, false, null, DEFAULT_DATE_TIME_PATTERN,excludesFieldsWithoutExpose, false);
	}

	/**
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 * 
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param targetType
	 *            目标对象的类型。
	 * @param version
	 *            字段的版本号注解({@literal @Since})。
	 * @param excludesFieldsWithoutExpose
	 *            是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 */
	public static String toJson(Object target, Type targetType, Double version,
			boolean excludesFieldsWithoutExpose) {
		return toJson(target, targetType, false, version, DEFAULT_DATE_TIME_PATTERN,excludesFieldsWithoutExpose, false);
	}

	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
	 * 
	 * @param <T>
	 *            要转换的目标类型。
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param token
	 *            {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
	 * @param datePattern
	 *            日期格式模式。
	 * @param isEmptyString2Null
	 *            是否将空字符串转为null。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 */
	public static <T> T fromJson(String json, TypeToken<T> token,
			String datePattern, boolean isEmptyString2Null) {
		GsonBuilder builder = new GsonBuilder();
		if (isEmpty(json)) {
			return null;
		}
		// GsonBuilder builder = new GsonBuilder();
		if (isEmpty(datePattern)) {
			datePattern = DEFAULT_DATE_TIME_PATTERN;
		}
		if (isEmptyString2Null) {
			builder.registerTypeAdapterFactory(stringNullEmptyConvertor);
		}
		builder.setDateFormat(datePattern);
		Gson gson = builder.registerTypeAdapter(Double.class, new DoubleImpl())
				.create();
		try {
			return gson.fromJson(json, token.getType());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("{}无法转换为{} 对象!", json, token.getRawType().getName());
			return null;
		}
	}

	/**
	 * json字符串转list对象
	 * 
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param clazz
	 *            要转换的目标类。
	 * @param isEmptyString2Null
	 *            是否将空字符串转为null。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型List对象。
	 */
	public static <T> List<T> fromListJson(String json, Class<T> clazz,
			boolean isEmptyString2Null) {
		GsonBuilder builder = new GsonBuilder();
		if (isEmpty(json)) {
			return null;
		}
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        builder.setDateFormat(DEFAULT_DATE_TIME_PATTERN);
        Gson gson = builder.registerTypeAdapter(Double.class, new DoubleImpl())
				.create();
		if (isEmptyString2Null) {
			builder.registerTypeAdapterFactory(stringNullEmptyConvertor);
		}
		List<T> result = new ArrayList<>(array.size());
		for (final JsonElement element : array) {
			result.add(gson.fromJson(element, clazz));
		}
		return result;
		// builder.setDateFormat(DEFAULT_DATE_TIME_PATTERN);
		// T [] arr = gson.fromJson(json,clazz);
		// return Arrays.asList(arr);
	}

	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
	 * 
	 * @param <T>
	 *            要转换的目标类型。
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param token
	 *            {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 */
	public static <T> T fromJson(String json, TypeToken<T> token) {
		return fromJson(json, token, null, false);
	}

	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * 
	 * @param <T>
	 *            要转换的目标类型。
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param clazz
	 *            要转换的目标类。
	 * @param datePattern
	 *            日期格式模式。
	 * @param isEmptyString2Null
	 *            是否将空字符串转为null。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 */
	public static <T> T fromJson(String json, Class<T> clazz,
			String datePattern, boolean isEmptyString2Null) {
		GsonBuilder builder = new GsonBuilder();
		if (isEmpty(json)) {
			return null;
		}
		// GsonBuilder builder = new GsonBuilder();
		if (isEmpty(datePattern)) {
			datePattern = DEFAULT_DATE_TIME_PATTERN;
		}
		if (isEmptyString2Null) {
			builder.registerTypeAdapterFactory(stringNullEmptyConvertor);
		}
		builder.setDateFormat(datePattern);
		Gson gson = builder.registerTypeAdapter(Double.class, new DoubleImpl())
				.create();
		try {
			return gson.fromJson(json, clazz);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(" {}无法转换为{} 对象!", json, clazz.getName());
			return null;
		}
	}

	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * 
	 * @param <T>
	 *            要转换的目标类型。
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param clazz
	 *            要转换的目标类。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 */
	public static <T> T fromJson(String json, Class<T> clazz) {
		return fromJson(json, clazz, DEFAULT_DATE_TIME_PATTERN, false);
	}
	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean}
	 * 对象，但会把空字符串转为null。</strong>
	 * 
	 * @param <T>
	 *            要转换的目标类型。
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param clazz
	 *            要转换的目标类。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 */
	public static <T> T fromJsonWithEmptyString2Null(String json, Class<T> clazz) {
		return fromJson(json, clazz, null, true);
	}
	public static boolean isEmpty(String inStr) {
		boolean reTag = false;
		if (inStr == null || "".equals(inStr)) {
			reTag = true;
		}
		return reTag;
	}
}

class DoubleImpl implements JsonSerializer<Double> {
	@Override
	public JsonElement serialize(Double src, Type typeOfSrc,
			JsonSerializationContext context) {
		if (src == src.longValue()) {
			return new JsonPrimitive(src.longValue());
		}
		return new JsonPrimitive(src);
	}
}

/**
 * 空字符串转为null，null转为空字符串适配器工厂类
 * 
 * @param <T>
 */
class StringNullEmptyAdapterFactory<T> implements TypeAdapterFactory {
	@SuppressWarnings({ "unchecked", "hiding" })
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		Class<T> rawType = (Class<T>) type.getRawType();
		if (rawType != String.class) {
			return null;
		}
		return (TypeAdapter<T>) new StringNullEmptyAdapter();
	}
}

/**
 * object转json时，String类型字段：null转"" 
 * json转object时，String类型字段：""转null
 */
class StringNullEmptyAdapter extends TypeAdapter<String> {
	@Override
	// json转对象时调用
	public String read(JsonReader reader) throws IOException {
		if (reader.peek() == JsonToken.NULL) {
			reader.nextNull();
			return null;
		}
		String value = reader.nextString();
		if ("".equals(value)) {
			return null;
		}
		return value;
	}

	@Override
	// 对象转json时调用
	public void write(JsonWriter out, String value) throws IOException {
		if (value == null) {
			out.value("");
			return;
		}
		out.value(value.toString());
	}
}