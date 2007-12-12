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

import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.html.events.SubmitListener;
import com.salmonllc.html.events.TreeExpandContractEvent;
import com.salmonllc.html.events.TreeListener;
import com.salmonllc.html.treeControl.TreeBuffer;
import com.salmonllc.html.treeControl.TreeTraversalCallBack;
import com.salmonllc.jsp.JspForm;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.DataStoreBuffer;
import com.salmonllc.sql.DataStoreEvaluator;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.sql.DataStoreExpression;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.ThreeObjectContainer;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/**
 * This class is used to place a tree control.
 * @see  com.salmonllc.html.treeControl
 */
public class HtmlTreeControl extends HtmlComponent implements TreeTraversalCallBack, java.io.Serializable {

	private TreeBuffer _tb;

	private String _fontTagStart = null;
	private String _fontTagEnd = null;
	private boolean _autoScroll = true;

	/*
	 * Added by: Henry Tafolla
	 * Date: Feburary 22 2003
	 * Description: Properties for the HtmlTreeComponent
	 */
	private String _anchorClass = null;
	private int _cellPadding = 0;
	private int _cellSpacing = 0;
	private boolean _window = false;
	private String _windowDirectories;
	private int _windowHeight;
	private String _windowLocation;
	private String _windowMenubar;
	private String _windowName;
	private String _windowResizable;
	private String _windowScrollbars;
	private String _windowStatus;
	private String _windowToolbar;
	private int _windowWidth = -1;
	private String _tableCellClass = null;
	private String _headerCellClass = null;
	private String _tableCellStyle = null;
	private String _headerCellStyle = null;
	private String _treeTableStyle = null;
	private String _treeTableClass = null;
	private HtmlComponent _submit = null;
	private boolean _clickSort = true;
	private int _clickSortColumn = -1;
	private int _sortColumn = -1;
	private int _sortDir = TreeBuffer.SORT_ASC;
	private int _sortPathDir = TreeBuffer.SORT_ANY;
	
	private String _nodePtSizeSpanStart = "<SPAN STYLE=\"font-size:12;\">";
	private String _nodePtSizeSpanEnd = "</SPAN>";

	private String _expandImage;
	private String _contractImage;
	private String _nullImage;
	private Vector _treeListeners = new Vector();
	private Vector _submitListeners = new Vector();
	private String _targetFrame;
	private String _scrollTop;
	private String _scrollLeft;
	private String _width = null;
	private Vector _propertyExpressions;
	private String _theme;

	private class TreeNodeParmProcessor implements TreeTraversalCallBack {
		Hashtable _tab;
		public TreeNodeParmProcessor(Hashtable tab) {
			_tab = tab;
		}
		public void callBack(TreeBuffer t, PrintWriter p) {
			Vector nodeComps = t.getNodeComponents();
			int compSize = nodeComps.size();
			int row = t.getRow();
			if (row >= 0) {
				for (int i = 0; i < compSize; i++) {
					HtmlComponent comp = (HtmlComponent) nodeComps.elementAt(i);
					try {
						if (comp.processParms(_tab, row))
							_submit = comp;
					} catch (Exception e) {
						MessageLog.writeErrorMessage("TreeBufferProcessParmsCallBack", e, this);
					}
				}
			}
		}
	}

	/**
	 * Constructs a new TreeControl.
	 */
	public HtmlTreeControl(String name, HtmlPage p) {
		this(name, null, p);
	}

	/**
	  * Constructs a new TreeControl with a theme.
	  */
	public HtmlTreeControl(String name, String theme, HtmlPage p) {
		super(name, p);
		setTheme(theme);
		_tb = new TreeBuffer();
	}

	/**
	 * Adds a listener that will be notified if the tree is submitted.
	 */
	public void addSubmitListener(SubmitListener t) {
		for (int i = 0; i < _submitListeners.size(); i++)
			if (t == (SubmitListener) _submitListeners.elementAt(i))
				return;
		_submitListeners.addElement(t);
	}

	/**
	 * Adds a listener that will be notified if a tree node is expanded or contracted.
	 */

	public void addTreeListener(TreeListener t) {
		for (int i = 0; i < _treeListeners.size(); i++)
			if (t == (TreeListener) _treeListeners.elementAt(i))
				return;
		_treeListeners.addElement(t);
	}

	/**
	 * This method is used internally by the tree control and should not be executed directly.
	 */
	public void callBack(TreeBuffer tb, PrintWriter p) {
		//if the tree item is invisible, it's image will be null so skip the whole line
		if (tb.getImage() == null)
			return;

		//selected node
		String bold = "";
		String unbold = "";
		if (tb.getHandle() == tb.getSelected()) {
			bold = "<b>";
			unbold = "</b>";
		}

		String text = tb.getText();
		String handle = new Integer(tb.getHandle()).toString();
		String name = getFullName();
		int level = tb.getLevel();

		StringBuffer output = new StringBuffer();
		output.append("<TR>");

		//select radio buttons or check boxes
		if (tb.getSelectMode() != TreeBuffer.SELECT_NONE) {
			output.append("<TD WIDTH=\"1%\"");
			if (_tableCellStyle != null)
				output.append(" style=\"" + _tableCellStyle + "\"");
			else if (_tableCellClass != null)
				output.append(" class=\"" + _tableCellClass + "\"");
			output.append(" valign=\"top\">");
		}
		if (tb.getSelectable()) {
			if (tb.getSelectMode() == TreeBuffer.SELECT_ONE) {
				output.append("<INPUT NAME=\"" + getFullName() + "Select\" TYPE=\"RADIO\"" + " VALUE=\"" + handle + "\"");
				if (tb.getHandle() == tb.getSelected())
					output.append(" CHECKED ");
				output.append("> ");
			} else if (tb.getSelectMode() == TreeBuffer.SELECT_MANY) {
				output.append("<INPUT NAME=\"" + getFullName() + handle + "_Select\" TYPE=\"CHECKBOX\" VALUE=\"" + handle + "\"");
				if (tb.getSelected(tb.getHandle()))
					output.append(" CHECKED ");
				output.append(">");
			}
		}
		if (tb.getSelectMode() != TreeBuffer.SELECT_NONE)
			output.append("</TD>");

		//write out the first anchor

		if (_tableCellClass != null)
			output.append("<TD NOWRAP valign=\"top\" class=\"" + _tableCellClass + "\"><a name=\"" + name + handle + "\"></a>");
		else if (_tableCellStyle != null)
			output.append("<TD NOWRAP valign=\"top\" style=\"" + _tableCellStyle + "\"><a name=\"" + name + handle + "\"></a>");
		else
			output.append("<TD NOWRAP valign=\"top\"><a name=\"" + name + handle + "\"></a>");

		//do the indent based on level
		if (_tb.getShowRoot() && (level > 0)) {
			for (int i = 0; i < level; i++)
				output.append("&nbsp;&nbsp;&nbsp;");
		} else if (!_tb.getShowRoot() && (level > 1)) {
			for (int i = 1; i < level; i++)
				output.append("&nbsp;&nbsp;&nbsp;");
		}

		//Expand/Contract image
		String img = new String(_nullImage);

		if (tb.hasChildren()) {
			String op;
			if (tb.isExpanded()) {
				op = "C";
				img = _contractImage;
			} else {
				op = "E";
				img = _expandImage;
			}

			if (_tb.getMode() == TreeBuffer.MODE_LINK) {
				output.append("<a href=\"javascript:" + name + "_expandContract('" + handle + "','" + op + "')\">");
				output.append("<img src=\"" + img + "\" border=\"0\"></a>");
			} else
				output.append("<INPUT TYPE=\"IMAGE\" BORDER=\"0\" NAME = \"" + name + "_" + handle + "_" + op + "\" SRC=\"" + img + "\">");
		} else
			output.append("<img src=\"" + img + "\" border=\"0\">");

		output.append(bold);

		//File/Folder image and text
		String link = tb.getURL();
		String border = "\"0\"";

		if (tb.getImageURL() != null) {

			if (_window) {

				output.append(
					"<a href=\"javascript:doNothing();\" onclick=\"javascript:window.open('"
						+ tb.getImageURL()
						+ "', '"
						+ _windowName
						+ "','toolbars="
						+ _windowToolbar
						+ ",directories="
						+ _windowDirectories
						+ ",scrollbars="
						+ _windowScrollbars
						+ ",location="
						+ _windowLocation
						+ ",status="
						+ _windowStatus
						+ ",menubar="
						+ _windowMenubar
						+ ",resizable="
						+ _windowResizable
						+ ",width="
						+ _windowWidth
						+ ",height="
						+ _windowHeight
						+ "');\">");

			} else {
				output.append("<a href=\"" + encodeURL(tb.getImageURL()) + "\"");
				if (_targetFrame != null)
					output.append(" target=\"" + _targetFrame + "\" ");
				output.append(">");
			}
		}

		output.append("<img src=\"" + tb.getImage() + "\" border=" + border + ">");

		if (tb.getImageURL() != null)
			output.append("</a>");

		//text for file or folder
		if (link != null) {

			if (_window && _anchorClass != null) {

				output.append(
					"<a class=\""
						+ _anchorClass
						+ "\" href=\"javascript:doNothing();\" onclick=\"javascript:window.open('"
						+ _tb.replaceUno(link)
						+ "', '"
						+ _windowName
						+ "','toolbars="
						+ _windowToolbar
						+ ",directories="
						+ _windowDirectories
						+ ",scrollbars="
						+ _windowScrollbars
						+ ",location="
						+ _windowLocation
						+ ",status="
						+ _windowStatus
						+ ",menubar="
						+ _windowMenubar
						+ ",resizable="
						+ _windowResizable
						+ ",width="
						+ _windowWidth
						+ ",height="
						+ _windowHeight
						+ "');\">");

			} else if (_window && _anchorClass == null) {

				output.append(
					"<a href=\"javascript:doNothing();\" onclick=\"javascript:window.open('"
						+ _tb.replaceUno(link)
						+ "', '"
						+ _windowName
						+ "','toolbars="
						+ _windowToolbar
						+ ",directories="
						+ _windowDirectories
						+ ",scrollbars="
						+ _windowScrollbars
						+ ",location="
						+ _windowLocation
						+ ",status="
						+ _windowStatus
						+ ",menubar="
						+ _windowMenubar
						+ ",resizable="
						+ _windowResizable
						+ ",width="
						+ _windowWidth
						+ ",height="
						+ _windowHeight
						+ "');\">");

			} else if (_anchorClass != null) {
				
				
				output.append("<a class=\"" + _anchorClass + "\" href=\"" + encodeURL(_tb.replaceUno(link)) + "\"");
				if (_targetFrame != null)
					output.append("target=\"" + _targetFrame + "\" ");
				output.append(">");
			} else {
				output.append("<a href=\"" + encodeURL(_tb.replaceUno(link)) + "\"");
				if (_targetFrame != null)
					output.append("target=\"" + _targetFrame + "\" ");
				output.append(">");
			}

		}

		//output.append("<font face=\"Helvetica\" SIZE=\"2\"><SPAN STYLE=\"font-size:12;\">" + text + "</SPAN></font>");
		if (_tableCellClass == null) {

			if (_fontTagStart != null)
				output.append(_fontTagStart + _nodePtSizeSpanStart + text + _nodePtSizeSpanEnd + _fontTagEnd);
			else
				output.append("<font face=\"Helvetica\" SIZE=\"2\">" + _nodePtSizeSpanStart + text + _nodePtSizeSpanEnd + "</font>");
		} else {
			output.append(text);
		}

		if (link != null)
			output.append("</a>");

		//Add any additional images to the line
		for (int i = 0; i < _tb.getAdditionalImageCount(); i++) {
			String img2 = _tb.getAdditionalImage(i);
			String txt = _tb.getAdditionalText(i);
			String u = _tb.getAdditionalURL(i);
			if (u != null) {
				u = _tb.replaceUno(u);
				output.append("<a href=\"" + u + "\"");
				if (_targetFrame != null)
					output.append("target=\"" + _targetFrame + "\" ");
				output.append(">");
			}

			output.append("<img src=\"" + img2 + "\" border=\"0\" alt=\"" + txt + "\">");
			if (u != null)
				output.append("</a>");
		}

		output.append(unbold);
		output.append("</TD>");
		p.println(output.toString());
		Vector nodeComps = _tb.getNodeComponents();
		if (nodeComps != null) {
			for (int i = 0; i < nodeComps.size(); i++) {
				int row = tb.getRow();
				p.print("<TD VALIGN=\"top\"");
				if (_tableCellStyle != null)
					p.print(" style=\"" + _tableCellStyle + "\"");
				else if (_tableCellClass != null)
					p.print(" class=\"" + _tableCellClass + "\"");
				int headerNo = i + 1;
				if (headerNo < tb.getHeaderCount()) {
					String align = _tb.getHeaderAlign(headerNo);
					if (align != null)
						p.print(" align =\"" + align + "\"");
				}
				p.println(">");
				try {
					if (row > -1)
						processPropertyExpressions(row);
					HtmlComponent comp = ((HtmlComponent) nodeComps.elementAt(i));
					if (!comp.getVisible())
						p.println("&nbsp;");
					else
						comp.generateHTML(p, row);
				} catch (Exception e) {
					MessageLog.writeErrorMessage("callback of Tree", e, this);
				}
				p.println("</TD>");
			}

		}

		//finish it up
		p.println("</TR>");

	}

	/**
	 * This method will remove all elements from the Tree
	 */
	public void clearBuffer() {
		_tb = new TreeBuffer();
	}

	public boolean executeEvent(int type) throws Exception {

		if (type == HtmlComponent.EVENT_OTHER) {
			if (_tb.getNodeComponents() != null) {
				HtmlComponent h = null;
				for (int i = 0; i < _tb.getNodeComponents().size(); i++) {
					h = (HtmlComponent) _tb.getNodeComponents().elementAt(i);
					if (!h.executeEvent(type))
						return false;
				}
			} else
				return true;
		} else if (_submit != null && type == HtmlComponent.EVENT_SUBMIT) {
			boolean retVal = _submit.executeEvent(type);
			_submit = null;
			return retVal;
		}

		if (type != EVENT_SUBMIT)
			return true;

		if (_treeListeners != null) {
			if (_tb.getHandle() != -1) {
				for (int i = 0; i < _treeListeners.size(); i++) {
					TreeListener l = (TreeListener) _treeListeners.elementAt(i);
					if (_tb.isExpanded())
						l.itemExpanded(new TreeExpandContractEvent(this, _tb.getHandle(), true, _tb));
					else
						l.itemContracted(new TreeExpandContractEvent(this, _tb.getHandle(), true, _tb));
				}
			}
		}

		if (_submitListeners != null) {
			SubmitEvent e = new SubmitEvent(getPage(), this, getName(), getFullName(), -1);
			for (int i = 0; i < _submitListeners.size(); i++) {
				SubmitListener l = (SubmitListener) _submitListeners.elementAt(i);
				e.setNextListener(_submitListeners.size() > (i + 1) ? _submitListeners.elementAt(i + 1) : null);
				if (!l.submitPerformed(e))
					return false;
			}
		}

		return true;
	}

	public void generateHTML(PrintWriter writer, int rowNo) throws Exception {
		if (!_visible)
			return;

		boolean clickSort = _clickSort;

		if (clickSort) {
			writer.println("<INPUT TYPE=\"HIDDEN\" NAME=\"SORTITEM" + getFullName() + "\" VALUE=\"-1\">");
			writer.println("<SCRIPT>");
			writer.println("function " + getFullName() + "_clickSort(i) {");
			writer.println(getFormString() + "SORTITEM" + getFullName() + ".value = i;");
			JspForm form = JspForm.findParentForm(this);
			if (form != null)
				writer.println(form.getSubmitScript());
			else
				writer.println(getFormString() + "submit();");
			writer.println("}");
			writer.println("</SCRIPT>");
		}

		writer.print("<TABLE BORDER=0 CELLPADDING=\"" + _cellPadding + "\" CELLSPACING=\"" + _cellSpacing + "\"");
		if (_width != null)
			writer.print(" width=\"" + _width + "\"");
		if (_treeTableClass != null)
			writer.print(" class=\"" + _treeTableClass + "\"");
		else if (_treeTableStyle != null)
			writer.print(" style=\"" + _treeTableStyle + "\"");
		writer.println(">");

		int count = _tb.getHeaderCount();
		if (count != 0) {
			writer.println("<TR>");
			for (int i = 0; i < count; i++) {
				writer.print("<TD");
				if (_headerCellClass != null)
					writer.print(" class=\"" + _headerCellClass + "\"");
				if (_headerCellStyle != null)
					writer.print(" style=\"" + _headerCellStyle + "\"");
				if (getTreeBuffer().getSelectMode() != TreeBuffer.SELECT_NONE && i == 0)
					writer.print(" colspan=\"2\"");
				String align = _tb.getHeaderAlign(i);
				if (align != null)
					writer.print(" align =\"" + align + "\"");
				String width = _tb.getHeaderWidth(i);
				if (width != null)
					writer.print(" width =\"" + width + "\"");
				writer.println(">");
				HtmlComponent comp = _tb.getHeaderComponent(i);
				boolean underLine = false;
				if (clickSort)
					underLine = _tb.canSortOnColumn(i);

				if (underLine)
					writer.print("<A HREF=\"javascript:" + getFullName() + "_clickSort(" + i + ");\">");

				comp.generateHTML(writer, -1);
				writer.println("</TD>");
			}
			writer.println("</TR>");

		}

		if (_tb.getMode() == TreeBuffer.MODE_LINK) {
			HtmlPage p = getPage();
			HttpServletRequest req = p.getCurrentRequest();

			String param[] = req.getParameterValues(getFullName());
			String op[] = req.getParameterValues("op");
			String scrollTop[] = req.getParameterValues("scrollTop");
			if (scrollTop == null)
				_scrollTop = null;
			else
				_scrollTop = scrollTop[0];

			String scrollLeft[] = req.getParameterValues("scrollLeft");
			if (scrollLeft == null)
				_scrollLeft = null;
			else
				_scrollLeft = scrollLeft[0];

			if (param != null) {
				try {
					int handle = Integer.parseInt(param[0]);
					_tb.gotoItem(handle);
					//_tb.setScrollTo(handle);

					if (op[0].equals("E"))
						_tb.setExpanded(true);
					else
						_tb.setExpanded(false);

					if (_treeListeners != null) {
						for (int i = 0; i < _treeListeners.size(); i++) {
							TreeListener l = (TreeListener) _treeListeners.elementAt(i);
							if (_tb.isExpanded())
								l.itemExpanded(new TreeExpandContractEvent(this, _tb.getHandle(), true, _tb));
							else
								l.itemContracted(new TreeExpandContractEvent(this, _tb.getHandle(), true, _tb));
						}
					}
				} catch (Exception e) {
					MessageLog.writeErrorMessage("generateHtml()", e, this);
				}
			}
		}

		if (_tb != null)
			_tb.traverse(this, writer);

		writer.println("</TABLE>");

		int scrollTo = _tb.getScrollTo();

		if (_tb.getMode() == TreeBuffer.MODE_SUBMIT && _autoScroll) {
			JspForm form = JspForm.findParentForm(this);
			if (form != null) {
				if (form.isScrollPositionSet()) {
					form.scrollToLastPosition();
					scrollTo = 0;
				}
			}
		} else if (_tb.getMode() == TreeBuffer.MODE_LINK) {
			HtmlPage p = getPage();
			HttpServletRequest req = p.getCurrentRequest();
			String url = encodeURL(req.getRequestURI());
			writer.println("<SCRIPT>");
			writer.println("function " + getFullName() + "_expandContract(handle_parm,op) {");
			if (getPage().getBrowserType() == HtmlPage.BROWSER_MICROSOFT)
				writer.println("     u = '" + url.toString() + "?c=" + _tb.getCount() + "&" + getFullName() + "=' + handle_parm + '&op=' + op + '&scrollTop=' + document.body.scrollTop + '&scrollLeft=' + document.body.scrollLeft");
			else
				writer.println("     u = '" + url.toString() + "?c=" + _tb.getCount() + "&" + getFullName() + "=' + handle_parm + '&op=' + op + '&scrollTop=' + window.pageYOffset + '&scrollLeft=' + window.pageXOffset");
			writer.println("     location.replace(u);");
			writer.println("}");
			writer.println("</SCRIPT>");
		}

		if (scrollTo > 0 && _autoScroll) {
			getPage().scrollToItem(getFullName() + scrollTo);
		} else if (_scrollTop != null && _autoScroll) {
			if (getPage().getBrowserType() == HtmlPage.BROWSER_MICROSOFT) {
				getPage().writeScript("document.body.scrollTop=" + _scrollTop + ";");
				getPage().writeScript("document.body.scrollLeft=" + _scrollLeft + ";");
			} else {
				getPage().writeScript("window.scrollTo(" + _scrollLeft + "," + _scrollTop + ");");
			}

			_scrollTop = null;
			_scrollLeft = null;
		}

		// if (_tb.getMode() == TreeBuffer.MODE_LINK) {
		//     for (int i = 0; i < 10; i++)
		//         writer.println("<BR>");
		// }
	}

	/**
	 * This method gets the end font tag for the component.
	 */
	public String getFontEndTag() {
		return _fontTagEnd;
	}

	/**
	 * This method gets the start font tag for the component.
	 */
	public String getFontStartTag() {
		return _fontTagStart;
	}

	/**
	 * This method returns the tree buffer that this component uses.
	 * The tree buffer is the class that maintains the state of the items on the tree.
	 */

	public TreeBuffer getTreeBuffer() {
		return _tb;
	}

	public boolean processParms(Hashtable ht, int rowNo) throws Exception {

		String param = null;
		String op = null;
		String selected = null;
		boolean retval = false;

		if (_tb.getSelectMode() == TreeBuffer.SELECT_MANY)
			_tb.clearVisibleSelections();


		Enumeration en = ht.keys();
		while (en.hasMoreElements()) {
			String parm = (String) en.nextElement();
			if (parm.startsWith(getFullName()) && !parm.equals(getFullName())) {
				if (parm.endsWith("Select")) {
					String[] sel = (String[]) ht.get(parm);
					if (sel != null) {
						if (_tb.getSelectMode() == TreeBuffer.SELECT_MANY)
							_tb.setSelected(Integer.parseInt(sel[0]), true);
						else
							selected = sel[0];
					}
				} else if (param == null) {
					int pos = parm.lastIndexOf(".");
					if (pos > 0)
						parm = parm.substring(0, pos);
					pos = parm.lastIndexOf("_");
					if (pos > 0) {
						op = parm.substring(pos + 1);
						parm = parm.substring(0, pos);
					}
					pos = parm.lastIndexOf("_");
					if (pos > 0) {
						param = parm.substring(pos + 1);
						parm = parm.substring(0, pos);
					}
				}
			}
		}

		if (selected != null) {
			int handle = Integer.parseInt(selected);
			_tb.setSelected(handle);
		}

		_clickSortColumn = -1;
		if (_clickSort) {
			String clickSort[] = (String[]) ht.get("SORTITEM" + getFullName());
			try {
				doSort(Integer.parseInt(clickSort[0]));
			} catch (Exception ex) {
			}
		}
		
		boolean retVal2 = processNodeParms(_tb, ht);

		if (param != null) {
			retval = true;
			int handle = Integer.parseInt(param);
			_tb.gotoItem(handle);
			if (op.equals("E"))
				_tb.setExpanded(true);
			else
				_tb.setExpanded(false);
			_tb.setScrollTo(Integer.parseInt(param));
		}

		return retval || retVal2;

	}

	private boolean processNodeParms(TreeBuffer tb, Hashtable tab) throws Exception {
		/*	String compName = null;
			try
				{
				if (!getVisible())
					return false;
		
				if (tb.getNodeComponents() != null) {
					TreeEnumerator enumera = (TreeEnumerator) tb.getElements();
					Vector nodeComps = tb.getNodeComponents(); 			
					int compSize = nodeComps.size();
					HtmlComponent comp = null;
					while (enumera.hasMoreElements()) {
						 enumera.nextElement();
						 int handle  = enumera.getHandle();
						 tb.gotoItem(handle);
						 int row = tb.getRow();
						 for (int i = 0; i < compSize; i++)	{
							comp = (HtmlComponent) nodeComps.elementAt(i);
							if (comp.processParms(tab, row))
								_submit = comp;
						}
					}	
		
				}
				if (_submit != null)
					return true;
				else
					return false;
		
			}
			catch (Exception e)
				{
				MessageLog.writeErrorMessage("processParms for " + compName + "\n", e, this);
				throw (e);
		
			}*/

		String compName = null;
		if (!getVisible())
			return false;

		if (tb.getNodeComponents() != null) {
			TreeNodeParmProcessor proc = new TreeNodeParmProcessor(tab);
			tb.traverse(proc, null);
		}
		if (_submit != null)
			return true;
		else
			return false;
	}
	/**
	 * Removes a submit listener.
	 */

	public void removeSubmitListener(SubmitListener t) {
		for (int i = 0; i < _submitListeners.size(); i++)
			if (t == (SubmitListener) _submitListeners.elementAt(i)) {
				_submitListeners.removeElementAt(i);
				return;
			}
	}

	/**
	 * Removes a tree listener.
	 */

	public void removeTreeListener(TreeListener t) {
		for (int i = 0; i < _treeListeners.size(); i++)
			if (t == (TreeListener) _treeListeners.elementAt(i)) {
				_treeListeners.removeElementAt(i);
				return;
			}
	}

	/**
	 * Adds a listener that will be notified if the tree is submitted.
	 */
	public void reset() {
		_tb = new TreeBuffer();
	}

	/**
	 * This method will load the font start and end tags from the page properties object.See the Constants at the top of the class for valid values to pass to this method.
	 */
	public void setFont(String font) {
		Props p = getPage().getPageProperties();
		_fontTagStart = p.getProperty(font + Props.TAG_START);
		_fontTagEnd = p.getProperty(font + Props.TAG_END);
	}

	/**
	 * This method sets the end font tag for the component.
	 */
	public void setFontEndTag(String value) {
		_fontTagEnd = value;
	}

	/**
	 * This method sets the start font tag for the component.
	 */
	public void setFontStartTag(String value) {
		_fontTagStart = value;
	}

	/**
	 * This method sets the images that will appear on every tree item. It must be called before the tree can be used.
	 * @param expandImage The image to use to expand a tree item.
	 * @param contractImage The image to use to contract a tree item.
	 * @param nullImage The image to use if a tree item can't be expanded or contracted.
	 */

	public void setSystemImages(String expandImage, String contractImage, String nullImage) {
		_expandImage = translateSiteMapURL(expandImage);
		_contractImage = translateSiteMapURL(contractImage);
		_nullImage = translateSiteMapURL(nullImage);
	}

	/**
	 * This method sets the frame that will be effected when the user selects an item in the tree.
	 */
	public void setTargetFrame(String f) {
		_targetFrame = f;
	}

	/**
	 * This method sets the tree buffer that this component uses.
	 * The tree buffer is the class that maintains the state of the items on the tree.
	 */

	public void setTreeBuffer(TreeBuffer tb) {
		_tb = tb;
		_sortColumn = 0;
		_sortDir = TreeBuffer.SORT_ASC;
		_clickSortColumn = -1;
		if (_sortPathDir != TreeBuffer.SORT_ANY)
			_sortPathDir = TreeBuffer.SORT_ASC;
	}


	/**
	 * For a click sort, decide whether or not the first column (tree path) takes precidence to the column being sorted
	 */
	public void setUsePathForClickSort(boolean usePath) {
		if (usePath) 
			_sortPathDir=TreeBuffer.SORT_ASC;	
		else
			_sortPathDir=TreeBuffer.SORT_ANY;		
	}
		
	/**
	 * Sort the tree
	 * @param column the number of the header column to sort on (0 for path name only sort)
	 * @param pathDir the sort direction for the tree path (first column) to sort on TreeBuffer.SORT_ASC or TreeBuffer.SORT_DES or TreeBuffer.SORT_ANY to not use a path sort
	 * @param dir the direction to sort the specified column on SORT_ASC or SORT_DES if column is not zero
	 */
	public void sort(int sortColumn, int sortPathDir, int sortDir) {
		_sortColumn = sortColumn;
		_sortPathDir = sortPathDir;
		_sortDir = sortDir;
		_tb.sort(_sortColumn, _sortPathDir, _sortDir);	
	}
	
	/**
	 * Sort the tree on a column
	 * @param column the number of the header column to sort on (0 for path name only sort)
	 * @param dir the direction to sort the specified column on SORT_ASC or SORT_DES if column is not zero
	 */
	public void sort(int sortColumn, int sortDir) {
		sort(sortColumn,TreeBuffer.SORT_ANY,sortDir);
	}	
	
	public void sort() 	{
		sort(_sortColumn, _sortPathDir, _sortDir);
	}
	
	/*
	 * Added by: Henry Tafolla
	 * Date: Feburary 22 2003
	 * Description: Properties for the HtmlTreeComponent
	 */
	/**
	 * This method sets the property theme for the component.
	 * @param theme The theme to use.
	 */
	public void setTheme(String theme) {

		Props prop = getPage().getPageProperties();

		_anchorClass = prop.getThemeProperty(theme, Props.TREE_ANCHOR_CLASS);
		_cellPadding = prop.getThemeIntProperty(theme, Props.TREE_CELLPADDING);
		_cellSpacing = prop.getThemeIntProperty(theme, Props.TREE_CELLSPACING);
		_window = prop.getThemeBooleanProperty(theme, Props.TREE_JAVASCRIPT_WINDOW);
		_windowDirectories = prop.getThemeProperty(theme, Props.TREE_JAVASCRIPT_WINDOW_DIRECTORIES);
		_windowHeight = prop.getThemeIntProperty(theme, Props.TREE_JAVASCRIPT_WINDOW_HEIGHT);
		_windowLocation = prop.getThemeProperty(theme, Props.TREE_JAVASCRIPT_WINDOW_LOCATION);
		_windowMenubar = prop.getThemeProperty(theme, Props.TREE_JAVASCRIPT_WINDOW_MENUBAR);
		_windowName = prop.getThemeProperty(theme, Props.TREE_JAVASCRIPT_WINDOW_NAME);
		_windowResizable = prop.getThemeProperty(theme, Props.TREE_JAVASCRIPT_WINDOW_RESIZABLE);
		_windowScrollbars = prop.getThemeProperty(theme, Props.TREE_JAVASCRIPT_WINDOW_SCROLLBARS);
		_windowStatus = prop.getThemeProperty(theme, Props.TREE_JAVASCRIPT_WINDOW_STATUS);
		_windowToolbar = prop.getThemeProperty(theme, Props.TREE_JAVASCRIPT_WINDOW_TOOLBAR);
		_windowWidth = prop.getThemeIntProperty(theme, Props.TREE_JAVASCRIPT_WINDOW_WIDTH);
		_tableCellClass = prop.getThemeProperty(theme, Props.TREE_TABLE_CELL_CLASS);
		_headerCellClass = prop.getThemeProperty(theme, Props.TREE_TABLE_HEADER_CELL_CLASS);
		_tableCellStyle = prop.getThemeProperty(theme, Props.TREE_TABLE_CELL_STYLE);
		_headerCellStyle = prop.getThemeProperty(theme, Props.TREE_TABLE_HEADER_CELL_STYLE);
		_treeTableStyle = prop.getThemeProperty(theme, Props.TREE_TABLE_STYLE);
		_treeTableClass = prop.getThemeProperty(theme, Props.TREE_TABLE_CLASS);
		_fontTagStart = prop.getThemeProperty(theme, Props.TREE_NODE_FONT + Props.TAG_START);
		_fontTagEnd = prop.getThemeProperty(theme, Props.TREE_NODE_FONT + Props.TAG_END);
		if (_fontTagStart != null) {
			_nodePtSizeSpanEnd = "";
			_nodePtSizeSpanStart = "";
		}
		_theme = theme;
	}

	/**
	 * Returns the theme the tree is using
	 */
	public String getTheme() {
		return _theme;
	}

	/**
	 * @return the width of the tree table
	 */
	public String getWidth() {
		return _width;
	}

	/**
	 * sets the width of the table
	 */
	public void setWidth(String string) {
		_width = string;
	}
	/**
	 * This method will add a property expression to the HtmlTree.
	 * The propExpression will be evaluated for each row in the DataStore and the set method for the specified property will be called on the specified object.
	 * @param comp The component to set the property for
	 * @param propertyName The name of the property to set. The component must have a corresponding setProperty method or this method will throw a NoSuchMethodException
	 * @param propExpression com.salmonllc.html.PropertyExpression The instance of PropertyExpression that should do the evaluating.
	 * @exception java.lang.NoSuchMethodException The exception description.
	 * @exception com.salmonllc.sql.DataStoreException The exception description.
	 */
	public void addPropertyExpression(Object comp, String propertyName, DataStoreBuffer dsb, DataStoreExpression propExpression) throws NoSuchMethodException, DataStoreException {

		DataStoreEvaluator dse = new DataStoreEvaluator(dsb, propExpression);
		addPropertyExpression(comp, propertyName, dse);
	}

	/**
	 * The propExpression will be evaluated for each row in the DataStore and the set method for the specified property will be called on the specified object.
	 @param comp The component to set the property for
	 * @param propertyName The name of the property to set. The component must have a corresponding setProperty method or this method will throw a NoSuchMethodException
	 * @param expression java.lang.String The datastore expression to evaluate
	 * @exception java.lang.NoSuchMethodException The exception description.
	 * @exception com.salmonllc.sql.DataStoreException The exception description.
	 */
	public void addPropertyExpression(Object comp, String propertyName, DataStoreBuffer dsb, String expression) throws NoSuchMethodException, DataStoreException {
		DataStoreEvaluator dse = new DataStoreEvaluator(dsb, expression);
		addPropertyExpression(comp, propertyName, dse);
	}

	/**
	 * The propExpression will be evaluated by the  evaluated for each row in the DataStore and the set method for the specified property will be called on the specified object.
	 * @param comp The component to set the property for
	 * @param propertyName The name of the property to set. The component must have a corresponding setProperty method or this method will throw a NoSuchMethodException
	 * @param expEval DataStoreEvaluator The datastore evaluator that evaluates the expression
	 * @exception java.lang.NoSuchMethodException The exception description.
	 * @exception com.salmonllc.sql.DataStoreException The exception description.
	 */
	public void addPropertyExpression(Object comp, String propertyName, DataStoreEvaluator expEval) throws NoSuchMethodException, DataStoreException {
		Class c = comp.getClass();
		Method m[] = c.getMethods();
		Method exe = null;
		String name = "set" + propertyName;
		Class parms[] = null;
		for (int i = 0; i < m.length; i++) {
			if (name.equalsIgnoreCase(m[i].getName())) {
				parms = m[i].getParameterTypes();
				if (parms.length == 1) {
					exe = m[i];
					break;
				}
			}
		}
		if (exe == null)
			throw new NoSuchMethodException("Couldn't find a set method for property:" + propertyName);
		ThreeObjectContainer t = new ThreeObjectContainer(comp, exe, expEval);
		if (_propertyExpressions == null)
			_propertyExpressions = new Vector();
		_propertyExpressions.addElement(t);
	}
	public void processPropertyExpressions(int row) {
		if (_propertyExpressions == null)
			return;

		ThreeObjectContainer c = null;
		Object comp = null;
		Method meth = null;
		DataStoreEvaluator eval = null;

		int propertyExpressionsSize = _propertyExpressions.size();
		for (int i = 0; i < propertyExpressionsSize; i++) {
			c = (ThreeObjectContainer) _propertyExpressions.elementAt(i);
			comp = c.getObject1();
			meth = (Method) c.getObject2();
			eval = (DataStoreEvaluator) c.getObject3();
			HtmlPage.executePropertyMethod(comp, meth, eval, row);
		}
	}

	/**
	 * @return true if the tree will automatically scroll to the location of the previous submit
	 */
	public boolean getAutoScroll() {
		return _autoScroll;
	}

	/**
	 * set to true for thee tree to automatically scroll page to the location it was on as of the previous submit
	 */
	public void setAutoScroll(boolean b) {
		_autoScroll = b;
	}

	/**
	 * Returns whether click sort on the heading components is enabled
	 */
	public boolean getClickSort() {
		return _clickSort;
	}

	/**
	 * Set to true to allow heading components to do a click sort
	 */
	public void setClickSort(boolean b) {
		_clickSort = b;
	}

	private void doSort(int colNo) {
		if (_tb.canSortOnColumn(colNo)) {
			if (colNo == _sortColumn) {
				if (_sortDir == TreeBuffer.SORT_ASC)
					_sortDir = TreeBuffer.SORT_DES;
				else
					_sortDir = TreeBuffer.SORT_ASC;
			} else
				_sortDir = TreeBuffer.SORT_ASC;
			
			if (colNo == 0 && _sortPathDir != TreeBuffer.SORT_ANY) {
				if (_sortColumn != 0)
					_sortPathDir = TreeBuffer.SORT_ASC;
				else if (_sortPathDir == TreeBuffer.SORT_ASC)
					_sortPathDir = TreeBuffer.SORT_DES;
				else
					_sortPathDir = TreeBuffer.SORT_ASC;			
			}
		
			_sortColumn = colNo;
			_tb.sort(_sortColumn, _sortPathDir, _sortDir);
		}
	}
}
