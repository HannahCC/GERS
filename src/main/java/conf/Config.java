package conf;

import java.text.DecimalFormat;

public class Config {

	public static boolean DIRECTED = false;
	public static String CurPath = null;
	public static String GraphFile = null;
	public static String ClusterFile = null;
	public static String NodeVectorFile = null;
	public static String GraphVectorFile = null;
	public static int MAX_LENGTH = 500;
	public static int SIZE = 128;
	public static DecimalFormat DF = new   java.text.DecimalFormat("#.######");   
	
	public static void init(String curPath, String dataSet){
		GraphFile =  curPath + "graphs/" + dataSet + ".edgelist";
		ClusterFile = curPath + "clusters/" + dataSet + "_spec.cluster";
		System.out.println("the graph file is : " + GraphFile);
		System.out.println("the cluster file is : " + ClusterFile);
	}
	

	public static void init(String curPath, String dataSet, String w2vparam){
		GraphFile =  curPath + "graphs/" + dataSet + ".edgelist";
		NodeVectorFile = curPath + "clusters/" + dataSet + "_spec_"+w2vparam+".emb";
		GraphVectorFile = curPath + "clusters/" + dataSet + "_spec_"+w2vparam+".edgevector";
		System.out.println("the graph file is : " + GraphFile);
		System.out.println("the node vector file is : " + NodeVectorFile);
		System.out.println("the graph vector file is : " + GraphVectorFile);
	}
}
