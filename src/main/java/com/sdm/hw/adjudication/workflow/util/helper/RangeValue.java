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
import java.math.BigDecimal;

// TODO: Auto-generated Javadoc

/**
 * The Class RangeValue.
 */
public class RangeValue implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8755218979380177720L;
	
	/** The value. */
	private String value = null;
	
	/** The is catchall. */
	private boolean isCatchall = false;
	
	/** The parent range. */
	private Range parentRange = null;
	
	/** The sub range. */
	private Range subRange = null;
	
	/** The value expression. */
	private String valueExpression = null;

	/**
	 * Instantiates a new range value.
	 */
	public RangeValue() {
	}

	/**
	 * Sets the sub range.
	 *
	 * @param subRange the new sub range
	 */
	public void setSubRange(Range subRange) {
		subRange.setRangeValueParent(this);
		this.subRange = subRange;
	}

	/**
	 * Gets the sub range.
	 *
	 * @return the sub range
	 */
	public Range getSubRange() {
		return subRange;
	}

	/**
	 * Sets the parent range.
	 *
	 * @param parentRange the new parent range
	 */
	public void setParentRange(Range parentRange) {
		this.parentRange = parentRange;
	}

	/**
	 * Gets the parent range.
	 *
	 * @return the parent range
	 */
	public Range getParentRange() {
		return parentRange;
	}

	/**
	 * Gets the display value.
	 *
	 * @return the display value
	 */
	public String getDisplayValue() {
		if (isCatchall()) {
			return "*";
		} else {
			return getValue();
		}
	}

	/**
	 * Gets the compute value.
	 *
	 * @return the compute value
	 */
	public String getComputeValue() {
		if (isCatchall()) {
			return String.valueOf(Double.MAX_VALUE); // Sonar Violation fixed by TCS on 9-11-2015
		} else {
			return getValue();
		}
	}

	/**
	 * Gets the numeric value.
	 *
	 * @return the numeric value
	 */
	public BigDecimal getNumericValue() {
		return new BigDecimal(getComputeValue());
	}

	/**
	 * Value in range.
	 *
	 * @param value the value
	 * @return true, if successful
	 */
	public boolean valueInRange(String value) {
		if (isCatchall()) {
			return true;
		}

		if (getType().equals(Range.RANGE_TYPE_NUMERIC)) {
			BigDecimal testValue = new BigDecimal(value);
			BigDecimal currValue = getNumericValue();

			return (testValue.compareTo(currValue) <= 0);
		} else {
			//assume string type
			return value.equals(getDisplayValue());
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getDisplayValue();
	}

	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	public int getLevel() {

		int level = -1;
		if (parentRange != null) {
			level = parentRange.getLevel();
		}
		return level;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		if (parentRange != null) {
			return parentRange.getType();
		} else {
			return null;
		}
	}

	/**
	 * Inits the from xml element.
	 *
	 * @param elm the elm
	 */
	public void initFromXmlElement(Element elm) {
		setValue(elm.getAttributeValue("value"));
		Boolean isCatchAll = Boolean.valueOf(elm.getAttributeValue("catchAll"));
		setIsCatchall(isCatchAll.booleanValue());
		setValueExpression(elm.getAttributeValue("expression"));

		Element eSubRange = elm.getChild("Range");
		if (eSubRange != null) {
			Range subRange = new Range(ExpressionTree.CATCHALL_NONE);
			subRange.initFromXmlElement(eSubRange);
			setSubRange(subRange);
		}

	}

	/**
	 * Checks if is catchall.
	 *
	 * @return true, if is catchall
	 */
	public boolean isCatchall() {
		return isCatchall;
	}

	/**
	 * Sets the checks if is catchall.
	 *
	 * @param isCatchall the new checks if is catchall
	 */
	public void setIsCatchall(boolean isCatchall) {
		this.isCatchall = isCatchall;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Sets the value expression.
	 *
	 * @param valueExpression the new value expression
	 */
	public void setValueExpression(String valueExpression) {
		this.valueExpression = valueExpression;
	}
	
	/**
	 * Gets the value expression.
	 *
	 * @return the value expression
	 */
	public String getValueExpression() {
		return valueExpression;
	}
	
	/**
	 * Checks for variable.
	 *
	 * @param variableName the variable name
	 * @return true, if successful
	 */
	public boolean hasVariable(String variableName) {
		boolean hasVariable =
			(this.valueExpression == null)
				? false
				: this.valueExpression.indexOf(variableName) > -1;
		if (!hasVariable && this.subRange != null) {
			hasVariable = this.subRange.hasVariable(variableName);
		}
		return hasVariable; 
	}
}
