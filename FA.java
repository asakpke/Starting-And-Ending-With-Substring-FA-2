import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
//import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

import java.awt.Graphics;

public class FA extends JFrame
       implements ActionListener {
  int i=1;
//  int nodeState=1;
  Node node[];
//  Node ndEnding[];
  Node dead;
  String type;
  String subStr;
  String sEnding;
  boolean wordOfLanguage = false;
  String sWord;

  JTextField txtWord;
  JButton btnCheckWord;

  public FA( String aType, String aSubStr, String aEnding  ) {
      if( aSubStr.equals( aEnding ) )
          if( aSubStr.length() == 0 )
              setTitle( "FA of RE = ( a + b )*" );
          else
              setTitle( "FA of RE = " + aSubStr + " [ ( a + b )* " +
                      aEnding + " ) ]*" );
      else
          setTitle( "FA of RE = " + aSubStr + " ( a + b )* " +
                    aEnding  );
    if(       ( aSubStr.length() == 0 && aEnding.length() > 4  )
         ||   ( aSubStr.length() > 4 && aEnding.length() == 0 ) )
        getContentPane().setLayout( new FlowLayout( FlowLayout.CENTER ) );
/*
    else if( aSubStr.length() > 4 )
        getContentPane().setLayout( new FlowLayout( FlowLayout.LEFT ) );
*/
    else
         getContentPane().setLayout( new FlowLayout( FlowLayout.RIGHT ) );

    txtWord = new JTextField( "", 11 );
    getContentPane().add( txtWord );

    btnCheckWord = new JButton("Check Word");
    btnCheckWord.setActionCommand( "btnCheckWord" );
    btnCheckWord.addActionListener(this);
    getContentPane().add( btnCheckWord );

    //JScrollPane scrollPane = new JScrollPane();
        //,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
        //,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//    getContentPane().add(scrollPane);
    sEnding = aEnding;
    type = aType;
    subStr = aSubStr;
//    System.out.println( type );
    node = new Node[10];
//    ndEnding = new Node[10];

    node[0] = new Node( 10, 100, 50, 50 );
    //repaint();
  }

  public static void main( String args[] ) {
    FA fa = new FA( "Contains sub string", "aaaaaaa", "aaaaaaa" );

    //fa.setSize( 750, 550 );
    fa.setSize( 775, 560 );
    fa.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    fa.show();
  }

public void actionPerformed(ActionEvent e) {
   if( "btnCheckWord" == e.getActionCommand() ) {
      //String sStart = txtWord.getText();
	sWord = txtWord.getText();
      int lenWord = sWord.length();
      char ch;
      if( lenWord > 0 ) {
         sWord = sWord.toLowerCase();
         for( i=0; i < lenWord; i++ ) {
            ch = sWord.charAt( i );
            //JOptionPane.showMessageDialog( this, String.valueOf( (int) ch ) );
            if( ch != 'a' && ch != 'b' ) {
               JOptionPane.showMessageDialog( this, "Enter only character " +
                  "\'a\' or \'b\' in the word at character # " +
                  String.valueOf( i+1 ) );
                  return;
            }
         }
      }

      checkWord();
      if( wordOfLanguage == true )
          if( sWord.length() == 0 )
              JOptionPane.showMessageDialog( this, "NULL" +
                  " is the word of this language" );
          else
              JOptionPane.showMessageDialog( this, "\'" + sWord +
                  "\' is the word of this language" );
      else if( sWord.length() == 0 )
         JOptionPane.showMessageDialog( this, "NULL" +
            " is NOT the word of this language" );
      else
         JOptionPane.showMessageDialog( this, "\'" + sWord +
            "\' is NOT the word of this language" );
   }
}

  void drawNode( Graphics g ) {
//JOptionPane.showMessageDialog(this, String.valueOf( subStr.length() ) );
if( subStr.length() == 0 && sEnding.length() == 0 ) {
    //circle
    g.drawOval( node[0].x, node[0].y, node[0].w, node[0].h );
    g.drawString( "-+1", node[0].x+node[0].w/2-8, node[0].y+node[0].h/2+5 );
    //loop
    g.drawArc( node[0].x+node[0].w/4, node[0].y-node[0].h
            , node[0].w/3, node[0].h, 280, 325 );
    g.drawString( "V", node[0].x+node[0].w/4, node[0].y );
    //loop transition
    g.drawString( "a, b", node[0].x+node[0].w/4, node[0].y-node[0].h-5 );
    return;
}
if( subStr.length() > 0 ) {
    //start dead end
    dead = new Node( node[0].x
        , node[0].y + node[0].y , node[0].w, node[0].h );
    g.drawOval( dead.x, dead.y, dead.w, dead.h );
    g.drawString( "2", dead.x+dead.w/2-5
        , dead.y+dead.h/2+5 );

    g.drawArc( dead.x+dead.w/4, dead.y+dead.h, dead.w/3, dead.h
                   , 125, 325 );
    g.drawString( "/\\", dead.x+dead.w/4, dead.y+dead.h+10 );
    g.drawString( "a, b", dead.x+dead.w/4, dead.y+dead.h+60);
    //end dead

   if(  type.equals( "Contains sub string" ) ) {
//1st node
        //circle
        g.drawOval( node[0].x, node[0].y, node[0].w, node[0].h );
        g.drawString( "-1", node[0].x+node[0].w/2-5, node[0].y+node[0].h/2+5 );
        //lines to dead
        g.drawLine( node[0].x+25, dead.y/2+50
                        , node[0].x+25, dead.y);
        g.drawString( "V", node[0].x+22, dead.y );
        // right
        g.drawLine( node[0].x+node[0].w, node[0].y+node[0].h/3, node[0].x+node[0].w*2
        , node[0].y+node[0].h/3 );
        g.drawString( ">", node[0].x+node[0].w*2-5, node[0].y+node[0].h/3+5 );
        if( subStr.charAt( 0 ) == 'a' ) {
            //char to dead
            //g.drawString( "b", node[0].x+node[0].w/4, node[0].y-node[0].h-5 );
            g.drawString( "b", node[0].x+node[0].w/4, node[0].y+node[0].h+25 );
            //right
            g.drawString( "a", node[0].x+node[0].w+node[0].w/2-5, node[0].y+node[0].h/2-10 );
        } else {
            //char to dead
            //g.drawString( "a", node[0].x+node[0].w/4, node[0].y-node[0].h-5 );
            g.drawString( "a", node[0].x+node[0].w/4, node[0].y+node[0].h+25 );
            //right
            g.drawString( "b", node[0].x+node[0].w+node[0].w/2-5, node[0].y+node[0].h/2-10 );
        }

        //f start
//others nodes
//        System.out.println( "str len = " + subStr.length() );

        for( i=1; i < subStr.length(); i++ ) {
             node[i] = new Node( node[i-1].x + node[i-1].w * 2
             , node[i-1].y, node[i-1].w, node[i-1].h );

             if( subStr.charAt( 0 ) == subStr.charAt( i ) ) {
                //circle
                g.drawOval( node[i].x, node[i].y, node[i].w, node[i].h );
                g.drawString( Integer.toString( i+1+1 ), node[i].x+node[i].w/2-5, node[i].y+node[i].h/2+5 );
                //right
                g.drawLine( node[i].x+node[i].w, node[i].y+node[i].h/3
                , node[i].x+node[i].w*2, node[i].y+node[i].h/3 );
                g.drawString( ">", node[i].x+node[i].w*2-5, node[i].y+node[i].h/3+5 );

                if( subStr.charAt( i ) == 'a' ) {
                     //right
                    g.drawString( "a", node[i].x+node[i].w+node[i].w/2-5
                    , node[i].y+node[i].h/2-10 );
                    //start make it function
                    //left
                    if( i > 1 ) {
                         //call function
                         DrawLines( g , 0 , 'b');
                         //alphbet comprision

                    } else {
                       DrawLines( g , 0 , 'b');
                    }

                    //end make it function
                } else {
                      //right
                  g.drawString( "b", node[i].x+node[i].w+node[i].w/2-5
                  , node[i].y+node[i].h/2-10 );
                    //start make it function

                    //left


                    if( i > 1 ) {
                        //call function
                         DrawLines( g , 0 , 'a');
                            //alphbet comprision
                    } else {
                        g.drawLine( node[i].x, node[i].y+node[i].h/2
                        , node[0].x+node[i].w, node[i].y+node[i].h/2);
                        g.drawString( "<", node[i].x-node[i].w, node[i].y+node[i].h/2+5 );
                        g.drawString( "a", node[i].x-node[i].w+node[i].w/2-5
                        , node[i].y+node[i].h-15 );
                    }

                    //end make it function
                }
//for diff letters
             } else {
                //circle
                g.drawOval( node[i].x, node[i].y, node[i].w, node[i].h );
                g.drawString( Integer.toString( i+1+1 ), node[i].x+node[i].w/2-5, node[i].y+node[i].h/2+5 );
                //right
                g.drawLine( node[i].x+node[i].w, node[i].y+node[i].h/3
                , node[i].x+node[i].w*2, node[i].y+node[i].h/3 );
                g.drawString( ">", node[i].x+node[i].w*2-5, node[i].y+node[i].h/3+5 );

                if( subStr.charAt( i ) == 'a' ) {
                    //right
                    g.drawString( "a", node[i].x+node[i].w+node[i].w/2-5
                    , node[i].y+node[i].h/2-10 );
                    //start make it function
                    //left
                    if( i > 1 ) {
                        if( subStr.charAt( i ) == subStr.charAt(0) ) {
                        //call function
                         DrawLines( g , 0 ,'b');
                            //alphbet comprision


                        } else {
                        //call function
                         DrawLines( g , 1, 'b');

                        }
                    } else {
                        //loop
                  DrawLines( g , 1, 'b');;
                    }
                    //end make it function
                } else {
                  //right
                  g.drawString( "b", node[i].x+node[i].w+node[i].w/2-5
                  , node[i].y+node[i].h/2-10 );
                    //start make it function
                    //left
                    if( i > 1 ) {
                       if( subStr.charAt( i ) == subStr.charAt(0) ) {
                                                     //call function
                         //call function
                         DrawLines( g ,0 , 'a');
                            //alphbet comprision

                        } else {
                          //call function
                         DrawLines( g , 1 , 'a');

                        }
                    } else {
                        //loop
DrawLines( g , 1, 'a');
                    }
                    //end make it function
                }
             }
        }
//last node
        node[i] = new Node( node[i-1].x + node[i-1].w * 2
                , node[i-1].y, node[i-1].w, node[i-1].h );
        if( sEnding.length() > 0 ) {
            g.drawLine( node[i].x, node[i].y+15, node[i].x, 300 );
            g.drawLine( node[i].x, 300, 50, 300 );
            g.drawLine( 50, 300, 50, 375 );
            g.drawString( "V", 50 - 3, 375 + 5 );
        } else {
            g.drawOval( node[i].x, node[i].y, node[i].w, node[i].h );

            g.drawArc( node[i].x+node[i].w/4, node[i].y-node[i].h, node[i].w/3, node[i].h
                    , 280, 325 );
            g.drawString( "V", node[i].x+node[i].w/4, node[i].y );

            g.drawString( "+" + Integer.toString(i+1+1), node[i].x+node[i].w/2-5
                , node[i].y+node[i].h/2+5 );
            g.drawString( "a, b", node[i].x+node[i].w/4, node[i].y-node[i].h-5 );
        }
        //f end
   }
}

//start - end with substring
if( sEnding.length() > 0 ) {
    String strTemp = subStr;
    subStr = sEnding;
    Node tempNode[] = node;
    int nodeState=1;
    //JOptionPane.showMessageDialog( this, String.valueOf( nodeState ) );
   if(  type.equals( "Contains sub string" ) ) {
        node = new Node[10];
//1st node
         // node for if start/end sub-string are same
         Node nodeEql = new Node( 10, 500, 50, 50 );
        if( strTemp.length() > 0 ) {
            node[0] = new Node( 10, 375, 50, 50 );
            nodeState = i + 1 + 1;
            if( strTemp.equals( subStr ) ) {
               //JOptionPane.showMessageDialog( this, "ok" );
               //nodeState++;
               //new node to handle starting sub-string == ending sub-string
               //Node nodeEql = new Node( 10 + 25, 500, 50, 50 );
               //circle
               g.drawOval( nodeEql.x, nodeEql.y, nodeEql.w, nodeEql.h );
               g.drawString( String.valueOf( nodeState++ )
                        , nodeEql.x + nodeEql.w / 2 - 5
                        , nodeEql.y + nodeEql.h / 2 + 5 );
               // loop
               //g.drawArc( nodeEql.x + nodeEql.w / 4, nodeEql.y - nodeEql.h
               //          , nodeEql.w / 3, nodeEql.h
               //          , 280, 325 );
               //g.drawString( "V", nodeEql.x + nodeEql.w / 4, nodeEql.y );

               //g.drawString( "t", nodeEql.x + nodeEql.w / 4
                     //, nodeEql.y - nodeEql.h - 5 );

               //node to be +ed for same input
               g.drawString( "+" + String.valueOf( nodeState++ )
                        , node[0].x+node[0].w/2-5
                        , node[0].y+node[0].h/2+5 );
            }
            else
               g.drawString( String.valueOf( nodeState++ )
                        , node[0].x+node[0].w/2-5
                        , node[0].y+node[0].h/2+5 );
        } else {
            node[0] = new Node( 10, 100, 50, 50 );
            g.drawString( "-" + String.valueOf( nodeState++ ), node[0].x+node[0].w/2-5
                , node[0].y+node[0].h/2+5 );
        }
        //circle
        g.drawOval( node[0].x, node[0].y, node[0].w, node[0].h );

        // loop
        if( strTemp.equals( subStr ) ) {
            g.drawArc( nodeEql.x + nodeEql.w / 4 - 10
                        , nodeEql.y - nodeEql.h + 5
                        , nodeEql.w / 3, nodeEql.h
                        , 280, 325 );
            g.drawString( "V"
                        , nodeEql.x + nodeEql.w / 4 - 10
                        , nodeEql.y + 5 );
        } else {
            g.drawArc( node[0].x+node[0].w/4, node[0].y-node[0].h, node[0].w/3, node[0].h
                , 280, 325 );
            g.drawString( "V", node[0].x+node[0].w/4, node[0].y );
        }

        // right
        g.drawLine( node[0].x+node[0].w, node[0].y+node[0].h/3, node[0].x+node[0].w*2
        , node[0].y+node[0].h/3 );
        g.drawString( ">", node[0].x+node[0].w*2-5, node[0].y+node[0].h/3+5 );
        if( subStr.charAt( 0 ) == 'a' ) {
            //loop
            if( strTemp.equals( subStr ) ) {
               g.drawString( "b", nodeEql.x + nodeEql.w / 4 - 10
                  , nodeEql.y - nodeEql.h + 5 );
               //line from node[0] to nodeEql
               g.drawLine( node[0].x + node[0].w - node[0].w / 4
                        ,  node[0].y + node[0].h - node[0].h / 4 + 8
                        ,  nodeEql.x + nodeEql.w - nodeEql.w / 4
                        ,  nodeEql.y + 5 );
               g.drawString( "b"
                     ,  node[0].x + node[0].w - node[0].w / 4 + 3
                     ,  node[0].y + node[0].h + 5 );
               g.drawString( "V"
                        ,  nodeEql.x + nodeEql.w - nodeEql.w / 4 - 3
                        ,  nodeEql.y + 5 );
               //line from nodeEql to node[1]
               g.drawLine( nodeEql.x + nodeEql.w
                        ,  nodeEql.y + nodeEql.h / 2
                        ,  nodeEql.x + nodeEql.w * 2 + nodeEql.w / 4
                        ,  nodeEql.y + nodeEql.h / 2 );
               g.drawString( "a"
                        ,  nodeEql.x + nodeEql.w + 10
                        ,  nodeEql.y + nodeEql.h / 2 + 10 );
               g.drawLine( nodeEql.x + nodeEql.w * 2 + nodeEql.w / 4
                        ,  nodeEql.y + nodeEql.h / 2
                        ,  nodeEql.x + nodeEql.w * 2 + nodeEql.w / 4
                        ,  nodeEql.y - nodeEql.h - nodeEql.h / 2 - 2 );
               g.drawString( "/\\"
                        ,  nodeEql.x + nodeEql.w * 2 + nodeEql.w / 4 - 2
                        ,  nodeEql.y - nodeEql.h - nodeEql.h / 2 + 5 );
            }
            else
                g.drawString( "b", node[0].x+node[0].w/4, node[0].y-node[0].h-5 );
            //right
            g.drawString( "a", node[0].x+node[0].w+node[0].w/2-5, node[0].y+node[0].h/2-10 );
        } else {
            // loop
            if( strTemp.equals( subStr ) ) {
               g.drawString( "a", nodeEql.x + nodeEql.w / 4 - 10
                     , nodeEql.y - nodeEql.h + 5 );
               //line from node[0] to nodeEql
               g.drawLine( node[0].x + node[0].w - node[0].w / 4
                        ,  node[0].y + node[0].h - node[0].h / 4 + 8
                        ,  nodeEql.x + nodeEql.w - nodeEql.w / 4
                        ,  nodeEql.y + 5 );
               g.drawString( "a"
                     ,  node[0].x + node[0].w - node[0].w / 4 + 3
                     ,  node[0].y + node[0].h + 5 );
               g.drawString( "V"
                        ,  nodeEql.x + nodeEql.w - nodeEql.w / 4 - 3
                        ,  nodeEql.y + 5 );
               //line from nodeEql to node[1]
               g.drawLine( nodeEql.x + nodeEql.w
                        ,  nodeEql.y + nodeEql.h / 2
                        ,  nodeEql.x + nodeEql.w * 2 + nodeEql.w / 4
                        , nodeEql.y + nodeEql.h / 2 );
               g.drawString( "b"
                        ,  nodeEql.x + nodeEql.w + 10
                        ,  nodeEql.y + nodeEql.h / 2 + 10 );
               g.drawLine( nodeEql.x + nodeEql.w * 2 + nodeEql.w / 4
                        ,  nodeEql.y + nodeEql.h / 2
                        ,  nodeEql.x + nodeEql.w * 2 + nodeEql.w / 4
                        ,  nodeEql.y - nodeEql.h - nodeEql.h / 2 - 2 );
               g.drawString( "/\\"
                        ,  nodeEql.x + nodeEql.w * 2 + nodeEql.w / 4 - 2
                        ,  nodeEql.y - nodeEql.h - nodeEql.h / 2 + 5 );
            }
            else
                g.drawString( "a", node[0].x+node[0].w/4, node[0].y-node[0].h-5 );
            //right
            g.drawString( "b", node[0].x+node[0].w+node[0].w/2-5, node[0].y+node[0].h/2-10 );
        }

        //f start
//others nodes
//        System.out.println( "str len = " + subStr.length() );
        boolean sameLetter = true;
        int i;
        for( i=1; i < subStr.length(); i++ ) {
             node[i] = new Node( node[i-1].x + node[i-1].w * 2
             , node[i-1].y, node[i-1].w, node[i-1].h );

             if( subStr.charAt( 0 ) == subStr.charAt( i ) ) {
                //circle
                g.drawOval( node[i].x, node[i].y, node[i].w, node[i].h );
                //g.drawString( Integer.toString( i+1 ), node[i].x+node[i].w/2-5
                //              , node[i].y+node[i].h/2+5 );
                g.drawString( String.valueOf( nodeState++ )
                        , node[i].x+node[i].w/2-5, node[i].y+node[i].h/2+5 );
                //right
                g.drawLine( node[i].x+node[i].w, node[i].y+node[i].h/3
                , node[i].x+node[i].w*2, node[i].y+node[i].h/3 );
                g.drawString( ">", node[i].x+node[i].w*2-5, node[i].y+node[i].h/3+5 );

                if( subStr.charAt( i ) == 'a' ) {
                    //loop
                    //g.drawString( "b", node[0].x+node[0].w/4, node[0].y-node[0].h-5 );
                    //right
                    g.drawString( "a", node[i].x+node[i].w+node[i].w/2-5
                    , node[i].y+node[i].h/2-10 );
                    //start make it function
                    //start - if both star/end sub-string are same
                  if( strTemp.equals( subStr ) ) {
                     //char
                     g.drawString( "b", node[i].x + node[i].w / 2 + 15
                        , node[i].y + node[i].h + 5 );
                     //vertical 1
                     g.drawLine( node[i].x + node[i].w / 2 + 13
                        , node[i].y + node[i].h - 3
                        , node[i].x+node[i].w - node[i].w / 2 + 13
                        , node[i].y + node[i].h * 3 + node[i].h / 3 );
                     //horizontal
                     g.drawLine( node[i].x + node[i].w / 2 + 13
                        , node[i].y + node[i].h * 3 + node[i].h / 3
                        , nodeEql.x+nodeEql.w - 5
                        , node[i].y + node[i].h * 3 + node[i].h / 3 );
                     //arrow <
                     g.drawString( "<", nodeEql.x + nodeEql.w - 5
                        , nodeEql.y + nodeEql.h - 4 );
                  } else
                    //end   - if both star/end sub-string are same
                     //left
                    if( i > 1 ) {
                       //vertical 1
                       g.drawLine( node[i].x + node[i].w / 2
                        , node[i].y+node[i].h
                        , node[i].x+node[i].w - node[i].w / 2
                        , node[i].y+node[i].h + (i-1) * node[i].h / 8);
                        //horizontal
                        g.drawLine( node[i].x + node[i].w / 2
                        , node[i].y+node[i].h + (i-1) * node[i].h / 8
                        , node[0].x+node[i].w - node[i].w / 2
                        , node[i].y+node[i].h + (i-1) * node[i].h / 8);
                        //vertical 2
                       g.drawLine( node[0].x + node[0].w / 2
                        , node[0].y+node[0].h
                        , node[0].x+node[0].w - node[0].w / 2
                        , node[0].y+node[0].h + (i-1) * node[0].h / 8);

                        g.drawString( "^", node[0].x + node[0].w / 2 - 2
                        , node[0].y+node[i].h + 8 );
                        g.drawString( "b", node[i].x + node[i].w / 2 + 5
                        , node[i].y + node[i].h + 10 );
                    } else {
                        g.drawLine( node[i].x, node[i].y+node[i].h/2
                        , node[0].x+node[i].w, node[i].y+node[i].h/2);
                        g.drawString( "<", node[i].x-node[i].w, node[i].y+node[i].h/2+5 );
                        g.drawString( "b", node[i].x-node[i].w+node[i].w/2-5
                        , node[i].y+node[i].h-15 );
                    }
                    //end make it function
                } else {
                  // loop
                  //g.drawString( "a", node[0].x+node[0].w/4, node[0].y-node[0].h-5 );
                  //right
                  g.drawString( "b", node[i].x+node[i].w+node[i].w/2-5
                  , node[i].y+node[i].h/2-10 );
                    //start make it function
                    //start - if both star/end sub-string are same
                  if( strTemp.equals( subStr ) ) {
                     //char
                     g.drawString( "a", node[i].x + node[i].w / 2 + 15
                        , node[i].y + node[i].h + 5 );
                     //vertical 1
                     g.drawLine( node[i].x + node[i].w / 2 + 13
                        , node[i].y + node[i].h - 3
                        , node[i].x+node[i].w - node[i].w / 2 + 13
                        , node[i].y + node[i].h * 3 + node[i].h / 3 );
                     //horizontal
                     g.drawLine( node[i].x + node[i].w / 2 + 13
                        , node[i].y + node[i].h * 3 + node[i].h / 3
                        , nodeEql.x+nodeEql.w - 5
                        , node[i].y + node[i].h * 3 + node[i].h / 3 );
                     //arrow <
                     g.drawString( "<", nodeEql.x + nodeEql.w - 5
                        , nodeEql.y + nodeEql.h - 4 );
                  } else
                    //end   - if both star/end sub-string are same
                    //left
                    if( i > 1 ) {
                       //vertical 1
                       g.drawLine( node[i].x + node[i].w / 2
                        , node[i].y+node[i].h
                        , node[i].x+node[i].w - node[i].w / 2
                        , node[i].y+node[i].h + (i-1) * node[i].h / 8);
                        //horizontal
                        g.drawLine( node[i].x + node[i].w / 2
                        , node[i].y+node[i].h + (i-1) * node[i].h / 8
                        , node[0].x+node[i].w - node[i].w / 2
                        , node[i].y+node[i].h + (i-1) * node[i].h / 8);
                        //vertical 2
                       g.drawLine( node[0].x + node[0].w / 2
                        , node[0].y+node[0].h
                        , node[0].x+node[0].w - node[0].w / 2
                        , node[0].y+node[0].h + (i-1) * node[0].h / 8);

                        g.drawString( "^", node[0].x + node[0].w / 2 - 2
                        , node[0].y+node[i].h + 8 );
                        g.drawString( "a", node[i].x + node[i].w / 2 + 5
                        , node[i].y + node[i].h + 10 );
                    } else {
                        g.drawLine( node[i].x, node[i].y+node[i].h/2
                        , node[0].x+node[i].w, node[i].y+node[i].h/2);
                        g.drawString( "<", node[i].x-node[i].w, node[i].y+node[i].h/2+5 );
                        g.drawString( "a", node[i].x-node[i].w+node[i].w/2-5
                        , node[i].y+node[i].h-15 );
                    }
                    //end make it function
                }
//for diff letters
             } else {
                //circle
                sameLetter = false;
                g.drawOval( node[i].x, node[i].y, node[i].w, node[i].h );
                //g.drawString( Integer.toString( i+1 ), node[i].x+node[i].w/2-5
                    //, node[i].y+node[i].h/2+5 );
                g.drawString( String.valueOf( nodeState++ ), node[i].x+node[i].w/2-5
                              , node[i].y+node[i].h/2+5 );
                //right
                g.drawLine( node[i].x+node[i].w, node[i].y+node[i].h/3
                , node[i].x+node[i].w*2, node[i].y+node[i].h/3 );
                g.drawString( ">", node[i].x+node[i].w*2-5, node[i].y+node[i].h/3+5 );

                if( subStr.charAt( i ) == 'a' ) {
                    //right
                    g.drawString( "a", node[i].x+node[i].w+node[i].w/2-5
                    , node[i].y+node[i].h/2-10 );
                    //start make it function
/*
                    //start - if both star/end sub-string are same
                  if( strTemp.equals( subStr ) ) {
                     //char
                     g.drawString( "b", node[i].x + node[i].w / 2 + 5
                        , node[i].y + node[i].h + 10 );
                     //vertical 1
                     g.drawLine( node[i].x + node[i].w / 2 + 13
                        , node[i].y + node[i].h - 3
                        , node[i].x+node[i].w - node[i].w / 2 + 13
                        , node[i].y + node[i].h * 3 + node[i].h / 3 );
                     //horizontal
                     g.drawLine( node[i].x + node[i].w / 2 + 13
                        , node[i].y + node[i].h * 3 + node[i].h / 3
                        , nodeEql.x+nodeEql.w - 5
                        , node[i].y + node[i].h * 3 + node[i].h / 3 );
                     //arrow <
                     g.drawString( "<", nodeEql.x + nodeEql.w - 5
                        , nodeEql.y + nodeEql.h - 4 );
                  } else
                    //end   - if both star/end sub-string are same
*/
                    //left
                    if( i > 1 ) {
                        if( subStr.charAt( i ) == subStr.charAt(0) ) {
                            //vertical 1
                            g.drawLine( node[i].x + node[i].w / 2
                            , node[i].y+node[i].h
                            , node[i].x+node[i].w - node[i].w / 2
                            , node[i].y+node[i].h + (i-1) * node[i].h / 8);
                            //horizontal
                            g.drawLine( node[i].x + node[i].w / 2
                            , node[i].y+node[i].h + (i-1) * node[i].h / 8
                            , node[0].x+node[i].w - node[i].w / 2
                            , node[i].y+node[i].h + (i-1) * node[i].h / 8);
                            //vertical 2
                            g.drawLine( node[0].x + node[0].w / 2
                            , node[0].y+node[0].h
                            , node[0].x+node[0].w - node[0].w / 2
                            , node[0].y+node[0].h + (i-1) * node[0].h / 8);

                            g.drawString( "^", node[0].x + node[0].w / 2 - 2
                            , node[0].y+node[i].h + 8 );
                            g.drawString( "b", node[i].x + node[i].w / 2 + 5
                            , node[i].y + node[i].h + 10 );
                        } else {
                            //vertical 1
                            g.drawLine( node[i].x + node[i].w / 2
                            , node[i].y+node[i].h
                            , node[i].x+node[i].w - node[i].w / 2
                            , node[i].y+node[i].h + (i-1) * node[i].h / 8);
                            //horizontal
                            g.drawLine( node[i].x + node[i].w / 2
                            , node[i].y+node[i].h + (i-1) * node[i].h / 8
                            , node[1].x+node[i].w - node[i].w / 2
                            , node[i].y+node[i].h + (i-1) * node[i].h / 8);
                            //vertical 2
                            g.drawLine( node[1].x + node[1].w / 2
                            , node[1].y+node[1].h
                            , node[1].x+node[1].w - node[1].w / 2
                            , node[1].y+node[1].h + (i-1) * node[1].h / 8);

                            g.drawString( "^", node[1].x + node[1].w / 2 - 2
                            , node[1].y+node[i].h + 8 );
                            g.drawString( "b", node[i].x + node[i].w / 2 + 5
                            , node[i].y + node[i].h + 10 );
                        }
                    } else {
                        //loop
                        g.drawArc( node[1].x+node[1].w/4, node[1].y-node[1].h
                        , node[1].w/3, node[1].h, 280, 325 );
                        g.drawString( "V", node[1].x+node[1].w/4, node[1].y );
                        g.drawString( "b", node[1].x+node[1].w/4, node[1].y-node[1].h-5 );
                    }
                    //end make it function
                } else {
                  //right
                  g.drawString( "b", node[i].x+node[i].w+node[i].w/2-5
                  , node[i].y+node[i].h/2-10 );
                    //start make it function
/*
                     //start - if both star/end sub-string are same
                  if( strTemp.equals( subStr ) ) {
                     //char
                     g.drawString( "a", node[i].x + node[i].w / 2 + 5
                        , node[i].y + node[i].h + 10 );
                     //vertical 1
                     g.drawLine( node[i].x + node[i].w / 2 + 13
                        , node[i].y + node[i].h - 3
                        , node[i].x+node[i].w - node[i].w / 2 + 13
                        , node[i].y + node[i].h * 3 + node[i].h / 3 );
                     //horizontal
                     g.drawLine( node[i].x + node[i].w / 2 + 13
                        , node[i].y + node[i].h * 3 + node[i].h / 3
                        , nodeEql.x+nodeEql.w - 5
                        , node[i].y + node[i].h * 3 + node[i].h / 3 );
                     //arrow <
                     g.drawString( "<", nodeEql.x + nodeEql.w - 5
                        , nodeEql.y + nodeEql.h - 4 );
                  } else
                    //end   - if both star/end sub-string are same
*/
                    //left
                    if( i > 1 ) {
                        if( subStr.charAt( i ) == subStr.charAt(0) ) {
                            //vertical 1
                            g.drawLine( node[i].x + node[i].w / 2
                            , node[i].y+node[i].h
                            , node[i].x+node[i].w - node[i].w / 2
                            , node[i].y+node[i].h + (i-1) * node[i].h / 8);
                            //horizontal
                            g.drawLine( node[i].x + node[i].w / 2
                            , node[i].y+node[i].h + (i-1) * node[i].h / 8
                            , node[0].x+node[i].w - node[i].w / 2
                            , node[i].y+node[i].h + (i-1) * node[i].h / 8);
                            //vertical 2
                            g.drawLine( node[0].x + node[0].w / 2
                            , node[0].y+node[0].h
                            , node[0].x+node[0].w - node[0].w / 2
                            , node[0].y+node[0].h + (i-1) * node[0].h / 8);

                            g.drawString( "^", node[0].x + node[0].w / 2 - 2
                            , node[0].y+node[i].h + 8 );
                            g.drawString( "a", node[i].x + node[i].w / 2 + 5
                            , node[i].y + node[i].h + 10 );
                        } else {
                            //vertical 1
                            g.drawLine( node[i].x + node[i].w / 2
                            , node[i].y+node[i].h
                            , node[i].x+node[i].w - node[i].w / 2
                            , node[i].y+node[i].h + (i-1) * node[i].h / 8);
                            //horizontal
                            g.drawLine( node[i].x + node[i].w / 2
                            , node[i].y+node[i].h + (i-1) * node[i].h / 8
                            , node[1].x+node[i].w - node[i].w / 2
                            , node[i].y+node[i].h + (i-1) * node[i].h / 8);
                            //vertical 2
                            g.drawLine( node[1].x + node[1].w / 2
                            , node[1].y+node[1].h
                            , node[1].x+node[1].w - node[1].w / 2
                            , node[1].y+node[1].h + (i-1) * node[1].h / 8);

                            g.drawString( "^", node[1].x + node[1].w / 2 - 2
                            , node[1].y+node[i].h + 8 );
                            g.drawString( "a", node[i].x + node[i].w / 2 + 5
                            , node[i].y + node[i].h + 10 );
                        }
                    } else {
                        //loop
                        g.drawArc( node[1].x+node[1].w/4, node[1].y-node[1].h
                        , node[1].w/3, node[1].h, 280, 325 );
                        g.drawString( "V", node[1].x+node[1].w/4, node[1].y );
                        g.drawString( "a", node[1].x+node[1].w/4, node[1].y-node[1].h-5 );
                    }
                    //end make it function
                }
             }
        }
//last node

        node[i] = new Node( node[i-1].x + node[i-1].w * 2
        , node[i-1].y, node[i-1].w, node[i-1].h );
        g.drawOval( node[i].x, node[i].y, node[i].w, node[i].h );
        //g.drawString( "+" + Integer.toString(i+1), node[i].x+node[i].w/2-5
        //    , node[i].y+node[i].h/2+5 );
        g.drawString( "+" + String.valueOf( nodeState++ ), node[i].x+node[i].w/2-5
            , node[i].y+node[i].h/2+5 );


///////////////////////////////////////////////////////////////////////
//           CHANGE THE TRANSITION FOR SAME START/END SUB-STRING
///////////////////////////////////////////////////////////////////////

//start - last node transition
        //JOptionPane.showMessageDialog( this, String.valueOf( sameLetter ) );
        //vertical 1
        if( sameLetter != true ) {
             g.drawLine( node[i].x + node[i].w / 2
                , node[i].y+node[i].h
                , node[i].x+node[i].w - node[i].w / 2
                , node[i].y+node[i].h + (i-1) * node[i].h / 8);
        }
      if( strTemp.equals( subStr ) ) {
         //vertical 1
         g.drawLine( node[i].x + node[i].w / 2 + 13
               , node[i].y + node[i].h - 3
               , node[i].x+node[i].w - node[i].w / 2 + 13
               , node[i].y + node[i].h * 3 + node[i].h / 3 );
         //horizontal
         g.drawLine( node[i].x + node[i].w / 2 + 13
               , node[i].y + node[i].h * 3 + node[i].h / 3
               , nodeEql.x+nodeEql.w - 5
               , node[i].y + node[i].h * 3 + node[i].h / 3 );
         //arrow <
         g.drawString( "<", nodeEql.x + nodeEql.w - 5
               , nodeEql.y + nodeEql.h - 4 );
      } else
        g.drawLine( node[i].x + node[i].w / 2 + 12
                , node[i].y+node[i].h - 2
                , node[i].x+node[i].w - node[i].w / 2 + 12
                , node[i].y+node[i].h + (i) * node[i].h / 8);
         //horizontal
        if( sameLetter != true ) {
            g.drawLine( node[i].x + node[i].w / 2
                , node[i].y+node[i].h + (i-1) * node[i].h / 8
                , node[1].x+node[i].w - node[i].w / 2
                , node[i].y+node[i].h + (i-1) * node[i].h / 8);
        }
      if( strTemp.equals( subStr ) ) {

      } else
        g.drawLine( node[i].x + node[i].w / 2 + 12
                , node[i].y+node[i].h + (i) * node[i].h / 8
                , node[0].x+node[i].w - node[i].w / 2
                , node[i].y+node[i].h + (i) * node[i].h / 8);
         //vertical 2
        if( sameLetter != true ) {
            g.drawLine( node[1].x + node[1].w / 2
                , node[1].y+node[1].h
                , node[1].x+node[1].w - node[1].w / 2
                , node[1].y+node[1].h + (i-1) * node[1].h / 8);
            g.drawString( "^", node[1].x + node[1].w / 2 - 2
                , node[1].y+node[i].h + 8 );
        }
      if( strTemp.equals( subStr ) ) {

      } else {
         g.drawLine( node[0].x + node[0].w / 2
                , node[0].y+node[0].h
                , node[0].x+node[0].w - node[1].w / 2
                , node[0].y+node[0].h + (i) * node[0].h / 8);
         g.drawString( "^", node[0].x + node[0].w / 2 - 2
                , node[0].y+node[0].h + 8 );
      }

            if( sameLetter == true ) {
                //loop
                g.drawArc( node[i].x+node[i].w/4, node[i].y-node[i].h
                             , node[i].w/3, node[i].h, 280, 325 );
                g.drawString( "V", node[i].x+node[i].w/4, node[i].y );
            }

            if( subStr.charAt(0) == 'a' ) {
                if( sameLetter != true ) {
                    g.drawString( "a", node[i].x + node[i].w / 2 + 3
                              , node[i].y + node[i].h + 10 );
                } else //loop
                    g.drawString( "a", node[i].x+node[i].w/4, node[i].y-node[i].h-5 );

                g.drawString( "b", node[i].x + node[i].w / 2 + 15
                              , node[i].y + node[i].h + 10 );
            } else {
                if( sameLetter != true ) {
                    g.drawString( "b", node[i].x + node[i].w / 2 + 3
                              , node[i].y + node[i].h + 10 );
                } else  // loop
                    g.drawString( "b", node[i].x+node[i].w/4, node[i].y-node[i].h-5 );

                g.drawString( "a", node[i].x + node[i].w / 2 + 15
                              , node[i].y + node[i].h + 10 );
            }

//end   - last node transition


/*
        g.drawArc( node[i].x+node[i].w/4, node[i].y-node[i].h, node[i].w/3, node[i].h
        , 280, 325 );
        g.drawString( "V", node[i].x+node[i].w/4, node[i].y );
        g.drawString( "a, b", node[i].x+node[i].w/4, node[i].y-node[i].h-5 );
*/
        //f end
    }
//end   - end with substring
    //String strTemp = subStr;
    subStr = strTemp;
    //Node tempNode[] = node;
    node = tempNode;
}//if( sEnding.length() > 0 ) {
  }//

  public void DrawLines( Graphics g ,int d , char c)
  {
                        //vertical 1
                         g.drawLine( node[i].x + node[i].w / 2
                            , node[i].y+node[i].h
                            , node[i].x+node[i].w - node[i].w / 2
                            , node[i].y+node[i].h + (i) * node[i].h / 8);
                        //horizontal
                        g.drawLine( node[i].x + node[i].w / 2
                        , node[i].y+node[i].h + (i) * node[i].h / 8
                        , dead.x+node[i].w - node[i].w / 2
                        , node[i].y+node[i].h + (i) * node[i].h / 8);
                         /* g.drawLine( node[i].x + node[i].w / 2
                          ,node[i].y+node[i].h + (i-1) * node[i].h / 8
                        , dead.x+node[0].w - node[i].w / 2, dead.y); */
                        //vertical 2
                     /*  g.drawLine( node[d].x + node[d].w / 2
                        , node[d].y+node[d].h
                        , node[d].x+node[d].w - node[d].w / 2
                        , node[d].y+node[d].h + (i-1) * node[d].h / 8);

                         g.drawString( "^", node[d].x + node[d].w / 2 - 2
                         , node[d].y+node[i].h + 8 ); */
                         g.drawString( ""+c, node[i].x + node[i].w / 2 + 5
                         , node[i].y + node[i].h + 10 );
      }
 public void paint( Graphics g ) {
    drawNode( g );

    txtWord.updateUI();
    btnCheckWord.updateUI();
  }

void checkWord() {
   //String word = txtWord.getText();

   if( sWord.length() < subStr.length() + sEnding.length() ) {
      //JOptionPane.showMessageDialog( this, "less" );
      if( sWord.equals( subStr ) && sWord.equals( sEnding ) )
          wordOfLanguage = true;
      else
          wordOfLanguage = false;
      return;
   }

   if( subStr.length() > 0 )
      if( sWord.startsWith( subStr ) )
         wordOfLanguage = true;
      else {
          wordOfLanguage = false;
          return;
      }
   else
       wordOfLanguage = true;

   if( sEnding.length() > 0 )//&& wordOfLanguage == true )
      if( sWord.endsWith( sEnding ) )
         wordOfLanguage = true;
      else {
          wordOfLanguage = false;
          //return;
      }
   //else if( wordOfLanguage == true )
       //wordOfLanguage = true;

/*
  if( subStr.length() == 0 && sEnding.length() == 0 ) {
    wordOfLanguage = true;
    return;
  }
  String word = txtWord.getText();
  if( word.startsWith( subStr ) ) {
    wordOfLanguage = true;
    //JOptionPane.showMessageDialog( this, "startsWith" );
  }
  if( word.endsWith( sEnding ) ) {
    wordOfLanguage = true;
    //JOptionPane.showMessageDialog( this, "endsWith" );
  }
*/
}//checkWord()
}//FA class