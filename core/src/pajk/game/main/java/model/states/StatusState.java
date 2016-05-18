package pajk.game.main.java.model.states;

import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.model.units.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by palm on 2016-05-14.
 */
public class StatusState extends State {
    private GameModel model;

    private boolean isInExtraState;
    private List<String> statusList;
    private int selectedExtraItem;


    @Override
    void enterAction(){
        //no action in this state
        if(!isInExtraState){
            isInExtraState = true;
        } else {
            //choose tile.
        }
    }

    @Override
    void upAction(){
        //show statusscreen for other ally unit?
        if(isInExtraState){
            selectedExtraItem = selectedExtraItem + 1 % statusList.size();
        }
    }

    @Override
    void downAction(){
        //show statusscreen for other ally unit?
        if(isInExtraState){
            selectedExtraItem = selectedExtraItem + selectedExtraItem -1 % statusList.size();
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
        if(!isInExtraState) {
            if (model.getActiveUnit().getAllegiance() == Unit.Allegiance.AI) {
                model.setState(GameModel.StateName.MAIN);
            } else {
                model.setState(GameModel.StateName.UNIT_MENU);
            }
        } else {
            isInExtraState = false;
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
        String weapon = "Weapon: " +unit.getWeapon().getWeaponType();
        String exp = "Experience: " +unit.getExperience();
        String strength = "Strength: " +unit.getStrength();
        String might = "Might: " +unit.getMight();
        String skill = "Skill: " +unit.getSpeed();
        String luck = "Luck: " + unit.getLuck();
        String resistance = "Resistance: " +unit.getResistance();
        String speed = "Speed: "+ unit.getSpeed();
        String movement = "Movement: " +unit.getMovement();
        String constitution = "Constitution: " +unit.getConstitution();
        String aid = "Aid: " +unit.getAid();

        isInExtraState = false;
        selectedExtraItem = 0;

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
        statusList.add(constitution);
        statusList.add(aid);

    }
    public String getActiveExtraItem(){
        return statusList.get(selectedExtraItem);
    }
    public String getExtraItem(int i){
        return statusList.get(i);
    }
    public int getSelectedExtraItem(){
        return selectedExtraItem;
    }
}
