package hitBox;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Before;
import org.junit.Test;

import manager.Utilities;
import prefab.entity.GameObject;
import prefab.information.Position;
import prefab.information.State;
import prefab.rendering.Animation;


/**
 * Unit test for simple App.
 */
public class hitboxTest 
{   
    GameObject testObject1;
    GameObject testObject2;
    GameObject testObject3;
    GameObject testObject4;



    @Before
	public void setUp() throws Exception {
        Animation animation = Animation.create(Utilities.getSpritesFromJSON("chest"));//Cela pourrait etre n'importe quelle image
        Position position = Position.create(0,0);

        testObject1 = new GameObject(position, animation, 1, 1, State.DEFAULT);	
        testObject2 = new GameObject(position, animation, 1, 2, State.DEFAULT);	
        testObject3 = new GameObject(position, animation, 3, 2, State.DEFAULT);	

        Position position2 = Position.create(1,1);
        testObject4 = new GameObject(position2, animation, 1, 1, State.DEFAULT);	


    }
    
    @Test
    public void testGetOccupiedCoordinatesInTheMap()throws Exception {
        int deltaX=0;
        int deltaY=0;
        List<Pair<Integer, Integer>> testPos = testObject1.getOccupiedCoordinates(deltaX,deltaY);

        List<Pair<Integer, Integer>> listOfPos= new ArrayList<Pair<Integer, Integer>>();
        listOfPos.add(new Pair<Integer, Integer>(0, 0) );  
        
        assertEquals(listOfPos, testPos);
    }

    @Test
    public void testGetOccupiedCoordinatesInTheMap2Hitbox()throws Exception {	
        int deltaX=0;
        int deltaY=0;
        List<Pair<Integer, Integer>> testPos = testObject2.getOccupiedCoordinates(deltaX,deltaY);

        List<Pair<Integer, Integer>> listOfPos= new ArrayList<Pair<Integer, Integer>>();
        listOfPos.add(new Pair<Integer, Integer>(0, 0) );  
        listOfPos.add(new Pair<Integer, Integer>(0, 1) );               
        assertEquals(listOfPos, testPos);
    }
    @Test
    public void testGetOccupiedCoordinatesInTheMap3Hitbox()throws Exception {	
        int deltaX=0;
        int deltaY=0;
        List<Pair<Integer, Integer>> testPos = testObject3.getOccupiedCoordinates(deltaX,deltaY);

        List<Pair<Integer, Integer>> listOfPos= new ArrayList<Pair<Integer, Integer>>();
        listOfPos.add(new Pair<Integer, Integer>(0, 0) );  
        listOfPos.add(new Pair<Integer, Integer>(0, 1) );   
        listOfPos.add(new Pair<Integer, Integer>(1, 0) );               
        listOfPos.add(new Pair<Integer, Integer>(1, 1) );               
        listOfPos.add(new Pair<Integer, Integer>(2, 0) );        
        listOfPos.add(new Pair<Integer, Integer>(2, 1) );               

        assertEquals(listOfPos, testPos);
    }

    @Test
    public void testGetOccupiedCoordinatesInTheMapDeplacementVerticalPositif()throws Exception {
        int deltaX=0;
        int deltaY=1;
        List<Pair<Integer, Integer>> testPos = testObject4.getOccupiedCoordinates(deltaX,deltaY);

        List<Pair<Integer, Integer>> listOfPos= new ArrayList<Pair<Integer, Integer>>();
        listOfPos.add(new Pair<Integer, Integer>(1, 2) );  
        
        assertEquals(listOfPos, testPos);
    }
    @Test
    public void testGetOccupiedCoordinatesInTheMapDeplacementVerticalNegatif()throws Exception {
        int deltaX=0;
        int deltaY=-1;
        List<Pair<Integer, Integer>> testPos = testObject4.getOccupiedCoordinates(deltaX,deltaY);

        List<Pair<Integer, Integer>> listOfPos= new ArrayList<Pair<Integer, Integer>>();
        listOfPos.add(new Pair<Integer, Integer>(1, 0) );  
        
        assertEquals(listOfPos, testPos);
    }
    @Test
    public void testGetOccupiedCoordinatesInTheMapDeplacementHorizontalPositif()throws Exception {
        int deltaX=1;
        int deltaY=0;
        List<Pair<Integer, Integer>> testPos = testObject4.getOccupiedCoordinates(deltaX,deltaY);

        List<Pair<Integer, Integer>> listOfPos= new ArrayList<Pair<Integer, Integer>>();
        listOfPos.add(new Pair<Integer, Integer>(2, 1) );  
        
        assertEquals(listOfPos, testPos);
    }
    @Test
    public void testGetOccupiedCoordinatesInTheMapDeplacementHorizontalNegatif()throws Exception {
        int deltaX=-1;
        int deltaY=0;
   
        List<Pair<Integer, Integer>> testPos = testObject4.getOccupiedCoordinates(deltaX,deltaY);

        List<Pair<Integer, Integer>> listOfPos= new ArrayList<Pair<Integer, Integer>>();
        listOfPos.add(new Pair<Integer, Integer>(0, 1) );  
        
        assertEquals(listOfPos, testPos);
    }
}