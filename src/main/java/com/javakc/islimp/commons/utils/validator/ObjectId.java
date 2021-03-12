package com.javakc.islimp.commons.utils.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @program: islimp
 * @description: 自定义校验规则
 * @author: zhou hong gang
 * @create: 2020-12-12 11:26
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint( validatedBy = ObjectIdValidator.class)
@Repeatable(value = ObjectId.List.class)
public @interface ObjectId {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List{
        ObjectId[] value();
    }

}
