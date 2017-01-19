package entrance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cluster.Clustering;
import cluster.KMeansFact;
import utils.FileUtils;

public class KmeansCluster {

	public static void main(String[] args) throws IOException {
		String rootPath = "/home/zps/experiment/";
		String nodeVectorFile = rootPath + "n2v_embeddings/jean_friedge1_n5d16i10b0f1_con_n2v_r10l10p2q1_m0n5w10d16i5_concFF.emb";
		String labelFile = rootPath + "labels/jean_friedge1_n5d16i10b0f1_con_n2v_r10l10p2q1_m0n5w10d16i5_concFF.label";

		List<String> nodes = new ArrayList<String>();
		double[][] vectors = FileUtils.readVector(nodeVectorFile, nodes);

		Clustering clustering = new KMeansFact();
		int[] labels = clustering.getClusterLabel(vectors, 6);
		FileUtils.writeCluster(labelFile, nodes, labels);
	}

}
