import org.example.cnf.CNF
import org.example.cnf.CNFFactory
import org.example.cnf.Clause
import org.example.cnf.Literal
import org.example.cnf.Variable
import kotlin.test.Test

const val YES_INSTANCE_1 = "(A v B v ~C) ^ (~D v XY) ^ (~B v ~XY)"
const val YES_INSTANCE_2 = "(A) ^ (~B)"

class CNFParserTests {
    private val cnfFactory = CNFFactory()

    @Test
    fun validFormulaTest1() {
        val varA = Variable("A")
        val varB = Variable("B")
        val varC = Variable("C")
        val varD = Variable("D")
        val varXY = Variable("XY")

        val litA = Literal(false, varA)
        val litB = Literal(false, varB)
        val litNotB = Literal(true, varB)
        val litNotC = Literal(true, varC)
        val litNotD = Literal(true, varD)
        val litXY = Literal(false, varXY)
        val litNotXY = Literal(true, varXY)

        val clause1 = Clause(listOf(litA, litB, litNotC))
        val clause2 = Clause(listOf(litNotD, litXY))
        val clause3 = Clause(listOf(litNotB, litNotXY))

        val expectedCNF = CNF(listOf(clause1, clause2, clause3))
        val parsedCNF: CNF = this.cnfFactory.createCNFByString(YES_INSTANCE_1)

        println(expectedCNF.toString())
        println(parsedCNF.toString())

        assert(expectedCNF == parsedCNF)
    }
}
