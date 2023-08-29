package com.chenhu.learning;

import com.chenhu.learning.controller.BatchService;
import com.chenhu.learning.entity.UserInfo;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author chenhu
 * @description
 * @date 2023-08-21 13:55
 */
@SpringBootTest
public class UserInfoTest {

    @Autowired
    private BatchService batchService;

    @Test
    public void mockUsers() {
        //批量生成10w条用户数据
        for (int i = 0; i < 200; i++) {
            insert500Users();
            System.out.println("已生成" + 500 * (i + 1) + "条数据！");
        }
    }

    private void insert500Users() {
        List<UserInfo> users = new ArrayList<>();
        DateTimeFormatter birthFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String[] educations = new String[]{"大专", "本科", "硕士", "博士"};
        String[] marriage = new String[]{"已婚", "未婚"};
        String[] professions = new String[]{"程序员", "公务员", "销售人员", "项目管理", "客服", "售后咨询", "无职业"};
        String[] workStatuses = new String[]{"在职", "离职"};
        String[] provinces = new String[]{"江苏省", "浙江省", "安徽省"};
        Map<String, String> cityMap = ImmutableMap.of("江苏省", "南京市", "浙江省", "杭州市", "安徽省", "合肥市");
        String[] userValues = new String[]{"高价值", "低价值", "中价值"};
        String[] initFirst = new String[]{"手机号", "身份证", "银行卡"};
        for (int i = 0; i < 500; i++) {
            String username = getName();
            LocalDate birthDay = getBirthday();
            String birth = birthDay.format(birthFmt);
            String phone = getTel();
            int age = getAge();
            String education = educations[new Random().nextInt(educations.length)];
            String sex = getSex();
            String maritalStatus = marriage[new Random().nextInt(marriage.length)];
            String profession = professions[new Random().nextInt(professions.length)];
            String workStatus = workStatuses[new Random().nextInt(workStatuses.length)];
            String income = new Random().nextInt(100000) + "元";
            Integer workingYears = new Random().nextInt(20) + 1;
            String province = provinces[new Random().nextInt(provinces.length)];
            String city = cityMap.get(province);
            String salesDepartment = city + "分行";
            Timestamp processDate = Timestamp.valueOf(LocalDateTime.now());
            String userValue = userValues[new Random().nextInt(userValues.length)];
            Integer totalAssets = new Random().nextInt(1000000);
            Integer birthMonth = birthDay.getMonthValue();
            String born = birthDay.getYear() + "";
            String ageGrades = getAgeGrades(age);
            String dateOfBirth = birthDay.getDayOfMonth() + "";
            String appInitFirst = initFirst[new Random().nextInt(initFirst.length)];
            Integer telRegisterDays = new Random().nextInt(30) + 1;
            int yesterdayBuyStockNum = new Random().nextInt(3);
            int buyStockNum7 = new Random().nextInt(7) + yesterdayBuyStockNum;
            int buyStockNum30 = buyStockNum7 * 4;
            Integer buyStockNum90 = buyStockNum30 * 3;
            String publishClickRate = (new Random().nextInt(100) + 1) + "%";
            String smsClickRate = (new Random().nextInt(100) + 1) + "%";
            String telConnectionRate = (new Random().nextInt(100) + 1) + "%";

            UserInfo userInfo = UserInfo.builder()
                    .userName(username).birth(birth).phone(phone).age(age).education(education)
                    .sex(sex).maritalStatus(maritalStatus).profession(profession).workStatus(workStatus).income(income)
                    .workingYears(workingYears).province(province).city(city).salesDepartment(salesDepartment).processDate(processDate)
                    .userValue(userValue).totalAssets(totalAssets).birthMonth(birthMonth).born(born).ageGrades(ageGrades)
                    .dateOfBirth(dateOfBirth).appInitFirst(appInitFirst).telRegisterDays(telRegisterDays).buyStockNum7(buyStockNum7)
                    .buyStockNum30(buyStockNum30).buyStockNum90(buyStockNum90).yesterdayBuyStockNum(yesterdayBuyStockNum)
                    .pushClickRate(publishClickRate).smsClickRate(smsClickRate).telConnectionRate(telConnectionRate)
                    .build();
            users.add(userInfo);
        }
        batchService.batchInsert(users);
    }

    /**
     * 返回姓名
     */
    private String getName() {
        Random random = new Random(System.currentTimeMillis());
        /* 598 百家姓 */
        String[] Surname = {"赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈", "褚", "卫", "蒋", "沈", "韩", "杨", "朱", "秦", "尤", "许",
                "何", "吕", "施", "张", "孔", "曹", "严", "华", "金", "魏", "陶", "姜", "戚", "谢", "邹", "喻", "柏", "水", "窦", "章", "云", "苏", "潘", "葛", "奚", "范", "彭", "郎",
                "鲁", "韦", "昌", "马", "苗", "凤", "花", "方", "俞", "任", "袁", "柳", "酆", "鲍", "史", "唐", "费", "廉", "岑", "薛", "雷", "贺", "倪", "汤", "滕", "殷",
                "罗", "毕", "郝", "邬", "安", "常", "乐", "于", "时", "傅", "皮", "卞", "齐", "康", "伍", "余", "元", "卜", "顾", "孟", "平", "黄", "和",
                "穆", "萧", "尹", "姚", "邵", "湛", "汪", "祁", "毛", "禹", "狄", "米", "贝", "明", "臧", "计", "伏", "成", "戴", "谈", "宋", "茅", "庞", "熊", "纪", "舒",
                "屈", "项", "祝", "董", "梁", "杜", "阮", "蓝", "闵", "席", "季", "麻", "强", "贾", "路", "娄", "危", "江", "童", "颜", "郭", "梅", "盛", "林", "刁", "钟",
                "徐", "邱", "骆", "高", "夏", "蔡", "田", "樊", "胡", "凌", "霍", "虞", "万", "支", "柯", "昝", "管", "卢", "莫", "经", "房", "裘", "缪", "干", "解", "应",
                "宗", "丁", "宣", "贲", "邓", "郁", "单", "杭", "洪", "包", "诸", "左", "石", "崔", "吉", "钮", "龚", "程", "嵇", "邢", "滑", "裴", "陆", "荣", "翁", "荀",
                "羊", "于", "惠", "甄", "曲", "家", "封", "芮", "羿", "储", "靳", "汲", "邴", "糜", "松", "井", "段", "富", "巫", "乌", "焦", "巴", "弓", "牧", "隗", "山",
                "谷", "车", "侯", "宓", "蓬", "全", "郗", "班", "仰", "秋", "仲", "伊", "宫", "宁", "仇", "栾", "暴", "甘", "钭", "厉", "戎", "祖", "武", "符", "刘", "景",
                "詹", "束", "龙", "叶", "幸", "司", "韶", "郜", "黎", "蓟", "溥", "印", "宿", "白", "怀", "蒲", "邰", "从", "鄂", "索", "咸", "籍", "赖", "卓", "蔺", "屠",
                "蒙", "池", "乔", "阴", "郁", "胥", "能", "苍", "双", "闻", "莘", "党", "翟", "谭", "贡", "劳", "逄", "姬", "申", "扶", "堵", "冉", "宰", "郦", "雍", "却",
                "璩", "桑", "桂", "濮", "牛", "寿", "通", "边", "扈", "燕", "冀", "浦", "尚", "农", "温", "别", "庄", "晏", "柴", "瞿", "阎", "充", "慕", "连", "茹", "习",
                "宦", "艾", "鱼", "容", "向", "古", "易", "慎", "戈", "廖", "庾", "终", "暨", "居", "衡", "步", "都", "耿", "满", "弘", "匡", "国", "文", "寇", "广", "禄",
                "阙", "东", "欧", "殳", "沃", "利", "蔚", "越", "夔", "隆", "师", "巩", "厍", "聂", "晁", "勾", "敖", "融", "冷", "訾", "辛", "阚", "那", "简", "饶", "空",
                "曾", "毋", "沙", "乜", "养", "鞠", "须", "丰", "巢", "关", "蒯", "相", "查", "后", "荆", "红", "游", "郏", "竺", "权", "逯", "盖", "益", "桓", "公", "仉",
                "督", "岳", "帅", "缑", "亢", "况", "郈", "有", "琴", "归", "海", "晋", "楚", "闫", "法", "汝", "鄢", "涂", "钦", "商", "牟", "佘", "佴", "伯", "赏", "墨",
                "哈", "谯", "篁", "年", "爱", "阳", "佟", "言", "福", "南", "火", "铁", "迟", "漆", "官", "冼", "真", "展", "繁", "檀", "祭", "密", "敬", "揭", "舜", "楼",
                "疏", "冒", "浑", "挚", "胶", "随", "高", "皋", "原", "种", "练", "弥", "仓", "眭", "蹇", "覃", "阿", "门", "恽", "来", "綦", "召", "仪", "风", "介", "巨",
                "木", "京", "狐", "郇", "虎", "枚", "抗", "达", "杞", "苌", "折", "麦", "庆", "过", "竹", "端", "鲜", "皇", "亓", "老", "是", "秘", "畅", "邝", "还", "宾",
                "闾", "辜", "纵", "侴", "万俟", "司马", "上官", "欧阳", "夏侯", "诸葛", "闻人", "东方", "赫连", "皇甫", "羊舌", "尉迟", "公羊", "澹台", "公冶", "宗正",
                "濮阳", "淳于", "单于", "太叔", "申屠", "公孙", "仲孙", "轩辕", "令狐", "钟离", "宇文", "长孙", "慕容", "鲜于", "闾丘", "司徒", "司空", "兀官", "司寇",
                "南门", "呼延", "子车", "颛孙", "端木", "巫马", "公西", "漆雕", "车正", "壤驷", "公良", "拓跋", "夹谷", "宰父", "谷梁", "段干", "百里", "东郭", "微生",
                "梁丘", "左丘", "东门", "西门", "南宫", "第五", "公仪", "公乘", "太史", "仲长", "叔孙", "屈突", "尔朱", "东乡", "相里", "胡母", "司城", "张廖", "雍门",
                "毋丘", "贺兰", "綦毋", "屋庐", "独孤", "南郭", "北宫", "王孙"};

        int index = random.nextInt(Surname.length - 1);
        String name = Surname[index]; //获得一个随机的姓氏

        /* 从常用字中选取一个或两个字作为名 */
        if (random.nextBoolean()) {
            name += getChinese() + getChinese();
        } else {
            name += getChinese();
        }

        return name;
    }

    private String getChinese() {
        String str = null;
        int highPos, lowPos;
        Random random = new Random();
        highPos = (176 + Math.abs(random.nextInt(71)));//区码，0xA0打头，从第16区开始，即0xB0=11*16=176,16~55一级汉字，56~87二级汉字
        random = new Random();
        lowPos = 161 + Math.abs(random.nextInt(94));//位码，0xA0打头，范围第1~94列

        byte[] bArr = new byte[2];
        bArr[0] = (new Integer(highPos)).byteValue();
        bArr[1] = (new Integer(lowPos)).byteValue();
        try {
            str = new String(bArr, "GB2312");    //区位码组合成汉字
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 返回手机号码
     */
    private int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }

    private final String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");

    private String getTel() {
        int index = getNum(0, telFirst.length - 1);
        String first = telFirst[index];
        String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
        String third = String.valueOf(getNum(1, 9100) + 10000).substring(1);
        return first + second + third;
    }

    /**
     * 功能：随机产生性别
     */
    private String getSex() {
        int randNum = new Random().nextInt(2) + 1;
        return randNum == 1 ? "男" : "女";
    }

    /**
     * 生成随机生日
     */
    private LocalDate getBirthday() {
        Random random = new Random();
        int minDay = (int) LocalDate.of(1900, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2015, 1, 1).toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    private int getAge() {
        return new Random().nextInt(100) + 1;
    }

    private String getAgeGrades(Integer age) {
        if (age < 7) {
            return "童年";
        } else if (age < 18) {
            return "少年";
        } else if (age < 41) {
            return "青年";
        } else if (age < 66) {
            return "中年";
        } else {
            return "老年";
        }
    }
}
