package mimuw.idlearn.scenes.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import mimuw.idlearn.scenes.ResourceHandler;
import mimuw.idlearn.scoring.PointsGiver;
import mimuw.idlearn.userdata.DataManager;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class TaskSelectionController extends GenericController {
	@FXML
	private VBox tasksVBox;
	@FXML
	private BorderPane mainBorderPane;

	/**
	 * Makes the button of a completed task darker and gives it a popup on click.
	 * This assumes the task's text has been set.
	 * @param taskBtn: button of a task
	 */
	private void setStyleOfButtonForCompletedTask(Button taskBtn) {
		taskBtn.setStyle("-fx-background-color: #029c5b;");
	}

	/** Loads all the user's available tasks as clickable buttons **/
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		Button pointsBtn = ResourceHandler.createUserPointsButton();
		mainBorderPane.setTop(pointsBtn);
		Set<String> completedTasks = PointsGiver.getCompletedTasks();

		List<String> tasks = new java.util.ArrayList<>(DataManager.getUnlockedTasks());
		Collections.sort(tasks);

		double btnWidth = tasksVBox.getMaxWidth();
		for (final String taskTitle : tasks) {
			Button taskBtn = ResourceHandler.createGreenButton(taskTitle, btnWidth);
			tasksVBox.getChildren().add(taskBtn);

			// make the style change dynamically
			PointsGiver.connectToTaskCompletion(event -> {
				if (event.value() == taskTitle)
					setStyleOfButtonForCompletedTask(taskBtn);
			});
			if (completedTasks.contains(taskTitle))
				setStyleOfButtonForCompletedTask(taskBtn);

			taskBtn.setOnAction((event) -> {
				try {
					enterNextScene(ResourceHandler.Task, new TaskController(taskTitle));
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
	}
}



