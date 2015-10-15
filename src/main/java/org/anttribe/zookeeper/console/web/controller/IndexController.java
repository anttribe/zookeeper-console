package org.anttribe.zookeeper.console.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.anttribe.zookeeper.console.common.Global;
import org.anttribe.zookeeper.console.constants.ConfigConstants;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
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
        
        Map<String, String> zkServers = new HashMap<String, String>();
        String servers = Global.me().getString(ConfigConstants.Keys.ZK_SERVERS);
        if (!StringUtils.isEmpty(servers))
        {
            String[] subServers = StringUtils.split(servers, ";");
            if (!ArrayUtils.isEmpty(subServers))
            {
                for (String subServer : subServers)
                {
                    subServer = StringUtils.trim(subServer);
                    if (StringUtils.isEmpty(subServer))
                    {
                        continue;
                    }
                    
                    String[] zkServer = StringUtils.split(subServer, "=");
                    if (ArrayUtils.isEmpty(zkServer) || zkServer.length != 2)
                    {
                        continue;
                    }
                    zkServers.put(zkServer[0], zkServer[1]);
                }
            }
        }
        
        mv.addObject("zkServers", zkServers);
        return mv;
    }
}