
//package statement
package tango.controllers;

//Salmon import statements
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.salmonllc.jsp.*;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;
import com.salmonllc.html.HtmlSubmitButton;
import com.salmonllc.html.events.*;


/**
 * LkpLegajoController: a SOFIA generated controller
 */
public class LkpLegajoController extends JspController implements SubmitListener, PageListener {

/**
	 * 
	 */
	private static final long serialVersionUID = 8726736581400826456L;
	//Visual Components
      public com.salmonllc.html.HtmlText _apellidoCAP4;
      public com.salmonllc.html.HtmlText _apellidoTXT4;
      public com.salmonllc.html.HtmlText _buscarCAP1;
      public com.salmonllc.html.HtmlText _cuilCAP6;
      public com.salmonllc.html.HtmlText _cuilTXT6;
      public com.salmonllc.html.HtmlText _legajoCAP3;
      public com.salmonllc.html.HtmlText _legajoIdCAP2;
      public com.salmonllc.html.HtmlText _legajoIdTXT2;
      public com.salmonllc.html.HtmlText _legajoTXT3;
      public com.salmonllc.html.HtmlText _nombreCAP5;
      public com.salmonllc.html.HtmlText _nombreTXT5;
      public com.salmonllc.html.HtmlTextEdit _buscarTE3;
      public com.salmonllc.html.HtmlTextEdit _buscarNombreTE3;
      public com.salmonllc.html.HtmlValidatorText _valErrorMessage;
      public com.salmonllc.jsp.JspBox _box1;
      public com.salmonllc.jsp.JspBox _box2;
      public com.salmonllc.jsp.JspDataTable _datatable1;
      public com.salmonllc.jsp.JspForm _pageForm;
      public com.salmonllc.jsp.JspListFormDisplayBox _listformdisplaybox1;
      public com.salmonllc.jsp.JspSearchFormDisplayBox _searchformdisplaybox1;

//DataSources
      public com.salmonllc.sql.QBEBuilder _dsQBE;
      public tango.models.LegajoView _dsLegajo;

//DataSource Column Constants
       public static final String DSLEGAJO_LEGAJO_ID_LEGAJO="LEGAJO.ID_LEGAJO";
       public static final String DSLEGAJO_LEGAJO_ID_TIPO_DOCUMENTO="LEGAJO.ID_TIPO_DOCUMENTO";
       public static final String DSLEGAJO_LEGAJO_ID_LEGAJO_JEFE="LEGAJO.ID_LEGAJO_JEFE";
       public static final String DSLEGAJO_LEGAJO_ID_PROVINCIA="LEGAJO.ID_PROVINCIA";
       public static final String DSLEGAJO_LEGAJO_ID_MODELO_ASIENTO_SU="LEGAJO.ID_MODELO_ASIENTO_SU";
       public static final String DSLEGAJO_LEGAJO_ID_NACIONALIDAD="LEGAJO.ID_NACIONALIDAD";
       public static final String DSLEGAJO_LEGAJO_ID_EXPEDIDO_POR="LEGAJO.ID_EXPEDIDO_POR";
       public static final String DSLEGAJO_LEGAJO_NRO_LEGAJO="LEGAJO.NRO_LEGAJO";
       public static final String DSLEGAJO_LEGAJO_APELLIDO="LEGAJO.APELLIDO";
       public static final String DSLEGAJO_LEGAJO_NOMBRE="LEGAJO.NOMBRE";
       public static final String DSLEGAJO_LEGAJO_APELLIDO_CONYUGE="LEGAJO.APELLIDO_CONYUGE";
       public static final String DSLEGAJO_LEGAJO_FECHA_NACIMIENTO="LEGAJO.FECHA_NACIMIENTO";
       public static final String DSLEGAJO_LEGAJO_NRO_DOCUMENTO="LEGAJO.NRO_DOCUMENTO";
       public static final String DSLEGAJO_LEGAJO_CALLE="LEGAJO.CALLE";
       public static final String DSLEGAJO_LEGAJO_NRO_DOMIC="LEGAJO.NRO_DOMIC";
       public static final String DSLEGAJO_LEGAJO_PISO="LEGAJO.PISO";
       public static final String DSLEGAJO_LEGAJO_DEPARTAMENTO_DOMIC="LEGAJO.DEPARTAMENTO_DOMIC";
       public static final String DSLEGAJO_LEGAJO_CODIGO_POSTAL="LEGAJO.CODIGO_POSTAL";
       public static final String DSLEGAJO_LEGAJO_LOCALIDAD="LEGAJO.LOCALIDAD";
       public static final String DSLEGAJO_LEGAJO_TAREA_HABITUAL="LEGAJO.TAREA_HABITUAL";
       public static final String DSLEGAJO_LEGAJO_CUIL="LEGAJO.CUIL";
       public static final String DSLEGAJO_LEGAJO_EMAIL="LEGAJO.EMAIL";
       public static final String DSLEGAJO_LEGAJO_SEXO="LEGAJO.SEXO";
       public static final String DSLEGAJO_LEGAJO_ESTADO_CIVIL="LEGAJO.ESTADO_CIVIL";
       public static final String DSLEGAJO_LEGAJO_FOTO_LEGAJO="LEGAJO.FOTO_LEGAJO";
       public static final String DSLEGAJO_LEGAJO_CONFIDENCIAL="LEGAJO.CONFIDENCIAL";
       public static final String DSLEGAJO_LEGAJO_OBSERVACIONES="LEGAJO.OBSERVACIONES";
       public static final String DSLEGAJO_LEGAJO_APELLIDO_MATERNO="LEGAJO.APELLIDO_MATERNO";
       public static final String DSLEGAJO_LEGAJO_ID_COMUNA="LEGAJO.ID_COMUNA";
       public static final String DSLEGAJO_LEGAJO_CA_83_IMPORT1="LEGAJO.CA_83_IMPORT1";
       public static final String DSLEGAJO_LEGAJO_CA_83_IMPORT2="LEGAJO.CA_83_IMPORT2";
       public static final String DSLEGAJO_LEGAJO_CA_83_IMPORT3="LEGAJO.CA_83_IMPORT3";
       public static final String DSLEGAJO_LEGAJO_CA_83_IMPORT4="LEGAJO.CA_83_IMPORT4";
       public static final String DSLEGAJO_LEGAJO_CA_83_IMPORT5="LEGAJO.CA_83_IMPORT5";
       public static final String DSLEGAJO_LEGAJO_CA_83_DESC1="LEGAJO.CA_83_DESC1";
       public static final String DSLEGAJO_LEGAJO_CA_83_DESC2="LEGAJO.CA_83_DESC2";
       public static final String DSLEGAJO_LEGAJO_CA_83_DESC3="LEGAJO.CA_83_DESC3";
       public static final String DSLEGAJO_LEGAJO_CA_83_DESC4="LEGAJO.CA_83_DESC4";
       public static final String DSLEGAJO_LEGAJO_CA_83_DESC5="LEGAJO.CA_83_DESC5";
       public static final String DSLEGAJO_LEGAJO_CA_83_ANTIGUE="LEGAJO.CA_83_ANTIGUE";
       public static final String DSLEGAJO_LEGAJO_CA_83_ANTIG_ANT="LEGAJO.CA_83_ANTIG_ANT";
       public static final String DSLEGAJO_LEGAJO_CA_83_EVALUAC="LEGAJO.CA_83_EVALUAC";
       public static final String DSLEGAJO_LEGAJO_CA_83_ADI_FIJ="LEGAJO.CA_83_ADI_FIJ";
       public static final String DSLEGAJO_LEGAJO_CA_83_TIP_SEG="LEGAJO.CA_83_TIP_SEG";
       public static final String DSLEGAJO_LEGAJO_CA_83_SEG_VID="LEGAJO.CA_83_SEG_VID";
       public static final String DSLEGAJO_LEGAJO_CA_83_NUM_JUB="LEGAJO.CA_83_NUM_JUB";
       public static final String DSLEGAJO_LEGAJO_CA_83_N_INSCRIP="LEGAJO.CA_83_N_INSCRIP";
       public static final String DSLEGAJO_TIPO_DOCUMENTO_TIPO_DOCUMENTO="TIPO_DOCUMENTO.TIPO_DOCUMENTO";
       public static final String DSLEGAJO_TIPO_DOCUMENTO_DESC_DOCUMENTO="TIPO_DOCUMENTO.DESC_DOCUMENTO";

       public static final String DSQBE_BUSCAR="buscar";
       
       //componentes custom
   	  public com.salmonllc.html.HtmlSubmitButton _buscarBUT1;


 
/**
 * Initialize the page. Set up listeners and perform other initialization activities.
 */
public void initialize(){
	
	// genera botones custom
	_buscarBUT1 = new HtmlSubmitButton("buscarBUT1","Buscar",this);
	_buscarBUT1.setAccessKey("B");
	_searchformdisplaybox1.addButton(_buscarBUT1);

	
     addPageListener(this);
     _buscarBUT1.addSubmitListener(this);
}
 
/**
 * Process the given submit event
 * @param event the submit event to be processed
 * @return true to continue processing events, false to stop processing events
 * @throws DataStoreException 
 */
public boolean submitPerformed(SubmitEvent event) throws DataStoreException {

	
	if (event.getComponent() == _buscarBUT1) {
			Props p;
			String driverTango = null;
			String urlTango = null;
			String userTango = null;
			String passWordTango = null;
			Connection connTango;
			Statement st = null;
			ResultSet r = null;
			String SQL = null;
			String debug = null;

			p = Props.getProps("partesMO", null);
			driverTango = p.getProperty("driverTango","sun.jdbc.odbc.JdbcOdbcDriver");
			urlTango = p.getProperty("urlTango", "jdbc:odbc:tango");
			userTango = p.getProperty("userTango", "tango");
			passWordTango = p.getProperty("passWordTango", "tango");
			int nro_legajo = -1;
			int legajo_id = -1;
			String nombre = null;
			String apellido = null;
			String cuil = null;

			// realiza una búsqueda custom de los legajos
			try {
				// Se carga el driver
				Class.forName(driverTango);
			} catch (ClassNotFoundException e) {
				MessageLog.writeErrorMessage(e, null);
				throw new DataStoreException(
						"Imposible cargar el driver para Tango: "
								+ e.getMessage());
			}

			try {
				// Se establece la conexión con la base de datos
				connTango = DriverManager.getConnection(urlTango, userTango,
						passWordTango);
			} catch (Exception e) {
				MessageLog.writeErrorMessage(e, null);
				throw new DataStoreException(
						"imposible establecer conexión con la base tango: "
								+ e.getMessage());
			}

			try {

				String whereClause = "1 = 1 ";
				if (_dsQBE.getString("legajo") != null)
					whereClause = whereClause + " AND NRO_LEGAJO = " + _dsQBE.getString("legajo");
				if (_dsQBE.getString("nombre") != null) {
					whereClause = whereClause + " AND (upper(APELLIDO) like '%" + _dsQBE.getString("nombre").toUpperCase() + "%'";
					whereClause = whereClause + " OR upper(NOMBRE) like '%" + _dsQBE.getString("nombre").toUpperCase() + "%'";
					whereClause = whereClause + " OR upper(CUIL) like '%" + _dsQBE.getString("nombre").toUpperCase() + "%')";
				}
				
				SQL = "select ID_LEGAJO,NRO_LEGAJO,NOMBRE,APELLIDO,CUIL from LEGAJO ";
				SQL = SQL + " where " + whereClause;
				
				MessageLog.writeErrorMessage(SQL, null, null);
				
				debug = "CreateStatement";
				st = connTango.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				debug = "executeQuery";
				r = st.executeQuery(SQL);

				_dsLegajo.reset();
				debug = "r.next";
				while (r.next()) {
					legajo_id = r.getInt("ID_LEGAJO");
					nro_legajo = r.getInt("NRO_LEGAJO");
					nombre = r.getString("NOMBRE");
					apellido = r.getString("APELLIDO");
					cuil = r.getString("CUIL");

					int row = _dsLegajo.insertRow();
					_dsLegajo.setLegajoApellido(row, apellido);
					_dsLegajo.setLegajoCuil(row, cuil);
					_dsLegajo.setLegajoIdLegajo(row, legajo_id);
					_dsLegajo.setLegajoNombre(row, nombre);
					_dsLegajo.setLegajoNroLegajo(row, nro_legajo);
				}

			} catch (SQLException e) {
				MessageLog.writeErrorMessage(e, null);
				// además de escribir en el log mando mensaje a la página
				throw new DataStoreException(
						"Error determinando legajo en tango: " + e.getMessage()
								+ " Debug: " + debug);

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
			}
		}
	
	return true;
}
 
/**
 * Process the page requested event
 * @param event the page event to be processed
 */
public void pageRequested(PageEvent event) {
}
 
/**
 * Process the page request end event
 * @param event the page event to be processed
 */
public void pageRequestEnd(PageEvent event) {
}
 
/**
 * Process the page submit end event
 * @param event the page event to be processed
 */
public void pageSubmitEnd(PageEvent event) {
}
 
/**
 * Process the page submit event
 * @param event the page event to be processed
 */
public void pageSubmitted(PageEvent event){
	
}

}
