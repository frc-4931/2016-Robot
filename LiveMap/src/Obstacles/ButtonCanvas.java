package Obstacles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ButtonCanvas extends Application implements ActionListener
{
	private static BufferedWriter sc;
	private Button[] buttons=new Button[9];
	private int count=1;
	private TextField tf;
	public static final String FILE_NAME = "Obstacles.txt";
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		GridPane c=new GridPane();
		buttons[0]=new Button("Lowbar");
		buttons[1]=new Button("Portcullis");
		buttons[2]=new Button("Cheval de Frise");
		buttons[3]=new Button("Ramparts");
		buttons[4]=new Button("Moat");
		buttons[5]=new Button("Rough Terrain");
		buttons[6]=new Button("RockWall");
		buttons[7]=new Button("Drawbridge");
		buttons[8]=new Button("Sally Port");
		tf=new TextField("For Position "+1);
		c.add(tf, 0, 0);
		for(int x=0;x<9;x++)
			c.add(buttons[x], 1, 2*x);
		primaryStage.setScene(new Scene(c));
		primaryStage.show();
	}
		public static void main(String[] args)
		{
			try 
			{
				sc =new BufferedWriter(new FileWriter(FILE_NAME));
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			launch(args);
			for(int x=0;x<10;x++)
				System.out.println(x);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			try
			if(e.getSource()==buttons[0])
			{
				sc.write(0);
			}
			if(e.getSource()==buttons[1])
			{

			}
			if(e.getSource()==buttons[2])
			{

			}
			if(e.getSource()==buttons[3])
			{

			}
			if(e.getSource()==buttons[4])
			{

			}
			if(e.getSource()==buttons[5])
			{

			}
			if(e.getSource()==buttons[6])
			{

			}
			if(e.getSource()==buttons[7])
			{
				
			}
			if(e.getSource()==buttons[8])
			{
				
			}
			
		}
}
