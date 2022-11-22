package application;

import Controller.Controller;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.geometry.*;
 
public class InitialFrame extends Application {
	
	private static Controller ctrl;
	
    public static void main(String[] args) {
    	ctrl = Controller.getController();
        launch();
        ctrl.close();
    }
    
    public void start(Stage primaryStage) {
    	
    	// Creating and customizing stage
        primaryStage.setTitle("CS Library - login");
        primaryStage.getIcons().add(new Image("/book_icon.png"));
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        primaryStage.setScene(new Scene(grid, 300, 275));
        
        Text scenetitle = new Text("Welcome, reader!");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Email:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField passwordTextFiled = new PasswordField();
        grid.add(passwordTextFiled, 1, 2);
        
        Button btn = new Button("Sign in");
        btn.setStyle("-fx-background-color: #b00739; -fx-text-fill: #ffffff; -fx-cursor: hand;");
        
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
        
        Text actiontarget = new Text();
        grid.add(actiontarget, 1, 5);
        
        // Checking login and rendering menu if login is successful
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	
                if (ctrl.checkLogin(userTextField.getText(), passwordTextFiled.getText())) {
                    actiontarget.setFill(Color.FORESTGREEN);
                    actiontarget.setText("Successful login.");
                    primaryStage.close();
                }
                else {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Failed to log in. Please try again.");
                }
            }
        });
        
        primaryStage.show();
    }
}