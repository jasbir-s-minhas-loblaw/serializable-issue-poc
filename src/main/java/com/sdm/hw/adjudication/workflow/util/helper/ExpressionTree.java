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

import com.sdm.hw.common.util.MoneyHelper;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


// TODO: Auto-generated Javadoc

/**
 * represents a 'table' or a 'component' of a Formula. Example: Cost table, Fee table,
 * Markup table...
 *
 * @author J.Buller
 * @author J.Gibbes
 *
 * v1.1 J.Gibbes - bugfix in findExp. This object seems to work with inverted
 *                  RangeLevel.level. There was a spot where this was not being done.
 *
 */
public class ExpressionTree implements Serializable
{
	
	/** The Constant serialVersionUID. */
	static final long serialVersionUID = -6885981061047562294L;

	/** The Constant RANGE_TYPE_EXPRESSION. */
	public static final String RANGE_TYPE_EXPRESSION = "EXPRESSION";
	
	/** The Constant CATCHALL_ADD. */
	public static final boolean CATCHALL_ADD = true;
	
	/** The Constant CATCHALL_NONE. */
	public static final boolean CATCHALL_NONE = false;

	/** The name. */
	private String name=null;
	
	/** The type. */
	private String type=null;
	
	/** The range levels. */
	private List<RangeLevel> rangeLevels=new ArrayList<RangeLevel>();

	/** a list of names of the variables that may be used in the math expressions in this tree. */
	private List<String> expressionVariables=new ArrayList<String>();
	
	/** The root range. */
	private Range rootRange=null;

	/**
	 * Basic constructor.
	 */
	public ExpressionTree()
	{
		/** @todo  ranges and expression variables must be specified when the tree is
		//        constructed, otherwise assumptions about levels don't work
		*/
	}

	/**
	 * Inits the.
	 */
	public void init() {
		if (rangeLevels == null) {
			throw new IllegalStateException("no range values defined, can't initialize expression tree");
		}

		//create an empty range tree consisting of catch-all for each level
		RangeLevel rTopLevel = getRangeLevel(getLevelCount()-1);

		rootRange = new Range(CATCHALL_ADD);
		rootRange.setName(rTopLevel.getName());
		rootRange.setType(rTopLevel.getType());
		rootRange.setLevel(getLevelCount() -1);

		//get auto-created catch-all range value
		RangeValue rv = rootRange.getRangeValue(0);
		completeRangeTree(rv);
	}

	/**
	 * returns a requested RangeLevel according it's position within the tree.
	 * A RangeLevel is the representation of a column in a pricing table. The levels
	 * are counted left-to-right starting from 0. If you think of the ExpressionTree
	 * as a table, you can think of the columns starting at 0 from left-to-right.
	 *
	 * If we have a Cost table with these columns:
	 *
	 * QTY - BASIC_COST - COST
	 *
	 * then the QTY column is level 0
	 * and  the BASIC_COST column is level 1
	 *
	 * @param level the level
	 * @return the range level
	 */
	public RangeLevel getRangeLevel(final int level) {
		/*internally, leaves have a level of 0 and then the level increases
		as you move closer to the root*/
		/*Externally, the caller of this method thinks of the root RangeLevel (the
		leftmost column of the 'table') to have a level of 0 and the levels increase
		as you move to the right in the 'table'*/
		if(level < 0 || level >= rangeLevels.size()) {
			throw new IllegalArgumentException("level (" + level + ") cannot be less than 0 or greater than " + (this.rangeLevels.size()-1));
		}

		//return (RangeLevel)rangeLevels.get(level);
		return rangeLevels.get(getLevelCount() - level - 1);

	}

   /**
    * Prepend range level.
    *
    * @param rl the rl
    */
   public void prependRangeLevel(final RangeLevel rl)
   {
	  rl.setExpressionTree(this);
	  rangeLevels.add(0,rl);
   }

   /**
    * Adds the range level.
    *
    * @param rl the rl
    */
   public void addRangeLevel(final RangeLevel rl)
   {
	  rl.setExpressionTree(this);
	  rangeLevels.add(rl);
   }

   /**
    * Gets the level count.
    *
    * @return the level count
    */
   public int getLevelCount() {

	  if(rangeLevels != null) {
		return rangeLevels.size();
	  }
	  else {
		return 0;
	  }

   }

   /**
    * Complete range tree.
    *
    * @param newRv the new rv
    */
   public void completeRangeTree(final RangeValue newRv) {

	  RangeLevel rlevel;
	  RangeValue rv;

	  //end of branch
	  if(newRv.getLevel() == 0) return;

	  int subLevel = newRv.getLevel() -1;

	  //rlevel = (RangeLevel)rangeLevels.get(subLevel);
	  rlevel = getRangeLevel(subLevel);

	  //add sub range to new range value(s) recursively
	  Range r = new Range(CATCHALL_ADD);
	  r.setName(rlevel.getName());
	  r.setType(rlevel.getType());
	  r.setLevel(subLevel);

	  newRv.setSubRange(r);

	  //recurse
	  //Range constructor automatically adds a catch-all range value
	  rv = r.getRangeValue(0);
	  completeRangeTree(rv);


   }

   /**
    * This method produces a table version of this ExpressionTree for use as input
    * to GenericResultSet.initFromObjectArray(...).
    *
    * @param r the r
    * @param row the row
    * @param aTable the a table
    * @return the list
    */
	public List<Object> flatten(final Range r, final Object[] row, final List<Object> aTable) {

		RangeValue rv;

		//loop thru all the RangeValues in the given Range (r)
		for (int i=0; i < r.getRangeValueCount(); i++) {
			rv = r.getRangeValue(i);

			//add the cell value for this RangeValue & row
			row[getLevelCount() - rv.getLevel() -1] = rv.getDisplayValue();

			//if this is the innermost/rightmost Range, then complete the row
			if (rv.getLevel()==0) {
				//end of branch

				//add a value for the expression to the row.
				row[getLevelCount()] = rv.getValueExpression();

				//copy the row and append to table arraylist
				Object[] rowCopy = new Object[row.length];
				//Sonar Violation Fix on 09-Nov-2015 by TCS:Avoid Array Loops
				System.arraycopy(row, 0, rowCopy, 0, row.length);
				/*for (int k=0; k < row.length; k++) {
					rowCopy[k] = row[k];
				}*/
				aTable.add(rowCopy);

				/* for testing / debugging
				rowString = "";
				for (int j=0; j < row.length; j++) {
					rowString += row[j] + " | ";
				}
				System.out.println(rowString);
				*/
			} else {
			//this is not the innermost/rightmost Range, so call flatten again
			//for the next subRange (i.e. the next Range to the right.
				flatten(rv.getSubRange(),row,aTable);
			}
		}
		return aTable;
	}

	/**
	 * returns a <code>String[]</code> of column names for use in converting an
	 * ExpressionTree to table form.
	 *
	 * @return  The result is the equivalent of the levelNames, in order, plus an
	 * extra column name "Expression" at the end. ("Expression" is hard-coded).
	 */
	public String[] getTableColumnNames() {
		String[] names = new String[getLevelCount()+1];
		String[] levelNames = getLevelNames();
		//Sonar Violation Fix on 09-Nov-2015 by TCS:Avoid Array Loops
		System.arraycopy(levelNames, 0, names, 0, levelNames.length);
		/*for(int i=0; i < levelNames.length; i++) {
			names[i] = levelNames[i];
		}*/

		//set the title for the Expression column.
		names[names.length-1] = RANGE_TYPE_EXPRESSION;

		return names;
	}

	/**
	 * Gets the level names.
	 *
	 * @return the level names
	 */
	public String[] getLevelNames() {
		RangeLevel rLev;

		int levels = getLevelCount();
		String[] names = new String[levels];

		for (int i=0; i < levels; i++) {
			rLev = getRangeLevel(levels-i-1);
			names[i] = rLev.getName();
		}
		return names;
	}

	/**
	 * Find expression.
	 *
	 * @param levelValues the level values
	 * @return the string
	 */
	public String findExpression(final String[] levelValues) {
		if(levelValues == null || levelValues.length != getLevelCount()) {
			throw new IllegalArgumentException("level values array is not compatible with expression tree structure");
		}
		return findExp(levelValues,rootRange);
	}

	/**
	 * Find exp.
	 *
	 * @param levelValues the level values
	 * @param r the r
	 * @return the string
	 */
	public String findExp(final String[] levelValues, final Range r) {
		RangeValue rv;
		for(int i=0; i < r.getRangeValueCount(); i++) {

			rv = r.getRangeValues().get(i);

			if (rv.valueInRange(levelValues[  rangeLevels.size() - r.getLevel() -1 ])) {
				if(r.getLevel() == 0) {
					return rv.getValueExpression();
				} else {
					return findExp(levelValues,rv.getSubRange());
				}
			}
		}
		return null;
	}

	/**
	 * Find exp table row.
	 *
	 * @param levelValues the level values
	 * @param r the r
	 * @return the int
	 */
	public int findExpTableRow(final String[] levelValues, final Range r) {
		RangeValue rv;
		for (int i=0; i<r.getRangeValueCount(); i++) {
			rv = r.getRangeValues().get(i);

			if(rv.valueInRange(levelValues[r.getLevel()])) {
				if(r.getLevel() == 0) {
					return 1;
				} else {
					return findExpTableRow(levelValues,rv.getSubRange()) + 1;
				}
			}
		}
		//not found
		return -1;
	}

	/**
	 * Navigates the ExpressionTree and finds and returns the proper mathematical
	 * expression for the given input values.
	 *
	 * @param lookupParams the lookup params
	 * @return String representation of the math expression
	 */
	public String getExpression(final Hashtable<String, Object> lookupParams) {
		String[] levelNames = this.getLevelNames();

		//make sure lookup params is consistent with tree levels
		//(i.e. a lookup parameter passed for each tree level)
		for(int i=0; i < levelNames.length; i++)
		{
			if(!lookupParams.containsKey(levelNames[i]))
			{
				throw new IllegalArgumentException("Expression tree: '"+this.getName()+
					"' is missing lookup parameter: " + levelNames[i]);
			}
		}

		//order lookup parameters to match order of value tree levels and convert to string
		String[] levelValues = new String[this.getLevelCount()];
		String levelValue=null;
		for(int i=0; i < levelNames.length; i++)
		{
			levelValue = lookupParams.get(levelNames[i])==null?null:lookupParams.get(levelNames[i]).toString();

			if(levelValue == null)
			{
			  throw new IllegalArgumentException("For the '"+this.getName()+
				"' Expression Tree, there is no lookup parameter value for parameter: " + levelNames[i]);
			}

			levelValues[i] = levelValue;
		}

		return this.findExpression(levelValues);
	}

	/**
	 * Adds the expression variable.
	 *
	 * @param varName the var name
	 */
	public void addExpressionVariable(final String varName)
	{
		expressionVariables.add(varName);
	}

	/**
	 * Gets the expression variables.
	 *
	 * @return the expression variables
	 */
	public List<String> getExpressionVariables()
	{
		return expressionVariables;
	}

	/**
	 * Gets the lookup parameter hashtable.
	 *
	 * @return the lookup parameter hashtable
	 */
	public Hashtable<String, BigDecimal> getLookupParameterHashtable() {
		Hashtable<String, BigDecimal> ht = new Hashtable<String, BigDecimal>();
		RangeLevel rl;

		for(int i=0; i < rangeLevels.size(); i ++)	{
			rl = rangeLevels.get(i);
			ht.put(rl.getName(),MoneyHelper.ZERO);
		}
		return ht;
	}

	/**
	 * Gets the expression variable hashtable.
	 *
	 * @return the expression variable hashtable
	 */
	public Hashtable<String, BigDecimal> getExpressionVariableHashtable() {
		Hashtable<String, BigDecimal> ht = new Hashtable<String, BigDecimal>();

		if (expressionVariables != null) {
			for(int i=0; i < expressionVariables.size(); i++) {
				ht.put(expressionVariables.get(i), MoneyHelper.ZERO);
			}
		}
		return ht;
	}

	/**
	 * Inits the from xml element.
	 *
	 * @param xml the xml
	 * @throws JDOMException the jDOM exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void initFromXmlElement(final String xml) throws JDOMException, IOException {
		// websphere/weblogic: do not specify the implementation of the xml reader
		SAXBuilder parser = new SAXBuilder();
		Document doc;
		doc = parser.build( new StringReader(xml));
		initFromXmlElement(doc.getRootElement());
	}


   /**
    * Inits the from xml element.
    *
    * @param elm the elm
    */
   public void initFromXmlElement(final Element elm)
   {

	  setName(elm.getAttributeValue("name"));

	  Element eRangeLevels = elm.getChild("RangeLevels");
	  if(eRangeLevels != null) {
		@SuppressWarnings("unchecked")
		List<Element> levels = eRangeLevels.getChildren("RangeLevel");
		Element eLevel;
		RangeLevel rl;
		for(int i=0; i < levels.size(); i++) {
		  eLevel = levels.get(i);
		  rl = new RangeLevel();
		  rl.initFromXmlElement(eLevel);
		  addRangeLevel(rl);
		  //prependRangeLevel(rl);
		}
	  }

	  Element eExpVars = elm.getChild("ExpressionVariables");
	  if(eExpVars != null) {
		@SuppressWarnings("unchecked")
		List<Element> vars = eExpVars.getChildren("ExpressionVariable");
		Element eVar;
		for(int i=0; i < vars.size(); i++) {
		  eVar = vars.get(i);
		  expressionVariables.add(eVar.getAttributeValue("name"));
		}
	  }

	  Element eRootRange = elm.getChild("Range");
	  rootRange = new Range(CATCHALL_NONE);
	  rootRange.initFromXmlElement(eRootRange);

   }

	/**
	 * returns a string represenation of this ExpressionTree. This method just
	 * returns the name of the ExpressionTree. It is used for displaying formulas.
	 *
	 * @return the string
	 */
	public String toString()
	{
		return this.getName();
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {	return name;	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(final String name) {	this.name = name;	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {	return type;	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(final String type) {	this.type = type;	}

	/**
	 * Gets the root range.
	 *
	 * @return the root range
	 */
	public Range getRootRange() {	return rootRange;	}

}
