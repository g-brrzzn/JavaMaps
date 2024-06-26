import java.util.*;

public class AStarAlgorithm {

    private static class Node implements Comparable<Node> {
        String city;
        int cost;
        int heuristic;

        Node(String city, int cost, int heuristic) {
            this.city = city;
            this.cost = cost;
            this.heuristic = heuristic;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.cost + this.heuristic, other.cost + other.heuristic);
        }
    }

    public static List<String> findShortestPath(CityGraph graph, String start, String goal) {
        return findPath(graph, start, goal, false);
    }

    public static List<String> findLowestTollPath(CityGraph graph, String start, String goal) {
        return findPath(graph, start, goal, true);
    }

    private static List<String> findPath(CityGraph graph, String start, String goal, boolean byToll) {
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Map<String, String> cameFrom = new HashMap<>();
        Map<String, Integer> gScore = new HashMap<>();

        for (String city : graph.getCities()) {
            gScore.put(city, Integer.MAX_VALUE);
        }
        gScore.put(start, 0);

        openSet.add(new Node(start, 0, heuristic(start, goal, graph)));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            if (current.city.equals(goal)) {
                return reconstructPath(cameFrom, current.city);
            }

            for (Map.Entry<String, Integer> neighborEntry : graph.getNeighborsWithDistances(current.city).entrySet()) {
                String neighbor = neighborEntry.getKey();
                int tentative_gScore;
                if (byToll) {
                    tentative_gScore = gScore.get(current.city) + graph.getToll(current.city, neighbor);
                } else {
                    tentative_gScore = gScore.get(current.city) + neighborEntry.getValue();
                }

                if (tentative_gScore < gScore.get(neighbor)) {
                    cameFrom.put(neighbor, current.city);
                    gScore.put(neighbor, tentative_gScore);
                    openSet.add(new Node(neighbor, tentative_gScore, heuristic(neighbor, goal, graph)));
                }
            }
        }

        return null;
    }

    private static List<String> reconstructPath(Map<String, String> cameFrom, String current) {
        List<String> path = new ArrayList<>();
        path.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(current);
        }
        Collections.reverse(path);
        return path;
    }

    private static int heuristic(String city1, String city2, CityGraph graph) {
        int[] coords1 = graph.getCoordinates(city1);
        int[] coords2 = graph.getCoordinates(city2);
        return (int) Math.sqrt(Math.pow(coords1[0] - coords2[0], 2) + Math.pow(coords1[1] - coords2[1], 2));
    }

    public static int getPathToll(CityGraph graph, List<String> path) {
        if (path == null || path.size() < 2) {
            return Integer.MAX_VALUE;
        }
        int totalToll = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            totalToll += graph.getToll(path.get(i), path.get(i + 1));
        }
        return totalToll;
    }

    public static int getPathDistance(CityGraph graph, List<String> path) {
        if (path == null || path.size() < 2) {
            return Integer.MAX_VALUE;
        }
        int totalDistance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            totalDistance += graph.getDistance(path.get(i), path.get(i + 1));
        }
        return totalDistance;
    }
}
