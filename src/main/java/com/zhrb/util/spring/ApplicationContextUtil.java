package com.zhrb.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextUtil implements ApplicationContextAware {
	private static ApplicationContext context;// 声明一个静态变量保存

	@SuppressWarnings("static-access")
	public void setApplicationContext(ApplicationContext contex)
			throws BeansException {
		this.context = contex;
	}

	public static ApplicationContext getContext() {
		return context;
	}
	
	/*
	 * 获取 bean
	 * 
	 * @param name
	 * @param requiredType
	 * @return
	 */
	public static <T> T getBean(String name, Class<T> requiredType) {
		return context.getBean(name, requiredType);
	}
	
}