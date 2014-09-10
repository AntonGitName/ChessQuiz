package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;

public class MainFrame extends JFrame {
    
    private static final int DEFAULT_BORDERSIZE = 8;
    
    public MainFrame() {
        getContentPane().setLayout(new MigLayout("", "[90%,grow][200pt]", "[100%,grow]"));
        
        final ChessPanel chessPanel = new ChessPanel(DEFAULT_BORDERSIZE);
        getContentPane().add(chessPanel, "cell 0 0,grow");
        
        JPanel controlPanel = new JPanel();
        getContentPane().add(controlPanel, "cell 1 0,grow");
        controlPanel.setLayout(new MigLayout("", "[163px]", "[30px][30px][]"));
        
        JPanel panel = new JPanel();
        controlPanel.add(panel, "cell 0 0,grow");
        panel.setLayout(new GridLayout(0, 1, 0, 0));
        
        final JLabel statusLabel = new JLabel("New label");
        controlPanel.add(statusLabel, "cell 0 2");
        statusLabel.setText("Status: Not solved yet.");
        
        JPanel panel_1 = new JPanel();
        panel.add(panel_1);
        panel_1.setLayout(new GridLayout(0, 2, 0, 0));
        
        JLabel sizeTextLabel = new JLabel("Set board size:");
        panel_1.add(sizeTextLabel);
        
        
        
        final JSpinner boardSizeSpinner = new JSpinner();
        panel_1.add(boardSizeSpinner);
        boardSizeSpinner.setModel(new SpinnerNumberModel(8, 4, 200, 1));
        
        JPanel panel_3 = new JPanel();
        panel.add(panel_3);
        panel_3.setLayout(new GridLayout(0, 2, 0, 0));
        
        JLabel lblSetStartTemperature = new JLabel("Set start Temperature:");
        panel_3.add(lblSetStartTemperature);
        
        final JSpinner temperatureSpinner = new JSpinner();
        temperatureSpinner.setModel(new SpinnerNumberModel(30, 20, 100, 1));
        panel_3.add(temperatureSpinner);
        
        JPanel panel_4 = new JPanel();
        panel.add(panel_4);
        panel_4.setLayout(new GridLayout(0, 2, 0, 0));
        
        JLabel label = new JLabel("Set decrease factor:");
        panel_4.add(label);
        
        final JSpinner alphaSpinner = new JSpinner();
        alphaSpinner.setModel(new SpinnerNumberModel(0.93, 0.8, 0.99, 0.01));
        panel_4.add(alphaSpinner);
        
        JPanel panel_2 = new JPanel();
        panel.add(panel_2);
        panel_2.setLayout(new GridLayout(0, 2, 0, 0));
        
        JLabel lblSetDecreaseFactor = new JLabel("Set iterations:");
        panel_2.add(lblSetDecreaseFactor);
        
        final JSpinner iterSpinner = new JSpinner();
        iterSpinner.setModel(new SpinnerNumberModel(50, 20, 200, 1));
        panel_2.add(iterSpinner);

        JButton solveButton = new JButton("Solve!");
        solveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                statusLabel.setText("Status: Not solved yet.");
                int bs = (int) boardSizeSpinner.getValue();
                double T = (int) temperatureSpinner.getValue();
                double alpha = (double) alphaSpinner.getValue();
                int iter = (int) iterSpinner.getValue();
                chessPanel.setBoardParameters(bs, T, alpha, iter);
                int iterations = chessPanel.solveQuiz();
                if (chessPanel.isSolutionCorrect()) {
                    statusLabel.setText(String.format("<html>Status: Solved.<br>Solution is correct.<br>Total iterations: %d.</html>", iterations));
                } else {
                    statusLabel.setText("<html>Status: Solved.<br>Solution is incorrect.<br>Consider changing parameters.</html>");
                }
            }
        });
        controlPanel.add(solveButton, "cell 0 1,grow");
        
        final ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent arg0) {
                statusLabel.setText("Status: Not solved yet.");
                int bs = (int) boardSizeSpinner.getValue();
                double T = (int) temperatureSpinner.getValue();
                double alpha = (double) alphaSpinner.getValue();
                int iter = (int) iterSpinner.getValue();
                chessPanel.setBoardParameters(bs, T, alpha, iter);
            }
        };
        
        boardSizeSpinner.addChangeListener(changeListener);
        temperatureSpinner.addChangeListener(changeListener);
        alphaSpinner.addChangeListener(changeListener);
        iterSpinner.addChangeListener(changeListener);
        
        
        setSize(800,  600);
    }

    private static final long serialVersionUID = 8456560429229699542L;

    public void createGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    
}
