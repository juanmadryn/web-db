<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp" extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="infraestructura.controllers.AccountInfoController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
                  <salmon:form name="pageForm">
                    <salmon:datasource name="dsUserDetail" type="SQL">
                      <salmon:datasourcedef>
                        <salmon:field tablename="credit_card" fieldname="credit_card_type" datatype="int" primarykey="False" updateable="False"/>
                        <salmon:field tablename="credit_card" fieldname="exp_date" datatype="varchar" primarykey="False" updateable="False"/>
                        <salmon:field tablename="credit_card" fieldname="credit_card_number" datatype="varchar" primarykey="False" updateable="False"/>
                        <salmon:field tablename="credit_card" fieldname="credit_card_id" datatype="int" primarykey="True" updateable="False"/>
                        <salmon:field tablename="user_detail" fieldname="credit_card_id" datatype="int" primarykey="False" updateable="False"/>
                        <salmon:field tablename="user_detail" fieldname="user_id" datatype="int" primarykey="False" updateable="False"/>
                        <salmon:field tablename="user_detail" fieldname="zip" datatype="varchar" primarykey="False" updateable="False"/>
                        <salmon:field tablename="user_detail" fieldname="state" datatype="varchar" primarykey="False" updateable="False"/>
                        <salmon:field tablename="user_detail" fieldname="phone_no" datatype="varchar" primarykey="False" updateable="False"/>
                        <salmon:field tablename="user_detail" fieldname="last_name" datatype="varchar" primarykey="False" updateable="False"/>
                        <salmon:field tablename="user_detail" fieldname="first_name" datatype="varchar" primarykey="False" updateable="False"/>
                        <salmon:field tablename="user_detail" fieldname="email" datatype="varchar" primarykey="False" updateable="False"/>
                        <salmon:field tablename="user_detail" fieldname="country" datatype="varchar" primarykey="False" updateable="False"/>
                        <salmon:field tablename="user_detail" fieldname="city" datatype="varchar" primarykey="False" updateable="False"/>
                        <salmon:field tablename="user_detail" fieldname="addr" datatype="varchar" primarykey="False" updateable="False"/>
                        <salmon:join outer="false">
                          <salmon:joinfield fieldname1="credit_card.credit_card_id" fieldname2="user_detail.credit_card_id"/>
                        </salmon:join>
                      </salmon:datasourcedef>
                    </salmon:datasource>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                      <tr> 
                        <td> <table width="100%">
                            <tr> 
                              <td> <salmon:text name="text70" text="Your account info goes here" font="PageHeadingFont" textlocalekey="HdngYourAccInfo"/> </td>
                            </tr>
                          </table></td>
                      </tr>
                      <tr> 
                        <td><%@include file="message.jsp"%> </td>
                      </tr>
                      <tr > 
                        <td  > </td>
                      </tr>
                      <tr > 
                        <td > <salmon:table name="tableContactInfo" width="-1%" border="0">
                            <tr>
                              <td colspan="3" align="Left"></td>
                              <salmon:text name="text3" text="Contact info goes here" font="SectionHeadingFont" textlocalekey="HdngContactInfo"/>
                            <tr> 
                            <tr>
                              <td align="Right"><salmon:text name="text4" text="First name goes here" font="EmphasisFont" textlocalekey="lblFirstName"/></td>
                              <td width="10" ></td>
                              <td align="Left"><salmon:text name="text5" text="First name goes here" datasource="dsUserDetail:user_detail.first_name"/></td>
                            <tr> 
                            <tr>
                              <td align="Right"><salmon:text name="text6" text="Last name goes here" font="EmphasisFont" textlocalekey="lblLastName"/></td>
                              <td width="10" ></td>
                              <td align="Left"><salmon:text name="text7" text="Last name  goes here" datasource="dsUserDetail:user_detail.last_name"/></td>
                            <tr> 
                            <tr>
                              <td align="Right"><salmon:text name="text8" text="Address goes here" font="EmphasisFont" textlocalekey="lblStrAddr"/></td>
                              <td width="10" ></td>
                              <td align="Left"><salmon:text name="text9" text="Address goes here" datasource="dsUserDetail:user_detail.addr"/></td>
                            <tr> 
                            <tr>
                              <td align="Right"><salmon:text name="text10"  font="EmphasisFont" text="City goes here" textlocalekey="lblCity"/></td>
                              <td width="10" ></td>
                              <td align="Left"><salmon:text name="text11" text="City goes here" datasource="dsUserDetail:user_detail.city"/></td>
                            <tr> 
                            <tr>
                              <td align="Right"><salmon:text name="text12"  font="EmphasisFont" text="State goes here" textlocalekey="lblState"/></td>
                              <td width="10" ></td>
                              <td align="Left"><salmon:input type="state" name="ddlState" codes="True" datasource="dsUserDetail:user_detail.state"></salmon:input></td>
                            <tr> 
                            <tr>
                              <td align="Right"><salmon:text name="text13" text="Zip goes here" font="EmphasisFont" textlocalekey="lblZip"/></td>
                              <td width="10" ></td>
                              <td align="Left"><salmon:text name="text14" text="Zip goes here" datasource="dsUserDetail:user_detail.zip"/></td>
                            <tr> 
                            <tr>
                              <td align="Right"><salmon:text name="text15" text="Country goes here" font="EmphasisFont" textlocalekey="lblCountry"/></td>
                              <td width="10" ></td>
                              <td align="Left"><salmon:text name="text16" text="Country goes here" datasource="dsUserDetail:user_detail.country"/></td>
                            <tr> 
                            <tr>
                              <td align="Right"><salmon:text name="text17" text="Tel no goes here" font="EmphasisFont" textlocalekey="lblTelNo"/></td>
                              <td width="10" ></td>
                              <td align="Left"><salmon:input type="phone" name="phoneNo" separator="-" areacodestart="(" areacodeend=")" datasource="dsUserDetail:user_detail.phone_no"></salmon:input></td>
                            <tr> 
                            <tr>
                              <td align="Right"><salmon:text name="text18" text="Email goes here"  font="EmphasisFont" textlocalekey="lblEmail"/></td>
                              <td width="10" ></td>
                              <td align="Left"><salmon:text name="text19" text="Email goes here" datasource="dsUserDetail:user_detail.email"/></td>
                            <tr> 
                            <tr >
                              <td height="20"></td>
                            <tr> 
                            <tr>
                              <td colspan="2" align="Left"><salmon:text name="text333" text="Credit card info goes here" font="SectionHeadingFont" textlocalekey="HdngCCInfo"/></td>
                            <tr> 
                            <tr>
                              <td align="Right"><salmon:text name="text21" text="Credit card type goes here" font="EmphasisFont" textlocalekey="lblCardType"/></td>
                              <td width="10" ></td>
                              <td align="Left"><salmon:input type="select" name="ddlCardType" size="16" multiple="False" datasource="dsUserDetail:credit_card.credit_card_type">
                                  <salmon:option display="abc" key="123" table="credit_card_type" keycolumn="credit_card_type" displaycolumn="description"/>
                                </salmon:input></td>
                            <tr> 
                            <tr>
                              <td align="Right"><salmon:text name="text22" text="CC no goes here" font="EmphasisFont" textlocalekey="lblCardNumber"/></td>
                              <td width="10" ></td>
                              <td align="Left"><salmon:text name="text23" text="CC no goes here" datasource="dsUserDetail:credit_card.credit_card_number"/></td>
                            <tr> 
                            <tr>
                              <td align="Right"><salmon:text name="text24" text="CC Exp date goes here" font="EmphasisFont" textlocalekey="lblCardExpDate"/></td>
                              <td width="10" ></td>
                              <td align="Left"><salmon:text name="text25" text="Exp date goes here" datasource="dsUserDetail:credit_card.exp_date"/></td>
                            <tr></salmon:table></td>
                      </tr>
                      <tr> 
                        <td height="20"> </td>
                      </tr>
                      <tr> 
                        <td><salmon:a name="lnkEditacct" href="%AccountEdit" onclick="document.forms['pageForm'].submit();">
                          <salmon:text name="text27" text="Edit your acc goes here" font="LargeLinkFont" textlocalekey="lnkTextEditYrAccInfo"/>
                          </salmon:a></td>
                      </tr>
                    </table>
                    </salmon:form> <jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage/>