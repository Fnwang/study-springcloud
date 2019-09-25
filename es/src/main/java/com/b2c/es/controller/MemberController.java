package com.b2c.es.controller;

import com.b2c.es.mapper.MemberMapper;
import com.b2c.es.model.MemberModel;
import com.b2c.es.service.IMemberService;
import com.b2c.es.service.esService.MemberServiceES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 会员模块API
 */
@CacheConfig(cacheNames = "member")
/*
 *  cacheconfig cachenames 指定下方全都用名字member（可以指定多个name）
 *  Cacheable：用户查询后存放数据，方法执行前先查找缓存内是否有数据。
 *             key只能从参数中指定，不能从返回结果#result设置。
 *  CachePut：用于数据新增、编辑时，把数据往缓存中存储数据。
 *             key设置一般使用#reslut，如：#reslut.id
 *  CacheEvict:用于删除数据时，从缓存中清除数据。可以一次性清除干净
 *             也可以指定key。
 *  缓存cachenames需保持一致，即：新增、修改、删除、查询（如按id查询）保持一致。
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    //member映射接口
    @Autowired
    MemberMapper memberMapper;

    //member服务接口
    @Autowired
    IMemberService iMemberService;
    //es服务
    @Autowired
    MemberServiceES memberServiceES;





    //--mybatis

    /**
     * 新增数据
     * 加RequestBody注解
     * 前端发送数据必须封装成json对象格式。
     *
     * @param memberModel
     * @return
     */
    @PostMapping("/add")
    @ResponseBody

    public String add(@RequestBody MemberModel memberModel){
        log.info("memberModel==>"+memberModel.toString());
        if(memberMapper.addData(memberModel))
        {
          return   memberModel.getId();
        }
        return null;
    }

    /**
     * 通过id获取会员信息
     * @param id
     * @return
     */

    @GetMapping("/info/{id}")
    public MemberModel getMbById( @PathVariable(name = "id") String id ) {
        log.info("id=======>>>"+id);
        return memberMapper.getMbById(id);
    }

    /**
     * 通过用户名称查询用户
     * @param username
     * @return
     */
    @GetMapping("/list/e")
    public List<MemberModel> getMbListEntity(String username){
        log.info("getMemberByName=======>>>"+username);
        return memberMapper.getMbListEntity(username);
    }

    /**
     * 模糊查询，只带回部分参数，并以map类型存储在list中
     * @param username
     * @return
     */
    @GetMapping("/list/m")
    public List<Map<String,Object>> getEmpListMap(String username){
        log.info("getMemberByName=======>>>"+username);
        return memberMapper.getMbListMap(username);
    }

    /**
     * 多表关联查询
     * @return
     */
    @GetMapping("/list/more")
    public List<Map<String,Object>>  getLists(){
        return memberMapper.getLists();
    }

    /**
     * 修改
     * @param memberModel
     * @return
     */
    @PutMapping("/edit")
    public int update(@RequestBody MemberModel memberModel){
        log.info("memberModel==>"+memberModel.toString());
        return  memberMapper.upData(memberModel);
    }

    /**
     * 通过id删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public int delete( @PathVariable(name = "id") String id ) {
        log.info("id=======>>>"+id);
        //删除mysql数据
        return memberMapper.delData(id);
    }


    //--mybatis-plus 、es

    /**
     * 新增
     *  1、先新增ES
     *  2、在新增mysql
     *  3、缓存：当结果不等于null，放入缓存
     * @param memberModel
     * @return
     */
    @PostMapping("/addDB")
    @CachePut(key = "#result.id")
    public MemberModel addDB(@RequestBody MemberModel memberModel) {
        try {

            log.info("新增数据");

            //保存数据到 mysql
            //方式一：mybatis-plus自带方法save
            //iMemberService.save(memberModel);
            //方式二：mapper中定义的方法
            memberMapper.addData(memberModel);

            log.info("mysql："+memberModel.toString());

            //保存数据到 es
            memberServiceES.addOrUpdateES(memberModel);
            //返回
            return memberModel;

        }catch (Exception e){
             log.info(e.getMessage());
        }
        return null;
    }


    /**
     * 新增
     *  1、先新增ES
     *  2、在新增mysql
     *  3、缓存：当结果不等于null，放入缓存
     * @param dataList
     * @return
     */
    @PostMapping("/addBatch")

    public boolean addBatch(@RequestBody List<MemberModel> dataList) {
        try {

            List<MemberModel> saveDataList = new ArrayList<MemberModel>();

            //循环生成id
            for (MemberModel memberModel : dataList){
                memberModel.setId(UUID.randomUUID().toString().replace("-", ""));
                saveDataList.add(memberModel);
            }

            log.info("批量新增数据到mysql中"+saveDataList.toString());

            //保存数据到 mysql
            //方式一：调用mybatis的mapper接口定义的方法
            memberMapper.addBatch(saveDataList);
            //方式二：调用mybatis-plus自带的批量操作方法
            // iMemberService.saveBatch(dataList);

            log.info("开始保持到ES中");

            //保存数据到 es
            memberServiceES.addOrUpdateES(saveDataList);
            //返回
            return true;

        }catch (Exception e){
            log.info(e.getMessage());
        }
        return false;
    }


    /**
     * 修改
     * @param memberModel
     * @return
     */
    @PutMapping("/updateDB")
    @CachePut(key = "#result.id")
    public MemberModel updateDB(@RequestBody MemberModel memberModel) {

        log.info("修改数据");
        //更新mysql
        iMemberService.saveOrUpdate(memberModel);
        //更新es
        memberServiceES.addOrUpdateES(memberModel);
        //返回
        return memberModel;
    }

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    @GetMapping("/es/{id}")
    @Cacheable(key = "#id")
    //@Cacheable(keyGenerator = "myKeyGenerator") //自定义生成规则
    public MemberModel getMbByIdFromES(@PathVariable("id") String id){





        log.info("根据id查询数据");
        MemberModel memberModel = null;
        Optional<MemberModel> optional = memberServiceES.getMemberRepository().findById(id);
        log.info("optional==>>"+optional);
        if(optional !=null){
            memberModel = optional.get();
        }
        return memberModel;
    }

    /**
     * es高性能模糊查询
     */
    @GetMapping("/search")
    @Cacheable(key = "#key")
    /*
    @Caching(
            cacheable = {
                    @Cacheable(key = "#key",cacheManager ="myCacheManager" )
            },
            put = {
                   // @CachePut(key = "#result.iterator().")
            }
            )
    */
    public Iterable<MemberModel> search(String key){
        log.info("查询关键字=======>>>"+key);
        //从es中查询数据
        return memberServiceES.searchES(key);
    }

    /**
     * 通过id删除用户
     *  1、先删除es
     *  2、再删除mysql
     * @param id
     * @return
     */
    @DeleteMapping("/delDB/{id}")
    @CacheEvict(key = "#id")
    public int delDB( @PathVariable(name = "id") String id ) {
        log.info("删除id=======>>>"+id);
        //删除es数据
        memberServiceES.delES(id);
        //删除mysql数据
        return memberMapper.delData(id);
    }
}
