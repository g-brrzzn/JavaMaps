import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        CityGraph graph = new CityGraph();

        graph.addCity("Andradina", 80, 50);
        graph.addCity("Araçatuba", 140, 93);
        graph.addCity("Araraquara", 370, 245);
        graph.addCity("Assis", 157, 245);
        graph.addCity("Bauru", 280, 200);
        graph.addCity("Campinas", 500, 270);
        graph.addCity("Itapeva", 300, 372);
        graph.addCity("Marilia", 220, 210);
        graph.addCity("Ourinhos", 250, 270);
        graph.addCity("Piracicaba", 435, 257);
        graph.addCity("Presidente Prudente", 40, 200);
        graph.addCity("Registro", 425, 440);
        graph.addCity("Ribeirão Preto", 435, 110);
        graph.addCity("Santos", 585, 375);
        graph.addCity("São João da Boa Vista", 522, 145);
        graph.addCity("São José do Rio Preto", 285, 40);
        graph.addCity("São Jose dos Campos", 645, 240);
        graph.addCity("São Paulo", 560, 350);
        graph.addCity("Sorocaba", 480, 325);

        // Adicione as arestas com novos preços de pedágio
        graph.addEdge("São Paulo", "Santos", 85, 12);
        graph.addEdge("São Paulo", "Sorocaba", 109, 15);
        graph.addEdge("São Paulo", "Campinas", 109, 20);
        graph.addEdge("São Paulo", "São Jose dos Campos", 78, 25);
        graph.addEdge("Campinas", "Piracicaba", 72, 10);
        graph.addEdge("Campinas", "Araraquara", 186, 18);
        graph.addEdge("Campinas", "Ribeirão Preto", 223, 30);
        graph.addEdge("Araraquara", "São José do Rio Preto", 168, 22);
        graph.addEdge("Sorocaba", "Bauru", 244, 17);
        graph.addEdge("Bauru", "Marilia", 106, 12);
        graph.addEdge("Bauru", "Araçatuba", 191, 16);
        graph.addEdge("Sorocaba", "Ourinhos", 292, 15);
        graph.addEdge("Sorocaba", "Registro", 156, 14);
        graph.addEdge("São Paulo", "Registro", 210, 28);
        graph.addEdge("Itapeva", "Registro", 268, 13);
        graph.addEdge("Itapeva", "Assis", 256, 11);
        graph.addEdge("Ourinhos", "Assis", 70, 9);
        graph.addEdge("Ourinhos", "Presidente Prudente", 126, 19);
        graph.addEdge("Marilia", "Presidente Prudente", 187, 14);
        graph.addEdge("Araçatuba", "Presidente Prudente", 164, 24);
        graph.addEdge("Andradina", "Presidente Prudente", 178, 17);
        graph.addEdge("Andradina", "Araçatuba", 112, 13);
        graph.addEdge("Andradina", "São José do Rio Preto", 236, 18);
        graph.addEdge("Araçatuba", "São José do Rio Preto", 151, 11);
        graph.addEdge("Ribeirão Preto", "São José do Rio Preto", 201, 20);
        graph.addEdge("Ribeirão Preto", "São João da Boa Vista", 171, 15);
        graph.addEdge("Campinas", "São João da Boa Vista", 123, 16);
        graph.addEdge("Campinas", "São Jose dos Campos", 153, 21);
        graph.addEdge("Bauru", "São José do Rio Preto", 221, 22);
        graph.addEdge("Itapeva", "Sorocaba", 197, 18);
        graph.addEdge("Araraquara", "Ribeirão Preto", 85, 16);
        graph.addEdge("Araraquara", "Itapeva", 303, 23);
        graph.addEdge("Campinas", "Sorocaba", 87, 10);
        graph.addEdge("Ourinhos", "Bauru", 125, 17);
        graph.addEdge("Assis", "Presidente Prudente", 126, 14);

        SwingUtilities.invokeLater(() -> new GraphUI(graph));
    }
}
