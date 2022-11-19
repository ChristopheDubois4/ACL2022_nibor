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


/**
 * (W I P)
 * a voir si on garde la classe
 */

public class JsonUtilities {
	
	
	
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
                Path path = Paths.get(pathStr);

                BufferedImage im = ImageIO.read(new File(path.toAbsolutePath().toString()) );

                

                graphics.put(state, im);
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

	
	
}
