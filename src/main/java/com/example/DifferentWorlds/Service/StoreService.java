package com.example.DifferentWorlds.Service;
import com.example.DifferentWorlds.Entity.Author;
import com.example.DifferentWorlds.Entity.LiteraryWorks;
import com.example.DifferentWorlds.Enums.ContentTypes;
import com.example.DifferentWorlds.Enums.FictionGenres;
import com.example.DifferentWorlds.Enums.NonFictionGenres;
import com.example.DifferentWorlds.Repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StoreService {
    /// use constructor injection not Field injection
    private final ItemRepository itemRepository;

    @Autowired
    public StoreService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    public LiteraryWorks getItem(LiteraryWorks item, long itemId){
        if(itemRepository.findById(itemId).isEmpty()) {
            System.out.println("Item Doesn't Found");
        }
        return item;
    }
    public void addItem(LiteraryWorks item){
        if(itemRepository.findByTitle(item.getTitle()).isEmpty()) {
            itemRepository.save(item);
        }
    }
    public void deleteItem(LiteraryWorks item){
        if(itemRepository.findByTitle(item.getTitle()).isPresent()) {
            itemRepository.delete(item);
        }
    }
    public void incCounts(LiteraryWorks item){
        Optional<LiteraryWorks> optionalObj=itemRepository.findByTitle(item.getTitle());

        if(optionalObj.isEmpty()) {
            itemRepository.save(item);
        }
        else{
            LiteraryWorks obj=optionalObj.get();
            obj.setCounts(obj.getCounts()+item.getCounts());
            itemRepository.save(obj);
        }
    }

    // 5-10 through exception, check if > | ==
    // 0 exception
    public void decCounts(LiteraryWorks item){
        Optional<LiteraryWorks> optionalObj=itemRepository.findByTitle(item.getTitle());
        if(optionalObj.isPresent() && optionalObj.get().getCounts()>= item.getCounts()) {
            optionalObj.get().setCounts(optionalObj.get().getCounts()-item.getCounts());
            itemRepository.save(optionalObj.get());
        }
        else {
             throw new ArithmeticException() ;
        }
    }
    public void sale(LiteraryWorks item, double saleVal){
        Optional<LiteraryWorks> optionalObj=itemRepository.findByTitle(item.getTitle());
        if(optionalObj.get().isInStore()){
            optionalObj.get().setPrice(optionalObj.get().getPrice()*saleVal);
            itemRepository.save(optionalObj.get());
        }
        else
            throw new NoSuchElementException();
    }

    public List<LiteraryWorks> getAllItems() {
        return itemRepository.findAll();
    }


    public void addItem(String name, String about, double price, boolean inStore,
                        ContentTypes contentType, NonFictionGenres nonFictionGenre,
                        FictionGenres fictionGenre, Author author) {
        // TODO extrtact into another method
        LiteraryWorks item = new LiteraryWorks();
        item.setTitle(name);
        item.setAbout(about);
        item.setPrice(price);
        item.setInStore(inStore);
        item.setContentType(contentType);
        item.setNonFictionGenre(nonFictionGenre);
        item.setFictionGenre(fictionGenre);
        item.setAuthor(author);
        //item.setApprovedByAdmin(false);
        itemRepository.save(item);
    }
    public void setNonFictionGenre(LiteraryWorks item, NonFictionGenres genre) {
        if (item.getFictionGenre() != null) {
            throw new IllegalArgumentException("Cannot set both fictionGenre and nonFictionGenre");
        }
        item.setNonFictionGenre(genre);
    }

    public void setFictionGenre(LiteraryWorks item, FictionGenres genre) {
        if (item.getNonFictionGenre() != null) {
            throw new IllegalArgumentException("Cannot set both nonFictionGenre and fictionGenre");
        }
        item.setFictionGenre(genre);
    }
}
