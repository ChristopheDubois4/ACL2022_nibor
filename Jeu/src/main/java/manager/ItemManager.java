package manager;

import prefab.equipment.*;
import prefab.equipment.Armor.ArmorPieces;
import prefab.equipment.Effect.TypeEffects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import prefab.entity.Character;



public class ItemManager {
	
    public static void useConsumable(int[] posItem, Character character) {

    	Consumable c =  (Consumable) character.getInventory()[posItem[0]][posItem[1]];
  
        character.addEffects(c.getEffects());
        Effect.applyEffects(character);
		deleteItem(posItem, character);
        	
    }
    
    public static void createItem(String file){

        // récupération du fichier JSON a partir d'un chemin
        File directory = new File(file);
        JSONParser jsonParser = new JSONParser();

        // lecture du fichier JSON
        try (FileReader reader = new FileReader(directory))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            // tableau des items sous format JSON
            JSONArray items = (JSONArray) obj;
            System.out.println(items);
            // 1 seul item dans le json donc index 0
            JSONObject item = (JSONObject) items.get(0);

            String type = (String) item.get("type");
            String itemName = (String) item.get("name");
            String graphicsSelector =(String) item.get("graphics");
            // tableau des effets sous format JSON
            JSONArray effectObjects = (JSONArray) item.get("Effects");
            //création de la liste des effets d'un item
            List<Effect> itemEffects = new ArrayList<Effect>();
            
            // parcours des effets de l'objet
            for (int k = 0; k < effectObjects.size() ; k++) {
                // Kième effect de l'objet
                JSONObject effect = (JSONObject) effectObjects.get(k);
                TypeEffects typeEffects = (TypeEffects) effect.get("TypeEffects");
                int powerValue = (int) effect.get("powerValue");
                itemEffects.add(new Effect(typeEffects, powerValue));
            }

            // traitement différent selon le type de l'objet
            switch (type) {
                case "Weapon" :
                    Weapon weapon = new Weapon(itemName, graphicsSelector, itemEffects);
                    break; 
                case "Consumable" :
                    Consumable consumable = new  Consumable(itemName,graphicsSelector,itemEffects);//recup les parametres pour le constructeur
                    break;       
                case "Armor" :
                    // 
                    ArmorPieces armorPieces = (ArmorPieces) item.get("graphics");
                    Armor armor = new Armor(itemName,graphicsSelector,armorPieces,itemEffects);//recup les parametres pour le constructeur
                    break;   
            }
                   
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

        // TESTS
        /*
        System.out.println("DANS LE WHILE");
        while (true);
        */
    }

    //on delete l'item
    public static  void deleteItem(int[] posItem, Character character){
        Item[][] playerInventory = character.getInventory();
        playerInventory[posItem[0]][posItem[1]] = null;
    }

}
