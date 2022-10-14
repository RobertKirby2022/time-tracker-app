import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class TaskTimeForm extends JDialog {
    private JTextField tfTaskMins;
    private JTextField tfTaskID;
    private JButton btnAddTask;
    private JButton btnCancel;
    private JPanel tasksPanel;

    public TaskTimeForm(JFrame parent)
    {
        super(parent);
        setTitle("Timely Tasks");
        setContentPane(tasksPanel);
        setMinimumSize(new Dimension(280, 300));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnAddTask.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                addTask();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    private void addTask()
    {
        //Take TaskTime and TaskID from Form
        Double TaskMins = Double.parseDouble(tfTaskMins.getText());
        int TaskID = Integer.parseInt(tfTaskID.getText());

        if(TaskMins <= 0 || TaskID <= 0)
        {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        task = addTaskToDatabase(TaskMins, TaskID);
        if(task != null)
        {
            dispose();
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Failed to add task time",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public Task task;
    private Task addTaskToDatabase(double TaskMins, int TaskID)
    {
        Task task = null;
        final String DB_URL = "jdbc:mysql://localhost/yourDBNameHere";
        final String USERNAME = "yourUsername";
        final String PASSWORD = "yourPassword";

        try
        {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO tasks (TaskMins, TaskID) " +
                    "VALUES (?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setDouble(1, TaskMins);
            preparedStatement.setInt(2, TaskID);

            //Insert row in table
            int addedRows = preparedStatement.executeUpdate();
            if(addedRows > 0)
            {
                task = new Task();
                task.TaskMins = TaskMins;
                task.TaskID = TaskID;
            }

            stmt.close();
            conn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return task;
    }

    public static void main(String[] args)
    {
        TaskTimeForm myForm = new TaskTimeForm(null);
        Task task = myForm.task;
        if(task != null) {
            System.out.println("Successful task add for id: " + task.TaskID);
        }
        else {
            System.out.println("Task add cancelled");
        }
    }
}