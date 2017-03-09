/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palaborator2;

import com.google.gson.JsonObject;

/**
 *
 * @author Iulian
 */
/**
 * Clasa student contine date despre studenti: nume, email, proiectul de lucru
 * dupa ce a fost atribuit si lista de proiecte preferate
 */
public class Student {

    /**
     * campuri membre: id, nume, prenume, proiectul in care este asignat, lista
     * de proiecte preferate
     */
    
    // constante folosite la serializare si deserializare
    static final String ID_PROP = "Id";
    static final String NAME_PROP = "Nume";
    static final String MAIL_PROP = "eMail";

    /**
     * id-ul studentului
     */
    protected int id;
    
    /**
     * numele studentului
     */
    protected String name;
    
    /**
     * adresa eMail
     */
    protected String email;
    
    /**
     * proiectul la care este atribuit
     */
    protected Project assignedTo;
    
    /**
     * lista de proiecte preferate
     */
    protected Projects prefered;

    /**
     * constructor ce foloseste parametrii:
     *
     * @param anId id-ul studentului
     * @param aName numele studentului
     * @param anEmail adresa email a studentului
     */
    Student(int anId, String aName, String anEmail) {
        id = anId;
        name = aName;
        email = anEmail;
    }

    /**
     * constructor dintr-un obiect Json
     *
     * @param jStudent obiect Json serializat
     */
    Student(JsonObject jStudent) {
        id = jStudent.get(ID_PROP).getAsInt();
        name = jStudent.get(NAME_PROP).toString();
        email = jStudent.get(MAIL_PROP).toString();
    }

    /**
     * functie care serializeaza in format Json o instanta a clasei Student
     *
     * @return obiect serializat Json din clasa Student care va contine numai
     * campurile id, nume si email
     */
    public JsonObject toJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty(ID_PROP, id);
        obj.addProperty(NAME_PROP, name);
        obj.addProperty(MAIL_PROP, email);
        return obj;
    }

    /**
     * functie care returneaza informatii in format String despre obiectul
     * curent. in caz ca avem solutia calculata se va afisa si proiectul la care
     * este asignat
     *
     * @return String ce contine prezentarea studentului, inclusiv proiectul la care este atribuit daca acesta exista
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Student ").append(name).append(" (id ").append(id).append(", ").append(email).append(")");
        if (assignedTo != null) {
            result.append(" is working on project ").append(assignedTo.name);
        }
        return result.toString();
    }

    /**
     * suprascrierea metodei equals pentru obiecte din clasa Student
     *
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() == this.getClass()) {
            return name.equals(((Student) other).name);
        }
        return false;
    }
}
