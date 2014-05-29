package game;

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
        new CampaignLevel("Hello World!", new MazeSettings(false, true, 5, 8, 10, 40, MazeSettings.NO_MODIFICATIONS, -1)),
        new CampaignLevel("Easy as ABC", new MazeSettings(false, true, 7, 8, 7, 40, MazeSettings.NO_MODIFICATIONS, -1)),
        new CampaignLevel("Mysterious ticking noise", new MazeSettings(false, true, 9, 8, 5, 20, MazeSettings.NO_MODIFICATIONS, -1)),
        new CampaignLevel("Time is tight", new MazeSettings(false, true, 11, 10, 5, 20, MazeSettings.NO_MODIFICATIONS, -1)),
        new CampaignLevel("Run run run", new MazeSettings(false, true, 13, 10, 3, 30, MazeSettings.NO_MODIFICATIONS, -1)),
        new CampaignLevel("I'm floating?!?", new MazeSettings(false, false, 15, 10, 0, 30, MazeSettings.NO_MODIFICATIONS, -1)),
        new CampaignLevel("iseedeadpeople", new MazeSettings(false, true, 17, 10, 0, 60, MazeSettings.NO_MODIFICATIONS, -1)),
        new CampaignLevel("Fading footsteps", new MazeSettings(false, false, 17, 10, 0, 60, MazeSettings.NO_MODIFICATIONS, -1)),
        new CampaignLevel("Hogwarts", new MazeSettings(false, false, 20, 10, 0, 120, MazeSettings.NO_MODIFICATIONS, -1)),
        new CampaignLevel("Final Destination", new MazeSettings(false, false, 25, 10, -5, 240, MazeSettings.NO_MODIFICATIONS, -1)),
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
