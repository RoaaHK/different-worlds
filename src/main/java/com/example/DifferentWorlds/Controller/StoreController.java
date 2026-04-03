package com.example.DifferentWorlds.Controller;

import com.example.DifferentWorlds.Entity.LiteraryWorks;
import com.example.DifferentWorlds.Service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itemManagement")
public class StoreController {
    /// use constructor injection not Field injection
    private final StoreService storeService;

    @Autowired
    StoreController(StoreService storeservice){
        this.storeService=storeservice;
    }

    //{itemId}
    @GetMapping("/FindLiteraryWorksById")
    public void getItemById(@RequestBody LiteraryWorks item, @RequestParam int itemId) {
        storeService.getItem(item, itemId);
    }

    /// method names and path names should be camel case
    @GetMapping("/GetAllItems")
    public List<LiteraryWorks> getAllItems() {
        return storeService.getAllItems();
    }

    // TODO all controllers must return something
    @PostMapping("/AddItem") //add item from one that works in store
    public void addItem(@RequestBody LiteraryWorks item) {
        storeService.addItem(item);
    }

    @DeleteMapping("/deleteItem")
    public void deleteItem(LiteraryWorks item) {
        storeService.deleteItem(item);
    }

    @PutMapping("/sale")
    public void sale(LiteraryWorks item, double saleVal) {
        storeService.sale(item, saleVal);
    }

    @PutMapping("/addItems")
    public void incCounts(LiteraryWorks item) {
        storeService.incCounts(item);
    }

    @PutMapping("/sellItems")
    public void decCounts(LiteraryWorks item) {
        storeService.incCounts(item);
    }
}
