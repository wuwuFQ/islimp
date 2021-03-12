package com.javakc.islimp.modules.phone.service;

import com.javakc.islimp.modules.phone.dao.PhoneDao;
import com.javakc.islimp.modules.phone.entity.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: islimp
 * @description: 逻辑层
 * @author: zhou hong gang
 * @create: 2020-12-19 10:06
 */
@Service
public class PhoneService {

    @Autowired
    private PhoneDao phoneDao;

    public void save(Phone phone)
    {
        phoneDao.save(phone);
    }

    public List<Phone> findByNameLike(String name)
    {
        return phoneDao.findByNameLike(name);
    }

}
