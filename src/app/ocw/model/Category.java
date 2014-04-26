package app.ocw.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for an OCW Category
 * 
 * @author Nick Ferraro
 *
 */
public class Category {
	private final Category parent;
	private final String name;
	private final int courseCount;
	private final List<Category> children;
	
	public Category(String name, int courseCount) {
		this(name, courseCount, null);
	}
	public Category(String name, int courseCount, Category parent) {
		this.name = (name == null ? "no-name" : name);
		this.courseCount = courseCount;
		this.parent = parent;
		this.children = new ArrayList<Category>();
	}
	
	public Category getParent() {
		return this.parent;
	}
	public String getName() {
		return this.name;
	}
	public int getCourseCount() {
		return this.courseCount;
	}
	public List<Category> getChildren() {
		return this.children;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
