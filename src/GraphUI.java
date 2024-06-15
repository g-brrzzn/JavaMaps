import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class GraphUI extends JFrame {
    private CityGraph graph;
    private JComboBox<String> origemComboBox;
    private JComboBox<String> destinoComboBox;
    private JTextField distanciaField;
    private JButton buscarButton;
    private JButton limparButton;

    public GraphUI(CityGraph graph) {
        this.graph = graph;
        initUI();
    }

    private void initUI() {
        setTitle("Busca em Grafo");
        setLayout(null);
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel origemLabel = new JLabel("Origem:");
        origemLabel.setBounds(50, 30, 100, 30);
        add(origemLabel);

        origemComboBox = new JComboBox<>();
        origemComboBox.setBounds(150, 30, 200, 30);
        add(origemComboBox);

        JLabel destinoLabel = new JLabel("Destino:");
        destinoLabel.setBounds(50, 80, 100, 30);
        add(destinoLabel);

        destinoComboBox = new JComboBox<>();
        destinoComboBox.setBounds(150, 80, 200, 30);
        add(destinoComboBox);

        JLabel distanciaLabel = new JLabel("Distância:");
        distanciaLabel.setBounds(50, 130, 100, 30);
        add(distanciaLabel);

        distanciaField = new JTextField();
        distanciaField.setBounds(150, 130, 200, 30);
        distanciaField.setEditable(false);
        add(distanciaField);

        buscarButton = new JButton("Buscar");
        buscarButton.setBounds(50, 180, 100, 30);
        add(buscarButton);

        limparButton = new JButton("Limpar Mapa");
        limparButton.setBounds(200, 180, 150, 30);
        add(limparButton);

        // Popula o ComboBox de origem
        for (String city : graph.getCities()) {
            origemComboBox.addItem(city);
        }

        // Adiciona ActionListener ao ComboBox de origem
        origemComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDestinoComboBox();
            }
        });

        // Adiciona ActionListener ao botão Buscar
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });

        setVisible(true);
    }

    private void updateDestinoComboBox() {
        destinoComboBox.removeAllItems();
        String selectedOrigin = (String) origemComboBox.getSelectedItem();
        if (selectedOrigin != null) {
            for (String neighbor : graph.getNeighbors(selectedOrigin)) {
                destinoComboBox.addItem(neighbor);
            }
        }
    }

    private void performSearch() {
        String origem = (String) origemComboBox.getSelectedItem();
        String destino = (String) destinoComboBox.getSelectedItem();
        if (origem != null && destino != null) {
            DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
            dijkstra.execute(origem);
            LinkedList<String> path = dijkstra.getPath(destino);
            if (path != null) {
                int distancia = dijkstra.getShortestDistance(destino);
                distanciaField.setText(Integer.toString(distancia));
            } else {
                distanciaField.setText("No path");
            }
        }
    }
}
