/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2018 Grégory Van den Borre
 *
 *  More infos available: https://www.yildiz-games.be
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

/**
 * @author Grégory Van den Borre
 */
final class ConfigurationTest {

   /* @Nested
    class Key {

        @Test
        void values() {
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
    class SetLogin {

        @Test
        void happyFlow() {
            Configuration c = givenAnEmpty();
            c.setLogin("tree");
            assertEquals("tree", c.getLogin());
        }

        @Test
        void withNull() {
            Configuration c = givenAnEmpty();
            assertThrows(AssertionError.class, () -> c.setLogin(null));
        }
    }

    @Nested
    class GetLogin {

        @Test
        void happyFlow() {
            Configuration c = Configuration.getInstance().readFromFile(getFile("login/chair"));
            assertEquals("chair", c.getLogin());
        }

        @Test
        void empty() {
            Configuration c = givenAnEmpty();
            assertEquals("", c.getLogin());
        }
    }

    @Nested
    class SetPassword {

        @Test
        void happyFlow() {
            Configuration c = givenAnEmpty();
            c.setPassword("leaf");
            assertEquals("leaf", c.getPassword());
        }

        @Test
        void withNull() {
            Configuration c = givenAnEmpty();
            assertThrows(AssertionError.class, () -> c.setPassword(null));
        }
    }

    @Nested
    class GetPassword {

        @Test
        void happyFlow() {
            Configuration c = Configuration.getInstance().readFromFile(getFile("password/table"));
            assertEquals("table", c.getPassword());
        }

        @Test
        void empty() {
            Configuration c = givenAnEmpty();
            assertEquals("", c.getPassword());
        }
    }

    @Nested
    class IsSaveCredential {

        private Configuration givenSaveCredentialConfiguration(final String file) {
            return Configuration.getInstance().readFromFile(getFile("save/" + file));
        }

        @Test
        void fromEmptyFile() {
            Configuration c = givenAnEmpty();
            assertFalse(c.isSaveCredentialsChecked());
        }

        @Test
        void fromFileTrueValue() {
            Configuration c = givenSaveCredentialConfiguration("true");
            assertTrue(c.isSaveCredentialsChecked());
        }

        @Test
        void fromFileFalseValue() {
            Configuration c = givenSaveCredentialConfiguration("false");
            assertFalse(c.isSaveCredentialsChecked());
        }

        @Test
        void fromFileAnyValue() {
            Configuration c = givenSaveCredentialConfiguration("invalid");
            assertFalse(c.isSaveCredentialsChecked());
        }
    }

    @Nested
    class SetSave {

        @Test
        void withTrueThenFalse() {
            Configuration c = givenAnEmpty();
            c.setSaveCredentialsChecked(true);
            assertTrue(c.isSaveCredentialsChecked());
            c.setSaveCredentialsChecked(false);
            assertFalse(c.isSaveCredentialsChecked());
        }

        @Test
        void swap() {
            Configuration c = givenAnEmpty();
            assertFalse(c.isSaveCredentialsChecked());
            c.swapSaveCredentialsChecked();
            assertTrue(c.isSaveCredentialsChecked());
            c.swapSaveCredentialsChecked();
            assertFalse(c.isSaveCredentialsChecked());

        }
    }

    @Nested
    class SaveFile {

        @Test
        void save() {
            File f = new File("fileToSave.properties");
            Configuration write = Configuration.getInstance().readFromFile(f);
            write.setLogin("aLoginValue");
            write.setPassword("aPwdValue");
            write.setLanguage(LanguageValue.FR);
            write.setSaveCredentialsChecked(true);
            write.save();
            Configuration read = Configuration.getInstance().readFromFile(f);
            assertEquals("aLoginValue", read.getLogin());
            assertEquals("aPwdValue", read.getPassword());
            assertEquals(LanguageValue.FR, read.getLanguage());
            assertTrue(read.isSaveCredentialsChecked());
            f.deleteOnExit();
        }
    }

    @Nested
    class GetLanguage {

        private Configuration givenLanguageConfiguration(final String file) {
            return Configuration.getInstance().readFromFile(getFile("language/" + file));
        }

        @Test
        void english() {
            Configuration c = givenLanguageConfiguration("english");
            assertTrue(c.isLanguagePresent());
            assertEquals(LanguageValue.EN, c.getLanguage());
        }

        @Test
        void french() {
            Configuration c = givenLanguageConfiguration("french");
            assertTrue(c.isLanguagePresent());
            assertEquals(LanguageValue.FR, c.getLanguage());
        }

        @Test
        void invalid() {
            Configuration c = givenLanguageConfiguration("invalid");
            assertTrue(c.isLanguagePresent());
            assertEquals(LanguageValue.EN, c.getLanguage());
        }

        @Test
        void withEmpty() {
            Configuration c = givenAnEmpty();
            assertFalse(c.isLanguagePresent());
            assertEquals(LanguageValue.EN, c.getLanguage());
        }

        @Test
        void withInvalid() throws IOException {
            File f = new File("temp.properties");
            f.createNewFile();
            Properties p = PropertiesHelper.getPropertiesFromFile(f);
            p.setProperty(Configuration.LANGUAGE_KEY, "GF");
            PropertiesHelper.save(p, f);
            Configuration oc = Configuration.getInstance().readFromFile(f);
            assertTrue(oc.isLanguagePresent());
            assertEquals(LanguageValue.EN, oc.getLanguage());
            f.deleteOnExit();
        }
    }

    @Nested
    class SetLanguage {

        @Test
        void french() {
            Configuration c = givenAnEmpty();
            assertFalse(c.isLanguagePresent());
            assertEquals(LanguageValue.EN, c.getLanguage());
            c.setLanguage(LanguageValue.FR);
            assertTrue(c.isLanguagePresent());
            assertEquals(LanguageValue.FR, c.getLanguage());
        }

        @Test
        void withNull() {
            Configuration c = givenAnEmpty();
            assertThrows(NullPointerException.class, () -> c.setLanguage(null));
        }
    }

    @Nested
    class IsDebug {

        private Configuration givenDebugConfiguration(final String file) {
            return Configuration.getInstance().readFromFile(getFile("debug/" + file));
        }

        @Test
        void fromEmptyFile() {
            Configuration c = givenAnEmpty();
            assertFalse(c.isDebug());
        }

        @Test
        void fromFileTrueValue() {
            Configuration c = givenDebugConfiguration("true");
            assertTrue(c.isDebug());
        }

        @Test
        void fromFileFalseValue() {
            Configuration c = givenDebugConfiguration("false");
            assertFalse(c.isDebug());
        }

        @Test
        void fromFileAnyValue() {
            Configuration c = givenDebugConfiguration("invalid");
            assertThrows(IllegalArgumentException.class, c::isDebug);
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
        return Configuration.getInstance().readFromFile(getFile("configEmpty"));
    }*/
}
