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

package be.yildiz.common.client.gui.listener;

/**
 * Listener of the keyboard events.
 *
 * @author Grégory Van den Borre
 */
@FunctionalInterface
public interface KeyboardListener {

    /**
     * Called when a char key is pressed.
     *
     * @param key Key pressed.
     * @return True if the listener used the key, false otherwise.
     */
    boolean keyPressed(char key);

    /**
     * Called when enter key is pressed.
     * @return True if the listener used the key, false otherwise.
     */
    default boolean enterKeyPressed() {
        return false;
    }

    /**
     * Called when delete key is pressed.
     * @return True if the listener used the key, false otherwise.
     */
    default boolean deleteKeyPressed() {
        return false;
    }

    /**
     * Called when an arrow key has been pressed.
     *
     * @param arrow Enum value to get witch arrow has been pressed.
     * @return True if the listener used the key, false otherwise.
     */
    default boolean arrowKeyPressed(ArrowKey arrow) {
        return false;
    }

    /**
     * Called when a key is released.
     *
     * @param key Code of the key.
     */
    default void keyReleased(int key) {
        //Default empty implementation.
    }

    /**
     * Called when a number from the keypad is pressed.
     *
     * @param number Number pressed.
     * @return True if the listener used the key, false otherwise.
     */
    default boolean numberPressed(int number) {
        return false;
    }
}
