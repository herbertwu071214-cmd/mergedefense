module com.herb.mergedefense {
    requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;


    opens com.herb.mergedefense to javafx.fxml;
    exports com.herb.mergedefense;
    exports com.herb.mergedefense.controller;
    exports com.herb.mergedefense.input;
    exports com.herb.mergedefense.model;
    exports com.herb.mergedefense.view;
}
