package HumanResourcesManagement.EmployeeAndDepartment;

import HumanResourcesManagement.Education;
import HumanResourcesManagement.Generator;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/* TODO: нужно проверить корректность работы с сессиями */
public class ComputerTest {


    @Test
    public void giveComputerToUnemployedPerson() {
        String FIO = "Иванов Иван Иванович";
        Employee emp = new Employee(FIO, Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER));

        Computer comp = new Computer(1);

        Assertions.assertThrows(RuntimeException.class, () ->
            comp.startUsingBy(emp)
        );

        String report = comp.makeReport();

        Assertions.assertNull(emp.getComputer());
        Assertions.assertFalse(comp.isOccupied());
        Assertions.assertTrue(report.isBlank());
    }

    @Test
    public void giveComputerToEmployee() {
        String FIO = "Иванов Иван Иванович";
        Employee emp = new Employee(FIO, Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER));
        Department dep = new Department(Generator.generateUniqueName("Отдел тестирования"), 10);
        emp.enrollIn(dep);

        Computer comp = new Computer(1);

        comp.startUsingBy(emp);

        String report = comp.makeReport();

        Assertions.assertEquals(comp, emp.getComputer());
        Assertions.assertTrue(comp.isOccupied());
        Assertions.assertTrue(report.startsWith("Computer has been assigned to employee " + FIO + " since "));
    }

    @Test
    public void giveSameComputerToEmployee() {
        String FIO = "Иванов Иван Иванович";
        Employee emp = new Employee(FIO, Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER));
        Department dep = new Department(Generator.generateUniqueName("Отдел тестирования"), 10);
        emp.enrollIn(dep);

        Computer comp = new Computer(1);

        comp.startUsingBy(emp);
        comp.startUsingBy(emp);

        String report = comp.makeReport();

        Assertions.assertEquals(comp, emp.getComputer());
        Assertions.assertTrue(comp.isOccupied());
        Assertions.assertTrue(report.startsWith("Computer has been assigned to employee " + FIO + " since "));
    }

    @Test
    public void giveTwoComputers() {
        String FIO = "Иванов Иван Иванович";
        Employee emp = new Employee(FIO, Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER));
        Department dep = new Department(Generator.generateUniqueName("Отдел тестирования"), 10);
        emp.enrollIn(dep);

        Computer comp1 = new Computer(1);
        Computer comp2 = new Computer(2);

        comp1.startUsingBy(emp);
        Assert.assertThrows(RuntimeException.class, () -> comp2.startUsingBy(emp) );
        String report = comp2.makeReport();

        Assertions.assertEquals(comp1, emp.getComputer());
        Assertions.assertTrue(comp1.isOccupied());
        Assertions.assertFalse(comp2.isOccupied());
        Assertions.assertEquals("", report);
    }

    @Test
    public void takeComputer() {
        Employee emp = new Employee("Иванов Иван Иванович",
                Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER));
        Department dep = new Department(Generator.generateUniqueName("Отдел тестирования"), 10);
        emp.enrollIn(dep);

        Computer comp = new Computer(1);

        comp.startUsingBy(emp);
        comp.stopUsing();
        String report = comp.makeReport();

        Assertions.assertNull(emp.getComputer());
        Assertions.assertFalse(comp.isOccupied());
        // TODO {
        Assertions.assertEquals("", report);
        // } TODO
    }

    @Test
    public void giveOccupiedComputer() {
        String FIO = "Иванов Иван Иванович";
        Employee emp1 = new Employee(FIO, Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER));
        Employee emp2 = new Employee("Петров Петр Петрович",
                Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER));
        Department dep = new Department(Generator.generateUniqueName("Отдел тестирования"), 10);
        emp1.enrollIn(dep);
        emp2.enrollIn(dep);

        Computer comp = new Computer(1);

        comp.startUsingBy(emp1);
        Assert.assertThrows(RuntimeException.class, () -> comp.startUsingBy(emp2));
        String report = comp.makeReport();

        Assertions.assertEquals(comp, emp1.getComputer());
        Assertions.assertNull(emp2.getComputer());
        Assertions.assertTrue(comp.isOccupied());
        Assertions.assertTrue(report.startsWith("Computer has been assigned to employee " + FIO + " since "));
    }
}
