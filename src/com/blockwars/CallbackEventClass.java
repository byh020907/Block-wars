package com.blockwars;

public abstract class CallbackEventClass {
	protected Object[] objs;
	public CallbackEventClass(Object[] objs){
		this.objs=objs;
	}
	
	public abstract void callbackMethod();
}
