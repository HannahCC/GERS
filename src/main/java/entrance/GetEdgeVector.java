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
		
		Config.initGetEdgeVecEnv(args[0], args[1], args[2], args[3], Integer.parseInt(args[4]));
		Map<String, Double[]> nodeVector = new HashMap<String, Double[]>();
		FileUtils.readVector(Config.NodeVectorFile, nodeVector);
		File graphFile = new File(Config.GraphFile);
		BufferedReader br = new BufferedReader(new FileReader(graphFile));
		File graphVectorFile = new File(Config.GraphVectorFile);
		BufferedWriter bw = new BufferedWriter(new FileWriter(graphVectorFile));
		String line = null;
		String[] items = null;
		int size = Config.SIZE;
		double[] edgeVector = new double[size];
		while (null != (line = br.readLine())) {
			items = line.split("\\s+");
			getEdgeVector1(nodeVector.get(items[0]), nodeVector.get(items[1]), edgeVector);
			for (int i = 0; i < size; i++) {
				bw.write(Config.DF.format(edgeVector[i]) + " ");
			}
			bw.write("\r\n");
		}
		br.close();
		bw.flush();
		bw.close();
	}

	private static void getEdgeVector1(Double[] vector1, Double[] vector2, double[] edgeVector) {
		int size = Config.SIZE;
		for (int i = 0; i < size; i++) {
			edgeVector[i] = (vector1[i] + vector2[i]) / 2;
		}
	}
}
