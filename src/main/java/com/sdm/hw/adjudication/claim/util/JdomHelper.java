// === File Prolog ===========================================================
// This code was developed for SDM, HealthWatch, Code 520
// for the Next Generation 6.0 (NG) project.
//
// --- Notes -----------------------------------------------------------------
// This class requires JDK version 1.1 or later.
//
// --- Development History ---------------------------------------------------
//
// 10/07/13 TCS
//
// Initial version.
//
// 10/07/13 TCS
//
// Converted class to comply with JavaBeans conventions.
// Now uses serialization to send/receive event objects.
//
// --- Warning ---------------------------------------------------------------
// This software is property of the Shoppers Drug Mart.
// Unauthorized use or duplication of this software is
// strictly prohibited. Authorized users are subject to the following
// restrictions:
// * Neither the author, their corporation, nor SDM is responsible for
// any consequence of the use of this software.
// * The origin of this software must not be misrepresented either by
// explicit claim or by omission.
// * Altered versions of this software must be plainly marked as such.
// * This notice may not be removed or altered.
//
// === End File Prolog =======================================================
package com.sdm.hw.adjudication.claim.util;

import java.math.BigDecimal;

// TODO: Auto-generated Javadoc

/**
 * This object contains static methods that provide services for working with
 * JDOM.
 * 
 * @author J.Buller
 */
public class JdomHelper
{

    /**
     * An empty string will represent <code>null</code> in xml.
     */
    private static final String NULL_TOKEN = "";

    /**
     * changes a numeric value from an xml file into a BigDecimal. Can handle
     *
     * @param attributeValue the attribute value
     * @return the big decimal
     * {@link JdomHelper#NULL_TOKEN}.
     * {@link JdomHelper#NULL_TOKEN}
     */
    public static BigDecimal parseBigDecimal(final String attributeValue)
    {
        return JdomHelper.isAttributeNull(attributeValue) ? null
                : new BigDecimal(attributeValue);
    }

    /**
     * changes a text value from an xml file into a String. Can handle
     *
     * @param attributeValue the attribute value
     * @return the string
     * {@link JdomHelper#NULL_TOKEN}.
     * {@link JdomHelper#NULL_TOKEN}
     */
    public static String parseString(final String attributeValue)
    {
        return JdomHelper.isAttributeNull(attributeValue) ? null
                : attributeValue;
    }

    /**
     * checks whether a String from an xml file is the.
     *
     * @param attributeValue the attribute value
     * @return true, if is attribute null
     * {@link JdomHelper#NULL_TOKEN}
     */
    private static boolean isAttributeNull(final String attributeValue)
    {
        return (attributeValue == null)
                || attributeValue.equals(NULL_TOKEN);
    }
}
