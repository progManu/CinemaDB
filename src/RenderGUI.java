import javax.swing.*;

public class RenderGUI {

    private static JFrame frame;
    private static JTable table;

    public static void renderTable(Object[][] rawData, Object[] colNames)
    {
        frame = new JFrame("RenderGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        table = new JTable(rawData, colNames);
        table.setBounds(30,40,400,600);
        JScrollPane scrollPane =new JScrollPane(table);
        frame.add(scrollPane);
        frame.setSize(500, 600);
        frame.setVisible(true);
    }
}
