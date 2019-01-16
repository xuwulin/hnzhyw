package com.swx.ibms.common.web.workflow;

import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * activiti的通用服务类
 * 2018-05-06
 * @author admin
 *
 */
@SuppressWarnings("all")
@Service
public class ActivitiService {
	private static final Logger LOG = LoggerFactory.getLogger(ActivitiService.class);

    @Autowired
    private SpringProcessEngineConfiguration processEngineConfiguration;

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;
    
    
    /**
     * 通过key启动流程
     *
     * @param key    流程Key
     * @param params 流程参数
     *
     * @return 流程实体对象
     */
    public ProcessInstance startProcessByKey (String key, Map<String, Object> params) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(key, params);

        LOG.info("--流程Id:{}", processInstance.getId());
        LOG.info("--流程定义Id:{}", processInstance.getProcessDefinitionId());
        LOG.info("--流程Name:{}", processInstance.getName());
        LOG.info("--流程业务Key:{}", processInstance.getBusinessKey());
        return processInstance;
    }

    /**
     * 通过key启动流程（带业务键）
     *
     * @param key         流程Key
     * @param businessKey 业务Key
     * @param params      流程参数
     *
     * @return 流程实体对象
     */
    public ProcessInstance startProcessByKey (String key, String businessKey, Map<String, Object> params) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, businessKey, params);
        LOG.info("--流程Id:{}", processInstance.getId());
        //LOG.info("--流程定义Id:{}", processInstance.getProcessDefinitionId());
        LOG.info("--流程Name:{}", processInstance.getName());
        LOG.info("--流程业务Key:{}", processInstance.getBusinessKey());
        return processInstance;
    }

    /**
     * 撤销发起流程
     *
     * @param processId 流程实例Id
     */
    public void revokeStartProcess (String processId) {
        removeProcessInstance(processId);
    }

    /**
     * 删除流程实例
     *
     * @param processId
     */
    public void removeProcessInstance (String processId) {
        runtimeService.deleteProcessInstance(processId, "");
    }

    /**
     * 部署流程
     *
     * @param name     流程名称
     * @param bpmnPath bpmn文件路径
     * @param picPath  流程图路径
     *
     * @return
     */
    public String deploy (String name, String bpmnPath, String picPath) {
        Deployment deployment = processEngine.getRepositoryService().createDeployment().addClasspathResource(bpmnPath)// bpmn文件路径
                .addClasspathResource(picPath)// 流程图路径文件路径
                .name(name).deploy();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        if (processDefinitionQuery != null) {
            LOG.info("部署成功");
        }
        LOG.info("流程部署名称:" + deployment.getName());
        LOG.info("流程部署Id:" + deployment.getId());
        LOG.info("流程部署时间：" + deployment.getDeploymentTime());
        return deployment.getId();
    }


    /**
     * 获取已部署的流程定义
     *
     * @return
     */
    public List<ProcessDefinition> getProcessDefinition () {
        return repositoryService.createProcessDefinitionQuery().list();
    }
    
    /**
     * 通过流程定义id获取流程定义对象
     * @param id 流程定义id
     * @return 流程定义对象
     */
    public ProcessDefinition getProcessDefinitionByDefinitionId(String id){
        return repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();
    }

    /**
     * 删除部署的流程定义
     *
     * @param depolymentId 部署Id
     */
    public void deleteProcessDefinition (String depolymentId) {
        repositoryService.deleteDeployment(depolymentId, true);
        LOG.info("删除流程部署Id:" + depolymentId);
    }
    
    /**
     * 获取指定人的待办任务列表
     *
     * @param assignee 指定人
     *
     * @return 任务列表
     *
     * @throws Exception 异常
     */
    public List<Task> getCurrentTasks (String assignee) throws Exception {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }


    /**
     * 通过指定人查询待办业务
     *
     * @param assignee 制定人
     *
     * @return 待办业务列表
     *
     * @throws Exception 异常
     */
    public List<Task> getTasksByAssignee (String assignee) throws Exception {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

    
}
