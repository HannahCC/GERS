import java.util.HashSet;
import java.util.Set;


import pojo.Node;

public class TestNode {

	public static void main(String[] args) {
		Set<String> nodes = new HashSet<String>();
		nodes.add(new String("123"));
		if(nodes.contains(new Node("123"))){
			System.out.println("true");
		}
	}
}
