package com.example.tmnewa.service;


import com.example.tmnewa.authentications.CustomSSLSocketFactory;
import com.example.tmnewa.config.TWNEWAConfigProperties;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.directory.InitialDirContext;
import java.net.URI;
import java.util.Properties;

@Service
@Slf4j
public class ADLoginService {

    @Autowired
    TWNEWAConfigProperties twnewaConfigProperties;

    public boolean activeDirectoryCheck(String account, String password) {

        try {

            if (Strings.isEmpty(twnewaConfigProperties.getAdDomain()) || Strings.isEmpty(twnewaConfigProperties.getAdLdap()) || Strings.isEmpty(twnewaConfigProperties.getAdBase())) {
                throw new Exception("AD Information is not Setting ");
            }
            Properties env = new Properties();
            // 使用UPN格式：User@domain或SamAccountName格式：domain\\User
            String adminName = account + "@" + twnewaConfigProperties.getAdDomain();


            URI uri = URI.create(twnewaConfigProperties.getAdLdap());
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.SECURITY_AUTHENTICATION, "simple");// LDAP訪問安全級別："none","simple","strong"
            env.put(Context.SECURITY_PRINCIPAL, adminName);// AD User
            env.put(Context.SECURITY_CREDENTIALS, password);// AD Password
            env.put(Context.PROVIDER_URL, twnewaConfigProperties.getAdLdap());// LDAP工廠類

            if (uri.getScheme().equalsIgnoreCase("ldaps")) {
                env.put(Context.SECURITY_PROTOCOL, "ssl");
                env.put("java.naming.ldap.factory.socket", CustomSSLSocketFactory.class.getName());
            }

            InitialDirContext ctx = new InitialDirContext(env);


            // 搜索控制器
//            SearchControls searchCtls = new SearchControls();
//            // 創建搜索控制器
//            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//            // LDAP搜索過濾器類，此處只獲取AD域用户，所以條件為用户user或者person均可
//            // (&(objectCategory=person)(objectClass=user)(name=*))
//            String searchFilter = String.format("sAMAccountName=%s", account);
//            // AD域節點結構
//            String searchBase = adbase;
//            String returnedAtts[] = {"sn", "cn", "mail", "name", "userPrincipalName",
//                    "department", "sAMAccountName", "whenChanged"};
//            searchCtls.setReturningAttributes(returnedAtts);
//            NamingEnumeration<SearchResult> answer = ctx.search(searchBase, searchFilter, searchCtls);


            ctx.close();
            return true;
        } catch (Exception e) {
            log.error("AD Login Error ={0}", e);
            return false;
        }


    }
}
