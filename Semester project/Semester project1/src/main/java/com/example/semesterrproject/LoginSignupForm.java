package com.example.semesterrproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.PauseTransition;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LoginSignupForm extends Application {

    private Stage primaryStage;

    private final String FILE_PATH = "accounts.txt";
    private Map<String, String> accounts = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        loadAccounts();


        BorderPane root = new BorderPane();


        VBox leftPane = new VBox(10);
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setStyle("-fx-background-color: #0078D7;");
        leftPane.setPrefWidth(400);


        ImageView logo = new ImageView(new Image("CarLogo.png"));
        logo.setFitWidth(130);
        logo.setFitHeight(100);

        Label title = new Label("Car Rental");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("Poppins", FontWeight.BOLD, 30));
        title.setAlignment(Pos.CENTER);

        StackPane formContainer = new StackPane();
        formContainer.setPrefWidth(400);
        formContainer.setStyle("-fx-background-color: #FFFFFF;");


        VBox loginForm = createLoginForm(formContainer);


        formContainer.getChildren().add(loginForm);

        Button createAccountButton = new Button("Create Account");
        Button backToLoginButton = new Button("Back to Login");


        createAccountButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 12px 35px 12px 35px; -fx-font-size: 18px; -fx-font-weight: 900; -fx-border-width: 2px; -fx-border-color: #fff; -fx-border-radius: 5px;");
        createAccountButton.setOnMouseEntered(e -> createAccountButton.setCursor(Cursor.HAND));
        createAccountButton.setOnMouseExited(e -> createAccountButton.setCursor(Cursor.DEFAULT));
        createAccountButton.setOnAction(e -> {
            formContainer.getChildren().clear();
            formContainer.getChildren().add(createSignupForm(formContainer));
            leftPane.getChildren().remove(createAccountButton);
            leftPane.getChildren().add(backToLoginButton);
        });


        backToLoginButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 12px 35px 12px 35px; -fx-font-size: 18px; -fx-font-weight: 900; -fx-border-width: 2px; -fx-border-color: #fff; -fx-border-radius: 5px;");
        backToLoginButton.setOnMouseEntered(e -> backToLoginButton.setCursor(Cursor.HAND));
        backToLoginButton.setOnMouseExited(e -> backToLoginButton.setCursor(Cursor.DEFAULT));
        backToLoginButton.setOnAction(e -> {
            formContainer.getChildren().clear();
            formContainer.getChildren().add(createLoginForm(formContainer));
            leftPane.getChildren().remove(backToLoginButton);
            leftPane.getChildren().add(createAccountButton);
        });

        leftPane.getChildren().addAll(logo, title, createAccountButton);
        VBox.setMargin(createAccountButton, new Insets(30, 0, 0, 0));
        VBox.setMargin(backToLoginButton, new Insets(30, 0, 0, 0));

        root.setLeft(leftPane);
        root.setCenter(formContainer);

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Login, Signup Form");
        primaryStage.setScene(scene);
        primaryStage.setMaxHeight(600);
        primaryStage.setMaxWidth(800);
        primaryStage.show();
    }

    private VBox createLoginForm(StackPane formContainer) {
        VBox loginForm = new VBox(25);
        loginForm.setPadding(new Insets(30, 70, 30, 70));
        loginForm.setAlignment(Pos.CENTER);

        ImageView usersimage = new ImageView(new Image("userslogin.png"));
        usersimage.setFitWidth(50);
        usersimage.setFitHeight(45);
        Label loginTitle = new Label("User Login");
        loginTitle.setFont(Font.font("Poppins", 25));
        loginTitle.setStyle("-fx-text-fill: #0078D7; -fx-font-weight: 600;");

        VBox fieldsbox = new VBox(25);

        HBox usernamebox = new HBox(15);
        ImageView userimage = new ImageView("userimage.png");
        userimage.setFitWidth(20);
        userimage.setFitHeight(20);
        userimage.setPreserveRatio(true);
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-border-color: #0078D7; -fx-border-radius: 2; -fx-padding: 10; -fx-font-size: 15px; -fx-border-width: 0 0 3 0; -fx-background-color: transparent;");
        usernamebox.getChildren().addAll(userimage, usernameField);
        HBox.setHgrow(usernameField, Priority.ALWAYS);
        usernamebox.setAlignment(Pos.BOTTOM_LEFT);


        HBox passwordbox = new HBox(15);
        ImageView passimage = new ImageView("lockimage.png");
        passimage.setFitWidth(18);
        passimage.setFitHeight(20);
        passimage.setPreserveRatio(true);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-border-color: #0078D7; -fx-border-radius: 2; -fx-padding: 10; -fx-font-size: 15px; -fx-border-width: 0 0 3 0; -fx-background-color: transparent;");
        passwordbox.getChildren().addAll(passimage, passwordField);
        HBox.setHgrow(passwordField, Priority.ALWAYS);
        passwordbox.setAlignment(Pos.BOTTOM_LEFT);


        HBox showpassfieldbox = new HBox(15);
        ImageView showpassimage = new ImageView("lockimage.png");
        showpassimage.setFitWidth(18);
        showpassimage.setFitHeight(20);
        showpassimage.setPreserveRatio(true);

        HBox showpassbox = new HBox();
        CheckBox showpass = new CheckBox("Show Password");
        showpass.setStyle("-fx-font-size: 16px");
        TextField showpassField = new TextField();
        showpassField.setPromptText("Show Password");
        showpassField.setStyle("-fx-border-color: #0078D7; -fx-border-radius: 2; -fx-padding: 10; -fx-font-size: 15px; -fx-border-width: 0 0 3 0; -fx-background-color: transparent;");
        showpassfieldbox.setVisible(false);
        showpassfieldbox.setManaged(false);


        showpassfieldbox.getChildren().addAll(showpassimage, showpassField);
        HBox.setHgrow(showpassField, Priority.ALWAYS);
        showpassfieldbox.setAlignment(Pos.BOTTOM_LEFT);

        showpass.setOnAction(e -> {
            if (showpass.isSelected()) {
                showpassField.setText(passwordField.getText());
                showpassfieldbox.setVisible(true);
                showpassfieldbox.setManaged(true);
                passwordbox.setVisible(false);
                passwordbox.setManaged(false);
            }
            else {
                passwordField.setText(showpassField.getText());
                showpassfieldbox.setVisible(false);
                showpassfieldbox.setManaged(false);
                passwordbox.setVisible(true);
                passwordbox.setManaged(true);
            }
        });
        showpassbox.getChildren().addAll(showpass);

        fieldsbox.getChildren().addAll(usernamebox, passwordbox, showpassfieldbox);



        HBox forgotPasswordHBox = new HBox();
        Button forgotPasswordButton = new Button("Forgot your Password?");
        forgotPasswordButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #0078D7; -fx-font-size: 16px; -fx-padding: 0px;");
        forgotPasswordButton.setOnMouseEntered(e -> forgotPasswordButton.setCursor(Cursor.HAND));
        forgotPasswordHBox.setOnMouseExited(e -> forgotPasswordButton.setCursor(Cursor.DEFAULT));
        forgotPasswordButton.setOnAction(e -> {
            formContainer.getChildren().clear();
            formContainer.getChildren().add(createForgotPasswordForm(formContainer));
        });
        forgotPasswordHBox.getChildren().addAll(forgotPasswordButton);

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 13px 0 13px 0; -fx-font-size: 18px; -fx-font-weight: 900;");
        loginButton.prefWidthProperty().bind(loginForm.widthProperty());
        loginButton.setOnMouseEntered(e -> loginButton.setCursor(Cursor.HAND));
        loginButton.setOnMouseExited(e -> loginButton.setCursor(Cursor.DEFAULT));
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.isVisible() ? passwordField.getText() : showpassField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                showMessage(formContainer, "Username and Password cannot be empty", Color.RED);
            } else if (accounts.containsKey(username) && accounts.get(username).equals(password)) {
                showMessage(formContainer, "Login Successful!", Color.GREEN);

                // Transition to the CarRentalUI stage
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5)); // Show success message briefly
                pause.setOnFinished(ev -> {
                    this.primaryStage.close();
                    new Dashboard().start(new Stage());
                });
                pause.play();
            } else {
                showMessage(formContainer, "Invalid Username or Password!", Color.RED);
            }
        });


        loginForm.getChildren().addAll(usersimage, loginTitle, fieldsbox, showpassbox, loginButton,forgotPasswordHBox);
        return loginForm;
    }

    private VBox createSignupForm(StackPane formContainer) {
        VBox signupForm = new VBox(25);
        signupForm.setPadding(new Insets(30, 70, 30, 70));
        signupForm.setAlignment(Pos.CENTER);

        ImageView accountimage = new ImageView(new Image("accountimage.png"));
        accountimage.setFitWidth(50);
        accountimage.setFitHeight(53);
        Label signupTitle = new Label("Create an Account");
        signupTitle.setFont(Font.font("Poppins", 25));
        signupTitle.setStyle("-fx-text-fill: #0078D7; -fx-font-weight: 600;");

        HBox accountusernamebox = new HBox(15);
        ImageView userimage = new ImageView("userimage.png");
        userimage.setFitWidth(20);
        userimage.setFitHeight(20);
        userimage.setPreserveRatio(true);
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-border-color: #0078D7; -fx-border-radius: 2; -fx-padding: 10; -fx-font-size: 15px; -fx-border-width: 0 0 3 0; -fx-background-color: transparent;");
        accountusernamebox.getChildren().addAll(userimage, usernameField);
        HBox.setHgrow(usernameField, Priority.ALWAYS);
        accountusernamebox.setAlignment(Pos.BOTTOM_LEFT);

        HBox accountemailbox = new HBox(15);
        ImageView mailimage = new ImageView("mailimage.png");
        mailimage.setFitWidth(22);
        mailimage.setFitHeight(17);
        mailimage.setPreserveRatio(true);
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setStyle("-fx-border-color: #0078D7; -fx-border-radius: 2; -fx-padding: 10; -fx-font-size: 15px; -fx-border-width: 0 0 3 0; -fx-background-color: transparent;");
        accountemailbox.getChildren().addAll(mailimage, emailField);
        HBox.setHgrow(emailField, Priority.ALWAYS);
        accountemailbox.setAlignment(Pos.BOTTOM_LEFT);


        HBox accountpasswordbox = new HBox(15);
        ImageView passimage = new ImageView("lockimage.png");
        passimage.setFitWidth(18);
        passimage.setFitHeight(20);
        passimage.setPreserveRatio(true);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-border-color: #0078D7; -fx-border-radius: 2; -fx-padding: 10; -fx-font-size: 15px; -fx-border-width: 0 0 3 0; -fx-background-color: transparent;");
        accountpasswordbox.getChildren().addAll(passimage, passwordField);
        HBox.setHgrow(passwordField, Priority.ALWAYS);
        accountpasswordbox.setAlignment(Pos.BOTTOM_LEFT);


        HBox showpassfieldbox = new HBox(15);
        ImageView showpassimage = new ImageView("lockimage.png");
        showpassimage.setFitWidth(18);
        showpassimage.setFitHeight(20);
        showpassimage.setPreserveRatio(true);

        HBox showpassbox = new HBox();
        CheckBox showpass = new CheckBox("Show Password");
        showpass.setStyle("-fx-font-size: 16px");
        TextField showpassField = new TextField();
        showpassField.setPromptText("Show Password");
        showpassField.setStyle("-fx-border-color: #0078D7; -fx-border-radius: 2; -fx-padding: 10; -fx-font-size: 15px; -fx-border-width: 0 0 3 0; -fx-background-color: transparent;");
        showpassfieldbox.setVisible(false);
        showpassfieldbox.setManaged(false);


        showpassfieldbox.getChildren().addAll(showpassimage, showpassField);
        HBox.setHgrow(showpassField, Priority.ALWAYS);
        showpassfieldbox.setAlignment(Pos.BOTTOM_LEFT);

        showpass.setOnAction(e -> {
            if (showpass.isSelected()) {
                showpassField.setText(passwordField.getText());
                showpassfieldbox.setVisible(true);
                showpassfieldbox.setManaged(true);
                accountpasswordbox.setVisible(false);
                accountpasswordbox.setManaged(false);
            }
            else {
                passwordField.setText(showpassField.getText());
                showpassfieldbox.setVisible(false);
                showpassfieldbox.setManaged(false);
                accountpasswordbox.setVisible(true);
                accountpasswordbox.setManaged(true);
            }
        });
        showpassbox.getChildren().addAll(showpass);


        Button signupButton = new Button("Sign Up");
        signupButton.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 13px 0 13px 0; -fx-font-size: 18px; -fx-font-weight: 900;");
        signupButton.prefWidthProperty().bind(signupForm.widthProperty());
        signupButton.setOnAction(e -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.isVisible() ? passwordField.getText() : showpassField.getText();

            if (username.isEmpty() || email.isEmpty() ||password.isEmpty() ) {
                showMessage(formContainer, "All fields must be filled!", Color.RED);
            }
            else if (password.length() < 8) {
                showMessage(formContainer, "Password must be at least 8 characters long!", Color.RED);
            }
            else if(!email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")){
                showMessage(formContainer, "Email address format is invalid!", Color.RED);
            }
            else if (accounts.containsKey(username)) {
                showMessage(formContainer, "Username already exists!", Color.RED);
            } else {
                accounts.put(username, password);
                saveAccounts();
                showMessage(formContainer, "Account Created Successfully!", Color.GREEN);
            }
        });

        signupButton.setOnMouseEntered(e -> signupButton.setCursor(Cursor.HAND));
        signupButton.setOnMouseExited(e -> signupButton.setCursor(Cursor.DEFAULT));



        signupForm.getChildren().addAll(accountimage, signupTitle, accountusernamebox, accountemailbox, accountpasswordbox, showpassfieldbox, showpassbox, signupButton);
        return signupForm;
    }

    private VBox createForgotPasswordForm(StackPane formContainer) {
        VBox forgotPasswordForm = new VBox(25);
        forgotPasswordForm.setPadding(new Insets(50));
        forgotPasswordForm.setAlignment(Pos.CENTER);

        ImageView resetPasswordimage = new ImageView(new Image("resetpassword.png"));
        resetPasswordimage.setFitWidth(50);
        resetPasswordimage.setFitHeight(50);
        Label forgotPasswordTitle = new Label("Reset Password");
        forgotPasswordTitle.setFont(Font.font("Poppins", 25));
        forgotPasswordTitle.setStyle("-fx-text-fill: #0078D7; -fx-font-weight: 600;");


        HBox usernamebox = new HBox(15);
        ImageView userimage = new ImageView("userimage.png");
        userimage.setFitWidth(20);
        userimage.setFitHeight(20);
        userimage.setPreserveRatio(true);
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-border-color: #0078D7; -fx-border-radius: 2; -fx-padding: 10; -fx-font-size: 15px; -fx-border-width: 0 0 3 0; -fx-background-color: transparent;");
        usernamebox.getChildren().addAll(userimage, usernameField);
        HBox.setHgrow(usernameField, Priority.ALWAYS);
        usernamebox.setAlignment(Pos.BOTTOM_LEFT);

        HBox passwordbox = new HBox(15);
        ImageView passimage = new ImageView("lockimage.png");
        passimage.setFitWidth(18);
        passimage.setFitHeight(20);
        passimage.setPreserveRatio(true);
        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("New Password");
        newPasswordField.setStyle("-fx-border-color: #0078D7; -fx-border-radius: 2; -fx-padding: 10; -fx-font-size: 15px; -fx-border-width: 0 0 3 0; -fx-background-color: transparent;");
        passwordbox.getChildren().addAll(passimage, newPasswordField);
        HBox.setHgrow(newPasswordField, Priority.ALWAYS);
        passwordbox.setAlignment(Pos.BOTTOM_LEFT);

        Button resetPasswordButton = new Button("Reset Password");
        resetPasswordButton.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 13px 0 13px 0; -fx-font-size: 18px; -fx-font-weight: 900;");
        resetPasswordButton.prefWidthProperty().bind(forgotPasswordForm.widthProperty());
        resetPasswordButton.setOnAction(e -> {
            String username = usernameField.getText();
            String newPassword = newPasswordField.getText();

            if (accounts.containsKey(username)) {
                accounts.put(username, newPassword);
                saveAccounts();
                showMessage(formContainer, "Password Reset Successfully!", Color.GREEN);
            } else {
                showMessage(formContainer, "Username not found!", Color.RED);
            }
        });
        resetPasswordButton.setOnMouseEntered(e -> resetPasswordButton.setCursor(Cursor.HAND));
        resetPasswordButton.setOnMouseExited(e -> resetPasswordButton.setCursor(Cursor.DEFAULT));

        Button backToLoginButton = new Button("Back to Login");
        backToLoginButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #0078D7; -fx-font-size: 15px;");
        backToLoginButton.setOnAction(e -> {
            formContainer.getChildren().clear();
            formContainer.getChildren().add(createLoginForm(formContainer));
        });
        backToLoginButton.setOnMouseEntered(e -> backToLoginButton.setCursor(Cursor.HAND));
        backToLoginButton.setOnMouseExited(e -> backToLoginButton.setCursor(Cursor.DEFAULT));

        VBox.setMargin(resetPasswordButton, new Insets(20, 0,0 ,0 ));

        forgotPasswordForm.getChildren().addAll(resetPasswordimage, forgotPasswordTitle, usernamebox, passwordbox, resetPasswordButton, backToLoginButton);
        return forgotPasswordForm;

    }


    private void loadAccounts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    accounts.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading accounts: " + e.getMessage());
        }
    }

    private void saveAccounts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, String> entry : accounts.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving accounts: " + e.getMessage());
        }
    }

    private void showMessage(StackPane formContainer, String message, Color color) {
        Label confirmationMessage = new Label(message);
        confirmationMessage.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        confirmationMessage.setTextFill(color);

        formContainer.getChildren().clear();
        formContainer.getChildren().add(confirmationMessage);

        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> {
            formContainer.getChildren().clear();
            formContainer.getChildren().add(createLoginForm(formContainer));
        });
        pause.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
