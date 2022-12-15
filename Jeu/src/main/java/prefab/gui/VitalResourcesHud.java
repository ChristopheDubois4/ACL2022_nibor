package prefab.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import manager.Utilities;
import prefab.information.Stats;
import prefab.rendering.Visual;

import java.awt.image.BufferedImage;

public class VitalResourcesHud extends Hud{

	private String inventoryPath = "src/main/ressources/images/huds/playerInfo/vitalresources.png";
	private BufferedImage backgroundImage;
    private PlayerInfosFofHud player;
	private Visual visual;

	private static final int firstPosX = 9, firstPosY = 0; 
	
	public VitalResourcesHud(PlayerInfosFofHud player) throws Exception {
		this.isDisplayed=true;
		this.player = player;
		this.backgroundImage = Utilities.getImage(inventoryPath);
		this.visual = Visual.createWithGameCoord(firstPosX, firstPosY, 40, 20, backgroundImage);
	}
	
	public void draw(Graphics2D g) {

		//Santé du joueur
		double health = player.getCurrentStats().get(Stats.HP);
		g.setColor(new Color( 81,169,16));
		double healthBar = (health/100)*444;
		g.fillRect(588, 828 , (int) healthBar, 28);
		
		//Mana du joueur
		double mana = player.getCurrentStats().get(Stats.MANA);
		g.setColor(new Color( 32,89,192));
		double manabar = (mana/100)*218;
		g.fillRect(588, 866 , (int) manabar, 28);

		//Stamina du joueur
		double stamina = player.getCurrentStats().get(Stats.STAMINA);
		g.setColor(new Color( 219,139,17));
		double staminabar = (stamina/100)*218;
		g.fillRect(814, 866 , (int) staminabar, 28);

		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN|Font.BOLD, 10));
		String currentHealthStat = String.valueOf((int) health);  
		String currentManaStat = String.valueOf((int) mana);  
		String currentStaminaStat = String.valueOf((int) stamina);

		String healthStat=String.valueOf(player.getStats().get(Stats.HP));
		g.drawString("HP :"+currentHealthStat+"/"+healthStat, 778, 846);

		String manaStat=String.valueOf(player.getStats().get(Stats.MANA));;
		g.drawString("MP :"+currentManaStat+"/"+manaStat, 658, 884);

		String staminaStat=String.valueOf(player.getStats().get(Stats.STAMINA));
		g.drawString("SP :"+currentStaminaStat+"/"+staminaStat, 884, 884);

	}

    @Override
    public List<Visual> getVisuals() {
        List<Visual> visuals = new ArrayList<Visual>();
        visuals.add(visual);
        return visuals;
    }
    
}