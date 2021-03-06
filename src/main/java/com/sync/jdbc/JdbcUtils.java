package com.sync.jdbc;

import com.sync.jdbc.datasource.MyDataSource;
import com.sync.jdbc.datasource.MyDataSource2;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

/**
 * Created by Administrator on 2016/11/1 0001.
 */
public final class JdbcUtils {

//  private static String url = "jdbc:mysql://localhost:3306/jdbc?generateSimpleParameterMetadata=true";
//  private static String user = "root";
//  private static String password = "root";

  private static DataSource myDataSource = null;

  private JdbcUtils() {
    // no instance
  }

  static {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      //myDataSource = new MyDataSource2();
      Properties prop = new Properties();
      //prop.setProperty("driverClassName","com.mysql.jdbc.Driver");
      //prop.setProperty("url","jdbc:mysql://localhost:3306/test");
      InputStream is = JdbcUtils.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
      prop.load(is);
      myDataSource = BasicDataSourceFactory.createDataSource(prop);
    } catch (Exception e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  public static DataSource getDataSource() {
    return myDataSource;
  }

  public static Connection getConnection() throws SQLException {
    //return DriverManager.getConnection(url, user, password);
    return myDataSource.getConnection();
  }

  public static void free(ResultSet rs, Statement st, Connection conn) {
    try {
      if (rs != null) {
        rs.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (st != null) {
          st.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        if (conn != null) {
          try {
            conn.close();
            //myDataSource.free(conn);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }
  }
}
