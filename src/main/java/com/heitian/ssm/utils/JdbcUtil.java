package com.heitian.ssm.utils;
import com.heitian.ssm.model.project.TransitemModel;
import oracle.jdbc.OracleTypes;

import java.sql.*;
import java.util.List;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2018-12-28 16:45
 **/
public class JdbcUtil {

    public static int TS_TRANSITEM(List<TransitemModel> list) throws Exception {

        int result=-10;
        Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        try {

            conn = DriverManager.getConnection("122.226.148.162", "zbgl", "zbgl");
            cs = conn.prepareCall("{ call  ?:= PKG_IMPXMK.TS_TRANSITEM(?,?,?,?,?,?,?,?,?)}");

            cs.registerOutParameter(1, OracleTypes.NUMBER);
            cs.setInt(2,Integer.parseInt(list.get(0).getIYEAR()));
            cs.setString(3, list.get(0).getFILENO());
            cs.setInt(4, Integer.parseInt(list.get(0).getDIVISIONGUID()));
            cs.setString(5, list.get(0).getDIVISIONNAME());
            cs.setInt(6, Integer.parseInt(list.get(0).getCBUDGETCATEGORYGUID()));
            cs.setString(7, list.get(0).getSUMMARY());
            cs.setInt(8, Integer.parseInt(list.get(0).getOPERATORGUID()));
            cs.setInt(9, list.get(0).getPNO());
            cs.setInt(10, Integer.parseInt(list.get(0).getISSOURCE()));
            cs.execute();
            conn.commit();
            System.out.print("cs.getInt"+cs.getInt(1));
            result = cs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (cs != null) {
                    cs.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
            }
        }

        return result;
    }


}
