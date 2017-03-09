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
import java.util.ArrayList;

/**
 * clasa extinde capacitatile unei liste de proiecte prin limitarea capacitatii,
 * facilizarea serializarii, deserializarii obiectelor membre
 *
 * @author Iulian
 */
public class Projects extends ArrayList<Project> {

    /**
     * capacitatea maxima a listei
     */
    protected int maxCapacity;

    /**
     * metoda surascrisa limiteaza capacitatea liste curente
     *
     * @param capacity
     */
    @Override
    public final void ensureCapacity(int capacity) {
        if (maxCapacity != 0) {
            out.println("Resizing is not allowed!");
            return;
        }
        super.ensureCapacity(maxCapacity = capacity);
    }

    /**
     * metoda limiteaza adaugarea de noi obiecte
     *
     * @param s
     * @return
     */
    @Override
    public final boolean add(Project s) {
        if (this.size() < maxCapacity) {
            return super.add(s);
        }
        return false;
    }

    /**
     * constructor pentru o lista goala de dimensiune maxima cunoscuta
     *
     * @param fixedCapacity
     */
    Projects(int fixedCapacity) {
        ensureCapacity(fixedCapacity);
    }

    /**
     * metoda ce va deserializa un JsonArray de obiecte Project
     *
     * @param jArrayProjects
     */
    Projects(JsonArray jArrayProjects) {
        clear();
        ensureCapacity(jArrayProjects.size());
        JsonObject jObjProject;
        for (JsonElement jElProject : jArrayProjects) {
            jObjProject = (JsonObject) jElProject;
            add(new Project(jObjProject));
        }
    }

    /**
     * getter dupa un id de proiect
     *
     * @param anId
     * @return
     */
    Project getProject(int anId) {
        for (Project proj : this) {
            if (anId == proj.id) {
                return proj;
            }
        }
        return null;
    }

    /**
     * metoda de serializare a obiectelor si incapsulare intr-un JsonArray
     *
     * @return
     */
    public JsonArray toJson() {
        JsonArray jProjs = new JsonArray();
        this.forEach((proj) -> {
            jProjs.add(proj.toJson());
        });
        return jProjs;
    }

    /**
     * metoda prezentare a unei liste de proiecte intr-un string
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        this.forEach((proj) -> {
            result.append(proj.toString()).append("\n");
        });
        return result.toString();
    }

    /**
     * cauta in lista curenta de proiecte, proiectul care il prefera pe un anume
     * student si are loc disponibil
     *
     * @param aStudent
     * @return
     */
    Project getPreferedAvailableProjectFor(Student aStudent) {
        for (Project p : this) {
            if (p.hasFreeSlotsFor(aStudent)) {
                return p;
            }
        }
        return null;
    }

    /**
     * cauta primul proiect din lista curenta care are un loc liber
     *
     * @return
     */
    Project getFirstProjectWithFreeSlot() {
        for (Project p : this) {
            if (p.hasFreeSlotsFor(null)) {
                return p;
            }
        }
        return null;
    }
}
