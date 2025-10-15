/* 
 *  Defines the 8x6 main button area
 *  This includes everything except the "ans" and "clear buttons" (and the side panel)
 *  Those buttons are not defined here since they are contained in the top panel
 *  
 *  NOTE: Buttons within the grid can be easily replaced just by editing `labels`, but the
 *        behavior will have to be defined in ButtonLogic (and possibly in other classes).
 *  
 *        Outside of the grid, new buttons will have to be created in a new class, or in a
 *        new part of this class.
*/

package calculator.ui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ButtonPanel extends JPanel {

    public interface ButtonListener {
        void onButtonClick(String label);
    }

    private final Map<String, JButton> buttons = new HashMap<>();

    @SuppressWarnings("unused")
    private final ButtonListener listener;

    public ButtonPanel(ButtonListener listener) {
        this.listener = listener;
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        String[][] labels = {
            {"mean", "stdev", "stdevp", "sort", "π", "e"},
            {"sin⁻¹", "cos⁻¹", "tan⁻¹", "(hyp.)", "nPr", "nCr"},
            {"sin", "cos", "tan", "(rec.)", "(deg.)", "!"},
            {"(", ")", ",", "1/x", "|x|", "ln"},
            {"7", "8", "9", "÷", "^", "eˣ"},
            {"4", "5", "6", "×", "√", "log"},
            {"1", "2", "3", "-", "x²", "10ˣ"},
            {"0", ".", "ans", "+", "=", "="}
        };

        for (int row = 0; row < labels.length; row++) {
            for (int col = 0; col < labels[row].length; col++) {
                gbc.gridx = col;
                gbc.gridy = row;
                String label = labels[row][col];

                JButton button = new JButton(label);
                button.setFont(new Font("SansSerif", Font.BOLD, 18));

                Dimension fixed = new Dimension(100, 40);
                button.setPreferredSize(fixed);
                button.setMinimumSize(fixed);
                button.setMaximumSize(fixed);

                buttons.put(label, button);

                // button reports clicks
                button.addActionListener(e -> {
                    if (listener != null) listener.onButtonClick(button.getText());
                });

                // merge last two "=" buttons into one wide cell
                if (row == 7 && col == 4) {
                    gbc.gridwidth = 2;
                    add(button, gbc);
                    col++;
                    gbc.gridwidth = 1;
                } else {
                    add(button, gbc);
                }
            }
        }

        // label changes for hyp, rec, and deg
        buttons.get("(hyp.)").addActionListener(e -> toggleHyperbolic());
        buttons.get("(rec.)").addActionListener(e -> toggleReciprocal());
        buttons.get("(deg.)").addActionListener(e -> toggleDegree());
    }

    public boolean isHyperbolic = false;
    public boolean isReciprocal = false;
    public boolean isDegree = false;

    private void toggleHyperbolic() {
        isHyperbolic = !isHyperbolic;

        buttons.get("sin").setText(isHyperbolic ? "sinh" : "sin");
        buttons.get("cos").setText(isHyperbolic ? "cosh" : "cos");
        buttons.get("tan").setText(isHyperbolic ? "tanh" : "tan");
        buttons.get("sin⁻¹").setText(isHyperbolic ? "sinh⁻¹" : "sin⁻¹");
        buttons.get("cos⁻¹").setText(isHyperbolic ? "cosh⁻¹" : "cos⁻¹");
        buttons.get("tan⁻¹").setText(isHyperbolic ? "tanh⁻¹" : "tan⁻¹");
    }

    private void toggleReciprocal() {
        isReciprocal = !isReciprocal;

        buttons.get("sin").setText(isReciprocal ? "csc" : "sin");
        buttons.get("cos").setText(isReciprocal ? "sec" : "cos");
        buttons.get("tan").setText(isReciprocal ? "cot" : "tan");
        buttons.get("sin⁻¹").setText(isReciprocal ? "csc⁻¹" : "sin⁻¹");
        buttons.get("cos⁻¹").setText(isReciprocal ? "sec⁻¹" : "cos⁻¹");
        buttons.get("tan⁻¹").setText(isReciprocal ? "cot⁻¹" : "tan⁻¹");
    }

    private void toggleDegree() {
        isDegree = !isDegree;

        buttons.get("(deg.)").setText(isDegree ? "(rad.)" : "(deg.)");
    }

}