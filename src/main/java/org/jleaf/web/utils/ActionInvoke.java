package org.jleaf.web.utils;

import org.jleaf.web.action.Action;

public class ActionInvoke {

	private static ThreadLocal<Action> action = new ThreadLocal<Action>();
	
	public static Action getAction(){
		return action.get();
	}
	
	public static void setAction(Action action){
		ActionInvoke.action.set(action);
	}
	
	public static void remove(){
		action.remove();
	}
	
}
