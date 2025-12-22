package com.example.pokedex.services;

/**
 * Adds locale management capability to a property provider.
 */
public interface LocalizedPropertyProviderInterface {
    /**
     * Sets the locale to use for all String properties provided by the service.
     *
     * @param localeCode the locale code (2 letters code) representing the desired locale to use
     *                   (e.g., "en", "fr", "de").
     */
    void setStringPropertyLocale(String localeCode);

    /**
     * Gets the locale that is currently used by the service
     *
     * @return the locale code (2 letters code) representing the locale (e.g., "en", "fr", "de").
     */
    String getStringPropertyLocale();
}