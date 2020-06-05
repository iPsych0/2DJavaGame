package dev.ipsych0.myrinnia.gfx;

import dev.ipsych0.myrinnia.Handler;
import dev.ipsych0.myrinnia.utils.FileUtils;
import dev.ipsych0.splashscreen.SplashScreen;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FontLoader {

    public static Font loadFont(String path, float size) {
        try {
            SplashScreen.addLoadedElement();
            InputStream input;

            String fixedFile = FileUtils.getResourcePath(path);
            input = new FileInputStream(fixedFile);
            Font f = Font.createFont(Font.TRUETYPE_FONT, input).deriveFont(Font.BOLD, size);
            input.close();
            return f;
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
