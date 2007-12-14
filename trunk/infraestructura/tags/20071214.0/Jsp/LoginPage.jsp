<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp" extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="infraestructura.controllers.LoginPageController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
                  <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr> 
                      <td> <salmon:text name="text1" font="PageHeadingFont" textlocalekey="btnLblSignIn" text="Ingrese a la Aplicación ..."/> <br> <br> </td>
                    </tr>
                    <tr> 
                      <td><%@include file="message.jsp"%> </td>
                    </tr>
                    <tr> 
                      <td> <table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="49%" valign="top">
							<salmon:box name="box1" width="100%"> <salmon:form name="loginForm">
                                            <table width="100%" border="0">
                                              <tr> 
                                                <td align="Center" colspan="2" height="40">
												<salmon:text name="text4" text="Bienvenido a la aplicación" font="EmphasisFont" textlocalekey="HdngLogin"/></td>
                                              </tr>
                                              <tr> 
                                                <td  align="Right"><salmon:text name="userPrompt" text="nombre de usuario" font="DefaultFont" textlocalekey="lblEmail"/></td>
                                                <td align="Left"><salmon:input type="text" name="userField" size="20" minlength="7" maxlength="60"></salmon:input></td>
                                              </tr>
                                              <tr> 
                                                <td align="Right"><salmon:text name="passwordPrompt" text="Clave" font="DefaultFont" textlocalekey="lblPassword"/></td>
                                                <td align="Left"><salmon:input type="password" name="passwordField" size="20" maxlength="60"></salmon:input></td>
                                              </tr>
                                              <tr> 
                                                <td height="30" valign="top" align="center" colspan="2"><salmon:text name="textRemMe" text="Recordarme" textlocalekey="lblRememberMe"/>
                                                  <salmon:input type="checkbox" name="chbxRememberMe" checkedtruevalue="1" checkedfalsevalue="0" checked="true"></salmon:input></td>
                                              </tr>
                                              <tr> 
                                                <td  align="Center" colspan="2"><salmon:input  classname="sbttn" type="submit" name="btnSignIn" value="Ingresar" displaynamelocalekey="btnLblSubmit"></salmon:input></td>
                                              </tr>
                                            </table>
                                            </salmon:form> </salmon:box> </td>
                          </tr>
                        </table></td>
                    </tr>
                  </table>
                  <jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage/>