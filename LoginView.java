import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LoginView extends VBox {
    public TextField usernameField;
    public PasswordField passwordField;
    public Button loginButton;
    public Label statusLabel;

    public LoginView() {
        initializeUI();
    }

    private void initializeUI() {
        setSpacing(20);
        setPadding(new Insets(40));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: #e8f4f8;"); // Light blue background

        // Header
        Label titleLabel = new Label("Banking System");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.DARKBLUE);
        
        Label subLabel = new Label("Assignment - OOAD with Java");
        subLabel.setFont(Font.font("Arial", 12));
        subLabel.setTextFill(Color.GRAY);

        VBox headerBox = new VBox(5, titleLabel, subLabel);
        headerBox.setAlignment(Pos.CENTER);

        // Login Form
        VBox formContainer = new VBox(15);
        formContainer.setAlignment(Pos.CENTER);
        formContainer.setMaxWidth(300);
        formContainer.setPadding(new Insets(25));
        formContainer.setStyle("-fx-background-color: white; -fx-border-color: #cccccc; -fx-border-width: 1; -fx-border-radius: 8;");

        Label formTitle = new Label("Login");
        formTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        // Form Fields
        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-padding: 8; -fx-font-size: 14;");
        usernameField.setMaxWidth(250);

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-padding: 8; -fx-font-size: 14;");
        passwordField.setMaxWidth(250);

        loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14; -fx-padding: 8 20;");
        loginButton.setMaxWidth(250);

        statusLabel = new Label();
        statusLabel.setWrapText(true);
        statusLabel.setMaxWidth(250);

        formContainer.getChildren().addAll(
            formTitle,
            new Label("Username:"),
            usernameField,
            new Label("Password:"),
            passwordField,
            loginButton,
            statusLabel
        );
        
        getChildren().addAll(headerBox, formContainer);
    }

    public void clearFields() {
        usernameField.clear();
        passwordField.clear();
    }

    public void setStatus(String message, boolean isError) {
        statusLabel.setText(message);
        if (isError) {
            statusLabel.setStyle("-fx-text-fill: #ff4444; -fx-font-size: 12;");
        } else {
            statusLabel.setStyle("-fx-text-fill: #009900; -fx-font-size: 12;");
        }
    }
}