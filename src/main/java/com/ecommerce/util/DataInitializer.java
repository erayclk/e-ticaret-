package com.ecommerce.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.ecommerce.dao.ProductDAO;
import com.ecommerce.model.Product;

public class DataInitializer {
    private final ProductDAO productDAO;
    private final String DEFAULT_IMAGE_URL = "https://placehold.co/400x400";
    
    public DataInitializer() {
        this.productDAO = new ProductDAO();
    }
    
    public void initializeProducts() {
        List<Product> products = Arrays.asList(
            // Telefonlar
            new Product(
                "iPhone 15 Pro Max",
                "1TB, Titanium Doğal, A17 Pro çip, 48MP Kamera, Dynamic Island",
                new BigDecimal("94999.00"),
                DEFAULT_IMAGE_URL,
                8
            ),
            new Product(
                "iPhone 15 Pro",
                "256GB, Titanium Siyah, A17 Pro çip, 48MP Kamera",
                new BigDecimal("84999.00"),
                DEFAULT_IMAGE_URL,
                10
            ),
            new Product(
                "Samsung Galaxy S24 Ultra",
                "512GB, Titanium Gri, Snapdragon 8 Gen 3, 200MP Kamera, S Pen",
                new BigDecimal("74999.00"),
                DEFAULT_IMAGE_URL,
                15
            ),
            new Product(
                "Samsung Galaxy Z Fold5",
                "512GB, Phantom Black, Ana Ekran: 7.6\", Dış Ekran: 6.2\"",
                new BigDecimal("69999.00"),
                DEFAULT_IMAGE_URL,
                6
            ),
            new Product(
                "Google Pixel 8 Pro",
                "256GB, Bay, Google Tensor G3, 50MP Kamera",
                new BigDecimal("44999.00"),
                DEFAULT_IMAGE_URL,
                12
            ),

            // Laptoplar
            new Product(
                "MacBook Pro 16\"",
                "M3 Max, 32GB RAM, 1TB SSD, Uzay Grisi, Mini LED Ekran",
                new BigDecimal("124999.00"),
                DEFAULT_IMAGE_URL,
                5
            ),
            new Product(
                "MacBook Air 15\"",
                "M2, 16GB RAM, 512GB SSD, Gece Yarısı",
                new BigDecimal("64999.00"),
                DEFAULT_IMAGE_URL,
                9
            ),
            new Product(
                "ASUS ROG Zephyrus G14",
                "AMD Ryzen 9 7940HS, RTX 4090, 32GB RAM, 1TB SSD",
                new BigDecimal("89999.00"),
                DEFAULT_IMAGE_URL,
                4
            ),
            new Product(
                "Lenovo ThinkPad X1 Carbon",
                "Intel Core i7-1355U, 32GB RAM, 1TB SSD, 14\" 2.8K OLED",
                new BigDecimal("54999.00"),
                DEFAULT_IMAGE_URL,
                7
            ),
            new Product(
                "Dell XPS 15",
                "Intel Core i9-13900H, RTX 4070, 32GB RAM, 1TB SSD, 4K OLED",
                new BigDecimal("79999.00"),
                DEFAULT_IMAGE_URL,
                6
            ),

            // Tabletler
            new Product(
                "iPad Pro 12.9\"",
                "M2 çip, 256GB, Wi-Fi + Cellular, Uzay Grisi, Liquid Retina XDR",
                new BigDecimal("44999.00"),
                DEFAULT_IMAGE_URL,
                8
            ),
            new Product(
                "Samsung Galaxy Tab S9 Ultra",
                "512GB, Graphite, 14.6\" AMOLED, S Pen Dahil",
                new BigDecimal("39999.00"),
                DEFAULT_IMAGE_URL,
                10
            ),
            new Product(
                "iPad Air",
                "M1 çip, 256GB, Wi-Fi, Gök Mavisi",
                new BigDecimal("24999.00"),
                DEFAULT_IMAGE_URL,
                15
            ),

            // Aksesuarlar
            new Product(
                "AirPods Pro 2",
                "USB-C şarj kutusu, Aktif Gürültü Engelleme, Adaptif Ses",
                new BigDecimal("7999.00"),
                DEFAULT_IMAGE_URL,
                20
            ),
            new Product(
                "Samsung Galaxy Buds2 Pro",
                "Intelligent ANC, 360 Audio, IPX7 Su Dayanıklılığı",
                new BigDecimal("4999.00"),
                DEFAULT_IMAGE_URL,
                25
            ),
            new Product(
                "Apple Watch Series 9",
                "45mm, Alüminyum Kasa, Gece Yarısı, GPS + Cellular",
                new BigDecimal("19999.00"),
                DEFAULT_IMAGE_URL,
                12
            ),
            new Product(
                "Samsung Galaxy Watch6 Classic",
                "47mm, Bluetooth, Döner Çerçeve, BIA Sensörü",
                new BigDecimal("14999.00"),
                DEFAULT_IMAGE_URL,
                18
            ),

            // Bilgisayar Bileşenleri
            new Product(
                "NVIDIA GeForce RTX 4090",
                "24GB GDDR6X, DLSS 3, Ray Tracing, 4K Gaming",
                new BigDecimal("64999.00"),
                DEFAULT_IMAGE_URL,
                3
            ),
            new Product(
                "AMD Ryzen 9 7950X3D",
                "16 Çekirdek, 32 Thread, 144MB Önbellek, AM5",
                new BigDecimal("24999.00"),
                DEFAULT_IMAGE_URL,
                8
            ),
            new Product(
                "Samsung 990 PRO SSD",
                "4TB, PCIe 4.0 NVMe, 7450MB/s Okuma",
                new BigDecimal("9999.00"),
                DEFAULT_IMAGE_URL,
                15
            ),

            // Oyun Konsolları
            new Product(
                "PlayStation 5",
                "Dijital Sürüm, DualSense Kontrolcü, 1TB SSD",
                new BigDecimal("19999.00"),
                DEFAULT_IMAGE_URL,
                10
            ),
            new Product(
                "Xbox Series X",
                "1TB SSD, 4K Gaming, Xbox Game Pass Ultimate 3 Ay",
                new BigDecimal("18999.00"),
                DEFAULT_IMAGE_URL,
                12
            ),
            new Product(
                "Nintendo Switch OLED",
                "7\" OLED Ekran, 64GB, Beyaz, Joy-Con Kontrolcü",
                new BigDecimal("12999.00"),
                DEFAULT_IMAGE_URL,
                20
            ),
            new Product(
                "Steam Deck",
                "512GB NVMe SSD, Anti-Glare Ekran, Özel Taşıma Çantası",
                new BigDecimal("21999.00"),
                DEFAULT_IMAGE_URL,
                6
            ),
            new Product(
                "ROG Ally",
                "512GB, AMD Z1 Extreme, Windows 11, 120Hz Ekran",
                new BigDecimal("24999.00"),
                DEFAULT_IMAGE_URL,
                4
            )
        );
        
        for (Product product : products) {
            try {
                productDAO.saveProduct(product);
                System.out.println("Ürün eklendi: " + product.getName());
            } catch (Exception e) {
                System.err.println("Ürün eklenirken hata: " + product.getName());
                e.printStackTrace();
            }
        }
    }
} 