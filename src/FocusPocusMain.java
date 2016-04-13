//import javax.swing.UIManager;
//import javax.swing.UIManager.LookAndFeelInfo;

//import javax.swing.UIManager;
//import javax.swing.UIManager.LookAndFeelInfo;

import focuspocus.gui.FocusPocus;

public final class FocusPocusMain {

  private FocusPocusMain() {
    throw new IllegalStateException();
  }

  public static void main(final String[] the_args) {
    
    final FocusPocus gui = new FocusPocus();
    gui.start();
  }
}
