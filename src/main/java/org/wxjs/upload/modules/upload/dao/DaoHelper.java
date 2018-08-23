package org.wxjs.upload.modules.upload.dao;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wxjs.upload.modules.upload.entity.InfPunish;
import org.wxjs.upload.modules.upload.entity.InfPunishProcess;
import org.wxjs.upload.modules.upload.entity.InfPunishResult;

public class DaoHelper {
	
	public static DataSource dataSource = getUploadDatasource();
	
	public static SqlSessionFactory sqlSessionFactory = getSessionFactory();
	
	public static DataSource getUploadDatasource(){
	    ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");  
	    
	    return ctx.getBean("dataSourceUpload",DataSource.class);  		
	}
	
	public static SqlSessionFactory getSessionFactory(){
		
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Environment environment = new Environment("development", transactionFactory, dataSource);
		Configuration configuration = new Configuration(environment);
		
		configuration.addMapper(InfPunish.class);
		configuration.addMapper(InfPunishProcess.class);
		configuration.addMapper(InfPunishResult.class);
		
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
		
		return sqlSessionFactory;
	}

}
