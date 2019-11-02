import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class Test {
    private static DefaultTableModel tableModel;
    private static int columnNumber = 1;

    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                tableModel = new DefaultTableModel(new Object[] { "Registers" }, 2);
                JTable table = new JTable(tableModel);
                JFrame frame = new JFrame("Table Column Add");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setBounds(100, 100, 600, 300);
                frame.add(new JScrollPane(table));
                frame.setVisible(true);
            }
        });

        for (;;) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    tableModel.addColumn("Values" + columnNumber++);
                }
            });
            Thread.sleep(2000);
        }
    }
}