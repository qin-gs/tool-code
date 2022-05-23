package com.trick.clear.design;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 解释器：给分析对象定义一个语言，并定义该语言的文法表示，再设计一个解析器来解释语言中的句子
 */
public class InterpreterTest {

    public static void main(String[] args) {
        Context context = new Context();
        context.operation("a的men");
        context.operation("c的women");
    }

    /**
     * 抽象表达式
     */
    interface AbstractExpression {
        boolean interpreter(String info);
    }

    /**
     * 终结符
     */
    static class TerminalExpression implements AbstractExpression {
        private Set<String> set = new HashSet<>();

        public TerminalExpression(String data[]) {
            Collections.addAll(set, data);
        }

        @Override
        public boolean interpreter(String info) {
            return set.contains(info);
        }
    }

    /**
     * 非终结符
     */
    static class NonterminalExpression implements AbstractExpression {
        private AbstractExpression exp1;
        private AbstractExpression exp2;

        public NonterminalExpression(AbstractExpression exp1, AbstractExpression exp2) {
            this.exp1 = exp1;
            this.exp2 = exp2;
        }

        @Override
        public boolean interpreter(String info) {
            String[] strings = info.split("的");
            return exp1.interpreter(strings[0]) && exp2.interpreter(strings[1]);
        }
    }

    static class Context {

        private String cities[] = {"a", "b"};
        private String[] persons = {"child", "old", "men"};

        private AbstractExpression exp;

        public Context() {
            AbstractExpression city = new TerminalExpression(cities);
            AbstractExpression person = new TerminalExpression(persons);
            exp = new NonterminalExpression(city, person);
        }

        public void operation(String info) {
            boolean interpreter = exp.interpreter(info);
            if (interpreter) {
                System.out.println("您是 " + info + " 免费");
            } else {
                System.out.println(info + " 不免费");
            }
        }
    }
}
