package com.chenhu.learning;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.chenhu.learning.controller.BatchService;
import com.chenhu.learning.entity.Position;
import com.chenhu.learning.repository.PositionRepository;
import com.chenhu.learning.utils.SnowFlakeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 陈虎
 * @date 2022-06-14 13:43
 */
@SpringBootTest
public class BatchTest {
    @Autowired
    private BatchService batchService;
    @Autowired
    private PositionRepository positionRepository;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Test
    public void batchInsertTest() {
        //制造数据
        List<Position> records = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            records.add(Position.builder().positionName("java测试" + (i + 1)).deptId(1).build());
        }
        TimeInterval timer = DateUtil.timer();
        batchService.batchInsert(records);
        System.out.printf("共花费 %d毫秒%n", timer.interval());
    }

    @Test
    public void batchInsertTest2() {
        //制造数据
        List<Position> records = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            records.add(Position.builder().positionName("java测试" + (i + 1)).deptId(1).build());
        }
        TimeInterval timer = DateUtil.timer();
        positionRepository.saveAll(records);
        System.out.printf("共花费 %d毫秒%n", timer.interval());
    }

    @Test
    public void batchInsertTest3() {
        //制造数据
        List<Position> records = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            records.add(Position.builder()
                    .positionName("java测试" + (i + 1))
                    .deptId(1)
                    .positionId(SnowFlakeUtil.snowflakeId())
                    .updateTime(System.currentTimeMillis())
                    .build());
        }
        TimeInterval timer = DateUtil.timer();
        String sql = "insert into t_position(position_id,position_name,dept_id,update_time) values(?,?,?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, records.get(i).getPositionId());
                ps.setString(2, records.get(i).getPositionName());
                ps.setInt(3, records.get(i).getDeptId());
                ps.setLong(4, records.get(i).getUpdateTime());
            }

            @Override
            public int getBatchSize() {
                return records.size();
            }
        });
        System.out.printf("共花费 %d毫秒%n", timer.interval());
    }

    @Test
    public void testHeartBeat(){
        System.out.println(positionRepository.heartBeat(System.currentTimeMillis(),2L));
    }
}
