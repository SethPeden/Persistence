package com.sethpeden.persistence.query.data;

import com.sethpeden.persistence.query.Condition;
import com.sethpeden.persistence.query.Query;

import java.util.Map;
import java.util.stream.Collectors;

public class Update extends Query {
  private Map<String, Object> data;
  private String table;
  private String schema;
  private Condition condition;

  public Update() { }

  public Update withData(Map<String, Object> data) {
    this.data = data;
    return this;
  }

  public Update table(String table) {
    this.table = table;
    return this;
  }

  public Update withSchema(String schema) {
    this.schema = schema;
    return this;
  }

  public Update where(Condition condition) {
    this.condition = condition;
    return this;
  }

  @Override
  public String toSQL() {
    String encodedData = this.data.entrySet().stream()
            .map(entry -> String.format("\"%s\"=%s", entry.getKey(),
                    entry.getValue() instanceof String ? String.format("'%s'", entry.getValue()) : entry.getValue()))
            .collect(Collectors.joining(", "));
    return String.format("%s \"%s\".\"%s\" SET %s WHERE %s;",
            this.getType(), this.schema, this.table, encodedData, this.condition);
  }
}
