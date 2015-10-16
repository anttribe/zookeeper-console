package org.anttribe.zookeeper.console.runtime;

import java.util.Collections;
import java.util.List;

import org.anttribe.zookeeper.console.core.ZkData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.zkclient.ZkClient;

/**
 * 抽象zk的操作处理
 * 
 * @author zhaoyong
 * @version 2015年10月16日
 */
public class Zk
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Zk.class);
    
    /**
     * zkClient zk客户端对象
     */
    private ZkClient zkClient;
    
    /**
     * <默认构造器>
     * 
     * @param zkServer
     */
    public Zk(String zkServer)
    {
        LOGGER.info("Connecting to zk or getting zkClient from cache, zkServer: {}", zkServer);
        zkClient = ZkClientManager.getClient(zkServer);
    }
    
    public boolean exists(String path)
    {
        if (path == null || path.trim().equals(""))
        {
            throw new IllegalArgumentException("path can not be null or empty");
        }
        return zkClient.exists(path);
    }
    
    public ZkData readData(String path)
    {
        ZkData zkdata = new ZkData();
        Stat stat = new Stat();
        zkdata.setData(zkClient.readData(getPath(path), stat));
        zkdata.setStat(stat);
        List<String> children = this.getChildren(path);
        if (CollectionUtils.isNotEmpty(children))
        {
            Collections.sort(children);
        }
        return zkdata;
    }
    
    public List<String> getChildren(String path)
    {
        return zkClient.getChildren(getPath(path));
    }
    
    public void create(String path, byte[] data)
    {
        path = getPath(path);
        zkClient.createPersistent(path, true);
        Stat stat = zkClient.writeData(path, data);
        LOGGER.info("create: node:{}, stat{}:", path, stat);
    }
    
    public void edit(String path, byte[] data)
    {
        path = getPath(path);
        Stat stat = zkClient.writeData(path, data);
        LOGGER.info("edit: node:{}, stat{}:", path, stat);
    }
    
    public void delete(String path)
    {
        path = getPath(path);
        boolean del = zkClient.delete(path);
        LOGGER.info("delete: node:{}, boolean{}:", path, del);
    }
    
    public void deleteRecursive(String path)
    {
        path = getPath(path);
        boolean deleteRecursive = zkClient.deleteRecursive(path);
        LOGGER.info("rmr: node:{}, boolean{}:", path, deleteRecursive);
    }
    
    private String getPath(String path)
    {
        path = path == null ? "/" : path.trim();
        if (!StringUtils.startsWith(path, "/"))
        {
            path = "/" + path;
        }
        return path;
    }
}