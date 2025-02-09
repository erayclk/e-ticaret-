package com.ecommerce.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.ecommerce.dao.ProductDAO;
import com.ecommerce.model.Product;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ShoppingCartController {
    
    @FXML private VBox cartItemsContainer;
    @FXML private Label totalPriceLabel;
    @FXML private Button clearCartButton;
    @FXML private Button checkoutButton;
    
    private List<Product> cartItems = new ArrayList<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private MainController mainController;
    private ProductDAO productDAO = new ProductDAO();
    
    @FXML
    public void initialize() {
        updateTotalPrice();
    }
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    
    public void addToCart(Product product) {
        cartItems.add(product);
        addCartItemView(product);
        updateTotalPrice();
    }
    
    private void addCartItemView(Product product) {
        HBox itemContainer = new HBox(15);
        itemContainer.setAlignment(Pos.CENTER_LEFT);
        itemContainer.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 5; -fx-border-color: #e0e0e0; -fx-border-radius: 5;");
        
        // Sol taraftaki ürün bilgileri için VBox
        VBox productInfo = new VBox(5);
        productInfo.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(productInfo, Priority.ALWAYS);
        
        // Ürün adı
        Label nameLabel = new Label(product.getName());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        nameLabel.setWrapText(true);
        
        // Ürün açıklaması
        Label descLabel = new Label(product.getDescription());
        descLabel.setStyle("-fx-text-fill: #666666; -fx-font-size: 12px;");
        descLabel.setWrapText(true);
        
        productInfo.getChildren().addAll(nameLabel, descLabel);
        
        // Sağ taraftaki fiyat ve buton için VBox
        VBox priceAndButton = new VBox(8);
        priceAndButton.setAlignment(Pos.CENTER_RIGHT);
        
        // Fiyat
        Label priceLabel = new Label(String.format("%,.2f TL", product.getPrice()));
        priceLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2ecc71; -fx-font-size: 14px;");
        
        // Kaldır butonu
        Button removeButton = new Button("Kaldır");
        removeButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 12px; " +
                            "-fx-background-radius: 3; -fx-cursor: hand;");
        removeButton.setOnAction(e -> removeFromCart(product, itemContainer));
        
        priceAndButton.getChildren().addAll(priceLabel, removeButton);
        
        // Tüm bileşenleri ana container'a ekle
        itemContainer.getChildren().addAll(productInfo, priceAndButton);
        cartItemsContainer.getChildren().add(itemContainer);
    }
    
    private void removeFromCart(Product product, HBox itemContainer) {
        // Yerel sepetten kaldır
        cartItems.remove(product);
        cartItemsContainer.getChildren().remove(itemContainer);
        updateTotalPrice();
        
        // MainController'daki sepetten de kaldır
        if (mainController != null) {
            mainController.getCartItems().remove(product);
        }
    }
    
    private void updateTotalPrice() {
        totalPrice = cartItems.stream()
            .map(Product::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        totalPriceLabel.setText(String.format("Toplam: %,.2f TL", totalPrice));
        checkoutButton.setDisable(cartItems.isEmpty());
        clearCartButton.setDisable(cartItems.isEmpty());
    }
    
    @FXML
    private void handleClearCart() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Sepeti Temizle");
        alert.setHeaderText("Sepeti Temizle");
        alert.setContentText("Sepetinizdeki tüm ürünler silinecek. Devam etmek istiyor musunuz?");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // MainController'daki sepeti temizle
                if (mainController != null) {
                    mainController.clearCart();
                }
                
                // Yerel sepeti temizle
                cartItems.clear();
                cartItemsContainer.getChildren().clear();
                updateTotalPrice();
            }
        });
    }
    
    @FXML
    private void handleCheckout() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Siparişi Tamamla");
        alert.setHeaderText("Siparişi Tamamla");
        
        // Sepetteki ürünleri listele
        StringBuilder orderDetails = new StringBuilder();
        orderDetails.append("Sipariş Detayları:\n\n");
        for (Product product : cartItems) {
            orderDetails.append(String.format("• %s\n", product.getName()));
        }
        orderDetails.append(String.format("\nToplam Tutar: %,.2f TL", totalPrice));
        orderDetails.append("\n\nSiparişinizi onaylıyor musunuz?");
        
        alert.setContentText(orderDetails.toString());
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Stokları güncelle
                boolean stockUpdateSuccess = updateProductStocks();
                
                if (stockUpdateSuccess) {
                    // Sipariş tamamlandı bildirimi
                    Alert successAlert = new Alert(AlertType.INFORMATION);
                    successAlert.setTitle("Sipariş Tamamlandı");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Siparişiniz başarıyla oluşturuldu!\nSiparişlerim sayfasından takip edebilirsiniz.");
                    
                    successAlert.showAndWait();
                    
                    // MainController'daki sepeti temizle
                    if (mainController != null) {
                        mainController.clearCart();
                    }
                    
                    // Sepeti temizle
                    cartItems.clear();
                    cartItemsContainer.getChildren().clear();
                    updateTotalPrice();
                    
                    // Siparişler sayfasına yönlendir
                    if (mainController != null) {
                        mainController.handleOrders();
                    }
                } else {
                    // Stok güncelleme hatası
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setTitle("Hata");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Sipariş oluşturulurken bir hata oluştu.\nLütfen daha sonra tekrar deneyin.");
                    errorAlert.showAndWait();
                }
            }
        });
    }
    
    private boolean updateProductStocks() {
        try {
            for (Product product : cartItems) {
                // Ürünü veritabanından al
                Product dbProduct = productDAO.getProductById(product.getId());
                if (dbProduct != null && dbProduct.getStock() > 0) {
                    // Stok sayısını azalt
                    dbProduct.setStock(dbProduct.getStock() - 1);
                    // Veritabanını güncelle
                    productDAO.updateProduct(dbProduct);
                } else {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Product> getCartItems() {
        return cartItems;
    }
    
    public void setCartItems(List<Product> items) {
        cartItems.clear();
        cartItemsContainer.getChildren().clear();
        
        for (Product item : items) {
            addToCart(item);
        }
    }
} 