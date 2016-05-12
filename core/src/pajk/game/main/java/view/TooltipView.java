//package pajk.game.main.java.view;
//
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//
///**
// * Created by palm on 2016-05-12.
// */
//public class TooltipView extends AbstractGameView {
//
//
//    private Texture tooltipBackground;
//    private OrthographicCamera camera;
//
//    public TooltipView(OrthographicCamera camera){
//        tooltipBackground = new Texture("tooltipBackground.png");
//        this.camera = camera;
//    }
//
//    @Override
//    public void render(SpriteBatch spriteBatch) {
//        spriteBatch.begin();
//
//        spriteBatch.end();
//    }
//
//    public void drawTooltip(SpriteBatch spriteBatch){
//        float x = camera.position.x - (camera.viewportWidth/2);
//        if(!shouldDrawMenuRight()){
//            x = camera.position.x + (camera.viewportHeight/2);
//        }
//
//        spriteBatch.draw(tooltipBackground,x,y);
//    }
//
//    @Override
//    public void update(float deltaTime) {
//
//    }
//}
