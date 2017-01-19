package pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import conf.Config;
import utils.Utils;

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

		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				if (adjacents.get(i).adjacents.contains(adjacents.get(j))) {
					w[j][i] = w[i][j] = 1;
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

	public double[][] getSubGraph(Map<String, double[]> vectors) {
		double[][] w = getSubGraph();
		if (w == null)
			return w;
		int size = w.length;
		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				if (w[i][j] == 1) {
					w[j][i] = w[i][j] = Utils.cosineSimilarity(vectors.get(adjacents.get(i).id),
							vectors.get(adjacents.get(j).id));
				}
			}
		}
		return w;
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

	@Override
	public String toString() {
		int count = 1;
		int max = Config.MAX_LENGTH;
		int size = adjacents.size();
		if (size == 0)
			return null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(id + " ");

		for (int i = 0; i < size; i++) {
			if ((count++ % max) == 0) {
				sBuilder.deleteCharAt(sBuilder.length() - 1);
				sBuilder.append("\r\n" + id + " ");
			}
			sBuilder.append(adjacents.get(i).getId() + " ");
		}

		return sBuilder.toString().trim();
	}

	public String getEdge() {
		int count = 1;
		int max = Config.MAX_LENGTH;
		int type = Config.AsNode;
		int size = adjacents.size();
		if (size == 0)
			return null;
		int id1, id2;
		String item = null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(id + " ");

		id1 = Integer.parseInt(id);
		for (int i = 0; i < size; i++) {
			if ((count++ % max) == 0) {
				sBuilder.deleteCharAt(sBuilder.length() - 1);
				sBuilder.append("\r\n" + id + " ");
			}
			id2 = Integer.parseInt(adjacents.get(i).getId());
			if (type == 0) {
				item = id1 < id2 ? id1 + "_" + id2 + " " : id2 + "_" + id1 + " ";
			} else {
				item = id1 < id2 ? id1 + " " + id2 + " " : id2 + " " + id1 + " ";
			}
			sBuilder.append(item);
		}
		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				if (adjacents.get(i).getAdjacents().contains(adjacents.get(j))) {
					if ((count++ % max) == 0) {
						sBuilder.deleteCharAt(sBuilder.length() - 1);
						sBuilder.append("\r\n" + id + " ");
					}
					id1 = Integer.parseInt(adjacents.get(i).getId());
					id2 = Integer.parseInt(adjacents.get(j).getId());
					if (type == 0) {
						item = id1 < id2 ? id1 + "_" + id2 + " " : id2 + "_" + id1 + " ";
					} else {
						item = id1 < id2 ? id1 + " " + id2 + " " : id2 + " " + id1 + " ";
					}
					sBuilder.append(item);
				}
			}
		}
		return sBuilder.toString().trim();
	}

	public static String getClosure(Node node1, Node node2, String src) {
		boolean flag = false;
		int max = Config.MAX_LENGTH;
		int type = Config.AsNode;
		int id1, id2;
		int count = 1;

		String item = null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(src + " ");
		for (Node adj : node1.getAdjacents()) {
			if (node2.getAdjacents().contains(adj)) {
				flag = true;
				if ((count++ % max) == 0) {
					sBuilder.deleteCharAt(sBuilder.length() - 1);
					sBuilder.append("\r\n" + src + " ");
				}
				id1 = Integer.parseInt(adj.getId());
				// edge node1-adj
				id2 = Integer.parseInt(node1.getId());
				if (type == 0) {
					item = id1 < id2 ? id1 + "_" + id2 + " " : id2 + "_" + id1 + " ";
				} else {
					item = id1 < id2 ? id1 + " " + id2 + " " : id2 + " " + id1 + " ";
				}
				sBuilder.append(item);
				// edge node2-adj
				if ((count++ % max) == 0) {
					sBuilder.deleteCharAt(sBuilder.length() - 1);
					sBuilder.append("\r\n" + src + " ");
				}
				id2 = Integer.parseInt(node2.getId());
				if (type == 0) {
					item = id1 < id2 ? id1 + "_" + id2 + " " : id2 + "_" + id1 + " ";
				} else {
					item = id1 < id2 ? id1 + " " + id2 + " " : id2 + " " + id1 + " ";
				}
				sBuilder.append(item);
			}
		}
		if (flag) {
			return sBuilder.toString().trim();
		} else {
			return null;
		}
	}
}
