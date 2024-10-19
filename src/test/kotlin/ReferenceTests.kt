import org.example.cnf.CNF
import org.example.cnf.CNFFactory
import org.example.reference._3SatSolver
import kotlin.test.Test
import kotlin.test.assertFalse

class ReferenceTests {
    private val cnfFactory = CNFFactory()
    private val referenceSolver = _3SatSolver()

    private fun assertYesInstance(string: String) {
        val cnf: CNF = this.cnfFactory.createCNFByString(string)
        assert(this.referenceSolver.solve(cnf))
    }

    private fun assertNoInstance(string: String) {
        val cnf: CNF = this.cnfFactory.createCNFByString(string)
        assertFalse(this.referenceSolver.solve(cnf))
    }

    @Test
    fun yesInstanceWithLessThanThreeLiteralsTest() {
        this.assertYesInstance("(~A) ^ (B v C)")
    }

    @Test
    fun simpleYesInstanceTest1() {
        this.assertYesInstance("(A v ~B v ~C) ^ (~A v B v C)")
    }

    @Test
    fun trivialNoInstanceTest() {
        this.assertNoInstance("(A) ^ (~A)")
    }

    /**
     * @author no results after 1hr 11min
     */
    @Test
    fun simpleNoInstanceTest1() {
        this.assertNoInstance("(A v B) ^ (~B v A) ^ (~A v B) ^ (~A v ~B)")
    }
}
