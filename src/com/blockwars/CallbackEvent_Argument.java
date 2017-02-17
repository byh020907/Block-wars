package com.blockwars;

public abstract class CallbackEvent_Argument implements CallbackAble{
	protected Object[] objs;
	public CallbackEvent_Argument(Object[] objs){
		this.objs=objs;
	}
	
	public abstract void callbackMethod();
}
