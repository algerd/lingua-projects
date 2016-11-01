package ru.javafx.multitenant;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.util.Assert;

public class MultitenantDatasource extends AbstractDataSource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private MultitenantData multitenantData;      
    private Map<Object, DataSource> resolvedDataSources;    
    private DataSource defaultTargetDataSource;

    public void setMultitenantData(MultitenantData multitenantData) {
        this.multitenantData = multitenantData;
    }  
    
    protected Object determineCurrentLookupKey() {        
        Object flag = MultitenantContext.getMultitenantFlag();
        logger.info("Multitenant flag: "+ flag);            
        if (flag == null) {
            return MultitenantContext.getMultitenantId();
        }
        else {           
            MultitenantContext.setMultitenantFlag(null);
            return multitenantData.getId();
        }       
    }

    protected DataSource determineTargetDataSource() {
        Assert.notNull(resolvedDataSources, "DataSource router not initialized");
        Assert.notNull(defaultTargetDataSource, "Default DataSource not initialized");
        
        Object lookupKey = determineCurrentLookupKey();
        logger.info("lookupKey: " + (lookupKey == null ? "null" : lookupKey));
        DataSource dataSource = (lookupKey == null) ? defaultTargetDataSource : resolvedDataSources.get(lookupKey);
        
        if (dataSource == null) {
            throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
        }
        return dataSource;
    }
    
    public void setResolvedDataSources(Map<Object, DataSource> resolvedDataSources) {
        this.resolvedDataSources = resolvedDataSources;
    }
    
    public void setDefaultTargetDataSource(DataSource defaultTargetDataSource) {
		this.defaultTargetDataSource = defaultTargetDataSource;
	}

    @Override
    public Connection getConnection() throws SQLException {
        return determineTargetDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return determineTargetDataSource().getConnection(username, password);
    }
  
    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return (T) this;
        }
        return determineTargetDataSource().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return (iface.isInstance(this) || determineTargetDataSource().isWrapperFor(iface));
    }
  
}
