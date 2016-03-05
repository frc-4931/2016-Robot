package application;

import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import static application.MetersToPixels.convertPixels;


import static application.MetersToPixels.convertMeters;

public class Map {
	private RobotConversion robot;
	private StartPos sp;
	private Side side;
	private Image imageField = new Image("/application/Field.png",814,400,true,false);
	private Image imageFieldB = new Image("/application/FieldBlue.png",814,400,true,false);
	private Image imageFieldR = new Image("/application/FieldRed.png",814,400,true,false);
	private Image imageBA = new Image("/application/BlueDot.png",39,39,true,false);
	private Image imageRA = new Image("/application/RedDot.png",39,39,true,false);
	private Image imageObg = new Image("/application/ObstacleGreenHighlight.png",28,63,true,false);
	private Image imageRS = new Image("/application/RedSide2.png",814,200,true,false);
	private Image imageBS = new Image("/application/BlueSide2.png",814,200,true,false);
	private Image imageSP = new Image("/application/StartingPosition.png",39,39,true,false);
	private Image imageUndo = new Image("/application/undoButton.png",60,20,true,false);
	private double sx = 0;
	private double sy = 0;
	private boolean robotIsOnField = false;
	private boolean sideHasBeenPicked = false;

	public void update(GraphicsContext gc)
	{
		if(robotIsOnField == true && sideHasBeenPicked == true)
		{
			int xCord = robot.xCoord();
			int yCord = robot.yCoord();
			double rotation = robot.rotation();
			if(side.getSide())
				gc.drawImage(imageFieldB,0,0);
			else
				gc.drawImage(imageFieldR, 0, 0);
			gc.save();
			rotate(gc, rotation, xCord + imageBA.getWidth() / 2, yCord + imageBA.getHeight() / 2);
			if(side.getSide())
			gc.drawImage(imageBA, xCord, yCord, 39, 39);
			else
			gc.drawImage(imageRA, xCord, yCord, 39, 39);
			gc.restore();
			gc.drawImage(imageObg, 255, 337, 28, 63);
			gc.drawImage(imageUndo,0,0,60,20);
		}
		else if (robotIsOnField == false && sideHasBeenPicked == true)
		{
			gc.getCanvas().setOnMouseMoved(new EventHandler <MouseEvent>(){
				public void handle(MouseEvent e)
				{
					sp = new StartPos(convertMeters((int)e.getX()-3),convertMeters((int)e.getY()-3));
				}
			});
			if(side.getSide())//Blue is true
			{
				gc.drawImage(imageFieldB,0,0);
				gc.drawImage(imageSP,convertPixels(sp.getX()),convertPixels(sp.getY()),39,39);
			}
			else
			{
				gc.drawImage(imageFieldR,0,0);
				gc.drawImage(imageSP,convertPixels(sp.getX()),convertPixels(sp.getY()),39,39);
			}
		}
		else
		{
			gc.drawImage(imageField,0,0);
			gc.drawImage(imageBS, 0, 0, 814, 200);
			gc.drawImage(imageRS, 0, 201, 814, 200);
		}
	}
	
	public void handleClick(int xc,int yc)
	{
		int x = xc;
		int y = yc;
		if(sideHasBeenPicked == false)
			side = new Side(y);
		if(side.getSide())
		{
			if(robotIsOnField == false && sideHasBeenPicked== true)
			{
				sx = (double) (convertMeters(x));
				sy = (double) (convertMeters(y));
					if(sp.withinBoundries(sx, sy))
					{
						robotIsOnField = true;
						robot = new RobotConversion(convertPixels(sp.getX()),convertPixels(sp.getY()));
						robot.setRotation(90);
						//System.out.println((x/PIXELS_PER_METER) +" "+ (y/PIXELS_PER_METER));
					}
			}
			else
				System.out.println((convertMeters(x)) +" "+ (convertMeters(y)));
			sideHasBeenPicked = true;
		}
		else
		{
			if(robotIsOnField == false && sideHasBeenPicked==true)
			{
				sx = (double) (convertMeters(x));
				sy = (double) (convertMeters(y));
					if(sp.withinBoundries(sx, sy))
					{
						robotIsOnField = true;
						robot = new RobotConversion(convertPixels(sp.getX()),convertPixels(sp.getY()));
						robot.setRotation(90);
						//System.out.println((x/PIXELS_PER_METER) +" "+ (y/PIXELS_PER_METER));
					}
			}
			else
				System.out.println((convertMeters(x)) +" "+ (convertMeters(y)));
			sideHasBeenPicked = true;
		}
		if(robotIsOnField == true)
		{
			if(x<=60 && y<=20)
			{
				robotIsOnField = false;
			}
		}
	}
	
	private void rotate(GraphicsContext gc, double angle, double px, double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }
}
