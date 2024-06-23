import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

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
        setTitle("City Graph");
        setSize(800, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel de controle
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(10, 2));

        origemComboBox = new JComboBox<>();
        destinoComboBox = new JComboBox<>();
        resultadoField = new JTextField();
        menorDistanciaButton = new JRadioButton("Menor Distância");
        menorPedagioButton = new JRadioButton("Menor Pedágio");
        buscarButton = new JButton("Buscar");
        limparButton = new JButton("Limpar");
        adicionarCidadeButton = new JButton("Adicionar Cidade");
        removerCidadeButton = new JButton("Remover Cidade");
        adicionarArestaButton = new JButton("Adicionar Aresta");
        removerArestaButton = new JButton("Remover Aresta");

        ButtonGroup group = new ButtonGroup();
        group.add(menorDistanciaButton);
        group.add(menorPedagioButton);

        controlPanel.add(new JLabel("Origem:"));
        controlPanel.add(origemComboBox);
        controlPanel.add(new JLabel("Destino:"));
        controlPanel.add(destinoComboBox);
        controlPanel.add(new JLabel("Resultado:"));
        controlPanel.add(resultadoField);
        controlPanel.add(menorDistanciaButton);
        controlPanel.add(menorPedagioButton);
        controlPanel.add(buscarButton);
        controlPanel.add(limparButton);
        controlPanel.add(adicionarCidadeButton);
        controlPanel.add(removerCidadeButton);
        controlPanel.add(adicionarArestaButton);
        controlPanel.add(removerArestaButton);


        add(controlPanel, BorderLayout.NORTH);

        mxGraphComponent graphComponent = new mxGraphComponent(mxGraph);
        add(graphComponent, BorderLayout.CENTER);

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

        adicionarArestaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String distance = JOptionPane.showInputDialog("Distancia:");
                String toll = JOptionPane.showInputDialog("Pedágio:");
                String origem = (String) origemComboBox.getSelectedItem();
                String destino = (String) destinoComboBox.getSelectedItem();
                addEdge(origem, destino, Integer.parseInt(distance), Integer.parseInt(toll));

            }
        });

        adicionarCidadeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cityName = JOptionPane.showInputDialog("Nome da cidade a adicionar:");
                if (cityName != null && !cityName.trim().isEmpty()) {
                    addVertex(cityName);
                }
            }
        });

        removerCidadeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cityName = JOptionPane.showInputDialog("Nome da cidade a remover:");
                if (cityName != null && !cityName.trim().isEmpty()) {
                    removeVertex(cityName);
                }
            }
        });

        removerArestaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String origem = (String) origemComboBox.getSelectedItem();
                String destino = (String) destinoComboBox.getSelectedItem();
                removeEdge(origem, destino);
            }
        });

        setVisible(true);
    }

    private void loadCities() {
        List<String> cities = new ArrayList<>(graph.getCities());
        Collections.sort(cities);
        for (String city : cities) {
            origemComboBox.addItem(city);
            destinoComboBox.addItem(city);
        }
    }

    private void populateGraph() {
        mxGraph.getModel().beginUpdate();
        try {
            Map<String, Object> vertices = new HashMap<>();
            Object parent = mxGraph.getDefaultParent();
            for (String city : graph.getCities()) {
                int[] coords = graph.getCoordinates(city);
                vertices.put(city, mxGraph.insertVertex(parent, null, city, coords[0], coords[1], 80, 30));
            }
            for (String city1 : graph.getCities()) {
                for (Map.Entry<String, Integer> entry : graph.getNeighborsWithDistances(city1).entrySet()) {
                    String city2 = entry.getKey();
                    int distance = entry.getValue();
                    mxGraph.insertEdge(parent, null, distance, vertices.get(city1), vertices.get(city2));
                }
            }
        } finally {
            mxGraph.getModel().endUpdate();
        }
    }

    private void addVertex(String cityName) {
        mxGraph.getModel().beginUpdate();
        try {
            Object parent = mxGraph.getDefaultParent();
            mxGraph.insertVertex(parent, null, cityName, 100, 100, 80, 30);
        } finally {
            mxGraph.getModel().endUpdate();
        }
    }

    private void removeVertex(String cityName) {
        Object parent = mxGraph.getDefaultParent();
        Object[] vertices = mxGraph.getChildVertices(parent);
        for (Object vertex : vertices) {
            if (mxGraph.getLabel(vertex).equals(cityName)) {
                mxGraph.getModel().beginUpdate();
                try {
                    mxGraph.removeCells(new Object[]{vertex});
                } finally {
                    mxGraph.getModel().endUpdate();
                }
                break;
            }
        }
    }

    private void addEdge(String city1, String city2, int distance, int toll) {
        Object parent = mxGraph.getDefaultParent();
        Object[] vertices = mxGraph.getChildVertices(parent);
        Object vertex1 = null, vertex2 = null;
        for (Object vertex : vertices) {
            if (mxGraph.getLabel(vertex).equals(city1)) {
                vertex1 = vertex;
            }
            if (mxGraph.getLabel(vertex).equals(city2)) {
                vertex2 = vertex;
            }
        }
        if (vertex1 != null && vertex2 != null) {
            mxGraph.getModel().beginUpdate();
            try {
                mxGraph.insertEdge(parent, null, distance, vertex1, vertex2);
            } finally {
                mxGraph.getModel().endUpdate();
            }
        }
    }

    private void removeEdge(String city1, String city2) {
        Object parent = mxGraph.getDefaultParent();
        Object[] edges = mxGraph.getChildEdges(parent);
        for (Object edge : edges) {
            Object source = mxGraph.getModel().getTerminal(edge, true);
            Object target = mxGraph.getModel().getTerminal(edge, false);
            if ((mxGraph.getLabel(source).equals(city1) && mxGraph.getLabel(target).equals(city2)) ||
                    (mxGraph.getLabel(source).equals(city2) && mxGraph.getLabel(target).equals(city1))) {
                mxGraph.getModel().beginUpdate();
                try {
                    mxGraph.removeCells(new Object[]{edge});
                } finally {
                    mxGraph.getModel().endUpdate();
                }
                break;
            }
        }
    }

    private void performSearch() {
        String origem = (String) origemComboBox.getSelectedItem();
        String destino = (String) destinoComboBox.getSelectedItem();
        if (origem != null && destino != null) {
            clearEdgeStyles();
            List<String> path = null;
            if (menorDistanciaButton.isSelected()) {
                path = DijkstraAlgorithm.findShortestPath(graph, origem, destino);
                int distance = DijkstraAlgorithm.getPathDistance(graph, path);
                resultadoField.setText(distance == Integer.MAX_VALUE ? "Caminho não encontrado" : String.valueOf(distance));
            } else if (menorPedagioButton.isSelected()) {
                path = DijkstraAlgorithm.findLowestTollPath(graph, origem, destino);
                int toll = DijkstraAlgorithm.getPathToll(graph, path);
                resultadoField.setText(toll == Integer.MAX_VALUE ? "Caminho não encontrado" : String.valueOf(toll));
            }
            highlightPath(path);
        }
    }


    private void clearGraph() {
        mxGraph.getModel().beginUpdate();
        try {
            mxGraph.removeCells(mxGraph.getChildCells(mxGraph.getDefaultParent(), true, true));
        } finally {
            mxGraph.getModel().endUpdate();
        }
    }

    private void clearEdgeStyles() {
        Object parent = mxGraph.getDefaultParent();
        Object[] edges = mxGraph.getChildEdges(parent);
        for (Object edge : edges) {
            mxGraph.setCellStyle(null, new Object[]{edge});
        }
    }

    private void highlightPath(List<String> path) {
        if (path == null || path.size() < 2) {
            return;
        }
        mxGraph.getModel().beginUpdate();
        try {
            Object parent = mxGraph.getDefaultParent();
            Object[] edges = mxGraph.getChildEdges(parent);
            for (int i = 0; i < path.size() - 1; i++) {
                String city1 = path.get(i);
                String city2 = path.get(i + 1);
                for (Object edge : edges) {
                    Object source = mxGraph.getModel().getTerminal(edge, true);
                    Object target = mxGraph.getModel().getTerminal(edge, false);
                    if ((mxGraph.getLabel(source).equals(city1) && mxGraph.getLabel(target).equals(city2)) ||
                            (mxGraph.getLabel(source).equals(city2) && mxGraph.getLabel(target).equals(city1))) {
                        mxGraph.setCellStyle("strokeColor=red", new Object[]{edge});
                    }
                }
            }
        } finally {
            mxGraph.getModel().endUpdate();
        }
    }
}
