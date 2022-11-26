package prefab.competence;

import prefab.equipment.Effect;

import java.awt.image.BufferedImage;


/**
 * représente les sorts des personnages de manière générale
 */
public class Spell {

    private String name;
    private int damage;
    private Effect spellEffect;
    private int manaConsuption;

    private BufferedImage graphics;
        

    /**
     * constructeur de la classe Spell
     * @param name nom de l attaque
     * @param damage degats de l attaque sur ennemies
     * @param spellEffect effet de l utilisation du sort
     * @param manaConsuption cout en energie
     * @param graphics representation graphique
     */
    public Spell(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
