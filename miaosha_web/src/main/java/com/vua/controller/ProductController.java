package com.vua.controller;

import com.vua.annotation.LoginRequired;
import com.vua.entity.Product;
import com.vua.entity.User;
import com.vua.service.ProductService;
import com.vua.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-02-29 18:16
 */
@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;
    @GetMapping("")
    public ModelAndView products(){
        return new ModelAndView("products","products",productService.products());
    }
    @GetMapping("/{id}")
    //@ResponseBody
    //@LoginRequired
    public String products(@PathVariable("id") int id,Model model){
        model.addAttribute("product",productService.findById(id));
        return  "product";//productService.findById(id);
    }
    @Autowired
    ShoppingService shoppingService;
    @GetMapping("/realUrl")
    @ResponseBody
    //@LoginRequired
    public String getURL(@RequestParam("id")int id, HttpServletRequest request){

        return shoppingService.secKillURL(id);//shoppingService.tryBuy(user.getId(),id);
    }
    @PostMapping("/{md5}/do_seckill")
    @ResponseBody
   // @LoginRequired
    public String buy(@PathVariable("md5")String md5, @RequestParam("id")int id, HttpSession session){
        //TODO 秒杀是否开启,时间判断

        int uid=1;//(int)session.getAttribute("uid");
        if(shoppingService.checkURL(md5,id)){

            return shoppingService.tryBuy(uid,id);
        }
        return "无效的请求地址";
    }

}
