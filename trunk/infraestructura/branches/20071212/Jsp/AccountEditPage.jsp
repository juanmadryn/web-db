<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp" extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="infraestructura.controllers..AccountEditController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
                  <salmon:form name="pageForm">
                    <salmon:datasource name="dsUserDetail" type="SQL">
                      <salmon:datasourcedef>
                        <salmon:field tablename="credit_card" fieldname="credit_card_type" datatype="INT" primarykey="False" updateable="True"/>
                        <salmon:field tablename="credit_card" fieldname="exp_date" datatype="varchar" primarykey="False" updateable="True"/>
                        <salmon:field tablename="credit_card" fieldname="credit_card_number" datatype="varchar" primarykey="False" updateable="True"/>
                        <salmon:field tablename="credit_card" fieldname="credit_card_id" datatype="int" primarykey="True" updateable="True"/>
                        <salmon:field tablename="user_detail" fieldname="credit_card_id" datatype="int" primarykey="False" updateable="True"/>
                        <salmon:field tablename="user_detail" fieldname="user_id" datatype="int" primarykey="True" updateable="True"/>
                        <salmon:field tablename="user_detail" fieldname="zip" datatype="varchar" primarykey="False" updateable="True"/>
                        <salmon:field tablename="user_detail" fieldname="state" datatype="varchar" primarykey="False" updateable="True"/>
                        <salmon:field tablename="user_detail" fieldname="phone_no" datatype="varchar" primarykey="False" updateable="True"/>
                        <salmon:field tablename="user_detail" fieldname="last_name" datatype="varchar" primarykey="False" updateable="True"/>
                        <salmon:field tablename="user_detail" fieldname="first_name" datatype="varchar" primarykey="False" updateable="True"/>
                        <salmon:field tablename="user_detail" fieldname="email" datatype="varchar" primarykey="False" updateable="True"/>
                        <salmon:field tablename="user_detail" fieldname="country" datatype="varchar" primarykey="False" updateable="True"/>
                        <salmon:field tablename="user_detail" fieldname="city" datatype="varchar" primarykey="False" updateable="True"/>
                        <salmon:field tablename="user_detail" fieldname="addr" datatype="varchar" primarykey="False" updateable="True"/>
                        <salmon:bucket name="bkt_cc_exp_month" datatype="varchar"/>
                        <salmon:bucket name="bkt_cc_exp_year" datatype="varchar"/>
                        <salmon:join outer="False">
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
                        <td> <salmon:validator name="valErrorMessage" visible="false" breaksafter="2"  allowmultipleerrors="true" validatorclass="infraestructura.models.ContactValidator" datasource="dsUserDetail" submitcomponents="btnSubmit" ></salmon:validator> </td>
                      </tr>
                      <tr> 
                        <td> </td>
                      </tr>
                      <tr> 
                        <td > <salmon:table name="tableContactInfo" width="-1%" border="0">
                            <tr>
                              <td colspan="3" align="Left"><salmon:text name="text3" text="Contact info goes here" font="SectionHeadingFont" textlocalekey="HdngContactInfo"/></td>
                            </tr>
                            <tr>
                              <td align="Right"><salmon:text name="capTeFirstName" text="First name goes here" font="EmphasisFont" textlocalekey="lblFirstName"/></td>
                              <td width="10" ></td>
                              <td align="Left"><salmon:input type="text" name="teFirstName" size="26" maxlength="50" datasource="dsUserDetail:user_detail.first_name"></salmon:input></td>
                            </tr>
                            <tr>
                              <td align="Right"><salmon:text name="capTeLastName" text="Last name goes here" font="EmphasisFont" textlocalekey="lblLastName"/></td>
                              <td width="10" ></td>
                              <td align="Left"><salmon:input type="text" name="teLastName" size="26" maxlength="50" datasource="dsUserDetail:user_detail.last_name"></salmon:input></td>
                            </tr>
                            <tr>
                              <td align="Right"><salmon:text name="capTeAddr" text="Address goes here" font="EmphasisFont" textlocalekey="lblStrAddr"/></td>
                              <td width="10" ></td>
                              <td align="Left"><salmon:input type="text" name="teAddr" size="26" maxlength="250" datasource="dsUserDetail:user_detail.addr"></salmon:input></td>
                            </tr>
                            <tr>
                              <td align="Right"><salmon:text name="text10"  font="EmphasisFont" text="City goes here" textlocalekey="lblCity"/></td>
                              <td width="10" ></td>
                              <td align="Left"><salmon:input type="text" name="teCity" size="26" maxlength="50" datasource="dsUserDetail:user_detail.city"></salmon:input></td>
                            </tr>
                            <tr>
                              <td  align="Right"><salmon:text name="text12"  font="EmphasisFont" text="State goes here" textlocalekey="lblState"/></td>
                              <td width="10" ></td>
                              <td align="Left"><salmon:input type="state" name="ddlState" codes="True" datasource="dsUserDetail:user_detail.state"></salmon:input></td>
                            </tr>
                            <tr>
                              <td  align="Right"><salmon:text name="text13" text="Zip goes here" font="EmphasisFont" textlocalekey="lblZip"/></td>
                              <td width="10" ></td>
                              <td align="Left"><salmon:input type="zip" name="teZip" extendedzip="False" datasource="dsUserDetail:user_detail.zip"></salmon:input></td>
                            </tr>
                            <tr>
                              <td align="Right"><salmon:text name="text15" text="Country goes here" font="EmphasisFont" textlocalekey="lblCountry"/></td>
                              <td width="10" ></td>
                              <td align="Left"><salmon:input type="select" name="ddlCountry" size="16" multiple="False" datasource="dsUserDetail:user_detail.country">
                                  <salmon:option key="USA" display="USA"/>
                                  <salmon:option key="Turkey" display="Turkey"/>
                                  <salmon:option key="Canada" display="Canada"/>
                                  <salmon:option key="France" display="France"/>
                                  <salmon:option key="Spain" display="Spain"/>
                                </salmon:input></td>
                            </tr>
                            <tr>
                              <td align="Right"><salmon:text name="capPhoneNo" text="Tel no goes here" font="EmphasisFont" textlocalekey="lblTelNo"/></td>
                              <td width="10" ></td>
                              <td align="Left"><salmon:input type="phone" name="phoneNo" separator="-" areacodestart="(" areacodeend=")" datasource="dsUserDetail:user_detail.phone_no"></salmon:input></td>
                            </tr>
                            <tr>
                              <td align="Right"><salmon:text name="capEmail" text="Email goes here"  font="EmphasisFont" textlocalekey="lblEmail"/></td>
                              <td width="10" ></td>
                              <td align="Left"><salmon:input type="email" name="email" size="20" minlength="7" maxlength="50" datasource="dsUserDetail:user_detail.email"></salmon:input></td>
                            </tr>
                            <tr >
                              <td height="20"></td>
                            </tr>
                            <tr >
                              <td align="Left"><salmon:text name="text16" text="CC Info goes here" font="SectionHeadingFont" textlocalekey="HdngCCInfo"/></td>
                            </tr>
                            <tr>
                              <td align="Right"><salmon:text name="text21" text="Credit card type goes here" font="EmphasisFont" textlocalekey="lblCardType"/></td>
                              <td width="10" ></td>
                              <td align="Left"><salmon:input type="select" name="ddlCardType" size="16" multiple="False" datasource="dsUserDetail:credit_card.credit_card_type">
                                  <salmon:option display="abc" key="123" table="credit_card_type" keycolumn="credit_card_type" displaycolumn="description"/>
                                </salmon:input></td>
                            </tr>
                            <tr>
                              <td align="Right"><salmon:text name="capTeCCNo" text="CC no goes here" font="EmphasisFont" textlocalekey="lblCardNumber"/></td>
                              <td width="10" ></td>
                              <td align="Left"><salmon:input type="text" name="teCCNo" size="26" maxlength="50" datasource="dsUserDetail:credit_card.credit_card_number"></salmon:input></td>
                            </tr>
                            <tr>
                              <td align="Right"><salmon:text name="text24" text="CC Exp date goes here" font="EmphasisFont" textlocalekey="lblCardExpDate"/></td>
                              <td width="10" ></td>
                              <td align="Left"><salmon:input type="select" name="ddlMonth" size="16" multiple="False" datasource="dsUserDetail:bkt_cc_exp_month">
                                  <salmon:option key="01" display="01"/>
                                  <salmon:option key="02" display="02"/>
                                  <salmon:option key="03" display="03"/>
                                  <salmon:option key="04" display="04"/>
                                  <salmon:option key="05" display="05"/>
                                  <salmon:option key="06" display="06"/>
                                  <salmon:option key="07" display="07"/>
                                  <salmon:option key="08" display="08"/>
                                  <salmon:option key="09" display="09"/>
                                  <salmon:option key="10" display="10"/>
                                  <salmon:option key="11" display="11"/>
                                  <salmon:option key="12" display="12"/>
                                </salmon:input>
                                / 
                                <salmon:input multiple="False" name="ddlYear" size="16" type="select" value="" datasource="dsUserDetail:bkt_cc_exp_year"> 
                                  <salmon:option key="2002" display="2002"/>
                                  <salmon:option key="2003" display="2003"/>
                                  <salmon:option key="2004" display="2004"/>
                                  <salmon:option key="2005" display="2005"/>
                                </salmon:input></td>
                            </tr>
                            </salmon:table> </td>
                      </tr>
                      <tr> 
                        <td height="20"> </td>
                      </tr>
                      <tr> 
                        <td><salmon:input type="submit"  classname="sbttn" name="btnSubmit" displaynamelocalekey="btnLblSubmit"></salmon:input></td>
                      </tr>
                    </table>
                    </salmon:form> <jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage/>