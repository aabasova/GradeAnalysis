package gui;

public class ModuleData {
	private int ECTS;
	private double Grade;
	private String Module;

	public ModuleData(int eCTS, double Grade, String Module) {
		super();
		ECTS = eCTS;
		this.Grade = Grade;
		this.Module = Module;
	}

	public int getECTS() {
		return ECTS;
	}

	public double getGrade() {
		return Grade;
	}

	public String getModule() {
		return Module;
	}

	public void setECTS(int eCTS) {
		ECTS = eCTS;
	}

	public void setGrade(double ModuleDataGrade) {
		Grade = ModuleDataGrade;
	}

	public void setModule(String module) {
		Module = module;
	}

}
