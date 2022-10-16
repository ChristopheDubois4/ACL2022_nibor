package prefab.competence;

import java.awt.image.BufferedImage;

/**
 * représente les attaques des personnages de manière générale
 */
public abstract class Attack {

    protected String name;
    protected int damage;
    protected int backlash;
    protected int staminaConsuption;

    protected BufferedImage graphics;
    
    


    /**
     * constructeur de la classe GameObject
     * @param name nom de l attaque
     * @param damage degats de l attaque sur ennemies
     * @param backlash degats de contre coup
     * @param staminaConsuption cout en energie
     * @param graphics representation graphique
     */

}
