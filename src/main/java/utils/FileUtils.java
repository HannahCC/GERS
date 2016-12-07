package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

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
	public static void readVector(String nodeVectorFile, Map<String, Double[]> nodeVectors) throws IOException {
		File f = new File(nodeVectorFile);
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line = null;
		String[] items = null;
		line = br.readLine();
		int size = Integer.parseInt(line.split("\\s+")[1]);
		Config.SIZE = size;
		while (null != (line = br.readLine())) {
			items = line.split("\\s+");
			String node = items[0];
			Double[] nodeVector = new Double[size];
			for (int i = 0; i < size; i++) {
				nodeVector[i] = Double.parseDouble(items[i + 1]);
			}
			nodeVectors.put(node, nodeVector);
		}
		br.close();
	}
	public static void writeCluster(String filename, Collection<Node> collection) throws IOException {
		File file = new File(filename);
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		for (Node node : collection) {
			for (Cluster cluster : node.getClusters()) {
				String clusterStr = cluster.toString();
				if (null != clusterStr) {
					bw.write(cluster.toString() + "\r\n");
				}
			}
		}
		bw.flush();
		bw.close();
	}

	public static void writeFriends(String filename, Collection<Node> collection) throws IOException {
		File file = new File(filename);
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		for (Node node : collection) {
			bw.write(node.toString()+"\r\n");
		}
		bw.flush();
		bw.close();
	}
	public static void writeFriendsEdge(String filename, Collection<Node> collection) throws IOException {
		File file = new File(filename);
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		for (Node node : collection) {
			bw.write(node.getEdge()+"\r\n");
		}
		bw.flush();
		bw.close();
	}
	


}
