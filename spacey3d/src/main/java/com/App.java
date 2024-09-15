package com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import com.src.MainInterface;
import com.src.Settings;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Image icon = new Image(getClass().getResourceAsStream("src/misc/graphic/icon.png"));
    
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Spacey3D");
        primaryStage.setHeight(Settings.getWindowHeight());
        primaryStage.setWidth(Settings.getWindowWidth());
        primaryStage.setResizable(false);

        MainInterface mainInterface = new MainInterface();

        Scene scene = mainInterface.start();
        scene.getStylesheets().add(getClass().getResource("src/misc/style.css").toExternalForm());
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}