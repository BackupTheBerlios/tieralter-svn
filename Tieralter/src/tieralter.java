import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JProgressBar;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 * Das Tieralter Programm zur Berechnung des Alters Ihres Haustiers.
 * Copyright (C) [2009]  [Oliver Türpe]
 * @author Oliver Türpe
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program; if not, see <http://www.gnu.org/licenses/>.
 * 
 * Elektronisch bin ich unter oliver@tuerpe.info zu erreichen.
 */

public class tieralter extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel Erklärung = null;
	private JButton Los = null;
	private JComboBox Tier = null;
	private JComboBox Monat = null;
	private JComboBox Jahr = null;
	private JLabel LTag = null;
	private JComboBox Tag = null;
	private JLabel LMonat = null;
	private JLabel LJahr = null;
	private JLabel LTier = null;
	private JMenuBar jJMenuBar = null;
	private JMenu config = null;
	private JMenu infos = null;
	private JProgressBar fortschritt = null;
	private JLabel LRasse = null;
	private JComboBox Rasse = null;
	private double faktor = 0; //Faktor soll aus Datei gelesen werden und wird hier nur initialisiert
	private double alterDesTieres = 0; //Das Alter des Tiers wird hier initialisiert
	private final double ALTERDESMENSCHEN = 90d; //hier steht das aktuelle Durchschnittsalter des Menschen
	/**
	 * Wertet aus welches Tier vorliegt.
	 * Hier wird ermittelt wie der Faktor zwischen Mensch und Tier ist.
	 * @return Faktor zur Weiterberechnung des Alters
	 */
	private double auswerten(Object Objekt){
		System.out.println("data" + File.separator + "rassen" + File.separator + Objekt.toString() + "_rassen.dat");
		File alterdatei = new File("data" + File.separator + "rassen" + File.separator + Objekt.toString() + "_rassen.dat"); //relative Pfadangabe zur Datenbank der Tiere
		String zeile = " "; //zeile initialisieren
		String wert = "";
		int zaehler = 0;
		try{
			FileReader fr = new FileReader(alterdatei);
			BufferedReader einlesen = new BufferedReader(fr);
			while (zeile != null){
				try {
					zeile = einlesen.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try{
					System.out.println(Objekt.toString());
					if (zeile.startsWith(Rasse.getSelectedItem().toString())){
						for (int i = 0; i< zeile.length();i++){
							if (zeile.charAt(i) == ','){
								break;
							}
							else{
								zaehler++;
							}
						}
						for (int i = zaehler +2; i < zeile.length(); i++){
							wert = wert + zeile.charAt(i); 
						}	
					}
				}catch(NullPointerException e2){
					//nix machen
				}
				System.out.println(wert);
			}
		}catch (FileNotFoundException e){
			System.out.println("tiere.dat nicht gefunden!");
		}
		System.out.println("Das Durchschnittsalter ist: " + wert);
		double faktor = ALTERDESMENSCHEN / Double.parseDouble(wert);
		return faktor;
	}
	
	/**
	 * Überprüft, ob das Datum korrekt ist.
	 * @return true wenn ja, false wenn nicht
	 */
	private boolean korrektesDatum(){
		if ((Tag.getSelectedIndex() == 0) | (Monat.getSelectedIndex() == 0) | (Jahr.getSelectedIndex() == 0)){
			System.out.println("Keinen Tag/Monat/Jahr ausgewählt."); //Wenn eine Angabe gemacht -> ungültig
			return false;
		}
		else{//Ist es ein Schaltjahr?
			if (Integer.parseInt(Jahr.getSelectedItem().toString()) % 4 == 0){ //ja
				if (Monat.getSelectedIndex() == 2){
					if (Tag.getSelectedIndex() > 29){
						System.out.println("Der Februar hat nur 29 Tage in Schaltjahren!");
						return false;
					}
				}
			}else{//nein, kein Schaltjahr
				switch(Monat.getSelectedIndex()){
				case 2:
					if (Tag.getSelectedIndex() > 28){ //Der Februar
						System.out.println("Der Februar hat nur 28 Tage!");
						return false;
					} 
				case 4: case 6: case 9: case 11: //Monate mit 30 Tagen
					if (Tag.getSelectedIndex() > 30){
						System.out.println("Dieser Monat hat höchstens 30 Tage!");
						return false;
					}
					break;
				}
			}
		}
		return true;//alles korrekt eingegeben -> true
	}
	
	/**
	 * Diese Methode rechnet aus, wie alt das Tier nun wirklich (im Vergleich zum Menschen) ist.
	 * @param faktor
	 * @return Alter des Tieres
	 */
	private double ausrechnen(double faktor){
		//Variablen für die Rechnung initialisieren
		double alter = 0D; //Der Endwert
		double tage = 0; //Zeitdifferenz der beiden Daten
		//Initialisierung abgeschlossen
		
		Calendar geboren = new GregorianCalendar(); //zwei Kalender erstellen
		Calendar heute = new GregorianCalendar(); //wann das Tier geboren wurde und der heutige Tag
		heute.setTime( new Date() ); //aktuelles Datum eintragen
		
		switch(Monat.getSelectedIndex()){ //Geburtstag festlegen
		case 1: { 
			geboren.set( Integer.parseInt(Jahr.getSelectedItem().toString()), Calendar.JANUARY, Tag.getSelectedIndex(), 0, 0, 0 );
			break;
		}
		case 2: {
			geboren.set( Integer.parseInt(Jahr.getSelectedItem().toString()), Calendar.FEBRUARY, Tag.getSelectedIndex(), 0, 0, 0 );
			break;
		}
		case 3: {
			geboren.set( Integer.parseInt(Jahr.getSelectedItem().toString()), Calendar.MARCH, Tag.getSelectedIndex(), 0, 0, 0 );
			break;
		}
		case 4: {
			geboren.set( Integer.parseInt(Jahr.getSelectedItem().toString()), Calendar.APRIL, Tag.getSelectedIndex(), 0, 0, 0 );
			break;
		}
		case 5: {
			geboren.set( Integer.parseInt(Jahr.getSelectedItem().toString()), Calendar.MAY, Tag.getSelectedIndex(), 0, 0, 0 );
			break;
		}
		case 6: {
			geboren.set( Integer.parseInt(Jahr.getSelectedItem().toString()), Calendar.JUNE, Tag.getSelectedIndex(), 0, 0, 0 );
			break;
		}
		case 7: {
			geboren.set( Integer.parseInt(Jahr.getSelectedItem().toString()), Calendar.JULY, Tag.getSelectedIndex(), 0, 0, 0 );
			break;
		}
		case 8: {
			geboren.set( Integer.parseInt(Jahr.getSelectedItem().toString()), Calendar.AUGUST, Tag.getSelectedIndex(), 0, 0, 0 );
			break;
		}
		case 9: {
			geboren.set( Integer.parseInt(Jahr.getSelectedItem().toString()), Calendar.SEPTEMBER, Tag.getSelectedIndex(), 0, 0, 0 );
			break;
		}
		case 10: {
			geboren.set( Integer.parseInt(Jahr.getSelectedItem().toString()), Calendar.OCTOBER, Tag.getSelectedIndex(), 0, 0, 0 );
			break;
		}
		case 11: {
			geboren.set( Integer.parseInt(Jahr.getSelectedItem().toString()), Calendar.NOVEMBER, Tag.getSelectedIndex(), 0, 0, 0 );
		break;
		}
		default: {
			geboren.set( Integer.parseInt(Jahr.getSelectedItem().toString()), Calendar.DECEMBER, Tag.getSelectedIndex(), 0, 0, 0 );
			break;
		}
	}
		long time = heute.getTime().getTime() - geboren.getTime().getTime(); //Zeitdifferenz in Millisekunden berechnen
		tage = Math.round( (double)time / (24. * 60.*60.*1000.) ); //Differenz in Tage umwandeln
		alter = Math.round( (tage*faktor) / 365D); //Alter in Menschenjahren berechnen
		return alter;
	}
	
	/**
	 * Macht aus einem String ein Objekt für die Comboboxes 
	 * @return Objekt, zum Einfügen in die Combobox
	 */
	private Object makeObj(final String item)  {
	     return new Object() { public String toString() { return item; } };
	}
	
	/**
	 * This method initializes Los		
	 * @return javax.swing.JButton	
	 */
	private JButton getLos() {
		if (Los == null) {
			Los = new JButton();
			Los.setBounds(new Rectangle(528, 139, 136, 42));
			Los.setText("Sag's mir!");
			Los.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("Rechnung startet!");
					System.out.println("Das wurde eingegeben:");
					System.out.println(Tag.getSelectedIndex() + ". " + Monat.getSelectedItem() + ", " + Jahr.getSelectedItem());
					System.out.println("Tier: " + Tier.getSelectedItem());
					System.out.println("Rasse: " + Rasse.getSelectedItem());
					if (Tier.getSelectedItem().toString().equals("---")){
						System.out.println("Sie haben kein Tier ausgewählt.");
					}
					else{
						if (Rasse.getSelectedItem().toString().equals("---")){
							System.out.println("Sie haben keine Rasse ausgewählt.");
						}
						else{
							if (korrektesDatum()){ //ist ein Datum ausgewählt
								System.out.println("Datum korreckt");
								faktor = auswerten(Tier.getSelectedItem()); //Faktor zur Berechnung festlegen
								System.out.println(faktor);
								System.out.println("Berechnung starten...");
								for(int i = 5; i <= 100; i = i+5){
									fortschritt.setValue(i);
									fortschritt.paint(fortschritt.getGraphics());
									try {
										Thread.sleep(100); //so ein Ladebalken sieht halt cool aus
									} catch (InterruptedException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
								alterDesTieres = ausrechnen(faktor); //Alter berechnen
								String jahr_jahre = "Jahre"; //wenn mehr als ein Jahr alt
								if (((int) alterDesTieres) == 0){
									jahr_jahre = "Jahr"; //wenn nur ein Jahr alt
								}
								JOptionPane.showMessageDialog(jContentPane, "Ihr " + Tier.getSelectedItem() + " ist: " + alterDesTieres + " " + jahr_jahre + " alt.", "fertig", -1);
								System.out.println("Ihr " + Tier.getSelectedItem() + " ist: " + alterDesTieres + " " + jahr_jahre + " alt.");
							}
						}
					}
				}
			});
		}
		return Los;
	}

	/**
	 * This method initializes Tier	
	 * 	
	 * @return javax.swing.JComboBox	 
	 */
	private JComboBox getTier() throws IOException {
		if (Tier == null) {
			Tier = new JComboBox();
			Tier.addItem(makeObj("---"));
			try{
				File tierdatei = new File("data/tiere.dat");
				FileReader fr = new FileReader(tierdatei);
				BufferedReader einlesen = new BufferedReader(fr);
				String zeile = " ";
				String tier = "";
				while (zeile != null){
					try {
						zeile = einlesen.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try{
						for (int i = 0; i < zeile.length();i++){
							if (zeile.charAt(i) == ','){
								break;
							}
							else{
								tier = tier + zeile.charAt(i);
							}
						}
					}catch(NullPointerException e2){
						//nix machen
					}
					Tier.addItem(makeObj(tier));
					tier = "";
				}
			}catch (FileNotFoundException e){
				System.out.println("tiere.dat nicht gefunden!");
			}
			Tier.removeItemAt(Tier.getItemCount()-1);
			Tier.setBounds(new Rectangle(503, 34, 202, 40));
			Tier.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
						Rasse.setEnabled(true);
						LRasse.setEnabled(true);
						System.out.println("Rasse Dialog aktivert");
						rasseleeren();
						rassefuellen(Tier.getSelectedItem());
				}
			});
		}
		return Tier;
	}

	/**
	 * Diese Funktion leert die Auswahl der Rassen.
	 */
	private void rasseleeren(){
		Rasse.removeAllItems(); //erst alles raus zur Initialisierung
		System.out.println("Rasse Dialog geleert");
	}
	
	/**
	 * Diese Funktion fügt die verschiedenen Rassen in der Combobox hinzu.
	 * @param Objekt
	 */
	private void rassefuellen(Object Objekt){
		System.out.println("Rassedialog füllen");
		Rasse.addItem(makeObj("---"));
		try{
			String Pfad = "data" + File.separator + "rassen" + File.separator + Objekt.toString() + "_rassen.dat";
			File rassedatei = new File(Pfad); //variable Pfadangabe damit es generell funktioniert
			System.out.println(Pfad);
			FileReader fr = new FileReader(rassedatei);
			BufferedReader einlesen = new BufferedReader(fr);
			String zeile = " ";
			String tier = "";
			while (zeile != null){
				/**try {
					zeile = einlesen.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Rasse.addItem(makeObj(zeile));
				**/
				try {
					zeile = einlesen.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try{
					for (int i = 0; i < zeile.length();i++){
						if (zeile.charAt(i) == ','){
							break;
						}
						else{
							tier = tier + zeile.charAt(i);
						}
					}
				}catch(NullPointerException e2){
					//nix machen
				}
				Rasse.addItem(makeObj(tier));
				tier = "";
			}
		}catch (FileNotFoundException e){
			System.out.println(Objekt + ".dat nicht gefunden!");
		}
		Rasse.removeItemAt(Rasse.getItemCount()-1);
	}
	
	/**
	 * This method initializes Monat	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getMonat() {
		if (Monat == null) {
			Monat = new JComboBox(); //Jeder Monat wird eingefügt
			Monat.addItem(makeObj("---"));
			Monat.addItem(makeObj("Januar"));
			Monat.addItem(makeObj("Februar"));
			Monat.addItem(makeObj("März"));
			Monat.addItem(makeObj("April"));
			Monat.addItem(makeObj("Mai"));
			Monat.addItem(makeObj("Juni"));
			Monat.addItem(makeObj("Juli"));
			Monat.addItem(makeObj("August"));
			Monat.addItem(makeObj("September"));
			Monat.addItem(makeObj("Oktober"));
			Monat.addItem(makeObj("November"));
			Monat.addItem(makeObj("Dezember"));
			Monat.setBounds(new Rectangle(89, 67, 149, 27));
		}
		return Monat;
	}

	/**
	 * This method initializes Jahr	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJahr() {
		Calendar jahrangabe = Calendar.getInstance();
		jahrangabe.setTime(new Date()); //Heute
		if (Jahr == null) {
			Jahr = new JComboBox();
			Jahr.addItem(makeObj("---"));
			for (int i = 1990;i<=jahrangabe.get(Calendar.YEAR);i++){ //Wir fügen alle möglichen Geburtstjahre ein
				Jahr.addItem(makeObj("" + i));
			}
			Jahr.setBounds(new Rectangle(243, 66, 109, 26));
		}
		return Jahr;
	}

	/**
	 * This method initializes Tag	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getTag() {
		if (Tag == null) {
			Tag = new JComboBox();
			Tag.addItem(makeObj("---"));
			for (int i = 1; i< 32; i++){ //alle Tage hinzufügen
				Tag.addItem(makeObj("" + i));
			}
			Tag.setBounds(new Rectangle(19, 67, 74, 27));
		}
		return Tag;
	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.setBackground(Color.lightGray);
			jJMenuBar.add(getConfig());
			jJMenuBar.add(getInfos());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes config	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getConfig() {
		if (config == null) {
			config = new JMenu();
			config.setText("Konfiguration");
			config.setBackground(Color.lightGray);
			config.setText("Konfiguration");
		}
		return config;
	}

	/**
	 * This method initializes infos	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getInfos() {
		if (infos == null) {
			infos = new JMenu();
			infos.setText("Info");
			infos.setBackground(Color.lightGray);
			infos.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					System.out.println("about aufgerufen"); // TODO Auto-generated Event stub actionPerformed()
					String about = "";
					try{
						File aboutdatei = new File("data" + File.separator + "about");
						FileReader fr = new FileReader(aboutdatei);
						BufferedReader einlesen = new BufferedReader(fr);
						String zeile = " ";
						while (zeile != null){
							try {
								zeile = einlesen.readLine();
							} catch (IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							about = about + zeile + "\n";
						}
					}catch (FileNotFoundException e3){
						System.out.println("data/about nicht gefunden!");
					}
					about = about.substring(0, about.length()-6);
					JOptionPane.showMessageDialog(jContentPane, about, "About/Info", 1);
				}
			});
			infos.setText("Info");
		}
		return infos;
	}

	/**
	 * This method initializes fortschritt	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	private JProgressBar getFortschritt() {
		if (fortschritt == null) {
			fortschritt = new JProgressBar();
			fortschritt.setBounds(new Rectangle(10, 147, 439, 27));
		}
		return fortschritt;
	}

	/**
	 * This method initializes Rasse	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getRasse() {
		if (Rasse == null) {
			Rasse = new JComboBox();
			Rasse.setBounds(new Rectangle(511, 88, 191, 35));
			Rasse.setEnabled(false);
		}
		return Rasse;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				tieralter thisClass = new tieralter();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}

	/**
	 * This is the default constructor
	 */
	public tieralter() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(751, 227);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(getJJMenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle("Tieralter");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			LRasse = new JLabel();
			LRasse.setBounds(new Rectangle(437, 90, 45, 32));
			LRasse.setEnabled(false);
			LRasse.setText("Rasse:");
			LTier = new JLabel();
			LTier.setBounds(new Rectangle(438, 38, 50, 24));
			LTier.setText("Tierart:");
			LJahr = new JLabel();
			LJahr.setBounds(new Rectangle(273, 42, 35, 21));
			LJahr.setText("Jahr:");
			LMonat = new JLabel();
			LMonat.setBounds(new Rectangle(140, 43, 47, 23));
			LMonat.setText("Monat:");
			LTag = new JLabel();
			LTag.setBounds(new Rectangle(32, 43, 33, 20));
			LTag.setText("Tag:");
			Erklärung = new JLabel();
			Erklärung.setBounds(new Rectangle(141, 2, 518, 30));
			Erklärung.setForeground(Color.red);
			Erklärung.setText("Dieses Programm rechnet das Alter ihres Tieres im Vergleich zum Menschen aus.");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(Erklärung, null);
			jContentPane.add(getLos(), null);
			try {
				jContentPane.add(getTier(), null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jContentPane.add(getMonat(), null);
			jContentPane.add(getJahr(), null);
			jContentPane.add(LTag, null);
			jContentPane.add(getTag(), null);
			jContentPane.add(LMonat, null);
			jContentPane.add(LJahr, null);
			jContentPane.add(LTier, null);
			jContentPane.add(getFortschritt(), null);
			jContentPane.add(LRasse, null);
			jContentPane.add(getRasse(), null);
		}
		return jContentPane;
	}

}  //  @jve:decl-index=0:visual-constraint="14,-4"
