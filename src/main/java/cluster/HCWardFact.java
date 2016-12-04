package cluster;

import conf.Config;
import smile.clustering.HierarchicalClustering;
import smile.clustering.linkage.WardLinkage;
import utils.Utils;

public class HCWardFact implements Clustering {

	@Override
	public int[] getClusterLabel(double[][] data, int size) {
		HierarchicalClustering clustering = new HierarchicalClustering(new WardLinkage(data));
		if(Config.DEBUG) Utils.print("HC-WardLinkage", clustering.partition(size));
		return clustering.partition(size);
	}

}
