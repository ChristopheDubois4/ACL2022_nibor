package manager;
import prefab.equipment.*;

import java.util.ArrayList;
import java.util.List;

import prefab.entity.Character;



public class ItemManager {
	
    public static void useConsumable(int[] posItem, Character character) {
    	
    	Consumable c =  (Consumable) character.getInventory()[posItem[0]][posItem[1]];
    	List<Effect> effects = new ArrayList<Effect>();
    	effects.add(c.getEffect());
        character.addEffects(effects);
        Effect.applyEffects(character);
		deleteItem(posItem, character);
        	
    }
    
    //on delete l'item
    public static  void deleteItem(int[] posItem, Character character){
        Item[][] playerInventory = character.getInventory();
        playerInventory[posItem[0]][posItem[1]] = null;
    }

}
