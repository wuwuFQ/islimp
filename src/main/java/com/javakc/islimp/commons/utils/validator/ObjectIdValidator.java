package com.javakc.islimp.commons.utils.validator;

import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

/**
 * @program: islimp
 * @description: 自定义校验规则
 * @author: zhou hong gang
 * @create: 2020-12-12 11:26
 */
public class ObjectIdValidator implements ConstraintValidator<ObjectId, Object> {

    private ObjectId annotation;

    @Override
    public void initialize(ObjectId constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        if(!ObjectUtils.isEmpty(object))
        {
            try {
                Field idField = object.getClass().getSuperclass().getDeclaredField("id");
                idField.setAccessible(true);
                Object id = idField.get(object);
                if(!ObjectUtils.isEmpty(id))
                {
                    return true;
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
