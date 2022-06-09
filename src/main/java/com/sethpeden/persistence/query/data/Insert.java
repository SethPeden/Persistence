package com.sethpeden.persistence.query.data;

import com.sethpeden.persistence.query.Query;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Insert extends Query {
  private List<String> columns;
  private List<Object> values;
  private String table;
  private String schema;

  public Insert() { }

  public Insert into(String table) {
    this.table = table;
    return this;
  }

  public Insert withSchema(String schema) {
    this.schema = schema;
    return this;
  }

  public Insert columns(String... columns) {
    this.columns = Arrays.asList(columns);
    return this;
  }

  public Insert values(Object... values) {
    this.values = Arrays.asList(values);
    return this;
  }

  @Override
  public String toSQL() {
    assert this.columns.size() == this.values.size();
    String cols = this.columns.stream().map(column -> String.format("\"%s\"", column))
            .collect(Collectors.joining(", "));
    String values = this.values.stream()
            .map(value -> value instanceof String ? String.format("'%s'", value) : value.toString())
            .collect(Collectors.joining(", "));
    return String.format("%s INTO \"%s\".\"%s\"(%s) VALUES (%s);", this.getType(), this.schema,
            this.table, cols, values);
  }
}
