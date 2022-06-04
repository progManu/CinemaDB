import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        StoreProcedureMangager spm = StoreProcedureMangager.getInstance();
        ArrayList<Object[]> arr = null;

        System.out.println("1) Film per età spettatore");
        System.out.println("2) Punti di un tesserato");
        System.out.println("3) Persone che hanno fatto un denterminato turno in un determinato mese");
        System.out.println("4) Posti liberi per proiezione");
        System.out.println("5) Guadagni anno");
        System.out.println("Inserire codice operazione: ");
        int op = Integer.parseInt(s.nextLine());

        switch (op){
            case 1: {
                System.out.println("Inserire età spettatore: ");
                int age = s.nextInt();
                arr = spm.getFilmUnderAge(age);
                break;
            }
            case 2: {
                System.out.println("Inserire codice tesserato: ");
                int codTesserato = s.nextInt();
                arr = spm.getPuntiTesserato(codTesserato);
                break;
            }
            case 3: {
                System.out.println("Inserire fascia oraria, (Mattina, Pomeriggio, Sera): ");
                String fasciaOraria = s.nextLine();
                System.out.println("Inserire mese: ");
                int mese = Integer.parseInt(s.nextLine());
                System.out.println("Inserire anno: ");
                int anno = Integer.parseInt(s.nextLine());
                arr = spm.getTurnistiMeseETurno(fasciaOraria, mese, anno);
                break;
            }
            case 4: {
                System.out.println("Inserire ora: ");
                String time = s.nextLine();
                Time tm = Time.valueOf(time);
                System.out.println("Inserire data film: ");
                Date data = Date.valueOf(s.nextLine());
                System.out.println("Inserire sala: ");
                int sala = Integer.parseInt(s.nextLine());
                System.out.println("Inserire codice film: ");
                int film = Integer.parseInt(s.nextLine());
                arr = spm.getPostiLiberi(sala, film, tm, data);
                break;
            }
            case 5: {
                System.out.println("Inserire anno: ");
                int anno = Integer.parseInt(s.nextLine());
                arr = spm.getYearRevenue(anno);
                break;
            }
        }

        if(arr != null) {
            Object[] colsName = Arrays.copyOf(arr.get(0), arr.get(0).length);
            arr.remove(0);
            Object[][] rawData = new Object[arr.size()][];

            int i = 0;
            for (Object[] o : arr) {
                rawData[i++] = Arrays.copyOf(o, o.length);
            }

            RenderGUI.renderTable(rawData, colsName);
        }
        spm.closeConn();
    }
}
