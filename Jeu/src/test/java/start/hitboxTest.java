package start;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Before;
import org.junit.Test;

import prefab.entity.GameObject;
import prefab.information.Position;


/**
 * Unit test for simple App.
 */
public class hitboxTest 
{   
    GameObject testObject;
    GameObject testObject1;
    GameObject testObject2;


    @Before
	public void setUp() throws Exception {
        testObject = new GameObject(null, null, 1, 2, null);	}
    
        @Test
    public void testGetOccupiedCoordinatesInTheMap()throws Exception {
        Position position = Position.create(0,0);
        testObject.setPosition(position);
        int deltaX=0;
        int deltaY=0;
        List<Pair<Integer, Integer>> testPos = testObject.getOccupiedCoordinates(deltaX,deltaY);

        List<Pair<Integer, Integer>> listOfPos= new ArrayList<Pair<Integer, Integer>>();
        listOfPos.add(new Pair<Integer, Integer>(0, 0) );  
        listOfPos.add(new Pair<Integer, Integer>(0, 1) );               

        
        assertSame( listOfPos, testPos);
    }
}