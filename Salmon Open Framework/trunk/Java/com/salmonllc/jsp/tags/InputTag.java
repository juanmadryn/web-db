package com.salmonllc.jsp.tags;

////////////////////////////////////////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/jsp/tags/InputTag.java $
//$Author: Dan $
//$Revision: 63 $
//$Modtime: 11/01/04 11:56a $
////////////////////////////////////////////////////////////

import com.salmonllc.html.*;
import com.salmonllc.util.Util;

import javax.servlet.jsp.JspWriter;

/**
 * This tag approximately corresponds to the HTML tag "INPUT"
 */

public class InputTag extends BaseBodyTag {
	private String _align;
	private String _alt;
	private String _height;
	private String _width;

	private String _backgroundColor;
	private String _border;
	private String _bottomRightBorderColor;
	private String _caption; // RADIO
	private String _captionPos; // RADIO list
	private String _checked;
	private String _checkedFalseValue; // CHECKBOX
	private String _checkedTrueValue; // CHECKBOX

	private String _cols; // TEXTAREA
	private String _enabled;

	private String _font;
	private String _disabledfont;
	private String _fontFace;
	private String _fontSize;
	private String _fontStyle;

	private String _maxLength;
	private String _multiple; // SELECT
	private String _onBlur;
	private String _onChange; // TEXT
	private String _onClick;
	private String _onKeyUp;
	private String _onFocus; // TEXT
	private String _orientation; // RADIO list
	private String _group; // RADIO

	private String _rows; // TEXTAREA
	private String _size;
	private String _src;
	private String _wrap; // TEXTAREA
	private String _style; // TEXTAREA

	private String _theme;
	private String _textColor;
	private String _topLeftBorderColor;
	private String _transparentColor;
	private String _type;

	private String _value;

	private String _applystyletoseparator; // PHONE
	private String _autoTab; // PHONE
	private String _areacodeend; // PHONE
	private String _areacodestart; // PHONE
	private String _codes; // STATE
	private String _decprecision; // DECIMAL
	private String _extendedzip; // ZIP
	private String _fracsize; // FRACTION
	private String _minlength; // EMAIL
	private String _nopadding; // FRACTION,DECIMAL
	private String _scale; // FRACTION,DECIMAL
	private String _separator; // SSNMULTI,PHONE

	private String _fInterval; // DATE
	private String _fFromtime; // DATE
	private String _fTotime; // DATE
	private String _fDateflag; // DATE
	private String _dataSource;
	private String _class;

	private String _displaynamelocalekey; //SUBMIT
	private String _textlocalekey; //IMAGE
	private String _urllocalekey; //IMAGE
	private String _displayformatlocalekey; //TEXT
	private String _displayformat; //TEXT

	private String _hspace; //IMAGE
	private String _vspace; //IMAGE
	private String _title;
	private String _onmouseout;
	private String _onmouseover;

	private String _readOnly;
	private String _accessKey;
	private String _tabIndex;
	private String _listType;

	/**
	 * Creates the component to be used by the tag.
	 */
	public HtmlComponent createComponent() {
		if (_type == null)
			return null;

		String type = _type.toUpperCase();

		if (type.equals("BUTTON")) {
			HtmlButton button = new HtmlButton(getName(), _value, _theme, getHelper().getController());
			button.setOnClick(_onClick);
			button.setVisible(BaseTagHelper.stringToBoolean(getVisible(), true));
			if (getEnabled() != null)
				button.setEnabled(BaseTagHelper.stringToBoolean(getEnabled(), true));
			if (_dataSource != null)
				button.setDataSource(_dataSource);
			if (getBgcolor() != null)
				button.setButtonBgColor(getBgcolor());
			if (getFontstyle() != null)
				button.setButtonFontStyle(getFontstyle());
			if (_accessKey != null)
				button.setAccessKey(_accessKey);
			if (_tabIndex != null)
				button.setTabIndex(BaseTagHelper.stringToInt(_tabIndex));
			button.setClassName(_class);
			return button;
		} else if (type.equals("CHECKBOX")) {
			HtmlCheckBox cbx = new HtmlCheckBox(getName(), _theme, getHelper().getController(), BaseTagHelper.ifNull(_checkedTrueValue, "1"), BaseTagHelper.ifNull(_checkedFalseValue, "0"));
			cbx.setValue(BaseTagHelper.stringToBoolean(_checked, false) ? BaseTagHelper.ifNull(_checkedTrueValue, "1") : BaseTagHelper.ifNull(_checkedFalseValue, "0"));
			cbx.setOnClick(_onClick);
			cbx.setVisible(BaseTagHelper.stringToBoolean(getVisible(), true));
			if (getEnabled() != null)
				cbx.setEnabled(BaseTagHelper.stringToBoolean(getEnabled(), true));

			String fontStartTag = getHelper().getController().getPageProperties().getProperty(getFont() + ".StartTag");
			String fontEndTag = getHelper().getController().getPageProperties().getProperty(getFont() + ".EndTag");

			cbx.setFontStartTag(fontStartTag);
			cbx.setFontEndTag(fontEndTag);
			if (_dataSource != null)
				cbx.setDataSource(_dataSource);
			if (_class != null)
				cbx.setClassName(_class);
			if (_accessKey != null)
				cbx.setAccessKey(_accessKey);
			if (_tabIndex != null)
				cbx.setTabIndex(BaseTagHelper.stringToInt(_tabIndex));

			return cbx;
		} else if (type.equals("FILE")) {
			HtmlFileUpload fileUpLoad = new HtmlFileUpload(getName(), _theme, getHelper().getController());
			if (_dataSource != null)
				fileUpLoad.setDataSource(_dataSource);
			if (_class != null)
				fileUpLoad.setClassName(_class);
			if (getEnabled() != null)
				fileUpLoad.setEnabled(BaseTagHelper.stringToBoolean(getEnabled(), true));
			if (getVisible() != null)
				fileUpLoad.setVisible(BaseTagHelper.stringToBoolean(getVisible(), true));
			if (getSize() != null)
				fileUpLoad.setSize(BaseTagHelper.stringToInt(getSize()));
			if (_tabIndex != null)
				fileUpLoad.setTabIndex(BaseTagHelper.stringToInt(_tabIndex));

			return fileUpLoad;
		} else if (type.equals("HIDDEN")) {
			HtmlHiddenField hiddenField = new HtmlHiddenField(getName(), _value, getHelper().getController());
			if (_dataSource != null)
				hiddenField.setDataSource(_dataSource);

			return hiddenField;
		} else if (type.equals("IMAGE")) {
			HtmlSubmitImage image = null;
			if (_src == null)
				image = new HtmlSubmitImage(getName(), _value, 0, getHelper().getController());
			else
				image = new HtmlSubmitImage(getName(), _src, getHelper().getController());

			image.setAlign(_align);
			image.setAlt(_alt);
			image.setFont(_fontFace, -1, BaseTagHelper.stringToInt(_fontSize));
			image.setBackgroundColor(_backgroundColor);
			image.setTextColor(_textColor);
			image.setTopLeftColor(_topLeftBorderColor);
			image.setBottomRightColor(_bottomRightBorderColor);
			image.setTransparentColor(_transparentColor);
			image.setVisible(BaseTagHelper.stringToBoolean(getVisible(), true));
			image.setOnClick(_onClick);
			image.setStyle(_style);
			if (_height != null)
				image.setHeight(BaseTagHelper.stringToInt(_height));
			if (_width != null)
				image.setWidth(BaseTagHelper.stringToInt(_width));
			if (_dataSource != null)
				image.setDataSource(_dataSource);
			if (!BaseTagHelper.isEmpty(_textlocalekey))
				image.setTextLocaleKey(_textlocalekey);
			if (!BaseTagHelper.isEmpty(_urllocalekey))
				image.setURLLocaleKey(_urllocalekey);
			if (_hspace != null)
				image.setHorizontalSpace(BaseTagHelper.stringToInt(_hspace));
			if (_vspace != null)
				image.setVerticalSpace(BaseTagHelper.stringToInt(_vspace));
			if (_accessKey != null)
				image.setAccessKey(_accessKey);
			if (_tabIndex != null)
				image.setTabIndex(BaseTagHelper.stringToInt(_tabIndex));
			if (_onmouseout != null)
				image.setOnMouseOut(_onmouseout);
			if (_onmouseover != null)
				image.setOnMouseOver(_onmouseover);
			image.setTitle(_title);
			return image;
		} else if (type.equals("PASSWORD")) {
			HtmlPasswordEdit pass = new HtmlPasswordEdit(getName(), _theme, getHelper().getController());
			pass.setSize(BaseTagHelper.stringToInt(_size));
			pass.setMaxLength(BaseTagHelper.stringToInt(_maxLength));
			pass.setValue(_value);

			if (Util.isFilled(getDisabledfont())) {
				String disabledFontStartTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".StartTag");
				String disabledFontEndTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".EndTag");

				pass.setDisabledFontStartTag(disabledFontStartTag);
				pass.setDisabledFontEndTag(disabledFontEndTag);
			}

			pass.setVisible(BaseTagHelper.stringToBoolean(getVisible(), true));
			if (getEnabled() != null)
				pass.setEnabled(BaseTagHelper.stringToBoolean(getEnabled(), true));

			if (_dataSource != null)
				pass.setDataSource(_dataSource);
			if (_class != null)
				pass.setClassName(_class);

			if (_accessKey != null)
				pass.setAccessKey(_accessKey);
			if (_tabIndex != null)
				pass.setTabIndex(BaseTagHelper.stringToInt(_tabIndex));
			if (_readOnly != null)
				pass.setReadOnly(BaseTagHelper.stringToBoolean(_readOnly));
			return pass;
		} else if (type.equals("RADIO") || type.equals("RADIOBUTTON")) {

			HtmlRadioButton rb;
			rb = new HtmlRadioButton(getName(), getHelper().getController(), BaseTagHelper.ifNull(_checkedTrueValue, "1"), BaseTagHelper.ifNull(_caption, ""));
			rb.setValue(BaseTagHelper.stringToBoolean(_checked, false) ? BaseTagHelper.ifNull(_checkedTrueValue, "1") : BaseTagHelper.ifNull(_checkedFalseValue, "0"));
			rb.setOnClick(_onClick);
			rb.setCaptionLayout(_captionPos);
			rb.setVisible(BaseTagHelper.stringToBoolean(getVisible(), true));
			if (getEnabled() != null)
				rb.setEnabled(BaseTagHelper.stringToBoolean(getEnabled(), true));

			if (_group != null)
				rb.setGroup(_group);
			String fontStartTag = getHelper().getController().getPageProperties().getProperty(getFont() + ".StartTag");
			String fontEndTag = getHelper().getController().getPageProperties().getProperty(getFont() + ".EndTag");
			rb.setFontStartTag(fontStartTag);
			rb.setFontEndTag(fontEndTag);
			if (_dataSource != null)
				rb.setDataSource(_dataSource);
			if (_class != null)
				rb.setClassName(_class);
			if (_accessKey != null)
				rb.setAccessKey(_accessKey);
			if (_tabIndex != null)
				rb.setTabIndex(BaseTagHelper.stringToInt(_tabIndex));
			return rb;
		} else if (type.equals("RADIOGROUP") || type.equals("RADIOBUTTONGROUP")) {
			HtmlRadioButtonGroup rbGroup = new HtmlRadioButtonGroup(getName(), _theme, getHelper().getController());
			rbGroup.setOrientation(_orientation);
			rbGroup.setCaptionLayout(_captionPos);
			rbGroup.setOnClick(_onClick);
			rbGroup.setValue(_value);
			rbGroup.setVisible(BaseTagHelper.stringToBoolean(getVisible(), true));
			if (getEnabled() != null)
				rbGroup.setEnabled(BaseTagHelper.stringToBoolean(getEnabled(), true));
			String fontStartTag = getHelper().getController().getPageProperties().getProperty(getFont() + ".StartTag");
			String fontEndTag = getHelper().getController().getPageProperties().getProperty(getFont() + ".EndTag");
			rbGroup.setFontStartTag(fontStartTag);
			rbGroup.setFontEndTag(fontEndTag);
			if (_dataSource != null)
				rbGroup.setDataSource(_dataSource);
			if (_class != null)
				rbGroup.setClassName(_class);
			if (_tabIndex != null)
				rbGroup.setTabIndex(BaseTagHelper.stringToInt(_tabIndex));
			return rbGroup;
		} else if ((type.equals("SELECT") && BaseTagHelper.stringToBoolean(_multiple, false)) || type.equals("LISTBOX")) {
			HtmlListBox lbx = new HtmlListBox(getName(), _theme, getHelper().getController());
			lbx.setSize(BaseTagHelper.stringToInt(_size));
			lbx.setOnChange(_onChange);
			lbx.setOnClick(_onClick);
			lbx.setOnFocus(_onFocus);
			lbx.setOnLoseFocus(_onBlur);
			lbx.setVisible(BaseTagHelper.stringToBoolean(getVisible(), true));
			if (getEnabled() != null)
				lbx.setEnabled(BaseTagHelper.stringToBoolean(getEnabled(), true));

			lbx.setMulti(BaseTagHelper.stringToBoolean(_multiple, false));

			if (Util.isFilled(getDisabledfont())) {
				String disabledFontStartTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".StartTag");
				String disabledFontEndTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".EndTag");

				lbx.setDisabledFontStartTag(disabledFontStartTag);
				lbx.setDisabledFontEndTag(disabledFontEndTag);
			}

			String fontStartTag = getHelper().getController().getPageProperties().getProperty(getFont() + ".StartTag");
			String fontEndTag = getHelper().getController().getPageProperties().getProperty(getFont() + ".EndTag");
			lbx.setFontStartTag(fontStartTag);
			lbx.setFontEndTag(fontEndTag);
			if (_dataSource != null)
				lbx.setDataSource(_dataSource);
			if (_class != null)
				lbx.setClassName(_class);

			if (_accessKey != null)
				lbx.setAccessKey(_accessKey);
			if (_tabIndex != null)
				lbx.setTabIndex(BaseTagHelper.stringToInt(_tabIndex));

			return lbx;
		} else if (type.equals("SELECT")) {
			HtmlDropDownList dd = new HtmlDropDownList(getName(), _theme, getHelper().getController());
			String fontStartTag = getHelper().getController().getPageProperties().getProperty(getFont() + ".StartTag");
			String fontEndTag = getHelper().getController().getPageProperties().getProperty(getFont() + ".EndTag");
			dd.setOnChange(_onChange);
			dd.setOnClick(_onClick);
			dd.setStyle(_style);
			dd.setOnFocus(_onFocus);
			dd.setOnLoseFocus(_onBlur);
			dd.setVisible(BaseTagHelper.stringToBoolean(getVisible(), true));
			if (getEnabled() != null)
				dd.setEnabled(BaseTagHelper.stringToBoolean(getEnabled(), true));

			dd.setFontStartTag(fontStartTag);
			dd.setFontEndTag(fontEndTag);
			dd.setValue(_value);

			if (Util.isFilled(getDisabledfont())) {
				String disabledFontStartTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".StartTag");
				String disabledFontEndTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".EndTag");

				dd.setDisabledFontStartTag(disabledFontStartTag);
				dd.setDisabledFontEndTag(disabledFontEndTag);
			}
			if (_dataSource != null)
				dd.setDataSource(_dataSource);
			if (_class != null)
				dd.setClassName(_class);
			if (_tabIndex != null)
				dd.setTabIndex(BaseTagHelper.stringToInt(_tabIndex));
			return dd;
		} else if (type.equals("SUBMIT")) {
			HtmlSubmitButton button = new HtmlSubmitButton(getName(), _value, _theme, getHelper().getController());
			button.setOnClick(_onClick);
			button.setVisible(BaseTagHelper.stringToBoolean(getVisible(), true));
			if (getEnabled() != null)
				button.setEnabled(BaseTagHelper.stringToBoolean(getEnabled(), true));

			if (_dataSource != null)
				button.setDataSource(_dataSource);
			if (getBgcolor() != null)
				button.setButtonBgColor(getBgcolor());
			if (getFontstyle() != null)
				button.setButtonFontStyle(getFontstyle());
			if (_class != null)
				button.setClassName(_class);
			if (!BaseTagHelper.isEmpty(_displaynamelocalekey))
				button.setDisplayNameLocaleKey(_displaynamelocalekey);
			if (_accessKey != null)
				button.setAccessKey(_accessKey);
			if (_tabIndex != null)
				button.setTabIndex(BaseTagHelper.stringToInt(_tabIndex));
			return button;
		} else if (type.equals("TEXT")) {
			HtmlTextEdit edit = new HtmlTextEdit(getName(), getHelper().getController());
			edit.setSize(BaseTagHelper.stringToInt(_size));
			edit.setMaxLength(BaseTagHelper.stringToInt(_maxLength));
			edit.setValue(_value);
			edit.setOnChange(_onChange);
			edit.setOnFocus(_onFocus);
			edit.setOnLoseFocus(_onBlur);
			edit.setOnKeyUp(_onKeyUp);
			edit.setStyle(_style);

			if (Util.isFilled(getDisabledfont())) {
				String disabledFontStartTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".StartTag");
				String disabledFontEndTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".EndTag");

				edit.setDisabledFontStartTag(disabledFontStartTag);
				edit.setDisabledFontEndTag(disabledFontEndTag);
			}

			edit.setVisible(BaseTagHelper.stringToBoolean(getVisible(), true));
			if (getEnabled() != null)
				edit.setEnabled(BaseTagHelper.stringToBoolean(getEnabled(), true));
			if (_dataSource != null)
				edit.setDataSource(_dataSource);
			if (!BaseTagHelper.isEmpty(_displayformatlocalekey))
				edit.setDisplayFormatLocaleKey(_displayformatlocalekey);
			if (!BaseTagHelper.isEmpty(_displayformat))
				edit.setDisplayFormat(_displayformat);
			if (!BaseTagHelper.isEmpty(_class))
				edit.setClassName(_class);
			if (_accessKey != null)
				edit.setAccessKey(_accessKey);
			if (_tabIndex != null)
				edit.setTabIndex(BaseTagHelper.stringToInt(_tabIndex));
			if (_readOnly != null)
				edit.setReadOnly(BaseTagHelper.stringToBoolean(_readOnly));

			return edit;
		} else if (type.equals("TEXTAREA")) {
			HtmlMultiLineTextEdit edit = new HtmlMultiLineTextEdit(getName(), _theme, getHelper().getController());
			edit.setColumns(BaseTagHelper.stringToInt(_cols));
			edit.setRows(BaseTagHelper.stringToInt(_rows));
			edit.setValue(_value);
			edit.setWrap(_wrap);
			edit.setOnChange(_onChange);
			edit.setOnFocus(_onFocus);
			edit.setOnLoseFocus(_onBlur);
			edit.setStyle(_style);

			if (getEnabled() != null)
				edit.setEnabled(BaseTagHelper.stringToBoolean(getEnabled(), true));

			if (Util.isFilled(getDisabledfont())) {
				String disabledFontStartTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".StartTag");
				String disabledFontEndTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".EndTag");

				edit.setDisabledFontStartTag(disabledFontStartTag);
				edit.setDisabledFontEndTag(disabledFontEndTag);
			}

			edit.setVisible(BaseTagHelper.stringToBoolean(getVisible(), true));
			if (_dataSource != null)
				edit.setDataSource(_dataSource);
			if (_class != null)
				edit.setClassName(_class);
			if (_accessKey != null)
				edit.setAccessKey(_accessKey);
			if (_tabIndex != null)
				edit.setTabIndex(BaseTagHelper.stringToInt(_tabIndex));
			if (_readOnly != null)
				edit.setReadOnly(BaseTagHelper.stringToBoolean(_readOnly));
			if (_maxLength != null)
				edit.setMaxLength(BaseTagHelper.stringToInt(_maxLength, -1));

			return edit;
		} else if (type.equals("EMAIL")) {
			HtmlEMailComponent email = new HtmlEMailComponent(getName(), getHelper().getController());
			email.setMinLength(BaseTagHelper.stringToInt(getMinlength()));
			email.setMaxLength(BaseTagHelper.stringToInt(getMaxlength()));
			email.setSize(BaseTagHelper.stringToInt(getSize()));
			email.setEnabled(BaseTagHelper.stringToBoolean(getEnabled()));

			if (Util.isFilled(getDisabledfont())) {
				String disabledFontStartTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".StartTag");
				String disabledFontEndTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".EndTag");

				email.setDisabledFontStartTag(disabledFontStartTag);
				email.setDisabledFontEndTag(disabledFontEndTag);
			}

			if (_dataSource != null)
				email.setDataSource(_dataSource);
			if (_class != null)
				email.setClassName(_class);

			if (_accessKey != null)
				email.setAccessKey(_accessKey);
			if (_tabIndex != null)
				email.setTabIndex(BaseTagHelper.stringToInt(_tabIndex));
			if (_readOnly != null)
				email.setReadOnly(BaseTagHelper.stringToBoolean(_readOnly));

			return email;
		} else if (type.equals("SSNMULTI")) {
			HtmlSSNComponent ssnmulti = new HtmlSSNComponent(getName(), getHelper().getController()); // if
			// the
			// user
			// gave
			// a
			// new
			// separator
			// char
			// use
			// it
			String separator = getSeparator();
			if (separator != null) {
				ssnmulti.setSsnSeparator(separator);
			}

			if (Util.isFilled(getDisabledfont())) {
				String disabledFontStartTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".StartTag");
				String disabledFontEndTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".EndTag");

				ssnmulti.setDisabledFontStartTag(disabledFontStartTag);
				ssnmulti.setDisabledFontEndTag(disabledFontEndTag);
			}

			ssnmulti.setEnabled(BaseTagHelper.stringToBoolean(getEnabled()));
			ssnmulti.setTheme(getTheme());
			if (_dataSource != null)
				ssnmulti.setDataSource(_dataSource);
			if (_class != null)
				ssnmulti.setClassName(_class);
			if (_accessKey != null)
				ssnmulti.setAccessKey(_accessKey);
			if (_tabIndex != null)
				ssnmulti.setTabIndex(BaseTagHelper.stringToInt(_tabIndex));
			if (_readOnly != null)
				ssnmulti.setReadOnly(BaseTagHelper.stringToBoolean(_readOnly));

			return ssnmulti;
		} else if (type.equals("SSN")) { // sr 07-11-2001 need to make
			// dreamweaver stuff for this comp
			HtmlSSNSingleComponent ssn = new HtmlSSNSingleComponent(getName(), getHelper().getController());
			String separator = getSeparator();
			if (separator != null) {
				ssn.setSeparator(getSeparator());
			}

			if (Util.isFilled(getDisabledfont())) {
				String disabledFontStartTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".StartTag");
				String disabledFontEndTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".EndTag");

				ssn.setDisabledFontStartTag(disabledFontStartTag);
				ssn.setDisabledFontEndTag(disabledFontEndTag);
			}

			ssn.setEnabled(BaseTagHelper.stringToBoolean(getEnabled()));
			ssn.setTheme(getTheme());
			if (_dataSource != null)
				ssn.setDataSource(_dataSource);
			if (_class != null)
				ssn.setClassName(_class);
			if (_accessKey != null)
				ssn.setAccessKey(_accessKey);
			if (_tabIndex != null)
				ssn.setTabIndex(BaseTagHelper.stringToInt(_tabIndex));
			if (_readOnly != null)
				ssn.setReadOnly(BaseTagHelper.stringToBoolean(_readOnly));

			return ssn;
		} else if (type.equals("STATE") || type.equals("STATECOUNTRY")) { // get
			// attribute
			// that
			// determines
			// if
			// you
			// want
			// to
			// see
			// full
			// state
			// names
			boolean showCodes = BaseTagHelper.stringToBoolean(getCodes(), true);
			HtmlStateComponent state = null;
			if (_listType == null)
				state = new HtmlStateComponent(getName(), getHelper().getController(), showCodes);
			else
				state = new HtmlStateComponent(getName(), getHelper().getController(), _listType, showCodes);
			state.setTheme(getTheme());

			if (Util.isFilled(getDisabledfont())) {
				String disabledFontStartTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".StartTag");
				String disabledFontEndTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".EndTag");

				state.setDisabledFontStartTag(disabledFontStartTag);
				state.setDisabledFontEndTag(disabledFontEndTag);
			}

			state.setEnabled(BaseTagHelper.stringToBoolean(getEnabled()));
			if (_dataSource != null)
				state.setDataSource(_dataSource);
			if (_class != null)
				state.setClassName(_class);
			if (_tabIndex != null)
				state.setTabIndex(BaseTagHelper.stringToInt(_tabIndex));

			return state;
		} else if (type.equals("PHONE")) {
			HtmlTelephoneComponent phone = new HtmlTelephoneComponent(getName(), getHelper().getController());

			// if the user gave a new start char use it
			String areaCodeStartChar = getAreacodestart();
			if (areaCodeStartChar != null) {
				phone.setAreaCodeStart(areaCodeStartChar);
			}

			//if autotab set it
			if (_autoTab != null)
				phone.setAutoTab(BaseTagHelper.stringToBoolean(_autoTab));

			// if the user gave a new end char use it
			String areaCodeEndChar = getAreacodeend();
			if (areaCodeEndChar != null) {
				phone.setAreaCodeEnd(areaCodeEndChar);
			}

			// if the user gave a new separator char use it
			String separator = getSeparator();
			if (separator != null) {
				phone.setPhoneSeparator(separator);
			}

			// 10/01/03 - BYD - Added the option to not apply a style to the
			// static elements of a telephone #
			//          such as () and -
			//          The main reason is to avoid unsightly border when border is added
			// to style
			//
			// Elect to disable applying the style to the static elements of the
			// Phone component
			// Default is "apply style" for backward compatibility
			String sApplyStyleToSeparator = getApplystyletoseparator();
			if ((sApplyStyleToSeparator != null) && ((sApplyStyleToSeparator.equals("0")) || (sApplyStyleToSeparator.toLowerCase().equals("false")) || (sApplyStyleToSeparator.toLowerCase().equals("no")))) {
				phone.setApplyStyle(false);
			} else
				phone.setApplyStyle(true);

			if (Util.isFilled(getDisabledfont())) {
				String disabledFontStartTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".StartTag");
				String disabledFontEndTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".EndTag");

				phone.setDisabledFontStartTag(disabledFontStartTag);
				phone.setDisabledFontEndTag(disabledFontEndTag);
			}
			phone.setEnabled(BaseTagHelper.stringToBoolean(getEnabled()));
			if (_dataSource != null)
				phone.setDataSource(_dataSource);
			if (_class != null)
				phone.setClassName(_class);
			if (_accessKey != null)
				phone.setAccessKey(_accessKey);
			if (_tabIndex != null)
				phone.setTabIndex(BaseTagHelper.stringToInt(_tabIndex));
			if (_readOnly != null)
				phone.setReadOnly(BaseTagHelper.stringToBoolean(_readOnly));

			return phone;
		} else if (type.equals("ZIP")) {
			boolean extended = BaseTagHelper.stringToBoolean(getExtendedzip(), false);
			HtmlZipCodeComponent zip = new HtmlZipCodeComponent(getName(), getHelper().getController(), extended);
			zip.setTheme(getTheme());

			if (Util.isFilled(getDisabledfont())) {
				String disabledFontStartTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".StartTag");
				String disabledFontEndTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".EndTag");

				zip.setDisabledFontStartTag(disabledFontStartTag);
				zip.setDisabledFontEndTag(disabledFontEndTag);
			}

			zip.setEnabled(BaseTagHelper.stringToBoolean(getEnabled()));
			if (_dataSource != null)
				zip.setDataSource(_dataSource);
			if (_class != null)
				zip.setClassName(_class);
			if (_accessKey != null)
				zip.setAccessKey(_accessKey);
			if (_tabIndex != null)
				zip.setTabIndex(BaseTagHelper.stringToInt(_tabIndex));
			if (_readOnly != null)
				zip.setReadOnly(BaseTagHelper.stringToBoolean(_readOnly));

			return zip;
		} else if (type.equals("FRACTION")) {
			HtmlFractionComponent fraction = new HtmlFractionComponent(getName(), getHelper().getController(), BaseTagHelper.stringToInt(getFracsize()), BaseTagHelper.stringToInt(getScale()));
			fraction.setNoPadding(BaseTagHelper.stringToBoolean(getNopadding(), false));

			if (Util.isFilled(getDisabledfont())) {
				String disabledFontStartTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".StartTag");
				String disabledFontEndTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".EndTag");

				fraction.setDisabledFontStartTag(disabledFontStartTag);
				fraction.setDisabledFontEndTag(disabledFontEndTag);
			}

			fraction.setEnabled(BaseTagHelper.stringToBoolean(getEnabled()));
			if (_dataSource != null)
				fraction.setDataSource(_dataSource);
			if (_class != null)
				fraction.setClassName(_class);
			if (_accessKey != null)
				fraction.setAccessKey(_accessKey);
			if (_tabIndex != null)
				fraction.setTabIndex(BaseTagHelper.stringToInt(_tabIndex));

			return fraction;
		} else if (type.equals("DECIMAL")) {
			HtmlDecimalComponent decimal = new HtmlDecimalComponent(getName(), getHelper().getController(), BaseTagHelper.stringToInt(getDecprecision()), BaseTagHelper.stringToInt(getScale()));
			decimal.setNoPadding(BaseTagHelper.stringToBoolean(getNopadding(), false));

			if (Util.isFilled(getDisabledfont())) {
				String disabledFontStartTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".StartTag");
				String disabledFontEndTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".EndTag");

				decimal.setDisabledFontStartTag(disabledFontStartTag);
				decimal.setDisabledFontEndTag(disabledFontEndTag);
			}

			decimal.setEnabled(BaseTagHelper.stringToBoolean(getEnabled()));
			if (_dataSource != null)
				decimal.setDataSource(_dataSource);
			if (_class != null)
				decimal.setClassName(_class);
			if (_accessKey != null)
				decimal.setAccessKey(_accessKey);
			if (_tabIndex != null)
				decimal.setTabIndex(BaseTagHelper.stringToInt(_tabIndex));
			if (_readOnly != null)
				decimal.setReadOnly(BaseTagHelper.stringToBoolean(_readOnly));

			return decimal;
		} else if (type.equals("DATE")) {
			int interval = BaseTagHelper.stringToInt(getInterval(), 48);
			int fromtime = BaseTagHelper.stringToInt(getFromtime());
			int totime = BaseTagHelper.stringToInt(getTotime());
			int dateflag = 0; // default MONTH_NAME_FULL
			// we convert a string into an int to pass the the HtmlDateComponent
			String dateFlagStr = getDateflag();
			if (dateFlagStr != null) {
				if (dateFlagStr.equalsIgnoreCase("FULL")) {
					dateflag = HtmlDateComponent.MONTH_NAME_FULL;
				} else if (dateFlagStr.equalsIgnoreCase("PARTIAL")) {
					dateflag = HtmlDateComponent.MONTH_NAME_PARTIAL;
				} else if (dateFlagStr.equalsIgnoreCase("DIGIT")) {
					dateflag = HtmlDateComponent.MONTH_NAME_DIGIT;
				}
			} //  HtmlDateComponent(String name, HtmlPage p, int nbrIntervals,
			// int iFromTime, int iToTime, int iFlag) {
			HtmlDateComponent date = new HtmlDateComponent(getName(), getHelper().getController(), interval, totime, fromtime, dateflag);
			if (_dataSource != null)
				date.setDataSource(_dataSource);
			if (_class != null)
				date.setClassName(_class);

			if (_tabIndex != null)
				date.setTabIndex(BaseTagHelper.stringToInt(_tabIndex));

			date.setEnabled(BaseTagHelper.stringToBoolean(getEnabled()));
			if (Util.isFilled(getDisabledfont())) {
				String disabledFontStartTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".StartTag");
				String disabledFontEndTag = getHelper().getController().getPageProperties().getProperty(getDisabledfont() + ".EndTag");

				date.setDisabledFontStartTag(disabledFontStartTag);
				date.setDisabledFontEndTag(disabledFontEndTag);
			}

			return date;
		} else {
			return null;
		}
	}

	/**
	 * This method is called when necessary to generate the required html for a
	 * tag. It should be overridden by tags that have more Html to generate
	 * (generally tags that require several passes to complete). A tag shouldn't
	 * generate any Html itself, but should instead delagate that to the Html or
	 * JSP component within it.
	 */

	protected void generateComponentHTML(JspWriter w) throws Exception {
		super.generateComponentHTML(w);
		writeFormHiddenFields(w);
	}

	/**
	 * 10/01/03 - BYD - Added the option to not apply a style to the static
	 * elements of a telephone # such as () and - The main reason is to avoid
	 * unsightly border when border is added to style <p/>get the tag's apply
	 * style to phone's separator elements
	 */
	public String getApplystyletoseparator() {
		return _applystyletoseparator;
	}

	/**
	 * get the tag's align attribute
	 */
	public String getAlign() {
		return _align;
	}

	/**
	 * get the tag's alt attribute
	 */
	public java.lang.String getAlt() {
		return _alt;
	}

	/**
	 * Gets the ending character to use to enclose the area code. For input type
	 * PHONE
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getAreacodeend() {
		return _areacodeend;
	}

	/**
	 * Gets the starting character to use to enclose the area code. For input
	 * type PHONE
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getAreacodestart() {
		return _areacodestart;
	}

	/**
	 * get the tag's backgroundColor attribute
	 */
	public String getBgcolor() {
		return _backgroundColor;
	}

	/**
	 * get the tag's border attribute
	 */
	public String getBorder() {
		return _border;
	}

	/**
	 * get the tag's bottomRightBorderColor attribute
	 */
	public String getBottomrightbordercolor() {
		return _bottomRightBorderColor;
	}

	/**
	 * get the tag's caption attribute
	 */
	public String getCaption() {
		return _caption;
	}

	/**
	 * get the tag's captionPos attribute
	 */
	public String getCaptionpos() {
		return _captionPos;
	}

	/**
	 * get the tag's checked attribute
	 */
	public String getChecked() {
		return _checked;
	}

	/**
	 * This method gets checked false attribute for the tag
	 */

	public String getCheckedfalsevalue() {
		return _checkedFalseValue;
	}

	/**
	 * This method gets checked true attribute for the tag
	 */

	public String getCheckedtruevalue() {
		return _checkedTrueValue;
	}

	/**
	 * get the tag's classname attribute
	 */
	public String getClassname() {
		return _class;
	}

	/**
	 * Gets A true false value indicating whether to use just code or to show
	 * full state names. For STATE input type
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getCodes() {
		return _codes;
	}

	/**
	 * get the tag's columns attribute
	 */
	public String getCols() {
		return _cols;
	}

	/**
	 * Get the Data Source the component should be bound to
	 */
	public String getDatasource() {
		return _dataSource;
	}

	/**
	 * Gets the attribute to signify how you want to see the month in the DATE
	 * input type. valid values are FULL , PARTIAL ,DIGIT
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getDateflag() {
		return _fDateflag;
	}

	/**
	 * Gets Total number of digits. For DECIMAL input type.
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getDecprecision() {
		return _decprecision;
	}

	/**
	 * Gets the font in use when the component is disabled. The actual font tag
	 * is loaded from the properties file.
	 */
	public java.lang.String getDisabledfont() {
		return _disabledfont;
	}

	/**
	 * get the tag's enabled attribute
	 */
	public String getEnabled() {
		return _enabled;
	}

	/**
	 * Gets a true/false value indicating whether to use a 10 digit zip. For ZIP
	 * input type.
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getExtendedzip() {
		return _extendedzip;
	}

	/**
	 * Gets the font in use for captions. The actual font tag is loaded from the
	 * properties file.
	 */
	public java.lang.String getFont() {
		return _font;
	}

	/**
	 * Set the tag's fontFace attribute
	 */
	public String getFontface() {
		return _fontFace;
	}

	/**
	 * Set the tag's fontFace attribute
	 */
	public String getFontsize() {
		return _fontSize;
	}

	/**
	 * Set the tag's fontFace attribute
	 */
	public String getFontstyle() {
		return _fontStyle;
	}

	/**
	 * Gets the number of palces for the integer portion. For FRACTION input
	 * type.
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getFracsize() {
		return _fracsize;
	}

	/**
	 * Gets the hours ,to skip before this time.
	 */
	public java.lang.String getFromtime() {
		return _fFromtime;
	}

	/**
	 * get the tag's group attribute
	 */
	public String getGroup() {
		return _group;
	}

	/**
	 * Set the tag's wrap attribute
	 */

	public String getHeight() {
		return _height;
	}

	/**
	 * gets the Hspace
	 */
	public String getHspace() {
		return _hspace;
	}

	/**
	 * Gets the hour component, to break up the day into this many intervals.
	 * For example, 48 = half-hour intervals. 0 means do not display the hour
	 * component.
	 * 
	 * @return
	 */
	public java.lang.String getInterval() {
		return _fInterval;
	}

	/**
	 * Returns the length of the field
	 */
	public java.lang.String getLength() {
		return _maxLength;
	}

	/**
	 * Set the tag's fontFace attribute
	 */
	public String getMaxlength() {
		return _maxLength;
	}

	/**
	 * Gets the minimum number of chars an email address can be default = 7(ex.
	 * 7 would allow 'a@a.com'). For EMAIL input type.
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getMinlength() {
		return _minlength;
	}

	/**
	 * Set the tag's fontFace attribute
	 */
	public String getMultiple() {
		return _multiple;
	}

	/**
	 * Gets whether to Pad left side with spaces, i.e. right-justify the number.
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getNopadding() {
		return _nopadding;
	}

	/**
	 * Set the tag's fontFace attribute
	 */
	public String getOnblur() {
		return _onBlur;
	}

	/**
	 * Set the tag's on Change attribute
	 */
	public String getOnchange() {
		return _onChange;
	}

	/**
	 * Set the tag's on Click attribute
	 */
	public String getOnclick() {
		return _onClick;
	}

	/**
	 * Set the tag's on focus attribute
	 */
	public String getOnfocus() {
		return _onFocus;
	}

	/**
	 * Set the tag's orientation attribute
	 */
	public String getOrientation() {
		return _orientation;
	}

	/**
	 * get the tag's rows attribute
	 */
	public String getRows() {
		return _rows;
	}

	/**
	 * Gets the highest fraction proportion, e.g 64 for 1/64 .. 63/64. Or Number
	 * of decimal places (digits to right of decimal point) For FRACTION,DECIMAL
	 * input types
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getScale() {
		return _scale;
	}

	/**
	 * Gets the separator to use between the edit fields. The default is '-' a
	 * dash. For SSNMULTI,PHONE input types
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getSeparator() {
		return _separator;
	}

	/**
	 * get the tag's size attribute
	 */
	public String getSize() {
		return _size;
	}

	/**
	 * get the tag's source attribute
	 */
	public String getSrc() {
		return _src;
	}

	/**
	 * Returns the type of DreamWeaver conversion that this tag uses.
	 */
	public int getTagConvertType() {
		return CONV_WRAP_ALL_NESTED;
	}

	/**
	 * Get the tag's text color attribute
	 */
	public String getTextcolor() {
		return _textColor;
	}

	/**
	 * Get the tag's theme attribute
	 */
	public String getTheme() {
		return _theme;
	}

	/**
	 * Get the tag's top left border color attribute
	 */
	public String getTopleftbordercolor() {
		return _topLeftBorderColor;
	}

	/**
	 * Gets the hours ,to skip after this time.
	 */
	public java.lang.String getTotime() {
		return _fTotime;
	}

	/**
	 * Get the tag's transparent color attribute
	 */
	public String getTransparentcolor() {
		return _transparentColor;
	}

	/**
	 * Get the tag's type attribute
	 */
	public String getType() {
		return _type;
	}

	/**
	 * Get the tag's value attribute
	 */
	public String getValue() {
		return _value;
	}

	/**
	 * gets the Vspace
	 */
	public String getVspace() {
		return _vspace;
	}

	/**
	 * Set the tag's width attribute
	 */

	public String getWidth() {
		return _width;
	}

	/**
	 * get the tag's wrap attribute
	 */

	public String getWrap() {
		return _wrap;
	}

	/**
	 * Release all resources used by the tag.
	 */
	public void release() {
		super.release();
		_align = null;
		_backgroundColor = null;
		_bottomRightBorderColor = null;
		_caption = null;
		_captionPos = null;
		_checked = null;
		_checkedFalseValue = null;
		_checkedTrueValue = null;
		_cols = null;
		_fontFace = null;
		_fontSize = null;
		_fontStyle = null;
		_maxLength = null;
		_multiple = null;
		_onBlur = null;
		_onChange = null;
		_onClick = null;
		_onFocus = null;
		_orientation = null;
		_rows = null;
		_size = null;
		_src = null;
		_theme = null;
		_textColor = null;
		_topLeftBorderColor = null;
		_transparentColor = null;
		_type = null;
		_value = null;
		_wrap = null;
		_width = null;
		_height = null;
		_displaynamelocalekey = null;
		_textlocalekey = null;
		_urllocalekey = null;
		_displayformatlocalekey = null;
		_displayformat = null;
		_hspace = null;
		_vspace = null;
		_applystyletoseparator = null;
		_autoTab = null;
		_readOnly = null;
		_accessKey = null;
		_tabIndex = null;
		_onmouseout = null;
		_onmouseover = null;
		_listType = null;
		_onKeyUp = null;
		_title = null;
	}

	/**
	 * Set the tag's align attribute
	 */
	public void setAlign(String align) {
		_align = align;
	}

	/**
	 * sets the tag's alt attribute
	 */
	public void setAlt(java.lang.String alt) {
		_alt = alt;
	}

	/**
	 * 10/01/03 - BYD - Added the option to not apply a style to the static
	 * elements of a telephone # such as () and - The main reason is to avoid
	 * unsightly border when border is added to style <p/>Sets the apply style
	 * used to decide whether to apply the style to the static elements of the
	 * input type PHONE
	 */
	public void setApplystyletoseparator(java.lang.String newApplyStyleToSeparator) {
		_applystyletoseparator = newApplyStyleToSeparator;
	}

	/**
	 * Sets the ending character to use to enclose the area code. For input type
	 * PHONE
	 */
	public void setAreacodeend(java.lang.String newAreacodeend) {
		_areacodeend = newAreacodeend;
	}

	/**
	 * Sets the starting character to use to enclose the area code. For input
	 * type PHONE
	 */
	public void setAreacodestart(java.lang.String newAreacodestart) {
		_areacodestart = newAreacodestart;
	}

	/**
	 * Set the tag's backgroundColor attribute
	 */
	public void setBgcolor(String backgroundColor) {
		_backgroundColor = backgroundColor;
	}

	/**
	 * sets the tag's border attribute
	 */

	public void setBorder(String border) {
		_border = border;
	}

	/**
	 * Set the tag's bottomRightBorderColor attribute
	 */
	public void setBottomrightbordercolor(String bottomRightBorderColor) {
		_bottomRightBorderColor = bottomRightBorderColor;
	}

	/**
	 * Set the tag's caption attribute
	 */
	public void setCaption(String caption) {
		_caption = caption;
	}

	/**
	 * Set the tag's captionPos attribute
	 */
	public void setCaptionpos(String captionPos) {
		_captionPos = captionPos;
	}

	/**
	 * Set the tag's checked attribute
	 */
	public void setChecked(String checked) {
		_checked = checked;
	}

	/**
	 * Sets the tags checked false attribute.
	 */
	public void setCheckedfalsevalue(String val) {
		_checkedFalseValue = val;
	}

	/**
	 * Sets the tags checked true attribute.
	 */
	public void setCheckedtruevalue(String val) {
		_checkedTrueValue = val;
	}

	/**
	 * Set the tag's classname attribute
	 */
	public void setClassname(String sClass) {
		_class = sClass;
	}

	/**
	 * Sets A true false value indicating whether to use just code or to show
	 * full state names. For STATE input type
	 */
	public void setCodes(java.lang.String newCodes) {
		_codes = newCodes;
	}

	/**
	 * Set the tag's cols attribute
	 */
	public void setCols(String val) {
		_cols = val;
	}

	/**
	 * Set the Data Source the component should be bound to
	 */
	public void setDatasource(String val) {
		_dataSource = val;
	}

	/**
	 * Sets the attribute to signify how you want to see the month in the DATE
	 * input type. valid values are FULL , PARTIAL ,DIGIT
	 * 
	 * @param newDateflag
	 *            java.lang.String
	 */
	public void setDateflag(java.lang.String newDateflag) {
		_fDateflag = newDateflag;
	}

	/**
	 * Sets Total number of digits. For DECIMAL input type.
	 * 
	 * @param newDecprecision
	 */
	public void setDecprecision(java.lang.String newDecprecision) {
		_decprecision = newDecprecision;
	}

	/**
	 * Sets the font in use when the component is disabled. The font name to use
	 * for the captions. The actual font tag will be loaded from the properties
	 * file.
	 */
	public void setDisabledfont(java.lang.String new_font) {
		_disabledfont = new_font;
	}

	/**
	 * Set the tag's displayformat attribute
	 */
	public void setDisplayformat(String val) {
		_displayformat = val;
	}

	/**
	 * Set the tag's displayformatlocalekey attribute
	 */
	public void setDisplayformatlocalekey(String val) {
		_displayformatlocalekey = val;
	}

	/**
	 * Set the tag's displaynamelocalekey attribute
	 */
	public void setDisplaynamelocalekey(String val) {
		_displaynamelocalekey = val;
	}

	/**
	 * Set the tag's enabled attribute
	 */
	public void setEnabled(String val) {
		_enabled = val;
	}

	/**
	 * Sets a true/false value indicating whether to use a 10 digit zip. For ZIP
	 * input type.
	 */
	public void setExtendedzip(java.lang.String newExtendedzip) {
		_extendedzip = newExtendedzip;
	}

	/**
	 * Sets the font used in captions. The font name to use for the captions.
	 * The actual font tag will be loaded from the properties file.
	 */
	public void setFont(java.lang.String new_font) {
		_font = new_font;
	}

	/**
	 * Set the tag's fontFace attribute
	 */
	public void setFontface(String fontFace) {
		_fontFace = fontFace;
	}

	/**
	 * Set the tag's fontSize attribute
	 */
	public void setFontsize(String fontSize) {
		_fontSize = fontSize;
	}

	/**
	 * Set the tag's fontStyle attribute
	 */
	public void setFontstyle(String fontStyle) {
		_fontStyle = fontStyle;
	}

	/**
	 * Sets the number of palces for the integer portion. For FRACTION input
	 * type.
	 */
	public void setFracsize(java.lang.String newFracsize) {
		_fracsize = newFracsize;
	}

	/**
	 * Sets the hour component,to skip the hours before this time.
	 * 
	 * @param newFromtime
	 *            java.lang.String
	 */
	public void setFromtime(java.lang.String newFromtime) {
		_fFromtime = newFromtime;
	}

	/**
	 * sets the tag's group attribute
	 */

	public void setGroup(String val) {
		_group = val;
	}

	/**
	 * Set the tag's height attribute
	 */

	public void setHeight(String val) {
		_height = val;
	}

	/**
	 * sets the Hspace
	 */
	public void setHspace(String hSpace) {
		_hspace = hSpace;
	}

	/**
	 * Sets the hour component, to break up the day into this many intervals.
	 * For example, 48 = half-hour intervals. 0 means do not display the hour
	 * component.
	 * 
	 * @param newInterval
	 *            java.lang.String
	 */
	public void setInterval(java.lang.String newInterval) {
		_fInterval = newInterval;
	}

	/**
	 * Sets the length if the field
	 */
	public void setLength(java.lang.String newLength) {
		_maxLength = newLength;
	}

	/**
	 * Set the tag's maxLength attribute
	 */
	public void setMaxlength(String maxLength) {
		_maxLength = maxLength;
	}

	/**
	 * Sets the minimum number of chars an email address can be default = 7(ex.
	 * 7 would allow 'a@a.com'). For EMAIL input type.
	 */
	public void setMinlength(java.lang.String newMinlength) {
		_minlength = newMinlength;
	}

	/**
	 * Set the tag's multiple attribute
	 */
	public void setMultiple(String multiple) {
		_multiple = multiple;
	}

	/**
	 * Sets whether to Pad left side with spaces, i.e. right-justify the number.
	 * 
	 * @param newNopadding
	 *            java.lang.String
	 */
	public void setNopadding(java.lang.String newNopadding) {
		_nopadding = newNopadding;
	}

	/**
	 * Set the tag's onBlur attribute
	 */
	public void setOnblur(String onBlur) {
		_onBlur = onBlur;
	}

	/**
	 * Set the tag's onChange attribute
	 */
	public void setOnchange(String onChange) {
		_onChange = onChange;
	}

	/**
	 * Set the tag's onClick attribute
	 */
	public void setOnclick(String onClick) {
		_onClick = onClick;
	}

	/**
	 * Set the tag's onFocus attribute
	 */
	public void setOnfocus(String onFocus) {
		_onFocus = onFocus;
	}

	/**
	 * Set the tag's orientation attribute
	 */
	public void setOrientation(String orientation) {
		_orientation = orientation;
	}

	/**
	 * Set the tag's rows attribute
	 */
	public void setRows(String rows) {
		_rows = rows;
	}

	/**
	 * Sets the highest fraction proportion, e.g 64 for 1/64 .. 63/64. Or Number
	 * of decimal places (digits to right of decimal point) For FRACTION,DECIMAL
	 * input types
	 */
	public void setScale(java.lang.String newScale) {
		_scale = newScale;
	}

	/**
	 * Sets the separator to use between the edit fields. The default is '-' a
	 * dash. For SSNMULTI,PHONE input types
	 * 
	 * @param newSeparator
	 */
	public void setSeparator(java.lang.String newSeparator) {
		_separator = newSeparator;
	}

	/**
	 * Set the tag's size attribute
	 */
	public void setSize(String size) {
		_size = size;
	}

	/**
	 * Set the tag's src attribute
	 */
	public void setSrc(String src) {
		_src = src;
	}

	/**
	 * Set the tag's textColor attribute
	 */
	public void setTextcolor(String textColor) {
		_textColor = textColor;
	}

	/**
	 * Set the tag's textlocalekey attribute
	 */
	public void setTextlocalekey(String val) {
		_textlocalekey = val;
	}

	/**
	 * Set the tag's theme attribute
	 */
	public void setTheme(String theme) {
		_theme = theme;
	}

	/**
	 * Set the tag's topLeftBorderColor attribute
	 */
	public void setTopleftbordercolor(String topLeftBorderColor) {
		_topLeftBorderColor = topLeftBorderColor;
	}

	/**
	 * Sets the hour component,to skip the hours after this time.
	 * 
	 * @param newTotime
	 *            java.lang.String
	 */
	public void setTotime(java.lang.String newTotime) {
		_fTotime = newTotime;
	}

	/**
	 * Set the tag's transparentColor attribute
	 */
	public void setTransparentcolor(String transparentColor) {
		_transparentColor = transparentColor;
	}

	/**
	 * Set the tag's type attribute
	 */

	public void setType(String type) {
		_type = type;
	}

	/**
	 * Set the tag's urllocalekey attribute
	 */
	public void setUrllocalekey(String val) {
		_urllocalekey = val;
	}

	/**
	 * Set the tag's value attribute
	 */

	public void setValue(String value) {
		_value = value;
	}

	/**
	 * sets the Vspace
	 */
	public void setVspace(String vSpace) {
		_vspace = vSpace;
	}

	/**
	 * Set the tag's wrap attribute
	 */

	public void setWidth(String val) {
		_width = val;
	}

	/**
	 * Set the tag's wrap attribute
	 */

	public void setWrap(String wrap) {
		_wrap = wrap;
	}

	/**
	 * sets the autotab attribute
	 */
	public void setAutotab(String val) {
		_autoTab = val;
	}

	/**
	 * @param string
	 */
	public void setAccesskey(String string) {
		_accessKey = string;
	}

	/**
	 * @param string
	 */
	public void setReadonly(String string) {
		_readOnly = string;
	}

	/**
	 * @param string
	 */
	public void setTabindex(String string) {
		_tabIndex = string;
	}

	public void setOnmouseout(String string) {
		_onmouseout = string;
	}

	/**
	 * @param string
	 */
	public void setOnmouseover(String string) {
		_onmouseover = string;
	}

	/**
	 * @param string
	 */
	public void setListtype(String string) {
		_listType = string;
	}

	/**
	 * @return
	 */
	public String getStyle() {
		return _style;
	}

	/**
	 * @param string
	 */
	public void setStyle(String string) {
		_style = string;
	}

	public void setOnkeyup(String string) {
		_onKeyUp = string;
	}

	/**
	 * @param string
	 *            The _title to set.
	 */
	public void setTitle(String string) {
		this._title = string;
	}
}