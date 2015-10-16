package org.anttribe.zookeeper.console.core;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.apache.zookeeper.data.Stat;

/**
 * 抽象Zookeeper数据
 * 
 * @author zhaoyong
 * @version 2015年10月9日
 */
public class ZkData
{
    private byte[] data;
    
    private Stat stat;
    
    /**
     * 子路径
     */
    private List<String> children;
    
    @Override
    public String toString()
    {
        return "ZkData [data=" + Arrays.toString(getData()) + ", stat=" + getStat() + "]";
    }
    
    public String getDataString()
    {
        return new String(getData(), Charset.forName("UTF-8"));
    }
    
    public byte[] getData()
    {
        return data;
    }
    
    public void setData(byte[] data)
    {
        this.data = data;
    }
    
    public Stat getStat()
    {
        return stat;
    }
    
    public void setStat(Stat stat)
    {
        this.stat = stat;
    }
    
    public List<String> getChildren()
    {
        return children;
    }
    
    public void setChildren(List<String> children)
    {
        this.children = children;
    }
}