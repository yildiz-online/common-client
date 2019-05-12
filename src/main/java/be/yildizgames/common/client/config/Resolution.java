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

package be.yildizgames.common.client.config;

/**
 * List of resolutions supported by the game.
 *
 * @author Grégory Van Den Borre
 */
public enum Resolution {

    /**
     * 800*600(4/3).
     */
    RES_800X600,

    /**
     * 1280*800.
     */
    RES_1280X800,

    /**
     * 1024*768(4/3).
     */
    RES_1024X768,

    /**
     * 1280*768.
     */
    RES_1280X768,

    /**
     * 1366*768.
     */
    RES_1366X768,

    /**
     * 1920*1080(16/9) HD.
     */
    RES_1920X1080;

    /**
     * X value of the resolution, immutable.
     */
    public final int x;

    /**
     * Y value of the resolution, immutable.
     */
    public final int y;

    /**
     * Simple constructor, initialize x and y values.
     */
    Resolution() {
        final String[] name = this.toString().split("X");
        this.x = Integer.parseInt(name[0]);
        this.y = Integer.parseInt(name[1]);
    }

    /**
     * @return The enumeration value name without the RES_ prefix.
     */
    @Override
    public final String toString() {
        return this.name().replace("RES_", "");
    }
}
