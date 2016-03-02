/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kennzeichenwriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Brian
 */

//TODO Leerzeichen entfernen 
//TODO Umlaute einfügen 

/* Liest die Daten aus einer Textdatei mittel Trennzeichen*/

public class KennzeichenWriter {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/kennzeichen";
        String user = "root";
        String passwort = "";
        Connection con;
        Statement stmt = null;

        System.out.print("i m alive");

        try {
            con = DriverManager.getConnection(url, user, passwort);
            stmt = con.createStatement();
            System.out.println("Created DB Connection....");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(new File("C:\\Users\\Brian\\Schulprojekte\\Finken\\Kfz-Kennzeichen\\liste.txt")));
            String line = null;
            while ((line = br.readLine()) != null) {
                // Ganze Zeile:

                // System.out.println(line);               
                String[] id = line.split("(-)", 2);
                String identifier = id[0];
                String city = null;

                if (id.length == 2) {
                    String[] cities = id[1].split("(/)|(\\()|(\\))");
                    city = cities[0];
                }
                System.out.println(id[0]);
                String[] parts = line.split("(/)|(\\()|(\\))");

                String sql = null;
                if (parts.length == 1) {
                    sql = "INSERT INTO bundesland  (identifier, Stadt)VALUES (\'" + identifier + "\' ,\' " + city + "\')";
                    System.out.println(sql);
                } else if (parts.length == 2) {
                    sql = "INSERT INTO bundesland  (identifier, Stadt,Bundesland)VALUES (\'" + identifier + "\' ,\' " + city + "\' ,\' " + parts[1] + "\')";
                } else if (parts.length == 3) {
                    sql = "INSERT INTO bundesland  (identifier, Stadt,Landkreis,Bundesland)VALUES (\'" + identifier + "\' ,\' " + city + "\' ,\' " + parts[1] + "\' ,\' " + parts[2] + "\')";

                }
                if (sql != null) {
                    stmt.executeUpdate(sql);
                }
                System.out.println("Inserting records into the table...");

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(KennzeichenWriter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }//end FirstExample
        }
    }
}
