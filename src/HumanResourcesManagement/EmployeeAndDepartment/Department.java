package HumanResourcesManagement.EmployeeAndDepartment;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Department {
    // -------------------------------------------------------------------------------------
    private final String _name;

    public String getName() {
        return _name;
    }

    private final int _maxHeadcount;
    private static final Set<Department> _allDepartments = new HashSet<>();

    public Department(String name, int maxHeadcount) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Имя отдела не может быть пустым");
        }
        if (maxHeadcount <= 0) {
            throw new IllegalArgumentException("Отдел должен комплектоваться хотя бы одним работником");
        }
        if (_allDepartments.stream().anyMatch((e) -> e.getName().equals(name))) {
            throw new IllegalArgumentException("Имя отдела должно быть уникальным");
        }

        _name = name;
        _maxHeadcount = maxHeadcount;
        _allDepartments.add(this);
    }

    // -------------------------------------------------------------------------------------
    // TODO {
    public int getHeadcount() {
        return 0;
    }

    boolean acceptEmployee(Employee employee) {
        return false;
    }

    // При увольнении работник обязательно будет отпущен отделом
    boolean releaseEmployee(Employee employee, boolean isTransferring) {
        return false;
    }

    public Iterator<Employee> iterator() {
        return null;
    }
    // TODO }
}