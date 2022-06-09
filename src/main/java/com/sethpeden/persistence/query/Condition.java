package com.sethpeden.persistence.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class Condition {
  public enum GroupOperator {
    AND,
    OR
  }

  private Conditions conditions = new Conditions();
  private GroupOperator groupOperator;

  private Condition(GroupOperator operator, Condition... conditions) {
    this.conditions.addAll(Arrays.asList(conditions));
    this.groupOperator = operator;
  }

  public static Condition group(GroupOperator operator, Condition... conditions) {
    return new Condition(operator, conditions);
  }

  public enum ColumnOperator {
    EQUALS("=", true),
    NOT_EQUALS("!=", true),
    GREATER_THAN(">", true),
    LESS_THAN("<", true),
    GREATER_THAN_EQUALS(">=", true),
    LESS_THAN_EQUALS("<=", true),
    IS_NULL("IS NULL", false),
    IS_NOT_NULL("IS NOT NULL", false);

    private String operator;
    private boolean expectsValue;
    ColumnOperator(String operator, boolean expectsValue) {
      this.operator = operator;
      this.expectsValue = expectsValue;
    }
  }

  private String column;
  private ColumnOperator columnOperator;
  private Object value;

  private Condition(String column, ColumnOperator operator, Object value) {
    this.column = column;
    this.columnOperator = operator;
    this.value = value;
    assert Objects.isNull(this.value) && !this.columnOperator.expectsValue;
  }

  public static Condition where(String column, ColumnOperator operator, Object value) {
    return new Condition(column, operator, value);
  }

  private Condition(String column, ColumnOperator operator) {
    this(column, operator, null);
  }

  public static Condition where(String column, ColumnOperator operator) {
    return new Condition(column, operator);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (Objects.nonNull(column) && Objects.nonNull(columnOperator)) {
      sb.append(String.format("(\"%s\" %s", this.column, this.columnOperator.operator));
      if (this.columnOperator.expectsValue) {
        if (this.value instanceof String) {
          sb.append(String.format(" '%s'", this.value));
        } else {
          sb.append(String.format(" %s", this.value));
        }
      }
      sb.append(")");
    } else if (this.conditions.size() > 0 && Objects.nonNull(this.groupOperator)) {
      sb.append("(");
      sb.append(this.conditions.stream().map(Condition::toString).collect(Collectors.joining(String.format(" %s ", this.groupOperator.name()))));
      sb.append(")");
    }
    return sb.toString();
  }
}

class Conditions extends ArrayList<Condition> { }
