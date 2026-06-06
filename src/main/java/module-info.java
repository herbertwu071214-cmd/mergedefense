module com.herb.macondo.mergedefense {
    requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;


    opens com.herb.macondo.mergedefense to javafx.fxml;
    exports com.herb.macondo.mergedefense;
    exports com.herb.macondo.mergedefense.controller;
    exports com.herb.macondo.mergedefense.input;
    exports com.herb.macondo.mergedefense.model;
    exports com.herb.macondo.mergedefense.view;
}
