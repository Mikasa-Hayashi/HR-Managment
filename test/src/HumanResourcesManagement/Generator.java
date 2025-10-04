package HumanResourcesManagement;

public class Generator {
    private static int _number = 0;

    public static String generateUniqueName(String name) {
        _number++;
        return name + " (" + _number + ")";
    }
}

