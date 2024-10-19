import org.example.graph.Edge
import org.example.graph.Graph
import org.example.graph.Vertex
import org.example.graph._3ColoringSolver
import kotlin.test.Test
import kotlin.test.assertFalse

class _3ColSolverTests {
    private val _3coloringSolver = _3ColoringSolver()

    private val colorableGraph: Graph
    private val nonColorableGraph: Graph
    private val connected5: Graph
    private val linearGraph: Graph
    private val cycleGraph: Graph

    init {
        val vertices: MutableList<Vertex> = mutableListOf()
        for (index: Int in 0..5) {
            vertices.add(Vertex("$index"))
        }

        val colorableEdges: MutableList<Edge> = mutableListOf(
            Edge(vertices[0], vertices[1]),
            Edge(vertices[0], vertices[3]),
            Edge(vertices[1], vertices[3]),
            Edge(vertices[1], vertices[4]),
            Edge(vertices[3], vertices[4]),
            Edge(vertices[1], vertices[2]),
            Edge(vertices[2], vertices[4]),
            Edge(vertices[3], vertices[5]),
            Edge(vertices[4], vertices[5]),
        )
        this.colorableGraph = Graph(colorableEdges)

        val nonColorableEdges: MutableList<Edge> = mutableListOf(
            Edge(vertices[0], vertices[1]),
            Edge(vertices[0], vertices[3]),
            Edge(vertices[1], vertices[3]),
            Edge(vertices[1], vertices[4]),
            Edge(vertices[3], vertices[4]),
            Edge(vertices[1], vertices[2]),
            Edge(vertices[2], vertices[4]),
            Edge(vertices[3], vertices[5]),
            Edge(vertices[4], vertices[5]),

            Edge(vertices[0], vertices[2]),
            Edge(vertices[0], vertices[5]),
            Edge(vertices[2], vertices[5]),
            Edge(vertices[1], vertices[5]) // this edge makes the graph a no-instance
        )
        this.nonColorableGraph = Graph(nonColorableEdges)

        val connected5Edges: MutableList<Edge> = mutableListOf()
        for (v1: Int in 0..4) {
            for (v2: Int in (v1 + 1)..5) {
                connected5Edges.add(Edge(vertices[v1], vertices[v2]))
            }
        }
        this.connected5 = Graph(connected5Edges)

        val linearEdges: MutableList<Edge> = mutableListOf()
        val cycleEdges: MutableList<Edge> = mutableListOf()
        for (index: Int in 0..4) {
            linearEdges.add(Edge(vertices[index], vertices[index + 1]))
            cycleEdges.add(Edge(vertices[index], vertices[index + 1]))
        }
        this.linearGraph = Graph(linearEdges)

        cycleEdges.add(Edge(vertices[0], vertices[5]))
        this.cycleGraph = Graph(cycleEdges)
    }

    @Test
    fun linearYesInstanceTest() {
        assert(this._3coloringSolver.solve(this.linearGraph))
    }

    @Test
    fun simpleCycleYesInstanceTest() {
        assert(this._3coloringSolver.solve(this.cycleGraph))
    }

    @Test
    fun yesInstanceTest() {
        assert(this._3coloringSolver.solve(this.colorableGraph))
    }

    @Test
    fun simpleNoInstanceTest() {
        assertFalse(this._3coloringSolver.solve(this.nonColorableGraph))
    }

    @Test
    fun connectedNoInstanceTest() {
        assertFalse(this._3coloringSolver.solve(this.connected5))
    }
}
