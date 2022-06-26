package com.arifng.camunda.config;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by Rana on 07/12/2021.
 */
@Component
public class MyBatisCustomConfiguration {

    public SqlSessionFactory initializeMyBatisSqlSessionFactory() {
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngines.getDefaultProcessEngine()
                .getProcessEngineConfiguration();
        DataSource dataSource = processEngineConfiguration.getDataSource();

        // use this transaction factory if you work in a non transactional
        // environment
        // TransactionFactory transactionFactory = new JdbcTransactionFactory();

        // use this transaction factory if you work in a transactional
        // environment (e.g. called within the engine or using JTA)
        TransactionFactory transactionFactory = new ManagedTransactionFactory();

        Environment environment = new Environment("customComments", transactionFactory, dataSource);

        InputStream myBatisCustomConfig = this.getClass().getResourceAsStream("/myBatisCustomConfig.xml");
        XMLConfigBuilder parser = new XMLConfigBuilder( //
                new InputStreamReader(myBatisCustomConfig), //
                "", // set environment later via code
                getSqlSessionFactoryProperties((ProcessEngineConfigurationImpl) processEngineConfiguration));
        Configuration configuration = parser.getConfiguration();
        configuration.setEnvironment(environment);
        configuration = parser.parse();

        configuration.setDefaultStatementTimeout(processEngineConfiguration.getJdbcStatementTimeout());

        return new SqlSessionFactoryBuilder().build(configuration);
    }

    private Properties getSqlSessionFactoryProperties(ProcessEngineConfigurationImpl engineConfiguration) {
        Properties properties = new Properties();
        ProcessEngineConfigurationImpl.initSqlSessionFactoryProperties(properties,
                engineConfiguration.getDatabaseTablePrefix(), engineConfiguration.getDatabaseType());
        properties.put("prefix", engineConfiguration.getDatabaseTablePrefix());
        return properties;
    }
}
