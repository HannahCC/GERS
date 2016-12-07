package entrance;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cluster.Clustering;
import cluster.DENCLUEFact;
import cluster.HCWardFact;
import cluster.KMeansFact;
import cluster.SpectralClusterFact;
import conf.Config;
import pojo.Node;
import utils.FileUtils;

public class EgoNetworkCluster {

	public static void main(String[] args) throws IOException {
		if (args.length == 3) {
			Config.initClusterEnv(args[0], args[1], args[2]);
		} else if (args.length == 5) {
			Config.initClusterEnv(args[0], args[1], args[2], args[3], Integer.parseInt(args[4]));
		} else {
			System.out.println("the params is wrong.");
			return;
		}
		String clusterMethod = Config.ClusterMethod;
		Map<String, Node> nodes = new HashMap<String, Node>();
		FileUtils.readGraph(Config.GraphFile, nodes);
		Map<String, Double[]> vectors = null;
		if (Config.NodeVectorFile != null) {
			vectors = new HashMap<String, Double[]>();
			FileUtils.readVector(Config.NodeVectorFile, vectors);
		}
		Clustering clustering = null;
		switch (clusterMethod) {
		case "spec":
			clustering = new SpectralClusterFact();
			break;
		case "hcward":
			clustering = new HCWardFact();
			break;
		case "kmeans":
			clustering = new KMeansFact();
			break;
		case "denclue":
			clustering = new DENCLUEFact();
			break;
		case "fri":
			getFri(nodes);
			break;
		case "friedge":
			getFriEdge(nodes);
			break;
		default:
			System.out.println("wrong cluster method.");
			return;
		}
		if (clusterMethod.equals("fri")||clusterMethod.equals("friedge")) {
			return;
		}
		int i = 0;
		int size = 0, isolateNode = 0, onlyTwoNode = 0;
		for (Node node : nodes.values()) {
			if (Config.DEBUG)
				System.out.print(i + "_");
			double[][] subGraph = null;
			if (vectors != null) {
				subGraph = node.getSubGraph(vectors);
			} else {
				subGraph = node.getSubGraph();
			}
			if (subGraph == null) {
				node.setCluster();
				isolateNode++;
			} else if (subGraph.length <= 2) {
				node.setCluster(1, new int[subGraph.length]);
				onlyTwoNode++;
			} else {
				size = (int) Math.sqrt(subGraph.length / 2) + 1;
				if (Config.DEBUG)
					System.out.println(size);
				if (i == 94) {
					System.out.println(subGraph);
				}
				node.setCluster(subGraph.length, clustering.getClusterLabel(subGraph, size));
			}
			if (i++ % 1000 == 0)
				System.out.println(i + "/" + nodes.size());
		}
		System.out.println(isolateNode + "node's adjacents are isolated.");
		System.out.println(onlyTwoNode + "node's adjacents with only two vertex connected.");
		FileUtils.writeCluster(Config.ClusterFile, nodes.values());
	}

	private static void getFriEdge(Map<String, Node> nodes) throws IOException {
		FileUtils.writeFriendsEdge(Config.ClusterFile, nodes.values());
	}

	private static void getFri(Map<String, Node> nodes) throws IOException {
		FileUtils.writeFriends(Config.ClusterFile, nodes.values());
	}

}
