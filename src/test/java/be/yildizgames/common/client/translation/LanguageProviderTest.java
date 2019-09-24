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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Grégory Van den Borre
 */
class LanguageProviderTest {

    @Nested
    class RegisterLanguage {

        @Test
        void happyFlow() {
            LanguageProvider p = new LanguageProvider();
            p.registerLanguage(Locale.ENGLISH);
            Assertions.assertNotNull(p.get(Locale.ENGLISH));
        }

        @Test
        void unregistered() {
            LanguageProvider p = new LanguageProvider();
            Assertions.assertThrows(NullPointerException.class, () -> p.get(Locale.ENGLISH));
        }

        @Test
        void withNull() {
            LanguageProvider p = new LanguageProvider();
            Assertions.assertThrows(NullPointerException.class, () -> p.registerLanguage(null));
        }

    }

    @Nested
    class AddString {

        @Test
        void happyFlow() {
            LanguageProvider p = new LanguageProvider();
            p.registerLanguage(Locale.ENGLISH);
            p.add("aKey", Locale.ENGLISH, "enValue");
            assertEquals("enValue", p.get(Locale.ENGLISH).getProperty("aKey"));
        }

        @Test
        void keyNull() {
            LanguageProvider p = new LanguageProvider();
            assertThrows(NullPointerException.class, () -> p.add(null, Locale.ENGLISH, "enValue"));
        }

        @Test
        void frNull() {
            LanguageProvider p = new LanguageProvider();
            assertThrows(NullPointerException.class, () -> p.add("aKey", null, "enValue"));
        }

        @Test
        void enNull() {
            LanguageProvider p = new LanguageProvider();
            assertThrows(NullPointerException.class, () -> p.add("aKey", Locale.ENGLISH, null));
        }
    }

    @Nested
    class AddValue {

        @Test
        void happyFlow() {
            LanguageProvider p = new LanguageProvider();
            p.registerLanguage(Locale.ENGLISH);
            TranslatedValue v = new TranslatedValue("aKey", Locale.ENGLISH, "enValue");
            p.add(v);
            assertEquals("enValue", p.get(Locale.ENGLISH).getProperty("aKey"));
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
            p.registerLanguage(Locale.ENGLISH);
            TranslatedValueProvider value = new SimpleTranslatedValueProvider(new TranslatedValue("tt", Locale.ENGLISH, "vv"));
            p.add(value);
            Assertions.assertEquals("vv", p.get(Locale.ENGLISH).getProperty("tt"));
        }

        @Test
        void valueNull() {
            LanguageProvider p = new LanguageProvider();
            assertThrows(NullPointerException.class, () -> p.add((TranslatedValueProvider) null));
        }
    }

    @Nested
    class AddProviders {

        @Test
        void happyFlow() {
            DummyValuesProvider providers = new DummyValuesProvider();
            providers.values.add(new SimpleTranslatedValueProvider(new TranslatedValue("t1", Locale.ENGLISH, "v1")));
            providers.values.add(new SimpleTranslatedValueProvider(new TranslatedValue("t2", Locale.ENGLISH, "v2")));
            LanguageProvider p = new LanguageProvider();
            p.registerLanguage(Locale.ENGLISH);
            p.add(providers);
            Assertions.assertEquals("v1", p.get(Locale.ENGLISH).getProperty("t1"));
            Assertions.assertEquals("v2", p.get(Locale.ENGLISH).getProperty("t2"));
        }


        @Test
        void valueNull() {
            LanguageProvider p = new LanguageProvider();
            assertThrows(NullPointerException.class, () -> p.add((TranslatedValuesProvider) null));
        }

        private class DummyValuesProvider implements TranslatedValuesProvider {

            private final List<TranslatedValueProvider> values = new ArrayList<>();

            @Override
            public Iterator<TranslatedValueProvider> iterator() {
                return values.iterator();
            }

            @Override
            public void forEach(Consumer<? super TranslatedValueProvider> action) {
                values.forEach(action);
            }
        }
    }

    @Nested
    class Get {

        @Test
        void happyFlow() {
            LanguageProvider p = new LanguageProvider();
            p.registerLanguage(Locale.ENGLISH);
            assertNotNull(p.get(Locale.ENGLISH));
        }

        @Test
        void nullParameter() {
            LanguageProvider p = new LanguageProvider();
            assertThrows(NullPointerException.class, () -> p.get(null));
        }
    }
}
