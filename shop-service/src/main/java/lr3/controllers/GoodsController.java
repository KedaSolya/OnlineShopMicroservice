package lr3.controllers;

import jakarta.persistence.EntityNotFoundException;
import lr3.helper.GoodsResponse;
import lr3.models.Goods;
import lr3.repo.GoodsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/goods")
public class GoodsController {

    @Autowired
    private GoodsRepo goodsRepo;

    @GetMapping("/all")
    public List<Goods> all(){
        return goodsRepo.findAll();
    }

    @GetMapping("/find")
    public GoodsResponse find(){
        GoodsResponse response = new GoodsResponse();
        response.getGoods().addAll(goodsRepo.findAll());
        return response;
    }

    @GetMapping("/{id}")
    public Goods findById(@PathVariable Long id){
        return goodsRepo.findById(id).get();
    }

    @PostMapping("/create")
    public void create(@RequestBody Goods goods){
        goodsRepo.save(goods);
    }

    @DeleteMapping("/{id}")
    public void deleteGoods(@PathVariable long id) {
        Optional<Goods> existingGoodsOptional = goodsRepo.findById(id);

        if (existingGoodsOptional.isPresent()) {
            Goods existingGoods = existingGoodsOptional.get();
            goodsRepo.delete(existingGoods);
        } else {
            throw new EntityNotFoundException("Goods not found with id: " + id);
        }
    }
}
