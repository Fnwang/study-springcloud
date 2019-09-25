package com.b2c.b2cprojectlogin.comm;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 分页查询
 */
@Data
@NoArgsConstructor
public class PageUtil<T>  {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());


    //当前页
    private long current;

    //每页显示
    private long size = 10;

    //总页数
    private long pages;

    //总记录数
    private long total;

    private List<T> records;


    /**
     * 2个参数的构造函数
     * @param current
     * @param queryWrapper
     */
    public PageUtil(long current, IService<T> iService, Wrapper<T> queryWrapper)
    {

        this.getPage(current,iService,queryWrapper,0);
    }

    /**
     * 3个参数的构造函数
     * @param current
     * @param size
     * @param queryWrapper
     */
    public PageUtil(long current, IService<T> iService, Wrapper<T> queryWrapper, long size)
    {
        this.getPage(current,iService,queryWrapper,size);
    }

    /**
     * 私有的查询方法
     * @param current
     * @param size
     * @param queryWrapper
     */
    private void getPage(long current,IService<T> iService, Wrapper<T> queryWrapper,long size)
    {
        if(current>0) this.current = current;
        if(size>0)    this.size    = size;
        //
        Page page = new Page(this.getCurrent(), this.getSize());

        IPage<T> iPage =  iService.page(page,queryWrapper);

        //设置查询结果集
        this.setRecords(iPage.getRecords());
        this.setPages(iPage.getPages());
        this.setTotal(iPage.getTotal());
    }


}
