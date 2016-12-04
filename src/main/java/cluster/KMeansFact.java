package cluster;

import conf.Config;
import smile.clustering.KMeans;
import utils.Utils;

public class KMeansFact implements Clustering {

	@Override
	public int[] getClusterLabel(double[][] data, int size) {
		KMeans clustering = new KMeans(data, size);
		if(Config.DEBUG) Utils.print("KMeans", clustering.getClusterLabel());
		return clustering.getClusterLabel();
	}

}
