package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class ButtonCanvas extends Application 
{
	private static final int[] IDENTIFIERS={0,1,2,3,4,5,6,7,8};
	private static PrintWriter sc;
	private RadioButton[] buttons=new RadioButton[8];
	private int count=0;
	private TextField auto=new TextField();
	public static final String FILE_NAME = "Obstacles.txt";
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		GridPane c=new GridPane();
		ToggleGroup tG=new ToggleGroup();
		buttons[0]=new RadioButton("Portcullis");
		buttons[1]=new RadioButton("Cheval de Frise");
		buttons[2]=new RadioButton("Ramparts");
		buttons[3]=new RadioButton("Moat");
		buttons[4]=new RadioButton("Rough Terrain");
		buttons[5]=new RadioButton("RockWall");
		buttons[6]=new RadioButton("Drawbridge");
		buttons[7]=new RadioButton("Sally Port");
		Button submit=new Button("Submit");
		submit.setOnAction(event->
		{
			RadioButton seleted=(RadioButton)tG.getSelectedToggle();
			sc.println(seleted.getText());
			sc.close();
			System.exit(0);
		});
		c.add(submit,5, 4);
		auto.setText("Obstacle is");
		c.add(auto, 0, 1);
		for(int x=0;x<buttons.length;x++)
		{
			buttons[x].setToggleGroup(tG);
			buttons[x].setOnAction(event ->
			{
				RadioButton st=(RadioButton) event.getSource();
				st.setSelected(true);
				auto.setText("Obstacle is "+st.getText());
				for(int i=0;i<buttons.length;i++)
					buttons[i].setBackground(new Background(new BackgroundFill(null, null, null)));
				st.setBackground(new Background(new BackgroundFill(Paint.valueOf("lightGray"), 
						new CornerRadii(10), null)));
			});
			c.add(buttons[x], 1, x);
		}
		primaryStage.setScene(new Scene(c));
		primaryStage.show();
	}
	public static void main(String[] args)
	{
		try 
		{
			sc =new PrintWriter(new FileWriter(FILE_NAME));
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		launch(args);
		sc.close();
	}
}
