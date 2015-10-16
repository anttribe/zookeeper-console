package org.anttribe.zookeeper.console.web.controller;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.anttribe.zookeeper.console.constants.Keys;
import org.anttribe.zookeeper.console.core.ZkData;
import org.anttribe.zookeeper.console.runtime.Zk;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * zk数据读取controller
 * 
 * @author zhaoyong
 * @version 2015年10月16日
 */
@Controller
@RequestMapping("/zkRead")
public class ZkReadController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ZkReadController.class);
    
    @RequestMapping("/connect")
    public String connect(HttpServletRequest request, RedirectAttributes attrs,
        @RequestParam(required = true) String zkServer)
    {
        if (StringUtils.isBlank(zkServer))
        {
            return "redirect:/";
        }
        zkServer = StringUtils.trimToEmpty(zkServer);
        request.getSession().setAttribute(Keys.KEY_ZKSERVER, zkServer);
        attrs.addFlashAttribute(Keys.KEY_ZKSERVER, zkServer);
        return "redirect:/zkRead/ls";
    }
    
    @RequestMapping("/ls")
    public String ls(HttpServletRequest request, Model model, String path)
    {
        String zkServer = (String)request.getSession().getAttribute(Keys.KEY_ZKSERVER);
        if (StringUtils.isBlank(zkServer))
        {
            return "redirect:/";
        }
        
        // 处理zkPath
        path = this.processZkPath(path);
        model.addAttribute("paths", Arrays.asList(StringUtils.split(path, "/")));
        
        // zk数据读取
        ZkData zkData = this.readZkData(zkServer, path);
        if (null != zkData)
        {
            model.addAttribute("children", zkData.getChildren());
            model.addAttribute("data", zkData.getDataString());
            model.addAttribute("dataSize", zkData.getData().length);
            try
            {
                Map<String, Object> statMap = PropertyUtils.describe(zkData.getStat());
                statMap.remove("class");
                model.addAttribute("stat", statMap);
            }
            catch (Exception e)
            {
                LOGGER.error("", e);
            }
        }
        
        return "ls";
    }
    
    /**
     * 处理请求zk的路径
     * 
     * @param path 路径
     * @return String
     */
    private String processZkPath(String path)
    {
        if (StringUtils.endsWith(path, "/"))
        {
            path = StringUtils.substring(path, 0, StringUtils.lastIndexOf(path, "/"));
        }
        path = StringUtils.trimToEmpty(path);
        if (StringUtils.isEmpty(path))
        {
            path = "/";
        }
        
        return path;
    }
    
    /**
     * 读取zk的数据
     * 
     * @param zkServer zk服务器地址
     * @param path 请求路径
     * @return ZkData
     */
    private ZkData readZkData(String zkServer, String path)
    {
        Zk reader = new Zk(zkServer);
        return reader.readData(path);
    }
}