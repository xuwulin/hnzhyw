package com.swx.ibms.common.web.workflow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;


/**
 * <h3>工作流的流程部署类</h3>
 *
 * @author admin
 * @since 2017/9/12.
 */
public class WorkflowDeployer implements InitializingBean,ApplicationContextAware{
	private final Logger LOG = LoggerFactory.getLogger(WorkflowDeployer.class);
	
	private Resource[] deploymentResources;

	/**
	 * 是否冻结流程设计，设置为true后，将不会部署流程了
	 */
	private String freeze;
	private String category;
	private ApplicationContext appCtx;
    public void setDeploymentResources(Resource[] deploymentResources) {
		this.deploymentResources = deploymentResources;
	}

	public void setCategory(String category) {
		this.category = category;
	}


	public void setFreeze (String freeze) {
		this.freeze = freeze;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationcontext) throws BeansException {
		this.appCtx = applicationcontext;
		
	}
	@Override
    public void afterPropertiesSet() throws Exception {
    	//如果冻结
    	if(BooleanUtils.toBoolean(freeze)){
    		return;
		}
		if(category == null){
			throw new FatalBeanException("资源找不到！");
		}
		
		if(deploymentResources != null){
			RepositoryService repositoryService = appCtx.getBean(RepositoryService.class);
			for(Resource resource : deploymentResources){
				String resourceName = resource.getFilename();
				String deploymentName = category;
				boolean doDeploy = true;
				
				//先查询数据库是否已经存在
				List<Deployment> deployments = repositoryService.createDeploymentQuery()
																.deploymentName(deploymentName)
																.orderByDeploymenTime()
																.desc()
																.list();
				if(!deployments.isEmpty()){
					Deployment existing = deployments.get(0);
					
					try {
						InputStream in = repositoryService.getResourceAsStream(existing.getId(), resourceName);
						
						if(in != null){
							File file = File.createTempFile("deployment", "xml",new File(System.getProperty("java.io.tmpdir")));
							file.deleteOnExit();
							OutputStream out = new FileOutputStream(file);
							IOUtils.copy(in, out);
							in.close();
							out.close();
							doDeploy = (FileUtils.checksumCRC32(file) != FileUtils.checksumCRC32(resource.getFile()));
						}else{
							throw new ActivitiException("读不到资源文件："+resourceName+",输入流为空！");
						}
					} catch (ActivitiException e) {
						LOG.error("已有的部署对象"+existing.getName()+"中不含有资源文件"+resourceName+"所描述的流程信息");
						LOG.error("部署ID为："+existing.getId()+"将重新部署");
					}
					
				}
				
				if(doDeploy){
					repositoryService.createDeployment().name(deploymentName)
														.addInputStream(resourceName, resource.getInputStream())
//														.addClasspathResource(resourceName+".png")
														.deploy();
					LOG.warn(resource.getFilename()+"已经部署成功");
				}
			}
		}
    }

	
}
