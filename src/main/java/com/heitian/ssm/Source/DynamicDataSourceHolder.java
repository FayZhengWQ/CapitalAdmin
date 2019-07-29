package com.heitian.ssm.Source;

/**
 * @program: CapitalAdmin
 * @description:    数据源标识保存在线程变量中，避免多线程操作数据源时互相干扰
 * @author: liangsizhuuo@163.com
 * @create: 2018-11-08 11:38
 **/
public class DynamicDataSourceHolder {
    private static final ThreadLocal<String> THREAD_DATA_SOURCE = new ThreadLocal<String>();

    public static String getDataSource() {
        return THREAD_DATA_SOURCE.get();
    }

    public static void setDataSource(String dataSource) {
        THREAD_DATA_SOURCE.set(dataSource);
    }

    public static void clearDataSource() {
        THREAD_DATA_SOURCE.remove();
    }
}
