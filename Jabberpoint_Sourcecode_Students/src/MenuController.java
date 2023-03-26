import Utility.ErrorMessages;
import Utility.FileMessages;
import Utility.MenuItems;

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

	public MenuController(Frame frame, Presentation pres) {
		this.parent = frame;
		this.presentation = pres;
		MenuItem menuItem;
		Menu fileMenu = new Menu(FileMessages.FILE);
		fileMenu.add(menuItem = MenuControllerFactory.mkMenuItem(MenuItems.OPEN));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				presentation.clear();
				XMLReader xmlReader = new XMLReader();
				try {
					xmlReader.loadFile(presentation, FileMessages.TESTFILE);
					presentation.setSlideNumber(0);
				} catch (IOException exc) {
					JOptionPane.showMessageDialog(parent, ErrorMessages.IOEX + exc,
         			ErrorMessages.LOADERR, JOptionPane.ERROR_MESSAGE);
				}
				parent.repaint();
			}
		} );
		fileMenu.add(menuItem = MenuControllerFactory.mkMenuItem(MenuItems.NEW));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				presentation.clear();
				parent.repaint();
			}
		});
		fileMenu.add(menuItem = MenuControllerFactory.mkMenuItem(MenuItems.SAVE));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Writable xmlWriter = AccessorFactory.createWriter("XML");
				try {
					xmlWriter.saveFile(presentation, FileMessages.SAVEFILE);
				} catch (IOException exc) {
					JOptionPane.showMessageDialog(parent, ErrorMessages.IOEX + exc,
							ErrorMessages.SAVEERR, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		fileMenu.addSeparator();
		fileMenu.add(menuItem = MenuControllerFactory.mkMenuItem(MenuItems.EXIT));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				presentation.exit(0);
			}
		});
		add(fileMenu);
		Menu viewMenu = new Menu(MenuItems.VIEW);
		viewMenu.add(menuItem = MenuControllerFactory.mkMenuItem(MenuItems.NEXT));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				presentation.nextSlide();
			}
		});
		viewMenu.add(menuItem = MenuControllerFactory.mkMenuItem(MenuItems.PREV));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				presentation.prevSlide();
			}
		});
		viewMenu.add(menuItem = MenuControllerFactory.mkMenuItem(MenuItems.GOTO));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				String pageNumberStr = JOptionPane.showInputDialog((Object)MenuItems.PAGENR);
				int pageNumber = Integer.parseInt(pageNumberStr);
				presentation.setSlideNumber(pageNumber - 1);
			}
		});
		add(viewMenu);
		Menu helpMenu = new Menu(MenuItems.HELP);
		helpMenu.add(menuItem = MenuControllerFactory.mkMenuItem(MenuItems.ABOUT));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				AboutBox.show(parent);
			}
		});
		setHelpMenu(helpMenu);		//Needed for portability (Motif, etc.).
	}
}
