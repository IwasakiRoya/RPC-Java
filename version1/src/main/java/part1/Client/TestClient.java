package part1.Client;


import part1.Client.proxy.ClientProxy;
import part1.common.service.UserService;
import part1.common.pojo.User;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/2/6 18:39
 */
public class TestClient {
    public static void main(String[] args) {
        ClientProxy clientProxy= new ClientProxy("127.0.0.1",9999);
        // 下面的代码是通过反射来获取接口的实现类
        // 虽然类型是UserService，但是实际上是一个动态代理类
        // 在使用这个UserService的时候，实际上会被拦截，然后调用invoke方法把你需要调用的方法、参数等信息传到服务端
        UserService proxy = clientProxy.getProxy(UserService.class);


        // 实际上你调用的是：invoke(proxy, method, new Object[]{1});
        User user = proxy.getUserByUserId(1);
        System.out.println("从服务端得到的user="+user.toString());

        User u=User.builder().id(100).userName("wxx").sex(true).build();
        Integer id = proxy.insertUserId(u);
        System.out.println("向服务端插入user的id"+id);
    }
}
