package org.jleaf.web.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
public class SessionAdapterMap implements Map<String, Object>, Serializable {

    private static final int NOT_DATA_LENGTH = -1;

    private HttpServletRequest request;

    private int size = NOT_DATA_LENGTH;

    private Map<String, Object> sessionMap = new HashMap<String, Object>();

    public SessionAdapterMap(HttpServletRequest request) {
        this.request = request;
    }

    private void initSessionMap() {
    	HttpSession session = getSession();
        Enumeration<String> names = session.getAttributeNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            sessionMap.put(name, session.getAttribute(name));
        }
    }

    public int size() {
        if (this.size == NOT_DATA_LENGTH) {
            initSessionMap();
        }
        this.size = sessionMap.size();
        return this.size;
    }

    public boolean isEmpty() {
        return this.size() <= 0;
    }

    public boolean containsKey(Object key) {
        return getSession().getAttribute(key.toString()) != null;
    }

    public boolean containsValue(Object value) {
        if (this.size() <= 0) {
            return false;
        } else {
            return sessionMap.containsValue(value);
        }
    }

    public Object get(Object key) {
        return getSession().getAttribute(key.toString());
    }

    public Object put(String key, Object value) {
        sessionMap.put(key, value);
        getSession().setAttribute(key, value);
        return value;
    }

    public Object remove(Object key) {
        sessionMap.remove(key);
        Object obj = getSession().getAttribute(key.toString());
        getSession().removeAttribute(key.toString());
        return obj;
    }

    public void putAll(Map<? extends String, ? extends Object> m) {
        for (Entry<? extends String, ? extends Object> entry : m.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    public void clear() {
        sessionMap.clear();
        Enumeration<String> names = getSession().getAttributeNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            getSession().removeAttribute(name);
        }
    }

    public Set<String> keySet() {
        if (this.size == NOT_DATA_LENGTH) {
            initSessionMap();
        }
        return sessionMap.keySet();
    }

    public Collection<Object> values() {
        if (this.size == NOT_DATA_LENGTH) {
            initSessionMap();
        }
        return sessionMap.values();
    }

    public Set<Entry<String, Object>> entrySet() {
        if (this.size == NOT_DATA_LENGTH) {
            initSessionMap();
        }
        return sessionMap.entrySet();
    }

    public String toString() {
        if (this.size == NOT_DATA_LENGTH) {
            initSessionMap();
        }
        return sessionMap.toString();
    }
    
    private HttpSession getSession(){
    	return request.getSession();
    }

}
