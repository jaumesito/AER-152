/*
S'utlitza l'algorisme Mergesort amb llistes enllaçades. S'utilitza aquest
mètode perquè té una complexitat nlog(n) i, com utilitza llistes enllaçades, no
utilitza memòria adicional. A més, aquest algorisme evita el cas de "pitjors
casos", ja que sempre fa les mateixes iteracions, dividint per la meitat.

Per conveniència de mostrar per pantalla els resultats, es guarden a un array 
de 25000 enters i quan es llegeix el 0, es mostren tots. Es podrien mostrar
per pantalla cada pic quan es troba el resultat, per evitar el problema 
d'utilitzar un array adicional per guardar les solucions, pero apareixerien per 
pantalla durant l'introducció manual dels números, i per això s'ha preferit
que es mostrin al final.

El mètode que conta els números de la llista 'mostFrequent' conté 4 variables:
- contador, que s'encarrega de contar les vegades que apareix un número
- maxC, que emmagatzema el contador més alt fins el moment
- numAct, que conté el número actual que s'està mirant
- numRep, el número més repetit
Si es el primer pic que apareix un número, el contador comença a contar,
inicialitzant-lo a 1. Si no és el primer pic que apareix el contador augmenta.
Després, es compara el contador actual amb el màxim, i si es major numAct també
serà numRep. Es retorna numRep.

Jaume Ribas Gayá
 */
package exercicipractic;

/**
 *
 * @author jriba
 */
public class solution {

    static java.util.Scanner in;
    static int [] resultats;
    static int indice;

    public static boolean casoDePrueba() {
        // leer caso de prueba
        int n = in.nextInt();
        if (n == 0) {
            for(int i = 0; i < indice; i++) {
                System.out.println(resultats[i]);
            }
            return false;
        }
        else {
            int nValores = n;
            LinkedList <Integer> llista = new LinkedList();
            for (int i = 0; i < nValores; i++) {
                n = in.nextInt();
                llista.add(n);
            }
                        
            // MERGESORT
            llista.mergeSort();            
            Node primer = llista.getFirst();
            // RETORNAR RESULTAT
            resultats[indice] = LinkedList.mostFrequent(primer);
            indice++;
            return true;
         }
    }

    public static void main(String[] args) {
        in = new java.util.Scanner(System.in);
        resultats = new int[25000];
        indice = 0;
        while (casoDePrueba()) {
        }
    }
}

// CLASSE NODE
class Node {

    // Element del node
    private Object item;
    
    // Punter al seguent node
    private Node next;
    
    // Crea un node a partir de l’element i el node següent
    public Node(Object item, Node next) {
        this.item = item;
        this.next = next;
    }
    
    // Retorna l’element del node
    public Object getItem() {
        return this.item;
    }
    
    // Especifica l’element del node
    public void setItem(Object item) {
        this.item = item;
    }
    
    // Retorna el node seguent
    public Node getNext() {
        return this.next;
    }
    
    // Especifica el node seguent
    public void setNext(Node next) {
        this.next = next;
    }
    
}

// CLASSE LINKEDLIST
class LinkedList <E extends Comparable <E>> {
    
    // Punter a l'inici de la llista
    private Node first;
    
    // Crea una llista enllaçada buida
    public LinkedList() {
        first = null;
    }
    
    // Afegeix un element al principi de la llista
    public void add(E element) {
        Node newNode = new Node(element, first);
        first = newNode;
    }
    
    // Sobreescriu toString(): genera un String amb
    // tots els elements de la llista: de principi a final
    @Override
    public String toString() {
        Node node = first;
        String str = "";
        while(node != null) {
            str += node.getItem() + " ";
            node = node.getNext();
        }
        return str;
    }
    
    // Troba el node que més es repeteix en la llista enllaçada
    // Només funciona en llistes ordenades
    public static int mostFrequent(Node first) {
        int contador = 0, maxC = 0, numRep = 0, numAct = 0;
        while(first != null) {
            if((int) first.getItem() != numAct) {
                contador = 1;
                numAct = (int) first.getItem();
            }
            else {
                contador++;
            }
            if(contador > maxC) {
                numRep = numAct;
                maxC = contador;
            }

            first = first.getNext();
        }
        return numRep;
    }
    
    // MERGESORT
    // Retorna el node intermedi de la llista
    // Ha de tenir un cost O(n):
    // Podem recorrer la llista una vegada
    private Node middle(Node firstNode) {
        Node aux = firstNode;
        
        while (firstNode.getNext() != null && firstNode.getNext().getNext() != null) {
            firstNode = firstNode.getNext().getNext();
            aux = aux.getNext();
        }

        return aux;
    }
    
    // Ordena la llista amb mergesort
    public void mergeSort() {
        first = mergeSort(first);
    }
    
    // mergeSort d'encarrega d'inicar l'ordenació de la llista
    // Es crida dins mergeSort() i retorna el punter a l'inici de la llista
    // ordenada
    // Adapta el mergeSort vist a classe
    private Node mergeSort(Node n) {
        if((n.getItem() == null) || (n.getNext() == null)) return n;
        
        Node middle = middle(n);
        Node nextMiddle = middle.getNext();
        middle.setNext(null);
        
        Node l = mergeSort(n);
        Node r = mergeSort(nextMiddle);
        
        return merge(l, r);
    }
    
    // S'encarrega de fusionar les dues subllistes ordenades l, r
    // Retorna el punter a l'inici de la llista fusionada
    private Node merge(Node l, Node r) {
        Node first, p;
        if(((E)l.getItem()).compareTo((E)r.getItem()) < 0){
            first = l;
            l = l.getNext();
        }
        else {
            first = r;
            r = r.getNext();
        }
        p = first;
        
        while((l != null) && (r != null)) {
            if(((E)l.getItem()).compareTo((E)r.getItem()) < 0){
                p.setNext(l);
                p = l;
                l = l.getNext();
            }
            else {
                p.setNext(r);
                p = r;
                r = r.getNext();
            }
        }
        
        if(l != null) {
            p.setNext(l);
        }
        else {
            p.setNext(r);
        }
        
        return first;
    }
    
    // mètode que retorna first
    public Node getFirst() {
        return first;
    }
    
}
