package org.jruby.ir.instructions.defined;

import org.jruby.ir.IRVisitor;
import org.jruby.ir.Operation;
import org.jruby.ir.instructions.Instr;
import org.jruby.ir.operands.BooleanLiteral;
import org.jruby.ir.operands.Operand;
import org.jruby.ir.transformations.inlining.InlinerInfo;
import org.jruby.runtime.Block;
import org.jruby.runtime.DynamicScope;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;

import java.util.Map;

public class SetWithinDefinedInstr extends Instr {
    private final BooleanLiteral define;
    
    public SetWithinDefinedInstr(BooleanLiteral define) {
        super(Operation.SET_WITHIN_DEFINED);
        
        this.define = define;
    }

    @Override
    public Operand[] getOperands() {
        return new Operand[] { define };
    }

    @Override
    public String toString() {
        return getOperation().toString() + "(" + define + ")";
    }

    @Override
    public void simplifyOperands(Map<Operand, Operand> valueMap, boolean force) {
        /* Nothing to do since 'define' is a ImmutableLiteral */
    }

    @Override
    public Instr cloneForInlining(InlinerInfo ii) {
        return new SetWithinDefinedInstr(define);
    }

    @Override
    public Object interpret(ThreadContext context, DynamicScope currDynScope, IRubyObject self, Object[] temp, Block block) {
        context.setWithinDefined(define.isTrue());
        return null;
    }

    @Override
    public void visit(IRVisitor visitor) {
        visitor.SetWithinDefinedInstr(this);
    }
}
