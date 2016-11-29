
import smile.clustering.SpectralClustering;


public class TestClustering {

	public static void main(String[] args){

        double[][] vals = { { 0, 4, 0,0 }, { 4,0,0,0 }, {0,0,0,0} , {0,0,0,0}};
		SpectralClustering spectralClustering = new SpectralClustering(vals, 4);
		int[] label = spectralClustering.getClusterLabel();
		for(int i=0,s = label.length;i<s;i++ ){
		System.out.print(label[i]+",");
		}
		System.out.println(spectralClustering.toString());
	}
}
