package com.fuda.dc.lhtz.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fuda.dc.lhtz.web.stock.IStockCrawler;
import com.fuda.dc.lhtz.web.stock.SinaStockCrawler;

@Controller
public class StockController {

    @Autowired
    private HttpServletRequest request;

    private final IStockCrawler stockCrawler = new SinaStockCrawler();

    @RequestMapping("/getRealStock")
    public String getRealStockData(@RequestParam("code") String code, Model model) {
        Map<String, String> dataMap = stockCrawler.getRealStockData(code);
        model.addAttribute("realdata", dataMap);
        return "stock";
    }

    @RequestMapping("/getStockImg")
    public String getStockImg(@RequestParam("code") String code,
            @RequestParam(value = "type", defaultValue = "0") int type, Model model) {
        String url = genUrl(code, type);
        model.addAttribute("stocksrc", url);
        return "stock";
    }

    private String genUrl(String code, int type) {
        String urlPrefix = "http://image.sinajs.cn/newchart";
        String url = null;
        String codePrefix = null;

        int flag = Integer.parseInt(code.substring(0, 1));
        switch (flag) {
        case 0:
            codePrefix = "sz";
            break;
        case 3:
            codePrefix = "sz";
            break;
        case 6:
            codePrefix = "sh";
            break;
        default:
            codePrefix = "";
            break;
        }

        switch (type) {
        case 0:
            url = sprintf(urlPrefix, "/min/n/", codePrefix, code, ".gif");
            break;
        case 1:
            url = sprintf(urlPrefix, "/daily/n/", codePrefix, code, ".gif");
            break;
        case 2:
            url = sprintf(urlPrefix, "/weekly/n/", codePrefix, code, ".gif");
            break;
        case 3:
            url = sprintf(urlPrefix, "/monthly/n/", codePrefix, code, ".gif");
            break;
        default:
            url = "";
            break;
        }
        return url;
    }

    private String sprintf(String urlPrefix, String period, String codePrefix, String code, String suffix) {
        StringBuilder sb = new StringBuilder();
        sb.append(urlPrefix);
        sb.append(period);
        sb.append(codePrefix);
        sb.append(code);
        sb.append(suffix);
        return sb.toString();
    }

}
