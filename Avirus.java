/*

For testing / education ONLY

*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Set;
import javax.swing.*;
import java.awt.*;  
import java.awt.event.*;  

class Avirus {
    int count = 0;
    int size = 0;
    int occur = 0;
    HashMap virusHashMap = new HashMap();

    static final String SIGNATURES_FILE = "signatures.txt";
    static final String FILE_TO_CHECK = "virus.exe";

    void showDialog(Avirus fr) throws Exception {
        JDialog dialog;
        JFrame frame = new JFrame();  
        JButton closeBtn = new JButton ("Close"); 
        JButton startBtn = new JButton("Start Scan"); 

        dialog = new JDialog(frame, "Avirus 0.1", true);  
        dialog.setLayout(new FlowLayout(FlowLayout.RIGHT));  
        
        closeBtn.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent event) {  
                dialog.setVisible(false);  
            }  
        });  
        
        startBtn.addActionListener (new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                try {
                    fr.readSignatures(SIGNATURES_FILE);
                    fr.searchForVirus(FILE_TO_CHECK);
                } catch(Exception err) {
                    System.out.println("Startup Error: " + err);
                }
            }  
        });

        dialog.add(closeBtn);
        dialog.add(startBtn);

        dialog.add(new JLabel("Click 'Start Scan' to Start the process and 'Close' to exit."));  
        dialog.setSize(600,300);    
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);  
    }

    @SuppressWarnings("unchecked")
    void readSignatures(String filename) throws Exception {
        try {
            FileReader in = new FileReader(filename);
            BufferedReader br = new BufferedReader(in);
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                virusHashMap.put(line.substring(0, line.indexOf("/")), line.substring(line.indexOf("/") + 1, line.length()));
                ++i;
            }
            size = i;
            br.close();
        } catch(Exception err) {
            System.out.println("Error: " + err);
        }
    }

    @SuppressWarnings("unchecked")
    void searchForVirus(String file) throws Exception {
        FileReader in = new FileReader(file);
        BufferedReader br = new BufferedReader(in);
        String line;
        while ((line = br.readLine()) != null) {
            Set keys = virusHashMap.keySet();
            count++;
            boolean containsKey = keys.contains(String.valueOf(count));
            if (containsKey) {
                String virus = (String)virusHashMap.get(String.valueOf(count));
                if (line.indexOf(virus) > -1) {
                    occur++;
                }
            }
        }
        br.close();
        if (size == occur) {
            JOptionPane.showMessageDialog(null, "Infected File", "Virus Detected ", JOptionPane.ERROR_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null, "Clean File", "No Virus Found ", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public static void main(String []nix) {
        try {
            Avirus fr = new Avirus();
            fr.showDialog(fr);
        } catch (Exception err) {
            System.out.println("Error: " + err);
        }
    }
}