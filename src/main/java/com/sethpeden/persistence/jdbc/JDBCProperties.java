package com.sethpeden.persistence.jdbc;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JDBCProperties {
  private static final String DB_PROPS = "db.properties";

  private String type;
  private String server;
  private Integer port;
  private String database;
  private String user;
  private String password;

  private JDBCProperties() { }

  public String getJDBCUrl() {
    return String.format("jdbc:%s://%s:%d/%s", this.type, this.server, this.port, this.database);
  }

  public Properties getProperties() throws IllegalAccessException {
    Properties properties = new Properties();
    for (Field field : JDBCProperties.class.getDeclaredFields()) {
      field.setAccessible(true);
      properties.put(field.getName(), field.get(this));
    }
    return properties;
  }

  public static JDBCProperties fromString(String contents) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    JDBCProperties properties = new JDBCProperties();
    for (Field field : JDBCProperties.class.getDeclaredFields()) {
      Pattern pattern = Pattern.compile(String.format("JDBC_%s=(\\w*)", field.getName().toUpperCase()));
      Matcher matcher = pattern.matcher(contents);
      if (matcher.find()) {
        String value = matcher.group(1);
        field.setAccessible(true);
        field.set(properties, field.getType().getConstructor(String.class).newInstance(value));
      }
    }
    return properties;
  }

  public static JDBCProperties fromFile(String filePath) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    String contents = String.join("\n", Files.readAllLines(Paths.get(filePath)));
    return JDBCProperties.fromString(contents);
  }

  public static JDBCProperties fromResource(String fileName) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    String contents = String.join("\n", Files.readAllLines(Paths.get(Objects.requireNonNull(JDBCProperties.class.getClassLoader().getResource(fileName)).getPath())));
    return JDBCProperties.fromString(contents);
  }

  public static JDBCProperties fromResources() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    return JDBCProperties.fromResource(JDBCProperties.DB_PROPS);
  }

}
