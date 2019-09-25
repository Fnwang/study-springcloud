package com.b2c.es;

import com.b2c.es.esRepository.MemberRepository;
import com.b2c.es.model.Employee;
import com.b2c.es.model.MemberModel;
import io.searchbox.action.Action;
import io.searchbox.action.BulkableAction;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试用jest来访问和操作ES
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ESTestJestClient {

    private  static  final  String indexV = "employee_index";

    private  static  final  String typeV  = "employee";

    //ec客户端操作
    @Autowired
    JestClient jestClient;

    @Autowired
    MemberRepository memberRepository;

    /**
     * 创建索引，并传入数据
     * @throws IOException
     */
    @Test
    public void createIndex() throws IOException {



        Employee employee = new Employee();

//        employee.setId(new Integer(1));
//        employee.setName("张三");
//        employee.setAge(new Float(21));

        employee.setId(new Integer(2));
        employee.setName("李四");
        employee.setAge(new Float(25));

        //进入Action接口 按住：contr+H 查找Action接口的实现类
        Action action = new Index.Builder(employee).index(indexV).type(typeV).build();
        JestResult execute = this.jestClient.execute(action);
        System.out.println("执行结果："+execute.getJsonObject());
    }



    /**
     * 检索信息
     */
    @Test
    public void search() throws IOException {
        String query = "{"
                     +"   \"query\":{ "
                     +"       \"match\":{ \"name\":\"张三\"}"
                     +"   }  "
                     +" }";
        Action action = new Search.Builder(query).addIndex(indexV).addType(typeV).build();
        JestResult rs = this.jestClient.execute(action);
        System.out.println("搜索结果："+rs.getJsonObject());
    }


    //---批量操作
    @Test
    public void bullExecute() throws IOException {

        List<Action> bulkableAction = new ArrayList<>();
        Action indexAction = null;
        Employee employee = null;

        for(int i=0; i<100; i++) {
            employee = new Employee();
            employee.setId(new Integer(i));
            employee.setName("李四-"+i);
            employee.setAge(new Float(i));
            //装入action
            //indexAction = new Index.Builder(employee).addIndex(indexV).addType(typeV).build();
            //装入list
            bulkableAction.add(indexAction);
        }

        //创建批量操作对象
        //Action action = new Bulk.Builder().addAction(bulkableAction).build();
        //批量执行
        //JestResult execute = this.jestClient.execute(action);
        //测试打印

    }


    /**
     *
     */
    @Test
    public void getByUsernameAndCityAndSex(){
        List<MemberModel> list = memberRepository.getByUsernameAndCityAndSex("张", "重庆", "男");
        list.forEach(System.out::println);
    }
}
