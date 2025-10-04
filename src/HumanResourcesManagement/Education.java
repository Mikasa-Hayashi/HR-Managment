package HumanResourcesManagement;

import java.util.Arrays;

public class Education {
    // -------------------------------------------------------------------------------------
    private final Speciality _speciality;
    public Speciality getSpeciality() {
        return _speciality;
    }

    // -------------------------------------------------------------------------------------
    private final Degree _degree;
    public Degree getDegree() {
        return _degree;
    }

    // -------------------------------------------------------------------------------------
    private Education(Speciality speciality, Degree degree) {
        _speciality = speciality;
        _degree = degree;
    }

    public static Education studentOf(Speciality speciality) {
        return new Education(speciality, Education.Degree.DEGREE_STUDENT);
    }

    public static Education bachelorOf(Speciality speciality) {
        return new Education(speciality, Education.Degree.DEGREE_BACHELOR);
    }

    public static Education specialistOf(Speciality speciality) {
        return new Education(speciality, Education.Degree.DEGREE_SPECIALIST);
    }

    public static Education masterOf(Speciality speciality) {
        return new Education(speciality, Education.Degree.DEGREE_MASTER);
    }

    public static Education postgraduateOf(Speciality speciality) {
        return new Education(speciality, Education.Degree.DEGREE_POSTGRADUATE);
    }

    public static Education doctoralOf(Speciality speciality) {
        return new Education(speciality, Education.Degree.DEGREE_DOCTORAL);
    }

    public Education nextDegree() {
        return nextDegree(getSpeciality());
    }

    public Education nextDegree(Speciality speciality) {
        Degree nextDegree = _degree.next();
        return nextDegree != null ? new Education(speciality, nextDegree) : null;
    }

    // -------------------------------------------------------------------------------------
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        } else if (!(other instanceof Education otherEducation)) {
            return false;
        } else {
            return getDegree() == otherEducation.getDegree() && getSpeciality() == otherEducation.getSpeciality();
        }
    }

    public String toString() {
        return _degree + " of " + _speciality;
    }

    // -------------------------------------------------------------------------------------
    public enum Speciality {
        SPECIALITY_SOFTWARE_ENGINEER("Software Engineer"),
        SPECIALITY_INFORM_AND_COMP_SYSTEMS("Information and Computer Systems"),
        SPECIALITY_INFORM_SECURITY("Information Security"),
        SPECIALITY_ROBOTICS("Robotics");

        private final String _name;

        Speciality(String name) { _name = name; }

        public String toString() {
            return _name;
        }
    }

    // -------------------------------------------------------------------------------------
    public enum Degree {
        DEGREE_STUDENT("Student"),
        DEGREE_BACHELOR("Bachelor"),
        DEGREE_SPECIALIST("Specialist"),
        DEGREE_MASTER("Master"),
        DEGREE_POSTGRADUATE("Postgraduate"),
        DEGREE_DOCTORAL("Doctoral");

        private final String _name;

        Degree(String name) {
            _name = name;
        }

        public Degree next() {
            return Arrays.stream(values()).filter(e -> e.ordinal() == ordinal() + 1).findFirst().orElse(null);
        }

        public String toString() { return _name; }
    }
}
