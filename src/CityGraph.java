import java.util.*;

public class CityGraph {
    private Map<String, Map<String, Integer>> graph;

    public CityGraph() {
        graph = new HashMap<>();
    }

    public void addCity(String city) {
        graph.putIfAbsent(city, new HashMap<>());
    }

    public void addEdge(String city1, String city2, int distance) {
        graph.get(city1).put(city2, distance);
        graph.get(city2).put(city1, distance);  // Se for grafo não-direcionado
    }

    public Set<String> getCities() {
        return graph.keySet();
    }

    public Set<String> getNeighbors(String city) {
        return graph.getOrDefault(city, Collections.emptyMap()).keySet();
    }

    public Map<String, Integer> getNeighborsWithDistances(String city) {
        return graph.getOrDefault(city, Collections.emptyMap());
    }
}
