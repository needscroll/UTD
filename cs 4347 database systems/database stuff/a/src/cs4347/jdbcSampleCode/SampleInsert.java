package cs4347.jdbcSampleCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import cs4347.sakilaEntities.Customer;

/**
 * This class provides an example of the error checking and the method of
 * obtaining and assigning the auto-increment primary key that was assigned to
 * the Customer when it was inserted into the DBMS. 
 */
public class SampleInsert
{
	private static final String insertSQL = 
			"INSERT INTO CUSTOMER (first_name, last_name, create_date, email, active, store_id, address_id) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?);";

	public Customer create(Connection connection, Customer customer) throws SQLException, DAOException
	{
		// Requirement: Create operations require that the customer's ID is null
		// before being inserted into the table.
		if (customer.getCustomerId() != null) {
			throw new DAOException("Trying to insert Customer with NON-NULL ID");
		}

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, customer.getFirstName());
			ps.setString(2, customer.getLastName());
			ps.setDate(3, customer.getCreateDate());
			ps.setString(4, customer.getEmail());
			ps.setInt(5, customer.getActive());
			ps.setLong(6, customer.getStoreId());
			ps.setLong(7, customer.getAddressId());

			int res = ps.executeUpdate();
			if(res != 1) {
				throw new DAOException("Create Did Not Update Expected Number Of Rows");
			}

			// REQUIREMENT: Copy the generated auto-increment primary key to the
			// customer ID.
			ResultSet keyRS = ps.getGeneratedKeys();
			keyRS.next();
			int lastKey = keyRS.getInt(1);
			customer.setCustomerId((long) lastKey);

			return customer;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}

	public static void main(String args[])
	{
		SampleInsert app = new SampleInsert();
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
			System.out.println("c1 " + c1.getCustomerId());
			
			c1.setCustomerId(null);
			Customer c2 = create(connection, c1);
			System.out.println("c2 " + c1.getCustomerId());
		}
		finally {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
	}
}