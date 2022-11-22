package application;

import javafx.collections.*;
import javafx.collections.transformation.*;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import Controller.Controller;
import Model.Borrow;

public class ConsultBorrowFrame {
	
	GridPane gridUp;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	ConsultBorrowFrame(boolean isManager) {
		
		// Creating and customizing stage
		Stage consultBorrowStage = new Stage();
		consultBorrowStage.setTitle("CS library - manage borrowings");
		consultBorrowStage.getIcons().add(new Image("/book_icon.png"));
		
		BorderPane border = new BorderPane();
		gridUp = new GridPane();
		
		gridUp.setHgap(5);
		gridUp.setVgap(10);
		gridUp.setPadding(new Insets(5, 5, 5, 5));
        
        addText("Last name:", 0, 0);
        addText("Category:", 1, 0);
        addText("Book Title:", 2, 0);
        addText("Late:", 3, 0);
        
        TextField lastNameField = new TextField();
        TextField categoryField = new TextField();
        TextField titleField = new TextField();
        CheckBox checkBox = new CheckBox();
        
        Button filter = new Button("Filter");
        filter.setStyle("-fx-background-color: #b00739; -fx-text-fill: #ffffff; -fx-cursor: hand;");
        
        gridUp.add(lastNameField, 0, 1);
        gridUp.add(categoryField, 1, 1);
        gridUp.add(titleField, 2, 1);
        gridUp.add(checkBox, 3, 1);
        gridUp.add(filter, 4, 1);
        
		TableView table = new TableView();
        
        TableColumn<String, Borrow> cl1 = new TableColumn<>("First name");
        TableColumn<String, Borrow> cl2 = new TableColumn<>("Last name");
        TableColumn<String, Borrow> cl3 = new TableColumn<>("Category");
        TableColumn<String, Borrow> cl4 = new TableColumn<>("Book title");
        TableColumn<String, Borrow> cl5 = new TableColumn<>("Borrowing date");
        TableColumn<Integer, Borrow> cl6 = new TableColumn<>("Duration (months)");
        TableColumn<Integer, Borrow> cl7 = new TableColumn<>("Allowed Duration");
        
        cl1.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        cl2.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        cl3.setCellValueFactory(new PropertyValueFactory<>("category"));
        cl4.setCellValueFactory(new PropertyValueFactory<>("title"));
        cl5.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        cl6.setCellValueFactory(new PropertyValueFactory<>("duration"));
        cl7.setCellValueFactory(new PropertyValueFactory<>("maxDuration"));
        
        table.getColumns().addAll(cl1, cl2, cl3, cl4, cl5, cl6, cl7);
        
        // Consulting borrowed books
        final ObservableList<Borrow> masterData = FXCollections.observableArrayList();
		for (Borrow c : Controller.getController().consultBorrows()) {
			masterData.add(c);
		}
        table.setItems(masterData);   
       
        Text txt = new Text();
        txt.setFill(Color.FIREBRICK);
        txt.setText("There are no results for this search.");
        table.setPlaceholder(txt);
        
        ImageView view = new ImageView("/returnPage.png");
        view.setFitHeight(25);
        view.setPreserveRatio(true);
        Button ret = new Button();
        ret.setPrefSize(20, 20);
        ret.setGraphic(view);
        ret.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        
        ret.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	new MenuFrame(isManager);
            	consultBorrowStage.close();
            }
        });
        
        FilteredList<Borrow> filteredData = new FilteredList<>(masterData, p -> true);
        filter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
    			filteredData.setPredicate(consult -> {
    				
    				boolean nameSelected = !lastNameField.getText().equals("");
    				boolean categorySelected = !categoryField.getText().equals("");
    				boolean titleSelected = !titleField.getText().equals("");
    				boolean lateSelected = checkBox.isSelected();
    				
    				boolean n = consult.getLastName().toLowerCase().contains(lastNameField.getText().toLowerCase());
    				boolean c = consult.getCategory().toLowerCase().contains(categoryField.getText().toLowerCase());
    				boolean t = consult.getTitle().toLowerCase().contains(titleField.getText().toLowerCase());
    				boolean l = consult.getDuration() > consult.getMaxDuration();
    				
    				if (nameSelected && categorySelected && titleSelected && lateSelected) return n && c && t && l;
    				if (nameSelected && categorySelected && titleSelected) return n && c && t;
    				if (nameSelected && categorySelected && lateSelected) return n && c && l;
    				if (nameSelected && titleSelected && lateSelected) return n && t && l;
    				if (categorySelected && lateSelected && titleSelected) return c && l && t;
    				if (nameSelected && categorySelected) return n && c;
    				if (nameSelected && lateSelected) return n && l;
    				if (nameSelected && titleSelected) return n && t;
    				if (categorySelected && lateSelected) return c && l;
    				if (categorySelected && titleSelected) return c && t;
    				if (lateSelected && titleSelected) return l && t;
    				if (nameSelected) return n;
    				if (categorySelected) return c;
    				if (titleSelected) return t;
    				if (lateSelected) return l;
    				
    				return true;
    			});
    			
    			SortedList<Borrow> sortedData = new SortedList<>(filteredData);
    			sortedData.comparatorProperty().bind(table.comparatorProperty());
    			table.setItems(sortedData);
            }
        });
        
        border.setTop(gridUp);
        border.setCenter(table);
        border.setBottom(ret);
        consultBorrowStage.setScene(new Scene(border, 550, 450));
        consultBorrowStage.show();
	}
	
	private void addText(String comment, int column, int row) {
        Text text = new Text(comment);
        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        gridUp.add(text, column, row);
	}
}