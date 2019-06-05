/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2019 Grégory Van den Borre
 *
 *  More infos available: https://engine.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 */

package be.yildizgames.common.client.config;

import be.yildizgames.common.util.language.LanguageValue;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Grégory Van den Borre
 */
public final class ConfigurationTest {

    @Nested
    public class TranslationKey {

        @Test
        public void values() {
            assertEquals("user_login", Configuration.LOGIN_KEY);
            assertEquals("user_password", Configuration.PWD_KEY);
            assertEquals("language", Configuration.LANGUAGE_KEY);
            assertEquals("save", Configuration.SAVE_CREDENTIALS_KEY);
            assertEquals("authentication_host", Configuration.AUTHENTICATION_HOST_KEY);
            assertEquals("authentication_port", Configuration.AUTHENTICATION_PORT_KEY);
            assertEquals("server_host", Configuration.SERVER_HOST_KEY);
            assertEquals("server_port", Configuration.SERVER_PORT_KEY);
            assertEquals("debug", Configuration.DEBUG_KEY);
        }
    }

    @Nested
    public class SetLogin {

        @Test
        public void happyFlow() throws IOException {
            Configuration c = givenAnEmpty();
            c.setLogin("tree");
            assertEquals("tree", c.getLogin());
        }

        @Test
        public void withNull() throws IOException {
            Configuration c = givenAnEmpty();
            assertThrows(NullPointerException.class, () -> c.setLogin(null));
        }
    }

    @Nested
    public class GetLogin {

        @Test
        public void happyFlow() throws IOException {
            Properties p = new Properties();
            p.load(new FileInputStream(getFile("login/chair")));
            Configuration c = Configuration.getInstance().readFromProperties(p);
            assertEquals("chair", c.getLogin());
        }

        @Test
        public void empty() throws IOException {
            Configuration c = givenAnEmpty();
            assertEquals("", c.getLogin());
        }
    }

    @Nested
    public class SetPassword {

        @Test
        public void happyFlow() throws IOException {
            Configuration c = givenAnEmpty();
            c.setPassword("leaf");
            assertEquals("leaf", c.getPassword());
        }

        @Test
        public void withNull() throws IOException {
            Configuration c = givenAnEmpty();
            assertThrows(NullPointerException.class, () -> c.setPassword(null));
        }
    }

    @Nested
    public class GetPassword {

        @Test
        public void happyFlow() throws IOException {
            Properties p = new Properties();
            p.load(new FileInputStream(getFile("password/table")));
            Configuration c = Configuration.getInstance().readFromProperties(p);
            assertEquals("table", c.getPassword());
        }

        @Test
        public void empty() throws IOException {
            Configuration c = givenAnEmpty();
            assertEquals("", c.getPassword());
        }
    }

    @Nested
    public class IsSaveCredential {

        private Configuration givenSaveCredentialConfiguration(final String file) throws IOException {
            Properties p = new Properties();
            p.load(new FileInputStream(getFile("save/" + file)));
            return Configuration.getInstance().readFromProperties(p);
        }

        @Test
        public void fromEmptyFile() throws IOException {
            Configuration c = givenAnEmpty();
            assertFalse(c.isSaveCredentialsChecked());
        }

        @Test
        public void fromFileTrueValue() throws IOException {
            Configuration c = givenSaveCredentialConfiguration("true");
            assertTrue(c.isSaveCredentialsChecked());
        }

        @Test
        public void fromFileFalseValue() throws IOException {
            Configuration c = givenSaveCredentialConfiguration("false");
            assertFalse(c.isSaveCredentialsChecked());
        }

        @Test
        public void fromFileAnyValue() throws IOException {
            Configuration c = givenSaveCredentialConfiguration("invalid");
            assertFalse(c.isSaveCredentialsChecked());
        }
    }

    @Nested
    public class SetSave {

        @Test
        public void withTrueThenFalse() throws IOException {
            Configuration c = givenAnEmpty();
            c.setSaveCredentialsChecked(true);
            assertTrue(c.isSaveCredentialsChecked());
            c.setSaveCredentialsChecked(false);
            assertFalse(c.isSaveCredentialsChecked());
        }

        @Test
        public void swap() throws IOException {
            Configuration c = givenAnEmpty();
            assertFalse(c.isSaveCredentialsChecked());
            c.swapSaveCredentialsChecked();
            assertTrue(c.isSaveCredentialsChecked());
            c.swapSaveCredentialsChecked();
            assertFalse(c.isSaveCredentialsChecked());

        }
    }


    @Nested
   public class GetLanguage {

        private Configuration givenLanguageConfiguration(final String file) throws IOException {
            Properties p = new Properties();
            p.load(new FileInputStream(getFile("language/" + file)));
            return Configuration.getInstance().readFromProperties(p);
        }

        @Test
        public void english() throws IOException {
            Configuration c = givenLanguageConfiguration("english");
            assertTrue(c.isLanguagePresent());
            assertEquals(LanguageValue.EN, c.getLanguage());
        }

        @Test
        public void french() throws IOException {
            Configuration c = givenLanguageConfiguration("french");
            assertTrue(c.isLanguagePresent());
            assertEquals(LanguageValue.FR, c.getLanguage());
        }

        @Test
        public void invalid() throws IOException {
            Configuration c = givenLanguageConfiguration("invalid");
            assertTrue(c.isLanguagePresent());
            assertEquals(LanguageValue.EN, c.getLanguage());
        }

        @Test
        public void withEmpty() throws IOException {
            Configuration c = givenAnEmpty();
            assertFalse(c.isLanguagePresent());
            assertEquals(LanguageValue.EN, c.getLanguage());
        }
    }

    @Nested
    public class SetLanguage {

        @Test
        public void french() throws IOException {
            Configuration c = givenAnEmpty();
            assertFalse(c.isLanguagePresent());
            assertEquals(LanguageValue.EN, c.getLanguage());
            c.setLanguage(LanguageValue.FR);
            assertTrue(c.isLanguagePresent());
            assertEquals(LanguageValue.FR, c.getLanguage());
        }

        @Test
        public void withNull() throws IOException {
            Configuration c = givenAnEmpty();
            assertThrows(NullPointerException.class, () -> c.setLanguage(null));
        }
    }

    @Nested
    public class IsDebug {

        private Configuration givenDebugConfiguration(final String file) throws IOException{
            Properties p = new Properties();
            p.load(new FileInputStream(getFile("debug/" + file)));
            return Configuration.getInstance().readFromProperties(p);
        }

        @Test
        public void fromEmptyFile() throws IOException {
            Configuration c = givenAnEmpty();
            assertFalse(c.isDebug());
        }

        @Test
        public void fromFileTrueValue() throws IOException {
            Configuration c = givenDebugConfiguration("true");
            assertTrue(c.isDebug());
        }

        @Test
        public void fromFileFalseValue() throws IOException {
            Configuration c = givenDebugConfiguration("false");
            assertFalse(c.isDebug());
        }

        @Test
        public void fromFileAnyValue() throws IOException {
            Configuration c = givenDebugConfiguration("invalid");
            assertFalse(c.isDebug());
        }
    }

    @Nested
    public class SetAuthenticationHost {

        @Test
        public void happyFlow() throws IOException {
            Configuration c = givenAnEmpty();
            c.setAuthenticationHost("localhost");
            assertEquals("localhost", c.getAuthenticationHost());
        }

        @Test
        public void withNull() throws IOException {
            Configuration c = givenAnEmpty();
            assertThrows(NullPointerException.class, () -> c.setAuthenticationHost(null));
        }
    }

    @Nested
    public class SetAuthenticationPort {

        @Test
        public void happyFlow() throws IOException {
            Configuration c = givenAnEmpty();
            c.setAuthenticationPort(5555);
            assertEquals(5555, c.getAuthenticationPort());
        }
    }

    @Nested
    public class SetServerHost {

        @Test
        public void happyFlow() throws IOException {
            Configuration c = givenAnEmpty();
            c.setServerHost("localhost");
            assertEquals("localhost", c.getServerHost());
        }

        @Test
        public void withNull() throws IOException {
            Configuration c = givenAnEmpty();
            assertThrows(NullPointerException.class, () -> c.setServerHost(null));
        }
    }

    @Nested
    public class SetServerPort {

        @Test
        public void happyFlow() throws IOException {
            Configuration c = givenAnEmpty();
            c.setServerPort(3333);
            assertEquals(3333, c.getServerPort());
        }
    }

    private static File getFile(final String name) {
        return new File("src/test/resources/config/" + name + ".properties");
    }

    private static Configuration givenAnEmpty() throws IOException{
        Properties p = new Properties();
        p.load(new FileInputStream(getFile("configEmpty")));
        if(!p.isEmpty()) {
            throw new InvalidParameterException("configEmpty.properties is not empty");
        }
        return Configuration.getInstance().readFromProperties(p);
    }
}
