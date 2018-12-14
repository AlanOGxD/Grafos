
/*
 * Luis Joyanes Aguilar, Ignacio Zahonero Martinez.(2008).
 * Grafos, representación y operaciones.
 * En Estructura de datos en Java(431 - 456).
 * España: Mc Graw Hill.
 */

import java.util.Scanner;

class Vertice {
	String nombre;
	int numVertice;
	
	public Vertice() {}
	
	public Vertice(String x) {
		nombre = x;
		numVertice = -1;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNumVertice() {
		return numVertice;
	}

	public void setNumVertice(int numVertice) {
		this.numVertice = numVertice;
	}

	public String nomVertice() // devuelve identificador del vértice
	{
		return nombre;
	}

	public boolean equals(Vertice n) // true, si dos vértices son iguales
	{
		return nombre.equals(n.nombre);
	}

	public void asigVert(int n) // establece el número de vértices
	{
		numVertice = n;
	}

	public String toString() // características del vértice
	{
		return nombre + " (" + numVertice + ")";
	}
}

class GrafoMatriz {
	int numVerts;
	static int MaxVerts = 20;
	Vertice[] verts;
	int[][] matAd;

	public GrafoMatriz() {
		this(MaxVerts);
	}

	public GrafoMatriz(int mx) {
		matAd = new int[mx][mx];
		verts = new Vertice[mx];
		for (int i = 0; i < mx; i++)
			for (int j = 0; i < mx; i++)
				matAd[i][j] = 0;
		numVerts = 0;
		MaxVerts=mx;
	}

	public void nuevoVertice(String nom) {
		boolean esta = numVertice(nom) >= 0;
		if (!esta) {
			Vertice v = new Vertice(nom);
			v.asigVert(numVerts);
			verts[numVerts++] = v;
		}
	}

	// numVertice() busca el vértice en el array. Devuelve -1 si no lo encuentra:
	public int numVertice(String vs) {
		Vertice v = new Vertice(vs);
		boolean encontrado = false;
		int i = 0;
		for (; (i < numVerts) && !encontrado;) {
			encontrado = verts[i].equals(v);
			if (!encontrado)
				i++;
		}
		return (i < numVerts) ? i : -1;
	}

	public void nuevoArco(String a, String b) throws Exception {
		int va, vb;
		va = numVertice(a);
		vb = numVertice(b);
		if (va < 0 || vb < 0)
			throw new Exception("Vértice no existe");
		matAd[va][vb] = 1;
	}

	public void nuevoArco(int va, int vb) throws Exception {
		if (va < 0 || vb < 0)
			throw new Exception("Vértice no existe");
		matAd[va][vb] = 1;
	}

	public boolean adyacente(String a, String b) throws Exception {
		int va, vb;
		va = numVertice(a);
		vb = numVertice(b);
		if (va < 0 || vb < 0)
			throw new Exception("Vértice no existe");
		return matAd[va][vb] == 1;
	}

	public boolean adyacente(int va, int vb) throws Exception {
		if (va < 0 || vb < 0)
			throw new Exception("Vértice no existe");
		return matAd[va][vb] == 1;
	}

	public static int[]recorrerAnchura(GrafoMatriz g, String org) throws Exception{
		int w,v;
		int[] m;
		
		v=g.numVertice(org);
		int CLAVE=-1;
		if (v < 0) throw new Exception("Vertice origen no existe");
        
        ColaLista cola = new ColaLista();
        m = new int[g.numVerts];
        // inicializa los vertices como no marcados
        for (int i = 0; i < g.numVerts; i++)
        	m[i] = CLAVE;
        m[v] = 0; 
        cola.insertar(new Integer(v));
        while (! cola.colaVacia()){
        	Integer cw;
        	cw = (Integer) cola.quitar();
        	w = cw.intValue();
        	System.out.println("Vertice " + g.verts[w] + " Visitado");
        	for (int u = 0; u < g.numVerts; u++){
        		if ((g.matAd[w][u] == 1) && (m[u] == CLAVE)){
		        	m[u] = m[w] + 1;
		        	cola.insertar(new Integer(u));
        		}
        	}
        }
        return m;
	}
	
	public boolean verificarInicializacion() {
		if(verts.length>0)
			return true;
		else
			return false;
	}
	
	public boolean verificarGrafoLleno() {
		if(numVerts<=MaxVerts-1)
			return false;
		else 
			return true;
	}
	
}

class GrafoAdcia {
	int numVerts;
    static int maxVerts = 20;
    Vertice [] tablAdc;
    
    //Constructor
    public GrafoAdcia(int mx){
    	tablAdc = new Vertice[mx];
        numVerts = 0;
        maxVerts = mx;
    }
    
    public int numVertice(String vs) {
        Vertice v = new Vertice(vs);
        boolean encontrado = false;
        int i = 0;
        for (; (i < numVerts) && !encontrado;){
        	encontrado = tablAdc[i].equals(v);
       	 	if (!encontrado) 
       	 		i++ ;
       	}
        return (i < numVerts) ? i : -1 ;
   }
}

class Arco {
	int destino;
	double peso;

	public Arco(int d) {
		destino = d;
	}

	public Arco(int d, double p) {
		this(d);
		peso = p;
	}

	public int getDestino() {
		return destino;
	}

	public boolean equals(Object n) {
		Arco a = (Arco) n;
		return destino == a.destino;
	}
}

class ColaLista {
	//Atributos
	protected Nodo frente;
	protected Nodo fin;
	
	//Constructor
	// crea cola vacia
	public ColaLista(){
		frente = fin = null;
	}
	
	//Metodos
	// insertar: pone elemento por el final
    public void insertar(Object elemento){
    	Nodo a;
        a = new Nodo(elemento);
        if (colaVacia()){
        	frente = a;
        }
        else{
        	fin.siguiente = a;
        }
        fin = a;
    }
    
    // quitar: sale el elemento frente
    public Object quitar() throws Exception{
    	Object aux;
    	if (!colaVacia()){
    		aux = frente.elemento;
    		frente = frente.siguiente;
    	}
    	else
    		throw new Exception("Eliminar de una cola vacia");
    	return aux;
    }
    
    // libera todos los nodos de la cola
    public void borrarCola(){
    	for (; frente != null;){
    		frente = frente.siguiente;
        }
    	System.gc();
    }
    
    // acceso al primero de la cola
    public Object frenteCola() throws Exception{
    	if (colaVacia()){
    		throw new Exception("Error: cola vacia");
        }
    	return (frente.elemento);
    }
 
    // verificacion del estado de la cola
    public boolean colaVacia(){
    	return (frente == null);
    }
}

/*public static
int[]recorrerAnchura(GrafoMatriz g, String org) throws Exception{
int w, v;
int [] m;
v = g.numVertice(org);
if (v < 0) throw new Exception("Vértice origen no existe");
ColaLista cola = new ColaLista();
m = new int[g.numeroDeVertices()];
// inicializa los vértices como no marcados
for (int i = 0; i < g.numeroDeVertices(); i++)
m[i] = CLAVE;
m[v] = 0;
// vértice origen queda marcado
cola.insertar(new Integer(v));
while (! cola.colaVacia()){
Integer cw;
cw = (Integer) cola.quitar()
w = cw.intValue();
System.out.println("Vértice " + g.verts[w] + "visitado");
// inserta en la cola los adyacentes de w no marcados
for (int u = 0; u < g.numeroDeVertices(); u++)
if ((g.matAd[w][u] == 1) && (m[u] == CLAVE)){
// se marca vertice u con número de arcos hasta el
m[u] = m[w] + 1;
cola.insertar(new Integer(u));
}}
return m;}
*/

/*static
public int[] recorrerProf(GrafoAdcia g, String org) throws Exception
{
int v, w;
PilaLista pila = new PilaLista();
int [] m;
m = new int[g.numeroDeVertices()];
// inicializa los vértices como no marcados
v = g.numVertice(org);
if (v < 0) throw new Exception("Vértice origen no existe");
for (int i = 0; i < g.numeroDeVertices(); i++)
m[i] = CLAVE;
m[v] = 0;
pila.insertar(new Integer(v));
while (!pila.pilaVacia())
{
Integer cw;
cw = (Integer) pila.quitar();
w = cw.intValue();
System.out.println("Vértice" + g.tablAdc[w] + "visitado");
// inserta en la pila los adyacentes de w no marcados
// recorre la lista con un iterador
ListaIterador list = new ListaIterador(g.tablAdc[w].lad);
Arco ck;
do
{int k;
ck = (Arco) list.siguiente();
if (ck != null)
{
k = ck.getDestino(); // vértice adyacente
if (m[k] == CLAVE)
{
pila.insertar(new Integer(k));
m[k] = 1;
}
}
} while (ck != null);
}
return m;}*/

/*import java.io.*;
import Grafo.*;
public class ComponentesFuertes
{
static BufferedReader entrada = new BufferedReader(
new InputStreamReader(System.in));
static final int CLAVE = 0xffff;
public static void main(String [] a)throws Exception
{
int n, i, v;
GrafoMatriz ga;
GrafoMatriz gaInverso;
System.out.print("Número de vértices del grafo: ");
n = Integer.parseInt(entrada.readLine());
ga = new GrafoMatriz(n);
gaInverso = new GrafoMatriz(n);
int []m = new int [n];
int []descendientes = new int[n];
int []ascendientes = new int[n];
int []bosque = new int[n];
entradaGrafo(ga, n);
grafoInverso(ga, gaInverso, n);
Vertice [] vs = new Vertice[n];
vs = ga.vertices();
for (i = 0; i < n; i++)
bosque[i] = 0;
v = 0;
do {
// vértice de partida
m = RecorreGrafo.recorrerProf(ga, vs[v].nomVertice());
// se obtiene conjunto de vértices descendientes
for (i = 0; i < n; i++)
{
descendientes[i] = m[i]!= CLAVE ? 1 : 0;
}
// recorre el grafo inverso y obtiene ascendientes
m = RecorreGrafo.recorrerProf(gaInverso, vs[v].nomVertice());
// se obtiene conjunto de vértices descendientes
for (i = 0; i < n; i++)
{
ascendientes[i] = m[i]!= CLAVE ? 1 : 0;
}
System.out.print("\nComponente conexa { ");
for (i = 0; i < n; i++)
{
if (descendientes[i] * ascendientes[i] == 1)
{
System.out.print(" " + vs[i].nomVertice());
bosque[i] = 1;
}
}
System.out.println(" }");

// vértice a partir del cual se obtiene otra componente
v = todosArboles(bosque,n);
} while (v != -1);
} // fin del método main
static void
grafoInverso(GrafoMatriz g, GrafoMatriz x, int n) throws Exception
{
Vertice [] vr = g.vertices();
for (int i = 0; i < n; i++)
x.nuevoVertice(vr[i].nomVertice());
for (int i = 0; i < n; i++)
for (int j = 0; j < n; j++)
if (g.adyacente(i,j)) x.nuevoArco(j,i);
}
static int todosArboles(int [] bosque, int n)
{
int i, w;
w = i = -1;
do
{
if (bosque[++i] == 0)
w = i;
} while ((i < n - 1)&& (w == -1));
return w;
}

}
static void entradaGrafo(GrafoMatriz gr, int n)
throws Exception{...}
}
*/
class NodoPila {
	Object elemento;
    NodoPila siguiente;
    
    NodoPila(Object x){
    	elemento = x;
        siguiente = null;
    }
}

class PilaLista {
	private NodoPila cima;
	
	public PilaLista(){
		cima = null;
    }
	
	public boolean pilaVacia(){
		return cima == null;
	}
	
	public void insertar(Object elemento){
		NodoPila nuevo;
	    nuevo = new NodoPila(elemento);
	    nuevo.siguiente = cima;
	    cima = nuevo;
	}
	
	public Object quitar() throws Exception{
		if (pilaVacia())
			throw new Exception ("Pila vacia, no se puede extraer.");
		Object aux = cima.elemento;
		cima = cima.siguiente;
		return aux;
	}
	
	public Object cimaPila() throws Exception{
		if (pilaVacia())
			throw new Exception ("Pila vacia, no se puede leer cima.");
		return cima.elemento;
	}
	public void limpiarPila(){
		NodoPila t;
		while(!pilaVacia()){
			t = cima;
			cima = cima.siguiente;
			t.siguiente = null;
		}
	}
}

class Nodo {
	Object elemento;
	Nodo siguiente;
	int dato;
	
	public Nodo(Object x){
		elemento = x;
		siguiente = null;
	}
	public Nodo(int x){
		dato = x;
	    siguiente = null;
	}
	public Nodo(int x, Nodo n){
	    dato = x;
	    siguiente = n;
	}
	
	public int getDato(){
	    return dato;
	}
	
	public Nodo getEnlace(){
	    return siguiente;
	}
	public void setEnlace(Nodo enlace){
	    this.siguiente = enlace;
	}
}

public class PruebaGrafos {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Ingrese la cantidad maxima de vertices a ingresar: ");
		int maximoVertices=sc.nextInt();
		System.out.println();
		System.out.println();
		
		GrafoMatriz grafo = new GrafoMatriz(maximoVertices);
		int opcion=0;
		boolean repetir=true;
		
		do {
			System.out.println("1) Agregar un vertice.");
			System.out.println("2) Agregar un arco.");
			System.out.println("3) Verificar adyacencia.");
			System.out.println("4) Recorrer grafo en anchura.");
			System.out.println("5) Salir.");
			System.out.println("------------------------------");
			System.out.println("Elija una opcion...");
		    opcion=sc.nextByte();
		    System.out.println();
		    System.out.println();
		    
		    if(opcion>=1 && opcion<=5){
		    	sc.nextLine();
		    	switch(opcion) {
			    case 1: 
			    	if(grafo.verificarInicializacion()){
			    		if(!grafo.verificarGrafoLleno()){
			    			System.out.println("Ingrese el nombre del vertice...");
							String nombre=sc.nextLine();
							grafo.nuevoVertice(nombre);
							System.out.println();
							System.out.println();
							System.out.println("   Vertice agregado correctamente.");
			    		}
			    		else
			    			System.out.println("   *Se ha alcanzado el maximo de vertices a ingresar");
			    	}
			    	else
			    		System.out.println("   *El grafo no ha sido inicializado");
					System.out.println();
					System.out.println();
					break;
			    case 2:
			    	System.out.println("Ingrese el nombre del vertice origen...");
					String verticeOrigen=sc.nextLine();
					System.out.println("Ingrese el nombre del vertice destino...");
					String verticeDestino=sc.nextLine();
					System.out.println();
					System.out.println();
					try {
						grafo.nuevoArco(verticeOrigen, verticeDestino);
						System.out.println("   Arco agregado correctamente.");
					}catch (Exception e) {
						System.out.println("   *Error al agregar el arco, ambos vertices deben ser validos.");
					}
					System.out.println();
					System.out.println();
					break;
			    case 3: 
			    	System.out.println("Ingrese el nombre del primer vertice...");
					String vertice1=sc.nextLine();
					System.out.println("Ingrese el nombre del segundo vertice...");
					String vertice2=sc.nextLine();
					System.out.println();
					System.out.println();
					try {
						if(grafo.adyacente(vertice1, vertice2))
							System.out.println("   Los vertices si son adyacentes.");
						else
							System.out.println("   Los vertices no son adyacentes.");
					} catch (Exception e) {
						System.out.println("   *Error al verificar adyacencia, ambos vertices deben ser validos.");
					}
					System.out.println();
					System.out.println();
					break;
			    case 4: 
			    	System.out.println("Ingrese el nombre del vertice a partir del cual se hara el recorrido...");
					String verticeInicio=sc.nextLine();
					System.out.println();
					System.out.println();
					try {
						System.out.print("   ");
						grafo.recorrerAnchura(grafo, verticeInicio);
					} catch (Exception e) {
						System.out.println("   *Error al recorrer el grafo, ingrese un vertice valido.");
					}
					System.out.println();
					System.out.println();
					break;
			    case 5: 
			    	repetir=false;
			    	break;
		    	}
		    }
		    else{
		    	System.out.println("   *"+opcion+" no es una opcion valida, intenta otra vez.");
				System.out.println();
				System.out.println();
		    }
		    System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println();
			System.out.println();
			
		}while(repetir);
		System.out.println("Usted ha salido.");
	}
	
}
