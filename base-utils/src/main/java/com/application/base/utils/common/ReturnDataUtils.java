package com.application.base.utils.common;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 返回数据的工具类.
 * 
 * @author bruce
 *
 */
public class ReturnDataUtils {

	/**
	 * 创建返回的 Map
	 * 
	 * @param data
	 * @param flag
	 * @param msg
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	public static Map<String, Object> buildHeadMap( Map data, Integer flag, String msg ) {
		Map<String, Map> resHead = new HashMap<String, Map>();
		data.put( "flag", flag );
		data.put( "msg", msg );
		return data;
	}
	
	/**
	 * 创建返回Map
	 * 
	 * @param data
	 * @param flag
	 * @param msg
	 * @param responseCode
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	public static Map<String, Object> buildHeadMap( Map data, Integer flag, String msg, String responseCode ) {
		Map<String, Map> resHead = new HashMap<String, Map>();
		data.put( "flag", flag );
		data.put( "msg", msg );
		data.put( "responseCode", responseCode );
		return data;
	}
	
	
	/**
	 * 生成返回客户端的body报文 
	 * 
	 * @param data
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Map> buildDataMap( Map data ) {
		Map<String, Map> resData = new HashMap<String, Map>();
		resData.put( "data", data );
		return resData;
	}
	
	
	/**
	 * 创建返回Map
	 * 
	 * @param data
	 * @param flag
	 * @param msg
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> buildResultMap( Map data, Integer flag, String msg ) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put( "data", data );
		result.put( "flag", flag );
		result.put( "msg", msg );
		return result;
		
	}
	
	/**
	 * 创建返回 Map
	 * 
	 * @param data
	 * @param flag
	 * @param msg
	 * @param responseCode
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> buildResultMap( Map data, Integer flag, String msg, String responseCode ) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put( "data", data );
		result.put( "flag", flag );
		result.put( "msg", msg );
		result.put( "responseCode", responseCode );
		return result;
	}
	
	
	/**
	 * bean 对象到 map 集合
	 * 
	 * @param bean
	 * @return
	 */
	public static Map<String, Object> entityToMap( Object bean ) {
		Class<? extends Object> clazz = bean.getClass();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo( clazz );
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for ( int i = 0; i < propertyDescriptors.length; i++ ) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if ( !propertyName.equals( "class" ) ) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = null;
                    result = readMethod.invoke( bean, new Object[0] );
                    if ( null != propertyName ) {
                        propertyName = propertyName.toString();
                    }
                    if ( null != result ) {
                        result = result.toString();
                    }
                    returnMap.put( propertyName, result );
                }
            }
        } catch ( IntrospectionException e ) {
            System.out.println( "分析类属性失败" );
        } catch ( IllegalAccessException e ) {
            System.out.println( "实例化 JavaBean 失败" );
        } catch ( IllegalArgumentException e ) {
            System.out.println( "映射错误" );
        } catch ( InvocationTargetException e ) {
            System.out.println( "调用属性的 setter 方法失败" );
        }
        return returnMap;
	}
	
	
	/**
	 * map to json
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String hashMapToJson( HashMap map) {  
        String string = "{";  
        for ( Iterator it = map.entrySet().iterator(); it.hasNext(); ) {  
            Entry e = ( Entry ) it.next();  
            string += "'" + e.getKey() + "':";  
            string += "'" + e.getValue() + "',";  
        }  
        string = string.substring( 0, string.lastIndexOf( "," ) );  
        string += "}";  
        return string;  
    }  
}
