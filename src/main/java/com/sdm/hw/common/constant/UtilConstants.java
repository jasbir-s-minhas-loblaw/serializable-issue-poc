/*
 * All rights reserved.
 */
package com.sdm.hw.common.constant;

/**
 * The Class UtilConstants.
 */
public class UtilConstants
{

    /** The Constant ZERO. */
    public static final String ZERO = "0";

    /** The Constant ONE. */
    public static final String ONE = "1";

    /** The Constant TWO. */
    public static final String TWO = "2";

    /** The Constant Four. */
    public static final String FOUR = "4";

    /** The Constant NULL. */
    public static final Object NULL = null;

    /** The Constant FALSE. */
    public static final boolean FALSE = false;

    /** The Constant COMMA. */
    public static final String COMMA = ",";

    /** The empty string. */
    private static String EMPTY_STRING = "";//Sonar fix S1444 on 23 Jan 2017 by TCS.

    /** The Constant SPACE. */
    public static final String SPACE = " ";

    /** The Constant PATH_SEPARATOR_DOT. */
    public static final String PATH_SEPARATOR_DOT = ".";

    /** The Constant PATH_SEPARATOR_SLASHDOT. */
    public static final String PATH_SEPARATOR_SLASHDOT = "\\.";

    /** The Constant NEW_LINE. */
    public static final String NEW_LINE = "\n";

    /**
     * Instantiates a new util constants.
     */
    public UtilConstants()
    {
        //
    }

	/**
	 * @return the eMPTY_STRING
	 *///Sonar Fix S1444 on 23 Jan 2017 by TCS
	public static String getEMPTY_STRING() {
		return EMPTY_STRING;
	}

	/**
	 * @param eMPTY_STRING the eMPTY_STRING to set
	 *///Sonar Fix S1444 on 23 Jan 2017 by TCS
	public static void setEMPTY_STRING(String eMPTY_STRING) {
		UtilConstants.EMPTY_STRING = eMPTY_STRING;//Sonar Fix S2444 on 24 jan 17 by Khushboo
	}

}
