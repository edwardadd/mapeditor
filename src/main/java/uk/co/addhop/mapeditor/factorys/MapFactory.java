package uk.co.addhop.mapeditor.factorys;

import uk.co.addhop.mapeditor.models.CaveMap;
import uk.co.addhop.mapeditor.models.Map;

/**
 * Factory for generating different map types
 *
 * Created by edwardaddley on 17/01/16.
 */
public class MapFactory {
    public static Map generateCave(int width, int height) {
        CaveMap caveMap = new CaveMap(width, height);
        caveMap.initialize();
        return caveMap;
    }
}
