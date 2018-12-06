import java.util.Scanner;
import java.sql.*;

public class DockerConnectMySQL {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://10.0.10.3:3306/bazaDanychJava";
    static final String USER = "achepov";
    static final String PASS = "root";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Boolean connect = false;
            do {
                try {
                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    connect = true;
                } catch (Exception e) {
                    System.out.println("Łączenie z serwerem bazy danych");
                    Thread.sleep(1000);
                }
            } while (!connect);
			System.out.println("Połączono z serwerem bazy danych");
            stmt = conn.createStatement();
            String sql;
            sql = "DROP TABLE IF EXISTS Employees";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE Employees (ID int, first_name varchar(25), last_name  varchar(25), email  varchar(50));";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO Persons(ID, first_name, last_name, email) VALUES (1,'qwerty', 'qwerty', 'qwerty'),(2, 'asdfgh', 'asdfgh', 'asdfgh'),(3, 'zxcvbn', 'zxcvbn', 'zxcvbn');";
            stmt.executeUpdate(sql);
            Scanner menu = new Scanner(System.in);
            String i;
            do {
                System.out.println("");
                System.out.println("menu od sql");
                System.out.println("wpisz tylko liczbę");
                System.out.println("wybierz opcje:");
                System.out.println("(1) dodanie encji");
                System.out.println("(2) wyświetlenie");
                System.out.println("(3) wyjdz");
                i = (String) menu.next();
                switch (i) {
                    case "1": {
                        Scanner insert = new Scanner(System.in);
                        sql = "SELECT ID FROM Employees ORDER BY ID DESC LIMIT 1;";
                        ResultSet rs = stmt.executeQuery(sql);
                        int e = 0;
                        if (rs.next()) {
                            e = rs.getInt("ID");
                        }
                        rs.close();
                        e++;
                        sql = "INSERT INTO Employees (ID, first_name, last_name, email) VALUES (" + e + ",'";
                        System.out.println("Podaj Imię");
                        sql += insert.nextLine();
                        sql += "', '";
                        System.out.println("Podaj Nazwisko::");
                        sql += insert.nextLine();
                        sql += "', '";
                        System.out.println("Podaj Email:");
                        sql += insert.nextLine();
                        sql += "');";
                        stmt.executeUpdate(sql);
						System.out.println("Dodano encje");
                        break;
                    }
                    case "2": {
                        sql = "SELECT ID, first_name, last_name, email FROM Employees";
                        ResultSet rs = stmt.executeQuery(sql);
                        System.out.printf("|%5s|%15s|%15s|%15s|\n", "ID: ", "Imię: ", "Nazwisko: ", "Email: ");
                        while (rs.next()) {
                            int id = rs.getInt("ID");
                            String first = rs.getString("first_name");
                            String last = rs.getString("last_name");
                            String email = rs.getString("email");
                            System.out.printf("|%4d |%14s |%14s |%14s |\n", id, first, last, email);
                        }
                        rs.close();
                        break;
                    }
                    case "3": {
                        i = "3";
                        break;
                    }
                    default: {
						System.out.println("");
                        System.out.println("Wybierz odpowiednią opcje!");
                        break;
                    }
                }
            } while (i != "3");
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
