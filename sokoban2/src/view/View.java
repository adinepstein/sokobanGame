package view;

import common.Level;
import javafx.collections.ObservableList;
import model.DB.GameResults;

public interface View {
	public void display(Level level);
	public void TopScoreDisplay(ObservableList<GameResults> ol,String type);
	public void searchResults(ObservableList<GameResults> ol,String searchType);
}
