package dev.kelvin.gameengine.save;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Scanner;

public interface SaveObject {

    String saveLocation = System.getProperty("user.home") + "/save";

    String div = "\\d";

    default boolean doesPathExist(String path) {
        return new File(path).exists();
    }

    default void save(String path) {
        File file = new File(saveLocation);
        if (!file.exists())
            file.mkdirs();
        Field[] fields = getClass().getDeclaredFields();
        StringBuilder save = new StringBuilder();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Save.class)) {
                try {
                    save.append(field.getGenericType()).append(div).append(field.getName()).append("=").append(field.get(this)).append("\n");
                } catch (IllegalAccessException e) {
                    System.out.println("Field to save is private, has to be public!");
                }
            }
        }
        if (!save.toString().equals("")) {
            try {
                FileWriter writer = new FileWriter(saveLocation + path);
                writer.write(save.toString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Nothing to save!");
        }
    }

    default void load(String path) {
        if (doesPathExist(saveLocation + path)) {
            try (Scanner scan = new Scanner(new File(saveLocation + path))) {
                while (scan.hasNextLine()) {
                    String in = scan.nextLine();
                    String genericType = in.substring(0, in.indexOf(div));
                    String name = in.substring(in.indexOf(div) + div.length(), in.indexOf("="));
                    String value = in.substring(in.indexOf("=") + 1);
                    switch (genericType) {
                        case "short": getClass().getDeclaredField(name).set(this, Short.parseShort(value));
                            break;
                        case "int": getClass().getDeclaredField(name).set(this, Integer.parseInt(value));
                            break;
                        case "float": getClass().getDeclaredField(name).set(this, Float.parseFloat(value));
                            break;
                        case "long": getClass().getDeclaredField(name).set(this, Long.parseLong(value));
                            break;
                        case "double": getClass().getDeclaredField(name).set(this, Double.parseDouble(value));
                            break;
                        case "byte": getClass().getDeclaredField(name).set(this, Byte.parseByte(value));
                            break;
                        case "char": getClass().getDeclaredField(name).set(this, value.charAt(0));
                            break;
                        case "boolean": getClass().getDeclaredField(name).set(this, Boolean.parseBoolean(value));
                            break;
                        case "class java.lang.String": getClass().getDeclaredField(name).set(this, value);
                            break;
                    }
                }
            } catch (FileNotFoundException | NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Path \"" + path + "\" does not exist");
        }

    }

}
