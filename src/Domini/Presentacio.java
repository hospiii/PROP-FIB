package Domini;

import java.awt.FlowLayout;
import javax.swing.*;

class JScrollPaneExample {
    private static final long serialVersionUID = 1L;

    private static void crearMarcVistaPrincipal() {

        final JFrame marc = new JFrame("GESTOR DE TEXTOS");

        marc.setSize(500, 500);
        marc.setVisible(true);
        marc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        marc.getContentPane().setLayout(new FlowLayout());
        crearTextAreaIScrollPanel(marc);
    }

    private static void crearTextAreaIScrollPanel(JFrame marc){
        JTextArea textArea = new JTextArea(20, 20);
        JScrollPane barra = new JScrollPane(textArea);

        barra.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        barra.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        marc.getContentPane().add(barra);
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                crearMarcVistaPrincipal();
            }
        });
    }
}