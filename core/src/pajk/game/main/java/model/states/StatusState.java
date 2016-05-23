package pajk.game.main.java.model.states;

import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.model.items.Weapon;
import pajk.game.main.java.model.units.Unit;
import pajk.game.main.java.model.utils.FileReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by palm on 2016-05-14.
 */
public class StatusState extends State {
    private GameModel model;

    private boolean isInInfoState;
    private List<String> statusList;
    private int selectedInfoItemNr;
    private Unit statusUnit;
    private List<String> statLines;
    private int unitIndex;


    @Override
    void enterAction(){
        //no action in this state
        if(!isInInfoState){
            isInInfoState = true;
        } else {
            System.out.println(getActiveInfoItemText());
        }
    }

    @Override
    void upAction(){
        //show statusscreen for other ally unit?
        if(isInInfoState){
            selectedInfoItemNr = (selectedInfoItemNr + statusList.size() -1) % statusList.size();
        } else {

            unitIndex = (unitIndex + model.getUnitList().size() -1) % model.getUnitList().size();

            setUpStatList();
        }
    }

    @Override
    void downAction(){
        //show statusscreen for other ally unit?

        if(isInInfoState){
            selectedInfoItemNr = (selectedInfoItemNr + 1) % statusList.size();
        } else {

            unitIndex = (unitIndex + 1) % model.getUnitList().size();
            setUpStatList();
        }
    }

    @Override
    void leftAction(){
        //possibly show more information if it does not fit in screen
    }

    @Override
    void rightAction(){
        //possibly show more information if it does not fit in screen
    }

    @Override
    void backAction(){
        if(!isInInfoState) {
            if (model.getActiveUnit().getAllegiance() == Unit.Allegiance.AI) {
                model.setState(GameModel.StateName.MAIN);
            } else {
                model.setState(GameModel.StateName.UNIT_MENU);
            }
        } else {
            isInInfoState = false;
        }

    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.UNIT_MENU;
    }

    /**
     * Sets up list with all the status texts. Gets info about each stat from "statusInfo.txt" and info
     * about each weapon from "item.getDescription"
     */
    private void setUpStatList(){

        statusUnit = model.getUnitList().get(unitIndex);

        String name = statusUnit.getName();
        String health = "Health: "+statusUnit.getHealth() + "/" + statusUnit.getMaxHealth();
        String level = "Level: " + statusUnit.getLevel() + " " + statusUnit.getProfession();
        String defence = "Defence: " +statusUnit.getDefence();
        String weapon = "Weapon: " +statusUnit.getWeapon().getName();
        String exp = "Experience: " +statusUnit.getExperience();
        String strength = "Strength: " +statusUnit.getStrength();
        String skill = "Skill: " +statusUnit.getSpeed();
        String luck = "Luck: " + statusUnit.getLuck();
        String resistance = "Resistance: " +statusUnit.getResistance();
        String speed = "Speed: "+ statusUnit.getSpeed();
        String movement = "Movement: " +statusUnit.getMovement();

        statusList = new ArrayList<>();
        statusList.add(name);
        statusList.add(health);
        statusList.add(level);
        statusList.add(exp);
        statusList.add(weapon);
        statusList.add(strength);
        statusList.add(skill);
        statusList.add(speed);
        statusList.add(luck);
        statusList.add(defence);
        statusList.add(resistance);
        statusList.add(movement);

        statLines = FileReader.readFile("statusInfo.txt"); //statusInfo.txt needs to match the units stats
        for(int i = 0; i < statLines.size(); i++){
            String tmp1 = statLines.get(i);
            String tmp2 = tmp1;
            String wepStatsText = "";
            if(i==4){   //row 4 is weapon row
                Weapon wep = statusUnit.getWeapon();
                wepStatsText = "Mgt: " + wep.getDamage() + " Acc: " + wep.getAccuracy() + " Crit: " + wep.getCritChance() + "\n";
                tmp1 = wep.getDescription();
            }

            if(tmp1.length() > 29 && tmp1.substring(29).contains(" ") && tmp1.length() < 65){
                int spaceIndex = tmp1.indexOf(" ",tmp1.length()/2);
                tmp2 = tmp1.substring(0,spaceIndex)+"\n"+tmp1.substring(spaceIndex+1);

            } else if (tmp1.length() >= 65){
                int spaceIndex1 = tmp1.indexOf(" ",tmp1.length()/3);
                int spaceIndex2 = tmp1.indexOf(" ",2*tmp1.length()/3);
                tmp2 = tmp1.substring(0,spaceIndex1)+"\n"+tmp1.substring(spaceIndex1+1,spaceIndex2) + "\n" +
                        tmp1.substring(spaceIndex2+1);

            }

            statLines.remove(i);
            statLines.add(i,wepStatsText + tmp2);

        }

    }

    @Override
    public void activate() {
        model = GameModel.getInstance();
        List<Unit> unitList = model.getUnitList();
        unitIndex = unitList.indexOf(model.getActiveUnit());

        isInInfoState = false;
        selectedInfoItemNr = 0;
        setUpStatList();


    }
    public String getActiveInfoItemText(){
        return statLines.get(selectedInfoItemNr);

    }
    public String getInfoItem(int i){
        return statusList.get(i);
    }
    public int getSelectedInfoItemNr(){
        return selectedInfoItemNr;
    }
    public int getStateSize(){
        return statusList.size();
    }

    public boolean isInInfoState() {
        return isInInfoState;
    }
    public Unit getStatusUnit(){ return statusUnit;}
}
