import javax.swing.*;
public final class MatrixException extends Throwable {
    /**
	 * 
	 */
	//private static final long serialVersionUID = 1L;

	public MatrixException(String message){
		JOptionPane.showMessageDialog(null,message,"Erreur",JOptionPane.INFORMATION_MESSAGE);
         }
}
