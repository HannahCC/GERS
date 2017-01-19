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

class Edge implements Comparable<Edge> {
	String edge;
	double similarity;

	@Override
	public int compareTo(Edge o) {
		if (o.similarity > this.similarity)
			return 1;
		else if (o.similarity < this.similarity)
			return -1;
		return 0;
	}

}

public class SimilarityEdge {

	public static int count = 20;
	public static void main(String[] args) throws IOException {

		Config.GraphFile = "/home/zps/experiment/graphs/blogcatalog.edgelist";
		Config.EdgeVectorFile = "/home/zps/experiment/clusters/blogcatalog_closure2_n5d128i10b1f1.cen.edge.emb";
		Config.TrainFile = "/home/zps/experiment/clusters/blogcatalog_friedge1.cluster";
		Config.AsNode = 1;
		/*Config.initSimilarityEdgeEnv(args[0], args[1], args[2], args[3], Integer.parseInt(args[4]), 1_776
				Integer.parseInt(args[5]));
		count = Config.COUNT;
		*/
		Map<String, double[]> edgeVectors = new HashMap<>();

		List<Set<String>> trainlist = new ArrayList<>();

		FileUtils.readEdgeVector(Config.GraphFile, Config.EdgeVectorFile, edgeVectors);
		System.out.println("EdgeVectorFile loaded.");
		/*if (Config.AsNode == 0) {
			FileUtils.readTrainFile0(Config.TrainFile, trainlist);
		} else {
			FileUtils.readTrainFile1(Config.TrainFile, trainlist);
		}
		System.out.println("TrainFile loaded.");*/
		Scanner scanner = new Scanner(System.in);
		System.out.println("please enter an edge:");
		while (scanner.hasNext()) {
			String edge = scanner.nextLine();
			if (edge.equalsIgnoreCase("quit"))
				break;
			Edge[] similars = new Edge[count];
			for (int i = 0; i < count; i++) {
				similars[i] = new Edge();
			}
			getSimilarEdge(edge, edgeVectors, similars);
			Arrays.sort(similars);
			print(edge, similars);
			/*int[] commonline = getCommonLineCount(edge, similars, trainlist);
			print(edge, similars, commonline);*/
		}
		scanner.close();
	}

	private static int[] getCommonLineCount(String edge, Edge[] similars, List<Set<String>> trainlist) {
		int n = similars.length;
		int[] commonline = new int[n];
		for (Set<String> set : trainlist) {
			if (set.contains(edge)) {
				for (int i = 0; i < n; i++) {
					if (set.contains(similars[i].edge)) {
						commonline[i]++;
					}
				}
			}
		}
		return commonline;
	}

	private static void getSimilarEdge(String edge, Map<String, double[]> edgeVectors, Edge[] similars) {
		double[] edgeVector = edgeVectors.get(edge);
		if (edgeVector == null) {
			System.out.println("there is no edge : " + edge);
			return;
		}
		for (Entry<String, double[]> entry : edgeVectors.entrySet()) {
			String _edge = entry.getKey();
			if (edge.equals(_edge))
				continue;
			double[] _edgeVector = entry.getValue();
			if (_edgeVector == null)
				continue;
			double similarity = Utils.cosineSimilarity(edgeVector, _edgeVector);
			if (similarity > similars[0].similarity) {
				similars[0].edge = _edge;
				similars[0].similarity = similarity;
				heapAdjust(similars, 0);
			}
		}
	}

	private static void heapAdjust(Edge[] similars, int idx) {
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
			String tmp2 = similars[idx].edge;
			similars[idx].edge = similars[min].edge;
			similars[min].edge = tmp2;
			heapAdjust(similars, min);
		}
	}

	public static void print(String info, Edge[] similars) {
		if (similars == null) {
			return;
		}
		for (int i = 0, size = similars.length; i < size; i++) {
			System.out.print(similars[i].edge + "\t" + similars[i].similarity + "\r\n");
		}
		System.out.println();
	}
	public static void print(String info, Edge[] similars, int[] commonline) {
		if (similars == null || commonline == null) {
			return;
		}
		for (int i = 0, size = similars.length; i < size; i++) {
			System.out.print(similars[i].edge + "\t" + similars[i].similarity + "\t" + commonline[i] + "\r\n");
		}
		System.out.println();
	}
}
