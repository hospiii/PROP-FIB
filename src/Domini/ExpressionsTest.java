package Domini;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.print.Doc;
import java.util.*;

import static org.junit.Assert.*;

public class ExpressionsTest {

    private static String s1;
    private static String s2;
    private static String s3;
    private static String s4;

    private static Document doc1;

    private static Document[] docs;
    private static Expressions expr;
    private static Expressions expressions;

    private static Carpeta carpeta;
    private static HashMap<String, Integer> tf1;

    private static Document doc_buit;
    private static Document doc;
    private static HashMap<String, List<Document>> posting_list;
    private static Set<Document> sdoc;




    @BeforeClass

    public static void ini(){
        sdoc = new HashSet<>() {};
        expr = new Expressions();
        s1 = new String("pol & pep");
        s2 = new String("sal | pebre");
        Vector<String> vec = new Vector<String>();
        vec.add(s1);
        vec.add(s2);
        expressions = new Expressions(vec);
        tf1 = new HashMap<>();
        tf1.put("peix",1);
        tf1.put("patates",1);
        doc1 = new Document("PROP1", "Lluc","patates i peix", tf1);
        docs = new Document[]{doc1};
        posting_list = new HashMap<String, List<Document>>();
        carpeta = new Carpeta("carpeta_estandard", "path1",docs,posting_list);
        sdoc.add(doc1);



    }
    @Test
    public void altaExpressio() {
        //s1 = new String("pol & pep");
        //s2 = new String("sal | pebre");
        try {
            expr.altaExpressio(s2);
            expr.altaExpressio(s1);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertNotNull(expr);
    }

    @Test
    public void baixaExpressio() {
        s1 = new String("pol & pep");
        s2 = new String("sal | pebre");
        s3 = new String("patata");
        try {
            expr.altaExpressio(s3);
            expr.altaExpressio(s2);
            expr.altaExpressio(s1);
            expr.baixaExpressio(s3);
            expr.baixaExpressio(s2);
            expr.baixaExpressio(s1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertTrue(expr.isEmpty());
    }


    @Test
    public void modifExpressio() {
        s1 = new String("pol & pep");
        s2 = new String("sal | pebre");
        s3 = new String("patata");
        s4 = new String("pa & tomauet | oli");
        try {
            expr.altaExpressio(s3);
            expr.altaExpressio(s2);
            expr.altaExpressio(s1);
            expr.baixaExpressio(s2);
            expr.modifExpressio(s3,s2);
            expr.baixaExpressio(s2);
            expr.altaExpressio(s4);
            expr.modifExpressio(s4,s2);
            expr.baixaExpressio(s2);
            expr.baixaExpressio(s1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertTrue(expr.isEmpty());
    }

    @Test
    public void doc_expressions() {
        s1 = "patates & !peix";
        Set<Document> cat = new HashSet<>() {};
        cat =expr.doc_expressions(s1,carpeta);
        assertEquals(cat.size(),0);

    }
}