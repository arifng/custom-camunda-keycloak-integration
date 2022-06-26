package com.arifng.camunda.service;

import com.arifng.camunda.config.MyBatisCustomConfiguration;
import com.arifng.camunda.dto.CustomCommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import org.camunda.bpm.engine.impl.db.ListQueryParameterObject;
/**
 * Created by Rana on 06/12/2021.
 */
@Service
public class TaskListService {
    @Autowired
    private MyBatisCustomConfiguration customConfiguration;

    public List<CustomCommentDto> getCommentsPerProcess(final String assignee, final String processInstanceId) {
        SqlSession session = customConfiguration.initializeMyBatisSqlSessionFactory().openSession();
        try {
            // You can use this object to leverage Camunda Pagination features
            ListQueryParameterObject queryParameterObject = new ListQueryParameterObject();
            queryParameterObject.setParameter(processInstanceId);

            List<CustomCommentDto> tasks = session.selectList("customComments.selectCommentsPerProcess", queryParameterObject);
            return tasks;
        } finally {
            session.close();
        }
    }

}
