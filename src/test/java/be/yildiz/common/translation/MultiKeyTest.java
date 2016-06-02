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

package be.yildiz.common.translation;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import be.yildiz.common.translation.Key.MultiKey;

/**
 * @author Grégory Van den Borre
 */
public class MultiKeyTest {
	
	@BeforeClass
	public static void enableAssert() {
		ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
	}

    @Rule
    public ExpectedException rule = ExpectedException.none();
    
    @Test
    public void TestMultiKey() {
        Key k = Key.get("blabla");
        Key k2 = Key.get("blablabla");
        MultiKey keys = Key.get(k, k, k2);
        Assert.assertTrue(keys.keys.contains(k));
        Assert.assertTrue(keys.keys.contains(k2));
        Assert.assertEquals(3, keys.keys.size());
    }
    
    @Test
    public void TestMultiKeyEmpty() {
        Key[] k = {};
        this.rule.expect(IllegalArgumentException.class);
        Key.get(k);
    }
    
    @Test
    public void TestMultiKeyNull() {
        Key[] k = {Key.get("blablabla"), null};
        this.rule.expect(IllegalArgumentException.class);
        Key.get(k);
    }
    
    @Test
    public void TestAdd() {
        Key k = Key.get("blabla");
        Key k2 = Key.get("blablabla");
        Key k3 = Key.get("azerty");
        MultiKey keys = Key.get(k, k, k2);
        Assert.assertFalse(keys.keys.contains(k3));
        keys.add(k3);
        Assert.assertEquals(4, keys.keys.size());
        Assert.assertTrue(keys.keys.contains(k3));
    }
    
    @Test
    public void TestAddNull() {
        Key k = Key.get("blabla");
        Key k2 = Key.get("blablabla");
        MultiKey keys = Key.get(k, k2);
        this.rule.expect(IllegalArgumentException.class);
        keys.add(null);
    }
}
