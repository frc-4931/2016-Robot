package application;

import java.io.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class ButtonCanvas  
{
	private static PrintWriter sc;
	private RadioButton[] buttons=new RadioButton[8];
	private TextField auto;
	public static final String FILE_NAME = "Obstacles.txt";
	private Stage stage;
	public ButtonCanvas() 
	{
		stage=new Stage();
		try 
		{
			sc =new PrintWriter(new FileWriter(FILE_NAME));
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
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
			stage.close();
		});
		c.add(submit,5, 4);
		auto=new TextField();
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
		stage.setScene(new Scene(c));
		stage.show();
	}
}
