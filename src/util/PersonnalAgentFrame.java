
package util;


import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.*;

import agents.PersonnalAgent;

public class PersonnalAgentFrame extends Frame implements KeyListener{
	/*Action action = new AbstractAction(){
    	@Override
    	public void actionPerformed(ActionEvent e)
    	{
    		System.out.println("Enter Key Pressed");
    		AddTextLine(textAreaMessages.getText());
    	}
    };*/
	
	private JTextField jtf;
	private PersonnalAgent agent;
	
	
	//private JLabel label = new JLabel("Request");
	private JButton b = new JButton ("OK");
	
    
    public PersonnalAgentFrame(PersonnalAgent a) {
    	this.agent = a;
    	jtf = new JTextField("Please enter your request");
    	
        initComponents();
        
        jtf.addKeyListener(this);
        
        //inchiderea unei ferestre determina inchiderea intregii aplicatii
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
           //Toolkit.getDefaultToolkit().beep(); 
        	System.out.println("Enter Key Pressed");
    		AddTextLine(jtf.getText());
    		agent.init(jtf.getText());
        }
    }
    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        textAreaMessages = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textAreaMessages.setColumns(40);
        textAreaMessages.setRows(10);
        jScrollPane1.setViewportView(textAreaMessages);
        
        /*this.setSize(480, 640);
        this.setLayout(new BorderLayout());
        this.getContentPane().add(jtf, BorderLayout.NORTH);
        this.getContentPane().add(jScrollPane1, BorderLayout.WEST);*/
        //this.getContentPane().add(jScrollPane1, BorderLayout.EAST);
        
        
        
        /*this.setTitle("Animation");
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        Frame container = null;
		//container.setBackground(Color.WHITE);
        container.setLayout(new BorderLayout());
        JPanel top = new JPanel();
        Font police = new Font("Arial", Font.BOLD, 14);
        jtf.setFont(police);
        jtf.setPreferredSize(new Dimension(150, 30));
        top.add(label);
        top.add(jtf);
        container.add(top, BorderLayout.NORTH);*/

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        	//layout.createSequentialGroup()
            .addComponent(jtf)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtf)
                .addGap(NORMAL)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                .addContainerGap())
        );
        
        this.setVisible(true);
        
        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AgentFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AgentFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AgentFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AgentFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AgentFrame().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    
    // End of variables declaration//GEN-END:variables
    
    public void AddTextLine(String s)
    {
        textAreaMessages.append(s + "\r\n");
    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
