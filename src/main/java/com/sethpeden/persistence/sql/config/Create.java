package com.sethpeden.persistence.sql.config;

import com.sethpeden.persistence.sql.Query;

/*
    CREATE TABLE IF NOT EXISTS "testSchema"."testTable"
    (
      "testColumn1" character varying(256) COLLATE pg_catalog."default",
      "testColumn2" character varying(256) COLLATE pg_catalog."default",
      "testColumn3" bigint,
      "testColumn4" character varying COLLATE pg_catalog."default"
    )
 */

public class Create extends Query {


  @Override
  public String toSQL() {
    return null;
  }
}
