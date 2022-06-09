package com.sethpeden.persistence.sql;

import com.sethpeden.persistence.sql.data.Delete;
import com.sethpeden.persistence.sql.data.Insert;
import com.sethpeden.persistence.sql.data.Select;
import com.sethpeden.persistence.sql.data.Update;

public abstract class Query {
  public abstract String toSQL();

  public Type getType() {
    return Type.valueOf(this.getClass().getSimpleName().toUpperCase());
  }

  private enum Type {
    CREATE,
    ALTER,
    DROP,
    SELECT,
    INSERT,
    UPDATE,
    DELETE
  }

  public static Select select() {
    return new Select();
  }

  public static Insert insert() {
    return new Insert();
  }

  public static Update update() {
    return new Update();
  }

  public static Delete delete() {
    return new Delete();
  }

  @Override
  public String toString() {
    return this.toSQL();
  }
}

