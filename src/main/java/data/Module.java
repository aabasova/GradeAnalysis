package data;

public class Module implements Comparable<Module>{
	private int ECTS;
	private double grade;
	private String name;
	private int semester;

	public Module(int eCTS, double grade, String name, int semester) {
		this.ECTS = eCTS;
		this.grade = grade;
		this.name = name;
		this.semester = semester;
	}

	public int getECTS() {
		return ECTS;
	}

	public double getGrade() {
		return grade;
	}

	public String getName() {
		return name;
	}

	public int getSemester() {
		return semester;
	}

	public int compareTo(Module o) {
		return this.name.compareTo(o.getName());
	}
	
	

}
