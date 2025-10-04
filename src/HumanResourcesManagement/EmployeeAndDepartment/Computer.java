package HumanResourcesManagement.EmployeeAndDepartment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Computer {
    // -------------------------------------------------------------------------------------
    private final int _id;

    public int getId() {
        return _id;
    }

    public Computer(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Инвентарный номер должен быть >= 1");
        }
        _id = id;
    }

    // TODO {
    public boolean isOccupied() {
        return false;
    }
    // } TODO

    // -------------------------------------------------------------------------------------
    private final List<String> _history = new ArrayList<>();

    public void startUsingBy(Employee employee) {
        // TODO {
        //    throw new RuntimeException("Компьютер занят");
        // } TODO

        // TODO {
        //_history.add("Computer has been assigned to employee " + getFIO() + " since " + toShortStyle(LocalDateTime.now()));
        // } TODO
    }

    public void stopUsing() {
        // TODO {
        //_history.add("Computer is released by employee " + getFIO() + " on " + toShortStyle(LocalDateTime.now()));
        // } TODO
    }

    // -------------------------------------------------------------------------------------
    private LocalDateTime _startSession;

    public boolean isSessionContinuing() {
        return _startSession != null;
    }

    public void startSession() {
        // TODO {
        //    throw new RuntimeException("У компьютера нет владельца");
        // } TODO
        // TODO {
        //    throw new RuntimeException("Нельзя начать новую сессию, пока не завершилась предыдущая");
        // TODO {

        _startSession = LocalDateTime.now();
    }

    public void stopSession() {
        // TODO {
        //    throw new RuntimeException("У компьютера нет владельца");
        // } TODO
        // TODO {
        //    throw new RuntimeException("Сессия еще не начата");
        // } TODO

        // TODO {
        //_history.add("Employee" + getFIO() + " used computer since " + toShortStyle(_startSession) +
        //        " to " + toShortStyle(LocalDateTime.now()));
        // } TODO
    }

    private String toShortStyle(LocalDateTime dt) {
        return dt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT));
    }

    public String makeReport() {
        StringBuilder report = new StringBuilder();

        Iterator<String> it = _history.iterator();
        while (it.hasNext()) {
            report.append(it.next());
            if (it.hasNext()) {
                report.append("\n");
            }
        }

        // TODO {
        if (isSessionContinuing()) {
        //    report.append("\nEmployee ").append(getFIO()).append(" used computer since ").append(toShortStyle(_startSession)).append(" until the current time");
        }
        // } TODO

        return report.toString();
    }
}