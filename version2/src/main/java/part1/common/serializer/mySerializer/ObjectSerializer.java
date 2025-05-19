package part1.common.serializer.mySerializer;

import java.io.*;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/6/2 22:36
 */
public class ObjectSerializer implements Serializer {
    //利用Java io 对象 -》字节数组
    @Override
    public byte[] serialize(Object obj) {

        // byte[] byteArray = new byte[]{72, 101, 108, 108, 111}; // "Hello"的ASCII码表示
        // 二进制字节流则是按顺序读取写入的数据序列，它更像是一种传输方式的定义，而不是简单的数据格式。
        // 例如，二进制字节流是将一份字节文件，定义成一种用于传输的01电信号，而不是单单指这个文件的表现形式（在屏幕上显示的具体内容）。


        /* bos和oos：
            bos相当于一个容器，你可以理解成申请了一个字符数组，这个容器存在于内存中，这个容器只有一个方法就是把容器里的数据变成字节数组
            oos相当于一个方法类，它能将一个文件变成一段用于传输的电信号，它需要一个容器来存储这段信号，所以它需要绑定一个容器，也就是bos
            oos的作用是将对象序列化为字节流，bos的作用是将字节流转换为字节数组
         */

        /* 聪明的小朋友就要问了：那我们为什么不直接传字节流呢？还要多搞一段字节数组出来不是多次语句？
            答案是：“流”是过程，byte[] 是结果。流的本质是“一个数据一个数据地写进去”，你写一次、就过去了，不能回头、不能查看。
            例如：ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            你可以理解成：我打开了一个“水管”，可以开始放水，但开始放水之后就拿不到了，你甚至不知道这个水得放到什么时候
            不像数组你可以随时 .get(3) 查看第三个元素。
            oos就像一个水龙头，他只管把你丢进来的文件像水一样输出出来，你不拿杯子怎么接的到呢？而这个杯子就是字节数组了。

            这个时候又有小朋友要问了：之前我们都没搞过这个字节数组，我们也可以直接往网络流里写字节流啊，为什么还要搞这个字节数组？
            像这样：
                Socket socket = new Socket();
                OutputStream out = socket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(out);
                oos.writeObject(obj); // 直接写过去了

            这样快，直接写入网络或文件，但一旦写了就没了，无法拿出来再次使用。也不知道写了多少字节（无法提前构建报文头）。网络中断就GG了。

            所以得像下面一样，先写入 ByteArrayOutputStream，这样你可以拿到完整的序列化结果。
            可以提前知道字节长度，方便你封装 RPC 协议（比如写入：包头+包体）。
            可以缓存、重复使用、压缩、加密等。
            出错也不会影响主流写入，可以做重试、日志等。
         */

        byte[] bytes=null;
        // ByteArrayOutputStream 的意义是让你“把写入过程缓存下来、集中处理”
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        try {
            //是一个对象输出流，用于将 Java 对象序列化为字节流，并将其连接到bos上
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            //刷新 ObjectOutputStream，确保所有缓冲区中的数据都被写入到底层流中。
            oos.flush();
            //将bos其内部缓冲区中的数据转换为字节数组
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    //字节数组 -》对象
    @Override
    public Object deserialize(byte[] bytes, int messageType) {
        Object obj = null;
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }

    //0 代表Java 原生序列器
    @Override
    public int getType() {
        return 0;
    }
}

