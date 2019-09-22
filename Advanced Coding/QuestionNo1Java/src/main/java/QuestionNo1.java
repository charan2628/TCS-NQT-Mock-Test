/**
 * Refer to question in "src/main/resources/QuestionNo1.txt"
 * 
 * Algorithm used: Prims's Minimum Spanning Tree
 * 
 */

import java.util.*;
import java.io.*;

public class QuestionNo1 {

	static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
	
	static int[][] matrix;
	static int n;
	static boolean[] taken;
	static int[] parent;
	/**
	 * It is format of (weight, (vertex1, vertex2))
	 * 
	 * Ex: If edge between vertex 3 and 4 is 8 then it will be stored as queue as (8, (3, 4))
	 * 
	 */
	static PriorityQueue<Pair<Integer, Pair<Integer, Integer>>> pq;
	
	public static void main(String[] args) throws Exception{
		n = Integer.parseInt(reader.readLine());
		int edges = 2*(n-1)*n;
		
		matrix = new int[n][];
		taken = new boolean[n*n];
		parent = new int[n*n];		
		
		//Sorts edges based on their weight
		pq = new PriorityQueue<>(edges, Comparator.comparing(p -> p.first));
		
		for(int i = 0; i < n; i++) {
			matrix[i] = Arrays.asList(reader.readLine().split(",")).stream().mapToInt(Integer::parseInt).toArray();
		}
		
		process(0);
		parent[0] = -1;
		
		while(!pq.isEmpty()) {
			Pair<Integer, Pair<Integer, Integer>> pair = pq.poll();
			if(!taken[pair.second.second]) {
				parent[pair.second.second] = pair.second.first;
				process(pair.second.second);
			}
		}
		
		writer.write(String.format("%d", calcMaxWeight(n*n - 1)));
		writer.close();
		reader.close();
	}
	
	static int calcMaxWeight(int u) throws Exception{
		//writer.write(String.format("VERTEX %d: ", u));
		int maxWeight = Integer.MIN_VALUE;
		while(u >= 0) {
			//writer.write(String.format("%d ", parent[u]));
			Pair<Integer, Integer> pair = oneDimToTwoDim(u);
			int temp = matrix[pair.first][pair.second];
			if(maxWeight < temp) {
				maxWeight = temp;
			}
			u = parent[u];
		}
		//writer.newLine();
		return maxWeight;
	}
	
	static void process(int u) throws Exception{
		taken[u] = true;
		for(Pair<Integer, Pair<Integer, Integer>> pair: getAdjacencyList(u)) {
			if(!taken[pair.second.second]) {
				pq.offer(pair);
			}
		}
	}
	
	static List<Pair<Integer, Pair<Integer, Integer>>> getAdjacencyList(int u) throws Exception{
		int row[] = {1, 0}, col[] = {0, 1};
		Pair<Integer, Integer> currVertex = oneDimToTwoDim(u);
		List<Pair<Integer, Pair<Integer, Integer>>> adjList = new ArrayList<>();
		for(int i = 0; i < 2; i++) {
			int newRow = row[i] + currVertex.first, newCol = col[i] + currVertex.second;
			if(newRow < 0 || newRow >= n || newCol < 0 || newCol >= n) continue;
			adjList.add(Pair.createPair(matrix[newRow][newCol], Pair.createPair(u, twoDimToOneDim(newRow, newCol))));
		}
		return adjList;
	}
	
	static Pair<Integer, Integer> oneDimToTwoDim(int u) {
		return Pair.createPair(u / n, u % n);
	}
	
	static int twoDimToOneDim(int u, int v) {
		return u*n + v;
	}
	
	static class Pair<K, V> {
		K first;
		V second;
		
		Pair(K k, V v) {
			this.first = k;
			this.second = v;
		}
		
		static<K, V> Pair<K, V> createPair(K k, V v) {
			return new Pair<>(k, v);
		}
	}
}
