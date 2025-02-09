package com.ecommerce.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.ecommerce.dao.UserDAO;
import com.ecommerce.model.User;

import java.io.IOException;
import java.time.LocalDateTime;

public class RegisterController {
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private TextField emailField;
    
    @FXML
    private TextField fullNameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private PasswordField confirmPasswordField;
    
    @FXML
    private Label messageLabel;
    
    private final UserDAO userDAO = new UserDAO();
    
    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String fullName = fullNameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        
        // Validation
        if (username.isEmpty() || email.isEmpty() || fullName.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Lütfen tüm alanları doldurunuz");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            messageLabel.setText("Şifreler eşleşmiyor");
            return;
        }
        
        if (password.length() < 6) {
            messageLabel.setText("Şifre en az 6 karakter olmalıdır");
            return;
        }
        
        try {
            // Check if username already exists
            if (userDAO.findByUsername(username) != null) {
                messageLabel.setText("Bu kullanıcı adı zaten kullanılıyor");
                return;
            }
            
            // Create new user
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setFullName(fullName);
            user.setPassword(password); // In real application, password should be hashed
            user.setCreatedAt(LocalDateTime.now());
            user.setAdmin(false);
            
            userDAO.save(user);
            messageLabel.setText("Kayıt başarılı! Giriş yapabilirsiniz.");
            
            // Navigate back to login after successful registration
            handleBack();
            
        } catch (Exception e) {
            messageLabel.setText("Kayıt olurken bir hata oluştu");
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent loginRoot = loader.load();
            
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.setScene(new Scene(loginRoot));
            currentStage.setTitle("Giriş Yap");
        } catch (IOException e) {
            messageLabel.setText("Giriş sayfasına dönülürken bir hata oluştu");
            e.printStackTrace();
        }
    }
} 