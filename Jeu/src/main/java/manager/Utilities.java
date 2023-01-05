package manager;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import prefab.information.State;
import prefab.rendering.Sprite;


/**
 * (W I P)
 * a voir si on garde la classe
 */

public class Utilities {
	
	
	
	/**
     * récupère les composantes graphiques de l'objet à partir d'un fichier JSON
     * @param model nom du fichier JSON a prendre pour modèle
     * @return les composantes graphiques de l'objet
     */
    public static HashMap<State,BufferedImage> getGraphicsFromJSON(String model) {

        HashMap<State,BufferedImage> graphics = new HashMap<State,BufferedImage>();
        model = "src/main/ressources/levels/graphics/"+model+".json";

        File directory = new File(model);
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(directory))
        {            
            //Read JSON file
            Object obj = jsonParser.parse(reader); 
            JSONArray graphicObjects = (JSONArray) obj;
            // parcours de toutes les images
            for (int i = 0; i < graphicObjects.size() ; i++) {
                JSONObject graphicObject = (JSONObject) graphicObjects.get(i);

                State state =  State.valueOf((String) graphicObject.get("state"));

                JSONObject visual = (JSONObject) graphicObject.get("visual");

                String pathStr = (String) visual.get("image");
                BufferedImage image = getImage(pathStr);

                graphics.put(state, image);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        return graphics;
    }

	
    public static BufferedImage getImage(String pathStr) {

        BufferedImage image = null;
        Path path = Paths.get(pathStr);
        try {
            image = ImageIO.read(new File(path.toAbsolutePath().toString()) );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }



    public static HashMap<State,Sprite> getSpritesFromJSON(String model) throws Exception {

        String tst = model;

        HashMap<State,Sprite> sprites = new HashMap<State,Sprite>();
        model = "src/main/ressources/levels/graphics/"+model+".json";

        File directory = new File(model);
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(directory))
        {            
            //Read JSON file
            Object obj = jsonParser.parse(reader); 
            JSONArray graphicObjects = (JSONArray) obj;
            // parcours de toutes les images
            for (int i = 0; i < graphicObjects.size() ; i++) {
                JSONObject graphicObject = (JSONObject) graphicObjects.get(i);

                State state =  State.valueOf((String) graphicObject.get("state"));

                JSONObject visual = (JSONObject) graphicObject.get("visual");

                String pathStr = (String) visual.get("image");

                Sprite s;

                if (tst.equals("robin") && state == State.IDLE_DOWN) {
                    s = Sprite.createBigSprite(pathStr, 5, 180);
                } 

                else if (state == State.FIGHT) {
                    s = Sprite.createBigSprite(pathStr, 2);
                } 
                else if (state == State.FIGHT) {
                    s = Sprite.createBigSprite(pathStr, 2);
                } 
                else {
                    if (state == State.DEFAULT) {
                        s = Sprite.createBigSprite(pathStr, 4);
                    } 
                    else {
                        s = Sprite.createSprite(pathStr, 4);
                    }
                }

                sprites.put(state, s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        return sprites;
    }

}
