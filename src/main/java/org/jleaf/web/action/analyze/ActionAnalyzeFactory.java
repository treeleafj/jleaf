package org.jleaf.web.action.analyze;


import java.io.Serializable;
import java.lang.reflect.Constructor;

import org.apache.log4j.Logger;
import org.jleaf.config.GlobalConfig;
import org.jleaf.utils.LogFactory;

/**
 * AbstractActionRequest 解析构造工厂
 *
 * @author leaf
 * @date 2014-1-2 下午10:49:14
 */
public abstract class ActionAnalyzeFactory implements Serializable {

    private final static Logger log = LogFactory.getLogger(ActionAnalyzeFactory.class);

    private static Class<? extends ActionAnalyze> actionAnalyzeClass = GlobalConfig.getDefaultActionAnalyzeClass();

    public static ActionAnalyze create(AnalyzeParam analyzeParam) {
        try{
            Constructor constructor = actionAnalyzeClass.getConstructor(AnalyzeParam.class);
            return (ActionAnalyze) constructor.newInstance(analyzeParam);
        }catch (Exception e){
            throw new Error(e);
        }
    }

}
