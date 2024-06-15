public class Main {
    public static void main(String[] args) {
        CityGraph graph = new CityGraph();
// Adicione cidades e distâncias
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

// Adicione distâncias (arestas)
        graph.addEdge("São Paulo", "Santos", 85);
        graph.addEdge("Santos", "São Paulo", 85);

        graph.addEdge("São Paulo", "Sorocaba", 109);
        graph.addEdge("Sorocaba", "São Paulo", 109);

        graph.addEdge("São Paulo", "Campinas", 109);
        graph.addEdge("Campinas", "São Paulo", 109);

        graph.addEdge("São Paulo", "São Jose dos Campos", 78);
        graph.addEdge("São Jose dos Campos", "São Paulo", 78);

        graph.addEdge("Campinas", "Piracicaba", 72);
        graph.addEdge("Piracicaba", "Campinas", 72);

        graph.addEdge("Campinas", "Araraquara", 186);
        graph.addEdge("Araraquara", "Campinas", 186);

        graph.addEdge("Campinas", "Ribeirão Preto", 223);
        graph.addEdge("Ribeirão Preto", "Campinas", 223);

        graph.addEdge("Araraquara", "São José do Rio Preto", 168);
        graph.addEdge("São José do Rio Preto", "Araraquara", 168);

        graph.addEdge("Sorocaba", "Bauru", 244);
        graph.addEdge("Bauru", "Sorocaba", 244);

        graph.addEdge("Bauru", "Marilia", 106);
        graph.addEdge("Marilia", "Bauru", 106);

        graph.addEdge("Bauru", "Araçatuba", 191);
        graph.addEdge("Araçatuba", "Bauru", 191);

        graph.addEdge("Sorocaba", "Presidente Prudente", 475);
        graph.addEdge("Presidente Prudente", "Sorocaba", 475);


        GraphUI ui = new GraphUI(graph);
    }
}
