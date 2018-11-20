import javax.swing.JFrame;

public class SemesterConversionRun {

public static void main( String[] args )
{ 
	SemesterConversionQ search = new SemesterConversionQ(); // create MenuFrame 
    search.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    search.setSize( 750, 700 ); // set frame size
    search.setResizable(false);
    search.setVisible( true ); // display frame
 } // end main
}
