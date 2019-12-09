package com.duoying.test.mapper.youxian_qa;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = YouXianQaDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "sql_factory_youxian_qa")
public class YouXianQaDataSourceConfig {

    static final String DB = "youxian_qa";
    static final String PACKAGE = "com.ly.shopping.mapper." + DB;
    static final String MAPPER_LOCATION = "classpath:mybatis/**/*.xml";

    @Value("${config.db.youxian_qa.url}")
    private String url;
    @Value("${config.db.youxian_qa.username}")
    private String user;
    @Value("${config.db.youxian_qa.password}")
    private String password;
    @Value("${config.db.driver}")
    private String driverClass;


    @Bean(name = "ds_youxian_qa")
    public DataSource ds_youxian_qa() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url); //数据源
        config.setUsername(user); //用户名
        config.setPassword(password); //密码
        config.setDriverClassName(driverClass);
        config.addDataSourceProperty("cachePrepStmts", "true"); //是否自定义配置，为true时下面两个参数才生效
        config.addDataSourceProperty("prepStmtCacheSize", "250"); //连接池大小默认25，官方推荐250-500
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048"); //单条语句最大长度默认256，官方推荐2048
        config.addDataSourceProperty("useServerPrepStmts", "true"); //新版本MySQL支持服务器端准备，开启能够得到显著性能提升
        config.addDataSourceProperty("useLocalSessionState", "true");
        config.addDataSourceProperty("useLocalTransactionState", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("cacheResultSetMetadata", "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("elideSetAutoCommits", "true");
        config.addDataSourceProperty("maintainTimeStats", "false");

        HikariDataSource ds = new HikariDataSource(config);
        return ds;
    }

    @Bean(name = "tx_youxian_qa")
    public DataSourceTransactionManager tx_youxian_qa() {
        return new DataSourceTransactionManager(ds_youxian_qa());
    }


    @Bean(name = "sql_factory_youxian_qa")
    public SqlSessionFactory sql_factory_youxian_qa(@Qualifier("ds_youxian_qa") DataSource ds_youxian_qa)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(ds_youxian_qa);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(YouXianQaDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
