package pojo;

import java.util.ArrayList;
import java.util.List;

import conf.Config;

public class Node {
	String id = null;
	List<Node> adjacents = null;
	Cluster[] clusters = null;
	int isolate_size = 0;

	public Node(String id) {
		this.id = id;
		this.adjacents = new ArrayList<Node>();
	}

	public void addAdjacent(Node adj) {
		if (!adjacents.contains(adj)) {
			adjacents.add(adj);
		}
	}

	public double[][] getSubGraph() {
		int size = adjacents.size();
		double[][] w = new double[size][size];
		if (Config.DIRECTED) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (i == j)
						continue;
					if (adjacents.get(i).adjacents.contains(adjacents.get(j))) {
						w[i][j] = 1;
					}
				}
			}
		} else {
			for (int i = 0; i < size; i++) {
				for (int j = i + 1; j < size; j++) {
					if (adjacents.get(i).adjacents.contains(adjacents.get(j))) {
						w[j][i] = w[i][j] = 1;
					}
				}
			}
		}
		// record isolate vertex
		List<Integer> isolates = new ArrayList<Integer>();
		double sum = 0;
		for (int i = 0; i < size; i++) {
			sum = 0;
			for (int j = 0; j < size; j++) {
				sum += w[i][j];
			}
			if (sum == 0.0) {
				isolates.add(i);
			}
		}
		isolate_size = isolates.size();

		if (isolate_size == 0)// there is no isolate vertex
			return w;
		if (isolate_size == size)// all vertex are isolate
			return null;
		for (int i = 0; i < isolate_size; i++) {
			int idx = isolates.get(i) - i;
			Node node = adjacents.remove(idx);
			adjacents.add(node);
		}

		int new_size = size - isolate_size;
		double[][] new_w = new double[new_size][new_size];

		int idx_i, idx_j, tem_i = 0, tem_j = 0;
		for (int i = 0; i < new_size; i++) {
			idx_i = i + tem_i;
			while (isolates.contains(idx_i)) {
				tem_i++;
				idx_i++;
			}
			tem_j = 0;
			for (int j = 0; j < new_size; j++) {
				idx_j = j + tem_j;
				while (isolates.contains(idx_j)) {
					tem_j++;
					idx_j++;
				}
				new_w[i][j] = w[idx_i][idx_j];
			}
		}

		return new_w;
	}

	public void setCluster(int k, int[] clusterLabel) {
		int sum = k + isolate_size;
		int size = clusterLabel.length;
		this.clusters = new Cluster[sum];
		for (int i = 0; i < sum; i++) {
			clusters[i] = new Cluster(this.id, i + "");
		}
		for (int i = 0; i < size; i++) {
			clusters[clusterLabel[i]].addNode(adjacents.get(i));
		}
		for (int i = 0; i < isolate_size; i++) {
			clusters[k + i].addNode(adjacents.get(size + i));
		}
	}

	public void setCluster() {
		this.clusters = new Cluster[isolate_size];
		for (int i = 0; i < isolate_size; i++) {
			clusters[i] = new Cluster(this.id, i + "");
		}
		for (int i = 0; i < isolate_size; i++) {
			clusters[i].addNode(adjacents.get(i));
		}
	}

	public String getId() {
		return id;
	}

	public List<Node> getAdjacents() {
		return adjacents;
	}

	public Cluster[] getClusters() {
		return clusters;
	}
}
