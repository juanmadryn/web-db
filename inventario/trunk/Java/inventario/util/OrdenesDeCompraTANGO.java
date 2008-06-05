package inventario.util;

import infraestructura.models.WebsiteUserModel;
import inventario.models.DetalleSCModel;
import inventario.models.OrdenesCompraModel;
import inventario.models.SolicitudCompraModel;

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
		
		DetalleSCModel dsDetalleSc = new DetalleSCModel("inventario");
		SolicitudCompraModel solicitudCompraModel = new SolicitudCompraModel("inventario");
		WebsiteUserModel websiteUserModel = new WebsiteUserModel("infraestructura");

		try {
			// Se carga el driver JTDS
			Class.forName(driverTango);
			// Conexion con Tango (SQL Server 2000)
			connTango = DriverManager.getConnection(urlTango, userTango,
					passWordTango);
			connTango.setAutoCommit(false);

			int COD_LISTA = 0;
			
			/*
			 * El codigo de comprador se corresponde con el nro. de legajo en tango del usuario
			 */
			dsDetalleSc.setGroupBy(
					DetalleSCModel.SOLICITUDES_COMPRA_USER_ID_SOLICITA + ", " +
					DetalleSCModel.DETALLE_SC_SOLICITUD_COMPRA_ID);
			dsDetalleSc.retrieve(DetalleSCModel.DETALLE_SC_ORDEN_COMPRA_ID + " = " + oc.getOrdenesCompraOrdenCompraId());
			dsDetalleSc.waitForRetrieve();
			
			// Pueden existir multiples solicitantes. Se selecciona el primero.
			dsDetalleSc.gotoFirst();
			
			/*
			 * Obtenemos el nro. de legajo del usuario solicitante
			 */			
			websiteUserModel.retrieve(WebsiteUserModel.WEBSITE_USER_USER_ID + " = " + dsDetalleSc.getSolicitudesCompraUserIdSolicita());
			websiteUserModel.waitForRetrieve();
			websiteUserModel.gotoFirst();
						
			String COD_COMPRA = String.valueOf(websiteUserModel.getWebsiteUserNroLegajo());

			/*
			 * Se guarda el apellido del usuario que haya generado la orden de compra
			 * El apellido se obtiene de la tabla de legajos de Tango
			 */
			websiteUserModel.retrieve(WebsiteUserModel.WEBSITE_USER_USER_ID + " = " + oc.getOrdenesCompraUserIdComprador() );
			websiteUserModel.waitForRetrieve();
			websiteUserModel.gotoFirst();

			String AUTORIZO = websiteUserModel.getLegajoApellido();

			/*
			 * El codigo de proveedor es importado desde tango en la columna
			 * codigo de la entidad externa con rol proveedor correspondiente 
			 */
			String COD_PROVEE = oc.getEntidadExternaCodigo();

			/*
			 * El código de la condición de compra se importa desde tanto en
			 * la columna nombre de CondicionesCompraModel
			 */
			int COND_COMPR = Integer.parseInt(oc.getOrdenesCompraCondicionNombre());
			
			char CONGELA = 0;
			float COTIZ = 3.0f;
			int ESTADO = ESTADO_EMITIDA;
			char EXPORTADO = 0;

			/*
			 * Tango setea la fecha de autorizacion a 1800-01-01 00:00:00.00 las
			 * ordenes de compra en estado "Generado".
			 */
			Timestamp FEC_AUTORI = oc.getOrdenesCompraFechaAprobacion();

			/*
			 * El modelo de Ordenes de Compra no tiene en el momento de escribir estas
			 * lineas una columna para indicar la fecha de emision. Se setea temporalmente
			 * como fecha de emision la fecha en que se ejecuta este codigo
			 */
			Timestamp FEC_EMISIO = new Timestamp(System.currentTimeMillis());

			/*
			 * Fecha de generación
			 */
			Timestamp FEC_GENER = oc.getOrdenesCompraFecha();

			/*
			 * La fecha de vigencia de la orden de compra se setea por defecto
			 * en un mes a partir de la fecha de emisión de esta
			 */
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Timestamp(System.currentTimeMillis()));
			calendar.add(Calendar.MONTH, 1);
			Timestamp FEC_VIGENC = new Timestamp(calendar.getTimeInMillis());

			/*
			 * Tango almacena la hora de autorizacion como un string con el formato HHmm
			 */
			String HORA_AUTOR = null;
			SimpleDateFormat dateFormat = new SimpleDateFormat("HHmm");
			if (oc.getOrdenesCompraFechaAprobacion() != null) {
				HORA_AUTOR = dateFormat.format(oc.getOrdenesCompraFechaAprobacion());
			} else
				HORA_AUTOR = dateFormat.format(new Timestamp(System.currentTimeMillis()));

			String INC_II = "N";
			String INC_IVA = "N";

			/*
			 * Recuperamos primero los detalles de SM asociadas con la OC actual
			 * agrupados por solicitud de compra
			 */
			dsDetalleSc.setGroupBy(DetalleSCModel.DETALLE_SC_SOLICITUD_COMPRA_ID);
			dsDetalleSc.retrieve(DetalleSCModel.DETALLE_SC_ORDEN_COMPRA_ID + " = " + oc.getOrdenesCompraOrdenCompraId());			
			dsDetalleSc.waitForRetrieve();			
			/*
			 * Recuperamos la solicitud de compra del primer detalle.
			 */			
			dsDetalleSc.gotoFirst();
			solicitudCompraModel.retrieve(SolicitudCompraModel.SOLICITUDES_COMPRA_SOLICITUD_COMPRA_ID + " = " + dsDetalleSc.getDetalleScSolicitudCompraId());
			solicitudCompraModel.waitForRetrieve();			
			/*
			 * En TANGO se setea el campo con OT + nro. proyecto + descripcion del proyecto 
			 */
			solicitudCompraModel.gotoFirst();
			String LEYENDA_1 = "OT " + solicitudCompraModel.getProyectosProyecto() + " " + solicitudCompraModel.getProyectosNombre();
			
			/*
			 * La 2da leyende hace igual a null temporalmente 
			 */
			String LEYENDA_2 = "";
			
			char MONTO_CTE = 1;
			
			/*
			 * Consultar este metodo para ver como se genera la recuperacion
			 */
			String N_ORDEN_CO = this.recuperaProximo(connTango);
			
			int NRO_SUCURS = 0;
			
			/*
			 * El campo de observaciones 
			 */
			String OBSERVACIO = oc.getOrdenesCompraObservaciones();

			/*
			 * Se recupera el bucket de la OC correspondiente a el porcentaje bonificado
			 */
			float PORC_BONIF = oc.getOrdenesCompraDescuento();
			
			/*
			 * Talonario para OCs en Tango es 11
			 */
			int TALONARIO = 11;
			
			/*
			 * Se recupera el bucket de la OC correspondiente a el monto descontado
			 */
			float TOTAL_BONI = oc.getDescuentoOrdenCompra();
			
			/*
			 * Al parecer en Tango este valor es siempre seteado a 0
			 */
			float TOTAL_II = 0.0f;
			
			/*
			 * Se recupera el bucket de la OC correspondiente a el porcentaje de iva
			 */
			float TOTAL_IVA = oc.getIvaOrdenCompra();

			pstTango = connTango
					.prepareStatement("INSERT INTO [FABRI_S.A.].[dbo].[CPA35]([AUTORIZO], [COD_COMPRA], [COD_LISTA], [COD_PROVEE], [COND_COMPR], [CONGELA], [COTIZ], [ESTADO], [EXPORTADO], [FEC_AUTORI], [FEC_EMISIO], [FEC_GENER], [FEC_VIGENC], [HORA_AUTOR], [INC_II], [INC_IVA], [LEYENDA_1], [LEYENDA_2], [MON_CTE], [N_ORDEN_CO], [NRO_SUCURS], [OBSERVACIO], [PORC_BONIF], [TALONARIO], [TOTAL_BONI], [TOTAL_II], [TOTAL_IVA]) "
							+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstTango.setString(1, AUTORIZO);
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
			pstTango.setString(18, LEYENDA_2);
			pstTango.setInt(19, MONTO_CTE);
			pstTango.setString(20, N_ORDEN_CO);
			pstTango.setInt(21, NRO_SUCURS);
			pstTango.setString(22, OBSERVACIO);
			pstTango.setFloat(23, PORC_BONIF);
			pstTango.setInt(24, TALONARIO);
			pstTango.setFloat(25, TOTAL_BONI);
			pstTango.setFloat(26, TOTAL_II);
			pstTango.setFloat(27, TOTAL_IVA);

			// pstTango.execute();			
			
			// Para debug
			System.out.println(AUTORIZO);
			System.out.println(COD_COMPRA);
			System.out.println(COD_LISTA);
			System.out.println(COD_PROVEE);
			System.out.println(COND_COMPR);
			System.out.println(String.valueOf(CONGELA));
			System.out.println(COTIZ);
			System.out.println(ESTADO);
			System.out.println(String.valueOf(EXPORTADO));
			System.out.println(FEC_AUTORI);
			System.out.println(FEC_EMISIO);
			System.out.println(FEC_GENER);
			System.out.println(FEC_VIGENC);
			System.out.println(HORA_AUTOR);
			System.out.println(INC_II);
			System.out.println(INC_IVA);
			System.out.println(LEYENDA_1);
			System.out.println(LEYENDA_2);
			System.out.println(String.valueOf(MONTO_CTE));
			System.out.println(N_ORDEN_CO);
			System.out.println(NRO_SUCURS);
			System.out.println(OBSERVACIO);
			System.out.println(PORC_BONIF);
			System.out.println(TALONARIO);
			System.out.println(TOTAL_BONI);
			System.out.println(TOTAL_II);
			System.out.println(TOTAL_IVA);
			
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
	
	private void insertaDetalleOC(OrdenesCompraModel oc, Connection conn)
			throws SQLException, DataStoreException {
		
		Statement tangoSt = null;
		PreparedStatement pstTango = null;
		ResultSet r = null;

		DetalleSCModel dsDetalleSc = new DetalleSCModel("inventario");
		SolicitudCompraModel solicitudCompraModel = new SolicitudCompraModel("inventario");
		WebsiteUserModel websiteUserModel = new WebsiteUserModel("infraestructura");

		try {
			
		} finally {
			if (r != null)
				r.close();
			if (tangoSt != null)
				tangoSt.close();
			if (pstTango != null)
				pstTango.close();			
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
				/*
				 * tangoSt2 .executeUpdate("UPDATE [FABRI_S.A.].[dbo].[CPA56]
				 * SET PROXIMO = '" + format.format((Integer.parseInt(r
				 * .getString(2)) + 1)) + "' WHERE TALONARIO = 11");
				 */
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
