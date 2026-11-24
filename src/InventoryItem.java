import java.time.LocalDate;

public class InventoryItem {
    private String itemId;
    private String itemName;
    private String category;
    private int stockQuantity;
    private LocalDate expirationDate;

    public InventoryItem(String itemId, String itemName, String category, int stockQuantity, String expirationDate) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.expirationDate = LocalDate.parse(expirationDate);
    }

    public String getItemId() { return itemId; }
    public String getItemName() { return itemName; }
    public String getCategory() { return category; }
    public int getStockQuantity() { return stockQuantity; }
    public LocalDate getExpirationDate() { return expirationDate; }

    public boolean isExpired() {
        return LocalDate.now().isAfter(expirationDate);
    }

    public void deductStock(int amount) throws InsufficientStockException, ExpiredItemException {
        if (isExpired()) {
            throw new ExpiredItemException("BLOCKED: " + itemName + " expired on " + expirationDate + "!");
        }
        if (this.stockQuantity < amount) {
            throw new InsufficientStockException("BLOCKED: Insufficient stock for " + itemName + " (Remaining: " + stockQuantity + ")");
        }
        this.stockQuantity -= amount;
    }

    public void checkLowStock() {
        if (stockQuantity <= 10) {
            System.out.println("   [LOW STOCK WARNING] " + itemName + " only has " + stockQuantity + " units left!");
        }
    }
}
