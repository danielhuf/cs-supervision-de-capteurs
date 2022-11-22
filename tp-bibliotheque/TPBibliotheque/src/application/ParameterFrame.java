package application;

import javafx.animation.PauseTransition;
import javafx.collections.*;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

import Controller.Controller;

class ParameterFrame {
	
	GridPane grid;
	
	ParameterFrame(boolean isManager) {
		
		// Creating and customizing stage
		Stage parameterStage = new Stage();
		parameterStage.setTitle("CS library - manage categories");
		parameterStage.getIcons().add(new Image("/book_icon.png"));
		
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        addText("Category: ", 1, 2);
        addText("Maximum number of loans: ", 1, 5);
        addText("Maximum duration of loans (months): ", 1, 8);
        
        // Displaying existent categories
        ObservableList<String> options = FXCollections.observableArrayList();
        ArrayList<String> categories = Controller.getController().getCategories();
        for (int i=0; i<categories.size(); i++) 
        	options.add(categories.get(i));
        
        ComboBox<String> comboBox = new ComboBox<String>(options);
        comboBox.setStyle("-fx-cursor: hand;");
        comboBox.getSelectionModel().selectFirst();
        grid.add(comboBox, 2, 2);

        Spinner<Integer> loansSpinner = new Spinner<>(0, 999, 0, 1);
        loansSpinner.setEditable(true);
        grid.add(loansSpinner, 2, 5);
        
        Spinner<Integer> durationSpinner = new Spinner<>(0, 999, 0, 1);
        durationSpinner.setEditable(true);
        grid.add(durationSpinner, 2, 8);
        
        Button register = new Button("Register");
        register.setStyle("-fx-background-color: #b00739; -fx-text-fill: #ffffff; -fx-font-size:15; -fx-cursor: hand;");
        register.setPrefSize(80, 40);
        grid.add(register, 2, 11);
        
        ImageView view = new ImageView("/returnPage.png");
        view.setFitHeight(40);
        view.setPreserveRatio(true);
        Button ret = new Button();
        ret.setPrefSize(50, 50);
        ret.setGraphic(view);
        ret.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        grid.add(ret, 1, 21);
        
        register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	
            	Text actiontarget = new Text();
            	grid.add(actiontarget, 2, 12);
            	
            	if (Controller.getController().setCategory(comboBox.getValue(), loansSpinner.getValue(), durationSpinner.getValue())) {
                    actiontarget.setFill(Color.FORESTGREEN);
                    actiontarget.setText("Parameters successfully modified.");
            	}
            	else {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Error: parameters could not be modified.");
            	}
            	
            	PauseTransition pause = new PauseTransition(Duration.seconds(2));
            	pause.setOnFinished(event -> actiontarget.setText(null));
            	pause.play();
            }
        });
        
        ret.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	new MenuFrame(isManager);
            	parameterStage.close();
            }
        });
		
        parameterStage.setScene(new Scene(grid, 550, 450));
        parameterStage.show();
	}
	
	private void addText(String comment, int column, int row) {
        Text text = new Text(comment);
        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        grid.add(text, column, row);
	}
}
