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

package be.yildiz.common.translation;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Grégory Van den Borre
 */
class KeyTest {

    @Nested
    class GetKey {

        @Test
        void happyFlow() {
            Key k = Key.get("blabla");
            assertEquals("blabla", k.translationKey);
            assertFalse(k.args.length > 0);
        }

        @Test
        void withNull() {
            assertThrows(IllegalArgumentException.class, () -> Key.get((String)null));
        }

        @Test
        void withKey() {
            Key k1 = Key.get("k1");
            Key k2 = Key.get("k2");
            Key.MultiKey mk = Key.get(k1, k2);
            assertEquals(2, mk.keys.size());
            assertTrue(mk.keys.contains(k1) && mk.keys.contains(k2));
        }

        @Test
        void withKeyList() {
            Key k1 = Key.get("k1");
            Key k2 = Key.get("k2");
            List<Key> keys = Arrays.asList(k1, k2);
            Key.MultiKey mk = Key.get(keys);
            assertEquals(2, mk.keys.size());
            assertTrue(mk.keys.contains(k1) && mk.keys.contains(k2));
        }

        @Test
        void withKeyListContainingNull() {
            List<Key> keys = Arrays.asList(Key.get("k1"), null);
            assertThrows(IllegalArgumentException.class, () -> Key.get(keys));
        }

        @Test
        void withKeyListNull() {
            assertThrows(IllegalArgumentException.class, () -> Key.get((List<Key>)null));
        }

        @Test
        void withKeyListEmpty() {
            assertThrows(IllegalArgumentException.class, () -> Key.get(new ArrayList<>()));
        }

        @Test
        void withNullKey() {
            assertThrows(IllegalArgumentException.class, () -> Key.get((Key)null));
        }

        @Test
        void withEmptyKey() {
            assertThrows(IllegalArgumentException.class, Key::get);
        }

        @Test
        void withArgs() {
            Key k = Key.get("blabla", 1);
            assertEquals("blabla", k.translationKey);
            assertEquals(1, k.args[0]);
        }

        @Test
        void withArgsContainingNull() {
            Object[] args = {"ok", null};
            assertThrows(IllegalArgumentException.class, () -> Key.get("blabla", args));
        }

        @Test
        void withArgsNull() {
            assertThrows(IllegalArgumentException.class, () -> Key.get("blabla", (Object[]) null));
        }

        @Test
        void withEmptyArgs() {
            Key k = Key.get("blabla", new Object[]{});
            assertEquals("blabla", k.translationKey);
            assertFalse(k.args.length > 0);
        }
    }

    @Nested
    class Add {

        @Test
        void happyFlow() {
            Key k1 = Key.get("k1");
            Key k2 = Key.get("k2");
            Key k3 = Key.get("k3");
            Key.MultiKey mk = Key.get(k1, k2);
            mk.add(k3);
            assertEquals(3, mk.keys.size());
            assertTrue(mk.keys.contains(k1) && mk.keys.contains(k2) && mk.keys.contains(k3));
        }

        @Test
        void withNull() {
            Key k1 = Key.get("k1");
            Key k2 = Key.get("k2");
            Key.MultiKey mk = Key.get(k1, k2);
            assertThrows(IllegalArgumentException.class, () -> mk.add(null));
        }

        @Test
        void withEmpty() {
            Key k1 = Key.get("k1");
            Key k2 = Key.get("k2");
            Key k3 = Key.get("");
            Key.MultiKey mk = Key.get(k1, k2);
            mk.add(k3);
            assertEquals(2, mk.keys.size());
            assertTrue(mk.keys.contains(k1) && mk.keys.contains(k2));
        }
    }

    @Nested
    class HashCode {

        @Test
        void happyFlow() {
            Key k1 = Key.get("k1");
            Key k2 = Key.get("k1");
            assertTrue(k1.hashCode() == k2.hashCode());
        }

        @Test
        void differentValue() {
            Key k1 = Key.get("k1");
            Key k2 = Key.get("k2");
            assertFalse(k1.hashCode() == k2.hashCode());
        }
    }

    @Nested
    class Equals {

        @Test
        void sameInstance() {
            Key k = Key.get("k1");
            assertTrue(k.equals(k));
        }

        @Test
        void sameValue() {
            assertTrue(Key.get("k1").equals(Key.get("k1")));
        }

        @Test
        void differentValue() {
            assertFalse(Key.get("k1").equals(Key.get("k2")));
        }

        @Test
        void withNull() {
            assertFalse(Key.get("k1").equals(null));
        }

        @Test
        void differentType() {
            assertFalse(Key.get("k1").equals("k1"));
        }
    }

    @Test
    void withEmptyKey() {
        Key k = Key.get("");
        assertTrue(k.isEmpty());
        k = Key.get("a");
        assertFalse(k.isEmpty());
    }



}
