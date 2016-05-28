package game.main.java.model.utils;

import java.io.*;
import java.nio.file.InvalidPathException;
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
        try (InputStream is = FileReader.class.getResourceAsStream("/" + fileName)){

            List<String> list = new ArrayList<>();
            Scanner scanner = new Scanner(is);
            while (scanner.hasNext()){
                list.add(scanner.nextLine());
            }
            return list;

        } catch (InvalidPathException e) {
            System.out.println("Couldn't load " + fileName);
            e.printStackTrace();
            return null;
        } catch (FileNotFoundException e){
            System.out.println("Couldn't find file " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
