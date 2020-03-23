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
        ResultSet rs3;
        
        
        
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
                        System.out.println("Which would you like to insert:\n1.WritingGroup\n2.Publishers\n3.Books\n4.Exit");
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
                            
                            sql2 = "SELECT COUNT(*) FROM WRITINGGROUP WHERE GROUPNAME = ?";
                            pstmt = conn.prepareStatement(sql2);
                            pstmt.setString(1, groupName);
                            rs2 = pstmt.executeQuery();
                            while(rs2.next()){
                                System.out.println(rs2.getInt(1));
                                if (rs2.getInt(1) == 1){
                                    System.out.println("Writing Group Exists."
                                            + "Please try again");
                                }
                                else{
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
                            }
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
                            
                            System.out.println("Enter old Publisher Name: ");
                            String oldPublisher = in.nextLine();
                            
                            sql4 = "SELECT COUNT(*) FROM BOOKS WHERE BOOKPUBLISHERNAME = ?";
                            pstmt = conn.prepareStatement(sql4);
                            pstmt.setString(1, oldPublisher);
                            rs2 = pstmt.executeQuery();
                            while(rs2.next()){
                                if(rs2.getInt(1) == 0){
                                    System.out.println("Old Publisher has not created any books");
                                }
                                else{
                                    sql2 = "SELECT COUNT(*) FROM PUBLISHERS WHERE PUBLISHERNAME = ?";
                                    pstmt = conn.prepareStatement(sql2);
                                    pstmt.setString(1, publisherName);
                                    rs3 = pstmt.executeQuery();
                                    while(rs3.next()){
                                        if (rs3.getInt(1) == 1){
                                        System.out.println("Publisher name already exists please try again");
                                        }
                                        else{
                                            sql2 = "INSERT INTO PUBLISHERS " + "(PUBLISHERNAME, PUBLISHERADDRESS, "
                                            + "PUBLISHERPHONE, PUBLISHEREMAIL)" + " values (?, ?, ?, ?)";
                            
                                            pstmt = conn.prepareStatement(sql2);

                                            pstmt.setString(1, publisherName);
                                            pstmt.setString(2, publisherAddress);
                                            pstmt.setString(3, phoneNumber);
                                            pstmt.setString(4, publisherEmail);

                                            pstmt.executeUpdate();
                                            pstmt.close();

                                            sql4 = "UPDATE BOOKS SET BOOKPUBLISHERNAME = ? WHERE BOOKPUBLISHERNAME = ?";
                                            pstmt = conn.prepareStatement(sql4);
                                            pstmt.setString(1, publisherName);
                                            pstmt.setString(2, oldPublisher);

                                            pstmt.executeUpdate();
                                            pstmt.close();
                                            //rs3.close();
                                            //rs2.close();
                                        }
                                    }    
                                }
                            }
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
                            
                            System.out.print("Enter the number of pages: ");
                            String numberPages = in.nextLine();
                            
                            sql4 = "SELECT COUNT(*) FROM BOOKS WHERE WRITINGGROUPNAME = ? AND BOOKTITLE = ?";
                            pstmt = conn.prepareCall(sql4);
                            pstmt.setString(1, writingGroupName);
                            pstmt.setString(2, bookTitle);
                            rs2 = pstmt.executeQuery();
                            while(rs2.next()){
                                if(rs2.getInt(1) == 1){
                                    System.out.println("The Writing Group Already created this book. Please try again.");
                                }
                                else{
                                    
                                    sql4 = "SELECT COUNT(*) FROM WRITINGGROUP WHERE GROUPNAME = ?";
                                    pstmt = conn.prepareStatement(sql4);
                                    pstmt.setString(1, writingGroupName);
                                    rs2 = pstmt.executeQuery();
                                    while(rs2.next()){
                                        if(rs2.getInt(1) != 1){
                                            System.out.println("WritingGroup does not exist");
                                        }
                                        else{
                                            sql2 = "SELECT COUNT(*) FROM PUBLISHERS WHERE PUBLISHERNAME = ?";
                                            pstmt = conn.prepareStatement(sql2);
                                            pstmt.setString(1, bookPublisherName);
                                            rs3 = pstmt.executeQuery();
                                            while(rs3.next()){
                                                if(rs3.getInt(1) != 1){
                                                    System.out.println("Publisher does not exist");
                                                }
                                                else{
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
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }while(!(choice0.equals("4")));
                    
                }
                else if(choice.equals("2")){
                    System.out.println("Enter the name of the Book to remove: ");
                    String removeBook = in.nextLine();
                    System.out.println("Enter the name of the WritingGroup: ");
                    String nameGroup = in.nextLine();
                    String test = "SELECT COUNT(*) FROM BOOKS WHERE WRITINGGROUPNAME = ? AND BOOKTITLE = ?";
                    pstmt = conn.prepareStatement(test);
                    pstmt.setString(1, nameGroup);
                    pstmt.setString(2, removeBook);
                    rs2 = pstmt.executeQuery();
                    while(rs2.next()){
                        System.out.println(rs2.getInt(1));
                        if (rs2.getInt(1) != 1){
                            System.out.println(removeBook + " does not exist "
                                    + "Please try again");
                        }
                        else{
                            sql4 = "DELETE FROM BOOKS WHERE WRITINGGROUPNAME = ? AND BOOKTITLE = ?";
                            pstmt = conn.prepareStatement(sql4);
                            pstmt.setString(1, nameGroup);
                            pstmt.setString(2, removeBook);
                            pstmt.executeUpdate();
                        }
                    }
                    pstmt.close();
                    
                }
                else if(choice.equals("3")){
                    String choice1;
                    do{
                        System.out.println("Which would like to list:\n1.List Writing Groups\n2.List Publishers"
                        + "\n3.List Book Titles\n4.List Information about a book"
                                + "\n5.List Information about a publisher"
                                + "\n6.List Information about a WritingGroup"
                                + "\n7.Exit");
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
                            sql4 = "SELECT BOOKTITLE FROM BOOKS";
                            pstmt = conn.prepareStatement(sql4);
                            rs2 = pstmt.executeQuery();
                            System.out.println("Book Titles: ");
                            while(rs2.next()){
                                String getBookTitle = rs2.getString("BOOKTITLE");
                                System.out.println(dispNull(getBookTitle));
                            }
                            rs2.close();
                            pstmt.close();
                        }
                        
                        else if(choice1.equals("4")){
                            System.out.print("Enter the name of the book you would like to find: ");
                            String getThatBook = in.nextLine();
                            System.out.print("Enter the name of the group you would like to find: ");
                            String getThatGroupName = in.nextLine();
                            sql4 = "SELECT BOOKTITLE, WRITINGGROUPNAME, BOOKPUBLISHERNAME, YEARPUBLISHED, NUMBERPAGES FROM BOOKS WHERE BOOKTITLE = ? AND WRITINGGROUPNAME = ?";                            
                            pstmt = conn.prepareStatement(sql4);
                            pstmt.setString(1, getThatBook);
                            pstmt.setString(2, getThatGroupName);
                            rs2 = pstmt.executeQuery();
                            while(rs2.next()){
                                String getGroupName = rs2.getString("WRITINGGROUPNAME");
                                String getBookTitle = rs2.getString("BOOKTITLE");
                                String getPublisherName = rs2.getString("BOOKPUBLISHERNAME");
                                String getYearPublished = rs2.getString("YEARPUBLISHED");
                                String getNumberPages = rs2.getString("NUMBERPAGES");
                                System.out.print(dispNull(getGroupName) + ", ");
                                System.out.print(dispNull(getBookTitle) + ", ");
                                System.out.print(dispNull(getPublisherName) + ", ");
                                System.out.print(dispNull(getYearPublished) + ", ");
                                System.out.print(dispNull(getNumberPages) + " ");
                            }
                            rs2.close();
                            pstmt.close();
                        }
                        else if(choice1.equals("5")){
                            System.out.print("Enter the name of the publisher you would like to find: ");
                            String getThatPublisher = in.nextLine();
                            sql4 = "SELECT PUBLISHERNAME, PUBLISHERADDRESS, PUBLISHERPHONE, PUBLISHEREMAIL FROM PUBLISHERS WHERE PUBLISHERNAME = ?";                        
                            pstmt = conn.prepareStatement(sql4);
                            pstmt.setString(1, getThatPublisher);
                            rs2 = pstmt.executeQuery();
                            while(rs2.next()){
                                String getPublisherName = rs2.getString("PUBLISHERNAME");
                                String getPublisherAddress = rs2.getString("PUBLISHERADDRESS");
                                String getPhone = rs2.getString("PUBLISHERPHONE");
                                String getPublisherEmail = rs2.getString("PUBLISHEREMAIL");
                                System.out.print(dispNull(getPublisherName) + ", ");
                                System.out.print(dispNull(getPublisherAddress) + ", ");
                                System.out.print(dispNull(getPhone) + ", ");
                                System.out.print(dispNull(getPublisherEmail) + ", ");
                            }
                            rs2.close();
                            pstmt.close();
                        }
                        else if(choice1.equals("6")){
                            System.out.print("Enter the name of the WritingGroup you would like to find: ");
                            String getThatGroup = in.nextLine();
                            sql4 = "SELECT GROUPNAME, HEADWRITER, YEARFORMED, SUBJECT FROM WRITINGGROUP WHERE GROUPNAME = ?";                        
                            pstmt = conn.prepareStatement(sql4);
                            pstmt.setString(1, getThatGroup);
                            rs2 = pstmt.executeQuery();
                            while(rs2.next()){
                                String getGroupName = rs2.getString("GROUPNAME");
                                String getWriter = rs2.getString("HEADWRITER");
                                String getYear = rs2.getString("YEARFORMED");
                                String getSubject = rs2.getString("SUBJECT");
                                System.out.print(dispNull(getGroupName) + ", ");
                                System.out.print(dispNull(getWriter) + ", ");
                                System.out.print(dispNull(getYear) + ", ");
                                System.out.print(dispNull(getSubject) + ", ");
                            }
                            rs2.close();
                            pstmt.close();
                        }
     
                    }while(!(choice1.equals("7")));
                }
                
            }while((!(choice.equals("4"))));
            
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