package manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import prefab.entity.Player;
import prefab.gui.InventoryHud;
import prefab.gui.HealthBar;
import prefab.information.Image;

import java.awt.image.BufferedImage;


/**
 * Créer les différents Huds
 */
public class HudCreator {

    Player player;

    InventoryHud inventory;
    HealthBar healthBar;

    public HudCreator(Player player) {
        this.player = player;
        initHuds();
        testSrpint1();
    }

    private void initHuds() {
    }
    
    /**
     * methode temporaire
     * 
     * Méthode pour tester des fonctionnalitées liées au sprint 1
     */
    private void testSrpint1() {   
        Image backgroundsImages = getImageFromJSON("inventory");
        inventory = new InventoryHud(player, backgroundsImages);
        
        healthBar = new HealthBar(player);
        
    }

    public InventoryHud getInventory() {
        return inventory;
    }
    
    public HealthBar getHealthBar() {
        return healthBar;
    }

    /**
     * récupère les composantes graphiques de l'objet à partir d'un fichier JSON
     * @param model nom du fichier JSON a prendre pour modèle
     * @return les composantes graphiques de l'objet
     */
    private Image getImageFromJSON(String model) {

        Image image = null;
        model = "src/main/ressources/huds/"+model+".json";
        File directory = new File(model);
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(directory))
        {            
            //Read JSON file
            Object obj = jsonParser.parse(reader); 
            
            JSONObject visual = (JSONObject) obj;
            String pathStr = (String) visual.get("image");
            Path path = Paths.get(pathStr);

            BufferedImage im = ImageIO.read(new File(path.toAbsolutePath().toString()) );
            int lengthX = (int) ((long) visual.get("lenghtX"));
            int lengthY = (int) ((long) visual.get("lenghtY"));  
            image = new Image(im, lengthX, lengthY);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        return image;
    }
}
