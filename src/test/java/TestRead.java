import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class TestRead {

	public static void main(String[] args) throws IOException {
		String filename = "/home/zps/experiment/clusters/blogcatalog_spec_c_m0n5d128i10.edgevector";
		File file = new File(filename);
		BufferedReader brBufferedReader = new BufferedReader(new FileReader(file));
		String line = null;
		while(null!=(line = brBufferedReader.readLine())){
			System.out.println(line);
		}
		brBufferedReader.close();
	}
}
