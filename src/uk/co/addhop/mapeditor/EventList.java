package uk.co.addhop.mapeditor;

import java.util.Stack;

/**
 * @stereotype control
 */
public class EventList {
    private Stack undoList;
    private Stack redoList;

    public void undo() {
        // message #1.1.1 to undoList:java.util.Stack
        if (undoList.peek() != null) {
            // message #1.1.2 to undoList:java.util.Stack
            // ActionEvent temp = undoList.pop();
            // message #1.1.3 to redoList:java.util.Stack
            //temp = redoList.push(temp);
        }
    }

    public void addEvent(Command command) {
        undoList.push(command);
    }
}
