import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RunBankingSystem extends Application {
    private LoginView loginView;
    private AccountView accountView;
    private Stage primaryStage;
    
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        loginView = new LoginView();
        accountView = new AccountView();

        showLoginView();
        
        stage.setTitle("Banking System - GUI Test");
        stage.show();
    }
    
    private void showLoginView() {

        loginView.loginButton.setOnAction(e -> {
            String username = loginView.usernameField.getText();
            String password = loginView.passwordField.getText();
            
            if (username.isEmpty() || password.isEmpty()) {
                loginView.setStatus("Please enter both username and password", true);
            } else {
                loginView.setStatus("Login successful! (UI Test)", false);

                new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            javafx.application.Platform.runLater(() -> showMainApplication());
                        }
                    },
                    1000
                );
            }
        });
        
        Scene scene = new Scene(loginView, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Banking System - Login");
    }
    
    private void showMainApplication() {

        accountView.logoutBtn.setOnAction(e -> {
            showLoginView();
            loginView.clearFields();
        });
        
        accountView.addCustomerBtn.setOnAction(e -> {
            System.out.println("Add Customer clicked - UI works!");
            accountView.clearCustomerFields();
        });
        
        Scene scene = new Scene(accountView, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Banking System - Main Application");
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}