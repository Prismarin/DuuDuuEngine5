package dev.kelvin.gameengine.resources;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

public class ResourceLoader {

    public String loadTxtFile(String path) {
        if (!path.endsWith(".txt"))
            throw new IllegalArgumentException("The path \"" + path + "\" does not lead to an .txt file");
        Scanner scan = null;
        StringBuilder ret = new StringBuilder();
        try {
            if (path.startsWith("/"))
                scan = new Scanner(Objects.requireNonNull(getClass().getResourceAsStream(path)));
            else
                scan = new Scanner(new File(path));
            while (scan.hasNextLine())
                ret.append(scan.nextLine());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (scan != null)
                scan.close();
        }
        return ret.toString();
    }

    public BufferedImage loadBufferedImage(String path) {
        if (!path.endsWith(".png"))
            throw new IllegalArgumentException("The path \"" + path + "\" does not lead to an .png file");

        URL url = getClass().getResource(path);
        try {
            if (url != null) {
                return ImageIO.read(url);
            } else {
                return ImageIO.read(new File(path));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}