package com.redmancometh.factionimmunity.databasing;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.MapMaker;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSource
{
    private HikariDataSource datasource;
    ConcurrentMap make = new MapMaker().weakValues().makeMap();

    public DataSource(String host, int port, String database, String username, String password) throws IOException, SQLException, PropertyVetoException
    {
        HikariConfig config = new HikariConfig();
        config.setMaximumPoolSize(50);
        config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        config.addDataSourceProperty("serverName", host);
        config.addDataSourceProperty("port", port);
        config.addDataSourceProperty("databaseName", database);
        config.addDataSourceProperty("user", username);
        config.addDataSourceProperty("password", password);
        config.setPoolName("Immunity");
        datasource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException
    {
        return datasource.getConnection();
    }
}