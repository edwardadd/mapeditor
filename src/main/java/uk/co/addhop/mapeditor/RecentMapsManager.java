package uk.co.addhop.mapeditor;

import java.util.*;
import java.util.prefs.Preferences;

public class RecentMapsManager {
    private static final String PREFS_LAST_OPENED_MAP = "LAST_OPENED_MAP_";
    private static final String PREFS_RECENT_MAP = "RECENT_MAP_";
    private static final int MAX_RECENTLIST_SIZE = 5;

    private Preferences preferences;

    private Deque<String> recentList = new ArrayDeque<>();

    RecentMapsManager() {

        preferences = Preferences.userNodeForPackage(this.getClass());

        updateRecentListFromPrefs();
    }

    private void updateRecentListFromPrefs() {
        for (int i = 0; i < MAX_RECENTLIST_SIZE; i++) {
            final String recentMap = preferences.get(PREFS_RECENT_MAP + i, "empty");
            if (!"empty".equals(recentMap)) {
                recentList.push(recentMap);
            }
        }
    }

    private void saveRecentListToPrefs() {
        for (int i = 0; i < MAX_RECENTLIST_SIZE; i++) {
            preferences.put(PREFS_RECENT_MAP + i, "empty");
        }

        int i = 0;
        for (String recentMap : recentList) {
            preferences.put(PREFS_RECENT_MAP + i, recentMap);
            i++;
        }
    }

    public void addToRecentList(String fileName) {
        for (String other : recentList) {
            if (other.equals(fileName)) {
                recentList.remove(other);
            }
        }

        recentList.push(fileName);

        // TODO Update main menu bars
    }

    public Collection<String> getRecentList() {
        return recentList;
    }

    List<String> recentlyOpenedMapsList() {
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < MainApplication.MAX_OPENED_WINDOWS; i++) {
            final String recentMap = preferences.get(PREFS_LAST_OPENED_MAP + i, "empty");
            if (!"empty".equals(recentMap)) {
                list.add(recentMap);
            }
        }

        return list;
    }
}
