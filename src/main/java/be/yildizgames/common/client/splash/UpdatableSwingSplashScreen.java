/*
 Copyright (C) Grégory Van den Borre - All Rights Reserved
 Unauthorized copying of this file, via any medium is strictly prohibited
 Proprietary and confidential
 Written by Grégory Van den Borre <vandenborre.gregory@hotmail.fr> 2020-2022
*/

package be.yildizgames.common.client.splash;

import be.yildizgames.common.application.helper.splashscreen.EmptySplashScreen;
import be.yildizgames.common.application.helper.splashscreen.UpdateSplashScreen;
import be.yildizgames.common.configuration.BaseConfiguration;

import javax.swing.*;
import java.util.Properties;

/**
 * @author Grégory Van den Borre
 */
public class UpdatableSwingSplashScreen extends UpdateSplashScreen {

    private final SwingSplashScreen splashScreen;

    private int count;

    private UpdatableSwingSplashScreen(BaseConfiguration configuration, String base64Logo, String base64Font) {
        super();
        this.splashScreen = SwingSplashScreen.create(configuration, base64Logo, base64Font);
    }

    public static UpdateSplashScreen create(Properties config, String base64Logo, String base64Font) {
        try {
            return new UpdatableSwingSplashScreen(new BaseConfiguration(config), base64Logo, base64Font);
        } catch (Exception e) {
            return new EmptySplashScreen();
        }
    }

    @Override
    public final void display() {
        this.splashScreen.display();
        new Timer(50, t -> {
            count++;
            int percent = count;
            this.setProgress(percent);
        }).start();
    }

    @Override
    public final void close() {
        this.splashScreen.close();
    }

    @Override
    public final void setName(String name) {
        this.splashScreen.setName(name);
    }

    @Override
    public final void setProgress(int percent) {
        this.splashScreen.setProgress(percent);
    }

    @Override
    public final void setCurrentLoading(String name) {
        this.splashScreen.setCurrentLoading(name);
    }

}