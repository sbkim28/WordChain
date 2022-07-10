package kr.ac.snu.sbkim28.analyze.graph;

public class ListWordGraph extends LinkedWordGraph<ListVertex> {

    public ListWordGraph() {
        super();
    }
    public ListWordGraph(int length){
        super(length);
    }

    public static ListWordGraph createNew(Iterable<String> wordSet){
        ListWordGraph graph = new ListWordGraph();
        for (String s : wordSet){
            char initC = s.charAt(0);
            char finalC = s.charAt(s.length() - 1);
            graph.addVertex(initC);
            graph.addVertex(finalC);
            graph.addEdge(initC, finalC);
        }
        return graph;
    }

    @Override
    public boolean addVertex(char c){
        return putVertex(c, new ListVertex(c));
    }

    @Override
    public boolean addEdge(char from, char to){
        ListVertex vFrom = getVertex(from);
        ListVertex vTo = getVertex(to);
        boolean ret = vFrom != null && vTo != null;
        if(ret)
            vFrom.add(vTo);
        return ret;
    }

    @Override
    public boolean removeEdge(char from, char to){
        ListVertex vFrom = getVertex(from);
        ListVertex vTo = getVertex(to);
        boolean ret = vFrom != null;
        if(ret)
            ret = vFrom.remove(vTo);
        return ret;
    }
}
