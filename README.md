# 1. SweepRelief: An OOP-Based Emergency Relief and Maintenance Medicine Dispatch System for Disaster-Stricken Barangays

## 2. Project Description
SweepRelief is a console-based Java application designed to assist Philippine barangays in the immediate aftermath of natural calamities such as typhoons and floods. Instead of relying on generic, one-size-fits-all relief packages, the system ensures precision in targeted aid allocation by matching the specific health and nutritional profiles of vulnerable evacuees—including Senior Citizens, Infants, PWDs, and Chronic Illness Patients—against real-time, unexpired barangay inventory.

## 3. Problem Being Addressed
During natural disasters in the Philippines, manual paper-based relief operations suffer from three critical issues:
- Mismatched Aid ("One-Size-Fits-All" Distribution): Generic relief packs containing instant noodles and canned goods fail to meet the critical needs of infants needing milk formula or senior citizens needing maintenance medicines.
- Blind Inventory and Spoilage: Without real-time tracking, relief donations pile up unmonitored, leading to sudden stock shortages or the accidental distribution of expired medicines.
- Distribution Leakage and Double-Claiming: High-stress post-disaster environments make manual verification prone to human error, allowing duplicate aid claims while leaving other vulnerable families unserved.

## 4. Project Objectives
General Objective:
To develop a console-based Java application to support the recovery of Filipino communities affected by typhoons and floods through precision targeted aid allocation and real-time inventory tracking.

Specific Objectives:
- To build a household registry that classifies vulnerable residents (Senior Citizens, Infants, General Residents) and tracks their exact medical or nutritional requirements.
- To implement an inventory tracking module that monitors live stock quantities, triggers low-stock warnings, and blocks expired items from being dispensed.
- To automate targeted aid matching and enforce anti-double claiming guardrails through custom exception handling and transaction receipts.

## 5. Target Users
- Barangay Health Workers (BHWs): For encoding resident health profiles and verifying specific maintenance medication needs.
- Barangay Disaster Risk Reduction and Management (BDRRMC) Staff / Relief Volunteers: For checking warehouse stock levels and dispatching targeted relief packages during emergency rollouts.
- Barangay Officials / Administrators: For auditing distribution receipts and monitoring remaining inventory balances.

## 6. Main Features
- Smart Household & Vulnerability Registry: Registers families, tracks individual demographic profiles, and computes a household priority score based on vulnerable members.
- Intelligent Inventory Control: Tracks item quantities and expiration dates, automatically flagging low-stock supplies and blocking expired medicines.
- Automated Precision Aid Matching: Scans a household's members and automatically allocates their required maintenance medications, infant formula, or standard food packs from the warehouse.
- Anti-Double Claiming & Receipt Auditing: Generates timestamped official relief receipts and blocks households from claiming twice within the same calamity cycle.

## 7. Technologies Used
- Programming Language: Java (JDK 17 or higher)
- Paradigm: Object-Oriented Programming (OOP)
- Standard Libraries: java.util.ArrayList, java.util.Scanner, java.time.LocalDate, java.time.LocalDateTime
- Version Control & Repository Hosting: Git & GitHub

## 8. Team Members and Roles
- [Member 1 Name] – Lead Developer / System Architect
- [Member 2 Name] – Documentation & UML Designer
- [Member 3 Name] – Quality Assurance & Testing

## 9. OOP Concepts Demonstrated

### Encapsulation
```java
private String residentId;
private String fullName;
private int age;
private String vulnerabilityType;

public String getResidentId() { return residentId; }
public String getFullName() { return fullName; }
public int getAge() { return age; }
public String getVulnerabilityType() { return vulnerabilityType; }
```
In Resident.java, personal demographic fields are declared private and accessed strictly through public getter methods to prevent unauthorized modification. Similarly, InventoryItem.java encapsulates stockQuantity and expirationDate so they can only be modified safely through deductStock().

### Inheritance
```java
public class SeniorCitizen extends Resident {
public class Infant extends Resident {
public class GeneralResident extends Resident {
```
SeniorCitizen, Infant, and GeneralResident inherit common attributes (residentId, fullName, age) and methods from the parent Resident class while adding specialized fields such as maintenanceMed and formulaType.

### Abstraction
```java
public abstract class Resident {
    public abstract String getSpecificNeed();
    public abstract void displayNeeds();
}
```
Resident.java is defined as an abstract superclass that enforces a uniform contract for reporting medical or nutritional needs without exposing subclass-specific implementation details.

### Polymorphism
```java
@Override
public String getSpecificNeed() { return maintenanceMed; }

@Override
public String getSpecificNeed() { return formulaType; }
```
During relief allocation in DistributionLog.java, the system iterates through a single ArrayList of Resident objects and calls member.getSpecificNeed() and r.displayNeeds(), dynamically executing the overridden behavior specific to each subclass.

### Exception Handling & Collections
- Custom exceptions (InsufficientStockException, ExpiredItemException, AlreadyClaimedException) and try-catch blocks prevent system crashes and enforce business rules during invalid inputs or duplicate claims.
- ArrayList collections dynamically manage household rosters and warehouse inventory items.

## 10. Instructions for Running the Program
1. Clone or download this repository to your local machine.
2. Open the project folder in any Java IDE (NetBeans, IntelliJ IDEA, Eclipse, or VS Code) or open a terminal inside the src/ directory.
3. Compile all Java files using the terminal command:
   javac *.java
4. Run the main console application:
   java Main
5. Follow the numbered on-screen console menu prompts (1 to 5) to view inventory, inspect households, register new families, or dispatch targeted relief.

## 11. Sample Screenshots
(Screenshots of the console execution are stored in the screenshots/ folder of this repository.)
- Warehouse Inventory & Expiration Status: screenshots/01-inventory.png
- Targeted Aid Dispatch & Official Receipt: screenshots/02-dispatch-receipt.png
- Security Guardrail / Duplicate Claim Exception Block: screenshots/03-exception-block.png

## 12. Known Limitations
- Volatile Memory Storage: Data is stored in runtime ArrayList structures and resets when the console application exits (no external SQL database connection yet).
- Console-Only Interface: User interaction is limited to text-based terminal inputs via Scanner rather than a graphical user interface.
- Exact String Matching: Inventory matching relies on exact item name strings (e.g., "Amlodipine 5mg") between resident profiles and warehouse records.

## 13. Future Improvements
- File Handling / Database Integration: Implement CSV file serialization or MySQL integration so household registries and transaction logs persist across sessions.
- Expanded Subclasses: Add dedicated subclasses for PWDResident (tracking assistive devices) and ChronicIllnessPatient (tracking dietary restrictions such as low-sodium or sugar-free packs).
- Warehouse Restocking & Expiration Alerts: Add an administrative menu option to restock supplies and generate an automated report of items expiring within 30 days.

## 14. Project Contributors
- [Member 1 Name] (@github-username)
- [Member 2 Name] (@github-username)
- [Member 3 Name] (@github-username)
