package com.example.DifferentWorlds.Service;

import com.example.DifferentWorlds.Entity.Administrator;
import com.example.DifferentWorlds.Entity.Author;
import com.example.DifferentWorlds.Entity.Customer;
import com.example.DifferentWorlds.Entity.LiteraryWorks;
import com.example.DifferentWorlds.Enums.LiteraryWorkStatus;
import com.example.DifferentWorlds.Repository.AdministratorRepository;
import com.example.DifferentWorlds.Repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
// TODO avoid redundant code.
public class AdminService implements UserDetailsService {

    private final AdministratorRepository adminRepository;
    private final ItemRepository literaryWorkRepository;


    @Autowired
    public AdminService(AdministratorRepository adminRepository, ItemRepository literaryWorkRepository) {
        this.adminRepository = adminRepository;
        this.literaryWorkRepository= literaryWorkRepository;
    }
//    @Transactional
//    public LiteraryWorks approveWork(Long workId) {
//        LiteraryWorks work = literaryWorkRepository.findById(workId)
//                .orElseThrow(() -> new IllegalArgumentException("Work not found"));
//        work.setApprovalStatus(LiteraryWorkStatus.APPROVED);
//        return literaryWorkRepository.save(work);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Administrator admin = adminRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(admin.getUserName(), admin.getPassword(),
                new ArrayList<>());
    }


    @Transactional
    public LiteraryWorks rejectWork(Long workId) {
        LiteraryWorks work = literaryWorkRepository.findById(workId)
                .orElseThrow(() -> new IllegalArgumentException("Work not found"));
        //work.setApprovalStatus(LiteraryWorkStatus.REJECTED);
        return literaryWorkRepository.save(work);
    }

    public void updatePassword(Administrator admin) {
        Optional<Administrator> adminObj = adminRepository.findByUserName(admin.getUserName());
        // TODO
        adminObj.get().setPassword(admin.getPassword());
        adminRepository.save(adminObj.get());
    }

    public Administrator getAdmin(long adminId) {
        Optional<Administrator> adminOnj = adminRepository.findById(adminId);
        // TODO since using optional try avoid using if condition and use the function provided with optional and try do it on a one line
        if (adminOnj.isEmpty()) {
            throw new EntityNotFoundException("Customer not found with ID: " + adminId);
        }
        return adminOnj.get();
    }

    public Administrator getAdminUserName(String userName) {
        return adminRepository.findByUserName(userName)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + userName));
    }

    public void addAdmin(Administrator admin) {
        // TODO convert it into optional remove if and handle if the user is already existed
        if (adminRepository.findByUserName(admin.getUserName()).isEmpty()) {
            adminRepository.save(admin);
        }
    }

    public void deleteAdmin(Administrator admin) {
        if (adminRepository.findByUserName(admin.getUserName()).isPresent()) {
            adminRepository.delete(admin);
        }
    }
}

