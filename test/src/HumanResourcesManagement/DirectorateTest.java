package HumanResourcesManagement;

import HumanResourcesManagement.EmployeeAndDepartment.Department;
import HumanResourcesManagement.EmployeeAndDepartment.Employee;
import HumanResourcesManagement.EmployeeAndDepartment.IdCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/* TODO: нужно проверить как дирекция формирует кадровую историю работников  */
public class DirectorateTest {

    @Test
    public void studyNextDegreeWithSameSpeciality() {
        String FIO = "Иванов Иван Иванович";
        Education ed = Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER);
        Employee emp = new Employee(FIO, ed);

        Directorate directorate = new Directorate();
        emp.addEmployeeListener(directorate);

        emp.completeNextDegreeOfStudy();

        String expReport = "1: Employee " + FIO + " was received a new qualification \"" + ed.nextDegree() + "\"";
        Assertions.assertEquals(expReport, directorate.makeReport());
    }

    @Test
    public void studyNextDegreeWithOtherSpeciality() {
        String FIO = "Иванов Иван Иванович";
        Education ed = Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER);
        Employee emp = new Employee(FIO, ed);

        Directorate directorate = new Directorate();
        emp.addEmployeeListener(directorate);

        Education.Speciality new_sp = Education.Speciality.SPECIALITY_ROBOTICS;
        emp.completeNextDegreeOfStudy(new_sp);

        String expReport = "1: Employee " + FIO + " was received a new qualification \"" + ed.nextDegree(new_sp) + "\"";
        Assertions.assertEquals(expReport, directorate.makeReport());
    }

    @Test
    public void enroll() {
        String FIO = "Иванов Иван Иванович";
        Employee emp = new Employee(FIO, Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER));

        Directorate directorate = new Directorate();

        String dep_name = Generator.generateUniqueName("Отдел тестирования");
        Department dep = new Department(dep_name, 10);

        emp.addEmployeeListener(directorate);
        emp.enrollIn(dep);

        String expReport = "1: Employee " + FIO + " (ID: " + emp.getIdCard().getNumber()  + ") was enrolled in department \"" + dep_name + "\"";
        Assertions.assertEquals(expReport, directorate.makeReport());
    }

    @Test
    public void dismiss() {
        String FIO = "Иванов Иван Иванович";
        Employee emp = new Employee(FIO, Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER));

        Directorate directorate = new Directorate();
        emp.addEmployeeListener(directorate);

        String dep_name = Generator.generateUniqueName("Отдел тестирования");
        Department dep = new Department(dep_name, 10);

        emp.enrollIn(dep);
        IdCard card = emp.getIdCard();
        emp.dismiss();

        String expReport = "1: Employee " + FIO + " (ID: " + card.getNumber()  + ") was enrolled in department \"" + dep_name + "\"";
        expReport = expReport + "\n2: Employee " + FIO + " was dismissed from department \"" + dep_name + "\"";
        Assertions.assertEquals(expReport, directorate.makeReport());
    }

    @Test
    public void transfer() {
        String FIO = "Иванов Иван Иванович";
        Employee emp = new Employee(FIO, Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER));

        Directorate directorate = new Directorate();
        emp.addEmployeeListener(directorate);

        String base_dep_name = Generator.generateUniqueName("Отдел тестирования");
        Department bas_dep = new Department(base_dep_name, 10);

        String next_dep_name = Generator.generateUniqueName("Отдел аналитики");
        Department next_dep = new Department(next_dep_name, 10);

        emp.enrollIn(bas_dep);
        IdCard card = emp.getIdCard();
        emp.transferTo(next_dep);

        String expReport = "1: Employee " + FIO + " (ID: " + card.getNumber()  + ") was enrolled in department \"" + base_dep_name + "\"";
        expReport = expReport + "\n2: Employee " + FIO + " (ID: " + card.getNumber()  + ") was transferred from department \"" + base_dep_name + "\"  to department \"" + next_dep_name + "\"";
        Assertions.assertEquals(expReport, directorate.makeReport());
    }
}

