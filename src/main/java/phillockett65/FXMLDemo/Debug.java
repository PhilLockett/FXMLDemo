/*  FXMLDemo - a JavaFX application 'framework' that uses Maven, FXML and CSS.
 *
 *  Copyright 2025 Philip Lockett.
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
 * Debug is the static class that manages debug output.
 */
package phillockett65.FXMLDemo;


public class Debug {

    private static final int NONE = 0;
    private static final int CRITICAL = 1;
    private static final int MAJOR = 2;
    private static final int MINOR = 3;
    private static final int INFO = 4;

    private static final int LEVEL = NONE;



    /************************************************************************
     * General support code.
     */

    private static String pre(int level) {
        switch (level) {
            case CRITICAL:  return "Critical error";
            case MAJOR:     return "Major error";
            case MINOR:     return "Minor error";
            case INFO:      return "Information";
        }

        return "";
    }

    private static void display(int level, String line) {
        if (level <= LEVEL) {
            final String message = pre(level) + ": " + line;
            if (level <= MAJOR)
                System.err.println(message);
            else
                System.out.println(message);
        }
    }



    /************************************************************************
     * Support code for the Initialization of the Model.
     */

    /**
     * Private default constructor - part of the Singleton Design Pattern.
     * Called at initialization only, constructs the single private instance.
     */
    private Debug() {
    }



    /************************************************************************
     * Support code for static public interface.
     */

    public static void critical(String line) { display(CRITICAL, line); }
    public static void major(String line) { display(MAJOR, line); }
    public static void minor(String line) { display(MINOR, line); }
    public static void info(String line) { display(INFO, line); }


}
