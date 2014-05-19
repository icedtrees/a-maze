Design Summary
==============

Game
----
The main Game class controls one main JFrame window, which it passes to various Page classes which determine the contents of the window. Each Page has a set of 

Page
------
The Page class is an abstract class that other Pages will extend. The constructor will accept a JFrame as a parameter that all GUI elements can be packed into.

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
Takes care of the maze generation as well as graphical output. The Maze will be contained in the MazePage.

Tile
----
Tile can either be empty or not empty, representing a wall or an empty space. Each Tile contains one TileObject, which is the contents of the Tile.

TileObject
----------
An object can be shown on an empty Tile. Each TileObject is initialised with a reference to an image file in the resources directory, indicating what image should be displayed for the given object.

PlayerObject
------------
A PlayerObject, unlike a TileObject, is mobile, and is displayed on top of the TileObject.

Non-base functionality to design later
--------------------------------------
- Page transitions
- Background images for the pages
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


