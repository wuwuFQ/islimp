package com.javakc.islimp.modules.phone.dao;

import com.javakc.islimp.modules.phone.entity.Phone;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PhoneDao extends ElasticsearchRepository<Phone, Integer> {

    public List<Phone> findByNameLike(String name);
    public List<Phone> findByPriceBetween(double a, double b);

    /**
     * Repository<Phone, Integer>
     *
     * CrudRepository<Phone, Integer>
     *
     * PagingAndSortingRepository<Phone, Integer>
     *
     * ElasticsearchRepository<Phone, Integer>
     */
}
