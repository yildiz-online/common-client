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

import be.yildiz.common.language.LanguageValue;
import be.yildiz.common.resource.PropertiesHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Properties;

/**
 * @author Grégory Van den Borre
 */
@RunWith(Enclosed.class)
public final class ConfigurationTest {

    public static class Key {

        @Test
        public void values() {
            Assert.assertEquals("user_login", Configuration.LOGIN_KEY);
            Assert.assertEquals("user_password", Configuration.PWD_KEY);
            Assert.assertEquals("language", Configuration.LANGUAGE_KEY);
            Assert.assertEquals("save", Configuration.SAVE_CREDENTIALS_KEY);
            Assert.assertEquals("authentication_host", Configuration.AUTHENTICATION_HOST_KEY);
            Assert.assertEquals("authentication_port", Configuration.AUTHENTICATION_PORT_KEY);
            Assert.assertEquals("server_host", Configuration.SERVER_HOST_KEY);
            Assert.assertEquals("server_port", Configuration.SERVER_PORT_KEY);
            Assert.assertEquals("debug", Configuration.DEBUG_KEY);
        }
    }

    public static class SetLogin {

        @Test
        public void happyFlow() {
            Configuration c = givenAnEmpty();
            c.setLogin("tree");
            Assert.assertEquals("tree", c.getLogin());
        }

        @Test(expected = AssertionError.class)
        public void withNull() {
            Configuration c = givenAnEmpty();
            c.setLogin(null);
        }
    }

    public static class GetLogin {

        @Test
        public void happyFlow() {
            Configuration c = Configuration.readFromFile(getFile("login/chair"));
            Assert.assertEquals("chair", c.getLogin());
        }

        @Test
        public void empty() {
            Configuration c = givenAnEmpty();
            Assert.assertEquals("", c.getLogin());
        }
    }

    public static class SetPassword {

        @Test
        public void happyFlow() {
            Configuration c = givenAnEmpty();
            c.setPassword("leaf");
            Assert.assertEquals("leaf", c.getPassword());
        }

        @Test(expected = AssertionError.class)
        public void withNull() {
            Configuration c = givenAnEmpty();
            c.setPassword(null);
        }
    }

    public static class GetPassword {

        @Test
        public void happyFlow() {
            Configuration c = Configuration.readFromFile(getFile("password/table"));
            Assert.assertEquals("table", c.getPassword());
        }

        @Test
        public void empty() {
            Configuration c = givenAnEmpty();
            Assert.assertEquals("", c.getPassword());
        }
    }

    public static class IsSaveCredential {

        private Configuration givenSaveCredentialConfiguration(final String file) {
            return Configuration.readFromFile(getFile("save/" + file));
        }

        @Test
        public void fromEmptyFile() {
            Configuration c = givenAnEmpty();
            Assert.assertFalse(c.isSaveCredentialsChecked());
        }

        @Test
        public void fromFileTrueValue() {
            Configuration c = givenSaveCredentialConfiguration("true");
            Assert.assertTrue(c.isSaveCredentialsChecked());
        }

        @Test
        public void fromFileFalseValue() {
            Configuration c = givenSaveCredentialConfiguration("false");
            Assert.assertFalse(c.isSaveCredentialsChecked());
        }

        @Test
        public void fromFileAnyValue() {
            Configuration c = givenSaveCredentialConfiguration("invalid");
            Assert.assertFalse(c.isSaveCredentialsChecked());
        }
    }

    public static class SetSave {

        @Test
        public void withTrueThenFalse() {
            Configuration c = givenAnEmpty();
            c.setSaveCredentialsChecked(true);
            Assert.assertTrue(c.isSaveCredentialsChecked());
            c.setSaveCredentialsChecked(false);
            Assert.assertFalse(c.isSaveCredentialsChecked());
        }

        @Test
        public void swap() {
            Configuration c = givenAnEmpty();
            Assert.assertFalse(c.isSaveCredentialsChecked());
            c.swapSaveCredentialsChecked();
            Assert.assertTrue(c.isSaveCredentialsChecked());
            c.swapSaveCredentialsChecked();
            Assert.assertFalse(c.isSaveCredentialsChecked());

        }
    }

    public static class SaveFile {

        @Test
        public void save() {
            File f = new File("fileToSave.properties");
            Configuration write = Configuration.readFromFile(f);
            write.setLogin("aLoginValue");
            write.setPassword("aPwdValue");
            write.setLanguage(LanguageValue.FR);
            write.setSaveCredentialsChecked(true);
            write.save();
            Configuration read = Configuration.readFromFile(f);
            Assert.assertEquals("aLoginValue", read.getLogin());
            Assert.assertEquals("aPwdValue", read.getPassword());
            Assert.assertEquals(LanguageValue.FR, read.getLanguage());
            Assert.assertTrue(read.isSaveCredentialsChecked());
            f.deleteOnExit();
        }
    }

    public static class GetLanguage {

        private Configuration givenLanguageConfiguration(final String file) {
            return Configuration.readFromFile(getFile("language/" + file));
        }

        @Test
        public void english() {
            Configuration c = givenLanguageConfiguration("english");
            Assert.assertTrue(c.isLanguagePresent());
            Assert.assertEquals(LanguageValue.EN, c.getLanguage());
        }

        @Test
        public void french() {
            Configuration c = givenLanguageConfiguration("french");
            Assert.assertTrue(c.isLanguagePresent());
            Assert.assertEquals(LanguageValue.FR, c.getLanguage());
        }

        @Test
        public void invalid() {
            Configuration c = givenLanguageConfiguration("invalid");
            Assert.assertTrue(c.isLanguagePresent());
            Assert.assertEquals(LanguageValue.EN, c.getLanguage());
        }

        @Test
        public void withEmpty() {
            Configuration c = givenAnEmpty();
            Assert.assertFalse(c.isLanguagePresent());
            Assert.assertEquals(LanguageValue.EN, c.getLanguage());
        }

        @Test
        public void withInvalid() throws IOException {
            File f = new File("temp.properties");
            f.createNewFile();
            Properties p = PropertiesHelper.getPropertiesFromFile(f);
            p.setProperty(Configuration.LANGUAGE_KEY, "GF");
            PropertiesHelper.save(p, f);
            Configuration oc = Configuration.readFromFile(f);
            Assert.assertTrue(oc.isLanguagePresent());
            Assert.assertEquals(LanguageValue.EN, oc.getLanguage());
            f.deleteOnExit();
        }
    }

    public static class SetLanguage {

        @Test
        public void french() {
            Configuration c = givenAnEmpty();
            Assert.assertFalse(c.isLanguagePresent());
            Assert.assertEquals(LanguageValue.EN, c.getLanguage());
            c.setLanguage(LanguageValue.FR);
            Assert.assertTrue(c.isLanguagePresent());
            Assert.assertEquals(LanguageValue.FR, c.getLanguage());
        }

        @Test(expected = NullPointerException.class)
        public void withNull() {
            Configuration c = givenAnEmpty();
            c.setLanguage(null);
        }
    }

    /**
     * isDebug methods.
     */
    public static class IsDebug {

        private Configuration givenDebugConfiguration(final String file) {
            return Configuration.readFromFile(getFile("debug/" + file));
        }

        @Test
        public void fromEmptyFile() {
            Configuration c = givenAnEmpty();
            Assert.assertFalse(c.isDebug());
        }

        @Test
        public void fromFileTrueValue() {
            Configuration c = givenDebugConfiguration("true");
            Assert.assertTrue(c.isDebug());
        }

        @Test
        public void fromFileFalseValue() {
            Configuration c = givenDebugConfiguration("false");
            Assert.assertFalse(c.isDebug());
        }

        @Test(expected = IllegalArgumentException.class)
        public void fromFileAnyValue() {
            Configuration c = givenDebugConfiguration("invalid");
            Assert.assertFalse(c.isDebug());
        }
    }

    private static File getFile(final String name) {
        return new File("src/test/resources/config/" + name + ".properties");
    }

    private static Configuration givenAnEmpty() {
        Properties p  = PropertiesHelper.getPropertiesFromFile(getFile("configEmpty"));
        if(!p.isEmpty()) {
            throw new InvalidParameterException("configEmpty.properties is not empty");
        }
        return Configuration.readFromFile(getFile("configEmpty"));
    }
}
