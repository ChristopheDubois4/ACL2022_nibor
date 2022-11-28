package manager;

import java.util.List;

import java.awt.Graphics2D;

import prefab.information.Visual;

public interface WorldPainter {

    public List<Visual> getVisuals();
    public List<Visual> getFrontVisuals();
    public void drawHuds(Graphics2D g);
}
