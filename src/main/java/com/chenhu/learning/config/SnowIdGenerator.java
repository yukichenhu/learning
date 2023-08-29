package com.chenhu.learning.config;

import com.chenhu.learning.utils.SnowFlakeUtil;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author 陈虎
 * @date 2022-06-14 9:30
 */
@Component
public class SnowIdGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return String.valueOf(SnowFlakeUtil.snowflakeId());
    }
}
