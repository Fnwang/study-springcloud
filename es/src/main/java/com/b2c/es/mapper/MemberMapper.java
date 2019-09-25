package com.b2c.es.mapper;

import com.b2c.es.model.MemberModel;
import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.ResultSetType;

import java.util.List;
import java.util.Map;

/**
 * 会员映射接口
 */
@Mapper
public interface MemberMapper  extends BaseMapper<MemberModel> {

    /**
     * 单个新增信息
     * @param member
     * @return
     */
    //新增语句
    @Insert("insert into member(id,username,sex,city,sign) values "
            +"(#{id},#{username},#{sex},#{city},#{sign})")
    public boolean addData(MemberModel member);

    /**
     * 批量新增数据
     * @param dataList
     * @return
     */
    @Insert(value = {
            "<script>",
            "insert into member(id,username,sex,city,sign) values ",
            //循环配置，遍历数据是dataList，当前是item
            "<foreach collection='dataList' item='item' index='index' separator=','>",
            "(#{item.id},#{item.username},#{item.sex},#{item.city},#{item.sign})",
            "</foreach>",
            "</script>"
    })
    public boolean addBatch(@Param("dataList") List<MemberModel> dataList) ;


    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Select("select * from member where id=#{id}")
    public  MemberModel getMbById(String id);

    /**
     * 更新信息
     * @param member
     * @return
     */
    @Update("update member " +
            " set username=#{username},sex=#{sex},city=#{city},sign=#{sign} " +
            " where id=#{id}")
    //返回更新数据对象
    public int upData(MemberModel member) ;


    /**
     * 模糊查询
     * @param username
     * @return
     */
    @Select("select * from member where username like  concat('%',#{username},'%')")
    public  List<MemberModel> getMbListEntity(String username);

    /**
     * 模糊查询，只带回部分参数，并以map类型存储在list中
     * @param username
     * @return
     */
    @Select("select " +
            "  id,username,sex,city,sign " +
            "from member " +
            "where username like  concat('%',#{username},'%')")
    public  List<Map<String,Object>> getMbListMap(String username);

    /**
     * 更具用户删除
     * @param id
     * @return
     */
    @Delete("delete from member where id=#{id}")
    public int delData(String id);

    /**
     * 多表关联查询
     * @return
     */
    @Select( " select "
            +"    a.id as id ,"
            +"    a.username as username ,"
            +"    b.pass_word as password "
            +" from  member a "
            +" inner join user b ON b.user_name = a.username "
            +" ")
    public List<Map<String,Object>> getLists();

}
