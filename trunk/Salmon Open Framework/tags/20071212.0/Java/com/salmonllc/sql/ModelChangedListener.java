package com.salmonllc.sql;

/**
 * For classes that want to be notified when changes are made to a datastore model must implement this interface and also register with the datastore they want to listen to
 */
public interface ModelChangedListener {

	public void modelChanged(ModelChangedEvent evt);
	
}
