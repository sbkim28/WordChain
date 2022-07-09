package kr.ac.snu.sbkim28.analyze.graph;

public class EdgeListWordGraph extends LinkedWordGraph<EdgedVertex>
{
    public EdgeListWordGraph() {
        super();
    }

    public EdgeListWordGraph(int length) {
        super(length);
    }

    @Override
    public boolean addVertex(char c) {
        return putVertex(c, new EdgedVertex(c));
    }

    @Override
    public boolean addEdge(char from, char to) {
        return addEdge(new String(new char[]{from, to}), from, to);
    }

    public boolean addEdge(String s){
        return addEdge(s, s.charAt(0), s.charAt(s.length() - 1));
    }

    private boolean addEdge(String s, char from, char to){
        EdgedVertex vFrom = getVertex(from);
        EdgedVertex vTo = getVertex(to);

        boolean ret = vFrom != null && vTo != null;
        if(ret)
            vFrom.add(new Edge(s, vFrom, vTo));
        return ret;
    }

    @Override
    public boolean removeEdge(char from, char to) {
        EdgedVertex vFrom = getVertex(from);
        EdgedVertex vTo = getVertex(to);
        boolean ret = vFrom != null;
        if(ret)
            ret = vFrom.remove(vTo);
        return ret;
    }

    public boolean removeEdge(String s){
        EdgedVertex vFrom = getVertex(s.charAt(0));
        return vFrom.remove(s);
    }
}
