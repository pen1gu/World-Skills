package algorithmtest;

public class BFS {
	public static void main(String[] args) {
		Graph g = new Graph(8);
		g.input(1, 2, 3);
		g.input(1, 5, 4);
		g.input(1, 4, 4);
		g.input(2, 3, 2);
		g.input(3, 4, 1);
		g.input(4, 5, 2);
		g.input(5, 6, 4);
		g.input(4, 7, 6);
		g.input(7, 6, 3);
		g.input(3, 8, 3);
		g.input(6, 8, 2);
		g.dijkstra(1);

	}
}

class Graph {
	private int n; // 노드들의 수
	private int maps[][]; // 노드들간의 가중치 저장할 변수

	public Graph(int n) {
		this.n = n;
		maps = new int[n + 1][n + 1];

	}

	public void input(int i, int j, int w) {
		// 위로 가던 아래로 가던 이동 위치는 같기 때문에 아래와 같이 사용
		// cost 가 들어갈 시 조금 바뀌어야 함

		maps[i][j] = w; // 좌표상 v 기준 오른 또는 아래
		maps[j][i] = w; // 위와 동일
	}

	public void dijkstra(int v) { // 거리라는 개념이 들어갔음
		int[] distance = new int[n + 1]; // 최단 거리를 저장할 변수
		boolean[] check = new boolean[n + 1]; // 해당 노드를 방문했는지 체크할 변수

		// distance값 초기화.
		for (int i = 1; i < n + 1; i++) {
			distance[i] = Integer.MAX_VALUE; // 처음에는 최단거리를 저장할 변수에 큰 값을 넣어둔다
		}

		for (int i = 1; i < n + 1; i++) {
			for (int j = 1; j < n + 1; j++) {
				System.out.printf("%2d", maps[i][j]);
			}
			System.out.println();
		}

//		---------------------------
		// 시작노드값 초기화.
		distance[v] = 0;
		check[v] = true;

		// 연결노드 distance갱신
		for (int i = 1; i < n + 1; i++) {
			if (!check[i] && maps[v][i] != 0) { // 가지 않은 곳이며, 시작 지점과 인접한 노드 탐색
				distance[i] = maps[v][i]; // 인접 노드를 길이에 넣는다.
			}
		}

		for (int a = 0; a < n - 1; a++) {
			// 원래는 모든 노드가 true될때까지 인데
			// 노드가 n개 있을 때 다익스트라를 위해서 반복수는 n-1번이면 된다.
			// 원하지 않으면 각각의 노드가 모두 true인지 확인하는 식으로 구현해도 된다.
			int min = Integer.MAX_VALUE;
			int min_index = -1;

			// 최소값 찾기
			for (int i = 1; i < n + 1; i++) {
				if (!check[i] && distance[i] != Integer.MAX_VALUE) { // 가지 않은 길이며, 인접한 노드가 아닐 때
					if (distance[i] < min) { // 거리가 최단 거리보다 작을 때
						min = distance[i]; // min에 최단거리 갱신
						min_index = i; // 가장 작은 index가 i
					}
				}
			}

			check[min_index] = true; // 가장 짧은 노드에 갔음을 입력
			for (int i = 1; i < n + 1; i++) {
				if (!check[i] && maps[min_index][i] != 0) { // 가지 않았고, 위에서 구한 가장 짧은 index층의 값을 구함
					if (distance[i] > distance[min_index] + maps[min_index][i]) { // 한 간선의 길이보다 두 간선의 길이가 짧을 시
						distance[i] = distance[min_index] + maps[min_index][i]; // 값에 넣는다
					}
				}
			}
		}

//		------------------------------

		// 결과값 출력
		for (int i = 1; i < n + 1; i++) {
			System.out.print(distance[i] + " ");
		}
		System.out.println("");

	}
}
