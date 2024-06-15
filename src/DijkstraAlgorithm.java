import java.util.*;

public class DijkstraAlgorithm {
    private final CityGraph graph;
    private final Set<String> settledNodes;
    private final Set<String> unsettledNodes;
    private final Map<String, Integer> distances;
    private final Map<String, String> predecessors;

    public DijkstraAlgorithm(CityGraph graph) {
        this.graph = graph;
        this.settledNodes = new HashSet<>();
        this.unsettledNodes = new HashSet<>();
        this.distances = new HashMap<>();
        this.predecessors = new HashMap<>();
    }

    public void execute(String source) {
        distances.put(source, 0);
        unsettledNodes.add(source);

        while (!unsettledNodes.isEmpty()) {
            String node = getMinimum(unsettledNodes);
            settledNodes.add(node);
            unsettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(String node) {
        Map<String, Integer> adjacentNodes = graph.getNeighborsWithDistances(node); // Supondo que este método retorna um Map<String, Integer>
        for (Map.Entry<String, Integer> entry : adjacentNodes.entrySet()) {
            String target = entry.getKey();
            Integer edgeWeight = entry.getValue();
            if (getShortestDistance(target) > getShortestDistance(node) + edgeWeight) {
                distances.put(target, getShortestDistance(node) + edgeWeight);
                predecessors.put(target, node);
                unsettledNodes.add(target);
            }
        }
    }

    public int getShortestDistance(String destination) {
        Integer d = distances.get(destination);
        return (d == null) ? Integer.MAX_VALUE : d;
    }

    private String getMinimum(Set<String> vertexes) {
        String minimum = null;
        for (String vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    public LinkedList<String> getPath(String target) {
        LinkedList<String> path = new LinkedList<>();
        String step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }
}
