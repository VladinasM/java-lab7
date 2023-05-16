package com.example.demo;

import java.io.*;
import java.util.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    /*
8 вариант - 110011
При перезагрузке страницы сервлета должно отображаться:1 – значение счётчика обращений к странице
сервлета после его запуска.
6. Организовать вывод результатов работы сервлета: 1 – данные полученные от сервлета должны быть каким-то образом
размещены в видимой таблице, в таблице допускается произвольное число столбцов
и строк.
7. Реализовать при обновлении страницы сервлета: 0 - уменьшение размера текста в
таблице до заданной величины, после чего на странице должна появляться надпись
(не в таблице), информирующая о том, что дальнейшее уменьшение не возможно;
8. Реализовать возможность сброса размера текста в таблице через параметр строки url запроса: 0 - до значения по умолчанию
9. Среди параметров, передаваемых в сервлет, нужно передавать Ф.И.О студента,
выполнившего разработку сервлета, и номер его группы, которые должны
отображаться следующим образом: 1 - на web-странице (не в таблице).
11. При необходимости могут быть изменены порты, по которым контейнер сервлетов
Tomcat слушает запросы.  1 — изменить на
произвольный.



   */
    private static List<Map.Entry<String, String>> storage;
    private static final byte min_size = 1;
    private static final byte default_size = 5;
    public static byte size;
    public static long counter;

    public HelloServlet() {
        storage = new ArrayList<>();
        size = default_size;
        HelloServlet.counter = 0;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Mingaleev Vladislave Yurevich 4310 </title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>ServletApply" + request.getServletPath() + "</h1>");
            String fio = request.getParameter("fio");
            String group = request.getParameter("group");
            out.println(fio + " + " + group);

            HelloServlet.counter++;
            out.println("<h3> MyServlet.counter : " + HelloServlet.counter + "</h3>");

            String toClear = request.getParameter("clear");
            if (toClear != null && toClear.equals("true")) {
                size = default_size;
            }

            String args = request.getParameter("args");
            if (args != null) {
                storage.add(new AbstractMap.SimpleEntry<>(args, sort_func(args.split(" "))));
            }
            out.println("<h" + HelloServlet.size + "><table border>");
            out.println("<tr><th>Входные данные</th><th>Результат</th></tr>");
            for (Map.Entry<String, String> elem : storage) {
                out.println("<tr><td>" + elem.getKey() + "</td><td>" +elem.getValue() + "</td></tr>");
            }
            out.println("<h" + HelloServlet.size + "></table>");

            if (size == min_size) {
                out.println("<h3> Достигунт лимит шрифта.</h3>");
            } else {
                size--;
            }
            out.println("</body>");
            out.println("</html>");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private String sort_func(String[] args) {
        int[] arr = new int[args.length];
        int negativeSum=0;
        int positiveSum=0;

        for (int i = 0; i < args.length; i++) {
            arr[i] = Integer.parseInt(args[i]);
        }
        for(int i = 0; i < arr.length; i++){
            if(i % 2 == 0 & arr[i] > 0){
                positiveSum += arr[i];
            }
            else if(i % 2 != 0 & arr[i] < 0){
                negativeSum += arr[i];
            }
        }
        if(positiveSum > negativeSum) {
            return "Больше четных положительных";
        } else if( positiveSum < negativeSum){
            return "Больше нечетных отрицателбных";
        } else {
            return "Одинаково";
        }
    }
}