package com.muyi.courage.common.config;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@MapperScan(basePackages = MybatisConfig.PACKAGES, sqlSessionFactoryRef = "dataSqlSessionFactory")
public class MybatisConfig {

	static final String PACKAGES = "com.muyi.courage.**.repository";
	private static final String MAPPER_LOCAL = "classpath*:mybatis/*.xml";

	/**
	 * 自动识别使用的数据库类型
	 * 在mapper.xml中databaseId的值就是跟这里对应，
	 * 如果没有databaseId选择则说明该sql适用所有数据库
	 */
	@Bean
	public DatabaseIdProvider getDatabaseIdProvider() {
		DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
		Properties properties = new Properties();
		properties.setProperty("Oracle", "oracle");
		properties.setProperty("MySQL", "mysql");
		properties.setProperty("DB2", "db2");
		properties.setProperty("Derby", "derby");
		properties.setProperty("H2", "h2");
		properties.setProperty("HSQL", "hsql");
		properties.setProperty("Informix", "informix");
		properties.setProperty("MS-SQL", "ms-sql");
		properties.setProperty("PostgreSQL", "postgresql");
		properties.setProperty("Sybase", "sybase");
		properties.setProperty("Hana", "hana");
		databaseIdProvider.setProperties(properties);
		return databaseIdProvider;
	}

	@Bean(name = "dataSqlSessionFactory")
	public SqlSessionFactory dataSqlSessionFactory(DataSource dataSource, DatabaseIdProvider databaseIdProvider) throws Exception {
		final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setDatabaseIdProvider(databaseIdProvider);
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCAL));
		return sqlSessionFactoryBean.getObject();
	}

	@Bean(name = "mybatisTransactionManager")
	@Primary //事务默认使用mysql数据库
	public DataSourceTransactionManager testTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}


}
