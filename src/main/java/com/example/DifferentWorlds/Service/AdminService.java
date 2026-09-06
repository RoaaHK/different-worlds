package com.example.DifferentWorlds.Service;

import com.example.DifferentWorlds.Entity.AdministratorEntity;
import com.example.DifferentWorlds.Entity.LiteraryWorksEntity;
import com.example.DifferentWorlds.Repository.AdministratorRepository;
import com.example.DifferentWorlds.Repository.LiteraryWorksRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
// TODO avoid redundant code.
public class AdminService  {

    private final AdministratorRepository adminRepository;
    private final LiteraryWorksRepository literaryWorkRepository;

    @Autowired
    public AdminService(AdministratorRepository adminRepository, LiteraryWorksRepository literaryWorkRepository) {
        this.adminRepository = adminRepository;
        this.literaryWorkRepository= literaryWorkRepository;
    }

    @Transactional(readOnly = true)
    public AdministratorEntity getAdmin(Integer adminId) {
        return adminRepository.findById(adminId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Admin not found with ID: " + adminId));
    }

    @Transactional(readOnly = true)
    public AdministratorEntity getAdminByUserName(String userName) {
        return adminRepository.findByUserName(userName)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Admin not found with username: " + userName));
    }

    @Transactional
    public void approveWork(Integer workId) {
        LiteraryWorksEntity work = literaryWorkRepository.findById(workId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Literary work not found with ID: " + workId));
//        if (work.getApprovalStatus() == LiteraryWorkStatus.APPROVED) {
//            throw new IllegalStateException("Work is already approved");
//        }
//        work.setApprovalStatus(LiteraryWorkStatus.APPROVED);
        literaryWorkRepository.save(work);
    }


    @Transactional
    public void rejectWork(Integer workId) {
        LiteraryWorksEntity work = literaryWorkRepository.findById(workId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Literary work not found with ID: " + workId));
//        if (work.getApprovalStatus() == LiteraryWorkStatus.REJECTED) {
//            throw new IllegalStateException("Work is already rejected");
//        }
//        work.setApprovalStatus(LiteraryWorkStatus.REJECTED);
        literaryWorkRepository.save(work);
    }

    @Transactional
    public void updatePassword(Integer adminId, String newEncodedPassword) {
        AdministratorEntity existing = adminRepository.findById(adminId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Admin not found with ID: " + adminId));
        existing.setPassword(newEncodedPassword);
        adminRepository.save(existing);
    }

    @Transactional
    public void addAdmin(AdministratorEntity admin) {
        if (adminRepository.findByUserName(admin.getUserName()).isPresent()) {
            throw new IllegalArgumentException(
                    "Admin already exists with username: " + admin.getUserName());
        }
        adminRepository.save(admin);
    }

    @Transactional
    public void deleteAdmin(Integer adminId) {
        if (!adminRepository.existsById(adminId)) {
            throw new EntityNotFoundException(
                    "Admin not found with ID: " + adminId);
        }
        adminRepository.deleteById(adminId);
    }
}

