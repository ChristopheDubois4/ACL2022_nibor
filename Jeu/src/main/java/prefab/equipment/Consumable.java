package prefab.equipment;

import java.util.List;

/**
 * Effets des consommables
 */

public class Consumable extends Item{

	private List<Effect> effects;
	private boolean isThrowable = false;

	public Consumable(String name, String graphicsSelector, int price, List<Effect> effects) {
		super(name, graphicsSelector, price);
		this.effects =effects;

	}

	public Consumable(String name, String graphicsSelector,List<Effect> effects) {
		this(name, graphicsSelector, 0, effects);
    }

	public List<Effect> getEffects() {
		return this.effects;
	}
	
	public boolean getIsThrowable() {
		return isThrowable;
	}
    
}
