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
import java.awt.event.KeyEvent;

public class PasteBox extends Application {
    
	public static volatile boolean stopTyping = false;
    public static String textToType;
    
    public void start(Stage stage) {
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
    	minTypingSpeed.getItems().addAll(10, 50, 200, 500, 750);
    	
    	Label minTypingSpeedLabel = new Label("Min Typing Speed (ms): ");
    	
    	ComboBox<Integer> maxTypingSpeed = new ComboBox<>();
    	maxTypingSpeed.getItems().addAll(10, 50, 100, 200, 500, 750, 1000, 2000, 10000);
    	
    	Label maxTypingSpeedLabel = new Label("Max Typing Speed (ms): ");
    	
    	// TYPE ARROW FUNCTION
    	finishButton.setOnAction(e -> {
    		stopTyping = false;
    		textToType = text.getText();
    		text.clear();
    		System.out.println(textToType);
    		new Thread(() -> {
    			try {
        			Robot typer = new Robot();
        			
        			for (int i = 0; i < textToType.length(); i++) {
        				boolean typo = false;
        				
        				if (stopTyping == true) {
        					break;
        				}
        				
        				Integer min = minTypingSpeed.getValue();
        				Integer max = maxTypingSpeed.getValue();
        				
        				int keyCode = KeyEvent.getExtendedKeyCodeForChar(textToType.charAt(i));
        				
        				if (keyCode == KeyEvent.VK_UNDEFINED) {
        				    continue; 
        				}
        				
        				int delay = 100; 

        			    if (min != null && max != null && min <= max && i > 0) {
        			        delay = min + waitTime.nextInt(max - min + 1);
        			        delay += waitTime.nextInt(30) - 15;
        			        
        			        if (textToType.charAt(i - 1) == '.' || textToType.charAt(i - 1) == ',') {
        			        	delay += waitTime.nextInt(2000);
        			        }
        			    }
        			    
        			    if (waitTime.nextInt(100) >= 95) {
        			    	typo = true;
        			    }
        			    
        			    if (typo == true) {
        			    	char randomChar;
        			    	
        			    	int choice = waitTime.nextInt(3);

        			    	if (choice == 0) {
        			    	    randomChar = (char) ('a' + waitTime.nextInt(26));
        			    	} else if (choice == 1) {
        			    	    randomChar = (char) ('A' + waitTime.nextInt(26));
        			    	} else {
        			    	    randomChar = (char) ('0' + waitTime.nextInt(10));
        			    	}
        			    	int randomKeyCode = KeyEvent.getExtendedKeyCodeForChar(randomChar);
        			    	typer.keyPress(randomKeyCode);
          			    	typer.keyRelease(randomKeyCode);
        			    	delay = Math.min(delay, 60000);
        			    	typer.delay(waitTime.nextInt(1500));
        			    	typer.keyPress(KeyEvent.VK_BACK_SPACE);
        			    	typer.keyRelease(KeyEvent.VK_BACK_SPACE);
        			    	typo = false;
        			    }
        			   
        			    if (typo != true) {
        			    	delay = Math.min(delay, 60000);
        			    	 typer.delay(delay);
        			    	   try {
              			    	 typer.keyPress(keyCode);
              			    	 typer.keyRelease(keyCode);
              			    } catch (IllegalArgumentException e3) {
              			    	 continue;
              			    }
        			    }
        			    typo = false;
        			}
        		} catch (AWTException a ) {
        			System.out.println("Failed To make Robot");
        			
        		} 
        	}).start();
    		});
    	
    	stopButton.setOnAction(e -> {
    		stopTyping = true;
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
    	
    	HBox maxTypingSpeedHBox = new HBox(2);
    	maxTypingSpeedHBox.getChildren().addAll(maxTypingSpeedLabel, maxTypingSpeed);
    	
    	VBox settingsVBox = new VBox(5);
    	settingsVBox.getChildren().addAll(minTypingSpeedHBox, maxTypingSpeedHBox);
    	
    	BorderPane root = new BorderPane();
    	root.setRight(settingsVBox);
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