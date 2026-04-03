package com.example.DifferentWorlds.Service;

import com.example.DifferentWorlds.Entity.Guest;
import com.example.DifferentWorlds.Entity.LiteraryWorks;
import com.example.DifferentWorlds.Repository.GuestRepository;
import jakarta.persistence.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GuestService {
    private final GuestRepository guestRepository;
    GuestService(GuestRepository guestRepository){
        this.guestRepository = guestRepository;
    }
    /// wrong
    public Guest getGuest(long guestId) {
        Optional<Guest> guestObj = guestRepository.findById(guestId);
        if (guestObj.isEmpty()) {
            throw new EntityNotFoundException("Customer not found with ID: " + guestId);
        }
        return guestObj.get();
    }
    /// wrong
//    public Guest getGuestUserName(String userName) {
//        Optional<Guest> guestObj = guestRepository.findByUserName(userName);
//        if (guestObj.isEmpty()) {
//            throw new EntityNotFoundException("Customer not found with ID: " + userName);
//        }
//        return guestObj.get();
//    }
    public LiteraryWorks viewItems(LiteraryWorks items) {
        return items;
    }

}
