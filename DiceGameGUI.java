// Alexandru Diloreanu

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DiceGameGUI extends JFrame {
    private JLabel player1Label, player2Label, resultLabel;
    private JButton rollBtn, rerollBtn, keepRollBtn;
    private DiceGameLogic gameLogic;
    private String currentPlayerName, nextPlayerName;
    private boolean canReroll;

    public DiceGameGUI() {
        // Ask for player names
        String player1Name = JOptionPane.showInputDialog(null, "Enter Player 1's Name:");
        String player2Name = JOptionPane.showInputDialog(null, "Enter Player 2's Name:");

        // Ask for number of lives
        int player1Lives = getStartingLives(player1Name);
        int player2Lives = getStartingLives(player2Name);

        setTitle("Dice Game");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        player1Label = new JLabel(player1Name + "'s Lives: " + player1Lives);
        player2Label = new JLabel(player2Name + "'s Lives: " + player2Lives);
        resultLabel = new JLabel("");

        rollBtn = new JButton("Roll");
        rerollBtn = new JButton("Reroll");
        rerollBtn.setEnabled(false); // Initially disabled
        keepRollBtn = new JButton("Keep Roll");
        keepRollBtn.setEnabled(false); // Initially disabled

        gameLogic = new DiceGameLogic(player1Name, player2Name, player1Lives, player2Lives);
        canReroll = false; // Initially disabled

        add(player1Label);
        add(player2Label);
        add(resultLabel);
        add(rollBtn);
        add(rerollBtn);
        add(keepRollBtn);

        currentPlayerName = gameLogic.getCurrentPlayerName();
        nextPlayerName = gameLogic.getNextPlayerName();
        resultLabel.setText("Current Turn: " + currentPlayerName);

        rollBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int roll = gameLogic.playerRoll(currentPlayerName);
                resultLabel.setText(currentPlayerName + " Rolled: " + roll);
                canReroll = true; // Set canReroll to true after the first roll
                rollBtn.setEnabled(false); // Disable roll button after rolling
                rerollBtn.setEnabled(true); // Enable reroll button after rolling
                keepRollBtn.setEnabled(true); // Enable keep roll button after rolling
                updateLabels();
            }
        });

        rerollBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (canReroll) {
                    int roll = gameLogic.playerReroll(currentPlayerName);
                    resultLabel.setText(currentPlayerName + " Rerolled: " + roll);
                    canReroll = false; // Disable rerolling until the next turn
                    rerollBtn.setEnabled(false); // Disable reroll button after rerolling
                    updateLabels();
                }
            }
        });

        keepRollBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameLogic.passTurn();
                currentPlayerName = gameLogic.getCurrentPlayerName();
                nextPlayerName = gameLogic.getNextPlayerName();
                resultLabel.setText("Current Turn: " + currentPlayerName);
                canReroll = false; // Reset canReroll to false when turn is passed
                rollBtn.setEnabled(true); // Enable roll button after passing the turn
                rerollBtn.setEnabled(false); // Disable reroll button after passing the turn
                keepRollBtn.setEnabled(false); // Disable keep roll button after passing the turn
                updateLabels();
            }
        });

        setVisible(true);
    }

    private int getStartingLives(String playerName) {
        while (true) {
            try {
                String input = JOptionPane.showInputDialog(null, "Enter " + playerName + "'s starting lives:");
                if (input == null || input.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number.");
                    continue;
                }
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number.");
            }
        }
    }

    private void updateLabels() {
        String currentPlayerName = gameLogic.getCurrentPlayerName();

        // Update labels for players' lives
        player1Label.setText(gameLogic.getPlayer1Name() + "'s Lives: " + gameLogic.getPlayer1Lives());
        player2Label.setText(gameLogic.getPlayer2Name() + "'s Lives: " + gameLogic.getPlayer2Lives());

        // Check for winner
        if (gameLogic.getWinner() != null) {
            resultLabel.setText(gameLogic.getWinner() + " Wins!");
            disableButtons();
        }
    }

    private void disableButtons() {
        rollBtn.setEnabled(false);
        rerollBtn.setEnabled(false);
        keepRollBtn.setEnabled(false);
    }

    public static void main(String[] args) {
        new DiceGameGUI();
    }
}
