/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palaborator2;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.ArrayList;

/**
 * clasa extinde capacitatile unei liste de preferinte (a studentilor fata de
 * proiecte) prin adaugarea de metode ce faciliteaza serializarea si
 * deserializarea unei liste de preferinte, cat si prezentarea lor
 *
 * @author Iulian
 */
public class StudentPreferencesSet extends ArrayList<StudentPreferences> {

    /**
     * deserializeaza o lista de preferinte a studentilor necesita lista
     * completa a studentilor si proiectelor pentru a reface legaturile intre
     * aceleasi obiecte
     *
     * @param jPrefs
     * @param existingProjects
     * @param existingStudents
     */
    StudentPreferencesSet(JsonArray jPrefs, Projects existingProjects, Students existingStudents) {
        ensureCapacity(jPrefs.size());
        for (JsonElement jPref : jPrefs) {
            add(new StudentPreferences(jPref.getAsJsonObject(), existingProjects, existingStudents));
        }
    }

    StudentPreferencesSet() {
    }

    /**
     * metoda de serializare pentru o lista de preferinte in forma unui
     * JsonArray
     *
     * @return
     */
    public JsonArray toJson() {
        JsonArray jPrefs = new JsonArray();
        this.forEach((pref) -> {
            jPrefs.add(pref.toJson());
        });
        return jPrefs;
    }

    /**
     * metoda de prezentare a unei liste de preferinte in forma unui string
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("student's preferences: \n");
        this.forEach((p) -> {
            result.append(p).append("\n");
        });
        return result.append("\n").toString();
    }
}
