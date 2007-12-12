package com.salmonllc.html;

/////////////////////////
//$Archive: /SOFIA/SourceCode/com/salmonllc/html/HtmlStateComponent.java $
//$Author: Dan $
//$Revision: 17 $
//$Modtime: 3/19/04 11:43a $
/////////////////////////

import com.salmonllc.html.events.ValueChangedEvent;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;

/**
 * Implements a drop down list that allows the user to select US states, Canadian Provinces or countries. The name "state" is historical; it oringally only supported US states.
 */
public class HtmlStateComponent extends HtmlFormComponent
{
	// This will become a J2SE 1.5 Enum sometime around the year 2010 due to backwards compatibility.
	protected static final int LIST_STATES = 0 ;
	// This will become a J2SE 1.5 Enum sometime around the year 2010 due to backwards compatibility.
	protected static final int LIST_PROVINCES = 1 ;
	// This will become a J2SE 1.5 Enum sometime around the year 2010 due to backwards compatibility.
	protected static final int LIST_STATEPROV = 2 ;
	// This will become a J2SE 1.5 Enum sometime around the year 2010 due to backwards compatibility.
	protected static final int LIST_PROVSTATE = 3 ;
	// This will become a J2SE 1.5 Enum sometime around the year 2010 due to backwards compatibility.
	protected static final int LIST_CANUSA = 4 ;
	// This will become a J2SE 1.5 Enum sometime around the year 2010 due to backwards compatibility.
	protected static final int LIST_COUNTRIES = 5 ;
	protected int listType = LIST_STATES;
	// Must stay in synch with the above int values.
	protected final static String[] listTypes = { "states", "provinces", "statesprovinces", "provincesstates", "canusa", "countries" };
	public static final String DEFAULT_LIST_TYPE = listTypes[0];
	private Integer _tabIndex;
    protected HtmlDropDownList _state;
    
    /** The US States */
    protected  static String[][] STATES = {
    		{ "AL", "Alabama" }, {
            "AK", "Alaska" }, {
            "AZ", "Arizona" }, {
            "AR", "Arkansas" }, {
            "CA", "California" }, {
            "CO", "Colorado" }, {
            "CT", "Connecticut" }, {
            "DE", "Delaware" }, {
            "DC", "District of Columbia" }, {
            "FL", "Florida" }, {
            "GA", "Georgia" }, {
            "HI", "Hawaii" }, {
            "ID", "Idaho" }, {
            "IL", "Illinois" }, {
            "IN", "Indiana" }, {
            "IA", "Iowa" }, {
            "KS", "Kansas" }, {
            "KY", "Kentucky" }, {
            "LA", "Louisiana" }, {
            "ME", "Maine" }, {
            "MD", "Maryland" }, {
            "MA", "Massachusetts" }, {
            "MI", "Michigan" }, {
            "MN", "Minnesota" }, {
            "MS", "Mississippi" }, {
            "MO", "Missouri" }, {
            "MT", "Montana" }, {
            "NE", "Nebraska" }, {
            "NV", "Nevada" }, {
            "NH", "New Hampshire" }, {
            "NJ", "New Jersey" }, {
            "NM", "New Mexico" }, {
            "NY", "New York" }, {
            "NC", "North Carolina" }, {
            "ND", "North Dakota" }, {
            "OH", "Ohio" }, {
            "OK", "Oklahoma" }, {
            "OR", "Oregon" }, {
            "PA", "Pennsylvania" }, {
            "RI", "Rhode Island" }, {
            "SC", "South Carolina" }, {
            "SD", "South Dakota" }, {
            "TN", "Tennessee" }, {
            "TX", "Texas" }, {
            "UT", "Utah" }, {
            "VT", "Vermont" }, {
            "VA", "Virginia" }, {
            "WA", "Washington" }, {
            "WV", "West Virginia" }, {
            "WI", "Wisconsin" }, {
            "WY", "Wyoming" }
    };
    // Optimization: geneate this statically as it is likely the most common one overall.
    protected final static String[] STATECODES = getSortedCodes(STATES);
    
    /**The Canadian Provinces.
     *  REFERENCE: gov't-run post office site  http://www.canadapost.ca/tools/pcl/bin/default-e.asp,
     */
    protected  static String[][] PROVINCES = { 
    		{ "AB", "Alberta" },
			{ "BC", "British Columbia" },
			{ "MB", "Manitoba"},
			{ "NB", "New Brunswick" },
			{ "NL", "Newfoundland and Labrador" },
			{ "NS", "Nova Scotia" },
			{ "NT", "Northwest Territories" },
			{ "NU", "Nunavut"},
			{ "ON", "Ontario"},
			{ "PE", "Prince Edward Island" },
			{ "QC", "Quebec"},
			{ "SK", "Saskatchewan" },
			{ "YK", "Yukon" },
    };
    
    /**The countries, with their 2-letter ISO country codes, which are lower case */
    protected  static String[][] COUNTRIES = {
    		// These first two are duplicates for the obvious optimization that
    		// most SOFIA users will be in North America initially. -- ian
			{ "us", "U.S.A." },
			{ "ca", "Canada" },
			{ "al", "Albania" },
			{ "dz", "Algeria" },
			{ "as", "American Samoa" },
			{ "ad", "Andorra" },
			{ "ao", "Angola" },
			{ "ai", "Anguilla" },
			{ "ag", "Antigua" },
			{ "ar", "Argentina" },
			{ "am", "Armenia" },
			{ "aw", "Aruba" },
			{ "au", "Australia" },
			{ "at", "Austria" },
			{ "az", "Azerbaijan" },
			{ "bs", "Bahamas" },
			{ "bh", "Bahrain" },
			{ "bd", "Bangladesh" },
			{ "bb", "Barbados" },
			{ "ag", "Barbuda" },
			{ "by", "Belarus" },
			{ "be", "Belgium" },
			{ "bz", "Belize" },
			{ "bj", "Benin" },
			{ "bm", "Bermuda" },
			{ "bt", "Bhutan" },
			{ "bo", "Bolivia" },
			{ "an", "Bonaire" },
			{ "bw", "Botswana" },
			{ "br", "Brazil" },
			{ "vg", "British Virgin Islands" },
			{ "bn", "Brunei" },
			{ "bg", "Bulgaria" },
			{ "bf", "Burkina Faso" },
			{ "bi", "Burundi" },
			{ "kh", "Cambodia" },
			{ "cm", "Cameroon" },
			{ "ca", "Canada" },
			{ "cv", "Cape Verde" },
			{ "ky", "Cayman Islands" },
			{ "cf", "Central African Republic" },
			{ "td", "Chad" },
			{ "cs", "Channel Islands" },
			{ "cl", "Chile" },
			{ "cn", "China" },
			{ "co", "Colombia" },
			{ "cg", "Congo-Brazzaville" },
			{ "zr", "Congo-Kinshasa" },
			{ "ck", "Cook Islands" },
			{ "cr", "Costa Rica" },
			{ "hr", "Croatia" },
			{ "cu", "Cuba" },
			{ "an", "Curacao" },
			{ "cy", "Cyprus" },
			{ "cz", "Czech Republic" },
			{ "dk", "Denmark" },
			{ "dj", "Djibouti" },
			{ "dm", "Dominica" },
			{ "do", "Dominican Republic" },
			{ "ec", "Ecuador" },
			{ "eg", "Egypt" },
			{ "sv", "El Salvador" },
			{ "gq", "Equatorial Guinea" },
			{ "er", "Eritrea" },
			{ "ee", "Estonia" },
			{ "et", "Ethiopia" },
			{ "fo", "Faroe Islands" },
			{ "fj", "Fiji" },
			{ "fi", "Finland" },
			{ "fr", "France" },
			{ "gf", "French Guiana" },
			{ "pf", "French Polynesia" },
			{ "ga", "Gabon" },
			{ "gm", "Gambia" },
			{ "ge", "Georgia" },
			{ "de", "Germany" },
			{ "gh", "Ghana" },
			{ "gi", "Gibraltar" },
			{ "gr", "Greece" },
			{ "gl", "Greenland" },
			{ "gd", "Grenada" },
			{ "gp", "Guadeloupe" },
			{ "gu", "Guam" },
			{ "gt", "Guatemala" },
			{ "gw", "Guinea Bissau" },
			{ "gn", "Guinea" },
			{ "gy", "Guyana" },
			{ "ht", "Haiti" },
			{ "hn", "Honduras" },
			{ "hk", "Hong Kong" },
			{ "hu", "Hungary" },
			{ "is", "Iceland" },
			{ "in", "India" },
			{ "id", "Indonesia" },
			{ "ir", "Iran" },
			{ "iq", "Iraq" },
			{ "ie", "Ireland" },
			{ "il", "Israel" },
			{ "it", "Italy" },
			{ "ci", "Ivory Coast" },
			{ "jm", "Jamaica" },
			{ "jp", "Japan" },
			{ "jo", "Jordan" },
			{ "kz", "Kazakhstan" },
			{ "ke", "Kenya" },
			{ "kw", "Kuwait" },
			{ "kg", "Kyrgyzstan" },
			{ "la", "Laos" },
			{ "lv", "Latvia" },
			{ "lb", "Lebanon" },
			{ "ls", "Lesotho" },
			{ "lr", "Liberia" },
			{ "ly", "Libya" },
			{ "li", "Liechtenstein" },
			{ "lt", "Lithuania" },
			{ "lu", "Luxembourg" },
			{ "mo", "Macau" },
			{ "mk", "Macedonia" },
			{ "mg", "Madagascar" },
			{ "mw", "Malawi" },
			{ "my", "Malaysia" },
			{ "ml", "Mali" },
			{ "mt", "Malta" },
			{ "mh", "Marshall Islands" },
			{ "mq", "Martinique" },
			{ "mr", "Mauritania" },
			{ "mu", "Mauritius" },
			{ "mx", "Mexico" },
			{ "fm", "Micronesia" },
			{ "md", "Moldova" },
			{ "mc", "Monaco" },
			{ "mn", "Mongolia" },
			{ "ms", "Montserrat" },
			{ "ma", "Morocco" },
			{ "mz", "Mozambique" },
			{ "mm", "MyanmarBurma" },
			{ "na", "Namibia" },
			{ "np", "Nepal" },
			{ "an", "Netherlands Antilles" },
			{ "nl", "Netherlands" },
			{ "nc", "New Caledonia" },
			{ "nz", "New Zealand" },
			{ "ni", "Nicaragua" },
			{ "ne", "Niger" },
			{ "ng", "Nigeria" },
			{ "no", "Norway" },
			{ "om", "Oman" },
			{ "pk", "Pakistan" },
			{ "pw", "Palau" },
			{ "pa", "Panama" },
			{ "pg", "Papua New Guinea" },
			{ "py", "Paraguay" },
			{ "pe", "Peru" },
			{ "ph", "Philippines" },
			{ "pl", "Poland" },
			{ "pt", "Portugal" },
			{ "pr", "Puerto Rico" },
			{ "qa", "Qatar" },
			{ "re", "Reunion" },
			{ "ro", "Romania" },
			{ "ru", "Russia" },
			{ "rw", "Rwanda" },
			{ "an", "Saba" },
			{ "mp", "Saipan" },
			{ "sm", "San Marino" },
			{ "sa", "Saudi Arabia" },
			{ "sn", "Senegal" },
			{ "sc", "Seychelles" },
			{ "sl", "Sierra Leone" },
			{ "sg", "Singapore" },
			{ "sk", "Slovak Republic" },
			{ "si", "Slovenia" },
			{ "so", "Somalia" },
			{ "za", "South Africa" },
			{ "kr", "South Korea" },
			{ "es", "Spain" },
			{ "lk", "Sri Lanka" },
			{ "gp_EN", "St. Barthelemy" },
			{ "vi", "St. Croix" },
			{ "an", "St. Eustatius" },
			{ "kn", "St. Kitts and Nevis" },
			{ "lc", "St. Lucia" },
			{ "an", "St. Maarten/St. Martin" },
			{ "vi", "St. Thomas" },
			{ "vc", "St. Vincent" },
			{ "sd", "Sudan" },
			{ "sr", "Suriname" },
			{ "sz", "Swaziland" },
			{ "se", "Sweden" },
			{ "ch", "Switzerland" },
			{ "sy", "Syria" },
			{ "tw", "Taiwan" },
			{ "tz", "Tanzania" },
			{ "th", "Thailand" },
			{ "tg", "Togo" },
			{ "vg", "Tortola" },
			{ "tt", "Trinidad and Tobago" },
			{ "tn", "Tunisia" },
			{ "tr", "Turkey" },
			{ "tm", "Turkmenistan" },
			{ "tc", "Turks and Caicos Islands" },
			{ "vi", "U.S. Virgin Islands" },
			{ "ug", "Uganda" },
			{ "ua", "Ukraine" },
			{ "ae", "United Arab Emirates" },
			{ "gb", "United Kingdom" },
			{ "uy", "Uruguay" },
			{ "us", "U.S.A." },
			{ "uz", "Uzbekistan" },
			{ "vu", "Vanuatu" },
			{ "va", "Vatican City" },
			{ "ve", "Venezuela" },
			{ "vn", "Vietnam" },
			{ "wf", "Wallis &amp; Futuna" },
			{ "ye", "Yemen" },
			{ "zm", "Zambia" },
			{ "zw", "Zimbabwe" },
		};
    
    /**
     * StateComponent constructor, using default list type.
     *
     * @param name		Name of component.
     * @param p			Page containing component.
     * @param codesonly	indicates whether to display codesonly on full state names.
     */
    public HtmlStateComponent(String name, HtmlPage page, boolean codesOnly) {  
    	this(name, page, DEFAULT_LIST_TYPE, codesOnly);
    }
    
    /**
     * StateComponent constructor specifying list type.
     *
     * @param name		Name of component.
     * @param p			Page containing component.
     * @param codesonly	indicates whether to display codesonly on full state names.
     */
    public HtmlStateComponent(String name, HtmlPage p, String listTypeName, boolean codesonly) {
        super(name, p);
        _state = new HtmlDropDownList(name, p);
        _state.addOption(" ", " ");
        if (listTypeName == null){
        	// Nothing to do, take default.
        } else {
	        for (int i = 0; i <listTypes.length; i++){
	        	if (listTypeName.equalsIgnoreCase(listTypes[i])){
	        		listType = i;
	        		break;
	        	}
	        }
        }
        String[][] data = STATES;
        String[] codes = STATECODES;
        switch(listType) {
        	case LIST_STATES:
        		data = STATES;
        		codes = STATECODES;
        		break;      		
        	// Use Lazy Evaluation of codes lists for the remaining listtypes.
        	case LIST_PROVINCES:
        			data = PROVINCES;
        			codes = getSortedCodes(data);
        			break;
        	// STATEPROV and PROVSTATE are a bit more work; catenate the lists but do not sort the names
        	case LIST_STATEPROV:
        		data = a2dCat(STATES, PROVINCES, false);
        		codes = a1dCat(getSortedCodes(STATES), getSortedCodes(PROVINCES));
        		break;
        	case LIST_PROVSTATE:
        		data = a2dCat(STATES, PROVINCES, false);
        		codes = a1dCat(getSortedCodes(PROVINCES), getSortedCodes(STATES));
        		break;
        	// SORTED is a bit more work too; catenate AND sort the lists
        	case LIST_CANUSA:
        		data = a2dCat(STATES, PROVINCES, true);
        		codes = getSortedCodes(data);
        		break;
        	// COUNTRIES is relatively easy
        	case LIST_COUNTRIES:
        		data = COUNTRIES;
        		codes = getSortedCodes(COUNTRIES);
        		codesonly = false;		// Country Codes only is nonsensical
        		break;
        }
        
        // Now we finally have the data, add it to the component..
        if (!codesonly) {
            for (int i = 0; i < data.length; i++) {
                _state.addOption(data[i][0], data[i][1]);
            }
        } else {
            for (int i = 0; i < codes.length; i++) {
                _state.addOption(codes[i], codes[i]);
            }
        }
    }
    /** Catenate two arrays into one
	 * @param strings An array of Strings
	 * @param strings2 Another array of Strings
	 * @return The concatenated array.
	 */
	private String[] a1dCat(String[] a1, String[] a2) {
		int newLength = a1.length + a2.length;
		String[] result = new String[newLength];
		System.arraycopy(a1, 0, result, 0, a1.length);
		System.arraycopy(a2, 0, result, a1.length, a2.length);
		return result;
	}

	/** Build a 2D array by merging two arrays.
	 * @param aa1 One of the two arrays.
	 * @param aa2 The other array.
	 * @return The merged array
	 */
	private String[][] a2dCat(String[][] aa1, String[][] aa2, boolean sort) {
		int newLength = aa1.length + aa2.length;
		String[][] result = new String[newLength][];
		System.arraycopy(aa1, 0, result, 0, aa1.length);
		System.arraycopy(aa2, 0, result, aa1.length, aa2.length);
		if (sort) {
			Arrays.sort(result, listComparator);
		}
		return result;
	}
	
	/**Compare entry names in one of the countries list, for sorting. Sorts by name, which is [1], not codes
	 * which would be [0], on each row of the list.
	 */
	static final Comparator listComparator = new Comparator() {
		public int compare(Object o1, Object o2) {
			String s1 = ((String[])o1)[1];
			String s2 = ((String[])o2)[1];
			return s1.compareTo(s2);
		}
	};

	/** The name says it all. */
	public void generateHTML(java.io.PrintWriter p, int row) throws Exception {
        // It is essential to call getValue() here because there may be a ValueChanged
        // event in the queue for this object, which needs to be removed, which getValue()
        // does.  The secondary calls to getValue() from the container will not
        // do this because there are no events associated with them.
        String val = getValue(row, true);
        if (val == null) {
            _state.setValue(null, row);
        } else {
            _state.setValue(val);
        }
        _state.generateHTML(p, row);
        if (_visible && _enabled)
            p.println("");
    }
    /**
     * Returns the sub-component to be used for setting focus.
     * @return com.salmonllc.html.HtmlComponent
     */
    public HtmlComponent getFocus() {
        return _state;
    }
    
    /**Get the sorted list of codes from one of the country lists */
    private final static String[] getSortedCodes(String[][] list) {
        String[] codes = new String[list.length];
        for (int i = 0; i<list.length; i++) {
            codes[i]  = list[i][0];
        }
        Arrays.sort(codes);
        return codes;
    }
    
    public boolean processParms(Hashtable parms, int rowNo) throws Exception {

        if (!getVisible() || !getEnabled())
            return false;

        // Determine the old value from both edit fields.

        String oldValue;
        if (_dsBuff == null) {
            oldValue = _state.getValue();
        } else {
            String s=null;
            if (rowNo > -1)
                s = (String) _dsBuff.getString(rowNo, _dsColNo);
            else if (_dsBuff.getRow() != -1)
                s = (String) _dsBuff.getString(_dsColNo);
            if (s == null)
                oldValue = null;
            else
                oldValue = s;
        }

        // Determine the new value from both edit fields.

        String newValue;
        String name1 = _state.getFullName();
        if (rowNo > -1)
            name1 += "_" + rowNo;
        String val[] = (String[]) parms.get(name1);
        if (val != null) {
            newValue = val[0].trim();
            if (newValue.equals(""))
                newValue = null;
        } else
            newValue = null;


        // Compare old and new values and possibly create a ValueChangedEvent.
        if (!valuesEqual(oldValue, newValue)) {
            ValueChangedEvent e = new ValueChangedEvent(getPage(), this, getName(), getFullName(), oldValue, newValue, rowNo, _dsColNo, _dsBuff);
            addEvent(e);
        }
        return false;
    }
    
    /**
     * This method will clear all pending events from the event queue for this component.
     */
    public void reset() {
        super.reset();
        _state.reset();
    }
    /**
     * Specifies the Style Class to be used for the State Component.
     * Creation date: (7/19/01 8:41:20 AM)
     * @param sClass java.lang.String A name of a class in Html to be used by this component
     */
    public void setClassName(String sClass) {
        super.setClassName(sClass);
        if (sClass != null)
            _state.setClassName(sClass);
    }
    /**
      * Sets the font end tag for disabled mode.
      * @param tag java.lang.String
      */
    public void setDisabledFontEndTag(String tag)
    {
        _state.setDisabledFontEndTag(tag);

    }
    /**
     * Sets the font tag for disabled mode.
     * @param tag java.lang.String
     */
    public void setDisabledFontStartTag(String tag)
    {

        _state.setDisabledFontStartTag(tag);

    }
    /**
     * Enables or disables the ability of this component to respond to user input.
     * @param editable boolean
     */
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        _state.setEnabled(enabled);
    }
    /**
     * Specifies the parent component for this State Component.
     * Creation date: (7/19/01 8:41:20 AM)
     * @param sClass java.lang.String A name of a class in Html to be used by this component
     */
     public void setParent(HtmlComponent parent) {
        super.setParent(parent);
        // This is necessary for the name to be generated correctly, else the leading
        // sequence of parent names will be lost.
        _state.setParent(parent);
    }
    /**
     * This method sets the property theme for the component.
     * @param theme The theme to use.
     */
    public void setTheme(String sTheme) {
        if (sTheme == null)
            return;

        if (_state != null)
            _state.setTheme(sTheme);
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
	 * @param sets the tab index html attribute. You can also pass TAB_INDEX_DEFAULT to use the default tab index for the component or TAB_INDEX_NONE to keep this component from being tabbed to
	 */
	public void setTabIndex(int val) {
		_state.setTabIndex(val + 3);			
			
	}
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		return 0;
	}
}
