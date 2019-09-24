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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
            TranslationKey k = TranslationKey.get("blabla");
            assertEquals("blabla", k.key);
            assertFalse(k.args.length > 0);
        }

        @Test
        void withNull() {
            assertThrows(NullPointerException.class, () -> TranslationKey.get((String)null));
        }

        @Test
        void withKey() {
            TranslationKey k1 = TranslationKey.get("k1");
            TranslationKey k2 = TranslationKey.get("k2");
            TranslationKey.MultiKey mk = TranslationKey.get(k1, k2);
            assertEquals(2, mk.keys.size());
            assertTrue(mk.keys.contains(k1) && mk.keys.contains(k2));
        }

        @Test
        void withKeyList() {
            TranslationKey k1 = TranslationKey.get("k1");
            TranslationKey k2 = TranslationKey.get("k2");
            List<TranslationKey> keys = Arrays.asList(k1, k2);
            TranslationKey.MultiKey mk = TranslationKey.get(keys);
            assertEquals(2, mk.keys.size());
            assertTrue(mk.keys.contains(k1) && mk.keys.contains(k2));
        }

        @Test
        void withKeyListContainingNull() {
            List<TranslationKey> keys = Arrays.asList(TranslationKey.get("k1"), null);
            assertThrows(NullPointerException.class, () -> TranslationKey.get(keys));
        }

        @Test
        void withKeyListNull() {
            assertThrows(NullPointerException.class, () -> TranslationKey.get((List<TranslationKey>)null));
        }

        @Test
        void withNullKey() {
            assertThrows(NullPointerException.class, () -> TranslationKey.get((TranslationKey)null));
        }

        @Test
        void withArgs() {
            TranslationKey k = TranslationKey.get("blabla", 1);
            assertEquals("blabla", k.key);
            assertEquals(1, k.args[0]);
        }

        @Test
        void withArgsContainingNull() {
            Object[] args = {"ok", null};
            assertThrows(NullPointerException.class, () -> TranslationKey.get("blabla", args));
        }

        @Test
        void withArgsNull() {
            assertThrows(NullPointerException.class, () -> TranslationKey.get("blabla", (Object[]) null));
        }

        @Test
        void withEmptyArgs() {
            TranslationKey k = TranslationKey.get("blabla", new Object[]{});
            assertEquals("blabla", k.key);
            assertFalse(k.args.length > 0);
        }
    }

    @Nested
    class Add {

        @Test
        void happyFlow() {
            TranslationKey k1 = TranslationKey.get("k1");
            TranslationKey k2 = TranslationKey.get("k2");
            TranslationKey k3 = TranslationKey.get("k3");
            TranslationKey.MultiKey mk = TranslationKey.get(k1, k2);
            mk.add(k3);
            assertEquals(3, mk.keys.size());
            assertTrue(mk.keys.contains(k1) && mk.keys.contains(k2) && mk.keys.contains(k3));
        }

        @Test
        void withNull() {
            TranslationKey k1 = TranslationKey.get("k1");
            TranslationKey k2 = TranslationKey.get("k2");
            TranslationKey.MultiKey mk = TranslationKey.get(k1, k2);
            assertThrows(NullPointerException.class, () -> mk.add(null));
        }

        @Test
        void withEmpty() {
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
    class HashCode {

        @Test
        void happyFlow() {
            TranslationKey k1 = TranslationKey.get("k1");
            TranslationKey k2 = TranslationKey.get("k1");
            assertEquals(k1.hashCode(), k2.hashCode());
        }

        @Test
        void differentValue() {
            TranslationKey k1 = TranslationKey.get("k1");
            TranslationKey k2 = TranslationKey.get("k2");
            assertNotEquals(k1.hashCode(), k2.hashCode());
        }
    }

    @Nested
    class Equals {

        @Test
        void sameInstance() {
            TranslationKey k = TranslationKey.get("k1");
            assertEquals(k, k);
        }

        @Test
        void sameValue() {
            assertEquals(TranslationKey.get("k1"), TranslationKey.get("k1"));
        }

        @Test
        void differentValue() {
            assertNotEquals(TranslationKey.get("k1"), TranslationKey.get("k2"));
        }

        @Test
        void withNull() {
            assertNotEquals(null, TranslationKey.get("k1"));
        }

        @Test
        void differentType() {
            assertNotEquals("k1", TranslationKey.get("k1"));
        }
    }

    @Test
    void withEmptyKey() {
        TranslationKey k = TranslationKey.get("");
        assertTrue(k.isEmpty());
        k = TranslationKey.get("a");
        assertFalse(k.isEmpty());
    }



}
