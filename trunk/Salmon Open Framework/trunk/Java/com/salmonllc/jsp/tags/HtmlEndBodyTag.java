package com.salmonllc.jsp.tags;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/HtmlEndBodyTag.java $
//$Author: Srufle $
//$Revision: 17 $
//$Modtime: 9/26/04 10:55a $
/////////////////////////

import com.salmonllc.jsp.*;
import javax.servlet.jsp.*;

/**
 * This tag is used to implement the Html End Body tag
 */

public class HtmlEndBodyTag extends BaseEmptyTag {
/**
 * This method must be implemented by each subclass of this tag. Each tag except NestedTags has an Html or Jsp Component associated with it. This method must be implemented to insure that the correct component gets created.
 */
public com.salmonllc.html.HtmlComponent createComponent() {
	return null;
}
/**
 * This method is called when necessary to generate the required html for a tag. It should be overridden by tags that have more Html to generate (generally tags that require several passes to complete).
 */   

protected void generateComponentHTML(JspWriter w) throws Exception {
   JspController cont = getHelper().getController();
   JspController sec = cont.getSecondaryController();
   if (!getHelper().getTagContext().getDreamWeaverMode() && (cont.getCurrentRequest().getMethod().equals("GET") || cont.getDisableRedirect() || cont.isRequestFromForward()) ) {
  	   cont.writeNamedHtml(w);
   	   w.println("<SCRIPT>");
    w.println("function " + cont.getOnLoadFunction() + "{");
       cont.generateScriptHtml(w);
       if (sec != null)
           sec.generateScriptHtml(w);
       w.println("}");
	   cont.writeNamedScripts(w);
       if (sec != null)
		  sec.writeNamedScripts(w);
       w.println("</SCRIPT>");
   }   
	w.print("</body>"); 
}
}
