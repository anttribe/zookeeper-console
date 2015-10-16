package org.anttribe.zookeeper.console.runtime;

import java.util.HashMap;
import java.util.Map;

import org.anttribe.zookeeper.console.common.Global;
import org.anttribe.zookeeper.console.constants.ConfigConstants;
import org.anttribe.zookeeper.console.constants.Constants;

import com.github.zkclient.ZkClient;

/**
 * Zk客户端管理
 * 
 * @author zhaoyong
 * @version 2015年10月16日
 */
public class ZkClientManager
{
    /**
     * 存放zkClient的缓存
     */
    private static Map<String, ZkClient> cache = new HashMap<String, ZkClient>();
    
    /**
     * 获取ZKClient对象
     * 
     * @param zkServer String
     * @return ZkClient
     */
    public static ZkClient getClient(String zkServer)
    {
        ZkClient zkClient = cache.get(zkServer);
        if (null == zkClient || !zkClient.isConnected())
        {
            zkClient = new ZkClient(zkServer,
                Global.me().getInt(ConfigConstants.Keys.ZK_CONNECT_TIMEOUT, Constants.DEFAULT_ZK_CONNECT_TIMEOUT));
        }
        cache.put(zkServer, zkClient);
        return zkClient;
    }
}