package com.ecommerce.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class OrdersController {
    
    @FXML private VBox ordersContainer;
    @FXML private ComboBox<String> filterComboBox;
    @FXML private Label emptyOrdersLabel;
    
    private List<Order> orders = new ArrayList<>();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    
    @FXML
    public void initialize() {
        // Filtre seçeneklerini ayarla
        filterComboBox.getItems().addAll(
            "Son 1 ay",
            "Son 3 ay",
            "Son 6 ay",
            "Tüm siparişler"
        );
        filterComboBox.setValue("Tüm siparişler");
        filterComboBox.setOnAction(e -> filterOrders());
        
        // Örnek siparişler ekle
        addSampleOrders();
        
        // Siparişleri göster
        updateOrdersView();
    }
    
    private void addSampleOrders() {
        // Örnek sipariş 1
        Order order1 = new Order();
        order1.orderNumber = "SP" + System.currentTimeMillis();
        order1.orderDate = LocalDateTime.now().minusDays(2);
        order1.totalAmount = 84999.00;
        order1.status = "Teslim Edildi";
        order1.products.add("iPhone 15 Pro - 256GB, Titanium");
        order1.products.add("AirPods Pro 2");
        orders.add(order1);
        
        // Örnek sipariş 2
        Order order2 = new Order();
        order2.orderNumber = "SP" + (System.currentTimeMillis() - 1000);
        order2.orderDate = LocalDateTime.now().minusDays(15);
        order2.totalAmount = 124999.00;
        order2.status = "Kargoda";
        order2.products.add("MacBook Pro 16\" - M3 Max, 32GB RAM");
        orders.add(order2);

        // Örnek sipariş 3
        Order order3 = new Order();
        order3.orderNumber = "SP" + (System.currentTimeMillis() - 2000);
        order3.orderDate = LocalDateTime.now().minusMonths(1);
        order3.totalAmount = 44999.00;
        order3.status = "Teslim Edildi";
        order3.products.add("iPad Pro 12.9\" - M2 çip, 256GB");
        order3.products.add("Apple Pencil 2. Nesil");
        orders.add(order3);

        // Örnek sipariş 4
        Order order4 = new Order();
        order4.orderNumber = "SP" + (System.currentTimeMillis() - 3000);
        order4.orderDate = LocalDateTime.now().minusMonths(2);
        order4.totalAmount = 74999.00;
        order4.status = "Teslim Edildi";
        order4.products.add("Samsung Galaxy S24 Ultra - 512GB");
        order4.products.add("Galaxy Buds2 Pro");
        orders.add(order4);

        // Örnek sipariş 5
        Order order5 = new Order();
        order5.orderNumber = "SP" + (System.currentTimeMillis() - 4000);
        order5.orderDate = LocalDateTime.now().minusMonths(2).minusDays(15);
        order5.totalAmount = 89999.00;
        order5.status = "Teslim Edildi";
        order5.products.add("ASUS ROG Zephyrus G14");
        order5.products.add("ROG Gaming Mouse");
        orders.add(order5);

        // Örnek sipariş 6
        Order order6 = new Order();
        order6.orderNumber = "SP" + (System.currentTimeMillis() - 5000);
        order6.orderDate = LocalDateTime.now().minusMonths(3);
        order6.totalAmount = 64999.00;
        order6.status = "Teslim Edildi";
        order6.products.add("NVIDIA GeForce RTX 4090");
        orders.add(order6);

        // Örnek sipariş 7
        Order order7 = new Order();
        order7.orderNumber = "SP" + (System.currentTimeMillis() - 6000);
        order7.orderDate = LocalDateTime.now().minusMonths(4);
        order7.totalAmount = 19999.00;
        order7.status = "Teslim Edildi";
        order7.products.add("PlayStation 5 Digital Edition");
        order7.products.add("DualSense Controller");
        orders.add(order7);

        // Örnek sipariş 8
        Order order8 = new Order();
        order8.orderNumber = "SP" + (System.currentTimeMillis() - 7000);
        order8.orderDate = LocalDateTime.now().minusMonths(5);
        order8.totalAmount = 24999.00;
        order8.status = "Teslim Edildi";
        order8.products.add("AMD Ryzen 9 7950X3D");
        orders.add(order8);

        // Örnek sipariş 9
        Order order9 = new Order();
        order9.orderNumber = "SP" + (System.currentTimeMillis() - 8000);
        order9.orderDate = LocalDateTime.now().minusMonths(5).minusDays(15);
        order9.totalAmount = 39999.00;
        order9.status = "Teslim Edildi";
        order9.products.add("Samsung Galaxy Tab S9 Ultra");
        order9.products.add("Samsung Book Cover Keyboard");
        orders.add(order9);

        // Örnek sipariş 10
        Order order10 = new Order();
        order10.orderNumber = "SP" + (System.currentTimeMillis() - 9000);
        order10.orderDate = LocalDateTime.now().minusMonths(6);
        order10.totalAmount = 21999.00;
        order10.status = "Teslim Edildi";
        order10.products.add("Steam Deck - 512GB");
        order10.products.add("Taşıma Çantası");
        orders.add(order10);
    }
    
    private void filterOrders() {
        String filter = filterComboBox.getValue();
        LocalDateTime cutoffDate = switch (filter) {
            case "Son 1 ay" -> LocalDateTime.now().minusMonths(1);
            case "Son 3 ay" -> LocalDateTime.now().minusMonths(3);
            case "Son 6 ay" -> LocalDateTime.now().minusMonths(6);
            default -> LocalDateTime.now().minusYears(100); // Tüm siparişler
        };
        
        List<Order> filteredOrders = orders.stream()
            .filter(order -> order.orderDate.isAfter(cutoffDate))
            .toList();
            
        updateOrdersView(filteredOrders);
    }
    
    private void updateOrdersView() {
        updateOrdersView(orders);
    }
    
    private void updateOrdersView(List<Order> ordersToShow) {
        ordersContainer.getChildren().clear();
        
        if (ordersToShow.isEmpty()) {
            emptyOrdersLabel.setVisible(true);
            return;
        }
        
        emptyOrdersLabel.setVisible(false);
        
        for (Order order : ordersToShow) {
            VBox orderBox = createOrderBox(order);
            ordersContainer.getChildren().add(orderBox);
        }
    }
    
    private VBox createOrderBox(Order order) {
        VBox orderBox = new VBox(10);
        orderBox.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 8; -fx-border-color: #e0e0e0; -fx-border-radius: 8;");
        
        // Üst kısım: Sipariş no, tarih ve durum
        HBox headerBox = new HBox(15);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        
        Label orderNumberLabel = new Label("Sipariş No: " + order.orderNumber);
        orderNumberLabel.setStyle("-fx-font-weight: bold;");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Label dateLabel = new Label(order.orderDate.format(formatter));
        dateLabel.setStyle("-fx-text-fill: #666666;");
        
        Label statusLabel = new Label(order.status);
        statusLabel.setStyle("-fx-background-color: " + getStatusColor(order.status) + "; -fx-text-fill: white; -fx-padding: 5 10; -fx-background-radius: 15;");
        
        headerBox.getChildren().addAll(orderNumberLabel, spacer, dateLabel, statusLabel);
        
        // Orta kısım: Ürünler
        VBox productsBox = new VBox(5);
        productsBox.setStyle("-fx-padding: 10 0;");
        for (String product : order.products) {
            Label productLabel = new Label("• " + product);
            productLabel.setStyle("-fx-text-fill: #333333;");
            productsBox.getChildren().add(productLabel);
        }
        
        // Alt kısım: Toplam tutar
        Label totalLabel = new Label(String.format("Toplam: %,.2f TL", order.totalAmount));
        totalLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2ecc71;");
        
        orderBox.getChildren().addAll(headerBox, new Separator(), productsBox, new Separator(), totalLabel);
        return orderBox;
    }
    
    private String getStatusColor(String status) {
        return switch (status) {
            case "Teslim Edildi" -> "#2ecc71";
            case "Kargoda" -> "#3498db";
            case "İptal Edildi" -> "#e74c3c";
            default -> "#95a5a6";
        };
    }
    
    private static class Order {
        String orderNumber;
        LocalDateTime orderDate;
        double totalAmount;
        String status;
        List<String> products = new ArrayList<>();
    }
} 