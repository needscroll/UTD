package cs4347.jdbcSampleCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import cs4347.sakilaEntities.Customer;

/**
 * This example passes the connection (instead of obtaining from DataSource because the same 
 * connection will be used in multiple SQL operations, each of which will be participating 
 * in the same transaction. 
 * This function will return the same Customer, but with the ID set to the value assigned
 * by the DBMS and the Auto-Increment column on the Customer table. 
 */
public class TransactionalInsert
{
	private static final String insertSQL = 
			"INSERT INTO CUSTOMER (first_name, last_name, create_date, email, active, store_id, address_id) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?);";
	
	/**
	 * Note: We are passing an open connection (Not a DataSource)
	 */
	public Customer create(Connection connection, Customer customer) throws SQLException, DAOException
	{
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

			// REQUIREMENT: Copy the generated auto-increment primary key to the customer ID.
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
		TransactionalInsert app = new TransactionalInsert();
		try {
			app.run();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void run() throws Exception
	{
		DataSource dataSource = DataSourceManager.getDataSource();
		Connection connection = dataSource.getConnection();
		connection.setAutoCommit(false); // *****
		try {
			long id = 1;
			SampleSingleSelect ssSelect = new SampleSingleSelect();
			Customer c1 = ssSelect.retrieve(connection, id);
			System.out.println("c1 " + c1.getCustomerId());

			c1.setCustomerId(null);
			Customer customer1 = create(connection, c1);
			System.out.println("Customer 1 ID: " + customer1.getCustomerId());
			
			c1.setCustomerId(null);
			//c1.setAddressId(null); // This will trigger an SQLException.
			Customer customer2 = create(connection, c1);
			System.out.println("Customer 2 ID: " + customer2.getCustomerId());

			connection.commit();
		}
		catch (Exception ex) {
			// Rollback will set AutoCommit back true.
			// See: http://stackoverflow.com/questions/3160756/in-jdbc-when-autocommit-is-false-and-no-explicit-savepoints-have-been-set-is-i
			connection.rollback(); 
			throw ex;
		}
		finally {
			if (connection != null && !connection.isClosed()) {
				connection.setAutoCommit(true);
				connection.close();
			}
		}
	}
}