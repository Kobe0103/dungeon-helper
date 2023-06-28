package features;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DungeonDeathTrackerGUI extends JFrame {
    private List<String> partyMembers;
    private List<String> deadMembers;
    private JTextArea outputTextArea;

    public DungeonDeathTrackerGUI() {
        partyMembers = new ArrayList<>();
        deadMembers = new ArrayList<>();

        initializeUI();
    }

    private void initializeUI() {
        setTitle("Dungeon Death Tracker");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(outputTextArea);

        JButton addButton = new JButton("Add Party Member");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String playerName = JOptionPane.showInputDialog(DungeonDeathTrackerGUI.this, "Enter player name:");
                addPartyMember(playerName);
            }
        });

        JButton removeButton = new JButton("Remove Party Member");
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String playerName = JOptionPane.showInputDialog(DungeonDeathTrackerGUI.this, "Enter player name:");
                removePartyMember(playerName);
            }
        });

        JButton deathButton = new JButton("Player Died");
        deathButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String playerName = JOptionPane.showInputDialog(DungeonDeathTrackerGUI.this, "Enter player name:");
                playerDied(playerName);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(deathButton);

        getContentPane().add(scrollPane, "Center");
        getContentPane().add(buttonPanel, "South");
    }

    public void addPartyMember(String playerName) {
        partyMembers.add(playerName);
        outputTextArea.append("Added party member: " + playerName + "\n");
    }

    public void removePartyMember(String playerName) {
        partyMembers.remove(playerName);
        outputTextArea.append("Removed party member: " + playerName + "\n");
    }

    public void playerDied(String playerName) {
        if (partyMembers.contains(playerName) && !deadMembers.contains(playerName)) {
            deadMembers.add(playerName);
            outputTextArea.append(playerName + " has died.\n");
        }
    }
}
