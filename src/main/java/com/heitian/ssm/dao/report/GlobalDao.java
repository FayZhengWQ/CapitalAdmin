package com.heitian.ssm.dao.report;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;
import java.util.Map;

public interface GlobalDao {


    @SuppressWarnings("UnusedReturnValue")
    @Select("${sql}")
    @Options(statementType = StatementType.CALLABLE)
    List<Map<String, Object>> selectCall(@Param("sql") String sql, @Param("args") Map<String, Object> args);

    @Select("${sql}")
    String getSetId(@Param("sql") String sql);

    @Select("${sql}")
    List<Map<String, Object>> selectListMap(@Param("sql") String sql);

    @Select("${sql}")
    Map<String, Object> selectMap(@Param("sql") String sql);

    @Insert("${sql}")
    @Options(useGeneratedKeys = false)
    Integer insertObject(@Param("sql") String sql);

    @Update("${sql}")
    @Options(useGeneratedKeys = false)
    Integer updateObject(@Param("sql") String sql);

    @Delete("${sql}")
    Integer deleteObject(@Param("sql") String sql);


    class SQLFactory {
        public static String selectCall(String procedure, Boolean isReturn, Map<String, Object> args) {
//            List<String> argStrings = new ArrayList<>();
            boolean first = true;
            StringBuilder sb = new StringBuilder("CALL ").append(procedure).append("(");
            for (String key : args.keySet()) {
                if (!first) sb.append(",");
                sb.append("'").append(args.get(key)).append("'");
                first = false;
            }
            if (isReturn) {
                if (!args.keySet().isEmpty()) sb.append(",");
                sb.append("#{args.cur,mode=OUT,jdbcType=CURSOR,javaType=ResultSet,resultMap=curMap9}");
            }
            sb.append(")");
            return sb.toString();
        }
    }
}
