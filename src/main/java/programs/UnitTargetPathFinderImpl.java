package programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.Edge;
import com.battle.heroes.army.programs.UnitTargetPathFinder;

import java.util.*;

public class UnitTargetPathFinderImpl implements UnitTargetPathFinder {

    private static final int WIDTH = 27;
    private static final int HEIGHT = 21;

    @Override
    public List<Edge> getTargetPath(Unit attacker, Unit target, List<Unit> units) {

        boolean[][] blocked = new boolean[WIDTH][HEIGHT];

        for (Unit u : units) {
            if (!u.isAlive()) continue;
            if (u == attacker) continue;
            blocked[u.getxCoordinate()][u.getyCoordinate()] = true;
        }

        int sx = attacker.getxCoordinate();
        int sy = attacker.getyCoordinate();

        int tx = target.getxCoordinate();
        int ty = target.getyCoordinate();

        boolean[][] visited = new boolean[WIDTH][HEIGHT];
        Edge[][] parent = new Edge[WIDTH][HEIGHT];

        Queue<Edge> q = new ArrayDeque<>();
        q.add(new Edge(sx, sy));
        visited[sx][sy] = true;

        int[] dx = {1, -1, 0, 0, 1, 1, -1, -1};
        int[] dy = {0, 0, 1, -1, 1, -1, 1, -1};

        Edge finish = null;

        while (!q.isEmpty()) {
            Edge cur = q.poll();

            // ДОСТИГЛИ СОСЕДНЕЙ К ЦЕЛИ КЛЕТКИ
            if (Math.abs(cur.getX() - tx) <= 1 &&
                    Math.abs(cur.getY() - ty) <= 1 &&
                    !(cur.getX() == tx && cur.getY() == ty)) {
                finish = cur;
                break;
            }

            for (int i = 0; i < dx.length; i++) {
                int nx = cur.getX() + dx[i];
                int ny = cur.getY() + dy[i];

                if (nx < 0 || ny < 0 || nx >= WIDTH || ny >= HEIGHT) continue;
                if (visited[nx][ny]) continue;
                if (blocked[nx][ny]) continue;

                visited[nx][ny] = true;
                parent[nx][ny] = cur;
                q.add(new Edge(nx, ny));
            }
        }

        if (finish == null) return Collections.emptyList();

        List<Edge> path = new ArrayList<>();
        Edge cur = finish;

        while (cur != null) {
            path.add(cur);
            cur = parent[cur.getX()][cur.getY()];
        }

        Collections.reverse(path);
        return path;
    }
}
