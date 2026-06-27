# Java Connect 4 (Networked)

A Java-based implementation of the classic Connect 4 game. This project features a centralized game server that supports gameplay over a local network using either a command-line terminal client or a graphical user interface (GUI).

## 🚀 How to Run the Game

Follow these steps to get the server and clients running locally.

### Step 1: Start the Server
First, you must launch the game server to manage connections and handle game logic.
* Navigate to your source directory and run the **`Server`** class.
* The server will host the game locally on port `1024`.

---

### Step 2: Choose Your Client
You can play using two different client styles. Run your preferred option below:

#### Option A: Terminal Client
To play a lightweight, text-based version of the game directly from your terminal:
1. Open your terminal and connect to the server by running:
   ```bash
   nc localhost 1024
   ```
2. **How to play:** When it is your turn, type a column number from **`0` to `6`** and press enter to drop your token. Any other input will be rejected by the game.
   Note: **`1` and `2`** on the board grid represent players 1 and 2, respectively.

#### Option B: GUI Client
To play using the graphical version of the game:
1. Run the **`Gui`** class in your Java environment.
2. **How to play:** Click the corresponding column buttons at the top of the window to drop your token into that column.

---

## ⚠️ Important Gameplay Notes

* **Turn Order (GUI):** Player prompts are not actively displayed on the GUI panel. 
  * The **first client** to connect to the server becomes **Player 1** and must make the first move.
  * The **second client** to connect becomes **Player 2** and must wait for Player 1 to make their move before they can click.

## 🛠️ Built With
* Java (Socket Programming, Swing GUI)
* Netcat (for Terminal Interface testing)

## 👤 Author
* **Jreanne Ramgeet** - *version 1.0*
