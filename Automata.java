import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Automata extends JFrame implements ActionListener {
    JTextField txtAlphabet;
//    JComboBox cboLang;
    JTextField txtStart;
    JTextField txtEnd;

    public Automata( String title) {
        super( title );
        getContentPane().setLayout( new FlowLayout( FlowLayout.LEFT ) );

        JLabel lblAlphabet = new JLabel( "Language alphabet = { a, b }" );
        getContentPane().add( lblAlphabet );

        //txtAlphabet = new JTextField( "ab", 7 );
        //getContentPane().add( txtAlphabet );

        JLabel lblLang = new JLabel( "Language of all the words starting with sub-string" );
        getContentPane().add( lblLang );

//        String[] strLang = { "Starting with sub-string", "Contains sub-string", "Others" };
//        cboLang = new JComboBox( strLang );
//        getContentPane().add( cboLang );

        txtStart = new JTextField( 7 );
        getContentPane().add( txtStart );

        JLabel lblLang2 = new JLabel( " and ending with sub-string" );
        getContentPane().add( lblLang2 );

        txtEnd = new JTextField(  7 );
        getContentPane().add( txtEnd );

        JButton btnRE = new JButton("Make RE");
        JButton btnFA = new JButton("Make FA");
        JButton btnExit = new JButton( "Exit" );

        btnRE.setActionCommand( "btnRE" );
        btnFA.setActionCommand( "btnFA" );
        btnExit.setActionCommand( "btnExit" );

        btnRE.addActionListener(this);
        btnFA.addActionListener(this);
        btnExit.addActionListener(this);

        getContentPane().add( btnRE );
        getContentPane().add( btnFA );
        getContentPane().add( btnExit );
    } // Automata()

public void actionPerformed(ActionEvent e) {
   String sStart = txtStart.getText();
   String sEnd   = txtEnd.getText();

        if( "btnRE" == e.getActionCommand() ) {
           JLabel lblRE;
            JFrame frameRE = new JFrame("RE");
            if( sStart.equals( sEnd ) ) {
               String sRESame;
               if( sStart.length() == 0 )
                   sRESame = "   RE = ( a + b )*";
               else
                   sRESame = "   RE = " + sStart +
                           " [ ( a + b )* " + sEnd + " ]*" ;
               lblRE = new JLabel( sRESame );
            }
            else {
               String sRE = "   RE = " + sStart + " ( a + b )* " +
                      sEnd;
               lblRE = new JLabel( sRE );
            }
            frameRE.getContentPane().add( lblRE );
            frameRE.setSize( 200, 100 );
            frameRE.show();
            //frameRE.pack();
        } else if( "btnFA" == e.getActionCommand() ) {
            if( sStart.length() > 7 ) {
                JOptionPane.showMessageDialog( this, "Letters should be less than 8 in " +
                        "starting with sub-string" );
                return;
            }
            if( sEnd.length() > 7 ) {
                JOptionPane.showMessageDialog( this, "Letter should be less than 8 in " +
                        "ending with sub-string" );
                return;
            }
            int lenStart  = sStart.length();
            int lenEnd    = sEnd.length();
            int i;
            char ch;

            if( lenStart > 0 ) {
                sStart = sStart.toLowerCase();
                for( i=0; i < lenStart; i++ ) {
                     ch = sStart.charAt( i );
                     //JOptionPane.showMessageDialog( this, String.valueOf( (int) ch ) );
                     if( ch != 'a' && ch != 'b' ) {
                        JOptionPane.showMessageDialog( this,
                           "Enter only character \'a\' or \'b\' " +
                           "in starting sub-string at character # " +
                            String.valueOf( i+1 ) );
                        //txtStart.select( i, i+1 );
                        return;
                    }
                }
            }

            if( lenEnd > 0 ) {
                sEnd = sEnd.toLowerCase();
                for( i=0; i < lenEnd; i++ ) {
                     ch = sEnd.charAt( i );
                     if( ch != 'a' && ch != 'b' ) {
                         JOptionPane.showMessageDialog( this, "Enter only character " +
                            "\'a\' or \'b\' in ending sub-string at character # " +
                            String.valueOf( i+1 ) );
                        return;
                    }
                }
            }

            FA fa = new FA( "Contains sub string", sStart, sEnd );
            //FA fa = new FA( "Contains sub string", txtAlphabet.getText(), txtStart.getText()
               //, txtEnd.getText() );
            //fa.setSize( ( txtStart.getText().length() + txtEnd.getText().length() )
            //            * 200, 550 );
            fa.setSize( 775, 560 );
            //fa.assignNode();
            fa.show();
            //this.hide();
            //fa.pack();
            //frameFA.pack();
        } else if( "btnExit" == e.getActionCommand() ) {
            System.exit( 0 );
        }
} // actionPerformed()

    public static void main( String args[] ) {
        Automata automata = new Automata( "Run Automata" );
        automata.setSize( 350, 150 );

        automata.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        automata.show();
        //automata.pack();
    } // main()
} // MainFrame class