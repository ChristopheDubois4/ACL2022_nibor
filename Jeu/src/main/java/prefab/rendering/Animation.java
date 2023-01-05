package prefab.rendering;

import java.util.HashMap;

import prefab.information.Position;
import prefab.information.State;


public class Animation implements Cloneable, Comparable<Animation>{

    /** <code>true</code> si l'animation est en cours */
    private boolean isPlaying;
    /** etat actuel de l'objet */
    State state;
    /** association des sprites avec leur etat */
    final HashMap<State,Sprite> sprites;
    /** le sprite sélectionné */
    Sprite currentSprite;
    /** position de l'image que l'on souhaite afficher dans un sprite */
    int spriteIndex;
    /**
     * compteur qui permet de savoir le nombre de fois
     * que la méthode nextFrame() est apellé
     * permet de changer de frame selon la vitesse de l'animation
     */
    int cptNrbSpriteCall = 1;
    /** position du personnage */
    Position position;

    Animation(HashMap<State,Sprite> sprites, State state) {
        this.sprites = sprites;
        this.state  = state;
        isPlaying = false;
        spriteIndex = 0;
        currentSprite = sprites.get(state);
    }

    public static Animation create(HashMap<State,Sprite> sprites) {
        if (sprites == null) {
            throw new NullPointerException("le sprite est nulle");
        }
        return new Animation(sprites, State.DEFAULT);
    }

    public void nextFrame() {
         if ( (cptNrbSpriteCall++ % currentSprite.getAnimationSpeed()) == 0) {
            spriteIndex = (spriteIndex + 1)%currentSprite.getNumberOfImages();
            cptNrbSpriteCall = 1;
        }
    }

    public void setState(State newState) {
        state = newState;
        System.out.println("state : "+state);
        currentSprite = sprites.get(state);
        spriteIndex = 0;
    }

    public void setPosition(Position position) throws CloneNotSupportedException {
        this.position = (Position) position.clone();
    }

    public boolean getIsPlaying() {
        return isPlaying;
    }

    public void playingAnimation() {
        isPlaying = true;
        Animator.getInstance().addAnimation(this);
    }

    public void stopAnimation() {
        isPlaying = false;
        Animator.getInstance().removeAnimation(this);
    }

    public Visual getVisual() throws Exception {
        return Visual.createWithGameCoord(position.getX(), position.getY()-1, 0,  currentSprite.getImage(spriteIndex).getHeight(), currentSprite.getImage(spriteIndex));
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int compareTo(Animation animation) {
        return this.position.compareTo(animation.position);
    }


}
