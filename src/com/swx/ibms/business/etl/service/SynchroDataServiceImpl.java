package com.swx.ibms.business.etl.service;

import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import com.swx.ibms.business.etl.mapper.SynchroDataMapper;
import org.apache.commons.lang3.StringUtils;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.springframework.stereotype.Service;


/**
 * 同步数据实现类型
 * @author 李治鑫
 * @since 2017年8月24日  下午2:20:52
 */
@Service("synchroDataService")
public class SynchroDataServiceImpl implements SynchroDataService {

	/**
	 * 数据接口
	 */
	@Resource
	private SynchroDataMapper synchroDataMapper;
	
	@Override
	public void deleteData(String[] tables) {
		for(String tablename : tables){
			synchroDataMapper.deleteData(tablename);
		}
	}

	@Override
	public void startSynchro() {
		//获取数据同步的配置文件 以及相关参数
		ResourceBundle bl = ResourceBundle.getBundle("synchroDatabase");
		
		//获取需要清空哪些表
//		String[] tables = bl.getString("deleteDataInTheTables").split(",");
		//清空这些表中的数据
//		this.deleteData(tables);
		
		//统一业务的数据源库的相关配置信息
		String sourceName = bl.getString("sourceName");
		String sourceHost = bl.getString("sourceHost");
		String sourceDBName = bl.getString("sourceDBName");
		String sourcePort = bl.getString("sourcePort");
		String sourceUserName = bl.getString("sourceUserName");
		String sourcePassword = bl.getString("sourcePassword");
		
		//综合业务数据库的相关信息
		String targetName = bl.getString("targetName");
		String targetHost = bl.getString("targetHost");
		String targetDBName = bl.getString("targetDBName");
		String targetPort = bl.getString("targetPort");
		String targetUserName = bl.getString("targetUserName");
		String targetPassword = bl.getString("targetPassword");
		
		try {
			KettleEnvironment.init();
			
			URL url = Thread.currentThread().getContextClassLoader().getResource("Exchange.ktr");
			TransMeta tsMeta = new TransMeta(Paths.get(url.toURI()).toString());
			
			List<DatabaseMeta> databaseMetaList = tsMeta.getDatabases();
			
			for(DatabaseMeta db : databaseMetaList){
				if(StringUtils.equals(StringUtils.trim(db.getDatabaseName()), sourceName)){
					//如果 ktr文件配置的源库 配置 与 项目配置的源库 名称相同则将配置信息尽行修改
					db.setHostname(sourceHost);
					db.setDBName(sourceDBName);
					db.setDBPort(sourcePort);
					db.setUsername(sourceUserName);
					db.setPassword(sourcePassword);
				}
				if(StringUtils.equals(StringUtils.trim(db.getDatabaseName()), targetName)){
					//如果 ktr文件配置的目标库 配置 与 项目配置的目标库 名称相同则将配置信息尽行修改
					db.setHostname(targetHost);
					db.setDBName(targetDBName);
					db.setDBPort(targetPort);
					db.setUsername(targetUserName);
					db.setPassword(targetPassword);
				}
			}
			
			Trans trans = new Trans(tsMeta);
			trans.execute(null);
			trans.waitUntilFinished();
			
			if(trans.getErrors()>0){
				throw new RuntimeException("导出数据时出错！");
			}else if(trans.isFinished()) {
				System.out.println("");
				System.out.println("");
				System.out.println("*******************************统一业务数据同步完成**************************************");
				System.out.println("");
				System.out.println("");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
