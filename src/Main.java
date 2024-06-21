import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        CityGraph graph = new CityGraph();

        // Adicione cidades e distâncias com pedágio
        graph.addCity("São Paulo", 510, 320);
        graph.addCity("Santos", 430, 340);
        graph.addCity("Sorocaba", 450, 300);
        graph.addCity("Bauru", 300, 200);
        graph.addCity("Marilia", 250, 210);
        graph.addCity("Araçatuba", 190, 125);
        graph.addCity("Presidente Prudente", 120, 205);
        graph.addCity("São Jose dos Campos", 600, 200);
        graph.addCity("Campinas", 350, 150);
        graph.addCity("Piracicaba", 300, 100);
        graph.addCity("Araraquara", 250, 200);
        graph.addCity("Ribeirão Preto", 150, 300);
        graph.addCity("São José do Rio Preto", 100, 400);
        graph.addCity("Guarulhos", 450, 150);
        graph.addCity("São Bernardo do Campo", 400, 250);
        graph.addCity("Santo André", 450, 250);
        graph.addCity("Osasco", 400, 150);
        graph.addCity("Mogi das Cruzes", 550, 150);
        graph.addCity("Jundiaí", 350, 100);

        // Adicione distâncias (arestas) com pedágio
        graph.addEdge("São Paulo", "Santos", 85, 10);
        graph.addEdge("São Paulo", "Sorocaba", 109, 8);
        graph.addEdge("São Paulo", "Campinas", 109, 5000);
        graph.addEdge("São Paulo", "São Jose dos Campos", 78, 5);
        graph.addEdge("Campinas", "Piracicaba", 72, 6);
        graph.addEdge("Campinas", "Araraquara", 186, 15);
        graph.addEdge("Campinas", "Ribeirão Preto", 223, 20);
        graph.addEdge("Araraquara", "São José do Rio Preto", 168, 12);
        graph.addEdge("Sorocaba", "Bauru", 244, 18);
        graph.addEdge("Bauru", "Marilia", 106, 7);
        graph.addEdge("Bauru", "Araçatuba", 191, 10);
        graph.addEdge("Sorocaba", "Presidente Prudente", 475, 30);

        // Cria e exibe a interface gráfica do grafo no EDT
        SwingUtilities SwingUtilities = null;
        SwingUtilities.invokeLater(() -> new GraphUI(graph));
    }
}
