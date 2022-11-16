package Domini;

import org.junit.BeforeClass;
import org.junit.Test;


import java.util.*;

import static org.junit.Assert.*;


public class AlgoritmeTest {
    private static Carpeta carpeta;
    private static Document doc1;
    private static Document doc2;
    private static Document doc3;
    private static Document docs[];
    private static List<Document> ldocs1;
    private static List<Document> ldocs2;
    private static List<Document> ldocs3;
    private static List<Document> ldocs4;
    private static TreeMap<Double, List<Document>> llistat;
    private static HashMap<String, List<Document>> posting_list;
    private static HashMap<String, Integer> tf1;
    private static HashMap<String, Integer> tf2;
    private static String[] paraules;
    private static Set<Document> docu_correcte;

    @BeforeClass
    public static void ini(){
        tf1 = new HashMap<>();
        tf1.put("peu",1);
        tf1.put("hores",1);
        doc1 = new Document("Lluc", "PROP1","Hores a peu", tf1);
        doc2 = new Document("Lluc", "PROP2","Hores a peu", tf1);
        tf2 = new HashMap<>();
        tf2.put("hola",1);
        tf2.put("bona",1);
        tf2.put("nit",1);
        doc3 = new Document("Lluc", "PROP3", "Hola bona nit?", tf2);
        docs = new Document[]{doc1, doc2, doc3};
        ldocs1 = new ArrayList<>();
        ldocs2 = new ArrayList<>();
        ldocs1.add(doc2);
        llistat = new TreeMap<Double, List<Document>>();
        llistat.put(1.0, ldocs1);
        //System.out.println("BROOOOOOO   "+ldocs1);
        ldocs2.add(doc3);
        //System.out.println("BROOOOOOO   "+ldocs2);
        llistat.put(0.0, ldocs2);
        System.out.println(llistat);
        ldocs3 = new ArrayList<>();
        ldocs4 = new ArrayList<>();
        ldocs3.add(doc1);
        ldocs3.add(doc2);
        posting_list = new HashMap<String, List<Document>>();
        posting_list.put("hores",ldocs3);
        posting_list.put("prop",ldocs3);
        ldocs4.add(doc3);
        posting_list.put("hola",ldocs4);
        posting_list.put("tot",ldocs4);
        posting_list.put("be", ldocs4);
        carpeta = new Carpeta("carpeta_estandard", "path1",docs,posting_list);
        paraules = new String[]{"hores"};
        docu_correcte = new HashSet<Document>();
        docu_correcte.add(doc1);
        docu_correcte.add(doc2);
    }

    @Test
    public void similitud_doc() {
        Algoritme alg = new Algoritme();
        TreeMap<Double, List<Document>> similitud = alg.similitud_doc(carpeta, doc1);
        System.out.println("Expected: "+llistat);
        System.out.println("Actual: "+llistat);
        assertEquals(llistat, similitud);
    }

    @Test
    public void expresions_boleanes() {
        System.out.println(carpeta.listado_documento);
        System.out.println((carpeta.posting_list));
        //prova amb búsqueda de la paraula "hores"
        Algoritme alg = new Algoritme();
        Set<Document> docu = alg.expresions_boleanes(paraules, carpeta);
        System.out.println("Expected: "+docu_correcte);
        System.out.println("Actual: "+docu);
        assertEquals(docu_correcte,docu);
    }
}