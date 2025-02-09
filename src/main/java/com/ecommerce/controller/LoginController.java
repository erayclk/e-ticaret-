package com.ecommerce.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ecommerce.dao.UserDAO;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    
    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());
    private UserDAO userDAO;
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Label messageLabel;
    
    @FXML
    public void initialize() {
        try {
            LOGGER.info("Initializing LoginController...");
            userDAO = new UserDAO();
            LOGGER.info("UserDAO initialized successfully");
        } catch (Exception e) {
            String errorMsg = "Veritabanı bağlantısı kurulamadı: " + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMsg, e);
            Platform.runLater(() -> {
                if (messageLabel != null) {
                    messageLabel.setText(errorMsg);
                }
            });
        }
    }
    
    @FXML
    private void handleLogin() {
        try {
            LOGGER.info("Login attempt started...");
            
            if (userDAO == null) {
                String errorMsg = "Veritabanı bağlantısı başlatılamadı";
                LOGGER.severe(errorMsg);
                messageLabel.setText(errorMsg);
                return;
            }
            
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            
            LOGGER.info("Validating input fields...");
            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Lütfen kullanıcı adı ve şifre giriniz");
                return;
            }
            
            LOGGER.info("Attempting authentication for user: " + username);
            try {
                if (userDAO.authenticate(username, password)) {
                    LOGGER.info("Authentication successful");
                    messageLabel.setText("Giriş başarılı!");
                    navigateToMainWindow();
                } else {
                    LOGGER.info("Authentication failed - invalid credentials");
                    messageLabel.setText("Geçersiz kullanıcı adı veya şifre");
                    passwordField.clear();
                }
            } catch (Exception e) {
                String errorMsg = "Kimlik doğrulama hatası: " + e.getMessage();
                LOGGER.log(Level.SEVERE, errorMsg, e);
                messageLabel.setText(errorMsg);
                e.printStackTrace();
            }
        } catch (Exception e) {
            String errorMsg = "Beklenmeyen bir hata oluştu: " + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMsg, e);
            messageLabel.setText(errorMsg);
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleRegister() {
        try {
            LOGGER.info("Attempting to navigate to registration page...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
            Parent registerRoot = loader.load();
            
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.setScene(new Scene(registerRoot));
            currentStage.setTitle("Kayıt Ol");
            LOGGER.info("Successfully navigated to registration page");
        } catch (IOException e) {
            String errorMsg = "Kayıt sayfası açılırken bir hata oluştu: " + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMsg, e);
            messageLabel.setText(errorMsg);
            e.printStackTrace();
        }
    }
    
    private void navigateToMainWindow() {
        try {
            LOGGER.info("Attempting to navigate to main window...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
            Parent mainRoot = loader.load();
            
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.setScene(new Scene(mainRoot));
            currentStage.setTitle("E-Ticaret Uygulaması");
            LOGGER.info("Successfully navigated to main window");
        } catch (IOException e) {
            String errorMsg = "Ana sayfa açılırken bir hata oluştu: " + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMsg, e);
            messageLabel.setText(errorMsg);
            e.printStackTrace();
        }
    }
} 