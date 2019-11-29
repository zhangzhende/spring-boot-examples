package com.zzd.spark.ebrealtimestreamprocessing.java;

import java.sql.*;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Description 说明类的用途
 * @ClassName JDBCWrapper
 * @Author zzd
 * @Date 2019/10/8 20:50
 * @Version 1.0
 **/
public class JDBCWrapper {
    private static LinkedBlockingQueue<Connection> dbConnectionPool = new LinkedBlockingQueue<Connection>();

    private static JDBCWrapper jdbcWrapperInstance = null;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 单例模式，双重检查锁
     *
     * @return
     */
    public static JDBCWrapper getInstance() {
        if (jdbcWrapperInstance == null) {
            synchronized (JDBCWrapper.class) {
                if (jdbcWrapperInstance == null) {
                    jdbcWrapperInstance = new JDBCWrapper();
                }
            }
        }
        return jdbcWrapperInstance;
    }

    private JDBCWrapper() {
        for (int i = 0; i < 10; i++) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.30.29:3306/db_ad_analyzed?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true", "root", "root");
                dbConnectionPool.put(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取连接池里面的链接，如果没有就一直等待，直到有链接释放
     *
     * @return
     */
    public synchronized Connection getConnection() {
        while (0 == dbConnectionPool.size()) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return dbConnectionPool.poll();
    }


    public int[] doBatch(String sql, List<Object[]> paramList) {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        int[] result = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            for (Object[] parameters : paramList) {
                for (int i = 0; i < parameters.length; i++) {
                    preparedStatement.setObject(i + 1, parameters[i]);
                }
                preparedStatement.addBatch();
            }
            result = preparedStatement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    dbConnectionPool.put(connection);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public void doQuery(String sqlText, Object[] paramList, ExecuteCallBack callBack) {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sqlText);
            if (paramList != null) {
                for (int i = 0; i < paramList.length; i++) {
                    preparedStatement.setObject(i + 1, paramList[i]);
                }
            }
            resultSet = preparedStatement.executeQuery();
            callBack.resultCallBack(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    dbConnectionPool.put(connection);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
