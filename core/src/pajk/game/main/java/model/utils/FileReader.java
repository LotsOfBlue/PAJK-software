package pajk.game.main.java.model.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Helper class that lets us convert text files into String lists.
 *
 * Created by Gustav on 2016-05-13.
 */
public abstract class FileReader {
    public static List<String> readFile(String fileName){
        try {
            Path path = Paths.get(fileName);
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (InvalidPathException e) {
            System.out.println("Couldn't load " + fileName);
            e.printStackTrace();
            return null;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
