package com.ming.mapper;

import com.ming.entity.Menu;
import com.ming.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserInfoMapper {
    /**
     * 根据用户 Id 查询用户角色
     * @param userId
     * @return
     */
    List<Role> selectRoleByUserId(Long userId);
    /**
     * 根据用户 Id 查询菜单
     * @param userId
     * @return
     */
    List<Menu> selectMenuByUserId(Long userId);

}
