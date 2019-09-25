package com.b2c.es.component;

import com.b2c.es.model.GRADES;
import com.b2c.es.model.GradesModel;
import com.b2c.es.esRepository.GradesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据初始化
 */
@Component
public class GradesInit {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GradesRepository gradesRepository;

    @PostConstruct
    public void innit(){

        Iterable<GradesModel> gradesModelsList = gradesRepository.findAll();
        if(gradesModelsList.iterator().hasNext()) {
            log.info("已经有数据。");
            return;
        }
        //准备数据
        List<GradesModel> list = new ArrayList();
        list.add(new GradesModel("1", GRADES.GRADES1.getVal(),"新生"));
        list.add(new GradesModel("2", GRADES.GRADES2.getVal(),"二年级的学生"));
        list.add(new GradesModel("3", GRADES.GRADES3.getVal(),"老油条学生"));
        list.add(new GradesModel("4", GRADES.GRADES4.getVal(),"马上毕业的学生"));

        //一次性执行
        Iterable<GradesModel> rs = gradesRepository.saveAll(list);
        log.info("执行结果===》"+rs.toString());
    }
}
