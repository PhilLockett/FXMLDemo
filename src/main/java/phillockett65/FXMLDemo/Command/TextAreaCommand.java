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
 * TextAreaCommand is a class that captures the setShowGuide Command.
 */
package phillockett65.FXMLDemo.Command;

import phillockett65.Debug.Debug;
import phillockett65.FXMLDemo.Model;

public class TextAreaCommand implements Command {

    // Debug delta used to adjust the local logging level.
    private static final int DD = 0;

    private final String className = "TextAreaCommand";
    private final String originalValue;
    private String newValue;

    public TextAreaCommand(String value) {
        originalValue = Model.getInstance().getMyBigText();
        newValue = value;
    }

    @Override
    public void execute() {
        Model.getInstance().setMyBigText(newValue);
    }

    @Override
    public void undo() {
        Debug.trace(DD, "undo " + className);
        Model model = Model.getInstance();

        model.setMyBigText(originalValue);
        model.syncUI();
    }

    @Override
    public void redo() {
        Debug.trace(DD, "redo " + className);
        execute();
        Model.getInstance().syncUI();
    }

    @Override
    public boolean update(Command newCommand) {
        final String name = newCommand.getClass().getSimpleName();
        if (name.compareTo(className) != 0) {
            return false;
        }

        TextAreaCommand command = (TextAreaCommand)newCommand;
        newValue = command.newValue;

        return true;
    }

    @Override
    public boolean isChanging() {
        return newValue.compareTo(originalValue) != 0;
    }

    @Override
    public boolean isReverting(Command newCommand) {
        final String name = newCommand.getClass().getSimpleName();
        if (name.compareTo(className) != 0) {
            return false;
        }

        TextAreaCommand command = (TextAreaCommand)newCommand;
        if (newValue.compareTo(command.originalValue) != 0) {
            return false;
        }
        if (originalValue.compareTo(command.newValue) != 0) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "[" + className + "| " + originalValue + " -> " + newValue + "]";
    }
}

