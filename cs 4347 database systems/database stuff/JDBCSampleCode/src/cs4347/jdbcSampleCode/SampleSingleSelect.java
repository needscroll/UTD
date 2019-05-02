package cs4347.jdbcSampleCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import cs4347.sakilaEntities.Customer;

public class SampleSingleSelect
{
	final static String selectQuery = "SELECT customer_id, first_name, last_name, create_date, email, store_id, address_id "
	        + "FROM customer where customer_id = ?";

	public Customer retrieve(Connection connection, Long id) throws SQLException, DAOException
	{
		if (id == null) {
			throw new DAOException("Trying to retrieve Customer with NULL ID");
		}

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(selectQuery);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return null;
			}

			Customer cust = new Customer();
			cust.setCustomerId(rs.getLong("customer_id"));
			cust.setFirstName(rs.getString("first_name"));
			cust.setLastName(rs.getString("last_name"));
			cust.setEmail(rs.getString("email"));
			cust.setStoreId(rs.getLong("store_id"));
			cust.setAddressId(rs.getLong("address_id"));
			cust.setCreateDate(rs.getDate("create_date"));
			return cust;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	public static void main(String args[])
	{
		SampleSingleSelect app = new SampleSingleSelect();
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
			long id = 1;
			Customer c = retrieve(connection, id);
			System.out.println(c);
		}
		finally {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}
}
