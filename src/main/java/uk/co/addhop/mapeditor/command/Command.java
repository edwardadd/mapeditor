/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.addhop.mapeditor.command;

/**
 * @author mr edward addley
 */
public interface Command {
    public boolean execute();

    public boolean undo();
}
