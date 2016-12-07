package conf;

import java.text.DecimalFormat;

public class Config {

	public static final boolean DEBUG = false;
	public static boolean DIRECTED = false;
	public static String CurPath = null;
	public static String GraphFile = null;
	public static String ClusterFile = null;
	public static String NodeVectorFile = null;
	public static String GraphVectorFile = null;
	public static int MAX_LENGTH = 500;
	public static int SIZE = 128;
	public static String ClusterMethod = null; // "spec"
	public static DecimalFormat DF = new java.text.DecimalFormat("#.######");
	public static boolean INCRE = false;

	public static void initClusterEnv(String curPath, String dataSet, String clusterMethod) {
		ClusterMethod = clusterMethod;
		GraphFile = curPath + "graphs/" + dataSet + ".edgelist";
		ClusterFile = curPath + "clusters/" + dataSet + "_" + clusterMethod + ".cluster";
		System.out.println("the graph file is : " + GraphFile);
		System.out.println("the cluster file is : " + ClusterFile);
	}

	public static void initClusterEnv(String curPath, String dataSet, String clusterMethod, String w2vparam, int iter) {
		ClusterMethod = clusterMethod;
		GraphFile = curPath + "graphs/" + dataSet + ".edgelist";
		if (iter == 0) {
			NodeVectorFile = curPath + "clusters/" + dataSet + "_" + clusterMethod + "_" + w2vparam + ".emb";
		} else {
			NodeVectorFile = curPath + "clusters/" + dataSet + "_" + clusterMethod + "_" + w2vparam + "_i" + iter
					+ ".emb";
		}
		ClusterFile = curPath + "clusters/" + dataSet + "_" + clusterMethod + "_" + w2vparam + "_i" + (iter + 1)
				+ ".cluster";
		System.out.println("the graph file is : " + GraphFile);
		System.out.println("the node vector file is : " + NodeVectorFile);
		System.out.println("the cluster file is : " + ClusterFile);
	}

	public static void initGetEdgeVecEnv(String curPath, String dataSet, String clusterMethod, String w2vparam,
			int iter) {
		ClusterMethod = clusterMethod;
		GraphFile = curPath + "graphs/" + dataSet + ".edgelist";
		if (iter == 0) {
			NodeVectorFile = curPath + "clusters/" + dataSet + "_" + clusterMethod + "_" + w2vparam + ".emb";
			GraphVectorFile = curPath + "clusters/" + dataSet + "_" + clusterMethod + "_" + w2vparam + ".edgevector";
		} else {
			NodeVectorFile = curPath + "clusters/" + dataSet + "_" + clusterMethod + "_" + w2vparam + "_i" + iter
					+ ".emb";
			GraphVectorFile = curPath + "clusters/" + dataSet + "_" + clusterMethod + "_" + w2vparam + "_i" + iter
					+ ".edgevector";
		}
		System.out.println("the graph file is : " + GraphFile);
		System.out.println("the node vector file is : " + NodeVectorFile);
		System.out.println("the graph vector file is : " + GraphVectorFile);
	}
}
