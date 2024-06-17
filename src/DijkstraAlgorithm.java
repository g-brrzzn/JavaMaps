import java.util.*;

public class DijkstraAlgorithm {

    public static int findShortestPath(CityGraph graph, String origem, String destino) {
        return findPath(graph, origem, destino, true);
    }

    public static int findLowestTollPath(CityGraph graph, String origem, String destino) {
        return findPath(graph, origem, destino, false);
    }

    private static int findPath(CityGraph graph, String origem, String destino, boolean useDistance) {
        Map<String, Integer> distances = new HashMap<>();
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
                return current.cost;
            }
            Map<String, Integer> neighbors = useDistance ? graph.getNeighborsWithDistances(current.city) : graph.getNeighborsWithTolls(current.city);
            for (Map.Entry<String, Integer> neighbor : neighbors.entrySet()) {
                if (visited.contains(neighbor.getKey())) {
                    continue;
                }
                int newCost = current.cost + neighbor.getValue();
                if (newCost < distances.get(neighbor.getKey())) {
                    distances.put(neighbor.getKey(), newCost);
                    queue.add(new Node(neighbor.getKey(), newCost));
                }
            }
        }
        return Integer.MAX_VALUE;
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
