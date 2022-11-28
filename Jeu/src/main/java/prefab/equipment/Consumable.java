package prefab.equipment;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import manager.Utilities;


import prefab.information.State;
import prefab.equipment.Effect;
import prefab.equipment.Effect.TypeEffects;
/**
 * Effets des consommables
 */

public class Consumable extends Item{

	private Effect effect;

	public Consumable(int price, HashMap<State, BufferedImage> graphics, String name) {
		super(price, graphics, name);
	}

	public Consumable(String name, String graphicsSelector,Effect effect) {
		super(name, graphicsSelector);
    	graphics = Utilities.getGraphicsFromJSON(graphicsSelector);
		this.effect =effect;
    }

	public Effect getEffect() {
		return this.effect;
	}
    
}
