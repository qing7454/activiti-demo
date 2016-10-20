package com.jiang.activiti.test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jiang.activiti.constant.ActivitiConstants;

public class TestUserFlow implements ActivitiConstants {
	
	private static final String CONFIG_PATH = "classpath:spring/spring-activiti.xml";
	
	private static ProcessEngine processEngine;
	
	private static RuntimeService runtimeService;
	
//	private static FormService formService;
	
	private static TaskService taskService;
	
//	private static HistoryService historyService;
	
	private static IdentityService identityService;
	
//	private static RepositoryService repositoryService;
	
	private static final String C_TASK_ID = "00044363fd2611e59b02d02788e9e6e4";
	
	public static void main(String[] args) {
		init();
//		deploy();
//		queryTask();
//		startFlow();
		startFlow1();
//		flow1();
//		flow2();
//		flow3();
	}
	
	/**
	 * 初始化
	 */
	@SuppressWarnings("resource")
	public static void init() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(CONFIG_PATH);
		taskService = (TaskService) ctx.getBean("taskService");
		processEngine = (ProcessEngine) ctx.getBean("processEngine");
//		formService = processEngine.getFormService();
		runtimeService = (RuntimeService) ctx.getBean("runtimeService");
//		historyService = (HistoryService) ctx.getBean("historyService");
		identityService = (IdentityService) ctx.getBean("identityService");
//		repositoryService = (RepositoryService) ctx.getBean("repositoryService");
	}
	
	/**
	 * 流程部署
	 */
	public static void deploy() {
		System.out.println("start deploy>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		RepositoryService repositoryService = processEngine.getRepositoryService();
		
		// 获取在classpath下的流程文件
		InputStream in = TestCollectTaskFlow.class.getClassLoader().getResourceAsStream("config/flow/collect-task-flow.zip");
		ZipInputStream zipInputStream = new ZipInputStream(in);
		
		repositoryService.createDeployment().addZipInputStream(zipInputStream).name(COLLECT_TASK.FLOW_NAME).deploy();
//		repositoryService.createDeployment().addClasspathResource("config/flow/collect-task-flow.bpmn").deploy();
		System.out.println("end deploy>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	/**
	 * 查询任务
	 */
	public static void queryTask() {
//		List<Task> tasks = taskService.createTaskQuery().taskAssignee("8a8190e55708e0c2015708e29f890006").list();
		List<Task> tasks = taskService.createTaskQuery().withoutTaskDueDate().list();
		System.out.println("task : " + tasks);
	}
	
	public static void startFlow1() {
		System.out.println("-》》》》》》开始任务：测试流程一");
		String proposerUser = "8a8190e55708e0c2015708e2447f0004";
		String applyUser = "8a8190e55708e0c2015708e29f890006";
		identityService.setAuthenticatedUserId(applyUser);
		System.out.println("-》》》》》》采集任务发起人：" + applyUser);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(COLLECT_TASK.TASK_ID, C_TASK_ID);
		map.put(COLLECT_TASK.NEXT_DELEGATE, proposerUser);
		System.out.println("-》》》》》》审核采集任务执行人：" + proposerUser);
		System.out.println("-》》》》》》[" + applyUser + "]提交采集任务，[" + proposerUser +"]审核");
		
		// jiangyg提交采集任务
		ProcessInstance instance = runtimeService.startProcessInstanceByKey(COLLECT_TASK.FLOW_NAME, "8a81901657bcaf490157bcc032ec007e", map);
		System.out.println("ActivityId=======" + instance.getActivityId());
		System.out.println("DeploymentId=======" + instance.getDeploymentId());
		System.out.println("Id=======" + instance.getId());
		System.out.println("TenantId=======" + instance.getTenantId());
		
	}
	
	public static void startFlow() {
		System.out.println("-》》》》》》开始任务：测试流程一");
		String proposerUser = "8a8190e55708e0c2015708e2447f0004";
		String applyUser = "8a8190e55708e0c2015708e29f890006";
		identityService.setAuthenticatedUserId(applyUser);
		System.out.println("-》》》》》》采集任务发起人：" + applyUser);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(COLLECT_TASK.TASK_ID, C_TASK_ID);
		map.put(COLLECT_TASK.NEXT_DELEGATE, proposerUser);
		System.out.println("-》》》》》》审核采集任务执行人：" + proposerUser);
		System.out.println("-》》》》》》[" + applyUser + "]提交采集任务，[" + proposerUser +"]审核");
		
		// jiangyg提交采集任务
		runtimeService.startProcessInstanceByKey(COLLECT_TASK.FLOW_NAME, "8a81901657bcaf490157bcc032ec007e", map);
		
		// 查询用户proposerUser需要处理的任务
		List<Task> bossTasks = taskService.createTaskQuery().taskAssignee(proposerUser).list();
		
		// boss审核采集任务
		for (Task task : bossTasks) {
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("auditSymbol", 1);
			System.out.println("-》》》》》》[" + proposerUser + "]审核任务[" + task.getId() + "]通过");
			taskService.setVariables(task.getId(), variables);
			taskService.complete(task.getId());
		}
	}
	
	/**
	 * 测试流程一：
	 * 			开始-》审核采集任务-》申请通过-》配置docker信息-》结束
	 */
	public static void flow1() {
		System.out.println("-》》》》》》开始任务：测试流程一");
		String proposerUser = "8a8190e55708e0c2015708e2447f0004";
		String applyUser = "8a8190e55708e0c2015708e29f890006";
		identityService.setAuthenticatedUserId(applyUser);
		System.out.println("-》》》》》》采集任务发起人：" + applyUser);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(COLLECT_TASK.TASK_ID, C_TASK_ID);
		map.put(COLLECT_TASK.NEXT_DELEGATE, proposerUser);
		System.out.println("-》》》》》》审核采集任务执行人：" + proposerUser);
		System.out.println("-》》》》》》[" + applyUser + "]提交采集任务，[" + proposerUser +"]审核");
		
		// jiangyg提交采集任务
		runtimeService.startProcessInstanceByKey(COLLECT_TASK.FLOW_NAME, "8a81901657bcaf490157bcc9306e0142", map);
		
		// 查询用户proposerUser需要处理的任务
		List<Task> bossTasks = taskService.createTaskQuery().taskAssignee(proposerUser).list();
		
		// boss审核采集任务
		for (Task task : bossTasks) {
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("auditSymbol", 1);
			System.out.println("-》》》》》》[" + proposerUser + "]审核任务[" + task.getId() + "]通过");
			taskService.complete(task.getId(), variables);
		}
		
		// jiangyg配置docker信息
		List<Task> jiangygTasks = taskService.createTaskQuery().taskAssignee(applyUser).list();
		for (Task task : jiangygTasks) {
			System.out.println("-》》》》》》[" + applyUser + "]配置docker信息任务[" + task.getId() + "]完成");
			taskService.complete(task.getId());
		}
		
		System.out.println("-》》》》》》结束任务：测试流程一");
	}
	
	/**
	 * 测试流程二：
	 * 			开始-》审核采集任务-》审核不通过-》结束
	 */
	public static void flow2() {
		System.out.println("-》》》》》》开始任务：测试流程二");
		String proposerUser = "8a8190e55708e0c2015708e2447f0004";
		String applyUser = "8a8190e55708e0c2015708e1ea9d0002";
		identityService.setAuthenticatedUserId(applyUser);
		System.out.println("-》》》》》》采集任务发起人：" + applyUser);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(COLLECT_TASK.TASK_ID, C_TASK_ID);
		map.put(COLLECT_TASK.NEXT_DELEGATE, proposerUser);
		System.out.println("-》》》》》》审核采集任务执行人：" + proposerUser);
		System.out.println("-》》》》》》[" + applyUser + "]提交采集任务，[" + proposerUser +"]审核");
		
		// zhangsan提交采集任务
		runtimeService.startProcessInstanceByKey(COLLECT_TASK.FLOW_NAME, "8a81901657bcaf490157bcc032ec007e", map);
		
		// 查询用户proposerUser需要处理的任务
		List<Task> bossTasks = taskService.createTaskQuery().taskAssignee(proposerUser).list();
		
		// boss审核采集任务
		for (Task task : bossTasks) {
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("auditSymbol", 3);
			System.out.println("-》》》》》》[" + proposerUser + "]审核任务[" + task.getId() + "]不通过");
			taskService.complete(task.getId(), variables);
		}
		
		System.out.println("-》》》》》》结束任务：测试流程二");
	}
	
	/**
	 * 测试流程三：
	 * 			开始-》审核采集任务-》驳回申请-》调整采集任务-》重新申请任务-》审核采集任务-》申请通过-》配置docker信息-》结束
	 */
	public static void flow3() {
		System.out.println("-》》》》》》开始任务：测试流程三");
		String proposerUser = "8a8190e55708e0c2015708e2447f0004";
		String applyUser = "8a8190e55708e0c2015708e0c29d0000";
		identityService.setAuthenticatedUserId(applyUser);
		System.out.println("-》》》》》》采集任务发起人：" + applyUser);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(COLLECT_TASK.TASK_ID, C_TASK_ID);
		map.put(COLLECT_TASK.NEXT_DELEGATE, proposerUser);
		System.out.println("-》》》》》》审核采集任务执行人：" + proposerUser);
		System.out.println("-》》》》》》[" + applyUser + "]提交采集任务，[" + proposerUser +"]审核");
		
		// lisi提交采集任务
		runtimeService.startProcessInstanceByKey(COLLECT_TASK.FLOW_NAME, "8a81901657bcaf490157bcbffade007c", map);
		
		// 查询用户proposerUser需要处理的任务
		List<Task> bossTasks = taskService.createTaskQuery().taskAssignee(proposerUser).list();
		
		// boss审核采集任务
		for (Task task : bossTasks) {
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("auditSymbol", 2);
			System.out.println("-》》》》》》[" + proposerUser + "]审核任务[" + task.getId() + "]驳回申请");
			taskService.complete(task.getId(), variables);
		}
		
		// lisi调整采集任务-》重新申请任务
		List<Task> adjustTasks = taskService.createTaskQuery().taskAssignee(applyUser).list();
		for (Task task : adjustTasks) {
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("adjustSymbol", 1);
			System.out.println("-》》》》》》[" + applyUser + "]调整采集任务[" + task.getId() + "]重新申请");
			taskService.complete(task.getId(), variables);
		}
		
		// boss审核采集任务
		List<Task> aginBossTasks = taskService.createTaskQuery().taskAssignee(proposerUser).list();
		for (Task task : aginBossTasks) {
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("auditSymbol", 1);
			System.out.println("-》》》》》》[" + proposerUser + "]审核任务[" + task.getId() + "]驳回申请");
			taskService.complete(task.getId(), variables);
		}
		
		// lisi配置docker信息
		List<Task> dockerTasks = taskService.createTaskQuery().taskAssignee(applyUser).list();
		for (Task task : dockerTasks) {
			System.out.println("-》》》》》》[" + applyUser + "]配置docker信息任务[" + task.getId() + "]完成");
			taskService.complete(task.getId());
		}
		
		System.out.println("-》》》》》》结束任务：测试流程三");
	}
}
