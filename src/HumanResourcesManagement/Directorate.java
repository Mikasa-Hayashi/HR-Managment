package HumanResourcesManagement;

import HumanResourcesManagement.EmployeeAndDepartment.Employee;
import HumanResourcesManagement.EmployeeAndDepartment.EmployeeEvent;
import HumanResourcesManagement.EmployeeAndDepartment.EmployeeListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Directorate implements EmployeeListener {
    private final List<String> _history = new ArrayList<>();

    public String makeReport() {
        StringBuilder report = new StringBuilder();

        Iterator<String> it = _history.iterator(); int index = 1;
        while(it.hasNext()) {
            report.append(index).append(": ").append(it.next());

            if (it.hasNext()) {
                report.append("\n");
                ++index;
            }
        }

        return report.toString();
    }

    /*
    TODO {
        _history.add("Employee " + employeeToString() + " was enrolled in department \"" + getCurrentDepartment().getName() + "\"");

        _history.add("Employee " + employeeToString() + " was transferred from department \"" + getPrevDepartment().getName() + "\"  to department \"" + getCurrentDepartment().getName() + "\"");

        _history.add("Employee " + employeeToString() + " was dismissed from department \"" + getPrevDepartment().getName() + "\"");

        _history.add("Employee " + employeeToString() + " was received a new qualification \"" + getCurrentEducation() + "\"");
     } TODO
     */

    private String employeeToString(Employee e) {
        String log = e.getFIO();
        if(e.getIdCard() != null) {
            log = log + " (ID: " + e.getIdCard().getNumber() + ")";
        }
        return log;
    }

    /**
     * @param e
     */
    @Override
    public void employeeEnrolled(EmployeeEvent e) {

    }

    /**
     * @param e
     */
    @Override
    public void employeeTransferred(EmployeeEvent e) {

    }

    /**
     * @param e
     */
    @Override
    public void employeeDismissed(EmployeeEvent e) {

    }

    /**
     * @param e
     */
    @Override
    public void employeeStudied(EmployeeEvent e) {

    }
}
