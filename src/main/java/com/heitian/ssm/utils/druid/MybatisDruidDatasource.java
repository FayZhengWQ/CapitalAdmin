package com.heitian.ssm.utils.druid;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.DataSourceFactory;

import java.sql.SQLException;
import java.util.Properties;

/**
 * @program: CapitalAdmin
 * @description:
 * @author: liangsizhuuo@163.com
 * @create: 2019-01-08 02:07
 **/
public class MybatisDruidDatasource implements DataSourceFactory {


    private Properties properties;

    @Override
    public javax.sql.DataSource getDataSource() {
        //创建druid数据源,这是druid jar包提供的一个类
        DruidDataSource ds = new DruidDataSource();
        //从配置好的properties加载配置
        ds.setUsername(this.properties.getProperty("username"));//用户名
        ds.setPassword(this.properties.getProperty("password"));//密码
        ds.setUrl(this.properties.getProperty("url"));
        ds.setDriverClassName(this.properties.getProperty("driver"));
        ds.setInitialSize(5);//初始连接数
        ds.setMaxActive(10);//最大活动连接数
        ds.setMaxWait(6000);//最大等待时间

        //初始化连接
        try {
            ds.init();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ds;
    }

    @Override
    public void setProperties(Properties properties) {
        // xml文档会将properties注入进来
        this.properties=properties;
    }
}
