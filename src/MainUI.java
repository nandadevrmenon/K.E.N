import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainUI extends Application implements Initializable {

    public AnchorPane welcomeWindow;
    public TextField messageBar;
    public ObservableList<String> data = FXCollections.observableArrayList();
    public ListView<String> listView = new ListView<>();

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

        // load icon
        stage.getIcons().add(new Image("image.png"));
        // load style
        root.getStylesheets().add("style.css");
        // set the title of the window
        stage.setTitle("Weather App");
        // set the scene of the window
        stage.setScene(new javafx.scene.Scene(root));
        // show the window
        stage.show();
    }

    @FXML
    private void onClick(ActionEvent event) {
        event.consume();
        // hide welcomeWindow when button click
        if (welcomeWindow.isVisible() && !messageBar.getText().isEmpty()) {
            welcomeWindow.setVisible(false);
        }

        // get the message from the messageBar
        if (!messageBar.getText().isEmpty()) {
            data.add("\uD83D\uDC66 : " + messageBar.getText());
            messageBar.clear();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.setItems(data);
    }
}
