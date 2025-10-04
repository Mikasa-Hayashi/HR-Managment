package HumanResourcesManagement.EmployeeAndDepartment;

import java.util.EventListener;

public interface EmployeeListener extends EventListener {
    void employeeEnrolled(EmployeeEvent e);
    void employeeTransferred(EmployeeEvent e);
    void employeeDismissed(EmployeeEvent e);
    void employeeStudied(EmployeeEvent e);
}
