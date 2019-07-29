package com.heitian.ssm.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;

/**
 * ClassName:ClobToString
 * Package:com.heitian.ssm.utils
 * Description:
 * author:@Fay
 * Date:2019/7/2610:31
 */
public class ClobToStringUtil {

    public static String ClobToString(Clob clob) throws SQLException, IOException {
        String ret = "";
        Reader read = clob.getCharacterStream();
        BufferedReader br = new BufferedReader(read);
        String s = br.readLine();
        StringBuffer sb = new StringBuffer();
        while (s != null) {
            sb.append(s);
            s = br.readLine();
        }
        ret = sb.toString();
        if (br != null) {
            br.close();
        }
        if (read != null) {
            read.close();
        }
        return ret;
    }
}
