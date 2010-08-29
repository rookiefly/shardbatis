/**
 * 
 */
package org.shardbatis.test;

import java.io.Serializable;


/**
 * @author CodeGen --powered by Sean
 *
 */
public class AppTest implements Serializable{
	private static final long serialVersionUID = 5594372943378452480L;
	private String cnt;
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public String getCnt() {
		return cnt;
	}
	public void setCnt(String cnt) {
		this.cnt = cnt;
	}
}