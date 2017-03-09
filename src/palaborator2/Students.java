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
 *
 * @author Iulian
 */
/**
 * Clasa extinde capacitatile unui ArrayList de studenti introduce o limitare a
 * capacitatii listei de studenti
 *
 * @author Iulian
 */
public class Students extends ArrayList<Student> {

    /**
     * membrul maxCapacity retine capacitatea maxima a listei de studenti
     */
    protected int maxCapacity;

    /**
     * rescrierea functie de redimensionare a capacitatii pentru a limita
     * numarul de obiecte din clasa
     *
     * @param capacity
     */
    @Override
    final public void ensureCapacity(int capacity) {
        if (maxCapacity != 0) {
            out.println("Resizing is not allowed!");
            return;
        }
        super.ensureCapacity(maxCapacity = capacity);
    }

    /**
     * constructor care primeste doar dimensiunea maxima a listei de studenti
     *
     * @param capacity
     */
    Students(int capacity) {
        ensureCapacity(capacity);
    }

    /**
     * constructor care primeste un obiect JsonArray ce contine obiecte de tip
     * Student din lista curenta deasemeni lista curenta va fi inextensibila ca
     * si numar de obiecte continute
     *
     * @param jArrayStudentsList
     */
    Students(JsonArray jArrayStudentsList) {
        clear();
        ensureCapacity(jArrayStudentsList.size());
        for (JsonElement jElStudent : jArrayStudentsList) {
            add(new Student((JsonObject) jElStudent));
        }
    }

    /**
     * suprascriere a functiei de adaugare obiect in lista care verifica sa nu
     * se depaseasca dimensiunea maxima a listei
     *
     * @param aStudent
     * @return
     */
    @Override
    final public boolean add(Student aStudent) {
        if (this.size() < maxCapacity) {
            return super.add(aStudent);
        }
        return false;
    }

    /**
     * functie ce permite serializarea unei liste de studenti: va serializa
     * fiecare obiect in parte si il va adauga intr-un JsonArray
     *
     * @return JsonArray-ul continand obiectele continute serializate
     */
    public JsonArray toJson() {
        JsonArray jStuds = new JsonArray();
        this.forEach((stud) -> {
            jStuds.add(stud.toJson());
        });
        return jStuds;
    }

    /**
     * funtie de cautare a studentului cu un anume id in lista curenta
     *
     * @param anId
     * @return
     */
    public Student getStudent(int anId) {
        for (Student stud : this) {
            if (stud.id == anId) {
                return stud;
            }
        }
        return null;
    }

    /**
     * metoda va returna un string ce va prezenta lista curenta de studenti
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        this.forEach((stud) -> {
            result.append(stud.toString()).append("\n");
        });
        return result.toString();
    }

    /**
     * functia va returna primul student din lista ce nu este atribuit catre
     * nici un proiect
     *
     * @return
     */
    public Student getNextFreeStudent() {
        for (Student s : this) {
            if (s.assignedTo == null) {
                return s;
            }
        }
        return null;
    }
}
