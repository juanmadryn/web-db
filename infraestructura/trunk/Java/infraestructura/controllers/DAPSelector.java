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

public class DAPSelector extends HtmlTable implements PageListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5387344124845525998L;
	DBConnection conn = null;
	Statement st = null;
	ResultSet r = null;
	String _appName = null;

	HtmlDropDownList _tipos;
	HtmlDropDownList _objetos;
	HtmlHiddenField _hidden;

	public DAPSelector(String name, HtmlPage p) {
		super(name, p);

		// set propiedades de la tabla
		setBorder(0);
		setCellSpacing(0);
		setCellPadding(0);

		// creo componentes para su visualización en pantalla
		_tipos = new HtmlDropDownList("tables", p);
		_objetos = new HtmlDropDownList("columns", p);
		setComponentAt(0, 0, _tipos);
		setComponentAt(0, 1, _objetos);
		
		_appName = p.getApplicationName();

		// Lleno la tabla de tipos
		try {
			conn = DBConnection.getConnection(_appName,"infraestructura");
			String SQL = "select tipo_objeto from diccionario_aplicacion group by tipo_objeto";
			st = conn.createStatement();
			r = st.executeQuery(SQL);

			while (r.next()) {
				_tipos.addOption(r.getString(1), r.getString(1));
			}
			_tipos.setSelectedIndex(0);
			recuperarObjetos();
			
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
		setComponentAt(2, 0, _hidden);

		// add a page listener so this page will get notified when the page
		// containing the component is submitted
		p.addPageListener(this);
	}
	
    private void recuperarObjetos() {
        String tipo = _tipos.getValue();
        _objetos.resetOptions();
        if (tipo == null)
            return;

        // Lleno la tabla de objetos
		try {
			conn = DBConnection.getConnection(_appName,"infraestructura");
			String SQL = "select nombre_objeto from diccionario_aplicacion where tipo_objeto = '" +
				tipo + "'";
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


	public void pageRequested(PageEvent p) throws Exception {
	}

	public void pageRequestEnd(PageEvent p) throws Exception {
	}

	public void pageSubmitEnd(PageEvent p) {
        if (_hidden.getValue() != null && _hidden.getValue().equals("1")) {
            recuperarObjetos();
            _hidden.setValue("0");
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

	public void setParent(HtmlComponent parent) {
        super.setParent(parent);
        _tipos.setOnChange( getFormString() + _hidden.getFullName() + ".value=1;" + getFormString() + "submit();");
   }

}
