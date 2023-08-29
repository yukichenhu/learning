package com.chenhu.learning.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author chenhu
 * @description
 * @date 2023-08-21 13:41
 */
@Data
@Table(name = "cdp_user_info")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    @Id
    @GenericGenerator(name = "snowId", strategy = "com.chenhu.learning.config.SnowIdGenerator")
    @GeneratedValue(generator = "snowId")
    private String userId;
    private String userName;
    private String birth;
    private String phone;
    private Integer age;
    private String education;
    private String sex;
    private String maritalStatus;
    private String profession;
    private String workStatus;
    private String income;
    private Integer workingYears;
    private String province;
    private String city;
    private String salesDepartment;
    private Timestamp processDate;
    private String userValue;
    private Integer totalAssets;
    private Integer birthMonth;
    private String born;
    private String ageGrades;
    private String dateOfBirth;
    private String appInitFirst;
    private Integer telRegisterDays;
    @Column(name = "7_buy_stock_num")
    private Integer buyStockNum7;
    @Column(name = "30_buy_stock_num")
    private Integer buyStockNum30;
    @Column(name = "90_buy_stock_num")
    private Integer buyStockNum90;
    private Integer yesterdayBuyStockNum;
    private String pushClickRate;
    private String smsClickRate;
    private String telConnectionRate;
}
