/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailextractor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author mike
 */
public class FXML_ExtractorController implements Initializable {

    @FXML
    private TextArea txtInput;
    @FXML
    private TextArea txtOutput;
    @FXML
    private Button btnExtract;
    @FXML
    private Button btnUrl;
    @FXML
    private TextField txtUrl;
    private String url;
    private Object readContents;
    @FXML
    private TextField txtCount;

     int counter = 0;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private boolean onClickExtract(ActionEvent event) {
        String input = txtInput.getText();
       

        if ((input.trim().length() == 0) || (input == "") || (input.trim().isEmpty())) {

            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Information Dialog");
            alert1.setHeaderText(null);
            alert1.setContentText("Input is Invalid.");

            alert1.showAndWait();
            //System.clearProperty(str_cost);

            return false;

        } else {
            Pattern pattern = Pattern.compile("([a-z0-9_.-]+)@([a-z0-9_.-]+[a-z])");
            Matcher matcher = pattern.matcher(input);

            while (matcher.find()) {

                counter++;
                System.out.println(matcher.group());
                txtOutput.appendText(matcher.group());
                txtOutput.appendText("\n");
            }
        }
        txtInput.setText("");
        txtCount.setText(counter + "");
        return true;
    }

    @FXML
    private void onClickExtractFromUrl(ActionEvent event) throws MalformedURLException {
        url = txtUrl.getText();

        if ((url.trim().length() == 0) || (url == "") || (url.trim().isEmpty())) {

            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Information Dialog");
            alert1.setHeaderText(null);
            alert1.setContentText("Input is Invalid.");

            alert1.showAndWait();
        }

        String pattern = "\\b[a-zA-Z0-9.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9.-]+\\b"; //Email Address Pattern
        URL urlMain; //URL Instance Variable
        StringBuilder contents; //Stores our URL Contents
        Set<String> emailAddresses = new HashSet<>(); //Contains unique email addresses
        urlMain = new URL(url);
        try {
            //Open Connection to URL and get stream to read
            BufferedReader read = new BufferedReader(new InputStreamReader(urlMain.openStream()));
            contents = new StringBuilder();
            //Read and Save Contents to StringBuilder variable
            String input = "";
            while ((input = read.readLine()) != null) {
                contents.append(input);
            }
        } catch (IOException ex) {
            System.out.println("Unable to read URL due to Unknown Host..");
        }
    }

}
