package focuspocus.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import focuspocus.filters.AbstractFilter;
import focuspocus.filters.EdgeDetectFilter;
import focuspocus.filters.EdgeHighlightFilter;
import focuspocus.filters.Filter;
import focuspocus.filters.FlipHorizontalFilter;
import focuspocus.filters.FlipVerticalFilter;
import focuspocus.filters.GrayscaleFilter;
import focuspocus.filters.SharpenFilter;
import focuspocus.filters.SoftenFilter;
import focuspocus.image.PixelImage;
import javax.swing.SwingUtilities;

public class FocusPocus extends JFrame {
  
  private static final int UNDO_VISIBLE = 9;
  private static final JPanel CENTER_PANEL = new JPanel(new FlowLayout());
  private static final ImageIcon CLOSE = new ImageIcon("cancel.png");
  private static final ImageIcon UNDO = new ImageIcon("undo.png");
  private static final JPanel MASTER_PANEL = new JPanel(new BorderLayout());
  private static final JPanel NORTH_PANEL = new JPanel(new FlowLayout());
  private static final int NUM_BUTTONS = 7;
  private static final ImageIcon OPEN = new ImageIcon("open.png");
  private static final ImageIcon SAVE_AS = new ImageIcon("save.png");
  private static final JPanel SOUTH_PANEL = new JPanel(new FlowLayout());
  private final List<JButton> my_buttonlist = new ArrayList<JButton>();
  private final List<AbstractFilter> my_filters = new ArrayList<AbstractFilter>();
  private final List<PixelImage> my_edits = new ArrayList<PixelImage>();
  private JButton my_holder;
  private final JFileChooser my_file_chooser = 
      new JFileChooser(System.getProperty("/Users"));
  private final File my_edited = new File("names");
  private final File my_edited_second = new File("name");
  private PixelImage my_image;
  private JLabel my_next;
  private int my_position;
  
  public FocusPocus() {
    super("FocusPocus");
    my_filters.add(new EdgeDetectFilter());
    my_filters.add(new EdgeHighlightFilter()); 
    my_filters.add(new FlipHorizontalFilter());
    my_filters.add(new FlipVerticalFilter());
    my_filters.add(new GrayscaleFilter());
    my_filters.add(new SharpenFilter());
    my_filters.add(new SoftenFilter());
    my_holder = new JButton();
    my_position = 0;
  }
  
  public void start() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    setMaximumSize(new Dimension(1440, 900));
    setMinimumSize(new Dimension(1440,900));
    setLocationByPlatform(true);
    setupComponents();  
    pack();
    setVisible(true);
  }
  
  private void setupComponents() {
    //create the filter buttons.
    for (int i = 0; i < NUM_BUTTONS; i++) {
      final JButton temp = createFilterButton(my_filters.get(i));
      temp.setForeground(Color.WHITE);
      temp.setBorderPainted(false);
      NORTH_PANEL.add(temp);
      NORTH_PANEL.setBackground(Color.DARK_GRAY);
      my_buttonlist.add(temp);
    }
    //create the open button.
    my_holder = createOpenButton();
    SOUTH_PANEL.add(my_holder);
    SOUTH_PANEL.setBackground(Color.DARK_GRAY);
    
    //create the save button.
    my_holder = createSaveButton();
    SOUTH_PANEL.add(my_holder);
    my_buttonlist.add(my_holder);
    
    //create the close button.
    my_holder = createCloseButton();
    SOUTH_PANEL.add(my_holder);
    my_buttonlist.add(my_holder);
    
    //create the undo button.
    my_holder = createUndoButton();
    SOUTH_PANEL.add(my_holder);
    my_buttonlist.add(my_holder);
    
    setVisibleButtons(false);
    MASTER_PANEL.setBackground(Color.DARK_GRAY);
    MASTER_PANEL.add(NORTH_PANEL, BorderLayout.NORTH);
    MASTER_PANEL.add(SOUTH_PANEL, BorderLayout.SOUTH);
    add(MASTER_PANEL);
  }
  
  private JButton createFilterButton(final Filter the_filter) {
    final JButton button = new JButton(the_filter.getDescription());
    button.addActionListener(new ActionListener() { 
      public void actionPerformed(final ActionEvent the_event) {
        try {
            /*SwingUtilities.invokeLater(new Runnable(){
                @Override
                public void run() {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });*/
          my_buttonlist.get(UNDO_VISIBLE).setEnabled(true);         
          the_filter.filter(my_image);
          my_image.save(my_edited);
          final PixelImage temp = PixelImage.load(my_edited);
          temp.save(my_edited_second);
          my_edits.add(temp);
          my_position++;    
          my_next.setIcon(new ImageIcon(my_image));
          setCenterPanel();
        } catch (final IOException e) {
          JOptionPane.showMessageDialog(null, "Invalid process.", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });
    return button;
  }
  
  private JButton createSaveButton() {
    final JButton save = new JButton("Save", SAVE_AS);
    save.setForeground(Color.WHITE);
    save.setBorderPainted(false);
    save.addActionListener(new ActionListener() { 
      public void actionPerformed(final ActionEvent the_event) {
        final int result = my_file_chooser.showSaveDialog(FocusPocus.this);
        if (result == JFileChooser.APPROVE_OPTION) {
          try {
            my_image.save(my_file_chooser.getSelectedFile());
          } catch (final IOException e) {
            e.printStackTrace();
          }
        }
      }
    });
    return save;
  }

  private void setCenterPanel() {
    CENTER_PANEL.setVisible(true);
    CENTER_PANEL.removeAll();
    CENTER_PANEL.add(my_next);
    CENTER_PANEL.setBackground(Color.DARK_GRAY); 
    
    MASTER_PANEL.add(CENTER_PANEL, BorderLayout.CENTER);
  }
  
  private JButton createOpenButton() {
    final JButton open = new JButton("Open", OPEN);
    open.setForeground(Color.WHITE);
    open.setBorderPainted(false);
    open.addActionListener(new ActionListener() { 
      public void actionPerformed(final ActionEvent the_event) {
        final int result = my_file_chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
          try {
            my_position = 0; my_edits.clear();
            my_buttonlist.get(UNDO_VISIBLE).setEnabled(false);
            my_image = PixelImage.load(my_file_chooser.getSelectedFile());
            final PixelImage temp = PixelImage.load(my_file_chooser.getSelectedFile());
            my_edits.add(temp); my_next = new JLabel();
            my_next.setIcon(new ImageIcon(my_image)); setCenterPanel();
            setVisibleButtons(true); my_buttonlist.get(UNDO_VISIBLE).setEnabled(false);
          } catch (final IOException e) {
            JOptionPane.showMessageDialog(null, "File did not contain a valid image: " 
                + my_file_chooser.getSelectedFile(), "Invalid", JOptionPane.ERROR_MESSAGE);
          }
          pack();
        }
      }
    });
    return open;
  }
  
  private JButton createCloseButton() {
    final JButton close = new JButton("Close", CLOSE);
    close.setForeground(Color.WHITE);
    close.setBorderPainted(false);
    close.addActionListener(new ActionListener() { 
      public void actionPerformed(final ActionEvent the_event) {
        CENTER_PANEL.removeAll();
        CENTER_PANEL.setVisible(false);
        setVisibleButtons(false);
        pack();
        my_position = 0;
        my_edits.clear();
      }
    });
    return close;
  }
  
  private JButton createUndoButton() {
    final JButton undo = new JButton("Undo", UNDO);
    undo.setForeground(Color.WHITE);
    undo.setBorderPainted(false);
    undo.addActionListener(new ActionListener() { 
      public void actionPerformed(final ActionEvent the_event) {
        try {
          if (my_position > 0) {
            final PixelImage temp = my_edits.get(my_position - 1);
            temp.save(my_edited_second);
            my_next.setIcon(new ImageIcon(temp));
            my_image = PixelImage.load(my_edited_second);
            setCenterPanel();
            my_edits.remove(my_position);
            my_position--;
            if (my_position == 0) {
              my_buttonlist.get(UNDO_VISIBLE).setEnabled(false);
            }
          }
        } catch (final IOException e) {
          e.printStackTrace();
        }
      }
    });
    return undo;
  }
  
  private void setVisibleButtons(final boolean the_value) {
    for (JButton b : my_buttonlist) {
      b.setEnabled(the_value);
    }
  }  
}
