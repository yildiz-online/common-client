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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A TranslationKey is a value meant to be translated, it is composed of a key, and optional arguments.
 * <p>
 * The class is mutable to improve performances, but only accessible from its package.
 *
 * @author Grégory Van den Borre

 * specfield key:String:TranslationKey to translate.
 * specfield args:Object[]:Optional arguments.
 */
public class TranslationKey {

    /**
     * TranslationKey to translate.
     */
    final String translationKey;

    /**
     * Optional arguments.
     */
    final Object[] args;

    /**
     * Create a new instance with a key, this.args will be empty.
     *
     * @param key Value to translate.
     */
     //@Ensures("this.key==key")
     //@Ensures("this.args==new Object[0]")
    private TranslationKey(final String key) {
        this(key, new Object[0]);
    }

    /**
     * Create a new instance with a key and arguments, this.args will be empty if the args param is empty.
     *
     * @param key  Value to translate.
     * @param args Arguments to add to translation.
     */
    //@Invariant("keys != null")
    //@Invariant("args != null")
    private TranslationKey(final String key, final Object... args) {
        super();
        this.translationKey = key;
        this.args = args;
        assert this.invariant();
    }

    private boolean invariant() {
        if(this.translationKey == null) {
            throw new IllegalArgumentException("Translation key should not be null.");
        }
        if(this.args == null) {
            throw new IllegalArgumentException("Args should not be null.");
        }
        for(Object o: this.args) {
            if(o == null) {
                throw new IllegalArgumentException("Args should not contains null.");
            }
        }
        return true;
    }

    /**
     * Create a new instance of TranslationKey with a key and no arguments.
     *
     * @param key TranslationKey to translate.
     * @return The build TranslationKey.
     */
    public static TranslationKey get(final String key) {
        return new TranslationKey(key);
    }

    /**
     * Create a new instance of TranslationKey with a key and arguments.
     *
     * @param key  TranslationKey to translate.
     * @param args Arguments to use for translation.
     * @return The build TranslationKey.
     */
    public static TranslationKey get(String key, Object... args) {
        return new TranslationKey(key, args);
    }

    /**
     * Create a new instance of MultiKey to use several translation at once.
     *
     * @param keys Keys to translate, must be at least one, and no null values is allowed.
     * @return The build MultiKey.
     */
    public static MultiKey get(final TranslationKey... keys) {
        return new MultiKey(keys);
    }

    public static MultiKey get(final List<TranslationKey> keys) {
        return new MultiKey(keys);
    }

    /**
     * Check if the key is empty.
     * @return <code>true</code> if the key is empty.
     */
    public boolean isEmpty() {
        return "".equals(this.translationKey);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TranslationKey)) {
            return false;
        }

        TranslationKey key = (TranslationKey) o;

        if (!translationKey.equals(key.translationKey)) {
            return false;
        }
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(args, key.args);
    }

    @Override
    public int hashCode() {
        int result = translationKey.hashCode();
        result = 31 * result + Arrays.hashCode(args);
        return result;
    }

    /**
     * A multi key is composed of several keys to provide the possibility to use several translation in one go.
     *
     * @author Van den Borre Grégory
     * specfield keys:List of TranslationKey:contains the different keys, null values not allowed.
     */
    //@Invariant("keys.size() > 0")
    //@Invariant("!keys.contains(null)")
    public static final class MultiKey {

        /**
         * List of keys composing this multikey, null not allowed.
         */
        final List<TranslationKey> keys;

        /**
         * Create a new MultiKey instance, must contains at least one key, and no null values.
         *
         * @param keys Keys to add.
         */
        private MultiKey(final TranslationKey... keys) {
            super();
            this.keys = new ArrayList<>();
            for(TranslationKey k : keys) {
                this.add(k);
            }
            assert this.invariant();
        }

        private MultiKey(final List<TranslationKey> l) {
            super();
            if (l == null) {
                throw new IllegalArgumentException("List is null");
            }
            this.keys = new ArrayList<>();
            for(TranslationKey k : l) {
                this.add(k);
            }
            assert this.invariant();
        }

        /**
         * Add a new TranslationKey to this MultiKey
         *
         * @param key key to add, null is not allowed.
         */
        public void add(final TranslationKey key) {
            if (key == null) {
                throw new IllegalArgumentException("key is null");
            }
            if (!key.isEmpty()) {
                this.keys.add(key);
            }
            assert this.invariant();
        }

        /**
         * Invariant, only called if assertions are enabled.
         *
         * @return <code>true</code>.
         * @throws AssertionError if the invariant is broken in any way.
         */
        private boolean invariant() {
            if (this.keys.isEmpty()) {
                throw new IllegalArgumentException("Keys is empty.");
            }
            if (this.keys.contains(null)) {
                throw new IllegalArgumentException("null value not allowed.");
            }
            return true;
        }
    }
}
