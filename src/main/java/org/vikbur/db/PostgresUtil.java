/*
package org.vikbur.db;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import org.vikbur.configs.DBConfig;

import javax.sql.DataSource;

@Service
public class PostgresUtil {
    private final JdbcTemplate jdbcTemplate;

    public PostgresUtil(DBConfig config){
        jdbcTemplate = new JdbcTemplate(getDataSource(config));
    }
    public DataSource getDataSource(DBConfig config){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(config.getUrl());
        driverManagerDataSource.setUsername(config.getUsername());
        driverManagerDataSource.setPassword(config.getPassword());
        driverManagerDataSource.setDriverClassName(config.getDriverClassName());
        return driverManagerDataSource;
    }

    public JdbcTemplate getJdbcTemplate(){
        return jdbcTemplate;
    }
}
*/
