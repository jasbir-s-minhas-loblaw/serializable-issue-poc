/*
 * All rights reserved.
 */
package com.sdm.hw.common.intf;

import java.io.Serializable;

/**
 * See title.
 */
public interface TypeSafeEnumerable
{

    /**
     * Gets the type safe enumeration id.
     * 
     * @return the type safe enumeration id
     */
    Serializable getTypeSafeEnumerationId();

    /**
     * Checks if is initialized.
     * 
     * @return true, if is initialized
     */
    boolean isInitialized();

    /**
     * Initialize.
     * 
     * @param TypeSafeEnumerable
     *            other the other
     */
    void initialize(TypeSafeEnumerable other);

    /**
     * Read resolve.
     * 
     * @return the object
     */
    Object readResolve();

}
