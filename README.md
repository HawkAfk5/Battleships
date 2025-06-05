# Battleships
# Battleship Game

A classic implementation of the popular Battleship game, written in Java with a Swing GUI. It supports single-player (against AI) and two-player modes.

## Features

*   Graphical User Interface (GUI) for easy interaction.
*   Two game modes:
    *   **Single Player:** Play against a computer AI.
    *   **Two Players:** Local play between two players on the same computer.
*   Interactive ship placement on the board.
*   Turn-based attack system.
*   Automatic detection of sunk ships and game win conditions.
*   Option to restart the game.

## Requirements

*   Java Development Kit (JDK) - version 11 or newer (JDK 17+ recommended).
*   (Optional) An IDE like Eclipse or IntelliJ IDEA for development or importing the project.
*   Git for cloning the repository (if sharing via Git).

## Installation and Setup

1.  **Clone the Repository (if using Git):**
    ```bash
    git clone <https://github.com/HawkAfk5/Battleships.git>
    cd Battleships
    ```
    

2.  **Import into Eclipse (or other IDE):**
    *   Open Eclipse.
    *   Select `File > Import...`.
    *   Choose `General > Existing Projects into Workspace` and click `Next`.
    *   Click `Browse...` next to `Select root directory` and navigate to your project folder (`eclipse-workspace/Battleships`).
    *   Ensure the "Battleships" project is checked in the `Projects` list and click `Finish`.

3.  **Running the Game:**
    *   Locate the `Main.java` class (or whichever is your main starting class that calls `StartingScreenGUI`) in Eclipse's Package Explorer.
    *   Right-click on it and select `Run As > Java Application`.

## How to Play

1.  Upon launching the application, the starting screen will appear.
2.  Select the game mode ("Single Player" or "Two Players") and click "Start".
3.  **Ship Placement:**
    *   **Two Players:** Each player, in turn, will be prompted to place their ships on the board. Click a cell to set the ship's starting position, then choose the orientation (horizontal or vertical) from the dialog box. The available ship sizes are: 5, 4, 3, 3, 2.
    *   **Single Player:** You will place your ships in the same manner. The computer will place its ships automatically.
4.  After ship placement, the main game board will be displayed.
5.  **Combat:**
    *   Players (or the player and the AI) take turns clicking on cells of the opponent's board to attack.
    *   A successful hit on a ship will be marked (e.g., with orange).
    *   A miss will be marked differently (e.g., with cyan).
    *   When a ship is completely sunk, it will be distinctly marked (e.g., with red).
6.  **Winning:** The first player to sink all of the opponent's ships wins the game!
7.  At the end of the game, you will be asked if you want to play again.

## Technologies Used

*   Java
*   Swing (for the GUI)

## Contributors

*   
HawkAfk5 Til_top
mariagete
nikostsormpa
K6619
DimiStell
Nikos-Palios
---

Feel free to add or remove sections based on your needs! For example, if you've used specific libraries or have more complex setup instructions, you can add them.
