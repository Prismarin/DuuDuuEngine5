package dev.kelvin.gameengine.resources;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ResourceClass {

    public final String res;
    public static final String resIndicator = "res:/";

    private final HashMap<String, String> paths;
    private final HashMap<String, String> txtFiles;
    private final HashMap<String, BufferedImage> pngFiles;

    private final ResourceLoader loader;

    public ResourceClass(String resPath) {
        res = resPath;
        loader = new ResourceLoader();
        paths = new HashMap<>();
        txtFiles = new HashMap<>();
        pngFiles = new HashMap<>();
    }

    public void loadUp() {
        Iterator<Map.Entry<String, String>> it = paths.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pair = it.next();
            if (pair.getValue().endsWith(".txt")) {
                if (pair.getValue().startsWith(resIndicator)) {
                    String newValue = pair.getValue().substring(resIndicator.length());
                    newValue = res + newValue;
                    pair.setValue(newValue);
                }
                txtFiles.put(pair.getKey(), loader.loadTxtFile(pair.getValue()));
            } else if (pair.getValue().endsWith(".png")) {
                if (pair.getValue().startsWith(resIndicator)) {
                    String newValue = pair.getValue().substring(resIndicator.length());
                    newValue = res + newValue;
                    pair.setValue(newValue);
                }
                pngFiles.put(pair.getKey(), loader.loadBufferedImage(pair.getValue()));
            } else
                System.err.println("Are you sure the path \"" + pair.getValue() + "\" is correct? Do use an unsupported filetype?");
            it.remove();
        }
    }

    public void addResource(String name, String relativePath) {
        paths.put(name, relativePath);
    }

    public String getLoadedTxtFile(String name) {
        return txtFiles.get(name);
    }

    public BufferedImage getLoadedBufferedImage(String name) {
        return pngFiles.get(name);
    }

}
