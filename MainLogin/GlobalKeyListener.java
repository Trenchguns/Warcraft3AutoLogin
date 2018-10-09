import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
public class GlobalKeyListener implements NativeKeyListener {
	public Boolean alt;
	public void nativeKeyPressed(NativeKeyEvent e) {
		System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
		if (e.getKeyCode() == NativeKeyEvent.VC_ALT) {
			alt = true;
			System.out.println("True");
		}
		if (alt == true && e.getKeyCode() == NativeKeyEvent.VC_E) {
				System.exit(0);
		}
	}

	public void nativeKeyReleased(NativeKeyEvent e) {
		if (e.getKeyCode() == NativeKeyEvent.VC_ALT && (e.getKeyLocation() == 3)) {
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
