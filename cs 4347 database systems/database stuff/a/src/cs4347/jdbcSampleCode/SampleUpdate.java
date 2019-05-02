package cs4347.jdbcSampleCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import cs4347.sakilaEntities.Customer;

public class SampleUpdate
{
	final static String updateSQL = "UPDATE customer SET first_name = ?, last_name = ?, create_date = ?, email = ?, active = ?, store_id = ?, address_id = ? "
	        + "WHERE customer_id = ?;";

	public int update(Connection connection, Customer customer) throws SQLException, DAOException
	{
		if (customer.getCustomerId() == null) {
			throw new DAOException("Trying to update Customer with NULL ID");
		}

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(updateSQL);
			ps.setString(1, customer.getFirstName());
			ps.setString(2, customer.getLastName());
			ps.setDate(3, customer.getCreateDate());
			ps.setString(4, customer.getEmail());
			ps.setInt(5, customer.getActive());
			ps.setLong(6, customer.getStoreId());
			ps.setLong(7, customer.getAddressId());
			ps.setLong(8, customer.getCustomerId());

			int rows = ps.executeUpdate();
			return rows;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	public static void main(String args[])
	{
		SampleUpdate app = new SampleUpdate();
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
			SampleSingleSelect ssSelect = new SampleSingleSelect();
			Customer c1 = ssSelect.retrieve(connection, id);
	
			int updated = update(connection, c1);
			System.out.println("Rows Updated " + updated);
		}
		finally {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}
}