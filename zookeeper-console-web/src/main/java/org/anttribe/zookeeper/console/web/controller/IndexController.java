package org.anttribe.zookeeper.console.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 首页controller
 * 
 * @author zhaoyong
 * @version 2015年10月9日
 */
@Controller
public class IndexController
{
    @RequestMapping(value = {"", "/", "/index"})
    public ModelAndView index()
    {
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("addrs", ConfUtils.getConxtions());
        return mv;
    }
}