package com.example.DifferentWorlds.Service;

import com.example.DifferentWorlds.Entity.AdministratorEntity;
import com.example.DifferentWorlds.Entity.LiteraryWorksEntity;
import com.example.DifferentWorlds.Repository.AdministratorRepository;
import com.example.DifferentWorlds.Repository.LiteraryWorksRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService  {

    private final AdministratorRepository adminRepository;
    private final LiteraryWorksRepository literaryWorkRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(AdministratorRepository adminRepository,
                        LiteraryWorksRepository literaryWorkRepository,
                        PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.literaryWorkRepository= literaryWorkRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public AdministratorEntity getAdmin(Integer adminId) {
        return findAdmin(adminId);
    }

    @Transactional(readOnly = true)
    public AdministratorEntity getAdminByUserName(String userName) {
        return adminRepository.findByUserName(userName)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Admin not found with username: " + userName));
    }

    @Transactional
    public void approveWork(Integer workId) {
        LiteraryWorksEntity work = findWork(workId);
//        if (work.getApprovalStatus() == LiteraryWorkStatus.APPROVED) {
//            throw new IllegalStateException("Work is already approved");
//        }
//        work.setApprovalStatus(LiteraryWorkStatus.APPROVED);
        literaryWorkRepository.save(work);
    }


    @Transactional
    public void rejectWork(Integer workId) {
        LiteraryWorksEntity work = findWork(workId);
//        if (work.getApprovalStatus() == LiteraryWorkStatus.REJECTED) {
//            throw new IllegalStateException("Work is already rejected");
//        }
//        work.setApprovalStatus(LiteraryWorkStatus.REJECTED);
        literaryWorkRepository.save(work);
    }

    @Transactional
    public void updatePassword(Integer adminId, String newRawPassword) {
        AdministratorEntity existing = findAdmin(adminId);
        existing.setPassword(passwordEncoder.encode(newRawPassword));
        adminRepository.save(existing);
    }

    @Transactional
    public void deleteAdmin(Integer adminId) {
        if (!adminRepository.existsById(adminId)) {
            throw new EntityNotFoundException(
                    "Admin not found with ID: " + adminId);
        }
        adminRepository.deleteById(adminId);
    }

    private AdministratorEntity findAdmin(Integer adminId) {
        return adminRepository.findById(adminId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Admin not found with ID: " + adminId));
    }

    private LiteraryWorksEntity findWork(Integer workId) {
        return literaryWorkRepository.findById(workId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Literary work not found with ID: " + workId));
    }
}
