package pajk.game.main.java.model.states;

import pajk.game.main.java.model.GameModel;
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

    private List<String> statLines;


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
        }
    }

    @Override
    void downAction(){
        //show statusscreen for other ally unit?

        if(isInInfoState){
            selectedInfoItemNr = (selectedInfoItemNr + 1) % statusList.size();
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

    @Override
    public void activate() {
        model = GameModel.getInstance();
        Unit unit = model.getActiveUnit();
        //Creates strings with info from active unit
        String name = unit.getName();
        String health = "Health: "+unit.getHealth() + "/" + unit.getMaxHealth();
        String level = "Level: " +unit.getLevel();
        String defence = "Defence: " +unit.getDefence();
        String weapon = "Weapon: " +unit.getWeapon().getName();
        String exp = "Experience: " +unit.getExperience();
        String strength = "Strength: " +unit.getStrength();
        String might = "Might: " +unit.getMight();
        String skill = "Skill: " +unit.getSpeed();
        String luck = "Luck: " + unit.getLuck();
        String resistance = "Resistance: " +unit.getResistance();
        String speed = "Speed: "+ unit.getSpeed();
        String movement = "Movement: " +unit.getMovement();
//        String constitution = "Constitution: " +unit.getConstitution();
//        String aid = "Aid: " +unit.getAid();

        isInInfoState = false;
        selectedInfoItemNr = 0;

        statusList = new ArrayList<>();
        statusList.add(name);
        statusList.add(health);
        statusList.add(level);
        statusList.add(defence);
        statusList.add(weapon);
        statusList.add(exp);
        statusList.add(strength);
        statusList.add(might);
        statusList.add(skill);
        statusList.add(luck);
        statusList.add(resistance);
        statusList.add(speed);
        statusList.add(movement);
//        statusList.add(constitution);
//        statusList.add(aid);

        statLines = FileReader.readFile("statusInfo.txt"); //statusInfo.txt needs to match the units stats
        for(int i = 0; i < statLines.size(); i++){
            if(statLines.get(i).length() > 29 && statLines.get(i).substring(29).contains(" ") && statLines.get(i).length() < 65){
                String tmp1 = statLines.get(i);

                int spaceIndex = tmp1.indexOf(" ",tmp1.length()/2);
                String tmp2 = tmp1.substring(0,spaceIndex)+"\n"+tmp1.substring(spaceIndex+1);

                statLines.remove(i);
                statLines.add(i,tmp2);

            } else if (statLines.get(i).length() >= 65){
                String tmp1 = statLines.get(i);

                int spaceIndex1 = tmp1.indexOf(" ",tmp1.length()/3);
                int spaceIndex2 = tmp1.indexOf(" ",2*tmp1.length()/3);

                String tmp2 = tmp1.substring(0,spaceIndex1)+"\n"+tmp1.substring(spaceIndex1+1,spaceIndex2) + "\n" +
                        tmp1.substring(spaceIndex2+1);
                statLines.remove(i);
                statLines.add(i,tmp2);


            }

        }



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
}
