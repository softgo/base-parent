package com.application.base.core.utils;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.application.base.core.constant.Constants;
import com.application.base.core.datasource.param.ESQLOperator;
import com.application.base.core.datasource.param.Param;
import com.application.base.utils.common.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SQL语句工具类
 * @ClassName:  SQLUtil
 *
 */
public class SQLUtil {

    private static Logger logger = LoggerFactory.getLogger(SQLUtil.class);
	public static final int FROUNT = 1;
	public static final int BACK = 2;
	public static final int ALL = 3;
	
	/**
	 * 根据参数返回相应SQL语句中拼写的值
	 * @param value
	 * @return
	 * String
	 */
	@SuppressWarnings("unchecked")
	public static String get(Object value) {
		StringBuilder builder = new StringBuilder();
		if (value instanceof String) {
			builder.delete(0, builder.length());
			builder.append(Constants.Separator.SINGLE_QUOTES).append(String.valueOf(value))
					.append(Constants.Separator.SINGLE_QUOTES);
			return builder.toString();
		} else if (value instanceof Date) {
			builder.delete(0, builder.length());
			String dateStr = DateUtils.forDatetime((Date) value);
			if (StringUtils.isEmpty(dateStr))
				throw new IllegalArgumentException("Date value can't be null or date value can't be formated!");
			builder.append(Constants.Separator.BLANK).append(Constants.Separator.SINGLE_QUOTES).append(dateStr)
					.append(Constants.Separator.SINGLE_QUOTES).append(Constants.Separator.BLANK);
			return builder.toString();
		} else if (value instanceof Number) {
			builder.delete(0, builder.length());
			builder.append(Constants.Separator.BLANK).append(value).append(Constants.Separator.BLANK);
			return builder.toString();
		} else if (value instanceof String[]) {
			builder.delete(0, builder.length());
			builder.append(Constants.Separator.BLANK).append(Constants.Separator.LBRACKET);
			String valueArr[] = (String[]) value;
			for (int i = 0; i < valueArr.length; i++) {
				String vStr = valueArr[i];
				builder = appendStringValue(builder, vStr);
				if (i != valueArr.length - 1)
					builder.append(Constants.Separator.COMMA);
			}
			builder.append(Constants.Separator.RBRACKET).append(Constants.Separator.BLANK);
			return builder.toString();
		}else if (value instanceof Number[]) {
			builder.delete(0, builder.length());
			builder.append(Constants.Separator.BLANK).append(Constants.Separator.LBRACKET);
			Integer valueArr[] = (Integer[]) value;
			for (int i = 0; i < valueArr.length; i++) {
				Integer v = valueArr[i];
				if (v!=null) {
					builder.append(v);
					if (i != valueArr.length - 1)
						builder.append(Constants.Separator.COMMA);
				}
			}
			builder.append(Constants.Separator.RBRACKET).append(Constants.Separator.BLANK);
			return builder.toString();
		} else if (value instanceof List) {
			builder.delete(0, builder.length());
			builder.append(Constants.Separator.BLANK).append(Constants.Separator.LBRACKET);
			List<Object> valueArr = List.class.cast(value);
			for (int i = 0; i < valueArr.size(); i++) {
				Object o = valueArr.get(i);
				if(!(o instanceof String) && !(o instanceof Number))
					throw new IllegalArgumentException();
				if (o!=null) {
					if(o instanceof String)
						builder = appendStringValue(builder, o.toString());
					if(o instanceof Number)
						builder.append(o);
				}
				if (i != valueArr.size() - 1)
					builder.append(Constants.Separator.COMMA);
			}
			builder.append(Constants.Separator.RBRACKET).append(Constants.Separator.BLANK);
			return builder.toString();
		}else {
			builder.delete(0, builder.length());
			builder.append(Constants.Separator.BLANK).append(String.valueOf(value)).append(Constants.Separator.BLANK);
			return builder.toString();
		}
	}

	private static StringBuilder appendStringValue(StringBuilder builder, String vStr) {
		builder.append(Constants.Separator.SINGLE_QUOTES).append(String.valueOf(vStr))
		.append(Constants.Separator.SINGLE_QUOTES);
		return builder;
	}

	/**
	 * 生成 LIKE值
	 * @param value
	 * @param type FROUNT,BACK,默认ALL
	 * @return
	 * String
	 */
	public static String likeValue(String value, int type) {
		StringBuilder builder = new StringBuilder();
		switch (type) {
		case FROUNT:
			return builder.append(Constants.Separator.PERCENT).append(value).toString();
		case BACK:
			return builder.append(value).append(Constants.Separator.PERCENT).toString();
		default:
			return builder.append(Constants.Separator.PERCENT).append(value).append(Constants.Separator.PERCENT).toString();
		}
	}

	/**
	 * 生成更新语句
	 * @param param
	 * @return
	 * String
	 *
	 */
	public static String updateSql(Param param) {
		StringBuilder builder = new StringBuilder();
		Map<String, Object> map = param.get();
		if (map == null)
			throw new RuntimeException("update param is null");
		Set<String> keys = map.keySet();
        builder.append(Constants.Separator.BLANK).append(ESQLOperator.SET);
		for (String column : keys) {
            builder.append(Constants.Separator.BLANK).append(formateToColumn(column)).append(ESQLOperator.EQ)
					.append(SQLUtil.get(map.get(column))).append(Constants.Separator.COMMA);
		}
		String result = builder.substring(Constants.WrapperExtend.ZERO, builder.length() - 1).toString();
		return result;
	}
    /**
     * 打印sql语句
     *
     * @param sqlId
     * @param map
     */
    public static  String getSql(SqlSession sqlSession, String sqlId, Map<String, Object> map) {
        Configuration configuration = sqlSession.getConfiguration();
        String sql = MyBatisSqlUtils.getSql(configuration, configuration.getMappedStatement(sqlId).getBoundSql(map));
        logger.debug("printlnSQL 【{}】", sql);
        return sql;
    }

    public static String formateToColumn(String columnName) {
        char[] arr = columnName.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char c : arr) {
            if (!Character.isUpperCase(c))
                builder.append(c);
            else
                builder.append(Constants.Separator.UPDERLINE).append(c);

        }
        return builder.toString().toUpperCase();
    }

}
