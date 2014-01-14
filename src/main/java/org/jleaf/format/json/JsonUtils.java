package org.jleaf.format.json;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

/**
 * JSON转换工具(使用fastjson)
 * @author leaf
 * @date 2014-1-3 下午6:09:49
 */
public class JsonUtils {
	
	//配置
	private static SerializeConfig mapping = new SerializeConfig();
	
	static{
		//时间类型转换
		mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
	}
	
	/**
	 * 将一个对象转为json字符窜
	 * 对象最好能继承IJSONObject(可防止相互引用的循环递归)
	 */
	public static String toJSON(Object obj){
		return JSON.toJSONString(obj,mapping);
	}
	
	/**
	 * 将一个json字符窜转为对象
	 */
	public static <T> T parseObject(String json, Class<T> classz){
		return JSON.parseObject(json,classz);
	}
	
	/**
	 * 将一个json字符窜转为对象数组
	 */
	public static <T> List<T> parseArray(String json,Class<T> classz){
		return JSON.parseArray(json, classz);
	}
	
	/**
	 * 将一个json字符窜转为对象
	 * @param typeReference 返回类型,例如 new TypeReference<Map<String, User>>() {},或者
	 * new TypeReference<List<String, User>>() {}
	 */
	public static <T> T parseObject(String json, TypeReference<T> typeReference){
		return JSON.parseObject(json,typeReference);
	}
	
	/**
	 * 将一个json字符窜转为对象数组
	 * [{header类型}, {body类型}],Type[] types = new Type[] {Header.class, Body.class};
	 * (Header) list.get(0);(Body) list.get(1);
	 */
	public static List<Object> parseArray(String json,Type[] types){
		return JSON.parseArray(json, types);
	}
}
