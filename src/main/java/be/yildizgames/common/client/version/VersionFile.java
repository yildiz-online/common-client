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

package be.yildizgames.common.client.version;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Create a version file to be matched against the server one to check if the application does need to be updated.
 * This is used by application that cannot be updated using update4j.
 *
 * @author Grégory Van den Borre
 */
public class VersionFile {

    /**
     * Create client version file to be matched against the server one.
     * The server file can be created using sha1sum {PATH_TO_APPLICATION}/application_name.jar | awk '{printf toupper($1)}' > {PATH_TO_FILE}/FILE_NAME on the server.
     */
    public static void createVersionFile(String fileName) {
        try {
            var version = Path.of(fileName);
            Files.deleteIfExists(version);
            Files.createFile(version);
            Files.writeString(version, createHash());
        } catch (Exception e) {
            System.getLogger(VersionFile.class.toString()).log(System.Logger.Level.ERROR, "", e);
        }
    }

    private static String createHash() throws NoSuchAlgorithmException, URISyntaxException, IOException {
        var file = new File(VersionFile.class.getProtectionDomain().getCodeSource().getLocation()
                .toURI()).getPath();
        var digest = MessageDigest.getInstance("SHA-1");
        try (var fis = new FileInputStream(file)) {
            var n = 0;
            var buffer = new byte[8192];
            while (n != -1) {
                n = fis.read(buffer);
                if (n > 0) {
                    digest.update(buffer, 0, n);
                }
            }
            var data = digest.digest();
            var builder = new StringBuilder(data.length * 2);
            var hexCode = "0123456789ABCDEF".toCharArray();
            for (byte b : data) {
                builder.append(hexCode[b >> 4 & 15]);
                builder.append(hexCode[b & 15]);
            }

            return builder.toString();
        }
    }
}
