package pojo;

import java.util.ArrayList;
import java.util.List;

import conf.Config;

public class Cluster {
	String nodeId;
	String id;
	List<Node> nodes;// 该簇有哪些节点

	public Cluster(String nodeId, String id) {
		this.nodeId = nodeId;
		this.id = nodeId + "_" + id;
		this.nodes = new ArrayList<Node>();
	}

	public void addNode(Node node) {
		nodes.add(node);
	}

	public String getId() {
		return id;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	// TODO
	@Override
	public String toString() {
		int count = 1;
		int max = Config.MAX_LENGTH;
		int size = nodes.size();
		if (size == 0)
			return null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(id + " ");
		for (int i = 0; i < size; i++) {
			sBuilder.append(nodeId + " " + nodes.get(i).getId() + " ");
			if ((count++ % max) == 0) {
				sBuilder.deleteCharAt(sBuilder.length() - 1);
				sBuilder.append("\r\n" + id + " ");
			}
		}
		if (Config.DIRECTED) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (i == j)
						continue;
					if (nodes.get(i).adjacents.contains(nodes.get(j))) {
						sBuilder.append(nodes.get(i).getId() + " " + nodes.get(j).getId() + " ");
						if ((count++ % max) == 0) {
							sBuilder.deleteCharAt(sBuilder.length() - 1);
							sBuilder.append("\r\n" + id + " ");
						}
					}
				}
			}
		} else {
			for (int i = 0; i < size; i++) {
				for (int j = i + 1; j < size; j++) {
					if (nodes.get(i).adjacents.contains(nodes.get(j))) {
						sBuilder.append(nodes.get(i).getId() + " " + nodes.get(j).getId() + " ");
						if ((count++ % max) == 0) {
							sBuilder.deleteCharAt(sBuilder.length() - 1);
							sBuilder.append("\r\n" + id + " ");
						}
					}
				}
			}
		}
		return sBuilder.toString().trim();
	}

}
