import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Options extends JFrame{
	
	private static final long serialVersionUID = 1L;

    public static void main(String[] args) throws IOException {
    	writeLaunch();
	}
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static String RadioButtonDialog(){
        JComboBox<?> b1 = new JComboBox();
        b1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Windowed Fullscreen", "Native Fullscreen", "Windowed"}));
        
        JPanel myPanel = new JPanel();
        myPanel.add(b1);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Launch Options", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            System.out.println("Field 1: " + b1.getSelectedIndex());
        }
        switch(b1.getSelectedIndex()) {
        case 0:
        	return "";
        case 1:
        	JOptionPane.showMessageDialog(null,"Warning, auto close will not currently function in native fullscreen.");
        	return " -nativefullscr";
        case 2:
        	JOptionPane.showMessageDialog(null,"Warning, auto close will not currently function in windowed mode.");
        	return " -windowed";
        }
        return "";
}
	public static void writeLaunch() throws IOException {
		String option = RadioButtonDialog();
		File f = new File("opts.txt");
		
		if(f.exists() && !f.isDirectory() && new BufferedReader(new FileReader("opts.txt")).readLine() != null) {
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(f);
			String old = sc.nextLine();
			FileWriter writer = new FileWriter(f, false);
			if (old.contains(" -nativefullscr")){
				old = old.substring(0,old.length()- 15) + option;
				System.out.println(old);
				writer.write(old);
				//fw.write(old);
			}
			else if (old.contains(" -windowed")){
				old = old.substring(0,old.length()- 10) + option;
				System.out.println(old);
				writer.write(old);
				//fw.write(old);
			}
			else {
				old = old + option;
				System.out.println(old);
				writer.write(old);
				//fw.write(old);
			}
			writer.close();
		}
	}
}
