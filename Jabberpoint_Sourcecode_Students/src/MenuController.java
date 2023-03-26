import java.awt.MenuBar;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

/** <p>The controller for the menu</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class MenuController extends MenuBar {
	
	private Frame parent; //The frame, only used as parent for the Dialogs
	private Presentation presentation; //Commands are given to the presentation
	
	private static final long serialVersionUID = 227L;
	
	protected static final String ABOUT = "About";
	protected static final String FILE = "File";
	protected static final String EXIT = "Exit";
	protected static final String GOTO = "Go to";
	protected static final String HELP = "Help";
	protected static final String NEW = "New";
	protected static final String NEXT = "Next";
	protected static final String OPEN = "Open";
	protected static final String PAGENR = "Page number?";
	protected static final String PREV = "Prev";
	protected static final String SAVE = "Save";
	protected static final String VIEW = "View";
	
	protected static final String TESTFILE = "testPresentation.xml";
	protected static final String SAVEFILE = "savedPresentation.xml";
	
	protected static final String IOEX = "IO Exception: ";
	protected static final String LOADERR = "Load Error";
	protected static final String SAVEERR = "Save Error";

	public MenuController(Frame frame, Presentation pres) {
		this.parent = frame;
		this.presentation = pres;
		MenuItem menuItem;
		Menu fileMenu = new Menu(FILE);
		fileMenu.add(menuItem = MenuControllerFactory.mkMenuItem(OPEN));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				presentation.clear();
				XMLReader xmlReader = new XMLReader();
				try {
					xmlReader.loadFile(presentation, TESTFILE);
					presentation.setSlideNumber(0);
				} catch (IOException exc) {
					JOptionPane.showMessageDialog(parent, IOEX + exc, 
         			LOADERR, JOptionPane.ERROR_MESSAGE);
				}
				parent.repaint();
			}
		} );
		fileMenu.add(menuItem = MenuControllerFactory.mkMenuItem(NEW));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				presentation.clear();
				parent.repaint();
			}
		});
		fileMenu.add(menuItem = MenuControllerFactory.mkMenuItem(SAVE));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Writable xmlWriter = AccessorFactory.createWriter("XML");
				try {
					xmlWriter.saveFile(presentation, SAVEFILE);
				} catch (IOException exc) {
					JOptionPane.showMessageDialog(parent, IOEX + exc, 
							SAVEERR, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		fileMenu.addSeparator();
		fileMenu.add(menuItem = MenuControllerFactory.mkMenuItem(EXIT));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				presentation.exit(0);
			}
		});
		add(fileMenu);
		Menu viewMenu = new Menu(VIEW);
		viewMenu.add(menuItem = MenuControllerFactory.mkMenuItem(NEXT));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				presentation.nextSlide();
			}
		});
		viewMenu.add(menuItem = MenuControllerFactory.mkMenuItem(PREV));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				presentation.prevSlide();
			}
		});
		viewMenu.add(menuItem = MenuControllerFactory.mkMenuItem(GOTO));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				String pageNumberStr = JOptionPane.showInputDialog((Object)PAGENR);
				int pageNumber = Integer.parseInt(pageNumberStr);
				presentation.setSlideNumber(pageNumber - 1);
			}
		});
		add(viewMenu);
		Menu helpMenu = new Menu(HELP);
		helpMenu.add(menuItem = MenuControllerFactory.mkMenuItem(ABOUT));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				AboutBox.show(parent);
			}
		});
		setHelpMenu(helpMenu);		//Needed for portability (Motif, etc.).
	}
}
