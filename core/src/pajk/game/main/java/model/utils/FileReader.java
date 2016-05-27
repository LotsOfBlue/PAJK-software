package game.main.java.model.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class that lets us convert text files into String lists.
 *
 * Created by Gustav on 2016-05-13.
 */
public abstract class FileReader {
    public static List<String> readFile(String fileName){
        List<String> stringList = new ArrayList<>();
        FileHandle file = Gdx.files.internal(fileName);
        for (String returnVal : file.readString().split("\\r?\\n")){
            stringList.add(returnVal);
        }
        return stringList;
    }
}
