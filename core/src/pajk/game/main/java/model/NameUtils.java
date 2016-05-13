package pajk.game.main.java.model;

import java.util.List;
import java.util.Random;

/**
 * Created by Gustav on 2016-05-13.
 */
public abstract class NameUtils {
    public static String getRandomName(Unit.Allegiance allegiance){
        List<String> nameList;
        if (allegiance == Unit.Allegiance.PLAYER){
            nameList = FileReader.readFile("goodGuys.txt");
        }else{
            nameList = FileReader.readFile("badGuys.txt");
        }
        Random rand = new Random();
        return nameList.get(rand.nextInt(nameList.size()));
    }
}
