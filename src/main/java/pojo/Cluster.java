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
		int type = Config.AsNode;
		int size = nodes.size();
		int id1 = 0, id2;
		String item;
		if (size == 0)
			return null;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(id + " ");

		id1 = Integer.parseInt(nodeId);
		for (int i = 0; i < size; i++) {
			if ((count++ % max) == 0) {
				sBuilder.deleteCharAt(sBuilder.length() - 1);
				sBuilder.append("\r\n" + id + " ");
			}
			id2 = Integer.parseInt(nodes.get(i).getId());
			if (type == 0) {
				item = id1 < id2 ? id1 + "_" + id2 + " " : id2 + "_" + id1 + " ";
			} else {
				item = id1 < id2 ? id1 + " " + id2 + " " : id2 + " " + id1 + " ";
			}
			sBuilder.append(item);
		}
		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				if (nodes.get(i).adjacents.contains(nodes.get(j))) {
					if ((count++ % max) == 0) {
						sBuilder.deleteCharAt(sBuilder.length() - 1);
						sBuilder.append("\r\n" + id + " ");
					}
					id1 = Integer.parseInt(nodes.get(i).getId());
					id2 = Integer.parseInt(nodes.get(j).getId());
					if (type == 0) {
						item = id1 < id2 ? id1 + "_" + id2 + " " : id2 + "_" + id1 + " ";
					} else {
						item = id1 < id2 ? id1 + " " + id2 + " " : id2 + " " + id1 + " ";
					}
					sBuilder.append(item);
				}
			}
		}

		return sBuilder.toString().trim();
	}

}
