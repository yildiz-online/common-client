/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *  Copyright (c) 2024 Grégory Van den Borre
 *  More infos available: https://engine.yildiz-games.be
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 *  the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright
 *  notice and this permission notice shall be included in all copies or substantial portions of the  Software.
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 *  OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package be.yildizgames.common.client.splash;

import be.yildizgames.common.application.helper.splashscreen.SplashScreen;
import be.yildizgames.common.configuration.BaseConfiguration;
import be.yildizgames.common.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.Locale;

/**
 * @author Grégory Van den Borre
 */
public class SwingSplashScreen implements SplashScreen {

    private final Logger logger = Logger.getLogger(this);

    private final JLabel title;

    private final JLabel logo;

    private final JLabel loadingItemName;

    private final int targetLogoSize;

    private final JPanel panel;

    private final JWindow window;

    private final JProgressBar progressBar;

    private SwingSplashScreen(BaseConfiguration configuration, String base64Logo, String base64Font) {
        super();
        var tk = Toolkit.getDefaultToolkit();
        var screenWidth = (int) tk.getScreenSize().getWidth();
        var screenHeight = (int) tk.getScreenSize().getHeight();
        this.window = new JWindow();
        this.window.setBounds(0, 0, screenWidth, screenHeight);
        this.window.setCursor(this.window.getToolkit().createCustomCursor(
                new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(),
                null));
        this.panel = new JPanel();
        this.panel.setLayout(null);
        this.panel.setBounds(0, 0, screenWidth, screenHeight);
        this.window.getContentPane().setLayout(null);
        this.window.getContentPane().add(this.panel);
        this.panel.setBackground(new Color(0, 0, 0));
        this.title = new JLabel("");
        this.title.setForeground(Color.WHITE);
        this.title.setBounds(screenWidth / 2 - 50, 100, 280, 30);
        this.panel.add(this.title);
        var subTitle = new JLabel(this.getMessageLoading(configuration.getLocale()));
        subTitle.setForeground(Color.WHITE);
        subTitle.setBounds(screenWidth / 2 - 100, 150, 280, 30);
        this.panel.add(subTitle);
        this.progressBar = new JProgressBar();
        this.progressBar.setLayout(null);
        this.progressBar.setMaximum(50);
        var progressWidth = screenWidth >> 2;
        this.progressBar.setBounds(screenWidth / 2 - progressWidth / 2, screenHeight - 200, progressWidth, 30);
        this.panel.add(this.progressBar);
        this.loadingItemName = new JLabel();
        this.loadingItemName.setLayout(null);
        this.loadingItemName.setForeground(Color.WHITE);
        this.loadingItemName.setBounds(screenWidth / 2 - 200, screenHeight - 100, 280, 30);
        this.panel.add(this.loadingItemName);
        this.logo = new JLabel();
        this.targetLogoSize = screenHeight >> 1;
        this.logo.setBounds(((screenWidth >> 1) - (targetLogoSize >> 1)), targetLogoSize >> 1, targetLogoSize, targetLogoSize);
        this.setLogo(base64Logo);
        this.setFont(base64Font);
        this.panel.add(this.logo);
    }

    public static SwingSplashScreen create(BaseConfiguration configuration, String base64Logo, String base64Font) {
        return new SwingSplashScreen(configuration, base64Logo, base64Font);
    }

    private void setFont(String base64Font) {
        try (var is = new ByteArrayInputStream(Base64.getDecoder().decode(base64Font))){
            var font = Font.createFont(Font.TRUETYPE_FONT, is);
            this.title.setFont(font.deriveFont(32.0f));

        } catch (Exception e) {
            this.logger.error(e);
            this.title.setFont(new Font("Arial", Font.BOLD, 32));

        }
    }

    private void setLogo(String base64Logo) {
        try {
            var logoBytes = Base64.getDecoder().decode(base64Logo);
            var resizedImage = new BufferedImage(this.targetLogoSize, this.targetLogoSize, BufferedImage.TYPE_INT_RGB);
            var graphics2D = resizedImage.createGraphics();
            graphics2D.drawImage(ImageIO.read(new ByteArrayInputStream(logoBytes)), 0, 0,
                    this.targetLogoSize, this.targetLogoSize, null);
            graphics2D.dispose();
            this.logo.setIcon(new ImageIcon(resizedImage));
        } catch (Exception e) {
            this.logger.error(e);
        }
    }

    @Override
    public final void setProgress(int percent) {
        this.progressBar.setValue(percent);
        this.progressBar.setString(percent + "%");
    }

    @Override
    public final void display() {
        this.window.setVisible(true);
    }

    @Override
    public final void close() {
        this.window.dispose();
    }

    @Override
    public final void setCurrentLoading(String name) {
        this.loadingItemName.setText(name);
    }

    @Override
    public final void setName(String name) {
        this.title.setText(name);
    }

    private void setBackgroundColor(int r, int g, int b) {
        this.panel.setBackground(new Color(r, g, b));
    }

    private String getMessageLoading(Locale locale) {
        return switch (locale.getLanguage()) {
            case "fr" -> "Vérification des mises à jour, un instant...";
            case "tr" -> "Güncellemeler kontrol ediliyor, bekleyin...";
            default -> "Checking for update please wait a moment...";
        };
    }
}
