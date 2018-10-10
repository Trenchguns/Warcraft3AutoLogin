//CopyRight 2018 Trenchguns
import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import java.net.URL;
import java.nio.file.Files;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumnModel;

import java.security.Key;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import org.apache.commons.io.FileUtils;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import static java.awt.event.KeyEvent.*;

public class Main {
	public static String pass;
	public static Process process;
	private static String key;
	public static String war3Dir;
	public boolean ctrlPressed;
	public boolean qPressed;
	public static int height, width, cx;
	public static String[] gameNames;
	public static String[] gameArray;
	public static JFrame frame;
	public static JTable[] gameTable;
	public static Object[][] gameTableData1;
	public static Object[][] gameTableData2;
	public static String nextGame;
	public static int nextGameInt;
	public static Robot robot;
	public static JLabel dataLabel;
	public static JLabel nextGameLabel;
	public static Boolean launching;
    
	public static void main(String[] args) throws Exception{
		File dir = new File("BNetAuto Folder");
		gameTable = new JTable[2];
		if(!dir.exists()) {
			dir.mkdir();
		}
		nextGameLabel = new JLabel("Next Game: ");
		launching = true;
		GlobalKeyListener.rAlt = false;
		GlobalKeyListener.lAlt = false;
 		nextGameInt = -1;
		try {
			Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
			logger.setLevel(Level.WARNING);
			logger.setUseParentHandlers(false);
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());
			System.exit(1);
		}
 		GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
 		gameNames = new String[81];
 		gameArray = new String[81];
     	double screenHeight, screenWidth;
     	robot = new Robot();
     	BufferedImage endC;
     	BufferedImage endCL;
     	File endCF =new File("endC.png");
     	if(!endCF.exists()){
   		  endC();
     	}
     	Thread.sleep(5000);
     	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
     	screenWidth = screenSize.getWidth();
     	screenHeight =  screenSize.getHeight();
     	height = (int) screenHeight;
     	width = (int) screenWidth;
     	frame = new JFrame("Data");
     	dataLabel = new JLabel("Getting Password");
     	/*
     	JTable gameTable = new JTable(2, 36);
     	*/
        String[] columns = new String[] {
                "Game Name: Lobby Count", "HotKey"
            };
        updateMmhGameList();
        updateEntGameList();
        gameTable[0] = new JTable(gameTableData1, columns);
        gameTable[1] = new JTable(gameTableData2, columns);
        getMmhGameList();
        getEntGameList();
     	JButton launchOptionsButton = new JButton("Set Launch Options");
     	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     	//frame.getContentPane().add(label, BorderLayout.CENTER);
     	launchOptionsButton.setBounds(40,45,200, 30); 
		launchOptionsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				launchOptionsButton.setEnabled(false);
					try {
						writeLaunch();
					} catch (IOException e) {
						e.printStackTrace();
					}
					launchOptionsButton.setEnabled(true);
			}   
		});
		dataLabel.setBounds(75,5,400,25);
		nextGameLabel.setBounds(75,20,400,30);
		frame.setSize(600,862);
     	frame.add(launchOptionsButton);
     	frame.add(nextGameLabel);
     	launchOptionsButton.setEnabled(false);
     	frame.add(dataLabel);
     	TableColumnModel columnModel1 = gameTable[0].getColumnModel();
     	columnModel1.getColumn(0).setPreferredWidth(200);
     	columnModel1.getColumn(1).setPreferredWidth(75);
     	TableColumnModel columnModel2 = gameTable[1].getColumnModel();
     	columnModel2.getColumn(0).setPreferredWidth(200);
     	columnModel2.getColumn(1).setPreferredWidth(75);
     	JScrollPane gameTable1Scroll = new JScrollPane(gameTable[0]);
     	JScrollPane gameTable2Scroll = new JScrollPane(gameTable[1]);
     	gameTable1Scroll.setBounds(5,80,275,599);
     	frame.add(gameTable1Scroll);
     	gameTable2Scroll.setBounds(290,80,275,743);
     	frame.add(gameTable2Scroll);
     	frame.setLayout(null);
     	frame.setVisible(true);
     	showOnScreen(1);
     	getPass();
     	launchOptionsButton.setEnabled(true);
     	Integer refreshWait = 0;
     	if(isWarRunning() == true && process == null) {
         	relaunchWar();
     	}
     	else {
         	runWar();
         	warLogin();
     	}
     	dataLabel.setText("Logged In");

           	while(true) {
               	if(isWarRunning() == false) {
                   	runWar();
                   	warLogin();
               	}
               	Thread.sleep(500);
               	if (refreshWait < 12) {
                   	refreshWait++;
                   	if(refreshWait%2 == 0) {
                   	dataLabel.setText("Games Refresh in: " + Integer.toString(6 - refreshWait/2));
                   	}
               	}
               	else {
               		dataLabel.setText("Games Refreshed");
               		getMmhGameList();
               		refreshWait = 0;
               	}

               	if(endCF.exists()){
           			endC = ImageIO.read(endCF);
           			endCL = image(width/8, height/23,width/2-width/16,height/2 - height/17,  "endC", false);
           			if(compareColors(endCL, endC, 1)) {
           				relaunchWar();
           			}
               	}
           	}
 	}
	 
	 private static void doType(int... keyCodes) {
	        doType(keyCodes, 0, keyCodes.length);
	    }

	    private static void doType(int[] keyCodes, int offset, int length) {
	        if (length == 0) {
	            return;
	        }

	        robot.keyPress(keyCodes[offset]);
	        doType(keyCodes, offset + 1, length - 1);
	        robot.keyRelease(keyCodes[offset]);
	    }
    	public static void typeCharacter(String letter)
    	{
        	try
        	{
        		char character = letter.charAt(0);
        		switch (character) {
        		case 'a': doType(VK_A); break;
                case 'b': doType(VK_B); break;
                case 'c': doType(VK_C); break;
                case 'd': doType(VK_D); break;
                case 'e': doType(VK_E); break;
                case 'f': doType(VK_F); break;
                case 'g': doType(VK_G); break;
                case 'h': doType(VK_H); break;
                case 'i': doType(VK_I); break;
                case 'j': doType(VK_J); break;
                case 'k': doType(VK_K); break;
                case 'l': doType(VK_L); break;
                case 'm': doType(VK_M); break;
                case 'n': doType(VK_N); break;
                case 'o': doType(VK_O); break;
                case 'p': doType(VK_P); break;
                case 'q': doType(VK_Q); break;
                case 'r': doType(VK_R); break;
                case 's': doType(VK_S); break;
                case 't': doType(VK_T); break;
                case 'u': doType(VK_U); break;
                case 'v': doType(VK_V); break;
                case 'w': doType(VK_W); break;
                case 'x': doType(VK_X); break;
                case 'y': doType(VK_Y); break;
                case 'z': doType(VK_Z); break;
                case 'A': doType(VK_SHIFT, VK_A); break;
                case 'B': doType(VK_SHIFT, VK_B); break;
                case 'C': doType(VK_SHIFT, VK_C); break;
                case 'D': doType(VK_SHIFT, VK_D); break;
                case 'E': doType(VK_SHIFT, VK_E); break;
                case 'F': doType(VK_SHIFT, VK_F); break;
                case 'G': doType(VK_SHIFT, VK_G); break;
                case 'H': doType(VK_SHIFT, VK_H); break;
                case 'I': doType(VK_SHIFT, VK_I); break;
                case 'J': doType(VK_SHIFT, VK_J); break;
                case 'K': doType(VK_SHIFT, VK_K); break;
                case 'L': doType(VK_SHIFT, VK_L); break;
                case 'M': doType(VK_SHIFT, VK_M); break;
                case 'N': doType(VK_SHIFT, VK_N); break;
                case 'O': doType(VK_SHIFT, VK_O); break;
                case 'P': doType(VK_SHIFT, VK_P); break;
                case 'Q': doType(VK_SHIFT, VK_Q); break;
                case 'R': doType(VK_SHIFT, VK_R); break;
                case 'S': doType(VK_SHIFT, VK_S); break;
                case 'T': doType(VK_SHIFT, VK_T); break;
                case 'U': doType(VK_SHIFT, VK_U); break;
                case 'V': doType(VK_SHIFT, VK_V); break;
                case 'W': doType(VK_SHIFT, VK_W); break;
                case 'X': doType(VK_SHIFT, VK_X); break;
                case 'Y': doType(VK_SHIFT, VK_Y); break;
                case 'Z': doType(VK_SHIFT, VK_Z); break;
                case '`': doType(VK_BACK_QUOTE); break;
                case '0': doType(VK_0); break;
                case '1': doType(VK_1); break;
                case '2': doType(VK_2); break;
                case '3': doType(VK_3); break;
                case '4': doType(VK_4); break;
                case '5': doType(VK_5); break;
                case '6': doType(VK_6); break;
                case '7': doType(VK_7); break;
                case '8': doType(VK_8); break;
                case '9': doType(VK_9); break;
                case '-': doType(VK_MINUS); break;
                case '=': doType(VK_EQUALS); break;
                case '~': doType(VK_SHIFT, VK_BACK_QUOTE); break;
                case '!': doType(VK_SHIFT, VK_1); break;
                case '@': doType(VK_SHIFT, VK_2); break;
                case '#': doType(VK_SHIFT, VK_3); break;
                case '$': doType(VK_SHIFT, VK_4); break;
                case '%': doType(VK_SHIFT, VK_5); break;
                case '^': doType(VK_SHIFT, VK_6); break;
                case '&': doType(VK_SHIFT, VK_7); break;
                case '*': doType(VK_SHIFT, VK_8); break;
                case '(': doType(VK_SHIFT, VK_9); break;
                case ')': doType(VK_SHIFT, VK_0); break;
                case '_': doType(VK_UNDERSCORE); break;
                case '+': doType(VK_SHIFT, VK_EQUALS); break;
                case '\t': doType(VK_TAB); break;
                case '\n': doType(VK_ENTER); break;
                case '[': doType(VK_OPEN_BRACKET); break;
                case ']': doType(VK_CLOSE_BRACKET); break;
                case '\\': doType(VK_BACK_SLASH); break;
                case '{': doType(VK_SHIFT, VK_OPEN_BRACKET); break;
                case '}': doType(VK_SHIFT, VK_CLOSE_BRACKET); break;
                case '|': doType(VK_SHIFT, VK_BACK_SLASH); break;
                case ';': doType(VK_SEMICOLON); break;
                case ':': doType(VK_COLON); break;
                case '\'': doType(VK_QUOTE); break;
                case '"': doType(VK_QUOTEDBL); break;
                case ',': doType(VK_COMMA); break;
                case '<': doType(VK_SHIFT, VK_COMMA); break;
                case '.': doType(VK_PERIOD); break;
                case '>': doType(VK_SHIFT, VK_PERIOD); break;
                case '/': doType(VK_SLASH); break;
                case '?': doType(VK_SHIFT, VK_SLASH); break;
                case ' ': doType(VK_SPACE); break;
                default:
                    throw new IllegalArgumentException("Cannot type character " + character);
                }/*
            	boolean upperCase = Character.isUpperCase( letter.charAt(0) );
            	String variableName = "VK_" + letter.toUpperCase();
    
            	KeyEvent ke = new KeyEvent(new JTextField(), 0, 0, 0, 0, ' ');
            	Class<? extends KeyEvent> clazz = ke.getClass();
            	Field field = clazz.getField( variableName );
            	int keyCode = field.getInt(ke);

            	if (upperCase) robot.keyPress( KeyEvent.VK_SHIFT );
    
            	robot.keyPress( keyCode );
            	robot.keyRelease( keyCode );
    
            	if (upperCase) robot.keyRelease( KeyEvent.VK_SHIFT );*/
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
         	JLabel pointLabel = new JLabel("NULL NULL | 255 255 255");
         	pointFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         	pointFrame.getContentPane().add(pointLabel, BorderLayout.CENTER);
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
            	pointLabel.setText(coords);
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
    	public static BufferedImage image(int x, int y, int xx, int yy, String name, boolean save) throws IOException {
        	BufferedImage bufferedImage = robot.createScreenCapture(new Rectangle(xx,yy,x,y));
        	if(save == true) {
            	File imageFile = new File("BNetAuto Folder/" + name+ ".png");
            	ImageIO.write(bufferedImage, "png", imageFile);
        	}
        	return bufferedImage;
    	}
    	public static void detectWar() {

    		//"HKLM\Software\WOW6432Node\Blizzard Entertainment\Warcraft III" /v InstallPath
    		if(System.getProperty("os.name").contains("Windows")) {
	        war3Dir = WindowsRegistry.readRegistry("HKLM\\Software\\WOW6432Node\\Blizzard Entertainment\\Warcraft III", "GamePath");
	        }
        	else {
        		JFileChooser jFileDir = new JFileChooser();
        		jFileDir.setCurrentDirectory(new File("C:/"));
        		jFileDir.setDialogTitle("Warcraft 3 Directory");
        		FileNameExtensionFilter filter = new FileNameExtensionFilter("EXE File","exe");
        		jFileDir.setFileFilter(filter);
        		jFileDir.showOpenDialog(frame);
        		war3Dir = jFileDir.getSelectedFile().getAbsolutePath();
        		System.out.println(war3Dir);
        		dataLabel.setText("Manual Warcraft Directory Input");
        		//JFrame path = new JFrame("Warcraft 3 Executable Path");
        		//war3Dir = JOptionPane.showInputDialog(path, "Path to (including) /Warcraft III.exe");
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
        	Runtime.getRuntime().exec("taskkill /F /IM " + '"' +process + '"');
       	 
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
    	public static boolean colorCheck(int x, int y, int red, int green, int blue) throws AWTException {
        	Color color = robot.getPixelColor(x, y);
        	int cr = color.getRed();
        	int cg = color.getGreen();
        	int cb = color.getBlue();
        	if(cr != red || cg != green || cb !=blue) {
            	return false;
        	}
        	return true;
    	}
    	public static void relaunchWar() throws IOException, InterruptedException, AWTException {
    		closeWar();
    		Thread.sleep(300);
    		runWar();
    		warLogin();
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
    	public static void warLogin() throws AWTException, IOException, InterruptedException {
    		robot.mouseMove(0, height-1);
    		dataLabel.setText("Waiting for launch");
          	cx = width - 1;
          	while (colorCheck(1, 1, 0, 0, 0) !=true && (colorCheck(width/2, height/2, 0, 0, 0) == false || colorCheck(width/2, height/2, 255, 255, 255) == false) && colorCheck(cx, 1, 0, 0, 0) !=true) {
          		//Wait for screen to fully blacken, and maybe that weird white box to pop up in middle
          	}
          	Thread.sleep(50);
          	Color color = robot.getPixelColor(width/2, height/2);
          	// && colorCheck(1, 1, 0, 0, 0, robot) !=true && colorCheck(width/2, height/2, 0, 0, 0, robot) == false && colorCheck(cx, 1, 0, 0, 0, robot) !=true
        	while(color.getBlue() < 100 || color.getBlue() == 255) { //Wait until the top left is black or the middle is not black
        		Thread.sleep(25);
        		color = robot.getPixelColor(width/2, height/2);

          	}
          	BufferedImage shot1 = image(width, height,0,0,  "Launch", false);

          	while(color(shot1, cx, 0).getRed() == 0 && color(shot1, cx, 0).getGreen() == 0 && color(shot1, cx, 0).getBlue() == 0) {
              	cx--;
              	if(cx < (width - (width / 7))) {
                  	cx = width-1;
              	}
          	}
          	dataLabel.setText("Waiting for menus");
          	BufferedImage bottomRight1 = image(cx/20, height/20, cx - cx/20, height - height/20, "BottomRight1", false);
          	Thread.sleep(200);
          	BufferedImage bottomRight2 = image(cx/20, height/20, cx - cx/20, height - height/20, "null", false);;
          	while (compareColors(bottomRight1, bottomRight2, 1) == false) {
              	bottomRight2 = image(cx/20, height/20, cx - cx/20, height - height/20, "null", false);
              	Thread.sleep(300);
              	bottomRight1 = image(cx/20, height/20, cx - cx/20, height - height/20, "BottomRight1", false);
          	}
          	dataLabel.setText("Opening Battle.net");
              	robot.keyPress(KeyEvent.VK_B);
               	bottomRight1 = image((width - cx) / 2 - ((width - cx) / 15), (width - cx) / 2 - ((width - cx) / 10), width - cx + ((width - cx) / 20), height / 3 - height / 30, "testpic", false);
               	while(checkWhite(bottomRight1) == false) {
                   	Thread.sleep(50);
                   	bottomRight1 = image((width - cx) / 2 - ((width - cx) / 15), (width - cx) / 2 - ((width - cx) / 10), width - cx + ((width - cx) / 20), height / 3 - height / 30, "testpic", false);
               	}
               	Thread.sleep(100);
               	bottomRight2 = image(cx / 3, height / 2, width-cx, height / 2,  "testpic", false);
               	dataLabel.setText("Logging In");
               	Thread.sleep(200);
                   	for(int cnt = 0; cnt < pass.length(); cnt++) {
                       	typeCharacter(Character.toString(pass.charAt(cnt)));
                      	 
                   	}
                   	Thread.sleep(100);
                   	robot.keyPress(KeyEvent.VK_ENTER);
                   	Thread.sleep(250);
               	bottomRight1 = image(cx / 3, height / 2, width-cx, height / 2,  "testpic2", false);
               	if(compareColors(bottomRight1, bottomRight2, 4) == false) {
                     	for(int cnt = 0; cnt < pass.length(); cnt++) {
                         	typeCharacter(Character.toString(pass.charAt(cnt)));
                        	 
                     	}
                     	robot.keyPress(KeyEvent.VK_ENTER);
          	}
               	Thread.sleep(1250);
              	launching = false;
               	dataLabel.setText("Waiting for Menus");
              	bottomRight1 = image(cx/20, height/20, cx - cx/20, height - height/20, "BottomRight1", false);
              	Thread.sleep(200);
              	bottomRight2 = image(cx/20, height/20, cx - cx/20, height - height/20, "null", false);
              	while (compareColors(bottomRight1, bottomRight2, 1) == false) {
                  	bottomRight2 = image(cx/20, height/20, cx - cx/20, height - height/20, "null", false);
                  	Thread.sleep(100);
                  	bottomRight1 = image(cx/20, height/20, cx - cx/20, height - height/20, "BottomRight1", false);
              	}
              	Thread.sleep(50);
              	robot.keyPress(KeyEvent.VK_G);
               	Thread.sleep(200);
               	dataLabel.setText("Waiting for Game Name Input");
              	bottomRight1 = image(cx/20, height/20, cx - cx/20, height - height/20, "BottomRight1", false);
              	Thread.sleep(200);
              	bottomRight2 = image(cx/20, height/20, cx - cx/20, height - height/20, "null", false);
              	while (compareColors(bottomRight1, bottomRight2, 1) == false) {
                  	bottomRight2 = image(cx/20, height/20, cx - cx/20, height - height/20, "null", false);
                  	Thread.sleep(100);
                  	bottomRight1 = image(cx/20, height/20, cx - cx/20, height - height/20, "BottomRight1", false);
              	}
              	if((Integer) nextGameInt >= 0 && gameNames[(Integer) nextGameInt].length() >= 0){
              		for(int cnt = 0; cnt < gameNames[(Integer) nextGameInt].length(); cnt++) {
              			typeCharacter(Character.toString(gameArray[(Integer) nextGameInt].charAt(cnt)));
                  	 
               		}
                  	robot.keyPress(KeyEvent.VK_ENTER);
              	}

    	}
    	public static void endC() {
   		 JFrame f=new JFrame("Grab End Screen");       	 
     	 JButton b=new JButton("Click when the below end game option is on screen");    
     	 b.setBounds(25,25,350, 20);
     	 f.add(b);
     	 f.setSize(425,150);
     	 JLabel imageExample = new JLabel(new ImageIcon("BNetAuto Folder/endExample.png"));
     	 imageExample.setBounds(85,50,240,46);
     	 f.add(imageExample);
     	 f.setLayout(null);
     	 f.setVisible(true);
     	 f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     	 b.addActionListener(new ActionListener() {
   		 @Override
   		 public void actionPerformed(ActionEvent arg0) {
   			 try {
   				 @SuppressWarnings("unused")
				BufferedImage endC = image(width/8, height/23,width/2-width/16,height/2 - height/17,  "endC", true);
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
                //System.out.println("Field 1: " + b1.getSelectedIndex());
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
    	public static void writeLaunch() throws IOException {
    		dataLabel.setText("Changing Launch Options");
    		String option = RadioButtonDialog();
    		File f = new File("opts.txt");
    		
    		if(f.exists() && !f.isDirectory() && new BufferedReader(new FileReader("opts.txt")).readLine() != null) {
    			@SuppressWarnings("resource")
    			Scanner sc = new Scanner(f);
    			String old = sc.nextLine();
    			FileWriter writer = new FileWriter(f, false);
    			if (old.contains(" -nativefullscr")){
    				old = old.substring(0,old.length()- 15) + option;
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
    	public static void getMmhGameList() throws IOException {
    		URL mmh = new URL("http://www.makemehost.com/refresh/divGames-table-mmh.php");
    		Document doc = Jsoup.parse(mmh, 3000);
    		Element table = doc.select("table").get(0);
    		Elements rows = table.select("tr");
    		for (int i = 1; i < rows.size(); i++) {
    		    Element row = rows.get(i);
    		    Elements cols = row.select("td");
    		    Element col = cols.get(3);
    		    String colStr = col.text();
    		    if (colStr.length() > 0) {
        		    col = cols.get(4);
        		    gameNames[i-1] = colStr;
        		    colStr = colStr + ": " + col.text();
    		    }
    		    gameArray[i-1] = colStr;
    		    Object gameNameObj = gameArray[i-1];
    		    gameTable[0].getModel().setValueAt(gameNameObj, i-1, 0);
    		    //updateGameList();
    		}

    		
    	}
    	public static void getEntGameList() throws IOException, InterruptedException {
    		URL ent = new URL("http://www.makemehost.com/refresh/divGames-table-ent.php");
    		Document doc = Jsoup.parse(ent, 3000);
    		Element table = doc.select("table").get(0);
    		Elements rows = table.select("tr");
    		for (int i = 2; i < rows.size(); i++) {
    		    Element row = rows.get(i);
    		    Elements cols = row.select("td");
    		    Element col = cols.get(1);
    		    String colStr = col.text();
    		    if (colStr.length() > 0) {
        		    col = cols.get(2);
        		    gameNames[i+34] = colStr;
        		    colStr = colStr + ": " + col.text();
    		    }
    		    gameArray[i+34] = colStr;
    		    Object gameNameObj = gameArray[i+34];
    		    gameTable[1].getModel().setValueAt(gameNameObj, i-2, 0);
    		    //updateGameList();
    		}

    		
    	}
    	public static void updateMmhGameList() {
            gameTableData1 = new Object[][] {
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
                {gameArray[26], "(L)Alt+1"},
                {gameArray[27], "(L)Alt+2"},
                {gameArray[28], "(L)Alt+3"},
                {gameArray[29], "(L)Alt+4"},
                {gameArray[30], "(L)Alt+5"},
                {gameArray[31], "(L)Alt+6"},
                {gameArray[32], "(L)Alt+7"},
                {gameArray[33], "(L)Alt+8"},
                {gameArray[34], "(L)Alt+9"},
                {gameArray[35], "(L)Alt+0"},
                
            };
    	}
    	public static void updateEntGameList() {
            gameTableData2 = new Object[][] {
    	{gameArray[36], "(R)Alt+Q"},
        {gameArray[37], "(R)Alt+W"},
        {gameArray[38], "(R)Alt+E"},
        {gameArray[39], "(R)Alt+R"},
        {gameArray[40], "(R)Alt+T"},
        {gameArray[41], "(R)Alt+Y"},
        {gameArray[42], "(R)Alt+U"},
        {gameArray[43], "(R)Alt+I"},
        {gameArray[44], "(R)Alt+O"},
        {gameArray[45], "(R)Alt+P"},
        {gameArray[46], "(R)Alt+A"},
        {gameArray[47], "(R)Alt+S"},
        {gameArray[48], "(R)Alt+D"},
        {gameArray[49], "(R)Alt+F"}, 
        {gameArray[50], "(R)Alt+G"},
        {gameArray[51], "(R)Alt+H"},
        {gameArray[52], "(R)Alt+J"},
        {gameArray[53], "(R)Alt+K"},
        {gameArray[54], "(R)Alt+L"},
        {gameArray[55], "(R)Alt+Z"},
        {gameArray[56], "(R)Alt+X"},
        {gameArray[57], "(R)Alt+C"},
        {gameArray[58], "(R)Alt+V"},
        {gameArray[59], "(R)Alt+B"},
        {gameArray[60], "(R)Alt+N"},
        {gameArray[61], "(R)Alt+M"},
        {gameArray[62], "(R)Alt+1"},
        {gameArray[63], "(R)Alt+2"},
        {gameArray[64], "(R)Alt+3"},
        {gameArray[65], "(R)Alt+4"},
        {gameArray[66], "(R)Alt+5"},
        {gameArray[67], "(R)Alt+6"},
        {gameArray[68], "(R)Alt+7"},
        {gameArray[69], "(R)Alt+8"},
        {gameArray[70], "(R)Alt+9"},
        {gameArray[71], "(R)Alt+0"},
        {gameArray[72], "(R)Alt+-"},
        {gameArray[73], "(R)Alt+="},
        {gameArray[74], "(R)Alt+["},
        {gameArray[75], "(R)Alt+]"},
        {gameArray[76], "(R)Alt+;"},
        {gameArray[77], "(R)Alt+'"},
        {gameArray[78], "(R)Alt+,"},
        {gameArray[79], "(R)Alt+."},
        {gameArray[80], "(R)Alt+/"},
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






