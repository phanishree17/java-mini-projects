package comlibgui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Date;

public class LibraryGUI extends JFrame {

    // File to store data
    private static final String FILE_NAME = "library.txt";

    public LibraryGUI() {
        setTitle("Library Management System");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton adminBtn = new JButton("Admin");
        JButton userBtn = new JButton("User");
        JButton exitBtn = new JButton("Exit");

        panel.add(adminBtn);
        panel.add(userBtn);
        panel.add(exitBtn);

        add(panel);

        // Button Actions
        adminBtn.addActionListener(e -> adminMenu());
        userBtn.addActionListener(e -> userMenu());
        exitBtn.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    // ================= ADMIN MENU ===================
    private void adminMenu() {
        String[] options = {"Add Book", "View Records", "Back"};
        int choice = JOptionPane.showOptionDialog(this, "Admin Menu", "Admin",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0 -> addBook();
            case 1 -> viewRecords();
            default -> {}
        }
    }

    // ================= USER MENU ===================
    private void userMenu() {
        String[] options = {"Issue Book", "Return Book", "Back"};
        int choice = JOptionPane.showOptionDialog(this, "User Menu", "User",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0 -> issueBook();
            case 1 -> returnBook();
            default -> {}
        }
    }

    // ================= ADD BOOK ===================
    private void addBook() {
        String id = JOptionPane.showInputDialog(this, "Enter Book ID:");
        String name = JOptionPane.showInputDialog(this, "Enter Book Name:");

        if (id != null && name != null && !id.isEmpty() && !name.isEmpty()) {
            try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
                fw.write("BOOK," + id + "," + name + ",Available\n");
                JOptionPane.showMessageDialog(this, "Book Added Successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error Saving Book!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Fields cannot be empty!");
        }
    }

    // ================= ISSUE BOOK ===================
    private void issueBook() {
        String bid = JOptionPane.showInputDialog(this, "Enter Book ID:");
        String pname = JOptionPane.showInputDialog(this, "Enter Your Name:");
        String phone = JOptionPane.showInputDialog(this, "Enter Phone Number:");
        Date d = new Date();

        if (bid != null && pname != null && phone != null &&
            !bid.isEmpty() && !pname.isEmpty() && !phone.isEmpty()) {
            try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
                fw.write("ISSUE," + bid + "," + pname + "," + phone + "," + d + "\n");
                JOptionPane.showMessageDialog(this, "Book Issued Successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error Issuing Book!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Fields cannot be empty!");
        }
    }

    // ================= RETURN BOOK ===================
    private void returnBook() {
        String bid = JOptionPane.showInputDialog(this, "Enter Book ID:");
        Date d = new Date();

        if (bid != null && !bid.isEmpty()) {
            try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
                fw.write("RETURN," + bid + "," + d + "\n");
                JOptionPane.showMessageDialog(this, "Book Returned Successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error Returning Book!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Book ID cannot be empty!");
        }
    }

    // ================= VIEW RECORDS ===================
    private void viewRecords() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            JScrollPane scroll = new JScrollPane(textArea);
            scroll.setPreferredSize(new Dimension(350, 200));
            JOptionPane.showMessageDialog(this, scroll, "All Records", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "No Records Found!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LibraryGUI::new);
    }
}
