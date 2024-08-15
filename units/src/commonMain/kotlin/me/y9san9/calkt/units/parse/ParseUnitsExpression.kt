package me.y9san9.calkt.units.parse

import me.y9san9.calkt.Expression
import me.y9san9.calkt.math.parse.DefaultMathInfixOperators
import me.y9san9.calkt.math.parse.DefaultMathParseOperand
import me.y9san9.calkt.math.parse.MathParse
import me.y9san9.calkt.parse.ParseContext

public fun ParseContext.parseUnitsExpression(parseUnitKey: UnitsParseUnitKeyFunction): Expression {
    val parseOperand = DefaultMathParseOperand
    val unitsParseOperand = UnitsMathParseOperand(parseUnitKey, parseOperand)
    
    val infixOperatorList = DefaultMathInfixOperators.list
    
    val mathParse = MathParse(unitsParseOperand, infixOperatorList)
    val parse = UnitsParse(mathParse, parseUnitKey)
    
    return parse(context)
}
