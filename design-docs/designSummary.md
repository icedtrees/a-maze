Note: run update.sh to update the HTML page from the md file. Modify the md file to make changes.


Design Summary
==============

Game
----
The main Game class controls one main JFrame window, which it passes to various Page classes which determine the contents of the window. Each Page has a set of 

abstract Page
------
The Page class is an abstract class that other Pages will extend. The constructor will accept a JFrame as a parameter that all GUI elements can be packed into.

Generally each Page will have a constructor, which will set up all required page elements, and a run() function, which fills the Frame with the Page and assumes control until the termination of the page. At the termination of the page, the Page will return an exit code defined in an enumeration within the Page. 

HomePage extends Page
-------------------------
The HomePage is the main menu screen, where the user can navigate to the other 

MazePage extends Page
-------------------------
Main game page which contains a Maze object as well as a Player object. The Maze object is drawn and the Player object is overlaid on the maze while other menu elements are placed in a sidebar on the right.

InstructionsPage extends Page
---------------------------------
Instructions

SettingsPage extends Page
-----------------------------
Settings page allows the user to manually choose display dimensions for the Game, overriding the default display dimensions derived from the monitor resolution.

HighScoresPage extends Page
-------------------------------
Shows the high scores


Maze extends JComponent
-----------------------
Contained in MazeWindow. Takes care of the maze generation as well as graphical output. Maze will have various public functions to alter the maze, which mazewindow will call. Maze returns result codes to MazePage to tell it what to do.


interface Modification
---------------------
Modification defines a certain way in which a Maze must be modified to fit the requirements of a new feature. Modifications must implement the 

void modify(Maze maze) function

which takes a Maze as an argument and modifies it minimally to suit the feature. MazePage features like enable timer will have to be manually hardcoded.


Tile
----
Tile can either be empty or not empty, representing a wall or an empty space. Each Tile contains one TileObject, which is the contents of the Tile.

TileObject
----------
An object can be shown on an empty Tile. Each TileObject is initialised with a reference to an image file in the resources directory. The Tile contains the TileObject

MobileObject
------------
A MobileObject, unlike a TileObject, is mobile, and is displayed on top of the TileObject. The Maze contains a list of mobileobjects contained in the maze, and draws them after drawing the rest of the maze

MazeOverlay
-----------
The MazeOverlay allows additional overlays to be drawn on top of the maze, and 

Non-base functionality to design later
--------------------------------------
-   [Page transitions]
-   Background images for the pages
-   Instructions for each feature
-   Level select
-   Co-op [versus/versus AI]

Non-base functionality that varies with difficulty level

-   Time limits (solving) and high scores
-   [Hints and other assistance]
-   TileObjects
    *   [Portals]
    *   [Switches/Doors]
    *   Treasure: extra time item
    *   Movement speed item
    *   Moving walls
    *   Fog of war
- MobileObjects
    *  [Enemies]


Stuff done
-----------
0. Multiple buttons + co-op buttons: Davy
2. Compile and run scripts: Davy

Stuff to do
-----------
1. Level completion: Leo
3. Colour tuning: Andy
4. Sprites + fix sprite size + sprite animations: Torches, clocks, player + walls: Andy
4. Page backgrounds + move to Page: Nara
5. Instructions: Nara
6. Implement timer on MazePage: Davy
7. Establish the difficulty and feature count for each level: Leo
8. MazeStats: Leo
9. Figure out GameOptions: Davy
10. SettingsPage: Davy
11. Co-op mode: Leo
12. Update UML: Davy

Sprite colour: Andy
Make threads safe: Leo/Davy
