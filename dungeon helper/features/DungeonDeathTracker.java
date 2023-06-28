package features;
import java.util.ArrayList;
import java.util.List;

public class DungeonDeathTracker {
    private List<String> partyMembers;
    private List<String> deadMembers;

    public DungeonDeathTracker() {
        partyMembers = new ArrayList<>();
        deadMembers = new ArrayList<>();
    }

    public void addPartyMember(String playerName) {
        partyMembers.add(playerName);
    }

    public void removePartyMember(String playerName) {
        partyMembers.remove(playerName);
    }

    public void playerDied(String playerName) {
        if (partyMembers.contains(playerName) && !deadMembers.contains(playerName)) {
            deadMembers.add(playerName);
            System.out.println(playerName + " has died.");
        }
    }

    public void displayDeadMembers() {
        System.out.println("Dead party members:");
        for (String playerName : deadMembers) {
            System.out.println(playerName);
        }
    }
}
