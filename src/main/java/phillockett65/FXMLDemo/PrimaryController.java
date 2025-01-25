/*  FXMLDemo - a JavaFX application 'framework' that uses Maven, FXML and CSS.
 *
 *  Copyright 2024 Philip Lockett.
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import phillockett65.Debug.Debug;
import phillockett65.FXMLDemo.Command.MonthCommand;
import phillockett65.FXMLDemo.Command.ColourCommand;
import phillockett65.FXMLDemo.Command.DayCommand;
import phillockett65.FXMLDemo.Command.DoubleCommand;
import phillockett65.FXMLDemo.Command.BestDayCommand;
import phillockett65.FXMLDemo.Command.FirstCheckBoxCommand;
import phillockett65.FXMLDemo.Command.IntegerCommand;
import phillockett65.FXMLDemo.Command.Invoker;
import phillockett65.FXMLDemo.Command.OutputFileCommand;
import phillockett65.FXMLDemo.Command.SecondCheckBoxCommand;
import phillockett65.FXMLDemo.Command.SelectRadioCommand;
import phillockett65.FXMLDemo.Command.SourceFileCommand;
import phillockett65.FXMLDemo.Command.TextAreaCommand;
import phillockett65.FXMLDemo.Command.TextFieldCommand;
import phillockett65.FXMLDemo.Command.ThirdCheckBoxCommand;

public class PrimaryController {

    // Debug delta used to adjust the local logging level.
    private static final int DD = 0;

    private Model model;
    private Invoker invoker;

    @FXML
    private VBox root;


    /************************************************************************
     * General support code.
     */




     /************************************************************************
     * Support code for the Initialization of the Controller.
     */

    /**
     * Default constructor.
     * Called by the FXMLLoader().
     */
    public PrimaryController() {
        Debug.trace(DD, "PrimaryController constructed.");
        model = Model.getInstance();
        invoker = Invoker.getInstance();
    }

    /**
     * Called by the FXML mechanism to initialize the controller. Called after 
     * the constructor to initialise all the controls.
     */
    @FXML public void initialize() {
        Debug.trace(DD, "PrimaryController initialized.");
        model.initialize();

        initializeTopBar();
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
        Debug.trace(DD, "PrimaryController init.");
        model.init(primaryStage, this);
        syncUI();
        setStatusMessage("Ready.");
        invoker.clear();

        // Use filter so text based controls do not affect the undo/redo.
        primaryStage.getScene().addEventFilter(KeyEvent.KEY_TYPED, 
            new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    final String key = event.getCharacter();
        
                    switch ((int)key.charAt(0)) {
                    case 25:
                        invoker.redo();
                        break;
        
                    case 26:
                        invoker.undo();
                        break;
        
                    case 13:
                        invoker.dump();
                        break;
        
                    default:
                        break;
                    }
                }
            });

    }

    /**
     * Set the styles based on the focus state.
     * @param state is true if we have focus, false otherwise.
     */
    public void setFocus(boolean state) {
        Model.styleFocus(root, "unfocussed-root", state);
        Model.styleFocus(topBar, "unfocussed-bar", state);
    }

    /**
     * Synchronise all controls with the model. This should be the last step 
     * in the initialisation.
     */
    public void syncUI() {
        syncSourceDocumentTextField();
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

    private void syncSourceDocumentTextField() {
        sourceDocumentTextField.setText(model.getSourceFilePath());
    }



    /************************************************************************
     * Support code for "Top Bar" panel.
     */

    private double x = 0.0;
    private double y = 0.0;

    @FXML
    private HBox topBar;

    @FXML
    void topBarOnMousePressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    void topBarOnMouseDragged(MouseEvent event) {
        model.getStage().setX(event.getScreenX() - x);
        model.getStage().setY(event.getScreenY() - y);
    }
 

    /**
     * Initialize "Top Bar" panel.
     */
    private void initializeTopBar() {
        Pane cancel = Model.buildCancelButton();
        cancel.setOnMouseClicked(event -> model.close());

        topBar.getChildren().add(cancel);
    }
  

    /************************************************************************
     * Support code for Pull-down Menu structure.
     */

    @FXML
    private void fileLoadOnAction() {
        launchLoadWindow();
    }

    @FXML
    private void fileSaveOnAction() {
        if (model.isOutputFilePath()) {
            if (model.saveFile())
                setStatusMessage("Saved to: " + model.getOutputFilePath());
        }
        else
            launchSaveAsWindow();
    }

    @FXML
    private void fileSaveAsOnAction() {
        launchSaveAsWindow();
    }

    @FXML
    private void fileCloseOnAction() {
        model.getStage().close();
    }

    @FXML
    private void editClearOnAction() {
        clearData();
    }

    @FXML
    private void helpAboutOnAction() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About FXML Demo");
        alert.setHeaderText(model.getTitle());
        alert.setContentText("The FXML Demo application demonstrates FXML capabilities.");

        alert.showAndWait();
    }

    private void launchLoadWindow() {
        openFile();
    }

    private void launchSaveAsWindow() {
        saveAs();
    }

    public void fileLoaded(boolean loaded) {
        if (loaded) {
            syncSourceDocumentTextField();
            setStatusMessage("Loaded file: " + model.getSourceFilePath());
        }
    }

    public void fileSaved(boolean saved) {
        if (saved) {
            setStatusMessage("Saved file: " + model.getOutputFilePath());
        }
    }


    /************************************************************************
     * Support code for "File Selector" panel.
     */

    @FXML
    private TextField sourceDocumentTextField;

    @FXML
    private Button browseButton;


    @FXML
    private void browseButtonActionPerformed(ActionEvent event) {
        launchLoadWindow();
    }

    /**
     * Use a FileChooser dialogue to select the source text file.
     */
    private void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Text File");

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text File", "*.txt"));

        if (model.isSourceFilePath()) {
            File current = new File(model.getSourceFilePath());
            if (current.exists()) {
                fileChooser.setInitialDirectory(new File(current.getParent()));
                fileChooser.setInitialFileName(current.getName());
            }
        }

        File file = fileChooser.showOpenDialog(model.getStage());
        if (file != null) {
            SourceFileCommand command = new SourceFileCommand(file.getAbsolutePath());
            invoker.invoke(command);
        }
    }

    private void saveAs() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Text File");

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text File", "*.txt"));

        if (model.isOutputFilePath()) {
            File current = new File(model.getOutputFilePath());
            fileChooser.setInitialFileName(current.getName());

            File parent = new File(current.getParent());
            if (parent.exists())
                fileChooser.setInitialDirectory(parent);
        }
        else
        if (model.isSourceFilePath()) {
            File current = new File(model.getSourceFilePath());
            File parent = new File(current.getParent());
            if (parent.exists())
                fileChooser.setInitialDirectory(parent);
        }
        File file = fileChooser.showSaveDialog(model.getStage());
        if (file != null) {
            OutputFileCommand command = new OutputFileCommand(file.getAbsolutePath());
            invoker.invoke(command);
        }
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
    private void myTextFieldKeyTyped(KeyEvent event) {
        Debug.trace(DD, "myTextFieldKeyTyped() " + event.toString());
        TextFieldCommand command = new TextFieldCommand(myTextField.getText());
        invoker.invoke(command);
    }


    @FXML
    private void myTextAreaKeyTyped(KeyEvent event) {
        Debug.trace(DD, "myTextAreaKeyTyped() " + event.toString());
        TextAreaCommand command = new TextAreaCommand(myTextArea.getText());
        invoker.invoke(command);
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
    private void firstCheckBoxActionPerformed(ActionEvent event) {
        FirstCheckBoxCommand command = new FirstCheckBoxCommand(firstCheckBox.isSelected());
        invoker.invoke(command);
    }

    @FXML
    private void secondCheckBoxActionPerformed(ActionEvent event) {
        SecondCheckBoxCommand command = new SecondCheckBoxCommand(secondCheckBox.isSelected());
        invoker.invoke(command);
    }

    @FXML
    private void thirdCheckBoxActionPerformed(ActionEvent event) {
        ThirdCheckBoxCommand command = new ThirdCheckBoxCommand(thirdCheckBox.isSelected());
        invoker.invoke(command);
    }

    @FXML
    private void myRadioButtonActionPerformed(ActionEvent event) {
        int index = 0;
        if (firstRadioButton.isSelected()) {
            index =  1;
        }
        else
        if (secondRadioButton.isSelected()) {
            index =  2;
        }
        else
        if (thirdRadioButton.isSelected()) {
            index =  3;
        }
        SelectRadioCommand command = new SelectRadioCommand(index);
        invoker.invoke(command);
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
    private void myComboBoxActionPerformed(ActionEvent event) {
        Debug.trace(DD, "myComboBoxActionPerformed() " + myComboBox.getValue());

        BestDayCommand command = new BestDayCommand(myComboBox.getValue());
        invoker.invoke(command);
    }

    @FXML
    private void myColourPickerActionPerformed(ActionEvent event) {
        Debug.trace(DD, "myColourPickerActionPerformed() " + myColourPicker.getValue());

        ColourCommand command = new ColourCommand(myColourPicker.getValue());
        invoker.invoke(command);
    }

    /**
     * Initialize "Selections" panel.
     */
    private void initializeSelections() {
        myChoiceBox.setItems(model.getMonthList());
        myComboBox.setItems(model.getBestDayList());

        myChoiceBox.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            Debug.trace(DD, "myChoiceBox..selectedItemProperty() " + oldValue + " -> " + newValue);

            MonthCommand command = new MonthCommand(oldValue, newValue);
            invoker.invoke(command);
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
            Debug.trace(DD, "intSpinner.Listener(" + newValue + ")");

            IntegerCommand command = new IntegerCommand(oldValue, newValue);
            invoker.invoke(command);
        });

        doubleSpinner.valueProperty().addListener( (v, oldValue, newValue) -> {
            Debug.trace(DD, "doubleSpinner.Listener(" + newValue + ")");

            DoubleCommand command = new DoubleCommand(oldValue, newValue);
            invoker.invoke(command);
        });

        daySpinner.valueProperty().addListener( (v, oldValue, newValue) -> {
            Debug.trace(DD, "daySpinner.Listener(" + newValue + ")");

            DayCommand command = new DayCommand(oldValue, newValue);
            invoker.invoke(command);
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
    private void clearDataButtonActionPerformed(ActionEvent event) {
        clearData();
    }

    public void setStatusMessage(String message) {
        statusLabel.setText(message);
    }

    /**
     * Clear all current data to the default settings then update the UI.
     */
    private void clearData() {
        model.defaultSettings();
        syncUI();
        setStatusMessage("Data reset.");
    }

    /**
     * Initialize "Status Line" panel.
     */
    private void initializeStatusLine() {
        statusLabel.setTooltip(new Tooltip("Current status"));
        clearDataButton.setTooltip(new Tooltip("Caution! This irreversible action will reset the form data to default values"));
    }

}