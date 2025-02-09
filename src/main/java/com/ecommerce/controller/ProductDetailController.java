package com.ecommerce.controller;

import com.ecommerce.model.Product;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailController {
    
    @FXML private Label nameLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label priceLabel;
    @FXML private Label stockLabel;
    @FXML private Label imageLabel;
    @FXML private Button addToCartButton;
    @FXML private Button decreaseButton;
    @FXML private Button increaseButton;
    @FXML private TextField quantityField;
    @FXML private TextFlow detailsFlow;
    
    private Product product;
    private int quantity = 1;
    private List<Product> cart = new ArrayList<>();
    
    public void setProduct(Product product) {
        this.product = product;
        updateUI();
    }
    
    private void updateUI() {
        if (product != null) {
            nameLabel.setText(product.getName());
            descriptionLabel.setText(product.getDescription());
            priceLabel.setText(String.format("%,.2f TL", product.getPrice()));
            
            boolean hasStock = product.getStock() > 0;
            stockLabel.setText(String.format("Stok Durumu: %d adet", product.getStock()));
            stockLabel.setTextFill(hasStock ? Color.web("#2ecc71") : Color.web("#e74c3c"));
            
            addToCartButton.setDisable(!hasStock);
            decreaseButton.setDisable(quantity <= 1 || !hasStock);
            increaseButton.setDisable(quantity >= product.getStock());
            
            // Ürün özelliklerini detaylı olarak göster
            detailsFlow.getChildren().clear();
            addDetailText("• Ürün Kodu: ", product.getId().toString());
            addDetailText("\n• Stok Durumu: ", String.format("%d adet", product.getStock()));
            // Ürüne özel detayları ekle
            if (product.getName().contains("iPhone")) {
                addDetailText("\n• İşlemci: ", "A17 Pro çip");
                addDetailText("\n• Kamera: ", "48MP Ana Kamera");
                addDetailText("\n• Ekran: ", "6.7 inç Super Retina XDR");
            } else if (product.getName().contains("MacBook")) {
                addDetailText("\n• İşlemci: ", "M3 Max");
                addDetailText("\n• RAM: ", "32GB");
                addDetailText("\n• Depolama: ", "1TB SSD");
            }
        }
    }
    
    private void addDetailText(String label, String value) {
        Text labelText = new Text(label);
        labelText.setStyle("-fx-font-weight: bold;");
        
        Text valueText = new Text(value);
        valueText.setFill(Color.web("#666666"));
        
        detailsFlow.getChildren().addAll(labelText, valueText);
    }
    
    @FXML
    private void handleBack() {
        // Ana ürün listesine geri dön
        VBox parent = (VBox) nameLabel.getScene().getRoot();
        parent.getScene().getWindow().hide();
    }
    
    @FXML
    private void handleDecrease() {
        if (quantity > 1) {
            quantity--;
            quantityField.setText(String.valueOf(quantity));
            updateUI();
        }
    }
    
    @FXML
    private void handleIncrease() {
        if (quantity < product.getStock()) {
            quantity++;
            quantityField.setText(String.valueOf(quantity));
            updateUI();
        }
    }
    
    @FXML
    private void handleAddToCart() {
        addToCart(product);
    }
    
    public void addToCart(Product product) {
        // Sepete ekleme işlevselliği
        if (!cart.contains(product)) {
            cart.add(product);
        }
        System.out.println(String.format("%d adet %s sepete eklendi", quantity, product.getName()));
        System.out.println("Sepetteki ürünler: " + cart);
    }
} 