package com.salmonllc.sql;

import javax.servlet.http.HttpServletRequest;

/**
 * Implement this method for any DataStore that you want to expose remotely via the a ProxyDataStore.
 */
public interface Remotable {

    public static final String OPERATION_CREATE = DataStoreProxy.OPERATION_CREATE;
	public static final String OPERATION_RETRIEVE = DataStoreProxy.OPERATION_RETRIEVE;
	public static final String OPERATION_CANCEL = DataStoreProxy.OPERATION_CANCEL;
	public static final String OPERATION_PING = DataStoreProxy.OPERATION_PING;
	public static final String OPERATION_UPDATE = DataStoreProxy.OPERATION_UPDATE;
	public static final String OPERATION_COUNT = DataStoreProxy.OPERATION_COUNT;
	public static final String OPERATION_DESTROY = DataStoreProxy.OPERATION_DESTROY;
/**
 * This method gets fired before each remote request to the DataStore is made (including the create method).
 * @return true if the request should be granted and false if the request should be denied.
 * @param reqType The type of request being made. See operation constants for valid values.
 * @param req javax.servlet.http.HttpServletRequest The servlet request used.
 * @param sessionValid true if this request came from a valid session or false if a new one had to be created.
 * @param userID The user id making the request
 * @param password The password of the user id making the request
 * @param criteria The selection criteria passed from the request
 */
public boolean request(String reqType, HttpServletRequest req, boolean sessionValid, String userID, String password, String criteria) throws DataStoreException;
}
