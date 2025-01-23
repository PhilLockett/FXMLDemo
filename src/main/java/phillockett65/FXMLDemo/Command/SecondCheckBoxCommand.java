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
 * SecondCheckBoxCommand is a class that captures the setShowGuide Command.
 */
package phillockett65.FXMLDemo.Command;

import phillockett65.FXMLDemo.Debug;
import phillockett65.FXMLDemo.Model;

public class SecondCheckBoxCommand implements Command {

    private final String className = "SecondCheckBoxCommand";
    private final boolean originalState;
    private boolean newState;

    public SecondCheckBoxCommand(boolean state) {
        originalState = Model.getInstance().isSecondCheck();
        newState = state;
    }

    @Override
    public void execute() {
        Model model = Model.getInstance();

        model.setSecondCheck(newState);
        final String state = newState ? "" : "un";
        model.setStatusMessage("Second check box " + state + "selected.");
    }

    @Override
    public void undo() {
        Debug.info("undo " + className);
        Model model = Model.getInstance();

        model.setSecondCheck(originalState);
        final String state = originalState ? "" : "un";
        model.setStatusMessage("Second check box " + state + "selected.");
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

        SecondCheckBoxCommand command = (SecondCheckBoxCommand)newCommand;
        newState = command.newState;

        return true;
    }

    @Override
    public boolean isChanging() {
        return newState != originalState;
    }

    @Override
    public boolean isReverting(Command newCommand) {
        final String name = newCommand.getClass().getSimpleName();
        if (name.compareTo(className) != 0) {
            return false;
        }

        SecondCheckBoxCommand command = (SecondCheckBoxCommand)newCommand;
        if (newState != command.originalState) {
            return false;
        }
        if (originalState != command.newState) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "[" + className + "| " + originalState + " -> " + newState + "]";
    }
}

