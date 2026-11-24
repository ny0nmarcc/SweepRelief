import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<InventoryItem> warehouse = new ArrayList<>();
        ArrayList<Household> households = new ArrayList<>();
        int txCounter = 1;

        // Seed Data: Warehouse Supplies
        warehouse.add(new InventoryItem("INV-01", "Standard Food Pack", "Food", 50, "2027-12-01"));
        warehouse.add(new InventoryItem("INV-02", "Amlodipine 5mg", "Medicine", 12, "2028-06-15"));
        warehouse.add(new InventoryItem("INV-03", "Infant Formula Stage 1", "Nutrition", 8, "2027-04-10"));
        warehouse.add(new InventoryItem("INV-04", "Paracetamol 500mg", "Medicine", 30, "2025-01-01")); // Expired sample

        // Seed Data: Sample Registered Household
        Household h1 = new Household(101, "Purok 2, Brgy. San Jose");
        h1.addMember(new GeneralResident("RES-01", "Juan Dela Cruz", 38));
        h1.addMember(new SeniorCitizen("RES-02", "Lola Maria Dela Cruz", 74, "Amlodipine 5mg"));
        h1.addMember(new Infant("RES-03", "Baby Mateo Dela Cruz", 4, "Infant Formula Stage 1"));
        households.add(h1);

        boolean running = true;
        while (running) {
            System.out.println("\n=== SWEEPRELIEF CONSOLE DISPATCH SYSTEM ===");
            System.out.println("1. View Warehouse Inventory");
            System.out.println("2. View Registered Households & Vulnerability Needs");
            System.out.println("3. Register New Household");
            System.out.println("4. Dispatch Targeted Relief to Household");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer

                switch (choice) {
                    case 1:
                        System.out.println("\n--- BARANGAY WAREHOUSE INVENTORY ---");
                        for (InventoryItem item : warehouse) {
                            String status = item.isExpired() ? "[EXPIRED]" : "[VALID]";
                            System.out.println(item.getItemId() + " | " + item.getItemName() +
                                    " | Qty: " + item.getStockQuantity() +
                                    " | Exp: " + item.getExpirationDate() + " " + status);
                        }
                        break;

                    case 2:
                        for (Household h : households) {
                            h.reviewHouseholdNeeds();
                        }
                        break;

                    case 3:
                        System.out.print("Enter new Household ID (number): ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Address: ");
                        String addr = scanner.nextLine();
                        Household newHouse = new Household(id, addr);

                        System.out.print("How many family members to register? ");
                        int count = scanner.nextInt();
                        scanner.nextLine();

                        for (int i = 1; i <= count; i++) {
                            System.out.println("Member #" + i + " Type: [1] General  [2] Senior Citizen  [3] Infant");
                            int type = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Full Name: ");
                            String name = scanner.nextLine();
                            System.out.print("Age (in years, or months if Infant): ");
                            int age = scanner.nextInt();
                            scanner.nextLine();

                            if (type == 2) {
                                System.out.print("Required Maintenance Med (e.g., Amlodipine 5mg): ");
                                String med = scanner.nextLine();
                                newHouse.addMember(new SeniorCitizen("RES-" + id + i, name, age, med));
                            } else if (type == 3) {
                                System.out.print("Required Formula (e.g., Infant Formula Stage 1): ");
                                String formula = scanner.nextLine();
                                newHouse.addMember(new Infant("RES-" + id + i, name, age, formula));
                            } else {
                                newHouse.addMember(new GeneralResident("RES-" + id + i, name, age));
                            }
                        }
                        households.add(newHouse);
                        System.out.println("Household #" + id + " registered successfully!");
                        break;

                    case 4:
                        System.out.print("Enter Household ID to receive aid: ");
                        int targetId = scanner.nextInt();
                        scanner.nextLine();

                        Household found = null;
                        for (Household h : households) {
                            if (h.getHouseholdId() == targetId) {
                                found = h;
                                break;
                            }
                        }

                        if (found == null) {
                            System.out.println("Household ID not found.");
                        } else {
                            try {
                                String txId = String.format("TXN-2026-%03d", txCounter++);
                                DistributionLog log = new DistributionLog(txId, found);
                                log.processDistribution(warehouse);
                            } catch (AlreadyClaimedException e) {
                                System.out.println("   [SECURITY BLOCK] " + e.getMessage());
                            }
                        }
                        break;

                    case 5:
                        running = false;
                        System.out.println("Exiting SweepRelief System. Stay safe!");
                        break;

                    default:
                        System.out.println("Invalid option. Please choose 1-5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("[INPUT ERROR] Please enter a valid number!");
                scanner.nextLine(); // Clear invalid input
            }
        }
        scanner.close();
    }
}
