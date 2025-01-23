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
 * DataStore is a class that serializes the settings data for saving and 
 * restoring to and from disc.
 */
package phillockett65.FXMLDemo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;

import javafx.scene.paint.Color;

public class DataStore1 extends DataStore {
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
     * Support code for the Initialization, getters and setters of DataStore1.
     */

    public DataStore1() {
        super();
    }


    public Color getMyColour() { return Color.color(red, green, blue); }
    public void setMyColour(Color colour) {
        red = colour.getRed();
        green = colour.getGreen();
        blue = colour.getBlue();
    }



    /**
     * Data exchange from the model to this DataStore.
     * @return true if data successfully pulled from the model, false otherwise.
     */
    private boolean pull() {
        boolean success = true;
        Model model = Model.getInstance();

        mainX = model.getStage().getX();
        mainY = model.getStage().getY();

        sourceDocument = model.getSourceFilePath();
        outputDocument = model.getOutputFilePath();

        myText = model.getMyText();
        myBigText = model.getMyBigText();

        firstCheck = model.isFirstCheck();
        secondCheck = model.isSecondCheck();
        thirdCheck = model.isThirdCheck();

        if (model.isFirstRadio()) {
            radioSelection =  1;
        }
        else
        if (model.isSecondRadio()) {
            radioSelection =  2;
        }
        else
        if (model.isThirdRadio()) {
            radioSelection =  3;
        }

        month = model.getMonth();
        bestDay = model.getBestDay();
        setMyColour(model.getMyColour());

        myInteger = model.getInteger();
        myDouble = model.getDouble();
        day = model.getDay();

        return success;
    }

    /**
     * Data exchange from this DataStore to the model.
     * @return true if data successfully pushed to the model, false otherwise.
     */
    private boolean push() {
        boolean success = true;
        Model model = Model.getInstance();

        model.getStage().setX(mainX);
        model.getStage().setY(mainY);

        model.setSourceFilePath(sourceDocument);
        model.setOutputFilePath(outputDocument);

        model.setMyText(myText);
        model.setMyBigText(myBigText);

        model.setFirstCheck(firstCheck);
        model.setSecondCheck(secondCheck);
        model.setThirdCheck(thirdCheck);

        if (radioSelection == 1) {
            model.setFirstRadio();
        }
        else
        if (radioSelection == 2) {
            model.setSecondRadio();
        }
        else
        if (radioSelection == 3) {
            model.setThirdRadio();
        }

        model.setMonth(month);
        model.setBestDay(bestDay);
        model.setMyColour(getMyColour());
    
        model.setInteger(myInteger);
        model.setDouble(myDouble);
        model.setDay(day);

        return success;
    }



    /************************************************************************
     * Support code for static public interface.
     */

    /**
     * Static method that instantiates a DataStore, populates it from the 
     * model and writes it to disc.
     * @return true if data successfully written to disc, false otherwise.
     */
    public static boolean writeData() {
        boolean success = false;

        DataStore1 store = new DataStore1();
        store.pull();
        store.dump();

        ObjectOutputStream objectOutputStream;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(Model.DATAFILE));

            objectOutputStream.writeObject(store);
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
            Debug.critical(e.getMessage());
        }

        return success;
    }

    /**
     * Static method that instantiates a DataStore, populates it from disc 
     * and writes it to the model.
     * @return true if data successfully read from disc, false otherwise.
     */
    public static boolean readData() {
        boolean success = false;

        ObjectInputStream objectInputStream;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(Model.DATAFILE));

            DataStore base = (DataStore)objectInputStream.readObject();
            long SVUID = ObjectStreamClass.lookup(base.getClass()).getSerialVersionUID();
 
            DataStore1 store = null;
            if (SVUID == 1) {
                store = (DataStore1)base;
                success = store.push();
                store.dump();
            }

        } catch (IOException e) {
            Debug.critical(e.getMessage());
        } catch (ClassNotFoundException e) {
            Debug.critical(e.getMessage());
        }

        return success;
    }



    /************************************************************************
     * Support code for debug.
     */

     /**
      * Print data store on the command line.
      */
      public void dump() {
        Debug.info("sourceDocument = " + sourceDocument);
        Debug.info("outputDocument = " + outputDocument);
        Debug.info("myText = " + myText);
        Debug.info("myBigText = " + myBigText);
        Debug.info("firstCheck = " + firstCheck);
        Debug.info("secondCheck = " + secondCheck);
        Debug.info("thirdCheck = " + thirdCheck);
        Debug.info("radioSelection = " + radioSelection);
        Debug.info("month = " + month);
        Debug.info("bestDay = " + bestDay);
        Debug.info("Colour = RGB(" + red + ", " + green + ", " + blue + ")");
        Debug.info("myInteger = " + myInteger);
        Debug.info("myDouble = " + myDouble);
        Debug.info("day = " + day);
    }


}

