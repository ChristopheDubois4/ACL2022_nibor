package prefab.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import prefab.information.Visual;

public class HealthBar extends Hud{
	
    private DisplayingPlayerInventory player;
	
	public HealthBar(DisplayingPlayerInventory player) {
		this.player = player;
	}
	
	public void draw(Graphics2D g) {
		double health = player.getHealth();
		g.setColor(Color.red);
		double healthBar = (health/100)*390;
		g.fillRect(30, 30, (int) healthBar, 30);	
	}

    @Override
    public List<Visual> getVisual() {
        return null;
    }
    
}