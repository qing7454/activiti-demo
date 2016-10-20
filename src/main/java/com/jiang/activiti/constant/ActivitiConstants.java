package com.jiang.activiti.constant;

/**
 * 工作流常用常量
 * @author jiangyg
 *
 */
public interface ActivitiConstants {
	
	/**
	 * 采集任务
	 * @author jiangyg
	 *
	 */
	public static class COLLECT_TASK {
		
		/**
		 * 采集任务流程
		 */
		public static final String FLOW_NAME = "collect-task-flow";
		
		/**
		 * 审核采集任务步骤
		 */
		public static final String AUDIT_STEP = "audit-collect-task-step";
		
		/**
		 * 配置docker信息步骤
		 */
		public static final String DOCKER_STEP = "config-docker-info-step";
		
		/**
		 * 调整采集任务步骤
		 */
		public static final String ADJUST_STEP = "adjust-collect-task-step";
		
		/**
		 * 下一个代理人
		 */
		public static final String NEXT_DELEGATE = "next_delegate_user_id";
		
		/**
		 * 采集任务ID
		 */
		public static final String TASK_ID = "collect-task-id";
		
		/**
		 * 审核标志变量
		 */
		public static final String AUDIT_SYMBOL = "auditSymbol";
		
		/**
		 * 调整标志变量
		 */
		public static final String ADJUST_SYMBOL = "adjustSymbol";
		
	}

}
