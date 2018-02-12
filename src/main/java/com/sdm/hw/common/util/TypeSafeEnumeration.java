/*
 * All rights reserved
 */
package com.sdm.hw.common.util;

import com.sdm.hw.common.constant.UtilConstants;
import com.sdm.hw.common.intf.TypeSafeEnumerable;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Parent class for type safe enumerations. Used for persistence of Hibernate
 * POJOs.
 */
public class TypeSafeEnumeration
{

    /** The instances. */
    private ConcurrentHashMap<Serializable, TypeSafeEnumerable> instances = new ConcurrentHashMap<Serializable, TypeSafeEnumerable>();

    public void setInstances(
        final ConcurrentHashMap<Serializable, TypeSafeEnumerable> instances)
    {
        this.instances = instances;
    }

    /**
     * Adds the given instance with respect to its TypeSafeEnumerationId.
     * 
     * @param TypeSafeEnumerable
     *            instance the instance
     */
    public void add(final TypeSafeEnumerable instance)
    {
        final Serializable serializableId = instance
                .getTypeSafeEnumerationId();
        if (this.instances.containsKey(serializableId))
        {
            throw new IllegalStateException("Duplicate id <" + serializableId
                    + "> is already in the map.");
        }
        this.instances.put(serializableId, instance);
    }

    /**
     * Gets the TypeSafeEnumerable for a Serializable.
     * 
     * @param Serializable
     *            id the id
     * @return the type safe enumerable
     */
    public TypeSafeEnumerable get(final Serializable serializableId)
    {
        return this.instances.get(serializableId);
    }

    /**
     * This method is responsible for returning the "approved" instance of a
     * type-safe enumeration. It does this by looking up the enumeration
     * instance by id.
     * 
     * It is also responsible for ensuring that the returned instance is
     * initialized.
     * 
     * It is also responsible for adding new instances into the map. This allows
     * us to support type-safe enumerations without mandating the definition of
     * constants for each element.
     * 
     * @param TypeSafeEnumerable
     *            input the provided instance
     * @return the "approved" instance
     */
    public TypeSafeEnumerable readResolve(final TypeSafeEnumerable input)
    {
    	 // Null conditions being added because some of the instances while calling from client .. .Like Form entity was getting null value.
    	// Changes start for Defect 15488 Date Nov 22, 2016
        TypeSafeEnumerable result = null;
        if(UtilConstants.NULL != input && UtilConstants.NULL != input.getTypeSafeEnumerationId())
        {
        	result = this.instances.get(input.getTypeSafeEnumerationId());
		        if (result == null) {
		            this.instances.put(input.getTypeSafeEnumerationId(), input);
		            result = input;
		        } else if (!result.isInitialized()) {
		            result.initialize(input);
		        }
        }
        //Changes End for Defect 15488 Date Nov 22, 2016
        return result;
     }

    /**
     * Gets the instances.
     * 
     * @return the instances
     */
    public Map<Serializable, TypeSafeEnumerable> getInstances()
    {
        return Collections.unmodifiableMap(this.instances);
    }

}
