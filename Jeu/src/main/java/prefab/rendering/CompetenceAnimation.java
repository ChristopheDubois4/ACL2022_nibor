package prefab.rendering;

import java.util.HashMap;

import prefab.information.State;


public class CompetenceAnimation extends Animation {

    private CompetenceAnimation(HashMap<State, Sprite> sprites, State state) {
        super(sprites, state);
    }

    public static CompetenceAnimation create(HashMap<State,Sprite> sprites) {

        return new CompetenceAnimation(sprites, State.DEFAULT);
    }

    @Override
    public void nextFrame() {
        super.nextFrame();
        if (spriteIndex == 0 && cptNrbSpriteCall == 0) {
            stopAnimation();
        }
    }

    @Override
    public Visual getVisual() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    
}
