package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import Controller.Controller;


class SituationFrame {
	
	GridPane grid;
	
	SituationFrame(boolean isManager) {
		
		// Creating and customizing stage
		Stage situationStage = new Stage();
		situationStage.setTitle("CS library - consult situation");
		situationStage.getIcons().add(new Image("/book_icon.png"));
		
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        // Getting data from user
        int loans = Controller.getController().getNbLoans();
        int[] categoryData = Controller.getController().getCategoryData();
        
        addText("Name: " + Controller.getController().getAPI().getUser().getFirstName() + " " + Controller.getController().getAPI().getUser().getLastName(), 1, 2, false);
        addText("Email CS: " + Controller.getController().getAPI().getUser().getEmail(), 1, 5, false);
        addText("Category: " + Controller.getController().getAPI().getUser().getCategory(), 1, 8, false);
        
        if (loans <= categoryData[0]) 
        	addText("Loans: " + Integer.valueOf(loans) + "/" + Integer.valueOf(categoryData[0]), 1, 11, false);

        else 
        	addText("Loans: " + Integer.valueOf(loans) + "/" + Integer.valueOf(categoryData[0]), 1, 11, true);

        addText("Maximum loan duration: " + Integer.valueOf(categoryData[1]) + " months", 1, 14, false);
        
        if (Controller.getController().getAPI().getUser().getRedList()) 
        	addText("Red list: Yes", 1, 17, true);
        else 
        	addText("Red list: No", 1, 17, false);
        
        ImageView view = new ImageView("/returnPage.png");
        view.setFitHeight(40);
        view.setPreserveRatio(true);
        Button ret = new Button();
        ret.setPrefSize(50, 50);
        ret.setGraphic(view);
        ret.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        grid.add(ret, 1, 21);
        
        ret.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	new MenuFrame(isManager);
            	situationStage.close();
            }
        });
		
        situationStage.setScene(new Scene(grid, 550, 450));
		situationStage.show();
	}
	
	private void addText(String comment, int column, int row, boolean red) {
        Text text = new Text(comment);
        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        if (red) 
        	text.setFill(Color.FIREBRICK);
        grid.add(text, column, row);
	}
}
