package Obstacles;
import java.util.Scanner;

import javafx.geometry.Point2D;

/**
 * 
 * @author Julian Nieto
 *
 */
public class Obstacles 
{
	public final static double[] TIME_FINAL ={0,1,2,3,4,5,6,7,8};
	public final static String LOWBAR = "Lowbar";
	public final static String ROCKWALL = "Rockwall";
	public final static String PORTCULLIS = "Portcullis";
	public final static String SHOVELS_OF_FRIES = "Cheval de Frise";
	public final static String RAMPARTS = "Ramparts";
	public final static String MOAT = "Moat";
	public final static String DRAWBRIDGE = "Drawbridge";
	public final static String SALLY_PORT = "Sally Port";
	public final static String ROUGH_TERRAIN = "Rough Terrain";

	private String identifierNum;
	private double time;
	/**
	 * Only pass the positions of the constants in the Position class
	 * @param o identifier number only Constants
	 * @param x Position  in constants
	 */
	public Obstacles(String o)
	{
			identifierNum=o;
		
	}
	public String getObstacle()
	{
		return identifierNum;
	}
	public String toString()
	{
		return ""+identifierNum;
	}
	public boolean checkIfGood(String r)
	{
		if(r.equals(LOWBAR)||r.equals(PORTCULLIS)||r.equals(ROCKWALL)||
			r.equals(ROUGH_TERRAIN)||r.equals(ROCKWALL)||r.equals(SHOVELS_OF_FRIES)
			||r.equals(SALLY_PORT)||r.equals(DRAWBRIDGE)||r.equals(MOAT))
		{
			return true;
		}
		return false;
	}
	public void assignTime()
	{
		switch(identifierNum)
		{
			case LOWBAR: time=TIME_FINAL[1];
				break;
			case ROCKWALL: time=TIME_FINAL[2];
				break;
			case PORTCULLIS: time=TIME_FINAL[3];
				break;
			case SHOVELS_OF_FRIES: time=TIME_FINAL[0];
				break;
			case RAMPARTS: time=TIME_FINAL[4];
				break;
			case MOAT: time=TIME_FINAL[5];
				break;
			case DRAWBRIDGE: time=TIME_FINAL[6];
				break;
			case SALLY_PORT: time=TIME_FINAL[7];
				break;
			case ROUGH_TERRAIN: time=TIME_FINAL[9];
				break;
		}
	}
	public double getTime()
	{
		return time;
	}
}
