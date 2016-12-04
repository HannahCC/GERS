package utils;

public class Utils {

	public static double cosineSimilarity(Double[] vec1, Double[] vec2) {
		double dotProduct = dotProduct(vec1, vec2);
		double normVec1 = Math.sqrt(dotProduct(vec1, vec1));
		double normVec2 = Math.sqrt(dotProduct(vec2, vec2));
		return Math.abs(dotProduct / (normVec1 * normVec2));
	}

	private static <K> double dotProduct(Double[] vec1, Double[] vec2) {
		double dotProduct = 0.0;
		for (int i = 0, size = vec1.length; i < size; i++) {
			dotProduct += vec1[i] * vec2[i];
		}
		return dotProduct;
	}

	public static double[][] copy(double[][] src) {
		int size = src.length;
		double[][] copy = new double[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				copy[i][j] = src[i][j];
			}
		}
		return copy;

	}

	public static void print(String info, int[] label) {
		System.out.print(info + ":");
		for (int i = 0, size = label.length; i < size; i++) {
			System.out.print(label[i] + ",");
		}
		System.out.println();
	}
}
