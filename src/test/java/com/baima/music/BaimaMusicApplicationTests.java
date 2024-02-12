package com.baima.music;

import com.baima.music.entity.User;
import com.baima.music.enums.Gender;
import com.baima.music.enums.RoleName;
import com.baima.music.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 测试类
 *
 * @author FlapyPan
 */
@SpringBootTest
class BaimaMusicApplicationTests {

    @Test
    void contextLoads() {
    }
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 可以使用这个方法来初始化数据库
     */
    @Test
    void addAdmin() {
        var user = new User();
        user.setRoles(String.join(",", RoleName.ROLE_USER.name(), RoleName.ROLE_ADMIN.name()));
        user.setUsername("admin@baima.com");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setNickname("adminadmin");
        user.setGender(Gender.UNKNOWN);
        user.setEnabled(true);
        user.setLocked(false);
        user.setEnabled(true);
        var save = userRepository.save(user);
        System.out.println(save);
    }

}
