package cs4347.jdbcSampleCode;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * This is a service which maintains a Singleton instance of the MySQL
 * DataSource. Application use the static method getDataSource() to obtain an
 * open connection to the MySQL server. The DBMS connection parameters (url, id,
 * and password) is maintained in a property file 'dbconfig.properties'. The
 * property file must be located on the application's CLASSPATH. See the
 * configuration property file is loaded by the method
 * getPropertiesFromClasspath().
 */
public class DataSourceManager
{
	private static DataSource dataSource;

	/**
	 * Lazy initialize the datasource singleton
	 */
	public synchronized static DataSource getDataSource() throws IOException
	{
		if (dataSource == null) {

			MysqlDataSource ds = null;
			Properties props = getPropertiesFromClasspath();
			String url = props.getProperty("url");
			if (url == null || url.isEmpty()) {
				throw new RuntimeException("property 'url' not found in configuration file");
			}
			String id = props.getProperty("id");
			if (id == null || id.isEmpty()) {
				throw new RuntimeException("property 'id' not found in configuration file");
			}
			String passwd = props.getProperty("passwd");
			if (passwd == null || passwd.isEmpty()) {
				throw new RuntimeException("property 'passwd' not found in configuration file");
			}

			ds = new MysqlDataSource();
			ds.setURL(url);
			ds.setUser(id);
			ds.setPassword(passwd);
			dataSource = ds;
		}
		return dataSource;
	}

	private static final String propFileName = "dbconfig.properties";

	public static Properties getPropertiesFromClasspath() throws IOException
	{
		// Load dbconfig.properties from the classpath
		InputStream inputStream = DataSourceManager.class.getClassLoader().getResourceAsStream(propFileName);
		if (inputStream == null) {
			throw new IOException("property file '" + propFileName + "' not found in the classpath");
		}

		Properties props = new Properties();
		props.load(inputStream);
		return props;
	}
}
