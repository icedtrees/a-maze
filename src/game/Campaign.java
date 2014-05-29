package game;

import java.util.ArrayList;
import java.util.List;

import maze.MazeSettings;

/**
 * @author icedtrees
 *
 */
public class Campaign {
    private static class CampaignLevel {
        private final String levelName;
        private final MazeSettings levelSettings;
        
        private CampaignLevel(String levelName, MazeSettings levelSettings) {
            this.levelName = levelName;
            this.levelSettings = levelSettings;
        }
    }
    
    private int currentLevel;
    private static final CampaignLevel[] levels = 
    {
        new CampaignLevel("Hello World!", new MazeSettings(false, 5, 8, 10, 40, MazeSettings.NO_MODIFICATIONS)),
        new CampaignLevel("Hello World!", new MazeSettings(false, 5, 8, 10, 40, MazeSettings.NO_MODIFICATIONS)),
        new CampaignLevel("Hello World!", new MazeSettings(false, 5, 8, 10, 40, MazeSettings.NO_MODIFICATIONS)),
        new CampaignLevel("Hello World!", new MazeSettings(false, 5, 8, 10, 40, MazeSettings.NO_MODIFICATIONS)),
        new CampaignLevel("Hello World!", new MazeSettings(false, 5, 8, 10, 40, MazeSettings.NO_MODIFICATIONS)),
        new CampaignLevel("Hello World!", new MazeSettings(false, 5, 8, 10, 40, MazeSettings.NO_MODIFICATIONS)),
        new CampaignLevel("Hello World!", new MazeSettings(false, 5, 8, 10, 40, MazeSettings.NO_MODIFICATIONS)),
        new CampaignLevel("Hello World!", new MazeSettings(false, 5, 8, 10, 40, MazeSettings.NO_MODIFICATIONS)),
        new CampaignLevel("Hello World!", new MazeSettings(false, 5, 8, 10, 40, MazeSettings.NO_MODIFICATIONS)),
        new CampaignLevel("Hello World!", new MazeSettings(false, 5, 8, 10, 40, MazeSettings.NO_MODIFICATIONS)),
        new CampaignLevel("Hello World!", new MazeSettings(false, 5, 8, 10, 40, MazeSettings.NO_MODIFICATIONS)),
    };
    
    public Campaign() {
        currentLevel = 0;
    }
    
    public String getLevelName() {
        return levels[currentLevel].levelName;
    }
    
    public MazeSettings getLevelSettings() {
        return levels[currentLevel].levelSettings;
    }
    
    public int getNumLevels() {
        return levels.length;
    }
    
    public void advance() {
        if (currentLevel >= getNumLevels()) {
            throw new IllegalStateException("Tried to advance beyond the maximum level");
        }
        currentLevel ++;
    }
}
