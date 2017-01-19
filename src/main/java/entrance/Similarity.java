package entrance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import conf.Config;

import java.util.Scanner;
import java.util.Set;

import utils.FileUtils;
import utils.Utils;

class Node implements Comparable<Node> {
	String node;
	double similarity;

	@Override
	public int compareTo(Node o) {
		if (o.similarity > this.similarity)
			return 1;
		else if (o.similarity < this.similarity)
			return -1;
		return 0;
	}

}

public class Similarity {

	public static int count = 20;

	public static void main(String[] args) throws IOException {

		Config.NodeVectorFile = "/home/zps/experiment/clusters/blogcatalog_friedge1_m0n5d128i10f1.cen.emb";
		Config.TrainFile = "/home/zps/experiment/clusters/blogcatalog_friedge1.cluster";
		Config.AsNode = 1;
		/*
		 * Config.initSimilarityEnv(args[0], args[1], args[2], args[3],
		 * Integer.parseInt(args[4]), Integer.parseInt(args[5])); count =
		 * Config.COUNT;
		 */

		Map<String, double[]> nodeVectors = new HashMap<>();
		FileUtils.readVector(Config.NodeVectorFile, nodeVectors);
		System.out.println("NodeVectorFile loaded.");

		Map<String, Set<String>> trainmap = new HashMap<>();
		if (Config.AsNode == 0) {
			FileUtils.readTrainFile0(Config.TrainFile, trainmap);
		} else {
			FileUtils.readTrainFile1(Config.TrainFile, trainmap);
		}
		System.out.println("TrainFile loaded.");
		Scanner scanner = new Scanner(System.in);
		System.out.println("please enter an node:");
		while (scanner.hasNext()) {
			String node = scanner.nextLine();
			if (node.equalsIgnoreCase("quit"))
				break;
			Node[] similars = new Node[count];
			for (int i = 0; i < count; i++) {
				similars[i] = new Node();
			}
			getSimilarNode(node, nodeVectors, similars);
			Arrays.sort(similars);
			// print(node, similars);
			int[] common = getCommonCount(node, similars, trainmap);
			print(node, similars, common);
		}
		scanner.close();
	}

	private static int[] getCommonCount(String node, Node[] similars, Map<String, Set<String>> trainmap) {
		int n = similars.length;
		int[] common = new int[n];
		Set<String> nodeset = trainmap.get(node);
		for (int i = 0; i < n; i++) {
			Set<String> set = trainmap.get(similars[i].node);
			for (String s : set) {
				if (nodeset.contains(s)) {
					common[i]++;
				}
			}
		}
		return common;
	}

	private static void getSimilarNode(String node, Map<String, double[]> nodeVectors, Node[] similars) {
		double[] nodeVector = nodeVectors.get(node);
		if (nodeVector == null) {
			System.out.println("there is no node : " + node);
			return;
		}
		for (Entry<String, double[]> entry : nodeVectors.entrySet()) {
			String _node = entry.getKey();
			if (node.equals(_node))
				continue;
			double[] _nodeVector = entry.getValue();
			if (_nodeVector == null)
				continue;
			double similarity = Utils.cosineSimilarity(nodeVector, _nodeVector);
			if (similarity > similars[0].similarity) {
				similars[0].node = _node;
				similars[0].similarity = similarity;
				heapAdjust(similars, 0);
			}
		}
	}

	private static void heapAdjust(Node[] similars, int idx) {
		int size = similars.length;
		int left = (idx << 1) + 1;
		int right = (idx << 1) + 2;
		int min = idx;
		if (left < size && similars[min].similarity > similars[left].similarity) {
			min = left;
		}
		if (right < size && similars[min].similarity > similars[right].similarity) {
			min = right;
		}
		if (min == idx) {
			return;
		} else {
			double tmp1 = similars[idx].similarity;
			similars[idx].similarity = similars[min].similarity;
			similars[min].similarity = tmp1;
			String tmp2 = similars[idx].node;
			similars[idx].node = similars[min].node;
			similars[min].node = tmp2;
			heapAdjust(similars, min);
		}
	}

	public static void print(String info, Node[] similars) {
		if (similars == null) {
			return;
		}
		for (int i = 0, size = similars.length; i < size; i++) {
			System.out.print(similars[i].node + "\t" + similars[i].similarity + "\r\n");
		}
		System.out.println();
	}

	public static void print(String info, Node[] similars, int[] commonline) {
		if (similars == null || commonline == null) {
			return;
		}
		for (int i = 0, size = similars.length; i < size; i++) {
			System.out.print(similars[i].node + "\t" + similars[i].similarity + "\t" + commonline[i] + "\r\n");
		}
		System.out.println();
	}
}
