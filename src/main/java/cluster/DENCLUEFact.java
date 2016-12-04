package cluster;

import conf.Config;
import utils.MyDENCLUE;
import utils.Utils;

public class DENCLUEFact implements Clustering {

	@Override
	public int[] getClusterLabel(double[][] data, int size) {
		MyDENCLUE clustering = new MyDENCLUE(data, 1, size);
		if(Config.DEBUG) Utils.print("MyDENCLUE", clustering.getClusterLabel());
		return clustering.getClusterLabel();
	}

}
