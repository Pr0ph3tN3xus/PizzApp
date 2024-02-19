    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.util.Objects;

    public class Main extends JFrame {
        private JComboBox<String> mainDropdown;
        private JButton addButton;
        private JPanel contentPane;
        private int dropdownCount = 0;
        private double totalPrice = 0.00;
        private JLabel priceLabel;

        public Main() {
            setTitle("PizzApp");
            setSize(400, 350); // Increased height to accommodate price total
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            contentPane = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;

            ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("Pizzer.png")));
            JLabel imageLabel = new JLabel(imageIcon);
            contentPane.add(imageLabel, gbc);

            // Price Label
            priceLabel = new JLabel("Total Price: $" + totalPrice);
            gbc.gridy++;
            contentPane.add(priceLabel, gbc);

            String[] options = {"Small", "Medium", "Large", "Super"};
            gbc.gridy++;
            mainDropdown = new JComboBox<>(options);
            contentPane.add(mainDropdown, gbc);
            mainDropdown.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (mainDropdown.getSelectedItem() == "Small") {
                        totalPrice = 5;
                    } if (mainDropdown.getSelectedItem() == "Medium") {
                        totalPrice = 10;
                    } if (mainDropdown.getSelectedItem() == "Large") {
                        totalPrice = 15;
                    } if (mainDropdown.getSelectedItem() == "Super") {
                        totalPrice = 20;
                    }
                    priceLabel.setText("Total Price: $" + totalPrice);
                    removeAdditionalDropdowns();
                }
            });


            addButton = new JButton("Add Toppings"); addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (dropdownCount <= 2) {
                        addDropdown(gbc);
                        dropdownCount++;
                    } else {
                        addButton.setEnabled(false);
                    }
                }
            });

            gbc.gridy++;
            contentPane.add(addButton, gbc);

            setContentPane(contentPane);
            setVisible(true);
        }

        private void addDropdown(GridBagConstraints gbc) {
            JComboBox<String> dropdown = new JComboBox<>(new String[]{"Sausage", "Pepperoni", "Extra Cheese"});
            dropdown.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedTopping = (String) dropdown.getSelectedItem();
                    if ("Extra Cheese".equals(selectedTopping)) {
                        totalPrice -= .50; // No additional cost for extra cheese
                    } else {
                        totalPrice += 0.50;
                        if (dropdownCount == 3) {
                            totalPrice -= .50;
                            totalPrice += .25;
                        }
                        if (totalPrice % 5 == 1 && dropdownCount == 1) {
                            totalPrice -= .50;
                        }
                        if (totalPrice % 5 == 1.5 && dropdownCount == 2) {
                            totalPrice -= .50;
                        }
                        if (totalPrice % 5 == 1.75 && dropdownCount == 3) {
                            totalPrice -= .50;
                        }
                    }
                    priceLabel.setText("Total Price: $" + totalPrice);
                }
            });
            gbc.gridy++;
            gbc.weighty = 0.1;
            contentPane.add(dropdown, gbc);
            contentPane.revalidate();
            contentPane.repaint();
        }
        private void removeAdditionalDropdowns() {
            Component[] components = contentPane.getComponents();
            for (Component component : components) {
                if (component instanceof JComboBox && !component.equals(mainDropdown)) {
                    contentPane.remove(component);
                }
            }
            contentPane.revalidate();
            contentPane.repaint();
            dropdownCount = 0;
            addButton.setEnabled(true);
        }


        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new Main();
                }
            });
        }
    }
