package com.nhry.common.ladp;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class LadpUserService {
	DirContext ctx = null;
	
	public boolean init() {
		boolean flag = false;
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://10.6.251.250:1389");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL,"uid=super,ou=People,o=newhopedairy,o=isp");
		env.put(Context.SECURITY_CREDENTIALS, "smart123");
		env.put("com.sun.jndi.ldap.connect.pool", "true");
		try {
			ctx = new InitialDirContext(env);
			flag = true;
			// System.out.println("authentication success");
		} catch (javax.naming.AuthenticationException e) {
			System.out.println("failed1 " + e);
		} catch (NamingException e) {
			System.out.println("failed2 " + e);
		}
		return flag;
	}
	
	public String getObjectsByFilter(String basedn, String filter) throws NamingException {
		NamingEnumeration<SearchResult> answer = search(basedn, filter);
		int totalResults = 0;
		int rows = 0;
		while (answer.hasMoreElements()) {
			SearchResult sr = (SearchResult) answer.next();// 得到符合搜索条件的DN
			++rows;
			String dn = sr.getName();
			System.out.println(dn);
			Attributes Attrs = sr.getAttributes();// 得到符合条件的属性集
			if (Attrs != null) {
				try {
					for (NamingEnumeration ne = Attrs.getAll(); ne.hasMore();) {
						Attribute Attr = (Attribute) ne.next();// 得到下一个属性
						System.out.println(" AttributeID=属性名：" + Attr.getID().toString());
						// 读取属性值
						for (NamingEnumeration e = Attr.getAll(); e.hasMore(); totalResults++) {
							String company = e.next().toString();
							System.out.println("    AttributeValues=属性值：" + company);
						}
						System.out.println("    ---------------");
					}
				} catch (NamingException e) {
					System.err.println("Throw Exception : " + e);
				}
			}
		}
		System.out.println("************************************************");
		System.out.println("Number: " + totalResults);
		System.out.println("总共用户数：" + rows);
		return "";
	}
	
	public NamingEnumeration<SearchResult> search(String basedn, String s) {
		// 设置返回的属性，可以使用*来返回大部份的属性值，但如
		String[] attrs = { "*" };
		NamingEnumeration<SearchResult> en = null;
		try {
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			constraints.setReturningAttributes(attrs);
			en = ctx.search(basedn, s, constraints);
		} catch (Exception e) {
			System.out.println("Exception in search():" + e);
		}
		return en;
	}

}
