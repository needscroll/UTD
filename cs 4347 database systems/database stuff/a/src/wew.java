import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class wew {
	
	public static void main(String[] args) throws SQLException, ClassNotFoundException
	{
		System.out.println(4);
		MysqlDataSource ds = new MysqlDataSource();
		ds.setURL("jdbc:mysql://localhost:3306");
		ds.setUser("james");
		ds.setPassword("guoheng");
		Connection connection = ds.getConnection();
	}

}
