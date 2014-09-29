package com.pnsk.app.config;

import org.apache.commons.dbcp2.cpdsadapter.DriverAdapterCPDS;
import org.apache.commons.dbcp2.datasources.SharedPoolDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by okuda_junko on 14/09/21.
 */
//TODO poolの設定を真面目にする
@Configuration
@Import(Application.class)
public class DatabaseConfiguration {

    private static final String SCHEMA_NAME = "sample";
    private static final String MASTER_DATABASE_URL = "jdbc:mysql://localhost:3306/";
    private static final String SLAVE_DATABASE_URL = "jdbc:mysql://localhost:3306/";

    private Map<DBType, DataSource> targetDataSourceMap = new HashMap<DBType, DataSource>();

	@Bean
	public Map createDataSource() throws ClassNotFoundException {

		targetDataSourceMap.put(DBType.MASTER, masterDataSource());
		targetDataSourceMap.put(DBType.SLAVE, slaveDataSource());

		return targetDataSourceMap;
	}

	public DataSource getMasterDataSource() throws SQLException {
		return getDataSource(DBType.SLAVE.toString().toUpperCase());
	}

	public DataSource getSlaveDataSource() throws SQLException {
		return getDataSource(DBType.SLAVE.toString().toUpperCase());
	}

    private SharedPoolDataSource sharedPoolDataSource() {
        SharedPoolDataSource tds = new SharedPoolDataSource();
        tds.setMaxTotal(3);
        tds.setDefaultMaxWaitMillis(50);
        tds.setValidationQuery("select 1");
        tds.setDefaultTestOnBorrow(true);
        tds.setDefaultTestOnReturn(true);
        tds.setDefaultTestWhileIdle(true);

        return tds;
    }

    private DriverAdapterCPDS cpds() {
        DriverAdapterCPDS cpds = new DriverAdapterCPDS();
        try {
            cpds.setDriver("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new AssertionError(e);
        }
        cpds.setUser("root");
        return cpds;
    }

	private DataSource getDataSource(String schema) throws SQLException {
		return targetDataSourceMap.get(DBType.valueOf(schema));
	}

    private SharedPoolDataSource masterDataSource(){

        DriverAdapterCPDS cpds = cpds();
        cpds.setUrl(MASTER_DATABASE_URL + SCHEMA_NAME + "?useUnicode=true&characterEncoding=UTF-8&socketTimeout=60000&connectTimeout=3000");

        SharedPoolDataSource dataSource = sharedPoolDataSource();
        dataSource.setConnectionPoolDataSource(cpds);
        return dataSource;
    }

    private SharedPoolDataSource slaveDataSource(){
        DriverAdapterCPDS cpds = cpds();
        cpds.setUrl(SLAVE_DATABASE_URL + SCHEMA_NAME + "?useUnicode=true&characterEncoding=UTF-8&socketTimeout=60000&connectTimeout=3000");

        SharedPoolDataSource dataSource = sharedPoolDataSource();
        dataSource.setConnectionPoolDataSource(cpds);
        return dataSource;
    }

    private enum DBType {
        MASTER,
        SLAVE
    }

}
