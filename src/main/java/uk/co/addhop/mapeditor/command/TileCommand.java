/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.addhop.mapeditor.command;

import uk.co.addhop.mapeditor.models.Map;
import uk.co.addhop.mapeditor.models.Tile;

/**
 * @author mr edward addley
 */
public class TileCommand implements Command {

    private Map map;

    private Tile originalTile;
    private Tile tile;

    private String tileSheetName;
    private int tileSheetIndex;

    public TileCommand(Map model, final Tile tile, final String tileSheetName, final int tileSheetIndex) {
        this.tile = tile;
        this.tileSheetName = tileSheetName;
        this.tileSheetIndex = tileSheetIndex;
        map = model;

        originalTile = tile.createCopy();
    }

    @Override
    public boolean execute() {
        tile.setTileSheet(tileSheetName);
        tile.setTileSetIndex(tileSheetIndex);

        map.tileChanged();
        return true;
    }

    @Override
    public boolean undo() {
        tile.copy(originalTile);

        map.tileChanged();
        return true;
    }
}
