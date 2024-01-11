/*  FXMLDemo - a JavaFX application 'framework' that uses Maven, FXML and CSS.
 *
 *  Copyright 2022 Philip Lockett.
 *
 *  This file is part of FXMLDemo.
 *
 *  FXMLDemo is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  FXMLDemo is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with FXMLDemo.  If not, see <https://www.gnu.org/licenses/>.
 */

/*
 * PrimaryController is the class that is responsible for centralizing control.
 * It is instantiated by the FXML loader creates the Model.
 */
package phillockett65.FXMLDemo;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



public class PrimaryController {

    private Model model;


    /************************************************************************
     * Support code for the Initialization of the Controller.
     */

    /**
     * Responsible for constructing the Model and any local objects. Called by 
     * the FXMLLoader().
     */
    public PrimaryController() {
        // System.out.println("PrimaryController constructed.");
        model = new Model();
    }

    /**
     * Called by the FXML mechanism to initialize the controller. Called after 
     * the constructor to initialise all the controls.
     */
    @FXML public void initialize() {
        // System.out.println("PrimaryController initialized.");
        model.initialize();

        initializeFileSelector();
        initializeTextBoxes();
        initializeCheckBoxes();
        initializeSelections();
        initializeSpinners();
        initializeStatusLine();
    }

    /**
     * Called by Application after the stage has been set. Completes any 
     * initialization dependent on other components being initialized.
     * 
     * @param mainController used to call the centralized controller.
     */
    public void init(Stage primaryStage) {
        // System.out.println("PrimaryController init.");
        model.init(primaryStage);
        syncUI();
        setStatusMessage("Ready.");
    }

    /**
     * Save the current state to disc, called by the application on shut down.
     */
    public void saveState() {
        model.writeData();
    }

    /**
     * Synchronise all controls with the model. This should be the last step 
     * in the initialisation.
     */
    private void syncUI() {
        sourceDocumentTextField.setText(model.getSourceFilePath());
        myTextField.setText(model.getMyText());
        myTextArea.setText(model.getMyBigText());

        firstCheckBox.setSelected(model.isFirstCheck());
        secondCheckBox.setSelected(model.isSecondCheck());
        thirdCheckBox.setSelected(model.isThirdCheck());

        firstRadioButton.setSelected(model.isFirstRadio());
        secondRadioButton.setSelected(model.isSecondRadio());
        thirdRadioButton.setSelected(model.isThirdRadio());

        myChoiceBox.setValue(model.getMonth());
        myComboBox.setValue(model.getBestDay());
        myColourPicker.setValue(model.getMyColour());
    }



    /************************************************************************
     * Support code for "File Selector" panel.
     */

     @FXML
     private TextField sourceDocumentTextField;
 
     @FXML
     private Button browseButton;
 

     @FXML
     void browseButtonActionPerformed(ActionEvent event) {
        openFile();
        setStatusMessage("Loaded file: " + model.getSourceFilePath());
     }

    /**
     * Use a FileChooser dialogue to select the source PDF file.
     * @return true if a file is selected, false otherwise.
     */
    private boolean openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Text File");

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Test File", "*.txt"));

        if (model.isSourceFilePath()) {
            File current = new File(model.getSourceFilePath());
            if (current.exists()) {
                fileChooser.setInitialDirectory(new File(current.getParent()));
                fileChooser.setInitialFileName(current.getName());
            }
        }

        File file = fileChooser.showOpenDialog(model.getStage());
        if (file != null) {
            model.setSourceFilePath(file.getAbsolutePath());
            syncUI();

            return true;
        }

        return false;
    }

     /**
     * Initialize "File Selector" panel.
     */
    private void initializeFileSelector() {
        sourceDocumentTextField.setTooltip(new Tooltip("Selected source document"));
        browseButton.setTooltip(new Tooltip("Select source document"));
    }



    /************************************************************************
     * Support code for "Text Boxes" panel.
     */

    @FXML
    private TextField myTextField;
    
    @FXML
    private TextArea myTextArea;

    @FXML
    void myTextFieldKeyTyped(KeyEvent event) {
        // System.out.println("myTextFieldKeyTyped() " + event.toString());
        model.setMyText(myTextField.getText());
    }


    @FXML
    void myTextAreaKeyTyped(KeyEvent event) {
        // System.out.println("myTextAreaKeyTyped() " + event.toString());
        model.setMyBigText(myTextArea.getText());
    }

    /**
     * Initialize "Text Boxes" panel.
     */
    private void initializeTextBoxes() {
        myTextArea.setPromptText("Add multi line text here.");
        myTextArea.setWrapText(true);

        myTextField.setTooltip(new Tooltip("Add some text"));
        myTextArea.setTooltip(new Tooltip("Add lots of text"));
    }



    /************************************************************************
     * Support code for "Check Boxes and Radio Buttons" panel.
     */

    @FXML
    private CheckBox firstCheckBox;

    @FXML
    private CheckBox secondCheckBox;

    @FXML
    private CheckBox thirdCheckBox;

    @FXML
    private ToggleGroup myToggleGroup;

    @FXML
    private RadioButton firstRadioButton;

    @FXML
    private RadioButton secondRadioButton;

    @FXML
    private RadioButton thirdRadioButton;

    @FXML
    void firstCheckBoxActionPerformed(ActionEvent event) {
        model.setFirstCheck(firstCheckBox.isSelected());
        final String state = model.isFirstCheck() ? "selected." : "unselected.";
        setStatusMessage("First check box " + state);
    }

    @FXML
    void secondCheckBoxActionPerformed(ActionEvent event) {
        model.setSecondCheck(secondCheckBox.isSelected());
        final String state = model.isSecondCheck() ? "selected." : "unselected.";
        setStatusMessage("Second check box " + state);
    }

    @FXML
    void thirdCheckBoxActionPerformed(ActionEvent event) {
        model.setThirdCheck(thirdCheckBox.isSelected());
        final String state = model.isThirdCheck() ? "selected." : "unselected.";
        setStatusMessage("Third check box " + state);
    }

    @FXML
    void myRadioButtonActionPerformed(ActionEvent event) {
        if (firstRadioButton.isSelected()) {
            model.setFirstRadio();
            setStatusMessage("First radio button selected.");
        }
        else
        if (secondRadioButton.isSelected()) {
            model.setSecondRadio();
            setStatusMessage("Second radio button selected.");
        }
        else
        if (thirdRadioButton.isSelected()) {
            model.setThirdRadio();
            setStatusMessage("Third radio button selected.");
        }
    }

    /**
     * Initialize "Check Boxes and Radio Buttons" panel.
     */
    private void initializeCheckBoxes() {
        firstCheckBox.setTooltip(new Tooltip("First check box"));
        secondCheckBox.setTooltip(new Tooltip("Second check box"));
        thirdCheckBox.setTooltip(new Tooltip("Third check box"));

        firstRadioButton.setTooltip(new Tooltip("First radio button"));
        secondRadioButton.setTooltip(new Tooltip("Second radio button"));
        thirdRadioButton.setTooltip(new Tooltip("Third radio button"));
    }



    /************************************************************************
     * Support code for "Selections" panel.
     */

    @FXML
    private ChoiceBox<String> myChoiceBox;

    @FXML
    private ComboBox<String> myComboBox;
    
    @FXML
    private ColorPicker myColourPicker;

    @FXML
    void myComboBoxActionPerformed(ActionEvent event) {
        // System.out.println("baseDirectoryComboBoxActionPerformed() " + event.toString());

        model.setBestDay(myComboBox.getValue());
    }

    @FXML
    void myColourPickerActionPerformed(ActionEvent event) {
        model.setMyColour(myColourPicker.getValue());
        myTextField.setText(model.getMyColourString());
    }

    /**
     * Initialize "Selections" panel.
     */
    private void initializeSelections() {
        myChoiceBox.setItems(model.getMonthList());
        myComboBox.setItems(model.getBestDayList());

        myChoiceBox.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            model.setMonth(newValue);
        });

        myChoiceBox.setTooltip(new Tooltip("Select from a choice box"));
        myComboBox.setTooltip(new Tooltip("Select from a combo box"));
        myColourPicker.setTooltip(new Tooltip("Select a Colour"));
    }



    /************************************************************************
     * Support code for "Spinners" panel.
     */

    @FXML
    private Spinner<Integer> intSpinner;
    
    @FXML
    private Spinner<Double> doubleSpinner;
    
    @FXML
    private Spinner<String> daySpinner;

    /**
     * Initialize "Spinners" panel.
     */
    private void initializeSpinners() {
        intSpinner.setValueFactory(model.getIntegerSVF());
        doubleSpinner.setValueFactory(model.getDoubleSVF());
        daySpinner.setValueFactory(model.getDaySpinnerSVF());

        intSpinner.getValueFactory().wrapAroundProperty().set(true);
        daySpinner.getValueFactory().wrapAroundProperty().set(true);

        intSpinner.setTooltip(new Tooltip("Select integer"));
        doubleSpinner.setTooltip(new Tooltip("Select double"));
        daySpinner.setTooltip(new Tooltip("Select your favourite day"));
        
        intSpinner.valueProperty().addListener( (v, oldValue, newValue) -> {
            // System.out.println("intSpinner.Listener(" + newValue + "))");
            model.syncInteger();
        });

        doubleSpinner.valueProperty().addListener( (v, oldValue, newValue) -> {
            // System.out.println("doubleSpinner.Listener(" + newValue + "))");
            model.syncDouble();
        });

        daySpinner.valueProperty().addListener( (v, oldValue, newValue) -> {
            // System.out.println("daySpinner.Listener(" + newValue + "))");
            model.setDay(newValue);
        });

    }



    /************************************************************************
     * Support code for "Status Line" panel.
     */

    @FXML
    private Label statusLabel;

    @FXML
    private Button clearDataButton;

    @FXML
    void clearDataButtonActionPerformed(ActionEvent event) {
        model.defaultSettings();
        syncUI();
        setStatusMessage("Data reset.");
    }

    private void setStatusMessage(String message) {
        statusLabel.setText(message);
    }

    /**
     * Initialize "Status Line" panel.
     */
    private void initializeStatusLine() {
        statusLabel.setTooltip(new Tooltip("Current status"));
        clearDataButton.setTooltip(new Tooltip("Caution! This irreversible action will reset the form data to default values"));
    }

}