package game.main.java.model.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Helper class that lets us convert text files into String lists.
 *
 * Created by Gustav on 2016-05-13.
 */
public abstract class FileReader {
    public static List<String> readFile(String fileName){

        try {
            List<String> list = new ArrayList<>();
            File file = new File(fileName);
            Scanner scanner = new Scanner(new java.io.FileReader(file));
            while (scanner.hasNext()){
                list.add(scanner.nextLine());
            }
            return list;
        } catch (InvalidPathException e) {
            System.out.println("Couldn't load " + fileName);
            e.printStackTrace();
            return null;
        } catch (FileNotFoundException e){

        }
        return null;
    }
}
