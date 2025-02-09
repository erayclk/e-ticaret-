package com.ecommerce.controller;

import java.io.IOException;
import java.util.List;

import com.ecommerce.dao.ProductDAO;
import com.ecommerce.model.Product;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProductController {
    
    @FXML
    private GridPane productsGrid;
    
    @FXML
    private TextField searchField;
    
    private ProductDAO productDAO;
    private MainController mainController;
    
    public void initialize() {
        productDAO = new ProductDAO();
        loadProducts();
        
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Implement search functionality later
            loadProducts();
        });
    }
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    
    private void loadProducts() {
        productsGrid.getChildren().clear();
        List<Product> products = productDAO.getAllProducts();
        
        int column = 0;
        int row = 0;
        
        for (Product product : products) {
            VBox productCard = createProductCard(product);
            productsGrid.add(productCard, column, row);
            
            column++;
            if (column > 2) {
                column = 0;
                row++;
            }
        }
    }
    
    private VBox createProductCard(Product product) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-border-color: #e0e0e0; " +
                     "-fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2); " +
                     "-fx-cursor: hand;");
        card.setPrefWidth(250);
        card.setAlignment(Pos.TOP_LEFT);
        
        // Ürün kartına tıklama olayı ekle
        card.setOnMouseClicked(e -> showProductDetails(product));
        
        // Product Name
        Label nameLabel = new Label(product.getName());
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        nameLabel.setWrapText(true);
        
        // Description
        Label descLabel = new Label(product.getDescription());
        descLabel.setWrapText(true);
        descLabel.setStyle("-fx-text-fill: #666666;");
        VBox.setVgrow(descLabel, Priority.ALWAYS);
        
        // Price and Stock Container
        HBox priceStockBox = new HBox(10);
        priceStockBox.setAlignment(Pos.CENTER_LEFT);
        
        // Price
        Label priceLabel = new Label(String.format("%,.2f TL", product.getPrice()));
        priceLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        priceLabel.setStyle("-fx-text-fill: #2ecc71;");
        
        // Stock Status
        Label stockLabel = new Label(String.format("Stok: %d", product.getStock()));
        stockLabel.setStyle("-fx-text-fill: " + (product.getStock() > 0 ? "#2ecc71" : "#e74c3c") + ";");
        
        priceStockBox.getChildren().addAll(priceLabel, stockLabel);
        
        // Add to Cart Button
        Button addToCartBtn = new Button("Sepete Ekle");
        addToCartBtn.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; " +
                            "-fx-background-radius: 5; -fx-cursor: hand;");
        addToCartBtn.setMaxWidth(Double.MAX_VALUE);
        addToCartBtn.setOnAction(e -> handleAddToCart(product));
        addToCartBtn.setDisable(product.getStock() <= 0);
        
        // Butona tıklandığında event'in yukarı yayılmasını engelle
        addToCartBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, Event::consume);
        
        // Add all components to card
        card.getChildren().addAll(nameLabel, descLabel, priceStockBox, addToCartBtn);
        VBox.setMargin(addToCartBtn, new Insets(10, 0, 0, 0));
        
        return card;
    }
    
    private void showProductDetails(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/product-detail.fxml"));
            Parent root = loader.load();
            
            ProductDetailController controller = loader.getController();
            controller.setProduct(product);
            
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(product.getName());
            stage.setScene(new Scene(root));
            stage.setWidth(800);
            stage.setHeight(600);
            stage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void handleAddToCart(Product product) {
        if (mainController != null) {
            mainController.addToCart(product);
        }
    }
} 