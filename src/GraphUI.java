import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GraphUI extends JFrame {
    private CityGraph graph;
    private mxGraph mxGraph;
    private JComboBox<String> origemComboBox;
    private JComboBox<String> destinoComboBox;
    private JTextField resultadoField;
    private JRadioButton menorDistanciaButton;
    private JRadioButton menorPedagioButton;
    private JButton buscarButton;
    private JButton limparButton;
    private JButton adicionarCidadeButton;
    private JButton removerCidadeButton;
    private JButton adicionarArestaButton;
    private JButton removerArestaButton;

    public GraphUI(CityGraph graph) {
        this.graph = graph;
        this.mxGraph = new mxGraph();
        initUI();
        loadCities();
        populateGraph();
    }

    private void initUI() {
        setTitle("Busca em Grafo");
        setLayout(null);
        setSize(1000, 800);
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

        JLabel resultadoLabel = new JLabel("Resultado:");
        resultadoLabel.setBounds(50, 130, 100, 30);
        add(resultadoLabel);

        resultadoField = new JTextField();
        resultadoField.setBounds(150, 130, 200, 30);
        resultadoField.setEditable(false);
        add(resultadoField);

        buscarButton = new JButton("Buscar");
        buscarButton.setBounds(50, 180, 100, 30);
        add(buscarButton);

        limparButton = new JButton("Limpar Mapa");
        limparButton.setBounds(200, 180, 150, 30);
        add(limparButton);

        menorDistanciaButton = new JRadioButton("Menor Distância");
        menorDistanciaButton.setBounds(50, 230, 150, 30);
        menorDistanciaButton.setSelected(true);
        add(menorDistanciaButton);

        menorPedagioButton = new JRadioButton("Menor Pedágio");
        menorPedagioButton.setBounds(200, 230, 150, 30);
        add(menorPedagioButton);

        ButtonGroup searchOptions = new ButtonGroup();
        searchOptions.add(menorDistanciaButton);
        searchOptions.add(menorPedagioButton);

        mxGraphComponent graphComponent = new mxGraphComponent(mxGraph);
        graphComponent.setBounds(50, 270, 900, 450);
        add(graphComponent);

        adicionarCidadeButton = new JButton("Adicionar Cidade");
        adicionarCidadeButton.setBounds(50, 720, 150, 30);
        add(adicionarCidadeButton);

        removerCidadeButton = new JButton("Remover Cidade");
        removerCidadeButton.setBounds(200, 720, 150, 30);
        add(removerCidadeButton);

        adicionarArestaButton = new JButton("Adicionar Aresta");
        adicionarArestaButton.setBounds(350, 720, 150, 30);
        add(adicionarArestaButton);

        removerArestaButton = new JButton("Remover Aresta");
        removerArestaButton.setBounds(500, 720, 150, 30);
        add(removerArestaButton);

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });

        limparButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearGraph();
            }
        });

        adicionarCidadeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cityName = JOptionPane.showInputDialog("Nome da cidade:");
                if (cityName != null && !cityName.trim().isEmpty()) {
                    addVertex(cityName);
                }
            }
        });

        removerCidadeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cityName = (String) origemComboBox.getSelectedItem();
                if (cityName != null) {
                    removeVertex(cityName);
                }
            }
        });

        adicionarArestaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city1 = (String) origemComboBox.getSelectedItem();
                String city2 = (String) destinoComboBox.getSelectedItem();
                if (city1 != null && city2 != null && !city1.equals(city2)) {
                    String distanciaStr = JOptionPane.showInputDialog("Distância:");
                    String pedagioStr = JOptionPane.showInputDialog("Pedágio:");
                    try {
                        int distancia = Integer.parseInt(distanciaStr);
                        int pedagio = Integer.parseInt(pedagioStr);
                        addEdge(city1, city2, distancia, pedagio);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(GraphUI.this, "Distância e pedágio devem ser números inteiros.");
                    }
                }
            }
        });

        removerArestaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city1 = (String) origemComboBox.getSelectedItem();
                String city2 = (String) destinoComboBox.getSelectedItem();
                if (city1 != null && city2 != null) {
                    removeEdge(city1, city2);
                }
            }
        });


        setVisible(true);
    }

    private void loadCities() {
        for (String city : graph.getCities()) {
            origemComboBox.addItem(city);
            destinoComboBox.addItem(city);
        }
    }

    private void populateGraph() {
        mxGraph.getModel().beginUpdate();
        try {
            Object parent = mxGraph.getDefaultParent();
            Map<String, Object> vertexMap = new HashMap<>();
            for (String city : graph.getCities()) {
                Object vertex = mxGraph.insertVertex(parent, null, city, 50, 50, 80, 30);
                vertexMap.put(city, vertex);
            }
            for (String city1 : graph.getCities()) {
                for (String city2 : graph.getNeighbors(city1)) {
                    Object v1 = vertexMap.get(city1);
                    Object v2 = vertexMap.get(city2);
                    mxGraph.insertEdge(parent, null, "", v1, v2);
                }
            }
        } finally {
            mxGraph.getModel().endUpdate();
            new mxFastOrganicLayout(mxGraph).execute(mxGraph.getDefaultParent());
        }
    }

    private void addVertex(String cityName) {
        if (!graph.getCities().contains(cityName)) {
            graph.addCity(cityName);
            origemComboBox.addItem(cityName);
            destinoComboBox.addItem(cityName);
            mxGraph.getModel().beginUpdate();
            try {
                Object parent = mxGraph.getDefaultParent();
                Object vertex = mxGraph.insertVertex(parent, null, cityName, 50, 50, 80, 30);
            } finally {
                mxGraph.getModel().endUpdate();
                new mxFastOrganicLayout(mxGraph).execute(mxGraph.getDefaultParent());
            }
        } else {
            JOptionPane.showMessageDialog(this, "A cidade já existe!");
        }
    }

    private void removeVertex(String cityName) {
        if (graph.getCities().contains(cityName)) {
            graph.removeCity(cityName);
            origemComboBox.removeItem(cityName);
            destinoComboBox.removeItem(cityName);
            mxGraph.getModel().beginUpdate();
            try {
                Object parent = mxGraph.getDefaultParent();
                Object[] cells = mxGraph.getChildVertices(parent);
                for (Object cell : cells) {
                    if (mxGraph.getLabel(cell).equals(cityName)) {
                        mxGraph.removeCells(new Object[]{cell});
                        break;
                    }
                }
            } finally {
                mxGraph.getModel().endUpdate();
                new mxFastOrganicLayout(mxGraph).execute(mxGraph.getDefaultParent());
            }
        }
    }

    private void addEdge(String city1, String city2, int distance, int toll) {
        graph.addEdge(city1, city2, distance, toll);
        mxGraph.getModel().beginUpdate();
        try {
            Object parent = mxGraph.getDefaultParent();
            Object[] cells = mxGraph.getChildVertices(parent);
            Object v1 = null, v2 = null;
            for (Object cell : cells) {
                if (mxGraph.getLabel(cell).equals(city1)) {
                    v1 = cell;
                } else if (mxGraph.getLabel(cell).equals(city2)) {
                    v2 = cell;
                }
            }
            if (v1 != null && v2 != null) {
                mxGraph.insertEdge(parent, null, "", v1, v2);
            }
        } finally {
            mxGraph.getModel().endUpdate();
            new mxFastOrganicLayout(mxGraph).execute(mxGraph.getDefaultParent());
        }
    }

    private void removeEdge(String city1, String city2) {
        graph.removeEdge(city1, city2);
        mxGraph.getModel().beginUpdate();
        try {
            Object parent = mxGraph.getDefaultParent();
            Object[] edges = mxGraph.getChildEdges(parent);
            for (Object edge : edges) {
                Object source = mxGraph.getModel().getTerminal(edge, true);
                Object target = mxGraph.getModel().getTerminal(edge, false);
                if ((mxGraph.getLabel(source).equals(city1) && mxGraph.getLabel(target).equals(city2)) ||
                        (mxGraph.getLabel(source).equals(city2) && mxGraph.getLabel(target).equals(city1))) {
                    mxGraph.getModel().remove(edge);
                }
            }
        } finally {
            mxGraph.getModel().endUpdate();
            new mxFastOrganicLayout(mxGraph).execute(mxGraph.getDefaultParent());
        }
    }

    private void performSearch() {
        String origem = (String) origemComboBox.getSelectedItem();
        String destino = (String) destinoComboBox.getSelectedItem();
        if (origem != null && destino != null) {
            clearEdgeStyles();
            if (menorDistanciaButton.isSelected()) {
                int distance = DijkstraAlgorithm.findShortestPath(graph, origem, destino);
                resultadoField.setText(distance == Integer.MAX_VALUE ? "Caminho não encontrado" : String.valueOf(distance));
            } else if (menorPedagioButton.isSelected()) {
                int toll = DijkstraAlgorithm.findLowestTollPath(graph, origem, destino);
                resultadoField.setText(toll == Integer.MAX_VALUE ? "Caminho não encontrado" : String.valueOf(toll));
            }
            highlightPath(origem, destino);
        }
    }

    private void clearGraph() {
        graph.clear();
        mxGraph.getModel().beginUpdate();
        try {
            mxGraph.removeCells(mxGraph.getChildVertices(mxGraph.getDefaultParent()));
        } finally {
            mxGraph.getModel().endUpdate();
        }
    }

    private void clearEdgeStyles() {
        mxGraph.getModel().beginUpdate();
        try {
            Object[] edges = mxGraph.getChildEdges(mxGraph.getDefaultParent());
            for (Object edge : edges) {
                mxGraph.setCellStyle("strokeColor=black", new Object[]{edge});
            }
        } finally {
            mxGraph.getModel().endUpdate();
        }
    }

    private void highlightPath(String origem, String destino) {
        mxGraph.getModel().beginUpdate();
        try {
            Object parent = mxGraph.getDefaultParent();
            Object[] edges = mxGraph.getChildEdges(parent);
            for (Object edge : edges) {
                Object source = mxGraph.getModel().getTerminal(edge, true);
                Object target = mxGraph.getModel().getTerminal(edge, false);
                if ((mxGraph.getLabel(source).equals(origem) && mxGraph.getLabel(target).equals(destino)) ||
                        (mxGraph.getLabel(source).equals(destino) && mxGraph.getLabel(target).equals(origem))) {
                    mxGraph.setCellStyle("strokeColor=red", new Object[]{edge});
                }
            }
        } finally {
            mxGraph.getModel().endUpdate();
        }
    }
}
