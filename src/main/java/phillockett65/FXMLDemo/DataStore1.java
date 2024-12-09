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
     * @param model contains the data.
     * @return true if data successfully pulled from the model, false otherwise.
     */
    private boolean pull(Model model) {
        boolean success = true;

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
     * @param model contains the data.
     * @return true if data successfully pushed to the model, false otherwise.
     */
    private boolean push(Model model) {
        boolean success = true;

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
        Model model = Model.getInstance();

        DataStore1 store = new DataStore1();
        store.pull(model);
        // store.dump();

        ObjectOutputStream objectOutputStream;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(model.getSettingsFile()));

            objectOutputStream.writeObject(store);
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
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
        Model model = Model.getInstance();

        ObjectInputStream objectInputStream;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(model.getSettingsFile()));

            DataStore base = (DataStore)objectInputStream.readObject();
            long SVUID = ObjectStreamClass.lookup(base.getClass()).getSerialVersionUID();
 
            DataStore1 store = null;
            if (SVUID == 1) {
                store = (DataStore1)base;
                success = store.push(model);
                // store.dump();
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
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


}

