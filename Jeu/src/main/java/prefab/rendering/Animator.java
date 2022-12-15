package prefab.rendering;

import java.util.ArrayList;
import java.util.List;

public final class Animator implements Runnable{
    
    private static final Animator INSTANCE = new Animator();

    static final long TIME = 10;

    private List<Animation> animations;   

    private Animator() {
        animations = new ArrayList<Animation>();
        new Thread(this).start();
    }

    public static Animator getInstance() {
        return INSTANCE;
    }
    
    @Override
    public void run() {

        while (true) {
            try {
                synchronized(this){    
                    for (Animation animation : animations) {
                        animation.nextFrame();
                    }
                }     
                Thread.sleep(TIME);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }      
    }

    public void addAnimation(Animation animation) {
        animations.add(animation);
    }

    public void removeAnimation(Animation animation) {
        animations.remove(animation);
    }

    public List<Animation> getAnimations() throws CloneNotSupportedException {

        List<Animation> newAnimations = new ArrayList<Animation>();
        for (Animation animation : animations) {    
            newAnimations.add( (Animation) animation.clone());
        }
        return newAnimations;
    }


}
