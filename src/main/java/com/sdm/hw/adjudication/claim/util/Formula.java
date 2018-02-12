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

import com.sdm.hw.adjudication.workflow.util.helper.ExpressionTree;
import com.sdm.hw.common.dto.Language;
import com.sdm.hw.common.util.MultilingualString;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc

/**
 * The Class Formula.
 */
public class Formula implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8062036829453085183L;

	//Fixed violation - "static final" arrays should be "private" - Ashpreet - 30/11/2015	
	//Removed EMPTY_STRING_ARRAY array in this class as is is not used in this class

	/** The name. */
	protected MultilingualString name = new MultilingualString();
	
	/** The description. */
	protected String description=null;
	
	/** The type. */
	protected String type=null;
	
	/** The max price. */
	protected BigDecimal maxPrice;
	
	/** The min price. */
	protected BigDecimal minPrice;
	
	/** The id. */
	protected Long id;
	
	/** The expression trees. */
	protected List<ExpressionTree> expressionTrees=new ArrayList<ExpressionTree>();
	
	/**
	 * Instantiates a new formula.
	 */
	public Formula() {
		super();
	}

	/**
	 * copy Constructor.
	 * 
	 * All the properties of the copy point to the same instance as the properties
	 * in the original EXCEPT for the ArrayList of ExpressionTrees. A new ArrayList
	 * is created for that. (The contents of the new Arraylist point to the same
	 * instances as the contents of the original Arraylist.)
	 *
	 * @param original the original
	 */
	public Formula(final Formula original) {
		this.id = original.id;
		this.name.putAll(original.name);
		this.description = original.description;
		this.type = original.type;
		this.maxPrice = original.maxPrice;
		this.minPrice = original.minPrice;

		//Create a new arraylist to hold the expressionTrees
		this.expressionTrees = new ArrayList<ExpressionTree>();
		//populate the new Arraylist with the contents of the old.
		for (int i = 0; i < original.expressionTrees.size(); i++) {
			this.expressionTrees.add(i, original.expressionTrees.get(i));
		}
	}
	
	/**
	 * Inits the from xml.
	 *
	 * @param xml the xml
	 * @throws JDOMException the jDOM exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void initFromXml(final String xml) throws JDOMException, IOException {
		SAXBuilder formulaXmlParser = new SAXBuilder();
		Document formulaXmlDocument = formulaXmlParser.build(new StringReader(xml));

		initFromXmlElement(formulaXmlDocument.getRootElement());
	}

	/**
	 * creates this object when given an xml representation.
	 *
	 * @param elm the elm
	 */
	protected void initFromXmlElement(final Element elm)
	{
		setNameEn(JdomHelper.parseString(elm.getAttributeValue("name")));
		setDescription(JdomHelper.parseString(elm.getAttributeValue("description")));
		setType(JdomHelper.parseString(elm.getAttributeValue("type")));
		setMinPrice(JdomHelper.parseBigDecimal(elm.getAttributeValue("minPrice")));
		setMaxPrice(JdomHelper.parseBigDecimal(elm.getAttributeValue("maxPrice")));

		Element eExpressionTrees = elm.getChild("ExpressionTrees");
		if(eExpressionTrees != null)
		{
			List<Element> trees = eExpressionTrees.getChildren("ExpressionTree");
			Element eExpressionTree;
			ExpressionTree et;
			for(int i=0; i < trees.size(); i++)
			{
				eExpressionTree = trees.get(i);
				et = new ExpressionTree();
				et.initFromXmlElement(eExpressionTree);
				expressionTrees.add(et);
			}
		}
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public MultilingualString getName() {
		return this.name;
	}

	/**
	 * Gets the name en.
	 *
	 * @return the name en
	 */
	public String getNameEn() {
		return getName().get(Language.ENGLISH);
	}

	/**
	 * Sets the name en.
	 *
	 * @param nameEn the new name en
	 */
	public void setNameEn(final String nameEn) {
		getName().put(Language.ENGLISH, nameEn);
	}

	/**
	 * Gets the name fr.
	 *
	 * @return the name fr
	 */
	public String getNameFr() {
		return getName().get(Language.FRENCH);
	}

	/**
	 * Sets the name fr.
	 *
	 * @param nameFr the new name fr
	 */
	public void setNameFr(final String nameFr) {
		getName().put(Language.FRENCH, nameFr);
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
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * Sets the max price.
	 *
	 * @param maxPrice the new max price
	 */
	public void setMaxPrice(final BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

	/**
	 * Gets the max price.
	 *
	 * @return the max price
	 */
	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	/**
	 * Sets the min price.
	 *
	 * @param minPrice the new min price
	 */
	public void setMinPrice(final BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	/**
	 * Gets the min price.
	 *
	 * @return the min price
	 */
	public BigDecimal getMinPrice() {
		return minPrice;
	}

	/**
	 * Gets the expression trees.
	 *
	 * @return the expression trees
	 */
	public List<ExpressionTree> getExpressionTrees() {
		return expressionTrees;
	}

	/**
	 * tells whether this formula contains an {@link ExpressionTree} of a given
	 * name.
	 *
	 * @param name the name
	 * @return true, if successful
	 */
	public boolean containsExpressionTree(final String name)
	{
		return (getExpressionTree(name) != null);
	}

	/**
	 * returns an {@link ExpressionTree} if one exists with the given name.
	 *
	 * @param name the name
	 * @return null if the formula does not the contain the requested
	 * {@link ExpressionTree}
	 */
	public ExpressionTree getExpressionTree(final String name)
	{
		ExpressionTree et;
		for(int i=0; i < expressionTrees.size(); i++)
		{
			et = expressionTrees.get(i);
			if(et.getName().equalsIgnoreCase(name))
			{
				return et;
			}
		}

		return null;
	}

	/**
	 * removes an {@link ExpressionTree} from the Formula.
	 *
	 * @param name the name
	 * @return true if the ExpressionTree was found and removed
	 * false if the ExpressionTree was not found.
	 */
	public boolean removeExpressionTree(final String name)
	{
		ExpressionTree et;
		for(int i=0; i < expressionTrees.size(); i++)
		{
			et = expressionTrees.get(i);
			if(et.getName().equalsIgnoreCase(name))
			{
				expressionTrees.remove(i);
				return true;
			}
		}

		return false;
	}

	/**
	 * returns an {@link ExpressionTree} by index.
	 *
	 * @param index the index
	 * @return the expression tree
	 */
	public ExpressionTree getExpressionTree(final int index)
	{
		return expressionTrees.get(index);
	}

	/**
	 * returns the index of a given {@link ExpressionTree}.
	 *
	 * @param name the name
	 * @return -1 if the {@link ExpressionTree} does not exist in this formula.
	 */
	public int getExpressionTreeIndex(final String name)
	{
		ExpressionTree et;
		int index = -1;
		for(int i=0; i < expressionTrees.size(); i++)
		{
			et = expressionTrees.get(i);
			if(et.getName().equalsIgnoreCase(name))
			{
				index = i;
				break;
			}
		}
		return index;
	}

	/**
	 * inserts an {@link ExpressionTree} at a specific index.
	 *
	 * @param index the index
	 * @param et the et
	 */
	public void setExpressionTreeAt(final int index, final ExpressionTree et)
	{
		expressionTrees.set(index,et);
	}

	/**
	 * removes an {@link ExpressionTree} from the formula.
	 *
	 * @param index the index
	 */
	public void removeExpressionTreeAt(final int index)
	{
		if(index >=1 && index < expressionTrees.size())
		{
			expressionTrees.remove(index);
		}
	}

	/**
	 * appends an {@link ExpressionTree} to the formula.
	 *
	 * @param et the et
	 */
	public void addExpressionTree(final ExpressionTree et)
	{
		expressionTrees.add(et);
	}

	/**
	 * Gives a string version of this object. This implementation will just return
	 * the name of the formula.  This is used by the
	 *
	 * @return the string
	 * {@link com.sdm.client.pricing.node.FormulaNode} when the formula is displayed.
	 */
	public String toString()
	{
		return this.getNameEn();
	}

	/**
	 * Checks for variable.
	 *
	 * @param variableName the variable name
	 * @return true, if successful
	 */
	public boolean hasVariable(final String variableName) {
		boolean hasVariable = false;
		for (ExpressionTree expressionTree : expressionTrees) {
			hasVariable |= expressionTree.getRootRange().hasVariable(variableName);
		}
		return hasVariable;
	}

	/**
	 * Checks for range level.
	 *
	 * @param variableName the variable name
	 * @return true, if successful
	 */
	public boolean hasRangeLevel(final String variableName) {
		for (ExpressionTree expressionTree : expressionTrees) {
			String[] levelNames = expressionTree.getLevelNames();
			for (int j = 0; j < levelNames.length; j++) {
				if (variableName.equals(levelNames[j])) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(final Long id) {
		this.id = id;
	}

}
