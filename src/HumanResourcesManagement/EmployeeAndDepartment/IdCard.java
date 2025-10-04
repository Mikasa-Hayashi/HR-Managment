package HumanResourcesManagement.EmployeeAndDepartment;

public class IdCard {
    // -------------------------------------------------------------------------------------
    private final int _number;
    public int getNumber() {
        return _number;
    }

    // -------------------------------------------------------------------------------------
    // TODO {
    public boolean isActive() { return false; }
    // } TODO

    // -------------------------------------------------------------------------------------
    private IdCard(int number) {
        _number = number;
    }

    private static int _currId = 0;

    static IdCard issueUniqueCard() {
        ++_currId;
        return new IdCard(_currId);
    }

    // TODO {
    void deactivate() { }
    // } TODO

    // -------------------------------------------------------------------------------------
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        } else if (!(other instanceof IdCard)) {
            return false;
        } else {
            return getNumber() == ((IdCard)other).getNumber();
        }
    }

    public int hashCode() {
        return getNumber();
    }
}
