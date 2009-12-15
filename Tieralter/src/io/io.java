package io;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

	/**
	 * @author Oliver Türpe
	 * Mit dieser einfachen Klasse kann man die einfachsten Datentypen per Eingabe behandeln.
	 */

public class io {
	static final int anzahl = 1;
	/**
	 * Diese Methode liest Zahlen vom Typ float ein.
	 * @return eine Float Zahl
	 */
	public static float readFloat(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		float f = 0; //falls er nie bei try-catch rein geht
		System.out.print("Zahl angeben: ");
		try{
			for (int i = 0; i<anzahl; i++){
				try{
					f = Float.parseFloat(br.readLine());
				}catch(NumberFormatException nf){
					System.out.print("Kein gültiger Wert.");
					i--;
				}
			}
		}catch(IOException e){
			throw new RuntimeException("Das war keine Float Zahl!");
		}
		return f;
	}
	
	/**
	 * Diese Methode liest Zahlen vom Typ double ein.
	 * @return eine Double Zahl
	 */
	public static double readDouble(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		double d = 0D; //falls er nie bei try-catch rein geht
		System.out.print("Zahl angeben: ");
		try{
			for (int i = 0; i<anzahl; i++){
				try{
					d = Double.parseDouble(br.readLine());
				}catch(NumberFormatException nf){
					System.out.print("Kein gültiger Wert.");
					i--;
				}
			}
		}catch(IOException e){
			throw new RuntimeException("Das war keine Zahl vom Typ Double!");
		}
		return d;
	}
	
	
	/**
	 * Diese Methode liest Zahlen vom Typ Integer ein.
	 * @return eine Integer Zahl
	 */
	public static int readInt(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int zahl = 0; //falls er nie bei try-catch rein geht
		System.out.print("Zahl angeben: ");
		try{
			for (int i = 0; i<anzahl; i++){
				try{
					zahl = Integer.parseInt(br.readLine());
				}catch(NumberFormatException nf){
					System.out.print("Kein gültiger Wert.");
					i--;
				}
			}
		}catch(IOException e){
			throw new RuntimeException("Das war keine Integer Zahl!");
		}
		return zahl;	
	}
		
	/**
	 * Diese Methode liest Zeichenketten vom Typ String ein.
	 * @return eine Zeichenkette
	 */
	public static String readString(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = ""; //falls er nie bei try-catch rein geht
		System.out.print("String: ");
		try{
			str = br.readLine();
		}catch (IOException e){
			throw new RuntimeException("böser Fehler");
		}
		return str;
	}
	
	/**
	 * Diese Methode liest ein Char Symbol ein an.
	 * @return ein Zeichen vom Typ char
	 */
	public static char readChar(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		char ch = ' '; //falls er nie bei try-catch rein geht
		System.out.print("Char: ");
		try{
			for (int i = 0; i<anzahl; i++){
				try{
					ch = br.readLine().charAt(0);
				}catch(NumberFormatException nf){
					System.out.print("Kein gültiger Wert.");
					i--;
				}
			}
		}catch(IOException e){
			throw new RuntimeException("Das war kein Char Symbol!");
		}
		return ch;
	}
	
	/**
	 * Diese Methode bietet eine einfache ja/nein Auswahl an.
	 * @return True oder False
	 */
	public static boolean auswahl(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = ""; //falls er nie bei try-catch rein geht
		System.out.print("ja/nein? ");
		try{
			str = br.readLine();
		}catch (IOException e){
			throw new RuntimeException("böser Fehler");
		}
		if(str.equalsIgnoreCase("ja")){
			return true;
		}
		return false;
	}
}