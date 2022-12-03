package Domini;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;
import java.util.Set;


public class Expressions {

    //private Carpeta carpeta;
    public Vector<String> expr = new Vector<String>();

    public Expressions(){};

    public Expressions(Vector<String>vec){
        expr = vec;
    }



    public  void altaExpressio(String x) throws Exception{
        if(!expr.contains(x)){
            expr.add(x);
        }
        else{
            throw new Exception("L'expressió ja existeix");
        }
    }


    public void baixaExpressio(String x) throws Exception{
        if(expr.contains(x)){

            expr.remove(x);
        }
        else throw new Exception("L'expressió no existeix");
    }

    public void modifExpressio(String vell, String nou) throws Exception{

        if(expr.contains(vell)){
            if(!expr.contains(nou) ){

                expr.remove(vell);
                expr.add(nou);
            }
            else throw new Exception("La nova expressió ja existeix");
        }
        else throw new Exception("L'expressió a modificar no existeix");
    }
    Vector<String>  getAll(){
        return expr;
    }


    Set<Document> juntadoc(Boolean and, Boolean or,Set<Document> contingut, Set<Document>fin){
        if(and){
            fin.retainAll(contingut);
        }
        else if(or){
            fin.addAll(contingut);
        }
        else{
            fin =contingut;
        }
        return fin;
    }
    public Set<Document> doc_expressions(String x,Carpeta carpeta){
        Domini.Algoritme algo = new Algoritme();
        boolean and = false;
        boolean or = false;
        Set<Document> doc_contenir = new HashSet<>();
        Set<Document> doc_nocontenir = new HashSet<>();
        Set<Document> result = new HashSet<>();
        Set<Document> inter = new HashSet<>();
        for(int i = 0; i<x.length();++i){
            if(x.startsWith("{")){
                int indexParenetesis = x.indexOf("}");
                String in = x.substring(1,indexParenetesis);
                String[] words = in.split(" ");
                for(String f:words){
                    String paraulf = f.toLowerCase();
                    f=paraulf;
                }
                doc_contenir = algo.expresions_boleanes(words,carpeta);
                result =juntadoc(and,or,doc_contenir,result);
                and = false;
                or = false;
                String borra = x.substring(0,indexParenetesis+1);
                String fi = x.replace(borra, "");
                x = fi.trim();

            }
            if(x.startsWith("&")){
                System.out.print("and");
                String v = x.replaceFirst("&", "");
                x = v.trim();
                and = true;
            }
            if(x.startsWith("|")){
                //System.out.print("or");
                String v = x.replaceFirst("[|]","");
                x = v.trim();
                or = true;
            }

            if(x.startsWith("(")){
                int indexParenetesis = x.indexOf(")");
                String in = x.substring(1,indexParenetesis);
                String[] words =x.split("\\s");
                boolean o =false;
                boolean a = false;
                for(String f:words){

                    String paraulf = f.toLowerCase();
                    f=paraulf;
                    if(f == "&") a = true;
                    if(f == "|") o = true;
                    else{

                        if(!a & !o){
                            doc_contenir = algo.expresions_boleanes(new String[]{f},carpeta);
                        }
                        if(a){
                            inter = algo.expresions_boleanes(new String[]{f},carpeta);
                            doc_contenir.retainAll(inter);
                            a=false;
                        }
                        if(o){
                            inter = algo.expresions_boleanes(new String[]{f},carpeta);
                            doc_contenir.addAll(inter);
                            o=false;
                        }
                    }
                }

                if(and) {
                    result.retainAll(doc_contenir);
                    and = false;
                }
                else if(or){
                    result.addAll(doc_contenir);
                    or = false;
                }
                else{
                    result = doc_contenir;
                }
                or = false;
                and = false;
                String borra = x.substring(0,indexParenetesis+1);
                String fi = x.replace(borra, "");
                x = fi.trim();

            }
            if(x.startsWith("!")){

                int acabaparaula = x.indexOf(" ");
                if (acabaparaula == -1) acabaparaula = x.length();
                String p = x.substring(1,acabaparaula);

                doc_nocontenir = algo.expresions_boleanes(new String[]{p},carpeta);

                if(and) {
                    inter = doc_nocontenir;
                    result.removeAll(inter);
                    and = false;
                }
                else if(or){
                    result.addAll(doc_nocontenir);
                    or = false;
                }
                else{
                    result = doc_contenir ;
                }

                String borra = x.substring(0,acabaparaula);
                String fi = x.replace(borra, "");
                x = fi.trim();

            }
            else if(!x.isEmpty() & !x.startsWith("{") & !x.startsWith("(") & !x.startsWith("&") & !x.startsWith("!") & !x.startsWith("|") & !x.startsWith("\\s")){

                int acabaparaula = x.indexOf(" ");
                if (acabaparaula == -1) acabaparaula = x.length();
                String p = x.substring(0,acabaparaula);

                String paraula = p.toLowerCase();

                doc_contenir = algo.expresions_boleanes(new String[]{paraula},carpeta);


                result =juntadoc(and,or,doc_contenir,result);
                and =false;
                or = false;
                String borra = x.substring(0,acabaparaula);
                String fi = x.replace(borra, "");
                x = fi.trim();
            }
        }
        return result;
    }

    public boolean isEmpty() {
        return expr.isEmpty();
    }
}
