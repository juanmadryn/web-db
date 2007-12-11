//** Copyright Statement ***************************************************
//The Salmon Open Framework for Internet Applications (SOFIA)
// Copyright (C) 1999 - 2002, Salmon LLC
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License version 2
// as published by the Free Software Foundation;
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//
// For more information please visit http://www.salmonllc.com
//** End Copyright Statement ***************************************************
package com.salmonllc.sql;

import javax.servlet.http.HttpServletRequest;

/**
 * This class is used to combine the functionality of a DataStoreProxy with a BanDataStore. It allows data from EJB value objects to be delivered to Swing applets and web start applications via HTTP HTTPS
 */
public abstract class ProxyBeanDataStoreAdaptor extends DataStore implements Remotable {
	BeanDataStore _beanDs;
	public ProxyBeanDataStoreAdaptor() {
		super(null);
		_beanDs = new BeanDataStore(getBeanClass());

		DSDataStoreDescriptor d = _beanDs.getDescriptor();

		try {
			for (int i = 0; i < d.getColumnCount(); i++)
				d.getColumn(i).setColumn(_beanDs.getColumnName(i));
		} catch (DataStoreException e) {
		}

		setProperties(_beanDs.getProperties());

	}

	/**
	 * Override the standard retrieve method in the datastore
	 */
	public void retrieve(String criteria) {
		reset();
		loadData(criteria);
		setRows(_beanDs.getRows());
	}

	/**
	 * Override the reset method of the datastore
	 */
	public void reset() {
		super.reset();
		_beanDs.reset();
	}

	/**
	 * Override the standard update method in the proxy
	 */
	public void update() {
		_beanDs.setRows(getRows());
		updateData();
	}

	/**
	 * Returns the underlying bean datastore used by this class. Instead of directly setting or getting values from this datastore, you should instead use the internal bean datastore
	 */
	public BeanDataStore getBeanDataStore() {
		return _beanDs;
	}

	/**
	 *Implementation of the Remotable interface. This method is called before each request, you can return true to allow the request and false to deny it.
	 */
	public abstract boolean request(String reqType, HttpServletRequest req, boolean sessionValid, String userID, String password, String criteria) throws DataStoreException;

	/**
	 * This method must return the class of the bean used for the BeanDataStore
	 */
	public abstract Class getBeanClass();

	/**
	 * This method must be implemented for the DataStore to return data from the proxy. In it you should load your bean value objects from the EJB server and move them into the BeanDataStore using getBeanDataStore().insertRow() for each bean you want to send back to the client
	 * @param criteria an arbitrary selection criteria string sent from the client
	 */
	public abstract void loadData(String criteria);

	/**
	 * This method must be implemented to send data from the client proxy back to the EJB server. It should get the data from the BeanDataStore using getBeanDataStore().getBeans() and move the data back to the EJB server through EJB method calls.
	 *
	 */
	public abstract void updateData();

}
