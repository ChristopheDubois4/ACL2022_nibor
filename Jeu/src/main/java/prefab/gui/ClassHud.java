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
import prefab.information.Layer;
import prefab.rendering.Visual;
import manager.Utilities;

/**
 * gère l'HUD lié à la selection de class
 */
public class ClassHud extends Hud{

    private static final ClassHud INSTANCE = new ClassHud();
    private String classPath = "src/main/ressources/images/huds/class/";
    private BufferedImage backgroundImage;

    private List<Visual> frontVisuals;

    private int tile = NiborPainter.TILE_LENGTH;
    private int screenH = NiborPainter.SCREEN_HEIGHT;

    // informations pour les menus
    private String[] menus = {"WARRIOR", "ASSASSIN", "ARCHER", "MAGE"};
    private boolean isMenuOpen = false;
    private int cursorMenu = 0;

    // message affiché dans le panneau d'affichage
    public String message = "";

     // couleurs des bars
    private Color colorBarBackground = new Color(0,0,0, 70);

     // couleurs des menus
    private Color colortextMenu = new Color(210,210,210);
    private Color colorMenu = new Color(49,73,106);
    private Color colorMenuActive = Color.white;
    private Color colorMenuBackground = colorBarBackground;
    private Color colorMenuBackgroundActive = new Color(0,0,0, 140);

    // indices pour afficher les sous-menus

    /**
     * constructeur de la classe ClassHud
     */
	public ClassHud(){
        super();
	}

    @Override
    public void initHud() throws Exception {
        initVisuals();
    }

    private void initVisuals() throws Exception {
        this.backgroundImage = Utilities.getImage(classPath+ "classBackground.png");
        BufferedImage warriorIcon = Utilities.getImage(classPath + "warrior.png");
        BufferedImage archerIcon = Utilities.getImage(classPath + "archer.png");
        BufferedImage mageIcon = Utilities.getImage(classPath + "mage.png");
        BufferedImage assassinsIcon = Utilities.getImage(classPath + "assassin.png");

        this.frontVisuals = new ArrayList<Visual>();

        this.frontVisuals.add(Visual.createWithGameCoord(7, 7, -60, 0, warriorIcon));
        this.frontVisuals.add(Visual.createWithGameCoord(11, 7, -60, 0, assassinsIcon));
        this.frontVisuals.add(Visual.createWithGameCoord(15, 7, -60, 0, archerIcon));
        this.frontVisuals.add(Visual.createWithGameCoord(19, 7, -60, 0, mageIcon));
        

    }
	
    /**
     * met à jour les informations de l'Hud
     * @param cursorMenu emplacement du curseur du menu
     * @param isMenuOpen vrai si un menu est ouvert, faux sinon
     * @param cursorSubMenu emplacement du curseur du sous-menu
     * @param submenuNames liste des noms à afficher dans le sous-menu
     * @param selectedTarget la cible selectionnée
     */
    public void updateHud(int cursorMenu) {
		this.cursorMenu = cursorMenu;
	}    
    
    /**
     * met à jour le message du panneau d'affichage
     * @param message le nouveau message
     */
    public void setMessageBox(String message) {
        this.message = message;        
    }

    /**
     * dessine l'hud de combat
     * @param g objet permettant de dessiner sur la fenêrte
     */
	public void draw(Graphics2D g) {       

        if (!hudIsDisplayed()) {
            return;
        }

        // Affichage temporaire de l'emplacement du joueur et de l'ennemi
        //drawTemp(g);
        
        g.setStroke(new BasicStroke(3));

        //  ___________________ PANNEAU D'AFFICHAGE ___________________ 
        int x = 9*tile;
        int y = screenH - 2*tile;
        int lengthX = 9*tile;
        int lengthY = tile;
      
        // message
        g.setColor(Color.black);            
        g.setFont(new Font("Apple Casual", Font.BOLD, 16));
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int textWidth = metrics.stringWidth(this.message);
        int textHeight = metrics.getHeight();           
        g.drawString(this.message, x + lengthX/2 - textWidth/2, y + lengthY/2 + textHeight/3);
        
              
        
        //  ___________________ MENU ___________________ 
        
        int menuY = 4*tile + 30;
        int menuWidth = 180;
        int menuHeight = 40;

        for (int i = 0; i < 4; i++) {
            // position en X de la case
            int menuX = (6+4*i)*tile;
            // desinne la case
            drawBoxMenu(g, i, menuX, menuY, menuWidth, menuHeight, cursorMenu, menus[i]);  
        }

        if (!isMenuOpen) {
            return;
        }
    }

    /**
     * dessine une case d'un menu ou sous-menu
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
	public List<Visual> getVisuals() throws Exception {
        List<Visual> visuals = new ArrayList<Visual>();
        if (isDisplayed) {
            visuals.add(Visual.createWithGameCoord(0, 14, backgroundImage));	
        }
        return visuals;
	}

    public List<Visual> getFrontVisuals() {
        if (isDisplayed) {
            return this.frontVisuals;
        }
        return new ArrayList<Visual>();        
	}

    public static ClassHud getInstance() {
        return INSTANCE;
    }


}
