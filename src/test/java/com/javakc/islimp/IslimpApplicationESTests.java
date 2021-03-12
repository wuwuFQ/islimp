package com.javakc.islimp;

import com.javakc.islimp.modules.phone.entity.Phone;
import com.javakc.islimp.modules.phone.service.PhoneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.document.Document;

@SpringBootTest
class IslimpApplicationESTests {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private PhoneService phoneService;

    @Test
    void contextLoads() {
        //创建索引
//        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(Phone.class);
        // 构建索引库
        //        indexOperations.create();
        // 删除索引库
        //        indexOperations.delete();

//        Document document = indexOperations.createMapping();
//        indexOperations.putMapping(document);

//        Phone phone = new Phone(1, "HUAWEI Mate30 Pro 5G", "手机", "华为", 6399.00, "https://res.vmallres.com/pimages//product/6901443353262/800_800_AFFE9AA1E983E45E CB491A2900027BF8AF03C3990462FD2Bmp.png");
//        Phone phone = new Phone(2, "iPhone 12 Pro", "手机", "Apple", 8499.00, "https://res.vmallres.com/pimages//product/6901443353262/800_800_AFFE9AA1E983E45E CB491A2900027BF8AF03C3990462FD2Bmp.png");
//        Phone phone = new Phone(3, "小米10", "手机", "小米", 3799.00, "https://res.vmallres.com/pimages//product/6901443353262/800_800_AFFE9AA1E983E45E CB491A2900027BF8AF03C3990462FD2Bmp.png");
//        phoneService.save( phone );

        phoneService.findByNameLike("米").forEach(phone -> {
            System.out.println( phone );
        });

    }

}
