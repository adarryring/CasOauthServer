package com.cs.core.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jasig.services.persondir.IPersonAttributes;
import org.jasig.services.persondir.support.AttributeNamedPersonImpl;
import org.jasig.services.persondir.support.StubPersonAttributeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.github.inspektr.audit.support.Slf4jLoggingAuditTrailManager;
import org.jasig.cas.authentication.PolicyBasedAuthenticationManager;
import org.jasig.cas.CentralAuthenticationServiceImpl;
import org.pac4j.core.exception.RequiresHttpAction;

/**
 * @author xh
 * remove
 */
//@Component
public class AccountAttributeDao extends StubPersonAttributeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public IPersonAttributes getPerson(String uid) {
        String sql = "select * from Account where email=? or username=?";
        final Map<String, Object> values = jdbcTemplate.queryForMap(sql, new Object[]{uid, uid});

        Map<String, List<Object>> attributes = new HashMap<>(1);
        attributes.put("password", Collections.singletonList(values.get("password")));
        return new AttributeNamedPersonImpl(attributes);
    }

}
