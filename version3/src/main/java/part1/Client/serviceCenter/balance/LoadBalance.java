package part1.Client.serviceCenter.balance;

import java.util.List;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/6/19 21:00
 * 给服务地址列表，根据不同的负载均衡策略选择一个
 */
// 首先你得清楚：为什么负载均衡不是写在zookeeper而是写在客户端？
// 因为zookeeper只是一个注册中心，负责存储服务地址列表，客户端负责选择一个服务地址进行调用，更别提此时客户端已经有缓存了。
// 负载均衡这个行为当然应该在客户端从缓存里选一个地址的时候完成，所以理所当然写在客户端
public interface LoadBalance {
    String balance(List<String> addressList);
    void addNode(String node) ;
    void delNode(String node);
}