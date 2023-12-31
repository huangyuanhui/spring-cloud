package cn.itcast.order.service;

import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import cn.itcast.order.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RestTemplate restTemplate;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        // 2：利用RestTemplate发送http请求，查询用户
        //String url = "http://localhost:8082/users/" + order.getUserId();
        // 2：用服务名代替IP、端口
        String url = "http://user-service/users/" + order.getUserId();
        User user = restTemplate.getForObject(url, User.class);
        // 3：封装user到order
        order.setUser(user);
        // 4.返回
        return order;
    }
}
