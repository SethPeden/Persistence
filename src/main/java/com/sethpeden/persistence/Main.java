package com.sethpeden.persistence;

import com.sethpeden.persistence.jdbc.JDBCConnection;
import com.sethpeden.persistence.query.Condition;
import com.sethpeden.persistence.query.Query;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main( String[] args ) throws SQLException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
//        Query query = Query.select().columns("testColumn1", "testColumn2").from("testTable").withSchema("testSchema").where(Condition.where("testColumn1", Condition.ColumnOperator.EQUALS, "7"));
//        Query query = Query.insert().into("testTable").withSchema("testSchema").columns("testColumn1", "testColumn2").values("3", "4");
//        Query query = Query.delete().from("testTable").withSchema("testSchema");//.where(Condition.where("testColumn1", Condition.ColumnOperator.EQUALS, "5"));
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("testColumn2", "42");
        Query query = Query.update().table("testTable").withSchema("testSchema").withData(updateMap).where(Condition.where("testColumn1", Condition.ColumnOperator.EQUALS, "3"));

        try (Statement stmt = JDBCConnection.load().createStatement()) {
            // SELECT
//            ResultSet rs = stmt.executeQuery(query.toSQL());
//            while (rs.next()) {
//                List<String> values = Arrays.asList(rs.getString("testColumn1"), rs.getString("testColumn2"));
//                System.out.println(values);
//            }

            // INSERT && DELETE && UPDATE
            stmt.execute(query.toSQL());
        }
    }
}
