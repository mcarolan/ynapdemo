package io.underscore.ynapdemo.polypattern

import io.underscore.ynapdemo.polypattern.PolyPattern.{Calculation, Calculator, Failure, Success}
import org.scalatest.{FunSuite, Matchers}

class PolyPatternTest extends FunSuite with Matchers {

  test("Division on successful calculation with non-zero divisor should be successful") {
    val calculation: Calculation = Success(4)
    Calculator./(calculation, 2) shouldBe Success(2)

    val calculation2: Calculation = Success(8)
    Calculator./(calculation2, 2) shouldBe Success(4)
  }

  test("Division on successful calculation with zero divisor should be failure") {
    val calculation: Calculation = Success(4)
    Calculator./(calculation, 0) shouldBe Failure("Division by zero")
  }

  test("Division on failed calculation with zero divisor should be the original failure") {
    val calculation: Calculation = Failure("Badness")
    Calculator./(calculation, 0) shouldBe calculation
  }

}
