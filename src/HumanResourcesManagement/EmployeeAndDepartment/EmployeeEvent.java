package HumanResourcesManagement.EmployeeAndDepartment;


import HumanResourcesManagement.Education;

import java.util.EventObject;

public class EmployeeEvent extends EventObject {

    // -------------------------------------------------------------------------------------
    EmployeeEvent(Employee employee) {
        super(employee);
    }

    public Employee getSource() {
        return (Employee)super.getSource();
    }

    // -------------------------------------------------------------------------------------
    private Department _currentDepartment;
    public Department getCurrentDepartment() {
        return _currentDepartment;
    }

    private Department _prevDepartment;
    public Department getPrevDepartment() {
        return _prevDepartment;
    }

    void setDepartment(Department prevDep, Department currentDep) {
        _prevDepartment = prevDep;
        _currentDepartment = currentDep;
    }

    // -------------------------------------------------------------------------------------
    private Education _currentEducation;
    public Education getCurrentEducation() {
        return _currentEducation;
    }

    private Education _prevEducation;
    public Education getPrevEducation() {
        return _prevEducation;
    }

    void setEducation(Education prevEducation, Education currentEducation) {
        _prevEducation = prevEducation;
        _currentEducation = currentEducation;
    }
}
