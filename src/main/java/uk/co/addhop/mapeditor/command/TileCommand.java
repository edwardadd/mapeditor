/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.addhop.mapeditor.command;

import uk.co.addhop.mapeditor.models.Tile;

/**
 * @author mr edward addley
 */
public class TileCommand extends Command {

    private Tile original;
    private Tile newTile;

    private String tileSheetName;
    private int tileSheetIndex;

    @Override
    public void undo() {

    }

    @Override
    public void redo() {
    }
}
