package conf;

import java.text.DecimalFormat;

public class Config {

	public static final boolean DEBUG = false;
	public static String CurPath = null;
	public static String GraphFile = null;
	public static String ClusterFile = null;
	public static String NodeVectorFile = null;
	public static String EdgeVectorFile = null;
	public static String TrainFile = null;
	public static int MAX_LENGTH = 500;
	public static int SIZE = 128;
	public static String ClusterMethod = null; // "spec"
	public static String EdgeMethod = null; // "spec"
	public static int AsNode = 1;
	public static DecimalFormat DF = new java.text.DecimalFormat("#.######");
	public static boolean INCRE = false;
	public static int COUNT = 20;

	public static void initClusterEnv(String curPath, String dataSet, String clusterMethod, int asNode) {
		ClusterMethod = clusterMethod;
		AsNode = asNode;
		GraphFile = curPath + "graphs/" + dataSet + ".edgelist";
		ClusterFile = curPath + "clusters/" + dataSet + "_" + clusterMethod + asNode + ".cluster";
		System.out.println("the graph file is : " + GraphFile);
		System.out.println("the cluster file is : " + ClusterFile);
	}

	public static void initClusterEnv(String curPath, String graphFile, String clusterFile, String clusterMethod,
			int asNode) {
		ClusterMethod = clusterMethod;
		AsNode = asNode;
		GraphFile = curPath + graphFile + ".edgelist";
		ClusterFile = curPath + clusterFile + ".cluster";
		System.out.println("the graph file is : " + GraphFile);
		System.out.println("the cluster file is : " + ClusterFile);
	}

	public static void initClusterEnv(String curPath, String dataSet, String clusterMethod, int asNode, String w2vparam,
			int iter) {
		ClusterMethod = clusterMethod;
		AsNode = asNode;
		GraphFile = curPath + "graphs/" + dataSet + ".edgelist";
		if (iter == 0) {
			NodeVectorFile = curPath + "clusters/" + dataSet + "_" + clusterMethod + "_" + w2vparam + ".emb";
		} else {
			NodeVectorFile = curPath + "clusters/" + dataSet + "_" + clusterMethod + "_" + w2vparam + "_i" + iter
					+ ".emb";
		}
		ClusterFile = curPath + "clusters/" + dataSet + "_" + clusterMethod + asNode + "_" + w2vparam + "_i"
				+ (iter + 1) + ".cluster";
		System.out.println("the graph file is : " + GraphFile);
		System.out.println("the node vector file is : " + NodeVectorFile);
		System.out.println("the cluster file is : " + ClusterFile);
	}

	public static void initGetEdgeVecEnv(String curPath, String graphFile, String nodeVectorFile, String method,
			String edgeVectorFile) {

		EdgeMethod = method;
		GraphFile = curPath + graphFile;
		NodeVectorFile = curPath + nodeVectorFile + ".emb";
		EdgeVectorFile = curPath + edgeVectorFile + ".edge.emb";

		System.out.println("the operator is : " + EdgeMethod);
		System.out.println("the graph file is : " + GraphFile);
		System.out.println("the node vector file is : " + NodeVectorFile);
		System.out.println("the graph vector file is : " + EdgeVectorFile);
	}

	public static void initSimilarityEdgeEnv(String curPath, String graphFile, String edgeVectorFile, String trainFile,
			int asNode, int count) {
		GraphFile = curPath + graphFile;
		EdgeVectorFile = curPath + edgeVectorFile;
		TrainFile = curPath + trainFile;
		AsNode = asNode;
		COUNT = count;
	}

	public static void initGetEdgeVecEnv(String[] args) {

		String curPath = args[0];
		GraphFile = curPath + args[1] + ".edgelist";
		NodeVectorFile = curPath + args[2] + ".emb";
		EdgeMethod = args[3];
		if (args.length == 4) {
			EdgeVectorFile = curPath + args[2] + ".edge.emb";
		} else if (args.length == 5) {
			EdgeVectorFile = curPath + args[4] + ".edge.emb";
		} else {
			System.out.println("parameters error.");
			System.exit(0);
		}

		System.out.println("the operator is : " + EdgeMethod);
		System.out.println("the graph file is : " + GraphFile);
		System.out.println("the node vector file is : " + NodeVectorFile);
		System.out.println("the graph vector file is : " + EdgeVectorFile);
	}
}
