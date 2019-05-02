package cs4347.jdbcSampleCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SampleDelete
{
	private Connection connection;

	public SampleDelete(Connection connection)
	{
		this.connection = connection;
	}

	final static String deleteSQL = "DELETE FROM CUSTOMER WHERE ID = ?;";

	public int delete(Long id) throws SQLException, DAOException
	{
		if (id == null) {
			throw new DAOException("Trying to delete Customer with NULL ID");
		}

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(deleteSQL);
			ps.setLong(1, id);

			int rows = ps.executeUpdate();
			return rows;
		}
		finally {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		}
	}
}