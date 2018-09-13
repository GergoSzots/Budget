package Gazd_UI;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class UI extends JFrame {
	String dbName;

	public UI() {
		//�ltal�nos be�ll�t�sok
		setVisible(true); //megjelenjen a program mikor lefut a script
		setSize(400,400);//java program kezd� m�rete
		setTitle("Budget: Costings and Savings");//neve az alkalmaz�snak
		setDefaultCloseOperation(EXIT_ON_CLOSE); //X-re kattintva z�rja be a programot
		
		Toolkit toolkit = getToolkit(); //megh�vom a toolkitet, hogy azzal majd a screen be�ll�t�st megtudjam
		Dimension monitorSize = toolkit.getScreenSize();
		
		setLocation(monitorSize.width/2 - getWidth()/2,
					monitorSize.height/2 - getHeight()/2);//k�z�pen jelenjen meg a program
		
		//Menubart itt adom hozz�
		JMenuBar menubar = new JMenuBar();
		JMenuBar menubar2 = new JMenuBar();
		
		
		//ImageIcon icon1 = new ImageIcon(this.getClass().getResource("/src/images/close.png"));//tall�zni kell valami k�pet ha akarom h legyen
		//Image icon11 = icon1.getImage();
		//ImageIcon icon2 = new ImageIcon("close.png");
		//ImageIcon icon1 = new ImageIcon("images/close.png");
		
		JMenu file = new JMenu("File");
		JMenu options = new JMenu("Options");
		JMenu help = new JMenu("Help");
		
		JMenuItem fileClose = new JMenuItem("Close"); //MenuItem ami a leny�l� fileb�l j�nnek
		fileClose.setMnemonic(KeyEvent.VK_X);//�GY K�NE GYORSBILLENTY�T �LL�TANI DE NEM M�KSZIK!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		fileClose.setToolTipText("Exit application");//itt �ll�tok be tooltippet az alkalmaz�shoz
		fileClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		}
		);
		
		JMenuItem save = new JMenuItem("Save"); // File men� al� save-t berakom
		save.setMnemonic(KeyEvent.VK_S);
		save.setToolTipText("Save current project");
		
		JMenuItem saveAs = new JMenuItem("Save As..");
		saveAs.setToolTipText("Save current project");
		
		JMenuItem open = new JMenuItem("Open");
		open.setToolTipText("Open an existing project");
		open.setMnemonic(KeyEvent.VK_O);
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try(
						Connection conn = DriverManager.getConnection(
								"jdbc:mysql://localhost:3306/", "myuser", "Mu1891!");
						Statement stmt = conn.createStatement();
						){
						
				String sqlStr = "show databases";
				ResultSet rset = stmt.executeQuery(sqlStr) ;
				List<String> dbNames = new ArrayList<>(); 
				while(rset.next()) {
					 String name = rset.getString("Database");
					 dbNames.add(name);

				}
					String dbName = JOptionPane.showInputDialog(
								"Which one of the following databases do you like to use?" + dbNames);
					String sqlStr2 = "use " + dbName ;
					int sqlUpgr = stmt.executeUpdate(sqlStr2);
				}
				catch(SQLException se) {
					se.printStackTrace();
				}
			}
			});

		JMenuItem delete = new JMenuItem("Delete");
		delete.setToolTipText("Delete the actual project");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(dbName != null) {
					try(
							
							
							String test = String.format("jdbc:mysql://localhost:3306/%s", dbName + "myuser", "Mu1891!");
							//itt szeretn�m, hogy a db nevet bele�rni a connection, ha l�tezik, mert �gy hagyom kiv�lasztok 1 database-t pl opennel, hi�ba useolom, nem t�rli
							//vagy azt is lehetne hogy itt useolom a dbName nev� adatb�zist azonnal, �s ut�na t�rl�s. majd ki kell pr�b�lni.
							
							
							Connection conn = DriverManager.getConnection(
									"jdbc:mysql://localhost:3306/" , "myuser", "Mu1891!");
							Statement stmt = conn.createStatement();
							){
						String sqlUpdate = "delete database" + dbName;
						JOptionPane.showMessageDialog(null,"The " + dbName + " has been deleted succesfully");
					}
					catch(SQLException se) {
						se.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(null,"There was no database selected");
				}
				}});
				
				
			
		
		
		
		
		JMenuItem newFile = new JMenuItem("New");
		newFile.setToolTipText("Create new project");
		newFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String dbName = JOptionPane.showInputDialog(null, "Name of the project: "); 
				try(
						Connection conn = DriverManager.getConnection(
								"jdbc:mysql://localhost:3306/", "myuser", "Mu1891!");
						Statement stmt = conn.createStatement();
						){
						
						String sqlUpdate = "create database if not exists " + dbName ;
						
						int Result = stmt.executeUpdate(sqlUpdate);
						String sqlStr = "use " + dbName ;
						int sqlUpgdate2 = stmt.executeUpdate(sqlStr);
					
						}
				catch(SQLException se) {
					se.printStackTrace();
			}
		}
		});
		
		
		JMenuItem theme = new JMenuItem("Theme");
		theme.setToolTipText("Choose a theme you like the best");
		
		
		JMenu preferences = new JMenu("Preferences"); //Ahhoz hogy lehessen submenu haszn�lni, ez JMenunek kell lennie
		preferences.setToolTipText("Set the colors, fonts, etc. here");//nem pedig JMenuItemnek.
		
		JMenuItem fonts = new JMenuItem("Fonts");
		JMenuItem size = new JMenuItem("Size");

		//itt rakom be a fentebb defini�lt dolgokat a frambe
		menubar.add(file);
		file.add(newFile);
		file.add(open);
		file.add(saveAs);
		file.add(save);
		file.add(delete);
		file.addSeparator();
		file.add(fileClose);
		
		menubar.add(options);
		options.add(theme);
		options.add(preferences);
		preferences.add(fonts);
		preferences.add(size);
		
		menubar.add(help);
		setJMenuBar(menubar);
		
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		UI ui = new UI();
		
		
	}

}


/*
Help: verzi�, el�r�s,
Help: Hints: pl. delete visszavonhatatlan, 
Help:keybindings
bugok: ha nem csatlakozott a szerverhez akkor ha pl new projekt, �rjon ki hib�t.
*/