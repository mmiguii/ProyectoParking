package backend.worker;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Worker {

	private String name;
	private String surname;
	private int id;
	private ZonedDateTime startDate;
	private int seniority;
	private double salary;

	public Worker() {
		super();
	}

	public Worker(String name, String surname, int id, ZonedDateTime startDate, int seniority, double salary) {
		super();
		this.name = name;
		this.surname = surname;
		this.id = id;
		this.startDate = startDate;
		this.seniority = seniority;
		this.salary = salary;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ZonedDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(ZonedDateTime startDate) {
		this.startDate = startDate;
	}

	public int getSeniority() {
		return seniority;
	}

	public void setSeniority(int seniority) {
		this.seniority = seniority;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
		return String.format("%s, %s, %d, %s, %d, %.2f", name, surname, id, dateFormatter.format(startDate), seniority,
				salary);
	}

}
