class Empleado implements Comparable<Empleado> {
    int numero;
    String nombre;
    String puesto; // Ventas, Distribución, Bodega
    double salario;

    Empleado(int numero, String nombre, String puesto, double salario) {
        this.numero = numero;
        this.nombre = nombre;
        this.puesto = puesto;
        this.salario = salario;
    }

      @Override
    public int compareTo(Empleado o) {
        return Integer.compare(this.numero, o.numero);
    }


    @Override
    public String toString() {
        return "Empleado{" +
                "Número=" + numero +
                ", Nombre='" + nombre + '\'' +
                ", Puesto='" + puesto + '\'' +
                ", Salario=" + salario +
                '}';
    }
}