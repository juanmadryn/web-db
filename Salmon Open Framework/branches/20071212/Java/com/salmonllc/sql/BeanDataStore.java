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

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/sql/BeanDataStore.java $
//$Author: Dan $ 
//$Revision: 21 $ 
//$Modtime: 11/08/04 10:19a $ 
/////////////////////////

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import com.salmonllc.util.MessageLog;
import com.salmonllc.util.Util;
/**
 * This class provides an interface between standard java beans and the rest of the framework.<BR>
 * This allows EJBs or other Java Beans to be bound to GUI components so their values can be displayed and manipulated in pages.<BR>
 * This class won't automatically retrieve data as the standard datastore does. This beans must retrieve the data in whatever manner required and passed to one of the insert row methods.<BR>
 * Calling the update method will cause the datastore to copy the values in its internal buffers back to the bean classes instead of the database. The beans themselves must persist the data.<BR>
 * Public bean get methods will automatically be called to move the data from a bean to the datastore buffer when one of the insert methods is called.<BR>
 * public bean set methods will automatically be called to move the data from the datastore buffer to the beans when the update method is called.<BR><BR>
 * Type conversions are as follows:<BR>
 *		String and char and Character bean attributes are converted to DATATYPE_STRING<BR>
 *		Integer and int bean attributes are converted to DATATYPE_INT<BR>
 *		Float and float bean attributes are converted to DATATYPE_FLOAT<BR>
 *		Double and double attributes are converted to DATATYPE_DOUBLE<BR>
 *		Short and short attributes are converted to DATATYPE_SHORT<BR>
 *		Long and long bean attributes are converted to DATATYPE_LONG<BR>
 *		Boolean and boolean bean attributes are converted to DATATYPE_SHORT with 0 being false and non-zero being true<BR>
 *		java.sql.Date and java.util.Date are converted to DATATYPE_DATE<BR>
 *		java.sql.Timestamp is to DATATYPE_DATETIME<BR>
 *		java.sql.Time is to DATATYPE_TIME<BR>
 *		byte[] is converted to DATATYPE_BYTEARRAY<BR>
 *	All other types in the bean (including array types) are ignored.<BR>
 * <BR><BR> 
 * Portions added by<BR>
 * 		Dr Jeffrey J Shifman shifmanjj@superiorcanada.com<BR>
 * 		Superior Electrics Limited http://superiorcanada.com<BR>
 */
public class BeanDataStore extends DataStoreBuffer {

	String _extraInfo;
	private int _beanCount=0;
	public final static int LOAD_ALL =	-1;
	private boolean _lowerCaseFirstLetter=false;
	public static class BeanClassInfo implements Serializable {
		private Class _beanClass;
		private String _alias;
		private int _index;
		
		public String getAlias() {
			return _alias;
		}
		public void setAlias(String alias) {
			_alias = alias;
		}
		public Class getBeanClass() {
			return _beanClass;
		}
		public void setBeanClass(Class beanClass) {
			_beanClass = beanClass;
		}
		public int getIndex() {
			return _index;
		}
		public void setIndex(int index) {
			_index = index;
		}
	}	
		
	public BeanDataStore() {
		
	}
	
	public BeanDataStore(String beanType) {
		this(beanType,false);
	}	
	
	public BeanDataStore(String beanType, boolean multiClass) {
		super();
		try {
			BeanClassInfo inf[]=getBeanInfo(beanType);
			for (int i=0; i < inf.length;i++)
				addBeanDefinition(inf[i].getBeanClass(),multiClass ? inf[i].getAlias() : null);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public BeanDataStore(Class beanType) {
		super();
		addBeanDefinition(beanType,null);
	}

	public BeanDataStore(BeanClassInfo inf) {
		super();
		addBeanDefinition(inf.getBeanClass(),inf.getAlias());
	}
	
	public BeanDataStore(BeanClassInfo inf[]) {
		super();
		for (int i=0; i < inf.length;i++)
			addBeanDefinition(inf[i].getBeanClass(),inf[i].getAlias());
	}
	
	/**
	 * Adds a new bean definition to this components buffer
	 * @param beanType The class name for the bean
	 * @param alias The name the bean is referenced by
	 */
	public void addBeanDefinition(Class beanType, String alias) {
		Method parentGetters[] = new Method[0];
		setUpBuffer(beanType,null,parentGetters,getColumnCount(),alias,_beanCount);
		_beanCount++;
	}
	
	/**
	 * Classes can override to figure out what type of datatype to map a column to
	 * @param beanType
	 * @param getMethod
	 * @return
	 */
	protected int getDataTypeForMethod(Class beanType, Method getMethod) {
		return -1;
	}	
	
	private int setUpBuffer(Class beanType, String attributeName, Method[] parentGetters, int index, String alias, int beanNumber) {
		DSDataStoreDescriptor desc = getDescriptor();
		Method m[] = beanType.getMethods();
		Class cl[] = new Class[1];

		outer:for (int i = 0; i < m.length; i++) {
			String methName = m[i].getName();
			String name=methName;
			int dataType = -1;
			Class returnType = m[i].getReturnType();

			if (name.startsWith("get") && m[i].getParameterTypes().length == 0) {
				if (Util.instanceOf(returnType, Integer.class) || Util.instanceOf(returnType, Integer.TYPE)) {
					dataType = DATATYPE_INT;
				} else if (Util.instanceOf(returnType, String.class)) {
					dataType = DATATYPE_STRING;
				} else if (Util.instanceOf(returnType, Character.class) || Util.instanceOf(returnType, Character.TYPE)) {
					dataType = DATATYPE_STRING;
					returnType = Character.TYPE;
				} else if (Util.instanceOf(returnType, Float.class) || Util.instanceOf(returnType, Float.TYPE)) {
					dataType = DATATYPE_FLOAT;
				} else if (Util.instanceOf(returnType, Double.class) || Util.instanceOf(returnType, Double.TYPE)) {
					dataType = DATATYPE_DOUBLE;
				} else if (Util.instanceOf(returnType, Long.class) || Util.instanceOf(returnType, Long.TYPE)) {
					dataType = DATATYPE_LONG;
				} else if (Util.instanceOf(returnType, byte[].class)) {
					dataType = DATATYPE_BYTEARRAY;
				} else if (Util.instanceOf(returnType, Short.class) || Util.instanceOf(returnType, Short.TYPE)) {
					dataType = DATATYPE_SHORT;
				} else if (Util.instanceOf(returnType, Boolean.class) || Util.instanceOf(returnType, Boolean.TYPE)) {
					dataType = getDescriptor().getBooleanDataType();
					returnType = Boolean.TYPE;
				} else if (Util.instanceOf(returnType, java.sql.Timestamp.class)) {
					dataType = DATATYPE_DATETIME;
				} else if (Util.instanceOf(returnType, java.sql.Time.class)) {
					dataType = DATATYPE_TIME;
				} else if (Util.instanceOf(returnType, java.util.Date.class)) {
					dataType = DATATYPE_DATE;
				} else if (Util.instanceOf(returnType, BigDecimal.class)) { 
					dataType = DATATYPE_DOUBLE;
				} else if (returnType==Object.class) {
					dataType=getDataTypeForMethod(beanType,m[i]);		
				}	
				else if (! isCollection(returnType) && parentGetters.length < 3 && (!name.equals("getClass"))) {
					Method met=m[i];
					String nm=name.substring(3);
					if (attributeName != null) {
						String test=attributeName;
						int pos=test.lastIndexOf(".");
						if (pos > -1)
							test = test.substring(pos + 1);
						if (test.equals(nm))
							continue;
						else
							nm=attributeName+"." + nm;
					}	
					Method newGetters[] = new Method[parentGetters.length + 1];
					for (int j=0; j < parentGetters.length; j++) {
						if (parentGetters[j].equals(met))
							continue outer;
						newGetters[j]=parentGetters[j];
					}	
					newGetters[newGetters.length - 1] = met;
					index = setUpBuffer(returnType,nm, newGetters,index,alias, beanNumber);
					continue;
				} 
				name = name.substring(3);
			} else if (name.startsWith("is") && m[i].getParameterTypes().length == 0 && returnType.isAssignableFrom(Boolean.TYPE)) {
				dataType = getDescriptor().getBooleanDataType();
				name = name.substring(2);
			}
	
			if (dataType != -1) {
				String bucketName=null;
				if (attributeName != null)
					bucketName=attributeName + "." + name;
				else
					bucketName=name;
				if (alias != null)
					bucketName=alias + "." + bucketName;
				if (_lowerCaseFirstLetter)
					bucketName=lowerCaseFirstLetter(bucketName);
				String test=bucketName;
				int testInc=0;
				while (getColumnIndex(test) != -1) {
					test=bucketName+testInc;
					testInc++;
				}
				bucketName=test;
				addBucket(bucketName, dataType);
				DSColumnDescriptor col = desc.getColumn(index);
				col.setGetMethod(m[i]);
				col.setPropertyClass(returnType);
				col.setParentGetMethods(parentGetters);
				col.setBeanInfo(newBeanInfo(beanType,alias, beanNumber));
				cl[0] = returnType;
				try {
					Method setMethod = beanType.getMethod("set" + name, cl);
					col.setSetMethod(setMethod);
					col.setUpdateable(true);
				} catch (Exception e) {
					col.setUpdateable(false);
				}
				index++;
			}
		}	
		return index;
		
	}	
	/**
	 * Removes the current row from the datastore buffer. 
	 * @return True if the row is remove and false if not.
	 */
	public synchronized boolean deleteRow() {
		return super.deleteRow();
	}
	/**
	 * Removes the row from the datastore buffer.
	 * @return True if the row is remove and false if not.
	 */
	public synchronized boolean deleteRow(int row) {
		return super.deleteRow(row);
	}
	/**
	 * Returns the bean at the current row from the standard buffer.
	 */
	public Object getBean() throws DataStoreException {
		return getBean(getRow());
	}
	/**
	 * Returns the bean at the specified row from the standard buffer.
	 */
	public Object getBean(int row) throws DataStoreException {
		return getBean(row, BUFFER_STANDARD);

	}
	/**
	 * Returns the bean at the specified row in the specified buffer.
	 * Buffer must be BUFFER_STANDARD or BUFFER_FILTERED
	 */
	public Object getBean(int row, int buffer) throws DataStoreException {
		Vector v = null;
		if (buffer == BUFFER_STANDARD)
			v = _rows;
		else if (buffer == BUFFER_FILTERED)
			v = _filteredRows;

		if (v == null)
			throw new DataStoreException("Buffer must be BUFFER_STANDARD or BUFFER_FILTERED");

		if (row < 0 || row >= v.size())
			throw new DataStoreException("Specified row (" + row + ") is out of range.");

		DSDataRow d = (DSDataRow) v.elementAt(row);
		return d.getBean();

	}
	/**
	 * Returns all the beans in the stardard buffer as an array of objects.
	 */
	public Object[] getBeans() throws DataStoreException {
		return getBeans(BUFFER_STANDARD);
	}
	/**
	 * Returns the beans in the specified buffer as an array of objects.
	 * Buffer must be BUFFER_STANDARD or BUFFER_FILTERED
	 */
	public Object[] getBeans(int buffer) throws DataStoreException {
		Vector v = null;
		if (buffer == BUFFER_STANDARD)
			v = _rows;
		else if (buffer == BUFFER_FILTERED)
			v = _filteredRows;

		if (v == null)
			throw new DataStoreException("Buffer must be BUFFER_STANDARD or BUFFER_FILTERED");

		Object ret[] = new Object[v.size()];
		for (int i = 0; i < v.size(); i++) {
			DSDataRow r = (DSDataRow) v.elementAt(i);
			ret[i] = r.getBean();
		}

		return ret;
	}
	/**
	 * Returns all the beans in the stardard buffer as a Vector.
	 */
	public Vector getBeansAsVector() throws DataStoreException {
		return getBeansAsVector(BUFFER_STANDARD);
	}
	/**
	 * Returns the beans in the specified buffer as a Vector.
	 * Buffer must be BUFFER_STANDARD or BUFFER_FILTERED
	 */
	public Vector getBeansAsVector(int buffer) throws DataStoreException {
		Object o[] = getBeans(buffer);
		Vector v = new Vector(o.length);
		for (int i = 0; i < o.length; i++)
			v.addElement(o[i]);
		return v;
	}
	/**
	 * Inserts the bean in the datastore after the last row. If the bean passed is null, this method won't do anything except write an error to the log file.
	 */
	public synchronized int insertRow(Object bean) {
		return insertRow(bean, -1);
	}

	/**
	 * Inserts the bean in the datastore after the last row. If the bean passed is null, this method won't do anything except write an error to the log file.
	 * @param bean The bean to update
	 * @param newBean if the bean represents a new row in the database, set this to true, false for an existing row. This can be used later to in the updateBean method to determine if persisting the bean will require an insert or an update
	 */
	public synchronized int insertRow(Object bean, boolean newBean) {
		return insertRow(bean, -1, newBean);
	}
	/**
	 * Inserts the bean in the datastore at the specified row position. If the bean passed is null, this method won't do anything except write an error to the log file.
	 */

	public synchronized int insertRow(Object bean, int pos) {
		return insertRow(bean, pos, false);
	}
	/**
	 * Inserts the bean in the datastore at the specified row position. If the bean passed is null, this method won't do anything except write an error to the log file.
	 * @param bean The bean to update
	 * @param pos The position in the datastore to place the bean, -1 to place it at the end
	 * @param newBean if the bean represents a new row in the database, set this to true, false for an existing row. This can be used later to in the updateBean method to determine if persisting the bean will require an insert or an update
	 **/
	public synchronized int insertRow(Object bean, int pos, boolean newBean) {
		if (pos == -1)
			pos = getRowCount();
		try {
			if (bean == null)
				throw new DataStoreException("Error, the insertRow method cannot accept a null bean");
		} catch (Exception e) {
			com.salmonllc.util.MessageLog.writeErrorMessage("insertRow()", e, this);
		}

		int row = super.insertRow(pos);
		DSDataRow rowObj = getDataRow(row);

		try {
			rowObj.populateFromBean(getDescriptor(), bean, newBean ? STATUS_NEW : STATUS_NOT_MODIFIED);
		} catch (Exception e) {
			com.salmonllc.util.MessageLog.writeErrorMessage("insertRow()", e, this);
		}
		return row;
	}
	/**
	 * Inserts an array of beans into the datastore at the end of the buffer. Any null beans will be skipped and an error will be written to the log.
	 */
	public synchronized void insertRows(Object bean[]) {
		if (bean != null) {
			for (int i = 0; i < bean.length; i++) {
				insertRow(bean[i]);
			}
		}
	}
	/**
	 * Inserts an collection of beans into the datastore at the end of the buffer. All the beans in the collection must be of the same type and any null beans will be skipped and an error will be written to the log.
	 */
	public synchronized void insertRows(Collection c) {
		if (c != null)
			insertRows(c.toArray());

	}
	/**
	 * Copies all the data changed in the datastore to the respective bean sources
	 */
	public synchronized void update() throws Exception {
		update(true);
	}
	
	/**
	 * Copies all the data changed in the datastore to the respective bean sources. Only resets the DataStoreStatus if you specify
	 */
	public void update(boolean resetStatus) throws Exception{
		int count = getRowCount();
		DSDataStoreDescriptor desc = getDescriptor();
		for (int i = 0; i < count; i++) {
			DSDataRow r = getDataRow(i);
			int rowStatus = r.getRowStatus();
			if (rowStatus == STATUS_MODIFIED || rowStatus == STATUS_NEW_MODIFIED) {
				r.copyDataToBean(desc,resetStatus);
				beanUpdated(r.getBean(), rowStatus == STATUS_NEW_MODIFIED);
			}	
			if (! resetStatus)
				r.setRowStatus(rowStatus);
		}
		for (int i = 0; i < getDeletedCount(); i++) {
			DSDataRow r = getDataStoreRow(i, BUFFER_DELETED).getDSDataRow();
			beanDeleted(r.getBean());
		}
		if (resetStatus)
			resetStatus();		
	}
	
	/**
	 * An empty method that can be overridden by subclasses to allow for automatic persistance of the data in the bean to the database. Called for new or modified rows from the update method.
	 * @param bean The updated data bean
	 * @param newBean True for a bean representing an existing row in the database and false for one representing an existing row in the database. Whether or not a bean is considered a new row or not is determined with the newBean argument to the insertRow method.
	 **/
	public void beanUpdated(Object bean, boolean newBean) throws Exception {

	}

	/**
	 * An empty method that can be overridden by subclasses to allow for automatic deletion of the data in the bean to the database. Called for deleted rows from the update method.
	 * @param bean The bean deleted from the DataStore
	 */
	public void beanDeleted(Object bean) {

	}

	/**
	 * This method will instantiate the a bean datastore described in the className field. The class must extend BeanDataStore and must have at least one of four constructors: One that takes a class (class name of the bean), one that takes a class name and an extraArgs string parm, one that takes just a String (extraInfo parm), or a no-args constructor.
	 * @param className The class name for the BeanDataStore subclass to construct
	 * @param beanName The class name of the bean this BeanDataStore will use
	 * @param extraInfo a String with arbitrary info passed to the constructor of the BeanDataStore
	 **/
	public static BeanDataStore constructBeanDataStore(String className, String beanName, String extraInfo) {
		BeanDataStore ret = null;
		try {
			Class stringClass = new String().getClass();
			Class classClass = Class.forName("java.lang.Class");
			Class beanInfoArrayClass = new BeanClassInfo[0].getClass(); 
			Class beanInfoClass = newBeanInfo(null,null,-1).getClass(); 

			if (className == null && beanName != null)
				className=beanName;
			Class cl = Class.forName(className, true, Thread.currentThread().getContextClassLoader());
			if (!Util.instanceOf(cl, BeanDataStore.class))
				return null;
			BeanClassInfo beanInf[]=null;
			if (beanName != null)
				beanInf = getBeanInfo(beanName);

			Constructor cs[] = cl.getConstructors();
			Constructor classOnlyCon = null;
			Constructor classAndExtraInfoCon = null;
			Constructor noArgsCon = null;
			Constructor extraInfoOnlyCon = null;
			Constructor beanInfoArrayCon=null;
			Constructor beanInfoCon=null;
			for (int i = 0; i < cs.length; i++) {
				Class[] args = cs[i].getParameterTypes();
				if (args.length == 2) {
					if (args[0] == classClass && args[1] == stringClass)
						classAndExtraInfoCon = cs[i];
					else if (args[0] == beanInfoArrayClass && args[1] == stringClass)
						beanInfoArrayCon=cs[i];
					else if (args[0] == beanInfoClass && args[1] == stringClass)
						beanInfoCon=cs[i];
				} else if (args.length == 1) {
					if (args[0] == classClass)
						classOnlyCon = cs[i];
					else if (args[0] == stringClass)
						extraInfoOnlyCon = cs[i];

				} else if (args.length == 0) {
					noArgsCon = cs[i];
				}
			}

			if (beanInf == null && extraInfo == null && noArgsCon != null) {
				Object[] args = new Object[0];
				ret = (BeanDataStore) noArgsCon.newInstance(args);
			} else if (beanInf == null && extraInfo != null && extraInfoOnlyCon != null) {
				Object[] args = new String[1];
				args[0] = extraInfo;
				ret = (BeanDataStore) extraInfoOnlyCon.newInstance(args);
			} else if (beanInf != null && beanInfoArrayCon != null) {
				Object[] args=new Object[2];
				args[0]=beanInf;
				args[1]=extraInfo;
				ret = (BeanDataStore) beanInfoArrayCon.newInstance(args);
			}
			else if (beanInf != null && beanInfoCon != null) {
				Object[] args=new Object[2];
				args[0]=beanInf[0];
				args[1]=extraInfo;
				ret = (BeanDataStore) beanInfoCon.newInstance(args);
			}	
			else if (beanInf != null && extraInfo == null && classOnlyCon != null) {
				Object[] args = new Class[1];
				args[0] = beanInf[0].getBeanClass();
				ret = (BeanDataStore) classOnlyCon.newInstance(args);
			} 
			else {
				if (classAndExtraInfoCon != null) {
					Object[] args = new Object[2];
					args[0] = (beanInf == null ? null : beanInf[0].getBeanClass());
					args[1] = extraInfo;
					ret = (BeanDataStore) classAndExtraInfoCon.newInstance(args);
				} else if (classOnlyCon != null) {
					Object[] args = new Class[1];
					args[0] = (beanInf == null ? null : beanInf[0].getBeanClass());
					ret = (BeanDataStore) classOnlyCon.newInstance(args);
				} else if (extraInfoOnlyCon != null) {
					Object[] args = new String[1];
					args[0] = extraInfo;
					ret = (BeanDataStore) extraInfoOnlyCon.newInstance(args);
				} else if (noArgsCon != null) {
					Object[] args = new Object[0];
					ret = (BeanDataStore) noArgsCon.newInstance(args);
				}
			}
		} catch (Exception e) {
			MessageLog.writeErrorMessage("BeanDataStore.constructDataStore()", e, null);
		} catch (Error e) {
			MessageLog.writeErrorMessage("BeanDataStore.constructDataStore()", e, null);
		}	
		return ret;
	}

	/**
	 * returns the extra info string. The string can be any arbitrary piece of information used by subclasses to construct themselves.
	 */
	public String getExtraInfo() {
		return _extraInfo;
	}

	/**
	 * Sets the extra info string. The string can be any arbitrary piece of information used by subclasses to construct themselves.
	 */
	public void setExtraInfo(String string) {
		_extraInfo = string;
	}
	
	/**
	 * Build a criteria string for the specified row in the datastore. This is an empty method that subclasses can implement to allow custom bean datastores to retrieve data.
	 */
	public String buildCriteriaStringForRow(int row) {
		return null;	
	}	
	
	/**
	 * Retrieves the data in the DataStore by passing a criteria string built with buildCriteriaStringBuild. This is an empty method that subclasses can implement to allow custom bean datastores to retrieve data based on a selection criteria string.
	 */
	public void retrieve(String criteriaString) throws Exception{
		return;	
	}	

	/**
	 * Refresh the data row from the data in the bean.
	 * Data is not retrieved from physical storage unless the bean is an Entity bean.
	 * @param dataRow
	 * @param bean
	 * @throws DataStoreException
	 */
	public void refreshInternal(DSDataRow dataRow, Object bean) throws DataStoreException {
		dataRow.populateFromBean(getDescriptor(), bean, DataStoreBuffer.STATUS_NOT_MODIFIED);
	}

	/**
	 * Refresh the given data rows from the data in the given beans.
	 * Data is not retrieved from physical storage unless the bean is an Entity bean.
	 * @param dataRows
	 * @param beans
	 * @throws DataStoreException
	 */	
	public void refreshInternal(Collection dataRows, Collection beans) throws DataStoreException {
		Iterator iterDataRows =	dataRows.iterator();
		Iterator iterBeans =	beans.iterator();
		
		while (iterDataRows.hasNext() && iterBeans.hasNext())
			refreshInternal((DSDataRow) iterDataRows.next(), iterBeans.next());
	}
	
	/**
	 * Partially reloads the datastore buffer from the bean. Only columns name starting with prefix will be reloaded.
	 * @param row the row to refresh from the bean
	 * @param elementPrefix the column prefix to search for
	 * @throws DataStoreException
	 */
	public void partialRefreshInternal(int row, String elementPrefix) throws DataStoreException {
		DSDataRow dataRow=getDataRow(row);
		dataRow.populateFromBean(getDescriptor(), dataRow.getBean(), getRowStatus(row), elementPrefix);
	}	
	
	/**
	 * Copy changed data in the specified datastore row to its bean source.
	 * Data is not copied back to physical storage unless the bean is an Entity bean.
	 * Return true if an update was required.
	 * @param dataRow
	 * @return
	 * @throws DataStoreException
	 */
	public synchronized boolean updateInternal(DSDataRow dataRow) throws DataStoreException {
		if (isModified(dataRow)) {
			dataRow.copyDataToBean(getDescriptor());
			return true;
		} else
			return false;
	}

	/**
	 * Copy changed data in the specified datastore row to its bean source.
	 * Data is not copied back to physical storage unless the bean is an Entity bean.
	 * Return true if an update was required.
	 * @param row
	 * @return
	 * @throws DataStoreException
	 */
	public synchronized boolean updateInternal(int row) throws DataStoreException {
		return updateInternal(getDataRow(row));
	}

	/**
	 * Copy changed data in the specified datastore row to its bean source.
	 * Data is not copied back to physical storage unless the beans are Entity bean.
	 * Return true if an update was required.
	 * @param dataRows
	 * @return
	 * @throws DataStoreException
	 */
	public synchronized boolean updateInternal(Collection dataRows) throws DataStoreException {
		try {
			Iterator iter = dataRows.iterator();
			boolean result = false;
			while (iter.hasNext())
				result = updateInternal((DSDataRow) iter.next()) || result;

			return result;
		} catch (Exception e) {
			throw new DataStoreException("Unable to update data.", e);
		}
	}

	/**
	 * Insert the given beans into the given dataRows.
	 * Data is not copied back to physical storage unless the beans is an Entity bean.
	 * @param beans
	 * @param dataRows
	 * @throws DataStoreException
	 */
	public void insertInternal(Collection beans, Collection dataRows) throws DataStoreException  {
		
		try {
			DSDataStoreDescriptor d =	getDescriptor();
			Iterator iterDataRows =		dataRows.iterator();
			Iterator iterBeans =		beans.iterator();
		
			while (iterDataRows.hasNext() && iterBeans.hasNext()) {
				DSDataRow dataRow =	(DSDataRow) iterDataRows.next();
				Object bean =		iterBeans.next();
				dataRow.populateFromBean(d, bean, DataStoreBuffer.STATUS_NOT_MODIFIED);
			}
		} catch (Exception e) {
			throw new DataStoreException("Unable to copy data back from physical storage.", e);
		}
	}
	
	/**
	 * Refresh the data for the given rows. 
	 * @param row
	 * @param findBean - bean containing the findMethod
	 * @param findMethod
	 * @param findParams - parameters of the findMethod
	 * @throws DataStoreException
	 */
	public void refresh(Collection dataRows, Object findBean, Method findMethod) throws DataStoreException {
		Collection beans = getBeans(dataRows);
		try {
			if (findMethod != null) { 
				Object[] findParams = {beans}; 
				Collection newBeans = (Collection) findMethod.invoke(findBean, findParams);
				refreshInternal(dataRows, newBeans);
			}
		} catch (Exception e) {
			throw new DataStoreException("Unable to refresh the bean data from physical storage.", e);
		}
	}

	/**
	 * Retrieve bean data for the specified data rows.
	 * @param dataRow
	 * @param findBean - bean containing the findMethod
	 * @param findMethod
	 * @param findParams - parameters of the findMethod
	 * @throws DataStoreException
	 */
	public Collection find(Collection dataRows, Object findBean, Method findMethod) throws DataStoreException {
		Collection beans = getBeans(dataRows);
		try {
			Object[] storeParams = {beans};
			if (findMethod != null) {
				Collection result = (Collection) findMethod.invoke(findBean, storeParams);
				return result;
			} else
				return null;
		} catch (Exception e) {
			throw new DataStoreException("Unable to retrieve bean data from physical storage.", e);
		}
	}

	/**
	 * Copy changed data in the specified datastore rows to their bean source.
	 * Using the bean's store method and parameters, save the changed data back to 
	 * physical storage through the EJB.
	 * If the store method is null, do not call the store method.
	 * 
	 * @param dataRow
	 * @param storeBean - bean containing the storeMethod
	 * @param storeMethod
	 * @param storeParams - parameters of the storeMethod
	 * @throws DataStoreException
	 */
	public void update(Collection dataRows, Object storeBean, Method storeMethod) throws DataStoreException {
		updateInternal(dataRows);
		Collection beans = getBeans(dataRows);
		try {
			Object[] storeParams = {beans};
			if (storeMethod != null) {
				storeMethod.invoke(storeBean, storeParams);
			}
		} catch (Exception e) {
			throw new DataStoreException("Unable to store bean data back to physical storage.", e);
		}
	}

	/**
	 * Update all modified and inserted rows and optionally refresh the data in those rows
	 * if the a find method is given.
	 * @throws DataStoreException
	 */	
	public void update(Object bean, Method create, Method store, Method find) throws DataStoreException {
		Vector updated =	new Vector();
		Vector inserted =	new Vector();

		// create collections of updated and inserted rows
		for (int i=0;i<getRowCount();i++)
			if (isModified(i))
				updated.add(getDataRow(i));
			else if (isInserted(i))
				inserted.add(getDataRow(i));
		
		// do a bulk update and bulk insert
		update(updated, bean, store);
		insert(inserted, bean, create);
		
		// do a bulk refresh
		if (find != null) {
			refresh(updated, bean, find);
			refresh(inserted, bean, find);		
		}
	}

	/**
	 * Insert changed data in the given data rows to their bean sources.
	 * Using the bean's create method and parameters, save the changed data back to 
	 * physical storage.
	 * If the store method is null, do not call the store method.
	 * 
	 * @param dataRow
	 * @param storeBean - bean containing the storeMethod
	 * @param storeMethod
	 * @param storeParams - parameters of the storeMethod
	 * @throws DataStoreException
	 */
	public void insert(Collection dataRows, Object createBean, Method createMethod) throws DataStoreException {
		Iterator iter =			dataRows.iterator();
		Vector fieldNames =		new Vector();
		Vector fieldValues =	new Vector();
		
		while (iter.hasNext()) {
			DSDataRow dataRow = (DSDataRow) iter.next();
			fieldNames.add(getUpdateableFieldNames(dataRow));
			fieldValues.add(getUpdateableFieldValues(dataRow));
		}
		
		try {
			Object[] createParams = {fieldNames, fieldValues};
			if (createMethod != null) {
				Collection beans = (Collection) createMethod.invoke(createBean, createParams);
				insertInternal(beans, dataRows);
			}
		} catch (Exception e) {
			throw new DataStoreException("Unable to store bean data back to physical storage.", e);
		}
	}

	/**
	 * Delete the specified datastore row in physical storage.
	 * If the store method is null, do not call the store method.
	 * 
	 * @param row
	 * @param deleteBean - bean containing the deleteMethod
	 * @param deleteMethod
	 * @param deleteParams - parameters of the deleteMethod
	 * @throws DataStoreException
	 */
	public void delete(int row, Object deleteBean, Method deleteMethod) throws DataStoreException {
		try {
			Collection beans = new Vector();
			beans.add(getBean(row));
			Object[] deleteParams = {beans};
			if (deleteMethod != null) {
				if (!isInserted(row)) 				
					deleteMethod.invoke(deleteBean, deleteParams);
				deleteRow(row);
			}
		} catch (Exception e) {
			throw new DataStoreException("Unable to delete bean in physical storage.", e);
		}
	}

	/**
	 * Delete a row and its associated bean.
	 * If the row has no associated been (because it has just been inserted) then
	 * simply remove the row.
	 * @param row
	 */
	public void deleteRowBean(int row) throws DataStoreException {
		try {
			Object bean = getBean(row);
			if (bean != null) {
				Method remove = bean.getClass().getMethod("remove", null);
				remove.invoke(bean, null);
			}
		} catch (Exception e) {
			throw new DataStoreException(getCause(e).getMessage()); 
		}
		if (!deleteRow(row)) 
			throw new DataStoreException(
				"Row was deleted on database but the presentation row could not be deleted.");
	}
	
	/**
	 * Load entity or session bean data.  Session beans must have an interface similar to an entity bean's
	 * remote interface.
	 * 
	 * @param	home - home interface to an entity bean (or session bean equivalent)
	 * @param 	method - search method name
	 * @param	params - array with search method parameters
	 * @param	maxRows - Maximum number of rows to be loaded. 
	 * 			If maxRows &eq LOADALL or if omitted then there is no limit to the number of rows.
	 * 
	 * To change he template for this generated type comment go to
	 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
	 */

	public void load(Object bean, String methodName, Object[] params, int maxRows) throws DataStoreException
	{
		try {

			Method method = bean.getClass().getMethod(methodName, toClasses(params));
			Collection all = (Collection) method.invoke(bean, params);
			
			Iterator iterator = all.iterator();

			// Load all beans		
			int count = 0;
			while (iterator.hasNext()) {
				if (maxRows >= 0)
					if (++count > maxRows) break;
				insertRow(iterator.next());
			}

		} catch (Exception e) {
			if (getCause(e) == null)
				throw new DataStoreException("Error loading data", e);
			else
				throw new DataStoreException("Error loading data", getCause(e));			
		}
		
	}

	public void load(Object bean, int maxRows) throws DataStoreException {
		load(bean, "findAll", null, maxRows);	
	}
	
	public void load(Object bean) throws DataStoreException { 
		load(bean, LOAD_ALL);
	}

	/**
	 * Refresh this datastore (without performing an update).
	 * @throws DataStoreException
	 * @throws Exception
	 */
	public void reload(Object bean) throws DataStoreException, Exception {
		reset();
		load(bean);
	}


	/**
	 * Convert an array of objects into an array of their classes.
	 * @param array
	 * @return
	 */
	public static Class[] toClasses(Object[] array) {
		if (array == null) return null;
		
		Class[] result = new Class[array.length];
		for (int i = 0 ; i < array.length ; i++) {
			if (array[i] == null)
				result[i] = null;
			else
				result[i] = array[i].getClass(); 
		}
		
		return result;
	}

	/**
	 * Return the collection of beans from the collection of data rows.
	 * @param dataRows
	 * @return
	 */
	public Collection getBeans(Collection dataRows) throws DataStoreException {
		
		try {
			Vector result = new Vector();
			Iterator iter = dataRows.iterator();
		
			while (iter.hasNext()) {
				DSDataRow dataRow = (DSDataRow) iter.next();
				result.add(dataRow.getBean());
			}
			return result;
			
		} catch (Exception e) {
			throw new DataStoreException("Error collecting beans.", e);
		}
	
	}
	
	/**
	 * Get the number of updateable columns for the given dataRow.
	 * @param dataRow
	 * @return
	 */
	public int getUpdateableColCount(DSDataRow dataRow) {
		DSDataStoreDescriptor d = getDescriptor();
		
		int updateableColCount = 0;
		for (int i = 0; i< d.getColumnCount(); i++) {
			DSColumnDescriptor col = d.getColumn(i);
			if (col.isUpdateable())
				updateableColCount++;
		}

		return updateableColCount;		
	}
	
	/**
	 * Get the updateable field names for the given DSDataRow.
	 * 
	 * @param dataRow
	 * @throws DataStoreException
	 */
	public String[] getUpdateableFieldNames(DSDataRow dataRow) throws DataStoreException {
		
		DSDataStoreDescriptor d =	getDescriptor();
		int updateableColCount =	getUpdateableColCount(dataRow);
		String [] fieldNames =		new String[updateableColCount];

		int colIndex = 0;		
		for (int i = 0; i < d.getColumnCount(); i++) {
			DSColumnDescriptor col = d.getColumn(i);
			if (col.isUpdateable())
				fieldNames[colIndex++] = getColumnName(i).toLowerCase();
		}
		
		return fieldNames;
	}	

	/**
	 * Get the updateable field names for the given DSDataRow.
	 * 
	 * @param dataRow
	 * @throws DataStoreException
	 */
	public Object[] getUpdateableFieldValues(DSDataRow dataRow) throws DataStoreException {
		
		DSDataStoreDescriptor d =	getDescriptor();
		int updateableColCount =	getUpdateableColCount(dataRow);
		Object[] fieldValues =		new Object[updateableColCount];
		
		int colIndex = 0;
		for (int i = 0; i < d.getColumnCount(); i++) {
			DSColumnDescriptor col = d.getColumn(i);

			int type = col.getType();
			Object o = dataRow.getCurrentData()[i];	// Used to get access to _DSDataRow
			Class propertyClass = col.getPropertyClass();

			if (type == DataStoreBuffer.DATATYPE_STRING) {
				if (propertyClass == Character.TYPE) {
					if (o == null)
						o = new Character(' ');
					else {
						String s = (String) o;
						if (s.length() == 0)
							o = new Character(' ');
						else
							o = new Character(s.charAt(0));
					}
				}
			} else if (type == DataStoreBuffer.DATATYPE_SHORT) {
				if (propertyClass == Boolean.TYPE) {
					boolean val = false;
					Short s = (Short) o;
					if (s != null && s.shortValue() != 0)
						val = true;
					o = new Boolean(val);
				} else if (o == null)
					o = new Short((short) 0);
			} else if (o == null && propertyClass.isPrimitive()) {
				if (type == DataStoreBuffer.DATATYPE_INT)
					o = new Integer(0);
				else if (type == DataStoreBuffer.DATATYPE_DOUBLE)
					o = new Double(0);
				else if (type == DataStoreBuffer.DATATYPE_FLOAT)
					o = new Float(0);
				else if (type == DataStoreBuffer.DATATYPE_LONG)
					o = new Long(0);
			}

			if (col.isUpdateable())
				fieldValues[colIndex++] = o;
		}
		
		return fieldValues;
	}	

	/**
	 * Determine if the given row has been modified.
	 * @param dataRow
	 * @return
	 */	
	public boolean isModified(DSDataRow dataRow) {
		int status = dataRow.getRowStatus();
		boolean isModified = status == DataStoreBuffer.STATUS_MODIFIED;
		return isModified;
	}
	
	/**
	 * Determine if the given row has been modified.
	 * @param row
	 * @return
	 */	
	public boolean isModified(int row) {
		return isModified(getDataRow(row));
	}

	/**
	 * Determine if the given row has been inserted.
	 * @param dataRow
	 * @return
	 */	
	public boolean isInserted(DSDataRow dataRow) {
		int status = dataRow.getRowStatus();
		boolean isInserted = status == DataStoreBuffer.STATUS_NEW ||
			status == DataStoreBuffer.STATUS_NEW_MODIFIED;
		return isInserted;
	}
	
	/**
	 * Determine if the given row has been inserted.
	 * @param row
	 * @return
	 */	
	public boolean isInserted(int row) {
		return isInserted(getDataRow(row));
	}
	
	/**
	 * Utility method to get the data store descriptor.
	 */	
	public DSDataStoreDescriptor getDescriptor() {
		return super.getDescriptor();
	}

	/**
	 * Utility method to retrieve a data row.
	 */	
	public DSDataRow getDataRow(int rowNo) {
		return super.getDataRow(rowNo);
	}

	/**
	 * Utility method to retrieve an error cause.
	 * @param e
	 * @return
	 */
	public static Throwable getCause(Exception e) {
		if (e instanceof InvocationTargetException) 
			return Util.getCause(e);
		else
			return e;
	}
	
	/**
	 * Returns true if the class is a collection, array, vector or hashtable
	 */
	public static boolean isCollection(Class c) {
		if (c.isArray())
			return true;
		else if (Util.instanceOf(Vector.class,c))
			return true;
		else if (Util.instanceOf(Hashtable.class,c))
			return true;
		else if (Util.instanceOf(Collection.class,c))
			return true;
		else
			return false;
		
	}	

	private static BeanClassInfo[] getBeanInfo(String beanString) throws ClassNotFoundException {
		if (beanString == null)
			return null;
		int pos=beanString.indexOf(",");
		if (pos > -1) {
			ArrayList l=new ArrayList();
			StringTokenizer tok=new StringTokenizer(beanString,",");
			int index=0;
			while (tok.hasMoreTokens()) {
				String beanEntry=tok.nextToken();
				l.add(getInfoForBean(beanEntry,index));
				index++;
			}
			
			if (l.size() == 0)
				return null;
			else {
				BeanClassInfo ret[] = new BeanClassInfo[l.size()];
				l.toArray(ret);
				return ret;
			}	
		}
		else {
			BeanClassInfo inf=getInfoForBean(beanString,0);
			BeanClassInfo ret[] = {inf};
			return ret;
		}	
	
	}
	
	private static BeanClassInfo getInfoForBean(String beanString, int index) throws ClassNotFoundException {
		beanString=beanString.trim();
		int pos=beanString.indexOf(" ");
		String bean=null;
		String alias=null;
		if (pos > -1) {
			bean=beanString.substring(0,pos);
			alias=beanString.substring(pos + 1);
		}	
		else {
			bean=beanString;
			int ndx=beanString.lastIndexOf(".");
			if (ndx == -1)
				alias=bean;
			else
				alias=bean.substring(ndx + 1);
		}	
		Class c = Class.forName(bean);
		BeanClassInfo info=new BeanClassInfo();
		info.setBeanClass(c);
		info.setAlias(alias);
		info.setIndex(index);
		return info;
	}	
	
	/**
	 * Creates a new BeanInfo object, Framework method, do not call directly
	 */
	protected static BeanClassInfo newBeanInfo(Class beanClass, String alias, int index) {
		BeanClassInfo inf= new BeanClassInfo();
		inf.setBeanClass(beanClass);
		inf.setAlias(alias);
		inf.setIndex(index);
		return inf;
	}	

	
	/**
	 * Sets the representation of boolean values in this datastore
	 * @param type The DataStore DataType to store booleans as
	 * @param trueValue An object representing the true value
	 * @param falseValue An object representing the false value
	 */
	public void setBooleanDataType(int type, Object trueValue, Object falseValue) {
		getDescriptor().setBooleanRepresentation(type,trueValue, falseValue);	
	}	
	
	protected static String lowerCaseFirstLetter(String st) {
		if (st == null)
			return st;
		boolean lowerNext = false;
		StringBuffer ret = new StringBuffer(st.length());
		for (int i = 0; i < st.length(); i++) {
			char c = st.charAt(i);
			if (lowerNext) {
				c = Character.toLowerCase(c);
				lowerNext = false;
			}
			if (c == '.')
				lowerNext = true;
			ret.append(c);
		}
		return ret.toString();
	}
	/**
	 * True to lower case the first letter of each attribute name. Otherwise it is the same case as the get method.
	 */
	public boolean getLowerCaseFirstLetter() {
		return _lowerCaseFirstLetter;
	}
	/**
	 * True to lower case the first letter of each attribute name. Otherwise it is the same case as the get method.
	 */

	public void setLowerCaseFirstLetter(boolean lowerCaseFirstLetter) {
		_lowerCaseFirstLetter = lowerCaseFirstLetter;
	}
	

	
	

}
