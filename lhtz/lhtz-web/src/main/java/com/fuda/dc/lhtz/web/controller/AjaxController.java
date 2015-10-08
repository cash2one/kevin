package com.fuda.dc.lhtz.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AjaxController {

    @RequestMapping("/getAjax")
    public String getAjax() {
        return "ajax";
    }

    @RequestMapping("/getCountryAjax")
    @ResponseBody
    public Map<String, Object> getCountry(String name) {

        Map<String, Object> map = new HashMap<String, Object>();
        if (name != null) {
            //Person person = helloService.getPerson(name);

            map.put("status", true);
        } else {
            map.put("status", false);
        }
        return map;

    }

}
