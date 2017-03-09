/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palaborator2;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import static java.lang.System.out;

/**
 *
 * @author Iulian
 */
/**
 * clasa modeleaza preferintele unui student fata de proiectele existente
 *
 * @author Iulian
 */
public class StudentPreferences {

    static final String PREF_PROP = "Proiecte preferate";
    static final String PROJ_PROP = "P";
    static final String STUD_PROP = "Student";

    /**
     * studentul in cauza
     */
    protected Student stud;
    
    /**
     * lista de proiecte in ordinea preferata
     */
    protected Projects projs;

    /**
     * constructor din date explicite: student si o lista de proiecte
     *
     * @param aStudent
     * @param someProjects
     */
    public StudentPreferences(Student aStudent, Projects someProjects) {
        stud = aStudent;
        projs = someProjects;
        stud.prefered = projs;
    }

    /**
     * constructor dintr-un obiect serializat, care are nevoie de lista tuturor
     * proiectelor si studentilor pentru a crea legaturile intre aceleasi
     * obiecte, cautandu-le dupa id.
     *
     * @param jObject
     * @param existingProjects
     * @param existingStudents
     */
    public StudentPreferences(JsonObject jObject, Projects existingProjects, Students existingStudents) {
        stud = existingStudents.getStudent(jObject.get(STUD_PROP).getAsInt());
        JsonArray jCon = jObject.getAsJsonArray(PREF_PROP);
        projs = new Projects(jCon.size());
        for (JsonElement pref : jCon) {
            if (!projs.add(existingProjects.getProject(pref.getAsJsonObject().get(PROJ_PROP).getAsInt()))) {
                out.println("proiect negasit: " + pref.getAsJsonObject().get(PROJ_PROP).getAsInt());
            }
        }
        stud.prefered = projs;
    }

    /**
     * metoda de serializare a preferinte studentului
     *
     * @return
     */
    public JsonObject toJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty(STUD_PROP, stud.id);
        JsonArray jProjs = new JsonArray();
        projs.stream().map((p) -> {
            JsonObject temp = new JsonObject();
            temp.addProperty(PROJ_PROP, p.id);
            return temp;
        }).forEachOrdered((temp) -> {
            jProjs.add(temp);
        });
        obj.add(PREF_PROP, jProjs);
        return obj;
    }

    /**
     * metoda de prezentare a preferintei curenti intr-un string
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("student ").append(stud.id).append(" preferences: ");
        projs.forEach((p) -> {
            result.append(p.name).append(" ");
        });
        return result.toString();
    }
}

