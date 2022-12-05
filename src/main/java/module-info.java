module com.example.empirejavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires static lombok;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.beval.empirejavafx to javafx.fxml;
    exports com.beval.empirejavafx;

    opens com.beval.empirejavafx.views to javafx.fxml;
    exports com.beval.empirejavafx.views;

    opens com.beval.empirejavafx.views.login to javafx.fxml;
    exports com.beval.empirejavafx.views.login;

    opens com.beval.empirejavafx.views.register to javafx.fxml;
    exports com.beval.empirejavafx.views.register;

    opens com.beval.empirejavafx.views.game to javafx.fxml;
    exports com.beval.empirejavafx.views.game;

    opens com.beval.empirejavafx.views.buildingmenu to javafx.fxml;
    exports com.beval.empirejavafx.views.buildingmenu;

    opens com.beval.empirejavafx.views.buildingpropertymenu to javafx.fxml;
    exports com.beval.empirejavafx.views.buildingpropertymenu;

    opens com.beval.empirejavafx.views.taxmenu to javafx.fxml;
    exports com.beval.empirejavafx.views.taxmenu;

    opens com.beval.empirejavafx.views.armymenu to javafx.fxml;
    exports com.beval.empirejavafx.views.armymenu;

    opens com.beval.empirejavafx.views.overviewmenu to javafx.fxml;
    exports com.beval.empirejavafx.views.overviewmenu;

    opens com.beval.empirejavafx.views.map to javafx.fxml;
    exports com.beval.empirejavafx.views.map;

    opens com.beval.empirejavafx.views.castlemenu to javafx.fxml;
    exports com.beval.empirejavafx.views.castlemenu;

    opens com.beval.empirejavafx.views.castlepreview to javafx.fxml;
    exports com.beval.empirejavafx.views.castlepreview;

    exports com.beval.empirejavafx.manager;
    opens com.beval.empirejavafx.manager to javafx.fxml;

    exports com.beval.empirejavafx.dto.payload;
    exports com.beval.empirejavafx.dto.response;
    exports com.beval.empirejavafx.dto.enums;
}