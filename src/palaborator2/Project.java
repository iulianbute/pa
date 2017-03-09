/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palaborator2;

import com.google.gson.JsonObject;

/**
 * Clasa Project contine informatii despre proiecte si metode ajutatoare
 *
 * @author Iulian
 */
public class Project {

    // aceste constante tin numele cheilor in serializarea Json
    static final String ID_PROP = "Id";
    static final String NAME_PROP = "Nume";
    static final String CAPACITY_PROP = "Capacitate";
    static final String DESCR_PROP = "Descriere";

    /**
     * id-ul proiectului
     */
    protected int id;
    
    /**
     * cati studenti pot participa la proiect
     */
    protected int capacity;
    
    /** 
     * numele proiectului
     */
    protected String name;
    
    /**
     * descrierea proiectului
     */
    protected String description;
    
    /** 
     * lista studentilor atribuiti acestui proiect
     */
    protected Students assignedStuds;
    
    /**
     * lista prioritatii studentilor la acest proiect
     */
    protected Students prefered;

    /**
     * constuctor din membri expliciti
     *
     * @param newId
     * @param newName
     * @param newCap
     * @param newDescription
     */
    Project(int newId, String newName, int newCap, String newDescription) {
        id = newId;
        name = newName;
        description = newDescription;
        assignedStuds = new Students(capacity = newCap);
    }

    // constructor dintr-un obiect Json
    Project(JsonObject jObjProject) {
        id = jObjProject.get(ID_PROP).getAsInt();
        name = jObjProject.get(NAME_PROP).toString();
        description = jObjProject.get(DESCR_PROP).getAsString();
        assignedStuds = new Students(capacity = jObjProject.get(CAPACITY_PROP).getAsInt());
    }

    /**
     * metoda ce serializeaza un obiect Project se vor serializa numai datele
     * proprii proiectului: id, nume, descriere si capacitate.
     *
     * @return serialized object as JsonObject
     */
    public JsonObject toJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty(ID_PROP, id);
        obj.addProperty(NAME_PROP, name);
        obj.addProperty(CAPACITY_PROP, capacity);
        obj.addProperty(DESCR_PROP, description);
        return obj;
    }

    /**
     * metoda va face prezentarea unui proiect ca si string; in caz ca avem
     * studenti asignati proiectului vor fi prezentati si ei dupa nume.
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Project ").append(name).append("(id ").append(id).append(") for ").append(capacity).append(" students: ").append(description);
        if (assignedStuds != null && assignedStuds.size() != 0) {
            result.append("  -  Students assigned: ");
            assignedStuds.forEach((s) -> {
                result.append(s.name).append(" ");
            });
        }
        return result.toString();
    }

    /**
     * functie de verificare a egalitatii intre 2 proiecte: acestea vor fi egale
     * daca au acelasi nume, pentru simplitate.
     *
     * @param other
     * @return
     */
    public boolean equals(Project other) {
        return this.id == other.id;
    }

    /**
     * functie care va verifica daca un student dat este in lista studentilor
     * preferati si exista un loc liber pentru in lista
     *
     * @param aStudent
     * @return
     */
    public boolean hasFreeSlotsFor(Student aStudent) {
        return (assignedStuds.size() < capacity) && prefered.contains(aStudent);
    }

    /**
     * metoda asigneaza un student proiectului curent; deasemeni si studentul va
     * apare ca fiind asignat acestui proiect.
     *
     * @param aStudent
     * @return
     */
    public boolean assignStudent(Student aStudent) {
        if (!assignedStuds.add(aStudent)) {
            return false;
        }
        aStudent.assignedTo = this;
        return true;
    }

    /**
     * metoda scoate un student din lista participantilor la proiectul curent;
     * deasemeni va marca si faptul ca studentul nu mai este participant la nici
     * un proiect
     *
     * @param aStudent
     * @return
     */
    public boolean removeStudent(Student aStudent) {
        if (!assignedStuds.remove(aStudent)) {
            return false;
        }
        aStudent.assignedTo = null;
        return true;
    }

    /**
     * metoda verifica daca in lista de participanti exista un student care este
     * mai putin preferat pentru acest proiect decat studentul primit ca
     * parametru, student care va fi returnat
     *
     * @param aStudent
     * @return
     */
    public Student getLessPreferedAssignedStudentThan(Student aStudent) {
        int priorityOfS = prefered.indexOf(aStudent);
        if (priorityOfS == -1) {
            return null;
        }
        for (Student assignedStud : assignedStuds) {
            if (prefered.indexOf(assignedStud) > priorityOfS) {
                return assignedStud;
            }
        }
        return null;
    }
}
