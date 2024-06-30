/*
 * Copyright (C) Grégory Van den Borre - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Grégory Van den Borre <vandenborre.gregory@hotmail.fr> 2019-2021
 */

package be.yildizgames.common.client.splash;

import be.yildizgames.common.application.helper.splashscreen.SplashScreen;

/**
 * Create a splashscreen.
 *
 * @author Grégory Van den Borre
 */
@FunctionalInterface
public interface SplashScreenFactory {

    /**
     * Create a new SplashScreen.
     * @return A new instance of a SplashScreen. Never null.
     */
    SplashScreen create();
}
