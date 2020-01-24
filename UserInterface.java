import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;

/**
 * This class extends JFrame, and it uses numerous libraries to build an
 * interactive form to make interaction with the user much smoother and more enjoyable
 * The UserInterface class contains functions that create menu items File>>Exit and Help>>About
 * as well as the function setupUserInterface() which makes up the bulk of the class, as it
 * is responsible for building the JFrame form and creating/assigning functionality to it
 * @author BobBarr
 */
public class UserInterface extends JFrame {
	
	private ArrayList<NFLegend> legends;
	
	/**
	 * A simple start to setup the menu for the form, which includes 
	 * File>>Exit and Help>>About functionalities (with a little bonus inside the Help menu)
	 */
	public void setupMenu() {
		JMenuBar mbar = new JMenuBar();
		JMenu mnuFile = new JMenu("File");
		JMenuItem miExit = new JMenuItem("Exit");
		miExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnuFile.add(miExit);
		mbar.add(mnuFile);
		JMenu mnuHelp = new JMenu("Help");
		JMenuItem miAbout = new JMenuItem("About");
		miAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String urlTop100 = "https://www.usatoday.com/"
						+ "picture-gallery/sports/nfl-100/2019/10/08/"
						+ "nfl-100-100-greatest-nfl-players-all-time/3860716002/";
				StringSelection stringSelection = new StringSelection(urlTop100);
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(stringSelection, null);
				JOptionPane.showMessageDialog(null, "The following address has been copied to your clipboard: \n"
						+ "https://www.usatoday.com/picture-gallery/sports/nfl-100/2019/10/08/"
						+ "nfl-100-100-greatest-nfl-players-all-time/3860716002/ \n \n"
						+ "			Paste it into the URL field and click Fetch to view the Top 100 NFL Players of all time");
				
			}
		});
		mnuHelp.add(miAbout);
		mbar.add(mnuHelp);
		setJMenuBar(mbar);
	}
	
	/**
	 * This function builds the form to be functional for the user. It sets the initial title, size,
	 * the default closing operation, and sets the initial layout of the Container created by the 
	 * getContentPane() function. From there, the appropriate text fields, areas, and buttons are created
	 * and assigned actions to accomplish the main tasks of the program.
	 */
	public void setupUserInterface() {
		setTitle("Web Scraper");
		setBounds(425,150,450,450);  // left,top,width,height
		setDefaultCloseOperation(EXIT_ON_CLOSE);  //closes interface upon hitting x
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		//adds text area to a scroll pane and then that is added to the center with a borderlayout
		JTextArea txtArea = new JTextArea();
		txtArea.setEditable(false);
		JScrollPane scrlPane = new JScrollPane(txtArea);
		c.add(scrlPane,BorderLayout.CENTER);
		
		//construct JPanel to add to north borderlayout
		JPanel panNorth = new JPanel();
		panNorth.setLayout(new FlowLayout());  //is more accommodating when there's multiple components
		JLabel label = new JLabel("Enter URL:");
		JTextField txtField = new JTextField(31);
		txtField.setPreferredSize(new Dimension(31,27));
		JButton btnFetch = new JButton("Fetch");
		c.add(btnFetch,BorderLayout.NORTH);
		btnFetch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String url = txtField.getText(); //gets the text/url entered by the user
				if (url.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Failed to obtain relevant data");
				} else {
					String legendList = "";
					legends = ScreenScraper.legendScraper(url); //visits the specified url to obtain the data
					for (NFLegend legend : legends) {
						legendList = legendList + legend.toString(); //creates a string from all NFLegend objects
					}
					txtArea.setText(legendList); //sends the string to the JTextArea to be visible to user
				}
			}
		});
		panNorth.add(label);
		panNorth.add(txtField);
		panNorth.add(btnFetch);
		c.add(panNorth,BorderLayout.NORTH); //adds all components to the top of the borderlayout
		
		//construct JPanel to add to south borderlayout
		JPanel panSouth = new JPanel();
		panSouth.setLayout(new FlowLayout());
		JButton btnText = new JButton("Save to text");
		panSouth.add(btnText,BorderLayout.SOUTH);
		btnText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser jfc = new JFileChooser();
					jfc.setSelectedFile(new File("NFLegends.txt")); //sets default file name/extension
					if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						if (LegendWriter.writeLegendsToTextFile(jfc.getSelectedFile(), legends)) {
							JOptionPane.showMessageDialog(null,  "NFLegends saved to text file");
						} else {
							JOptionPane.showMessageDialog(null, "NFLegends could not be saved to text");
						}
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null,"Unable to save file to text");
				}
			}
		});
		JButton btnJSON = new JButton("Save to JSON");
		panSouth.add(btnJSON,BorderLayout.SOUTH);
		btnJSON.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser jfc = new JFileChooser();
					jfc.setSelectedFile(new File("NFLegends.json")); //sets default file name/extension
					if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						if (LegendWriter.writeLegendsToJSON(jfc.getSelectedFile(), legends)) {
							JOptionPane.showMessageDialog(null, "NFLegends saved to JSON file");
						} else {
							JOptionPane.showMessageDialog(null,"NFLegends could not be saved to JSON");
						}
					}
				} catch (Exception ex) {
					System.out.println("Unable to save file to JSON");
				}
			}
		});
		c.add(panSouth,BorderLayout.SOUTH);
		setupMenu();
	}
	
	public UserInterface() {
		setupUserInterface();
	}
}