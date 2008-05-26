/**
 *
 */
package inventario.reglasNegocio;

import infraestructura.controllers.Constants;
import infraestructura.models.UsuarioRolesModel;
import infraestructura.reglasNegocio.ValidadorReglasNegocio;
import inventario.models.ComprobanteMovimientoArticuloModel;
import inventario.models.ConversionesModel;
import inventario.models.MovimientoArticuloModel;
import inventario.models.ResumenSaldoArticulosModel;

import java.sql.SQLException;
import java.sql.Statement;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;

/**
 * @author Francisco
 * 
 * Regla de negocio asociada al rechazo de una OC
 * 
 */
public final class ValRN_0217_1 extends ValidadorReglasNegocio implements
		Constants {

	/*
	 * (non-Javadoc)
	 * 
	 * @see infdev.reglasNegocio.validadorReglasNegocio#esValido(java.lang.Object,
	 *      java.lang.String)
	 */
	public boolean esValido(Object obj, StringBuilder msg, DBConnection conn) {
		Statement st = null;
		try {
			ComprobanteMovimientoArticuloModel ds = (ComprobanteMovimientoArticuloModel) obj;
			
			if(!UsuarioRolesModel.isRolUsuario(ds.getCurrentWebsiteUserId(), USER_ENCARGADO_ALMACEN))
				throw new DataStoreException("Usted no está autorizado para anular movimientos.");
			
			MovimientoArticuloModel movimientos = new MovimientoArticuloModel(
					"inventario");
			ResumenSaldoArticulosModel resumenes = new ResumenSaldoArticulosModel(
					"inventario");
			resumenes.setOrderBy("resumen_saldo_articulos.periodo DESC");

			movimientos
					.retrieve("movimiento_articulo.comprobante_movimiento_id ="
							+ ds
									.getComprobanteMovimientoArticuloComprobanteMovimientoId());

			int almacen_id = ds.getComprobanteMovimientoArticuloAlmacenId();
			int articulo_id;
			double cantidad_pedida;
			double cantidad_en_proceso;

			for (int row = 0; row < movimientos.getRowCount(); row++) {
				articulo_id = movimientos.getMovimientoArticuloArticuloId(row);
				cantidad_pedida = ConversionesModel.getUnidadConvertida(
						articulo_id, movimientos
								.getMovimientoArticuloUnidadMedidaId(row),
						movimientos
								.getMovimientoArticuloCantidadSolicitada(row),
						conn);
				if ("F"
						.equalsIgnoreCase(ds
								.getTipoMovimientoArticuloPositivo())) {
					resumenes.retrieve("resumen_saldo_articulos.almacen_id ="
							+ almacen_id
							+ " AND resumen_saldo_articulos.articulo_id = "
							+ articulo_id);

					if (resumenes.gotoFirst()) {
						cantidad_en_proceso = resumenes
								.getResumenSaldoArticulosEnProceso();
						resumenes
								.setResumenSaldoArticulosEnProceso(cantidad_en_proceso
										- cantidad_pedida);
						resumenes.update(conn);
						resumenes.resetStatus();
					} else {
						msg
								.append("No existe el resumen de stock correspondiente");
					}
				}
			}

			if (msg.length() != 0)
				return false;

		} catch (SQLException ex) {
			msg
					.append("Ocurrió un error en el SQL mientras se procesaba su aprobación: "
							+ ex.getMessage());

			return false;
		} catch (DataStoreException ex) {
			msg
					.append("Ocurrió un error en el DataStore mientras se procesaba su aprobación: "
							+ ex.getMessage());

			return false;
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException ex) {
				msg
						.append("Ocurrió un error de SQL mientras se cerraba el Statement: "
								+ ex.getMessage());
			}
		}
		return true;
	}
}
