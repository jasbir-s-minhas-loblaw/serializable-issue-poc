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
package com.sdm.hw.adjudication.workflow.util.helper;

import org.jdom.Element;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc

/**
 * The Class RangeLevel.
 */
public class RangeLevel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1586691579677961271L;
	
	/** The name. */
	private String name = null;
	
	/** The type. */
	private String type = null;
	
	/** The string codes. */
	private List<String> stringCodes = new ArrayList<String>();
	
	/** The expression tree. */
	private ExpressionTree expressionTree = null;

	/**
	 * Instantiates a new range level.
	 */
	public RangeLevel() {}

	/**
	 * Adds the string code.
	 *
	 * @param codeName the code name
	 */
	public void addStringCode(String codeName) {
		stringCodes.add(codeName);
	}

	/**
	 * Inits the from xml element.
	 *
	 * @param elm the elm
	 */
	public void initFromXmlElement(Element elm) {
		setName(elm.getAttributeValue("name"));
		setType(elm.getAttributeValue("type"));

		Element eCodes = elm.getChild("Codes");
		if (eCodes != null) {
			@SuppressWarnings("unchecked")
			List<Element> codes = eCodes.getChildren("Code");
			Element eCode;
			for (int i = 0; i < codes.size(); i++) {
				eCode = codes.get(i);
				stringCodes.add(eCode.getAttributeValue("value"));
			}
		}

	}
	
	/**
	 * Gets the expression tree.
	 *
	 * @return the expression tree
	 */
	public ExpressionTree getExpressionTree() {
		return expressionTree;
	}
	
	/**
	 * Sets the expression tree.
	 *
	 * @param expressionTree the new expression tree
	 */
	public void setExpressionTree(ExpressionTree expressionTree) {
		this.expressionTree = expressionTree;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the string codes.
	 *
	 * @return the string codes
	 */
	public List<String> getStringCodes() {
		return stringCodes;
	}
	
	/**
	 * Sets the string codes.
	 *
	 * @param stringCodes the new string codes
	 */
	public void setStringCodes(List<String> stringCodes) {
		this.stringCodes = stringCodes;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

}
