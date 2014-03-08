package org.jleaf.web.action.analyze;


import java.io.Serializable;
import java.lang.reflect.Constructor;

import org.jleaf.config.GlobalConfig;

/**
 * AbstractActionRequest 解析构造工厂
 *
 * @author leaf
 * @date 2014-1-2 下午10:49:14
 */
@SuppressWarnings("serial")
public abstract class ActionAnalyzeFactory implements Serializable {

    private static Class<? extends ActionAnalyze> actionAnalyzeClass = GlobalConfig.getDefaultActionAnalyzeClass();

    public static ActionAnalyze create(AnalyzeParam analyzeParam) {
        try{
            Constructor<?> constructor = actionAnalyzeClass.getConstructor(AnalyzeParam.class);
            return (ActionAnalyze) constructor.newInstance(analyzeParam);
        }catch (Exception e){
            throw new Error(e);
        }
    }

}
