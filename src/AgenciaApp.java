import java.awt.*;
import javax.swing.*;

public class AgenciaApp extends JFrame {
    private ArbolBinario<Agencia> arbolAgencias;

    public AgenciaApp() {

        arbolAgencias = new ArbolBinario<>();
        setTitle("Gestión de Agencias");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear panel lateral para botones
        JPanel panelOpciones = new JPanel(new GridLayout(9, 1, 10, 10));
        panelOpciones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelOpciones.setBackground(new Color(240, 240, 240));

        // Crear botones con diseño uniforme
        JButton btnAgregarAgencia = crearBoton("Agregar Agencia");
        JButton btnBuscarAgencia = crearBoton("Buscar Agencia");
        JButton btnListarAgencias = crearBoton("Listar Agencias");
        JButton btnEliminarAgencia = crearBoton("Eliminar Agencia");
        JButton btnAgregarEmpleado = crearBoton("Agregar Empleado");
        JButton btnBuscarEmpleado = crearBoton("Buscar Empleado");
        JButton btnListarEmpleados = crearBoton("Listar Empleados");
        JButton btnEliminarEmpleado = crearBoton("Eliminar Empleado");
        JButton btnSalir = crearBoton("Salir");

        // Agregar botones al panel lateral
        panelOpciones.add(btnAgregarAgencia);
        panelOpciones.add(btnBuscarAgencia);
        panelOpciones.add(btnListarAgencias);
        panelOpciones.add(btnEliminarAgencia);
        panelOpciones.add(btnAgregarEmpleado);
        panelOpciones.add(btnBuscarEmpleado);
        panelOpciones.add(btnListarEmpleados);
        panelOpciones.add(btnEliminarEmpleado);
        panelOpciones.add(btnSalir);

        btnSalir.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(
                this, 
                "¿Está seguro de que desea salir?", 
                "Confirmar salida", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE
            );
        
            if (confirmacion == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        

        // Crear área de texto para resultados
        JTextArea areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(areaResultados);

        // Agregar componentes al marco principal
        add(panelOpciones, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

        // Tooltips para los botones
        btnAgregarAgencia.setToolTipText("Agregar una nueva agencia");
        btnBuscarAgencia.setToolTipText("Buscar una agencia existente");
        btnListarAgencias.setToolTipText("Listar todas las agencias registradas");
        btnEliminarAgencia.setToolTipText("Eliminar una agencia");
        btnAgregarEmpleado.setToolTipText("Agregar un nuevo empleado a una agencia");
        btnBuscarEmpleado.setToolTipText("Buscar un empleado en una agencia");
        btnListarEmpleados.setToolTipText("Listar todos los empleados de una agencia");
        btnEliminarEmpleado.setToolTipText("Eliminar un empleado de una agencia");

        // Listeners para acciones
        btnAgregarAgencia.addActionListener(e -> agregarAgencia(areaResultados));
        btnBuscarAgencia.addActionListener(e -> buscarAgencia(areaResultados));
        btnListarAgencias.addActionListener(e -> listarAgencias(areaResultados));
        btnEliminarAgencia.addActionListener(e -> eliminarAgencias(areaResultados));
        btnAgregarEmpleado.addActionListener(e -> agregarEmpleado(areaResultados));
        btnBuscarEmpleado.addActionListener(e -> buscarEmpleado(areaResultados));
        btnListarEmpleados.addActionListener(e -> listarEmpleados(areaResultados));
        btnEliminarEmpleado.addActionListener(e -> eliminarEmpleado(areaResultados));
    }

    // Método auxiliar para crear botones estilizados
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setBackground(new Color(220, 220, 220));
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return boton;
    }

    private void agregarAgencia(JTextArea areaResultados) {
        JTextField txtCodigo = new JTextField();
        JTextField txtCanton = new JTextField();
        Object[] mensaje = {
            "Código de la Agencia:", txtCodigo,
            "Cantón:", txtCanton
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Agregar Agencia", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            try {
                int codigo = Integer.parseInt(txtCodigo.getText());
                String canton = txtCanton.getText();

                // Validar si el código ya existe
                if (arbolAgencias.buscar(new Agencia(codigo, ""))) {
                    JOptionPane.showMessageDialog(this, "Ya existe una agencia con el código " + codigo + ".", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                  // Validar el rango del código
                if (!validarRangoCodigo(codigo)) {
                    JOptionPane.showMessageDialog(this, "El código " + codigo + " no pertenece a un rango válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                arbolAgencias.agregar(new Agencia(codigo, canton));
                areaResultados.append("Agencia agregada: Código " + codigo + ", Cantón " + canton + "\n");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Código debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validarRangoCodigo(int codigo) {
        // Definir los rangos por provincia
        int[][] rangos = {
            {1, 100},     // Alajuela
            {101, 200},   // Limón
            {201, 300},   // Guanacaste
            {301, 400},   // San José
            {401, 500},   // Cartago
            {501, 600},   // Heredia
            {601, 700}    // Puntarenas
        };
    
        // Verificar si el código pertenece a alguno de los rangos
        for (int[] rango : rangos) {
            if (codigo >= rango[0] && codigo <= rango[1]) {
                return true;
            }
        }
    
        return false; // No pertenece a ningún rango
    }
    

    private void buscarAgencia(JTextArea areaResultados) {
        String codigoStr = JOptionPane.showInputDialog(this, "Ingrese el código de la Agencia:");
        if (codigoStr != null) {
            try {
                int codigo = Integer.parseInt(codigoStr);
                if (arbolAgencias.buscar(new Agencia(codigo, ""))) {
                    areaResultados.append("Agencia encontrada: Código " + codigo + "\n");
                } else {
                    areaResultados.append("Agencia no encontrada: Código " + codigo + "\n");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Código debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void listarAgencias(JTextArea areaResultados) {
        String[] opciones = {"InOrden", "PreOrden", "PostOrden"};
        String orden = (String) JOptionPane.showInputDialog(this, "Seleccione el orden:", "Listar Agencias",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (orden != null) {
            areaResultados.append("------Agencias en orden------" + orden + "------"+ ":\n");
            String resultado = arbolAgencias.listar(orden.toLowerCase()); // Obtener listado como texto
            areaResultados.append(resultado); // Mostrarlo en el JTextArea
        }
    }
    
    private void eliminarAgencias(JTextArea areaResultados) {
        String codigoStr = JOptionPane.showInputDialog(this, "Ingrese el código de la Agencia a eliminar:");
        if (codigoStr != null) {
            try {
                int codigo = Integer.parseInt(codigoStr);
                if (arbolAgencias.buscar(new Agencia(codigo, ""))) {
                    arbolAgencias.eliminar(new Agencia(codigo, ""));
                    areaResultados.append("Agencia eliminada: Código " + codigo + "\n");
                } else {
                    areaResultados.append("Agencia no encontrada: Código " + codigo + "\n");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Código debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void agregarEmpleado(JTextArea areaResultados) {
        JTextField txtCodigoAgencia = new JTextField();
        JTextField txtNumeroEmpleado = new JTextField();
        JTextField txtNombreEmpleado = new JTextField();
        JTextField txtPuesto = new JTextField();
        JTextField txtSalario = new JTextField();

        Object[] mensaje = {
            "Código de la Agencia:", txtCodigoAgencia,
            "Número del Empleado:", txtNumeroEmpleado,
            "Nombre del Empleado:", txtNombreEmpleado,
            "Puesto (Ventas, Distribución, Bodega):", txtPuesto,
            "Salario:", txtSalario
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Agregar Empleado", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            try {
                int codigoAgencia = Integer.parseInt(txtCodigoAgencia.getText());
                Nodo<Agencia> nodoAgencia = arbolAgencias.buscarNodo(new Agencia(codigoAgencia, ""));
                if (nodoAgencia != null) {
                    Agencia agencia = nodoAgencia.data;
                    int numeroEmpleado = Integer.parseInt(txtNumeroEmpleado.getText());
                    String nombre = txtNombreEmpleado.getText();
                    String puesto = txtPuesto.getText();
                    double salario = Double.parseDouble(txtSalario.getText());

                    if (!puesto.equalsIgnoreCase("Ventas") &&
                        !puesto.equalsIgnoreCase("Distribución") &&
                        !puesto.equalsIgnoreCase("Bodega")) {
                        JOptionPane.showMessageDialog(this, "Puesto no válido.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    agencia.getEmpleados().agregar(new Empleado(numeroEmpleado, nombre, puesto, salario));
                    areaResultados.append("Empleado agregado a Agencia " + codigoAgencia + ": " + nombre + "\n");
                } else {
                    areaResultados.append("Agencia no encontrada: Código " + codigoAgencia + "\n");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Valores numéricos inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarEmpleado(JTextArea areaResultados) {
        JTextField txtCodigoAgencia = new JTextField();
        JTextField txtNumeroEmpleado = new JTextField();
        Object[] mensaje = {
         "Código de la Agencia:", txtCodigoAgencia,
         "Número del Empleado:", txtNumeroEmpleado
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Eliminar Empleado", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            try {
                int codigoAgencia = Integer.parseInt(txtCodigoAgencia.getText());
                int numeroEmpleado = Integer.parseInt(txtNumeroEmpleado.getText());

                Nodo<Agencia> nodoAgencia = arbolAgencias.buscarNodo(new Agencia(codigoAgencia, ""));
                if (nodoAgencia != null) {
                    Agencia agencia = nodoAgencia.data;
                    if (agencia.getEmpleados().buscar(new Empleado(numeroEmpleado, "", "", 0.0))) {
                        agencia.getEmpleados().eliminar(new Empleado(numeroEmpleado, "", "", 0.0));
                        areaResultados.append("Empleado Eliminado: Número " + numeroEmpleado + "\n");
                    } else {
                        areaResultados.append("Empleado no encontrado: Número " + numeroEmpleado + "\n");
                    }
                } else {
                    areaResultados.append("Agencia no encontrada: Código " + codigoAgencia + "\n");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Valores numéricos inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }    
    }

    private void buscarEmpleado(JTextArea areaResultados) {
        JTextField txtCodigoAgencia = new JTextField();
        JTextField txtNumeroEmpleado = new JTextField();
        Object[] mensaje = {
         "Código de la Agencia:", txtCodigoAgencia,
         "Número del Empleado:", txtNumeroEmpleado
        };

        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Buscar Empleado", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            try {
                int codigoAgencia = Integer.parseInt(txtCodigoAgencia.getText());
                int numeroEmpleado = Integer.parseInt(txtNumeroEmpleado.getText());

                Nodo<Agencia> nodoAgencia = arbolAgencias.buscarNodo(new Agencia(codigoAgencia, ""));
                if (nodoAgencia != null) {
                    Agencia agencia = nodoAgencia.data;
                    if (agencia.getEmpleados().buscar(new Empleado(numeroEmpleado, "", "", 0.0))) {
                        areaResultados.append("Empleado encontrado: Número " + numeroEmpleado + "\n");
                    } else {
                        areaResultados.append("Empleado no encontrado: Número " + numeroEmpleado + "\n");
                    }
                } else {
                    areaResultados.append("Agencia no encontrada: Código " + codigoAgencia + "\n");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Valores numéricos inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    private void listarEmpleados(JTextArea areaResultados) {
        String codigoStr = JOptionPane.showInputDialog(this, "Ingrese el código de la Agencia:");
        if (codigoStr != null) {
            try {
                int codigo = Integer.parseInt(codigoStr);
                Nodo<Agencia> nodoAgencia = arbolAgencias.buscarNodo(new Agencia(codigo, ""));
                if (nodoAgencia != null) {
                    Agencia agencia = nodoAgencia.data;
                    areaResultados.append("Empleados de la Agencia " + codigo + ":\n");
                    String empleados = agencia.getEmpleados().listar("inorden");
                    areaResultados.append(empleados);
                } else {
                    areaResultados.append("Agencia no encontrada: Código " + codigo + "\n");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Código debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
