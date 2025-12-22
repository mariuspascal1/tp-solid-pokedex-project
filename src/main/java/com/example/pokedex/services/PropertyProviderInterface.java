package com.example.pokedex.services;

/**
 * The {@code PropertyProviderInterface} describes methods for retrieving
 * properties as either integer or string values based on a property name.
 */
public interface PropertyProviderInterface {

    /**
     * Retrieves the integer value of the specified property.
     *
     * @param propertyName the name of the property to retrieve
     * @return the integer value of the property, or {@code null} if the
     * property doesn't exist
     */
    Integer getIntProperty(String propertyName);

    /**
     * Retrieves the string value of the specified property.
     *
     * @param propertyName the name of the property to retrieve
     * @return the string value of the property, or {@code null}  if the
     *      * property doesn't exist
     */
    String getStringProperty(String propertyName);
    
}
