package com.jiang.activiti.email;

import java.util.List;

import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Test;

/**
 * 测试邮件流程
 * @author jiangyg
 *
 */
public class TestEmailFlow extends PluggableActivitiTestCase {
	
	@Test
	@Deployment(resources = "flow/email/email-flow-test.bpmn")
	public void testEmailFlow() {
		System.out.println("start emailFlowTest.............");
		runtimeService.startProcessInstanceByKey("email-flow");
		
		List<Task> tasks = taskService.createTaskQuery().taskAssignee("jiangyg").list();
		System.out.println("jiangyg tasks========>>>>>>>>>>>>>" + tasks);
		for (Task task : tasks) {
			taskService.complete(task.getId());
			System.out.println("complete task=========>>>>>>>>>>>>>>" + tasks);
		}
		
		List<Task> allTasks = taskService.createTaskQuery().active().list();
		System.out.println("all tasks============>>>>>>>>>>>>>>" + allTasks);
		
		System.out.println("end emailFlowTest.............");
	}
	
}
