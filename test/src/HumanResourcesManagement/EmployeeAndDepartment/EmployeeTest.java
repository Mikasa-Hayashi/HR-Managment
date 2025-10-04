package HumanResourcesManagement.EmployeeAndDepartment;

import HumanResourcesManagement.Education;
import HumanResourcesManagement.Generator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmployeeTest {
    private int _signalCount;   // кол-во событий, сгенерированных во время теста
    private IdCard _card;

    @BeforeEach
    public void init() {
        _signalCount = 0;
        _card = null;
    }

    private class EmployeeObserver implements EmployeeListener {
        public EmployeeObserver() {
            _signalCount = 0;
        }

        @Override
        public void employeeEnrolled(EmployeeEvent e) {
            _signalCount++;
        }

        @Override
        public void employeeTransferred(EmployeeEvent e) {
            _signalCount++;
        }

        @Override
        public void employeeDismissed(EmployeeEvent e) {
            _signalCount++;
        }

        @Override
        public void employeeStudied(EmployeeEvent e) {
            _signalCount++;
        }
    }

    @Test
    public void unemployedPerson() {
        Education ed = Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER);
        Employee emp = new Employee("Иванов Иван Иванович", ed);

        Assertions.assertFalse(emp.isWorks());
        Assertions.assertNull(emp.getDepartment());
        Assertions.assertNull(emp.getIdCard());
        Assertions.assertEquals(ed, emp.getEducation());
    }

    @Test
    public void enroll() {
        Education ed = Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER);
        Employee emp = new Employee("Иванов Иван Иванович", ed);
        Department dep = new Department(Generator.generateUniqueName("Отдел тестирования"), 10);

        emp.addEmployeeListener(new EmployeeObserver() {
            @Override
            public void employeeEnrolled(EmployeeEvent e) {
                super.employeeEnrolled(e);

                Assertions.assertTrue(emp.isWorks());
                Assertions.assertEquals(dep, emp.getDepartment());
                Assertions.assertEquals(1, emp.getDepartment().getHeadcount());
                Assertions.assertNotNull(emp.getIdCard());
                Assertions.assertTrue(emp.getIdCard().isActive());
                Assertions.assertEquals(ed, emp.getEducation());

                Assertions.assertEquals(emp, e.getSource());
                Assertions.assertNull(e.getPrevDepartment());
                Assertions.assertEquals(dep, e.getCurrentDepartment());
                Assertions.assertEquals(ed, e.getPrevEducation());
                Assertions.assertEquals(ed, e.getCurrentEducation());
            }
        });

        boolean isOk = emp.enrollIn(dep);

        Assertions.assertTrue(isOk);
        Assertions.assertEquals(1, _signalCount);
        Assertions.assertTrue(emp.isWorks());
        Assertions.assertEquals(dep, emp.getDepartment());
        Assertions.assertEquals(1, emp.getDepartment().getHeadcount());
        Assertions.assertNotNull(emp.getIdCard());
        Assertions.assertTrue(emp.getIdCard().isActive());
        Assertions.assertEquals(ed, emp.getEducation());
    }

    @Test
    public void enrollTwice() {
        Education ed = Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER);
        Employee emp = new Employee("Иванов Иван Иванович", ed);
        Department testingDep = new Department(Generator.generateUniqueName("Отдел тестирования"), 10);
        Department analyticsDep = new Department(Generator.generateUniqueName("Аналитический отдел"), 10);

        emp.addEmployeeListener(new EmployeeObserver());

        emp.enrollIn(testingDep);
        _card = emp.getIdCard();

        Assertions.assertThrows(RuntimeException.class, () ->
                emp.enrollIn(analyticsDep)
        );

        Assertions.assertEquals(1, _signalCount);
        Assertions.assertTrue(emp.isWorks());
        Assertions.assertEquals(testingDep, emp.getDepartment());
        Assertions.assertEquals(1, emp.getDepartment().getHeadcount());
        Assertions.assertEquals(_card, emp.getIdCard());
        Assertions.assertTrue(_card.isActive());
        Assertions.assertEquals(ed, emp.getEducation());
    }

    @Test
    public void enrollInOvercrowdedDepartment() {
        Education ed = Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER);
        Employee emp_I = new Employee("Иванов Иван Иванович", ed);
        Employee emp_P = new Employee("Петров Петр Петрович", ed);
        Employee emp_S = new Employee("Сидоров Сидор Сидорович", ed);
        Department dep = new Department(Generator.generateUniqueName("Отдел тестирования"), 2);

        emp_I.addEmployeeListener(new EmployeeObserver());
        emp_P.addEmployeeListener(new EmployeeObserver());
        emp_S.addEmployeeListener(new EmployeeObserver());

        emp_I.enrollIn(dep);
        emp_P.enrollIn(dep);
        boolean isOk = emp_S.enrollIn(dep);

        Assertions.assertFalse(isOk);

        Assertions.assertEquals(2, _signalCount);
        Assertions.assertEquals(2, dep.getHeadcount());

        Assertions.assertFalse(emp_S.isWorks());
        Assertions.assertNull(emp_S.getDepartment());

        Assertions.assertNull(emp_S.getIdCard());
        Assertions.assertEquals(ed, emp_S.getEducation());
    }

    @Test
    public void dismissUnemployedPerson() {
        Education ed = Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER);
        Employee emp = new Employee("Иванов Иван Иванович", ed);

        emp.addEmployeeListener(new EmployeeObserver());

        Assertions.assertThrows(RuntimeException.class, emp::dismiss);

        Assertions.assertEquals(0, _signalCount);
        Assertions.assertFalse(emp.isWorks());
        Assertions.assertNull(emp.getDepartment());
        Assertions.assertNull(emp.getIdCard());
        Assertions.assertEquals(ed, emp.getEducation());
    }

    @Test
    public void dismiss() {
        Education ed = Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER);
        Employee emp = new Employee("Иванов Иван Иванович", ed);
        Department dep = new Department(Generator.generateUniqueName("Отдел тестирования"), 10);

        emp.addEmployeeListener(new EmployeeObserver() {
            @Override
            public void employeeDismissed(EmployeeEvent e) {
                super.employeeEnrolled(e);

                Assertions.assertFalse(emp.isWorks());
                Assertions.assertNull(emp.getDepartment());
                Assertions.assertEquals(0, dep.getHeadcount());
                Assertions.assertNull(emp.getIdCard());
                Assertions.assertFalse(_card.isActive());
                Assertions.assertEquals(ed, emp.getEducation());

                Assertions.assertEquals(emp, e.getSource());
                Assertions.assertEquals(dep, e.getPrevDepartment());
                Assertions.assertNull(e.getCurrentDepartment());
                Assertions.assertEquals(ed, e.getPrevEducation());
                Assertions.assertEquals(ed, e.getCurrentEducation());
            }
        });

        emp.enrollIn(dep);
        _card = emp.getIdCard();

        emp.dismiss();

        Assertions.assertEquals(2, _signalCount);
        Assertions.assertFalse(emp.isWorks());
        Assertions.assertNull(emp.getDepartment());
        Assertions.assertEquals(0, dep.getHeadcount());
        Assertions.assertNull(emp.getIdCard());
        Assertions.assertFalse(_card.isActive());
        Assertions.assertEquals(ed, emp.getEducation());
    }

    @Test
    public void dismissTwice() {
        Education ed = Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER);
        Employee emp = new Employee("Иванов Иван Иванович", ed);
        Department dep = new Department(Generator.generateUniqueName("Отдел тестирования"), 10);

        emp.addEmployeeListener(new EmployeeObserver());

        emp.enrollIn(dep);
        _card = emp.getIdCard();

        emp.dismiss();
        Assertions.assertThrows(RuntimeException.class, emp::dismiss);

        Assertions.assertEquals(2, _signalCount);
        Assertions.assertFalse(emp.isWorks());
        Assertions.assertNull(emp.getDepartment());
        Assertions.assertEquals(0, dep.getHeadcount());
        Assertions.assertNull(emp.getIdCard());
        Assertions.assertFalse(_card.isActive());
        Assertions.assertEquals(ed, emp.getEducation());
    }

    @Test
    public void enrollAgainIntoSameDepartment() {
        Education ed = Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER);
        Employee emp = new Employee("Иванов Иван Иванович", ed);
        Department dep = new Department(Generator.generateUniqueName("Отдел тестирования"), 10);

        emp.addEmployeeListener(new EmployeeObserver() {
            @Override
            public void employeeEnrolled(EmployeeEvent e) {
                super.employeeEnrolled(e);

                Assertions.assertTrue(emp.isWorks());
                Assertions.assertEquals(dep, emp.getDepartment());
                Assertions.assertEquals(1, emp.getDepartment().getHeadcount());
                Assertions.assertNotNull(emp.getIdCard());
                Assertions.assertTrue(emp.getIdCard().isActive());
                Assertions.assertEquals(ed, emp.getEducation());

                Assertions.assertEquals(emp, e.getSource());
                Assertions.assertNull(e.getPrevDepartment());
                Assertions.assertEquals(dep, e.getCurrentDepartment());
                Assertions.assertEquals(ed, e.getPrevEducation());
                Assertions.assertEquals(ed, e.getCurrentEducation());
            }
        });

        emp.enrollIn(dep);
        _card = emp.getIdCard();
        emp.dismiss();
        boolean isOk = emp.enrollIn(dep);

        Assertions.assertTrue(isOk);
        Assertions.assertEquals(3, _signalCount);
        Assertions.assertTrue(emp.isWorks());
        Assertions.assertEquals(dep, emp.getDepartment());
        Assertions.assertEquals(1, emp.getDepartment().getHeadcount());
        Assertions.assertNotNull(emp.getIdCard());
        Assertions.assertTrue(emp.getIdCard().isActive());
        Assertions.assertNotEquals(_card, emp.getIdCard());
        Assertions.assertEquals(ed, emp.getEducation());
    }

    @Test
    public void transferUnemployedPerson() {
        Education ed = Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER);
        Employee emp = new Employee("Иванов Иван Иванович", ed);
        Department dep = new Department(Generator.generateUniqueName("Отдел тестирования"), 10);

        emp.addEmployeeListener(new EmployeeObserver());

        Assertions.assertThrows(RuntimeException.class, () ->
                emp.transferTo(dep)
        );

        Assertions.assertEquals(0, _signalCount);
        Assertions.assertFalse(emp.isWorks());
        Assertions.assertNull(emp.getDepartment());
        Assertions.assertEquals(0, dep.getHeadcount());
        Assertions.assertNull(emp.getIdCard());
        Assertions.assertEquals(ed, emp.getEducation());
    }

    @Test
    public void transfer() {
        Education ed = Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER);
        Employee emp = new Employee("Иванов Иван Иванович", ed);
        Department testingDep = new Department(Generator.generateUniqueName("Отдел тестирования"), 10);
        Department analyticsDep = new Department(Generator.generateUniqueName("Аналитический отдел"), 10);

        emp.addEmployeeListener(new EmployeeObserver() {
            @Override
            public void employeeTransferred(EmployeeEvent e) {
                super.employeeTransferred(e);

                Assertions.assertTrue(emp.isWorks());
                Assertions.assertEquals(analyticsDep, emp.getDepartment());
                Assertions.assertEquals(1, emp.getDepartment().getHeadcount());
                Assertions.assertEquals(_card, emp.getIdCard());
                Assertions.assertTrue(emp.getIdCard().isActive());
                Assertions.assertEquals(ed, emp.getEducation());

                Assertions.assertEquals(emp, e.getSource());
                Assertions.assertEquals(testingDep, e.getPrevDepartment());
                Assertions.assertEquals(0, testingDep.getHeadcount());
                Assertions.assertEquals(analyticsDep, e.getCurrentDepartment());
                Assertions.assertEquals(ed, e.getPrevEducation());
                Assertions.assertEquals(ed, e.getCurrentEducation());
            }
        });

        emp.enrollIn(testingDep);
        _card = emp.getIdCard();
        boolean isOk = emp.transferTo(analyticsDep);

        Assertions.assertTrue(isOk);
        Assertions.assertEquals(2, _signalCount);
        Assertions.assertTrue(emp.isWorks());
        Assertions.assertEquals(analyticsDep, emp.getDepartment());
        Assertions.assertEquals(1, emp.getDepartment().getHeadcount());
        Assertions.assertEquals(_card, emp.getIdCard());
        Assertions.assertTrue(emp.getIdCard().isActive());
        Assertions.assertEquals(ed, emp.getEducation());
    }

    @Test
    public void transferIntoSameDepartment() {
        Education ed = Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER);
        Employee emp = new Employee("Иванов Иван Иванович", ed);
        Department dep = new Department(Generator.generateUniqueName("Отдел тестирования"), 10);

        emp.addEmployeeListener(new EmployeeObserver());

        emp.enrollIn(dep);
        _card = emp.getIdCard();
        Assertions.assertThrows(RuntimeException.class, () ->
                emp.transferTo(dep)
        );

        Assertions.assertEquals(1, _signalCount);
        Assertions.assertTrue(emp.isWorks());
        Assertions.assertEquals(dep, emp.getDepartment());
        Assertions.assertEquals(1, emp.getDepartment().getHeadcount());
        Assertions.assertEquals(_card, emp.getIdCard());
        Assertions.assertTrue(emp.getIdCard().isActive());
        Assertions.assertEquals(ed, emp.getEducation());
    }

    @Test
    public void transferInOvercrowdedDepartment() {
        Education ed = Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER);
        Employee emp_I = new Employee("Иванов Иван Иванович", ed);
        Employee emp_P = new Employee("Петров Петр Петрович", ed);
        Employee emp_S = new Employee("Сидоров Сидор Сидорович", ed);
        Department testingDep = new Department(Generator.generateUniqueName("Отдел тестирования"), 2);
        Department analyticsDep = new Department(Generator.generateUniqueName("Аналитический отдел"), 2);

        emp_I.addEmployeeListener(new EmployeeObserver());
        emp_P.addEmployeeListener(new EmployeeObserver());
        emp_S.addEmployeeListener(new EmployeeObserver());

        emp_I.enrollIn(testingDep);
        emp_P.enrollIn(testingDep);
        emp_S.enrollIn(analyticsDep);
        _card = emp_S.getIdCard();

        boolean isOk = emp_S.transferTo(testingDep);

        Assertions.assertTrue(isOk);

        Assertions.assertEquals(3, _signalCount);
        Assertions.assertEquals(2, testingDep.getHeadcount());
        Assertions.assertEquals(1, analyticsDep.getHeadcount());

        Assertions.assertTrue(emp_S.isWorks());
        Assertions.assertEquals(analyticsDep, emp_S.getDepartment());

        Assertions.assertEquals(_card, emp_S.getIdCard());
        Assertions.assertTrue(emp_S.getIdCard().isActive());
        Assertions.assertEquals(ed, emp_S.getEducation());
    }

    @Test
    public void cyclicTransfer() {
        Education ed = Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER);
        Employee emp = new Employee("Иванов Иван Иванович", ed);
        Department testingDep = new Department(Generator.generateUniqueName("Отдел тестирования"), 2);
        Department analyticsDep = new Department(Generator.generateUniqueName("Аналитический отдел"), 2);

        emp.addEmployeeListener(new EmployeeObserver());

        emp.enrollIn(testingDep);
        _card = emp.getIdCard();
        emp.transferTo(analyticsDep);
        boolean isOk = emp.transferTo(testingDep);

        Assertions.assertTrue(isOk);

        Assertions.assertEquals(3, _signalCount);
        Assertions.assertEquals(1, testingDep.getHeadcount());
        Assertions.assertEquals(0, analyticsDep.getHeadcount());

        Assertions.assertTrue(emp.isWorks());
        Assertions.assertEquals(testingDep, emp.getDepartment());

        Assertions.assertEquals(_card, emp.getIdCard());
        Assertions.assertTrue(emp.getIdCard().isActive());
        Assertions.assertEquals(ed, emp.getEducation());
    }

    @Test
    public void nextOnlyDegreeOfStudyForUnemployedPerson() {
        Education baseEd = Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER);
        Education nextEd = Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER).nextDegree();

        Employee emp = new Employee("Иванов Иван Иванович", baseEd);
        emp.addEmployeeListener(new EmployeeObserver());

        emp.completeNextDegreeOfStudy();

        Assertions.assertEquals(1, _signalCount);
        Assertions.assertFalse(emp.isWorks());
        Assertions.assertNull(emp.getDepartment());
        Assertions.assertNull(emp.getIdCard());
        Assertions.assertEquals(nextEd, emp.getEducation());
    }

    @Test
    public void noNextOnlyDegreeOfStudyForUnemployedPerson() {
        Education baseEd = Education.doctoralOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER);

        Employee emp = new Employee("Иванов Иван Иванович", baseEd);
        emp.addEmployeeListener(new EmployeeObserver());

        emp.completeNextDegreeOfStudy();

        Assertions.assertEquals(0, _signalCount);
        Assertions.assertFalse(emp.isWorks());
        Assertions.assertNull(emp.getDepartment());
        Assertions.assertNull(emp.getIdCard());
        Assertions.assertEquals(baseEd, emp.getEducation());
    }

    @Test
    public void nextDegreeWithChangeSpecialityForUnemployedPerson() {
        Education baseEd = Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER);
        Education nextEd = Education.bachelorOf(Education.Speciality.SPECIALITY_ROBOTICS).nextDegree();

        Employee emp = new Employee("Иванов Иван Иванович", baseEd);
        emp.addEmployeeListener(new EmployeeObserver());

        emp.completeNextDegreeOfStudy(Education.Speciality.SPECIALITY_ROBOTICS);

        Assertions.assertEquals(1, _signalCount);
        Assertions.assertFalse(emp.isWorks());
        Assertions.assertNull(emp.getDepartment());
        Assertions.assertNull(emp.getIdCard());
        Assertions.assertEquals(nextEd, emp.getEducation());
    }

    @Test
    public void nextOnlyDegreeOfStudyForWorkingPerson() {
        Education baseEd = Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER);
        Education nextEd = Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER).nextDegree();
        Employee emp = new Employee("Иванов Иван Иванович", baseEd);

        Department dep = new Department(Generator.generateUniqueName("Отдел тестирования"), 10);

        emp.addEmployeeListener(new EmployeeObserver() {
            @Override
            public void employeeStudied(EmployeeEvent e) {
                super.employeeStudied(e);

                Assertions.assertTrue(emp.isWorks());
                Assertions.assertEquals(dep, emp.getDepartment());
                Assertions.assertEquals(_card, emp.getIdCard());
                Assertions.assertTrue(emp.getIdCard().isActive());
                Assertions.assertEquals(nextEd, emp.getEducation());

                Assertions.assertEquals(emp, e.getSource());
                Assertions.assertEquals(dep, e.getPrevDepartment());
                Assertions.assertEquals(dep, e.getCurrentDepartment());
                Assertions.assertEquals(baseEd, e.getPrevEducation());
                Assertions.assertEquals(nextEd, e.getCurrentEducation());

            }
        });

        emp.enrollIn(dep);
        _card = emp.getIdCard();
        emp.completeNextDegreeOfStudy();

        Assertions.assertEquals(2, _signalCount);
        Assertions.assertTrue(emp.isWorks());
        Assertions.assertEquals(dep, emp.getDepartment());
        Assertions.assertEquals(_card, emp.getIdCard());
        Assertions.assertTrue(emp.getIdCard().isActive());
        Assertions.assertEquals(nextEd, emp.getEducation());
    }

    @Test
    public void nextOnlyDegreeWithChangeSpecialityForWorkingPerson() {
        Education baseEd = Education.bachelorOf(Education.Speciality.SPECIALITY_SOFTWARE_ENGINEER);
        Education nextEd = Education.bachelorOf(Education.Speciality.SPECIALITY_ROBOTICS).nextDegree();
        Employee emp = new Employee("Иванов Иван Иванович", baseEd);

        Department dep = new Department(Generator.generateUniqueName("Отдел тестирования"), 10);

        emp.addEmployeeListener(new EmployeeObserver() {
            @Override
            public void employeeStudied(EmployeeEvent e) {
                super.employeeStudied(e);

                Assertions.assertTrue(emp.isWorks());
                Assertions.assertEquals(dep, emp.getDepartment());
                Assertions.assertEquals(_card, emp.getIdCard());
                Assertions.assertTrue(emp.getIdCard().isActive());
                Assertions.assertEquals(nextEd, emp.getEducation());

                Assertions.assertEquals(emp, e.getSource());
                Assertions.assertEquals(dep, e.getPrevDepartment());
                Assertions.assertEquals(dep, e.getCurrentDepartment());
                Assertions.assertEquals(baseEd, e.getPrevEducation());
                Assertions.assertEquals(nextEd, e.getCurrentEducation());

            }
        });

        emp.enrollIn(dep);
        _card = emp.getIdCard();
        emp.completeNextDegreeOfStudy(Education.Speciality.SPECIALITY_ROBOTICS);

        Assertions.assertEquals(2, _signalCount);
        Assertions.assertTrue(emp.isWorks());
        Assertions.assertEquals(dep, emp.getDepartment());
        Assertions.assertEquals(_card, emp.getIdCard());
        Assertions.assertTrue(emp.getIdCard().isActive());
        Assertions.assertEquals(nextEd, emp.getEducation());
    }
}