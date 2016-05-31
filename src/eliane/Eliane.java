/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eliane;

import java.io.File;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 *
 * @author Nanard
 */
public class Eliane extends Application {
    
    private String comboValue = "";
    private ProgressBar pb = new ProgressBar(0);
    
    @Override
     public void start(Stage primaryStage) {
        primaryStage.setTitle("Eliane : extracteur concerto");
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
       // grid.setGridLinesVisible(true);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        
        final ComboBox typeDate = new ComboBox();
        typeDate.setPromptText("Type de dates");
        typeDate.getItems().addAll(
            "Date de la demande",
            "Date de l'inscription"
        );
        typeDate.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String t1) {                
                comboValue = t1;                
            }    
        });
        grid.add(typeDate,0,1,2,1);
        
        Label dateDebutLib = new Label("Date de début");
        grid.add(dateDebutLib,0,2);
        DatePicker dateDebut = new DatePicker();
        grid.add (dateDebut,1,2);
        
        Label dateFinLib = new Label("Date de fin");
        grid.add(dateFinLib,2,2);
        DatePicker dateFin = new DatePicker();
        grid.add (dateFin,3,2);
        
        TextField repertoire = new TextField();
        grid.add (repertoire,0,3,2,1);
        
        final Text actiontarget = new Text();
         
        Button btnOpenDirectoryChooser = new Button();
        btnOpenDirectoryChooser.setText("Parcourir...");
        btnOpenDirectoryChooser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File selectedDirectory = directoryChooser.showDialog(primaryStage);
                 
                if(selectedDirectory == null){
                    actiontarget.setText("No Directory selected");
                }else{
                    repertoire.setText(selectedDirectory.getAbsolutePath());
                }
            }
        });
        grid.add (btnOpenDirectoryChooser,2,3,2,1);
        
        /*Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);*/

       /* Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);*/
        
        HBox hb = new HBox();
        hb.setSpacing(2);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(pb);       
        grid.add(hb, 0, 4, 3, 1);
        
        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 3, 4);
        
        grid.add(actiontarget, 0, 6,4,1);
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
                 @Override
                 public void handle(ActionEvent e) {
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Sign in button pressed " + comboValue);
                 }
        });

        Scene scene = new Scene(grid, 700, 275);
        primaryStage.setScene(scene);
        
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
