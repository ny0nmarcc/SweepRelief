public class GeneralResident extends Resident {
    public GeneralResident(String residentId, String fullName, int age) {
        super(residentId, fullName, age, "General");
    }

    @Override
    public String getSpecificNeed() {
        return "Standard Food Pack";
    }

    @Override
    public void displayNeeds() {
        System.out.println("   [General] " + getFullName() + " (Age " + getAge() + ") -> Needs: Standard Food Pack");
    }
}
