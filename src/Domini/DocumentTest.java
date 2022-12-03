package Domini;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class DocumentTest {


    private static Document doc1, docCarregaTest;
    private static String modCont1,modTitol1,modAutor1;
    private static HashMap<String, Integer> tf1;
    private static Carpeta carpeta;
    private static HashMap<String, List<Document>> pos_list;
    private static HashMap<String, Integer> tf = new HashMap<>();
    private static Document docs[];
    private static HashMap<String, List<Document>> posting_list;
    private static List<Document> ldocs1;

    @BeforeClass
    public static void iniDoc(){
        tf = new HashMap<>();
        tf.put("peu",1);
        tf.put("hores",1);
        tf1 = new HashMap<>();
        doc1= new Document("PROP1","Lluc","Hores a peu",tf1);
        doc1.path= "/Users/lluchospitalescat/PROP/PROVES/hola.txt"; //Users/rubenriveravilloldo/IdeaProjects/PROP/src/txts/doc1Test.txt
        docCarregaTest= new Document();
        modCont1= "Soc el document 1, pero modificat";
        modTitol1= "PROP1_Mod";
        modAutor1= "Lluc_Mod_";
        carpeta = new Carpeta("carpeta_estandard", "path_carpeta");
        pos_list = new HashMap<String, List<Document>>();



        docs = new Document[]{doc1};
        ldocs1 = new ArrayList<>();
        ldocs1.add(doc1);
        posting_list = new HashMap<String, List<Document>>();
        posting_list.put("hores",ldocs1);
        posting_list.put("prop",ldocs1);
        carpeta = new Carpeta("carpeta_estandard", "path1",docs,posting_list);

    }


    @Test
    public void getPath() {
        String pathtest= doc1.getPath();
        System.out.println("Expected: "+pathtest);
        System.out.println("Actual: "+doc1.path);
        assertEquals(pathtest, doc1.path);
    }

    @Test
    public void getTitol() {
        String titoltest= doc1.getTitol();
        System.out.println("Expected: "+titoltest);
        System.out.println("Actual: "+doc1.titol);
        assertEquals(titoltest, doc1.titol);
    }

    @Test
    public void getAutor() {
        String autortest= doc1.getAutor();
        System.out.println("Expected: "+autortest);
        System.out.println("Actual: "+doc1.autor);
        assertEquals(autortest, doc1.autor);
    }

    @Test
    public void getContingut() {
        String conttest= doc1.getContingut();
        System.out.println("Expected: "+conttest);
        System.out.println("Actual: "+doc1.contingut);
        assertEquals(conttest, doc1.contingut);
    }

    @Test
    public void carregar_doc()  throws FileNotFoundException {
        try {
            docCarregaTest.carregar_doc("/Users/lluchospitalescat/PROP/PROVES/hola.txt");
            System.out.println("Expected: " + docCarregaTest.autor);
            System.out.println("Actual: " + doc1.autor);
            assertEquals(docCarregaTest.autor, doc1.autor);
            System.out.println("Expected: " + docCarregaTest.titol);
            System.out.println("Actual: " + doc1.titol);
            assertEquals(docCarregaTest.titol, doc1.titol);
            System.out.println("Expected: " + docCarregaTest.contingut);
            System.out.println("Actual: " + doc1.contingut);
            assertEquals(docCarregaTest.contingut, doc1.contingut);
            System.out.println("Expected: " + docCarregaTest.path);
            System.out.println("Actual: " + doc1.path);
            assertEquals(docCarregaTest.path, doc1.path);
        }catch(Exception exc) {
            exc.printStackTrace();
        }
    }

    @Test
    public void omple_TFHash() {
        doc1.omple_TFHash(pos_list, carpeta);
        System.out.println("Expected: "+tf);
        System.out.println("Actual: "+doc1.tf_hash);
        assertEquals(tf, doc1.tf_hash);
    }

    @Test
    public void modificacio_document() {

        doc1.modificacio_document("Soc el document 1, pero modificat" );

        System.out.println("Expected: "+modCont1);
        System.out.println("Actual: "+doc1.contingut);
        assertEquals(modCont1,doc1.contingut);
    }
}