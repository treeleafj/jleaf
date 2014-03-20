package org.jleaf.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

/**
 * 提供快速操作javabean的方法
 * 
 * @author leaf
 * @date 2014-3-20 下午8:59:54
 */
public class FastBeanUtils extends BeanUtils{
	
	private static FastBeanCache fastBeanCache = new FastBeanCache();
	
	protected static FastBeanCache getFastBeanCache(){
		return fastBeanCache;
	}

	/**
	 * 将Map的值装入指定的对象中,对象由 classz 类型决定
	 * 
	 * @param classz
	 *            对象类型
	 * @param properties
	 *            map值
	 * @return 返回装入后生成的对象
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	public static Object fastPopulate(Class<?> classz, Map<String,Object> properties)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object obj = classz.newInstance();
		fastPopulate(obj, properties);
		return obj;
	}

	/**
	 * 将Map的值装入指定的对象中
	 * @param obj 对象
	 * @param properties map值
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void fastPopulate(Object obj, Map<String,Object> properties) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Class<?> classz = obj.getClass();
		Map<String, PropertiesEntry> map = getFastBeanCache().getPropertiesEntryMap(classz);
		if(map.size() > 0){
			for(Map.Entry<String, Object> entry : properties.entrySet()){
				PropertiesEntry pe = map.get(entry.getKey());
				if(pe != null){
					if(pe.getSet() != null){
						Object v = ConvertUtils.convert(entry.getValue(), pe.getType());
						pe.getSet().invoke(obj, v);
					}
				}
			}
		}
	}

}
