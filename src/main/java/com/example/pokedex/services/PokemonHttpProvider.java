package com.example.pokedex.services;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class PokemonHttpProvider implements PropertyProviderInterface {
    @Override
    public Integer getIntProperty(String propertyName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getStringProperty(String propertyName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setStringPropertyLocale(String localeCode) {
        // TODO Auto-generated method stub
    }

    @Override
    public String getStringPropertyLocale() {
        // TODO Auto-generated method stub
        return null;
    }
}
