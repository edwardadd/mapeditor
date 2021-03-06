package uk.co.addhop.mapeditor.command;

import uk.co.addhop.mapeditor.models.Map;
import uk.co.addhop.mapeditor.models.Tile;

/**
 * FillCommand
 * <p/>
 * Created by edwardaddley on 19/01/15.
 */
public class FillCommand implements Command {
    private static final int MAX_STACK = 2048;
    private Map model;
    private Tile tile;
    private String tileSheetName;
    private int tileSheetCellIndex;

    public FillCommand(final Map model, final Tile tile, final String tileSheetName, final int tileSheetCellIndex) {
        this.model = model;
        this.tile = tile;
        this.tileSheetName = tileSheetName;
        this.tileSheetCellIndex = tileSheetCellIndex;
    }

    public boolean execute() {
        fill(tile.getXPosition(), tile.getYPosition(), tile.getTileSheet(), tile.getTileSetIndex(), tileSheetName, tileSheetCellIndex, model.getMapWidth(), model.getMapHeight());
        return true;
    }

    private void fill(int x, int y, String originalTileSheet, int originalTileSheetCellIndex, String tileSheetName, int tileSheetCellIndex, int mapWidth, int mapHeight) {

        int start = 0, x1, x2, dy;

        Segment[] stack = new Segment[MAX_STACK];
        for (int i = 0; i < MAX_STACK; i++) {
            stack[i] = new Segment(0, 0, 0, 0);
        }
        int stackIndex = 0;

        if ((originalTileSheet.equals(tileSheetName) && originalTileSheetCellIndex == tileSheetCellIndex) || x < 0 || x > mapWidth || y < 0 || y > mapHeight) {
            return;
        }

        // Push
        if (y + 1 >= 0 && y + 1 < mapHeight) {
            stack[stackIndex++].set(y, x, x, 1);

        }

        // Push
        if (y >= 0 && y < mapHeight) {
            stack[stackIndex++].set(y + 1, x, x, -1);
        }

        while (stackIndex > 0) {

            // Pop
            stackIndex--;
            y = stack[stackIndex].y + stack[stackIndex].dy;
            dy = stack[stackIndex].dy;
            x1 = stack[stackIndex].xl;
            x2 = stack[stackIndex].xr;

            for (x = x1; x >= 0; ) {
                final Tile tile = model.getTile(x, y);
                if (tile.getTileSheet().equals(originalTileSheet) && tile.getTileSetIndex() == originalTileSheetCellIndex) {
                    tile.setTileSheet(tileSheetName);
                    tile.setTileSetIndex(tileSheetCellIndex);
                    x--;
                } else {
                    break;
                }
            }

            boolean skip = false;

            if (x >= x1) {
                skip = true;
            }

            if (!skip) {
                start = x + 1;

                if (start < x1) {
                    // Push
                    if (y + -dy >= 0 && y + -dy < mapHeight) {
                        stack[stackIndex++].set(y, start, x1 - 1, -dy);
                    }
                }

                x = x1 + 1;
            }

            do {
                if (!skip) {
                    for (; x < mapWidth; ) {
                        final Tile tile = model.getTile(x, y);
                        if (tile.getTileSheet().equals(originalTileSheet) && tile.getTileSetIndex() == originalTileSheetCellIndex) {
                            tile.setTileSheet(tileSheetName);
                            tile.setTileSetIndex(tileSheetCellIndex);
                            x++;
                        } else {
                            break;
                        }
                    }

                    // Push
                    if (y + dy >= 0 && y + dy < mapHeight) {
                        stack[stackIndex++].set(y, start, x - 1, dy);
                    }

                    if (x > x2 + 1) {
                        // Push
                        if (y + -dy >= 0 && y + -dy < mapHeight) {
                            stack[stackIndex++].set(y, x2 + 1, x - 1, -dy);
                        }
                    }
                }

                skip = false;

                for (x++; x <= x2; ) {
                    final Tile tile = model.getTile(x, y);
                    if (!(tile.getTileSheet().equals(originalTileSheet) && tile.getTileSetIndex() == originalTileSheetCellIndex)) {
                        x++;
                    } else {
                        break;
                    }
                }

                start = x;
            } while (x <= x2);
        }
    }

    private static class Segment {

        Segment(int y, int xl, int xr, int dy) {
            set(y, xl, xr, dy);
        }

        void set(int y, int xl, int xr, int dy) {
            this.y = y;
            this.xl = xl;
            this.xr = xr;
            this.dy = dy;
        }

        int y;
        int xl;
        int xr;
        int dy;
    }

    public boolean undo() {
        return false;
    }
}
