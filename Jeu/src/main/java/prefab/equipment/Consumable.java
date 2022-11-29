package prefab.equipment;


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
