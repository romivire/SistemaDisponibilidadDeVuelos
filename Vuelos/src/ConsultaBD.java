import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.SystemColor;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import quick.dbtable.DBTable;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Enumeration;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JScrollPane;

import java.awt.Dimension;
import javax.swing.JScrollBar;
import javax.swing.border.LineBorder;


public class ConsultaBD extends JInternalFrame {
	private JTextField usuario;
	private JPasswordField contrasena;
	private JPanel panel;
	private JLabel label;
	private JLabel label_1;
	private JTextArea txtConsulta;
	private JButton btConsulta;
	private JButton btIngresar;
	private JTextPane textPane;
	private JPanel panel_1;
	private JButton borrarConsulta;
	private JCheckBox btSelect;
	private JCheckBox btABM;
	private DBTable tabla;
	private JList listaTablas;
	private JList listaAtributosTabla;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	protected Connection conexionBD = null;
	private JLabel lblTablasDeBase;
	private JLabel lblAtributosDeLa;


	/**
	 * Create the frame.
	 */
	public ConsultaBD() {
		setSize(new Dimension(1170, 689));
		setBounds(100, 100, 1121, 689);
		getContentPane().setLayout(null);
		this.setClosable(true);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
       
      
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(SystemColor.textHighlight);
		panel.setBounds(10, 11, 197, 759);
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
		
		btIngresar = new JButton("Ingresar");
		btIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		btIngresar.setBounds(52, 262, 89, 23);
		panel.add(btIngresar);
		
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
		panel_1.setBounds(217, 11, 1349, 759);
		panel_1.setVisible(false);
		getContentPane().add(panel_1);
		
		btConsulta = new JButton("Ejecutar");
		btConsulta.setFont(new Font("Arial", Font.BOLD, 12));
		btConsulta.setBounds(933, 229, 89, 23);
		btConsulta.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent arg0) {
    		  String botonElegido=obtenerBotonSeleccionado(buttonGroup);
    		  if(botonElegido==null) {
    			  JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(ConsultaBD.this),
                          "Ingrese una opcion de consulta", 
                          "Error",
                          JOptionPane.ERROR_MESSAGE);
    		  }
    		  else {
    			  
    			  realizarConsulta(botonElegido);
    		  }
    		}
    	});
		panel_1.add(btConsulta);
		
		borrarConsulta = new JButton("Borrar");
		borrarConsulta.setFont(new Font("Arial", Font.BOLD, 12));
		borrarConsulta.setBounds(933, 263, 89, 23);
		borrarConsulta.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent arg0) {
    		  txtConsulta.setText("");            			
    		}
    	});
		panel_1.add(borrarConsulta);
		
		
		btSelect = new JCheckBox("SELECT");
		buttonGroup.add(btSelect);
		btSelect.setBounds(10, 179, 89, 23);
		btSelect.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent arg0) {
      		  txtConsulta.setVisible(true);        			
      		}
      	});
		panel_1.add(btSelect);
		
		btABM = new JCheckBox("UPDATE-INSERT-DELETE");
		buttonGroup.add(btABM);
		btABM.setBounds(101, 179, 172, 23);
		panel_1.add(btABM);
		
		tabla = new DBTable();
		tabla.setBounds(10, 323, 1329, 425);
		panel_1.add(tabla);
		tabla.setEditable(false);
		
		JLabel lblFormatoFechas = new JLabel("Ingresar fechas en formato \"aaaa:mm:dd\"");
		lblFormatoFechas.setBorder(new LineBorder(new Color(255, 0, 0), 4, true));
		lblFormatoFechas.setFont(new Font("Arial", Font.BOLD, 14));
		lblFormatoFechas.setBounds(10, 113, 304, 24);
		panel_1.add(lblFormatoFechas);
		
		lblTablasDeBase = new JLabel("Tablas de Base de Datos \"Vuelos\"");
		lblTablasDeBase.setFont(new Font("Arial", Font.BOLD, 13));
		lblTablasDeBase.setBounds(408, 11, 256, 24);
		panel_1.add(lblTablasDeBase);
		
		lblAtributosDeLa = new JLabel("Atributos de la Tabla seleccionada");
		lblAtributosDeLa.setFont(new Font("Arial", Font.BOLD, 13));
		lblAtributosDeLa.setBounds(752, 11, 256, 24);
		panel_1.add(lblAtributosDeLa);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 213, 913, 99);
		panel_1.add(scrollPane);
		
		txtConsulta = new JTextArea();
		scrollPane.setViewportView(txtConsulta);
		txtConsulta.setTabSize(3);
		txtConsulta.setRows(10);
		txtConsulta.setColumns(100);
		txtConsulta.setToolTipText("");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(408, 33, 256, 139);
		panel_1.add(scrollPane_1);
		
		listaTablas = new JList();
		scrollPane_1.setViewportView(listaTablas);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(752, 33, 256, 139);
		panel_1.add(scrollPane_2);
		
		listaAtributosTabla = new JList();
		scrollPane_2.setViewportView(listaAtributosTabla);
		listaAtributosTabla.setEnabled(false);
		
		JCheckBox CreateCheckBox = new JCheckBox("CREATE");
		buttonGroup.add(CreateCheckBox);
		CreateCheckBox.setBounds(275, 179, 97, 23);
		panel_1.add(CreateCheckBox);
		
		JCheckBox DropCheckBox = new JCheckBox("DROP");
		buttonGroup.add(DropCheckBox);
		DropCheckBox.setBounds(374, 179, 97, 23);
		panel_1.add(DropCheckBox);
		
		JLabel lblSeleccioneUnaOpcion = new JLabel("Seleccione una opcion");
		lblSeleccioneUnaOpcion.setFont(new Font("Arial", Font.BOLD, 14));
		lblSeleccioneUnaOpcion.setBounds(10, 148, 320, 24);
		panel_1.add(lblSeleccioneUnaOpcion);
		
		listaTablas.addListSelectionListener(new ListSelectionListener() {
  	      
    		public void valueChanged(ListSelectionEvent evt) {
    	        cambiarTablaAtributos(evt);
	        }
    		
    	});
		tabla.setVisible(false);
		
		this.addInternalFrameListener(new InternalFrameAdapter(){
		//Acciones a realizar cuando se cierra el InternalFrame
            public void internalFrameClosing(InternalFrameEvent e) {
                panel_1.setVisible(false);
                usuario.setText("");
				contrasena.setText("");
				txtConsulta.setText("");
				buttonGroup.clearSelection();

		    	  usuario.setEnabled(true);
					contrasena.setEnabled(true);
					btIngresar.setEnabled(true);
		    	  desconectarBD();	
            }
        });
		
	}
	
	private void realizarConsulta(String boton) {
		if(boton.equals("SELECT")) {
			tabla.setVisible(true);
			refrescarTablaSELECT();
			}
		else if(boton.equals("UPDATE-INSERT-DELETE")) {
				tabla.setVisible(false);
				refrescarTablaABM();
				}
			 else if(boton.equals("CREATE")) {
				 	tabla.setVisible(false);
				 		refrescarTablaCREATE();
				 		}
			 	  else if(boton.equals("DROP")) {
			 		  		tabla.setVisible(false);
							refrescarTablaDROP();
						}
	}
	
	
	private void refrescarTablaABM() {
		boolean modificacionExitosa=true;
		String sql=txtConsulta.getText().trim();
		String s=sql.toLowerCase();
		if(!s.startsWith("delete")&&!s.startsWith("update")&&!s.startsWith("insert")) {
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
                    "Debe ingresar una sentencia INSERT, DELETE o UPDATE", 
                    "Error",
                    JOptionPane.ERROR_MESSAGE);}
		else {
				try {
				
					Statement stmt = this.conexionBD.createStatement();
					stmt.execute(sql);
				}
				catch(SQLException ex) {
					modificacionExitosa=false;
					String mensaje = "SQLException: " + ex.getMessage() + "\n" + "SQLState: " + ex.getSQLState() + "\n"
							+ "VendorError: " + ex.getErrorCode();
	        	 JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
	                        mensaje, 
	                        "Error",
	                        JOptionPane.ERROR_MESSAGE);
				}
				if(modificacionExitosa) {
					JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
		                    "Se modificaron exitosamente los datos de la Base de Datos Vuelos", 
		                    "Modificacion exitosa",
		                    JOptionPane.INFORMATION_MESSAGE);
				}
		}
	}
	
	private void refrescarTablaCREATE() {
		boolean modificacionExitosa=true;
		String sql=txtConsulta.getText().trim();
		String s=sql.toLowerCase();
		if(!s.startsWith("create")) {
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
                    "Debe ingresar una sentencia CREATE", 
                    "Error",
                    JOptionPane.ERROR_MESSAGE);}
		else {
				try {
				
					Statement stmt = this.conexionBD.createStatement();
					stmt.execute(sql);
				}
				catch(SQLException ex) {
					modificacionExitosa=false;
					String mensaje = "SQLException: " + ex.getMessage() + "\n" + "SQLState: " + ex.getSQLState() + "\n"
							+ "VendorError: " + ex.getErrorCode();
	        	 JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
	                        mensaje, 
	                        "Error",
	                        JOptionPane.ERROR_MESSAGE);
				}
				if(modificacionExitosa) {
					JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
		                    "Se creo exitosamente la nueva tabla en la Base de Datos Vuelos", 
		                    "Modificacion exitosa",
		                    JOptionPane.INFORMATION_MESSAGE);
				}
		}
	}
	private void refrescarTablaDROP() {
		boolean modificacionExitosa=true;
		String sql=txtConsulta.getText().trim();
		String s=sql.toLowerCase();
		if(!s.startsWith("drop")) {
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
                    "Debe ingresar una sentencia DROP", 
                    "Error",
                    JOptionPane.ERROR_MESSAGE);}
		else {
				try {
				
					Statement stmt = this.conexionBD.createStatement();
					stmt.execute(sql);
				}
				catch(SQLException ex) {
					modificacionExitosa=false;
					String mensaje = "SQLException: " + ex.getMessage() + "\n" + "SQLState: " + ex.getSQLState() + "\n"
							+ "VendorError: " + ex.getErrorCode();
	        	 JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
	                        mensaje, 
	                        "Error",
	                        JOptionPane.ERROR_MESSAGE);
				}
				if(modificacionExitosa) {
					JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
		                    "Se elimino exitosamente la tabla en la Base de Datos Vuelos", 
		                    "Modificacion exitosa",
		                    JOptionPane.INFORMATION_MESSAGE);
				}
		}
	}
		
	private void refrescarTablaSELECT() {
		// TODO Auto-generated method stub
		String sql=txtConsulta.getText().trim();
		String s=sql.toLowerCase();
		if(!s.startsWith("select")) {
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
                    "Debe ingresar una sentencia SELECT", 
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
		}
		else {
				try
			      {    
					
			    	 // seteamos la consulta a partir de la cual se obtendrán los datos para llenar la tabla
			    	 tabla.setSelectSql(this.txtConsulta.getText().trim());
		
			    	  // obtenemos el modelo de la tabla a partir de la consulta para 
			    	  // modificar la forma en que se muestran de algunas columnas  
			    	  tabla.createColumnModelFromQuery();    	    
			    	  for (int i = 0; i < tabla.getColumnCount(); i++)
			    	  { // para que muestre correctamente los valores de tipo TIME (hora)  		   		  
			    		 if	 (tabla.getColumn(i).getType()==Types.TIME)  
			    		 {    		 
			    		    tabla.getColumn(i).setType(Types.CHAR);  
			  	       	 }
			    		 // cambiar el formato en que se muestran los valores de tipo DATE
			    		 if	 (tabla.getColumn(i).getType()==Types.DATE)
			    		 {
			    		    tabla.getColumn(i).setDateFormat("dd/MM/YYYY");
			    		 }
			          }  
			    	  // actualizamos el contenido de la tabla.   	     	  
			    	  tabla.refresh();
			    	  // No es necesario establecer  una conexión, crear una sentencia y recuperar el 
			    	  // resultado en un resultSet, esto lo hace automáticamente la tabla (DBTable) a 
			    	  // patir de la conexión y la consulta seteadas con connectDatabase() y setSelectSql() respectivamente.
			          
			    	  
			    	  
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
		}
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
	
	private void login() {
		//Valida el usuario admin y su contrasena y en caso correcto conecta a la BD y muestra el panel de la seccion
			String u=usuario.getText();
			String p=contrasena.getText();
			if(u.equals("admin")) {
				if(p.equals("")) {
					JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
                        "Contrasena invalida", 
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
						usuario.setText("");
						contrasena.setText("");
				}
				else {
					
					
					conectarBD(p);
					
					try{
						
					Statement stmt = this.conexionBD.createStatement();
					String sql= "SHOW TABLES";
					ResultSet rs= stmt.executeQuery(sql);
					String nombres []= obtenerContenidoFilas(rs);
					listaTablas.setListData(nombres);
					
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}				
			}
			else {
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
                        "Usuario invalido", 
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
				usuario.setText("");
				contrasena.setText("");
			}
				
		}
	
	private void cambiarTablaAtributos(ListSelectionEvent evt) {
		//Cambia los Atributos de la ListaAtributosTabla dependiendo de que tabla se seleccione
		String atributos []= null;
		String itemSeleccionado = (String) listaTablas.getSelectedValue();
		
		try {
			Statement stmt = this.conexionBD.createStatement();
			String sql= "DESCRIBE "+itemSeleccionado+";";
			ResultSet rs= stmt.executeQuery(sql);
			atributos = obtenerContenidoFilas(rs);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		listaAtributosTabla.setListData(atributos);
	}
	
	private void conectarBD(String pass)
	   {	boolean sqlE=false; //ExceptionSQL
	   		boolean classE=false;//ExceptionClassNotFound
	         try
	         {
	            String driver ="com.mysql.cj.jdbc.Driver";
	        	String servidor = "localhost:3306";
	        	String baseDatos = "vuelos"; 
	        	String usuario = "admin";
	        	String clave = pass;
	            String uriConexion = "jdbc:mysql://" + servidor + "/" + 
	        	                     baseDatos +"?serverTimezone=America/Argentina/Buenos_Aires";
	   
	            this.conexionBD = DriverManager.getConnection(uriConexion, usuario, clave);
	       //establece una conexión con la  B.D. "batallas"  usando directamante una tabla DBTable    
	            tabla.connectDatabase(driver, uriConexion, usuario, clave);	            
	         }
	         catch (SQLException ex)
	         {
	        	 if(ex.getSQLState().equals("28000")) {
	            JOptionPane.showMessageDialog(this,"Contrasena invalida" ,"Error",JOptionPane.ERROR_MESSAGE);
	            usuario.setText("");
				contrasena.setText("");
	        	 }
	        	 else {
	        		 JOptionPane.showMessageDialog(this,
	                           "Se produjo un error al conectarse a la base de datos"+"/n"+ex.getMessage() ,"Error",JOptionPane.ERROR_MESSAGE);
	        	 }
	            if(ex!=null)
	            	sqlE=true;
	         }
	         catch (ClassNotFoundException e)
	         {
	            e.printStackTrace();
	            if(e!=null)
	            	classE=true;
	           
	         }
	        
	         
	   	
	    if(sqlE==false && classE==false) {
	    	usuario.setEnabled(false);
			contrasena.setEnabled(false);
			btIngresar.setEnabled(false);
       	 panel_1.setVisible(true);

       	}
	    
	   }

	private void desconectarBD(){
	      if (this.conexionBD != null) {
	         try
	         {	
	            tabla.close();            
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
	      }
	   }

	private String[] obtenerContenidoFilas(ResultSet tabla) {
		String nombres[]=null;
		int cantFilas=0,i=0;
		try {
			if(tabla.last()){ 
		          cantFilas = tabla.getRow(); 
		          tabla.beforeFirst(); //se posiciona antes del primero
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
}
