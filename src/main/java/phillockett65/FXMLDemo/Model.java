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
 * Model is the class that captures the dynamic shared data plus some 
 * supporting constants and provides access via getters and setters.
 */
package phillockett65.FXMLDemo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;

public class Model {

    public static final String DATAFILE = "Settings.dat";
    public static final double TOPBARHEIGHT = 32.0;
    private static final String TOPBARICON = "top-bar-icon";

    private static Model model = new Model();
    private Stage stage;


    /************************************************************************
     * General support code.
     */

    /**
     * Convert a real colour value (0.0 to 1.0) to an int (0 too 256).
     * @param value to convert.
     * @return converted value.
     */
    private int colourRealToInt(double value) {
        return (int)(value * 256);
    }

 
    /**
     * Builds the cancel button as a Pane.
     * Does not include the mouse click handler.
     * @return the Pane that represents the cancel button.
     */
    public static Pane buildCancelButton() {
        final double iconSize = TOPBARHEIGHT;
        final double cancelPadding = 0.3;

        Pane cancel = new Pane();
        cancel.setPrefWidth(iconSize);
        cancel.setPrefHeight(iconSize);
        cancel.getStyleClass().add(TOPBARICON);

        double a = iconSize * cancelPadding;
        double b = iconSize - a;
        Line line1 = new Line(a, a, b, b);
        line1.setStroke(Color.WHITE);
        line1.setStrokeWidth(4.0);
        line1.setStrokeLineCap(StrokeLineCap.ROUND);

        Line line2 = new Line(a, b, b, a);
        line2.setStroke(Color.WHITE);
        line2.setStrokeWidth(4.0);
        line2.setStrokeLineCap(StrokeLineCap.ROUND);

        cancel.getChildren().addAll(line1, line2);

        return cancel;
    }

    /**
     * Add or remove the unfocussed style from the given pane object.
     * @param pane to add/remove unfocussed style.
     * @param style named in .css to define unfocussed style.
     * @param state is true if we have focus, false otherwise.
     */
    public static void styleFocus(Pane pane, String style, boolean state) {
        if (state) {
            pane.getStyleClass().remove(style);
        } else {
            if (!pane.getStyleClass().contains(style)) {
                pane.getStyleClass().add(style);
            }
        }
    }



    /************************************************************************
     * Support code for the Initialization of the Model.
     */

    /**
     * Private default constructor - part of the Singleton Design Pattern.
     * Called at initialization only, constructs the single private instance.
     */
    private Model() {

    }

    /**
     * Singleton implementation.
     * @return the only instance of the model.
     */
    public static Model getInstance() { return model; }

    /**
     * Called by the controller after the constructor to initialise any 
     * objects after the controls have been initialised.
     */
    public void initialize() {
        // System.out.println("Model initialized.");

        initializeFileSelector();
        initializeTextBoxes();
        initializeCheckBoxes();
        initializeSelections();
        initializeSpinners();
        initializeStatusLine();
    }

    /**
     * Called by the controller after the stage has been set. Completes any 
     * initialization dependent on other components being initialized.
     */
    public void init(Stage primaryStage) {
        // System.out.println("Model init.");
        
        stage = primaryStage;
        if (!readData())
            defaultSettings();
    }

    public Stage getStage() { return stage; }
    public String getTitle() { return stage.getTitle(); }

    public void close() {
        stage.close();
    }

    /**
     * Set all attributes to the default values.
     */
    public void defaultSettings() {
        setSourceFilePath("");
        setOutputFilePath("");
        setMyText("Hello World");
        setMyBigText("");

        setFirstCheck(true);
        setSecondCheck(false);
        setThirdCheck(false);

        setSecondRadio();

        setInteger(10);
        setDouble(1.0);
        setDay("Tuesday");

        setMonth("July");
        setBestDay("New Year");
        setMyColour(Color.RED);
    }



    /************************************************************************
     * Support code for state persistence.
     */

    /**
     * Call the static DataStore1 method, to read the data from disc.
     * @return true if data successfully read from disc, false otherwise.
     */
    private boolean readData() {
        if (DataStore1.readData() == true) {
            return true;
        }

        return false;
    }



    /************************************************************************
     * Support code for "File Selector" panel.
     */

     private String sourceDocument;
     private String outputFilePath;

    /**
     * Set the full file path for the source document.
     * @param text string of the source document file path.
     */
    public void setSourceFilePath(String text) { sourceDocument = text; }

    /**
     * @return the full file path for the current source document.
     */
    public String getSourceFilePath() { return sourceDocument; }

    /**
     * @return true if a source document has been selected, false otherwise.
     */
    public boolean isSourceFilePath() { return !sourceDocument.isBlank(); }

    /**
     * Set the full file path for the generated document.
     * @param text string of the file name for the generated document.
     */
    public void setOutputFilePath(String text) { outputFilePath = text; }

    /**
     * @return the full file name for the generated document.
     */
    public String getOutputFilePath() { return outputFilePath; }

    /**
     * @return true if a source document has been selected, false otherwise.
     */
    public boolean isOutputFilePath() { return !outputFilePath.isBlank(); }

    /**
     * Save the file.
     * @return the file path of the saved data.
     */
    public boolean saveFile() {

        return true;
    }


    /**
     * Initialize "File Selector" panel.
     */
    private void initializeFileSelector() {
    }


    /************************************************************************
     * Support code for "Text Boxes" panel.
     */

    private String myText;
    private String myBigText;

    public void setMyText(String text) { myText = text; }
    public String getMyText() { return myText; }
    public void setMyBigText(String text) { myBigText = text; }
    public String getMyBigText() { return myBigText; }

    /**
     * Initialize "Text Boxes" panel.
     */
    private void initializeTextBoxes() {
    }



    /************************************************************************
     * Support code for "Check Boxes and Radio Buttons" panel.
     */

    private boolean firstCheck;
    private boolean secondCheck;
    private boolean thirdCheck;

    private enum RadioSelection { FIRST, SECOND, THIRD };

    private RadioSelection radioSelected;

    public void setFirstCheck(boolean state) { firstCheck = state; }
    public void setSecondCheck(boolean state) { secondCheck = state; }
    public void setThirdCheck(boolean state) { thirdCheck = state; }

    public boolean isFirstCheck() { return firstCheck; }
    public boolean isSecondCheck() { return secondCheck; }
    public boolean isThirdCheck() { return thirdCheck; }

    public void setFirstRadio() { radioSelected = RadioSelection.FIRST; }
    public void setSecondRadio() { radioSelected = RadioSelection.SECOND; }
    public void setThirdRadio() { radioSelected = RadioSelection.THIRD; }

    public boolean isFirstRadio() { return radioSelected == RadioSelection.FIRST; }
    public boolean isSecondRadio() { return radioSelected == RadioSelection.SECOND; }
    public boolean isThirdRadio() { return radioSelected == RadioSelection.THIRD; }

    /**
     * Initialize "Check Boxes and Radio Buttons" panel.
     */
    private void initializeCheckBoxes() {
    }



    /************************************************************************
     * Support code for "Selections" panel.
     */

    private String month;
    private ObservableList<String> monthList = FXCollections.observableArrayList();

    private String bestDay;
    private ObservableList<String> bestDayList = FXCollections.observableArrayList();

    private Color myColour;

    public ObservableList<String> getMonthList() { return monthList; }
    public void setMonth(String value) { month = value; }
    public String getMonth() { return month; }

    public ObservableList<String> getBestDayList() { return bestDayList; }
    public void setBestDay(String value) { bestDay = value; }
    public String getBestDay() { return bestDay; }

    public Color getMyColour() { return myColour; }
    public void setMyColour(Color colour) { myColour = colour; }

    /**
     * @return myColour as a displayable RGB string.
     */
    public String getMyColourString() {
        return String.format("rgb(%d, %d, %d)",
                colourRealToInt(myColour.getRed()),
                colourRealToInt(myColour.getGreen()),
                colourRealToInt(myColour.getBlue()));
    }

    /**
     * Initialize "Selections" panel.
     */
    private void initializeSelections() {
        bestDayList.add("New Year");
        bestDayList.add("Good Friday");
        bestDayList.add("Easter Monday");
        bestDayList.add("Victoria Day");
        bestDayList.add("Canada Day");
        bestDayList.add("Civic Holiday");
        bestDayList.add("Labour Day");
        bestDayList.add("Thanksgiving Day");
        bestDayList.add("Remembrance Day");
        bestDayList.add("Christmas Day");
        bestDayList.add("Boxing Day");

        monthList.addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    }



    /************************************************************************
     * Support code for "Spinners" panel.
     */

    private SpinnerValueFactory<Integer> integerSVF;

    private SpinnerValueFactory<Double>  doubleSVF;

    private SpinnerValueFactory<String>  daySVF;

    private ObservableList<String> daysOfWeekList = FXCollections.observableArrayList();

    public SpinnerValueFactory<Integer> getIntegerSVF() { return integerSVF; }
    public SpinnerValueFactory<Double> getDoubleSVF() { return doubleSVF; }
    public SpinnerValueFactory<String> getDaySpinnerSVF() { return daySVF; }

    public int getInteger() { return integerSVF.getValue(); }
    public double getDouble() { return doubleSVF.getValue(); }
    public String getDay() { return daySVF.getValue(); }
    public void setInteger(int value) { integerSVF.setValue(value); }
    public void setDouble(double value) { doubleSVF.setValue(value); }
    public void setDay(String value) { daySVF.setValue(value); }

    /**
     * Selected Integer has changed, so synchronize values.
     */
    public void syncInteger() {  }

    /**
     * Selected Double has changed, so synchronize values.
     */
    public void syncDouble() {  }

    /**
     * Selected Day has changed, so synchronize values.
     */
    public void syncDay() {  }


    /**
     * Initialize "Spinners" panel.
     */
    private void initializeSpinners() {
        integerSVF = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 10);
        doubleSVF = new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0, 20.0, 1.0, 0.2);

        daysOfWeekList.add("Monday");
        daysOfWeekList.add("Tuesday");
        daysOfWeekList.add("Wednesday");
        daysOfWeekList.add("Thursday");
        daysOfWeekList.add("Friday");
        daysOfWeekList.add("Saturday");
        daysOfWeekList.add("Sunday");

        daySVF = new SpinnerValueFactory.ListSpinnerValueFactory<String>(daysOfWeekList);
    }



    /************************************************************************
     * Support code for "Status Line" panel.
     */

    /**
     * Initialize "Status Line" panel.
     */
    private void initializeStatusLine() {
    }


}
