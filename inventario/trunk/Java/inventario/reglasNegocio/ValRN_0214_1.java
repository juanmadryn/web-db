/**
 *
 */
package inventario.reglasNegocio;

import infraestructura.controllers.Constants;
import infraestructura.models.AtributosEntidadModel;
import infraestructura.reglasNegocio.ValidadorReglasNegocio;
import inventario.models.ComprobanteMovimientoArticuloModel;
import inventario.models.ConversionesModel;
import inventario.models.MovimientoArticuloModel;
import inventario.models.ResumenSaldoArticulosModel;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

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

			if (ds.getComprobanteMovimientoArticuloUserIdRetira() == 0
					&& "F".equalsIgnoreCase(ds
							.getTipoMovimientoArticuloPositivo())) {
				msg
						.append("Especifique el legajo de quien retira para completar el comprobante");
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
			double cantidad_en_proceso;

			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			for (int row = 0; row < movimientos.getRowCount(); row++) {
				articulo_id = movimientos
				.getMovimientoArticuloArticuloId(row);
				cantidad_pedida = ConversionesModel
				.getUnidadConvertida(
						articulo_id,
						movimientos
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

					if (!resumenes.gotoFirst()) {
						resumenes.gotoRow(resumenes.insertRow());
						resumenes.setResumenSaldoArticulosAlmacenId(almacen_id);
						resumenes
								.setResumenSaldoArticulosArticuloId(articulo_id);
						resumenes
								.setResumenSaldoArticulosPeriodo(new java.sql.Date(
										calendar.getTimeInMillis()));
						resumenes.setResumenSaldoArticulosStockEnMano(0);
						resumenes.setResumenSaldoArticulosReservado(0);						
					}

					cantidad_en_proceso = resumenes
							.getResumenSaldoArticulosEnProceso();					
					cantidad_disponible = resumenes
							.getResumenSaldoArticulosStockEnMano()
							- cantidad_en_proceso
							- resumenes.getResumenSaldoArticulosReservado()
							- Double.parseDouble(AtributosEntidadModel
									.getValorAtributoObjeto(
											ARTICULO_STOCK_MINIMO, articulo_id,
											"TABLA", "articulos"));
					if (cantidad_disponible < cantidad_pedida)
						msg.append("La cantidad disponible en este momento es "
								+ cantidad_disponible + " para el artículo "
								+ movimientos.getArticulosDescripcion(row));
					else {
						resumenes
								.setResumenSaldoArticulosEnProceso(cantidad_pedida
										+ cantidad_en_proceso);						
					}
					resumenes.update(conn);
					resumenes.resetStatus();
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
