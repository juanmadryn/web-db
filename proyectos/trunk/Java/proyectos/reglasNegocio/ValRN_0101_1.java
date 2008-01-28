/**
 *
 */
package proyectos.reglasNegocio;

import infraestructura.models.AtributosEntidadModel;
import infraestructura.reglasNegocio.ValidadorReglasNegocio;

import java.sql.SQLException;

import proyectos.models.ProyectoModel;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;


/**
 * @author Demian
 *
 */
public final class ValRN_0101_1 extends ValidadorReglasNegocio {

	/* (non-Javadoc)
	 * @see infdev.reglasNegocio.validadorReglasNegocio#esValido(java.lang.Object, java.lang.String)
	 */
	public boolean esValido(Object obj, StringBuilder msg,DBConnection conn) {
		ProyectoModel ds = (ProyectoModel) obj;

		AtributosEntidadModel dsAtributos = new AtributosEntidadModel("infraestructura","infraestructura");

		// recalculo los totales del recibo
		try {
			StringBuilder criteria = new StringBuilder();
			criteria.append("atributos_entidad.objeto_id = ");
			criteria.append(ds.getProyectosProyectoId());
			criteria.append(" and atributos_entidad.tipo_objeto = 'TABLA'");
			criteria.append(" and atributos_entidad.nombre_objeto = 'proyectos'");
			dsAtributos.retrieve(criteria.toString());
			dsAtributos.validaAtributosUpdate();
			return true;		
		} catch (DataStoreException e) {
			msg.append(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			msg.append(e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

}
