package com.salmonllc.properties;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/properties/Props.java $
//$Author: Dan $
//$Revision: 130 $
//$Modtime: 11/08/04 1:39p $
/////////////////////////

import java.util.*;
import java.io.*;
import java.awt.*;

import com.salmonllc.util.*;

/**
 * This object is used to get properties from files that describe the attributes of the system. <BR>
 * There are three types of properties files. <BR>
 * The System properties file is called System.properties and contains the default properties for every application on the server.<BR><BR>
 * Application property files follow the naming convention [application name].properties. They contain default properties for particular applications which override the System properties<BR><BR>
 * Unit property files follow the naming convention [application name]_[unitname].properties. They contain default properties for particular units (html pages) for particular application. These override both the application properties and the System properties.<BR><BR>

 */

public class Props
{
	//private static PropsDescriptor _systemProps;
	private static HashMap _appProps = new HashMap();

	private Properties _system;
	private Properties _application;
	private Properties _unit;
	private static boolean _displayBanner = (System.getProperty("salmon.mode.silent") == null);
	//private static boolean _displayBanner = true;

	private static String PROPS_DIR;
	public static final String SYS_PROPS_DIR_PROPERTY="salmon.props.path";
	public static final String SYS_APP_PACKAGE= "ApplicationPackage";
	public static final String SYS_DEFAULT_APP= "DefaultApplication";
	public static final String SYS_DEFAULT_PAGE= "DefaultPage";
	public static final String SYS_OBJECTSTORE_PATH= "ObjectStorePath";
	public static final String SYS_OBJECTSTORE_URL= "ObjectStoreURL";
	public static final String SYS_HISTORY_FIX= "HistoryFix";
	public static final String SYS_SCHEDULE_OBJECT= "ScheduleObject";
	public static final String SYS_SCHEDULE_INTERVAL= "ScheduleIntervalMinutes";
	public static final String SYS_SCHEDULER_MAX_OBJECTS= "SchedulerMaxObjects";
	public static final String SYS_SCHEDULE_APPLICATION= "ScheduleApplication";

	public static final String SYS_DATASERVER_SECURITY= "DataServerSecurity";
	public static final String SYS_REMOTEREFLECTOR_SECURITY_MANAGER= "RemoteReflectorSecurityManager";
	public static final String SYS_OBJECTSTORE_CACHE_SIZE= "ObjectStoreCacheSize";
	public static final String SYS_OBJECTSTORE_CACHE_IDLE_TIME_OUT= "ObjectStoreCacheIdleTimeOut";
	public static final String SYS_OBJECTSTORE_USE_CACHE= "ObjectStoreUseCache";
	public static final String SYS_SECURE_SCHEME= "SecureScheme";
	public static final String SYS_SECURE_PORT= "SecurePort";
	public static final String SYS_UNSECURE_SCHEME= "UnSecureScheme";
	public static final String SYS_UNSECURE_PORT= "UnSecurePort";
	public static final String SYS_LOCALHOST = "LocalHost";
	public static final String SYS_CACHE_CONTROLLERS = "CacheJSPControllers";
	public static final String SYS_REPLACE_JSP_FACTORY = "ReplaceJSPFactory";
	public static final String SYS_INFO_SERVLETS= "ViewInfoServlets";
	public static final String SYS_RECORD_PAGE_TIMERS="RecordPageTimers";
    public static final String SYS_ABSOLUTE_URLS_TO_RELATIVE_ON_REDIRECT="AbsoluteURLSToRelativeOnRedirect";
	public static final String SYS_MESSAGELOG_LOGGER_CLASS="MessageLogLoggerClass";
	public static final String SYS_MESSAGELOG_LOGGER_CONFIG_FILE="MessageLogLoggerConfigFile";
	public static final String SYS_ENABLE_CODE_GENERATION="EnableCodeGeneration";
	public static final String SYS_SESSION_KEEP_ALIVE_MINUTES="SessionKeepAliveMinutes";
	public static final String SYS_INCLUDE_ID_AFTER_POST="IncludeIDAfterPost";

	public static final String DB_POOL= "DBPool";
	public static final String DB_DRIVER= "DBDriver";
	public static final String DB_URL= "DBURL";
	public static final String DB_USER= "DBUser";
	public static final String DB_PASSWORD= "DBPassword";
	public static final String DB_DEFAULT= "DBDefaultProfile";
	public static final String DB_DBMS= "DBDBMS";
	public static final String DB_MAX_CONNECTIONS= "MaxConnections";
	public static final String DB_WAIT_TIME_OUT= "WaitTimeOut";
	public static final String DB_IDLE_TIME_OUT= "IdleTimeOut";
	public static final String DB_NAME= "DBName";
    public static final String DB_VERIFY_CONNECTION= "DBVerifyConnection";
    public static final String DB_VERIFY_CONNECTION_STATEMENT= "DBVerifyConnectionStatement";
	public static final String DB_VERIFY_CONNECTION_MAXAGE= "DBVerifyConnectionMaxAge";
	public static final String DB_PARMS= "DBParms";

	public static final String DBMSTYPE_SYBASE= "Sybase";
	public static final String DBMSTYPE_SQLANYWHERE= "SQLAnywhere";
	public static final String DBMSTYPE_MSSQLSERVER= "MSSQLServer";
	public static final String DBMSTYPE_DB2= "DB2";
	public static final String DBMSTYPE_DB2VSE= "DB2VSE";
	public static final String DBMSTYPE_DB2MVS= "DB2MVS";
	public static final String DBMSTYPE_DB2400= "DB2400";
	public static final String DBMSTYPE_ORACLE= "ORACLE";
	public static final String DBMSTYPE_MYSQL= "MYSQL";
    public static final String DBMSTYPE_INGRES= "INGRES";
    public static final String DBMSTYPE_FIREBIRDSQL= "FireBirdSQL";
    public static final String DBMSTYPE_POSTGRES= "POSTGRES";
    public static final String DBMSTYPE_ANSISQL92= "ANSISQL92";
    public static final String DBMSTYPE_HIBERNATE= "HIBERNATE";

	public static final String TABLE_BORDER= "TableBorder";
	public static final String TABLE_CELLSPACING= "TableCellSpacing";
	public static final String TABLE_CELLPADDING= "TableCellPadding";
	public static final String TABLE_BACKGROUND_COLOR= "TableBackgroundColor";

	public static final String DATA_TABLE_BORDER= "DataTableBorder";
	public static final String DATA_TABLE_CELLSPACING= "DataTableCellSpacing";
	public static final String DATA_TABLE_CELLPADDING= "DataTableCellPadding";
	public static final String DATA_TABLE_BACKGROUND_COLOR= "DataTableBackgroundColor";
	public static final String DATA_TABLE_HEADING_BACKGROUND_COLOR= "DataTableHeadingBackgroundColor";
	public static final String DATA_TABLE_ROW_BACKGROUND_COLOR_1= "DataTableRowBackgroundColor1";
	public static final String DATA_TABLE_ROW_BACKGROUND_COLOR_2= "DataTableRowBackgroundColor2";
	public static final String DATA_TABLE_ROWS_PER_PAGE= "DataTableRowPerPage";
	public static final String DATA_TABLE_MAX_PAGE_BUTTONS= "DataTableMaxPageButtons";
	public static final String DATA_TABLE_CLICK_SORT= "DataTableClickSort";
	public static final String DATA_TABLE_SCROLL_ON_CLICK_SORT= "DataTableScrollOnClickSort";
    public static final String DATA_TABLE_PAGE_SELECT_RENDERER= "DataTablePageSelectRenderer";
    public static final String DATA_TABLE_ROWS_PER_PAGE_RENDERER= "DataTableRowsPerPageRenderer";

	public static final String BOX_LINE_WIDTH= "BoxLineWidth";
	public static final String BOX_LINE_COLOR= "BoxLineColor";
	public static final String BOX_BG_COLOR= "BoxBgColor";
	public static final String BOX_MARGIN= "BoxMargin";

    public static final String CONTAINER_CLASS="ContainerClass";

	public static final String DISPLAY_BOX_BORDER= "DisplayBoxBorder";
	public static final String DISPLAY_BOX_BACKGROUND_COLOR= "DisplayBoxBackgroundColor";
	public static final String DISPLAY_BOX_HEADING_BACKGROUND_COLOR= "DisplayBoxHeadingBackgroundColor";
	public static final String DISPLAY_BOX_CELLSPACING= "DisplayBoxCellSpacing";
	public static final String DISPLAY_BOX_CELLPADDING= "DisplayBoxCellPadding";

	public static final String DATE_COMPONENT_DISPLAY_FORMAT="DateComponentDisplayFormat";

	public static final String FONT_DEFAULT= "DefaultFont";
	public static final String FONT_TEXT_EDIT= "TextEditFont";
	public static final String FONT_DDLB= "DDLBFont";
	public static final String FONT_BUTTON= "ButtonFont";
	public static final String FONT_PAGE_HEADING= "PageHeadingFont";
	public static final String FONT_SECTION_HEADING= "SectionHeadingFont";
	public static final String FONT_TABLE_HEADING= "TableHeadingFont";
	public static final String FONT_COLUMN_CAPTION= "ColumnCaptionFont";
	public static final String FONT_EMPHASIS= "EmphasisFont";
	public static final String FONT_DISPLAY_BOX_HEADING= "DisplayBoxHeadingFont";
	public static final String FONT_LINK= "LinkFont";
	public static final String FONT_LARGE_LINK= "LargeLinkFont";
	public static final String FONT_ERROR= "ErrorFont";
	public static final String FONT_SMALL= "SmallFont";
	public static final String FONT_DEFAULT_LABEL_TEXT= "DefaultLabelTextFont";
	public static final String FONT_DEFAULT_DISPLAY_DATA= "DefaultDisplayDataFont";

	public static final String TAG_START= ".StartTag";
	public static final String TAG_END= ".EndTag";

	public static final String PAGE_BACKGROUND_COLOR= "PageBackgroundColor";
	public static final String PAGE_BACKGROUND= "PageBackground";
	public static final String PAGE_TEXT_COLOR= "PageTextColor";
	public static final String PAGE_LINK_COLOR= "PageLinkColor";
	public static final String PAGE_HOVER_LINK_COLOR= "PageHoverLinkColor";
	public static final String PAGE_VISITED_LINK_COLOR= "PageVisitedLinkColor";
	public static final String PAGE_ACTIVE_LINK_COLOR= "PageActiveLinkColor";
	public static final String PAGE_MARGINWIDTH= "PageMarginWidth";
	public static final String PAGE_MARGINHEIGHT= "PageMarginHeight";
	public static final String PAGE_LEFTMARGIN= "PageLeftMargin";
	public static final String PAGE_TOPMARGIN= "PageTopMargin";
	public static final String PAGE_RIGHTMARGIN= "PageRightMargin";

	public static final String DATE_FORMAT= "DateFormat";
	public static final String TIME_FORMAT= "TimeFormat";
	public static final String DATETIME_FORMAT= "DateTimeFormat";
	public static final String CURRENCY_FORMAT= "CurrencyFormat";
	public static final String EMAIL_SMTP_SERVER= "EMailSMTPServer";
	public static final String EMAIL_SMTP_PORT= "EMailSMTPPort";

	public static final String LOG_ERRORS= "LogErrors";
	public static final String LOG_ASSERTIONS= "LogAssertions";
	public static final String LOG_INFO= "LogInfoMessages";
	public static final String LOG_DEBUG= "LogDebugMessages";
	public static final String LOG_SQL= "LogSQLStatements";
	public static final String LOG_ERRORS_TO_CONSOLE= "LogErrorsToConsole";
	public static final String LOG_ASSERTIONS_TO_CONSOLE= "LogAssertionsToConsole";
	public static final String LOG_INFO_TO_CONSOLE= "LogInfoMessagesToConsole";
	public static final String LOG_DEBUG_TO_CONSOLE= "LogDebugMessagesToConsole";
	public static final String LOG_SQL_TO_CONSOLE= "LogSQLStatementsToConsole";
	public static final String LOG_FILE_NAME= "LogFileName";
	public static final String LOG_FILE_SIZE_LIMIT= "LogFileSizeLimit";
	public static final String LOG_FILE_EXT= "LogFileExt";
	public static final String LOG_FILE_APPEND_DATE= "LogFileAppendDate";
	public static final String LOG_HIDE_THREAD= "LogHideThread";
	public static final String LOG_HIDE_TIME= "LogHideTime";

	public static final int LOG_LEVEL_1= 1;
	public static final int LOG_LEVEL_2= 2;
	public static final int LOG_LEVEL_3= 3;
	public static final int LOG_LEVEL_4= 4;
	public static final int LOG_LEVEL_5= 5;
	public static final int LOG_LEVEL_6= 6;
	public static final int LOG_LEVEL_7= 7;
	public static final int LOG_LEVEL_8= 8;
	public static final int LOG_LEVEL_9= 9;
	public static final int LOG_LEVEL_10= 10;

	public static final String TAB_COLOR= "TabColor";
	public static final String TAB_ACTIVE_COLOR= "TabActiveColor";
	public static final String TAB_FONT_START_TAG= "TabFont.StartTag";
	public static final String TAB_FONT_END_TAG= "TabFont.EndTag";
	public static final String TAB_ACTIVE_FONT_START_TAG= "TabActiveFont.StartTag";
	public static final String TAB_ACTIVE_FONT_END_TAG= "TabActiveFont.EndTag";
	public static final String TAB_LEFT_IMAGE= "TabLeftImage";
	public static final String TAB_RIGHT_IMAGE= "TabRightImage";
	public static final String TAB_BORDER_IMAGE= "TabBorderImage";
	public static final String TAB_HEIGHT= "TabHeight";
	public static final String ALL_TAB_WIDTH= "TotalWidth";
	public static final String TAB_SHOW_STRIPE= "TabShowStripe";

	public static final String CAL_HEADING_BACKGROUND_COLOR= "CalendarHeadingBackgroundColor";
	public static final String CAL_HEADING_FOREGROUND_COLOR= "CalendarHeadingForegroundColor";
	public static final String CAL_WEEK_BACKGROUND_COLOR= "CalendarWeekBackgroundColor";
	public static final String CAL_WEEK_FOREGROUND_COLOR= "CalendarWeekForegroundColor";
	public static final String CAL_DAY_BACKGROUND_COLOR= "CalendarDayBackgroundColor";
	public static final String CAL_DAY_FOREGROUND_DEEMPHISIS= "CalendarDayForegroundDeEmphisis";
	public static final String CAL_DAY_FOREGROUND_NORMAL= "CalendarDayForegroundNormal";
	public static final String CAL_DAY_FOREGROUND_CURRENT= "CalendarDayForegroundCurrent";
	public static final String CAL_FONT_FACE= "CalendarFontFace";
	public static final String CAL_FONT_LARGE= "CalendarLargeFontSize";
	public static final String CAL_FONT_SMALL= "CalendarSmallFontSize";

	public static final String RADIOBUTTON_IMAGE_ON= "RadioButtonImageOn";
	public static final String RADIOBUTTON_IMAGE_OFF= "RadioButtonImageOff";
	public static final String CHECKBOX_IMAGE_ON= "CheckBoxImageOn";
	public static final String CHECKBOX_IMAGE_OFF= "CheckBoxImageOff";
	public static final String TRISTATECHECKBOX_DISABLED_IMAGE_CHECKED= "TriStateCheckBoxDisabledImageChecked";
	public static final String TRISTATECHECKBOX_DISABLED_IMAGE_UNCHECKED= "TriStateCheckBoxDisabledImageUnChecked";
	public static final String TRISTATECHECKBOX_DISABLED_IMAGE_MIXED= "TriStateCheckBoxDisabledImageMixed";
	public static final String TRISTATECHECKBOX_IMAGE_CHECKED= "TriStateCheckBoxImageChecked";
	public static final String TRISTATECHECKBOX_IMAGE_UNCHECKED= "TriStateCheckBoxImageUnChecked";
	public static final String TRISTATECHECKBOX_IMAGE_MIXED= "TriStateCheckBoxImageMixed";

	public static final String SUBMIT_IMAGE_FONT_STYLE= "SubmitImageFontStyle";
	public static final String SUBMIT_IMAGE_FONT_FACE= "SubmitImageFontFace";
	public static final String SUBMIT_IMAGE_FONT_SIZE= "SubmitImageFontSize";
	public static final String SUBMIT_IMAGE_BACKGROUND_COLOR= "SubmitImageBackgroundColor";
	public static final String SUBMIT_IMAGE_TEXT_COLOR= "SubmitImageTextColor";
	public static final String SUBMIT_IMAGE_TOPLEFT_BORDER_COLOR= "SubmitImageTopLeftBorderColor";
	public static final String SUBMIT_IMAGE_BOTTOMRIGHT_BORDER_COLOR= "SubmitImageBottomRightBorderColor";
	public static final String SUBMIT_IMAGE_TRANSPARENT_COLOR= "SubmitImageTransparentColor";
	public static final String SUBMIT_IMAGE_STYLE= "SubmitImageStyle";
	public static final String SUBMIT_IMAGE_DEFAULT_HEIGHT= "SubmitImageDefaultHeight";

	public static final String TEXTEDIT_FORCE_CASE= "TextEditForceCase";
    public static final String USE_DISABLED_ATTRIBUTE = "UseDisabledAttribute";

	public static final String SKIP_SECUREID= "SkipSecureID";
	public static final String DB_DATASOURCE= "DataSource";

	public static final String TLD_JAR= "TLDJar";
	public static final String TLD_PATH= "TLDPath";
	public static final String JSP_ENGINE_DEBUG_INPUT_FILE= "JSPEngineDebugInputFile";
	public static final String JSP_ENGINE_DEBUG_OUTPUT_FILE= "JSPEngineDebugOutputFile";

	public static final String XML_DATA_PATH= "XMLDataPath";

	public static final String WINWORD_PATH= "WinWordPath";
	public static final String WINWORD_TEMPLATE_PATH= "WinWordTemplatePath";

	public static final String IDE_DEFAULT_HOST= "IDEDefaultHost";
	public static final String IDE_TOMCAT_MANAGER_APP= "IDETomcatManagerApplication";
	public static final String IDE_TOMCAT_PATH= "IDETomcatPath";
	public static final String IDE_INTELLIJ_PATH= "IDEIntelliJPath";
	public static final String IDE_WEB_APP= "IDEDefaultWebApplication";
	public static final String IDE_BROWSER_PATH= "IDEBrowserPath";
	public static final String IDE_DEFAULT_RUN_URL= "IDEDefaultRunURL";
	public static final String IDE_DREAMWEAVER_PATH= "IDEDreamWeaverPath";
	public static final String IDE_DEFAULT_SOURCE_PATH= "IDEDefaultSourcePath";
	public static final String IDE_DEFAULT_FRAMEWORK_APP= "IDEDefaultFrameworkApplication";
	public static final String IDE_SERVER_TYPE= "IDEServerType";
	public static final String IDE_WEBLOGIC_APPLICATION_PATH= "IDEWeblogicApplicationPath";
	public static final String IDE_FRAMEWORK_SOURCE_PATH= "IDEFrameworkSourcePath";
	public static final String IDE_FRAMEWORK_RESOURCES_PATH= "IDEFrameworkResourcesPath";
	public static final String IDE_CLASSPATH= "IDEClassPath";
	public static final String IDE_LIBPATH= "IDELibraryPath";
	public static final String IDE_VMARGS= "IDEVMArgs";
	public static final String IDE_SERVER_CONFIG_FILE= "IDEConfigFile";
	public static final String IDE_WORKING_DIRECTORY= "IDEWorkingDirectory";
	public static final String IDE_SERVER_MANAGER_ID= "IDEServerManagerID";
	public static final String IDE_SERVER_MANAGER_PW= "IDEServerManagerPassword";
	public static final String IDE_SITE_MAP_INTERFACE= "IDESiteMapInterface";
	public static final String IDE_APP_PROPS_PATH= "IDEApplicationPropsPath";
	public static final String IDE_USE_APP_PROPERTIES= "IDEUseApplicationProperties";
	public static final String IDE_ECLIPSE_VM="IDEEclipseVM";
	public static final String IDE_ECLIPSE_PLUGIN_JAR="IDEEclipsePlugInJar";

	public static final String WML_MAINTAIN_SESSIONS= "WMLMaintainSessions";
	public static final String WML_SESSION_KEY_LENGTH= "WMLSessionKeyLength";

	public static final String LOCALE_TABLE= "LocaleTable";
	public static final String LOCALE_APP_COLUMN= "LocaleAppColumn";
	public static final String LOCALE_LANG_COLUMN= "LocaleLangColumn";
	public static final String LOCALE_KEY_COLUMN= "LocaleKeyColumn";
	public static final String LOCALE_RESOURCE_COLUMN= "LocaleResourceColumn";
	public static final String LOCALE_DB_PROFILE= "LocaleDBProfile";
	public static final String LOCALIZER_CLASS= "LocalizerClass";
	public static final String LOCALIZER_USE_CACHE= "LocalizerUseCache";

	public static final String NAVBAR_BG_COLOR ="NavBarBgColor";
	public static final String NAVBAR_ALIGN ="NavBarAlign";
	public static final String NAVBAR_BORDER ="NavBarBorder";
	public static final String NAVBAR_CELLPADDING ="NavBarCellPadding";
	public static final String NAVBAR_CELLSPACING ="NavBarCellSpacing";
	public static final String NAVBAR_H_SPACE ="NavBarHSpace";
	public static final String NAVBAR_V_SPACE ="NavBarVSpace";
	public static final String NAVBAR_HEIGHT ="NavBarHeight";
	public static final String NAVBAR_WIDTH ="NavBarWidth";
	public static final String NAVBAR_HREF_STYLE ="NavBarHrefStyle";
	public static final String NAVBAR_HOVER_STYLE ="NavBarHoverStyle";
	public static final String NAVBAR_TEXT_STYLE ="NavBarTextStyle";
	public static final String NAVBAR_SELECTED_STYLE ="NavBarSelectedStyle";
	public static final String NAVBAR_SELECTED_HOVER_STYLE ="NavBarSelectedHoverStyle";
	public static final String NAVBAR_SHOW_POPUP_STYLE ="NavBarShowPopupStyle";
	public static final String NAVBAR_SHOW_POPUP_HOVER_STYLE ="NavBarShowPopupHoverStyle";
	public static final String NAVBAR_SHOW_POPUP_SELECTED_STYLE ="NavBarShowPopupSelectedStyle";
	public static final String NAVBAR_SHOW_POPUP_SELECTED_HOVER_STYLE ="NavBarShowPopupSelectedHoverStyle";
	public static final String NAVBAR_CELL_BG_COLOR ="NavBarCellBgColor";
	public static final String NAVBAR_GROUP_CELL_BG_COLOR ="NavBarGroupCellBgColor";
    public static final String NAVBAR_GROUP_HREF_STYLE ="NavBarGroupHrefStyle";
	public static final String NAVBAR_GROUP_HOVER_STYLE ="NavBarGroupHoverStyle";
	public static final String NAVBAR_HOVER_BG_COLOR ="NavBarHoverBgColor";
    public static final String NAVBAR_GROUP_HOVER_BG_COLOR ="NavBarGroupHoverBgColor";
	public static final String NAVBAR_SELECTED_BG_COLOR ="NavBarSelectedBgColor";
	public static final String NAVBAR_SELECTED_HOVER_BG_COLOR ="NavBarSelectedHoverBgColor";
	public static final String NAVBAR_SHOW_POPUP_BG_COLOR ="NavBarShowPopupBgColor";
	public static final String NAVBAR_SUBMENU_BG_COLOR ="NavBarSubMenuBgColor";
	public static final String NAVBAR_MARKER_IMAGE ="NavBarMarkerImage";
	public static final String NAVBAR_MARKER_OVER_IMAGE ="NavBarMarkerOverImage";
	public static final String NAVBAR_SELECTED_MARKER_IMAGE ="NavBarSelectedMarkerImage";
	public static final String NAVBAR_TEXT_IMAGE ="NavBarTextImage";
	public static final String NAVBAR_V_SPACE_IMAGE ="NavBarVSpaceImage";
	public static final String NAVBAR_H_SPACE_IMAGE ="NavBarHSpaceImage";
	public static final String NAVBAR_GROUP_SPACER_IMAGE ="NavBarGroupSpacerImage";
	public static final String NAVBAR_SHOW_POPUP_IMAGE ="NavBarShowPopupImage";
    //SUBITEM STYLES
    public static final String NAVBAR_SUB_HREF_STYLE ="NavBarSubHrefStyle";
	public static final String NAVBAR_SUB_HOVER_STYLE ="NavBarSubHoverStyle";
	public static final String NAVBAR_SUB_HOVER_BG_COLOR ="NavBarSubHoverBgColor";

    //Button Properties
    public static final String BUTTON_BG_COLOR="ButtonBgColor";
    public static final String BUTTON_FONT_STYLE="ButtonFontStyle";
    public static final String BUTTON_STYLE_CLASS="ButtonStyleClass";

    //SKIN Properties
    public static final String SKIN_MANAGER_CLASS="SkinManagerClass";
    public static final String SKIN_PATH="SkinPath";
    public static final String SKIN_TABLE="SkinTable";
    public static final String SKIN_DB_PROFILE="SkinDBProfile";
    public static final String SKIN_NAME_COLUMN="SkinNameColumn";
    public static final String SKIN_KEY_COLUMN="SkinKeyColumn";
	public static final String SKIN_VALUE_COLUMN="SkinValueColumn";
	public static final String SKIN_DEFAULT="SkinDefault";

   	/*
	 * Added by: Henry Tafolla
	 * Date: Feburary 22 2003
	 * Description: Properties for the HtmlTreeComponent
	 */
	public static final String TREE_ANCHOR_CLASS= "TreeAnchorClass";
	public static final String TREE_CELLPADDING= "TreeCellPadding";
    public static final String TREE_CELLSPACING= "TreeCellSpacing";
    public static final String TREE_JAVASCRIPT_WINDOW= "TreeJavaScriptWindow";
    public static final String TREE_JAVASCRIPT_WINDOW_DIRECTORIES= "TreeJavaScriptWindowDirectories";
    public static final String TREE_JAVASCRIPT_WINDOW_HEIGHT= "TreeJavaScriptWindowHeight";
    public static final String TREE_JAVASCRIPT_WINDOW_LOCATION= "TreeJavaScriptWindowLocation";
    public static final String TREE_JAVASCRIPT_WINDOW_MENUBAR= "TreeJavaScriptWindowMenubar";
    public static final String TREE_JAVASCRIPT_WINDOW_NAME= "TreeJavaScriptWindowName";
    public static final String TREE_JAVASCRIPT_WINDOW_RESIZABLE= "TreeJavaScriptWindowResizable";
    public static final String TREE_JAVASCRIPT_WINDOW_SCROLLBARS= "TreeJavaScriptWindowScrollbars";
    public static final String TREE_JAVASCRIPT_WINDOW_STATUS= "TreeJavaScriptWindowStatus";
    public static final String TREE_JAVASCRIPT_WINDOW_TOOLBAR= "TreeJavaScriptWindowToolbar";
    public static final String TREE_JAVASCRIPT_WINDOW_WIDTH= "TreeJavaScriptWindowWidth";
	public static final String TREE_TABLE_CELL_CLASS= "TreeTableCellClass";
	public static final String TREE_TABLE_HEADER_CELL_CLASS= "TreeHeaderCellClass";
	public static final String TREE_TABLE_CELL_STYLE= "TreeTableCellStyle";
	public static final String TREE_TABLE_HEADER_CELL_STYLE= "TreeHeaderCellStyle";
	public static final String TREE_TABLE_STYLE= "TreeTableStyle";
	public static final String TREE_TABLE_CLASS= "TreeTableClass";
	public static final String TREE_NODE_FONT="TreeNodeFont";

	public static final String SEARCH_FORM_DISPLAY_BOX_ADD_BUTTON_ACCESS_KEY="SearchFormDisplayBoxAddButtonAccessKey";
	public static final String SEARCH_FORM_DISPLAY_BOX_ADD_BUTTON_CAPTION="SearchFormDisplayBoxAddButtonCaption";
	public static final String SEARCH_FORM_DISPLAY_BOX_SEARCH_BUTTON_ACCESS_KEY="SearchFormDisplayBoxSearchButtonAccessKey";
	public static final String SEARCH_FORM_DISPLAY_BOX_SEARCH_BUTTON_CAPTION="SearchFormDisplayBoxSearchButtonCaption";
	public static final String SEARCH_FORM_DISPLAY_BOX_CANCEL_BUTTON_ACCESS_KEY="SearchFormDisplayBoxCancelButtonAccessKey";
	public static final String SEARCH_FORM_DISPLAY_BOX_CANCEL_BUTTON_CAPTION="SearchFormDisplayBoxCancelButtonCaption";
	public static final String SEARCH_FORM_DISPLAY_BOX_BUTTON_BG_COLOR="SearchFormDisplayBoxButtonBgColor";
	public static final String SEARCH_FORM_DISPLAY_BOX_BUTTON_FONT_STYLE="SearchFormDisplayBoxButtonFontStyle";
	public static final String SEARCH_FORM_DISPLAY_BOX_BUTTON_LOCATION="SearchFormDisplayBoxButtonLocation";
	public static final String SEARCH_FORM_DISPLAY_BOX_BOTTOM_BUTTON_ALIGN="SearchFormDisplayBoxBottomButtonAlign";
	public static final String SEARCH_FORM_DISPLAY_BOX_CLASS="SearchFormDisplayBoxClass";


	public static final String LIST_FORM_DISPLAY_BOX_ADD_BUTTON_ACCESS_KEY="ListFormDisplayBoxAddButtonAccessKey";
	public static final String LIST_FORM_DISPLAY_BOX_ADD_BUTTON_CAPTION="ListFormDisplayBoxAddButtonCaption";
	public static final String LIST_FORM_DISPLAY_BOX_SAVE_BUTTON_ACCESS_KEY="ListFormDisplayBoxSaveButtonAccessKey";
	public static final String LIST_FORM_DISPLAY_BOX_SAVE_BUTTON_CAPTION="ListFormDisplayBoxSaveButtonCaption";
	public static final String LIST_FORM_DISPLAY_BOX_BUTTON_BG_COLOR="ListFormDisplayBoxButtonBgColor";
	public static final String LIST_FORM_DISPLAY_BOX_BUTTON_FONT_STYLE="ListFormDisplayBoxButtonFontStyle";
	public static final String LIST_FORM_DISPLAY_BOX_BUTTON_LOCATION="ListFormDisplayBoxButtonLocation";
	public static final String LIST_FORM_DISPLAY_BOX_BOTTOM_BUTTON_ALIGN="ListFormDisplayBoxBottomButtonAlign";
	public static final String LIST_FORM_DISPLAY_BOX_ROW_HIGHLIGHT_COLOR="ListFormDisplayBoxRowHighlightColor";
	public static final String LIST_FORM_DISPLAY_BOX_CLASS="ListFormDisplayBoxClass";

	public static final String DETAIL_FORM_DISPLAY_BOX_ADD_BUTTON_ACCESS_KEY="DetailFormDisplayBoxAddButtonAccessKey";
	public static final String DETAIL_FORM_DISPLAY_BOX_ADD_BUTTON_CAPTION="DetailFormDisplayBoxAddButtonCaption";
	public static final String DETAIL_FORM_DISPLAY_BOX_CANCEL_BUTTON_ACCESS_KEY="DetailFormDisplayBoxCancelButtonAccessKey";
	public static final String DETAIL_FORM_DISPLAY_BOX_CANCEL_BUTTON_CAPTION="DetailFormDisplayBoxCancelButtonCaption";
	public static final String DETAIL_FORM_DISPLAY_BOX_DELETE_BUTTON_ACCESS_KEY="DetailFormDisplayBoxDeleteButtonAccessKey";
	public static final String DETAIL_FORM_DISPLAY_BOX_DELETE_BUTTON_CAPTION="DetailFormDisplayBoxDeleteButtonCaption";
	public static final String DETAIL_FORM_DISPLAY_BOX_SAVE_BUTTON_ACCESS_KEY="DetailFormDisplayBoxSaveButtonAccessKey";
	public static final String DETAIL_FORM_DISPLAY_BOX_SAVE_BUTTON_CAPTION="DetailFormDisplayBoxSaveButtonCaption";
	public static final String DETAIL_FORM_DISPLAY_BOX_BUTTON_BG_COLOR="DetailFormDisplayBoxButtonBgColor";
	public static final String DETAIL_FORM_DISPLAY_BOX_BUTTON_FONT_STYLE="DetailFormDisplayBoxButtonFontStyle";
	public static final String DETAIL_FORM_DISPLAY_BOX_BUTTON_LOCATION="DetailFormDisplayBoxButtonLocation";
	public static final String DETAIL_FORM_DISPLAY_BOX_BOTTOM_BUTTON_ALIGN="DetailFormDisplayBoxBottomButtonAlign";
	public static final String DETAIL_FORM_DISPLAY_BOX_CLASS="DetailFormDisplayBoxClass";

	public static final String FORM_DISPLAY_BOX_OK_BUTTON_CAPTION="FormDisplayBoxOKButtonCaption";
	public static final String FORM_DISPLAY_BOX_CANCEL_BUTTON_CAPTION="FormDisplayBoxCancelButtonCaption";
	public static final String FORM_DISPLAY_BOX_UNDO_BUTTON_CAPTION="FormDisplayBoxUndoButtonCaption";

	public static final String LOOKUP_COMPONENT_POPUPATTRIBUTES="LookupPopupAttributes";
	public static final String LOOKUP_COMPONENT_URL="LookupURL";
	public static final String LOOKUP_COMPONENT_USEPOPUP="LookupUsePopup";
	public static final String LOOKUP_COMPONENT_POPUPMODAL="LookupPopupModal";
	public static final String LOOKUP_COMPONENT_POPUPDIV="LookupPopupDiv";
	public static final String LOOKUP_COMPONENT_POPUPDIVBORDERSTYLE="LookupPopupDivBorderStyle";
	public static final String LOOKUP_COMPONENT_POPUPWIDTH="LookupPopupWidth";
	public static final String LOOKUP_COMPONENT_POPUPHEIGHT="LookupPopupHeight";
	public static final String LOOKUP_COMPONENT_POPUPTOP="LookupPopupTop";
	public static final String LOOKUP_COMPONENT_POPUPLEFT="LookupPopupLeft";
	public static final String LOOKUP_COMPONENT_POPUPPOSITION="LookupPopupPosition";
	public static final String LOOKUP_COMPONENT_EDITDESCRIPTION="LookupEditDescription";

	public static final String HIBERNATE_FACTORY_CLASS="HibernateFactoryClass";
	public static final String HIBERNATE_CONFIG_FILE="HibernateConfigFile";

	//Otras variables de internacionalización añadidas
	//Juan Manuel Cortez, 14/10/2007
	public static final String FORM_DISPLAY_BOX_CANCEL_CONFIRMATION_MESSAGE = "FormDisplayBoxCancelConfirmationMessage";
	public static final String FORM_DISPLAY_BOX_ADD_CONFIRMATION_MESSAGE = "FormDisplayBoxAddConfirmationMessage";
	public static final String FORM_DISPLAY_BOX_EDIT_CONFIRMATION_MESSAGE = "FormDisplayBoxAddConfirmationMessage";
	public static final String FORM_DISPLAY_BOX_DELETE_CONFIRMATION_MESSAGE = "FormDisplayBoxDeleteConfirmationMessage";

	public static final String FORM_DISPLAY_BOX_DIRTY_DATA_ERROR_MESSAGE = "FormDisplayBoxDirtyDataErrorMessage";

	public static final String SEARCH_FORM_DISPLAY_BOX_DATA_MODIFIED_QUESTION = "SearchFormDisplayBoxDataModifiedQuestion";

    /* End */

	static {
		setPropsPath();
	}


	private Props(Properties system, Properties application, Properties unit) {
		super();
		_system = system;
		_application = application;
		_unit = unit;
	}
	private static PropsDescriptor getSystemPropsDescriptor() {
		return (PropsDescriptor)_appProps.get(getAppID());
	}

	private static String fixSlashes(String in) {
		StringBuffer ret = new StringBuffer(in.length());
		for (int i = 0; i < in.length(); i++) {
			char c = in.charAt(i);
			if (c == '\\')
				ret.append("\\\\");
			else
				ret.append(c);
		}
		return ret.toString();
	}
	/**
	 * This method will return an boolean value from the properties object.
	 * @return true if the property is found and is equal to : True,Yes,T,Y. It will return false otherwise.
	 * @param key The property to search for.
	 */
	public boolean getBooleanProperty(String key) {
		String prop = getProperty(key, null);
		if (prop == null)
			return false;

		prop = prop.toLowerCase();
		if (prop.equals("t") || prop.equals("true") || prop.equals("y") || prop.equals("yes"))
			return true;

		return false;
	}
	/**
	 * This method will return an boolean value from the properties object.
	 * @return true if the property is found and is equal to : True,Yes,T,Y. It will return false otherwise.
	 * @param key The property to search for.
	 * @param defaultProperty The property to return if no property is found.
	 */
	public boolean getBooleanProperty(String key, boolean defaultProperty) {
		String prop = getProperty(key, null);
		if (prop == null)
			return defaultProperty;

		prop = prop.toLowerCase();
		if (prop.equals("t") || prop.equals("true") || prop.equals("y") || prop.equals("yes"))
			return true;

		return false;
	}
	/**
	 * This method will return an color value from the properties object.
	 * @return The property or null if it is not found or the property is not a color.
	 * @param key The property to search for.
	 */
	public Color getColorProperty(String key) {
		String prop = getProperty(key, null);
		if (prop == null)
			return null;

		if (prop.startsWith("#"))
			return Color.decode(prop);
		else {
			prop = prop.toUpperCase();
			if (prop.equals("WHITE"))
				return Color.white;
			else if (prop.equals("LIGHTGRAY"))
				return Color.lightGray;
			else if (prop.equals("GRAY"))
				return Color.gray;
			else if (prop.equals("DARKGRAY"))
				return Color.darkGray;
			else if (prop.equals("BLACK"))
				return Color.black;
			else if (prop.equals("RED"))
				return Color.red;
			else if (prop.equals("PINK"))
				return Color.pink;
			else if (prop.equals("ORANGE"))
				return Color.orange;
			else if (prop.equals("YELLOW"))
				return Color.yellow;
			else if (prop.equals("YELLOW"))
				return Color.yellow;
			else if (prop.equals("MAGENTA"))
				return Color.magenta;
			else if (prop.equals("CYAN"))
				return Color.cyan;
			else if (prop.equals("BLUE"))
				return Color.blue;
		}
		return null;
	}
	/**
	 * This method will return an integer value from the properties object.
	 * @return The property or -1 if it is not found or the property is not an integer.
	 * @param key The property to search for.
	 */
	public int getIntProperty(String key) {
		String prop = getProperty(key, null);
		if (prop == null)
			return -1;

		try {
			int ret = Integer.parseInt(prop);
			return ret;
		} catch (Exception e) {
			return -1;
		}

	}
	/**
	 * This method will return an enumerareration of keys from the system properties file
	 */
	public Enumeration getKeys() {
		return _system.keys();
	}
	/**
	 * This method will return a value from the properties object.
	 * @return The property or null if it is not found.
	 * @param key The property to search for.
	 */
	public String getProperty(String key) {
		return getProperty(key, null);
	}
	/**
	 * This method will return a value from the properties object.
	 * @return The property.
	 * @param key The property to search for.
	 * @param def The default property to return if the key is not found.
	 */
	public String getProperty(String key, String def) {

		String property = null;

		if (_unit != null) {
			property = _unit.getProperty(key);
			if (property != null)
				if (property.equals("null"))
					return null;
		}

		if (property == null && _application != null) {
			property = _application.getProperty(key);
			if (property != null)
				if (property.equals("null"))
					return null;
		}

		if (property == null && _system != null) {
			property = _system.getProperty(key);
			if (property != null)
				if (property.equals("null"))
					return null;
		}

		if (property == null)
			property = def;

		return property;
	}
	/**
	 * Use this method to get a Props object.
	 */
	public static Props getProps(String appName, String unitName) {

		File sysFile = new File(getPropsPath() + File.separatorChar + "System.properties");
		File appFile = new File(getPropsPath() + File.separatorChar + appName + ".properties");

		// if unitName has .jsp in it stip it off and use just the root pageName
		unitName = Util.stripDotJsp(unitName);
		File unitFile = new File(getPropsPath() + File.separatorChar + appName + "_" + unitName + ".properties");

		PropsDescriptor systemProps=getSystemPropsDescriptor();
		if (systemProps == null) {
			systemProps = loadProperties(sysFile, "System");
			_appProps.put(getAppID(),systemProps);
		} else if (systemProps.getLastMod() != sysFile.lastModified()) {
			reloadProperties(sysFile, systemProps);
			_appProps.put(getAppID(),systemProps);
		}

		PropsDescriptor app = systemProps.getSubProperty(appName);
		if (app == null) {
			app = loadProperties(appFile, appName);
			systemProps.setSubProperty(app);
		} else if (app.getLastMod() != appFile.lastModified()) {
			reloadProperties(appFile, app);
		}

		// srufle Jan 27, 2004
//		if (unitName != null) {
		if (Util.isFilled(unitName )) {
			PropsDescriptor unit = app.getSubProperty(unitName);
			if (unit == null) {
				unit = loadProperties(unitFile, unitName);
				app.setSubProperty(unit);
			} else if (unit.getLastMod() != unitFile.lastModified()) {
				reloadProperties(unitFile, unit);
			}
			return new Props(systemProps.getProperties(), app.getProperties(), unit.getProperties());
		} else
			return new Props(systemProps.getProperties(), app.getProperties(), null);

	}
	/**
	 * Use this method to get the system properties object.
	 */
	public static Props getSystemProps() {
		File sysFile = new File(getPropsPath() + File.separatorChar + "System.properties");
		if (!sysFile.exists())
			return null;

		PropsDescriptor systemProps=getSystemPropsDescriptor();
		if (systemProps == null) {
			systemProps = loadProperties(sysFile, "System");
			_appProps.put(getAppID(),systemProps);
		} else if (systemProps.getLastMod() != sysFile.lastModified()) {
			reloadProperties(sysFile, systemProps);
			_appProps.put(getAppID(),systemProps);
		}

		return new Props(systemProps.getProperties(), null, null);
	}
	/**
	 * This method returns the last modified time for the system properties file
	 */
	public static long getSystemPropsLastModified() {
		File sysFile = new File(getPropsPath() + File.separatorChar + "System.properties");
		return (sysFile.lastModified());
	}
	/**
	 * This method will return an boolean value from the properties object using the specified theme. If the theme is null or not found, the default property will be returned.
	 * @return true if the property is found and is equal to : True,Yes,T,Y. It will return false otherwise.
	 * @param theme The theme of the property to search for.
	 * @param key The property to search for.
	 */
	public boolean getThemeBooleanProperty(String theme, String key) {
		if (theme == null)
			return getBooleanProperty(key);

		String prop = getProperty(theme + "." + key, null);
		if (prop == null) {
			prop = getProperty(key, null);
			if (prop == null)
				return false;
		}

		prop = prop.toLowerCase();
		if (prop.equals("t") || prop.equals("true") || prop.equals("y") || prop.equals("yes"))
			return true;

		return false;
	}
	/**
	 * This method will return an boolean value from the properties object using the specified theme. If the theme is null or not found, the default property will be returned.
	 * @return true if the property is found and is equal to : True,Yes,T,Y. It will return false otherwise.
	 * @param theme The theme of the property to search for.
	 * @param key The property to search for.
	 * @param defaultProperty The default value if the property is not found
	 */
	public boolean getThemeBooleanProperty(String theme, String key, boolean defaultProperty) {
		if (theme == null)
			return getBooleanProperty(key, defaultProperty);

		String prop = getProperty(theme + "." + key, null);
		if (prop == null) {
			prop = getProperty(key, null);
			if (prop == null)
				return defaultProperty;
		}

		prop = prop.toLowerCase();
		if (prop.equals("t") || prop.equals("true") || prop.equals("y") || prop.equals("yes"))
			return true;

		return false;
	}
	/**
	 * This method will return an Color value from the properties object using the specified theme. If the theme is null or not found, the default property will be returned..
	 * @return The property or null if it is not found or the property is not a color.
	 * @param theme The theme of the property to search for.
	 * @param key The property to search for.
	 */
	public Color getThemeColorProperty(String theme, String key) {

		if (theme == null)
			return getColorProperty(key);

		Color c = getColorProperty(theme + "." + key);
		if (c == null)
			return getColorProperty(key);
		else
			return c;

	}
	/**
	 * This method will return an integer value from the properties object using the specified theme. If the theme is null or not found, the default property will be returned..
	 * @return The property or -1 if it is not found or the property is not an integer.
	 * @param theme The theme of the property to search for.
	 * @param key The property to search for.
	 */
	public int getThemeIntProperty(String theme, String key) {

		if (theme == null)
			return getIntProperty(key);

		String prop = getProperty(theme + "." + key, null);
		if (prop == null) {
			prop = getProperty(key, null);
			if (prop == null)
				return -1;
		}

		try {
			int ret = Integer.parseInt(prop);
			return ret;
		} catch (Exception e) {
			return -1;
		}

	}

	/**
	 * This method will return an integer value from the properties object using the specified theme. If the theme is null or not found, the default property will be returned..
	 * @return The property or -1 if it is not found or the property is not an integer.
	 * @param theme The theme of the property to search for.
	 * @param key The property to search for.
	 * @param defaultVal the value to return if non is found in the property file
	 */
	public int getThemeIntProperty(String theme, String key, int defaultVal) {

		if (theme == null)
			return getIntProperty(key);

		String prop = getProperty(theme + "." + key, null);
		if (prop == null) {
			prop = getProperty(key, null);
			if (prop == null)
				return defaultVal;
		}

		try {
			int ret = Integer.parseInt(prop);
			return ret;
		} catch (Exception e) {
			return defaultVal;
		}

	}
	/**
	 * This method will return a value from the properties object using the specified theme. If the theme is null or not found, the default property will be returned.
	 * @return The property or null if it is not found.
	 * @param theme The theme of the property to search for.
	 * @param key The property to search for.
	 */
	public String getThemeProperty(String theme, String key) {
		if (theme == null)
			return getProperty(key, null);
		else {
			String property = getProperty(theme + "." + key, null);
			if (property == null)
				return getProperty(key, null);
			else
				return property;
		}
	}

	/**
	 * This method will return a value from the properties object using the specified theme. If the theme is null or not found, the default property will be returned.
	 * @return The property or null if it is not found.
	 * @param theme The theme of the property to search for.
	 * @param key The property to search for.
	 * @param defaultVal The default value for the property
	 */
	public String getThemeProperty(String theme, String key, String defaultVal) {
		if (theme == null)
			return getProperty(key, null);
		else {
			String property = getProperty(theme + "." + key, null);
			if (property == null)
				return getProperty(key, defaultVal);
			else
				return property;
		}
	}
	private static PropsDescriptor loadProperties(File f, String name) {
		PropsDescriptor ret = null;
		try {
			FileInputStream in = new FileInputStream(f);
			Properties p = new Properties();
			p.load(in);
			in.close();
			ret = new PropsDescriptor(p, name, f.lastModified());

		} catch (Exception e) {
			ret = new PropsDescriptor(null, name, f.lastModified());
		}
		return ret;
	}
	private static void reloadProperties(File f, PropsDescriptor pd) {
		try {
			FileInputStream in = new FileInputStream(f);
			Properties p = new Properties();
			p.load(in);
			in.close();
			pd.setProperties(p);
			pd.setLastMod(f.lastModified());
			ObjectstoreCache.clearCache();
		} catch (Exception e) {
		}
	}
	public static void setPropsPath() {
		PROPS_DIR=null;
		String prop = System.getProperty("salmon.props.path");
		if (prop != null && prop.length() > 0)
			PROPS_DIR = prop;
		else {
			prop = System.getProperty("salmon.root");
			if (prop == null)
				prop = System.getProperty("server.root");
			if (prop != null)
				PROPS_DIR = prop + File.separatorChar + "properties" + File.separatorChar + "HTML Framework";
		}

		if (_displayBanner) {
			System.err.println("*********************************************************");
			System.err.println("Initializing Salmon Framework Property files");
			System.err.println("Salmon Server Properties File Directory:" + PROPS_DIR);
			if (PROPS_DIR != null) {
				File propsDir = new File(PROPS_DIR);
			    System.err.println("Absolute path is:" + propsDir.getAbsolutePath());
			    if (!propsDir.exists() || !propsDir.canRead()) {
				    System.err.println("**Warning** Directory not readable!!");
				    System.err.println("Property files will be loaded from the /WEB-INF/properties directory of each web application.");
				    PROPS_DIR=null;
			    }
			}
			else
				System.err.println("Property files will be loaded from the /WEB-INF/properties directory of each web application.");
			System.err.println("To change the property file location, set the VM System property \"salmon.props.path\" via the -D runtime switch.");
			System.err.println("*********************************************************");
		}
		_displayBanner = false;
	}

	/**
	 * This method will change the path where property files are loaded from and reload the property files
	 */
	public static void setPropsPath(String propsPath) {
		System.setProperty(SYS_PROPS_DIR_PROPERTY, propsPath);
		_appProps.remove(getAppID());
		setPropsPath();
	}

	/**
	 * This method will change the path where property files are loaded from and reload the property files
	 */
	public static void setPropsPath(String propsPath, boolean displayBanner) {
		_displayBanner = displayBanner;
		System.setProperty(SYS_PROPS_DIR_PROPERTY, propsPath);
		_appProps.remove(getAppID());
		setPropsPath();
	}

    /**
     * This method returns the directory for the property files
     */
    public static String getPropsPath() {
    	if (PROPS_DIR != null && PROPS_DIR.length() > 0)
    		return PROPS_DIR;
    	else {
    		String ret=System.getProperty(SYS_PROPS_DIR_PROPERTY);
    		if (ret != null && ret.length() > 0)
    			return ret;
    		else {
    			ApplicationContext cont=ApplicationContext.getContext();
    			if (cont == null)
    				return null;
    			else
    				return cont.getAppPropertiesPath();
    		}
    	}
    }

    /**
     * @returns true if the System.properties file is shared between web applications
     */
    public static boolean isPropsPathShared() {
    	String ret=System.getProperty(SYS_PROPS_DIR_PROPERTY);
    	return ret != null;
    }

	/**
	 * Save changes to an application.properties file. Pass the directory where the System.properties are located. The passed hashtable should contain string key/value pairs with the items to be added to or modified in the property file.
	 */
	public static void updateApplicationProperties(String propsDir, String applicationName,Hashtable changes) throws IOException{
		updateProperties(applicationName,changes,propsDir);

	}
	/**
	 * Save changes to an application.properties file. The passed hashtable should contain string key/value pairs with the items to be added to or modified in the property file.
	 */
	public static void updateApplicationProperties(String applicationName,Hashtable changes) throws IOException{
		updateProperties(applicationName,changes,null);

	}

	private static void mergeProperties(String fileToMerge, String app, String propsPath) throws IOException{
		String fileName = "System.properties";
		if (app != null)
			fileName = app + ".properties";

		String propsDir = PROPS_DIR;
		if (propsPath != null)
			propsDir = propsPath;

		// first write out the current properties file
		Hashtable currentProps = new Hashtable();
		File outFile = new File(propsDir + File.separator + fileName + ".$temp");
		File inFile = new File(propsDir + File.separator + fileName);
		PrintWriter out = new PrintWriter(new FileOutputStream(outFile));
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));
		String s = in.readLine();
		while (s != null) {
			int ndx = s.indexOf('=');
			if (ndx > -1) {
				String key = s.substring(0, ndx).trim();
				currentProps.put(key,key);
			}
			out.println(s);
			s = in.readLine();
		}
		in.close();

		//Now read the input file and merge in any items not already in there
		String lastComment = null;
		File mergeFile = new File(fileToMerge);
		in = new BufferedReader(new InputStreamReader(new FileInputStream(mergeFile)));
		s = in.readLine();
		while (s != null) {
			int ndx = s.indexOf('=');
			if (s.trim().length() == 0)
				lastComment="\r\n";
			else if (s.startsWith("#")) {
				if (lastComment == null)
					lastComment = s;
				else
					lastComment += s;
				lastComment+="\r\n";
			}
			else if (ndx > -1) {
				String key = s.substring(0, ndx).trim();
				if (!currentProps.contains(key)) {
					if (lastComment != null) {
						out.print(lastComment);
						lastComment = null;
					}
					out.println(s);
				}
			}
			s = in.readLine();
		}
		in.close();
		out.close();

		if (inFile.delete())
			outFile.renameTo(inFile);
		else
			MessageLog.writeInfoMessage("Error Merging Properties File:" + inFile + " delete of old file failed.",null);

	}

	private static void updateProperties(String app, Hashtable changes, String propsPath) throws IOException {

		String fileName = "System.properties";
		if (app != null)
			fileName = app + ".properties";

		String propsDir = PROPS_DIR;
		if (propsPath != null)
			propsDir = propsPath;

		File outFile = new File(propsDir + File.separator + fileName + ".$temp");
		File inFile = new File(propsDir + File.separator + fileName);
		PrintWriter out = new PrintWriter(new FileOutputStream(outFile));
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));
        Hashtable changes2 = (Hashtable) changes.clone();
		String s = in.readLine();
		while (s != null) {
			int ndx = s.indexOf('=');
			if (ndx == -1)
				out.println(s);
			else {
				String key = s.substring(0, ndx).trim();
				if (changes.containsKey(key)) {
                    s = key + "=" + fixSlashes((String) changes.get(key));
					changes2.remove(key);
				}
				out.println(s);
			}
			s = in.readLine();
		}

		if (changes2.size() > 0) {
			out.println();
			Enumeration e = changes2.keys();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String val = (String) changes.get(key);
				out.println(key + "=" + fixSlashes(val));
			}
		}

		in.close();
		out.close();
		if (inFile.delete())
		    outFile.renameTo(inFile);
        else
            MessageLog.writeInfoMessage("Error Updating Properties File:" + inFile + " delete of old file failed.",null);

	}

	/**
	 * Merges properties from the property file specified in inFile into the System.properties file in the propsDir. Merging consists of adding any new properties from the inFile that aren't currently in the System.properties.
	 * @param inFile The properties file to merge into the System.properties
	 * @param propsDir The directory where the System.properties file can be found or null to use the default
	 * @throws IOException
	 */
	public static void mergeSystemProperties(String inFile, String propsDir) throws IOException{
		mergeProperties(inFile,null,propsDir);
	}

	/**
	 * Merges properties from the property file specified in inFile into the System.properties file. Merging consists of adding any new properties from the inFile that aren't currently in the System.properties.
	 * @param inFile The properties file to merge into the System.properties
	 * @throws IOException
	 */
	public static void mergeSystemProperties(String inFile) throws IOException{
		mergeProperties(inFile,null,null);
	}
	/**
	 * Save changes to the System.properties file. Pass the directory where the System.properties are located. The passed hashtable should contain string key/value pairs with the items to be added to or modified in the property file.
	 */
	public static void updateSystemProperties(String propsDir, Hashtable changes) throws IOException{
		updateProperties(null,changes,propsDir);

	}
	/**
	 * Save changes to the System.properties file. The passed hashtable should contain string key/value pairs with the items to be added to or modified in the property file.
	 */
	public static void updateSystemProperties(Hashtable changes) throws IOException{
		updateProperties(null,changes,null);

	}

    /**
     * Overrides the unit properties loaded with this Props object and replaces it with the one specified
     */
    public void setUnitProperties(Properties props) {
        _unit = props;
    }

    private static String getAppID() {
    	ApplicationContext cont =ApplicationContext.getContext();
    	if (cont == null)
    		return "";
    	else
    		return cont.getAppID();
    }
}
