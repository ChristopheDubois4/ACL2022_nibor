package prefab.rendering;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import manager.FightManager;
import model.NiborPainter;
import prefab.information.Position;
import prefab.information.State;


public class CharacterAnimation extends Animation {


    private final long MOVE_TIME;

    /**
     * le rappoprt IMAGES_PER_MOVE / MOVE_TIME doit
     * être un multiple de 10 car l'horloge de l'animator
     * est de 10 ms
     */
    private final int IMAGES_PER_MOVE;

    private static final int defaultShift = NiborPainter.TILE_LENGTH;

    
    final HashMap<State, State> running = new HashMap<State, State>() {{
        put(State.IDLE_DOWN, State.WALK_DOWN);
        put(State.IDLE_UP, State.WALK_UP);
        put(State.IDLE_RIGHT, State.WALK_RIGHT);
        put(State.IDLE_LEFT, State.WALK_LEFT);
    }};


    private int shift;
    private boolean playMoving;
    private int deltaX, deltaY;

    private boolean isInFight;
    private boolean isPlayer;

    int cptNrbUpdateShiftCall = 0;


    private CharacterAnimation(HashMap<State, Sprite> sprites, State state, boolean isPlayer) {
        super(sprites, state);
        this.isPlayer = isPlayer;    
        MOVE_TIME = isPlayer ? 150 : 200;
        IMAGES_PER_MOVE = (int) MOVE_TIME/10;
        playMoving  = false;
        isInFight = false;
        deltaX = 0;
        deltaY = 0;
        shift = defaultShift;
    }

    public static CharacterAnimation createForPlayer(HashMap<State,Sprite> sprites) {
        return new CharacterAnimation(sprites, State.IDLE_DOWN, true);
    }

    public static CharacterAnimation createForPNJ(HashMap<State,Sprite> sprites) {
        return new CharacterAnimation(sprites, State.IDLE_DOWN, false);
    }

    public void playMovement(int deltaX, int deltaY, Position position) {
        this.deltaX =  deltaX;
        this.deltaY = deltaY;
        this.position = position;
        playMoving = true;
        shift = defaultShift;
        spriteIndex = 0;
        currentSprite = sprites.get(running.get(state));
    }

    @Override
    public void nextFrame() {

        if (playMoving) {
            cptNrbUpdateShiftCall++;
            // si le nombre d'appel de la fct * 10 est >= à 150
            if ( (long) (cptNrbUpdateShiftCall * Animator.TIME) >= MOVE_TIME ) {
                cptNrbUpdateShiftCall = 0;
                playMoving = false;
            } else {
                updateMoveShift();
            }
        }   
        // met à jour l'image du Sprite
        super.nextFrame();

    }

    private void updateMoveShift() {
    	if (shift <= 0) {
    		shift = defaultShift;
    	}
    	shift = shift - (int) (defaultShift/IMAGES_PER_MOVE);
    }

    public boolean getPlayMoving() {
        return playMoving;
    }

    public void stopMoving() {
        this.playMoving = false;
    }

    public boolean getIsInFight() {
        return isInFight;
    }

    public void setIsInFight(boolean isInFight) {
        this.isInFight = isInFight;
    }

    @Override
    public Visual getVisual() throws Exception {
        // récupération des informations
        BufferedImage image = currentSprite.getImage(spriteIndex);
        boolean mirorV = currentSprite.getMirorV();
        Boolean mirorH = currentSprite.getMirorH();
        /** si le jeu est en mode combat */
        if (FightManager.getInstance().getIsInFight()) {
            if (!isInFight) {
                return null;
            }
            /**
             * afficage des images des personnes en combats
             * (positon indépendante de celle dans le monde)
             * côté gauche pour le joueur et droit pour l'adversaire
             */
            if (isPlayer) {
                return Visual.createWithGameCoord(0, 10, 30, 0, image, mirorV, mirorV);
            }
            return Visual.createWithGameCoord(21, 10, -30, 0, image, mirorV, mirorH);
        }
        /** si le jeu est en mode monde ouvert */
        return Visual.createWithGameCoord(position.getX(), position.getY() -1 , -deltaX*shift, -deltaY*shift + image.getHeight(), image, mirorV, mirorH);
    }


}
