import java.sql.*;
import java.util.ArrayList;

public class StoreProcedureMangager {
    private static StoreProcedureMangager instance = null;

    private static StoreProcedureDetails filmUnderAge;
    private static StoreProcedureDetails postiLiberi;
    private static StoreProcedureDetails puntiTesserato;
    private static StoreProcedureDetails turnistiMeseETurno;

    private static StoreProcedureDetails yearRevenue;

    private static Connection conn;

    private StoreProcedureMangager()
    {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cinema_db", "root", "egoat2W7p$");
        }catch (SQLException ex)
        {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        filmUnderAge = new StoreProcedureDetails("{call getFilmUnderCertainAge(?)}",
                new String[]{
                        "age"
                });
        puntiTesserato = new StoreProcedureDetails("{call getPointsTesserato(?)}",
                new String[]{
                        "codiceTesserato"
                });
        turnistiMeseETurno = new StoreProcedureDetails("{call getDipendentiDaTurniEMese(?, ?, ?)}",
                new String[]{
                        "fasciaOrariaIN",
                        "meseIN",
                        "annoIN"
                });
        postiLiberi = new StoreProcedureDetails("{call getPostiLiberi(?, ?, ?, ?)}",
                new String[]{
                        "salaIN",
                        "filmIN",
                        "giornoIN",
                        "oraIN"
                });
        yearRevenue = new StoreProcedureDetails("{call calcYearRevenue(?)}",
                new String[]{
                        "anno"
                });
    }

    public static StoreProcedureMangager getInstance() {
        if (instance == null) {
            instance = new StoreProcedureMangager();
        }
        return instance;
    }

    public ArrayList<Object[]> getFilmUnderAge(int age)
    {
        ArrayList<Object[]> arr = new ArrayList<Object[]>();
        try
        {
            CallableStatement cs = conn.prepareCall(filmUnderAge.getCallSP());
            cs.setInt(filmUnderAge.getSpParameterNames()[0], age);
            ResultSet rs = cs.executeQuery();
            ResultSetMetaData rsMetaData = rs.getMetaData();

            int col_size = rsMetaData.getColumnCount();
            arr.add(new Object[col_size]);

            for(int i = 0; i < col_size; i++)
                arr.get(0)[i] = rsMetaData.getColumnName(i + 1);


            while(rs.next()) {
                arr.add(new Object[]{rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)});
            }
        }catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return arr;
    }

    public ArrayList<Object[]> getPuntiTesserato(int codiceTesserato)
    {
        ArrayList<Object[]> arr = new ArrayList<Object[]>();
        try
        {
            CallableStatement cs = conn.prepareCall(puntiTesserato.getCallSP());
            cs.setInt(puntiTesserato.getSpParameterNames()[0], codiceTesserato);
            ResultSet rs = cs.executeQuery();
            ResultSetMetaData rsMetaData = rs.getMetaData();

            int col_size = rsMetaData.getColumnCount();
            arr.add(new Object[col_size]);

            for(int i = 0; i < col_size; i++)
                arr.get(0)[i] = rsMetaData.getColumnName(i + 1);


            while(rs.next()) {
                arr.add(new Object[]{
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5)
                });
            }
        }catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return arr;
    }

    public ArrayList<Object[]> getTurnistiMeseETurno(String fasciaOraria, int mese, int anno)
    {
        ArrayList<Object[]> arr = new ArrayList<Object[]>();
        try
        {
            CallableStatement cs = conn.prepareCall(turnistiMeseETurno.getCallSP());
            cs.setString(turnistiMeseETurno.getSpParameterNames()[0], fasciaOraria);
            cs.setInt(turnistiMeseETurno.getSpParameterNames()[1], mese);
            cs.setInt(turnistiMeseETurno.getSpParameterNames()[2], anno);

            ResultSet rs = cs.executeQuery();
            ResultSetMetaData rsMetaData = rs.getMetaData();

            int col_size = rsMetaData.getColumnCount();
            arr.add(new Object[col_size]);

            for(int i = 0; i < col_size; i++)
                arr.get(0)[i] = rsMetaData.getColumnName(i + 1);


            while(rs.next()) {
                arr.add(new Object[]{
                        rs.getString(1),
                        rs.getString(2)
                });
            }
        }catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return arr;
    }

    public ArrayList<Object[]> getPostiLiberi(int sala, int film, Time ora, Date giorno)
    {
        ArrayList<Object[]> arr = new ArrayList<Object[]>();
        try
        {
            CallableStatement cs = conn.prepareCall(postiLiberi.getCallSP());
            cs.setInt(postiLiberi.getSpParameterNames()[0], sala);
            cs.setInt(postiLiberi.getSpParameterNames()[1], film);
            cs.setDate(postiLiberi.getSpParameterNames()[2], giorno);
            cs.setTime(postiLiberi.getSpParameterNames()[3], ora);
            ResultSet rs = cs.executeQuery();
            ResultSetMetaData rsMetaData = rs.getMetaData();

            int col_size = rsMetaData.getColumnCount();
            arr.add(new Object[col_size]);

            for(int i = 0; i < col_size; i++)
                arr.get(0)[i] = rsMetaData.getColumnName(i + 1);


            while(rs.next()) {
                arr.add(new Object[]{
                        rs.getString(1),
                        rs.getInt(2)
                });
            }
        }catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return arr;
    }

    public ArrayList<Object[]> getYearRevenue(int anno)
    {
        ArrayList<Object[]> arr = new ArrayList<Object[]>();
        try
        {
            CallableStatement cs = conn.prepareCall(yearRevenue.getCallSP());
            cs.setInt(yearRevenue.getSpParameterNames()[0], anno);
            ResultSet rs = cs.executeQuery();
            ResultSetMetaData rsMetaData = rs.getMetaData();

            int col_size = rsMetaData.getColumnCount();
            arr.add(new Object[col_size]);

            for(int i = 0; i < col_size; i++)
                arr.get(0)[i] = rsMetaData.getColumnName(i + 1);


            while(rs.next()) {
                arr.add(new Object[]{
                        rs.getFloat(1)
                });
            }
        }catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return arr;
    }

    public void closeConn() {
        try {
            conn.close();
        }catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
