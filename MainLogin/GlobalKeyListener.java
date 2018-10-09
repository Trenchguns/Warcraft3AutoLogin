import java.awt.AWTException;
import java.io.IOException;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
public class GlobalKeyListener implements NativeKeyListener {
	public static Boolean alt;
	public void nativeKeyPressed(NativeKeyEvent e) {
		if (e.getKeyCode() == NativeKeyEvent.VC_ALT) {
			alt = true;
		}
		if (alt == true) {
			if(e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
				System.exit(0);
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_Q) {
				Main.nextGameInt = 0;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[0]);
				System.out.println(Main.launching);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_W) {
				Main.nextGameInt = 1;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[1]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_E) {
				Main.nextGameInt = 2;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[2]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_R) {
				Main.nextGameInt = 3;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[3]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_T) {
				Main.nextGameInt = 4;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[4]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_Y) {
				Main.nextGameInt = 5;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[5]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_U) {
				Main.nextGameInt = 6;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[6]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_I) {
				Main.nextGameInt = 7;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[7]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_O) {
				Main.nextGameInt = 8;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[8]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_P) {
				Main.nextGameInt = 9;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[9]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_A) {
				Main.nextGameInt = 10;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[10]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_S) {
				Main.nextGameInt = 11;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[11]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_D) {
				Main.nextGameInt = 12;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[12]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_F) {
				Main.nextGameInt = 13;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[13]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_G) {
				Main.nextGameInt = 14;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[14]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_H) {
				Main.nextGameInt = 15;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[15]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_J) {
				Main.nextGameInt = 16;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[16]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_K) {
				Main.nextGameInt = 17;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[17]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_L) {
				Main.nextGameInt = 18;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[18]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_Z) {
				Main.nextGameInt = 19;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[19]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_X) {
				Main.nextGameInt = 20;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[20]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_C) {
				Main.nextGameInt = 21;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[21]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_V) {
				Main.nextGameInt = 22;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[22]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_B) {
				Main.nextGameInt = 23;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[23]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_N) {
				Main.nextGameInt = 24;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[24]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_1) {
				Main.nextGameInt = 25;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[25]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_2) {
				Main.nextGameInt = 26;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[26]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_3) {
				Main.nextGameInt = 27;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[27]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_4) {
				Main.nextGameInt = 28;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[28]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_5) {
				Main.nextGameInt = 29;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[29]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_6) {
				Main.nextGameInt = 30;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[30]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_7) {
				Main.nextGameInt = 31;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[31]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_8) {
				Main.nextGameInt = 32;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[32]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_9) {
				Main.nextGameInt = 33;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[33]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_0) {
				Main.nextGameInt = 34;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[34]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			else if(e.getKeyCode() == NativeKeyEvent.VC_MINUS) {
				Main.nextGameInt = 35;
				Main.nextGameLabel.setText("Next game: " + Main.gameNames[35]);
				if(Main.launching == false) {
					try {
						Main.relaunchWar();
					} catch (IOException | InterruptedException | AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
			

		}
	}

	public void nativeKeyReleased(NativeKeyEvent e) {
		if (e.getKeyCode() == NativeKeyEvent.VC_ALT) {
			alt = false;
		}
	}

	public void nativeKeyTyped(NativeKeyEvent e) {
		System.out.println("Key Typed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
	}

	public static void main(String[] args) {
		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}

		GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
	}
}
