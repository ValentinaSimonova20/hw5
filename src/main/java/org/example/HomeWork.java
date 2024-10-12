package org.example;

import java.util.*;
import java.util.function.BiFunction;

public class HomeWork {

    static Map<Character, Integer> OPERATORS_WITH_PRIORITIES = Map.of(
            '+', 1,
            '-', 1,
            '*', 2,
            '/', 2,
            '^', 3
    );

    static Map<Character, BiFunction<Double, Double, Double>> OPERATORS_WITH_FUNCTIONS = Map.of(
            '+', Double::sum,
            '-', (a, b) -> b - a,
            '*', (a, b) -> a * b,
            '/', (a, b) -> b / a,
            '^', Math::pow
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
                stack.push(
                        OPERATORS_WITH_FUNCTIONS
                                .get(cur.charAt(0))
                                .apply(
                                        Double.valueOf(stack.pop()),
                                        Double.valueOf(stack.pop())
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
        Deque<Character> stack = new LinkedList<>();
        for (String cur : input) {
            //Если токен — число, то добавить его в очередь вывода.
            if (cur.chars().allMatch(Character::isDigit)) {
                output.add(cur);
            }

            if(cur.length() > 1){
                continue;
            }
            char c = cur.charAt(0);
            //Если токен — оператор op1, то:
            if (OPERATORS_WITH_PRIORITIES.containsKey(c)) {
                //Пока присутствует на вершине стека токен оператор op2, чей приоритет выше или равен приоритету op1,
                // и при равенстве приоритетов op1 является левоассоциативным:
                while (
                        !stack.isEmpty()
                                && stack.peek() != '('
                                && OPERATORS_WITH_PRIORITIES.get(stack.peek()) >= OPERATORS_WITH_PRIORITIES.get(c)
                ) {
                    //Переложить op2 из стека в выходную очередь;
                    output.add(stack.pop().toString());
                }
                //Положить op1 в стек.
                stack.push(c);
            }
            //Если токен — открывающая скобка, то положить его в стек.
            if (c == '(') {
                stack.push(c);
            }
            //Если токен — закрывающая скобка:
            if (c == ')') {
                //Пока токен на вершине стека не открывающая скобка
                while (!stack.isEmpty() && stack.peek() != '(') {
                    //Переложить оператор из стека в выходную очередь.
                    output.add(stack.pop().toString());
                }
                //Если стек закончился до того, как был встречен токен открывающая скобка, то в выражении пропущена скобка.
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("Missing '(' in expression");
                }
                //Выкинуть открывающую скобку из стека, но не добавлять в очередь вывода.
                stack.pop();
            }
        }
        //Если больше не осталось токенов на входе:
        //Пока есть токены операторы в стеке:
        while (!stack.isEmpty()) {
            //Если токен оператор на вершине стека — открывающая скобка, то в выражении пропущена скобка.
            if (stack.peek() == '(') {
                throw new IllegalArgumentException("Missing ')' in expression");
            }
            //Переложить оператор из стека в выходную очередь.
            output.add(stack.pop().toString());
        }


        return String.join(" ", output);
    }

}
