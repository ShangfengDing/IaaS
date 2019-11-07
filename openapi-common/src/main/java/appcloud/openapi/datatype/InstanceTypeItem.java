package appcloud.openapi.datatype;

public class InstanceTypeItem {
	private String InstanceTypeId;
	private Integer CpuCoreCount;
	private Integer MemorySize;
	private Integer HardDisk;
	private Integer BandWidth;
	private int YearPrice;
	private int MonthPrice;
	private int DayPrice;
	private int Hourprice;
	
	public InstanceTypeItem()
	{
		
	}

	public InstanceTypeItem(String InstanceTypeId,int CpuCoreCount,int MemorySize,int HardDisk,int BandWidth,int YearPrice,
			int MonthPrice,int DayPrice,int Hourprice)
	{
		this.InstanceTypeId=InstanceTypeId;
		this.CpuCoreCount=CpuCoreCount;
		this.MemorySize=MemorySize;
		this.HardDisk=HardDisk;
		this.BandWidth=BandWidth;
		this.YearPrice=YearPrice;
		this.MonthPrice=MonthPrice;
		this.DayPrice=DayPrice;
		this.Hourprice=Hourprice;
	}

	public String getInstanceTypeId() {
		return InstanceTypeId;
	}

	public void setInstanceTypeId(String instanceTypeId) {
		InstanceTypeId = instanceTypeId;
	}

	public Integer getCpuCoreCount() {
		return CpuCoreCount;
	}

	public void setCpuCoreCount(Integer cpuCoreCount) {
		CpuCoreCount = cpuCoreCount;
	}

	public Integer getMemorySize() {
		return MemorySize;
	}

	public void setMemorySize(Integer memorySize) {
		MemorySize = memorySize;
	}

	public Integer getHardDisk() {
		return HardDisk;
	}

	public void setHardDisk(Integer hardDisk) {
		HardDisk = hardDisk;
	}

	public Integer getBandWidth() {
		return BandWidth;
	}

	public void setBandWidth(Integer bandWidth) {
		BandWidth = bandWidth;
	}

	public int getYearPrice() {
		return YearPrice;
	}

	public void setYearPrice(int yearPrice) {
		YearPrice = yearPrice;
	}

	public int getMonthPrice() {
		return MonthPrice;
	}

	public void setMonthPrice(int monthPrice) {
		MonthPrice = monthPrice;
	}

	public int getDayPrice() {
		return DayPrice;
	}

	public void setDayPrice(int dayPrice) {
		DayPrice = dayPrice;
	}

	public int getHourprice() {
		return Hourprice;
	}

	public void setHourprice(int hourprice) {
		Hourprice = hourprice;
	}
	
}
