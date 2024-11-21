class Agencia implements Comparable<Agencia> {
    int codigo;
    String canton;
    ArbolBinario<Empleado> empleados;

    Agencia(int codigo, String canton) {
        this.codigo = codigo;
        this.canton = canton;
        this.empleados = new ArbolBinario<>();
    }

    public ArbolBinario<Empleado> getEmpleados() {
        return empleados;
    }
    
    
    @Override
    public int compareTo(Agencia o) {
        return Integer.compare(this.codigo, o.codigo);
    }

    @Override
    public String toString() {
        return "Agencia{" +
                "Código=" + codigo +
                ", Cantón='" + canton + '\'' +
                '}';
    }
}