import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TimeTrackerGUI extends JFrame {
    private JPanel mainPanel;
    private JTextField taskTimeInput;
    private JButton addTimeButton;
    private JLabel taskTime;
    private JLabel totalTime;
    private JLabel enterTaskTime;

    //Constructor
    public TimeTrackerGUI(String title) {
        super(title);

        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        List<Double> timeList = new ArrayList<>();

        addTimeButton.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {

                //grabs text from taskTimeInput
                //Converts to Double
                timeList.add((Double.parseDouble(taskTimeInput.getText())));

                taskTime.setText(timeList.get(timeList.size() - 1) + " Mins Added");

                totalTime.setText((getTotalTime(timeList)) + " Total Hours");

                }
        });
    }

    public String getTotalTime(List<Double> timeList)
    {
        double sum = 0;

        for(int i = 0; i < timeList.size(); i++)
        {
            sum += timeList.get(i);
        }
        sum = sum / 60;
        return String.valueOf(sum);

    }

    public static void main(String[] args)
    {
        JFrame frame = new TimeTrackerGUI("My Time Tracker");

        frame.setSize(300,200);

        frame.setVisible(true);


    }
}
