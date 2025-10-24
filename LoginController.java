import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label statusLabel;
    
    private BankingGUI mainApp;

    public void setMainApp(BankingGUI mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize() {
        loginButton.setOnAction(e -> handleLogin());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showStatus("Please enter username and password", true);
            return;
        }

        if (validateLogin(username, password)) {
            showStatus("Login successful!", false);
            mainApp.showMainScreen();
        } else {
            showStatus("Invalid username or password", true);
        }
    }

    private boolean validateLogin(String username, String password) {
        return "Amber_May".equals(username) && "admin123".equals(password);
    }

    private void showStatus(String message, boolean isError) {
        statusLabel.setText(message);
        statusLabel.setStyle(isError ? "-fx-text-fill: #ff4444;" : "-fx-text-fill: #009900;");
    }
}