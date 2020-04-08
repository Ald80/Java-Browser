package br.com.browser;
	
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(final Stage stage) {
		
		WebView webView = new WebView();
		
		webView.getEngine().titleProperty().addListener(new ChangeListener<String>() {
			
			public void changed(ObservableValue<? extends String> ov, 
					final String oldValue, final String newValue) {
				
				stage.setTitle(newValue);
			}
		});
		
		String homePageUrl = "https://www.google.com";
		
		MenuButton menu = new WebMenu(webView);
		
		BrowserHistory history = new BrowserHistory(webView);
		
		NavigationBar navigationBar = new NavigationBar(webView, homePageUrl, true);
		
		navigationBar.getChildren().addAll(menu, history);
		
		VBox root = new VBox(navigationBar, webView);
		
		root.setStyle("-fx-padding: 10;"
				+ "-fx-border-style: solid inside;"
				+ "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;"
				+ "-fx-border-radius: 5;"
				+ "-fx-border-color: blue;");
		
		Scene scene = new Scene(root);
		
		stage.setScene(scene);
		
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
