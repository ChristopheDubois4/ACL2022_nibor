package manager;

import java.util.List;

import prefab.rendering.Visual;

import java.awt.Graphics2D;

public interface WorldPainter {

    public List<Visual> getVisuals() throws CloneNotSupportedException, Exception;
    public List<Visual> getFrontVisuals();
    public void drawHuds(Graphics2D g);
}
