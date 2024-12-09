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
 * DataStore is the Base class that serializes and persists the settings data 
 * for saving to and restoring from disc.
 */
package phillockett65.FXMLDemo;

import java.io.Serializable;

public class DataStore implements Serializable {
    private static final long serialVersionUID = 0L;

    public DataStore() { }

}

