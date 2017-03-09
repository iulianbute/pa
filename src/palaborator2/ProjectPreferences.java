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
 * metoda modeleaza prioritatea participarii studentilor la un proiect anume
 *
 * @author Iulian
 */
public class ProjectPreferences {

    // constante ce asigura consistenta serializariii si deserializarii
    static final String PREF_PROP = "Studenti preferati";
    static final String PROJ_PROP = "Proiect";
    static final String STUD_PROP = "S";

    /**
     * proiectul in cauza
     */
    protected Project proj;
    
    /**
     * lista studentilor in ordinea prioritatii lor
     */
    protected Students studs;

    /**
     * constructor din date explicite
     *
     * @param aProject
     * @param someStudents
     */
    public ProjectPreferences(Project aProject, Students someStudents) {
        proj = aProject;
        studs = someStudents;
        proj.prefered = studs;
    }

    /**
     * constuctor dintr-un obiect serializat; necesita lista completa a
     * studentilor si a proiectelor
     *
     * @param object
     * @param existingProjects
     * @param existingStudents
     */
    public ProjectPreferences(JsonObject object, Projects existingProjects, Students existingStudents) {
        proj = existingProjects.getProject(object.get(PROJ_PROP).getAsInt());
        JsonArray jCon = object.getAsJsonArray(PREF_PROP);
        studs = new Students(jCon.size());
        for (JsonElement pref : jCon) {
            if (!studs.add(existingStudents.getStudent(pref.getAsJsonObject().get(STUD_PROP).getAsInt()))) {
                out.println("student negasit: " + pref.getAsJsonObject().get(STUD_PROP).getAsInt());
            }
        }
        proj.prefered = studs;
    }

    /**
     * metoda de serializare a unei liste de prioritati la un proiect;
     * rezultatul va fi un obiect ce va avea ca si cheie un proiect, ca si
     * valoare o lista de studenti; identificarea se va face dupa id-uri.
     *
     * @return obiect serializat Json
     */
    public JsonObject toJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty(PROJ_PROP, proj.id);
        JsonArray jProjs = new JsonArray();
        studs.stream().map((s) -> {
            JsonObject temp = new JsonObject();
            temp.addProperty(STUD_PROP, s.id);
            return temp;
        }).forEachOrdered((temp) -> {
            jProjs.add(temp);
        });
        obj.add(PREF_PROP, jProjs);
        return obj;
    }

    /**
     * metoda de prezentare a unei liste de prioritati ale studentilor la un
     * proiect anume intr-un string
     *
     * @return string de prezentare a obiectului
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("project ").append(proj.id).append(" preferences: ");
        studs.forEach((s) -> {
            result.append(s.name).append(" ");
        });
        return result.toString();
    }
}
