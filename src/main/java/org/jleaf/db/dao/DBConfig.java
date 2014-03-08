package org.jleaf.db.dao;

import org.jleaf.config.GlobalConfig;

public class DBConfig {
	
	private static Class<? extends BaseDao> baseDaoClass = GlobalConfig.getDefaultDao();
	
	private static BaseDao baseDao;
	
	public static Class<? extends BaseDao> getBaseDaoClass(){
		return baseDaoClass;
	}
	
	public static BaseDao getBaseDao(){
		if(baseDao == null){
			try {
				baseDao = getBaseDaoClass().newInstance();
			} catch (Exception e) {
				throw new Error(e);
			}
		}
		return baseDao;
	}

}
