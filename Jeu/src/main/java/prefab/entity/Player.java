package prefab.entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import manager.Utilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import prefab.competence.Attack;
import prefab.competence.Spell;
import prefab.equipment.Armor;
import prefab.equipment.Armor.ArmorPieces;
import prefab.equipment.Consumable;
import prefab.equipment.Effect;
import prefab.equipment.Item;
import prefab.equipment.Weapon;
import prefab.equipment.Effect.TypeEffects;
import prefab.gui.PlayerInfosFofHud;
import prefab.information.Position;
import prefab.information.Stats;
import prefab.rendering.CharacterAnimation;


/**
 * représente le joueur, un personnage particulié du jeu que l'on contrôle
 */

public class Player extends Character implements PlayerInfosFofHud{

    /**
     * les classes de joueur sélectionables en début de jeu
     */
    public enum PlayerClasses {
        MAGE,
        WARRIOR,
        ASSASSIN,
        ARCHER
    }
    
    private static Player INSTANCE;
    
    PlayerClasses classPlayed;
    
    
    private int xpToNextLevel = 100;
    
    
    /**
    * constructeur de la classe Player heritant de Character
    * @param classPlayed la classe de combattant du joueur
    * @throws CloneNotSupportedException
    * @throws Exception
    */
   private Player() throws CloneNotSupportedException, Exception {              
       super(
           Position.create(10, 10),
           CharacterAnimation.createForPlayer(Utilities.getSpritesFromJSON("player")),
           1,
           1,
           "Nibor"
       );        
   }

   public static Player getInstance() throws CloneNotSupportedException, Exception {
       if (INSTANCE == null) {
           INSTANCE = new Player();
       }
       return INSTANCE;
   }

   public void initPlayer(PlayerClasses classPlayed) throws Exception {
       this.classPlayed = classPlayed;
       initCharacteristic();
       startAnimation();
   }

    /**
     * méthode qui initailise les charactéristiques par défaut du joueur 
     * selon la classe qu'il à choisi
     * @throws Exception
     */
    protected void initCharacteristic() throws Exception{    
        attacks = new ArrayList<Attack>();
        spells = new ArrayList<Spell>();
        inventory = new Item[inventoryLengthX][inventoryLengthY];

        initDefaultEquipment();
        setWeapon(new Weapon(null, "", 0));
        
        initClassFromJson();
        updateCurrentStats();

    }   
    
    public void receiveXp(int newXp) {
    	xp += newXp;
    	if (xp >= xpToNextLevel) {
    		levelUp();
    		xp -= xpToNextLevel;
    		xpToNextLevel = (int) (1.2*xpToNextLevel);
    	}
    }
    
    private void levelUp() {
    	level++;
    	
    }



    /**
     * utilise un consommable
     * (méthode pour le joueur)
     * @param posItem la position de l'item dans l'inventaire
     * @return 
     *      - vraie si le consommable peut être utilisé
     *      - faux sinon
     */
    public boolean useConsumable(int[] posItem) {

        try {
            if (addEffects(((Consumable) inventory[posItem[0]][posItem[1]]).getEffects())) {
                deleteItem(posItem);
                return true;
            } 
        } catch (Exception e) {
            System.out.println("(1) La position de l'objet est invalide\n(2) L'objet n'est pas de type 'Consumable'");
        }
        return false;
    }

    @Override
    public void updateCurrentStats() {
        resetCurrentStats();
        Set<Entry<ArmorPieces, Armor>> set1 = equippedArmor.entrySet();
        for (Entry<ArmorPieces, Armor> e1 : set1) {
            ArmorPieces key1 = e1.getKey();
            Armor map2 = e1.getValue();
            Set<Entry<Stats, Integer>> set2 = map2.getBonusStats().entrySet();
            for (Entry<Stats, Integer> e2 : set2) {
                Stats key = e2.getKey();
                Integer value = e2.getValue();
                currentStats.put(key, currentStats.get(key) + value); 
                currentBonusStats.put(key, currentBonusStats.get(key) + value); 
            }
            
        }
    	
    }

    @Override
    public int getMoney() {       
        return this.money;
    }
    
    @Override
    public HashMap<ArmorPieces, Armor> getEquipedArmor() {
        return equippedArmor;
    }

	public int getHealth() {
		return currentStats.get(Stats.HP);
	}

    public Weapon getWeapon() {
        return weapon;
    }
    
    @Override
    public void setWeapon(Weapon newWeapon) {
        this.weapon=newWeapon;
    }

    public void setClass(PlayerClasses classSelected) throws Exception{
        this.classPlayed = classSelected;
        initCharacteristic();

    }
    
    public void initClassFromJson() throws Exception {

        String pathClassPlayed = "src/main/ressources/ClassAbilities/"+classPlayed.toString().toLowerCase()+".json";
        String pathBaseEquipment = "src/main/ressources/items/equipment.json";

        File directory = new File(pathClassPlayed);
        File directory1 = new File(pathBaseEquipment);
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(directory))
        {            
            //Read JSON file
            Object obj = jsonParser.parse(reader); 
            JSONArray objects = (JSONArray) obj;
            JSONObject jObjects = (JSONObject) objects.get(0);

            JSONObject initClass = (JSONObject) jObjects.get("init");
            
            JSONObject basicStats = (JSONObject) initClass.get("basicStats");

            JSONArray basicAttacks = (JSONArray) initClass.get("basicAttacks");

            JSONArray basicSpells = (JSONArray) initClass.get("basicSpells");

            //JSONObject basicEquippments = (JSONObject) initClass.get("basicEquippments");

            for (Iterator iterator = basicStats.keySet().iterator(); iterator.hasNext(); ) {
                String key = (String) iterator.next();
                int val = ((Long)  basicStats.get(key)).intValue();
                stats.put(Stats.valueOf(key), val);
                currentBonusStats.put(Stats.valueOf(key),0);
            }

            for (Object iterator : basicAttacks) {
                JSONObject iteratorJson = (JSONObject) iterator;
                String name = (String) iteratorJson.get("name");
                int damage = ((Long) iteratorJson.get("damage")).intValue();
                int staminaConsuption = ((Long) iteratorJson.get("staminaConsuption")).intValue();
                if (iteratorJson.containsKey("backlash")){
                    int backlash = ((Long) iteratorJson.get("backlash")).intValue();
                    attacks.add(new Attack(name, damage ,staminaConsuption, backlash));
                } else {
                    attacks.add(new Attack(name, damage ,staminaConsuption));
                }
            }

            for (Object iterator : basicSpells) {
                JSONObject iteratorJson = (JSONObject) iterator;
                String name = (String) iteratorJson.get("name");
                int damage = ((Long) iteratorJson.get("damage")).intValue();
                int manaConsuption = ((Long) iteratorJson.get("manaConsuption")).intValue();
                List<Effect> effectSpell = new ArrayList<Effect>();
                if (iteratorJson.containsKey("effects")){
                    JSONArray effects = (JSONArray) iteratorJson.get("effects");
                    for (Object iteratorEffect : effects) {
                        JSONObject iteratorJsoniteratorEffect = (JSONObject) iteratorEffect;
                        String type = (String) iteratorJsoniteratorEffect.get("type");
                        int powerValue = ((Long) iteratorJsoniteratorEffect.get("powerValue")).intValue();
                        effectSpell.add(new Effect(TypeEffects.valueOf(type), powerValue));
                    }
                }
                spells.add(new Spell(name, damage, manaConsuption, effectSpell));
            }



            /*
            for (Iterator iterator = basicEquippments.keySet().iterator(); iterator.hasNext(); ) {
                String key = (String) iterator.next();
                String equipmentID = (String) basicEquippments.get(key);
                loadItem(equipmentID);
            }
             */
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

        try (FileReader reader = new FileReader(directory1)){
            Object obj = jsonParser.parse(reader); 
            JSONArray objects = (JSONArray) obj;
            JSONObject jObjects = (JSONObject) objects.get(0);
            JSONObject initStuff = (JSONObject) jObjects.get("baseStuff");

            JSONObject weapon = (JSONObject) initStuff.get(classPlayed.toString().toLowerCase());
            setWeapon(new Weapon((String) weapon.get("name"), (String) weapon.get("graphics"), ((Long) weapon.get("powerValue")).intValue()));
            

            HashMap<ArmorPieces,Armor> equippedArmorTemp = new HashMap<ArmorPieces,Armor>();

            for (int i = 1 ; i < 5 ; i++){
                JSONObject armor = (JSONObject) initStuff.get(Integer.toString(i));

                ArmorPieces type = ArmorPieces.valueOf(((String) armor.get("type")));
                String name = (String) armor.get("name");
                String graphics = (String) armor.get("graphics");
                HashMap<Stats, Integer> bonusStats = new HashMap<Stats, Integer>();
                if (armor.containsKey("bonusStats")){
                    JSONObject bonusStatsJson = (JSONObject) armor.get("bonusStats");
                    for (Iterator iterator = bonusStatsJson.keySet().iterator(); iterator.hasNext(); ) {
                        String key = (String) iterator.next();
                        int val = ((Long)  bonusStatsJson.get(key)).intValue();
                        bonusStats.put(Stats.valueOf(key), val);
                    }
                }
                equippedArmorTemp.put(type,new Armor(name, graphics, type,bonusStats));
            }
            

            setEquippedArmor(equippedArmorTemp);
            

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }

    
}
