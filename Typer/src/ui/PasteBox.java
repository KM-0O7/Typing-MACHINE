package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.ComboBox;
import java.awt.Robot;
import javafx.scene.layout.VBox;
import java.util.Random;
import java.awt.AWTException;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class PasteBox extends Application {
    
    public static String textToType;
    
    public void start(Stage stage) throws InterruptedException {
    	Random waitTime = new Random();
    	
    	//MAIN FUNCTION
    	Label prompt = new Label("Insert Prompt: ");
    	TextArea text = new TextArea("");
    	Button finishButton = new Button("Type Prompt");
    	finishButton.setPrefWidth(200);
    	Button stopButton = new Button("Stop Typing");
    	stopButton.setPrefWidth(200);
    	
    	//SETTINGS
    	Label settingsLabel = new Label("Settings:");
    	ComboBox<Integer> minTypingSpeed = new ComboBox<>();
    	minTypingSpeed.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    	Label minTypingSpeedLabel = new Label("Min Typing Speed: ");
    	
    	// TYPE ARROW FUNCTION
    	finishButton.setOnAction(e -> {
    		textToType = text.getText();
    		text.clear();
    		System.out.println(textToType);
    		
    		try {
    			Robot typer = new Robot();
    			for (int i = 0; i < textToType.length(); i++) {
    				Thread.sleep(1000);
    			}
    			
    		} catch (AWTException a ) {
    			System.out.println("Failed To make Robot");
    			
    		} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	});
    	
    	stopButton.setOnAction(e -> {
    		textToType = "";
    		text.clear();
    		System.out.println("Stopped Typing!");
    	});
    	
    	// LAYOUT
    	HBox buttons = new HBox(0);
    	buttons.getChildren().addAll(finishButton, stopButton);
    	
    	HBox top = new HBox(150);
    	top.getChildren().addAll(prompt, settingsLabel);
    	
    	//SETTINGS LAYOUT
    	HBox minTypingSpeedHBox = new HBox(2);
    	minTypingSpeedHBox.getChildren().addAll(minTypingSpeedLabel, minTypingSpeed);
    	
    	BorderPane root = new BorderPane();
    	root.setRight(minTypingSpeedHBox);
    	root.setTop(top);
    	root.setCenter(text);
    	root.setBottom(buttons);
    
        Scene scene = new Scene(root, 400, 200);
        
        stage.setTitle("PromptManager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }  
    
}