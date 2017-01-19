package entrance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import conf.Config;
import utils.FileUtils;

public class GetEdgeVector {

	public static void main(String args[]) throws IOException {

		Config.initGetEdgeVecEnv(args);
		Map<String, double[]> nodeVector = new HashMap<String, double[]>();
		FileUtils.readVector(Config.NodeVectorFile, nodeVector);
		File graphFile = new File(Config.GraphFile);
		BufferedReader br = new BufferedReader(new FileReader(graphFile));
		File edgeVectorFile = new File(Config.EdgeVectorFile);
		BufferedWriter bw = new BufferedWriter(new FileWriter(edgeVectorFile));
		String line = null, item = null;
		String[] items = null;
		int id1, id2;
		int size = Config.SIZE;
		double[] edgeVector = new double[size];
		while (null != (line = br.readLine())) {
			items = line.split("\\s+");
			// bw.write(items[0]+ "_"+items[1]+" ");
			if (Config.EdgeMethod.equals("f0")) {
				id1 = Integer.parseInt(items[0]);
				id2 = Integer.parseInt(items[1]);
				item = id1 < id2 ? id1 + "_" + id2 : id2 + "_" + id1;
				edgeVector = nodeVector.get(item);
			} else if (Config.EdgeMethod.equals("f1")) {
				getEdgeVector1(nodeVector.get(items[0]), nodeVector.get(items[1]), edgeVector);
			} else if (Config.EdgeMethod.equals("f2")) {
				getEdgeVector2(nodeVector.get(items[0]), nodeVector.get(items[1]), edgeVector);
			} else if (Config.EdgeMethod.equals("f3")) {
				getEdgeVector3(nodeVector.get(items[0]), nodeVector.get(items[1]), edgeVector);
			} else if (Config.EdgeMethod.equals("f4")) {
				getEdgeVector4(nodeVector.get(items[0]), nodeVector.get(items[1]), edgeVector);
			} else {
				getEdgeVector1(nodeVector.get(items[0]), nodeVector.get(items[1]), edgeVector);
			}
			if (null == edgeVector) {
				edgeVector = new double[size]; // 没有向量的边设为全0
			}
			for (int i = 0; i < size; i++) {
				bw.write(Config.DF.format(edgeVector[i]) + " ");
			}
			bw.write("\r\n");
		}
		br.close();
		bw.flush();
		bw.close();
	}

	private static void getEdgeVector4(double[] vector1, double[] vector2, double[] edgeVector) {
		if (vector1 == null || vector2 == null) {
			edgeVector = null;
			return;
		}
		int size = Config.SIZE;
		for (int i = 0; i < size; i++) {
			edgeVector[i] = Math.pow(vector1[i] - vector2[i], 2);
		}
	}

	private static void getEdgeVector3(double[] vector1, double[] vector2, double[] edgeVector) {
		if (vector1 == null || vector2 == null) {
			edgeVector = null;
			return;
		}
		int size = Config.SIZE;
		for (int i = 0; i < size; i++) {
			edgeVector[i] = Math.abs(vector1[i] - vector2[i]);
		}
	}

	private static void getEdgeVector2(double[] vector1, double[] vector2, double[] edgeVector) {
		if (vector1 == null || vector2 == null) {
			edgeVector = null;
			return;
		}
		int size = Config.SIZE;
		for (int i = 0; i < size; i++) {
			edgeVector[i] = vector1[i] * vector2[i];
		}
	}

	private static void getEdgeVector1(double[] vector1, double[] vector2, double[] edgeVector) {
		if (vector1 == null || vector2 == null) {
			edgeVector = null;
			return;
		}
		int size = Config.SIZE;
		for (int i = 0; i < size; i++) {
			edgeVector[i] = (vector1[i] + vector2[i]) / 2;
		}
	}
}
