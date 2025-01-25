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
 * BestDayCommand is a class that captures the setShowGuide Command.
 */
package phillockett65.FXMLDemo.Command;

import phillockett65.Debug.Debug;
import phillockett65.FXMLDemo.Model;

public class BestDayCommand implements Command {

    private final String className = "BestDayCommand";
    private final String originalValue;
    private String newValue;

    public BestDayCommand(String value) {
        originalValue = Model.getInstance().getBestDay();
        newValue = value;
    }

    @Override
    public void execute() {
        Model.getInstance().setBestDay(newValue);
    }

    @Override
    public void undo() {
        Debug.info("undo " + className);
        Model model = Model.getInstance();

        model.setBestDay(originalValue);
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

        BestDayCommand command = (BestDayCommand)newCommand;
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

        BestDayCommand command = (BestDayCommand)newCommand;
        if (newValue != command.originalValue) {
            return false;
        }
        if (originalValue != command.newValue) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "[" + className + "| " + originalValue + " -> " + newValue + "]";
    }
}

