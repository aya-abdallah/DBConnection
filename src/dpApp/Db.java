package dpApp;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aya
 */
public class Db {

    private static String tableName = "PERSON";

    private static Connection con = null;
    private static Statement stmt = null;
    private static int id = 0;

    public static boolean connectDb() {

        try {
            try {
                try {
                    Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
                } catch (InstantiationException ex) {
                    Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    con = DriverManager.getConnection("jdbc:derby://localhost:1527/DB", "aya", "aya");
                } catch (SQLException ex) {
                    Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);

                }

                return true;
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

//    public Db() {
//
//        try {
//            Statement stmt = con.createStatement();
//            String queryString = new String("select * from person");
//            ResultSet rs = stmt.executeQuery(queryString);
//
//            while (rs.next()) {
//                System.out.println(rs.getString(2));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public static void insertPerson(Person p) {
        try {

            stmt = con.createStatement();
            stmt.execute("insert into PERSON values (" + p.id + ",'"
                    + p.fName + "','" + p.mName + "','" + p.lName + "','" + p.email + "','" + p.phone + "')");

//             String queryString = new String("select * from person");
//            ResultSet rs = stmt.executeQuery(queryString);
            stmt.close();
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }

    public static Vector getAll() {
        try {
            stmt = con.createStatement();
            ResultSet results = stmt.executeQuery("select * from " + tableName);
            ResultSetMetaData rsmd = results.getMetaData();
            int numberCols = rsmd.getColumnCount();
            Vector<Person> allRow = new Vector();
            for (int i = 1; i <= numberCols; i++) {
                //print Column Names
                System.out.print(rsmd.getColumnLabel(i) + "\t\t");
            }

            System.out.println("\n------------------------------------------------------------");
            while (results.next()) {
                Person p = new Person();
                p.id = results.getInt(1);
                p.fName = results.getString(2);
                p.mName = results.getString(3);
                p.lName = results.getString(4);
                p.email = results.getString(5);
                p.phone = results.getString(6);
                allRow.add(p);
                System.out.println(p.id + "\t\t" + p.fName + "\t\t" + p.mName + "\t\t" + p.lName + "\t\t" + p.email + "\t\t" + p.phone);
            }

            results.close();
            stmt.close();
            return allRow;
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
        return null;
    }

    public static void deleteRow(int id) {
        try {
            PreparedStatement st = con.prepareStatement("DELETE FROM PERSON WHERE id = " + id);
            st.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static Vector getFirstRow() {
        try {
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String selectquery = "select * from PERSON";
            ResultSet rs = stmt.executeQuery(selectquery);
            Vector firstRow = new Vector();

            rs.first();
            firstRow.add(rs.getInt(1));
            firstRow.add(rs.getString(2));
            firstRow.add(rs.getString(3));
            firstRow.add(rs.getString(4));
            firstRow.add(rs.getString(5));
            firstRow.add(rs.getString(6));

            for (int i = 0; i < firstRow.size(); i++) {
                System.out.print(firstRow.get(i) + " ");
            }

            return firstRow;
//            System.out.print(" id :" + rs.getInt(1) + " ");
//            System.out.print(" fName :" + rs.getString(2) + " ");
//            System.out.print(" mName :" + rs.getString(3) + " ");
//            System.out.print(" lName :" + rs.getString(4) + " ");
//            System.out.print(" email :" + rs.getString(5) + " ");
//            System.out.print(" phone :" + rs.getString(6) + " ");
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public static Vector getLastRow() {
        try {
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String selectquery = "select * from PERSON";
            ResultSet rs = stmt.executeQuery(selectquery);

            Vector lastRow = new Vector();

            rs.last();
            lastRow.add(rs.getInt(1));
            lastRow.add(rs.getString(2));
            lastRow.add(rs.getString(3));
            lastRow.add(rs.getString(4));
            lastRow.add(rs.getString(5));
            lastRow.add(rs.getString(6));

            for (int i = 0; i < lastRow.size(); i++) {
                System.out.print(lastRow.get(i) + " ");
            }

            return lastRow;

//            System.out.print(" id :" + rs.getInt(1) + " ");
//            System.out.print(" fName :" + rs.getString(2) + " ");
//            System.out.print(" mName :" + rs.getString(3) + " ");
//            System.out.print(" lName :" + rs.getString(4) + " ");
//            System.out.print(" email :" + rs.getString(5) + " ");
//            System.out.print(" phone :" + rs.getString(6) + " ");
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
    static int counter = 0;

    public static Vector getPerviousRow() {
        try {
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String selectquery = "select * from PERSON ";
            ResultSet rs = stmt.executeQuery(selectquery);
            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
            }

            if (counter > 0) {
                counter--;
                Vector<Person> allRow = getAll();
                System.out.println(allRow.get(counter));
                Vector perviousRow = new Vector();

                perviousRow.add(allRow.get(counter).id);
                perviousRow.add(allRow.get(counter).fName);
                perviousRow.add(allRow.get(counter).mName);
                perviousRow.add(allRow.get(counter).lName);
                perviousRow.add(allRow.get(counter).email);
                perviousRow.add(allRow.get(counter).phone);

                for (int i = 0; i < perviousRow.size(); i++) {
                    System.out.print(perviousRow.get(i) + " ");
                }

                return perviousRow;
            } else {
                counter=counter;
//                System.out.println("you in first");
                return null;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Vector getNextRow() {
        try {

            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String selectquery = "select * from PERSON";
            ResultSet rs = stmt.executeQuery(selectquery);
            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
            }
           
            if (counter < rowCount-1) {
                 counter++;
                Vector<Person> allRow = getAll();
                Vector nextRow = new Vector();
                nextRow.add(allRow.get(counter).id);
                nextRow.add(allRow.get(counter).fName);
                nextRow.add(allRow.get(counter).mName);
                nextRow.add(allRow.get(counter).lName);
                nextRow.add(allRow.get(counter).email);
                nextRow.add(allRow.get(counter).phone);

                for (int i = 0; i < nextRow.size(); i++) {
                    System.out.print(nextRow.get(i) + " ");
                }

                return nextRow;
            } else {
                counter=counter;
                //System.out.println("this the last row .");
                return null;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void updateRow(Person p) {
        try {
            PreparedStatement st = con.prepareStatement("update PERSON set fName =?,mName=?,lName=?,email=?,phone=? where id = ? ");

            st.setString(1, p.fName);
            st.setString(2, p.mName);
            st.setString(3, p.lName);
            st.setString(4, p.email);
            st.setString(5, p.phone);
            st.setInt(6, p.id);

            st.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


}
