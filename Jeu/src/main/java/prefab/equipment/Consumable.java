package prefab.equipment;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import manager.Utilities;


import prefab.information.State;

/**
 * Effets des consommables
 */

public class Consumable extends Item{

	private Effect effect;

	public Consumable(String name, String graphicsSelector, int price, Effect effect) {
		super(name, graphicsSelector, price);
		this.effect =effect;

	}

	public Consumable(String name, String graphicsSelector,Effect effect) {
		this(name, graphicsSelector, 0, effect);
    }

	public Effect getEffect() {
		return this.effect;
	}
    
}
