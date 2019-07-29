package com.heitian.ssm.utils.druid;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2019-01-08 02:12
 **/
public class MybatisUtils {
    private static SqlSessionFactory sf=null;
    private static SqlSessionFactory osf=null;
    static{
        InputStream in = String.class.getResourceAsStream("/mybatis-config.xml");
        InputStream in1 = String.class.getResourceAsStream("/mybatis-config.xml");
        sf = new SqlSessionFactoryBuilder().build(in);
        osf = new SqlSessionFactoryBuilder().build(in1,"oracleDS");
    }


    /**
     * 获取mysql的数据源
     * @return
     */
    public static SqlSession getSession(){
        SqlSession session = sf.openSession();

        return session;
    }
    /**
     * 获取oracle的数据源
     * @return
     */
    public static SqlSession getOracleSession(){
        SqlSession session = osf.openSession();

        return session;
    }

    public static void closeSession(SqlSession session){
        if(session!=null){
            session.close();
        }
    }

}
