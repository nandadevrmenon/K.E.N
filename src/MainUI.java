import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainUI extends Application {

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

        stage.setTitle("WeatherBot");
        stage.setScene(root.getScene());
        stage.show();
    }

}
