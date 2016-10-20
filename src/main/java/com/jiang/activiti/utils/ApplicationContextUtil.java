package com.jiang.activiti.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring应用全文工具类
 * 
 * @author jiangyg
 *
 */
public class ApplicationContextUtil implements ApplicationContextAware {
	
	private static ApplicationContext context;

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		ApplicationContextUtil.context = context;
	}

	/**
	 * 获取spring上下文
	 * @return
	 */
	public static ApplicationContext getContext() {
		return context;
	}
	
	/**
	 * 根据class获取bean
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(Class<T> clazz) {
		return context.getBean(clazz);
	}
	
	/**
	 * 根据bean name获取bean
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) context.getBean(name);
	}

}
