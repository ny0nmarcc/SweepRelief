import java.util.ArrayList;

public class Household {
    private int householdId;
    private String address;
    private ArrayList<Resident> members;
    private boolean hasClaimedAid;

    public Household(int householdId, String address) {
        this.householdId = householdId;
        this.address = address;
        this.members = new ArrayList<>();
        this.hasClaimedAid = false;
    }

    public void addMember(Resident resident) {
        members.add(resident);
    }

    public int getHouseholdId() { return householdId; }
    public String getAddress() { return address; }
    public ArrayList<Resident> getMembers() { return members; }
    public boolean hasClaimedAid() { return hasClaimedAid; }
    public void markAsClaimed() { this.hasClaimedAid = true; }

    public int calculatePriorityScore() {
        int score = 0;
        for (Resident r : members) {
            if (!r.getVulnerabilityType().equals("General")) {
                score += 10;
            } else {
                score += 1;
            }
        }
        return score;
    }

    public void reviewHouseholdNeeds() {
        System.out.println("\n=======================================================");
        System.out.println("HOUSEHOLD #" + householdId + " | Address: " + address);
        System.out.println("Priority Score: " + calculatePriorityScore() + " pts | Claimed: " + (hasClaimedAid ? "YES" : "NO"));
        System.out.println("Members & Targeted Needs:");
        for (Resident r : members) {
            r.displayNeeds();
        }
        System.out.println("=======================================================");
    }
}
