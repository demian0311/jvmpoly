package fourthings.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.DataSourceConnectionFactory;
import org.junit.Test;

import javax.sql.DataSource;

/**
 * Play with DBCP connection pooling.
 */
public class Database {

    // jdbc:mysql://localhost/haka
    //             driverClassName = "com.mysql.jdbc.Driver"


    @Test
    public void testC3p0() throws Exception{
        System.out.println("hello world");

        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass( "org.postgresql.Driver" ); //loads the jdbc driver
        cpds.setJdbcUrl("jdbc:mysql://localhost/haka");
        cpds.setUser("haka");
        cpds.setPassword("akah");

        // the settings below are optional -- c3p0 can work with defaults
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
    }

    @Test public void test(){
        //DataSource ds = new BasicDataSource();
        //ds.setDriverClassName("");

        //DataSourceConnectionFactory dscf = new DataSourceConnectionFactory();
    }

}
