package com.sprokazin.cakesShop.goods;

import com.sprokazin.cakesShop.rest.dto.cake.CakeMoreInfo;
import com.sprokazin.cakesShop.rest.dto.cake.Cakes;

public interface CakeService {

    Cakes getCakes();

    CakeMoreInfo getMoreInfo(Long id);

    Long addCake(CakeMoreInfo cake);

    void deleteCake(Long id);

    CakeMoreInfo getCake(Long id);

}
