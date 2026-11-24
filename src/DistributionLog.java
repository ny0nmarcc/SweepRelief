import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DistributionLog {
    private String transactionId;
    private Household targetHousehold;
    private ArrayList<String> allocatedItems;
    private String timestamp;

    public DistributionLog(String transactionId, Household targetHousehold) {
        this.transactionId = transactionId;
        this.targetHousehold = targetHousehold;
        this.allocatedItems = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.timestamp = LocalDateTime.now().format(dtf);
    }

    public void processDistribution(ArrayList<InventoryItem> warehouse) throws AlreadyClaimedException {
        if (targetHousehold.hasClaimedAid()) {
            throw new AlreadyClaimedException("FRAUD ALERT: Household #" + targetHousehold.getHouseholdId() + " has already claimed relief for this calamity cycle!");
        }

        System.out.println("\n>>> Processing Targeted Aid Allocation for Household #" + targetHousehold.getHouseholdId() + "...");

        for (Resident member : targetHousehold.getMembers()) {
            String neededItemName = member.getSpecificNeed();
            boolean itemFound = false;

            for (InventoryItem item : warehouse) {
                if (item.getItemName().equalsIgnoreCase(neededItemName)) {
                    itemFound = true;
                    try {
                        item.deductStock(1);
                        allocatedItems.add(item.getItemName() + " (For: " + member.getFullName() + ")");
                        item.checkLowStock();
                    } catch (ExpiredItemException | InsufficientStockException e) {
                        System.out.println("   [!] " + e.getMessage());
                    }
                    break;
                }
            }
            if (!itemFound) {
                System.out.println("   [!] Item not registered in warehouse: " + neededItemName);
            }
        }

        targetHousehold.markAsClaimed();
        generateReceipt();
    }

    public void generateReceipt() {
        System.out.println("\n--- OFFICIAL BARANGAY RELIEF RECEIPT ---");
        System.out.println("Transaction ID : " + transactionId);
        System.out.println("Date/Time      : " + timestamp);
        System.out.println("Recipient      : Household #" + targetHousehold.getHouseholdId() + " (" + targetHousehold.getAddress() + ")");
        System.out.println("Allocated Supplies:");
        for (String record : allocatedItems) {
            System.out.println("  * " + record);
        }
        System.out.println("----------------------------------------");
    }
}
