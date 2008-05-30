package inventario.util;

import inventario.models.OrdenesCompraModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.salmonllc.sql.DataStoreException;

/**
 * Extrae información de proveedores de la tabla CPA01 de Tango y la agrega o
 * actualiza en la tabla entidad_externa y en los atributos corresponientes.
 * 
 * @author Francisco Paez
 * 
 */
public class OrdenesDeCompraTANGO {
	/**
	 * 
	 */
	private String driverTango = "net.sourceforge.jtds.jdbc.Driver";
	private String urlTango = "jdbc:jtds:sqlserver://SERV-FABRI/FABRI_S.A.;instance=MSDE_AXOFT";
	private String userTango = "Axoft";
	private String passWordTango = "Axoft";
	private int ESTADO_EMITIDA = 3;

	public void insertaCabeceraOC(OrdenesCompraModel oc, String proyecto)
			throws SQLException, DataStoreException {
		Connection connTango = null;
		Statement tangoSt = null;
		PreparedStatement pstTango = null;
		ResultSet r = null;

		try {
			// Se carga el driver JTDS
			Class.forName(driverTango);
			// Conexion con Tango (SQL Server 2000)
			connTango = DriverManager.getConnection(urlTango, userTango,
					passWordTango);
			connTango.setAutoCommit(false);

			int COD_LISTA = 0;
			String COD_COMPRA = "01";
			
			String AUTORIZO = oc.getWebsiteUserNombreComprador(); 
			
			// TODO
			String COD_PROVEE = oc.getEntidadExternaCodigo();
			
			// TODO
			int COND_COMPR = Integer.parseInt(oc
					.getOrdenesCompraCondicionNombre());
			char CONGELA = 0;
			float COTIZ = 3.0f;
			int ESTADO = ESTADO_EMITIDA;
			char EXPORTADO = 0;
			
			// TODO
			Timestamp FEC_AUTORI = oc.getOrdenesCompraFechaAprobacion();
			
			// TODO
			Timestamp FEC_EMISIO = new Timestamp(System.currentTimeMillis());
			
			// TODO
			Timestamp FEC_GENER = oc.getOrdenesCompraFecha();
			
			// TODO
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Timestamp(System.currentTimeMillis()));
			calendar.add(Calendar.MONTH, 1);
			Timestamp FEC_VIGENC = new Timestamp(calendar.getTimeInMillis());
			
			// TODO
			String HORA_AUTOR = null;
			SimpleDateFormat dateFormat = new SimpleDateFormat("HHmm");
			if (oc.getOrdenesCompraFechaAprobacion() != null) {
				HORA_AUTOR = dateFormat.format(oc.getOrdenesCompraFechaAprobacion());
			} else
				HORA_AUTOR = dateFormat.format(new Timestamp(System.currentTimeMillis()));

			String INC_II = "N";
			String INC_IVA = "N";
			// TODO
			String LEYENDA_1 = proyecto;
			// TODO
			String LEYENDA_2 = null;
			char MONTO_CTE = 1;
			// TODO
			String N_ORDEN_CO = this.recuperaProximo(connTango);
			int NRO_SUCURS = 0;
			// TODO
			String OBSERVACIO = oc.getOrdenesCompraObservaciones();
			// TODO
			float PORC_BONIF = oc.getOrdenesCompraDescuento();
			int TALONARIO = 11;
			// TODO
			float TOTAL_BONI = oc.getDescuentoOrdenCompra();
			// TODO
			float TOTAL_II = 0.0f;
			// TODO
			float TOTAL_IVA = oc.getIvaOrdenCompra();

			pstTango = connTango
					.prepareStatement("INSERT INTO [FABRI_S.A.].[dbo].[CPA35]([AUTORIZO], [COD_COMPRA], [COD_LISTA], [COD_PROVEE], [COND_COMPR], [CONGELA], [COTIZ], [ESTADO], [EXPORTADO], [FEC_AUTORI], [FEC_EMISIO], [FEC_GENER], [FEC_VIGENC], [HORA_AUTOR], [INC_II], [INC_IVA], [LEYENDA_1], [LEYENDA_2], [MON_CTE], [N_ORDEN_CO], [NRO_SUCURS], [OBSERVACIO], [PORC_BONIF], [TALONARIO], [TOTAL_BONI], [TOTAL_II], [TOTAL_IVA]) "
							+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstTango.setString(1, "VILLAHOZ");
			pstTango.setString(2, COD_COMPRA);
			pstTango.setInt(3, COD_LISTA);
			pstTango.setString(4, COD_PROVEE);
			pstTango.setInt(5, COND_COMPR);
			pstTango.setInt(6, CONGELA);
			pstTango.setFloat(7, COTIZ);
			pstTango.setInt(8, ESTADO);
			pstTango.setInt(9, EXPORTADO);
			pstTango.setTimestamp(10, FEC_AUTORI);
			pstTango.setTimestamp(11, FEC_EMISIO);
			pstTango.setTimestamp(12, FEC_GENER);
			pstTango.setTimestamp(13, FEC_VIGENC);
			pstTango.setString(14, HORA_AUTOR);
			pstTango.setString(15, INC_II);
			pstTango.setString(16, INC_IVA);
			pstTango.setString(17, LEYENDA_1);
			pstTango.setString(18, "");
			pstTango.setInt(19, MONTO_CTE);
			pstTango.setString(20, N_ORDEN_CO);
			pstTango.setInt(21, NRO_SUCURS);
			pstTango.setString(22, OBSERVACIO);
			pstTango.setFloat(23, PORC_BONIF);
			pstTango.setInt(24, TALONARIO);
			pstTango.setFloat(25, TOTAL_BONI);
			pstTango.setFloat(26, TOTAL_II);
			pstTango.setFloat(27, TOTAL_IVA);

			//pstTango.execute();
			connTango.commit();

		} catch (ClassNotFoundException e) {
			com.salmonllc.util.MessageLog.writeErrorMessage(e, this);
		} finally {
			if (r != null)
				r.close();
			if (tangoSt != null)
				tangoSt.close();
			if (pstTango != null)
				pstTango.close();
			if (connTango != null) {
				connTango.rollback();
				connTango.close();
			}
		}
	}

	private String recuperaProximo(Connection connTango) throws SQLException {
		Statement tangoSt = null;
		Statement tangoSt2 = null;
		ResultSet r = null;

		try {
			/**
			 * Seleccionamos de la tabla CPA01 los datos básicos para la tabla
			 * entidades_externas
			 */
			String proximaOCTangoSQL = "SELECT SUCURSAL, PROXIMO FROM [FABRI_S.A.].[dbo].[CPA56] WHERE TALONARIO = 11";

			tangoSt = connTango
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			tangoSt2 = connTango
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);

			r = tangoSt.executeQuery(proximaOCTangoSQL);

			if (r.first()) {
				DecimalFormat format = new DecimalFormat("00000000");
				/*tangoSt2
						.executeUpdate("UPDATE [FABRI_S.A.].[dbo].[CPA56] SET PROXIMO = "
								+ format.format((Integer.parseInt(r
										.getString(2)) + 1)) + " WHERE TALONARIO = 11");*/
				return " " + r.getString(1) + r.getString(2);
			}

			return null;
		} finally {
			if (r != null)
				r.close();
			if (tangoSt != null)
				tangoSt.close();
			if (tangoSt2 != null)
				tangoSt2.close();
		}
	}
}
