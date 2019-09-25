package com.b2c.es;

import com.b2c.es.model.StudentModel;
import com.b2c.es.esRepository.StudentRepository;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * 测试es-学生模块
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsApplicationTests {

	//日志
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	//学生
	@Autowired
	StudentRepository studentRepository;


	//es通用API
	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;

	//
	@Before
	public void contextLoads() {

		log.info("测试开始：====》》》");
	}

	/**
	 * 批量数据新增，2000条保存一次。
	 */
	@Test
	public void addData02()
	{
		//创建索引和结构
		this.createIndexAndMaper(StudentModel.class);
		//批量插入数据集合
		List<StudentModel> itemModel =  new ArrayList();
		//StudentModel模型
		StudentModel studentModel;
		int count  = 0;
		int size   = 2000;
        //开始循环模拟数据
		for(int i=1000001; i<=3000000; i++) {

			//数据准备========================================
			studentModel = new StudentModel();
			studentModel.setId("id_"+i);
			studentModel.setStudentName("王麻子-"+i);
			studentModel.setAddress("重庆渝北-"+i);
			studentModel.setNumber(new Double(count));
			if(i<400) {
				studentModel.setSchool("重庆文理学院");
			}else if(i<500) {
				studentModel.setSchool("重庆理工大学人力资源");
			}else if(i<600) {
				studentModel.setSchool("重庆工商大学");
			}else if(i<600) {
				studentModel.setSchool("重庆大学大学");
			}else{
				studentModel.setSchool("北京人民大学");
			}
			//装满一定数量后，批量执行
			itemModel.add(studentModel);
			count++;
			//准备完毕========================================
            //满2000条，作为一批保存
			if(count % size ==0) {

				log.info("开始插入第{}次",(i+1)/size);
				//调用保存方法save
				studentRepository.saveAll(itemModel);

				//重新计算,清空批量集合
				itemModel.clear();
				//重新计算条数
				count = 0;
			}
		}
		if(count<size){
			studentRepository.saveAll(itemModel);
		}

	}


	/**
	 * 删除索引,包括类型、数据、请慎重操作
	 */
	@Test
	public void removeIndex(){


		//删除索引下所有内容，包括数据
		boolean rs = elasticsearchTemplate.deleteIndex(StudentModel.class);

		log.info("删除索引结果：=》"+rs);
	}

	/**
	 * 更新数据，以id为条件更新
	 * 也可以批量更新
	 */
	@Test
	public  void updateData(){

		StudentModel studentModel = new StudentModel();
		studentModel.setId("id_1");
		studentModel.setStudentName("李四");
		studentModel.setAddress("广西");
		studentModel.setSchool("广西大学");
		studentModel.setNumber(new Double(670));

		StudentModel rs = studentRepository.save(studentModel);

		log.info("更新结果=》》"+rs.toString());
	}


	/**
	 * 全部查询
	 */
	@Test
	public void selectAll(){
		Iterable<StudentModel> studentModelList = studentRepository.findAll();

		studentModelList.forEach(System.out::println);
	}

	/**
	 * 通过id查询数据
	 */
	@Test
	public void selectById()
	{
		StudentModel studentModel = studentRepository.findById("id_1").get();

		log.info(studentModel.toString());
	}

	/**
	 * 查询结果排序
	 */
	@Test
	public void selectSort(){
		//对某个字段排序
		Sort sort = new Sort(Sort.Direction.DESC,"id");
		Iterable<StudentModel> studentModelList = studentRepository.findAll(sort);

		studentModelList.forEach(System.out::println);
	}

	/**
	 * 精准查询
	 */
	@Test
	public void select01(){
		//查询条件
		QueryBuilder serchQuery = QueryBuilders.termQuery("studentName", "莫瑞祥");

		//
		Iterable<StudentModel> studentModelList = studentRepository.search(serchQuery);
		studentModelList.forEach(System.out::println);
	}

	/**
	 * 精准查询
	 */
	@Test
	public void selecttermQuery(){
		//查询条件
		QueryBuilder serchQuery = QueryBuilders.termQuery("studentName", "瑞祥");

		//
		Iterable<StudentModel> studentModelList = studentRepository.search(serchQuery);
		studentModelList.forEach(System.out::println);
	}

	/**
	 * 模糊查询，支持通配符
	 */
	@Test
	public void selectwildcardQuery(){
		//查询条件
		DisMaxQueryBuilder disMaxQueryBuilder = QueryBuilders.disMaxQuery();
		//设置权重,匹配studentName、disrector多个字段
		//QueryBuilder queryBuilder01  = QueryBuilders.matchQuery("studentName", "祥瑞").boost(2f);
		//QueryBuilder queryBuilder02  = QueryBuilders.matchQuery("address", "龙盘").boost(2f);
		QueryBuilder queryBuilder03  = QueryBuilders.matchQuery("school", "人大").boost(2f);
		//disMaxQueryBuilder.add(queryBuilder01);
		//disMaxQueryBuilder.add(queryBuilder02);
		disMaxQueryBuilder.add(queryBuilder03);
		//分页
		Pageable pageable = PageRequest.of(0, 20);

		//调用方法
		Page<StudentModel> page = studentRepository.search(disMaxQueryBuilder,pageable);
		log.info("共有：[{}]条信息",page.getTotalElements());
		log.info("共有：[{}]页",page.getTotalPages());
		//当前页打印输出
		List<StudentModel> list = page.getContent();
		list.forEach(System.out::println);
	}


	private void createIndexAndMaper(Class clazz){
		//创建索引
		elasticsearchTemplate.createIndex(clazz);
		elasticsearchTemplate.putMapping(clazz);
	}

}
