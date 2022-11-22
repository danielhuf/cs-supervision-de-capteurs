package application;

import Controller.Controller;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class MenuFrame {
	
	GridPane grid;
	
	public MenuFrame(boolean isManager) {
		
		// Creating and customizing stage
        Stage menuStage = new Stage();
        menuStage.setTitle("CS library - user menu");
        menuStage.getIcons().add(new Image("/book_icon.png"));
        
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        // Resize grid columns size
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints();
        
        col1.setPercentWidth(1);
        col2.setPercentWidth(15);
        col3.setPercentWidth(40);
        
        grid.getColumnConstraints().addAll(col1,col2,col3);
        
        Text scenetitle = new Text("Hey there " + Controller.getController().getAPI().getUser().getFirstName() + " !");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        
        Button consultSituation = addButton("/profile.png", "Your situation", 1, 4);
        Button borrowBook = addButton("/borrow.png", "Borrow book", 1, 7);
        Button returnBook = addButton("/return.png", "Return book", 1, 10);
        
        consultSituation.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	new SituationFrame(isManager);
            	menuStage.close();
            }
        });
        
        borrowBook.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	new BorrowFrame(isManager);
            	menuStage.close();
            }
        });
        
        returnBook.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	new ReturnFrame(isManager);
            	menuStage.close();
            }
        });
        
        // Adding tools just for managers
        if (isManager) {
            Button manageParameters = addButton("/settings.png", "Manage categories", 3, 4);
            Button manageBorrowings = addButton("/borrowings.png", "Manage borrowings", 3, 7);
            Button manageUsers = addButton("/multiple_users.png", "Manage users", 3, 10);
            
            manageParameters.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                	new ParameterFrame(isManager);
                	menuStage.close();
                }
            });
            
            manageBorrowings.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                	new ConsultBorrowFrame(isManager);
                	menuStage.close();
                }
            });
            
            manageUsers.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                	new UserFrame(isManager);
                	menuStage.close();
                }
            });
        }
        
        menuStage.setScene(new Scene(grid, 550, 450));
        menuStage.show();
	}
	
	private Button addButton(String path, String comment, int row, int column) {
        ImageView view = new ImageView(new Image(path));
        view.setFitHeight(40);
        view.setPreserveRatio(true);

        Button button = new Button();
        button.setPrefSize(50, 50);
        button.setGraphic(view);
        button.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        grid.add(button, row, column);
        
        Text text = new Text(comment);
        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        grid.add(text, row+1, column);
        
        return button;
	}
}
