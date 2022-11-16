package Domini;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.*;

public class Carpeta {

    //Atributos
    String nombre_carpeta;
    String path;
    TreeMap<String, TreeMap<String, Document>> listado_documento =
            new TreeMap<String, TreeMap<String, Document>>();
    static HashMap<String, List<Document>> posting_list = new HashMap<String, List<Document>>();

    //Geter path
    public String getPath() {
        return path;
    }

    //Constructora
    public Carpeta(String nombre_asignado, String path_asignado) {


        this.nombre_carpeta = nombre_asignado;
        this.path = path_asignado;

    }
    //Només seerveix per els testos
    public Carpeta(String nombre_asignado, String path_asignado, Document docs[], HashMap<String, List<Document>> posting){
        this.nombre_carpeta = nombre_asignado;
        this.path = path_asignado;

        for(Document d : docs) {
            String autor = d.getAutor();
            String titol = d.getTitol();
            TreeMap<String, Document> aux = new TreeMap<String, Document>();
            if (listado_documento.containsKey((autor))) {
                aux = listado_documento.get(autor);
            }
            aux.put(titol, d);
            listado_documento.put(autor, aux);
        }
        //creem la posting list plena
        posting_list = posting;
    }

    //Metodos
    /*public void carrega_doc(Document doc, String aux_file) {
        doc.carregar_doc(path + "/" + aux_file);
        doc_en_listado(doc);
    }*/
    private void carrega_doc_extern(Document doc, String aux_file) throws FileNotFoundException{
        try {
            doc.carregar_doc(aux_file);
        }
        catch(FileNotFoundException e){
            throw e;
        }
        doc_en_listado(doc);
    }

    public void importa_document(Document doc, String old_string) throws FileNotFoundException {
        //File filename = new File(old_string);
        try {
            carrega_doc_extern(doc, old_string);
        }
        catch(FileNotFoundException e){
            throw e;
        }
        /*String nomFile = filename.getName();
        doc.canviarPath(path + "/" + nomFile);
        System.out.println(doc.getPath());
        File newFile = new File(path + "/" + nomFile);
        System.out.println(newFile.getPath());


        Path old = filename.toPath();
        Path imported = newFile.toPath();
        try{
            Files.copy(old, imported);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    }

    private void doc_en_listado(Document d){
        String autor = d.getAutor();
        String titol = d.getTitol();
        TreeMap<String, Document> aux = new TreeMap<String, Document>();
        if(listado_documento.containsKey((autor))) {
            aux = listado_documento.get(autor);
        }
        aux.put(titol, d);
        listado_documento.put(autor, aux);
    }

    public void baixa_Document(String autor, String titol){

        Document eliminat = listado_documento.get(autor).get(titol);
        doc_en_listado(eliminat);

        listado_documento.get(autor).remove(titol);
        if (listado_documento.get(autor).size()==0) listado_documento.remove(autor);

    }

    public String get_titols (String autor) throws Exception {
        String aux = "";
        if(listado_documento.containsKey(autor)){

            Set<Map.Entry<String, Document>> lista_titulos = listado_documento.get(autor).entrySet();
            boolean empty = true, leido = false;
            for (Map.Entry<String, Document> iterador: lista_titulos){
                if (leido){
                    aux = aux + "\n" + iterador.getKey();
                }
                else
                    aux = aux + (iterador.getKey());
                empty = false;
                leido = true;

            }
            if(empty)
                throw new Exception("El autor introducido no tiene documentos registrados");

        }

        else {

            throw new Exception ("El autor no existe");
        }
        return aux;
    }

    public String get_autors (String prefix) throws Exception{
        boolean prefix_vacio = false, used = false, leido = false;

        if(prefix.length() == 0)
            prefix_vacio = true;
        String aux = "";

        for(Map.Entry <String, TreeMap<String, Document>> iterador: listado_documento.entrySet()){


            String key = iterador.getKey();
            if(prefix_vacio || key.startsWith(prefix)) {
                used = true;
                if(leido)
                    aux = aux + "\n" + key;
                else
                    aux = aux + key;

                leido = true;
            }
        }
        if (!used) {
            throw new Exception ("La lista de autores esta vacia o " +
                    "no existe ningun autor con el prefijo dado.");
        }

        return aux;

    }

    public String get_contenido (String autor, String titol) throws Exception{
        String carry = "";
        if (listado_documento.containsKey(autor)){
            if(listado_documento.get(autor).containsKey((titol))) {
               carry = listado_documento.get(autor).get(titol).getContingut();

            }
            else
               throw new Exception ("El autor introducido no tiene el documento solicitado");
        }

        else
            throw new Exception ("El autor introducido no existe");

        return carry;

    }
    private void eliminar_paraules_posting(Document d) {
        for (String s : d.tf_hash.keySet()){
            if(posting_list.containsKey(s)){
                posting_list.get(s).remove(d);
            }
        }
    }

    public void modifica_document_en_carpeta (String autor, String titol, String str) throws Exception{

        if(!listado_documento.containsKey((autor)))
            throw new Exception("El autor introducido no existe");
        if(!listado_documento.get(autor).containsKey(titol))
            throw new Exception ("El autor introducido no tiene el documento solicitado");

       listado_documento.get(autor).get(titol).modificacio_document(str);
       eliminar_paraules_posting(listado_documento.get(autor).get(titol));
    }




    /* public void visualizar_lista(){

        for(Map.Entry <String, TreeMap<String, Document>> EntradaExterior: listado_documento.entrySet()){

            //TreeMap <String, Document> aux = );
            System.out.println(EntradaExterior.getKey());
            for(Map.Entry <String, Document> EntradaInterior : EntradaExterior.getValue().entrySet()){

                System.out.println(" " + EntradaInterior.getKey());

            }
            System.out.println("-----------Autor-------------");
        }

    } */

}