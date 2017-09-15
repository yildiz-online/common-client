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
import be.yildiz.common.language.LanguageValue;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Grégory Van den Borre
 */
class LanguageProviderTest {

    @Nested
    class AddString {

        @Test
        void happyFlow() {
            LanguageProvider p = new LanguageProvider();
            p.add("aKey", "frValue", "enValue");
            assertEquals("frValue", p.get(LanguageValue.FR).getProperty("aKey"));
            assertEquals("enValue", p.get(LanguageValue.EN).getProperty("aKey"));
        }

        @Test
        void keyNull() {
            LanguageProvider p = new LanguageProvider();
            assertThrows(NullPointerException.class, () -> p.add((String)null, "frValue", "enValue"));
        }

        @Test
        void frNull() {
            LanguageProvider p = new LanguageProvider();
            assertThrows(NullPointerException.class, () -> p.add("aKey", (String)null, "enValue"));
        }

        @Test
        void enNull() {
            LanguageProvider p = new LanguageProvider();
            assertThrows(NullPointerException.class, () -> p.add("aKey", "frValue", null));
        }
    }

    @Nested
    class AddValue {

        @Test
        void happyFlow() {
            LanguageProvider p = new LanguageProvider();
            TranslatedValue v = new TranslatedValue("aKey", "frValue", "enValue");
            p.add(v);
            assertEquals("frValue", p.get(LanguageValue.FR).getProperty("aKey"));
            assertEquals("enValue", p.get(LanguageValue.EN).getProperty("aKey"));
        }

        @Test
        void valueNull() {
            LanguageProvider p = new LanguageProvider();
            assertThrows(NullPointerException.class, () -> p.add((TranslatedValue)null));
        }
    }

    @Nested
    class AddProvider {

        @Test
        void happyFlow() {
            LanguageProvider p = new LanguageProvider();
            TranslatedValuesProvider tvp = new TranslatedValuesProvider() {

                List<TranslatedValueProvider> providers = Lists.newList();

                {
                    providers.add(() -> new TranslatedValue("aKey", "frValue", "enValue"));
                    providers.add(() -> new TranslatedValue("aKey2", "frValue2", "enValue2"));
                }
                @Override
                public Iterator<TranslatedValueProvider> iterator() {
                    return providers.iterator();
                }
            };
            p.add(tvp);
            assertEquals("frValue", p.get(LanguageValue.FR).getProperty("aKey"));
            assertEquals("enValue", p.get(LanguageValue.EN).getProperty("aKey"));
            assertEquals("frValue2", p.get(LanguageValue.FR).getProperty("aKey2"));
            assertEquals("enValue2", p.get(LanguageValue.EN).getProperty("aKey2"));
        }

        @Test
        void valueNull() {
            LanguageProvider p = new LanguageProvider();
            assertThrows(NullPointerException.class, () -> p.add((TranslatedValuesProvider) null));
        }
    }

    @Nested
    class Get {

        @Test
        void happyFlow() {
            LanguageProvider p = new LanguageProvider();
            assertNotNull(p.get(LanguageValue.FR));
            assertNotNull(p.get(LanguageValue.EN));
        }

        @Test
        void nullParameter() {
            LanguageProvider p = new LanguageProvider();
            assertThrows(AssertionError.class, () -> p.get(null));
        }
    }
}
