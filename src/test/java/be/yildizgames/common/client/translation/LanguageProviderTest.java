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

import be.yildizgames.common.exception.implementation.ImplementationException;
import be.yildizgames.common.util.language.LanguageValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Grégory Van den Borre
 */
public class LanguageProviderTest {

    @Nested
    public class RegisterLanguage {

        @Test
        public void happyFlow() {
            LanguageProvider p = new LanguageProvider();
            p.registerLanguage(LanguageValue.EN);
            Assertions.assertNotNull(p.get(LanguageValue.EN));
        }

        @Test
        public void unregistered() {
            LanguageProvider p = new LanguageProvider();
            Assertions.assertThrows(ImplementationException.class, () -> p.get(LanguageValue.EN));
        }

        @Test
        public void withNull() {
            LanguageProvider p = new LanguageProvider();
            Assertions.assertThrows(ImplementationException.class, () -> p.registerLanguage(null));
        }

    }

    @Nested
    public class AddString {

        @Test
        public void happyFlow() {
            LanguageProvider p = new LanguageProvider();
            p.registerLanguage(LanguageValue.EN);
            p.add("aKey", LanguageValue.EN, "enValue");
            assertEquals("enValue", p.get(LanguageValue.EN).getProperty("aKey"));
        }

        @Test
        public void keyNull() {
            LanguageProvider p = new LanguageProvider();
            assertThrows(ImplementationException.class, () -> p.add(null, LanguageValue.EN, "enValue"));
        }

        @Test
        public void frNull() {
            LanguageProvider p = new LanguageProvider();
            assertThrows(ImplementationException.class, () -> p.add("aKey", null, "enValue"));
        }

        @Test
        public void enNull() {
            LanguageProvider p = new LanguageProvider();
            assertThrows(ImplementationException.class, () -> p.add("aKey", LanguageValue.EN, null));
        }
    }

    @Nested
    public class AddValue {

        @Test
        public void happyFlow() {
            LanguageProvider p = new LanguageProvider();
            p.registerLanguage(LanguageValue.EN);
            TranslatedValue v = new TranslatedValue("aKey", LanguageValue.EN, "enValue");
            p.add(v);
            assertEquals("enValue", p.get(LanguageValue.EN).getProperty("aKey"));
        }

        @Test
        public void valueNull() {
            LanguageProvider p = new LanguageProvider();
            assertThrows(ImplementationException.class, () -> p.add((TranslatedValue)null));
        }
    }

    @Nested
    public class AddProvider {

        @Test
        public void happyFlow() {
            LanguageProvider p = new LanguageProvider();
            p.registerLanguage(LanguageValue.EN);
            TranslatedValueProvider value = new SimpleTranslatedValueProvider(new TranslatedValue("tt", LanguageValue.EN, "vv"));
            p.add(value);
            Assertions.assertEquals("vv", p.get(LanguageValue.EN).getProperty("tt"));
        }

        @Test
        public void valueNull() {
            LanguageProvider p = new LanguageProvider();
            assertThrows(ImplementationException.class, () -> p.add((TranslatedValueProvider) null));
        }
    }

    @Nested
    public class AddProviders {

        @Test
        public void happyFlow() {
            DummyValuesProvider providers = new DummyValuesProvider();
            providers.values.add(new SimpleTranslatedValueProvider(new TranslatedValue("t1", LanguageValue.EN, "v1")));
            providers.values.add(new SimpleTranslatedValueProvider(new TranslatedValue("t2", LanguageValue.EN, "v2")));
            LanguageProvider p = new LanguageProvider();
            p.registerLanguage(LanguageValue.EN);
            p.add(providers);
            Assertions.assertEquals("v1", p.get(LanguageValue.EN).getProperty("t1"));
            Assertions.assertEquals("v2", p.get(LanguageValue.EN).getProperty("t2"));
        }


        @Test
        public void valueNull() {
            LanguageProvider p = new LanguageProvider();
            assertThrows(ImplementationException.class, () -> p.add((TranslatedValuesProvider) null));
        }

        private class DummyValuesProvider implements TranslatedValuesProvider {

            private List<TranslatedValueProvider> values = new ArrayList<>();

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
    public class Get {

        @Test
        public void happyFlow() {
            LanguageProvider p = new LanguageProvider();
            p.registerLanguage(LanguageValue.EN);
            assertNotNull(p.get(LanguageValue.EN));
        }

        @Test
        public void nullParameter() {
            LanguageProvider p = new LanguageProvider();
            assertThrows(ImplementationException.class, () -> p.get(null));
        }
    }
}
