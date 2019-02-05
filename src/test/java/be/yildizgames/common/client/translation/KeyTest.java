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

package be.yildizgames.common.client.translation;

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
public class KeyTest {

    @Nested
    public class GetKey {

        @Test
        public void happyFlow() {
            TranslationKey k = TranslationKey.get("blabla");
            assertEquals("blabla", k.translationKey);
            assertFalse(k.args.length > 0);
        }

        @Test
        public void withNull() {
            assertThrows(IllegalArgumentException.class, () -> TranslationKey.get((String)null));
        }

        @Test
        public void withKey() {
            TranslationKey k1 = TranslationKey.get("k1");
            TranslationKey k2 = TranslationKey.get("k2");
            TranslationKey.MultiKey mk = TranslationKey.get(k1, k2);
            assertEquals(2, mk.keys.size());
            assertTrue(mk.keys.contains(k1) && mk.keys.contains(k2));
        }

        @Test
        public void withKeyList() {
            TranslationKey k1 = TranslationKey.get("k1");
            TranslationKey k2 = TranslationKey.get("k2");
            List<TranslationKey> keys = Arrays.asList(k1, k2);
            TranslationKey.MultiKey mk = TranslationKey.get(keys);
            assertEquals(2, mk.keys.size());
            assertTrue(mk.keys.contains(k1) && mk.keys.contains(k2));
        }

        @Test
        public void withKeyListContainingNull() {
            List<TranslationKey> keys = Arrays.asList(TranslationKey.get("k1"), null);
            assertThrows(IllegalArgumentException.class, () -> TranslationKey.get(keys));
        }

        @Test
        public void withKeyListNull() {
            assertThrows(IllegalArgumentException.class, () -> TranslationKey.get((List<TranslationKey>)null));
        }

        @Test
        public void withKeyListEmpty() {
            assertThrows(IllegalArgumentException.class, () -> TranslationKey.get(new ArrayList<>()));
        }

        @Test
        public void withNullKey() {
            assertThrows(IllegalArgumentException.class, () -> TranslationKey.get((TranslationKey)null));
        }

        @Test
        public void withEmptyKey() {
            assertThrows(IllegalArgumentException.class, TranslationKey::get);
        }

        @Test
        public void withArgs() {
            TranslationKey k = TranslationKey.get("blabla", 1);
            assertEquals("blabla", k.translationKey);
            assertEquals(1, k.args[0]);
        }

        @Test
        public void withArgsContainingNull() {
            Object[] args = {"ok", null};
            assertThrows(IllegalArgumentException.class, () -> TranslationKey.get("blabla", args));
        }

        @Test
        public void withArgsNull() {
            assertThrows(IllegalArgumentException.class, () -> TranslationKey.get("blabla", (Object[]) null));
        }

        @Test
        public void withEmptyArgs() {
            TranslationKey k = TranslationKey.get("blabla", new Object[]{});
            assertEquals("blabla", k.translationKey);
            assertFalse(k.args.length > 0);
        }
    }

    @Nested
    public class Add {

        @Test
        public void happyFlow() {
            TranslationKey k1 = TranslationKey.get("k1");
            TranslationKey k2 = TranslationKey.get("k2");
            TranslationKey k3 = TranslationKey.get("k3");
            TranslationKey.MultiKey mk = TranslationKey.get(k1, k2);
            mk.add(k3);
            assertEquals(3, mk.keys.size());
            assertTrue(mk.keys.contains(k1) && mk.keys.contains(k2) && mk.keys.contains(k3));
        }

        @Test
        public void withNull() {
            TranslationKey k1 = TranslationKey.get("k1");
            TranslationKey k2 = TranslationKey.get("k2");
            TranslationKey.MultiKey mk = TranslationKey.get(k1, k2);
            assertThrows(IllegalArgumentException.class, () -> mk.add(null));
        }

        @Test
        public void withEmpty() {
            TranslationKey k1 = TranslationKey.get("k1");
            TranslationKey k2 = TranslationKey.get("k2");
            TranslationKey k3 = TranslationKey.get("");
            TranslationKey.MultiKey mk = TranslationKey.get(k1, k2);
            mk.add(k3);
            assertEquals(2, mk.keys.size());
            assertTrue(mk.keys.contains(k1) && mk.keys.contains(k2));
        }
    }

    @Nested
    public class HashCode {

        @Test
        public void happyFlow() {
            TranslationKey k1 = TranslationKey.get("k1");
            TranslationKey k2 = TranslationKey.get("k1");
            assertTrue(k1.hashCode() == k2.hashCode());
        }

        @Test
        public void differentValue() {
            TranslationKey k1 = TranslationKey.get("k1");
            TranslationKey k2 = TranslationKey.get("k2");
            assertFalse(k1.hashCode() == k2.hashCode());
        }
    }

    @Nested
    public class Equals {

        @Test
        public void sameInstance() {
            TranslationKey k = TranslationKey.get("k1");
            assertTrue(k.equals(k));
        }

        @Test
        public void sameValue() {
            assertTrue(TranslationKey.get("k1").equals(TranslationKey.get("k1")));
        }

        @Test
        public void differentValue() {
            assertFalse(TranslationKey.get("k1").equals(TranslationKey.get("k2")));
        }

        @Test
        public void withNull() {
            assertFalse(TranslationKey.get("k1").equals(null));
        }

        @Test
        public void differentType() {
            assertFalse(TranslationKey.get("k1").equals("k1"));
        }
    }

    @Test
    public void withEmptyKey() {
        TranslationKey k = TranslationKey.get("");
        assertTrue(k.isEmpty());
        k = TranslationKey.get("a");
        assertFalse(k.isEmpty());
    }



}
