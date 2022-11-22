package application;

import java.util.List;
import Controller.Controller;
import Model.Book;
import javafx.collections.*;
import javafx.collections.transformation.*;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Callback;

public class BorrowFrame {
	
	GridPane gridUp;
	Stage borrowStage;
	boolean isManager;
	
	@SuppressWarnings("rawtypes")
	TableView table;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	BorrowFrame (boolean isManager) {
		
		this.isManager = isManager;
		
		// Creating and customizing stage
		borrowStage = new Stage();
		borrowStage.setTitle("CS library - borrow books");
		borrowStage.getIcons().add(new Image("/book_icon.png"));
		
		BorderPane border = new BorderPane();
		gridUp = new GridPane();
		
		gridUp.setHgap(5);
		gridUp.setVgap(10);
		gridUp.setPadding(new Insets(5, 5, 5, 5));
		
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints();
        ColumnConstraints col4 = new ColumnConstraints();
        
        col1.setPercentWidth(22);
        col2.setPercentWidth(22);
        col3.setPercentWidth(22);
        col4.setPercentWidth(22);

        gridUp.getColumnConstraints().addAll(col1,col2,col3, col4);
		
        addText("Title:", 0, 0);
        addText("Year:", 1, 0);
        addText("Author:", 2, 0);
        addText("ISBN:", 3, 0);
        
        TextField titleField = new TextField();
        TextField yearField = new TextField();
        TextField authorField = new TextField();
        TextField isbnField = new TextField();
        
        Button filter = new Button("Filter");
        filter.setStyle("-fx-background-color: #b00739; -fx-text-fill: #ffffff; -fx-cursor: hand;");
        
        gridUp.add(titleField, 0, 1);
        gridUp.add(yearField, 1, 1);
        gridUp.add(authorField, 2, 1);
        gridUp.add(isbnField, 3, 1);
        gridUp.add(filter, 4, 1);
		
		table = new TableView();
		
        TableColumn<String, Book> cl1 = new TableColumn<>("Book title");
        TableColumn<Integer, Book> cl2 = new TableColumn<>("Edition");
        TableColumn<String, Book> cl3 = new TableColumn<>("Author");
        TableColumn<Integer, Book> cl4 = new TableColumn<>("Copies");
        TableColumn<Integer, Book> cl5 = new TableColumn<>("Available");
        
        cl1.setCellValueFactory(new PropertyValueFactory<>("title"));
        cl2.setCellValueFactory(new PropertyValueFactory<>("publicationYear"));
        cl3.setCellValueFactory(new PropertyValueFactory<>("author"));
        cl4.setCellValueFactory(new PropertyValueFactory<>("copies"));
        cl5.setCellValueFactory(new PropertyValueFactory<>("available"));
        
        table.getColumns().addAll(cl1, cl2, cl3, cl4, cl5);
        
        Text txt = new Text();
        txt.setFill(Color.FIREBRICK);
        
        // Consulting user situation and available books
        List<Object> o = Controller.getController().borrowBooks();
        if ((int)o.get(0) == 1)
        	txt.setText("You cannot borrow books while on red list.");
        
        else if ((int)o.get(0) == 2)
        	txt.setText("You have achieved the maximum number of borrows for your category.");
        
        else
        	txt.setText("There are no books available to borrow.");
        
        final ObservableList<Book> masterData = FXCollections.observableArrayList();
		for (Book b : (List<Book>)o.get(1)) {
			masterData.add(b);
		}
		
		table.setPlaceholder(txt);
        table.setItems(masterData);   
        addButtonToTable();
		
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
            	borrowStage.close();
            }
        });
        
        FilteredList<Book> filteredData = new FilteredList<>(masterData, p -> true);
        filter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
    			filteredData.setPredicate(book -> {
    				
    				boolean titleSelected = !titleField.getText().equals("");
    				boolean yearSelected = !yearField.getText().equals("");
    				boolean authorSelected = !authorField.getText().equals("");
    				boolean isbnSelected = !isbnField.getText().equals("");
    				
    				boolean t = book.getTitle().toLowerCase().contains(titleField.getText().toLowerCase());
    				boolean y = String.valueOf(book.getPublicationYear()).toLowerCase().contains(yearField.getText().toLowerCase());
    				boolean a = book.getAuthor().toLowerCase().contains(authorField.getText().toLowerCase());
    				boolean i = book.getIsbn().toLowerCase().contains(isbnField.getText().toLowerCase());
    				
    				if (titleSelected && yearSelected && authorSelected && isbnSelected) return t && y && a && i;
    				if (titleSelected && yearSelected && authorSelected) return t && y && a;
    				if (titleSelected && yearSelected && isbnSelected) return t && y && i;
    				if (titleSelected && authorSelected && isbnSelected) return t && a && i;
    				if (yearSelected && isbnSelected && authorSelected) return y && i && a;
    				if (titleSelected && yearSelected) return t && y;
    				if (titleSelected && isbnSelected) return t && i;
    				if (titleSelected && authorSelected) return t && a;
    				if (yearSelected && isbnSelected) return y && i;
    				if (yearSelected && authorSelected) return y && a;
    				if (isbnSelected && authorSelected) return i && a;
    				if (titleSelected) return t;
    				if (yearSelected) return y;
    				if (authorSelected) return a;
    				if (isbnSelected) return i;
    				
    				return true;
    			});
    			
    			SortedList<Book> sortedData = new SortedList<>(filteredData);
    			sortedData.comparatorProperty().bind(table.comparatorProperty());
    			table.setItems(sortedData);
            }
        });
        
        border.setTop(gridUp);
        border.setCenter(table);
        border.setBottom(ret);
        borrowStage.setScene(new Scene(border, 550, 450));
        borrowStage.show();
	}

	private void addText(String comment, int column, int row) {
        Text text = new Text(comment);
        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        gridUp.add(text, column, row);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addButtonToTable() {
		
		TableColumn<Book, Void> colBtn = new TableColumn("");
		
        Callback<TableColumn<Book, Void>, TableCell<Book, Void>> cellFactory = new Callback<TableColumn<Book, Void>, TableCell<Book, Void>>() {
            @Override
            public TableCell<Book, Void> call(final TableColumn<Book, Void> param) {
                final TableCell<Book, Void> cell = new TableCell<Book, Void>() {
                    private final Button btn = new Button("borrow");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Book data = getTableView().getItems().get(getIndex());
                            Controller.getController().borrowBook(data);
                            
                    		Alert alert = new Alert(AlertType.INFORMATION);
                    		alert.setTitle(null);
                    		alert.setHeaderText(null);
                    		alert.setContentText("Book successfully borrowed!");
                    		alert.showAndWait();
                    		
                        	new MenuFrame(isManager);
                        	borrowStage.close();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) 
                            setGraphic(null);
                        else 
                            setGraphic(btn);
                    }
                };
                return cell;
            }
		};
		
        colBtn.setCellFactory(cellFactory);
        table.getColumns().add(colBtn);
	}
}
