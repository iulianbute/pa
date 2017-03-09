/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palaborator2;

import com.google.gson.JsonObject;

/**
 * clasa modeleaza problema matching-ului dintre o lista de studenti si o lista
 * de proiecte pe baza unei liste de preferinte fata de proiecte a studentilor
 * si a unei liste de prioritati ale studentilor in participarea la proiecte. in
 * urma rezolvarii, studentii ce nu se pot atribui catre proiectele preferate
 * vor fi atribuiti catre proiectele ramase cu loc liber
 *
 * @author Iulian
 */
public class Problem {

    // constante folosite la serializare si deserializare de date, reprezentand numele campurilor
    static final String STUDS_PROP = "Studenti";
    static final String PROJS_PROP = "Proiecte";
    static final String PROJS_PREFS_PROP = "Preferintele profesorilor";
    static final String STUDS_PREFS_PROP = "Preferintele studentilor";

    /**
     * lista de studenti,
     */
    protected Students studs;

    /**
     * lista proiectelor
     */
    protected Projects projs;

    /**
     * lista preferintelor studentilor
     */
    protected StudentPreferencesSet studsPrefs;

    /**
     * lista prioritatilor studentilor la fiecare proiect
     */
    protected ProjectPreferencesSet projsPrefs;

    Problem() {
    }

    /**
     * constructor al problemei din obiect serializat
     *
     * @param jsonProblem datele problemei in format serializat
     */
    Problem(JsonObject jsonProblem) {
        studs = new Students(jsonProblem.getAsJsonArray(STUDS_PROP));
        projs = new Projects(jsonProblem.getAsJsonArray(PROJS_PROP));
        studsPrefs = new StudentPreferencesSet(jsonProblem.getAsJsonArray(STUDS_PREFS_PROP), projs, studs);
        projsPrefs = new ProjectPreferencesSet(jsonProblem.getAsJsonArray(PROJS_PREFS_PROP), projs, studs);
    }

    /**
     * metoda de serializare a datelor problemei
     *
     * @return problema serializata ca JsonObject
     */
    public JsonObject toJson() {
        JsonObject obj = new JsonObject();
        obj.add(STUDS_PROP, studs.toJson());
        obj.add(PROJS_PROP, projs.toJson());
        obj.add(STUDS_PREFS_PROP, studsPrefs.toJson());
        obj.add(PROJS_PREFS_PROP, projsPrefs.toJson());
        return obj;
    }

    /**
     * metoda de prezentare in string a datelor problemei daca problema este
     * rezolvata, se vor afisa si asignarile efectuate
     *
     * @return prezentarea problemei ca string
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        return result.append("\nstudents:\n").append(studs).append("\nprojects:\n").append(projs).append("\n").append(studsPrefs).append(projsPrefs).toString();
    }

    /**
     * functie ce va genera un fisier de intrare. nu va fi folosita decat daca
     * este nevoie.
     */
    void test() {
        studs = new Students(4);
        studs.add(new Student(1, "S1", "@1"));
        studs.add(new Student(2, "S2", "@2"));
        studs.add(new Student(3, "S3", "@3"));
        studs.add(new Student(4, "S4", "@4"));

        projs = new Projects(3);
        projs.add(new Project(1, "P1", 2, "-doP1-"));
        projs.add(new Project(2, "P2", 1, "-doP2-"));
        projs.add(new Project(3, "P3", 1, "-doP3-"));

        studsPrefs = new StudentPreferencesSet();

        Projects s1list = new Projects(3);
        s1list.add(projs.getProject(1));
        s1list.add(projs.getProject(2));
        s1list.add(projs.getProject(3));
        studsPrefs.add(new StudentPreferences(studs.getStudent(1), s1list));

        Projects s2list = new Projects(3);
        s2list.add(projs.getProject(1));
        s2list.add(projs.getProject(3));
        s2list.add(projs.getProject(2));
        studsPrefs.add(new StudentPreferences(studs.getStudent(2), s2list));

        Projects s3list = new Projects(1);
        s3list.add(projs.getProject(1));
        studsPrefs.add(new StudentPreferences(studs.getStudent(3), s3list));

        Projects s4list = new Projects(3);
        s4list.add(projs.getProject(3));
        s4list.add(projs.getProject(2));
        s4list.add(projs.getProject(1));
        studsPrefs.add(new StudentPreferences(studs.getStudent(4), s4list));

        projsPrefs = new ProjectPreferencesSet();
        Students p1list = new Students(4);
        p1list.add(studs.getStudent(3));
        p1list.add(studs.getStudent(1));
        p1list.add(studs.getStudent(2));
        p1list.add(studs.getStudent(4));
        projsPrefs.add(new ProjectPreferences(projs.getProject(1), p1list));

        Students p2list = new Students(4);
        p2list.add(studs.getStudent(1));
        p2list.add(studs.getStudent(2));
        p2list.add(studs.getStudent(3));
        p2list.add(studs.getStudent(4));
        projsPrefs.add(new ProjectPreferences(projs.getProject(2), p2list));

        Students p3list = new Students(4);
        p3list.add(studs.getStudent(4));
        p3list.add(studs.getStudent(3));
        p3list.add(studs.getStudent(1));
        p3list.add(studs.getStudent(2));
        projsPrefs.add(new ProjectPreferences(projs.getProject(3), p3list));

    }

    /**
     * functia de rezolvare a problemei
     */
    public void resolve() {

        Student freeStudent;
        Project none = new Project(-1, "No project available", studs.size(), "");

        matching:
        while ((freeStudent = studs.getNextFreeStudent()) != null) {
            // cat timp avem studenti neatribuiti niciunui proiect

            // caut loc liber in proiectele preferate de student
            Project availablePreferedProject = (freeStudent.prefered).getPreferedAvailableProjectFor(freeStudent);
            if (availablePreferedProject != null) {
                availablePreferedProject.assignStudent(freeStudent);
                continue matching;
            }

            // caut sa inlocuiesc studenti prinsi in proiecte care au prioritate mai mica la acel proiect
            for (Project p : freeStudent.prefered) {
                Student otherStudent = p.getLessPreferedAssignedStudentThan(freeStudent);
                if (otherStudent == null) {
                    continue;
                }
                p.removeStudent(otherStudent);
                p.assignStudent(freeStudent);
                continue matching;
            }

            // caut un oricare proiect in care este loc liber
            Project availableProject = projs.getPreferedAvailableProjectFor(null);
            if (availableProject != null) {
                availableProject.assignStudent(freeStudent);
                continue matching;
            }

            // studentul ramane neatribuit niciunui proiect
            none.assignStudent(freeStudent);
        }
    }
}
