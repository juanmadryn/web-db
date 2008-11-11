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
package com.salmonllc.html;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlTable.java $
//$Author: Dan $
//$Revision: 47 $
//$Modtime: 4/13/04 9:52a $
/////////////////////////

import com.salmonllc.properties.Props;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.TwoObjectContainer;
import com.salmonllc.util.Vector2D;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * This component will generate an HTML table. It serves as a container and layout manager for other components.
 */

public class HtmlTable extends HtmlContainer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7718050559566347113L;
	public static final int SIZE_PERCENT = 0;
	public static final int SIZE_PIXELS = 1;

	public static final String ALIGN_LEFT = "LEFT";
	public static final String ALIGN_CENTER = "CENTER";
	public static final String ALIGN_RIGHT = "RIGHT";
	public static final String ALIGN_NONE = "";

	public static final String VALIGN_BASELINE = "BASELINE";
	public static final String VALIGN_BOTTOM = "BOTTOM";
	public static final String VALIGN_MIDDLE = "MIDDLE";
	public static final String VALIGN_TOP = "TOP";
	public static final String VALIGN_NONE = "";

	private int _sizeOption = SIZE_PERCENT;
	private int _containerWidth = -1;
	private int _containerHeight = -1;

	private Vector2D _componentsVec2D = new Vector2D();
	private Vector<Object> _columnWidth = new Vector<Object>();

	private Vector<Object> _columnHeight = new Vector<Object>();

	private Vector<HtmlTableRowProperties> _rowProps;
	private boolean _allowNullCells = true;
	private String _align;
	private int _border = -1;
	private String _bgColor = "";
	private int _cellPadding = -1;
	private int _cellSpacing = -1;
	private String _theme;
	private boolean _correctColSpan = true;
	private String _defaultRowStyleClassName;

	// for addDisplay
	private Hashtable<String, HtmlComponent> _compLookUpHash = new Hashtable<String, HtmlComponent>();
	private int _currColVal = 0;
	private int _currRowVal = -1;
	private final static String CAPTION_COMP_HASH_KEY = "Caption_comp_";
	private final static String SEARCH_COMP_HASH_KEY = "Search_comp_";

	/**
	 * Constructs a new HTMLTable.
	 * @param name Each component on a page must have a unique name.
	 * @param p The page that the table will be added to.
	 */
	public HtmlTable(String name, HtmlPage p) {
		this(name, null, p);
	}

	/**
	 * Constructs a new HTMLTable.
	 * @param name Each component on a page must have a unique name.
	 * @param theme The theme to use for loading properties
	 * @param p The page that the table will be added to.
	 */
	public HtmlTable(String name, String theme, HtmlPage p) {
		super(name, p);
		setTheme(theme);
	}

	/**
	 * This method should not be used in this type of container. Instead use setComponentAt().
	 */
	public void add(HtmlComponent comp) {
		return;
	}

	public boolean executeEvent(int eventType) throws Exception {
		if (eventType == HtmlComponent.EVENT_OTHER) {
			// sr 10-15-2000
			int rowCount = _componentsVec2D.getRowCount();
			int colCount = _componentsVec2D.getColumnCount();
			Object o = null;
			HtmlComponent h = null;
			//
			for (int i = 0; i < rowCount; i++) {
				for (int j = 0; j < colCount; j++) {
					o = _componentsVec2D.elementAt(i, j);
					if (o != null) {
						if (o instanceof TwoObjectContainer) {
							h = (HtmlComponent) ((TwoObjectContainer) o).getObject1();
						} else {
							h = (HtmlComponent) o;
						}
						if (h != null) {
							if (!h.executeEvent(eventType)) {
								return false;
							}
						} else {
							return true;
						}
					}
				}
			}
		} else if (_submit != null && eventType == HtmlComponent.EVENT_SUBMIT) {
			boolean retVal = _submit.executeEvent(eventType);
			_submit = null;
			return retVal;
		}
		return true;
	}

	public void generateHTML(java.io.PrintWriter p, int rowNo) throws Exception {		
		if (!getVisible())
			return;
		if (_center)
			p.print("<CENTER>");
		String tableHeading = "<TABLE";
		if (_containerWidth > -1) {
			tableHeading += " WIDTH=\"" + _containerWidth;
			if (_sizeOption == SIZE_PERCENT) {
				tableHeading += "%";
			}
			tableHeading += "\"";
		}
		if (_containerHeight > -1)
			tableHeading += " HEIGHT=\"" + _containerHeight + "\"";
		if (_border > -1)
			tableHeading += " BORDER=\"" + _border + "\"";
		if (_bgColor != null) {
			if (!_bgColor.equals("")) {
				tableHeading += " BGCOLOR=\"" + _bgColor + "\"";
			}
		}
		if (_cellPadding > -1)
			tableHeading += " CELLPADDING=\"" + _cellPadding + "\"";
		if (_cellSpacing > -1)
			tableHeading += " CELLSPACING=\"" + _cellSpacing + "\"";
		if (_align != null) {
			if (_align.equals(ALIGN_LEFT) || _align.equals(ALIGN_RIGHT) || _align.equals(ALIGN_CENTER))
				tableHeading += " align=\"" + _align + "\"";
		}
		tableHeading += ">";
		p.println(tableHeading);
		StringBuffer td = new StringBuffer();
		StringBuffer tr = new StringBuffer();
		HtmlTableRowProperties rowProps = null;
		// sr 10-15-2000
		int rowCount = _componentsVec2D.getRowCount();
		int colCount = _componentsVec2D.getColumnCount();
		Object o = null;
		HtmlComponent comp = null;
		HtmlTableCellProperties props = null;
		TwoObjectContainer cont = null;
		//		
		for (int i = 0; i < rowCount; i++) {
			tr.setLength(0);
			rowProps = getRowProperty(i);
			if (rowProps != null) {
				if (!rowProps.getVisible())
					continue;
				tr.append("\t<TR");
				if (rowProps.getAlign() != null)
					if (!rowProps.getAlign().equals(ALIGN_NONE))
						tr.append(" ALIGN=\"" + rowProps.getAlign() + "\"");
				if (rowProps.getBackgroundColor() != null)
					if (!rowProps.getBackgroundColor().equals(""))
						tr.append(" BGCOLOR=\"" + rowProps.getBackgroundColor() + "\"");
				String rowClass = _defaultRowStyleClassName;
				if (rowProps.getRowClass() != null)
					if (!rowProps.getRowClass().equals(""))
						rowClass = rowProps.getRowClass();
				if (rowClass != null)
					tr.append(" CLASS=\"" + rowClass + "\"");
				if (!rowProps.getWrapStyle())
					tr.append(" NOWRAPSTYLE");
				tr.append(">");
			} else {

				tr.append("<TR");
				if (_defaultRowStyleClassName != null) {
					tr.append(" CLASS=\"");
					tr.append(_defaultRowStyleClassName);
					tr.append("\"");
				}
				tr.append(">");
			}

			//		p.println("<TR>");
			p.println(tr.toString());
			for (int j = 0; j < colCount; j++) {
				td.setLength(0);
				if (_allowNullCells) {
					td.append("\t\t<TD");
				}
				o = _componentsVec2D.elementAt(i, j);
				// clear values out
				comp = null;
				props = null;
				//
				if (o != null) {
					if (!_allowNullCells) {
						td.append("\t\t<TD");
					}
					if (o instanceof HtmlComponent)
						comp = (HtmlComponent) o;
					else {
						cont = (TwoObjectContainer) o;
						comp = (HtmlComponent) cont.getObject1();
						props = (HtmlTableCellProperties) cont.getObject2();
					}
				} else {
					if (!_allowNullCells) {
						continue;
					}
				}
				if (props != null) {
					if (props.getAlign() != null)
						if (!props.getAlign().equals(ALIGN_NONE))
							td.append(" ALIGN=\"" + props.getAlign() + "\"");
					if (props.getVertAlign() != null)
						if (!props.getVertAlign().equals(VALIGN_NONE))
							td.append(" VALIGN=\"" + props.getVertAlign() + "\"");
					if (props.getBackgroundColor() != null)
						if (!props.getBackgroundColor().equals(""))
							td.append(" BGCOLOR=\"" + props.getBackgroundColor() + "\"");
					if (props.getColumnSpan() != 1) {
						td.append(" COLSPAN=\"" + props.getColumnSpan() + "\"");
						// BUG:
						// The code that follows increments j one too many because of
						// the j++ in the continuation of the loop.  The fix is
						//	j += props.getColumnSpan() - 1;
						// To avoid destabilization the bug is temporarily left in place.
						// Added 11/22/00: option to correct bug based on boolean flag
						if (_correctColSpan)
							j += props.getColumnSpan() - 1;
						else
							j += props.getColumnSpan();
					}
					if (props.getRowSpan() != 1) {
						td.append(" ROWSPAN=\"" + props.getRowSpan() + "\"");
					}
					if (props.getCellHeight() != -1) {
						td.append(" HEIGHT=\"" + props.getCellHeight());
						// get column size option
						if (props.getCellHeightSizeOption() == SIZE_PERCENT)
							td.append("%");
						td.append("\"");
					}
					if (props.getOnClick() != null)
						td.append(" ONCLICK=\"" + props.getOnClick() + "\"");
                    if (props.getOnMouseDown() != null)
                        td.append(" ONMOUSEDOWN=\"" + props.getOnMouseDown() + "\"");
					if (props.getOnMouseOver() != null)
						td.append(" ONMOUSEOVER=\"" + props.getOnMouseOver() + "\"");
					if (props.getOnMouseOut() != null)
						td.append(" ONMOUSEOUT=\"" + props.getOnMouseOut() + "\"");
                    if (props.getOnMouseUp() != null)
                        td.append(" ONMOUSEUP=\"" + props.getOnMouseUp() + "\"");
					if (props.getStyle() != null && props.getStyle().getStyleName() != null && !props.getStyle().getStyleName().trim().equals("")) {
						td.append(" CLASS=\"" + props.getStyle().getStyleName() + "\"");
					}
					if (props.getCellWidth() != -1) {
						td.append(" WIDTH=\"" + props.getCellWidth());
						// get column size option
						if (props.getCellWidthSizeOption() == SIZE_PERCENT)
							td.append("%");
						td.append("\"");
					}
					if (!props.getWrap())
						td.append(" NOWRAP");
				}				
				// column width
				int width = getColumnWidth(j);
				if (width > 0) {
					td.append(" WIDTH=\"" + width);
					// get size option
					if (_sizeOption == SIZE_PERCENT)
						td.append("%");
					td.append("\"");
				}
				// column height
				int height = getColumnHeight(j);
				if (height > 0) {
					td.append(" HEIGHT=\"" + height);
					// get size option
					if (_sizeOption == SIZE_PERCENT)
						td.append("%");
					td.append("\"");
				}
				td.append('>');
				p.println(td.toString());
				if (comp != null)
					comp.generateHTML(p, rowNo);
				//			else
				//	p.println("&nbsp;");
				p.println("</TD>");
			}
			p.println("</TR>");
		}
		p.print("</TABLE>");
		if (_center)
			p.print("</CENTER>");
		p.println("");
	}

	public void generateInitialHTML(java.io.PrintWriter p) throws Exception {
		if (!getVisible())
			return;
		
		// sr 10-15-2000
		int rowCount = _componentsVec2D.getRowCount();
		int colCount = _componentsVec2D.getColumnCount();
		Object o = null;
		HtmlComponent comp = null;
		TwoObjectContainer cont = null;
		//
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				o = _componentsVec2D.elementAt(i, j);
				// clear values
				comp = null;
				if (o != null) {
					if (o instanceof HtmlComponent)
						comp = (HtmlComponent) o;
					else {
						cont = (TwoObjectContainer) o;
						comp = (HtmlComponent) cont.getObject1();
					}
				}
				if (comp != null)
					comp.generateInitialHTML(p);
			}
		}
	}

	/**
	 * Returns the alignment property for the table.
	 * @return align Valid values are ALIGN_LEFT,ALIGN_CENTER,ALIGN_RIGHT and ALIGN_NONE.
	 */
	public String getAlign() {
		return _align;
	}

	/**
	 * Gets the background color for the table.
	 */
	public String getBackgroundColor() {
		return _bgColor;
	}

	/**
	 * Gets the border thickness for the table.
	 */
	public int getBorder() {
		return _border;
	}

	/**
	 * Gets the cell padding for the table.
	 */
	public int getCellPadding() {
		return _cellPadding;
	}

	/**
	 * Gets the cell spacing for the table.
	 */
	public int getCellSpacing() {
		return _cellSpacing;
	}

	/**
	 * This method gets the minimum width for a particular column in the table or -1 if the column width has not been set
	 */
	public int getColumnHeight(int column) {
		if (column < 0)
			return -1;

		if (column >= _columnHeight.size())
			return -1;

		Object o = _columnHeight.elementAt(column);

		if (o == null)
			return -1;

		return ((Integer) o).intValue();
	}

	/**
	 * This method gets the minimum width for a particular column in the table or -1 if the column width has not been set
	 */
	public int getColumnWidth(int column) {
		if (column < 0)
			return -1;

		if (column >= _columnWidth.size())
			return -1;

		Object o = _columnWidth.elementAt(column);

		if (o == null)
			return -1;

		return ((Integer) o).intValue();
	}

	/**
	 * This method will return a list of all components in the container.
	 */
	public Enumeration<HtmlComponent> getComponents() {
		Vector<HtmlComponent> comps = new Vector<HtmlComponent>();
		//

		int rowCount = _componentsVec2D.getRowCount();
		int colCount = _componentsVec2D.getColumnCount();
		Object o = null;
		HtmlComponent h = null;
		//
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				o = _componentsVec2D.elementAt(i, j);
				if (o != null) {
					if (o instanceof TwoObjectContainer)
						h = (HtmlComponent) ((TwoObjectContainer) o).getObject1();
					else
						h = (HtmlComponent) o;
					comps.addElement(h);
				}
			}
		}
		return comps.elements();
	}

	/**
	 * This method will return a single component if the
	 * name or a portion of the name is found in this HtmlContainer or any
	 * HtmlContainer contained in this container.
	 * @return HtmlComponent - if a component can not be found null is returned
	 * @param name - name of component being searched for
	 */
	public HtmlComponent getComponent(String name) {
		HtmlComponent ret = null;
		HtmlComponent comp = null;
		Enumeration<HtmlComponent> enumera = getComponents();
		String compName = null;
		// we will search all items in this enumeraeration
		// recursivly calling the containers getComponent method
		boolean breakout = enumera.hasMoreElements();
		while (breakout && enumera.hasMoreElements()) {
			ret = null;

			comp = (HtmlComponent) enumera.nextElement();
			compName = comp.getName();

			// if passed name or comparision name is null return earily because we can not go any further.
			if (compName == null || name == null) {
				MessageLog.writeInfoMessage("getComponent\n\t*** Looking for component named " + name + " but " + getFullName() + " contains a component with a null name. \nSolution may be to pass empty string instead of null. ***", 9, this);
				return ret;
			}

			if (compName.indexOf(name) > -1) {
				ret = comp;
				breakout = false;
			} else {
				if (comp instanceof HtmlTable) {
					ret = ((HtmlTable) comp).getComponent(name);
					// checking the value of the return value
					breakout = (ret == null);
				} else if (comp instanceof HtmlContainer) {
					ret = ((HtmlContainer) comp).getComponent(name);
					// checking the value of the return value
					breakout = (ret == null);
				}
			}
		}

		return ret;
	}

	/**
	 * This method gets the minimum height of the table in pixels.
	 */
	public int getHeight() {
		return _containerHeight;
	}

	/**
	 * Returns the number of rows currently in the table.
	 * @return int
	 */
	public int getRowCount() {
		return _componentsVec2D.getRowCount();
	}

	/**
	 * This method was created in VisualAge.
	 * @return com.salmonllc.html.HtmlTableRowProperties
	 * @param propertyRow int
	 */
	public HtmlTableRowProperties getRowProperty(int propertyRow) {
		if (_rowProps != null && propertyRow < _rowProps.size())
			return (HtmlTableRowProperties) _rowProps.elementAt(propertyRow);
		else
			return null;
	}

	/**
	 * This method returns the size option for the table and each cell in it. Valid return values are SIZE_PIXELS or SIZE_PERCENT.
	 */
	public int getSizeOption() {
		return _sizeOption;
	}

	/**
	 * This method returns the property theme for the component.
	 */
	public String getTheme() {
		return _theme;
	}

	/**
	 * This method returns the width of the table.
	 */
	public int getWidth() {
		return _containerWidth;
	}

	/**
	 * Inserts an entire row before the specified one.
	 */
	public void insertRow(int row) {
		_componentsVec2D.insertRow(row);
	}

	public boolean processParms(Hashtable<String, Object>parms, int rowNo) throws Exception {
		if (!getVisible())
			return false;
		boolean retVal = false;
		//

		int rowCount = _componentsVec2D.getRowCount();
		int colCount = _componentsVec2D.getColumnCount();
		Object o = null;
		HtmlComponent h = null;
		//
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				o = _componentsVec2D.elementAt(i, j);
				if (o != null) {
					if (o instanceof TwoObjectContainer) {
						h = (HtmlComponent) ((TwoObjectContainer) o).getObject1();
					} else {
						h = (HtmlComponent) o;
					}

					// if you have a TwoObjectContainer with a null (HtmlComponent)Object1
					// then you have to check for null before you processParams
					if (h != null) {
						if (h.processParms(parms, rowNo)) {
							_submit = h;
							retVal = true;
						}
					}
				}
			}
		}
		return retVal;
	}

	/**
	 * Removes an html component from this container.
	 * @param comp The component to remove
	 */
	public void remove(HtmlComponent comp) {
		//

		int rowCount = _componentsVec2D.getRowCount();
		int colCount = _componentsVec2D.getColumnCount();
		Object o = null;
		HtmlComponent h = null;
		//
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				o = _componentsVec2D.elementAt(i, j);
				if (o != null) {
					if (o instanceof TwoObjectContainer)
						h = (HtmlComponent) ((TwoObjectContainer) o).getObject1();
					else
						h = (HtmlComponent) o;
					if (h == comp) {
						_componentsVec2D.setElementAt(i, j, null);
						return;
					}
				}
			}
		}
	}

	/**
	 * Removes an entire row from this container.
	 * @param row The row to remove
	 */
	public void removeRow(int row) {
		if (row < _componentsVec2D.getRowCount()) {
			_componentsVec2D.removeRow(row);
		}
	}

	/**
	 * Sets the alignment property for the table.
	 * @param align Valid values are ALIGN_LEFT,ALIGN_CENTER,ALIGN_RIGHT and ALIGN_NONE.
	 */

	public void setAlign(String align) {
		_align = align;
	}

	/**
	 * Sets the border width for the table.
	 */
	public void setAllowNullCells(boolean value) {
		_allowNullCells = value;
	}

	/**
	 * Sets the background color for the table.
	 */
	public void setBackgroundColor(String value) {
		_bgColor = value;
	}

	/**
	 * Sets the border width for the table.
	 */
	public void setBorder(int border) {
		_border = border;
	}

	/**
	 * Sets the cell padding for the table.
	 */
	public void setCellPadding(int value) {
		_cellPadding = value;
	}

	/**
	 * Sets the cell spacing for the table.
	 */
	public void setCellSpacing(int value) {
		_cellSpacing = value;
	}

	/**
	 * Sets the column width for a particular column in the table.
	 */
	public void setColumnWidth(int column, int width) {
		if (column < 0)
			return;

		if (column >= _columnWidth.size())
			_columnWidth.setSize(column + 1);

		_columnWidth.setElementAt(new Integer(width), column);
	}

	/**
	 * Sets a component at a particular row column position in the table. The cell in the table will use the browsers default cell properties.
	 */
	public void setComponentAt(int row, int column, HtmlComponent comp) {
		if (column < 0)
			return;
		if (row < 0)
			return;
		if (column >= _componentsVec2D.getColumnCount())
			_componentsVec2D.addColumns((column - _componentsVec2D.getColumnCount()) + 1);
		if (row >= _componentsVec2D.getRowCount())
			_componentsVec2D.addRows((row - _componentsVec2D.getRowCount()) + 1);
		_componentsVec2D.setElementAt(row, column, comp);
		if (comp != null) {
			comp.setParent(this);
		}
	}

	/**
	 * Sets a component at a particular row column position in the table. The cell in the table will use properties specified in the props argument.
	 */
	public void setComponentAt(int row, int column, HtmlComponent comp, HtmlTableCellProperties props) {
		if (column < 0)
			return;
		if (row < 0)
			return;
		if (column >= _componentsVec2D.getColumnCount())
			_componentsVec2D.addColumns((column - _componentsVec2D.getColumnCount()) + 1);
		if (row >= _componentsVec2D.getRowCount())
			_componentsVec2D.addRows((row - _componentsVec2D.getRowCount()) + 1);
		TwoObjectContainer cont = new TwoObjectContainer(comp, props);
		_componentsVec2D.setElementAt(row, column, cont);
		if (comp != null) {
			comp.setParent(this);
		}
	}

	/**
	 * Sets a 2D array  of components in the table. The cells in the table will use the browsers default cell properties.
	 * The routine works left to right, top to bottom.
	 * ex. comp[row][col]
	 */
	public void setComponents(HtmlComponent comp[][]) {
		int rows = comp.length;
		int cols = 0;
		// get the max number of cols in any row
		for (int rowIndex = 0; rowIndex < comp.length; rowIndex++) {
			if (comp[rowIndex].length > cols) {
				cols = comp[rowIndex].length;
			}
		}

		// make sure that there are enough columns.
		if (cols >= _componentsVec2D.getColumnCount())
			_componentsVec2D.addColumns((cols - _componentsVec2D.getColumnCount()));
		// make sure that there are enough rows.
		if (rows >= _componentsVec2D.getRowCount())
			_componentsVec2D.addRows((rows - _componentsVec2D.getRowCount()));
		//
		for (int rowIndex = 0; rowIndex < comp.length; rowIndex++) {
			for (int colIndex = 0; colIndex < comp[rowIndex].length; colIndex++) {
				_componentsVec2D.setElementAt(rowIndex, colIndex, comp[rowIndex][colIndex]);
				// This is to set the parent container of each item added.
				if (comp[rowIndex][colIndex] != null) {
					comp[rowIndex][colIndex].setParent(this);
				}
			}
		}
	}

	/**
	 * Sets a 2D array  of components in the table. The cells in the table will use the cell properties from the 2D HtmlTableCellProperties array.
	 * The routine works left to right, top to bottom.
	 * ex. comp[row][col]
	 */
	public void setComponents(HtmlComponent comp[][], HtmlTableCellProperties props[][]) {
		int rows = comp.length;
		int cols = 0;
		// get the max number of cols in any row
		for (int rowIndex = 0; rowIndex < comp.length; rowIndex++) {
			if (comp[rowIndex].length > cols) {
				cols = comp[rowIndex].length;
			}
		}

		// make sure that there are enough columns.
		if (cols >= _componentsVec2D.getColumnCount())
			_componentsVec2D.addColumns((cols - _componentsVec2D.getColumnCount()));
		// make sure that there are enough rows.
		if (rows >= _componentsVec2D.getRowCount())
			_componentsVec2D.addRows((rows - _componentsVec2D.getRowCount()));
		//
		for (int rowIndex = 0; rowIndex < comp.length; rowIndex++) {
			for (int colIndex = 0; colIndex < comp[rowIndex].length; colIndex++) {
				TwoObjectContainer cont = new TwoObjectContainer(comp[rowIndex][colIndex], props[rowIndex][colIndex]);
				_componentsVec2D.setElementAt(rowIndex, colIndex, cont);
				// This is to set the parent container of each item added.
				if (comp[rowIndex][colIndex] != null) {
					comp[rowIndex][colIndex].setParent(this);
				}
			}
		}
	}

	/**
	 * This method sets a flag indicating whether the bug related
	 * to COLSPAN has been fixed.
	 */
	public void setCorrectColSpan(boolean correctColSpan) {
		_correctColSpan = correctColSpan;
	}

	/**
	 * This method sets the minimum height of the table in pixels.
	 */
	public void setHeight(int height) {
		_containerHeight = height;
	}

	/**
	 * This method was created in VisualAge.
	 * @param row int
	 * @param rowProp com.salmonllc.html.HtmlTableRowProperties
	 */
	public void setRowProperty(int row, HtmlTableRowProperties rowProp) {
		if (_rowProps == null)
			_rowProps = new Vector<HtmlTableRowProperties>();
		int size = _rowProps.size();
		for (int i = size; i <= row; i++)
			_rowProps.addElement(null);

		_rowProps.setElementAt(rowProp, row);
	}

	/**
	 * This method sets the size option for the table and each cell in it. Valid return values are SIZE_PIXELS or SIZE_PERCENT.
	 * @param option
	 */
	public void setSizeOption(int option) {
		_sizeOption = option;
	}

	/**
	 * This method sets the property theme for the component.
	 * @param theme The theme to use.
	 */
	public void setTheme(String theme) {
		Props prop = getPage().getPageProperties();

		_border = prop.getThemeIntProperty(theme, Props.TABLE_BORDER);
		_bgColor = prop.getThemeProperty(theme, Props.TABLE_BACKGROUND_COLOR);
		_cellPadding = prop.getThemeIntProperty(theme, Props.TABLE_CELLPADDING);
		_cellSpacing = prop.getThemeIntProperty(theme, Props.TABLE_CELLSPACING);

		_theme = theme;
	}

	/**
	 * This method sets the minimum width of the table in either pixels or percent depending on size option.
	 * @param width
	 */
	public void setWidth(int width) {
		_containerWidth = width;
	}

	/**
	 * Adds a component to the table a makes a caption comp.
	 *
	 * @param name		Name to associate internally with the search component.
	 * @param caption	Text of caption to put before the search component, or null.
	 * @param component	The search component.
	 */
	public void addDisplay(String name, String caption, HtmlComponent component) throws Exception {

		addDisplay(name, caption, component, false);
	}

	/**
	 * Adds a component to the table a makes a caption comp.
	 *
	 * @param name		Name to associate internally with the search component.
	 * @param caption	Text of caption to put before the search component, or null.
	 * @param component	The search component.
	 * @param sameRow	boolean specifies whether you want the next component on the same row.
	 */
	public void addDisplay(String name, String caption, HtmlComponent component, boolean sameRow) throws Exception {

		addDisplay(name, caption, component, sameRow, null, null);

	}

	/**
	 * Adds a component to the table a makes a caption comp.
	 *
	 * @param name		Name to associate internally with the search component.
	 * @param caption	Text of caption to put before the search component, or null.
	 * @param component	The search component.
	 * @param sameRow	boolean specifies whether you want the next component on the same row.
	 * @param propCaption	Table properties for caption, or null.
	 * @param propSearch	Table properties for search component, or null.
	 */
	public void addDisplay(String name, String caption, HtmlComponent component, boolean sameRow, HtmlTableCellProperties propCaption, HtmlTableCellProperties propSearch) throws Exception {

		if (sameRow == false || (_currRowVal == -1)) {
			_currRowVal++;
			_currColVal = -1;
		}

		if (name == null) {
			//  ROW_+rowindexCOL_+colindex
			name = "ROW_" + new Integer(_currRowVal).toString();
			name += "COL_" + new Integer(_currColVal).toString();

		}

		if (caption != null) {
			String s = caption;
			if (s.length() > 0) {
				switch (s.charAt(s.length() - 1)) {
					case ':' :
					case '.' :
					case '?' :
						break;
					default :
						s += ":";
				}
			}
			HtmlComponent capComp = null;
			// save what caption goes with what search comp for later
			capComp = new HtmlText(s, HtmlText.FONT_COLUMN_CAPTION, getPage());

			// Put Caption in search table
			if (propCaption != null) {
				setComponentAt(_currRowVal, ++_currColVal, capComp, propCaption);
			} else {
				setComponentAt(_currRowVal, ++_currColVal, capComp);
			}
			_compLookUpHash.put(CAPTION_COMP_HASH_KEY + name, capComp);
		}

		//
		if (component != null) {
			// Null component means caption only, perhaps.
			if (propSearch != null) {
				setComponentAt(_currRowVal, ++_currColVal, component, propSearch);
			} else {
				setComponentAt(_currRowVal, ++_currColVal, component);
			}
			_compLookUpHash.put(SEARCH_COMP_HASH_KEY + name, component);

		}
	}

	/**
	 * This method will return a caption Component.
	 * @param name
	 * @return
	 */
	public HtmlComponent getCaptionComp(String name) {
		return (HtmlComponent) _compLookUpHash.get(CAPTION_COMP_HASH_KEY + name);
	}

	/**
	 * This method will return a serach component.
	 * @param name
	 * @return
	 */
	public HtmlComponent getSearchComp(String name) {
		return (HtmlComponent) _compLookUpHash.get(SEARCH_COMP_HASH_KEY + name);

	}

	/**
	 * This method removes all of the components out of the table.
	 */
	public void removeAll() {
		super.removeAll();

		int colCount = _componentsVec2D.getColumnSize();
		int rowCount = _componentsVec2D.getRowSize();

		/** rows */
		for (int i = 0; i < rowCount; i++) {
			/** cols */
			for (int j = 0; j < colCount; j++) {
				HtmlComponent hc = null;
				Object o = _componentsVec2D.elementAt(i, j);

				if (o instanceof TwoObjectContainer)
					hc = (HtmlComponent) ((TwoObjectContainer) o).getObject1();
				else
					hc = (HtmlComponent) o;

				if (hc instanceof HtmlTable) {
					((HtmlTable) hc).removeAll();
				} else if (hc instanceof HtmlContainer) {
					((HtmlContainer) hc).removeAll();
				}

			}

		}

		// last cleanup
		_componentsVec2D.removeAll();
	}
	/**
	 * Removes the row from the table containing a specific component
	 * @author Ian Booth
	 */
	public void removeRow(HtmlComponent comp) {
		int rowCount = _componentsVec2D.getRowCount();
		int colCount = _componentsVec2D.getColumnCount();
		Object o = null;
		HtmlComponent h = null;

		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				o = _componentsVec2D.elementAt(i, j);
				if (o != null) {
					if (o instanceof TwoObjectContainer)
						h = (HtmlComponent) ((TwoObjectContainer) o).getObject1();
					else
						h = (HtmlComponent) o;
					if (h == comp) {
						removeRow(i);
						return;
					}
				}
			}
		}
	}

	/**
	 * @return the default style sheet class for table alternate rows
	 */
	public String getRowStyleClassName() {
		return _defaultRowStyleClassName;
	}

	/**
	 * @param string sets the default style sheet class for table rows 
	 */
	public void setRowStyleClassName(String string) {
		_defaultRowStyleClassName = string;
	}

}
