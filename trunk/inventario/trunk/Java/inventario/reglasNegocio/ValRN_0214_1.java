/**
 *
 */
package inventario.reglasNegocio;

import infraestructura.controllers.Constants;
import infraestructura.models.AtributosEntidadModel;
import infraestructura.reglasNegocio.ValidadorReglasNegocio;
import inventario.models.ComprobanteMovimientoArticuloModel;
import inventario.models.MovimientoArticuloModel;
import inventario.models.ResumenSaldoArticulosModel;

import java.sql.SQLException;
import java.sql.Statement;

import com.salmonllc.properties.Props;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;

/**
 * @author Francisco
 * 
 * Regla de negocio asociada al rechazo de una OC
 * 
 */
public final class ValRN_0214_1 extends ValidadorReglasNegocio implements
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
			MovimientoArticuloModel movimientos = new MovimientoArticuloModel(
					"inventario");
			ResumenSaldoArticulosModel resumenes = new ResumenSaldoArticulosModel(
					"inventario");
			resumenes.setOrderBy("resumen_saldo_articulos.periodo DESC");

			movimientos
					.retrieve("movimiento_articulo.comprobante_movimiento_id ="
							+ ds
									.getComprobanteMovimientoArticuloComprobanteMovimientoId());

			if (ds.getComprobanteMovimientoArticuloTipoMovimientoArticuloId() == Props
					.getProps("inventario", null).getIntProperty(
							TIPO_MOVIMIENTO_RECEPCIONES)) {
				msg
						.append("Para cargar recepciones de compras realizadas mediante OC ingrese al sistema de Recepciones");
				return false;
			}

			if (movimientos.getRowCount() == 0) {
				msg.append("Debe detallar por lo menos un artículo");
				return false;
			}

			int almacen_id = ds.getComprobanteMovimientoArticuloAlmacenId();
			int articulo_id;
			double cantidad_pedida;
			double cantidad_disponible;
			for (int row = 0; row < movimientos.getRowCount(); row++) {
				articulo_id = movimientos.getMovimientoArticuloArticuloId(row);
				resumenes.retrieve("resumen_saldo_articulos.almacen_id ="
						+ almacen_id
						+ " AND resumen_saldo_articulos.articulo_id = "
						+ articulo_id);
				resumenes.gotoFirst();
				cantidad_pedida = movimientos
						.getMovimientoArticuloCantidadSolicitada(row);
				cantidad_disponible = resumenes
						.getResumenSaldoArticulosStockEnMano()
						- resumenes.getResumenSaldoArticulosEnProceso()
						- resumenes.getResumenSaldoArticulosReservado()
						- Double.parseDouble(AtributosEntidadModel
								.getValorAtributoObjeto(ARTICULO_STOCK_MINIMO,
										articulo_id, "TABLA", "articulos"));
				if (cantidad_disponible < cantidad_pedida)
					msg.append("La cantidad disponible en este momento es "
							+ cantidad_disponible + " para el artículo "
							+ movimientos.getArticulosDescripcion(row));
				else 
					resumenes.setResumenSaldoArticulosEnProceso(cantidad_pedida);
			}

			if(msg.length() != 0)
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
