package application;

import java.util.List;
import Controller.Controller;
import Model.User;
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
import javafx.util.Callback;

public class UserFrame {
	
	GridPane gridUp;
	
	@SuppressWarnings("rawtypes")
	TableView table;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	UserFrame (boolean isManager) {
		
		// Creating and customizing stage
		Stage userStage = new Stage();
		userStage.setTitle("CS library - manage users");
		userStage.getIcons().add(new Image("/book_icon.png"));
		
		BorderPane border = new BorderPane();
		gridUp = new GridPane();
		
		gridUp.setHgap(10);
		gridUp.setVgap(10);
		gridUp.setPadding(new Insets(5, 5, 5, 5));
		
        addText("Last name:", 1, 0);
        addText("Category:", 2, 0);
        addText("Late:", 3, 0);
        addText("Red List:", 4, 0);
        
        TextField lastNameField = new TextField();
        TextField categoryField = new TextField();
        CheckBox lateBox = new CheckBox();
        CheckBox redBox = new CheckBox();
        
        Button filter = new Button("Filter");
        filter.setStyle("-fx-background-color: #b00739; -fx-text-fill: #ffffff; -fx-cursor: hand;");
        
        gridUp.add(lastNameField, 1, 1);
        gridUp.add(categoryField, 2, 1);
        gridUp.add(lateBox, 3, 1);
        gridUp.add(redBox, 4, 1);
        gridUp.add(filter, 5, 1);
        
		table = new TableView();
        
        TableColumn<String, User> cl1 = new TableColumn<>("First name");
        TableColumn<String, User> cl2 = new TableColumn<>("Last name");
        TableColumn<String, User> cl3 = new TableColumn<>("Email CS");
        TableColumn<String, User> cl4 = new TableColumn<>("Category");
        TableColumn<Boolean, User> cl5 = new TableColumn<>("Red List");
        
        cl1.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        cl2.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        cl3.setCellValueFactory(new PropertyValueFactory<>("email"));
        cl4.setCellValueFactory(new PropertyValueFactory<>("category"));
        cl5.setCellValueFactory(new PropertyValueFactory<>("redList"));
        
        table.getColumns().addAll(cl1, cl2, cl3, cl4, cl5);
        
        // Consult list of all users
        final ObservableList<User> masterData = FXCollections.observableArrayList();
		for (User u : Controller.getController().manageUsers()) {
			masterData.add(u);
		}
		
        table.setItems(masterData);   
        addButtonToTable();
        
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
            	userStage.close();
            }
        });
		
        FilteredList<User> filteredData = new FilteredList<>(masterData, p -> true);
        filter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	List<Integer> lateUsers = Controller.getController().filterUsers();
    			filteredData.setPredicate(user -> {
    				
    				boolean nameSelected = !lastNameField.getText().equals("");
    				boolean categorySelected = !categoryField.getText().equals("");
    				boolean redSelected = redBox.isSelected();
    				boolean lateSelected = lateBox.isSelected();
    				
    				boolean n = user.getLastName().toLowerCase().contains(lastNameField.getText().toLowerCase());
    				boolean c = user.getCategory().toLowerCase().contains(categoryField.getText().toLowerCase());
    				boolean r = user.getRedList();
    				boolean l = lateUsers.contains(user.getId());
    				
    				if (nameSelected && categorySelected && redSelected && lateSelected) return n && c && r && l;
    				if (nameSelected && categorySelected && redSelected) return n && c && r;
    				if (nameSelected && categorySelected && lateSelected) return n && c && l;
    				if (nameSelected && redSelected && lateSelected) return n && r && l;
    				if (categorySelected && lateSelected && redSelected) return c && l && r;
    				if (nameSelected && categorySelected) return n && c;
    				if (nameSelected && lateSelected) return n && l;
    				if (nameSelected && redSelected) return n && r;
    				if (categorySelected && lateSelected) return c && l;
    				if (categorySelected && redSelected) return c && r;
    				if (lateSelected && redSelected) return l && r;
    				if (nameSelected) return n;
    				if (categorySelected) return c;
    				if (redSelected) return r;
    				if (lateSelected) return l;
    				
    				return true;
    			});
    			
    			SortedList<User> sortedData = new SortedList<>(filteredData);
    			sortedData.comparatorProperty().bind(table.comparatorProperty());
    			table.setItems(sortedData);
            }
        });
        
        border.setTop(gridUp);
        border.setCenter(table);
        border.setBottom(ret);
        userStage.setScene(new Scene(border, 550, 450));
        userStage.show();
	}

	private void addText(String comment, int column, int row) {
        Text text = new Text(comment);
        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        gridUp.add(text, column, row);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addButtonToTable() {
		
		TableColumn<User, Void> colBtn = new TableColumn("");
		
        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<User, Void>() {
                    private final Button btn = new Button("put/remove");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            User data = getTableView().getItems().get(getIndex());
                            User newData = Controller.getController().updateUser(data);
                            
                            table.getItems().set(getIndex(), newData);
                         // **ALERT** : THIS METHOD RAISES A BUG WHEN THE FILTER BUTTON IS CLICKED BEFORE CLICKING THE BUTTON IN THE ROW (Exception in thread "JavaFX Application Thread" java.lang.UnsupportedOperationException)
                         // ALTHOUGH MODIFICATIONS IN THE DATABASE STILL HAPPEN, YOU CAN ONLY SEE SUCH MODIFICATIONS WHEN YOU CLICK ON THE FILTER BUTTON AGAIN OR RELOAD THE SCREEN
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
