import java.util.*;

public class DijkstraAlgorithm {

    public static List<String> findShortestPath(CityGraph graph, String origem, String destino) {
        return findPath(graph, origem, destino, true);
    }

    public static List<String> findLowestTollPath(CityGraph graph, String origem, String destino) {
        return findPath(graph, origem, destino, false);
    }

    private static List<String> findPath(CityGraph graph, String origem, String destino, boolean useDistance) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.cost));
        Set<String> visited = new HashSet<>();

        for (String city : graph.getCities()) {
            distances.put(city, Integer.MAX_VALUE);
        }
        distances.put(origem, 0);
        queue.add(new Node(origem, 0));

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (!visited.add(current.city)) {
                continue;
            }
            if (current.city.equals(destino)) {
                return constructPath(previous, destino);
            }
            Map<String, Integer> neighbors = useDistance ? graph.getNeighborsWithDistances(current.city) : graph.getNeighborsWithTolls(current.city);
            for (Map.Entry<String, Integer> neighbor : neighbors.entrySet()) {
                if (visited.contains(neighbor.getKey())) {
                    continue;
                }
                int newCost = current.cost + neighbor.getValue();
                if (newCost < distances.get(neighbor.getKey())) {
                    distances.put(neighbor.getKey(), newCost);
                    previous.put(neighbor.getKey(), current.city);
                    queue.add(new Node(neighbor.getKey(), newCost));
                }
            }
        }
        return Collections.emptyList(); // Caminho não encontrado
    }

    private static List<String> constructPath(Map<String, String> previous, String destino) {
        List<String> path = new LinkedList<>();
        for (String at = destino; at != null; at = previous.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    public static int getPathDistance(CityGraph graph, List<String> path) {
        return getPathCost(graph, path, true);
    }

    public static int getPathToll(CityGraph graph, List<String> path) {
        return getPathCost(graph, path, false);
    }

    private static int getPathCost(CityGraph graph, List<String> path, boolean useDistance) {
        if (path == null || path.size() < 2) {
            return Integer.MAX_VALUE;
        }
        int cost = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            String city1 = path.get(i);
            String city2 = path.get(i + 1);
            int edgeCost = useDistance ? graph.getDistance(city1, city2) : graph.getToll(city1, city2);
            if (edgeCost == Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }
            cost += edgeCost;
        }
        return cost;
    }

    private static class Node {
        String city;
        int cost;

        Node(String city, int cost) {
            this.city = city;
            this.cost = cost;
        }
    }
}
