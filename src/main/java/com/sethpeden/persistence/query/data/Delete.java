package com.sethpeden.persistence.query.data;

import com.sethpeden.persistence.query.Condition;
import com.sethpeden.persistence.query.Query;

import java.util.Objects;

public class Delete extends Query {
  private String table;
  private String schema;
  private Condition condition;

  public Delete() { }

  public Delete from(String table) {
    this.table = table;
    return this;
  }

  public Delete withSchema(String schema) {
    this.schema = schema;
    return this;
  }

  public Delete where(Condition condition) {
    this.condition = condition;
    return this;
  }

  @Override
  public String toSQL() {
    StringBuilder sb = new StringBuilder(String.format("%s FROM \"%s\".\"%s\"", this.getType(), this.schema, this.table));
    if (Objects.nonNull(this.condition)) {
      sb.append(String.format(" WHERE %s", this.condition.toString()));
    }
    sb.append(";");
    return sb.toString();
  }
}
