public abstract class Resident {
    private String residentId;
    private String fullName;
    private int age;
    private String vulnerabilityType;

    public Resident(String residentId, String fullName, int age, String vulnerabilityType) {
        this.residentId = residentId;
        this.fullName = fullName;
        this.age = age;
        this.vulnerabilityType = vulnerabilityType;
    }

    public String getResidentId() { return residentId; }
    public String getFullName() { return fullName; }
    public int getAge() { return age; }
    public String getVulnerabilityType() { return vulnerabilityType; }

    public abstract String getSpecificNeed();
    public abstract void displayNeeds();
}
