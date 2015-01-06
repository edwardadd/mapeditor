/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.addhop.mapeditor.command;

/**
 * @author mr edward addley
 */
public abstract class Command {
    public String name;

    public abstract void undo();

    public abstract void redo();
}
