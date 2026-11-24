# SweepRelief: An OOP-Based Emergency Relief and Maintenance Medicine Dispatch System for Disaster-Stricken Barangays

**Description:** "SweepRelief" is a Java-based emergency relief and maintenance medicine dispatch system designed to assist Philippine barangays in the immediate aftermath of natural calamities such as typhoons and floods. Instead of relying on generic, one-size-fits-all relief packages, the system ensures precision in targeted aid allocation by matching the specific health and nutritional profiles of vulnerable evacuees—including Senior Citizens, Infants, PWDs, and Chronic Illness Patients—against real-time, unexpired barangay inventory. The program's architecture revolves around the four core OOP principles to safeguard sensitive medical data, monitor stock quantities and expiration dates, and prevent duplicate aid claims during emergency distribution.

---

## Object-Oriented Programming Concepts Applied

The following is the detailed documentation of how the OOP principles were used throughout the program:

### Encapsulation
```java
private String residentId;
private String fullName;
private int age;
private String vulnerabilityType;

public String getResidentId() { 
    return residentId; 
}

public String getFullName() { 
    return fullName; 
}

public int getAge() { 
    return age; 
}

public String getVulnerabilityType() { 
    return vulnerabilityType; 
}
```
In `Resident.java`, encapsulation keeps personal demographic details private and prevents unauthorized modification, exposing data safely only through public getter methods.

```java
private String itemId;
private String itemName;
private int stockQuantity;
private LocalDate expirationDate;

public String getItemName() { 
    return itemName; 
}

public int getStockQuantity() { 
    return stockQuantity; 
}

public void deductStock(int amount) throws InsufficientStockException, ExpiredItemException {
    if (isExpired()) {
        throw new ExpiredItemException("BLOCKED: " + itemName + " is expired!");
    }
    if (this.stockQuantity < amount) {
        throw new InsufficientStockException("BLOCKED: Insufficient stock!");
    }
    this.stockQuantity -= amount;
}
```
These encapsulated inventory fields in `InventoryItem.java` cannot be altered directly outside the class. Stock levels can only be reduced through `deductStock()`, which enforces expiration and stock-availability guardrails.

```java
private int householdId;
private String address;
private ArrayList<Resident> members;
private boolean hasClaimedAid;

public boolean hasClaimedAid() { 
    return hasClaimedAid; 
}

public void markAsClaimed() { 
    this.hasClaimedAid = true; 
}
```
Private fields in `Household.java` protect family rosters and claim statuses from accidental tampering during chaotic relief operations.

---

### Inheritance
```java
public class SeniorCitizen extends Resident {
```
`SeniorCitizen` in `SeniorCitizen.java` inherits from the `Resident` parent class (`Resident.java`), automatically acquiring core attributes such as `residentId`, `fullName`, `age`, and `vulnerabilityType` without rewriting code, while adding specialized fields like `maintenanceMed`.

```java
public class Infant extends Resident {
```
`Infant` in `Infant.java` also extends `Resident`, inheriting standard resident traits while introducing infant-specific nutritional requirements such as `formulaType`.

---

### Abstraction
```java
public abstract class Resident {
    private String residentId;
    private String fullName;
    private int age;
    private String vulnerabilityType;

    public abstract String getSpecificNeed();
    public abstract void displayNeeds();
}
```
`Resident.java` serves as an abstract superclass that defines what every resident profile must provide (`getSpecificNeed()` and `displayNeeds()`) without specifying how each vulnerable category fulfills those needs. It forces all subclasses (`SeniorCitizen`, `Infant`, `GeneralResident`) to follow a unified contract.

---

### Polymorphism
```java
@Override
public String getSpecificNeed() { 
    return maintenanceMed; 
}

@Override
public void displayNeeds() {
    System.out.println("   [Senior] " + getFullName() + " (Age " + getAge() + ") -> Needs Med: " + maintenanceMed);
}
```
In `SeniorCitizen.java`, overriding `getSpecificNeed()` and `displayNeeds()` allows a senior citizen object to return their exact maintenance medicine (e.g., *Amlodipine 5mg*) when scanned during aid allocation.

```java
@Override
public String getSpecificNeed() { 
    return formulaType; 
}

@Override
public void displayNeeds() {
    System.out.println("   [Infant] " + getFullName() + " (" + getAge() + " mos.) -> Needs Nutrition: " + formulaType);
}
```
In `Infant.java`, the same methods are overridden to return infant formula requirements. Inside `Household.java` and `DistributionLog.java`, the system loops through a single `ArrayList<Resident>` and calls `r.displayNeeds()` or `member.getSpecificNeed()`, dynamically executing the right behavior depending on whether the object is a Senior, Infant, or General Resident.

---

## Program Structure

### Main Class (`Main.java`)
* Starts the SweepRelief dispatch system
* Initializes warehouse `InventoryItem` stock (food packs, maintenance meds, infant formula)
* Registers affected `Household` units and adds diverse `Resident` members
* Triggers `DistributionLog` transactions and catches custom exceptions

### Resident Class (`Resident.java`)
**ROLE:** Abstract parent class for all community members.  
**DATA:**
* `residentId`, `fullName`, `age`, `vulnerabilityType`  
**METHODS:**
* `getSpecificNeed()`
* `displayNeeds()`  
**RELATIONSHIP:**
* Extended by `SeniorCitizen`, `Infant`, and `GeneralResident`

### SeniorCitizen / Infant / GeneralResident Classes
**ROLE:** Specialized demographic subclasses defining exact medical or nutritional requirements.  
**RESPONSIBILITIES:**
* Store category-specific needs (`maintenanceMed`, `formulaType`)
* Override polymorphic need-reporting methods  
**RELATIONSHIP:**
* Inherits from `Resident`

### Household Class (`Household.java`)
**ROLE:** Groups individual residents into a single family unit and tracks claim eligibility.  
**RESPONSIBILITIES:**
* Store family members in an `ArrayList<Resident>`
* Calculate household vulnerability priority scores
* Track whether aid has already been claimed (`hasClaimedAid`)  
**METHODS:**
* `addMember(Resident)`
* `calculatePriorityScore()`
* `reviewHouseholdNeeds()`
* `markAsClaimed()`  
**RELATIONSHIP:**
* Has-a list of `Resident` objects (Composition)

### InventoryItem Class (`InventoryItem.java`)
**ROLE:** Tracks physical relief supplies and medicines in the barangay warehouse.  
**RESPONSIBILITIES:**
* Monitor available `stockQuantity` and `expirationDate`
* Block expired or out-of-stock items from being distributed
* Trigger low-stock warnings  
**METHODS:**
* `isExpired()`
* `deductStock(int amount)`
* `checkLowStock()`

### DistributionLog Class (`DistributionLog.java`)
**ROLE:** Core allocation and accountability controller. Handles:
* Anti-double claiming security checks
* Precision matching between household member needs and warehouse stock
* Official relief receipt generation with timestamps  
**RESPONSIBILITIES:**
* `processDistribution(ArrayList<InventoryItem> warehouse)`
* `generateReceipt()`  
**RELATIONSHIPS:**
* Has-a target `Household`
* Interacts with `InventoryItem` stock and `Resident` subclasses via polymorphism
