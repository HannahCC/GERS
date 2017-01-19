package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import conf.Config;
import pojo.Cluster;
import pojo.Node;

public class FileUtils {

	public static void readGraph(String graphFile, Map<String, Node> nodes) throws IOException {
		File f = new File(graphFile);
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line = null;
		Node node0 = null, node1 = null;
		while (null != (line = br.readLine())) {
			String[] items = line.split("\\s+");

			if (!nodes.containsKey(items[1])) {
				node1 = new Node(items[1]);
				nodes.put(items[1], node1);
			} else {
				node1 = nodes.get(items[1]);
			}

			if (!nodes.containsKey(items[0])) {
				node0 = new Node(items[0]);
				nodes.put(items[0], node0);
				node0.addAdjacent(node1);
			} else {
				node0 = nodes.get(items[0]);
				node0.addAdjacent(node1);
			}
		}
		br.close();
	}

	public static void readVector(String vectorFile, Map<String, double[]> vectors) throws IOException {
		File f = new File(vectorFile);
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line = null;
		String[] items = null;
		line = br.readLine();
		int size = Integer.parseInt(line.split("\\s+")[1]);
		Config.SIZE = size;
		while (null != (line = br.readLine())) {
			items = line.split("\\s+");
			String node = items[0];
			double[] vector = new double[size];
			for (int i = 0; i < size; i++) {
				vector[i] = Double.parseDouble(items[i + 1]);
			}
			vectors.put(node, vector);
		}
		br.close();
	}

	public static double[][] readVector(String vectorFile, List<String> nodes) throws IOException {
		File f = new File(vectorFile);
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line = br.readLine();
		String[] items = line.split("\\s+");
		int size1 = Integer.parseInt(items[0]);
		int size2 = Integer.parseInt(items[1]);
		double[][] vectors = new double[size1][size2];
		int c = 0;
		while (null != (line = br.readLine())) {
			items = line.split("\\s+");
			String node = items[0];
			nodes.add(node);
			for (int i = 0; i < size2; i++) {
				vectors[c][i] = Double.parseDouble(items[i + 1]);
			}
			c++;
		}
		br.close();
		return vectors;
	}

	public static void writeCluster(String filename, Collection<Node> collection) throws IOException {
		File file = new File(filename);
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		for (Node node : collection) {
			for (Cluster cluster : node.getClusters()) {
				String clusterStr = cluster.toString();
				if (null != clusterStr) {
					bw.write(clusterStr + "\r\n");
				}
			}
		}
		bw.flush();
		bw.close();
	}

	public static void writeFriends(String filename, Collection<Node> collection) throws IOException {
		File file = new File(filename);
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		String friedge;
		for (Node node : collection) {
			friedge = node.toString();
			if (null != friedge) bw.write( friedge+ "\r\n");
		}
		bw.flush();
		bw.close();
	}

	public static void writeFriendsEdge(String filename, Collection<Node> collection) throws IOException {
		File file = new File(filename);
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		String friedge;
		for (Node node : collection) {
			friedge = node.getEdge();
			if (null != friedge) bw.write( friedge+ "\r\n");
		}
		bw.flush();
		bw.close();
	}

	public static void writeClosureEdge(String filename, Collection<Node> collection) throws IOException {
		File file = new File(filename);
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		String item = null;
		String src = null;
		int id1, id2;
		int type = Config.AsNode;
		Set<String> set = new HashSet<>();

		for (Node node : collection) {
			for (Node node2 : node.getAdjacents()) {
				id1 = Integer.parseInt(node.getId());
				id2 = Integer.parseInt(node2.getId());
				if (type == 0 || type == 2) {
					src = id1 < id2 ? id1 + "_" + id2 + " " : id2 + "_" + id1 + " ";
				} else {
					src = id1 < id2 ? id1 + " " + id2 + " " : id2 + " " + id1 + " ";
				}
				if (set.contains(src))
					continue;
				set.add(src);
				item = Node.getClosure(node, node2, src);
				if (null != item) {
					bw.write(item + "\r\n");
				}
			}

		}
		bw.flush();
		bw.close();
	}

	public static void readEdgeVector(String graphFile, String edgeVectorFile, Map<String, double[]> edgeVectors)
			throws IOException {
		File file1 = new File(graphFile);
		BufferedReader br1 = new BufferedReader(new FileReader(file1));
		File file2 = new File(edgeVectorFile);
		BufferedReader br2 = new BufferedReader(new FileReader(file2));
		String line = null, item = null;
		while (null != (line = br1.readLine())) {
			String[] items = line.split("\\s+");
			int id1 = Integer.parseInt(items[0]);
			int id2 = Integer.parseInt(items[1]);
			item = id1 < id2 ? id1 + "_" + id2 : id2 + "_" + id1;
			line = br2.readLine();
			if (edgeVectors.containsKey(item)) {
				continue; // 重复边
			} else {
				items = line.split("\\s+");
				double[] vector = new double[items.length];
				for (int i = 0, size = items.length; i < size; i++) {
					vector[i] = Double.parseDouble(items[i]);
				}
				edgeVectors.put(item, vector);
			}
		}
		br1.close();
		br2.close();
	}

	public static void readTrainFile0(String trainFile, List<Set<String>> trainlist) throws IOException {
		File file = new File(trainFile);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		while (null != (line = br.readLine())) {
			String[] items = line.split("\\s+");
			Set<String> set = new HashSet<>();
			for (int i = 1, size = items.length; i < size; i++) {
				set.add(items[i]);
			}
			trainlist.add(set);
		}
		br.close();
	}

	public static void readTrainFile1(String trainFile, List<Set<String>> trainlist) throws IOException {
		File file = new File(trainFile);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null, item = null;
		while (null != (line = br.readLine())) {
			String[] items = line.split("\\s+");
			Set<String> set = new HashSet<>();
			for (int i = 1, size = items.length; i < size; i += 2) {
				item = items[i] + "_" + items[i + 1];
				set.add(item);
			}
			trainlist.add(set);
		}
		br.close();
	}

	public static void readTrainFile0(String trainFile, Map<String, Set<String>> trainmap) throws IOException {
		File file = new File(trainFile);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		while (null != (line = br.readLine())) {
			String[] items = line.split("\\s+");
			Set<String> set = new HashSet<>();
			for (int i = 1, size = items.length; i < size; i++) {
				set.add(items[i]);
			}
			trainmap.put(items[0], set);
		}
		br.close();
	}

	public static void readTrainFile1(String trainFile, Map<String, Set<String>> trainmap) throws IOException {
		File file = new File(trainFile);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null, item = null;
		while (null != (line = br.readLine())) {
			String[] items = line.split("\\s+");
			Set<String> set = new HashSet<>();
			for (int i = 1, size = items.length; i < size; i += 2) {
				item = items[i] + "_" + items[i + 1];
				set.add(item);
			}
			trainmap.put(items[0], set);
		}
		br.close();
	}

	public static void writeCluster(String labelFile, List<String> nodes, int[] labels) throws IOException {
		File file = new File(labelFile);
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		for (int i = 0, size = nodes.size(); i < size; i++) {
			bw.write(nodes.get(i) + "\t" + labels[i] + "\r\n");
		}
		bw.flush();
		bw.close();
	}

}
