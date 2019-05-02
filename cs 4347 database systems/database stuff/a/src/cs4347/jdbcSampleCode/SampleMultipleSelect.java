package cs4347.jdbcSampleCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import cs4347.sakilaEntities.Customer;

public class SampleMultipleSelect
{
	final static String storeQuery = 
			"SELECT customer_id, first_name, last_name, active, create_date, email, store_id, address_id "
			+ "FROM customer where store_id = ?";

	public List<Customer> retrieveByStoreID(Connection connection, int storeID) throws SQLException, DAOException
	{
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(storeQuery);
			ps.setInt(1, storeID);
			ResultSet rs = ps.executeQuery();

			List<Customer> result = new ArrayList<Customer>();
			while (rs.next()) {
				Customer cust = new Customer();
				cust.setCustomerId(rs.getLong("customer_id"));
				cust.setFirstName(rs.getString("first_name"));
				cust.setLastName(rs.getString("last_name"));
				cust.setActive(rs.getInt("active"));
				cust.setEmail(rs.getString("email"));
				cust.setStoreId(rs.getLong("store_id"));
				cust.setAddressId(rs.getLong("address_id"));
				cust.setCreateDate(rs.getDate("create_date"));
				result.add(cust);
			}
			return result;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	public static void main(String args[])
	{
		SampleMultipleSelect app = new SampleMultipleSelect();
		try {
			app.run();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void run() throws Exception
	{
		DataSource dataSource = DataSourceManager.getDataSource();
		Connection connection = dataSource.getConnection();
		try {
			int storeID = 1;
			List<Customer> result = retrieveByStoreID(connection, storeID);
			System.out.println(result.size());
		}
		finally {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}
}