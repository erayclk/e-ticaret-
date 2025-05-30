﻿# E-Ticaret Uygulaması

Modern ve kullanıcı dostu bir masaüstü e-ticaret uygulaması. JavaFX ve Hibernate kullanılarak geliştirilmiştir.

## Özellikler

### 🛍️ Ürün Yönetimi
- Ürünleri listele ve detaylarını görüntüle
- Ürün arama
- Stok takibi
- Kategorilere göre filtreleme

### 🛒 Alışveriş Sepeti
- Ürün ekleme/çıkarma
- Toplam tutar hesaplama
- Sepeti temizleme
- Siparişi tamamlama

### 📦 Sipariş Yönetimi
- Sipariş geçmişi görüntüleme
- Sipariş durumu takibi
- Tarih bazlı filtreleme
- Sipariş detayları

### 👤 Kullanıcı Yönetimi
- Kullanıcı kaydı
- Giriş yapma
- Profil yönetimi

## Teknik Özellikler

### 🔧 Kullanılan Teknolojiler
- Java 17
- JavaFX - UI framework
- Hibernate - ORM
- MySQL - Veritabanı
- Maven - Bağımlılık yönetimi

### 📁 Proje Yapısı
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── ecommerce/
│   │           ├── controller/  # UI kontrolcüleri
│   │           ├── dao/         # Veritabanı işlemleri
│   │           ├── model/       # Veri modelleri
│   │           └── util/        # Yardımcı sınıflar
│   └── resources/
│       ├── fxml/       # UI tasarım dosyaları
│       ├── css/        # Stil dosyaları
│       └── images/     # Görseller
```

## Kurulum

### 📋 Gereksinimler
- Java 17 veya üzeri
- MySQL 8.0 veya üzeri
- Maven 3.6 veya üzeri

### ⚙️ Veritabanı Kurulumu
1. MySQL sunucusunu başlatın
2. Aşağıdaki SQL komutunu çalıştırın:
```sql
CREATE DATABASE ecommerce;
```

### 🚀 Uygulama Kurulumu
1. Projeyi klonlayın:
```bash
git clone https://github.com/kullanici/e-ticaret.git
```

2. Proje dizinine gidin:
```bash
cd e-ticaret
```

3. Bağımlılıkları yükleyin:
```bash
mvn install
```

4. Uygulamayı başlatın:
```bash
mvn javafx:run
```

### ⚙️ Veritabanı Yapılandırması
`src/main/resources/hibernate.cfg.xml` dosyasında veritabanı bağlantı ayarlarını yapın:

```xml
<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/ecommerce</property>
<property name="hibernate.connection.username">kullanici_adi</property>
<property name="hibernate.connection.password">sifre</property>
```

## 📸 Ekran Görüntüleri

### Giriş Sayfası
![Giriş Sayfası](screenshots/loginPage.png)
- Kullanıcı girişi
- Yeni hesap oluşturma

### Ana Sayfa
![Ana Sayfa](screenshots/mainPage.png)
- Ürünlerin listelendiği ana sayfa
- Kategorilere göre filtreleme
- Ürün arama

### Alışveriş Sepeti
![Sepet](screenshots/cardPage.png)
- Sepetteki ürünler
- Toplam tutar
- Ürün kaldırma
- Siparişi tamamlama

### Siparişler
![Siparişler](screenshots/orderPage.png)
- Sipariş geçmişi
- Sipariş durumu
- Tarih filtreleme

## 🤝 Katkıda Bulunma
1. Bu depoyu fork edin
2. Yeni bir branch oluşturun (`git checkout -b feature/yeniOzellik`)
3. Değişikliklerinizi commit edin (`git commit -am 'Yeni özellik: XYZ'`)
4. Branch'inizi push edin (`git push origin feature/yeniOzellik`)
5. Pull Request oluşturun

## 📝 Lisans
Bu proje MIT lisansı altında lisanslanmıştır. Detaylar için [LICENSE](LICENSE) dosyasına bakın.


