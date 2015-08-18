package me.onlyxin.javaWebFramework.mvc;

public class Result {

	private boolean sucess;
	private int error;
	private Object data;
	
	public Result(boolean sucess) {
		this.sucess = sucess;
	}
	
}
