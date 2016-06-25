//        This file is part of the Yildiz-Online project, licenced under the MIT License
//        (MIT)
//
//        Copyright (c) 2016 Grégory Van den Borre
//
//        More infos available: http://yildiz.bitbucket.org
//
//        Permission is hereby granted, free of charge, to any person obtaining a copy
//        of this software and associated documentation files (the "Software"), to deal
//        in the Software without restriction, including without limitation the rights
//        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//        copies of the Software, and to permit persons to whom the Software is
//        furnished to do so, subject to the following conditions:
//
//        The above copyright notice and this permission notice shall be included in all
//        copies or substantial portions of the Software.
//
//        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//        SOFTWARE.

package be.yildiz.common.config;

import be.yildiz.common.resource.PropertiesHelper;
import be.yildiz.common.translation.Language;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Properties;

/**
 * @author Grégory Van den Borre
 */
public final class ConfigurationTest {

    private File f;

    private Configuration c;

    @Before
    public void initMethod() {
        this.f = new File("td");
        this.c = Configuration.readFromFile(f);
    }

    @After
    public void afterMethod() {
        this.f.delete();
    }

    @Test
    public void testGetLoginNotFound() {
        Assert.assertEquals("", c.getLogin());
    }

    @Test
    public void testGetSetLogin() {
        final String value = "blabla";
        c.setLogin(value);
        Assert.assertEquals(value, c.getLogin());
    }

    @Test(expected = NullPointerException.class)
    public void testSetLoginNull() {
        c.setLogin(null);
    }

    @Test
    public void testGetPasswordNotFound() {
        Assert.assertEquals("", c.getPassword());
    }

    @Test(expected = NullPointerException.class)
    public void testSetPasswordNull() {
        c.setPassword(null);
    }

    @Test
    public void testGetSetPassword() {
        final String value = "blabla";
        c.setPassword(value);
        Assert.assertEquals(value, c.getPassword());
    }

    @Test
    public void testIsSaveCredentialsChecked() {
        Assert.assertFalse(c.isSaveCredentialsChecked());
    }

    @Test
    public void testGetSetSwapSaveCredentialsChecked() {
        Configuration c = Configuration.readFromFile(f);
        Assert.assertFalse(c.isSaveCredentialsChecked());
        c.setSaveCredentialsChecked(true);
        Assert.assertTrue(c.isSaveCredentialsChecked());
        c.setSaveCredentialsChecked(false);
        Assert.assertFalse(c.isSaveCredentialsChecked());
        c.swapSaveCredentialsChecked();
        Assert.assertTrue(c.isSaveCredentialsChecked());
        c.swapSaveCredentialsChecked();
        Assert.assertFalse(c.isSaveCredentialsChecked());
    }

    @Test
    public void testSaveAndReadFromFile() {
        final String login = "blabla";
        final String password = "blablapass";
        c.setLogin(login);
        c.setPassword(password);
        c.setLanguage(Language.FR);
        c.setSaveCredentialsChecked(true);
        c.save();
        Configuration c2 = Configuration.readFromFile(f);
        Assert.assertEquals(login, c2.getLogin());
        Assert.assertEquals(password, c2.getPassword());
        Assert.assertTrue(c2.isSaveCredentialsChecked());
        Assert.assertEquals(Language.FR, c2.getLanguage());
    }

    @Test
    public void testGetLanguage() {
        Assert.assertFalse(c.isLanguagePresent());
        Assert.assertEquals(Language.EN, c.getLanguage());
    }

    @Test(expected = NullPointerException.class)
    public void testSetLanguageNull() {
        c.setLanguage(null);
    }

    @Test
    public void testGetSetLanguage() {
        Assert.assertFalse(c.isLanguagePresent());
        c.setLanguage(Language.FR);
        Assert.assertTrue(c.isLanguagePresent());
        Assert.assertEquals(Language.FR, c.getLanguage());
    }

    @Test
    public void testGetSetInvalidLanguage() {
        Properties p = PropertiesHelper.getPropertiesFromFile(f);
        p.setProperty(Configuration.LANGUAGE_KEY, "GF");
        PropertiesHelper.save(p, f);
        Configuration oc = Configuration.readFromFile(f);
        Assert.assertTrue(oc.isLanguagePresent());
        Assert.assertEquals(Language.EN, oc.getLanguage());
    }

    @Test
    public void testIsDebug() {
        Assert.assertFalse(c.isDebug());
    }
}
