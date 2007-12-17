package infraestructura.controllers;

//The Salmon Open Framework for Internet Applications (SOFIA)
//Copyright (C) 1999 - 2002, Salmon LLC
//
//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License version 2
//as published by the Free Software Foundation;
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//
//For more information please visit http://www.salmonllc.com


import infraestructura.models.WebsiteUserModel;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.util.MessageLog;

/* This class will keep the information about the user*/
public class WebSiteUser implements Constants, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3561021292247014229L;
	private static String _appName;
	private String _userName, _userPassword, _sEmail, _apeynom,_nivelVisibilidad;
	private int _userID;
	private boolean _isValid;
	private Hashtable<String, String> _roles;
	protected WebsiteUserModel _dsUserInfo;

	/**
	 * Create a new WebSiteUser object
	 * @param sAppName String
	 * @param sUserName String
	 */
	public WebSiteUser(String sAppName, String sUserName) {
		this(sAppName, sUserName, null);
	}

	/**
	 * Create a new WebSiteUser object
	 * @param sAppName String
	 * @param sUserName
	 * @param sPassword
	 */
	public WebSiteUser(String sAppName, String sUserName, String sPassword)
	{
		int rol;
		DBConnection conn = null;
		_isValid = false;
		setApplicationName(sAppName);

		_roles = new Hashtable<String, String>();
		
		_dsUserInfo = new WebsiteUserModel(_appName,"infraestructura");

		Statement st = null;
		ResultSet r  = null;
		try
		{
			conn = DBConnection.getConnection(_appName,"infraestructura");
			String SQL = "select u.user_id,u.login_password, u.nivel_visibilidad"
					+ " ,u.nombre_completo, u.email"
					+ " from infraestructura.website_user u"
					+ " where u.login_name = '"+ sUserName + "'";
			st = conn.createStatement();
			r = st.executeQuery(SQL);

			if (r.first())
			{
				_userID = r.getInt(1);
				_userName = sUserName;
				_apeynom = r.getString(4);
				_userPassword = r.getString(2);
				_nivelVisibilidad = r.getString(3);
				_sEmail = r.getString(5);
				

				//password check for this application
				if (sPassword != null && sPassword.equals(_userPassword)) {
					_isValid = true;

				} else // no es un usuario válido
					_isValid = false;
			}
			
			if (_isValid){
				// si llegamos hasta acá y el usuario es válido, 
				// recupero sus roles 
				SQL = "SELECT u.rol_id,r.nombre FROM usuario_roles u"
					+ " inner join roles r using (rol_id)"
					+ " where now() between u.desde and COALESCE(u.hasta,now())"
					+ " and u.anulado = 'F' "
					+ " and user_id = " + _userID;
				
				st = conn.createStatement();
				r = st.executeQuery(SQL);

				if (r.first()){
					// recorro y guardo TODOS los roles activos del usuario
					_roles.clear();
					do {
						rol = r.getInt(1);
						if (rol != -1) 
							_roles.put(new String(Integer.toString(rol)), new String(r.getString(2)));
					} while (r.next());
					
				}

			}
		}
		catch (SQLException e)
		{
			MessageLog.writeErrorMessage(e, null);
		}
		finally
		{
			if(r != null){
				try{
					r.close();
				}catch(Exception ex){}
			}

			if(st !=null)
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			if (conn != null)
				conn.freeConnection();
		}
	}

	/**
	 * Return user id
	 * @return int
	 */
	public int getUserID () {
		return _userID;
	}

	/**
	 * Return user name
	 * @return String
	 */
	public String getUserName()
	{
		return _userName;
	}

	/**
	 * @return Returns the _apeynom.
	 */
	public String getApeynom() {
		return _apeynom;
	}


	/**
	 * @return Returns the _nivelVisibilidad.
	 */
	public String getNivelVisibilidad() {
		return _nivelVisibilidad;
	}

	/**
	 * Returns true if the user is a valid one.
	 * @return boolean
	 */
	public boolean isValid() {
		return _isValid;
	}

	/**
	 * Set application name
	 * @param name  String
	 */
	public static void setApplicationName (String name) {
		_appName = name;
	}

	/**
	 * retorna la lista de roles separados por coma entre paréntesis
	 * @return String
	 */
	public String getSetRoles(){
		boolean primero = true;
		String aux = "(";
		
		for (Enumeration e = _roles.keys() ; e.hasMoreElements() ;) {
			if (! primero)
				aux = aux +",";
			else
				primero = false;

	         aux = aux + (String) e.nextElement();
	    }
		aux = aux + ")";
		
		return aux;
	}
	
	/**
	 * @param rol (id del rol a buscar)
	 * @return verdadero si el rol est� en la lista de roles del usuario
	 */
	public boolean tieneRol(int rol){
        if (_roles.containsKey(Integer.toString(rol)))
    		 return true;
		
		return false;
	}

	/**
	 * Returns email
	 * @return String
	 */
	public String getEmail() {
		return _sEmail;
	}
	
	public Hashtable getRoles(){
		return _roles;
	}

}
