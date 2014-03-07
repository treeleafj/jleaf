package org.jleaf.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.jleaf.web.action.Action;

/**
 * leaf
 * 14-3-1 下午6:32.
 */
public class ControllersImpl implements Controllers {

    private Map<String, Controller> controllerMap = new HashMap<String, Controller>();

    @Override
    public Controller getController(Action action) {
        return controllerMap.get(getControllerUriByRequestUri(action.getAnalyzeResult().getUri()));
    }

    @Override
    public void add(Controller controller) {
        if (controllerMap.containsKey(controller.getInfo().getName())) {
            throw new Error("重复添加Controller:" + controller);
        }
        controllerMap.put(controller.getInfo().getName(), controller);
    }

    /**
     * 通过请求的URI得到Controller的URI
     * @param requestUri 请求的URI
     * @return Controller的URI
     */
    private String getControllerUriByRequestUri(String requestUri){
        int index = requestUri.lastIndexOf("/");
        if(index >= 0){
            return requestUri.substring(0,index);
        }
        return requestUri;
    }
}
