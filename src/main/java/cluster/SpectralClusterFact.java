package cluster;

import conf.Config;
import smile.clustering.SpectralClustering;
import utils.Utils;

public class SpectralClusterFact implements Clustering{

	@Override
	public int[] getClusterLabel(double[][] data, int size) {
		SpectralClustering clustering = new SpectralClustering(data, size);
		if(Config.DEBUG) Utils.print("SpectralClustering", clustering.getClusterLabel());
		return clustering.getClusterLabel();
	}

}
