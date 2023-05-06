/*
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class MainUITest {
    @Test
    public void launchApp() throws InterruptedException {
        Thread thread = new Thread(() -> {
            new JFXPanel(); // Initializes the JavaFx Platform
            Platform.runLater(() -> {
                new MainUI().start(new Stage()); // Create and initialize the app.
            });
        });

        try {
            thread.start();// Initialize the thread

            Assertions.assertTrue(true);
        } catch (IllegalThreadStateException e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

    @Test
    public void loadUI() {
        File file = new File("ressources/UI.fxml");
        Assertions.assertTrue(file.exists());
    }
}

 */