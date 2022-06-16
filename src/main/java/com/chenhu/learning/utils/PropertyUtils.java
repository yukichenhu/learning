package com.chenhu.learning.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 陈虎
 * @since 2022-06-02 15:36
 */
public class PropertyUtils {

    public static void copyNotNullProperty(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    private static String[] getNullPropertyNames(Object source) {
        BeanWrapper wrapper = new BeanWrapperImpl(source);
        Set<String> names = new HashSet<>();
        for (PropertyDescriptor pd : wrapper.getPropertyDescriptors()) {
            if (wrapper.getPropertyValue(pd.getName()) == null) {
                names.add(pd.getName());
            }
        }
        String[] results = new String[names.size()];
        return names.toArray(results);
    }
}
