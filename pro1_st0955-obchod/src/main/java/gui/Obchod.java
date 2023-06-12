package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Sklad;
import model.Zbozi;
import serdes.GsonSerDes;

public class Obchod {
    private Sklad sklad;

    private JPanel hlavniPanel;
    private JPanel panelSkladu;
    private JTable tabulkaSkladu;

    private JMenuBar nabidka;

    public Obchod() {
        sklad = new Sklad();
        Docasne.naplSklad(sklad);

        vytvorKomponenty();
    }

    public JPanel getHlavniPanel() {
        return hlavniPanel;
    }

    public JMenuBar getNabidka() {
        return nabidka;
    }

    private void vytvorKomponenty() {
        vytvorNabidku();
        vytvorPanelSkladu();

        hlavniPanel = new JPanel();
        hlavniPanel.add(panelSkladu);
    }

    private void vytvorNabidku() {
        JMenuItem miUkoncit = new JMenuItem();
        miUkoncit.setText("Ukončit");
        miUkoncit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JMenuItem miOProgramu = new JMenuItem("O programu");
        miOProgramu.addActionListener((e) -> JOptionPane.showMessageDialog(
                hlavniPanel,
                "O programu Obchod\nSimulace jednoduchého e-shopu.",
                "O programu",
                JOptionPane.INFORMATION_MESSAGE
        ));

        JMenuItem miNactiJson = new JMenuItem("Načti JSON");
        miNactiJson.addActionListener((e) -> {
            try {
                JFileChooser dialog = new JFileChooser(".");
                if (dialog.showOpenDialog(panelSkladu) == JFileChooser.APPROVE_OPTION) {
                    String soubor = dialog.getSelectedFile().getPath();
                    sklad.nacti(new GsonSerDes(), soubor);
                }
            } catch (Exception exp) {
                JOptionPane.showMessageDialog(hlavniPanel,
                        "Při načítání do JSON formátu nastala: "
                                + exp.getLocalizedMessage(), "Chyba načítání",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        JMenuItem miUlozJson = new JMenuItem("Ulož JSON");
        miUlozJson.addActionListener((e) -> {
            try {
                JFileChooser dialog = new JFileChooser(".");
                if (dialog.showSaveDialog(panelSkladu) == JFileChooser.APPROVE_OPTION) {
                    String soubor = dialog.getSelectedFile().getPath();
                    sklad.uloz(new GsonSerDes(), soubor);
                }
            } catch (Exception exp) {
                JOptionPane.showMessageDialog(hlavniPanel,
                        "Při ukládání do JSON formátu nastala: "
                            + exp.getLocalizedMessage(), "Chyba ukládání",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        JMenu mnSoubor = new JMenu();
        mnSoubor.setText("Soubor");
        mnSoubor.add(miNactiJson);
        mnSoubor.add(miUlozJson);
        mnSoubor.add(miUkoncit);

        JMenu mnNapoveda = new JMenu("Nápověda");
        mnNapoveda.add(miOProgramu);

        nabidka = new JMenuBar();
        nabidka.add(mnSoubor);
        nabidka.add(mnNapoveda);
    }

    private void vytvorPanelSkladu() {
        tabulkaSkladu = new JTable();
        tabulkaSkladu.setModel(sklad);
        tabulkaSkladu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabulkaSkladu.setFillsViewportHeight(true);

        JScrollPane spTabulkaSkladu = new JScrollPane(tabulkaSkladu);

        JButton btPridej = new JButton("Přidat");
        btPridej.addActionListener((e) -> {
            sklad.pridejZbozi(new Zbozi("-", 0.0f, 0));
        });

        JButton btSmaz = new JButton("Smaž");
        btSmaz.addActionListener((e) -> {
            int radek = tabulkaSkladu.getSelectedRow();
            sklad.smazatZbozi(radek);
        });

        JButton btSmazVse = new JButton("Smaž vše");

        JPanel pnTlacitka = new JPanel();
        pnTlacitka.setLayout(new GridLayout(0, 1));
        pnTlacitka.add(btPridej);
        pnTlacitka.add(btSmaz);
        pnTlacitka.add(btSmazVse);

        panelSkladu = new JPanel();
        panelSkladu.setLayout(new BorderLayout());
        panelSkladu.add(spTabulkaSkladu, BorderLayout.CENTER);
        panelSkladu.add(pnTlacitka, BorderLayout.EAST);
    }

    public static void vytvorHlavniOkno() {
        JFrame hlavniOkno = new JFrame();
        hlavniOkno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hlavniOkno.setTitle("Obchod");
        hlavniOkno.setLocationByPlatform(true);

        Obchod obchod = new Obchod();
        hlavniOkno.setContentPane(obchod.getHlavniPanel());
        hlavniOkno.setJMenuBar(obchod.getNabidka());

        hlavniOkno.pack();
        hlavniOkno.setVisible(true);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> vytvorHlavniOkno());
    }
}
