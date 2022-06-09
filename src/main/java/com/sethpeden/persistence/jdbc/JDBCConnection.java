package com.sethpeden.persistence.jdbc;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCConnection {
  private String jdbcUrl;
  private Properties properties;
  private Connection connection;

  public Statement createStatement() throws SQLException {
    return this.connection.createStatement();
  }

  public JDBCConnection(String jdbcUrl, Properties properties) throws SQLException {
    this.jdbcUrl = jdbcUrl;
    this.properties = properties;
    this.connection = DriverManager.getConnection(this.jdbcUrl, this.properties);
  }

  public JDBCConnection(JDBCProperties jdbcProperties) throws IllegalAccessException, SQLException {
    this(jdbcProperties.getJDBCUrl(), jdbcProperties.getProperties());
  }

  public static JDBCConnection load(String contents) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, SQLException {
    return new JDBCConnection(JDBCProperties.fromString(contents));
  }

  public static JDBCConnection loadFile(String filePath) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, SQLException {
    return new JDBCConnection(JDBCProperties.fromFile(filePath));
  }

  public static JDBCConnection loadResource(String fileName) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, SQLException {
    return new JDBCConnection(JDBCProperties.fromResource(fileName));
  }

  public static JDBCConnection load() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException, SQLException {
    return new JDBCConnection(JDBCProperties.fromResources());
  }

}
