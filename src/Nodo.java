class Nodo<T> {
    T data;
    Nodo<T> izquierda, derecha;

    Nodo(T data) {
        this.data = data;
        this.izquierda = null;
        this.derecha = null;
    }
}
