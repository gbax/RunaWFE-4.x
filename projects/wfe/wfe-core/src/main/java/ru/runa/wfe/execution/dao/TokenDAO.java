package ru.runa.wfe.execution.dao;

import java.util.List;

import ru.runa.wfe.commons.dao.GenericDAO;
import ru.runa.wfe.execution.Token;
import ru.runa.wfe.lang.NodeType;

/**
 * DAO for {@link Token}.
 * 
 * @author dofs
 * @since 4.0
 */
@SuppressWarnings("unchecked")
public class TokenDAO extends GenericDAO<Token> {

    public List<Token> findActiveTokens(NodeType nodeType) {
        return getHibernateTemplate().find("from Token where nodeType=? and endDate is null", nodeType);
    }

}
