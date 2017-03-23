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

import java.util.Iterator;
import java.util.List;

/**
 * @author Grégory Van den Borre
 */
@RunWith(Enclosed.class)
public class LanguageProviderTest {

    public static class AddString {

        @Test
        public void happyFlow() {
            LanguageProvider p = new LanguageProvider();
            p.add("aKey", "frValue", "enValue");
            Assert.assertEquals("frValue", p.get(Language.FR).getProperty("aKey"));
            Assert.assertEquals("enValue", p.get(Language.EN).getProperty("aKey"));
        }

        @Test(expected = NullPointerException.class)
        public void keyNull() {
            LanguageProvider p = new LanguageProvider();
            p.add(null, "frValue", "enValue");
        }

        @Test(expected = NullPointerException.class)
        public void frNull() {
            LanguageProvider p = new LanguageProvider();
            p.add("aKey", null, "enValue");
        }

        @Test(expected = NullPointerException.class)
        public void enNull() {
            LanguageProvider p = new LanguageProvider();
            p.add("aKey", "frValue", null);
        }
    }

    public static class AddValue {

        @Test
        public void happyFlow() {
            LanguageProvider p = new LanguageProvider();
            TranslatedValue v = new TranslatedValue("aKey", "frValue", "enValue");
            p.add(v);
            Assert.assertEquals("frValue", p.get(Language.FR).getProperty("aKey"));
            Assert.assertEquals("enValue", p.get(Language.EN).getProperty("aKey"));
        }

        @Test(expected = NullPointerException.class)
        public void valueNull() {
            LanguageProvider p = new LanguageProvider();
            p.add((TranslatedValue)null);
        }
    }

    public static class AddProvider {

        @Test
        public void happyFlow() {
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
            Assert.assertEquals("frValue", p.get(Language.FR).getProperty("aKey"));
            Assert.assertEquals("enValue", p.get(Language.EN).getProperty("aKey"));
            Assert.assertEquals("frValue2", p.get(Language.FR).getProperty("aKey2"));
            Assert.assertEquals("enValue2", p.get(Language.EN).getProperty("aKey2"));
        }

        @Test(expected = NullPointerException.class)
        public void valueNull() {
            LanguageProvider p = new LanguageProvider();
            p.add((TranslatedValuesProvider) null);
        }
    }

    public static class Get {

        @Test
        public void happyFlow() {
            LanguageProvider p = new LanguageProvider();
            Assert.assertNotNull(p.get(Language.FR));
            Assert.assertNotNull(p.get(Language.EN));
        }

        @Test(expected = AssertionError.class)
        public void nullParameter() {
            LanguageProvider p = new LanguageProvider();
            p.get(null);
        }
    }
}
