package com.arifng.camunda.service.impl;

import com.arifng.camunda.config.MyBatisCustomConfiguration;
import com.arifng.camunda.service.ActivityInstanceService;
import com.arifng.camunda.dto.ActivityInstanceDto;
import org.apache.ibatis.session.SqlSession;
import org.camunda.bpm.engine.impl.db.ListQueryParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Rana on 07/12/2021.
 */
@Service
public class ActivityInstanceServiceImpl implements ActivityInstanceService {
    @Autowired
    private MyBatisCustomConfiguration customConfiguration;

    @Override
    public List<ActivityInstanceDto> getActivityInstancesByProcessId(String processInstanceId) {
        SqlSession session = customConfiguration.initializeMyBatisSqlSessionFactory().openSession();
        try {
            ListQueryParameterObject queryParameterObject = new ListQueryParameterObject();
            queryParameterObject.setParameter(processInstanceId);

            return session.selectList(
                    "customComments.selectActivityWithComment", queryParameterObject);
        } finally {
            session.close();
        }
    }
}
