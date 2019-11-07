package com.example.testcart.controller;

import com.example.testcart.entity.Item;
import com.example.testcart.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/cart")
public class CartController {
    @Autowired
    private BookService bookService;

    @GetMapping(value = "/listCard")
    public String index(Model model,HttpSession session){
        model.addAttribute("total",totalPrice(session));
        return "cart";
    }
    @PostMapping(value = "/update")
    public String update(HttpServletRequest request,HttpSession session){

        String[] quantities = request.getParameterValues("quantity");
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        for (int i=0;i<cart.size();i++){
            cart.get(i).setQuantity(Integer.parseInt(quantities[i]));
        }
        session.setAttribute("cart",cart);
        return "redirect:/cart/listCard";
    }
    @GetMapping(value = "/remove/{id}")
    public String deleteCart(@PathVariable("id") long id, HttpSession session){
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        int index =isExists(id,cart);
        cart.remove(index);
        session.setAttribute("cart",cart);
        return "redirect:/cart/listCard";
    }
    @GetMapping(value = "/buy/{id}")
    public String addCart(@PathVariable("id") long id, HttpSession session){
        if(session.getAttribute("cart") == null){
            List<Item> cart = new ArrayList<>();
            cart.add(new Item(bookService.findBookById(id),1));
            session.setAttribute("cart",cart);
        }else {
            List<Item> cart = (List<Item>) session.getAttribute("cart");
            int index =isExists(id,cart);
            if(index==-1){
                cart.add(new Item(bookService.findBookById(id),1));

            }else {
                int quantity =cart.get(index).getQuantity() +1;
                cart.get(index).setQuantity(quantity);
            }
            session.setAttribute("cart",cart);
        }
        return "redirect:/cart/listCard";
    }
    private int isExists(long id,List<Item> cart){
        for (int i=0;i<cart.size();i++){
            if(cart.get(i).getBook().getId() ==id){
                return i;
            }
        }
        return -1;
    }
    private double totalPrice(HttpSession session){
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        double total=0;
        for (Item item :cart){
            total += item.getQuantity() * item.getBook().getPrice();
        }
        return total;
    }
}
