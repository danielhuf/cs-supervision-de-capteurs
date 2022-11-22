package Controller;

import java.util.*;
import Model.*;
import application.MenuFrame;

public class Controller {
	
	private ModelFacade api;
	private static Controller ctrl = null;
	private boolean isManager;
	
	/**
	 * Singleton pattern implementation
	 * @return unique instance of Controller type
	 */
    public static Controller getController() {
    	if (ctrl == null) 
    		ctrl = new Controller();
    	return ctrl;
    }
    
    private Controller() {
    	api = ModelFacade.getAPI();
    	api.establishConnection();
    }
    
    public ModelFacade getAPI() { return api; }
    
    public void close() { api.closeConnection(); }
    
    public boolean checkLogin(String user, String password) {
    	if (api.checkCredentials(user, password)) {
    		new MenuFrame(isManager);
    		return true;
    	}
    	return false;
    }
    
    public void setManager(boolean isManager) { this.isManager = isManager; }
    
    public ArrayList<String> getCategories() { return api.getCategories(); }
    
    public boolean setCategory(String cat, int maxLoan, int maxDuration) { return api.setCategory(cat, maxLoan, maxDuration); }
    
    public int getNbLoans() { return api.getNbLoans(); }
    
    public int[] getCategoryData() { return api.getCategoryData(); }
    
    public List<Borrow> consultBorrows() { return api.consultBorrows(); }
    
    public List<User> manageUsers() { return api.manageUsers(); }
    
    public List<Integer> filterUsers() { return api.filterUsers(); }
    
    public User updateUser(User u) { return ModelFacade.getAPI().setRedList(u); }
    
    public List<Object> borrowBooks() { return api.borrowBooks(); }
    
    public void borrowBook(Book b) { ModelFacade.getAPI().borrowBook(b); }
    
    public List<Borrow> returnBooksList() { return api.returnBooksList(); }
    
    public void returnBook(Borrow b) { ModelFacade.getAPI().returnBook(b); }
}

