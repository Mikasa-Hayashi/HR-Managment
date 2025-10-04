import HumanResourcesManagement.Directorate;
import HumanResourcesManagement.Education;
import HumanResourcesManagement.EmployeeAndDepartment.Computer;
import HumanResourcesManagement.EmployeeAndDepartment.Department;
import HumanResourcesManagement.EmployeeAndDepartment.Employee;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        Computer c = new Computer(1);
        Employee e = new Employee("hh", Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER));
        Directorate dd = new Directorate();
        e.addEmployeeListener(dd);

        String s = e.getEducation().toString();

        c.startUsingBy(e);
        c.startSession();

        String ss = c.makeReport();

        Department dep = new Department("Testing", 10);
        e.enrollIn(dep);

        e.completeNextDegreeOfStudy();

        String sss = dd.makeReport();

        int a = 1;

    }
}