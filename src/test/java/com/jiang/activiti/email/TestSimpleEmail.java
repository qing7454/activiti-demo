package com.jiang.activiti.email;

import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.test.Deployment;
import org.junit.Test;

/**
 * 测试简单的邮件发送任务
 * @author jiangyg
 *
 */
public class TestSimpleEmail extends PluggableActivitiTestCase {
	
	@Test
	@Deployment(resources = "flow/email/test-email.bpmn")
	public void testSendEmail() {
		System.out.println("start testSendEmail.............");
		runtimeService.startProcessInstanceByKey("mail");
		System.out.println("end testSendEmail.............");
	}

}
