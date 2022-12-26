package prefab.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import prefab.competence.Attack;
import prefab.competence.Spell;
import prefab.equipment.Armor;
import prefab.equipment.Consumable;
import prefab.equipment.Effect;
import prefab.equipment.Item;
import prefab.equipment.Weapon;
import prefab.equipment.Armor.ArmorPieces;
import prefab.equipment.Effect.TypeEffects;
import prefab.information.Position;
import prefab.information.State;
import prefab.information.Stats;
import prefab.rendering.Animation;
import prefab.rendering.CharacterAnimation;

/**
 * représente les personnages du jeu de manière générale
 */
public abstract class Character extends GameObject {

    String name;

    HashMap<Stats, Integer> stats;
    HashMap<Stats, Integer> currentStats;

    int money;
    int level;
    int xp;

    public static final int inventoryLengthX = 15;
    public static final int inventoryLengthY = 6;
    Item[][] inventory = new Item[inventoryLengthX][inventoryLengthY];

    HashMap<ArmorPieces,Armor> equippedArmor;

    Weapon weapon;

    List<Attack> attacks;
    List<Spell> spells;

    List<Effect> effects;

    private boolean isAlvie = true;
    private boolean isInMouvement = false;


    // ___________________________________
    // __________ CONSTRUCTEURS __________


     /**
      * constructeur de la classe Character heritant de GameObject
      * @param position position de l'objet
      * @param animation animation de l'objet
      * @param horizontalHitBox largeur de la hitbox de l'objet
      * @param verticalHitBox hauteur de la hitbox de l'objet
      * @param name nom du personnage
     * @throws CloneNotSupportedException
      */
    Character(Position position, Animation animation, int horizontalHitBox, int verticalHitBox, String name) throws CloneNotSupportedException {
        super(position, animation, horizontalHitBox, verticalHitBox, State.DEFAULT);
        this.name = name;
        this.xp = 0;
        this.attacks = new ArrayList<Attack>();
        this.spells = new ArrayList<Spell>();
        this.effects = new ArrayList<>();
        this.stats  =  new HashMap<Stats , Integer>();
        this.currentStats = new HashMap<Stats , Integer>();
        initDefaultEquipment();
    }

     /**
      * constructeur de la classe Character heritant de GameObject
      * @param position position de l'objet
      * @param animation animation de l'objet
      * @param horizontalHitBox largeur de la hitbox de l'objet
      * @param verticalHitBox hauteur de la hitbox de l'objet
      * @param name nom du personnage
      * @param stats les stats par defaut du personnage
      * @param money l'argent par defaut du personnage
      * @param level le niveau par defaut du personnage
      * @param xp l'experience par defaut du personnage
      * @param attacks les attaques par defaut du personnage
      * @param spells les sorts par defaut du personnage
     * @throws CloneNotSupportedException
      */
    Character(Position position, Animation animation, int horizontalHitBox, int verticalHitBox,  String name,
            HashMap<Stats, Integer> stats, int money, int level, int xp, List<Attack> attacks,List<Spell> spells) throws CloneNotSupportedException {
        this(position, animation, horizontalHitBox, verticalHitBox, name);
        this.stats = stats;
        this.money = money;
        this.level = level;
        this.xp = xp;
        this.attacks = attacks;
        this.spells = spells;
        resetCurrentStats();
    }


    // ____________________________________
    // __________ INITIALISATION __________

    public void resetCurrentStats(){
        currentStats.putAll(stats);
    }

    public void initDefaultEquipment(){
        equippedArmor = new HashMap<ArmorPieces, Armor>();
        equippedArmor.put(ArmorPieces.HELMET,null);
        equippedArmor.put(ArmorPieces.CHESTPLATE,null);
        equippedArmor.put(ArmorPieces.LEGGING,null);
        equippedArmor.put(ArmorPieces.BOOTS,null);
    }

    /**
     * initailise les charactéristiques par défaut du personnage
     */
    protected abstract void initCharacteristic();

    // _____________________________
    // __________ GETTERS __________

    public HashMap<Stats, Integer> getStats(){
        return stats;
    }

    public HashMap<Stats, Integer> getCurrentStats(){
        return currentStats;
    }

    public Item[][] getInventory() {
        return inventory;
    }

    public List<Attack> getAttacks() {
        return attacks;
    }

    public List<Spell> getSpells() {
        return spells;
    }

    public List<Effect> getEffects() {
        return  this.effects;
    }

    public boolean getIsAlive() {
    	return this.isAlvie;
    }

    // _____________________________
    // __________ SETTERS __________

    public void setEquippedArmor(HashMap<ArmorPieces, Armor> newEquippedArmor) {
        this.equippedArmor = newEquippedArmor;
    }

    public void setEquippedWeapon(Weapon newWeapon) {
        this.weapon = newWeapon;
    }


    public void setIsInFight(boolean isInFight) {
        isInFight = true;
        ((CharacterAnimation) animation).setIsInFight(true);
        setState(State.FIGHT);
    }

    // ___________________________________
    // __________ VIE DU JOUEUR __________

    /**
	 * inflige des dommages au joueur
	 * @param value Valeur des degats
	 */
    public void takeDammage(int value){

        int damage = Math.max( (int) (value*(120-currentStats.get(Stats.DEFENSE))/100f), 0);

        System.out.println("damage "+damage);

        int newHp =  Math.max(currentStats.get(Stats.HP) - damage, 0);
        currentStats.replace(Stats.HP, newHp);
        if (currentStats.get(Stats.HP) == 0)
            die();
    }

    /**
	 * redonne de la vie au joueur
	 * @param value Valeur des points de vie rendus
	 */
    public void healCharacter(int value){

        value = Math.min(stats.get(Stats.HP), currentStats.get(Stats.HP) + value);
        currentStats.put(Stats.HP, value);
    }

    // _______________________________________
    // __________ ACTIONS EN COMBAT __________

    /**
     * attaque un adversaire
     * @param target l'adversaire sur qui l'attaque est dirigée
     * @param pos la position de l'attaque dans la liste
     * @param attackName sert à récupèrer le nom de l'attaque
     * @return
     *      <code>true</code> si l'attaque peut être lancée
     *      <li><code>false</code> sinon
     */
    public boolean attack(Character target, int pos, String[] attackName) {

        Attack atk = this.attacks.get(pos);
        attackName[0] = atk.toString();
        int staminaConsuption = atk.getStaminaConsuption();
        if (!consumeEnergy(staminaConsuption, Stats.STAMINA)) {
            return false;
        }
        int atkDamages = atk.getDamage();
        int weaponPower =  weapon == null ? 30 : weapon.getPower();
        int characDamage = currentStats.get(Stats.DAMAGE);
        int finalDamages = (int) (atkDamages*weaponPower*characDamage/3000f);
        target.takeDammage(finalDamages);
        int backlash = atk.getBacklash();
        if (backlash > 0) {
            takeDammage(backlash);
        }
        return true;
    }

    /**
     * lance un sort sur un adversaire
     * @param target l'adversaire sur qui le sort est dirigé
     * @param pos la position du sort dans la liste
     * @param attackName sert à récupèrer le nom du sort
     * @return
     *      <code>true</code> si le sort peut être lancé
     *      <li><code>false</code> sinon
     */
    public boolean lauchSpell(Character target, int pos, String[] attackName) {

    	Spell spell = this.spells.get(pos);
        attackName[0] = spell.toString();
        int staminaConsuption = spell.getManaConsuption();
        if (!consumeEnergy(staminaConsuption, Stats.MANA)) {
            return false;
        }
        int spellDamages = spell.getDamage();
        boolean isEffectsOk = addEffects(spell.getEffects());
        if (spellDamages == 0) {
            return isEffectsOk;
        }
        int characDamage = currentStats.get(Stats.DAMAGE);
        int finalDamages = (int) (spellDamages*spellDamages*characDamage/3000f);
        target.takeDammage(finalDamages);
        return true;
    }

    /**
     * consomme de l'énergie
     * @param value niveau d'énergie
     * @param stat mana ou stamina
     * @return
     *      <code>true</code> si le niveau d'énergie requis tes suffisant
     *      <li><code>false</code> sinon
     */
    private boolean consumeEnergy(int value, Stats stat){
        int newEnergy = currentStats.get(stat) - value;
        if (newEnergy >= 0) {
            currentStats.replace(stat, newEnergy);
            return true;
        }
        return false;
    }

    /**
     * utilise un consommable
     * @param consumable
     * @return
     *      <code>true</code> si le consommable peut être utilisé
     *      <li><code>false</code> sinon
     */
    public boolean useConsumable(Consumable consumable) {

        return addEffects(consumable.getEffects());
    }

    // ____________________________
    // __________ ITEMS ___________

    /**
     * ajoute un item dans l'inventaire
     * @param item l'item à ajouter
     */
    public void addItem(Item item) {
    	 for (int j = 0; j < Character.inventoryLengthY; j++) {
             for (int i = 0; i < Character.inventoryLengthX; i++) {
                 if ( inventory[i][j] == null) {
                	 inventory[i][j] = item;
                	 return;
                 }
             }
         }
    }

    /**
     * supprime un item
     * @param posItem position de l'item
     */
    public void deleteItem(int[] posItem) {
        inventory[posItem[0]][posItem[1]] = null;
    }

    /**
     * remplace un item par un autre dans l'inventaire
     * @param item objet que l'on souhaite ajouté
     * @param positionX position X à laquelle on veut ranger l'objet
     * @param positionY position Y à laquelle on veut ranger l'objet
     * @return
     *      <code>null</code> si l'emplacement est vide
     *      <li><code>Item</code> l'objet à l'emplacement [x][y]
     */
    public Item switchItem(Item item, int positionX, int positionY) {
        Item itemExchanged = inventory[positionX][positionY];
        inventory[positionX][positionY] = item;
        return itemExchanged;
    }

    // __________________________________
    // __________ DEPLACEMENTS __________

    /**
     * déplace le personnage
     */
    public void move(int deltaX, int deltaY) {
        position = position.addToXY(deltaX, deltaY);
        // position est immubale donc pas de (Position) position.clone()
        isInMouvement = true;
        ((CharacterAnimation) animation).playMovement(deltaX, deltaY, position);
    }

     /**
     * 2 fonctionnalités :
     * <ul>
     * <li> informme si l'animlation de déplacement est en train d'âtre jouée
     * <li> si le personnage s'arrête et que l'animation de déplacement est finie
     * on remet l'état de l'animation en "IDLE"
     * </ul>
     * @return
     *      <code>true</code> si l'animation de mouvement est en train d'être jouée
     *      <li><code>false</code> sinon
     */
    public boolean AnimationPlayMoving( ) {
        boolean playMvt = ((CharacterAnimation) animation).getPlayMoving();
        if (!playMvt && isInMouvement) {
            isInMouvement = false;
            animation.setState(state);
        }
    	return playMvt;
    }

    public void startAnimation() {
        animation.playingAnimation();
    }

    // ____________________________
    // __________ EFFETS __________

    /**
     * ajoute plusieurs effets au personnage
     * si au moins un effet de la liste peut être ajouté, on retourne vraie
     * @param newEffects la liste en question
     * @return
     *       <code>true</code> si au moins un effet a pu être ajouté
     *       <li><code>false</code> sinon
     */
    public boolean addEffects(List<Effect> newEffects) {
        boolean isEffectAdded = false;
        for (Effect newEffect : newEffects) {
            if (addEffect(newEffect)) {
                isEffectAdded = true;
            }
        }
        return isEffectAdded;
    }

    /**
     * ajoute un effet (si possible)
     * par exemple si le personnage a toute sa vie, l'effet heal n'est pas ajouté
     * @param newEffect l'effet à ajouter
     * @return
     *      <code>true</code> si l'effet a pu être ajouté
     *      <li><code>false</code> sinon
     */
    public boolean addEffect(Effect newEffect) {

        if (stats.get(Stats.HP) == currentStats.get(Stats.HP) && newEffect.getTypeEffects() == TypeEffects.HEAL) {
            return false;
        }

        this.effects.add(newEffect);
        return true;
    }


    // ___________________________
    // __________ AUTRE __________


    /**
     * gère la mort du personnage
     */
    public void die() {
        setState(State.DEAD);
        this.isAlvie = false;
    }

    public String toString() {
        return name;
    }
}
