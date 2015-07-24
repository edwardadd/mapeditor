package uk.co.addhop.mapeditor.models;

import junit.framework.Assert;
import junit.framework.TestCase;

public class MapTest extends TestCase {

    private Map map;

    public void setUp() throws Exception {
        super.setUp();

        final TileTypeDatabase database = new TileTypeDatabase();

        map = new Map();
        map.createMap("@World", 10, 10, 8, 8);
        map.setDatabase(database);
    }

    public void tearDown() throws Exception {
        map = null;
    }

    public void testInitialize() throws Exception {
        map.initialize();

        Assert.assertNotNull(map);
    }

    public void testMapConstructorLoadTileSet() throws Exception {
        final Map newMap = Map.LoadMap("fake");
        Assert.assertNull(newMap);
    }

    public void testLoadTileSet() throws Exception {
        try {
            map.loadTileSet("fake");

            Assert.assertTrue(false);
        } catch (Exception ex) {
            Assert.assertTrue(true);
        }
    }

    public void testSaveTileSet() throws Exception {

    }

    public void testCreateMap() throws Exception {

    }

    public void testGetMapWidth() throws Exception {

    }

    public void testGetMapHeight() throws Exception {

    }

    public void testGetTileWidth() throws Exception {

    }

    public void testGetTileHeight() throws Exception {

    }

    public void testGetMapName() throws Exception {

    }

    public void testGetFileName() throws Exception {

    }

    public void testGetMapTiles() throws Exception {

    }

    public void testGetDatabase() throws Exception {

    }

    public void testSetDatabase() throws Exception {

    }

    public void testGetTile() throws Exception {

    }

    public void testTileChanged() throws Exception {

    }
}