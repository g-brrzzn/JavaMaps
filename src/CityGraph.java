import java.util.*;

public class CityGraph {
    private Map<String, Map<String, Integer>> adjacencyMap;
    private Map<String, Map<String, Integer>> tollMap;
    private Map<String, int[]> coordinatesMap; // Novo mapa para coordenadas

    public CityGraph() {
        this.adjacencyMap = new HashMap<>();
        this.tollMap = new HashMap<>();
        this.coordinatesMap = new HashMap<>(); // Inicializa o mapa de coordenadas
    }

    public void addCity(String city, int x, int y) { // Altere o método para aceitar coordenadas
        adjacencyMap.putIfAbsent(city, new HashMap<>());
        tollMap.putIfAbsent(city, new HashMap<>());
        coordinatesMap.put(city, new int[]{x, y}); // Adicione as coordenadas ao mapa
    }

    public void removeCity(String city) {
        adjacencyMap.remove(city);
        tollMap.remove(city);
        coordinatesMap.remove(city); // Remova as coordenadas
        for (Map<String, Integer> edges : adjacencyMap.values()) {
            edges.remove(city);
        }
        for (Map<String, Integer> tolls : tollMap.values()) {
            tolls.remove(city);
        }
    }

    public void addEdge(String city1, String city2, int distance, int toll) {
        adjacencyMap.get(city1).put(city2, distance);
        adjacencyMap.get(city2).put(city1, distance);
        tollMap.get(city1).put(city2, toll);
        tollMap.get(city2).put(city1, toll);
    }

    public void removeEdge(String city1, String city2) {
        adjacencyMap.get(city1).remove(city2);
        adjacencyMap.get(city2).remove(city1);
        tollMap.get(city1).remove(city2);
        tollMap.get(city2).remove(city1);
    }

    public Set<String> getCities() {
        return adjacencyMap.keySet();
    }

    public Map<String, Integer> getNeighborsWithDistances(String city) {
        return adjacencyMap.getOrDefault(city, Collections.emptyMap());
    }

    public Map<String, Integer> getNeighborsWithTolls(String city) {
        return tollMap.getOrDefault(city, Collections.emptyMap());
    }

    public Set<String> getNeighbors(String city) {
        return adjacencyMap.getOrDefault(city, Collections.emptyMap()).keySet();
    }

    public int getDistance(String city1, String city2) {
        return adjacencyMap.getOrDefault(city1, Collections.emptyMap()).getOrDefault(city2, Integer.MAX_VALUE);
    }

    public int getToll(String city1, String city2) {
        return tollMap.getOrDefault(city1, Collections.emptyMap()).getOrDefault(city2, Integer.MAX_VALUE);
    }

    public int[] getCoordinates(String city) { // Novo método para obter coordenadas
        return coordinatesMap.get(city);
    }

    public void clear() {
        adjacencyMap.clear();
        tollMap.clear();
        coordinatesMap.clear(); // Limpe o mapa de coordenadas
    }
}
