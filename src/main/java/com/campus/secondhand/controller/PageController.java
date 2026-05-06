package com.campus.secondhand.controller;

import com.campus.secondhand.entity.Item;
import com.campus.secondhand.repository.ItemRepository;
import com.campus.secondhand.repository.OrderRepository;
import com.campus.secondhand.repository.UserRepository;
import com.campus.secondhand.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class PageController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    // 首页
    @GetMapping("/")
    public String index(Model model) {
        long totalItems = itemRepository.count();
        Double avgPrice = itemRepository.getAveragePrice();
        if (avgPrice == null) avgPrice = 0.0;
        List<Object[]> categoryCount = itemRepository.countByCategory();
        List<Object[]> topSeller = itemRepository.findTopSeller();

        model.addAttribute("totalItems", totalItems);
        model.addAttribute("avgPrice", avgPrice);
        model.addAttribute("categoryCount", categoryCount);
        model.addAttribute("topSeller", topSeller);
        return "index";
    }

    // 商品列表页面（含所有操作）
    @GetMapping("/items")
    public String items(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        return "items";
    }

    // 用户列表
    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }

    // 订单列表（包含订单详情连接查询）
    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        model.addAttribute("orderDetails", orderRepository.findOrderDetails());
        return "orders";
    }

    // 基本查询1：未售出商品
    @GetMapping("/unsold")
    public String unsold(Model model) {
        model.addAttribute("items", itemRepository.findByStatus(0));
        model.addAttribute("queryName", "未售出商品");
        return "query_result";
    }

    // 基本查询2：价格大于30
    @GetMapping("/price-gt-30")
    public String priceGt30(Model model) {
        model.addAttribute("items", itemRepository.findByPriceGreaterThan(BigDecimal.valueOf(30.0)));
        model.addAttribute("queryName", "价格大于30的商品");
        return "query_result";
    }

    // 基本查询3：生活用品类
    @GetMapping("/daily-goods")
    public String dailyGoods(Model model) {
        model.addAttribute("items", itemRepository.findByCategory("DailyGoods"));
        model.addAttribute("queryName", "生活用品类商品");
        return "query_result";
    }

    // 基本查询4：u001发布的商品
    @GetMapping("/u001-items")
    public String u001Items(Model model) {
        model.addAttribute("items", itemRepository.findBySellerId("u001"));
        model.addAttribute("queryName", "u001发布的商品");
        return "query_result";
    }

    // 连接查询1：已售商品+买家姓名
    @GetMapping("/sold-with-buyer")
    public String soldWithBuyer(Model model) {
        List<Object[]> results = itemRepository.findSoldItemsWithBuyerName();
        model.addAttribute("results", results);
        model.addAttribute("queryName", "已售商品及买家姓名");
        return "join_query_result";
    }

    // 连接查询2：订单详情（商品名+买家名+日期）
    @GetMapping("/order-details")
    public String orderDetails(Model model) {
        List<Object[]> results = orderRepository.findOrderDetails();
        model.addAttribute("results", results);
        model.addAttribute("queryName", "订单详情（商品+买家+日期）");
        return "join_query_result";
    }

    // 连接查询3：卖家u001的商品是否被购买
    @GetMapping("/u001-purchase-status")
    public String u001PurchaseStatus(Model model) {
        List<Object[]> results = orderRepository.findU001ItemsWithOrder();
        model.addAttribute("results", results);
        model.addAttribute("queryName", "u001商品购买状态");
        return "u001_purchase";
    }

    // 添加新商品
    @PostMapping("/add-item")
    public String addItem(@RequestParam String itemId,
                          @RequestParam String itemName,
                          @RequestParam String category,
                          @RequestParam Double price,
                          @RequestParam String sellerId) {
        Item item = new Item();
        item.setItemId(itemId);
        item.setItemName(itemName);
        item.setCategory(category);
        item.setPrice(BigDecimal.valueOf(price));
        item.setStatus(0);
        item.setSellerId(sellerId);
        itemRepository.save(item);
        return "redirect:/items";
    }

    // 修改商品价格
    @PostMapping("/update-price")
    public String updatePrice(@RequestParam String itemId, @RequestParam Double price) {
        itemRepository.findById(itemId).ifPresent(item -> {
            item.setPrice(BigDecimal.valueOf(price));
            itemRepository.save(item);
        });
        return "redirect:/items";
    }

    // 删除未售出商品
    @PostMapping("/delete-item")
    public String deleteItem(@RequestParam String itemId) {
        itemRepository.findById(itemId).ifPresent(item -> {
            if (item.getStatus() == 0) {
                itemRepository.delete(item);
            }
        });
        return "redirect:/items";
    }

    // 购买商品
    @PostMapping("/buy-item")
    public String buyItem(@RequestParam String itemId,
                          @RequestParam String buyerId,
                          Model model) {
        boolean success = orderService.buyItem(itemId, buyerId);
        if (!success) {
            model.addAttribute("error", "购买失败：商品不存在或已被购买");
        }
        return "redirect:/items";
    }
}