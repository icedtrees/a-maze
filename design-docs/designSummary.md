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


MazeSettings
-------------
maze size: will involve a single slider that controls the maze HEIGHT. only ODD numbers. scale from 11 to 55
branching: single slider which controls branching. scale from 1 to 10
straightness: single slider. scale from -10 to 10
starting time: time in seconds. single slider. scale from 10 to 300


interface Modification
---------------------
Modification defines a certain way in which a Maze must be modified to fit the requirements of a new feature. Modifications must implement the 

void modify(Maze maze) function

which takes a Maze as an argument and modifies it minimally to suit the feature. MazePage features like enable timer will have to be manually hardcoded.


implemented Modifications
-------------------------
explored trail: yes or no
clocks: yes or no. single slider. frequency of clocks - 0 to 100
boots: yes or no. singler slider. frequency of boots - 0 to 100
fogofwar: yes or no. single slider. frequency of torches - 0 to 100
shifting walls: yes or no. single slider. percentage of walls to shift each time - 0 to 100

Campaign
---------
level1 - hello world
5, 8, 10, 40
explored
no no no no

level2 - easy as pie
7, 8, 7, 40
explored
no no no no

level3 - whats that ticking sound?
9, 8, 5, 20
explored
3 no no no

level4 - time is tight
11, 10, 5, 20
explored
3 no no no

level5 - run run run
13, 10, 3, 30
yes 1 3 no no

level6 - training wheels off
15, 10, 0, 30
no 2 2 no no

level7 - iseedeadpeople
17, 10, 0, 60
yes 2 2 3 no

level8 - fading footsteps
17, 10, 0, 60
no 2 2 3 no

level9 - hogwarts
20, 10, 0, 120
no 3 2 no 10

level10 - final destination
25, 10, -5, 180
no 5 3 4 10

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

TASK LISTS MOVED TO TRELLO
--------------------------
