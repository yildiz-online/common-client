/*
 * Copyright (C) Grégory Van den Borre - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Grégory Van den Borre <vandenborre.gregory@hotmail.fr> 2019-2021
 */

package be.yildizgames.common.client.translation;

import be.yildizgames.common.configuration.LanguageConfiguration;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author Grégory Van den Borre
 */
public class BaseTranslation implements Translation {

    private final Map<Locale, ResourceBundle> bundles = new HashMap<>();

    private final LanguageConfiguration configuration;

    protected BaseTranslation(LanguageConfiguration configuration, Class<?> c) {
        super();
        this.configuration = Objects.requireNonNull(configuration);
        for(var l : configuration.getSupportedLocale()) {
            this.bundles.put(l, ResourceBundle.getBundle(c.getName(), l));
        }
    }

    @Override
    public final String get(String key) {
        try {
            return this.bundles.get(this.configuration.getLocale()).getString(key);
        } catch (Exception e) {
            System.getLogger(this.getClass().getName()).log(System.Logger.Level.ERROR, "Missing translation for " + this.configuration.getLocale() + " : " + key, e);
            return key;
        }
    }
}
