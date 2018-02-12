package com.sdm.hw.common.util;

import java.math.BigDecimal;


/**
 * 
 * A Utility for dealing with BigDecimals that represent money.
 * 
 */
public class MoneyHelper
        extends DecimalHelper
{

    /** The Constant CURRENCY_SCALE. */
    public static final int CURRENCY_SCALE = 2;

    /**
     * Performs Conversion of null.
     * 
     * @param BigDecimal
     *            value the value
     * @return the big decimal
     */
    public static BigDecimal convertNull(final BigDecimal value)
    {
        final BigDecimal result = (value == null ? DecimalHelper.ZERO : value);
        return result.setScale(MoneyHelper.CURRENCY_SCALE,
                BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Convert null or negative to zero.
     * 
     * @param BigDecimal
     *            value the value
     * @return the big decimal
     */
    public static BigDecimal convertNullOrNegativeToZero(final BigDecimal value)
    {
        final BigDecimal result = ((value == null)
                || (value.compareTo(DecimalHelper.ZERO) < 0) ? DecimalHelper.ZERO
                : value);
        return result.setScale(MoneyHelper.CURRENCY_SCALE,
                BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Adds the two values.
     * 
     * @param BigDecimal
     *            param1 the param1
     * @param BigDecimal
     *            param2 the param2
     * @return the big decimal
     */
    public static BigDecimal add(final BigDecimal param1,
        final BigDecimal param2)
    {
        final BigDecimal value1 = MoneyHelper.convertNull(param1);
        final BigDecimal value2 = MoneyHelper.convertNull(param2);

        return value1.add(value2).setScale(DecimalHelper.DEFAULT_SCALE,
                BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Subtract the two values.
     * 
     * @param BigDecimal
     *            param1 the param1
     * @param BigDecimal
     *            param2 the param2
     * @return the big decimal
     */
    public static BigDecimal subtract(final BigDecimal param1,
        final BigDecimal param2)
    {
        final BigDecimal value2 = MoneyHelper.convertNull(param2);
        return MoneyHelper.add(param1, value2.negate());
    }

    /**
     * Multiply the two vakues.
     * 
     * @param BigDecimal
     *            param1 the param1
     * @param BigDecimal
     *            param2 the param2
     * @return the big decimal
     */
    public static BigDecimal multiply(final BigDecimal param1,
        final BigDecimal param2)
    {
        // SS-20050525: for the record, I think this is extremely shady. The
        // public convertNull methods
        // round to CURRENCY_SCALE before multiplying, so multiplying something
        // like unit cost (which
        // has fractions of a cent) using this method will result in imprecise
        // answers.
        final BigDecimal value1 = MoneyHelper.convertNull(param1);
        final BigDecimal value2 = MoneyHelper.convertNull(param2);
        return value1.multiply(value2).setScale(MoneyHelper.CURRENCY_SCALE,
                BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Divide the two numbers.
     * 
     * @param BigDecimal
     *            value1 the value1
     * @param BigDecimal
     *            value2 the value2
     * @return the big decimal
     */
    public static BigDecimal divide(final BigDecimal value1,
        final BigDecimal value2)
    {
        return DecimalHelper.divide(value1, value2).setScale(
                MoneyHelper.CURRENCY_SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Perform add operation on two numbers.
     * 
     * @param values
     *            the values
     * @return the big decimal
     */
    public static BigDecimal sum(final BigDecimal[] values)
    {
        BigDecimal result = DecimalHelper.ZERO;
        for (BigDecimal value : values)
        {
            result = MoneyHelper.add(result, MoneyHelper.convertNull(value));
        }
        return result;
    }
}
