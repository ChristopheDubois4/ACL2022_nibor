package prefab.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import manager.Utilities;
import prefab.information.Stats;
import prefab.information.Visual;
import java.awt.image.BufferedImage;

public class StatsHud extends Hud{

	private String inventoryPath = "src/main/ressources/images/huds/playerInfo/stats.png";
    private PlayerInfosFofHud player;
	private Visual visual;
	private BufferedImage backgroundImage;
	

	private static final int firstPosX = 8, firstPosY = 0; 
	
	public StatsHud(PlayerInfosFofHud player) {
		this.isDisplayed=true;
		this.player = player;
		this.backgroundImage = Utilities.getImage(inventoryPath);
		this.visual = new Visual(firstPosX, firstPosY, backgroundImage);
		visual.setDeltaPos(0,20);
	}
	
	public void draw(Graphics2D g) {

		g.setFont(new Font("Arial", Font.PLAIN|Font.BOLD, 10));

		g.setColor(new Color(175,158,72));

		//Defense du joueur
		double defense = player.getCurrentStats().get(Stats.DEFENSE);
		String defenseStat = String.valueOf((int) defense);  
		g.drawString("DEF : "+defenseStat, 522, 851);
		
		//Speed du joueur
		double speed = player.getCurrentStats().get(Stats.SPEED);
		String speedStat = String.valueOf((int) speed); 
		g.drawString("SPD : "+speedStat, 522, 868);

		//Damage du joueur
		double damage = player.getCurrentStats().get(Stats.DAMAGE);
		String damageStat = String.valueOf((int) damage);
		g.drawString("DP : "+damageStat, 522, 883);
	}

    @Override
    public List<Visual> getVisual() {
        List<Visual> visuals = new ArrayList<Visual>();
        visuals.add(visual);
        return visuals;
    }
    
}