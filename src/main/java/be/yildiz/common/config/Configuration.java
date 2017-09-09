/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2017 Grégory Van den Borre
 *
 * More infos available: https://www.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 */

package be.yildiz.common.config;

import be.yildiz.common.language.Language;
import be.yildiz.common.language.LanguageValue;
import be.yildiz.common.resource.PropertiesHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Optional;
import java.util.Properties;

/**
 * Configuration data, data are retrieved by parsing the property file. If the file does not contains the required property, a default value is returned.
 *
 * @author Grégory Van Den Borre
 *         specfield login:String: Value of the login if the user saved it, empty string if not found.
 *         specfield password:String: Value of the password if the user saved it, saved in clear to be updated in view if necessary, empty string if not found.
 *         specfield language:String: Value of one possible language in the system, if empty of invalid, English is returned.
 *         specfield saveCredential: boolean: if true, the password and language will be persisted in the property file.
 */
public final class Configuration {

    private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);

    private static final Configuration INSTANCE = new Configuration();

    /**
     * Property key for language.
     */
    public static final String LANGUAGE_KEY = "language";

    /**
     * Property key for save credentials.
     */
    public static final String SAVE_CREDENTIALS_KEY = "save";

    /**
     * Property key for login.
     */
    public static final String LOGIN_KEY = "user_login";

    /**
     * Property key for password.
     */
    public static final String PWD_KEY = "user_password";

    /**
     * Property key for the authentication host address.
     */
    public static final String AUTHENTICATION_HOST_KEY = "authentication_host";

    /**
     * Property key for the authentication port number.
     */
    public static final String AUTHENTICATION_PORT_KEY = "authentication_port";

    /**
     * Property key for the server host address.
     */
    public static final String SERVER_HOST_KEY = "server_host";

    /**
     * Property key for the server port number.
     */
    public static final String SERVER_PORT_KEY = "server_port";

    /**
     * Property key to check the debug mode.
     */
    public static final String DEBUG_KEY = "debug";

    /**
     * Associated property file.
     */
    private Properties properties = new Properties();

    /**
     * Path of the config file.
     */
    private File filePath = new File("config.properties");

    /**
     * Full constructor.
     */
    private Configuration() {
        super();
    }

    public static Configuration getInstance() {
        return INSTANCE;
    }


    /**
     * Test if the configuration has been loaded from the file and return it.
     *
     * @param file File containing the configuration, if the file does not exists, it will be created with default values.
     * @param args Default values.
     * @return A full copy of the config data to prevent any change in it.
     */
    public Configuration readFromFile(final File file, final String... args) {
        this.filePath = file;
        if(file.exists()) {
            this.properties = PropertiesHelper.getPropertiesFromFile(file, args);
        } else {
            LOGGER.debug("Create non-existing property file " + file.getAbsolutePath());
            this.save();
        }
        return this;
    }

    /**
     * @return The login, or "" if the property is not found in this configuration.
     */
    public String getLogin() {
        return this.properties.getProperty(LOGIN_KEY, "");
    }

    /**
     * @param value The login to set.
     * @return This object for chaining.
     */
    public Configuration setLogin(final String value) {
        assert value != null;
        this.properties.setProperty(LOGIN_KEY, value);
        return this;
    }

    /**
     * @return The password or "" if tht property is not found in this configuration.
     */
    public String getPassword() {
        return this.properties.getProperty(Configuration.PWD_KEY, "");
    }

    /**
     * @param value The password to set.
     * @return This object for chaining.
     */
    public Configuration setPassword(final String value) {
        assert value != null;
        this.properties.setProperty(Configuration.PWD_KEY, value);
        return this;
    }

    /**
     * @return <code>true</code> if credentials have to be persisted on client computer, false otherwise.
     */
    public boolean isSaveCredentialsChecked() {
        return Boolean.parseBoolean(this.properties.getProperty(Configuration.SAVE_CREDENTIALS_KEY));
    }

    /**
     * @param checked Flag to persist or not the credentials on client computer.
     * @return This object for chaining.
     */
    public Configuration setSaveCredentialsChecked(final boolean checked) {
        this.properties.setProperty(Configuration.SAVE_CREDENTIALS_KEY, String.valueOf(checked));
        return this;
    }

    /**
     * Invert the flag to save or not the credentials on the client computer.
     */
    public void swapSaveCredentialsChecked() {
        this.setSaveCredentialsChecked(!this.isSaveCredentialsChecked());
    }

    /**
     * Persist the configuration in a file.
     */
    public void save() {

        Optional.ofNullable(this.filePath).ifPresent(p -> PropertiesHelper.save(this.properties, p));
    }

    /**
     * @return The language used for the game, or English if the value in property file is empty or invalid.
     */
    public Language getLanguage() {
        String value = this.properties.getProperty(LANGUAGE_KEY, LanguageValue.EN.shortName);
        return LanguageValue.fromShortName(value);
    }

    /**
     * Change the language to use.
     *
     * @param language New language.
     * @return This object for chaining.
     */
    public Configuration setLanguage(final Language language) {
        this.properties.setProperty(LANGUAGE_KEY, language.getShortName());
        return this;
    }

    /**
     * @return <code>true</code> if the language is present in the property file.
     */
    boolean isLanguagePresent() {
        return this.properties.getProperty(LANGUAGE_KEY) != null;
    }

    /**
     * Flag to check if the application must be run in debug mode, this flag is hard coded.
     *
     * @return <code>true</code> If the application must be run in debug mode.
     */
    public boolean isDebug() {
        return PropertiesHelper.getBooleanValue(this.properties, Configuration.DEBUG_KEY, false);
    }

    /**
     * @return The Authentication host address or "localhost" if that property is not found in this configuration.
     */
    public String getAuthenticationHost() {
        return this.properties.getProperty(Configuration.AUTHENTICATION_HOST_KEY, "localhost");
    }

    /**
     * @param value The authentication host address to set.
     * @return This object for chaining.
     * @throws NullPointerException If value is null.
     */
    public Configuration setAuthenticationHost(final String value) {
        assert value != null;
        this.properties.setProperty(Configuration.AUTHENTICATION_HOST_KEY, value);
        return this;
    }

    /**
     * @return The Authentication port number or 15023 if that property is not found in this configuration.
     */
    public int getAuthenticationPort() {
        return Integer.valueOf(this.properties.getProperty(Configuration.AUTHENTICATION_PORT_KEY, "15023"));
    }

    /**
     * @param value The authentication port number to set.
     * @return This object for chaining.
     */
    public Configuration setAuthenticationPort(final int value) {
        this.properties.setProperty(Configuration.AUTHENTICATION_PORT_KEY, String.valueOf(value));
        return this;
    }

    /**
     * @return The server host address or "localhost" if that property is not found in this configuration.
     */
    public String getServerHost() {
        return this.properties.getProperty(Configuration.SERVER_HOST_KEY, "localhost");
    }

    /**
     * @param value The server host address to set.
     * @return This object for chaining.
     * @throws NullPointerException If value is null.
     */
    public Configuration setServerHost(final String value) {
        assert value != null;
        this.properties.setProperty(Configuration.SERVER_HOST_KEY, value);
        return this;
    }

    /**
     * @return The server port number or 11139 if that property is not found in this configuration.
     */
    public int getServerPort() {
        return Integer.valueOf(this.properties.getProperty(Configuration.SERVER_PORT_KEY, "11139"));
    }

    /**
     * @param value The server port number to set.
     * @return This object for chaining.
     */
    public Configuration setServerPort(final int value) {
        this.properties.setProperty(Configuration.SERVER_PORT_KEY, String.valueOf(value));
        return this;
    }
}
