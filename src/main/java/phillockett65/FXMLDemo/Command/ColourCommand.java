/*  Command - a Java based Command pattern implementation.
 *
 *  Copyright 2025 Philip Lockett.
 *
 *  This file is part of Command.
 *
 *  Command is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Command is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Command.  If not, see <https://www.gnu.org/licenses/>.
 */

/*
 * GuideLineColourCommand is a class that captures the setGuideLineColour 
 * Command.
 */
package phillockett65.FXMLDemo.Command;

import javafx.scene.paint.Color;
import phillockett65.FXMLDemo.Debug;
import phillockett65.FXMLDemo.Model;

public class ColourCommand implements Command {

    private final String className = "ColourCommand";
    private final Color originalColour;
    private Color newColour;

    public ColourCommand(Color colour) {
        originalColour = Model.getInstance().getMyColour();
        newColour = colour;
    }

    @Override
    public void execute() {
        Model model = Model.getInstance();

        model.setMyColour(newColour);
    }

    @Override
    public void undo() {
        Debug.info("undo " + className);
        Model model = Model.getInstance();

        model.setMyColour(originalColour);
        model.syncUI();
    }

    @Override
    public void redo() {
        Debug.info("redo " + className);
        execute();
        Model.getInstance().syncUI();
    }

    @Override
    public boolean update(Command newCommand) {
        final String name = newCommand.getClass().getSimpleName();
        if (name.compareTo(className) != 0) {
            return false;
        }

        ColourCommand command = (ColourCommand)newCommand;
        newColour = command.newColour;

        return true;
    }

    @Override
    public boolean isChanging() {
        return newColour.equals(originalColour) == false;
    }

    @Override
    public boolean isReverting(Command newCommand) {
        final String name = newCommand.getClass().getSimpleName();
        if (name.compareTo(className) != 0) {
            return false;
        }

        ColourCommand command = (ColourCommand)newCommand;
        if (newColour.equals(command.originalColour) == false) {
            return false;
        }
        if (originalColour.equals(command.newColour) == false) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "[" + className + "| " + originalColour + " -> " + newColour + "]";
    }
}

