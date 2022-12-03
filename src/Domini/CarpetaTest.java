package Domini;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class CarpetaTest {
    private static Carpeta carpeta;
    private static HashMap<String, Integer> tf1;
    private static Document doc1;
    private static Document doc_buit;
    private static Document doc;
    private static Document docs[];
    private static HashMap<String, List<Document>> posting_list;

    private static List<Document> ldocs;


    @BeforeClass
    public static void ini() {
        tf1 = new HashMap<>();
        tf1.put("peu",1);
        tf1.put("hores",1);
        doc1 = new Document("PROP1", "Lluc","Hores a peu", tf1);
        docs = new Document[]{doc1};
        posting_list = new HashMap<String, List<Document>>();
        ldocs = new ArrayList<>();
        ldocs.add(doc1);
        posting_list.put("hores",ldocs);
        posting_list.put("peu",ldocs);
        carpeta = new Carpeta("carpeta_estandard", "path1",docs,posting_list);
        doc_buit = new Document();
        doc = new Document();
    }

    @Test
    public void getPath() {
        String path_act = carpeta.getPath();
        assertEquals("path1",path_act);
    }

    @Test
    public void importa_document() throws FileNotFoundException {
        try {
            carpeta.importa_document(doc_buit, "/Users/lluchospitalescat/PROP/PROVES/hola3.txt");
            System.out.println(doc_buit.autor);
            System.out.println(carpeta.listado_documento);
            doc = carpeta.listado_documento.get("Lluc").get("PROP3");
            assertNotNull(doc);
        }catch(FileNotFoundException e) {
            throw e;
        }
    }

    @Test
    public void baixa_Document() {
        carpeta.baixa_Document("Lluc", "PROP1");
        assertFalse(carpeta.listado_documento.containsKey("Lluc"));
    }

    @Test
    public void get_titols() throws Exception {
            String titol = carpeta.get_titols("Lluc");
            assertEquals("PROP1", titol);
    }

    @Test
    public void get_autors() throws Exception {
        String autors = carpeta.get_autors("Ll");
        assertEquals("Lluc",autors);
    }

    @Test
    public void get_contenido() throws Exception {
        String contingut = carpeta.get_contenido("Lluc","PROP1");
        assertEquals("Hores a peu",contingut);
    }

    @Test
    public void modifica_document_en_carpeta() throws Exception{
        try {
            carpeta.modifica_document_en_carpeta("Lluc", "PROP1", "Adeu");
            assertEquals("Adeu", carpeta.listado_documento.get("Lluc").get("PROP1").contingut);
            assertFalse(carpeta.posting_list.containsKey("hola")); //per comprovar que ha borrat les apraules dol contingut vell
        } catch (Exception e) {
            throw e;
        }
    }
}