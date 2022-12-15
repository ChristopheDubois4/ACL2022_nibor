package prefab.competence;

import prefab.equipment.Effect;

import java.awt.image.BufferedImage;
import java.util.List;


/**
 * représente les sorts des personnages de manière générale
 */
public class Spell {

    private String name;
    private int damage = 60;
    private List<Effect> effects;
    private int manaConsuption = 20;

    private BufferedImage graphics;
        
    /**
     * constructeur de la classe Spell
     * @param name nom de l attaque
     * @param damage degats de l attaque sur ennemies
     * @param spellEffect effet de l utilisation du sort
     * @param manaConsuption cout en energie
     * @param graphics representation graphique
     */
    public Spell(String name, int damage, int manaConsuption, List<Effect> effects) {
        this.name = name;
        this.damage = damage;
        this.manaConsuption = manaConsuption;
        this.effects = effects;
    }
    
    public int getDamage() {
    	return this.damage;
    }
    
    public int getManaConsuption() {
    	return this.manaConsuption;
    }
    
    public List<Effect> getEffects() {
		return this.effects;
	}


    @Override
    public String toString() {
        return this.name;
    }
}
