package pajk.game.main.java.model.states;

import pajk.game.main.java.model.Board;
import pajk.game.main.java.model.GameModel;
import pajk.game.main.java.model.units.Unit;

import java.util.List;

/**
 * Created by Gustav on 2016-05-23.
 */
public class EndConfirmState extends State{
    private GameModel model;
    private List<Unit> unitList;

    @Override
    public void enterAction(){
        for (Unit u :
                unitList) {
            if (u.getAllegiance().equals(Unit.Allegiance.PLAYER)) {
                u.setUnitState(Unit.UnitState.DONE);
            }
        }
        model.setState(GameModel.StateName.MAIN);
    }

    @Override
    public void backAction(){
        model.setState(GameModel.StateName.MAIN);
    }

    @Override
    public void activate() {
        model = GameModel.getInstance();
        unitList = model.getUnitList();
    }

    @Override
    public GameModel.StateName getName() {
        return GameModel.StateName.END_CONFIRM;
    }
}
