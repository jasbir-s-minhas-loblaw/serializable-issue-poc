/*
 * All rights reserved
 */
package com.sdm.hw.common.util;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * A set of utility methods for working with {@link BigDecimal}.
 */
public abstract class DecimalHelper
{

    /** The Constant ZERO. */
    public static final BigDecimal ZERO = new BigDecimal("0.00");

    /** The Constant ONE. */
    public static final BigDecimal ONE = new BigDecimal("1.00");

    /** The Constant ONE_HUNDRED. */
    public static final BigDecimal ONE_HUNDRED = new BigDecimal("100.00");

    /** The Constant TEN. */
    public static final BigDecimal TEN = new BigDecimal("10.00");

    /** The Constant FIVE. */
    public static final BigDecimal FIVE = new BigDecimal("5.00");

    /** The Constant DEFAULT_SCALE. */
    public static final int DEFAULT_SCALE = 2;

    /**
     * Guard against nulls and set the scale as appropriate. Assumption: these
     * are all monetary values
     * 
     * @param BigDecimal
     *            value the value
     * @return the big decimal
     */
    public static BigDecimal convertNull(final BigDecimal value)
    {
        return DecimalHelper.convertNull(value, false);
    }

    /**
     * This method sets the scale of big decimal value and guard it against
     * null.
     * 
     * @param BigDecimal
     *            value the value
     * @param forceScale
     *            the force scale
     * @return the big decimal
     */
    public static BigDecimal convertNull(final BigDecimal value,
        final boolean forceScale)
    {
        BigDecimal result = value == null ? DecimalHelper.ZERO : value;
        if (forceScale)
        {
            result = result.setScale(DecimalHelper.DEFAULT_SCALE,
                    BigDecimal.ROUND_HALF_UP);
        }
        return result;
    }

    /**
     * This method Ceil the big decimal value which round off the given
     * BigDecimal value to next higher integer value.For example it makes value
     * of 1.2 as 2.
     * 
     * @param BigDecimal
     *            value the value
     * @return the int value
     */
    public static int ceilIt(final BigDecimal value)
    {
        return DecimalHelper.convertNull(value)
                .setScale(0, BigDecimal.ROUND_CEILING).intValue();
    }

    /**
     * Compare one BigDecimal value with another.
     * 
     * @param BigDecimal
     *            value1 the value1
     * @param BigDecimal
     *            value2 the value2
     * @return the int value
     */
    public static int compareTo(final BigDecimal value1,
        final BigDecimal value2)
    {
        return DecimalHelper.convertNull(value1).compareTo(
                DecimalHelper.convertNull(value2));
    }

    /**
     * Checks if two BigDecimal values are equal .
     * 
     * @param BigDecimal
     *            value1 the value1
     * @param BigDecimal
     *            value2 the value2
     * @return true, if successful
     */
    public static boolean isEqual(final BigDecimal value1,
        final BigDecimal value2)
    {
        return DecimalHelper.compareTo(value1, value2) == 0;
    }

    /**
     * Checks if bigDecimal value is zero.
     * 
     * @param BigDecimal
     *            bigDecimal the big decimal
     * @return true, if is zero
     */
    public static boolean isZero(final BigDecimal bigDecimal)
    {
        return 0 == DecimalHelper.compareToZero(bigDecimal);
    }

    /**
     * Checks if bigDecimal value is greater than zero.
     * 
     * @param BigDecimal
     *            bigDecimal the big decimal
     * @return true, if is greater than zero
     */
    public static boolean isGreaterThanZero(final BigDecimal bigDecimal)
    {
        return 0 < DecimalHelper.compareToZero(bigDecimal);
    }

    /**
     * Checks if bigDecimal value is less than zero.
     * 
     * @param BigDecimal
     *            bigDecimal the big decimal
     * @return true, if is less than zero
     */
    public static boolean isLessThanZero(final BigDecimal bigDecimal)
    {
        return 0 > DecimalHelper.compareToZero(bigDecimal);
    }

    /**
     * Checks if one BigDecimal value is greater than other BigDecimal value.
     * 
     * @param BigDecimal
     *            v1 the v1
     * @param BigDecimal
     *            v2 the v2
     * @return true, if is greater than
     */
    public static boolean isGreaterThan(final BigDecimal value1,
        final BigDecimal value2)
    {
        return DecimalHelper.convertNull(value1).compareTo(
                DecimalHelper.convertNull(value2)) > 0;
    }

    /**
     * Checks if one BigDecimal is greater than equal to another BigDecimal
     * value.
     * 
     * @param BigDecimal
     *            v1 the v1
     * @param BigDecimal
     *            v2 the v2
     * @return true, if is greater than equal
     */
    public static boolean isGreaterThanEqual(final BigDecimal value1,
        final BigDecimal value2)
    {
        return DecimalHelper.convertNull(value1).compareTo(
                DecimalHelper.convertNull(value2)) >= 0;
    }

    /**
     * Checks if given BigDecimal value is within the given range.
     * 
     * @param BigDecimal
     *            param the param
     * @param String
     *            lowerBoundary the lower boundary
     * @param String
     *            upperBoundary the upper boundary
     * @return true if value falls within or on the given upper and lower bounds
     */
    public static boolean isInRange(final BigDecimal param,
        final String lowerBoundary, final String upperBoundary)
    {
        final BigDecimal value = DecimalHelper.convertNull(param);
        return (new BigDecimal(lowerBoundary).compareTo(value) <= 0)
                && (value.compareTo(new BigDecimal(upperBoundary)) <= 0);
    }

    /**
     * Compare BigDecimal value to zero.
     * 
     * @param BigDecimal
     *            value1 the value1
     * @return the int value
     */
    public static int compareToZero(final BigDecimal value1)
    {
        return DecimalHelper.convertNull(value1).compareTo(DecimalHelper.ZERO);
    }

    /**
     * Perform Subtraction on two BigDecimal values.
     * 
     * @param BigDecimal
     *            param1 the param1
     * @param BigDecimal
     *            param2 the param2
     * @return the big decimal value
     */
    public static BigDecimal subtract(final BigDecimal param1,
        final BigDecimal param2)
    {
        final BigDecimal value1 = DecimalHelper.convertNull(param1);
        final BigDecimal value2 = DecimalHelper.convertNull(param2);

        return value1.subtract(value2).setScale(DecimalHelper.DEFAULT_SCALE,
                BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Adds the one BigDecimal values with one int value.
     * 
     * @param BigDecimal
     *            value1 the value1
     * @param int value2 the value2
     * @return the big decimal
     */
    public static BigDecimal add(final BigDecimal value1, final int value2)
    {
        return DecimalHelper.add(value1,
                new BigDecimal(String.valueOf(value2)));
    }

    /**
     * Adds the two BigDecimal values.
     * 
     * @param BigDecimal
     *            param1 the param1
     * @param BigDecimal
     *            param2 the param2
     * @return the big decimal value
     */
    public static BigDecimal add(final BigDecimal param1,
        final BigDecimal param2)
    {
        final BigDecimal value1 = DecimalHelper.convertNull(param1);
        final BigDecimal value2 = DecimalHelper.convertNull(param2);

        return value1.add(value2);
    }

    /**
     * Takes the collection of BigDecimal values and sum up them.
     * 
     * @param Collection
     *            <BigDecimal> arguments the arguments
     * @return the big decimal
     */
    public static BigDecimal sum(final Collection<BigDecimal> arguments)
    {
        BigDecimal result = DecimalHelper.ZERO;
        for (final BigDecimal argument : arguments)
        {
            result = DecimalHelper.add(result, argument);
        }
        return result;
    }

    /**
     * Takes the Array of BigDecimal values and sum up them.
     * 
     * @param BigDecimal
     *            [] arguments the arguments
     * @return the big decimal
     */
    public static BigDecimal sum(final BigDecimal[] arguments)
    {
        BigDecimal result = DecimalHelper.ZERO;
        for (final BigDecimal argument : arguments)
        {
            result = DecimalHelper.add(result, argument);
        }
        return result;
    }

    /**
     * Performs the devision operation on two BigDecimal values NOTE: It handles
     * division by ZERO by returning ZERO, which is convenient but dangerous.
     * 
     * @param BigDecimal
     *            value1 the value1
     * @param BigDecimal
     *            value2 the value2
     * @return the big decimal value
     */
    public static BigDecimal divide(final BigDecimal value1,
        final BigDecimal value2)
    {
        return DecimalHelper.divide(value1, value2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Sets the Scale for two BigDecimal values and Performs the devision on
     * them NOTE: It handles division by ZERO by returning ZERO, which is
     * convenient but dangerous.
     * 
     * @param BigDecimal
     *            param1 the param1
     * @param BigDecimal
     *            param2 the param2
     * @param int roundingMode the rounding mode
     * @return the big decimal
     */
    public static BigDecimal divide(final BigDecimal param1,
        final BigDecimal param2, final int roundingMode)
    {
        final BigDecimal value1 = DecimalHelper.convertNull(param1);
        final BigDecimal value2 = DecimalHelper.convertNull(param2);
        BigDecimal result = DecimalHelper.ZERO;
        if (DecimalHelper.ZERO.compareTo(value2) != (DecimalHelper.ZERO
                .intValue()))
        {
            result = value1.divide(value2, roundingMode);
        }
        return result;
    }

    /**
     * NOTE: It handles division by ZERO by returning ZERO, which is convenient
     * but dangerous.
     * 
     * @param BigDecimal
     *            value1 the value1
     * @param int value2 the value2
     * @param int scale the scale
     * @return the big decimal value
     */
    public static BigDecimal divide(final BigDecimal value1, final int value2,
        final int scale)
    {
        return DecimalHelper.divide(value1,
                new BigDecimal(String.valueOf(value2)), scale,
                BigDecimal.ROUND_HALF_UP);
    }

    /**
     * NOTE: It handles division by ZERO by returning ZERO, which is convenient
     * but dangerous.
     * 
     * @param BigDecimal
     *            param1 the param1
     * @param BigDecimal
     *            param2 the param2
     * @param int scale the scale
     * @param int roundingMode the rounding mode
     * @return the big decimal value
     */
    public static BigDecimal divide(final BigDecimal param1,
        final BigDecimal param2, final int scale, final int roundingMode)
    {
        final BigDecimal value1 = DecimalHelper.convertNull(param1);
        final BigDecimal value2 = DecimalHelper.convertNull(param2);
        BigDecimal result = DecimalHelper.ZERO;
        if (DecimalHelper.ZERO.compareTo(value2) != Integer
                .valueOf(DecimalHelper.ZERO.intValue()))
        {
            result = value1.divide(value2, scale, roundingMode);
        }
        return result;
    }

    /**
     * Multiply one BigDecimal value with one int value .
     * 
     * @param BigDecimal
     *            value1 the value1
     * @param int value2 the value2
     * @return the big decimal value
     */
    public static BigDecimal multiply(final BigDecimal value1, final int value2)
    {
        return DecimalHelper.multiply(value1,
                new BigDecimal(String.valueOf(value2)));
    }

    /**
     * Sets the scale for two BigDecimal values Multiply .
     * 
     * @param BigDecimal
     *            param1 the param1
     * @param BigDecimal
     *            param2 the param2
     * @return the big decimal value
     */
    public static BigDecimal multiply(final BigDecimal param1,
        final BigDecimal param2)
    {
        final BigDecimal value1 = DecimalHelper.convertNull(param1);
        final BigDecimal value2 = DecimalHelper.convertNull(param2);
        return value1.multiply(value2);
    }

    /**
     * Divide the Bigdecimal value by 10.
     * 
     * @param String
     *            value the value
     * @return the big decimal
     */
    public static BigDecimal toTenth(final String value)
    {
        return StringUtils.isBlank(value) ? null : new BigDecimal(value)
                .divide(DecimalHelper.TEN, 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Divide the Bigdecimal value by 100.
     * 
     * @param String
     *            value the value
     * @return the big decimal
     */
    public static BigDecimal toHundredth(final String value)
    {
        return StringUtils.isBlank(value) ? null
                : new BigDecimal(value).divide(DecimalHelper.ONE_HUNDRED, 2,
                        BigDecimal.ROUND_HALF_UP);
    }

    /**
     * This methods first remove the leading Zeros and minus signs from value
     * and convert it into percentage form.
     * 
     * @param String
     *            value the value
     * @return the big decimal
     */
    public static BigDecimal toPercentage(final String value)
    {
        BigDecimal result;
        if (value.matches("\\d+-\\d+"))
        {
            // Remove leading zeros if there is any minus signs within string.
            // i.e. "00-140"
            result = DecimalHelper.toPercentage(new BigDecimal(DecimalHelper
                    .removeLeadingZeros(value)));
        }
        else
        {
            result = DecimalHelper.toPercentage(new BigDecimal(value));
        }
        return result;
    }

    /**
     * Removes the leading zeros from the given value.
     * 
     * @param String
     *            value the value
     * @return the string
     */
    private static String removeLeadingZeros(final String value)
    {
    	StringBuilder result = new StringBuilder(value);//SonarFix S1149 on 7Mar17 by TCS
        while (result.charAt(0) == '0')
        {
            final String temp = result.substring(1, result.length());
            result = result.replace(0, result.length(), temp);
        }
        return result.toString();
    }

    /**
     * convert the BigDecimal value into percentage form by dividing it by 100.
     * 
     * @param BigDecimal
     *            param the param
     * @return the big decimal
     */
    public static BigDecimal toPercentage(final BigDecimal param)
    {
        final BigDecimal value = DecimalHelper.convertNull(param);
        return value.divide(DecimalHelper.ONE_HUNDRED, 2,
                BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Gets the Extract percentage value in integer form.
     * 
     * @param BigDecimal
     *            param the param
     * @param BigDecimal
     *            percentageParam the percentage param
     * @return the big decimal
     */
    public static BigDecimal extractPercentage(final BigDecimal param,
        final BigDecimal percentageParam)
    {
        final BigDecimal value = DecimalHelper.convertNull(param);
        final BigDecimal percentage = DecimalHelper
                .convertNull(percentageParam);
        return value.multiply(percentage).divide(DecimalHelper.ONE_HUNDRED,
                DecimalHelper.DEFAULT_SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Gets the whole number length.
     * 
     * @param BigDecimal
     *            value the value
     * @return the whole number length
     */
    public static int getWholeNumberLength(final BigDecimal value)
    {
        return String.valueOf(Math.abs(value.intValue())).length();

    }

    /**
     * This method makes use of the divide method which accepts the division by
     * zero by returning zero. Therefore the denominator can be zero. Anything
     * like modulo(x, 0) = 0, where x is any number.
     * 
     * @param BigDecimal
     *            numeratorParam the numerator param
     * @param BigDecimal
     *            denominatorParam the denominator param
     * @return the big decimal
     */
    public static BigDecimal modulo(final BigDecimal numeratorParam,
        final BigDecimal denominatorParam)
    {
        final BigDecimal numerator = DecimalHelper.convertNull(numeratorParam,
                true);
        final BigDecimal denominator = DecimalHelper.convertNull(
                denominatorParam, true);

        final BigDecimal divideResult = DecimalHelper.divide(numerator,
                denominator);
        final BigDecimal wholeNumber = divideResult.setScale(0,
                BigDecimal.ROUND_FLOOR);
        return DecimalHelper.subtract(divideResult, wholeNumber);
    }

    /**
     * If the numerator is a multiple of the denominator then it returns true.
     * Special case of denominator being zero will return true, as well.
     * 
     * @param BigDecimal
     *            numerator the numerator
     * @param BigDecimal
     *            denominator the denominator
     * @return true, if is multiple of
     */
    public static boolean isMultipleOf(final BigDecimal numerator,
        final BigDecimal denominator)
    {
        return DecimalHelper.modulo(numerator, denominator).equals(
                DecimalHelper.ZERO);
    }

    /**
     * Returns the minimum of the values.
     * 
     * @param BigDecimal
     *            values the values
     * @return the big decimal value
     */
    public static BigDecimal min(final BigDecimal... values)
    {
        BigDecimal least = null;
        for (final BigDecimal value : values)
        {
            if ((least == null) || (value.compareTo(least) < 0))
            {
                least = value;
            }
        }
        return least;
    }
}
