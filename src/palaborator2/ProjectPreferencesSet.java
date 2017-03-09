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
 * metoda va extinde o lista de prioritati a studentilor la proiecte cu metode
 * ce faciliteaza manipularea lor ca grup
 *
 * @author Iulian
 */
class ProjectPreferencesSet extends ArrayList<ProjectPreferences> {

    /** constructor a unei liste din obiect serializat Json
     * necesita lista completa de studenti si proiecte pentru construirea legaturilor intre aceleasi obiecte
     * 
     * @param jPrefs
     * @param existingProjects
     * @param existingStudents 
     */
    ProjectPreferencesSet(JsonArray jPrefs, Projects existingProjects, Students existingStudents) {
        ensureCapacity(jPrefs.size());
        for (JsonElement jPref : jPrefs) {
            add(new ProjectPreferences(jPref.getAsJsonObject(), existingProjects, existingStudents));
        }
    }

    ProjectPreferencesSet() {
    }

    /**
     * metoda de serializare a listei de prioritati
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
     * metoda de prezentare a unei liste de prioritati
     * 
     * @return 
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("teacher's preferences: \n");
        this.forEach((p) -> {
            result.append(p).append("\n");
        });
        return result.append("\n").toString();
    }
}
