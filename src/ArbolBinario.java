public class ArbolBinario<T extends Comparable<T>> {
    private Nodo<T> raiz;

    // Constructor
    public ArbolBinario() {
        this.raiz = null;
    }

    // Método para agregar un nodo
    public void agregar(T data) {
        raiz = agregarRecursivo(raiz, data);
    }

    private Nodo<T> agregarRecursivo(Nodo<T> nodo, T data) {
        if (nodo == null) {
            return new Nodo<>(data);
        }
        if (data.compareTo(nodo.data) < 0) {
            nodo.izquierda = agregarRecursivo(nodo.izquierda, data);
        } else if (data.compareTo(nodo.data) > 0) {
            nodo.derecha = agregarRecursivo(nodo.derecha, data);
        }
        return nodo;
    }

    // Método para buscar un nodo
    public boolean buscar(T data) {
        return buscarRecursivo(raiz, data) != null;
    }

    // Método público para buscar un nodo completo
    public Nodo<T> buscarNodo(T dato) {
        return buscarRecursivo(raiz, dato);
    }

    private Nodo<T> buscarRecursivo(Nodo<T> nodo, T data) {
        if (nodo == null) {
            return null; // No encontrado
        }

        int comparacion = data.compareTo(nodo.data);

        if (comparacion == 0) {
            return nodo; // Encontrado
        } else if (comparacion < 0) {
            return buscarRecursivo(nodo.izquierda, data); // Buscar en el subárbol izquierdo
        } else {
            return buscarRecursivo(nodo.derecha, data); // Buscar en el subárbol derecho
        }
    }

    // Listar elementos (InOrden, PreOrden, PostOrden)
    public String listar(String orden) {
        StringBuilder sb = new StringBuilder();
        switch (orden.toLowerCase()) {
            case "inorden":
                
                inOrden(raiz, sb);
                break;
            case "preorden":
                preOrden(raiz, sb);
                break;
            case "postorden":
                postOrden(raiz, sb);
                break;
            default:
                sb.append("Orden no válido.");
        }
        return sb.toString();
    }

    // Método para listar en orden (InOrden)
    private void inOrden(Nodo<T> nodo, StringBuilder sb) {
        if (nodo != null) {
            inOrden(nodo.izquierda, sb);
            sb.append(nodo.data.toString()).append("\n");  // Añade el dato al StringBuilder
            inOrden(nodo.derecha, sb);
        }
    }

    // Recorridos Preorden y Postorden
    private void preOrden(Nodo<T> nodo, StringBuilder sb) {
        if (nodo != null) {
            sb.append(nodo.data.toString()).append("\n");
            preOrden(nodo.izquierda, sb);
            preOrden(nodo.derecha, sb);
        }
    }

    private void postOrden(Nodo<T> nodo, StringBuilder sb) {
        if (nodo != null) {
            postOrden(nodo.izquierda, sb);
            postOrden(nodo.derecha, sb);
            sb.append(nodo.data.toString()).append("\n");
            
        }
    }

    public void eliminar(T data) {
        raiz = eliminarRecursivo(raiz, data);
    }

    private Nodo<T> eliminarRecursivo(Nodo<T> nodo, T data) {
        if(nodo == null) {
            return null;
        }

        int comparacion = data.compareTo(nodo.data);

        if(comparacion < 0) {
            nodo.izquierda = eliminarRecursivo(nodo.izquierda,data);
        } else if (comparacion > 0) {
            nodo.derecha = eliminarRecursivo(nodo.derecha, data);
        } else {
            //nodo encontrado
            if (nodo.izquierda == null && nodo.derecha == null) {
                return null; // Sin hijos
            } else if (nodo.izquierda == null) {
                return nodo.derecha; // Un hijo (derecho)
            } else if (nodo.derecha == null) {
                return nodo.izquierda; // Un hijo (izquierdo)
            } else {
                // Dos hijos: encontrar el menor en el subárbol derecho
                Nodo<T> sucesor = encontrarMinimo(nodo.derecha);
                nodo.data = sucesor.data;
                nodo.derecha = eliminarRecursivo(nodo.derecha, sucesor.data);
            }
        }
        return nodo;
    }

    private Nodo<T> encontrarMinimo(Nodo<T> nodo) {
        while (nodo.izquierda != null){
            nodo = nodo.izquierda;
        }
        return nodo;
    }
    
    
}
