package com.example.DifferentWorlds.Controller;

import com.example.DifferentWorlds.Entity.LiteraryWorksEntity;
import com.example.DifferentWorlds.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    // TODO avoid using auto wire as best practice of spring use dependency injection instead using constructor or setter
//    @Autowired
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
//    @PatchMapping("/works/{id}/approve")
//    public ResponseEntity<LiteraryWorks> approveWork(@PathVariable Long id) {
//        LiteraryWorks approvedWork = adminService.approveWork(id);
//        return ResponseEntity.ok(approvedWork);
//    }

    @PatchMapping("/works/{id}/reject")
    public ResponseEntity<LiteraryWorksEntity> rejectWork(@PathVariable Integer id) {
        LiteraryWorksEntity rejectedWork = adminService.rejectWork(id);
        return ResponseEntity.ok(rejectedWork);
    }
}