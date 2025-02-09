package com.ecommerce.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ecommerce.model.Product;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainController {
    
    @FXML
    private StackPane contentArea;
    
    @FXML
    private Label statusLabel;
    
    private List<Product> cartItems = new ArrayList<>();
    private ShoppingCartController cartController;
    
    @FXML
    private void handleExit() {
        Platform.exit();
    }
    
    @FXML
    private void handleProfile() {
        viewProfile();
    }
    
    public void viewProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/profile.fxml"));
            Parent profileView = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(profileView);
            updateStatus("Profil sayfası");
            
            // Implement profile view
            // For example, you can load user data from database and display it in the profile view
            // ProfileController profileController = loader.getController();
            // profileController.setUserData(userData);
        } catch (IOException e) {
            updateStatus("Profil sayfası yüklenirken bir hata oluştu");
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent loginRoot = loader.load();
            
            Stage currentStage = (Stage) contentArea.getScene().getWindow();
            currentStage.setScene(new Scene(loginRoot));
            currentStage.setTitle("Giriş Yap");
        } catch (IOException e) {
            updateStatus("Çıkış yapılırken bir hata oluştu");
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleHome() {
        // Ana sayfaya dönüldüğünde ürünler sayfasını göster
        handleProducts();
        updateStatus("Ana sayfa");
    }
    
    @FXML
    private void handleProducts() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/products.fxml"));
            Parent productsView = loader.load();
            
            ProductController productController = loader.getController();
            productController.setMainController(this);
            
            contentArea.getChildren().clear();
            contentArea.getChildren().add(productsView);
            updateStatus("Ürünler sayfası");
        } catch (IOException e) {
            updateStatus("Ürünler sayfası yüklenirken bir hata oluştu");
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleCart() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/shoppingCart.fxml"));
            Parent shoppingCartView = loader.load();
            
            cartController = loader.getController();
            cartController.setMainController(this);
            cartController.setCartItems(cartItems);
            
            contentArea.getChildren().clear();
            contentArea.getChildren().add(shoppingCartView);
            updateStatus("Alışveriş sepeti");
        } catch (IOException e) {
            updateStatus("Alışveriş sepeti yüklenirken bir hata oluştu");
            e.printStackTrace();
        }
    }
    
    public void addToCart(Product product) {
        cartItems.add(product);
        if (cartController != null) {
            cartController.addToCart(product);
        }
        updateStatus(product.getName() + " sepete eklendi");
    }
    
    public void clearCart() {
        cartItems.clear();
        if (cartController != null) {
            cartController.setCartItems(new ArrayList<>());
        }
    }
    
    public List<Product> getCartItems() {
        return cartItems;
    }
    
    @FXML
    public void handleOrders() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/orders.fxml"));
            Parent ordersView = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(ordersView);
            updateStatus("Siparişler sayfası");
        } catch (IOException e) {
            updateStatus("Siparişler sayfası yüklenirken bir hata oluştu");
            e.printStackTrace();
        }
    }
    
    private void updateStatus(String message) {
        statusLabel.setText(message);
    }
} 