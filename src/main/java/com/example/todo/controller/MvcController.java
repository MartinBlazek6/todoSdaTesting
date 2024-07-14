package com.example.todo.controller;

// HLAVNÍ TEORIE JAK funguji REQUESTY a co je REDIRECT v return, how and where template should be set,
// jak nastavit MODEL , a TAKY NA MVC (THYMELEAF) - RŮZNÉ PRINCIPY!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!:
// Pro zadání nových TASKů do DB mohu taky využít WEB stránku v browser na URL: http://localhost:8080/
// Tam totiž sídlí : index.html !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//********************VELMI DŮLEŽITÉ*************************
// todo Pozor: Když za běhu programu přidám třeba jen komentař, tak se zruší spojení s DB a musím zadat TASKy znovu!!!!!!!!!!!!!!

// In your Spring application, the @PostMapping("/createTodo") annotation indicates that the createTodo method should be
// called in response to a POST request to the URL path "/createTodo". This is part of the Spring MVC framework,
// where you map HTTP requests to specific handler methods in your controller classes.
//Regarding where to set the template, this typically refers to specifying a view that should be rendered as the response.
// In Spring MVC, this is usually done by returning a String from your controller methods, which represents the name of
// the view (like a Thymeleaf or JSP page). However, in your method createTodo, you are returning "redirect:/".
// This instructs Spring MVC to perform a redirection to the root URL of your application ("/").
// Here's a step-by-step guide on how you might set a template:
//1.	Define a View Template: First, you need a view template. This could be a Thymeleaf template, a JSP file, or
//      another type of view technology supported by Spring. The template should be placed in the appropriate directory in
//      your project (e.g., src/main/resources/templates for Thymeleaf).
//2.	Return View Name: In your controller method, instead of returning "redirect:/", you would return the name of your
//      view template. For example, if you have a Thymeleaf template named todoCreated.html, you would return "todoCreated".
//3.	Model Data: If you want to pass data to your view, you can use a Model object as a parameter in your controller
//      method. You can add attributes to this model, which will be accessible in your view template.
// Example with a template:
// @PostMapping("/createTodo")
// public String createTodo(@RequestParam String title, String date, Model model){
//    Todo todo = todoService.createTodo(title, todoService.convertStringToDate(date));
//    model.addAttribute("todo", todo);
//    return "todoCreated"; // Name of the view template }
//
// As for "redirect:/", it's a special type of return value in Spring MVC:
// •	Redirect: This tells Spring MVC to redirect the user's browser to a different URL. In this case, it's redirecting
// to the root URL of your application ("/"). This is often used after performing operations like creating or updating
// data, where you want to redirect the user to another page (like a confirmation page or back to a list view) rather
// than directly rendering a view at the end of the operation. This approach follows the Post/Redirect/Get pattern,
// which helps prevent duplicate form submissions.

// DŮLEŽITÝ PRINCIP "redirect:/" a "index.html": V našem případě po zadání nějakých TASKů do DB (pomocí createTodo metody) do
// DB si mohu prohlédnout Thymeleaf stránku index.html přímo v rootu na http://localhost:8080/, kde sídlí index.html.
// Toto je zadáno v tom prvním @GetMapping :  @GetMapping("/")
//                                            public String getAllTodos(Model model)
// HLAVNI PRINCIP: To "redirect:/" je tam kvůli tomu, že když zadávám nové Tasky pomocí formuláře v index.html,
//                 tak tam volám metodu createTodo pomocí "post", která sídlí na adrese "/createTodo". Po odeslání
//                 formuláře se však chci vrátit zpět na formulář, který je v INDEX v rootu (http://localhost:8080/)!!!!
// A taky v RESPONSE na ten POST příkaz uvidím tu stránku index.html, protože za return je "redirect:/", a to přesměruje
// na tu jinou stránku - tj. zde na ":/" dané v: return "redirect:/". A zde sídlí díky prvnímu GET níže stránka index.html.
// TAKY MOHU ZADAT NĚJAKÝ TASK DO DB POMOCÍ PŘÍMÉHO ZÁPISU DO BROWSERU NA URL.
// POZOR: DO BROWSERU NA URL LZE ZADAT POUZE GET příkaz !!!!!!!!!!!!!!!!!!!!!!!!!!!!
// http://localhost:8080/


import com.example.todo.service.TodoService;
import com.example.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@Slf4j
@Controller
@RequiredArgsConstructor
public class MvcController {
    private final TodoService todoService;
    private final UserService userService;

    //In Spring MVC, mostly returning a String from your controller methods represents the name of the view
    // (like a Thymeleaf or JSP page). The template should be placed in the appropriate directory in your project
    // (e.g., src/main/resources/templates for Thymeleaf).

    // HLAVNÍ PRINCIP: V MVC (THYMELEAF) MOHU POUŽÍVAT JAK PARAMETRY ZADANÉ V METODĚ PRO DANÝ PŘÍKAZ POST, GET, atd.,
    //                 ALE TAKY ATRIBUTY ZADANÉ POMOCÍ Model, JAKO PARAMETR METODY.

    @GetMapping("/")
    public String getAllTodos(Model model){
        // Metoda getAllTodos vrací seznam Todo Entit získaný z DB !!!!!!!!!!!!!!!!!
        model.addAttribute("todos",todoService.getAllTodos());
        model.addAttribute("userRole", userService.getLoggedInUserRole());  // zjistí aktuálně přihláš. user ROLE
        log.info(userService.getLoggedInUserRole());
        return "index";  // VIEW soubor typu thymeleaf musí být uložen v "src/main/resources/templates"
    }                    // Zde je to soubor: index.html !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    @PostMapping("/changeStatus")    // Když dělám přes formulář v MVC view, tak to jde taky nejprve na stránku
    public String changeStatus(@RequestParam Long id){  // "/changeStatus", tam vykoná metodu, a nakonec "redirect:/" do rootu.
        todoService.changeStatus(id);                   // Tj. zpět do formuláře.
        return "redirect:/";  // This tells Spring MVC to redirect the user's browser to a different URL. In this case,
          // it's redirecting to the root URL of your application ("/"). Více viz. DŮLEŽITÝ PRINCIP "redirect:/" a "index.html".
    }

    // POZOR: DATUM NUTNO ZADAT VE FORMÁTU: "yyyy-MM-dd"
    @PostMapping("/createTodo")
    public String createTodo(@RequestParam String title, String date){
        todoService.createTodo(title,todoService.convertStringToDate(date)); // Zde musím převest zadaný String na date format
        return "redirect:/";          // This tells Spring MVC to redirect the user's browser to a different URL. In this case,
        // it's redirecting to the root URL of your application ("/"). Více viz. DŮLEŽITÝ PRINCIP "redirect:/" a "index.html".
    }

    @PostMapping("/deleteTodo/{id}")
    public String deleteTodo(@PathVariable Long id){
        todoService.deletedTodo(id);
        return "redirect:/";
    }


}
