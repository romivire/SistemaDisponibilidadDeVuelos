import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.ParseException;
import java.util.Enumeration;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import java.awt.Color;
import javax.swing.JPasswordField;
import quick.dbtable.DBTable;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.text.MaskFormatter;

import javax.swing.JFrame;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JDesktopPane;

public class DisponibilidadVuelos extends JInternalFrame {
	private JTextField usuario;
	private JPasswordField contrasena;
	private JPanel panel;
	private JLabel label;
	private DBTable tablaClases;
	private JFormattedTextField fechaIda;
	private JFormattedTextField fechaVuelta;
	private JCheckBox checkBoxIdaVuelta;
	private JCheckBox checkBoxIda;
	private DBTable tablaVuelos;
	private JPanel panel_1;
	private JTextPane textPane;
	private JButton button;
	private JLabel label_1;
	protected Connection conexionBD = null;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JComboBox comboDestino;
	private JComboBox comboOrigen;
	private DBTable tablaClaseVuelta;
	private DBTable tablaVuelosVuelta;
	private JButton btnReservar_1;
	private JButton btnReservar;
	private JTextField TipoTxt;
	private JTextField NumeroTxt;
	private JLabel lblEstaReservandoPara;
	private JLabel lblEstaReservandoPara_1;
	private JTextField idaReserva;
	private JPanel panel_2;
	private JTextField vueltaReserva;
	private Date fVuelta;
	private Date fIda;
	private String ciudaD="";
	private String ciudaO="";
	private JLabel lblClaseYPrecio;
	private JLabel label_2;
	private JTextField claseIdaTxt;
	private JTextField claseVueltaTxt;
	private int legajoNumerico;

	/**
	 * Create the frame.
	 */
	public DisponibilidadVuelos() {
		setSize(new Dimension(1288, 681));
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1276, 681);
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(SystemColor.textHighlight);
		panel.setBounds(10, 11, 178, 764);
		getContentPane().add(panel);
		
		label = new JLabel("Usuario");
		label.setFont(new Font("Arial", Font.BOLD, 13));
		label.setBounds(10, 116, 57, 14);
		panel.add(label);
		
		label_1 = new JLabel("Contrase\u00F1a");
		label_1.setFont(new Font("Arial", Font.BOLD, 13));
		label_1.setBounds(10, 168, 86, 14);
		panel.add(label_1);
		
		usuario = new JTextField();
		usuario.setColumns(10);
		usuario.setBounds(10, 137, 86, 20);
		panel.add(usuario);
		
		button = new JButton("Ingresar");
		button.setBounds(52, 262, 89, 23);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conectarBD();
				inicializarCiudades();
			}
		});
		panel.add(button);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setText("Ingrese usuario y contrase\u00F1a");
		textPane.setForeground(Color.BLACK);
		textPane.setFont(new Font("Arial", Font.BOLD, 18));
		textPane.setBackground(SystemColor.textHighlight);
		textPane.setBounds(10, 34, 177, 71);
		panel.add(textPane);
		
		contrasena = new JPasswordField();
		contrasena.setBounds(10, 198, 86, 20);
		panel.add(contrasena);
		
		panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(SystemColor.activeCaptionBorder);
		panel_1.setBounds(198, 11, 1369, 764);
		getContentPane().add(panel_1);
		
		tablaVuelos = new DBTable();
		tablaVuelos.setToolTipText("Doble-click o Espacio para seleccionar el registro.");
		tablaVuelos.setEditable(false);
		tablaVuelos.setBounds(10, 148, 868, 200);
		tablaVuelos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
			mostrarInfoVuelo(tablaVuelos);
			actualizarReserva(tablaVuelos);
			}
		});
		panel_1.add(tablaVuelos);
		
		checkBoxIda = new JCheckBox("Vuelo de ida");
		buttonGroup.add(checkBoxIda);
		checkBoxIda.setBounds(244, 39, 125, 23);
		panel_1.add(checkBoxIda);
		
		checkBoxIdaVuelta = new JCheckBox("Vuelo de ida y vuelta");
		buttonGroup.add(checkBoxIdaVuelta);
		checkBoxIdaVuelta.setBounds(244, 89, 125, 23);
		panel_1.add(checkBoxIdaVuelta);
		
		try {
			fechaVuelta = new JFormattedTextField(new MaskFormatter("##'/##'/####"));
			fechaIda = new JFormattedTextField(new MaskFormatter("##'/##'/####"));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		fechaVuelta.setBounds(410, 88, 207, 24);
		panel_1.add(fechaVuelta);
	
		fechaIda.setBounds(410, 38, 207, 24);
		panel_1.add(fechaIda);
		
		tablaClases = new DBTable();
		tablaClases.setEditable(false);
		tablaClases.setBounds(888, 148, 471, 200);
		tablaClases.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
			actualizarClaseReserva(tablaClases);
			}
		});
		
		panel_1.add(tablaClases);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.setFont(new Font("Arial", Font.BOLD, 13));
		btnConsultar.setBounds(663, 62, 110, 23);
		panel_1.add(btnConsultar);
		
		comboOrigen = new JComboBox();
		comboOrigen.setBounds(10, 39, 207, 26);
		panel_1.add(comboOrigen);
		
		comboDestino = new JComboBox();
		comboDestino.setBounds(10, 89, 207, 24);
		panel_1.add(comboDestino);
		
		tablaVuelosVuelta = new DBTable();
		tablaVuelosVuelta.setToolTipText("Doble-click o Espacio para seleccionar el registro.");
		tablaVuelosVuelta.setEditable(false);
		tablaVuelosVuelta.setBounds(10, 373, 868, 200);
		tablaVuelosVuelta.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
			mostrarInfoVuelo(tablaVuelosVuelta);
			actualizarReserva(tablaVuelosVuelta);
			}
		});
		panel_1.add(tablaVuelosVuelta);
		
		tablaClaseVuelta = new DBTable();
		tablaClaseVuelta.setEditable(false);
		tablaClaseVuelta.setBounds(888, 373, 471, 200);
		tablaClaseVuelta.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
			actualizarClaseReserva(tablaClaseVuelta);
			}
		});
		panel_1.add(tablaClaseVuelta);
		
		JLabel lblCiudadorigen = new JLabel("Ciudad de Origen");
		lblCiudadorigen.setFont(new Font("Arial", Font.BOLD, 12));
		lblCiudadorigen.setBounds(10, 21, 125, 14);
		panel_1.add(lblCiudadorigen);
		
		JLabel lblCiudadDeDestino = new JLabel("Ciudad de Destino");
		lblCiudadDeDestino.setFont(new Font("Arial", Font.BOLD, 12));
		lblCiudadDeDestino.setBounds(10, 71, 125, 14);
		panel_1.add(lblCiudadDeDestino);
		
		JLabel lblOpcionDeBusqueda = new JLabel("Opcion de busqueda");
		lblOpcionDeBusqueda.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblOpcionDeBusqueda.setFont(new Font("Arial", Font.BOLD, 12));
		lblOpcionDeBusqueda.setBounds(244, 21, 125, 14);
		panel_1.add(lblOpcionDeBusqueda);
		
		JLabel lblFechaDeIda = new JLabel("Fecha de ida");
		lblFechaDeIda.setFont(new Font("Arial", Font.BOLD, 12));
		lblFechaDeIda.setBounds(410, 22, 140, 14);
		panel_1.add(lblFechaDeIda);
		
		JLabel lblFechaDeVuelta = new JLabel("Fecha de Vuelta");
		lblFechaDeVuelta.setFont(new Font("Arial", Font.BOLD, 12));
		lblFechaDeVuelta.setBounds(410, 71, 140, 14);
		panel_1.add(lblFechaDeVuelta);
		
		JLabel lblVuelosDeIda = new JLabel("Vuelos de Ida");
		lblVuelosDeIda.setFont(new Font("Arial", Font.BOLD, 14));
		lblVuelosDeIda.setBounds(10, 124, 173, 23);
		panel_1.add(lblVuelosDeIda);
		
		JLabel lblVuelosDeVuelta = new JLabel("Vuelos de Vuelta");
		lblVuelosDeVuelta.setFont(new Font("Arial", Font.BOLD, 14));
		lblVuelosDeVuelta.setBounds(10, 351, 173, 23);
		panel_1.add(lblVuelosDeVuelta);
		
		JLabel lblClasesEnVuelos = new JLabel("Clases en Vuelos de Ida");
		lblClasesEnVuelos.setFont(new Font("Arial", Font.BOLD, 14));
		lblClasesEnVuelos.setBounds(895, 124, 189, 23);
		panel_1.add(lblClasesEnVuelos);
		
		JLabel lblClasesEnVuelo = new JLabel("Clases en Vuelo de Vuelta");
		lblClasesEnVuelo.setFont(new Font("Arial", Font.BOLD, 14));
		lblClasesEnVuelo.setBounds(888, 346, 217, 32);
		panel_1.add(lblClasesEnVuelo);
		
		panel_2 = new JPanel();
		panel_2.setBounds(10, 586, 351, 167);
		panel_1.add(panel_2);
		panel_2.setVisible(false);
		panel_2.setLayout(null);
		
		JLabel lblIngreseLosDatos = new JLabel("Ingrese los datos del Pasajero para reservar");
		lblIngreseLosDatos.setFont(new Font("Arial", Font.BOLD, 13));
		lblIngreseLosDatos.setBounds(31, 11, 292, 21);
		panel_2.add(lblIngreseLosDatos);
		
		TipoTxt = new JTextField();
		TipoTxt.setBounds(129, 53, 86, 20);
		panel_2.add(TipoTxt);
		TipoTxt.setColumns(10);
		
		NumeroTxt = new JTextField();
		NumeroTxt.setColumns(10);
		NumeroTxt.setBounds(129, 98, 86, 20);
		panel_2.add(NumeroTxt);
		
		JLabel lblTipoDeDocumento = new JLabel("Tipo de Documento");
		lblTipoDeDocumento.setFont(new Font("Arial", Font.PLAIN, 11));
		lblTipoDeDocumento.setBounds(125, 39, 110, 14);
		panel_2.add(lblTipoDeDocumento);
		
		JLabel lblNumeroDeDocumento = new JLabel("Numero de Documento");
		lblNumeroDeDocumento.setFont(new Font("Arial", Font.PLAIN, 11));
		lblNumeroDeDocumento.setBounds(116, 84, 119, 14);
		panel_2.add(lblNumeroDeDocumento);
		
		btnReservar_1 = new JButton("Ingresar");
		btnReservar_1.setBounds(116, 133, 110, 23);
		panel_2.add(btnReservar_1);
		btnReservar_1.setFont(new Font("Arial", Font.BOLD, 13));
		
		lblEstaReservandoPara = new JLabel("Esta reservando para vuelo de ida:");
		lblEstaReservandoPara.setBounds(394, 595, 207, 14);
		panel_1.add(lblEstaReservandoPara);
		
		lblEstaReservandoPara_1 = new JLabel("Esta reservando para vuelo de vuelta:");
		lblEstaReservandoPara_1.setBounds(394, 631, 223, 14);
		panel_1.add(lblEstaReservandoPara_1);
		
		idaReserva = new JTextField();
		idaReserva.setEditable(false);
		idaReserva.setBounds(392, 608, 262, 20);
		panel_1.add(idaReserva);
		idaReserva.setColumns(10);
		
		vueltaReserva = new JTextField();
		vueltaReserva.setEditable(false);
		vueltaReserva.setColumns(10);
		vueltaReserva.setBounds(394, 648, 262, 20);
		panel_1.add(vueltaReserva);
		
		lblClaseYPrecio = new JLabel("Clase y Precio");
		lblClaseYPrecio.setBounds(680, 595, 103, 14);
		panel_1.add(lblClaseYPrecio);
		
		label_2 = new JLabel("Clase y Precio");
		label_2.setBounds(680, 631, 103, 14);
		panel_1.add(label_2);
		
		claseIdaTxt = new JTextField();
		claseIdaTxt.setEditable(false);
		claseIdaTxt.setColumns(10);
		claseIdaTxt.setBounds(680, 608, 198, 20);
		panel_1.add(claseIdaTxt);
		
		claseVueltaTxt = new JTextField();
		claseVueltaTxt.setEditable(false);
		claseVueltaTxt.setColumns(10);
		claseVueltaTxt.setBounds(680, 648, 198, 20);
		panel_1.add(claseVueltaTxt);
		
		btnReservar = new JButton("Reservar ");
		btnReservar.setFont(new Font("Arial", Font.BOLD, 13));
		btnReservar.setBounds(611, 699, 110, 23);
		panel_1.add(btnReservar);
		btnReservar.setEnabled(false);
		
		btnReservar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panel_2.setVisible(true);
				TipoTxt.setText("");
				NumeroTxt.setText("");
				
			}});
		
		
		btnReservar_1.addActionListener(new ActionListener() {
    		
		public void actionPerformed(ActionEvent arg0) {
			boolean procesoCorrecto=true;
			String mensaje="";
			if(TipoTxt.getText().equals("")) {
				mensaje="Ingrese un tipo de Docuemento";
				procesoCorrecto=false;
			} 
			else if(NumeroTxt.getText().equals("")) {
				mensaje="Ingrese un numero de Documento";
				procesoCorrecto=false;
				} 
				else if(claseIdaTxt.getText().equals("") || idaReserva.getText().equals("")){
						mensaje="Ingrese vuelo y clase para la reserva del Vuelo de Ida";
						procesoCorrecto=false;
						
					}
					else if(obtenerBotonSeleccionado(buttonGroup).equals("Vuelo de ida y vuelta") && (claseVueltaTxt.getText().equals("") || vueltaReserva.getText().equals(""))){
						mensaje="Ingrese vuelo y clase para la reserva del Vuelo de Vuelta";
						procesoCorrecto=false;
						}
						else{
							try{
						Statement stmt = conexionBD.createStatement();
			            
						String tipoDni=TipoTxt.getText();
						int nroDni=Integer.parseInt(NumeroTxt.getText());

						String sql = "SELECT doc_tipo,doc_nro FROM pasajeros having (doc_tipo = " + '"' + tipoDni +'"'
									+ " and doc_nro="+ nroDni+")";
						String[] columnas = null;

						boolean isResultSet = stmt.execute(sql);
						ResultSet resultado = null;

						if (isResultSet) {
							resultado = stmt.getResultSet();
							int cantFilas = 0;
							if (resultado.last())
									cantFilas = resultado.getRow();
							if (cantFilas == 1) { //Hay un empleado en la BD que coincide con el usuario y contrasena
								if(obtenerBotonSeleccionado(buttonGroup).equals("Vuelo de ida")){
									confirmarReserva();
								}
								else {
									confirmarReservaIdayVuelta();
								}
								
						    }
							else {
								mensaje="Ingrese tipo y numero de Documento existente";
								procesoCorrecto=false;
							}
						}
					}
					catch(SQLException ex)
			         {
			        	 mensaje = "SQLException: " + ex.getMessage() + "\n" + "SQLState: " + ex.getSQLState() + "\n"
									+ "VendorError: " + ex.getErrorCode();
			        	
			         }
				}
			if(mensaje!=null && procesoCorrecto==false) {
				 mostrarMensaje(mensaje);
			}
		}
		}
      );
		
		btnConsultar.addActionListener(new ActionListener() {
    		
			public void actionPerformed(ActionEvent arg0) {
				panel_2.setVisible(false);
      		  	if(validarEntrada()==true){
      		  		realizarConsulta(); 
      		  		btnReservar.setEnabled(true);
      		  	}
      		  		
      		  	String sql="select null from vuelos_disponibles";
				 
      		  	tablaClases.setSelectSql(sql);    //Limpia la tabla de las clases de la consulta anterior	
				tablaClaseVuelta.setSelectSql(sql); //Limpia la tabla de las clases de la consulta anterior
				if(obtenerBotonSeleccionado(buttonGroup).equals("Vuelo de ida")){
				 tablaVuelosVuelta.setSelectSql(sql);}

		    	  try {
					tablaClases.refresh();
					tablaClaseVuelta.refresh();
					tablaVuelosVuelta.refresh();
				} catch (SQLException ex) {
					String mensaje = "SQLException: " + ex.getMessage() + "\n" + "SQLState: " + ex.getSQLState() + "\n"
							+ "VendorError: " + ex.getErrorCode();
	        	 JOptionPane.showMessageDialog(null,
	                        mensaje, 
	                        "Error",
	                        JOptionPane.ERROR_MESSAGE);
	        	 
				}
		    	  
		    	  claseIdaTxt.setText("");
			      claseVueltaTxt.setText("");
			      idaReserva.setText("");
			      vueltaReserva.setText("");
			}
			
      	});
		
		panel_1.setVisible(false);

		this.addInternalFrameListener(new InternalFrameAdapter(){
            public void internalFrameClosing(InternalFrameEvent e) {
            	//Acciones a realizar cuando se cierra el JInternalFrame
				 panel_1.setVisible(false);
	                usuario.setText("");
					contrasena.setText("");
				buttonGroup.clearSelection();
				
				if(conexionBD!=null) {
					String sql="select null from vuelos_disponibles;";
					 tablaVuelos.setSelectSql(sql);   //Vacia la tabla de vuelos consultados 	     	
					 tablaClases.setSelectSql(sql);	  //Vacia la tabla de vuelos consultados
					 tablaVuelosVuelta.setSelectSql(sql);
					 tablaClaseVuelta.setSelectSql(sql);
			    	  try {
						tablaVuelos.refresh();
						tablaClases.refresh();
						tablaVuelosVuelta.refresh();
						tablaClaseVuelta.refresh();
					} catch (SQLException ex) {
						String mensaje = "SQLException: " + ex.getMessage() + "\n" + "SQLState: " + ex.getSQLState() + "\n"
								+ "VendorError: " + ex.getErrorCode();
		        	 JOptionPane.showMessageDialog(null,
		                        mensaje, 
		                        "Error",
		                        JOptionPane.ERROR_MESSAGE);
					}
			    	  usuario.setEnabled(true);
						contrasena.setEnabled(true);
						button.setEnabled(true);
			    	  desconectarBD();
				}
            }
        });
		
	}
	
	private void inicializarCiudades() {
	//Inicializa los  JComboBox con las ciudades de Origen y Destino respectivamente
		try {
			Statement stmt;
			String sql= "select distinct Ciudad_Salida,Pais_Salida from vuelos_disponibles";
			stmt = this.conexionBD.createStatement();
			ResultSet rs= stmt.executeQuery(sql);
			String nombresS []= obtenerContenidoFilas(rs);
			rs.beforeFirst();
			for (int i=0;i<nombresS.length;i++) {
				if(rs.next())
				comboOrigen.addItem(nombresS[i]+","+rs.getObject(2));
			}
			
			String sql2= "select distinct Ciudad_Llegada,Pais_Llegada from vuelos_disponibles";
			rs= stmt.executeQuery(sql2);
			String nombresL []= obtenerContenidoFilas(rs);
			rs.beforeFirst();
			for (int i=0;i<nombresL.length;i++) {
				if(rs.next())
				comboDestino.addItem(nombresL[i]+","+rs.getObject(2));
			}
		}catch (SQLException e) {
			String mensaje = "SQLException: " + e.getMessage() + "\n" + "SQLState: " + e.getSQLState() + "\n"
					+ "VendorError: " + e.getErrorCode();
    	 JOptionPane.showMessageDialog(null,
                    mensaje, 
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
		}
		
		
		
		}
	
	private void conectarBD()
	   { //Conecta a la BD y verifica que el usuario y contrasena sean correctos
			
	         try
	         {
	            String driver ="com.mysql.cj.jdbc.Driver";
	        	String servidor = "localhost:3306";
	        	String baseDatos = "vuelos"; 
	        	String usuario = "empleado";
	        	String clave = "empleado";
	            String uriConexion = "jdbc:mysql://" + servidor + "/" + 
	        	                     baseDatos +"?serverTimezone=America/Argentina/Buenos_Aires";
	   
	            this.conexionBD = DriverManager.getConnection(uriConexion, usuario, clave);
	       //establece una conexión con la  B.D. "batallas"  usando directamante una tabla DBTable    
	            tablaVuelosVuelta.connectDatabase(driver, uriConexion, usuario, clave);
	            tablaClaseVuelta.connectDatabase(driver, uriConexion, usuario, clave);
	            tablaVuelos.connectDatabase(driver, uriConexion, usuario, clave);
	            tablaClases.connectDatabase(driver, uriConexion, usuario, clave);
	         
	            String usu=this.usuario.getText();
	            String passw=contrasena.getText();
	          	         
	            Statement stmt = conexionBD.createStatement();
	            
				legajoNumerico = Integer.parseInt(usu);

				String sql = "SELECT legajo, password FROM empleados having legajo = " + legajoNumerico
							+ " and password = md5('" + passw + "');";
				String[] columnas = null;

				boolean isResultSet = stmt.execute(sql);
				ResultSet resultado = null;

				if (isResultSet) {
					resultado = stmt.getResultSet();
					int cantFilas = 0;
					if (resultado.last())
							cantFilas = resultado.getRow();
					if (cantFilas == 1) { //Hay un empleado en la BD que coincide con el usuario y contrasena
							panel_1.setVisible(true);
							this.usuario.setEnabled(false);
							this.contrasena.setEnabled(false);
							button.setEnabled(false);
				    }else{
							JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
			                       "Identificacion invalida. Ingrese usuario y contrasena nuevamente", 
			                        "Error",
			                        JOptionPane.ERROR_MESSAGE);
							this.usuario.setText("");
							this.contrasena.setText("");
							desconectarBD();
					}
				}
	        }
				catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
	                        "Legajo invalido. Ingrese legajo nuevamente", 
	                        "Error",
	                        JOptionPane.ERROR_MESSAGE);
					this.usuario.setText("");
					this.contrasena.setText("");
					desconectarBD();
				}
				catch (SQLException ex)
		         {
		        	 String mensaje = "SQLException: " + ex.getMessage() + "\n" + "SQLState: " + ex.getSQLState() + "\n"
								+ "VendorError: " + ex.getErrorCode();
		        	 JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
		                        mensaje, 
		                        "Error",
		                        JOptionPane.ERROR_MESSAGE);
		         }
	         catch (ClassNotFoundException e)
	         {
	            e.printStackTrace();
	         }
		  
	}
	
	private void realizarConsulta() {
		
		if(obtenerBotonSeleccionado(buttonGroup).equals("Vuelo de ida")){
			fechaVuelta.setValue(null);
		}
		//Primero recupero el nombre de la Ciudad del String "Ciudad,Pais" del JComboBox
		String o=(String) comboOrigen.getSelectedItem();
		String d=(String) comboDestino.getSelectedItem();
		
		ciudaD="";
		ciudaO="";
		for(int i=0;i<o.length()&&o.charAt(i)!=',';i++)
		{
			ciudaO+=o.charAt(i);
		}
		for(int i=0;i<d.length()&&d.charAt(i)!=',';i++)
		{
			ciudaD+=d.charAt(i);
		}
		//Recupero la fecha de Ida
		
		fIda=Fechas.convertirStringADateSQL(fechaIda.getText());
		
		//Obtengo todos los vuelos de ida
		try {
			String sql="select distinct Vuelo,Fecha,Aeropuerto_Salida,Hora_Salida,Aeropuerto_Llegada,Hora_Llegada,tiempo_estimado from vuelos_disponibles where (Ciudad_Salida='"+ciudaO+"' and Ciudad_Llegada='"+ciudaD+"' and Fecha='"+fIda+"');";
			tablaVuelos.setSelectSql(sql);
			tablaVuelos.createColumnModelFromQuery();    	    
			for (int i = 0; i < tablaVuelos.getColumnCount(); i++)
	  	  	{ // para que muestre correctamente los valores de tipo TIME (hora)  		   		  
	  		 if	 (tablaVuelos.getColumn(i).getType()==Types.TIME)  
	  		 {    		 
	  		    tablaVuelos.getColumn(i).setType(Types.CHAR);  
		       	 }
	        }  
			
				tablaVuelos.refresh();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Si estuviera seleccionada la opcion de vuelos de ida y vuelta, busco los vuelos de vuelta
		if(obtenerBotonSeleccionado(buttonGroup).equals("Vuelo de ida y vuelta")){
			try{
				fVuelta=Fechas.convertirStringADateSQL(fechaVuelta.getText());
				String sqlConVuelta="select distinct Vuelo,Aeropuerto_Salida,Hora_Salida,Aeropuerto_Llegada,Hora_Llegada,tiempo_estimado from vuelos_disponibles where (Ciudad_Salida='"+ciudaD+"' and Ciudad_Llegada='"+ciudaO+"' and Fecha='"+fVuelta+"');";
				tablaVuelosVuelta.setSelectSql(sqlConVuelta);   	     	  
				tablaVuelosVuelta.createColumnModelFromQuery();    	    
		    	  for (int i = 0; i < tablaVuelosVuelta.getColumnCount(); i++)
		    	  { // para que muestre correctamente los valores de tipo TIME (hora)  		   		  
		    		 if	 (tablaVuelosVuelta.getColumn(i).getType()==Types.TIME)  
		    		 {    		 
		    		    tablaVuelosVuelta.getColumn(i).setType(Types.CHAR);  
		  	       	 }
		          }  
					tablaVuelosVuelta.refresh();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	private boolean validarEntrada() {
		//Valida todos los datos de entrada de la consulta
		      String mensajeError = null;
		      if(comboOrigen.getSelectedItem().equals(comboDestino.getSelectedItem())) {
					mensajeError= "Ciudad de origen y destino no pueden ser iguales";
		      }
		      else if (comboOrigen.getSelectedIndex()==(-1))
		      {
		         mensajeError = "Debe ingresar ciudad de origen";
		      }
		      else if (comboDestino.getSelectedIndex()==(-1))
		      {
			     mensajeError = "Debe ingresar ciudad de destino";
			     
			  }else if (obtenerBotonSeleccionado(buttonGroup)==null)
		      {
			     mensajeError = "Debe seleccionar una Opcion de Busqueda";
			     
			  }else if (! Fechas.validar(this.fechaIda.getText().trim()))
		      {
		         mensajeError = "En el campo 'Fecha Ida' debe ingresar un valor con el formato dd/mm/aaaa.";
		         
		      }else if(obtenerBotonSeleccionado(buttonGroup).equals("Vuelo de ida y vuelta")&&(! Fechas.validar(this.fechaVuelta.getText().trim()))) 
		      {
		    	  mensajeError = "En el campo 'Fecha Vuelta' debe ingresar un valor con el formato dd/mm/aaaa.";
		      }

		      if (mensajeError != null)
		      {
		         JOptionPane.showMessageDialog(this,
		                                       mensajeError,
		                                       "Error",
		                                       JOptionPane.ERROR_MESSAGE);
		         return false;
		      }
		      return true;
		   }
	
	private void desconectarBD(){

	      if (this.conexionBD != null) {
	         try
	         {	
	            tablaVuelos.close();            
	            tablaClases.close();
	            tablaClaseVuelta.close();
	            tablaVuelosVuelta.close();
	         }
	         catch (SQLException ex)
	         {
	            System.out.println("SQLException: " + ex.getMessage());
	            System.out.println("SQLState: " + ex.getSQLState());
	            System.out.println("VendorError: " + ex.getErrorCode());
	         }   
	      }
	      claseIdaTxt.setText("");
	      claseVueltaTxt.setText("");
	      idaReserva.setText("");
	      vueltaReserva.setText("");
	      panel_2.setVisible(false);
	      btnReservar.setEnabled(false);
	   }
	
	private String[] obtenerContenidoFilas(ResultSet tabla) {
		String nombres[]=null;
		int cantFilas=0,i=0;
		try {
			if(tabla.last()){ 
		          cantFilas = tabla.getRow(); 
		          tabla.beforeFirst(); //nos posicionamos antes del inicio
		     }
			nombres= new String[cantFilas];
			while(tabla.next()) {
				nombres[i]= tabla.getString(1);
				i++;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return nombres;
	}
	
	private String obtenerBotonSeleccionado(ButtonGroup buttonGroup) {
		//Obtiene el boton selecionado del JButtonGroup
		for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }
	
	private void mostrarInfoVuelo(DBTable tabla) {
		//Muestra la informacion de la clase,asientos disponibles y precio de el vuelo seleccionado de la consulta
		Date f=null;
		DBTable clase=null;
		int r=tabla.getSelectedRow();
		String vuelo=(String) tabla.getValueAt(r,0);
		
		if(tabla==tablaVuelos) {
		f=Fechas.convertirStringADateSQL(fechaIda.getText());
		clase=tablaClases;
		}
		if(tabla==tablaVuelosVuelta) {
			f=Fechas.convertirStringADateSQL(fechaVuelta.getText());
			clase=tablaClaseVuelta;
		}
		
		String sql="select Clase,Asientos_Disponibles_En_Clase,Precio_Clase from vuelos_disponibles where Vuelo='"+vuelo+"' and Fecha='"+f+"';";
		try {
		clase.setSelectSql(sql);   	     	   	    
		clase.refresh();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void actualizarReserva(DBTable tabla) {
		Date f=null;
		int r=tabla.getSelectedRow();
		String vuelo=(String) tabla.getValueAt(r,0);
		if(tabla==tablaVuelos) {
			f=Fechas.convertirStringADateSQL(fechaIda.getText());
			idaReserva.setText(vuelo);
			claseIdaTxt.setText("");
			}
			if(tabla==tablaVuelosVuelta) {
				f=Fechas.convertirStringADateSQL(fechaVuelta.getText());
				vueltaReserva.setText(vuelo);
				claseVueltaTxt.setText("");
			}
	}
	private void actualizarClaseReserva(DBTable tabla) {
		int r=tabla.getSelectedRow();
		String clase=(String) tabla.getValueAt(r,0);
		if(tabla==tablaClases) {
			claseIdaTxt.setText(clase);
			
			}
		if(tabla==tablaClaseVuelta) {
			claseVueltaTxt.setText(clase);
			
			}
	}
	private void confirmarReservaIdayVuelta() {
		int dialogResult=JOptionPane.showInternalConfirmDialog(this, 
                "Desea confirmar la Reserva?", "Confirmacion de Reserva", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
		if (dialogResult==JOptionPane.YES_OPTION) {
			String tipoDoc=TipoTxt.getText();
			int nroDoc=Integer.parseInt(NumeroTxt.getText());
			String v=idaReserva.getText();
			String c=claseIdaTxt.getText();
			String v2=vueltaReserva.getText();
			String c2=claseVueltaTxt.getText();
			String r="";
	try {
		 CallableStatement cst = conexionBD.prepareCall("{call ReservaIdaVuelta (?,?,?,?,?,?,?,?,?)}");
		 cst.setString(1, tipoDoc);
		 cst.setInt(2,nroDoc);
		 cst.setInt(3, legajoNumerico);
		 cst.setString(4, v);
		 cst.setDate(5, fIda);
		 cst.setString(6, c);
		 cst.setString(7, v2);
		 cst.setDate(8, fVuelta);
		 cst.setString(9, c2);
		 
         
         // Definimos los tipos de los parametros de salida del procedimiento almacenado
		 ResultSet rs = cst.executeQuery();
	        while (rs.next()) {
	            r = rs.getString("Resultado");
	            }
					 JOptionPane.showMessageDialog(this,
			                   r, 
			                   "Mensaje",
			                   JOptionPane.INFORMATION_MESSAGE); 

				
			
	}
	catch(SQLException e) {

		String mensaje = "SQLException: " + e.getMessage() + "\n" + "SQLState: " + e.getSQLState() + "\n"
					+ "VendorError: " + e.getErrorCode();
		JOptionPane.showMessageDialog(this,
                   mensaje, 
                   "Error",
                   JOptionPane.ERROR_MESSAGE);
	}
						
				
		}
	}
	
	private void confirmarReserva() {

		int dialogResult=JOptionPane.showInternalConfirmDialog(this, 
                "Desea confirmar la Reserva?", "Confirmacion de Reserva", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
		if (dialogResult==JOptionPane.YES_OPTION) {
			String tipoDoc=TipoTxt.getText();
			int nroDoc=Integer.parseInt(NumeroTxt.getText());
			String v=idaReserva.getText();
			String c=claseIdaTxt.getText();
			String r="";
	try {
		 CallableStatement cst = conexionBD.prepareCall("{call ReservaIda (?,?,?,?,?,?)}");
		 cst.setString(1, tipoDoc);
		 cst.setInt(2,nroDoc);
		 cst.setInt(3, legajoNumerico);
		 cst.setString(4, v);
		 cst.setDate(5, fIda);
		 cst.setString(6, c);
		 
		 
         
         // Definimos los tipos de los parametros de salida del procedimiento almacenado
		 ResultSet rs = cst.executeQuery();
	        while (rs.next()) {
	            r = rs.getString("Resultado");
	            }
					 JOptionPane.showMessageDialog(this,
			                   r, 
			                   "Mensaje",
			                   JOptionPane.INFORMATION_MESSAGE); 

				
			
	}
	catch(SQLException e) {

		String mensaje = "SQLException: " + e.getMessage() + "\n" + "SQLState: " + e.getSQLState() + "\n"
					+ "VendorError: " + e.getErrorCode();
		JOptionPane.showMessageDialog(this,
                   mensaje, 
                   "Error",
                   JOptionPane.ERROR_MESSAGE);
	}
						
				
		}
	}
	private void mostrarMensaje(String s) {
		JOptionPane.showMessageDialog(this,
                s, 
                "Error",
                JOptionPane.ERROR_MESSAGE);
	}
}
