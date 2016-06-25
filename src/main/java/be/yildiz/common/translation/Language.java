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

/**
 * Enumeration for possible languages.
 *
 * @author Grégory Van den Borre
 * @specfield description:String:Language translated in its own language.
 * @specfield value:int:Language unique index value.
 * @invariant description != null.
 * @invariant value >= 0.
 * @immutable
 */
public enum Language {

    /**
     * English language.
     */
    EN("English", 0),

    /**
     * French language.
     */
    FR("Français", 1);

    /**
     * Language name in its own translation.
     */
    public final String description;

    /**
     * Associated value.
     */
    public final int value;

    Language(final String description, final int value) {
        this.description = description;
        this.value = value;
        assert this.invariant();
    }

    /**
     * Invariant, only called if assertions are enabled.
     *
     * @return <code>true</code>.
     * @throws AssertionError if the invariant is broken in any way.
     */
    private boolean invariant() {
        if (this.description == null) {
            throw new AssertionError("description is null");
        }
        if (this.value < 0) {
            throw new AssertionError("value < 0");
        }
        return true;
    }
}
