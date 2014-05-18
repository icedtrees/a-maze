Design Summary
==============
Game
----
The main Game class is effectively a window manager that controls when windows open and close. 

Window
------
The Window class is an abstract class that other Windows will extend. The constructor will accept a JFrame as a parameter that all GUI elements can be packed into.

HomeWindow extends Window
-------------------------
Main menu

MazeWindow extends Window
-------------------------
Main game which contains a Maze object as well as a Player object. The Maze object is drawn and the Player object is overlaid on the maze while other menu elements are placed in a sidebar on the right.

InstructionsWindow extends Window
---------------------------------
Instructions

SettingsWindow extends Window
-----------------------------
Settings window allows the user to manually choose display dimensions for the Game, overriding the default display dimensions derived from the monitor resolution.

HighScoresWindow extends Window
-------------------------------
Shows the high scores

Maze extends JPanel
-------------------
Takes care of the maze generation as well as graphical output.

Tile
----
Tile can either be empty or not empty, representing a wall or an empty space. Each Tile contains one TileObject, which is the contents of the Tile.

TileObject
----------
An object can be shown on an empty Tile. Each TileObject is initialised with a reference to an image file in the resources directory, indicating what image should be displayed for the given object.


TileObject
----------
An object can be shown on an empty Tile. Each TileObject is initialised with a reference to an image file in the resources directory.

PlayerObject
------------
A PlayerObject, unlike a TileObject, is mobile, and is displayed on top of the TileObject.

Non-base functionality to design later
--------------------------------------
- Page transitions
- Background images for the windows
- Walking on the main menu
- Time limits (planning + solving) and high scores
- Hints and other assistance
- Instructions for each level
- TileObjects
    * Portals
    * Switches/Doors
    * Treasure
    * Extra time item
    * Movement speed item
    * Move back in time item
    * Transparent walls, moving walls
    * Fog of war/line of sight
- PlayerObjects
    * Enemies


