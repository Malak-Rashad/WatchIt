package com.project.watchit;
import com.project.watchit.person_app.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.*;
import java.util.Objects;
public class ControllerSubscription {
@FXML
private RadioButton StandardOption;
    @FXML
private RadioButton PremiumOption;
    @FXML
private RadioButton BasicOption;
    @FXML
private Button SubmitButton;
    @FXML
private ToggleGroup SubscriptionPlans;
    @FXML
    private void onSubmit()
    {

        if(BasicOption.isSelected()){


        }
        else if(PremiumOption.isSelected())
        {

        }
        else if(PremiumOption.isSelected())
        {

        }
        else {

        }
    }
}
