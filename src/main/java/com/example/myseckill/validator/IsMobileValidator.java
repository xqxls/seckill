package com.example.myseckill.validator;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.myseckill.util.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Description: 手机号校验规则
 * @Author: huzhuo
 * @Date: Created in 2023/3/9 22:00
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile,String> {

    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(required){
            return ValidatorUtil.isMobile(s);
        }
        else{
            if(StringUtils.isBlank(s)){
                return true;
            }
            else{
                return ValidatorUtil.isMobile(s);
            }
        }
    }
}
