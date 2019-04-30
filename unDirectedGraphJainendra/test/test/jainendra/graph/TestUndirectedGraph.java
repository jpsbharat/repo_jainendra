package test.jainendra.graph;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jainendra.graph.Graph;
import com.jainendra.graph.model.CycleBase;
import com.jainendra.graph.model.Cycles;
import com.jainendra.graph.model.DFSForest;
import com.jainendra.graph.model.DFSTree;
import com.jainendra.graph.model.NodeBase;
import com.jainendra.graph.model.NodeDFSTree;
import com.jainendra.graph.operation.search.cycle.CycleSearchUtil;
import com.jainendra.graph.operation.search.path.PathSearchUtil;
import com.jainendra.graph.operation.search.traversal.DFSUtil;

public class TestUndirectedGraph {

	public static void main(String[] args) {
		//testDFSForestAndTree();
		testGraph();
		//testCycle();
	}
	
	public static void testDFSForestAndTree() {
		Graph<String> graph = new Graph<String>();
		graph.add("1", "2", 5);
		graph.add("2", "3", 5);
		graph.add("3", "4", 5);
		graph.add("1", "4", 5);
		graph.add("3", "5", 5);
		graph.add("2", "5", 5);
		graph.add("4", "5", 5);
		graph.add("6", "8", 5);
		graph.add("9", "7", 5);
		graph.add("6", "10", 5);
		graph.add("10", "11", 5);
		System.out.println(graph);

		DFSUtil<String> dfsUtil = new DFSUtil<String>(graph);
		DFSForest<String> dfsForest = dfsUtil.getDFSForest();
		System.out.println(dfsForest);
		System.out.println();
		
		Map<DFSTree<String>, List<NodeDFSTree<String>>> aps  = dfsUtil.getArticulationPoints();
		for(Entry<DFSTree<String>, List<NodeDFSTree<String>>> entry : aps.entrySet()){
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());	
			System.out.println();	
		}
		
		System.out.println();
	}

	public static void testGraph() {
		Graph<String> graph = new Graph<String>();
		graph.add("1", "2", 5);
		graph.add("2", "3", 5);
		graph.add("3", "4", 5);
		graph.add("1", "4", 5);
		graph.add("3", "5", 5);
		graph.add("2", "5", 5);
		graph.add("4", "5", 5);
		System.out.println(graph);

		CycleSearchUtil<String> cycleSearchUtil = new CycleSearchUtil<String>(
				graph);
		PathSearchUtil<String> pathSearchUtil = new PathSearchUtil<String>(
				graph);

		List<List<NodeBase<String>>> paths1 = pathSearchUtil
				.getAllPaths("1", "5");
		List<List<NodeBase<String>>> paths2 = pathSearchUtil
				.getAllPaths("1", "4");

		printPaths(paths1);
		System.out.println();

		printPaths(paths2);
		System.out.println();

		CycleBase<String> cycleBase = cycleSearchUtil.getCycleBase();
		System.out.println(cycleBase);
		System.out.println();
		
		Cycles<String> cycles = cycleSearchUtil.getAllCycles();
		System.out.println(cycles);
		System.out.println();
		
		Cycles<String> allSimpleCycles = cycleSearchUtil.getAllSimpleCycles();
		System.out.println(allSimpleCycles);
		System.out.println();
	}

	public static void testCycle() {
		Graph<String> graph = createCyclicGraph();
		CycleSearchUtil<String> cycleSearchUtil = new CycleSearchUtil<String>(
				graph);
		CycleBase<String> cycleBase = cycleSearchUtil.getCycleBase();
		System.out.println(cycleBase);
		System.out.println();
		Cycles<String> cycles = cycleSearchUtil.getAllCycles();
		System.out.println(cycles);
	}

	public static Graph<String> createCyclicGraph() {
		Graph<String> graph = new Graph<String>();
		graph.add("a", "b", 1);
		graph.add("a", "c", 2);
		graph.add("a", "d", 2);
		graph.add("b", "x", 3);
		graph.add("x", "g", 4);
		graph.add("x", "m", 5);
		graph.add("m", "o", 6);
		graph.add("g", "o", 6);
		graph.add("x", "o", 6);

		graph.add("m", "n", 6);
		graph.add("n", "l", 6);
		graph.add("l", "j", 6);
		graph.add("j", "m", 6);

		graph.add("g", "a", 6);
		graph.add("o", "b", 6);

		return graph;
	}

	public static Graph<String> createACyclicGraph() {
		Graph<String> graph = new Graph<String>();
		graph.add("a", "b", 1);
		graph.add("a", "c", 2);
		graph.add("a", "d", 2);
		graph.add("b", "x", 3);
		graph.add("x", "g", 4);
		graph.add("x", "m", 5);
		graph.add("m", "o", 6);
		graph.add("g", "o", 6);
		graph.add("x", "o", 6);

		return graph;
	}

	public static Graph<String> createGraph() {
		Graph<String> graph = new Graph<String>();
		graph.add("a", "b", 1);
		graph.add("a", "c", 2);
		graph.add("a", "d", 2);
		graph.add("b", "e", 3);
		graph.add("c", "e", 4);
		graph.add("d", "e", 5);
		graph.add("e", "o", 6);
		graph.add("g", "o", 6);
		graph.add("x", "o", 6);

		return graph;
	}
	
	public static void printPaths(List<List<NodeBase<String>>> paths){
		for(List<NodeBase<String>> path : paths){
			StringBuilder sb = new StringBuilder();
			for(NodeBase<String> node : path){
				sb.append(node.getData() + "-");
			}
			sb.replace(sb.length()-1, sb.length(), "");
			System.out.println(sb.toString());
		}
	}
}
