package maze;

import java.awt.image.BufferedImage;

import maze.Direction;

/**
 * This is a class for getting images in an animated sprite
 * (in particular, a MobileObject which has animated movement).
 * @author Andy
 *
 */
public class Animation {
    
    private final int loopSize;
    private Direction currentDirection; // guaranteed to be != null
    private int currentIndex;
    private int totalEast; // the current number of frames stored
    private int totalNorth;
    private int totalWest;
    private int totalSouth;
    
    private BufferedImage[] spriteEast;
    private BufferedImage[] spriteNorth;
    private BufferedImage[] spriteWest;
    private BufferedImage[] spriteSouth;
    
    public Animation(int loopSize, Direction direction) {
        assert(loopSize > 0);
        this.loopSize = loopSize;
        this.currentIndex = 0;
        this.totalEast = 0;
        this.totalNorth = 0;
        this.totalWest = 0;
        this.totalSouth = 0;
        this.spriteEast = new BufferedImage[loopSize];
        this.spriteNorth = new BufferedImage[loopSize];
        this.spriteWest = new BufferedImage[loopSize];
        this.spriteSouth = new BufferedImage[loopSize];
        if (direction == null) {
            this.currentDirection = Direction.SOUTH;
        } else {
            this.currentDirection = direction;
        }
    }
    
//    public Animation(int loopSize) {
//        // TODO fix this shit
//        Direction temp = null;
//        Animation(loopSize, temp);
//    }
    
    /**
     * Adds the next image in the series for a given direction. If they max
     * capacity of images for that direction has been reached however, this
     * method will do nothing.
     * @param image
     * @param direction
     */
    public void setNextImage(BufferedImage image, Direction direction) {
        // TODO complete this function
        if (image == null || direction == null) {
            return;
        }
        BufferedImage[] targetSprite = spriteArray(direction);
        int totalDirection = spriteArraySize(direction);
        if (totalDirection >= loopSize) {
            return;
        }
        // not at max capacity for the direction
        targetSprite[totalDirection] = image; // TODO see if we can copy this
        if (direction == Direction.EAST) {
            totalEast++;
        } else if (direction == Direction.NORTH) {
            totalNorth++;
        } else if (direction == Direction.WEST) {
            totalWest++;
        } else {
            totalSouth++;
        }
    }
    
    /**
     * Returns the next image in the series for the given direction, if
     * previous calls were for the same direction. If this call is for a
     * different direction, it is instead equivalent to
     * <code>getFirstImage(direction)</code>. 
     * @param direction the direction the sprite is traveling in
     */
    public BufferedImage getNextImage(Direction direction) {
        // TODO return the next image in the series
        if (direction == null) {
            currentIndex = 0;
            return getFirstImage(currentDirection);
        }
        if (currentDirection != direction) {
            currentIndex = 0;
            currentDirection = direction;
        } else {
            currentIndex = (currentIndex + 1) % spriteArraySize(direction);
        }
        BufferedImage[] array = spriteArray(direction);
        currentDirection = direction;
        return array[currentIndex];
    }
    
    /**
     * Gets the n-th frame for a particular direction (modulo the number of
     * frames currently stored for that direction).
     * @param frame the frame in question
     */
    public BufferedImage getFrame(Direction direction, double frame) {
        if (direction == null) {
            return null;
        }
        int target = (int) frame;
        if (target < 0) {
            target = 0;
        }
        BufferedImage[] array = spriteArray(direction);
        currentIndex = target % spriteArraySize(direction); // allow for cycling
        return array[currentIndex];
    }
    
    /**
     * Returns the first image of the given direction if it has been set. Note
     * that if the returned object is modified, the internal reference to that
     * image in this class will be changed too.
     */
    public BufferedImage getFirstImage(Direction direction) {
        if (direction == null || spriteArraySize(direction) == 0) {
            return null;
        }
        // valid direction is given and there's at least one image for that direction
        return spriteArray(direction)[0];
    }
    
    /**
     * Returns a reference to the array containing the sprite images in
     * the given direction.
     */
    private BufferedImage[] spriteArray(Direction direction) {
        if (direction == null) {
            return null;
        }
        if (direction == Direction.EAST) {
            return spriteEast; 
        } else if (direction == Direction.NORTH) {
            return spriteNorth;
        } else if (direction == Direction.WEST) {
            return spriteWest;
        } else {
            return spriteSouth;
        }
    }
    
    /**
     * Returns the number of images currently stored for the
     * given direction.
     */
    private int spriteArraySize(Direction direction) {
        if (direction == null) {
            return -1;
        }
        if (direction == Direction.EAST) {
            return totalEast;
        } else if (direction == Direction.NORTH) {
            return totalNorth;
        } else if (direction == Direction.WEST) {
            return totalWest;
        } else {
            return totalSouth;
        }
    }
    
}
