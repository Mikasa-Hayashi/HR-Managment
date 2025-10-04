package HumanResourcesManagement.EmployeeAndDepartment;

import HumanResourcesManagement.Education;

public class Employee {
    // -------------------------------------------------------------------------------------
    private String _FIO;
    public String getFIO() {
        return _FIO;
    }

    public void setFIO(String FIO) {
        _FIO = FIO;
    }

    // -------------------------------------------------------------------------------------
    // TODO {
    public Education getEducation() {
        return null;
    }

    public void completeNextDegreeOfStudy() {
        completeNextDegreeOfStudy(null);
    }

    public void completeNextDegreeOfStudy(Education.Speciality speciality) {
    }

    public Employee(String FIO, Education education) {
        _FIO = FIO;
    }
    // } TODO

    // -------------------------------------------------------------------------------------
    // TODO {
    public IdCard getIdCard() {
        return null;
    }

    private void chooseCardAsNeeded() {
    }
    // } TODO

    // -------------------------------------------------------------------------------------
    // TODO {
    public Computer getComputer() {
        return null;
    }

    void setComputer(Computer comp) {

        //    throw new RuntimeException("Работник еще не работает");

        //    throw new RuntimeException("Работник уже имеет компьютер");
    }

    void unsetComputer() { }
    // } TODO

    // -------------------------------------------------------------------------------------
    // TODO {
    public Department getDepartment() {
        return null;
    }

    public boolean isWorks() {
        return false;
    }

    public boolean enrollIn(Department department) { return false; }

    private boolean enrollIn(Department department, boolean isTransferring) {

        //    throw new RuntimeException("Работник уже работает");

        //    throw new RuntimeException("Работник уже работает в этом отделе");

        return false;
    }

    public boolean transferTo(Department department) {

        //    throw new RuntimeException("Работник уже работает в этом отделе");

        //    throw new RuntimeException("Отдел, в который переводились, не принял, а тот, в котором работали, не принимает обратно");

        return false;
    }

    public void dismiss() {
        dismiss(false);
    }

    private boolean dismiss(boolean isTransferring) {

        //    throw new RuntimeException("Работник не работает");

        return false;
    }
    // } TODO

    // -------------------------------------------------------------------------------------
    // TODO {
    public void addEmployeeListener(EmployeeListener el) {
    }

    public void removeEmployeeListener(EmployeeListener el) {
    }

    private void fireEmployeeEnrolled() {
    }

    private void fireEmployeeTransferred(Department prevDepartment) {
    }

    private void fireEmployeeDismissed(Department lastDepartment) {
    }

    private void fireEmployeeStudied(Education prevEducation) {
    }
    // } TODO
}

