package prefab.props;

import java.util.List;
import java.util.Random;

import engine.Cmd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import prefab.information.Position;
import prefab.information.State;
import prefab.rendering.Animation;
import prefab.rendering.Visual;
import prefab.entity.GameObject;
import prefab.entity.Player;
import prefab.entity.Player.PlayerClasses;
import prefab.equipment.Consumable;
import prefab.equipment.Effect;
import prefab.equipment.Item;
import prefab.equipment.Weapon;
import prefab.equipment.Effect.TypeEffects;
import prefab.gui.InventoryHud;


/**
 * représente un coffre ouvrable par le joueur
 */
public class Chest extends GameObject implements UsableObject{
    

    private static final int chestFirstPosX = 10, chestFirstPosY = 11;
    private static final int inventoryLength = 15;
    private Item[] chestContents = new Item[inventoryLength];

    private InventoryHud inventoryHud;


    /**
     * constructeur de la classe Chest heritant de GameObject
     * @throws CloneNotSupportedException
     */
    public Chest(Position position, Animation animation, int horizontalHitBox, int verticalHitBox) throws CloneNotSupportedException {
        super(position, animation, horizontalHitBox, verticalHitBox, State.CLOSE);
        this.inventoryHud = InventoryHud.getInstance();
    }
    
    
    public Chest(Position position, Animation animation, int horizontalHitBox, int verticalHitBox, Item[] chestContents) throws CloneNotSupportedException {
        this(position, animation, horizontalHitBox, verticalHitBox);
        initChestContents(chestContents);
        this.inventoryHud = InventoryHud.getInstance();
        
    }

    


    public void initChestContents(Item [] c){
        for(Item item : c){
            int k=0;
            while (chestContents[k] != null){
                k++;
            }
            chestContents[k]=item;
        }
    }

    /**
    * le joueur ouvre le coffre
    * @param user l'utilisateur du coffre
    * @return true le joueur a obtenu le contenu du coffre
    */
    @Override
    public void objectUse(Player user,Cmd cmd) {
        // il faut afficher inventaire
        // et le contenu du coffre puis échanger objet avec la souris
        if (getState() == State.OPEN ){
            setState(State.CLOSE);
            return;
        }
        inventoryHud.openWith(this);
        setState(State.OPEN);
    }

    public List<Visual> getVisuals() throws Exception{
        ArrayList<Visual> visuals = new ArrayList<Visual>();
        for ( int line=0; line<chestContents.length; line++ ) {
            int x = chestFirstPosX + line ;
            if(chestContents[line] != null)  visuals.add(Visual.createWithGameCoord(x, chestFirstPosY, chestContents[line].getImage()));
        }    
        return visuals;
    }

    public Item[] getChestContents() {
        return chestContents;
    }

    static Item[] addElement(Item[] a, Item e) {
        a  = Arrays.copyOf(a, a.length + 1);
        a[a.length - 1] = e;
        return a;
    }

    private static final List<PlayerClasses> playerClasses = Collections.unmodifiableList(Arrays.asList(PlayerClasses.values()));
    private static final int SIZE = playerClasses.size();
    private static final Random RANDOM = new Random();

    public static PlayerClasses randomClasses()  {
        return playerClasses.get(RANDOM.nextInt(SIZE));
    }

    public static Item[] fillChestItem() {
        List<Effect> effectPopo = new ArrayList<Effect>();
        Item[] chestContents = new Item[]{};
        double isInChest = Math.random();
        if (isInChest > 0.5){
            PlayerClasses playerClasses = randomClasses();
            Weapon weapon;
            if (playerClasses == PlayerClasses.ARCHER){
                weapon = new Weapon("epee pirate", "basicWeaponArcher", (int) (30+Math.round(Math.random()*50)));
                chestContents = addElement(chestContents, weapon);
            }

            if (playerClasses == PlayerClasses.WARRIOR){
                weapon = new Weapon("epee pirate", "basicWeaponWarrior", (int) (30+Math.round(Math.random()*50)));
                chestContents = addElement(chestContents, weapon);
            }
            if (playerClasses == PlayerClasses.ASSASSIN){
                weapon = new Weapon("epee pirate", "basicWeaponAssassin", (int) (30+Math.round(Math.random()*50)));
                chestContents = addElement(chestContents, weapon);
            }
            if (playerClasses == PlayerClasses.MAGE){
                weapon = new Weapon("epee pirate", "basicWeaponMage", (int) (30+Math.round(Math.random()*50)));
                chestContents = addElement(chestContents, weapon);
            }
        }

        isInChest = Math.random();

        if (isInChest > 0.5){
            effectPopo =new ArrayList<Effect>();
            effectPopo.add(new Effect(TypeEffects.HEAL, (int) (10+Math.round(Math.random()*50))));
            Consumable consumable = new Consumable("Potion de soin","potion_heal", effectPopo);
            chestContents = addElement(chestContents, consumable);
        }

        isInChest = Math.random();

        if (isInChest > 0.5){
            effectPopo =new ArrayList<Effect>();
            effectPopo.add(new Effect(TypeEffects.HEAL, (int) (10+Math.round(Math.random()*50))));
            Consumable consumable = new Consumable("Potion de soin","potion_heal", effectPopo);
            chestContents = addElement(chestContents, consumable);
        }

        isInChest = Math.random();

        if (isInChest > 0.5){
            effectPopo =new ArrayList<Effect>();
            effectPopo.add(new Effect(TypeEffects.MANA, (int) (10+Math.round(Math.random()*50))));
            Consumable consumable = new Consumable("Potion de mana","potion_mana", effectPopo);
            chestContents = addElement(chestContents, consumable);
        }

        isInChest = Math.random();

        if (isInChest > 0.5){
            effectPopo =new ArrayList<Effect>();
            effectPopo.add(new Effect(TypeEffects.DEFENSEDOWN, (int) (10+Math.round(Math.random()*20))));
            Consumable consumable = new Consumable("Clementine","clementine", effectPopo);
            chestContents = addElement(chestContents, consumable);
        }

        isInChest = Math.random();

        if (isInChest > 0.5){
            effectPopo =new ArrayList<Effect>();
            effectPopo.add(new Effect(TypeEffects.HIT,(int) (10+Math.round(Math.random()*50))));
            Consumable consumable = new Consumable("Burger","burger", effectPopo);
            chestContents = addElement(chestContents, consumable);
        }
        return chestContents;
    }

    public static Item[] fillChestItemTest() {
        List<Effect> effectPopo = new ArrayList<Effect>();
        effectPopo.add(new Effect(TypeEffects.HEAL, 10));
        Item[] chestContents = new Item[]{new Weapon("epee pirate", "sword_1", 42),
        new Consumable("Potion de soin","potion_heal", effectPopo)};

        return chestContents;
    }
}
