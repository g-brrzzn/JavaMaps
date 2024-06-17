import java.util.*;

public class CityGraph {
    private Map<String, Map<String, Integer>> adjacencyMap;
    private Map<String, Map<String, Integer>> tollMap;

    public CityGraph() {
        this.adjacencyMap = new HashMap<>();
        this.tollMap = new HashMap<>();
    }

    public void addCity(String city) {
        adjacencyMap.putIfAbsent(city, new HashMap<>());
        tollMap.putIfAbsent(city, new HashMap<>());
    }

    public void removeCity(String city) {
        adjacencyMap.remove(city);
        tollMap.remove(city);
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

    public void clear() {
        adjacencyMap.clear();
        tollMap.clear();
    }
}
