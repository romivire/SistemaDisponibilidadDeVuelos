import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JDesktopPane;
import java.awt.Color;
import java.awt.Dimension;

public class Principal extends JFrame {

	private JPanel contentPane;
	private DisponibilidadVuelos ventanaDV;
	private ConsultaBD ventanaCBD;
	private JDesktopPane desktopPane;
	private JPanel panel;
	private JButton btDV;
	private JButton btCBD;
	private JButton btSalir;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		 SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
		            
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		 int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	       int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
	       this.setBounds(ancho-10,alto-10,1000,1000);
	       
		setAlwaysOnTop(true);
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1591, 844);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setSize(new Dimension(1062, 838));
		desktopPane.setBackground(SystemColor.textHighlight);
		desktopPane.setBounds(0, 0, 1585, 815);
		contentPane.add(desktopPane);
		desktopPane.setLayout(null);
	      
	      ventanaDV = new DisponibilidadVuelos();
	      ventanaDV.setBounds(56, 710, 1411, 773);
	    
	      ventanaDV.setVisible(false);
	      desktopPane.add(ventanaDV);
	      
	      JPanel panel_1 = new JPanel();
	      panel_1.setBounds(452, 249, 668, 354);
	      desktopPane.add(panel_1);
	      
	      
	      JButton btDV_1 = new JButton("Consultar Disponibilidad de Vuelos");
	      btDV_1.setBounds(189, 110, 307, 27);
	      btDV_1.setFont(new Font("Arial", Font.BOLD, 15));
	      btDV_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               btDVActionPerformed(evt);
            }
         });
	      panel_1.setLayout(null);
	      panel_1.add(btDV_1);
	      
	      JButton btCBD_1 = new JButton("Consultar Base de Datos Vuelos");
	      btCBD_1.setBounds(189, 171, 307, 27);
	      btCBD_1.setFont(new Font("Arial", Font.BOLD, 15));
	      btCBD_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               btCBDActionPerformed(evt);
            }
         });
	      panel_1.add(btCBD_1);
	      
	      JLabel label = new JLabel("Bienvenido!");
	      label.setBounds(256, 40, 168, 35);
	      label.setHorizontalAlignment(SwingConstants.CENTER);
	      label.setFont(new Font("Arial", Font.BOLD, 30));
	      panel_1.add(label);
	      
	      JButton btSalir_1 = new JButton("Salir");
	      btSalir_1.setBounds(296, 244, 90, 27);
	      btSalir_1.setFont(new Font("Arial", Font.BOLD, 15));
	      btSalir_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               btSalirActionPerformed(evt);
            }
         });
	      panel_1.add(btSalir_1);
	      
	      ventanaCBD = new ConsultaBD();
	      ventanaCBD.setBounds(0, 0, 1585, 815);
	       ventanaCBD.setVisible(false);
	       desktopPane.add(ventanaCBD);
	      
	  
	}
	
	private void btSalirActionPerformed (ActionEvent evt) 
	   {
	      this.dispose();
	   }
	private void btCBDActionPerformed (ActionEvent evt) 
	   {
		try
	      {
	         this.ventanaCBD.setMaximum(true);
	      }
	      catch (PropertyVetoException e) {}
	      this.ventanaCBD.setVisible(true);
	   }
	private void btDVActionPerformed (ActionEvent evt) 
	   {
		try
	      {
	         this.ventanaDV.setMaximum(true);
	      }
	      catch (PropertyVetoException e) {}
	      this.ventanaDV.setVisible(true);
	   }
}
