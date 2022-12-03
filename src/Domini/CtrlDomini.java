package Domini;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CtrlDomini {

    public CtrlDomini(){};

    static Carpeta carpeta_estandard = new Carpeta("carpeta", "/Users/rubenriveravilloldo/IdeaProjects/PROP/src/txts" );
    static Carpeta carpeta_eliminats = new Carpeta("eliminats", "/Users/rubenriveravilloldo/IdeaProjects/PROP/src/eliminats" );
    static Expressions expressions = new Expressions();

    static HashMap<String, List<String>> posting_list = new HashMap<String, List<String>>();
    public void carregar_un_document(){

        Document doc = new Document();
        carga_document(doc);

    }

    public void donar_de_baixa_document(String autor, String obra)throws Exception{
        //Pre:
        //Post:
        try {
            baixa_document(autor, obra);
        }catch (Exception e){
            throw e;
        }

    }
    public void alta_document(String path_document) throws FileNotFoundException{
        Document new_doc = new Document();

        try {
            importar_document(new_doc, path_document);
        }
        catch (FileNotFoundException e){
            throw e;
        }
        //carpeta_estandard.visualizar_lista();

    }
    public void recuperar_un_document(String autor, String titol) throws Exception{
        try {
            recuperar_document(autor, titol);
        }
        catch(Exception e){
            throw e;

        }
    }
    public static void carga_document(Document doc){
        /*
         *
         *
         * */
        String path = carpeta_estandard.getPath(); //FALTARA Carpeta.getPath()
        //System.out.println("Seleccione el documento que desea cargar");
        lecturacarpeta(path);
        //String aux_file = System.console().readLine();
        //carpeta_estandard.carrega_doc(doc, aux_file);
    };
    static void lecturacarpeta(String path){
        File carpeta = new File(path);

        archivos_carpeta(carpeta);
    }
    static void archivos_carpeta(File carpet){

        for (File arxiu : Objects.requireNonNull(carpet.listFiles())) {
            if (!arxiu.isDirectory()) {
               // System.out.println(arxiu.getName());

            } /*else{
                archivos_carpeta(arxiu); //en el cas que hi hagués una carpeta dins una altra carpeta entraria a buscar els arxius
            }*/
        }
    }

    static void baixa_document(String autor_per_eliminar, String titol_per_eliminar) throws Exception{



        if(!carpeta_estandard.listado_documento.containsKey(autor_per_eliminar))
            throw new Exception("Autor no existent en el sistema.");


        if(!carpeta_estandard.listado_documento.get(autor_per_eliminar).containsKey(titol_per_eliminar))
            throw new Exception("El documento no existe para este autor.");

        String path = carpeta_estandard.getPath();
        Document docu= carpeta_estandard.listado_documento.get(autor_per_eliminar).get(titol_per_eliminar);
        String docupath= docu.getPath();
        carpeta_eliminats.importa_document(docu, docupath);
        carpeta_estandard.baixa_Document(autor_per_eliminar,titol_per_eliminar);
    };


    static void importar_document(Document doc, String old_path) throws FileNotFoundException {

        ///Users/rubenriveravilloldo/Desktop/proves_txt/monotonia.txt

        try {
            carpeta_estandard.importa_document(doc, old_path);
        }
        catch(FileNotFoundException e){
            throw e;


        }
        doc.omple_TFHash(carpeta_estandard.posting_list, carpeta_estandard);

    }




    static void recuperar_document(String autor_per_recuperar, String titol_per_recuperar) throws Exception{


        if(!carpeta_eliminats.listado_documento.containsKey(autor_per_recuperar))
            throw new Exception("Autor no existent en eliminats");


        if(!carpeta_eliminats.listado_documento.get(autor_per_recuperar).containsKey(titol_per_recuperar))
            throw new Exception("No existeix cap títol a eliminats amb aquest nom de l'autor: "+ autor_per_recuperar);


        Document docu = carpeta_eliminats.listado_documento.get(autor_per_recuperar).get(titol_per_recuperar);

        String docupath = docu.getPath();

        carpeta_estandard.importa_document(docu, docupath);

        carpeta_eliminats.baixa_Document(autor_per_recuperar, titol_per_recuperar);

    };

    static void alta_expressio(String exp) throws Exception{
        try{
             expressions.altaExpressio(exp);
       }
        catch(Exception e){
            throw e;
        }
    };
    static void baixa_expressió(String exp) throws Exception{
        try{
            expressions.baixaExpressio(exp);
        }
        catch(Exception e){
            throw e;
        }

    };
    static void modificar_expresio(String vell, String nou) throws Exception{
        try{
            expressions.modifExpressio(vell,nou);
        }
        catch(Exception e){
            throw e;
        }

    };

    static void modificacion_de_documento(String autor, String titol, String str) throws Exception{

        try{
            carpeta_estandard.modifica_document_en_carpeta(autor, titol, str);
            carpeta_estandard.listado_documento.get(autor).get(titol).omple_TFHash(carpeta_estandard.posting_list,carpeta_estandard);
        }
        catch(Exception e){
            throw e;
        }

    }

    static String llistar_per_titol(String autor) throws Exception{

        String aux =  "";
        try {
            aux = carpeta_estandard.get_titols(autor);
        }
        catch(Exception e){
            throw e;
        }
        return aux;
    } //input=autor;output=titulos del autor,solo display, no hay que abrir ni tocar nada

    static String llistar_autor_per_prefix(String autor_prefix) throws Exception{
       try {
           String aux = carpeta_estandard.get_autors(autor_prefix);
           return aux;
       }
       catch(Exception e){
           throw e;

       }
    }; //input=prefijo;output=nombres de los autores con ese prefijo

    static String llistar_contingut(String autor, String titol) throws Exception{
        try {
            String carry = carpeta_estandard.get_contenido(autor, titol);
            return carry;
        }
        catch(Exception e){
            throw e;
        }

    }; //input=autor,titol; output= contenido del documento al que accedes desde el treemap con <Autor,titol>

    static TreeMap<Double, List<Document>> llistar_k_documents(String autor1, String titol1,Integer k)throws Exception{

        if(!carpeta_estandard.listado_documento.containsKey(autor1))
            throw new Exception("Autor no existent en el sistema");

        if(!carpeta_estandard.listado_documento.get(autor1).containsKey(titol1))
            throw new Exception("Autor no existent en el sistema");

        if(k < 0)
            throw new Exception("Valor negatiu");

        else {
            Algoritme alg = new Algoritme();
            Document doc = carpeta_estandard.listado_documento.get(autor1).get(titol1);
            TreeMap<Double, List<Document>> semblants = alg.similitud_doc(carpeta_estandard, doc); //ens retorna tots els documents ordenats per similitud de menor a major
            return semblants;
        }

    };

    static Set<Document> documents_per_booleans(String exp){
        //Algoritme alg = new Algoritme
        Set<Document> docs = expressions.doc_expressions(exp,carpeta_estandard);
        //Set<Document> docs = alg.expresions_boleanes(contenir, carpeta_estandard);
        return docs;
    }
    static Carpeta get_carpeta() {
        return carpeta_estandard;
    }
};




