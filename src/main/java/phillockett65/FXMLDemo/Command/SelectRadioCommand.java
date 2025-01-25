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
 * SelectRadioCommand is a class that captures the setShowGuide Command.
 */
package phillockett65.FXMLDemo.Command;

import phillockett65.Debug.Debug;
import phillockett65.FXMLDemo.Model;

public class SelectRadioCommand implements Command {

    private final String className = "SelectRadioCommand";
    private final int originalIndex;
    private int newIndex;

    public SelectRadioCommand(int index) {
        Model model = Model.getInstance();
        if (model.isFirstRadio()) {
            originalIndex =  1;
        }
        else
        if (model.isSecondRadio()) {
            originalIndex =  2;
        }
        else
        if (model.isThirdRadio()) {
            originalIndex =  3;
        }
        else {
            originalIndex =  0;
        }

        newIndex = index;
    }

    private void setRadio(int index) {
        Model model = Model.getInstance();

        switch (index) {
        case 1:
            model.setFirstRadio();
            model.getController().setStatusMessage("First radio button selected.");
            break;

        case 2:
            model.setSecondRadio();
            model.getController().setStatusMessage("Second radio button selected.");
            break;

        case 3:
            model.setThirdRadio();
            model.getController().setStatusMessage("Third radio button selected.");
            break;
        }
        model.syncUI();
    }

    @Override
    public void execute() {
        setRadio(newIndex);
    }

    @Override
    public void undo() {
        Debug.info("undo " + className);
        setRadio(originalIndex);
    }

    @Override
    public void redo() {
        Debug.info("redo " + className);
        execute();
    }

    @Override
    public boolean update(Command newCommand) {
        final String name = newCommand.getClass().getSimpleName();
        if (name.compareTo(className) != 0) {
            return false;
        }

        SelectRadioCommand command = (SelectRadioCommand)newCommand;
        newIndex = command.newIndex;

        return true;
    }

    @Override
    public boolean isChanging() {
        return newIndex != originalIndex;
    }

    @Override
    public boolean isReverting(Command newCommand) {
        final String name = newCommand.getClass().getSimpleName();
        if (name.compareTo(className) != 0) {
            return false;
        }

        SelectRadioCommand command = (SelectRadioCommand)newCommand;
        if (newIndex != command.originalIndex) {
            return false;
        }
        if (originalIndex != command.newIndex) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "[" + className + "| " + originalIndex + " -> " + newIndex + "]";
    }
}

