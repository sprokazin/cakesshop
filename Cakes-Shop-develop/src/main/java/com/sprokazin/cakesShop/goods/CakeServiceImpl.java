package com.sprokazin.cakesShop.goods;

import com.sprokazin.cakesShop.exceptions.CakeNotFoundException;
import com.sprokazin.cakesShop.rest.dto.cake.Cake;
import com.sprokazin.cakesShop.rest.dto.cake.CakeMoreInfo;
import com.sprokazin.cakesShop.rest.dto.cake.Cakes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CakeServiceImpl implements CakeService {

    private final CakeRepository cakeRepository;

    @Autowired
    public CakeServiceImpl(CakeRepository cakeRepository) {
        this.cakeRepository = cakeRepository;
    }

    @Override
    public Cakes getCakes() {
        List<CakeEntity> cakeEntityList = cakeRepository.findAll();
        List<Cake> cakesList = cakeEntityList.stream().map(cakeEntity -> {
            Cake cake = new Cake();
            cake.setId(cakeEntity.getId());
            cake.setName(cakeEntity.getName());
            cake.setCalories(cakeEntity.getCalories());
            cake.setWeight(cakeEntity.getWeight());
            cake.setPrice(cakeEntity.getPrice());
            cake.setImage(cakeEntity.getImage());
            cake.setAvailabilityOfCake(cakeEntity.getAvailabilityOfCake());
            return cake;
        }).filter(cake -> !cake.getAvailabilityOfCake().equals(AvailabilityOfCake.UNAVAILABLE)).collect(Collectors.toList());
        Cakes cakes = new Cakes();
        cakes.setCakeList(cakesList);
        return cakes;
    }

    @Override
    public CakeMoreInfo getMoreInfo(Long id) {
        return cakeRepository.findById(id)
                .map(cakeEntity -> {
                    CakeMoreInfo cakeMoreInfo = new CakeMoreInfo();
                    cakeMoreInfo.setId(cakeEntity.getId());
                    cakeMoreInfo.setName(cakeEntity.getName());
                    cakeMoreInfo.setCalories(cakeEntity.getCalories());
                    cakeMoreInfo.setWeight(cakeEntity.getWeight());
                    cakeMoreInfo.setPrice(cakeEntity.getPrice());
                    cakeMoreInfo.setImage(cakeEntity.getImage());
                    cakeMoreInfo.setComposition(cakeEntity.getCompositions());
                    cakeMoreInfo.setShelfLife(cakeEntity.getShelfLife());
                    return cakeMoreInfo;
                }).orElseThrow(()-> new CakeNotFoundException("Wrong cake id"));
    }

    @Override
    public Long addCake(CakeMoreInfo cake) {
        CakeEntity cakeEntity = new CakeEntity();
        cakeEntity.setCalories(cake.getCalories());
        cakeEntity.setImage(cake.getImage());
        cakeEntity.setCompositions(cake.getComposition());
        cakeEntity.setName(cake.getName());
        cakeEntity.setPrice(cake.getPrice());
        cakeEntity.setWeight(cake.getWeight());
        cakeEntity.setShelfLife(cake.getShelfLife());
        cakeEntity.setAvailabilityOfCake(cake.getAvailabilityOfCake());
        cakeRepository.saveAndFlush(cakeEntity);
        return cakeEntity.getId();
    }

    @Override
    public void deleteCake(Long id) {
        CakeEntity cake = cakeRepository.getById(id);
        cake.setAvailabilityOfCake(AvailabilityOfCake.UNAVAILABLE);
        cakeRepository.flush();
    }

    @Override
    public CakeMoreInfo getCake(Long id) {
        return cakeRepository.findById(id).map(cakeEntity -> {
                    CakeMoreInfo cake = new CakeMoreInfo();
                    cake.setId(cakeEntity.getId());
                    cake.setCalories(cakeEntity.getCalories());
                    cake.setName(cakeEntity.getName());
                    cake.setImage(cakeEntity.getImage());
                    cake.setPrice(cakeEntity.getPrice());
                    cake.setWeight(cakeEntity.getWeight());
                    cake.setShelfLife(cakeEntity.getStorageConditions());
                    cake.setComposition(cakeEntity.getCompositions());
                    return cake;
                })
                .orElseThrow(() -> new CakeNotFoundException("No such cake"));
    }
}
