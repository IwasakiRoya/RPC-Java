package part1.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wxx
 * @version 1.0
 * @create 2024/1/28 17:50
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
// 需要注意，这里的引用接口是一个标志性的接口，不是我们在spring中学习的功能性接口
// Serializable 是一个标记接口，用来表示这个类可以被序列化
// 这个User类也不是接口的实现类，他不具备任何行为功能，他仅仅是一种数据结构，用来存储一个用户的数据
public class User implements Serializable {
    // 客户端和服务端共有的
    private Integer id;
    private String userName;
    private Boolean sex;
}

