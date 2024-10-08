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
 * DataStore is a class that serializes the settings data for saving and 
 * restoring to and from disc.
 */
package phillockett65.FXMLDemo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.scene.paint.Color;

public class DataStore implements Serializable {
    private static final long serialVersionUID = 1L;

    private double mainX;
    private double mainY;

    private String sourceDocument;
    private String outputDocument;

    private String myText;
    private String myBigText;

    private Boolean firstCheck;
    private Boolean secondCheck;
    private Boolean thirdCheck;

    private Integer radioSelection;

    private String month;
    private String bestDay;
    private Double red;
    private Double green;
    private Double blue;

    private Integer myInteger;
    private Double myDouble;
    private String day;



    /************************************************************************
     * Support code for the Initialization of the DataStore.
     */

    public DataStore() {
    }

    public Double getMainX() { return mainX; }
    public void setMainX(Double myDouble) { this.mainX = myDouble; }
    public Double getMainY() { return mainY; }
    public void setMainY(Double myDouble) { this.mainY = myDouble; }

    public String getSourceDocument() { return sourceDocument; }
    public void setSourceDocument(String text) { this.sourceDocument = text; }
    public String getOutputDocument() { return outputDocument; }
    public void setOutputDocument(String text) { this.outputDocument = text; }

    public String getMyText() { return myText; }
    public void setMyText(String myText) { this.myText = myText; }
    public String getMyBigText() { return myBigText; }
    public void setMyBigText(String myBigText) { this.myBigText = myBigText; }

    public Boolean getFirstCheck() { return firstCheck; }
    public void setFirstCheck(Boolean firstCheck) { this.firstCheck = firstCheck; }
    public Boolean getSecondCheck() { return secondCheck; }
    public void setSecondCheck(Boolean secondCheck) { this.secondCheck = secondCheck; }
    public Boolean getThirdCheck() { return thirdCheck; }
    public void setThirdCheck(Boolean thirdCheck) { this.thirdCheck = thirdCheck; }

    public boolean isFirstRadio() { return radioSelection == 1; }
    public void setFirstRadio() { radioSelection =  1; }
    public boolean isSecondRadio() { return radioSelection == 2; }
    public void setSecondRadio() { radioSelection =  2; }
    public boolean isThirdRadio() { return radioSelection == 3; }
    public void setThirdRadio() { radioSelection =  3; }

    public String getMonth() { return month; }
    public void setMonth(String value) { this.month = value; }
    public String getBestDay() { return bestDay; }
    public void setBestDay(String value) { this.bestDay = value; }
    public Color getMyColour() { return Color.color(red, green, blue); }
    public void setMyColour(Color colour) {
        red = colour.getRed();
        green = colour.getGreen();
        blue = colour.getBlue();
    }

    public Integer getMyInteger() { return myInteger; }
    public void setMyInteger(Integer myInteger) { this.myInteger = myInteger; }
    public Double getMyDouble() { return myDouble; }
    public void setMyDouble(Double myDouble) { this.myDouble = myDouble; }
    public String getDay() { return day; }
    public void setDay(String day) { this.day = day; }



    /************************************************************************
     * Support code for debug.
     */

     /**
      * Print data store on the command line.
      */
      public void dump() {
        System.out.println("sourceDocument = " + sourceDocument);
        System.out.println("outputDocument = " + outputDocument);
        System.out.println("myText = " + myText);
        System.out.println("myBigText = " + myBigText);
        System.out.println("firstCheck = " + firstCheck);
        System.out.println("secondCheck = " + secondCheck);
        System.out.println("thirdCheck = " + thirdCheck);
        System.out.println("radioSelection = " + radioSelection);
        System.out.println("month = " + month);
        System.out.println("bestDay = " + bestDay);
        System.out.println("Colour = RGB(" + red + ", " + green + ", " + blue + ")");
        System.out.println("myInteger = " + myInteger);
        System.out.println("myDouble = " + myDouble);
        System.out.println("day = " + day);
    }



    /************************************************************************
     * Support code for static public interface.
     */

    /**
     * Writes populated DataStore to disc.
     * @param settingsFile path of the settings data file.
     * @return true if data successfully written to disc, false otherwise.
     */
    public boolean writeData(String settingsFile) {
        boolean success = false;

        ObjectOutputStream objectOutputStream;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(settingsFile));

            objectOutputStream.writeObject(this);
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return success;
    }


    /**
     * Static method that instantiates a DataStore, populates it from disc 
     * and returns it.
     * @param settingsFile path of the settings data file.
     * @return a populated DataStore if data successfully read from disc, null otherwise.
     */
    public static DataStore readData(String settingsFile) {
        DataStore dataStore = null;

        ObjectInputStream objectInputStream;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(settingsFile));

            dataStore = (DataStore)objectInputStream.readObject();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return dataStore;
    }


}

