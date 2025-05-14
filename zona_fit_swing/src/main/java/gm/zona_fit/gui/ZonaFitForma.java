package gm.zona_fit.gui;

import gm.zona_fit.modelo.Cliente;
import gm.zona_fit.servicio.ClienteServicio;
import gm.zona_fit.servicio.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class ZonaFitForma extends JFrame {
    /// //////////////atributos de la clase/////////////////////////
    private JPanel panelPrincipal;
    private JTable clientesTabla;
    private JTextField nombreTexto;
    private JTextField apellidoTexto;
    private JTextField membresiaTexto;
    private JButton guardarButton;
    private JButton eliminarButton;
    private JButton limpiarButton;
    IClienteServicio clienteServicio;
    private DefaultTableModel tablaModeloClientes;
    //integer es un objeto asi que podemos guardar datos como null
    private Integer idCliente;


    /// ////////////////////////CONSTRUCTOR//////////////////////////////////////////
    //constructor de la forma tambien se agrega el autowired en el constructor
    @Autowired
    public ZonaFitForma(ClienteServicio clienteServicio) {

        this.clienteServicio = clienteServicio;
        iniciarForma();

        guardarButton.addActionListener(e -> guardarCliente());

        clientesTabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = clientesTabla.getSelectedRow();
                cargarClienteSeleccionado();
            }
        });

        eliminarButton.addActionListener(e -> eliminarCliente());

        limpiarButton.addActionListener(e -> limpiarFormulario());
    }


    /// ////////////////////////////METODOS///////////////////////////////////////////
    //este metodo es para iniciar el JFRAME
    //Lo que se encuentra dentro son las configuraciones basicas para que se muestre el Jframe
    private void iniciarForma() {
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);//centra la ventana


    }


    //este metodo se ejecuta antes del constructor
    //este metodo nos permite crear y modificar los objetos que seleccionemos como custom
    //para este ejercicio solo se trabajara con la tabla como custom
    private void createUIComponents() {
        // TODO: place custom component creation code here
        //el 0 son los registros que estos seran dinamicos, el 4 son las columnas de la tabla
        //this.tablaModeloClientes = new DefaultTableModel(0, 4);
        this.tablaModeloClientes = new DefaultTableModel(0,4){
            @Override
            public boolean isCellEditable (int row, int column){
                return false;
            }
        };
        //recuerda que este es un arreglo de datos
        String[] cabeceros = {"Id", "Nombre", "Apellido", "Membresia"};
        //el siguiente metodo es para agregar los identificadores de tabla
        this.tablaModeloClientes.setColumnIdentifiers(cabeceros);
        //creamos la tabla como la teniamos antes
        this.clientesTabla = new JTable(tablaModeloClientes);
        //restringimos la multiseleccion en la tabla
        this.clientesTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //Cargar listado clientes
        listarClientes();
    }

    private void listarClientes() {
        this.tablaModeloClientes.setRowCount(0);
        var clientes = this.clienteServicio.listarClientes();
        clientes.forEach(cliente -> {
            Object[] renglonCliente = {
                    cliente.getId(),
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.getMembresia()

            };
            this.tablaModeloClientes.addRow(renglonCliente);

        });


    }


    private void guardarCliente() {

        //validaciones
        if (nombreTexto.getText().equals("")) {

            mostrarMensaje("Proporciona un nombre");
            nombreTexto.requestFocusInWindow();
            return;


        }
        if (membresiaTexto.getText().equals("")){
            mostrarMensaje("Proporciona una membresia");
            nombreTexto.requestFocusInWindow();
            return;

        }
    //recuperamos los datos del formulario

        var nombre = nombreTexto.getText();
        var apellido = apellidoTexto.getText();
        var membresia =Integer.parseInt(membresiaTexto.getText());

    //un nuevo objeto de tipo cliente
        var cliente = new Cliente(this.idCliente, nombre, apellido, membresia);
    //Guardamos el objeto en la BD con ayuda de la clase clienteServicio y su metoido guardar
    this.clienteServicio.guardarCliente(cliente);
     if (this.idCliente == null){

         mostrarMensaje("Se agrego un nuevo cliente");

     }else{

         mostrarMensaje("Se actualizo un cliente");

     }
    //limpiamos formulario
    limpiarFormulario();

    //listamos de nuevo
        listarClientes();
    }



    private void cargarClienteSeleccionado(){
        //clientes tabla despues usamos el metodo getSelectedRow()
        var renglon = clientesTabla.getSelectedRow();
        if(renglon != -1){
            //esta linea de codigo guarda el id de la linea que se selcciona
            //(renglon, 0). esto indica que la fila 0 es de la cual se van a tomar los datos
            var id = clientesTabla.getModel().getValueAt(renglon, 0).toString();
            this.idCliente = Integer.parseInt(id);
            //ahora el 1 seria el nombre
            var nombre = clientesTabla.getModel().getValueAt(renglon, 1).toString();
            this.nombreTexto.setText(nombre);
            var apellido=clientesTabla.getModel().getValueAt(renglon, 2).toString();
            this.apellidoTexto.setText(apellido);
            var membresia = clientesTabla.getModel().getValueAt(renglon, 3).toString();
            //setText inserta texto en la caja de texto
            this.membresiaTexto.setText(membresia);
        }


    }

    private void eliminarCliente(){
        var renglon = clientesTabla.getSelectedRow();
        if (renglon != -1){
            var idClienteStr = clientesTabla.getModel().getValueAt(renglon,0).toString();
            this.idCliente = Integer.parseInt(idClienteStr);
            var cliente = new Cliente();
            cliente.setId(this.idCliente);
            clienteServicio.eliminarCliente(cliente);
            mostrarMensaje("Cliente con id "+ this.idCliente + " eliminado");
            limpiarFormulario();
            listarClientes();
        }else{
            mostrarMensaje("Debe seleccionar cun cliente para eliminar");
        }



    }

    //metodos comunes
    private void mostrarMensaje(String mensaje) {

        JOptionPane.showMessageDialog(this, mensaje);


    }

    private void limpiarFormulario(){
        nombreTexto.setText("");
        apellidoTexto.setText("");
        membresiaTexto.setText("");
        //limpiamos el id seleccionado
        this.idCliente = null;
        //deseleccionmos en la tabla
        this.clientesTabla.getSelectionModel().clearSelection();

    }

}