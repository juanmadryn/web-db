<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ taglib uri="/WEB-INF/birt.tld" prefix="birt"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<script src="javascripts/prototype.js" type="text/javascript"></script>
<script src="javascripts/scriptaculous.js" type="text/javascript"></script>
<script src="javascripts/utils.js" type="text/javascript"></script>
<salmon:page
	controller="infraestructura.controllers.BaseController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<jsp:include page="reporte_ranking_compradores.jsp" flush="true"></jsp:include>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage />