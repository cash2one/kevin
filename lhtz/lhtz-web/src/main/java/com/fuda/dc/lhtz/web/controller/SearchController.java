package com.fuda.dc.lhtz.web.controller;

import java.util.List;

import javax.jws.WebParam.Mode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fuda.dc.lhtz.web.service.ISearchService;
import com.fuda.dc.lhtz.web.service.IUserService;
import com.fuda.dc.lhtz.web.vo.IcbIndexInfo;

@Controller
public class SearchController {

    @Autowired
    private ISearchService searchService;
    
	@Autowired
	private IUserService userService;

    @RequestMapping("/search")
    public String search(String query, Model model) {
        List<IcbIndexInfo> searchResults = searchService.search(query);
        model.addAttribute("results", searchResults);
        return "search";
    }
    
    @RequestMapping("/search1")
    public String search1(String name, String passwd, Model model) {
    	userService.doLogin(name, passwd);
    	return "redirect:/index.jsp";
    }

}
