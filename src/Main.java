public class Main {
    public static void main(String[] args) {
        CityGraph graph = new CityGraph();

        // Adicione cidades e distâncias com pedágio
        graph.addCity("São Paulo");
        graph.addCity("Santos");
        graph.addCity("Sorocaba");
        graph.addCity("Bauru");
        graph.addCity("Marilia");
        graph.addCity("Araçatuba");
        graph.addCity("Presidente Prudente");
        graph.addCity("São Jose dos Campos");
        graph.addCity("Campinas");
        graph.addCity("Piracicaba");
        graph.addCity("Araraquara");
        graph.addCity("Ribeirão Preto");
        graph.addCity("São José do Rio Preto");

        // Adicione distâncias (arestas) com pedágio
        graph.addEdge("São Paulo", "Santos", 85, 10);
        graph.addEdge("São Paulo", "Sorocaba", 109, 8);
        graph.addEdge("São Paulo", "Campinas", 109, 7);
        graph.addEdge("São Paulo", "São Jose dos Campos", 78, 5);
        graph.addEdge("Campinas", "Piracicaba", 72, 6);
        graph.addEdge("Campinas", "Araraquara", 186, 15);
        graph.addEdge("Campinas", "Ribeirão Preto", 223, 20);
        graph.addEdge("Araraquara", "São José do Rio Preto", 168, 12);
        graph.addEdge("Sorocaba", "Bauru", 244, 18);
        graph.addEdge("Bauru", "Marilia", 106, 7);
        graph.addEdge("Bauru", "Araçatuba", 191, 10);
        graph.addEdge("Sorocaba", "Presidente Prudente", 475, 30);

        // Cria e exibe a interface gráfica do grafo
        new GraphUI(graph);
    }
}
