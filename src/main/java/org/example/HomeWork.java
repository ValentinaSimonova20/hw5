package org.example;

import java.util.*;

public class HomeWork {

    static Map<String, OperatorInfo> OPERATORS = Map.of(
            "+", new OperatorInfo(1, Double::sum),
            "-", new OperatorInfo(1, (a, b) -> b - a),
            "*", new OperatorInfo(2, (a, b) -> a * b),
            "/", new OperatorInfo(2, (a, b) -> b / a),
            "pow", new OperatorInfo(3, (a, b) -> Math.pow(b, a)),
            "sqr", new OperatorInfo(3, (a, b) -> a * a, false),
            "sin", new OperatorInfo(3, (a, b) -> Math.sin(a), false),
            "cos", new OperatorInfo(3, (a, b) -> Math.cos(a), false)

    );

    /**
     * <h1>Задание 1.</h1>
     * Требуется реализовать метод, который по входной строке будет вычислять математические выражения.
     * <br/>
     * Операции: +, -, *, / <br/>
     * Функции: sin, cos, sqr, pow <br/>
     * Разделители аргументов в функции: , <br/>
     * Поддержка скобок () для описания аргументов и для группировки операций <br/>
     * Пробел - разделитель токенов, пример валидной строки: "1 + 2 * ( 3 - 4 )" с результатом -1.0 <br/>
     * <br/>
     * sqr(x) = x^2 <br/>
     * pow(x,y) = x^y
     */
    double calculate(String expr) {
        String[] input = translate(expr).split(" ");
        Deque<String> stack = new LinkedList<>();
        for(String cur: input) {
            if(cur.chars().allMatch(Character::isDigit)) {
                stack.push(cur);
            } else {
                OperatorInfo operatorInfo = OPERATORS.get(cur);
                stack.push(
                        operatorInfo
                                .getFunction()
                                .apply(
                                        Double.valueOf(stack.pop()),
                                        Double.valueOf(operatorInfo.isBi() ? stack.pop() : "0")
                                )
                                .toString()
                );
            }
        }
        return Double.parseDouble(stack.pop());
    }


    static String translate(String inputString) {
        String[] input = inputString.split(" ");
        List<String> output = new ArrayList<>();
        Deque<String> stack = new LinkedList<>();
        for (String cur : input) {
            //Если токен — число, то добавить его в очередь вывода.
            if (cur.chars().allMatch(Character::isDigit)) {
                output.add(cur);
            }

            //Если токен — оператор op1, то:
            if (OPERATORS.containsKey(cur)) {
                //Пока присутствует на вершине стека токен оператор op2, чей приоритет выше или равен приоритету op1,
                // и при равенстве приоритетов op1 является левоассоциативным:
                while (
                        !stack.isEmpty()
                                && !stack.peek().equals("(")
                                && OPERATORS.get(stack.peek()).getPriority() >= OPERATORS.get(cur).getPriority()
                ) {
                    //Переложить op2 из стека в выходную очередь;
                    output.add(stack.pop());
                }
                //Положить op1 в стек.
                stack.push(cur);
            }
            //Если токен — открывающая скобка, то положить его в стек.
            if (cur.equals("(")) {
                stack.push(cur);
            }
            //Если токен — закрывающая скобка:
            if (cur.equals(")")) {
                //Пока токен на вершине стека не открывающая скобка
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    //Переложить оператор из стека в выходную очередь.
                    output.add(stack.pop());
                }
                //Если стек закончился до того, как был встречен токен открывающая скобка, то в выражении пропущена скобка.
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("Missing '(' in expression");
                }
                //Выкинуть открывающую скобку из стека, но не добавлять в очередь вывода.
                stack.pop();
            }

            // если токен - функция
            if(Set.of("pow", "sqr", "sin", "cos").stream().anyMatch(cur::contains)) {
                String[] parsedFunc = cur.split("[(,)]");
                output.addAll(Arrays.asList(parsedFunc).subList(1, parsedFunc.length));
                stack.push(parsedFunc[0]);
            }
        }
        //Если больше не осталось токенов на входе:
        //Пока есть токены операторы в стеке:
        while (!stack.isEmpty()) {
            //Если токен оператор на вершине стека — открывающая скобка, то в выражении пропущена скобка.
            if (stack.peek().equals("(")) {
                throw new IllegalArgumentException("Missing ')' in expression");
            }
            //Переложить оператор из стека в выходную очередь.
            output.add(stack.pop());
        }


        return String.join(" ", output);
    }

}
