// Alexandru Diloreanu

import java.util.Random;

public class DiceGameLogic {
    private String player1Name, player2Name;
    private int player1Lives, player2Lives;
    private Random random;
    private String currentPlayerName;
    private int lastRollPlayer1, lastRollPlayer2;

    public DiceGameLogic(String player1Name, String player2Name, int player1Lives, int player2Lives) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.player1Lives = player1Lives;
        this.player2Lives = player2Lives;
        random = new Random();
        currentPlayerName = flipCoin() ? player1Name : player2Name; // Coin flip to determine starting player
    }

    public int playerRoll(String playerName) {
        int roll = rollDice();
        if (playerName.equals(player1Name)) {
            lastRollPlayer1 = roll;
        } else {
            lastRollPlayer2 = roll;
        }
        return roll;
    }

    public int playerReroll(String playerName) {
        int roll = rollDice();
        if (playerName.equals(player1Name)) {
            lastRollPlayer1 = roll;
        } else {
            lastRollPlayer2 = roll;
        }
        return roll;
    }

    public void passTurn() {
        currentPlayerName = currentPlayerName.equals(player1Name) ? player2Name : player1Name;
        
        // Check if both players have taken their turn
        if (lastRollPlayer1 != 0 && lastRollPlayer2 != 0) {
            // Determine which player has the lowest roll
            if (lastRollPlayer1 < lastRollPlayer2) {
                playerLosesLife(player1Name);
            } else if (lastRollPlayer2 < lastRollPlayer1) {
                playerLosesLife(player2Name);
            }
            // Reset rolls for the next turn
            lastRollPlayer1 = 0;
            lastRollPlayer2 = 0;
        }
    }

    private void playerLosesLife(String playerName) {
        if (playerName.equals(player1Name)) {
            player1Lives--;
        } else {
            player2Lives--;
        }
    }

    private int rollDice() {
        return random.nextInt(6) + 1 + random.nextInt(6) + 1;
    }

    public int getPlayer1Lives() {
        return player1Lives;
    }

    public int getPlayer2Lives() {
        return player2Lives;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public String getCurrentPlayerName() {
        return currentPlayerName;
    }

    public String getNextPlayerName() {
        if (currentPlayerName.equals(player1Name)) {
            return player2Name;
        } else {
            return player1Name;
        }
    }

    public String getWinner() {
        if (player1Lives <= 0) {
            return player2Name;
        } else if (player2Lives <= 0) {
            return player1Name;
        }
        return null;
    }
    
    private boolean flipCoin() {
        return random.nextBoolean();
    }
}
