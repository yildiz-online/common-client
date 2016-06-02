//        This file is part of the Yildiz-Online project, licenced under the MIT License
//        (MIT)
//
//        Copyright (c) 2016 Grégory Van den Borre
//
//        More infos available: http://yildiz.bitbucket.org
//
//        Permission is hereby granted, free of charge, to any person obtaining a copy
//        of this software and associated documentation files (the "Software"), to deal
//        in the Software without restriction, including without limitation the rights
//        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//        copies of the Software, and to permit persons to whom the Software is
//        furnished to do so, subject to the following conditions:
//
//        The above copyright notice and this permission notice shall be included in all
//        copies or substantial portions of the Software.
//
//        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//        SOFTWARE.

package be.yildiz.common.translation;

import java.util.List;
import java.util.Optional;

import be.yildiz.common.collections.Lists;
import lombok.EqualsAndHashCode;

/**
 * A Key is a value meant to be translated, it is composed of a key, and optional arguments.
 * 
 * The class is mutable to improve performances, but only accessible from its package.
 * 
 * @mutable
 * 
 * @specfield key:String:Key to translate.
 * @specfield args:Object[]:Optional arguments.
 * 
 * @author Grégory Van den Borre
 *
 */
@EqualsAndHashCode
public class Key {

    /**
     * Key to translate.
     */
    final String translationKey;

    /**
     * Optional arguments.
     */
    final Optional<Object[]> args;

    /**
     * Create a new instance with a key, this.args will be empty.
     * 
     * @param key
     *            Value to translate.
     * 
     * @ensures this.key==key
     * @ensures this.args==Optional.empty()
     */
    private Key(final String key) {
        super();
        this.translationKey = key;
        this.args = Optional.empty();
    }

    /**
     * Create a new instance with a key and arguments, this.args will be empty if the args param is empty.
     * 
     * @param key
     *            Value to translate.
     * @param args
     *            Arguments to add to translation.
     * 
     * @ensures this.key==key
     * @ensures this.args==this.args.length==0?Optional.empty():Optional.of(args)
     */
    private Key(final String key, final Object... args) {
        super();
        this.translationKey = key;
        if (args.length == 0) {
            this.args = Optional.empty();
        } else {
            this.args = Optional.of(args);
        }
    }

    /**
     * Create a new instance of Key with a key and no arguments.
     * 
     * @param key
     *            Key to translate.
     * @return The build Key.
     */
    public static Key get(final String key) {
        return new Key(key);
    }

    /**
     * Create a new instance of Key with a key and arguments.
     * 
     * @param key
     *            Key to translate.
     * @param args
     *            Arguments to use for translation.
     * @return The build Key.
     */
    public static Key get(String key, Object... args) {
        return new Key(key, args);
    }

    /**
     * Create a new instance of MultiKey to use several translation at once.
     * 
     * @param keys
     *            Keys to translate, must be at least one, and no null values is allowed.
     * @return The build MultiKey.
     */
    public static MultiKey get(final Key... keys) {
        return new MultiKey(keys);
    }

    public boolean isEmpty() {
        return this.translationKey.equals("");
    }

    /**
     * A multi key is composed of several keys to provide the possibility to use several translation in one go.
     * 
     * @mutable
     * 
     * @specfield keys:List<Key>:contains the different keys, null values not allowed.
     * 
     * @invariant keys.size() > 0
     * @invariant !keys.contains(null)
     * 
     * @author Van den Borre Grégory
     *
     */
    public static final class MultiKey {

        /**
         * List of keys composing this multikey, null not allowed.
         */
        final List<Key> keys;

        /**
         * Create a new MultiKey instance, must contains at least one key, and no null values.
         * 
         * @param keys
         *            Keys to add.
         */
        private MultiKey(final Key... keys) {
            super();
            this.keys = Lists.newList(keys);
            assert this.invariant();
        }

        /**
         * Add a new Key to this MultiKey
         * 
         * @param key
         *            key to add, null is not allowed.
         */
        public void add(final Key key) {
            if(key == null) {
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
         * @throws AssertionError
         *             if the invariant is broken in any way.
         * @return <code>true</code>.
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

        public boolean isEmpty() {
            return this.keys.isEmpty();
        }
    }
}
