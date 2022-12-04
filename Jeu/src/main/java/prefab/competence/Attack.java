package prefab.competence;

import java.awt.image.BufferedImage;

/**
 * représente les attaques des personnages de manière générale
 */
public class Attack {

    private String name;
    private int damage;
    private int backlash;
    private int staminaConsuption;

    private BufferedImage graphics;
    
    /**
     * constructeur de la classe Attack
     * @param name nom de l attaque
     * @param damage degats de l attaque sur ennemies
     * @param backlash degats de contre coup
     * @param staminaConsuption cout en energie
     * @param graphics representation graphique
     */
    public Attack(String name, int damage, int staminaConsuption,int backlash) {
        this.name = name;
        this.damage = damage;
        this.staminaConsuption = staminaConsuption;
        this.backlash = backlash;
    }

    public Attack(String name, int damage, int staminaConsuption) {
       this(name, damage, staminaConsuption, 0);
    }
    
    public int getDamage() {
    	return this.damage;
    }
    
    public int getStaminaConsuption() {
    	return this.staminaConsuption;
    }

    public int getBacklash() {
        return this.backlash;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
