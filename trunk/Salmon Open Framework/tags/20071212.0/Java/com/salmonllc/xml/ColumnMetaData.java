package com.salmonllc.xml;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/xml/ColumnMetaData.java $
//$Author: Len $ 
//$Revision: 20 $ 
//$Modtime: 6/17/03 2:13p $ 
/////////////////////////
/**
 * This object serves the purpose of the header of each column in the XML File Creation date: (6/19/01 3:40:47 PM)
 * @author  Deepak Agarwal
 */
public class ColumnMetaData
{
	private String fieldComponent = null;
	private String fieldComponentType = null;

	private String fieldInternalName = null;
	private String fieldColumnName = null;
	private String fieldColumnType = null;

	private boolean fieldNullable = false;
	private boolean fieldPrimaryKey = false;
	private boolean fieldAutoIncrement = false;

	private boolean fieldSearchDisplay = false;
	private boolean fieldDetailDisplay = false;
	private boolean fieldListDisplay = false;

	private String fieldTable = null;
	private String fieldSchema = null;
    private boolean fieldNotBound = true;
	private String fieldOnClick = null;

	private String fieldMode = null;
	private String fieldCaption = null;
	private boolean fieldLocked = false;
	private boolean fieldPrecedence = false;
	private boolean fieldExactMatch = false;
	private boolean fieldLeadingWildCard = false;
	private boolean fieldCaseSensitive = false;
	private String fieldFormat = null;
	private String fieldMaxLength = null;
	private String fieldSize = null;
	private String fieldHref = null;
	private boolean fieldMandatory = true;
	private Options fieldValues = null;
	private Options fieldParms = null;

	private boolean fieldAdvanceSearch = true;

	private boolean fieldBucket = false;
	private boolean fieldUpdateable = true;
	private boolean fieldSameRow = false;
	private boolean fieldReadOnly = false;
	private boolean fieldOrderBy = false;
	private String fieldDefaultValue = "";
	private String fieldImageFile = "";
	private String fieldJoinTo = "";

	/**
	 * Column constructor comment.
	 */
	public ColumnMetaData() {
		super();
	}
	/**
	 * Gets the column Caption
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @return java.lang.String
	 */
	public java.lang.String getCaption() {
		return fieldCaption;
	}
	/**
	 * Gets the column name.
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @return java.lang.String
	 */
	public java.lang.String getColumnName() {
		return fieldColumnName;
	}
	/**
	 * Gets the Column Type
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @return java.lang.String
	 */
	public java.lang.String getColumnType() {
		return fieldColumnType;
	}
	/**
	 * Gets the component used by Column
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @return java.lang.String
	 */
	public java.lang.String getComponent() {
		return fieldComponent;
	}
	/**
	 * Gets the Column Component Type
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @return java.lang.String
	 */
	public java.lang.String getComponentType() {
		return fieldComponentType;
	}
	/**
	 * Gets the Column default value
	 * Creation date: (8/10/01 2:02:21 PM)
	 * @return java.lang.String
	 */
	public java.lang.String getDefaultValue() {
		return fieldDefaultValue;
	}
	/**
	 * Gets the format used by the Column
	 * Creation date: (7/23/01 10:10:14 AM)
	 * @return java.lang.String
	 */
	public java.lang.String getFormat() {
		return fieldFormat;
	}
	/**
	 * Gets the link used by the column
	 * Creation date: (7/25/01 11:06:03 AM)
	 * @return java.lang.String
	 */
	public java.lang.String getHref() {
		return fieldHref;
	}
	/**
	 * Gets the Image file name for the column
	 * Creation date: (8/16/01 11:31:40 AM)
	 * @return java.lang.String
	 */
	public java.lang.String getImageFile() {
		return fieldImageFile;
	}
	/**
	 * Gets the internal name for the component
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @return java.lang.String
	 */
	public java.lang.String getInternalName() {
		return fieldInternalName;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (8/2/2002 10:35:23 AM)
	 * @return java.lang.String
	 */
	public java.lang.String getJoinTo() {
		return fieldJoinTo;
	}
	/**
	 * Gets the Mode of the Column.
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @return java.lang.String
	 */
	public java.lang.String getMode() {
		return fieldMode;
	}
	/**
	 * Gets the params, options, for the columns
	 * Creation date: (8/1/2002 3:21:59 PM)
	 * @return com.salmonllc.xml.Options
	 */
	public Options getParms() {
		return fieldParms;
	}
	/**
	 * Gets the Schema Name for the Column
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @return java.lang.String
	 */
	public java.lang.String getSchema() {
		return fieldSchema;
	}
	/**
	 * Gets the table name for the column
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @return java.lang.String
	 */
	public java.lang.String getTable() {
		return fieldTable;
	}
	/**
	 * Gets the values, options, for the columns
	 * Creation date: (7/26/01 2:02:04 PM)
	 * @return com.salmonllc.xml.Values
	 */
	public Options getValues() {
		return fieldValues;
	}
	/**
	 * Gets the flag ' is advance search allowed'
	 * Creation date: (7/30/01 11:11:39 AM)
	 * @return boolean
	 */
	public boolean isAdvanceSearch() {
		return fieldAdvanceSearch;
	}
	/**
	 * Gets the flag if the column is just a bucket not a column from a database table
	 * Creation date: (8/10/01 11:14:23 AM)
	 * @return boolean
	 */
	public boolean isBucket() {
		return fieldBucket;
	}
	/**
	 * Gets the flag if the search should be case sensitive or not
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @return boolean
	 */
	public boolean isCaseSensitive() {
		return fieldCaseSensitive;
	}
	/**
	 * Gets the flag if the column is used in Detail Form
	 * Creation date: (7/25/01 10:49:31 AM)
	 * @return boolean
	 */
	public boolean isDetailDisplay() {
		return fieldDetailDisplay;
	}
	/**
	 * Gets the flag if the search perfomed on this column should use Exact match or not
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @return boolean
	 */
	public boolean isExactMatch() {
		return fieldExactMatch;
	}
	/**
	 * Gets the flag if the column should be used with wild card search or not
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @return java.lang.String
	 */
	public  boolean isLeadingWildCard() {
		return fieldLeadingWildCard;
	}
	/**
	 * Gets the flag if the column is used in List Form
	 * Creation date: (7/25/01 10:49:31 AM)
	 * @return boolean
	 */
	public boolean isListDisplay() {
		return fieldListDisplay;
	}
	/**
	 * Gets the flag if column is locked
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @return boolean
	 */
	public boolean isLocked() {
		return fieldLocked;
	}
	/**
	 * Gets the flag if column is mandatory
	 * Creation date: (7/25/01 4:02:56 PM)
	 * @return boolean
	 */
	public boolean isMandatory() {
		return fieldMandatory;
	}
	/**
	 * Gets the flag if the column is Nullable
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @return boolean
	 */
	public boolean isNullable() {
		return fieldNullable;
	}
	/**
	 * Gets the precendence order of column
	 * Creation date: (8/10/01 11:34:34 AM)
	 * @return boolean
	 */
	public boolean isPrecedence() {
		return fieldPrecedence;
	}
	/**
	 * Gets the flag if the column is a part of primary key or not
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @return boolean
	 */
	public boolean isPrimaryKey() {
		return fieldPrimaryKey;
	}
	/**
	 * Gets the flag if column is read only or not
	 * Creation date: (8/10/01 11:32:06 AM)
	 * @return boolean
	 */
	public boolean isReadOnly() {
		return fieldReadOnly;
	}
	/**
	 * Gets the flag if column is added to "ORDER BY" SQL clause
	 * Creation date: (8/10/01 11:32:06 AM)
	 * @return boolean
	 */
	public boolean isOrderBy() {
		return fieldOrderBy;
	}
	/**
	 * Gets the falg if column is displayed in same row or not
	 * Creation date: (8/10/01 11:14:23 AM)
	 * @return boolean
	 */
	public boolean isSameRow() {
		return fieldSameRow;
	}
	/**
	 * Gets the flag if column should be dispalyed in search form or not
	 * Creation date: (7/25/01 10:49:31 AM)
	 * @return boolean
	 */
	public boolean isSearchDisplay() {
		return fieldSearchDisplay;
	}
	/**
	 * Gets the flag if column is updateable
	 * Creation date: (8/10/01 11:14:23 AM)
	 * @return boolean
	 */
	public boolean isUpdateable() {
		return fieldUpdateable;
	}
	/**
	 * Sets the advance search link
	 * Creation date: (7/30/01 11:11:39 AM)
	 * @param newAdvanceSearch boolean
	 */
	public void setAdvanceSearch(boolean newAdvanceSearch) {
		fieldAdvanceSearch = newAdvanceSearch;
	}
	/**
	 * Sets the flag if column is a bucket
	 * Creation date: (8/10/01 11:14:23 AM)
	 * @param newBucket boolean
	 */
	public void setBucket(boolean newBucket) {
		fieldBucket = newBucket;
	}
	/**
	 * Sets the caption for a column.
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @param newCaption java.lang.String
	 */
	public void setCaption(java.lang.String newCaption) {
		fieldCaption = newCaption;
	}
	/**
	 * Sets the flag on a column if the search perfomed is case sensitive or not
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @param newCaseSensitive boolean
	 */
	public void setCaseSensitive(boolean newCaseSensitive) {
		fieldCaseSensitive = newCaseSensitive;
	}
	/**
	 * Sets the column name
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @param newColumnName java.lang.String
	 */
	public void setColumnName(java.lang.String newColumnName) {
		fieldColumnName = newColumnName;
	}
	/**
	 * Sets the column type
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @param newColumnType java.lang.String
	 */
	public void setColumnType(java.lang.String newColumnType) {
		fieldColumnType = newColumnType;
	}
	/**
	 * Sets the component to be used by column
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @param newComponent java.lang.String
	 */
	public void setComponent(java.lang.String newComponent) {
		fieldComponent = newComponent;
	}
	/**
	 * Sets the component type for the column
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @param newComponentType java.lang.String
	 */
	public void setComponentType(java.lang.String newComponentType) {
		fieldComponentType = newComponentType;
	}
	/**
	 * Sets the default value for the column
	 * Creation date: (8/10/01 2:02:21 PM)
	 * @param newDefaultValue java.lang.String
	 */
	public void setDefaultValue(java.lang.String newDefaultValue) {
		fieldDefaultValue = newDefaultValue;
	}
	/**
	 * Sets the detail display flag for the column
	 * Creation date: (7/25/01 10:49:31 AM)
	 * @param newDetailDisplay boolean
	 */
	public void setDetailDisplay(boolean newDetailDisplay) {
		fieldDetailDisplay = newDetailDisplay;
	}
	/**
	 * Sets the exact match flag for the column
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @param newExactMatch boolean
	 */
	public void setExactMatch(boolean newExactMatch) {
		fieldExactMatch = newExactMatch;
	}
	/**
	 * Sets the format to be used by the column
	 * Creation date: (7/23/01 10:10:14 AM)
	 * @param newFormat java.lang.String
	 */
	public void setFormat(java.lang.String newFormat) {
		fieldFormat = newFormat;
	}
	/**
	 * Sets the link if used by the column for display
	 * Creation date: (7/25/01 11:06:03 AM)
	 * @param newHref java.lang.String
	 */
	public void setHref(java.lang.String newHref) {
		fieldHref = newHref;
	}
	/**
	 * Sets the Image file name if used by column
	 * Creation date: (8/16/01 11:31:40 AM)
	 * @param newImageFile java.lang.String
	 */
	public void setImageFile(java.lang.String newImageFile) {
		fieldImageFile = newImageFile;
	}
	/**
	 * Sets the internal name of the column
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @param newInternalName java.lang.String
	 */
	public void setInternalName(java.lang.String newInternalName) {
		fieldInternalName = newInternalName;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (8/2/2002 10:35:23 AM)
	 * @param newJoinTo java.lang.String
	 */
	public void setJoinTo(java.lang.String newJoinTo) {
		fieldJoinTo = newJoinTo;
	}
	/**
	 * Sets the leading wild card flag for the column
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @param newLeadingWildCard java.lang.String
	 */
	public void setLeadingWildCard(boolean newLeadingWildCard) {
		fieldLeadingWildCard = newLeadingWildCard;
	}
	/**
	 * Sets the list display flag for the column
	 * Creation date: (7/25/01 10:49:31 AM)
	 * @param newListDisplay boolean
	 */
	public void setListDisplay(boolean newListDisplay) {
		fieldListDisplay = newListDisplay;
	}
	/**
	 * Sets the licked flag for the column
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @param newLocked boolean
	 */
	public void setLocked(boolean newLocked) {
		fieldLocked = newLocked;
	}
	/**
	 * Sets the mandatory flag for the columns
	 * Creation date: (7/25/01 4:02:56 PM)
	 * @param newMandatory boolean
	 */
	public void setMandatory(boolean newMandatory) {
		fieldMandatory = newMandatory;
	}
	/**
	 * Sets the mode for the column
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @param newMode java.lang.String
	 */
	public void setMode(java.lang.String newMode) {
		fieldMode = newMode;
	}
	/**
	 * Sets the flag if the column is nullable
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @param newNullable boolean
	 */
	public void setNullable(boolean newNullable) {
		fieldNullable = newNullable;
	}
	/**
	 * Sets the params, options, for the columns
	 * Creation date: (8/1/2002 3:21:59 PM)
	 * @param newParms com.salmonllc.xml.Options
	 */
	public void setParms(Options newParms) {
		fieldParms = newParms;
	}
	/**
	 * Sets the precedence for the column while searching
	 * Creation date: (8/10/01 11:34:34 AM)
	 * @param newPrecedence boolean
	 */
	public void setPrecedence(boolean newPrecedence) {
		fieldPrecedence = newPrecedence;
	}
	/**
	 * Sets if the column is a part of primary key
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @param newPrimaryKey boolean
	 */
	public void setPrimaryKey(boolean newPrimaryKey) {
		fieldPrimaryKey = newPrimaryKey;
	}
	/**
	 * Sets the flag if the column is read only
	 * Creation date: (8/10/01 11:32:06 AM)
	 * @param newReadOnly boolean
	 */
	public void setReadOnly(boolean newReadOnly) {
		fieldReadOnly = newReadOnly;
	}

	/**
	 * Sets the flag if the column will be in the "ORDER BY" clause
	 * Creation date: (8/10/01 11:32:06 AM)
	 * @param newOrderByColumn boolean
	 */
	public void setOrderByColumn(boolean newOrderByColumn) {
		fieldOrderBy = newOrderByColumn;
	}
	/**
	 * Sets the flag if column is part if the same row as the pervious component was
	 * Creation date: (8/10/01 11:14:23 AM)
	 * @param newSameRow boolean
	 */
	public void setSameRow(boolean newSameRow) {
		fieldSameRow = newSameRow;
	}
	/**
	 * sets the schema, if present for the column name
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @param newSchema java.lang.String
	 */
	public void setSchema(java.lang.String newSchema) {
		fieldSchema = newSchema;
	}
	/**
	 * Sets the search display flag for the column
	 * Creation date: (7/25/01 10:49:31 AM)
	 * @param newSearchDisplay boolean
	 */
	public void setSearchDisplay(boolean newSearchDisplay) {
		fieldSearchDisplay = newSearchDisplay;
	}
	/**
	 * Sets the table name for the column
	 * Creation date: (7/20/01 12:09:40 PM)
	 * @param newTable java.lang.String
	 */
	public void setTable(java.lang.String newTable) {
		fieldTable = newTable;
	}
	/**
	 * Sets the flag if column is updateable
	 * Creation date: (8/10/01 11:14:23 AM)
	 * @param newUpdateable boolean
	 */
	public void setUpdateable(boolean newUpdateable) {
		fieldUpdateable = newUpdateable;
	}
	/**
	 * Sets the options for a column - this can be from anywhere
	 * Creation date: (7/26/01 2:02:04 PM)
	 * @param newValues com.salmonllc.xml.Values
	 */
	public void setValues(Options newValues) {
		fieldValues = newValues;
	}

	public String getMaxLength() {
		return fieldMaxLength;
	}

	public void setMaxLength(String fieldMaxLength) {
		this.fieldMaxLength = fieldMaxLength;
	}

	public String getSize() {
		return fieldSize;
	}

	public void setSize(String newSize) {
		this.fieldSize = newSize;
	}

	public boolean isAutoIncrement() {
		return fieldAutoIncrement;
	}

	public void setAutoIncrement(boolean fieldAutoIncrement) {
		this.fieldAutoIncrement = fieldAutoIncrement;
	}

    public boolean isNotBound() {
        return fieldNotBound;
    }

    public void setNotBound(boolean fieldNotBound) {
        this.fieldNotBound = fieldNotBound;
    }

	public String getOnClick() {
		return fieldOnClick;
	}

	public void setOnClick(String onClick) {
		this.fieldOnClick = onClick;
	}
}


