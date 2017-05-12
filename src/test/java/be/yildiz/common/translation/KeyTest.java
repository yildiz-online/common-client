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

package be.yildiz.common.translation;

import be.yildiz.common.collections.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

/**
 * @author Grégory Van den Borre
 */
@RunWith(Enclosed.class)
public class KeyTest {

    public static class GetKey {

        @Test
        public void happyFlow() {
            Key k = Key.get("blabla");
            Assert.assertEquals("blabla", k.translationKey);
            Assert.assertFalse(k.args.length > 0);
        }

        @Test(expected = IllegalArgumentException.class)
        public void withNull() {
            Key.get((String)null);
        }

        @Test
        public void withKey() {
            Key k1 = Key.get("k1");
            Key k2 = Key.get("k2");
            Key.MultiKey mk = Key.get(k1, k2);
            Assert.assertEquals(2, mk.keys.size());
            Assert.assertTrue(mk.keys.contains(k1) && mk.keys.contains(k2));
        }

        @Test
        public void withKeyList() {
            Key k1 = Key.get("k1");
            Key k2 = Key.get("k2");
            List<Key> keys = Arrays.asList(k1, k2);
            Key.MultiKey mk = Key.get(keys);
            Assert.assertEquals(2, mk.keys.size());
            Assert.assertTrue(mk.keys.contains(k1) && mk.keys.contains(k2));
        }

        @Test(expected = IllegalArgumentException.class)
        public void withKeyListContainingNull() {
            List<Key> keys = Arrays.asList(Key.get("k1"), null);
            Key.get(keys);
        }

        @Test(expected = IllegalArgumentException.class)
        public void withKeyListNull() {
            Key.get((List<Key>)null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void withKeyListEmpty() {
            Key.get(Lists.newList());
        }

        @Test(expected = IllegalArgumentException.class)
        public void withNullKey() {
            Key.get((Key)null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void withEmptyKey() {
            Key.get();
        }

        @Test
        public void withArgs() {
            Key k = Key.get("blabla", 1);
            Assert.assertEquals("blabla", k.translationKey);
            Assert.assertEquals(1, k.args[0]);
        }

        @Test(expected = IllegalArgumentException.class)
        public void withArgsContainingNull() {
            Object[] args = {"ok", null};
            Key.get("blabla", args);
        }

        @Test(expected = IllegalArgumentException.class)
        public void withArgsNull() {
            Key.get("blabla", null);
        }

        @Test
        public void withEmptyArgs() {
            Key k = Key.get("blabla", new Object[]{});
            Assert.assertEquals("blabla", k.translationKey);
            Assert.assertFalse(k.args.length > 0);
        }
    }

    public static class Add {

        @Test
        public void happyFlow() {
            Key k1 = Key.get("k1");
            Key k2 = Key.get("k2");
            Key k3 = Key.get("k3");
            Key.MultiKey mk = Key.get(k1, k2);
            mk.add(k3);
            Assert.assertEquals(3, mk.keys.size());
            Assert.assertTrue(mk.keys.contains(k1) && mk.keys.contains(k2) && mk.keys.contains(k3));
        }

        @Test(expected = IllegalArgumentException.class)
        public void withNull() {
            Key k1 = Key.get("k1");
            Key k2 = Key.get("k2");
            Key.MultiKey mk = Key.get(k1, k2);
            mk.add(null);
        }

        @Test
        public void withEmpty() {
            Key k1 = Key.get("k1");
            Key k2 = Key.get("k2");
            Key k3 = Key.get("");
            Key.MultiKey mk = Key.get(k1, k2);
            mk.add(k3);
            Assert.assertEquals(2, mk.keys.size());
            Assert.assertTrue(mk.keys.contains(k1) && mk.keys.contains(k2));
        }
    }

    public static class HashCode {

        @Test
        public void happyFlow() {
            Key k1 = Key.get("k1");
            Key k2 = Key.get("k1");
            Assert.assertTrue(k1.hashCode() == k2.hashCode());
        }

        @Test
        public void differentValue() {
            Key k1 = Key.get("k1");
            Key k2 = Key.get("k2");
            Assert.assertFalse(k1.hashCode() == k2.hashCode());
        }
    }

    public static class Equals {

        @Test
        public void sameInstance() {
            Key k = Key.get("k1");
            Assert.assertTrue(k.equals(k));
        }

        @Test
        public void sameValue() {
            Assert.assertTrue(Key.get("k1").equals(Key.get("k1")));
        }

        @Test
        public void differentValue() {
            Assert.assertFalse(Key.get("k1").equals(Key.get("k2")));
        }

        @Test
        public void withNull() {
            Assert.assertFalse(Key.get("k1").equals(null));
        }

        @Test
        public void differentType() {
            Assert.assertFalse(Key.get("k1").equals("k1"));
        }
    }

    @Test
    public void withEmptyKey() {
        Key k = Key.get("");
        Assert.assertTrue(k.isEmpty());
        k = Key.get("a");
        Assert.assertFalse(k.isEmpty());
    }



}
