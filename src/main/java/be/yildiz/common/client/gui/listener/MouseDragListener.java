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

import be.yildiz.common.vector.Point2D;

/**
 * Listener to notify when the mouse is dragged.
 *
 * @author Grégory Van den Borre
 */
@FunctionalInterface
public interface MouseDragListener {

    /**
     * Called when the mouse is dragged holding the left button.
     *
     * @param position Current mouse position.
     * @param delta    Delta between the current and the last position.
     */
    void mouseDragLeft(Point2D position, Point2D delta);

    /**
     * Called when the mouse is dragged holding the right button.
     *
     * @param position Current mouse position.
     * @param delta    Delta between the current and the last position.
     */
    default void mouseDragRight(Point2D position, Point2D delta) {
        //Default empty implementation.
    }

    /**
     * Called when the mouse is dragged holding the wheel button.
     *
     * @param position Current mouse position.
     * @param delta    Delta between the current and the last position.
     */
    default void mouseDragWheel(Point2D position, Point2D delta) {
        //Default empty implementation.
    }

    /**
     * Called when the mouse right button is released.
     *
     * @param position Current mouse position.
     */
    default void mouseDragRightReleased(Point2D position) {
        //Default empty implementation.
    }

    /**
     * Called when the mouse left button is released.
     *
     * @param position Current mouse position.
     */
    default void mouseDragLeftReleased(Point2D position) {
        //Default empty implementation.
    }
}
