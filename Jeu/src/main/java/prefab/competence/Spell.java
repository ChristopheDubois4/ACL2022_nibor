package prefab.competence;


import java.awt.image.BufferedImage;


/**
 * représente les sorts des personnages de manière générale
 */
public abstract class Spell {

    protected String name;
    protected int damage;
    protected int manaConsuption;

    protected BufferedImage graphics;
    
    


    /**
     * constructeur de la classe GameObject
     * @param name nom de l attaque
     * @param damage degats de l attaque sur ennemies
     * @param spellEffect effet de l utilisation du sort
     * @param manaConsuption cout en energie
     * @param graphics representation graphique
     */
}
