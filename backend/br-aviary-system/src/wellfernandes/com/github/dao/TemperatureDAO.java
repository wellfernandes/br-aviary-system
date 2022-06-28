package wellfernandes.com.github.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import wellfernandes.com.github.bean.TemperatureBEAN;
import wellfernandes.com.github.util.ConnectionFactory;

public class TemperatureDAO {

	// sql insert into database
	public boolean insert(TemperatureBEAN temperaturaBean) {

		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;

		try {
			stmt = con.prepareStatement("INSERT INTO tb_temperature (temperature_value) VALUES (?)");
			stmt.setFloat(1, temperaturaBean.gettemperatureValue());
			stmt.executeUpdate();
			System.out.println("temperature saved successfully.");
			return true;
		} catch (SQLException e) {
			System.err.println("error saving temperature.");
			return false;
		} finally {
			ConnectionFactory.closeConnection(con, stmt);
		}
	}
}
