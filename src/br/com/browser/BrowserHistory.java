package br.com.browser;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebHistory.Entry;
import javafx.scene.web.WebView;
import javafx.util.Callback;

public class BrowserHistory extends HBox {
	
	public BrowserHistory(WebView webView){
		
		this.setSpacing(4);
		
		this.setStyle("-fx-padding: 10;"
				+ "-fx-border-style: solid inside;"
				+ "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;"
				+ "-fx-border-radius: 5;"
				+ "-fx-border-color: blue;");
		
		WebHistory history = webView.getEngine().getHistory();
		
		Label label = new Label("History");
		
		Button backButton = new Button("Back");
		backButton.setDisable(true);
		Button forwardButton = new Button("Forward");
		forwardButton.setDisable(true);
		
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				history.go(-1);
			}
		});
		
		forwardButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				history.go(1);
			}
			
		});
		
		history.currentIndexProperty().addListener(new ChangeListener<Number>() {
			
			public void changed(ObservableValue<? extends Number>ov, 
					final Number oldValue, final Number newValue) {
				
				int currentIndex = newValue.intValue();
				
				if (currentIndex <= 0) {
					backButton.setDisable(true);
				} else {
					backButton.setDisable(false);
				}
				
				if (currentIndex >= history.getEntries().size()) {
					forwardButton.setDisable(true);
				} else {
					forwardButton.setDisable(false);
				}
			}
		});
		
		ComboBox<Entry> historyList = new ComboBox();
		historyList.setPrefWidth(150);
		historyList.setItems(history.getEntries());
		
		historyList.setCellFactory(new Callback<ListView<WebHistory.Entry>, ListCell<WebHistory.Entry>>() {
			
			@Override
			public ListCell<WebHistory.Entry> call(ListView<WebHistory.Entry> list) {
				
				ListCell<Entry> cell = new ListCell<Entry>() {
					
					@Override
					public void updateItem(Entry item, boolean empty) {
						
						super.updateItem(item, empty);
						
						if (empty) {
							this.setText(null);
							this.setGraphic(null);
						} else {
							String pageTitle = item.getTitle();
							this.setText(pageTitle);
						}
					}
				};
				
				return cell;
			}
		});
		
		historyList.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				int currentIndex = history.getCurrentIndex();
				
				Entry selectedEntry = historyList.getValue();
				
				int selectedIndex = historyList.getItems().indexOf(selectedEntry);
				int offset = selectedIndex - currentIndex;
				history.go(offset);
			}
		});
		
		this.getChildren().addAll(backButton, forwardButton, label, historyList);
	}
}