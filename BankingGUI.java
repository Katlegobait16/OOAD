import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class BankingGUI extends Application {
    private LoginView loginView;
    private AccountView accountView;
    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        
        loginView = new LoginView();
        accountView = new AccountView();
        
        setupLoginEvents();
        
        stage.setScene(new Scene(loginView, 500, 500));
        stage.setTitle("Banking System - Login");
        stage.show();
    }

    private void setupLoginEvents() {
        loginView.loginButton.setOnAction(e -> {
            String username = loginView.usernameField.getText();
            String password = loginView.passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                loginView.setStatus("Please enter username and password", true);
                return;
            }

            if ("admin".equals(username) && "admin123".equals(password)) {
                loginView.setStatus("Login successful!", false);
                showMainApplication();
            } else {
                loginView.setStatus("Invalid credentials! Use: admin / admin123", true);
            }
        });
    }

    private void showMainApplication() {
        // navigation
        accountView.logoutBtn.setOnAction(e -> {
            primaryStage.setScene(new Scene(loginView, 500, 500));
            primaryStage.setTitle("Banking System - Login");
            loginView.clearFields();
        });

        Scene mainScene = new Scene(accountView, 800, 600);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Banking System - Main Dashboard");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}