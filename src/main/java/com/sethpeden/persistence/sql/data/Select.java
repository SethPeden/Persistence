package com.sethpeden.persistence.sql.data;

import com.sethpeden.persistence.sql.Condition;
import com.sethpeden.persistence.sql.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Select extends Query {
  private List<String> columns;
  private String table;
  private String schema;
  private Condition condition;

  public Select() { }

  public Select columns(String... columns) {
    this.columns = Arrays.asList(columns);
    return this;
  }

  public Select from(String table) {
    this.table = table;
    return this;
  }

  public Select withSchema(String schema) {
    this.schema = schema;
    return this;
  }

  public Select where(Condition condition) {
    this.condition = condition;
    return this;
  }

  @Override
  public String toSQL() {
    String cols = this.columns.stream().map(column -> String.format("\"%s\"", column))
            .collect(Collectors.joining(", "));
    StringBuilder sb = new StringBuilder(String.format("%s %s FROM \"%s\".\"%s\"",
            this.getType(), cols, this.schema, this.table));
    if (Objects.nonNull(this.condition)) {
      sb.append(String.format(" WHERE %s", this.condition.toString()));
    }
    sb.append(";");
    return sb.toString();
  }
}
