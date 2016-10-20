package com.jiang.activiti.delegate;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import com.jiang.activiti.constant.ActivitiConstants;
import com.jiang.activiti.utils.ApplicationContextUtil;

/**
 * 获取采集任务下一个节点执行人
 * 
 * @author jiangyg
 *
 */
public class CollectTaskNextDelegateHandler implements TaskListener, ActivitiConstants {

	private static final long serialVersionUID = 1088881239768086438L;

	@Override
	public void notify(DelegateTask delegateTask) {
		ProcessEngine processEngine = (ProcessEngine) ApplicationContextUtil.getContext().getBean("processEngine");
		RuntimeService runtimeService = processEngine.getRuntimeService();
		// 获取存储在流程实例中的nextAssignee变量的值
		String next = (String) runtimeService.getVariable(delegateTask.getExecutionId(), COLLECT_TASK.NEXT_DELEGATE);
		// 设置节点的下个执行人
		delegateTask.setAssignee(next);
	}

}
