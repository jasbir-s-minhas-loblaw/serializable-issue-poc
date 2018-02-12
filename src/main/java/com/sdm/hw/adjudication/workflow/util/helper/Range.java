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

import com.sdm.hw.common.util.DecimalHelper;
import org.jdom.Element;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc

/**
 * The Class Range.
 */
public class Range implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7182040076148573679L;
	//Range Types
	/** The Constant RANGE_TYPE_NUMERIC. */
	public static final String RANGE_TYPE_NUMERIC = "numeric";
	
	/** The Constant RANGE_TYPE_STRING. */
	public static final String RANGE_TYPE_STRING = "string";

	
	/** The name. */
	private String name = null;
	
	/** The type. */
	private String type = null;
	
	/** The level. */
	private int level = -1;
	
	/** The range values. */
	private List<RangeValue> rangeValues = new ArrayList<RangeValue>();
	
	/** The range value parent. */
	private RangeValue rangeValueParent = null;

	/**
	 * Instantiates a new range.
	 *
	 * @param addCatchall the add catchall
	 */
	public Range(final boolean addCatchall) {

		if (addCatchall) {

			//automatically create a catch-all range value for the new range
			RangeValue rv = new RangeValue();
			rv.setParentRange(this);
			rv.setIsCatchall(true);
			rv.setValue("*");
			addRangeValue(rv);

		}

	}

	/**
	 * Adds the range value.
	 *
	 * @param rv the rv
	 */
	public void addRangeValue(final RangeValue rv) {
		rangeValues.add(rv);
	}

	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Gets the range value count.
	 *
	 * @return the range value count
	 */
	public int getRangeValueCount() {
		return rangeValues.size();
	}

	/**
	 * Append value.
	 *
	 * @param value the value
	 */
	public void appendValue(final RangeValue value) {
		value.setParentRange(this);
		rangeValues.add(rangeValues.size() - 1, value);
	}

	/**
	 * Insert value before.
	 *
	 * @param value the value
	 * @param valueIndex the value index
	 */
	public void insertValueBefore(final RangeValue value, final int valueIndex) {
		value.setParentRange(this);
		rangeValues.add(valueIndex, value);

	}

	/**
	 * Insert value after.
	 *
	 * @param value the value
	 * @param valueIndex the value index
	 */
	public void insertValueAfter(final RangeValue value, final int valueIndex) {
		value.setParentRange(this);
		rangeValues.add(valueIndex + 1, value);

	}

	/**
	 * Removes the range value.
	 *
	 * @param valueIndex the value index
	 */
	public void removeRangeValue(final int valueIndex) {
		if (valueIndex >= 0 && valueIndex < rangeValues.size()) {
			//!TODO
			//make this recursive to handle sub-ranges
			//can't remove catch-all
			rangeValues.remove(valueIndex);
		}
	}

	/**
	 * Gets the range value.
	 *
	 * @param index the index
	 * @return the range value
	 */
	public RangeValue getRangeValue(final int index) {
		if (index < 0 || index >= rangeValues.size()) {
			return null;
		}
		return rangeValues.get(index);
	}

	/**
	 * Gets the range value index.
	 *
	 * @param value the value
	 * @return the range value index
	 */
	public int getRangeValueIndex(final String value) {
		int index = -1;

		RangeValue rv;
		for (int i = 0; i < rangeValues.size(); i++) {
			rv = rangeValues.get(i);
			if (value.equalsIgnoreCase(rv.getDisplayValue())) {
				index = i;
				break;
			}
		}

		return index;
	}

	/**
	 * Can insert before.
	 *
	 * @param rvSelected the rv selected
	 * @param newValue the new value
	 * @return true, if successful
	 */
	public boolean canInsertBefore(final RangeValue rvSelected, final String newValue) {

		int selIndex = getRangeValueIndex(rvSelected.getDisplayValue());

		if (rvSelected.getType().equals(RANGE_TYPE_STRING)) {
			return (getRangeValueIndex(newValue) == -1);
		}

		RangeValue rvPrev = null;
		if (selIndex > 0) {
			//get previous range value
			rvPrev = getRangeValue(selIndex - 1);
		}

		BigDecimal fltNew = new BigDecimal(newValue);
		BigDecimal fltSel = new BigDecimal(rvSelected.getComputeValue());

		boolean result;
		if (rvPrev != null) {
			BigDecimal fltPrev = new BigDecimal(rvPrev.getComputeValue());
			result =
				fltNew.compareTo(DecimalHelper.ZERO) > 0
					&& fltNew.compareTo(fltPrev) > 0
					&& fltNew.compareTo(fltSel) < 0;
		} else {
			result =
				fltNew.compareTo(DecimalHelper.ZERO) > 0 && fltNew.compareTo(fltSel) < 0;
		}
		return result;
	}

	/**
	 * Can insert after.
	 *
	 * @param rvSelected the rv selected
	 * @param newValue the new value
	 * @return true, if successful
	 */
	public boolean canInsertAfter(final RangeValue rvSelected, final String newValue) {

		int selIndex = getRangeValueIndex(rvSelected.getDisplayValue());

		if (rvSelected.isCatchall())
			return false;
		if (rvSelected.getType().equals(RANGE_TYPE_STRING)) {
			return (getRangeValueIndex(newValue) == -1);
		}

		RangeValue rvNext = null;
		if (selIndex < rangeValues.size() - 1) {
			//get next range value
			rvNext = getRangeValue(selIndex + 1);
		}

		BigDecimal fltNew = new BigDecimal(newValue);
		BigDecimal fltSel = new BigDecimal(rvSelected.getComputeValue());

		boolean result;
		if (rvNext != null) {
			BigDecimal fltNext = new BigDecimal(rvNext.getComputeValue());
			result = fltNew.compareTo(fltSel) > 0 && fltNew.compareTo(fltNext) < 0;
		} else {
			result = fltNew.compareTo(fltSel) > 0;
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder ret = new StringBuilder("Range: name = " + name + " type = " + type + " level = " + level);
		ret = ret.append("\n");

		RangeValue rv;
		for (int i = 0; i < rangeValues.size(); i++) {
			rv = rangeValues.get(i);
			ret =ret.append("     ").append(rv).append("\n");
		}
		return ret.toString();
	}

	/**
	 * Inits the from xml element.
	 *
	 * @param elm the elm
	 */
	public void initFromXmlElement(final Element elm) {

		setName(elm.getAttributeValue("name"));
		setType(elm.getAttributeValue("type"));
		setLevel(Integer.parseInt(elm.getAttributeValue("level")));

		//m_rangeValues
		RangeValue rv;
		@SuppressWarnings("unchecked")
		List<Element> rVals = elm.getChildren("RangeValue");
		for (int i = 0; i < rVals.size(); i++) {
			rv = new RangeValue();
			rv.initFromXmlElement(rVals.get(i));
			rv.setParentRange(this);
			addRangeValue(rv);
		}
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
	public void setType(final String type) {
		this.type = type;
	}
	
	/**
	 * Gets the range value parent.
	 *
	 * @return the range value parent
	 */
	public RangeValue getRangeValueParent() {
		return rangeValueParent;
	}
	
	/**
	 * Sets the range value parent.
	 *
	 * @param rangeValueParent the new range value parent
	 */
	public void setRangeValueParent(final RangeValue rangeValueParent) {
		this.rangeValueParent = rangeValueParent;
	}
	
	/**
	 * Gets the range values.
	 *
	 * @return the range values
	 */
	public List<RangeValue> getRangeValues() {
		return rangeValues;
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
	public void setName(final String name) {
		this.name = name;
	}
	
	/**
	 * Sets the level.
	 *
	 * @param level the new level
	 */
	public void setLevel(final int level) {
		this.level = level;
	}

	/**
	 * Checks for variable.
	 *
	 * @param variableName the variable name
	 * @return true, if successful
	 */
	public boolean hasVariable(final String variableName) {
		boolean hasVariable = false;
		for (RangeValue value : rangeValues) {
			hasVariable |= value.hasVariable(variableName);
		}
		return hasVariable;
	}
}
