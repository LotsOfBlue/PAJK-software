package pajk.game.main.java.States;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by palm on 2016-04-20.
 */
public class GameStateManager {

    private Stack<State> states;

    public GameStateManager(){
        states = new Stack<>();

    }

    public void push(State state){
        states.push(state);
    }
    public void set(State state){
        states.pop();
        states.push(state);
    }
    public void update(float deltaTime){
        states.peek().update(deltaTime);
    }
    public void render(SpriteBatch spriteBatch){
        states.peek().render(spriteBatch);
    }
}
