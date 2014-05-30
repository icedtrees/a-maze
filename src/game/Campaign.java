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
    
    public enum CampaignType {
        SINGLEPLAYER,
        MULTIPLAYER
    }
    
    public static final int SINGLEPLAYER_DEFAULT_VISION = 4; 
    public static final int MULTIPLAYER_DEFAULT_VISION = 4; 
    private static final CampaignLevel[] singlePlayerLevels = new CampaignLevel[]
    {
        new CampaignLevel("Hello World!", new MazeSettings(false, true, 5, 8, 10, 40, true, -1, MazeSettings.NO_MODIFICATIONS)),
        new CampaignLevel("Easy as pie", new MazeSettings(false, true, 7, 8, 7, 40, true, -1, MazeSettings.NO_MODIFICATIONS)),
        new CampaignLevel("What's that ticking sound?", new MazeSettings(false, true, 9, 8, 5, 20, true, -1, Arrays.asList(new Modification[] {
                new ClockMod(3)
        }))),
        new CampaignLevel("Time is tight", new MazeSettings(false, true, 11, 10, 5, 20, true, -1, Arrays.asList(new Modification[] {
                new ClockMod(5)
        }))),
        new CampaignLevel("Run, run, run", new MazeSettings(false, true, 13, 10, 3, 30, true, -1, Arrays.asList(new Modification[] {
                new SpeedMod(6),
                new ClockMod(3)
        }))),
        new CampaignLevel("Lonely cat", new MazeSettings(false, false, 15, 10, 0, 30, false, -1, Arrays.asList(new Modification[] {
                new SpeedMod(5),
                new ClockMod(5)
        }))),
        new CampaignLevel("iseedeadpeople", new MazeSettings(false, true, 17, 10, 0, 40, false, -1, Arrays.asList(new Modification[] {
                new SpeedMod(8),
                new ClockMod(7),
                new FogMod(SINGLEPLAYER_DEFAULT_VISION, 10)
        }))),
        new CampaignLevel("Fading footsteps", new MazeSettings(false, false, 17, 10, 0, 60, false, -1, Arrays.asList(new Modification[] {
                new SpeedMod(10),
                new ClockMod(8),
                new FogMod(SINGLEPLAYER_DEFAULT_VISION, 10)
        }))),
        new CampaignLevel("HOGWARTS", new MazeSettings(false, false, 20, 10, 0, 60, false, -1, Arrays.asList(new Modification[] {
                new SpeedMod(12),
                new ClockMod(12),
                new ShiftingWallsMod(8, 10),
        }))),
        new CampaignLevel("Final Destination", new MazeSettings(false, false, 25, 10, -5, 120, false, -1, Arrays.asList(new Modification[] {
                new SpeedMod(8),
                new ClockMod(14),
                new FogMod(SINGLEPLAYER_DEFAULT_VISION, 8),
                new ShiftingWallsMod(8, 10)
        })))
    };
    public static final int SINGLEPLAYER_NUM_LEVELS = singlePlayerLevels.length;
 
    private static final CampaignLevel[] multiPlayerLevels = new CampaignLevel[]
    {
        new CampaignLevel("Hello World!", new MazeSettings(true, true, 5, 8, 5, 40, true, -1, MazeSettings.NO_MODIFICATIONS)),
        new CampaignLevel("Easy as pie", new MazeSettings(true, true, 7, 8, 7, 40, true, -1, MazeSettings.NO_MODIFICATIONS)),
        new CampaignLevel("What's that ticking sound?", new MazeSettings(true, true, 9, 8, 5, 20, true, -1, Arrays.asList(new Modification[] {
                new ClockMod(3)
        }))),
        new CampaignLevel("Time is tight", new MazeSettings(true, true, 11, 10, 5, 20, true, -1, Arrays.asList(new Modification[] {
                new ClockMod(5)
        }))),
        new CampaignLevel("Run, run, run", new MazeSettings(true, true, 13, 10, 3, 30, true, -1, Arrays.asList(new Modification[] {
                new SpeedMod(6),
                new ClockMod(3)
        }))),
        new CampaignLevel("Two lonely cats", new MazeSettings(true, false, 15, 10, 0, 30, false, -1, Arrays.asList(new Modification[] {
                new SpeedMod(5),
                new ClockMod(5)
        }))),
        new CampaignLevel("iseedeadpeople", new MazeSettings(true, true, 17, 10, 0, 40, false, -1, Arrays.asList(new Modification[] {
                new SpeedMod(8),
                new ClockMod(7),
                new FogMod(SINGLEPLAYER_DEFAULT_VISION, 10)
        }))),
        new CampaignLevel("Fading footsteps", new MazeSettings(true, false, 17, 10, 0, 60, false, -1, Arrays.asList(new Modification[] {
                new SpeedMod(10),
                new ClockMod(8),
                new FogMod(SINGLEPLAYER_DEFAULT_VISION, 10)
        }))),
        new CampaignLevel("HOGWARTS", new MazeSettings(true, false, 20, 10, 0, 60, false, -1, Arrays.asList(new Modification[] {
                new SpeedMod(12),
                new ClockMod(12),
                new ShiftingWallsMod(8, 10),
        }))),
        new CampaignLevel("Final Destination", new MazeSettings(true, false, 25, 10, -5, 120, false, -1, Arrays.asList(new Modification[] {
                new SpeedMod(8),
                new ClockMod(14),
                new FogMod(SINGLEPLAYER_DEFAULT_VISION, 8),
                new ShiftingWallsMod(8, 10)
        })))
    };
    public static final int MULTIPLAYER_NUM_LEVELS = multiPlayerLevels.length;
    
    private int currentLevel;
    private CampaignLevel[] campaignLevels;
    
    public Campaign(CampaignType campaignType) {
        currentLevel = 0;
        if (campaignType.equals(CampaignType.SINGLEPLAYER)) {
            this.campaignLevels = singlePlayerLevels;
        } else if (campaignType.equals(CampaignType.MULTIPLAYER)) {
            this.campaignLevels = multiPlayerLevels;
        }
    }
    
    public String getLevelName() {
        return campaignLevels[currentLevel].levelName;
    }
    
    public MazeSettings getLevelSettings() {
        return campaignLevels[currentLevel].levelSettings;
    }

    /**
     * Levels start at 1
     * @return
     */
    public int getCurrentLevel() {
        // We need to add one so that the return value counts from 1 upwards.
        return currentLevel + 1;
    }
    
    public void advance() {
        if (currentLevel >= campaignLevels.length) {
            throw new IllegalStateException("Tried to advance beyond the maximum level");
        }
        currentLevel ++;
    }
}
