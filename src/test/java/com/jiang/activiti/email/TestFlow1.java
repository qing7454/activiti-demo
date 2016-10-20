package com.jiang.activiti.email;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestFlow1 {
	
	private static final String CONFIG_PATH = "classpath:config/spring/spring-test-activiti.xml";
	
	private static RuntimeService runtimeService;
	
	private static TaskService taskService;
	
	private static RepositoryService repositoryService;
	
	public static void main(String[] args) {
		init();
		repositoryService.createDeployment().addClasspathResource("flow/email/email-flow-test.bpmn").name("email-flow").deploy();
		System.out.println("start emailFlowTest.............");
		
//		runtimeService.startProcessInstanceByKey("email-flow");
		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("sendEmail", "email@jiangyg.com");
		runtimeService.startProcessInstanceByKey("email-flow", variables);
		
		List<Task> tasks = taskService.createTaskQuery().taskAssignee("jiangyg").list();
		System.out.println("jiangyg tasks========>>>>>>>>>>>>>" + tasks);
		for (Task task : tasks) {
//			Map<String, Object> variables = new HashMap<String, Object>();
//			variables.put("sendEmail", "email@jiangyg.com");
//			taskService.complete(task.getId(), variables);
			taskService.complete(task.getId());
			System.out.println("complete task=========>>>>>>>>>>>>>>" + tasks);
		}
		
		List<Task> allTasks = taskService.createTaskQuery().active().list();
		System.out.println("all tasks============>>>>>>>>>>>>>>" + allTasks);
		
		System.out.println("end emailFlowTest.............");
	}
	
	@SuppressWarnings("resource")
	public static void init() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(CONFIG_PATH);
		ProcessEngine processEngine = (ProcessEngine) ctx.getBean("processEngine");
		repositoryService = processEngine.getRepositoryService();
		taskService = (TaskService) ctx.getBean("taskService");
		runtimeService = (RuntimeService) ctx.getBean("runtimeService");
	}

}
