module com.project.watchit {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.project.watchit to javafx.fxml;
    exports com.project.watchit;
    exports com.project.watchit.Moviee;
    opens com.project.watchit.Moviee to javafx.fxml;

}