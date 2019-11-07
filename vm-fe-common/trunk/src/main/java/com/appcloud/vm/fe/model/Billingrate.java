package com.appcloud.vm.fe.model;

import java.io.Serializable;
import javax.persistence.*;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

//import com.free4lab.account.model.BillingRule;


/**
 * The persistent class for the billingrate database table.
 * 
 */
@Entity
@Table(name = "billingrate")
public class Billingrate implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Billingrate.class);
	
	public final static String PTYPE_PACKAGE = "VMPACKAGE";
	public final static String PTYPE_CPU = "CPU";
	public final static String PTYPE_MEM = "MEM";
	public final static String PTYPE_HD = "HD";
	public final static String PTYPE_BW = "BW";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private int bandwidth;

	private int clusterid;

	private int cpu;

	private int dayprice;

	private String description;

	private int harddisk;

	private int hourprice;

	private int memory;

	private int monthprice;

	private String name;

	private String ptype;

	private int yearprice;
	
	public Billingrate(){
		
	}

    public Billingrate(JSONObject o) {
    	try {
			LOGGER.info(o.has("id"));
			if(o.has("id") && o.getInt("id") != 0){
				this.id = Integer.parseInt( o.getString("id") );
			}
			o.getString("");
			Integer.parseInt( o.getString("") );
			this.bandwidth = Integer.parseInt( o.getString("bandwidth") );
			this.clusterid=Integer.parseInt( o.getString("clusterid") );
			this.cpu=Integer.parseInt( o.getString("cpu") );
			this.dayprice=Integer.parseInt( o.getString("dayprice") );
			this.description=o.getString("description");
			this.harddisk=Integer.parseInt( o.getString("harddisk") );
			this.hourprice=Integer.parseInt( o.getString("hourprice") );
			this.memory=Integer.parseInt( o.getString("memory") );
			this.monthprice=Integer.parseInt( o.getString("monthprice") );
			this.name=o.getString("name");
			this.ptype=o.getString("ptype");
			this.yearprice=Integer.parseInt( o.getString("yearprice") );
		} catch (JSONException e) {
			LOGGER.debug("invalid Billingrate",e);
		}
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBandwidth() {
		return this.bandwidth;
	}

	public void setBandwidth(int bandwidth) {
		this.bandwidth = bandwidth;
	}

	public int getClusterid() {
		return this.clusterid;
	}

	public void setClusterid(int clusterid) {
		this.clusterid = clusterid;
	}

	public int getCpu() {
		return this.cpu;
	}

	public void setCpu(int cpu) {
		this.cpu = cpu;
	}

	public int getDayprice() {
		return this.dayprice;
	}

	public void setDayprice(int dayprice) {
		this.dayprice = dayprice;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getHarddisk() {
		return this.harddisk;
	}

	public void setHarddisk(int harddisk) {
		this.harddisk = harddisk;
	}

	public int getHourprice() {
		return this.hourprice;
	}

	public void setHourprice(int hourprice) {
		this.hourprice = hourprice;
	}

	public int getMemory() {
		return this.memory;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	public int getMonthprice() {
		return this.monthprice;
	}

	public void setMonthprice(int monthprice) {
		this.monthprice = monthprice;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPtype() {
		return this.ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public int getYearprice() {
		return this.yearprice;
	}

	public void setYearprice(int yearprice) {
		this.yearprice = yearprice;
	}

}