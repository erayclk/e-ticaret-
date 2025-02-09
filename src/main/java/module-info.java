module com.ecommerce {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires java.sql;

    opens com.ecommerce to javafx.fxml;
    opens com.ecommerce.controller to javafx.fxml;
    opens com.ecommerce.model to org.hibernate.orm.core;
    
    exports com.ecommerce;
    exports com.ecommerce.controller;
    exports com.ecommerce.model;
    exports com.ecommerce.dao;
    exports com.ecommerce.util;
} 