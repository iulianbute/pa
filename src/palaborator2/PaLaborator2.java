package palaborator2;

import com.google.gson.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.System.out;


/**
 *
 * problema matching intre studenti cu proiecte preferate, unde la un proiect
 * pot lucra mai multi studenti
 */
public class PaLaborator2 {

    final private static String IN_FILE = "students.json";

    static Problem myProblem;
    
    /**
     *
     * Citeste un obiect Json dintr-un fisier
     *
     * @param fileName fisierul sursa
     * @return obiectul json parsat
     */
    public static JsonObject readJsonFromFile(String fileName) {
        try {
            return ((JsonObject) new JsonParser().parse(new BufferedReader(new FileReader(fileName))));
        } catch (IOException e) {
            out.println(e.getMessage());
            System.exit(0);
        }
        return null;
    }

    /**
     *
     * Scrie un obiect json intr-un fisier
     *
     * @param fileName fisier destinatie
     * @param obj obiect json de scris
     */
    public static void writeJsonToFile(String fileName, JsonObject obj) {
        FileWriter writer;
        try {
            writer = new FileWriter(fileName);
            writer.write(obj.toString());
            writer.close();
        } catch (IOException e) {
            out.println(e.getMessage());
            System.exit(0);
        }
    }

    /**
     *
     * ruleaza fara argumente
     *
     * @param args neutilizat
     */
    public static void main(String[] args) {
        if (false) { // se va executa doar daca avem nevoie sa regeneram json-ul de intrare standard
            myProblem = new Problem();
            myProblem.test();
            writeJsonToFile(IN_FILE, myProblem.toJson());
            System.exit(0);
        }
        myProblem = new Problem(readJsonFromFile(IN_FILE));
        myProblem.resolve();
        out.println(myProblem);
    }
}
