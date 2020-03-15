package cecs.pkg323project;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author Mimi Opkins with some tweaking from Dave Brown
 */
public class CECS323JavaTermProject {
    //  Database credentials
    static String DBNAME;
    //This is the specification for the printout that I'm doing:
    //each % denotes the start of a new field.
    //The - denotes left justification.
    //The number indicates how wide to make the field.
    //The "s" denotes that it's a string.  All of our output in this test are 
    //strings, but that won't always be the case.
    static final String displayFormat="%-5s%-15s%-15s%-15s\n";
// JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/";
//            + "testdb;user=";
/**
 * Takes the input string and outputs "N/A" if the string is empty or null.
 * @param input The string to be mapped.
 * @return  Either the input string or "N/A" as appropriate.
 */
    public static String dispNull (String input) {
        //because of short circuiting, if it's null, it never checks the length.
        if (input == null || input.length() == 0)
            return "N/A";
        else
            return input;
    }
    
    public static void main(String[] args) {
        //Prompt the user for the database name, and the credentials.
        //If your database has no credentials, you can update this code to 
        //remove that from the connection string.
        Scanner in = new Scanner(System.in);
        System.out.print("Name of the database (not the user account): ");
        DBNAME = in.nextLine();
        System.out.print("Database user name: ");
        //Constructing the database URL connection string
        DB_URL = DB_URL + DBNAME;
        Connection conn = null; //initialize the connection
        Statement stmt = null;  //initialize the statement that we're using
        PreparedStatement pstmt = null;
        Statement stmt2 = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);
            
            
            System.out.println("\nChoices:\n1.Insert\n2.Delete\n3.Exit");
            String choice = in.nextLine();
            while (!(choice.equals("3"))){
                if (choice.equals("1")){
                    // My STEP: Insert Values to the table
                    System.out.print("\nEnter your groupName: ");
                    String groupName = in.nextLine();

                    System.out.println("\nEnter the HeadWriter: ");
                    String headWriter = in.nextLine();

                    System.out.println("\nEnter the YearFormed: ");
                    String yearFormed = in.nextLine();

                    System.out.println("\nEnter the Subject: ");
                    String subject = in.nextLine();
                    String sql2;
                    sql2 = "INSERT INTO WRITINGGROUP " + "(GROUPNAME, HEADWRITER, "
                            + "YEARFORMED, SUBJECT)" + " values (?, ?, ?, ?)";

                    pstmt = conn.prepareStatement(sql2);

                    pstmt.setString(1, groupName);
                    pstmt.setString(2, headWriter);
                    pstmt.setString(3, yearFormed);
                    pstmt.setString(4, subject);

                    pstmt.executeUpdate();
                }
                else if(choice.equals("2")){
                    String removeGroup = in.nextLine();
                    String sql3 = "DELETE FROM WRITINGGROUP WHERE GROUPNAME = ?";
                    pstmt = conn.prepareStatement(sql3);
                    pstmt.setString(1, removeGroup);
                    pstmt.executeUpdate();
                }
            }
            
            /*
            // My STEP: Insert Values to the table
            System.out.print("\nEnter your groupName: ");
            String groupName = in.nextLine();
            
            System.out.println("\nEnter the HeadWriter: ");
            String headWriter = in.nextLine();
            
            System.out.println("\nEnter the YearFormed: ");
            String yearFormed = in.nextLine();
            
            System.out.println("\nEnter the Subject: ");
            String subject = in.nextLine();
            String sql2;
            sql2 = "INSERT INTO WRITINGGROUP " + "(GROUPNAME, HEADWRITER, "
                    + "YEARFORMED, SUBJECT)" + " values (?, ?, ?, ?)";
            
            pstmt = conn.prepareStatement(sql2);
            
            pstmt.setString(1, groupName);
            pstmt.setString(2, headWriter);
            pstmt.setString(3, yearFormed);
            pstmt.setString(4, subject);
            
            pstmt.executeUpdate();
            */
            
            /*
            String q;
            System.out.println("Would you like to remove a Group?");
            q = in.nextLine();
            
            if (q.equals("yes")){
                String removeGroup = in.nextLine();
                String sql3 = "DELETE FROM WRITINGGROUP WHERE GROUPNAME = ?";
                pstmt = conn.prepareStatement(sql3);
                pstmt.setString(1, removeGroup);
                pstmt.executeUpdate();
            }
            */
            
            
            
            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            
            sql = "SELECT * FROM WRITINGGROUP";
            //sql = "SELECT au_id, au_fname, au_lname, phone FROM Authors";
            ResultSet rs = stmt.executeQuery(sql);
            

            //STEP 5: Extract data from result set
            System.out.printf(displayFormat, "ID", "First Name", "Last Name", "Phone #");
            while (rs.next()) {
                //Retrieve by column name
                String getGroupName = rs.getString("GROUPNAME");
                String getHeadWriter = rs.getString("HEADWRITER");
                String getYearFormed = rs.getString("YEARFORMED");
                String getSubject = rs.getString("SUBJECT");

                //Display values
                System.out.printf(displayFormat, 
                        dispNull(getGroupName), dispNull(getHeadWriter), dispNull(getYearFormed), dispNull(getSubject));
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main
}//end FirstExample}

