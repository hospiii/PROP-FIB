package Domini;

import java.io.*;
import java.util.*;

//on es guarden totes les dades utilitzades per l'algorisme
public class Dades {
    static HashMap<Document, HasMap<String, Integer>> = new HashMap<Document, HashMap<String, Integer>>; //per cada document en el conjunt de documents tenim el seu tf
    static HashMap<String, List<Document>> posting_list = new HashMap<String, List<Document>>();//Per a cada paraula del conjunt de documents ens guardem els documents els quals la contenen

    //CARPETA PERSISTÈNCIA (BASE DE DADES)
    // -- CSV per cada Document on hi guardem el tf (s'actualitza cada cop que es vol sortir de l'aplicació amb lo últim carregat al hashmap local si ha estat modificat.
    // -- CSV únic per a la Posting List (Actualitzat cada cop que es vol sortir de l'aplicació. (o sinó fer algorisme de compresió i llegir binari)

}