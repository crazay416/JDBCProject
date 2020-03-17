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
        String sql4;
        String sql2;
        ResultSet rs2;
        
        
        
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);
            
            
            
            String choice;
            do {
                System.out.println("\nChoices:\n1.Insert\n2.Delete\n3.Display\n4.Exit");
                choice = in.nextLine();
                if (choice.equals("1")){
                    String choice0;
                    do{
                        System.out.println("\n1.WritingGroup\n2.Publishers\n3.Books\n4.Exit");
                        choice0 = in.nextLine();
                        if (choice0.equals("1")){
                            // My STEP: Insert Values to the table
                            System.out.print("\nEnter your groupName: ");
                            String groupName = in.nextLine();

                            System.out.println("\nEnter the HeadWriter: ");
                            String headWriter = in.nextLine();

                            System.out.println("\nEnter the YearFormed: ");
                            String yearFormed = in.nextLine();

                            System.out.println("\nEnter the Subject: ");
                            String subject = in.nextLine();
                            sql2 = "INSERT INTO WRITINGGROUP " + "(GROUPNAME, HEADWRITER, "
                                    + "YEARFORMED, SUBJECT)" + " values (?, ?, ?, ?)";

                            pstmt = conn.prepareStatement(sql2);

                            pstmt.setString(1, groupName);
                            pstmt.setString(2, headWriter);
                            pstmt.setString(3, yearFormed);
                            pstmt.setString(4, subject);

                            pstmt.executeUpdate();
                            pstmt.close();
                        }
                        
                        else if(choice0.equals("2")){
                            System.out.print("Enter the PublisherName: ");
                            String publisherName = in.nextLine();
                            
                            System.out.print("Enter the Publisher Address: ");
                            String publisherAddress = in.nextLine();
                            
                            System.out.print("Enter the publisher Phone number: ");
                            String phoneNumber = in.nextLine();
                            
                            System.out.print("Enther the email of the publisher: ");
                            String publisherEmail = in.nextLine();
                            
                            sql2 = "INSERT INTO PUBLISHERS " + "(PUBLISHERNAME, PUBLISHERADDRESS, "
                                    + "PUBLISHERPHONE, PUBLISHEREMAIL)" + " values (?, ?, ?, ?)";
                            
                            pstmt = conn.prepareStatement(sql2);

                            pstmt.setString(1, publisherName);
                            pstmt.setString(2, publisherAddress);
                            pstmt.setString(3, phoneNumber);
                            pstmt.setString(4, publisherEmail);

                            pstmt.executeUpdate();
                            pstmt.close();
                        }
                        
                        else if(choice0.equals("3")){
                            System.out.print("Enter the GroupName: ");
                            String writingGroupName = in.nextLine();
                            
                            System.out.print("Enter the book title:");
                            String bookTitle = in.nextLine();
                            
                            System.out.print("Enter Publisher Name: ");
                            String bookPublisherName = in.nextLine();
                            
                            System.out.print("Enter the year the book was published: ");
                            String yearPublished = in.nextLine();
                            
                            System.out.print("Enter the numbewr of pages: ");
                            String numberPages = in.nextLine();
                            
                            sql2 = "INSERT INTO BOOKS " + "(WRITINGGROUPNAME, BOOKTITLE, "
                                    + "BOOKPUBLISHERNAME, YEARPUBLISHED, NUMBERPAGES)" + " values (?, ?, ?, ?, ?)";
                            
                            pstmt = conn.prepareStatement(sql2);

                            pstmt.setString(1, writingGroupName);
                            pstmt.setString(2, bookTitle);
                            pstmt.setString(3, bookPublisherName);
                            pstmt.setString(4, yearPublished);
                            pstmt.setString(5, numberPages);
                            
                            pstmt.executeUpdate();
                            pstmt.close();
                            
                        }
                        
                        
                    }while(!(choice0.equals("4")));
                    
                }
                else if(choice.equals("2")){
                    System.out.println("Enter the name of the Group to remove: ");
                    String removeGroup = in.nextLine();
                    String sql3 = "DELETE FROM WRITINGGROUP WHERE GROUPNAME = ?";
                    //ResultSet rs2 = stmt2.executeQuery(sql3);
                    //System.out.println(rs2.next());
                    pstmt = conn.prepareStatement(sql3);
                    pstmt.setString(1, removeGroup);
                    pstmt.executeUpdate();
                    pstmt.close();
                    
                }
                else if(choice.equals("3")){
                    String choice1;
                    do{
                        System.out.println("\n1.List Writing Groups\n2.List Publiushers"
                        + "\n3.List Information about a Book\n4.Exit");
                        choice1 = in.nextLine();
                        if(choice1.equals("1")){
                            sql4 = "SELECT GROUPNAME FROM WRITINGGROUP";
                            pstmt = conn.prepareStatement(sql4);
                            rs2 = pstmt.executeQuery();
                            System.out.println("GroupName");
                            while(rs2.next()){
                                String getTheName = rs2.getString("GROUPNAME");
                                System.out.println(dispNull(getTheName));
                            }
                            rs2.close();
                            pstmt.close();
                            }
                        
                        else if(choice1.equals("2")){
                            sql4 = "SELECT PUBLISHERNAME FROM PUBLISHERS";
                            pstmt = conn.prepareStatement(sql4);
                            rs2 = pstmt.executeQuery();
                            System.out.println("Publisher Names");
                            while(rs2.next()){
                                String getThePublisher = rs2.getString("PUBLISHERNAME");
                                System.out.println(dispNull(getThePublisher));
                            }
                            rs2.close();
                            pstmt.close();
                        }
                        
                        else if(choice1.equals("3")){
                            
                        
                        }
     
                    }while(!(choice1.equals("4")));
                }
                
            }while((!(choice.equals("4"))));
            
            
            
            
            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            
            sql = "SELECT * FROM WRITINGGROUP";
            //sql = "SELECT au_id, au_fname, au_lname, phone FROM Authors";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println(rs.next());
            

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


