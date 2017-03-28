package com.nscorp.address.controller;

import com.nscorp.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by raphael on 27/03/17.
 */
@Controller
@RequestMapping("/ender/address")
public class FrontController {

    @Autowired
    private RestTemplate consumer;

    @GetMapping("list")
    public String getAddressList(Model model, ServletRequest req){
        String url = String.format("http://%s:%s/ender/ws/address", req.getServerName(), req.getServerPort());
        List<Address> list = asList(consumer.getForObject(url, Address[].class));
        model.addAttribute("addressList", list);

        return "list";
    }

    @GetMapping("new")
    public String newAddress(Address address){
        return "new";
    }

    @GetMapping("view/{id}")
    public String viewAddress(@PathVariable String id, Model model, ServletRequest req){
        String url = String.format("http://%s:%s/ender/ws/address/%s", req.getServerName(), req.getServerPort(), id);
        Address address = consumer.getForObject(url, Address.class);
        model.addAttribute("address", address);

        return "view";
    }

    @GetMapping("edit/{id}")
    public String editAddress(@PathVariable String id, Model model, ServletRequest req){
        String url = String.format("http://%s:%s/ender/ws/address/%s", req.getServerName(), req.getServerPort(), id);
        Address address = consumer.getForObject(url, Address.class);
        model.addAttribute("address", address);

        return "edit";
    }

    @GetMapping("remove/{id}")
    public String removeAddress(@PathVariable String id, ServletRequest req){
        String url = String.format("http://%s:%s/ender/ws/address/%s", req.getServerName(), req.getServerPort(), id);
        consumer.delete(url);

        return "redirect:../list";
    }

    @PostMapping("save")
    public String saveAddress(@Valid Address address, BindingResult result, HttpServletRequest req){
        if (result.hasErrors()){
            if(address.getId() != null) return "edit";
            return "new";
        }

        String url = String.format("http://%s:%s/ender/ws/address", req.getServerName(), req.getServerPort());
        consumer.postForObject(url, address, Address.class);

        return "view";
    }

}
