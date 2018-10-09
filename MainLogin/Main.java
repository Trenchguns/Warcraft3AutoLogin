//CopyRight 2018 Trenchguns
import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Files;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.security.Key;
import java.util.Scanner;
import javax.crypto.Cipher;
import org.apache.commons.io.FileUtils;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class Main {
	public static String pass;
	public static Process process;
	private static String key;
	public static String war3Dir;
	public boolean ctrlPressed;
	public boolean qPressed;
	public static String nextgame;
	public static int height, width, cx;
	public static String[] gameArray;
	public static JFrame frame;
	public static JTable gameTable;
	public static Object[][] gameTableData;
    
 	public static void main(String[] args) throws Exception{
 		gameArray = new String[36];
     	double screenHeight, screenWidth;
     	Robot robot = new Robot();
     	BufferedImage endC;
     	BufferedImage endCL;
     	File endCF =new File("endC.png");
     	if(!endCF.exists()){
   		  endC(robot);
     	}
     	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
     	screenWidth = screenSize.getWidth();
     	screenHeight =  screenSize.getHeight();
     	height = (int) screenHeight;
     	width = (int) screenWidth;
     	frame = new JFrame("Data");
     	JLabel label = new JLabel("Getting Password");
     	/*
     	JTable gameTable = new JTable(2, 36);
     	*/
        String[] columns = new String[] {
                "Game Name: Lobby Count", "HotKey"
            };
        updateGameList();
        gameTable = new JTable(gameTableData, columns);
     	getGameList();
     	JButton launchOptionsButton = new JButton("Set Launch Options");
     	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     	//frame.getContentPane().add(label, BorderLayout.CENTER);
     	launchOptionsButton.setBounds(40,30,200, 30); 
		launchOptionsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				launchOptionsButton.setEnabled(false);
					try {
						writeLaunch(label);
					} catch (IOException e) {
						e.printStackTrace();
					}
					launchOptionsButton.setEnabled(true);
			}   
		});
		label.setBounds(75,5,300,25);
		frame.setSize(300,650);
     	frame.add(launchOptionsButton);
     	launchOptionsButton.setEnabled(false);
     	frame.add(label);
     	JScrollPane gameTableScroll = new JScrollPane(gameTable);
     	gameTableScroll.setBounds(5,75,275,500);
     	//frame.add(gameTable);
     	frame.add(gameTableScroll);
     	frame.setLayout(null);
     	frame.setVisible(true);
     	showOnScreen(1);
     	getPass(label);
     	launchOptionsButton.setEnabled(true);
       	//Thread.sleep(50000);
     	Integer refreshWait = 0;
     	if(isWarRunning() == true) {
         	closeWar();
         	Thread.sleep(300);
         	runWar();
         	warLogin(robot, label);
     	}
     	else {
         	runWar();
         	warLogin(robot, label);
     	}
           	label.setText("Logged In");

           	while(true) {
               	if(isWarRunning() == false) {
                   	runWar();
                   	warLogin(robot, label);
               	}
               	Thread.sleep(500);
               	if (refreshWait < 6) {
                   	refreshWait++;
               	}
               	else {
               		//getGameList(frame);
               		refreshWait = 0;
               	}

               	if(endCF.exists()){
           			endC = ImageIO.read(endCF);
           			endCL = image(width/8, height/23,width/2-width/16,height/2 - height/17,  "endC", robot, false);
           			if(compareColors(endCL, endC, 1)) {
           				closeWar();
           				Thread.sleep(200);
           				runWar();
           				warLogin(robot, label);
           			}
               	}
           	}
 	}
	 
	 
    	public static void typeCharacter(Robot robot, String letter)
    	{
        	try
        	{
            	boolean upperCase = Character.isUpperCase( letter.charAt(0) );
            	String variableName = "VK_" + letter.toUpperCase();
    
            	KeyEvent ke = new KeyEvent(new JTextField(), 0, 0, 0, 0, ' ');
            	Class<? extends KeyEvent> clazz = ke.getClass();
            	Field field = clazz.getField( variableName );
            	int keyCode = field.getInt(ke);
    
            	//robot.delay(20);
    
            	if (upperCase) robot.keyPress( KeyEvent.VK_SHIFT );
    
            	robot.keyPress( keyCode );
            	robot.keyRelease( keyCode );
    
            	if (upperCase) robot.keyRelease( KeyEvent.VK_SHIFT );
        	}
        	catch(Exception e)
        	{
            	System.out.println(e);
        	}
    	}
    	public static void pointAndColor() throws AWTException, InterruptedException {
        	Robot robot = new Robot();
        	Color color = robot.getPixelColor(494, 423);
        	JFrame pointFrame = new JFrame("Data");
         	JLabel label = new JLabel("NULL NULL | 255 255 255");
         	pointFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         	pointFrame.getContentPane().add(label, BorderLayout.CENTER);
         	pointFrame.pack();
         	pointFrame.setVisible(true);
         	int x= 0;
         	int y= 0;
        	while(true){
            	Thread.sleep(300);
            	PointerInfo a = MouseInfo.getPointerInfo();
            	Point b = a.getLocation();
            	if(x!= b.getX() || y != b.getY()) {
            	x = (int) b.getX();
            	y = (int) b.getY();
            	color = robot.getPixelColor(x, y);
            	String coords = x + " " + y + " | " + color.getRed() + " " + color.getGreen() + " " + color.getBlue();
            	//String coords2 = y + " " + x + " " + returnColor(y,x);
            	label.setText(coords);
            	}
        	}
       	 
    	}
    	public static boolean isWarRunning() throws IOException {
        	String line;
        	String pidInfo ="";

        	Process p =Runtime.getRuntime().exec(System.getenv("windir") +"\\system32\\"+"tasklist.exe");

        	BufferedReader input =  new BufferedReader(new InputStreamReader(p.getInputStream()));

        	while ((line = input.readLine()) != null) {
            	pidInfo+=line;
        	}
        	input.close();
        	if(pidInfo.contains("Warcraft III.exe"))
        	{
            	return true;
        	}
        	return false;
    	}
    	public static BufferedImage image(int x, int y, int xx, int yy, String name, Robot robot, boolean save) throws IOException {
        	BufferedImage bufferedImage = robot.createScreenCapture(new Rectangle(xx,yy,x,y));
        	if(save == true) {
            	File imageFile = new File(name+ ".png");
            	ImageIO.write(bufferedImage, "png", imageFile);
        	}
        	return bufferedImage;
    	}
    	public static void detectWar(JLabel label) {

    		//"HKLM\Software\WOW6432Node\Blizzard Entertainment\Warcraft III" /v InstallPath
    		if(System.getProperty("os.name").contains("Windows")) {
	        war3Dir = WindowsRegistry.readRegistry("HKLM\\Software\\WOW6432Node\\Blizzard Entertainment\\Warcraft III", "GamePath");
	        /*if(war3Dir == null) {
	        	if (new File("C:/Program Files (x86)/Warcraft III/Warcraft III.exe").exists()) {
	        		war3Dir = ("C:/Program Files (x86)/Warcraft III/Warcraft III.exe");
	        	}
	        	else if (new File("D:/Program Files (x86)/Warcraft III/Warcraft III.exe").exists()){
	        		
	        		war3Dir = ("D:/Program Files (x86)/Warcraft III/Warcraft III.exe");
	        	}
	        	else if (new File("D:/Program Files/Warcraft III/Warcraft III.exe").exists()){
	        		war3Dir = ("D:/Program Files/Warcraft III/Warcraft III.exe");
	        	}
	        	else if (new File("C:/Program Files/Warcraft III/Warcraft III.exe").exists()){
                	war3Dir = ("C:/Program Files/Warcraft III/Warcraft III.exe");
	        	}
	        	else if (new File("C:/Warcraft III/Warcraft III.exe").exists()){
	        		war3Dir = ("C:/Warcraft III/Warcraft III.exe");
	        	}
	        	else if (new File("D:/Warcraft III/Warcraft III.exe").exists()){
	        		war3Dir = ("D:/Warcraft III/Warcraft III.exe");
	        	}
    		}*/

	        }
        	else {
        		label.setText("Manual Warcraft Directory Input");
        		JFrame path = new JFrame("Warcraft 3 Executable Path");
        		war3Dir = JOptionPane.showInputDialog(path, "Path to (including) /Warcraft III.exe");
        	}
           	 
       	}
   	 
    	public static void runWar() {
        	Runtime runtime = Runtime.getRuntime();
            	try
            	{
                	process = runtime.exec(war3Dir);
            	}
            	catch (IOException e)
            	{
                	e.printStackTrace();
            	}
           	 
       	}
    	public static void closeWar() throws IOException {
        	if (process!= null) {
            	process.destroy();
        	}
        	String process = "Warcraft III.exe";
        	Runtime.getRuntime().exec("taskkill /F /IM " + process);
       	 
    	}
    	public static boolean compareColors(BufferedImage im1, BufferedImage im2, int accuracy) {
        	for (int x = 0; x < im1.getWidth()-accuracy; x=x+accuracy) {
            	for (int y = 0; y < im1.getHeight()-accuracy; y=y+accuracy) {
                	if (im1.getRGB(x, y) != im2.getRGB(x, y))
                    	return false;
            	}
        	}
        	return true;
    	}
    	public static boolean colorCheck(int x, int y, int red, int green, int blue, Robot robot) throws AWTException {
        	Color color = robot.getPixelColor(x, y);
        	int cr = color.getRed();
        	int cg = color.getGreen();
        	int cb = color.getBlue();
        	if(cr != red || cg != green || cb !=blue) {
            	return false;
        	}
        	return true;
    	}
    	public static String returnColor(int x, int y) throws AWTException {
        	Robot robot = new Robot();
        	Color color = robot.getPixelColor(x, y);
        	String colors = color.getRed() + " " + color.getGreen() + " " + color.getBlue() + " ";
        	return colors;
    	}
    	/*public static boolean blackCheck() throws AWTException {
        	int x=0;
        	//28 27 24
        	while(colorCheck(x,0,28,27,24, robot)==false) {
            	x++;
        	}
        	System.out.println(x);
        	return false;
    	}*/
 
    	public static void showOnScreen( int screen) {
        	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        	GraphicsDevice[] gd = ge.getScreenDevices();
        	if( screen > -1 && screen < gd.length ) {
            	frame.setLocation(gd[screen].getDefaultConfiguration().getBounds().x, frame.getY());
        	} else if( gd.length > 0 ) {
            	frame.setLocation(gd[0].getDefaultConfiguration().getBounds().x, frame.getY());
        	} else {
            	throw new RuntimeException( "No Screens Found" );
        	}
    	}
    	public static Color color(BufferedImage img, int x, int y) {
        	Color mycolor = new Color(img.getRGB(x, y));
        	return mycolor;
    	}

    	public static boolean checkWhite(BufferedImage img) {
           	for (int x = 0; x < img.getWidth(); x++) {
            	for (int y = 0; y < img.getHeight(); y++) {
                	Color tempcolor = new Color(img.getRGB(x, y));
                	if (tempcolor.getRed() == 0 && tempcolor.getGreen() == 0 && tempcolor.getGreen() == 0) {
                    	return true;
                	}
            	}
        	}
           	return false;
    	}
    	public static void warLogin(Robot robot, JLabel label) throws AWTException, IOException, InterruptedException {
          	label.setText("Waiting for launch");
        	while(colorCheck(1, 1, 0, 0, 0, robot) !=true || colorCheck(width/2, height/2, 0, 0, 0, robot) == true) {
              	Thread.sleep(25);
          	}
          	BufferedImage shot1 = image(width, height,0,0,  "Launch", robot, true);

          	cx = width - 1;
          	System.out.println(cx);
          	while(color(shot1, cx, 0).getRed() == 0 && color(shot1, cx, 0).getGreen() == 0 && color(shot1, cx, 0).getBlue() == 0) {
              	cx--;
              	if(cx < (width - (width / 7))) {
                  	cx = width-1;
              	}
          	}
          	label.setText("Waiting for menus");
          	BufferedImage bottomRight1 = image(cx/20, height/20, cx - cx/20, height - height/20, "BottomRight1", robot, false);
          	Thread.sleep(200);
          	BufferedImage bottomRight2 = image(cx/20, height/20, cx - cx/20, height - height/20, "null", robot, false);;
          	while (compareColors(bottomRight1, bottomRight2, 1) == false) {
              	bottomRight2 = image(cx/20, height/20, cx - cx/20, height - height/20, "null", robot, false);
              	Thread.sleep(200);
              	bottomRight1 = image(cx/20, height/20, cx - cx/20, height - height/20, "BottomRight1", robot, false);
          	}
          	Thread.sleep(100);
          	label.setText("Opening Battle.net");
          	for(int x = 0; x < 20; x++) {
              	Thread.sleep(50);
              	robot.keyPress(KeyEvent.VK_B);
          	}
               	bottomRight1 = image((width - cx) / 2 - ((width - cx) / 15), (width - cx) / 2 - ((width - cx) / 10), width - cx + ((width - cx) / 20), height / 3 - height / 30, "testpic", robot, false);
               	while(checkWhite(bottomRight1) == false) {
                   	Thread.sleep(50);
                   	bottomRight1 = image((width - cx) / 2 - ((width - cx) / 15), (width - cx) / 2 - ((width - cx) / 10), width - cx + ((width - cx) / 20), height / 3 - height / 30, "testpic", robot, false);
               	}
               	Thread.sleep(100);
               	bottomRight2 = image(cx / 3, height / 2, width-cx, height / 2,  "testpic", robot, false);
               	label.setText("Logging In");

                   	for(int cnt = 0; cnt < pass.length(); cnt++) {
                       	typeCharacter(robot, Character.toString(pass.charAt(cnt)));
                      	 
                   	}
                   	robot.keyPress(KeyEvent.VK_ENTER);
                   	Thread.sleep(50);
               	bottomRight1 = image(cx / 3, height / 2, width-cx, height / 2,  "testpic2", robot, false);
               	Thread.sleep(50);
               	if(compareColors(bottomRight1, bottomRight2, 4) == false) {
                     	for(int cnt = 0; cnt < pass.length(); cnt++) {
                         	typeCharacter(robot, Character.toString(pass.charAt(cnt)));
                        	 
                     	}
                     	robot.keyPress(KeyEvent.VK_ENTER);
          	}
    	}
    	public static void endC(Robot robot) {
   		 JFrame f=new JFrame("Grab End Screen");       	 
     	 JButton b=new JButton("Click me on game end");    
     	 b.setBounds(25,25,175, 20);    
     	 f.add(b);    
     	 f.setSize(250,100);    
     	 f.setLayout(null);    
     	 f.setVisible(true);    
     	 f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     	 b.addActionListener(new ActionListener() {
   		 @Override
   		 public void actionPerformed(ActionEvent arg0) {
   			 try {
   				 @SuppressWarnings("unused")
				BufferedImage endC = image(width/8, height/23,width/2-width/16,height/2 - height/17,  "endC", robot, true);
   				 f.dispose();
   			 } catch (IOException e) {
   				 e.printStackTrace();
   			 }
   			 
   		 }     	 
      	});
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
            	JOptionPane.showMessageDialog(null,"Warning, auto close and login will not currently function in native fullscreen.");
            	return " -nativefullscr";
            case 2:
            	JOptionPane.showMessageDialog(null,"Warning, auto close and login will 	not currently function in windowed mode.");
            	return " -windowed";
            }
            return "";
    }
    	public static void writeLaunch(JLabel label) throws IOException {
    		label.setText("Changing Launch Options");
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
    				writer.write(old);
    				//fw.write(old);
    			}
    			else {
    				old = old + option;
    				writer.write(old);
    				//fw.write(old);
    			}
    			war3Dir=old;
    			writer.close();
    		}
    	}
    	public static void getGameList() throws IOException {
    		URL url = new URL("http://www.makemehost.com/refresh/divGames-table-mmh.php");
    		Document doc = Jsoup.parse(url, 3000);
    		Element table = doc.select("table").get(0);
    		Elements rows = table.select("tr");
    		for (int i = 1; i < rows.size(); i++) {
    		    Element row = rows.get(i);
    		    Elements cols = row.select("td");
    		    Element col = cols.get(3);
    		    String colStr = col.text();
    		    if (colStr.length() > 0) {
        		    col = cols.get(4);
        		    colStr = colStr + ": " + col.text();
    		    }
    		    gameArray[i] = colStr;
    		    Object gameNameObj = gameArray[i];
    		    gameTable.getModel().setValueAt(gameNameObj, i, 0);
    		    updateGameList();
    		}

    		
    	}
    	public static void updateGameList() {
            gameTableData = new Object[][] {
                {gameArray[0], "(L)Alt+Q"},
                {gameArray[1], "(L)Alt+w"},
                {gameArray[2], "(L)Alt+E"},
                {gameArray[3], "(L)Alt+R"},
                {gameArray[4], "(L)Alt+T"},
                {gameArray[5], "(L)Alt+Y"},
                {gameArray[6], "(L)Alt+U"},
                {gameArray[7], "(L)Alt+I"},
                {gameArray[8], "(L)Alt+O"},
                {gameArray[9], "(L)Alt+P"},
                {gameArray[10], "(L)Alt+A"},
                {gameArray[11], "(L)Alt+S"},
                {gameArray[12], "(L)Alt+D"},
                {gameArray[13], "(L)Alt+F"}, 
                {gameArray[14], "(L)Alt+G"},
                {gameArray[15], "(L)Alt+H"},
                {gameArray[16], "(L)Alt+J"},
                {gameArray[17], "(L)Alt+K"},
                {gameArray[18], "(L)Alt+L"},
                {gameArray[19], "(L)Alt+Z"},
                {gameArray[20], "(L)Alt+X"},
                {gameArray[21], "(L)Alt+C"},
                {gameArray[22], "(L)Alt+V"},
                {gameArray[23], "(L)Alt+B"},
                {gameArray[24], "(L)Alt+N"},
                {gameArray[25], "(L)Alt+M"},
            };
    	}

    	public static byte[] encrypt(String pass)
    	{
			return null;
        	//Not public
    	}
    	public static String decrypt(byte[] encr)
    	{
			return key;
        	//Not public
    	}
    	public static void getPass(JLabel label) throws IOException {
        	
        	
        	File f = new File("opts.txt");
          	if(f.exists() && !f.isDirectory() && new BufferedReader(new FileReader("opts.txt")).readLine() != null) {
              	Scanner sc = new Scanner(f);
				pass = sc.nextLine();
              	war3Dir = sc.nextLine();
                	sc.close();
          	}
         	 
        	else {
        		JPasswordField pwField = new JPasswordField(10);
        		int action = JOptionPane.showConfirmDialog(null, pwField,"Enter Password",JOptionPane.OK_CANCEL_OPTION);
        	    if(action < 0) {
        	    	JOptionPane.showMessageDialog(null,"Cancel, X or escape key selected");
        	    	System.exit(0);;
        	    }
        	    pass = new String(pwField.getPassword());
            	PrintWriter writer = new PrintWriter("opts.txt", "UTF-8");
            	detectWar(label);
				writer.println(pass);
            	writer.println(war3Dir);
            	writer.close();
        	}
    	}
   	 
}






