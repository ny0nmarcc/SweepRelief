public class Infant extends Resident {
    private String formulaType;

    public Infant(String residentId, String fullName, int ageInMonths, String formulaType) {
        super(residentId, fullName, ageInMonths, "Infant");
        this.formulaType = formulaType;
    }

    @Override
    public String getSpecificNeed() {
        return formulaType;
    }

    @Override
    public void displayNeeds() {
        System.out.println("   [Infant] " + getFullName() + " (" + getAge() + " mos.) -> Needs Nutrition: " + formulaType);
    }
}
