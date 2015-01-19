package uk.co.addhop.mapeditor.models;

import uk.co.addhop.mapeditor.command.Command;

import java.util.Stack;

/**
 * @stereotype control
 */
public class EventList {
    private Stack<Command> undoList = new Stack<Command>();
    private Stack<Command> redoList = new Stack<Command>();

    private static EventList instance;

    public static EventList getInstance() {
        if (instance == null) {
            instance = new EventList();
        }
        return instance;
    }

    public void push(Command command) {
        undoList.push(command);
        redoList.clear();
    }

    public Command undo() {

        if (!undoList.isEmpty()) {
            final Command command = undoList.pop();
            command.undo();
            redoList.push(command);

            return command;
        }

        return null;
    }

    public Command redo() {

        if (!redoList.isEmpty()) {
            final Command command = redoList.pop();
            command.execute();
            undoList.push(command);
            return command;
        }

        return null;
    }
}
