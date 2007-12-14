package infraestructura.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.salmonllc.html.HtmlComponent;
import com.salmonllc.html.HtmlDropDownList;
import com.salmonllc.html.HtmlHiddenField;
import com.salmonllc.html.HtmlPage;
import com.salmonllc.html.HtmlTable;
import com.salmonllc.html.events.PageEvent;
import com.salmonllc.html.events.PageListener;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.util.MessageLog;

public class DDAPSelector extends HtmlTable implements PageListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8921388885945581690L;
	DBConnection conn = null;
	Statement st = null;
	ResultSet r = null;
	String _appName = null;

	HtmlDropDownList _tipos;
	HtmlDropDownList _objetos;
	HtmlDropDownList _tipos_detalle;
	HtmlDropDownList _nombres_detalle;
	HtmlHiddenField _hidden;
	HtmlHiddenField _hidden2;
	HtmlHiddenField _hidden3;

	public DDAPSelector(String name, HtmlPage p) {
		super(name, p);

		// set propiedades de la tabla
		setBorder(0);
		setCellSpacing(0);
		setCellPadding(0);

		// creo componentes para su visualizaci�n en pantalla
		_tipos = new HtmlDropDownList("tipos_objeto", p);
		_objetos = new HtmlDropDownList("nombres_objeto", p);
		_tipos_detalle = new HtmlDropDownList("tipos_detalle", p);
		_nombres_detalle = new HtmlDropDownList("nombres_detalle", p);
		setComponentAt(0, 0, _tipos);
		setComponentAt(0, 1, _objetos);
		setComponentAt(0, 2, _tipos_detalle);
		setComponentAt(0, 3, _nombres_detalle);
		
		_appName = p.getApplicationName();

		// Lleno la tabla de tipos
		try {
			conn = DBConnection.getConnection(_appName,"infraestructura");
			String SQL = "select tipo_objeto from diccionario_aplicacion_detalle group by tipo_objeto";
			st = conn.createStatement();
			r = st.executeQuery(SQL);

			while (r.next()) {
				_tipos.addOption(r.getString(1), r.getString(1));
			}
			_tipos.setSelectedIndex(0);
			recuperarObjetos();
			recuperarTipoDetalle();
			recuperarNombreDetalle();
			
		} catch (SQLException e) {
			MessageLog.writeErrorMessage(e, null);
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (Exception ex) {
				}
			}

			if (st != null)
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			if (conn != null)
				conn.freeConnection();
		}

		// add a hidden field and some javascript to the component
		// so it submits the page when the user changes it.
		// This hidden field is used so the submit performed event can test to
		// make sure that it was this component that submitted the page and not
		// a different one.
		_hidden = new HtmlHiddenField("hidden", "0", p);
		_hidden2 = new HtmlHiddenField("hidden2", "0", p);
		_hidden3 = new HtmlHiddenField("hidden3", "0", p);
		setComponentAt(2, 0, _hidden);
		setComponentAt(2, 1, _hidden2);
		setComponentAt(2, 2, _hidden3);

		// add a page listener so this page will get notified when the page
		// containing the component is submitted
		p.addPageListener(this);
	}
	
    private void recuperarObjetos() {
        String tipo = _tipos.getValue();
        _objetos.resetOptions();
        _tipos_detalle.resetOptions();
        _nombres_detalle.resetOptions();

        if (tipo == null)
            return;

        // Lleno la tabla de objetos
		try {
			conn = DBConnection.getConnection(_appName,"infraestructura");
			String SQL = "select nombre_objeto from diccionario_aplicacion_detalle where tipo_objeto = '" +
				tipo + "' group by nombre_objeto";
			st = conn.createStatement();
			r = st.executeQuery(SQL);

			while (r.next()) {
				_objetos.addOption(r.getString(1), r.getString(1));
			}
			_objetos.setSelectedIndex(0);
		} catch (SQLException e) {
			MessageLog.writeErrorMessage(e, null);
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (Exception ex) {
				}
			}

			if (st != null)
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			if (conn != null)
				conn.freeConnection();
		}
    }

    private void recuperarTipoDetalle() {
        String tipo = _tipos.getValue();
        String objeto = _objetos.getValue();
        _tipos_detalle.resetOptions();
        _nombres_detalle.resetOptions();
        if (tipo == null)
            return;

        // Lleno la tabla de objetos
		try {
			conn = DBConnection.getConnection(_appName,"infraestructura");
			String SQL = "select tipo_detalle from diccionario_aplicacion_detalle where tipo_objeto = '" +
				tipo + "' and nombre_objeto = '" + objeto + "' group by tipo_detalle";
			st = conn.createStatement();
			r = st.executeQuery(SQL);

			while (r.next()) {
				_tipos_detalle.addOption(r.getString(1), r.getString(1));
			}
			_tipos_detalle.setSelectedIndex(0);
		} catch (SQLException e) {
			MessageLog.writeErrorMessage(e, null);
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (Exception ex) {
				}
			}

			if (st != null)
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			if (conn != null)
				conn.freeConnection();
		}
    }

    private void recuperarNombreDetalle() {
        String tipo = _tipos.getValue();
        String objeto = _objetos.getValue();
        String tipos_detalle = _tipos_detalle.getValue();
        _nombres_detalle.resetOptions();
        if (tipo == null)
            return;

        // Lleno la tabla de objetos
		try {
			conn = DBConnection.getConnection(_appName,"infraestructura");
			String SQL = "select nombre_detalle from diccionario_aplicacion_detalle where tipo_objeto = '" +
				tipo + "' and nombre_objeto = '" + objeto + "' and tipo_detalle = '" +
				tipos_detalle + "'";
			st = conn.createStatement();
			r = st.executeQuery(SQL);

			while (r.next()) {
				_nombres_detalle.addOption(r.getString(1), r.getString(1));
			}
			_nombres_detalle.setSelectedIndex(0);
		} catch (SQLException e) {
			MessageLog.writeErrorMessage(e, null);
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (Exception ex) {
				}
			}

			if (st != null)
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			if (conn != null)
				conn.freeConnection();
		}
    }


	public void pageRequested(PageEvent p) throws Exception {
	}

	public void pageRequestEnd(PageEvent p) throws Exception {
	}

	public void pageSubmitEnd(PageEvent p) {
        if (_hidden.getValue() != null && _hidden.getValue().equals("1")) {
            recuperarObjetos();
            _hidden.setValue("0");
            _hidden2.setValue("1");
        }
        if (_hidden2.getValue() != null && _hidden2.getValue().equals("1")) {
            recuperarTipoDetalle();
            _hidden2.setValue("0");
            _hidden3.setValue("1");
        }
        if (_hidden3.getValue() != null && _hidden3.getValue().equals("1")) {
            recuperarNombreDetalle();
            _hidden3.setValue("0");
        }
	}

	public void pageSubmitted(PageEvent p) {
	}
	
	public String getTipoSeleccionado() {
		return _tipos.getValue();
	}

	public String getObjetoSeleccionado() {
		return _objetos.getValue();
	}
	
	public String getTipoDetalleSeleccionado() {
		return _tipos_detalle.getValue();
	}
	
	public String getNombreDetalleSeleccionado() {
		return _nombres_detalle.getValue();
	}

	public void setParent(HtmlComponent parent) {
        super.setParent(parent);
        _tipos.setOnChange( getFormString() + _hidden.getFullName() + ".value=1;" + getFormString() + "submit();");
        _objetos.setOnChange( getFormString() + _hidden2.getFullName() + ".value=1;" + getFormString() + "submit();");
        _tipos_detalle.setOnChange( getFormString() + _hidden3.getFullName() + ".value=1;" + getFormString() + "submit();");
   }

}
