package prefab.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.BasicStroke;
import java.awt.FontMetrics;

import java.util.ArrayList;
import java.util.List;

import model.NiborPainter;
import prefab.entity.Character;
import prefab.entity.Player;
import prefab.information.Layer;
import prefab.information.Stats;
import prefab.rendering.Visual;
import manager.Utilities;

/**
 * <b>[SINGLETON]</b>
 * <p>gère l'HUD lié aux combats
 */
public class FightHud extends Hud{

    private static final FightHud INSTANCE = new FightHud();

    private String fightPath = "src/main/ressources/images/huds/fight/";
    private String[] decors = {"plaines_1"};
    private BufferedImage backgroundImage;

    private List<Visual> frontVisuals;
	
	private Character player;
	private Character enemy;
    private int tile = NiborPainter.TILE_LENGTH;
    private int screenH = NiborPainter.SCREEN_HEIGHT;

    // informations pour les menus
    private String[] menus = {"ATTAQUES", "MAGIE", "CONSOMMABLES"};
    private static final int maxSubmenuRows = 10;
    private boolean isMenuOpen = false;
    private int cursorMenu = 0;
    private int cursorSubMenu = 0;
    private int selectedTarget = -1;
    private List<String> submenuNames;

    // message affiché dans le panneau d'affichage
    public String message = "";

     // couleurs des bars
    private Color colorHealth = new Color(181,230,29);
    private Color colorMana = new Color(0,162,232);
    private Color colorStamina = new Color(255,201,14);
    private Color colorBarBackground = new Color(0,0,0, 70);

     // couleurs des menus
    private Color colortextMenu = new Color(210,210,210);
    private Color colorMenu = new Color(49,73,106);
    private Color colorMenuActive = Color.white;
    private Color colorMenuBackground = colorBarBackground;
    private Color colorMenuBackgroundActive = new Color(0,0,0, 140);

    // indices pour afficher les sous-menus
    private int iMax = maxSubmenuRows;
    private int iMin = 0;

    /**
     * constructeur de la classe FightHud
     */
	public FightHud() {
        super();
    }

    /**
     * initialise le fightHud
     * @throws Exception
     */
    @Override
    public void initHud() throws Exception {
        this.player = Player.getInstance();
        submenuNames = new ArrayList<String>();
        initVisuals();        
    }

    public static FightHud getInstance() {
        return INSTANCE;
    }


    private void initVisuals() throws Exception {
        this.backgroundImage = Utilities.getImage(fightPath+decors[0] + ".png");
        BufferedImage healthIcon = Utilities.getImage(fightPath + "health_icon.png");
        BufferedImage manaIcon = Utilities.getImage(fightPath + "mana_icon.png");
        BufferedImage staminaIcon = Utilities.getImage(fightPath + "stamina_icon.png");

        this.frontVisuals = new ArrayList<Visual>();

        // côté joueur
        this.frontVisuals.add(Visual.createWithGameCoord(1, 2, -16, 15, healthIcon));
        this.frontVisuals.add(Visual.createWithGameCoord(1, 1, -10, 11, manaIcon));
        this.frontVisuals.add(Visual.createWithGameCoord(1, 0, -6, 23, staminaIcon));

        // côté ennemi
        this.frontVisuals.add(Visual.createWithGameCoord(25, 2, 19, 15, healthIcon, true, false));
        this.frontVisuals.add(Visual.createWithGameCoord(25, 1, 25, 11, manaIcon, true, false));
        this.frontVisuals.add(Visual.createWithGameCoord(25, 0, 25, 23, staminaIcon, true, false));
    }

    public void changeDisplayState(int numDecor) {
        super.changeDisplayState();
        if (isDisplayed) {
            this.backgroundImage = Utilities.getImage(fightPath+decors[numDecor] + ".png");
        }
    }
	
    /**
     * charge un ennemi dans le hud
     * @param enemy l'ennemi
     */
	public void loadEnemy(Character enemy) {
		this.enemy = enemy;
	}
	
    /**
     * met à jour les informations de l'Hud
     * @param cursorMenu emplacement du curseur du menu
     * @param isMenuOpen vrai si un menu est ouvert, faux sinon
     * @param cursorSubMenu emplacement du curseur du sous-menu
     * @param submenuNames liste des noms à afficher dans le sous-menu
     * @param selectedTarget la cible selectionnée
     */
    public void updateHud(int cursorMenu, boolean isMenuOpen, int cursorSubMenu, List<String> submenuNames, int selectedTarget) {
        
		this.cursorMenu = cursorMenu;
        this.submenuNames = submenuNames;
        this.selectedTarget = selectedTarget;
        this.cursorSubMenu = cursorSubMenu; 
        this.isMenuOpen = isMenuOpen;
        if (isMenuOpen) {         
            System.out.println("objet : " + submenuNames.get(cursorSubMenu));   
            updateSubmenuLimits();
        }
	}    
    
    /**
     * met à jour le message du panneau d'affichage
     * @param message le nouveau message
     */
    public void setMessageBox(String message) {
        this.message = message;        
    }

    /**
     * met à jour les bornes de la boucle qui affiche les
     * différentes cases selon la taille de la liste des noms
     * et de la position du curseur du sous-menu
     */
    private void updateSubmenuLimits() {
        // nombre de cases à afficher
        int length = submenuNames.size();         
        // si le curseur est ou repasse a zero
        if (cursorSubMenu == 0) {
            iMin = 0;
            iMax = Math.min(maxSubmenuRows, length);
            return;
        }
        // si le curseur est ou repasse a l'indice maximun
        if (cursorSubMenu == length - 1) {
            iMin = Math.max(0, length - maxSubmenuRows);
            iMax = length;
            return;
        }
        // si le curseur est supérieur à l'indice maximun on décale l'affichage vers le bas
        if (cursorSubMenu >= iMax) {
            iMin++;
            iMax++;
            return;
        } 
        // si le curseur est inférieur à l'indice maximun on décale l'affichage vers le bas
        if (cursorSubMenu < iMin) {
            iMin--;
            iMax--;
        }
    }

    /**
     * dessine l'hud de combat
     * @param g objet permettant de dessiner sur la fenêrte
     */
	public void draw(Graphics2D g) {     
        
        if (!isDisplayed) {
            return;
        }

        // Affichage temporaire de l'emplacement du joueur et de l'ennemi
        //drawTemp(g);
        
        g.setStroke(new BasicStroke(3));

         //  ___________________ NOMS DES PERSONNAGES ___________________ 

        g.setColor(Color.WHITE);            
        g.setFont(new Font("Apple Casual", Font.BOLD, 25));

        String[] names = {player.toString(), enemy.toString()};
        for (int i = 0; i < names.length; i++) {
            FontMetrics nameMetrics = g.getFontMetrics(g.getFont());
            int textWidth = nameMetrics.stringWidth(names[i]);
            int textHeight = nameMetrics.getHeight();          
            int x =  (int) (3*tile + 30 - textWidth/2 + i*20*tile);
            int y = (int) (tile + 30 + textHeight/3);
            if (selectedTarget == i) {
                g.setColor(Color.RED);            
                g.drawLine(x - 20, y + 10, x + textWidth + 5, y + 10);
                g.drawString("> ", x - 20, y);
                g.setColor(Color.WHITE);            

            }
            g.drawString(names[i], x, y);

        }       

        //  ___________________ PANNEAU D'AFFICHAGE ___________________ 
        int x = 9*tile;
        int y = screenH - 2*tile;
        int lengthX = 9*tile;
        int lengthY = tile;
        // fond
        g.setColor(Color.white);
        g.fillRect(x, y, lengthX, lengthY);
        // message
        g.setColor(Color.black);            
        g.setFont(new Font("Apple Casual", Font.BOLD, 16));
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int textWidth = metrics.stringWidth(this.message);
        int textHeight = metrics.getHeight();           
        g.drawString(this.message, x + lengthX/2 - textWidth/2, y + lengthY/2 + textHeight/3);
        
        // contour
        g.setColor(Color.black);
        g.drawRect(x, y, lengthX, lengthY);

         // informations demandés par toutes les barres
         int enemyBarX = 19*tile -20;
         int playerBarX = tile + 20;
         int widthBar = 7*tile;          

        //  ___________________ BARRE DE VIE ___________________ 

        int heightHealthBar = 40;        
        int healthBarY = screenH - 4*tile + 50;     
       
        /**
         * barre de vie du joueur
         */
        // vie du joueur en pourcentage
        double playerHealth = (double) (player.getCurrentStats().get(Stats.HP)) / (player.getStats().get(Stats.HP));

        // fond
        g.setColor(colorBarBackground);
        g.fillRect(playerBarX, healthBarY, widthBar, heightHealthBar);
        // niveau de vie
        g.setColor(colorHealth);
        g.fillRect(playerBarX, healthBarY, (int) (widthBar*playerHealth), heightHealthBar);
        // contour
        g.setColor(Color.black);
        g.drawRect(playerBarX, healthBarY, widthBar, heightHealthBar);

        /**
         * barre de vie de l'ennemi
         */
        // vie de l'ennemi en pourcentage
        double enemyHealth = (double) (enemy.getCurrentStats().get(Stats.HP)) / enemy.getStats().get(Stats.HP);
        // fond
        g.setColor(colorBarBackground);
        g.fillRect(enemyBarX, healthBarY, widthBar, heightHealthBar);
        // niveau de vie
        g.setColor(colorHealth);
        g.fillRect(enemyBarX + widthBar - (int) (widthBar*enemyHealth), healthBarY, (int) (widthBar*enemyHealth), heightHealthBar);
        // contour
        g.setColor(Color.black);
        g.drawRect(enemyBarX, healthBarY, widthBar, heightHealthBar);
        
        // ___________________ BARRE DE MANA ___________________ 

        int manaBarY = screenH - 2*tile;
        int heightManaBars = 20;

        /**
         * barre de mana du joueur
         */
        // mana du joueur en pourcentage
        double playerMana = (double) (player.getCurrentStats().get(Stats.MANA)) / player.getStats().get(Stats.MANA);
        // fond
        g.setColor(colorBarBackground);
        g.fillRect(playerBarX, manaBarY, widthBar, heightManaBars);
        // niveau de mana
        g.setColor(colorMana);
        g.fillRect(playerBarX, manaBarY, (int) (widthBar*playerMana), heightManaBars);
        // contour
        g.setColor(Color.black);
        g.drawRect(playerBarX, manaBarY, widthBar, heightManaBars);

        /**
         * barre de mana de l'ennemi
         */
        // mana de l'ennemi en pourcentage
        double enemyMana = (double) (enemy.getCurrentStats().get(Stats.MANA)) / enemy.getStats().get(Stats.MANA);
        // fond
        g.setColor(colorBarBackground);
        g.fillRect(enemyBarX, manaBarY, widthBar, heightManaBars);
        // niveau de mana
        g.setColor(colorMana);
        g.fillRect(enemyBarX + widthBar - (int) (widthBar*enemyMana), manaBarY, (int) (widthBar*enemyMana), heightManaBars);
        // contour
        g.setColor(Color.black);
        g.drawRect(enemyBarX, manaBarY, widthBar, heightManaBars);

        // ___________________ BARRE DE STAMINA ___________________ 

        int staminaBarY = manaBarY + 40;
        int heightStaminaBars = 20;

        /**
         * barre de stamina du joueur
         */
        // stamina du joueur en pourcentage
        double playerStamina = (double) (player.getCurrentStats().get(Stats.STAMINA)) / player.getStats().get(Stats.STAMINA);
        // fond
        g.setColor(colorBarBackground);
        g.fillRect(playerBarX, staminaBarY, widthBar, heightStaminaBars);
        // niveau de stamina
        g.setColor(colorStamina);
        g.fillRect(playerBarX, staminaBarY, (int) (widthBar*playerStamina), heightStaminaBars);
        // contour
        g.setColor(Color.black);
        g.drawRect(playerBarX, staminaBarY, widthBar, heightStaminaBars);

        /**
         * barre de stamina de l'ennemi
         */
        // stamina de l'ennemi en pourcentage
        double enemyStamina = (double) (enemy.getCurrentStats().get(Stats.STAMINA)) / enemy.getStats().get(Stats.STAMINA);
        // fond
        g.setColor(colorBarBackground);
        g.fillRect(enemyBarX, staminaBarY, widthBar, heightStaminaBars);
        // niveau de stamina
        g.setColor(colorStamina);
        g.fillRect(enemyBarX + widthBar - (int) (widthBar*enemyStamina), staminaBarY, (int) (widthBar*enemyStamina), heightStaminaBars);
        // contour
        g.setColor(Color.black);
        g.drawRect(enemyBarX, staminaBarY, widthBar, heightStaminaBars);

        if (cursorMenu == -1) {
            return;
        }
        
        //  ___________________ MENU ___________________ 
        
        int menuX = 7*tile + 30;
        int menuWidth = 180;
        int menuHeight = 40;

        for (int i = 0; i < 3; i++) {
            // position en Y de la case
            int menuY = (2+i)*tile + 20;
            // desinne la case
            drawBoxMenu(g, i, menuX, menuY, menuWidth, menuHeight, cursorMenu, menus[i]);  
        }

        if (!isMenuOpen) {
            return;
        }

        //  ___________________ SOUS-MENU ___________________ 

        menuX = 12*tile;
        menuWidth = 4*tile;
        menuHeight = 48;

        g.setStroke(new BasicStroke(3));

        int cpt = 0;
        for (int i = iMin; i < iMax; i++) {                               
            // position en Y de la case
            int menuY = 2*tile + 52*cpt++;
            // desinne la case          
            drawBoxMenu(g, i, menuX, menuY, menuWidth, menuHeight, cursorSubMenu, submenuNames.get(i));
        }
	}

    /**
     * dessine une case d'un menu ou d'un sous-menu
     * @param g objet permettant de dessiner sur la fenêrte
     * @param i l'indice de l'objet
     * @param menuX la position horizontale de la case
     * @param menuY la position verticale de la case
     * @param menuWidth la largeur de la case
     * @param menuHeight la hauteur de la case
     * @param cursor la position du curseur
     * @param name le nom à écrire dans la case
     */
    private void drawBoxMenu(Graphics2D g, int i, int menuX, int menuY, int menuWidth, int menuHeight, int cursor, String name) {

        Color background, menu, text;
        if (i == cursor) {
            background = colorMenuBackgroundActive;
            menu = colorMenuActive;
            text = colorMenuActive;
        } else {
            background = colorMenuBackground;
            menu = colorMenu;
            text = colortextMenu;
        }
        // fond
        g.setColor(background);
        g.fillRect(menuX, menuY, menuWidth, menuHeight);
        // gros contour blanc
        g.setStroke(new BasicStroke(5));
        g.setColor(Color.white);
        g.drawRect(menuX, menuY, menuWidth, menuHeight);            
        // petit contour
        g.setStroke(new BasicStroke(3));
        g.setColor(menu);
        g.drawRect(menuX, menuY, menuWidth, menuHeight);
        // texte du menu
        g.setColor(text);            
        g.setFont(new Font("Apple Casual", Font.BOLD, 18));
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int textWidth = metrics.stringWidth(name);
        int textHeight = metrics.getHeight();           
        g.drawString(name, menuX + menuWidth/2 - textWidth/2, menuY + menuHeight/2 + textHeight/3);       
    }

    /**
     * methode de test temporaire
     * @param g
     */
    private void drawTemp(Graphics2D g) {

        g.setStroke(new BasicStroke(5));

        // PLAYER 
        g.setColor(new Color(34,177,76));
        g.drawRect(tile, tile + 10, 5*tile, 40);
        g.drawRect(tile, 2*tile, 5*tile, 9*tile);
       
        // ENEMY

        g.setColor(new Color(255,0,0));
        g.drawRect(21*tile, tile + 10, 5*tile, 40);
        g.drawRect(21*tile, 2*tile, 5*tile, 9*tile);
    }

    @Override
    public boolean hudIsDisplayed() {
        return isDisplayed;
    }

    @Override
	public List<Visual> getVisuals() throws Exception {
        List<Visual> visuals = new ArrayList<Visual>();
        if (isDisplayed) {
            visuals.add(Visual.createWithGameCoord(0, 14, backgroundImage, Layer.BACKGROUND));	
        }
        return visuals;
	}

    public List<Visual> getFrontVisuals() {
        if (isDisplayed) {
            return this.frontVisuals;
        }
        return new ArrayList<Visual>();        
	}

}
