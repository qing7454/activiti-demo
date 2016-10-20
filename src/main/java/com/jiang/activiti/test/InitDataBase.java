package com.jiang.activiti.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;

/**
 * 初始化数据库
 * @author jiangyg
 *
 */
public class InitDataBase {
	
	public static void main(String[] args) {
		System.out.println("start init database>>>>>>>>>>>>>>>>>>");
		ProcessEngine processEngine1 = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
		System.out.println("processEngine==============" + processEngine1);
		System.out.println("end init database>>>>>>>>>>>>>>>>>>>>");
	}

}
