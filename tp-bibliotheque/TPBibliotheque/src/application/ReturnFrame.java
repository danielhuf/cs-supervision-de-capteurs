package application;

import Controller.Controller;
import Model.Borrow;
import javafx.collections.*;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ReturnFrame {
	
	@SuppressWarnings("rawtypes")
	TableView table;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	ReturnFrame (boolean isManager) {
		
		// Creating and customizing stage
		Stage returnStage = new Stage();
		returnStage.setTitle("CS library - return books");
		returnStage.getIcons().add(new Image("/book_icon.png"));
		
		BorderPane border = new BorderPane();
		table = new TableView();
		
        TableColumn<String, Borrow> cl1 = new TableColumn<>("Book title");
        TableColumn<Integer, Borrow> cl2 = new TableColumn<>("Edition");
        TableColumn<String, Borrow> cl3 = new TableColumn<>("Author");
        TableColumn<String, Borrow> cl4 = new TableColumn<>("Borrowing Date");
        TableColumn<String, Borrow> cl5 = new TableColumn<>("Duration (months)");
        
        cl1.setCellValueFactory(new PropertyValueFactory<>("title"));
        cl2.setCellValueFactory(new PropertyValueFactory<>("publicationYear"));
        cl3.setCellValueFactory(new PropertyValueFactory<>("author"));
        cl4.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        cl5.setCellValueFactory(new PropertyValueFactory<>("duration"));
        
        table.getColumns().addAll(cl1, cl2, cl3, cl4, cl5);
		
        // Consult list of user's borrowed books
        final ObservableList<Borrow> masterData = FXCollections.observableArrayList();
		for (Borrow b : Controller.getController().returnBooksList()) {
			masterData.add(b);
		}
        table.setItems(masterData);  
        
        Text txt = new Text();
        txt.setFill(Color.FIREBRICK);
        txt.setText("You don't have any borrowed books at the moment.");
        table.setPlaceholder(txt);
        
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
            	returnStage.close();
            }
        });
		
        border.setCenter(table);
        border.setBottom(ret);
        returnStage.setScene(new Scene(border, 550, 450));
        returnStage.show();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addButtonToTable() {
		
		TableColumn<Borrow, Void> colBtn = new TableColumn("");
		
        Callback<TableColumn<Borrow, Void>, TableCell<Borrow, Void>> cellFactory = new Callback<TableColumn<Borrow, Void>, TableCell<Borrow, Void>>() {
            @Override
            public TableCell<Borrow, Void> call(final TableColumn<Borrow, Void> param) {
                final TableCell<Borrow, Void> cell = new TableCell<Borrow, Void>() {

                    private final Button btn = new Button("return");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                        	Borrow data = getTableView().getItems().get(getIndex());
                            Controller.getController().returnBook(data);
                            getTableView().getItems().remove(data);
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
