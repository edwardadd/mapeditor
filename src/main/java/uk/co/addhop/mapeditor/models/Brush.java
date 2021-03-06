package uk.co.addhop.mapeditor.models;

import java.util.Observable;

/**
 * Brush
 * <p/>
 * Created by edwardaddley on 10/01/15.
 */
public class Brush extends Observable {

    private Tile selectedTile;
    private BrushType brushType = BrushType.PAINT;

    private String tileSheetName = "Default";
    private int tileSheetCellIndex = 0;

    public enum BrushType {
        PAINT,
        FILL,
        SELECT,
        CLEAR
    }

    public Brush() {

    }

    public Tile getSelectedTile() {
        return selectedTile;
    }

    public void setSelectedTile(Tile selectedTile) {
        this.selectedTile = selectedTile;

        setChanged();
    }

    public BrushType getBrushType() {
        return brushType;
    }

    public void setBrushType(BrushType brushType) {
        this.brushType = brushType;

        setChanged();
    }

    public String getTileSheetName() {
        return tileSheetName;
    }

    public void setTileSheetName(String tileSheetName) {
        this.tileSheetName = tileSheetName;

        setChanged();
    }

    public int getTileSheetCellIndex() {
        return tileSheetCellIndex;
    }

    public void setTileSheetCellIndex(int tileSheetCellIndex) {
        this.tileSheetCellIndex = tileSheetCellIndex;

        setChanged();
    }
}
