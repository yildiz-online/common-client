package be.yildiz.common.translation;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author Grégory Van den Borre
 */
@RunWith(Enclosed.class)
public class TranslatedValueTest {

    public static class Constructor {

        @Test
        public void happyFlow() {
            TranslatedValue v = new TranslatedValue("aKey", "frValue", "enValue");
            Assert.assertEquals("aKey", v.getKey());
            Assert.assertEquals("frValue", v.getFrench());
            Assert.assertEquals("enValue", v.getEnglish());
        }

        @Test(expected = NullPointerException.class)
        public void withNullKey() {
            new TranslatedValue(null, "frValue", "enValue");
        }

        @Test(expected = NullPointerException.class)
        public void withNullFrench() {
            new TranslatedValue("aKey", null, "enValue");
        }

        @Test(expected = NullPointerException.class)
        public void withNullEnglish() {
            new TranslatedValue("aKey", "frValue", null);
        }

    }


}