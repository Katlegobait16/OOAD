import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BankingGUI extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        showLoginScreen();
    }

    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Scene scene = new Scene(loader.load(), 500, 500);
            
            LoginController loginController = loader.getController();
            loginController.setMainApp(this);
            
            primaryStage.setScene(scene);
            primaryStage.setTitle("Banking System - Login");
            primaryStage.show();
            
        } catch (Exception e) {
            System.out.println("Error loading login screen: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void showMainScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            
            MainController mainController = loader.getController();
            mainController.setMainApp(this);
            
            primaryStage.setScene(scene);
            primaryStage.setTitle("Banking System - Main");
            
        } catch (Exception e) {
            System.out.println("Error loading main screen: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}