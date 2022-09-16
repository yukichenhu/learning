package com.chenhu.learning;

import com.chenhu.learning.entity.Animal;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

/**
 * @author 陈虎
 * @date 2022-06-15 8:34
 */
public class HutoolTest {

    @Test
    public void testClone() throws JsonProcessingException {
        Animal a=new Animal();
        a.setName(null);
        Animal.Detail detail=new Animal.Detail();
        detail.setAge(2);
        a.setDetail(detail);
        System.out.println(new ObjectMapper().writeValueAsString(a));
        //System.out.println(new GsonBuilder().create().toJson(a));
    }

    @SneakyThrows
    @Test
    public void test() {
        int num = 7 * 60 / 100;
        System.out.println(num);
    }
}
