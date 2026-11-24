public class SeniorCitizen extends Resident {
    private String maintenanceMed;

    public SeniorCitizen(String residentId, String fullName, int age, String maintenanceMed) {
        super(residentId, fullName, age, "Senior Citizen");
        this.maintenanceMed = maintenanceMed;
    }

    @Override
    public String getSpecificNeed() {
        return maintenanceMed;
    }

    @Override
    public void displayNeeds() {
        System.out.println("   [Senior] " + getFullName() + " (Age " + getAge() + ") -> Needs Med: " + maintenanceMed);
    }
}
