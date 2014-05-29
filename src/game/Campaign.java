package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import maze.MazeSettings;
import maze.modification.Modification;

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
    private static final CampaignLevel[] levels = new CampaignLevel[]
    {
        new CampaignLevel("Hello World!", new MazeSettings(false, 5, 8, 10, 40, Arrays.asList(new Modification[] {
                
        }))),
        new CampaignLevel("Easy as pie", new MazeSettings(false, 7, 8, 7, 40, MazeSettings.NO_MODIFICATIONS)),
        new CampaignLevel("What's that ticking sound?", new MazeSettings(false, 9, 8, 5, 20, Arrays.asList(new Modification[] {
                
        }))),
        new CampaignLevel("Time is tight", new MazeSettings(false, 11, 10, 5, 20, Arrays.asList(new Modification[] {
                
        }))),
        new CampaignLevel("Run, run, run", new MazeSettings(false, 13, 10, 3, 30, Arrays.asList(new Modification[] {
                
        }))),
        new CampaignLevel("Training wheels off", new MazeSettings(false, 15, 10, 0, 30, Arrays.asList(new Modification[] {
                
        }))),
        new CampaignLevel("iseedeadpeople", new MazeSettings(false, 17, 10, 0, 60, Arrays.asList(new Modification[] {
                
        }))),
        new CampaignLevel("Fading footsteps", new MazeSettings(false, 17, 10, 0, 60, Arrays.asList(new Modification[] {
                
        }))),
        new CampaignLevel("HOGWARTS", new MazeSettings(false, 20, 10, 0, 120, Arrays.asList(new Modification[] {
                
        }))),
        new CampaignLevel("Final Destination", new MazeSettings(false, 25, 10, -5, 180, Arrays.asList(new Modification[] {
                
        })))
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
