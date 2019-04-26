package com.nowcoder.dao;

import com.nowcoder.model.Admin;
import com.nowcoder.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminDAO {
    String TABLE_NAME = "admin";
    String INSET_FIELDS = " username, password, salt ";
    String SELECT_FIELDS = " id, username, password, salt";

    @Insert({"insert into ", TABLE_NAME, "(", INSET_FIELDS,
            ") values (#{username},#{password},#{salt})"})
    int addAdmin(Admin admin);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Admin selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where username=#{username}"})
    Admin selectByName(String username);

    @Update({"update ", TABLE_NAME, " set password=#{password} where id=#{id}"})
    void updatePassword(Admin admin);

    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);

}
