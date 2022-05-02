package mimuw.idlearn.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import mimuw.idlearn.Application;

import java.io.IOException;
import java.net.URL;

/**
 * A set of utility functions related to scenes
 */
public class SceneUtils {
	public static URL MainMenu = SceneUtils.class.getResource("MainMenu.fxml");
	public static URL GameMenu = SceneUtils.class.getResource("GameMenu.fxml");
	public static URL Preloader = SceneUtils.class.getResource("Preloader.fxml");
	public static URL Settings = SceneUtils.class.getResource("Settings.fxml");
	public static URL Store = SceneUtils.class.getResource("Store.fxml");
	public static URL TaskSelection = SceneUtils.class.getResource("TaskSelection.fxml");

	public static URL StyleSheet = Application.class.getResource("style.css");
	public static URL AppIcon = Application.class.getResource("icon.png");
	public static URL AppLogo = Application.class.getResource("logo.png");

	/**
	 * Load a scene from an .fxml file and return its root
	 * @param url url of the file
	 * @return root of loaded scene
	 * @throws IOException thrown when loading of the file fails
	 */
	public static Parent loadScene(URL url) throws IOException {
		Scene scene = new Scene(FXMLLoader.load(url));
		Parent root = scene.getRoot();
		root.getStylesheets().add(StyleSheet.toExternalForm());
		//scene.getStylesheets().add(StyleSheet.toExternalForm());
		scene.setRoot(new Group());
		return root;
	}
}
