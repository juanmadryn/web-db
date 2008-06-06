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
import java.text.ParseException;
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
	
	/**
	 * Inserta la cabecera de la Orden de Compra en Tango. 
	 * 
	 * @param oc la Orden de Compra que se guardara en Tango
	 * @param proyecto 
	 * @throws SQLException
	 * @throws DataStoreException
	 * @throws ParseException 
	 */
	public void insertaCabeceraOC(OrdenesCompraModel oc)
			throws SQLException, DataStoreException, ParseException {
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
						
			DecimalFormat formatCodCompra = new DecimalFormat("00");		
			String COD_COMPRA = formatCodCompra.format(websiteUserModel.getWebsiteUserNroLegajo());			

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
			
			int CONGELA = 0;
			float COTIZ = 3.0f;
			int ESTADO = ESTADO_EMITIDA;
			int EXPORTADO = 0;

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

			/*
			 * Valores deducidos de Tango
			 */
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
			 * CODIGO PROTECTIVO
			 * Si la OC tiene detalles que corresponden a más de una SM, abortamos el proceso 
			 */
			if (dsDetalleSc.getRowCount() > 0) {
				// Error, cancelar proceso de exportacion
			}
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
			
			int MONTO_CTE = 1;
			
			/*
			 * Consultar este metodo para ver como se genera la recuperacion
			 */
			String N_ORDEN_CO = this.recuperaProximo(connTango);
			
			int NRO_SUCURS = 0;
			
			/*
			 * El campo de observaciones 
			 */
			String OBSERVACIO = oc.getOrdenesCompraObservaciones() == null ? oc.getObservaciones() : "";

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
			System.out.println("== CABECERA OC ==");
			System.out.println("AUTORIZO\t-->\t" + AUTORIZO);
			System.out.println("COD_COMPRA\t-->\t" + COD_COMPRA);
			System.out.println("COD_LISTA\t-->\t" + COD_LISTA);
			System.out.println("COD_PROVEE\t-->\t" + COD_PROVEE);
			System.out.println("COND_COMPR\t-->\t" + COND_COMPR);
			System.out.println("CONGELA \t-->\t" + CONGELA);
			System.out.println("COTIZ   \t-->\t" + COTIZ);
			System.out.println("ESTADO  \t-->\t" + ESTADO);
			System.out.println("EXPORTADO\t-->\t" + EXPORTADO);
			System.out.println("FEC_AUTORI\t-->\t" + FEC_AUTORI);
			System.out.println("FEC_EMISIO\t-->\t" + FEC_EMISIO);
			System.out.println("FEC_GENER\t-->\t" + FEC_GENER);
			System.out.println("FEC_VIGENC\t-->\t" + FEC_VIGENC);
			System.out.println("HORA_AUTOR\t-->\t" + HORA_AUTOR);
			System.out.println("INC_II  \t-->\t" + INC_II);
			System.out.println("INC_IVA \t-->\t" + INC_IVA);
			System.out.println("LEYENDA 1\t-->\t" + LEYENDA_1);
			System.out.println("LEYENDA 2\t-->\t" + LEYENDA_2);
			System.out.println("MONTO_CTE\t-->\t" + MONTO_CTE);
			System.out.println("N_ORDEN_CO\t-->\t" + N_ORDEN_CO);
			System.out.println("NRO_SUCURS\t-->\t" + NRO_SUCURS);
			System.out.println("OBSERVACIO\t-->\t" + OBSERVACIO);
			System.out.println("PORC_BONIF\t-->\t" + PORC_BONIF);
			System.out.println("TALONARIO\t-->\t" + TALONARIO);
			System.out.println("TOTAL_BONI\t-->\t" + TOTAL_BONI);
			System.out.println("TOTAL_II\t-->\t" + TOTAL_II);
			System.out.println("TOTAL_IVA\t-->\t" + TOTAL_IVA);
			
			insertaDetalleOC(oc, connTango, N_ORDEN_CO);
			
			connTango.rollback();

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
	
	/**
	 * Inserta renglones del detalle de la OC en la tabla CPA36
	 * Completa informacion extra para cada renglon en la tabla CPA37
	 * 
	 * @param oc La Orden de Compra de la cual se tomaran los detalles
	 * @param conn La conexión en la cual se enmarca la transacción
	 * @throws SQLException
	 * @throws DataStoreException
	 * @throws ParseException 
	 */
	private void insertaDetalleOC(OrdenesCompraModel oc, Connection conn, String nroOcTango)
			throws SQLException, DataStoreException, ParseException {
		
		Statement tangoSt = null;
		PreparedStatement pstTango = null;
		ResultSet r = null;

		DetalleSCModel dsDetalleSc = new DetalleSCModel("inventario");
		
		try {
			dsDetalleSc.retrieve(
					DetalleSCModel.DETALLE_SC_ORDEN_COMPRA_ID + " = " 
					+ oc.getOrdenesCompraOrdenCompraId());			
			dsDetalleSc.waitForRetrieve();
			
			for (int row = 0; row < dsDetalleSc.getRowCount(); row++) {
				// calcula totales de cada detalle
				dsDetalleSc.calculaPrecioUnitarioPam(row);
				dsDetalleSc.calculaMontoTotalPedido(row);
				dsDetalleSc.calculaMontoTotalNetoPedido(row);
				
				// Segun lo que se investigo, este valor no se setea
				String FILLER = "";

				// Cant. equivalente, por ahora se setea en 1
				Float CAN_EQUIVA = 1.0F;

				// Cant. pedida
				Float CAN_PEDIDA = dsDetalleSc.getDetalleScCantidadPedida(row);

				// Cant. pendiente
				Float CAN_PENDIE = CAN_PEDIDA;

				// Cant. recibida
				Float CAN_RECIBI = dsDetalleSc.getDetalleScCantidadRecibida(row);

				/*
				 * Valor para Ordenes de Compra emitida = 0;
				 * Valor para Ordenes de Compra anulada = 1;
				 */
				int CIERRE = 0;

				// Codigo del Articulo
				String COD_ARTICU = dsDetalleSc.getArticulosNombre(row);

				/*
				 * Valores posibles: 01, 03
				 * 01 es el Almacen gral.
				 */
				String COD_DEPOSI = "01";
				
				/*
				 * Valores posibles: UC, US
				 * US es el valor por defecto
				 */
				String COD_PRE_CO = "US";

				// Valor por defecto
				Float IMP_INT = 0.0F;

				// Nro. de Orden de Compra
				String N_ORDEN_CO = nroOcTango;

				// Nro. del renglon
				int N_RENGL_OC = row + 1;

				// Porcentaje de Descuento
				Float PORC_DESC = dsDetalleSc.getDetalleScDescuento(row);
								
				Float PRECIO = dsDetalleSc.getMontoUnitarioPam(row);
				
				Float PRECIO_PAN = dsDetalleSc.getDetalleScMontoUnitario(row);
				
				// Para debug
				System.out.println("== DETALLE " + N_RENGL_OC + " OC " + N_ORDEN_CO + " ==");
				System.out.println("FILLER  \t-->\t" + FILLER);
				System.out.println("CAN_EQUIVA\t-->\t" + CAN_EQUIVA);
				System.out.println("CAN_PEDIDA\t-->\t" + CAN_PEDIDA);
				System.out.println("CAN_PENDIE\t-->\t" + CAN_PENDIE);
				System.out.println("CAN_RECIBI\t-->\t" + CAN_RECIBI);
				System.out.println("CIERRE  \t-->\t" + CIERRE);
				System.out.println("COD_ARTICU\t-->\t" + COD_ARTICU);
				System.out.println("COD_DEPOSI\t-->\t" + COD_DEPOSI);
				System.out.println("COD_PRE_CO\t-->\t" + COD_PRE_CO);
				System.out.println("IMP_INT  \t-->\t" + IMP_INT);
				System.out.println("N_ORDEN_CO\t-->\t" + N_ORDEN_CO);
				System.out.println("N_RENGL_OC\t-->\t" + N_RENGL_OC);
				System.out.println("PORC_DESC\t-->\t" + PORC_DESC);
				System.out.println("PRECIO  \t-->\t" + PRECIO);
				System.out.println("PRECIO_PAN\t-->\t" + PRECIO_PAN);
				
				/*
				 * ===========================================================================
				 * Tabla CPA37
				 * ===========================================================================
				 * 
				 */
				String CPA37_FILLER = "";
				Float CPA37_CANTIDAD = CAN_PEDIDA;
				Timestamp CPA37_FEC_RECEPC = new Timestamp(oc.getOrdenesCompraFechaEstimadaEntrega().getTime());
				String CPA37_N_ORDEN_CO = N_ORDEN_CO;
				int CPA37_N_RENGL_OC = N_RENGL_OC;
				
				// Para debug
				System.out.println("== CPA37 ==");
				System.out.println("FILLER  \t-->\t" + CPA37_FILLER);
				System.out.println("CANTIDAD\t-->\t" + CPA37_CANTIDAD);
				System.out.println("FEC_RECEPC\t-->\t" + CPA37_FEC_RECEPC);
				System.out.println("N_ORDEN_CO\t-->\t" + CPA37_N_ORDEN_CO);
				System.out.println("N_RENGL_OC\t-->\t" + CPA37_N_RENGL_OC);
			}
		} finally {
			if (r != null)
				r.close();
			if (tangoSt != null)
				tangoSt.close();
			if (pstTango != null)
				pstTango.close();			
		} 
	}

	/**
	 * Recupera el próximo número la orden de compra
	 * 
	 * @param connTango
	 * @return
	 * @throws SQLException
	 */
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
				System.out.println("Proximo nro. a insertar en cpa56 : " + format.format((Integer.parseInt(r.getString(2)) + 1)));
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
