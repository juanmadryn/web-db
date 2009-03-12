package gerencia.util;

import java.sql.SQLException;
import java.sql.Statement;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;

/**
 * Extrae información de proveedores de la tabla CPA01 de Tango y la agrega o
 * actualiza en la tabla entidad_externa y en los atributos corresponientes.
 * 
 * @author Francisco Paez
 */
public class CargaTablaMaterialCompradoQuartzJob implements Job {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			this.cargaTablaMaterialComprado();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @throws JobExecutionException
	 * @throws SQLException
	 * @throws DataStoreException
	 */
	public void cargaTablaMaterialComprado() throws JobExecutionException, SQLException, DataStoreException {
			DBConnection conn = null;
			Statement st = null;
			
			try {
			conn = DBConnection.getConnection("gerencia", "gerencia");
			st = conn.createStatement();
			st.execute("call cargaTablaMaterialComprado();");
			} catch(SQLException e) {
				if(conn != null) {
					conn.freeConnection();
				}
				if(st != null) {
					st.close();
				}
				
			}
	}}
