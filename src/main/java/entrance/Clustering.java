package entrance;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import conf.Config;
import pojo.Node;
import smile.clustering.SpectralClustering;
import utils.FileUtils;

public class Clustering {

	public static void main(String[] args) throws IOException {
		Config.init(args[0], args[1]);
		Map<String, Node> nodes = new HashMap<String, Node>();
		FileUtils.readGraph(Config.GraphFile, nodes);
		int size = 0;
		for (Node node : nodes.values()) {
			double[][] subGraph = node.getSubGraph();
			if(subGraph == null){
				node.setCluster();
				System.out.println(node.getId()+" : "+node.getAdjacents().size()+":"+"all isolate.");
			}else if (subGraph.length <= 2) {
				node.setCluster(1, new int[subGraph.length]);
				System.out.println(node.getId()+" : "+node.getAdjacents().size()+":"+"only two vertex connected.");
			} else {
				size = (int) Math.sqrt(subGraph.length / 2) + 1;
				SpectralClustering spectralClustering = new SpectralClustering(subGraph, size);
				node.setCluster(size, spectralClustering.getClusterLabel());
			}
		}
		FileUtils.writeCluster(Config.ClusterFile, nodes.values());
	}
}
