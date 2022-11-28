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
    public Attack(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
