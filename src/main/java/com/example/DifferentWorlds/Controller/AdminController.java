package com.example.DifferentWorlds.Controller;

import com.example.DifferentWorlds.Service.AdminService;
import com.example.DifferentWorlds.dto.MessageResponseDTO;
import com.example.DifferentWorlds.dto.UpdatePasswordRequestDTO;
import com.example.DifferentWorlds.dto.UserResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getAdminById(@PathVariable Integer id) {
        var admin = adminService.getAdmin(id);
        return ResponseEntity.ok(new UserResponseDTO(
                admin.getId(), admin.getName(), admin.getUserName(), admin.getEmail(), "ADMIN"));
    }

    @GetMapping("/by-username/{userName}")
    public ResponseEntity<UserResponseDTO> getAdminByUserName(@PathVariable String userName) {
        var admin = adminService.getAdminByUserName(userName);
        return ResponseEntity.ok(new UserResponseDTO(
                admin.getId(), admin.getName(), admin.getUserName(), admin.getEmail(), "ADMIN"));
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<MessageResponseDTO> updatePassword(
            @PathVariable Integer id,
            @RequestBody @Valid UpdatePasswordRequestDTO request) {
        adminService.updatePassword(id, request.getNewPassword());
        return ResponseEntity.ok(new MessageResponseDTO("Password updated successfully."));
    }

    @PatchMapping("/works/{workId}/approve")
    public ResponseEntity<MessageResponseDTO> approveWork(@PathVariable Integer workId) {
        adminService.approveWork(workId);
        return ResponseEntity.ok(new MessageResponseDTO("Work approved."));
    }

    @PatchMapping("/works/{workId}/reject")
    public ResponseEntity<MessageResponseDTO> rejectWork(@PathVariable Integer workId) {
        adminService.rejectWork(workId);
        return ResponseEntity.ok(new MessageResponseDTO("Work rejected."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Integer id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }
}