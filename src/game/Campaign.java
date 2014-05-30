package game;

import java.util.Arrays;
import maze.MazeSettings;
import maze.modification.*;

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
    
    public static final int SINGLEPLAYER_DEFAULT_VISION = 4; 
    public static final int MULTIPLAYER_DEFAULT_VISION = 4; 
    
    private int currentLevel;
    private static final CampaignLevel[] levels = new CampaignLevel[]
    {
        new CampaignLevel("Hello World!", new MazeSettings(false, true, 5, 8, 10, 40, -1, MazeSettings.NO_MODIFICATIONS)),
        new CampaignLevel("Easy as pie", new MazeSettings(false, true, 7, 8, 7, 40, -1, MazeSettings.NO_MODIFICATIONS)),
        new CampaignLevel("What's that ticking sound?", new MazeSettings(false, true, 9, 8, 5, 20, -1, Arrays.asList(new Modification[] {
                new ClockMod(3)
        }))),
        new CampaignLevel("Time is tight", new MazeSettings(false, true, 11, 10, 5, 20, -1, Arrays.asList(new Modification[] {
                new ClockMod(3)
        }))),
        new CampaignLevel("Run, run, run", new MazeSettings(false, true, 13, 10, 3, 30, -1, Arrays.asList(new Modification[] {
                new SpeedMod(1),
                new ClockMod(3)
        }))),
        new CampaignLevel("Training wheels off", new MazeSettings(false, false, 15, 10, 0, 30, -1, Arrays.asList(new Modification[] {
                new SpeedMod(2),
                new ClockMod(2)
        }))),
        new CampaignLevel("iseedeadpeople", new MazeSettings(false, true, 17, 10, 0, 60, -1, Arrays.asList(new Modification[] {
                new SpeedMod(2),
                new ClockMod(2),
                new FogMod(SINGLEPLAYER_DEFAULT_VISION, 3)
        }))),
        new CampaignLevel("Fading footsteps", new MazeSettings(false, false, 17, 10, 0, 60, -1, Arrays.asList(new Modification[] {
                new SpeedMod(2),
                new ClockMod(2),
                new FogMod(SINGLEPLAYER_DEFAULT_VISION, 3)
        }))),
        new CampaignLevel("HOGWARTS", new MazeSettings(false, false, 20, 10, 0, 120, -1, Arrays.asList(new Modification[] {
                new SpeedMod(2),
                new ClockMod(2),
                new ShiftingWallsMod(10, 10),
        }))),
        new CampaignLevel("Final Destination", new MazeSettings(false, false, 25, 10, -5, 180, -1, Arrays.asList(new Modification[] {
                new SpeedMod(5),
                new ClockMod(3),
                new FogMod(SINGLEPLAYER_DEFAULT_VISION, 4),
                new ShiftingWallsMod(10, 10)
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
