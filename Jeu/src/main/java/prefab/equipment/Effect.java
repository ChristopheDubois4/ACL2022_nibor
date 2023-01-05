package prefab.equipment;

import java.util.HashMap;
import java.util.List;

import prefab.entity.Character;
import prefab.information.Stats;

public class Effect {
	
	public enum TypeEffects {
	   
	    HEAL,
		MANA,
	    HIT,
	    DEFENSEDOWN,
	    POISON,
	    SLOWNESS,
		BLEEDING;	    
	}
	
	private TypeEffects typeEffects;
	private int powerValue = 0;
	private int duration = 1;
	
	public Effect(TypeEffects typeEffects, int powerValue) {
		this.typeEffects = typeEffects;
		this.powerValue = powerValue;
	}
	
	public TypeEffects getTypeEffects() {
		return typeEffects;
	}
	
	public int getPowerValue() {
		return powerValue;
	}
	
	public void decreaseDuration() {
		duration--;
	}
	
	public static void applyEffects(Character character) {
		    	
    	HashMap<Stats, Integer> stats = character.getStats();
    	HashMap<Stats, Integer> currentStats = character.getCurrentStats();
    	
    	List<Effect> effects = character.getEffects();

		if (effects.size() == 0) {
			return;
		}
    	
    	
    	for (Effect effect : effects) {    
    		switch(effect.getTypeEffects()){
     	   
            case HEAL: 
				System.out.println("effect.getPowerValue()"+effect.getPowerValue());
				character.healCharacter(effect.getPowerValue());
                break;
			case MANA: 
				character.restoreEnergy(effect.getPowerValue(), Stats.MANA);
				break;          
            case HIT:
            	character.takeDammage(effect.getPowerValue());
                break;        
            case DEFENSEDOWN:
				currentStats.replace(Stats.DEFENSE , Math.max(0,currentStats.get(Stats.DEFENSE)-effect.getPowerValue())); 
                break;
            case POISON:
                //System.out.println("Buenos dias");
                break;
            case SLOWNESS:
                //System.out.println("Buenos dias");
                break;
            default:
            	return;
    		}
			effect.decreaseDuration();    		
		}
		//stocker les effets appliqué pour les remove tous
		if (effects.get(0).getDuration() <= 0){
			effects.remove(effects.get(0));
		}
	}
	
	private int getDuration() {
		return 0;
	}

}

	