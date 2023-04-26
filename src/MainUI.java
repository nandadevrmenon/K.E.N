import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;

public class MainUI extends Application {

    public AnchorPane welcomeWindow;
    public TextField messageBar;
    public TextArea answerArea;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        Parent root;
        try { // load the UI from the FXML file
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("UI.fxml")));
        } catch (IOException e) { // if the FXML file is not found or null, throw an exception
            throw new RuntimeException(e);
        }

        // load style
        root.getStylesheets().add("style.css");

        // set the title of the window
        stage.setTitle("Weather App");

        // set the scene of the window
        stage.setScene(new javafx.scene.Scene(root));

        // hide the window when button click
        stage.setOnCloseRequest(event -> {
            stage.hide();
            event.consume();
        });



        // show the window
        stage.show();

    }



    @FXML
    private void printHelloWorld(ActionEvent event) {
        event.consume();
        // hide welcomeWindow when button click
        welcomeWindow.setVisible(false);

        // show messageBar and answerArea when button click
        String message = messageBar.getText();

        // clear messageBar when button click
        messageBar.clear();

        // print message in answerArea when button click
        answerArea.appendText(message + "\n");
    }

}
