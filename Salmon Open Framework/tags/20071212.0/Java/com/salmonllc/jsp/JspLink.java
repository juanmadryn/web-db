package com.salmonllc.jsp;

import com.salmonllc.html.HtmlValidatorText;
import com.salmonllc.html.events.SubmitEvent;
import com.salmonllc.html.events.SubmitListener;
import com.salmonllc.properties.Props;
import com.salmonllc.sql.*;
import com.salmonllc.util.MessageLog;
import com.salmonllc.util.Util;
import java.util.Hashtable;
import java.util.Vector;


/**
 * This container will construct an anchor tag with a html link (HREF) reference.
 */
public class JspLink extends JspContainer {
	private DataStoreEvaluator _dsEval;
	private String _bracketFont;
	private String _href;
	private String _onClick;
	private String _onMouseOut;
	private String _onMouseOver;
	private String _style;
	private String _target;
	private String _zoomTo = null;
	private Vector _listeners;
	private boolean _doSubmit = false;
	private boolean _genBracket = false;
	private boolean _generateLink = true;
	private boolean _submitAlreadyGenerated = false;
	private int _rowNo = -1;
	private Integer _tabIndex;
	private String _accessKey;
	private String _title;

	/**
	 * JspLink constructor comment.
	 *
	 * @param name The name of the link
	 * @param href The url for the link
	 * @param p The page the link will go in.
	 */
	public JspLink(String name, String href, com.salmonllc.html.HtmlPage p) {
		this(name, href, null, p);
	}

	/**
	 * JspLink constructor comment.
	 *
	 * @param name The name of the link
	 * @param href The url for the link
	 * @param target The target for the link
	 * @param p The page the link will go in.
	 */
	public JspLink(String name, String href, String target, com.salmonllc.html.HtmlPage p) {
		super(name, p);
		_href = href;
		_target = target;
	}

	/**
	 * This method sets whether or not the anchor tag will be generated with brackets([]) around the Tag. Pass true to have the tag generated with and false not to.
	 *
	 * @param bracket DOCUMENT ME!
	 */
	public void setBracket(boolean bracket) {
		_genBracket = bracket;
	}

	/**
	 * This method sets the font for the brackets around the link.
	 *
	 * @param font DOCUMENT ME!
	 */
	public void setBracketFont(String font) {
		_bracketFont = font;
	}

	/**
	 * Sets whether or not the component will submit the page before transfering control to the link page. If the link does a submit, all the user entered data will sent to the server to be recorded. If the flag is true, the target and onClick properties cannot be used.
	 *
	 * @param doSubmit DOCUMENT ME!
	 */
	public void setDoSubmit(boolean doSubmit) {
		_doSubmit = doSubmit;
	}

	/**
	 * Gets whether or not the component will submit the page before transfering control to the link page. If the link does a submit, all the user entered data will sent to the server to be recorded.
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean getDoSubmit() {
		return _doSubmit;
	}

	/**
	 * This method sets whether or not the anchor tag will be generated for the link. Pass true to have the a tag generated and false not to. Setting the value to false, will cause the items inside the container to be displayed but nothing will happen when the user clicks on them.
	 *
	 * @param gen DOCUMENT ME!
	 */
	public void setGenerateLink(boolean gen) {
		_generateLink = gen;
	}

	/**
	 * This method gets whether or not the anchor tag will be generated for the link. Pass true to have the a tag generated and false not to. Setting the value to false, will cause the items inside the container to be displayed but nothing will happen when the user clicks on them.
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean getGenerateLink() {
		return _generateLink;
	}

	/**
	 * Sets the href for the link
	 *
	 * @param href java.lang.String
	 */
	public void setHref(String href) {
		_href = href;
	}

	/**
	 * Returns the href for the link
	 *
	 * @return DOCUMENT ME!
	 */
	public String getHref() {
		return _href;
	}

	/**
	 * This method sets a datastore expression that will be used to compute the href for the link.
	 *
	 * @param ds com.salmonllc.sql.DataStoreBuffer
	 * @param expression The expression to evaluate
	 *
	 * @throws Exception DOCUMENT ME!
	 */
	public void setHrefExpression(DataStoreBuffer ds, DataStoreExpression expression)
			throws Exception {
		try {
			_dsEval = new DataStoreEvaluator(ds, expression);
		} catch (Exception e) {
			MessageLog.writeErrorMessage("setHrefExpression(DataStoreBuffer ds, DataStoreExpression expression )", e, this);
			throw e;
		}
	}

	/**
	 * This method sets a datastore expression that will be used to compute the href for the link.
	 *
	 * @param ds com.salmonllc.sql.DataStoreBuffer
	 * @param expression java.lang.String
	 *
	 * @throws Exception DOCUMENT ME!
	 */
	public void setHrefExpression(DataStoreBuffer ds, String expression)
			throws Exception {
		try {
			_dsEval = new DataStoreEvaluator(ds, expression);
		} catch (Exception e) {
			MessageLog.writeErrorMessage("setHrefExpression(DataStoreBuffer ds, String expression )", e, this);
			throw e;
		}
	}

	/**
	 * This method gets the DataStoreEvaluator being used for href expressions.
	 *
	 * @return DataStoreEvaluator
	 *
	 * @see DataStoreEvaluator
	 */
	public DataStoreEvaluator getHrefExpression() {
		return _dsEval;
	}

	/**
	 * Use this method to set the javascript that will be executed when the user clicks on one of the components in the link.
	 *
	 * @param onClick DOCUMENT ME!
	 */
	public void setOnClick(String onClick) {
		_onClick = onClick;
	}

	/**
	 * Use this method to get the javascript that will be executed when the user clicks on one of the components in the link.
	 *
	 * @return DOCUMENT ME!
	 */
	public String getOnClick() {
		return _onClick;
	}

	/**
	 * Use this method to set the javascript that will be executed when the mouse passes over out of all the components
	 *
	 * @param onMouseOut DOCUMENT ME!
	 */
	public void setOnMouseOut(String onMouseOut) {
		_onMouseOut = onMouseOut;
	}

	/**
	 * Use this method to get the javascript that will be executed when the mouse passes over out of all the components
	 *
	 * @return DOCUMENT ME!
	 */
	public String getOnMouseOut() {
		return _onMouseOut;
	}

	/**
	 * Use this method to set the javascript that will be executed when the mouse passes over any component in the link
	 *
	 * @param onMouseOver DOCUMENT ME!
	 */
	public void setOnMouseOver(String onMouseOver) {
		_onMouseOver = onMouseOver;
	}

	/**
	 * Use this method to get the javascript that will be executed when the mouse passes over any component in the link
	 *
	 * @return DOCUMENT ME!
	 */
	public String getOnMouseOver() {
		return _onMouseOver;
	}

	/**
	 * This method sets the sytle for the link.
	 *
	 * @param style DOCUMENT ME!
	 */
	public void setStyle(String style) {
		_style = style;
	}

	/**
	 * Use this method to get the style that will be used to display the link
	 *
	 * @return DOCUMENT ME!
	 */
	public String getStyle() {
		return _style;
	}

	/**
	 * Sets whether the script that allows a JspLink to listen for SumitPerformed events  has should be  generated. A case where you would need this method is if when you are on a page and  in the submit performed event you leave the page and the JspLink is hidden when you come back.  You will have to set the value to true before you leave the page otherwise when you come back to the page you
	 * will recieve a javascript error.
	 *
	 * @param submitAlreadyGenerated boolean value
	 */
	public void setSubmitAlreadyGenerated(boolean submitAlreadyGenerated) {
		_submitAlreadyGenerated = submitAlreadyGenerated;
	}

	/**
	 * Returns the whether the script that allows a JspLink to listen for SumitPerformed events  has been generated.
	 *
	 * @return boolean
	 */
	public boolean isSubmitAlreadyGenerated() {
		return _submitAlreadyGenerated;
	}

	/**
	 * Sets the target for the link.<BR> _blank opens the destination document in a new unnamed window. <BR> _parent opens the destination document in the parent window of the one displaying the current document. <BR> _self opens the destination document in the same window as the one in which the link was clicked. <BR> _top opens the destination document in the full body of the current window.
	 * This value can be used to ensure that the       destination document takes over the full window even if the original document was displayed in a frame. <BR>
	 *
	 * @param target DOCUMENT ME!
	 */
	public void setTarget(String target) {
		_target = target;
	}

	/**
	 * Returns the target for the link.
	 *
	 * @return DOCUMENT ME!
	 */
	public String getTarget() {
		return _target;
	}

	/**
	 * This method adds a listener the will be notified when this button causes the page to be submitted.
	 *
	 * @param l The listener to add.
	 */
	public void addSubmitListener(SubmitListener l) {
		if (_listeners == null) {
			_listeners = new Vector();
		}

		int listenersSize = _listeners.size();

		for (int i = 0; i < listenersSize; i++) {
			if (((SubmitListener) _listeners.elementAt(i)) == l) {
				return;
			}
		}

		_listeners.addElement(l);
		setDoSubmit(true);
	}

	/**
	 * Clears the submit component in this container or children containers. The container stores which component inside it submitted a particular page for one invocation so it can route to the correct submit performed methods. Once that's done, the framework needs to clear out that value for the next page invocation. This method is used by the framework and should not be called directly.
	 */
	public void clearSubmit() {
		super.clearSubmit();
		setSubmitAlreadyGenerated(false);
	}

	public boolean executeEvent(int eventType) throws Exception {
		if ((eventType == EVENT_SUBMIT) && (_zoomTo != null)) {
			// check listeners
			// if  any have been registered
			if ((_listeners != null) && (_listeners.size() > 0)) {
				SubmitEvent e = new SubmitEvent(getPage(), this, getName(), getFullName(), _rowNo);
				SubmitListener l = null;
				int listenersSize = _listeners.size();

				for (int i = 0; i < listenersSize; i++) {
					l = (SubmitListener) _listeners.elementAt(i);
					e.setNextListener( _listeners.size() > (i + 1) ?_listeners.elementAt(i + 1) : null);
					if (!l.submitPerformed(e)) {
						return false;
					}
				}

				return true;
			} else {
				getPage().getCurrentResponse().sendRedirect(_zoomTo);
				_zoomTo = null;
			}
		} else if (!super.executeEvent(eventType)) {
			return false;
		}

		return true;
	}

	/**
	 * Generates the Html for the component. This method is called by the framework and should not be called directly
	 *
	 * @param t DOCUMENT ME!
	 * @param box DOCUMENT ME!
	 * @param rowNo DOCUMENT ME!
	 *
	 * @throws java.io.IOException DOCUMENT ME!
	 */
	public void generateHTML(TagWriter t, String box, int rowNo)
			throws java.io.IOException {
		StringBuffer sb = new StringBuffer();

		if (!isSubmitAlreadyGenerated()) {
			if (_visible && _doSubmit && _generateLink) {
				sb.append("<INPUT TYPE=\"HIDDEN\" NAME=\"" + getFullName() + "_HIDDEN\" VALUE=\"\">");
				getPage().writeScript(getFormString() + getFullName() + "_HIDDEN.value = '';");
				sb.append("<SCRIPT>");
				sb.append("function " + getFullName() + "_submit(row) {");
				sb.append(getFormString() + getFullName() + "_HIDDEN.value = row;");
				sb.append(getFormString() + "submit();");
				sb.append("}");
				sb.append("</SCRIPT>");
				setSubmitAlreadyGenerated(true);
			}
		}

		if (!_visible)
			return;


		String onClick = _onClick;
		String valScript = HtmlValidatorText.generateOnClickJavaScriptForButtons(_onClick,_listeners,getFullName());
		if (valScript != null) {
			sb.append(valScript);
			onClick = "return " + HtmlValidatorText.generateOnClickJavaScriptFunctionName(getFullName()) + ";";
		}

		_zoomTo = null;

		StringBuffer href = null;

		if (_doSubmit) {
			href = new StringBuffer("javascript:" + getFullName() + "_submit('" + rowNo + "');");
		} else {
			try {
				if (_dsEval != null) {
					if (rowNo > -1) {
						_href = _dsEval.evaluateRowFormat(rowNo);
					} else {
						_href = _dsEval.evaluateRowFormat();
					}
				}
			} catch (Exception e) {
				MessageLog.writeErrorMessage("generateHTML._dsEval.evaluateRowFormat", e, this);
			}

			// sr 12-08-2000 was getting a null pointer exception
			if (!Util.isNull(_href)) {
				href = new StringBuffer(_href);
				if (!href.toString().toLowerCase().startsWith("javascript:")) {
					int hrefLength = href.length();

					for (int i = 0; i < hrefLength; i++) {
						if (href.charAt(i) == ' ') {
							href.setCharAt(i, '+');
						}
					}
				}
			}
		}

		String row = "";
		String bracketStartFont = "";
		String bracketEndFont = "";

		if (rowNo != -1) {
			row = "_" + row + new Integer(rowNo).toString();
		}

		if (_genBracket) {
			com.salmonllc.properties.Props props = getPage().getPageProperties();

			if (_bracketFont != null) {
				bracketStartFont = props.getProperty(_bracketFont + Props.TAG_START);
				bracketEndFont = props.getProperty(_bracketFont + Props.TAG_END);
			} else {
				bracketStartFont = props.getProperty(com.salmonllc.html.HtmlText.FONT_DEFAULT + Props.TAG_START);
				bracketEndFont = props.getProperty(com.salmonllc.html.HtmlText.FONT_DEFAULT + Props.TAG_END);
			}

			sb.append(bracketStartFont + " [" + bracketEndFont);
		}

		sb.append("<A NAME=\"" + getFullName() + row + "\" HREF=\"" + encodeURL(href) + "\"");

		if (_target != null) {
			sb.append(" TARGET=\"" + _target + "\"");
		}

		if (onClick != null) {
			sb.append(" ONCLICK=\"" + onClick + "\"");
		}

		if (_onMouseOver != null) {
			sb.append(" ONMOUSEOVER=\"" + _onMouseOver + "\"");
		}

		if (_onMouseOut != null) {
			sb.append(" ONMOUSEOUT=\"" + _onMouseOut + "\"");
		}

		if (_style != null) {
			sb.append(" CLASS=\"" + _style + "\"");
		}
		if (_title != null)
			  sb.append(" TITLE=\"" + _title + "\"");

		if (_tabIndex != null) {
			sb.append(" tabindex=\"");
			sb.append(_tabIndex.toString());
			sb.append("\"");
		}
		if (_accessKey != null) {
			sb.append(" accesskey=\"");
			sb.append(_accessKey);
			sb.append("\"");
		}
		sb.append(">");

		if (_generateLink) {
			t.print(sb.toString(), TagWriter.TYPE_BEGIN_TAG);
		}

		t.print(box, TagWriter.TYPE_CONTENT);

		if (_generateLink) {
			if (_genBracket) {
				t.print("</A>" + bracketStartFont + "]" + bracketEndFont, TagWriter.TYPE_END_TAG);
			} else {
				t.print("</A>", TagWriter.TYPE_END_TAG);
			}
		}
	}

	/**
	 * This method inserts a listener the will be notified when this button causes the page to be submitted.
	 *
	 * @param l The listener to add.
	 */
	public void insertSubmitListener(SubmitListener l) {
		if (_listeners == null) {
			_listeners = new Vector();
		}

		int listenersSize = _listeners.size();

		for (int i = 0; i < listenersSize; i++) {
			if (((SubmitListener) _listeners.elementAt(i)) == l) {
				return;
			}
		}

		_listeners.insertElementAt(l, 0);
		setDoSubmit(true);
	}

	/**
	 * This method will process the parms from a post for every component in the container.
	 *
	 * @param parms DOCUMENT ME!
	 * @param rowNo DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws Exception DOCUMENT ME!
	 */
	public boolean processParms(Hashtable parms, int rowNo)
			throws Exception {
		if (super.processParms(parms, rowNo)) {
			return true;
		}

		String name = getFullName() + "_HIDDEN";
		String p[] = (String[]) parms.get(name);

		if ((p != null) && (p[0].trim().length() > 0) && (_zoomTo == null)) {
			_submit = this;

			_rowNo = Integer.parseInt(p[0].trim());

			String href = "";

			if (_dsEval != null) {
				if (_rowNo > -1) {
					href = _dsEval.evaluateRowFormat(_rowNo);
				} else {
					href = _dsEval.evaluateRowFormat();
				}
			} else {
				href = _href;
			}

			_zoomTo = href;

			return true;
		}

		return false;
	}

	/**
	 * This method removes a listener from the list that will be notified if this button causes the page to be submitted.
	 *
	 * @param l The listener to remove.
	 */
	public void removeSubmitListener(SubmitListener l) {
		if (_listeners == null) {
			return;
		}

		//
		int listenersSize = _listeners.size();

		for (int i = 0; i < listenersSize; i++) {
			if (((SubmitListener) _listeners.elementAt(i)) == l) {
				_listeners.removeElementAt(i);

				return;
			}
		}
	}

	/**
	 * @returns the access key html attribute
	 */
	public String getAccessKey() {
		return _accessKey;
	}


	/**
	 * @returns the tab index html attribute
	 */
	public int getTabIndex() {
		if (_tabIndex == null)
			return -1;
		return _tabIndex.intValue();
	}

	/**
	 * @param sets the access key html attribute
	 */
	public void setAccessKey(String string) {
		_accessKey = string;
	}



	/**
	 * @param sets the tab index html attribute. You can also pass TAB_INDEX_DEFAULT to use the default tab index for the component or TAB_INDEX_NONE to keep this component from being tabbed to
	 */
	public void setTabIndex(int val) {
		if (val == -1)
			_tabIndex = null;
		else
			_tabIndex = new Integer(val);
	}
	/**
	 * @return
	 */
	public String getTitle() {
		return _title;
	}

	/**
	 * @param string
	 */
	public void setTitle(String string) {
		_title = string;
	}

}
